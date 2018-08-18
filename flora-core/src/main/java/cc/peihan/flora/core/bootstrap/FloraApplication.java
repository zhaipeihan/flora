package cc.peihan.flora.core.bootstrap;


import cc.peihan.flora.core.annotation.FloraBootApplication;
import cc.peihan.flora.core.annotation.HttpEngineSettingX;
import cc.peihan.flora.core.helper.ConfigHelper;
import cc.peihan.flora.core.http.HttpServer;
import org.apache.commons.lang3.StringUtils;

public final class FloraApplication {

    public static void run(Class<?> clazz) {
        FloraApplication.preCheck(clazz);
        FloraBootApplication floraBootApplication = clazz.getAnnotation(FloraBootApplication.class);
        HttpEngineSettingX httpEngineSettingX = clazz.getAnnotation(HttpEngineSettingX.class);
        initConfig(floraBootApplication, httpEngineSettingX);
        start();
    }

    private static void start() {
        HttpServer httpServer = new HttpServer(ConfigHelper.getPort());
        httpServer.start();
    }

    private static void preCheck(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz can not be null");
        }
        if (!clazz.isAnnotationPresent(FloraBootApplication.class)) {
            throw new IllegalStateException(clazz.getName() + "lack @FloraBootApplication");
        }
        FloraBootApplication floraBootApplication = clazz.getAnnotation(FloraBootApplication.class);
        if (floraBootApplication == null) {
            throw new IllegalStateException(clazz.getName() + "@FloraBootApplication can not be null");
        }

        if (StringUtils.isEmpty(floraBootApplication.basePackage())) {
            throw new IllegalStateException(clazz.getName() + "@FloraBootApplication basePackage can not be empty");
        }

        if (!clazz.isAnnotationPresent(HttpEngineSettingX.class)) {
            throw new IllegalStateException(clazz.getName() + "lack @HttpEngineSettingX");
        }

        HttpEngineSettingX httpEngineSettingX = clazz.getAnnotation(HttpEngineSettingX.class);

        if (httpEngineSettingX.serviceProtocal() == null) {
            throw new IllegalStateException(clazz.getName() + "@FloraBootApplication serviceProtocal can not be null");
        }

        if (httpEngineSettingX.port() == 0) {
            throw new IllegalStateException(clazz.getName() + "@FloraBootApplication port can not be 0");
        }
    }

    private static void initConfig(FloraBootApplication floraBootApplication, HttpEngineSettingX httpEngineSettingX) {
        ConfigHelper.ConfigInit configInit = new ConfigHelper().new ConfigInit();
        configInit.setBasePackageName(floraBootApplication.basePackage());
        configInit.setServiceProtocol(httpEngineSettingX.serviceProtocal());
        configInit.setPort(httpEngineSettingX.port());
        ConfigHelper.init(configInit);
    }

}
