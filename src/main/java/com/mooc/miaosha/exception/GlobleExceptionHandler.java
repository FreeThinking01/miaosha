package com.mooc.miaosha.exception;

import com.mooc.miaosha.result.CodeMsg;
import com.mooc.miaosha.result.Result;
import org.springframework.http.HttpRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        System.out.println(e);
        if (e instanceof GlobleException){
            //增加处理抛出异常逻辑，即登录的参数与数据库数据对比
            GlobleException ex = (GlobleException)e;
            return Result.error(ex.getCm());
        }else if (e instanceof BindException){
            //对参数格式校验的错误异常处理
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);

            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
