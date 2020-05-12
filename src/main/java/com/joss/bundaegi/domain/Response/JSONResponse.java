package com.joss.bundaegi.domain.Response;
import com.joss.bundaegi.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONResponse<T> {
    int code;
    String msg;
    T data;

    public JSONResponse(int code, String msg, T data) {
        this.code = code;
        this.msg =  MessageUtils.getMessage(msg);
        this.data = data;
    }
}
