package com.ch.stornetonline.modules.app.thread;

import com.alibaba.fastjson.JSON;
import com.ch.stornetonline.common.cache.JedisAPI;
import com.ch.stornetonline.common.constants.JedisNameConstants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DealPushThread {

    public void execute(String trId, String createTime, String dealClass, String fromAddr, String toAddr,
                        String totalAmount, String description) {
        new DealPush(trId, createTime, dealClass, fromAddr, toAddr, totalAmount, description).start();
    }

    private class DealPush extends Thread {
        private String trId;
        private String createTime;
        private String dealClass;
        private String fromAddr;
        private String toAddr;
        private String totalAmount;
        private String description;

        public DealPush(String trId, String createTime, String dealClass, String fromAddr, String toAddr,
                        String totalAmount, String description) {
            super();
            this.trId = trId;
            this.createTime = createTime;
            this.dealClass = dealClass;
            this.fromAddr = fromAddr;
            this.toAddr = toAddr;
            this.totalAmount = totalAmount;
            this.description = description;
        }

        @Override
        public void run() {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("trId", trId);
            params.put("createTime", createTime);
            params.put("dealClass", dealClass);
            params.put("fromAddr", fromAddr);
            params.put("toAddr", toAddr);
            params.put("totalAmount", totalAmount);
            params.put("description", description);
            try {
                JedisAPI.lpush(JedisNameConstants.QU_TRA_EDU_SEND_ING, JSON.toJSONString(params));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
