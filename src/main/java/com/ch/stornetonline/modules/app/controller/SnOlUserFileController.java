package com.ch.stornetonline.modules.app.controller;

import java.util.*;

import com.ch.stornetonline.common.bean.ResultBean;
import com.ch.stornetonline.modules.app.service.SnOlUserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-08 23:21:15
 */
@RestController
@RequestMapping("/app/file")
public class SnOlUserFileController extends AbstractController {
    @Autowired
    private SnOlUserFileService snOlUserFileupService;

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public ResultBean renterUploadFile(HttpServletRequest request) {
        // 取得用户ID
        String userId = request.getParameter("userId");
        // 取得上传的文件
        MultipartResolver resolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("uploadFileName");
        return snOlUserFileupService.renterUploadFile(userId, file);
    }

    /**
     * 文件下载
     */
    @RequestMapping("/download")
    public ResultBean renterDownloadFile(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        String userId = getParam("userId", params);
        String fileId = getParam("fileId", params);
        return snOlUserFileupService.renterDownloadFile(userId, fileId, response);
    }

    /**
     * 分页列表
     *
     * type 0：删除文件列表 1：全部文件列表 2：收藏文件列表
     * name 模糊搜索
     * sort 按字段排序
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public ResultBean fileList(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String pageNum = getParam("pageNum", params);
        String pageSize = getParam("pageSize", params);
        String type = getParam("type", params);
        String name = getParam("name", params);
        String sort = getParam("sort", params);
        String param = name + "%";
        return snOlUserFileupService.getFileList(userId, pageNum, pageSize,type,param,sort);
    }

    /**
     * 文件详细信息
     */
    @RequestMapping("/info")
    public ResultBean fileInfo(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileId = getParam("fileId", params);

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("fileid", fileId);

        return snOlUserFileupService.getFileInfo(map);
    }

    /**
     * 文件收藏
     */
    @RequestMapping("/collect")
    public ResultBean fileCollect(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileId = getParam("fileId", params);

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("fileid", fileId);

        return snOlUserFileupService.markFileCollect(map);
    }

    /**
     * 取消文件收藏
     */
    @RequestMapping("/cancelCollect")
    public ResultBean cancelFileCollect(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileId = getParam("fileId", params);

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("fileid", fileId);

        return snOlUserFileupService.cancelFileCollect(map);
    }

    /**
     * 重命名文件
     */
    @RequestMapping("/rename")
    public ResultBean renterRenameFile(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileId = getParam("fileId", params);
        String fileName = getParam("fileName", params);

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("fileid", fileId);

        return snOlUserFileupService.renterRenameFile(map, fileName);
    }

    /**
     * 删除文件
     */
    @RequestMapping("/delete")
    public ResultBean renterDeleteFile(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileIds = getParam("fileIds", params);

        String sub = fileIds.substring(1,fileIds.length()-1);
        String subs = sub.replaceAll(" ","");
        String arr[] = subs.split(",");

        return snOlUserFileupService.renterDeleteFile(userId,arr);
    }

    /**
     * 彻底删除文件
     */
    @RequestMapping("/realDelete")
    public ResultBean renterRealDeleteFile(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileIds = getParam("fileIds", params);

        String sub = fileIds.substring(1,fileIds.length()-1);
        String subs = sub.replaceAll(" ","");
        String arr[] = subs.split(",");

        return snOlUserFileupService.renterRealDeleteFile(userId,arr);

    }
        /**
         * 还原文件
         */
    @RequestMapping("/restore")
    public ResultBean renterRestoreFile(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileIds = getParam("fileIds", params);
        String sub = fileIds.substring(1,fileIds.length()-1);
        String subs = sub.replaceAll(" ","");
        String arr[] = subs.split(",");

        return snOlUserFileupService.renterRestoreFile(userId,arr);
    }

    /**
     * 推送到HashStor
     */
    @RequestMapping("/push")
    public ResultBean renterPushFile(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fileIds = getParam("fileIds", params);
        String arr[] = fileIds.split(",");

        return snOlUserFileupService.renterPushFile(userId,arr);
    }

}
