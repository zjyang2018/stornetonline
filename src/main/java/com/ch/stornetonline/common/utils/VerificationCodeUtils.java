/**
 * Copyright (C) 2013 北京学信科技有限公司
 *
 * @className:com.xuexin.bizserver.util.VerificationCodeUtils
 * @version:v1.0.0 
 * @author:李大鹏
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2013-7-11       李大鹏                        v1.0.0        create
 *
 */
package com.ch.stornetonline.common.utils;

import java.util.Random;


public class VerificationCodeUtils {

  
    
  public static String genRegCode(){
	  int max=9999;
      int min=1001;
      Random random = new Random();

      int s = random.nextInt(max)%(max-min+1) + min;
	  
      return String.valueOf(s);
		
  }


    
    
}
