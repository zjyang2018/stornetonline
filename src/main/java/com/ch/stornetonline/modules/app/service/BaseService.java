package com.ch.stornetonline.modules.app.service;

import com.ch.stornetonline.modules.app.entity.SnOlUserInfo;

public interface BaseService {

    SnOlUserInfo getUserByMobile(String mobile);

    SnOlUserInfo getUserById(String userId);

    String getUserNoById(String userId);

}
