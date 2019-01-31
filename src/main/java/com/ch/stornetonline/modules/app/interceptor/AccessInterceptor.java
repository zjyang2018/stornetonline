package com.ch.stornetonline.modules.app.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ch.stornetonline.common.bean.ErrorBean;
import com.ch.stornetonline.common.cache.JedisAPI;
import com.ch.stornetonline.common.constants.Constants;
import com.ch.stornetonline.common.constants.ErrorConstants;
import com.ch.stornetonline.common.constants.JedisNameConstants;
import com.ch.stornetonline.common.utils.StringUtils;
import com.ch.stornetonline.modules.app.entity.service.UserToken;
import com.ch.stornetonline.modules.app.http.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    private static final Logger Log = LoggerFactory.getLogger(AccessInterceptor.class);

    /***
     * 检查必要参数 是否有效 需要拦截的路径 就在 InterceptorConfig 的配置文件中配置
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Log.info("Enter AccessInterceptor:" + request.getRequestURI());
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String strData = requestWrapper.getBody();
        Map<String, Object> data = JSON.parseObject(strData);
        String timestamp = null;
        Object o = data.get("timestamp");
        if (o != null) {
            timestamp = o.toString();
        }
        String sign = null;
        o = data.get("sign");
        if (o != null) {
            sign = o.toString();
        }
        String userId = null;
        o = data.get("userId");
        if (o != null) {
            userId = o.toString();
        }
        String token = null;
        o = data.get("token");
        if (o != null) {
            token = o.toString();
        }
        ErrorBean errorBean = null;
        if (StringUtils.isNull(timestamp)) {
            errorBean = new ErrorBean(ErrorConstants.ERRORCODE_TIMESTAMP_NULL,
                    ErrorConstants.ERRORMSG_TIMESTAMP_NULL);
            request.setAttribute("errorBean", errorBean);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.append(JSONObject.toJSONString(errorBean));
            out.close();
            return false;
        }
        if (StringUtils.isNull(sign)) {
            errorBean = new ErrorBean(ErrorConstants.ERRORCODE_SIGN_NULL,
                    ErrorConstants.ERRORMSG_SIGN_NULL);
            request.setAttribute("errorBean", errorBean);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.append(JSONObject.toJSONString(errorBean));
            out.close();
            return false;
        }
        if (StringUtils.isNull(userId)) {
            errorBean = new ErrorBean(ErrorConstants.ERRORCODE_USERID_NULL,
                    ErrorConstants.ERRORMSG_USERID_NULL);
            request.setAttribute("errorBean", errorBean);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.append(JSONObject.toJSONString(errorBean));
            out.close();
            return false;
        }
        if (StringUtils.isNull(token)) {
            errorBean = new ErrorBean(ErrorConstants.ERRORCODE_TOKEN_NULL,
                    ErrorConstants.ERRORMSG_TOKEN_NULL);
            request.setAttribute("errorBean", errorBean);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.append(JSONObject.toJSONString(errorBean));
            out.close();
            return false;
        }
        if (Constants.DEBUG_SIGN_CHECK && (!StringUtils.hash(token + timestamp).equals(sign))) {
            errorBean = new ErrorBean(ErrorConstants.ERRORCODE_SIGN_CHECK,
                    ErrorConstants.ERRORMSG_SIGN_CHECK);
            request.setAttribute("errorBean", errorBean);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.append(JSONObject.toJSONString(errorBean));
            out.close();
            return false;
        }
        String strToken = JedisAPI.get(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId);
        UserToken userToken = JSONObject.parseObject(strToken, UserToken.class);
        if (userToken == null || !token.equals(userToken.getToken())) {
            errorBean = new ErrorBean(ErrorConstants.RRRORCODE_TOKEN_CHECK_ERROR,
                    ErrorConstants.RRRORMSG_TOKEN_CHECK_ERROR);
            request.setAttribute("errorBean", errorBean);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.append(JSONObject.toJSONString(errorBean));
            out.close();
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
