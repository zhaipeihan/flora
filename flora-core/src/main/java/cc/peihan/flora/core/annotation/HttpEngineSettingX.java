package cc.peihan.flora.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpEngineSettingX {
    /**
     * 服务协议，目前支持rest和pansy
     *
     * @return
     */
    ServiceProtocol serviceProtocal();

    /**
     * 监听端口
     * @return
     */
    int port();
}
