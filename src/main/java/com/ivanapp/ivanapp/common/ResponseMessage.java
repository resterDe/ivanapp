package com.ivanapp.ivanapp.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/3/5 21:45
 * 封装一个共享使用的类数据
 * 封装一个返回的响应数据
 * (暂时未接入使用，后期完善再使用，使用后将括号内容删除)
 */
public class ResponseMessage<T> implements Serializable {
    private static final int STATUS_SUCCESS = 0; //成功
    private static final int STATUS_FAIL = 1; //失败

    /**
     * 成功与否代码
     * 成功为0 失败为1
     */
    private int status;
    /**
     * 返回响应信息
     */
    private String msg;
    /**
     * 返回对象、数据信息
     */
    private T data;

    /**
     * 返回成功与否代码
     * @param status 成功为0 失败为1
     */
    private ResponseMessage(int status){
        this.status=status;
    }

    /**
     * 返回数据跟判断代码
     * @param status
     * @param data
     */
    private ResponseMessage(int status, T data){
        this.status=status;
        this.data=data;
    }

    /**
     * 返回判断代码、数据、响应信息
     * @param status
     * @param msg
     * @param data
     */
    private ResponseMessage(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回判断代码、响应信息
     * @param status
     * @param msg
     */
    private ResponseMessage(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore //返回时忽略掉这个属性
    public boolean isSuccess() {
        return this.status == STATUS_SUCCESS;
    }

    public int getStatus() {
        return this.status;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {return this.msg;}


    public static <T> ResponseMessage<T> createBySuccess() {
        return new ResponseMessage<T>(STATUS_SUCCESS);
    }


    public static <T> ResponseMessage<T> createBySuccessMessage(String msg) {
        return new ResponseMessage<T>(STATUS_SUCCESS, msg);
    }

    public static <T> ResponseMessage<T> createBySuccess(T data) {
        return new ResponseMessage<T>(STATUS_SUCCESS, data);
    }

    public static <T> ResponseMessage<T> createBySuccess(String msg, T data) {
        return new ResponseMessage<T>(STATUS_SUCCESS, msg, data);
    }

    public static <T> ResponseMessage<T> createByError() {
        return new ResponseMessage<T>(STATUS_FAIL, "请与管理员联系");
    }

    public static <T> ResponseMessage<T> createByError(T data) {
        return new ResponseMessage<T>(STATUS_FAIL, data);
    }

    public static <T> ResponseMessage<T> createByErrorMessage(String msg) {
        return new ResponseMessage<T>(STATUS_FAIL, msg);
    }

    public static <T> ResponseMessage<T> createByErrorCodeMessage(int errorCode, String msg) {
        return new ResponseMessage<T>(errorCode, msg);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
