package com.ch.stornetonline.config;

import com.ch.stornetonline.modules.app.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private AccessInterceptor accessInterceptor;

    /**
     * 该方法用于注册拦截器
     * 可注册多个拦截器，多个拦截器组成一个拦截器链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 添加路径
        // excludePathPatterns 排除路径
        //registry.addInterceptor(accessInterceptor).addPathPatterns("/**").excludePathPatterns("/app/comm/**").excludePathPatterns("/app/user/login").excludePathPatterns("/app/user/reg").excludePathPatterns("/app/user/mobilecode").excludePathPatterns("/app/user/modpwd").excludePathPatterns("/v1.0/userinfo/querybymobile");

    }
}
