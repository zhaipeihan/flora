package cc.peihan.flora.core.config;

import java.io.*;
import java.util.Properties;


/**
 * 应用的配置文件
 */
public class ApplicationProperties extends Properties {

    public ApplicationProperties() {
        String propertiesConfig = "/application.properties";
        this.init(propertiesConfig);
    }

    private void init(String propertiesConfig) {

        InputStream inputStream = this.getClass().getResourceAsStream(propertiesConfig);

        try {
            this.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("load application.properties error!");
        }
    }
}
