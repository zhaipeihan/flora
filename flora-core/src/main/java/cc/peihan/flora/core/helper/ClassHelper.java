package cc.peihan.flora.core.helper;

import cc.peihan.pansy.core.annotation.Controller;
import cc.peihan.pansy.core.util.ClassUtil;

import java.util.HashSet;
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

    public static Set<Class<?>> getControllerClass() {
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(clazz -> {
            if (clazz.isAnnotationPresent(Controller.class)) {
                classSet.add(clazz);
            }
        });
        return classSet;
    }

}
