package com.qihuan.complier;

import com.google.auto.service.AutoService;
import com.qihuan.annotation.ItemType;
import com.qihuan.complier.bean.ViewHolderInfoBean;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ItemTypeProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private List<ViewHolderInfoBean> viewHolderInfoList;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        viewHolderInfoList = new ArrayList<>();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> annotations = new LinkedHashSet<>();
        annotations.add(ItemType.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(ItemType.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                error(element, "Only classes can be annotated with @%s", ItemType.class.getSimpleName());
                return true;
            }
            analysisAnnotated(element);
        }
        genTypeFactory();
        initTypeFactory();
        return false;
    }

    private void analysisAnnotated(Element element) {
        ItemType annotation = element.getAnnotation(ItemType.class);
        int layoutId = annotation.layoutId();
        TypeMirror typeMirror = getTypeMirror(annotation);
        viewHolderInfoList.add(new ViewHolderInfoBean()
                .setLayoutId(layoutId)
                .setDataClassTypeMirror(typeMirror)
                .setViewHolderTypeMirror(element.asType())
        );
    }

    private void genTypeFactory() {
        ParameterSpec typeParameterSpec = ParameterSpec.builder(TypeName.INT, "type").build();
        ParameterSpec viewParameterSpec = ParameterSpec.builder(ClassName.get("android.view", "View"), "view").build();
        ParameterSpec itemParameterSpec = ParameterSpec.builder(ClassName.get(Const.ADAPTER_PACKAGE_NAME, "Item"), "data").build();
        CodeBlock.Builder typeBlock = CodeBlock.builder();
        CodeBlock.Builder createViewHolderBlock = CodeBlock.builder();

        for (ViewHolderInfoBean viewHolderInfoBean : viewHolderInfoList) {
            int layoutId = viewHolderInfoBean.getLayoutId();
            TypeMirror dataClassTypeMirror = viewHolderInfoBean.getDataClassTypeMirror();
            TypeMirror viewHolderTypeMirror = viewHolderInfoBean.getViewHolderTypeMirror();

            typeBlock.beginControlFlow("if ($N instanceof $T)", itemParameterSpec, TypeName.get(dataClassTypeMirror))
                    .indent()
                    .addStatement("return $L", layoutId)
                    .unindent()
                    .endControlFlow();

            createViewHolderBlock.beginControlFlow("if ($N == $L)", typeParameterSpec, layoutId)
                    .indent()
                    .addStatement("return new $T($N)", TypeName.get(viewHolderTypeMirror), viewParameterSpec)
                    .unindent()
                    .endControlFlow();
        }

        // 写入文件
        JavaFile javaFile = JavaFile.builder(Const.ADAPTER_PACKAGE_NAME,
                TypeSpec.classBuilder("TypeFactoryImpl")
                        .addSuperinterface(ClassName.get(Const.ADAPTER_PACKAGE_NAME, "TypeFactory"))
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(
                                MethodSpec.methodBuilder("type")
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(Override.class)
                                        .addParameter(itemParameterSpec)
                                        .returns(TypeName.INT)
                                        .addCode(CodeBlock.builder()
                                                .add(typeBlock.build())
                                                .addStatement("return -1")
                                                .build()
                                        )
                                        .build()
                        )
                        .addMethod(
                                MethodSpec.methodBuilder("createViewHolder")
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(Override.class)
                                        .addParameter(typeParameterSpec)
                                        .addParameter(viewParameterSpec)
                                        .addCode(createViewHolderBlock.build())
                                        .addStatement("return null")
                                        .returns(ClassName.get(Const.ADAPTER_PACKAGE_NAME, "BaseViewHolder"))
                                        .build()
                        )
                        .build())
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTypeFactory() {

    }

    @SuppressWarnings("SameParameterValue")
    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private TypeMirror getTypeMirror(ItemType annotation) {
        try {
            annotation.dataClass();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }
}
