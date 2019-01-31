package com.ch.stornetonline.common.constants;

public class JedisNameConstants {

    public static final String VAL_BIZAPP_USER_REG_VERIFYCODE = "bizapp:val:user:reg:verifycode:";

    public static final String VAL_BIZAPP_USER_PWD_VERIFYCODE = "bizapp:val:user:pwd:verifycode:";

    public static final String VAL_BIZAPP_USER_PHONE = "bizapp:val:user:phone:";

    public static final String VAL_BIZAPP_USER_TOKEN = "bizapp:val:user:token:";

    public static final String VAL_BIZAPP_EMAO_INFO = "bizapp:val:emao:info";

    public static final String VAL_BIZAPP_MERCHANT_INFO = "bizapp:val:merchant:info:";

    public static final String QU_TRA_EDU_SEND_ING = "qu:tra:edu:send:ing"; //推送区块链的交易队列

    public static final String REDIS_SEND_ING = "qu:tra:edu:send:ing"; //要发送到区块链中的交易
    public static final String REDIS_SEND_ERR = "qu:tra:edu:send:err"; //发送错的交易
    //public static final String REDIS_SEND_OK="qu:tra:edu:send:ok" ;  // 发送OK的交易，用于查询确认状态


}