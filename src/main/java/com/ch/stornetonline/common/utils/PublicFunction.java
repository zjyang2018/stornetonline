package com.ch.stornetonline.common.utils;

import com.ch.stornetonline.common.constants.Constants;

public class PublicFunction {

    public static String getRandomHeadlessUrl() {
        int rdm = (int) (1 + Math.random() * 4);
        return "http://" + Constants.HEADLESSLIST[rdm - 1];
    }

}
