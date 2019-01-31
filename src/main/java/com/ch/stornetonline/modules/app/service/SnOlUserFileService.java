package com.ch.stornetonline.modules.app.service;

import com.ch.stornetonline.common.bean.ResultBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-08 23:21:15
 */
public interface SnOlUserFileService {

    ResultBean renterUploadFile(String userId, MultipartFile file);
    
    ResultBean getFileInfo(Map<String, Object> map);

    ResultBean markFileCollect(Map<String, Object> map);

    ResultBean cancelFileCollect(Map<String, Object> map);

    ResultBean renterDownloadFile(String userId,String fileId, HttpServletResponse response);

    ResultBean getFileList(String userId,String pageNum,String pageSize,String type,String param,String sort);

    ResultBean renterRenameFile(Map<String, Object> map, String fileName);

    ResultBean renterDeleteFile(String userId, String[] arr);

    ResultBean renterRestoreFile(String userId, String[] arr);

    ResultBean renterPushFile(String userId, String[] arr);

    ResultBean renterRealDeleteFile(String userId, String[] arr);
}

