package com.wusuowei.lgy.exception;

import com.wusuowei.lgy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e){
        log.error("运行时异常：---------{}"+e.getMessage());
        return R.error(e.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    public R fileHandler(RuntimeException e){
        log.error("文件上传异常：---------{}"+e.getMessage());
        return R.error("文件上传异常");
    }
}
