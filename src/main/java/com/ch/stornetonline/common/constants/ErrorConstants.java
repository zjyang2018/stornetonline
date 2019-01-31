package com.ch.stornetonline.common.constants;


public class ErrorConstants {

    public static final String ERRORCODE_OK = "000000";
    public static final String ERRORMSG_OK = "成功";

    public static final String ERRORCODE_SYSTEM_ERROR = "000001";
    public static final String ERRORMSG_SYSTEM_ERROR = "系统内部错误";

    public static final String ERRORCODE_CATCH = "-1";
    public static final String ERRORMSG_GEN = "错误!错误码为:";

    public static final String ERRORCODE_USERID_NULL = "100000";
    public static final String ERRORMSG_USERID_NULL = "用户ID不能为空";

    public static final String ERRORCODE_PWD_NULL = "100001";
    public static final String ERRORMSG_PWD_NULL = "用户密码不能为空";

    public static final String ERRORCODE_PWD_OLD_NULL = "100002";
    public static final String ERRORMSG_PWD_OLD_NULL = "原密码不能为空";

    public static final String ERRORCODE_MOBILE_NULL = "100003";
    public static final String ERRORMSG_MOBILE_NULL = "手机号不能为空";

    public static final String ERRORCODE_TIMESTAMP_NULL = "100004";
    public static final String ERRORMSG_TIMESTAMP_NULL = "时间戳不能为空";

    public static final String ERRORCODE_SIGN_NULL = "100005";
    public static final String ERRORMSG_SIGN_NULL = "签名体不能为空";

    public static final String ERRORCODE_SIGN_CHECK = "100006";
    public static final String ERRORMSG_SIGN_CHECK = "签名体校验不对";

    public static final String ERRORCODE_ADDR_NULL = "100007";
    public static final String ERRORMSG_ADDR_NULL = "地址不能不空";

    public static final String ERRORCODE_REALNAME_NULL = "100008";
    public static final String ERRORMSG_REALNAME_NULL = "用户名不能为空";

    public static final String ERRORCODE_VERIFY_CODE_NULL = "100009";
    public static final String ERRORMSG_VERIFY_CODE_NULL = "验证码不能为空";

    public static final String ERRORCODE_APPNAME_CODE_NULL = "100010";
    public static final String ERRORMSG_APPNAME_CODE_NULL = "系统名不能为空";

    public static final String ERRORCODE_WXOPENID_NULL = "100011";
    public static final String ERRORMSG_WXOPENID_NULL = "微信OPENID不能为空";

    public static final String ERRORCODE_WXCODE_NULL = "100012";
    public static final String ERRORMSG_WXCODE_NULL = "微信CODE不能为空";


    public static final String ERRORCODE_PARAM_NULL = "100020";
    public static final String ERRORMSG_PARAM_NULL = "参数不能为空";

    public static final String ERRORCODE_PARAM_ERROR = "100021";
    public static final String ERRORMSG_PARAM_ERROR = "参数格式错误";

    public static final String ERRORCODE_SENDID_NULL = "100022";
    public static final String ERRORMSG_SENDID_NULL = "发送者ID不能为空";

    public static final String ERRORCODE_RECVID_NULL = "100023";
    public static final String ERRORMSG_RECVID_NULL = "接收者ID不能为空";

    public static final String ERRORCODE_DATA_NULL = "100024";
    public static final String ERRORMSG_DATA_NULL = "发送内容不能为空";

    public static final String ERRORCODE_PAGE_NULL = "100025";
    public static final String ERRORMSG_PAGE_NULL = "Page不能为空";

    public static final String ERRORCODE_PAGESIZE_NULL = "100026";
    public static final String ERRORMSG_PAGESIZE_NULL = "PageSize不能为空";

    public static final String ERRORCODE_ROOMID_NULL = "100027";
    public static final String ERRORMSG_ROOMID_NULL = "群ID不能为空";

    public static final String ERRORCODE_ACT_NULL = "100028";
    public static final String ERRORMSG_ACT_NULL = "ACT不能为空";

    public static final String ERRORCODE_COMM_NULL = "100029";
    public static final String ERRORMSG_COMM_NULL = "COMM不能为空";

    public static final String ERRORCODE_PROID_NULL = "100030";
    public static final String ERRORMSG_PROID_NULL = "ProId不能为空";

    public static final String ERRORCODE_TITLE_NULL = "100031";
    public static final String ERRORMSG_TITLE_NULL = "标题不能为空";

    public static final String ERRORCODE_MERCHANT_NOEXIST = "100200";
    public static final String ERRORMSG_MERCHANT_NOEXIST = "商户不存在";

    public static final String ERRORCODE_MERCHANT_APPLY_ERROR = "100201";
    public static final String ERRORMSG_MERCHANT_APPLY_ERROR = "已通过或正在审核";


    public static final String ERRORCODE_APPID_NULL = "200000";
    public static final String ERRORMSG_APPID_NULL = "应用号不能为空";

    public static final String ERRORCODE_FILENAME_NULL = "200001";
    public static final String ERRORMSG_FILENAME_NULL = "文件名不能为空";

    public static final String ERRORCODE_APPID_UNLAWFUL = "200002";
    public static final String ERRORMSG_APPID_UNLAWFUL = "应用号非法";


    public static final String RRRORCODE_RE_LOGIN = "800000";
    public static final String RRRORMSG_RE_LOGIN = "请重新登录";

    public static final String ERRORCODE_TOKEN_NULL = "800001";
    public static final String ERRORMSG_TOKEN_NULL = "令牌不能为空";

    public static final String ERRORCODE_NO_PERMISSION = "800002";
    public static final String ERRORMSG_NO_PERMISSION = "您没有权限";

    public static final String ERRORCODE_USER_NOEXIST = "800003";
    public static final String ERRORMSG_USER_NOEXIST = "用户不存在";

    public static final String ERRORCODE_PWD_ERROR = "800005";
    public static final String ERRORMSG_PWD_ERROR = "密码错误";

    public static final String ERRORCODE_REREG_ERROR = "800006";
    public static final String ERRORMSG_REREG_ERROR = "手机号已注册,请直接登录";

    public static final String ERRORCODE_PWD_OLD_ERROR = "800007";
    public static final String ERRORMSG_PWD_OLD_ERROR = "原密码错误";

    public static final String ERRORCODE_VERIFYCODE_ERROR = "800008";
    public static final String ERRORMSG_VERIFYCODE_ERROR = "验证码不对，请重新取验证码";

    public static final String RRRORCODE_APPID_USER_NOLOGIN = "800010";
    public static final String RRRORMSG_APPID_USER_NOLOGIN = "用户暂未登录";

    public static final String RRRORCODE_TOKEN_CHECK_ERROR = "800011";
    public static final String RRRORMSG_TOKEN_CHECK_ERROR = "用户暂未登录";

    public static final String RRRORCODE_RECHARGE_ERROR= "90000";
    public static final String RRRORMSG_RECHARGE_ERROR = "充值失败";

    public static final String RRRORCODE_PAY_ERROR= "90001";
    public static final String RRRORMSG_PAY_ERROR = "钱包余额不足";

    public static final String ERRORCODE_FILE_NOEXIST = "90002";
    public static final String ERRORMSG_FILE_NOEXIST = "文件不存在";

    public static final String ERRORCODE_SPACE_NOT_ENOUGH = "90003";
    public static final String ERRORMSG_SPACE_NOT_ENOUGH = "空间不足";

    public static final String ERRORCODE_NOT_FILE_RECORD = "90004";
    public static final String ERRORMSG_NOT_FILE_RECORD = "无文件记录表";

    public static final String ERRORCODE_FILE_DOWNLOAD_FAIL = "90005";
    public static final String ERRORMSG_FILE_DOWNLOAD_FAIL = "文件下载失败";

    public static final String ERRORCODE_SIA_SYSTEM_ERROR = "90006";
    public static final String ERRORMSG_SIA_SYSTEM_ERROR = "Sia系统内部错误";

    public static final String ERRORCODE_NOT_BUY_SPACE = "90007";
    public static final String ERRORMSG_NOT_BUY_SPACE = "该用户尚未购买空间";

    public static final String ERRORCODE_SYSTEM_RUNTIME_ERROR = "90008";
    public static final String ERRORMSG_SYSTEM_RUNTIME_ERROR = "系统异常";

    public static final String ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY = "90009";
    public static final String ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY = "系统异常,请稍后重试";


}