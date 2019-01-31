package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserBuy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnOlUserBuyMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnOlUserBuy record);

    int insertSelective(SnOlUserBuy record);

    SnOlUserBuy selectByPrimaryKey(String id);

    SnOlUserBuy selectByUserId(String userId);

    int updateByPrimaryKeySelective(SnOlUserBuy record);

    int updateByPrimaryKey(SnOlUserBuy record);
}