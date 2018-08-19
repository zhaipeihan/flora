package cc.peihan.flora.core.protocol.fcp;


import cc.peihan.flora.core.protocol.payload.ErrorPayload;
import lombok.Data;

@Data
public class FcpResponsePayload {

    /**
     * 结果
     */
    private Object result;

    /**
     * 错误
     */
    private ErrorPayload error;

}
