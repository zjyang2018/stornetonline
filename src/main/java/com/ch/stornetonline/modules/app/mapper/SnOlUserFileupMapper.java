package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserFileup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SnOlUserFileupMapper {
    int deleteByPrimaryKey(String fileid);

    int insert(SnOlUserFileup record);

    int insertSelective(SnOlUserFileup record);

    SnOlUserFileup selectByPrimaryKey(String fileid);

    int updateByPrimaryKeySelective(SnOlUserFileup record);

    int updateByPrimaryKey(SnOlUserFileup record);

    SnOlUserFileup selectByUserIdAndFileId(Map map);

    List<SnOlUserFileup> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    List<SnOlUserFileup> queryAllList(Map<String, Object> map);

    int queryAllTotal(Map<String, Object> map);

    List<SnOlUserFileup> getFileInfoByName(Map<String, Object> map);

    List<SnOlUserFileup> getAllFileInfoByName(Map<String, Object> map);

    List<SnOlUserFileup> getFileInfoByStatus(Map<String, Object> map);

    List<SnOlUserFileup> selectBatchByUserIdAndFileId(@Param("userId") String userId, @Param("fileIds") String[] arr);
}