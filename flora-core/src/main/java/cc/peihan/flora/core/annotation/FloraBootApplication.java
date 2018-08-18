package cc.peihan.flora.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FloraBootApplication {

    /**
     * 基础包名
     * 应用启动时会加载该包下的所有类
     * @return
     */
    String basePackage();
}
