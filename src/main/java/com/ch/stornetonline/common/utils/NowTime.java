package com.ch.stornetonline.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class NowTime {
	private static final Logger log = LoggerFactory.getLogger(NowTime.class);
	public static Date creatTime() {
		try {
		/*Long sysTime=System.currentTimeMillis();
		Integer hour=8*60*60*1000;
	    return new Date(sysTime+hour);*/
			return new Date();
		}catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			return new Date();
		}
	}

}
