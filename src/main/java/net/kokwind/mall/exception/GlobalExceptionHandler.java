package net.kokwind.mall.exception;

import ch.qos.logback.classic.Logger;
import net.kokwind.mall.common.ApiRestResponse;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //处理Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        logger.error("Default Exception", e);
        return ApiRestResponse.error(DdMallExceptionEnum.SYSTEM_ERROR);
    }
    //处理DdMallException异常
    @ExceptionHandler(DdMallException.class)
    @ResponseBody
    public Object handleDdMallException(DdMallException e) {
        logger.error("DdMallException Exception", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}
