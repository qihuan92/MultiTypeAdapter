package com.qihuan.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.sun.tools.javac.code.Symbol;

import javax.annotation.Nullable;

/**
 * Res
 *
 * @author qi
 * @date 2018/12/5
 */
final class Res {
    private static final String R = "R";
    private static final ClassName ANDROID_R = ClassName.get("android", R);

    final int value;
    final CodeBlock codeBlock;
    final boolean qualifed;

    Res(int value) {
        this(value, null);
    }

    Res(int value, @Nullable Symbol rSymbol) {
        this.value = value;
        if (rSymbol == null) {
            this.codeBlock = CodeBlock.of("$L", value);
            this.qualifed = false;
            return;
        }
        ClassName className = ClassName.get(rSymbol.packge().getQualifiedName().toString(), R, rSymbol.enclClass().name.toString());
        String resourceName = rSymbol.name.toString();

        this.codeBlock = className.topLevelClassName().equals(ANDROID_R)
                ? CodeBlock.of("$L.$N", className, resourceName)
                : CodeBlock.of("$T.$N", className, resourceName);
        this.qualifed = true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Res && value == ((Res) o).value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Please use value or code explicitly");
    }
}
