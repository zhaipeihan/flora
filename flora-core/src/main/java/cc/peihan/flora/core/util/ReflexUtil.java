package cc.peihan.flora.core.util;

import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public final class ReflexUtil {

    public static Object[] orderMethodParam(Method method, Map<String, Object> params) {

        if (method == null || MapUtils.isEmpty(params)) {
            return null;
        }

        return Arrays.stream(method.getParameters())
                .filter(Objects::nonNull)
                .map(p -> params.getOrDefault(p.getName(), null))
                .toArray();

    }

}
