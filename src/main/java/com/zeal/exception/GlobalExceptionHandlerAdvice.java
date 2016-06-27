package com.zeal.exception;

import com.zeal.http.response.Response;
import com.zeal.utils.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yang_shoulai on 2015/10/26.
 */
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response uncaughtException(Exception exception, HttpServletRequest request) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "uncaughtException ：" + exception.getMessage());
        return new Response.Builder()
                .status(Response.Status.Error)
                .message("未知异常")
                .result(null)
                .build();
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response apiException(BizException exception, HttpServletRequest request) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "BizException ：" + exception.getMessage());
        return new Response.Builder()
                .status(Response.Status.Exception)
                .message(exception.getMessage())
                .result(null).build();
    }
}
