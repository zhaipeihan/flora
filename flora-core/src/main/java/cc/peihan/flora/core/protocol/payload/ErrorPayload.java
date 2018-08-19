package cc.peihan.flora.core.protocol.payload;

import lombok.Data;

@Data
public class ErrorPayload {

    private String code;
    private String message;

    public static ErrorPayload make(Exception e) {
        if (e == null) {
            return null;
        }
        ErrorPayload  errorPayload = new ErrorPayload();
        errorPayload.setMessage(e.getMessage());

        return errorPayload;
    }

}
