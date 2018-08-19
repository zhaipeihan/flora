package cc.peihan.flora.core.protocol.fcp;

import cc.peihan.flora.core.annotation.FCPService;
import cc.peihan.flora.core.helper.ClassHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class FcpMethodMapping {

    private static Map<String, Class<?>> FCP_CLASS_MAPPING;
    private static Map<String, Map<String, Method>> FCP_METHOD_MAPPING;

    public static void init() {
        Set<Class<?>> classSet = ClassHelper.getRequireClass(FCPService.class);
        FCP_METHOD_MAPPING = new HashMap<>(16);
        FCP_CLASS_MAPPING = new HashMap<>(16);
        if (!CollectionUtils.isEmpty(classSet)) {
            for (Class<?> clazz : classSet) {
                String[] ss = clazz.getName().split("\\.");
                if (FCP_METHOD_MAPPING.containsKey(ss[ss.length - 1]) || FCP_CLASS_MAPPING.containsKey(ss[ss.length - 1])) {
                    throw new IllegalStateException("FCPService class's name can not be same，even if they are under different packages");
                }
                Map<String, Method> methodMap = new HashMap<>(16);
                Method[] methods = clazz.getDeclaredMethods();
                if (!ArrayUtils.isEmpty(methods)) {
                    for (Method method : methods) {
                        if (methodMap.containsKey(method.getName())) {
                            throw new IllegalStateException(String.format("class:[%s] has the same method name [%s], even if they are overloaded", clazz.getName(), method.getName()));
                        }
                        methodMap.put(method.getName(), method);
                    }
                }
                FCP_METHOD_MAPPING.put(ss[ss.length - 1], methodMap);
                FCP_CLASS_MAPPING.put(ss[ss.length - 1], clazz);
            }
        }
    }


    public static Class mappingClass(String serviceName) {
        return FCP_CLASS_MAPPING.getOrDefault(serviceName,null);
    }

    public static Method mappingMethod(String serviceName, String methodName) {
        if (!FCP_METHOD_MAPPING.containsKey(serviceName)) {
            return null;
        }
        Map<String, Method> methodMap = FCP_METHOD_MAPPING.get(serviceName);

        if (MapUtils.isEmpty(methodMap) || !methodMap.containsKey(methodName)) {
            return null;
        }
        return methodMap.getOrDefault(methodName, null);
    }

}
