package com.xpf.dubbo.dubbotool.dto;

import lombok.Data;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@Data
public class ResultDTO<T> {

    private static final int DEFAULT_SUCCESS_CODE = 1;
    private static final int DEFAULT_ERROR_CODE = 0;

    private int code;
    private boolean success;
    private String msg;
    private T data;

    private ResultDTO() {

    }

    public static <T> ResultDTO<T> createSuccess(String msg, T data) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ResultDTO<T> createError(String msg, T data) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(DEFAULT_ERROR_CODE);
        result.setSuccess(false);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
