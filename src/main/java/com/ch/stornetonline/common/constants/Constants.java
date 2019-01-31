package com.ch.stornetonline.common.constants;


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


    public static final String E_HEAD_USER_ID = "6FNYZ5LMA7FKIAuBTeFVMQAdSU7eSLeP3-0";//总账号
    public static final String E_HEAD_WALLET_ADDR = "EY7X4WJCGWSCT6GCO2RQETHNYESDRUI5";//总账号
    public static final String E_USABLE_USER_ID = "6FNYZ5LMA7FKIAuBTeFVMQAdSU7eSLeP3-5";//可用账号
    public static final String E_USABLE_WALLET_ADDR = "QBAQOI2T7V672EJ2XQPCA7W63WJO6DI3";//可用账号
    public static final String E_LEND_USER_ID = "6FNYZ5LMA7FKIAuBTeFVMQAdSU7eSLeP3-6";//放贷账号
    public static final String E_LEND_WALLET_ADDR = "OHXC7ZX64IXSWVHH2IE2JHDWBWPVVYRA";//放贷账号

    public static final String VERIFY_CODE_SMS_CONTENT = "为您的验证码，请于5分钟内填写。如非本人操作，请忽略本短信。";

    public static final String PWD_SECRET_KEY = "";

    //    public static final String[] HEADLESSLIST = {"122.114.23.66:10065", "122.114.23.66:10065", "122.114.23
    // .66:10065",
//            "122.114.23.66:10065"};
    public static final String[] HEADLESSLIST = {"hub.ebaoedu.com:10092", "hub.ebaoedu.com:10092", "hub.ebaoedu.com:10092",
            "hub.ebaoedu.com:10092"};
    public static final String CHAIN_VERSION = "2.0";
    public static final String CHAIN_TRA_ID = "1";
    public static final String SENDTRANSACTION = "sendtransaction";

}