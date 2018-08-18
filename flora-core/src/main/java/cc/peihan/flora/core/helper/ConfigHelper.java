package cc.peihan.flora.core.helper;

import cc.peihan.flora.core.annotation.ServiceProtocol;
import lombok.Data;

public final class ConfigHelper {

    private static String basePackageName;

    private static ServiceProtocol serviceProtocol;

    private static int port;


    public static String getBasePackageName() {
        return basePackageName;
    }

    public static ServiceProtocol getServiceProtocol() {
        return serviceProtocol;
    }

    public static int getPort() {
        return port;
    }

    public static void init(ConfigInit configInit) {
        ConfigHelper.basePackageName = configInit.getBasePackageName();
        ConfigHelper.serviceProtocol = configInit.getServiceProtocol();
        ConfigHelper.port = configInit.getPort();
    }

    @Data
    public class ConfigInit {
        private String basePackageName;
        private ServiceProtocol serviceProtocol;
        private int port;
    }
}
