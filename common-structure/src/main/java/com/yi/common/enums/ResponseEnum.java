package com.yi.common.enums;

/**
 * 返回信息的枚举类
 */
public enum ResponseEnum {
    // 成功段0
    SUCCESS(200, "操作成功"),
    // 默认错误处理枚举对象
    ERROR(114514,"未知错误"),
    // 登录段1~50
    NEED_LOGIN(1, "需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2, "密码错误"),

    // 请求参数段4000~4999
    ERROR_PARAM(4001,"请求参数为空"),

    // 秀操作段5000~5999
    OPERATION_FORBIDDEN(5001,"禁止的操作");

    Integer code;
    String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
