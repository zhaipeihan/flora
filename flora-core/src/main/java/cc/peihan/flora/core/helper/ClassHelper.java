package cc.peihan.flora.core.helper;


import cc.peihan.flora.core.annotation.Controller;
import cc.peihan.flora.core.util.ClassUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用来获取特定注解的类
 */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String baseAppPackageName = ConfigHelper.getBasePackageName();
        CLASS_SET = ClassUtil.getClassSet(baseAppPackageName);
    }


    public static Set<Class<?>> getAllClass() {
        return CLASS_SET;
    }


    public static Set<Class<?>> getRequireClass(Class<? extends java.lang.annotation.Annotation> requireClazz) {
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(clazz -> {
            if (clazz.isAnnotationPresent(requireClazz)) {
                classSet.add(clazz);
            }
        });
        return classSet;
    }

    public static Set<Class<?>> getClassesByPackageName(String packageName) {
        return ClassUtil.getClassSet(packageName);
    }
}
