package com.zeal.http.response;

import com.google.gson.Gson;

/**
 * Created by yang_shoulai on 2015/10/24.
 * 框架Api返回数据的格式
 * 例如
 * {
 * "status":1,
 * "message":"",
 * "result":200
 * }
 */
public final class Response {

    /**
     * API调用是否成功的标识
     */
    private int status;

    /**
     * API调用失败时的消息
     */
    private String message;

    /**
     * API调用返回的数据结果
     */
    private Object result;

    private Response(int status, String message, Object result) {
        this.message = message;
        this.result = result;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

    public static final Response UNAUTHENTICATED = new Response(Status.AuthFailed.getCode(), "用户尚未登录", null);

    public static final Response SUCCESS = new Response(Status.Success.getCode(), "成功", null);

    public static final Response ERROR = new Response(Status.Error.getCode(), "错误", null);

    public static final Response FAILED = new Response(Status.Failed.getCode(), "失败", null);

    public static final Response NO_AUTHORITY = new Response(Status.NO_Authority.getCode(), "没有权限进行此操作", null);

    public enum Status {
        /**
         * 成功
         */
        Success(1),

        /**
         * 失败
         */
        Failed(2),

        /**
         * 错误
         */
        Error(3),

        /**
         * 未登录
         */
        AuthFailed(4),

        /**
         * 异常
         */
        Exception(5),

        /**
         * 没有权限
         */
        NO_Authority(6);

        private int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static class Builder {
        private int status;

        private String message;

        private Object result;

        public Builder status(Status status) {
            this.status = status == null ? 0 : status.getCode();
            return this;
        }

        public Builder failed() {
            this.status = Status.Failed.getCode();
            return this;
        }

        public Builder success() {
            this.status = Status.Success.getCode();
            return this;
        }

        public Builder error() {
            this.status = Status.Error.getCode();
            return this;
        }

        public Builder authFailed() {
            this.status = Status.AuthFailed.getCode();
            return this;
        }

        public Builder exception() {
            this.status = Status.Exception.getCode();
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder result(Object result) {
            this.result = result;
            return this;
        }

        public Response build() {
            return new Response(status, message, result);
        }
    }

}
