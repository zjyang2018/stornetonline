package com.ch.stornetonline.modules.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    public static String getParam(String paramName, Map<String, Object> params) {
        String param = null;
        Object o = params.get(paramName);
        if (o != null) {
            param = o.toString();
        }
        return param;
    }

    public static String getParams(String paramName, Map<String, Object> params) {
        String param = null;
        Object o = params.get(paramName);
        if (o != null) {
            param = o.toString();
        }
        return param;
    }

}
