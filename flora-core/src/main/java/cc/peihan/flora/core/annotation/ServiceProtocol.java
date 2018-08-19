package cc.peihan.flora.core.annotation;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ServiceProtocol {
    FCP(0, "Flora-Control-Protocol"),
    REST(1, "RESTful-Protocol");

    @Getter
    private int index;
    @Getter
    private String protocolName;
}
