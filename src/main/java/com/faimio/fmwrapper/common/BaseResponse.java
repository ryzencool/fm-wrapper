package com.faimio.fmwrapper.common;
import lombok.Getter;

/**
 * controller层返回封装
 *
 * @author lucky
 * @version 1.0
 */
public class BaseResponse<T> {

    /**
     * 错误码
     */
    @Getter
    private String code;

    /**
     * 错误信息
     */
    @Getter
    private String msg;

    /**
     * 数据
     */
    @Getter
    private T data;

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse() {
    }

    /**
     * 成功返回，无数据
     *
     * @param <T> 参数泛型
     * @return 结果
     */
    public static <T> BaseResponse<T> success() {
        return response("000000","ok" ,null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return response("000000","ok" ,data);
    }

    /**
     * 成功返回
     *
     * @param <T> 参数泛型，参数
     * @return 结果
     */


    /**
     * 成功返回，无数据
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return 结果
     */
    public static BaseResponse<Void> fail(String code, String msg) {
        return new BaseResponse<>(code, msg, null);
    }


    /**
     * 常规返回
     */
    public static <T> BaseResponse<T> response(String code, String message, T data) {
        return new BaseResponse<>(code, message, data);
    }


}
