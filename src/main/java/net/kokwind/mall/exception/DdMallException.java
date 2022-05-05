package net.kokwind.mall.exception;

/**
 * 描述： 统一异常
 */
public class DdMallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public DdMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public DdMallException(DdMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
