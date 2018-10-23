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

        System.out.println(ApplicationProperties.class.getResource("/"));
        System.out.println(ApplicationProperties.class.getResource(""));
        System.out.println(ApplicationProperties.class.getClassLoader().getResource(""));

        InputStream inputStream = this.getClass().getResourceAsStream(propertiesConfig);

//        FileReader fileReader = null;
//        try {
//            fileReader = new FileReader(propertiesConfig);
//        } catch (FileNotFoundException e) {
//            throw new IllegalStateException("can not find application.properties in the classpath");
//        }
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            this.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("load application.properties error!");
        }
    }
}
