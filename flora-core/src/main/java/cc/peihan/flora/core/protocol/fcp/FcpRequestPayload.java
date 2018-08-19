package cc.peihan.flora.core.protocol.fcp;


import lombok.Data;

import java.util.Map;

/**
 * Fcp请求体
 */
@Data
public class FcpRequestPayload {

    /**
     * 方法
     */
    private String method;


    /**
     * 服务
     */
    private String service;


    /**
     * 参数
     */
    private Map<String, Object> params;

}
