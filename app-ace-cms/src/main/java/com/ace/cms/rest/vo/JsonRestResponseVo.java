package com.ace.cms.rest.vo;

import java.io.Serializable;

public class JsonRestResponseVo implements Serializable {

    /**
     */
    private static final long serialVersionUID = -7400604952066943313L;
    private String code; //获取错误码

    private String message;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
