package com.ch.stornetonline.sia;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Utils {
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static long convertMBToBytes(long mb){
		return mb * 1000000;
	}
	
	public static long convertGBToBytes(long gb){
		return gb * 1000000000;
	}

	/**
	 * 去除字符串首尾字符
	 *
	 * @param source 源字符串
	 * @return String
	 */
	public static String trimFirstAndLastChar(String source) {
		return source.substring(1,source.length()-1);
	}

	public static String uploadToServer(String url,File file){

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try{
		HttpPost httpPost = new HttpPost(url);

		log.info("post url:"+url);

		//httpPost.setHeader("User-Agent","SOHUWapRebot");

		//httpPost.setHeader("Accept-Language","zh-cn,zh;q=0.5");

		//httpPost.setHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");

		httpPost.setHeader("Connection","keep-alive");

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
		httpPost.setConfig(requestConfig);

		FileBody bin = new FileBody(file);
		StringBody comment = new StringBody("This is comment", ContentType.MULTIPART_FORM_DATA);

		HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("uploadFileName", bin).addPart("comment", comment).build();

		//File file = new File("d:/photo.jpg");

		//mutiEntity.addPart("desc",new StringBody("美丽的西双版纳", Charset.forName("utf-8")));

		//reqEntity.addPart("uploadFileName", new FileBody(file));

		httpPost.setEntity(reqEntity);

		log.info("executing request " + httpPost.getRequestLine());
		CloseableHttpResponse response = httpClient.execute(httpPost);
		try{
			log.info("" + response.getStatusLine());
			HttpEntity resEntity = response.getEntity();

			String responseEntityStr = null;
			if (resEntity != null) {
				responseEntityStr = EntityUtils.toString(resEntity);
				log.info(responseEntityStr);
				log.info("Response content length: " + resEntity.getContentLength());
			}
			EntityUtils.consume(resEntity);
			return responseEntityStr;
		} finally {
			response.close();
		}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
