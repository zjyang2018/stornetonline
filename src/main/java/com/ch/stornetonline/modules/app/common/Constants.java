package com.ch.stornetonline.modules.app.common;


public class Constants {

    public static final boolean DEBUG_SIGN_CHECK = true;

    public static final int USER_NO = 100;

    public static final int TOKEN_EXPIRE_TIME = 60 * 60 * 24;

    public static final int USER_EXPIRE_TIME = 60 * 60 * 24;

    public static final int VERIFY_CODE_EXPIRE_TIME = 60 * 5;


    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long WEEK = 7 * DAY;

    //public static final String SNC_HEADLESS_ADDRESS ="ce941daaf4b13946dddc7d951881f5f4d01bb9e221b9f8a0469523fa09c8bcbf999eecce039b";//水龙头地址


    public static final String VERIFY_CODE_SMS_CONTENT = "为您的验证码，请于5分钟内填写。如非本人操作，请忽略本短信。";

    // 文件上传服务器绝对路径
    //public static final String NFS_BASIC_FILE_PATH = "/home/zjyang/JavaProject/stornetonline/data";
    // 文件上传服务器绝对路径
    //public static final String NFS_BASIC_FILE_PATH = "/home/ubuntu/stornetcloud/online/data";

    // 文件上传目录
    public static final String UPLOAD_FILE_CATALOG = "/uploadFiles";
    // 文件下载目录
    public static final String DOWNLOAD_FILE_CATALOG = "/downloadFiles";
    // 文件删除目录
    public static final String DELETE_FILE_CATALOG = "/deleteFiles";
    // 文件推送目录
    public static final String PUSH_FILE_CATALOG = "/pushFiles";
    // 文件上传成功访问URL前缀
    public static final String SNTORNET_HTTP = "http://192.168.5.99:10032/stornet/app/file/download";
    // 文件分隔符
    public static final String FILE_SEPARATOR = "/";


//    public static final String  CALLBACK_EVENT_TYPE_LOGIN="1" ;
//    public static final String  CALLBACK_EVENT_TYPE_LOGOUT="2" ;
//    public static final String  CALLBACK_EVENT_TYPE_REG="3" ;
//    


}