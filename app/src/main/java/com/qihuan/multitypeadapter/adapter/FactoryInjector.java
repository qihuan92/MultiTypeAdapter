package com.qihuan.multitypeadapter.adapter;

import java.lang.reflect.InvocationTargetException;

/**
 * FactoryInjector
 *
 * @author qi
 * @date 2018/11/30
 */
public class FactoryInjector {

    private static final String SUFFIX_IMPL = "$$Impl";

    public static void inject(Object target) {
        String targetClassName = target.getClass().getName();
        try {
            Class<?> realClass = Class.forName(targetClassName + SUFFIX_IMPL);
            Object o = realClass.getConstructor().newInstance();
            // TODO: 2018/11/30  
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
