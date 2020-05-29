package com.joss.bundaegi.domain;

import com.joss.bundaegi.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestException extends RuntimeException {
    // private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private String detail;
    private HttpStatus status;
    public RestException(int code, String message, String detail, HttpStatus status){
        this.code = code;
        this.message = MessageUtils.getMessage(message);
        this.detail = detail;
        this.status = status;
    }
}
