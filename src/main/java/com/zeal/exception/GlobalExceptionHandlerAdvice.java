package com.zeal.exception;

import com.zeal.http.response.Response;
import com.zeal.utils.LogUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yang_shoulai on 2015/10/26.
 */
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response uncaughtException(Exception exception, HttpServletResponse httpServletResponset) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "uncaughtException ：" + exception.getMessage());
        httpServletResponset.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new Response.Builder()
                .status(Response.Status.Error)
                .message("服务器异常")
                .result(null)
                .build();
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response nullPointerException(Exception exception, HttpServletResponse httpServletResponset) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "nullPointerException ：" + exception.getMessage());
        httpServletResponset.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new Response.Builder()
                .status(Response.Status.Error)
                .message("空指针异常")
                .result(null)
                .build();
    }

    @ExceptionHandler(value = DataAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response dataAccessException(Exception exception, HttpServletResponse httpServletResponset) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "dataAccessException ：" + exception.getMessage());
        httpServletResponset.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new Response.Builder()
                .status(Response.Status.Error)
                .message("数据库异常")
                .result(null)
                .build();
    }


    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response bizException(BizException exception, HttpServletResponse httpServletResponset) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "BizException ：" + exception.getMessage());
        httpServletResponset.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new Response.Builder()
                .status(Response.Status.Exception)
                .message(exception.getMessage())
                .result(null).build();
    }


    @ExceptionHandler(value = NoAuthorityException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response noAuthorityException (NoAuthorityException exception, HttpServletResponse httpServletResponset) {
        LogUtils.error(GlobalExceptionHandlerAdvice.class, "NoAuthorityException ：" + exception.getMessage());
        httpServletResponset.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return Response.NO_AUTHORITY;
    }
}
