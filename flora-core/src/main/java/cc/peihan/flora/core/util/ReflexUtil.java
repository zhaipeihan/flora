package cc.peihan.flora.core.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ReflexUtil {

    /**
     * 按照method中的参数列表重排params
     *
     * @param method
     * @param params
     * @return
     */
    public static Object[] orderMethodParam(Method method, Map<String, Object> params) {

        if (method == null || MapUtils.isEmpty(params)) {
            return null;
        }

        return Arrays.stream(method.getParameters())
                .filter(Objects::nonNull)
                .map(p -> params.getOrDefault(p.getName(), null))
                .toArray();

    }

    /**
     * 查找classes中的clazz的实现类
     *
     * @param clazz
     * @param classes
     * @return
     */
    public static Set<Class> findImplementationClass(Class clazz, Set<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) {
            return null;
        }
        return classes.stream().filter(Objects::nonNull)
                .filter(cla -> isImpl(clazz, cla))
                .collect(Collectors.toSet());
    }

    public static boolean isImpl(Class parent, Class child) {
        return !parent.equals(child) && parent.isAssignableFrom(child);
    }


}
