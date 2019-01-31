package com.ch.stornetonline.modules.app.config;

import com.ch.stornetonline.sia.SiaObject;
import com.ch.stornetonline.sia.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;


@Configuration
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
public class CommonConfig {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    private static String sncHeadlessAddress;

    private static String baseFilePath;

    private static String address;

    private static String url;

    private static String uploadURL;

    public static String getUploadURL() {
        return uploadURL;
    }

    @Value("${stornet.uploadurl}")
    public static void setUploadURL(String uploadURL) {
        CommonConfig.uploadURL = uploadURL;
    }

    public static String getUrl() {
        return url;
    }

    @Value("${stornet.url}")
    public void setUrl(String url) {
        this.url = url;
    }

    public static String getAddress() {
        return address;
    }

    @Value("${stornet.address}")
    public void setAddress(String address) {
        this.address = address;
    }

    public static String getSncHeadlessAddress() {
        return sncHeadlessAddress;
    }

    @Value("${stornet.sncHeadlessAddress}")
    public void setSncHeadlessAddress(String sncHeadlessAddress) {
        this.sncHeadlessAddress = sncHeadlessAddress;
    }

    @Value("${stornet.basicFilePath}")
    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public static String getBaseFilePath() {
        return baseFilePath;
    }


    /**
     * 获取钱包地址
     *
     * @return String address
     */
    public static String getWalletAddress() {
        SiaObject so = getSiaObject();
        try {
            JSONArray siaAddress = so.walletAddress();
            return siaAddress.getJSONObject(0).get("address").toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("getWalletAddress" + ": 从StorNet网络获取地址失败");
            return null;
        }
    }

    /**
     * StorNet网络调用结果
     *
     * @param jsonArray
     * @return boolean
     */
    public static Boolean callResult(JSONArray jsonArray) {
        JSONObject item = jsonArray.getJSONObject(0);
        String value = item.getString("message");//得到值
        String okCode = "No Content";
        return !okCode.equals(value);
    }

    /**
     * 获取SiaObject对象
     *
     * @return SiaObject so
     */
    public static SiaObject getSiaObject() {
        return new SiaObject(address);
    }

    /**
     * 文件上传到StorNet网络结果
     *
     * @param so
     * @param sia_path
     * @return
     */
    public static Boolean getUploadResult(SiaObject so, String sia_path) {
        try {
            Double uploadprogress = 0.0;
            int compareResult = -1;
            while (compareResult != 0) {
                JSONArray result = so.renterFiles();
                JSONObject items = result.getJSONObject(0);
                JSONArray files = items.getJSONArray("files");//得到值
                for (int i = 0; i < files.length(); i++) {
                    JSONObject item = files.getJSONObject(i);
                    String siapath = (String) item.get("siapath");
                    if (sia_path.equals(siapath)) {
                        Object up = item.get("uploadprogress");//得到值
                        uploadprogress = Double.parseDouble(up.toString());//得到值
                        compareResult = uploadprogress.compareTo(100.0);
                        //log.info(uploadprogress.toString());
                        break;
                    }
                }
                //log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("renterFiles" + ": 从StorNet网络获取上传进度失败");
            return false;
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return false;
        }
        return true;
    }
}
