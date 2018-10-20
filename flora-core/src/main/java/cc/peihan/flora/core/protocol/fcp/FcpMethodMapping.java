package cc.peihan.flora.core.protocol.fcp;

import cc.peihan.flora.core.annotation.FCPService;
import cc.peihan.flora.core.helper.ClassHelper;
import cc.peihan.flora.core.util.ReflexUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class FcpMethodMapping {

    private static Map<String, Class<?>> FCP_CLASS_MAPPING;
    private static Map<String, Class<?>> FCP_CLASS_IMPL_MAPPING;
    private static Map<String, Map<String, Method>> FCP_METHOD_MAPPING;

    public static void init() {
        Set<Class<?>> interfaceSet = ClassHelper.getRequireClass(FCPService.class);
        FCP_METHOD_MAPPING = new HashMap<>(16);
        FCP_CLASS_MAPPING = new HashMap<>(16);
        FCP_CLASS_IMPL_MAPPING = new HashMap<>(16);
        Set<Class<?>> allClass = ClassHelper.getAllClass();
        if (!CollectionUtils.isEmpty(interfaceSet)) {
            for (Class<?> inter : interfaceSet) {
                String[] ss = inter.getName().split("\\.");
                String interfaceName = ss[ss.length - 1];
                if (FCP_METHOD_MAPPING.containsKey(interfaceName) || FCP_CLASS_MAPPING.containsKey(interfaceName)) {
                    throw new IllegalStateException("FCPService class's name can not be same，even if they are under different packages");
                }
                Map<String, Method> methodMap = new HashMap<>(16);

                //寻找实现类
                Set<Class> implClassSet = ReflexUtil.findImplementationClass(inter, allClass);
                // 未找到实现类
                if (CollectionUtils.isEmpty(implClassSet)) {
                    throw new IllegalStateException(String.format("can not find interface:%s 's impl class", interfaceName));
                }
                // 多于一个实现类，目前不支持这种情况
                if (interfaceSet.size() > 1) {
                    throw new IllegalStateException(String.format("interface:%s has two or more impl class", interfaceName));
                }

                Iterator<Class> iterator = implClassSet.iterator();
                Class clazz = iterator.next();
                Method[] methods = clazz.getDeclaredMethods();
                if (!ArrayUtils.isEmpty(methods)) {
                    for (Method method : methods) {
                        if (methodMap.containsKey(method.getName())) {
                            throw new IllegalStateException(String.format("class:[%s] has the same method name [%s], even if they are overloaded", clazz.getName(), method.getName()));
                        }
                        methodMap.put(method.getName(), method);
                    }
                }
                FCP_METHOD_MAPPING.put(interfaceName, methodMap);
                FCP_CLASS_MAPPING.put(interfaceName, inter);
                FCP_CLASS_IMPL_MAPPING.put(interfaceName, clazz);
            }
        }
    }


    public static Class mappingClass(String serviceName) {
        return FCP_CLASS_MAPPING.getOrDefault(serviceName, null);
    }

    public static Class mappingImplClass(String serviceName) {
        return FCP_CLASS_IMPL_MAPPING.getOrDefault(serviceName, null);
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
