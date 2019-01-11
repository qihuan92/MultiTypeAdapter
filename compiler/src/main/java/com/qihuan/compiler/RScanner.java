package com.qihuan.compiler;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

class RScanner extends TreeScanner {

    Map<Integer, Res> resourceIds = new LinkedHashMap<>();

    @Override
    public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
        Symbol symbol = jcFieldAccess.sym;
        if (symbol.getEnclosingElement() != null
                && symbol.getEnclosingElement().getEnclosingElement() != null
                && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
            try {
                int value = (Integer) requireNonNull(((Symbol.VarSymbol) symbol).getConstantValue());
                resourceIds.put(value, new Res(value, symbol));
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void visitLiteral(JCTree.JCLiteral jcLiteral) {
        try {
            int value = (Integer) jcLiteral.value;
            resourceIds.put(value, new Res(value));
        } catch (Exception ignored) {
        }
    }

    void reset() {
        resourceIds.clear();
    }
}