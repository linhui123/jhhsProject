package com.jhhscm.platform.http;

/**
 * 类说明：服务器返回状态码说明
 * 作者：huangqiuxin on 2016/5/12 09:42
 * 邮箱：648859026@qq.com
 */
public enum ServerErrorCode {
    SYS_PHP_FATAL_ERROR(500,"致命错误！"),
    SYS_PHP_EXCEPTION(501, "错误！"),
    SYS_PHP_ERROR(502, "异常！"),
    SYS_ERROR(404,"服务器异常！"),
    SYS_REQUEST_PACKET_HEADER_INVALID(503, "无效的请求包头！"),
    SYS_REQUEST_PACKET_BODY_INVALID(504, "无效的请求包体！"),
    SYS_REQUEST_STRUCT_NOT_EXIST(505, "用于请求的结构体不存在！"),
    SYS_RESPONSE_STRUCT_NOT_EXIST(506, "用于返回的结构体不存在！"),
    SYS_INTERFACE_NOT_EXIST(507, "请求的接口不存在！"),
    SYS_CONTROLLER_NOT_EXIST(508, "请求的控制器不存在！"),
    SYS_UNKNOWN_BIZ_ERR(509, "出现了一个业务逻辑中的错误并且该错误不存在错误列表中！"),
    SYS_PARAM_NOT_ENOUGH(510, "参数不足"),
    USER_REGISTER_CLOSED(1001, "注册已关闭"),
    USER_REGISTER_PASSWORD_DISTINCT(1002, "密码和重复密码不一致！"),
    USER_REGISTER_USERNAME_WRONG_LENGTH(1003, "token过期或失效！"),
    USER_REGISTER_USERNAME_WRONG_FORMAT(1004, "用户名/手机号码格式不正确！"),
    USER_REGISTER_USERNAME_FORBIDDEN(1005, "用户名/手机号码被禁止注册！"),
    USER_REGISTER_USERNAME_USED(1006, "用户名/手机号码被占用！"),
    USER_REGISTER_PWD_WRONG_LENGTH(1007, "密码长度必须在4-30个字符之间！"),
    USER_REGISTER_EMAIL_WRONG_FORMAT(1008, "邮箱格式不正确！"),
    USER_REGISTER_EMAIL_WRONG_LENGT(1009, "邮箱长度必须在1-32个字符之间！"),
    USER_REGISTER_EMAIL_FORBIDDEN(1010, "邮箱被禁止注册！"),
    USER_REGISTER_EMAIL_USED(1011, "邮箱被占用！"),
    USER_REGISTER_MOBILE_WRONG_FORMAT(1012, "手机格式不正确！"),
    USER_REGISTER_MOBILE_FORBIDDEN(1013, "手机被禁止注册！"),
    USER_REGISTER_MOBILE_OCCUPY(1014, "手机被占用"),
    USER_LOGIN_USERNAME_FORBIDDEN(1015, "用户不存在或被禁用！"),
    USER_LOGIN_WRONG_PWD(1016, "密码错误！"),
    USER_LOGIN_UNKNOWN_ERR(1017, "未知错误！"),
    USER_NOT_LOGGED_IN(1018, "用户未登录！"),
    USER_LOGOUT_FAILED(999, "用户未登录！"),
    USER_REGISTER_NICKNAME_WRONG_LENGTH(1020, "昵称长度必须在1-8个字符之间！"),
    USER_REGISTER_SEX_INVALID(1021, "无效的性别值！"),
    USER_REGISTER_AGE_GROUP_INVALID(1022, "无效的年龄段值！"),
    USER_UPDATE_NICKNAME_LIMIT_REACHED(1023, "超出允许设置昵称的最大次数！"),
    USER_NEW_PWD_INVALID(1024, "设置的用户密码无效！"),
    USER_FEEDBACK_CONTENT_EMPTY(1025, "反馈的内容不能为空！"),
    USER_VERIVICATION_CODE_FORBIDDEN(1026, "验证码无效！"),
    USER_SPACE_IMAGE_FORBIDDEN(1027, "用户空间图片不存在！"),
    USER_SPACE_IMAGE_FULL(1028, "用户空间图片已满！"),
    PICTURE_TOKEN_FORBIDDEN(1101, "图片Token不存在！"),
    PICTURE_INDEX_FORBIDDEN(1102, "图片索引不存在！"),
    ;
    private int code;
    private String msg;
    ServerErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
