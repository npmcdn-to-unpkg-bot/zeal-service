package com.zeal.exception;

import com.zeal.utils.StringUtils;

/**
 * Created by yang_shoulai on 2016/6/27.
 */
public class BizException extends RuntimeException {

    private int code;

    public BizException() {
        this(-1);
    }

    public BizException(int code) {
        this(code, null);
    }

    public BizException(String message) {
        this(-1, message);
    }

    public BizException(int code, String message) {
        this(code, message, null);
    }

    public BizException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
