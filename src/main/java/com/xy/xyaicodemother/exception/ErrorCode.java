package com.xy.xyaicodemother.exception;
import lombok.Getter;

/**
 * 错误码枚举类
 * 定义系统中常见的业务错误码和对应的提示信息
 */
@Getter
public enum ErrorCode {
    // 枚举常量定义
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String message;

    // 枚举构造方法（默认私有，显式声明更规范）
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}