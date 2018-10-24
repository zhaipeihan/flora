package cc.peihan.flora.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseSettingX {

    /**
     * dao类所在包路径
     * @return
     */
    String daoPackageName();
}
