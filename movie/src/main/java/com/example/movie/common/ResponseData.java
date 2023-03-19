package com.example.movie.common;

public class ResponseData<T> {
    private int code;
    private String message;
    private T data;

    public ResponseData(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseData<T> success() {
        return new ResponseData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseData<T> success(T data, String message) {
        return new ResponseData<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseData<T> failed(ErrorCode errorCode) {
        return new ResponseData<>(ResultCode.FAILED.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ResponseData<T> failed(ErrorCode errorCode, String message) {
        return new ResponseData<>(errorCode.getCode(), message, null);
    }

    public static <T> ResponseData<T> failed() {
        return failed(ResultCode.FAILED);
    }

    public static <T> ResponseData<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    public static <T> ResponseData<T> unauthorized(T data) {
        return new ResponseData<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> ResponseData<T> forbidden(T data) {
        return new ResponseData<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}
