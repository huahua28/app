package com.ace.cms.exceptions;

import com.ace.cms.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebControllerException extends RuntimeException {

    private static final long serialVersionUID = -6507251648970080038L;

    private String code;
    
    public WebControllerException() {
        
    }

    public WebControllerException(String code) {
        this.code = code;
    }

    public WebControllerException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public WebControllerException(String code, String message) {
        super(message);
        this.code = code;
    }

    public WebControllerException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public WebControllerException(ErrorCode e) {
        super(e.getDesc());
        this.code = e.getCode();
    }

    @Override
    public String toString() {
        return "ServiceException{" + "code='" + code + '\'' + "message='" + getMessage() + '\'' + '}';
    }
}