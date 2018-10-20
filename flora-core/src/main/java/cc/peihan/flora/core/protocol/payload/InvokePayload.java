package cc.peihan.flora.core.protocol.payload;


import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * 连接request和response的结构体
 */
@Data
public class InvokePayload {


    /**
     * 待执行的方法所属的类
     */
    private Class clazz;

    /**
     * 待执行的方法
     */
    private Method method;


    /**
     * 参数
     * name:value
     */
    private Map<String,Object> parameters;


    /**
     * 异常
     */
    private Exception exception;


    /**
     * 执行结果
     */
    private Object result;
}
