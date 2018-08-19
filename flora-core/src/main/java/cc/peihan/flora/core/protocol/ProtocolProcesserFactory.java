package cc.peihan.flora.core.protocol;


import cc.peihan.flora.core.helper.ConfigHelper;
import cc.peihan.flora.core.protocol.fcp.FcpProtocolProcesser;

/**
 * 根据配置的协议类型，构建不同的processer
 */
public class ProtocolProcesserFactory {

    public static ProtocolProcesser newProcesser() {
        ProtocolProcesser protocolProcesser = null;
        switch (ConfigHelper.getServiceProtocol()) {
            case FCP:
                protocolProcesser = new FcpProtocolProcesser();
                break;
        }
        return protocolProcesser;
    }


}
