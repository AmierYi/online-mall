package com.yi.common.response;

import com.yi.common.enums.ResponseEnum;

import java.io.Serializable;


/**
 * 相应对象
 *
 * @param <T> 相应对象中保存的数据的类型
 */
public class ResponseResult<T> implements Serializable {

    /**
     * 返回值状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult(T data) {
        this.code = ResponseEnum.SUCCESS.getCode();
        this.message = ResponseEnum.SUCCESS.getMessage();
        this.data = data;
    }

    private ResponseResult(String message) {
        this.code = ResponseEnum.ERROR.getCode();
        this.data = null;
        this.message = message;
    }


    public ResponseResult(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    /**
     * 根据返回的状态枚举, 构建返回结果
     *
     * @param responseEnum {@link ResponseEnum} 返回状态枚举
     * @return ResponseResult
     */
    public static <Object> ResponseResult<Object> build(ResponseEnum responseEnum) {
        return new ResponseResult<>(responseEnum);
    }

    /**
     * 根据自定义状态码{@code code}和自定义提示信息{@code msg}构建返回结果
     *
     * @param code 自定义状态码
     * @param msg  自定义提示信息
     * @return ResponseResult
     */
    public static <Object> ResponseResult<Object> build(Integer code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    /**
     * 根据自定义状态码{@code code}, 自定义提示信息{@code msg}以及返回实体{@code T}构建返回结果
     *
     * @param code 自定义状态码
     * @param msg  自定义提示信息
     * @param <T>  返回实体的类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> build(Integer code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

    /**
     * 成功时调用, 没有data内容
     *
     * @return ResponseResult
     */
    public static <String> ResponseResult<String> success() {
        return ResponseResult.build(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功时候的调用
     *
     * @param <T> 返回实体的类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data);
    }

    /**
     * 出错时调用, 自定义提示信息{@code msg}
     *
     * @param msg 自定义提示信息
     * @param <T> 返回实体的类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult<>(msg);
    }

    /**
     * 出错时调用, 根据返回实体{@code T}构建返回结果
     *
     * @param data 返回实体
     * @param <T>  返回实体的类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(T data) {
        return new ResponseResult<>(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getMessage(), data);
    }

}
