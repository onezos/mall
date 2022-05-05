package net.kokwind.mall.exception;

public enum DdMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能小于8位"),
    NAME_EXISTED(10004, "用户名已存在"),
    REGISTER_FAILED(10005, "创建失败,请稍后重试"),
    WRONG_PASSWORD(10006, "密码错误"),
    NEED_LOGIN(10007, "用户未登录"),
    UPDATE_FAILED(10008, "更新失败,请稍后重试"),
    NEED_ADMIN_ROLE(10009, "需要管理员权限"),
    NAME_NOT_NULL(10010, "参数不能为空"),
    CREATE_FAILED(10011, "新增失败"),
    REQUEST_PARAM_ERROR(10012, "请求参数错误"),
    DELETE_FAILED(10013, "删除失败"),
    SYSTEM_ERROR(20000, "系统异常");
    Integer code;
    String message;

    DdMallExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
