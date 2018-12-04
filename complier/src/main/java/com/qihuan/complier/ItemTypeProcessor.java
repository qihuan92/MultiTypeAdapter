package com.qihuan.complier;

import com.google.auto.service.AutoService;
import com.qihuan.annotation.BindItemView;
import com.qihuan.complier.bean.ViewHolderInfo;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import static com.qihuan.complier.Const.ADAPTER_PACKAGE_NAME;
import static com.qihuan.complier.Const.TYPE_FACTORY;
import static com.qihuan.complier.Const.TYPE_FACTORY_IMPL;

/**
 * ItemTypeProcessor
 *
 * @author qi
 * @date 2018-12-04
 */
@SuppressWarnings("unused")
@AutoService(Processor.class)
public class ItemTypeProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private List<ViewHolderInfo> viewHolderInfoList;

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
        annotations.add(BindItemView.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindItemView.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                error(element, "Only classes can be annotated with @%s", BindItemView.class.getSimpleName());
                return true;
            }
            analysisAnnotated(element);
        }
        genTypeFactory();
        return false;
    }

    @Nullable
    private TypeMirror getSuperGenericClass(@Nonnull Element element) {
        TypeElement typeElement = (TypeElement) element;
        DeclaredType declaredType = (DeclaredType) typeElement.getSuperclass();
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments == null || typeArguments.size() == 0) {
            return null;
        }
        return typeArguments.get(0);
    }

    private void analysisAnnotated(Element element) {
        BindItemView annotation = element.getAnnotation(BindItemView.class);
        int layoutId = annotation.value();
        TypeMirror typeMirror = getSuperGenericClass(element);
        if (typeMirror == null) {
            error(element, "ViewHolder 缺少泛型");
        }
        viewHolderInfoList.add(new ViewHolderInfo()
                .setLayoutId(layoutId)
                .setDataClassTypeMirror(typeMirror)
                .setViewHolderTypeMirror(element.asType())
        );
    }

    private void genTypeFactory() {
        ParameterSpec typeParameterSpec = ParameterSpec.builder(TypeName.INT, "type").build();
        ParameterSpec viewParameterSpec = ParameterSpec.builder(ClassName.get("android.view", "View"), "view").build();
        ParameterSpec itemParameterSpec = ParameterSpec.builder(ClassName.get(ADAPTER_PACKAGE_NAME, "Item"), "data").build();
        CodeBlock.Builder typeBlock = CodeBlock.builder();
        CodeBlock.Builder createViewHolderBlock = CodeBlock.builder();

        for (ViewHolderInfo viewHolderInfo : viewHolderInfoList) {
            int layoutId = viewHolderInfo.getLayoutId();
            TypeMirror dataClassTypeMirror = viewHolderInfo.getDataClassTypeMirror();
            TypeMirror viewHolderTypeMirror = viewHolderInfo.getViewHolderTypeMirror();

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
        JavaFile javaFile = JavaFile.builder(ADAPTER_PACKAGE_NAME,
                TypeSpec.classBuilder(TYPE_FACTORY_IMPL)
                        .addSuperinterface(ClassName.get(ADAPTER_PACKAGE_NAME, TYPE_FACTORY))
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(
                                MethodSpec.methodBuilder("type")
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(Override.class)
                                        .addParameter(itemParameterSpec)
                                        .returns(TypeName.INT)
                                        .addCode(CodeBlock.builder()
                                                .add(typeBlock.build())
                                                .addStatement("return View.NO_ID")
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
                                        .returns(ClassName.get(ADAPTER_PACKAGE_NAME, "BaseViewHolder"))
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

    @SuppressWarnings({"SameParameterValue", "unused"})
    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    @SuppressWarnings({"SameParameterValue", "unused"})
    private void note(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args), e);
    }
}
