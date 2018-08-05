package com.ace.cms.vo;

import java.io.Serializable;

import com.google.common.base.Objects;


/**
 * 调用返回
 * Created by:sanhu Date:16/7/26 Time:下午7:00
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -3249679162904643876L;

    private T body;  //获取调用返回值

    private String code; //获取错误码

    private String message;


    public Response(T body) {
        this.code = "200";
        this.message="成功!";
        this.body = body;
    }

    /**
     * 构造方法，根据flag返回不同结果
     *
     * @param flag   true|false
     * @param body 若flag=true，则返回body对象,若flag=false则返回errorCode
     */
    public Response(boolean flag, T body) {
        if (flag) {
            this.code = "200";
            this.message="成功!";
            this.body = body;
        } else {
            this.code = (String) body;
        }
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getbody() {
        return body;
    }

    public void setbody(T body) {
        code = "200";
        this.message="成功!";
        this.body = body;
    }

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
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("body", this.body).add("code", this.code).add("message", this.message).omitNullValues().toString();
    }
}
