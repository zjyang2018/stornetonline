package com.ch.stornetonline.common.utils.fileutils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
     * @author liaijun
     * 创建时间：2017年2月17日 下午5:38:40  
     * 描述：
     * $LastChangedBy$
     * 修改时间：2017年2月17日 下午5:38:40
 */
public class R extends HashMap<String, Object> {
	 public static Integer SUCCESS=200;
	 public static Integer ERROR=1000;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8029795159511332145L;

	public R() {
		put("code", 0);
	}
	
	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public static R success(String msg) {
		R r = new R();
		r.put("code", SUCCESS);
		r.put("msg", msg);
		return r;
	}
	
	public static R fail(String msg) {
		return error(ERROR, msg);
	}
}
