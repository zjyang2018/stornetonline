package com.ch.stornetonline.modules.app.service.impl;

import com.ch.stornetonline.common.bean.ErrorBean;
import com.ch.stornetonline.common.bean.ResultBean;
import com.ch.stornetonline.common.bean.SuccessBean;
import com.ch.stornetonline.common.constants.ErrorConstants;
import com.ch.stornetonline.common.utils.IdWorker;
import com.ch.stornetonline.common.utils.NowDate;
import com.ch.stornetonline.common.utils.UUIDGenerator;
import com.ch.stornetonline.common.utils.fileutils.PageUtils;
import com.ch.stornetonline.modules.app.common.Constants;
import com.ch.stornetonline.modules.app.common.FileConstants;
import com.ch.stornetonline.modules.app.config.CommonConfig;
import com.ch.stornetonline.modules.app.entity.SnOlUserFiledw;
import com.ch.stornetonline.modules.app.entity.SnOlUserFileup;
import com.ch.stornetonline.modules.app.entity.SnOlUserSpace;
import com.ch.stornetonline.modules.app.entity.SnOlUserSpaceRec;
import com.ch.stornetonline.modules.app.mapper.SnOlUserFiledwMapper;
import com.ch.stornetonline.modules.app.mapper.SnOlUserFileupMapper;
import com.ch.stornetonline.modules.app.mapper.SnOlUserSpaceMapper;
import com.ch.stornetonline.modules.app.mapper.SnOlUserSpaceRecMapper;
import com.ch.stornetonline.modules.app.service.SnOlUserFileService;
import com.ch.stornetonline.sia.SiaObject;
import com.ch.stornetonline.sia.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("snOlUserFileupService")
@Transactional
public class SnOlUserFileServiceImpl extends BaseServiceImpl implements SnOlUserFileService {
    private static final Logger log = LoggerFactory.getLogger(SnOlUserFileServiceImpl.class);

    private IdWorker idWorker = new IdWorker(2);

    private SiaObject so = CommonConfig.getSiaObject();

    @Resource
    SnOlUserFileupMapper snOlUserFileupMapper;

    @Resource
    SnOlUserSpaceMapper snOlUserSpaceMapper;

    @Resource
    SnOlUserSpaceRecMapper snOlUserSpaceRecMapper;

    @Resource
    SnOlUserFiledwMapper snOlUserFiledwMapper;

    @Override
    public ResultBean getFileInfo(Map map) {
        SnOlUserFileup snOlUserFileup = null;
        try {
            snOlUserFileup = snOlUserFileupMapper.selectByUserIdAndFileId(map);
            if (snOlUserFileup == null){
                return new ErrorBean("-1","文件不存在");
            }

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(snOlUserFileup);
    }

    @Override
    public ResultBean renterUploadFile(String userId, MultipartFile file) {

        // 文件大小
        long size = file.getSize();
        SnOlUserSpace snOlUserSpace = snOlUserSpaceMapper.selectByUserId(userId);
        if (snOlUserSpace == null) {
            return new ErrorBean("-1", "请先购买空间");
        }

        try {
            long currentSpace = snOlUserSpace.getFreeSpace();
            // 判断用户空间是否够用
            long result = currentSpace - size;
            if (result < 0) {
                log.error(userId + ": 空间不足");
                return new ErrorBean(ErrorConstants.ERRORCODE_SPACE_NOT_ENOUGH, ErrorConstants.ERRORMSG_SPACE_NOT_ENOUGH);
            }
            String realFileName = file.getOriginalFilename();
            int pos = realFileName.indexOf('.');
            String realFileNameExt = realFileName.substring(pos + 1, realFileName.length());

            //String genRealPath = Constants.FILE_SEPARATOR + NowDate.creatDate();
            //String fileRealPath = CommonConfig.getBaseFilePath() + Constants.UPLOAD_FILE_CATALOG + genRealPath;
            String fileRealPath = CommonConfig.getBaseFilePath() + Constants.UPLOAD_FILE_CATALOG;

            // 判断文件夹是否存在
            File targerFile = new File(fileRealPath);

            // 判断是否存在目录
            if (!targerFile.exists()) {
                targerFile.mkdirs();
            }

            //Long id = idWorker.nextId();
            //String genFilename = String.valueOf(id) + "." + realFileNameExt;
            String genFilename = UUIDGenerator.getUUID() + "." + realFileNameExt;

            // 文件存储的路径
            String targerFilename = fileRealPath + Constants.FILE_SEPARATOR + genFilename;
            File uploadFile = new File(targerFilename);

            // 上传文件到本地
            FileCopyUtils.copy(file.getBytes(), uploadFile);

            String siaPath = userId + Constants.FILE_SEPARATOR + NowDate.creatDate() + Constants.FILE_SEPARATOR + UUIDGenerator.getUUID() + Constants.FILE_SEPARATOR + realFileName;

            // 从本地上传文件到StorNet网络
            JSONArray jsonArray = so.renterUploadFile(uploadFile.getCanonicalPath(), siaPath);
            if (CommonConfig.callResult(jsonArray)) {
                log.error("renterUploadFile" + ": 上传文件到StorNet网络失败");
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "上传文件到StorNet网络失败");
            }

            if(!CommonConfig.getUploadResult(so,siaPath)){
                log.error("renterUploadFile" + ": 上传文件到StorNet网络失败");
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "上传文件到StorNet网络失败");
            }

            // 上传成功后，将本地文件删除
            uploadFile.delete();

            // 将文件上传记录写入数据库
            Long nowTime = System.currentTimeMillis();
            String fileId = UUIDGenerator.getUUID();
            SnOlUserFileup snOlUserFileup = new SnOlUserFileup();
            snOlUserFileup.setFileid(fileId);
            snOlUserFileup.setUserid(userId);
            snOlUserFileup.setLocalPath(targerFilename);
            snOlUserFileup.setSiaPath(siaPath);
            snOlUserFileup.setName(realFileName);
            snOlUserFileup.setSize(size);
            snOlUserFileup.setStatus("1");
            snOlUserFileup.setUpdateTime(nowTime);
            snOlUserFileup.setUploadTime(nowTime);
            snOlUserFileupMapper.insertSelective(snOlUserFileup);

            // 记录空间使用信息
            SnOlUserSpaceRec snOlUserSpaceRec = new SnOlUserSpaceRec();
            snOlUserSpaceRec.setId(UUIDGenerator.getUUID());
            snOlUserSpaceRec.setUserid(userId);
            snOlUserSpaceRec.setDuration("");
            long convertSpacesTOGB = size * FileConstants.CONVERT_TO_TB;
            snOlUserSpaceRec.setSpace(convertSpacesTOGB);
            snOlUserSpaceRec.setType("2");
            snOlUserSpaceRec.setCreateTime(nowTime);
            snOlUserSpaceRecMapper.insertSelective(snOlUserSpaceRec);

            // 更新用户空间信息
            long existFreeSpace = snOlUserSpace.getFreeSpace();
            long currentFreeSpace = existFreeSpace - size;

            snOlUserSpace.setFreeSpace(currentFreeSpace);
            snOlUserSpace.setUpdateTime(nowTime);
            snOlUserSpaceMapper.updateByPrimaryKey(snOlUserSpace);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(ErrorConstants.ERRORMSG_OK);

    }

    @Override
    public ResultBean markFileCollect(Map<String, Object> map) {
        Long nowTime = System.currentTimeMillis();

        try {
            SnOlUserFileup snOlUserFileup = snOlUserFileupMapper.selectByUserIdAndFileId(map);
            snOlUserFileup.setStatus("2");
            snOlUserFileup.setUpdateTime(nowTime);
            snOlUserFileupMapper.updateByPrimaryKeySelective(snOlUserFileup);
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }


    @Override
    public ResultBean cancelFileCollect(Map<String, Object> map) {
        Long nowTime = System.currentTimeMillis();

        try {
            SnOlUserFileup snOlUserFileup = snOlUserFileupMapper.selectByUserIdAndFileId(map);
            snOlUserFileup.setStatus("1");
            snOlUserFileup.setUpdateTime(nowTime);
            snOlUserFileupMapper.updateByPrimaryKeySelective(snOlUserFileup);
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean renterDownloadFile(String userId, String fileId, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("fileid", fileId);
        String uuid = UUIDGenerator.getUUID();
        String nowdata = NowDate.creatDate();
        SnOlUserFileup snOlUserFileup = null;
        String filename = null;

        try {
            snOlUserFileup = snOlUserFileupMapper.selectByUserIdAndFileId(map);
            filename = snOlUserFileup.getName();
            String siaPath = snOlUserFileup.getSiaPath();

            // 下载文件存储的目录路径
            String targerFilename = CommonConfig.getBaseFilePath() + Constants.DOWNLOAD_FILE_CATALOG + Constants.FILE_SEPARATOR + nowdata + Constants.FILE_SEPARATOR + uuid;

            // 判断文件夹是否存在
            File targerFile = new File(targerFilename);

            // 判断是否存在目录
            if (!targerFile.exists()) {
                targerFile.mkdirs();
            }

            // 下载文件存储的路径
            String downFile = targerFilename + Constants.FILE_SEPARATOR + filename;

            JSONArray jsonArray = so.renterDownloadFile(siaPath, downFile);

            if (CommonConfig.callResult(jsonArray)) {
                log.error("renterDownloadFile" + ": 从StorNet网络下载文件失败");
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "从StorNet网络下载文件失败");
            }
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        SnOlUserFiledw snOlUserFiledw = snOlUserFiledwMapper.selectByPrimaryKey(fileId.trim());
        if (snOlUserFiledw == null) {
            try {
                SnOlUserFiledw snOlUserFile = new SnOlUserFiledw();
                snOlUserFile.setFileid(fileId);
                snOlUserFile.setUserid(userId);
                snOlUserFile.setPath(snOlUserFileup.getLocalPath());
                snOlUserFile.setName(snOlUserFileup.getName());
                snOlUserFile.setSize(snOlUserFileup.getSize());
                long nowTime = System.currentTimeMillis();
                snOlUserFile.setDownloadTime(nowTime);
                snOlUserFile.setDownloadConut(1);
                snOlUserFiledwMapper.insertSelective(snOlUserFile);
            } catch (RuntimeException e) {
                log.error("系统异常," + e.getMessage(), e);
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
            } catch (Exception e) {
                log.error("系统异常,请稍后重试", e);
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
            }
        } else {
            try {
                int conut = snOlUserFiledw.getDownloadConut();
                snOlUserFiledw.setDownloadConut(conut++);
                snOlUserFiledwMapper.updateByPrimaryKeySelective(snOlUserFiledw);
            } catch (RuntimeException e) {
                log.error("系统异常," + e.getMessage(), e);
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
            } catch (Exception e) {
                log.error("系统异常,请稍后重试", e);
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
            }
        }

        String vfile = CommonConfig.getUrl() + Constants.FILE_SEPARATOR + nowdata + Constants.FILE_SEPARATOR + uuid + Constants.FILE_SEPARATOR  + filename;

        return new SuccessBean(vfile);
    }

    @Override
    public ResultBean getFileList(String userId, String pageNum, String pageSize, String type, String param, String sort) {
        Integer page = Integer.parseInt(pageNum);
        Integer limit = Integer.parseInt(pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        map.put("param", param);
        if ("".equals(sort)) {
            map.put("orderByClause", "update_time");
        } else {
            map.put("orderByClause", sort);
        }

        List<SnOlUserFileup> fileList = null;
        int total = 0;
        try {
            switch (Integer.parseInt(type)) {
                case 0:
                    map.put("status", "0");
                    fileList = snOlUserFileupMapper.queryList(map);
                    total = snOlUserFileupMapper.queryTotal(map);
                    break;
                case 2:
                    map.put("status", "2");
                    fileList = snOlUserFileupMapper.queryList(map);
                    total = snOlUserFileupMapper.queryTotal(map);
                    break;
                default:
                    //查询列表数据
                    fileList = snOlUserFileupMapper.queryAllList(map);
                    total = snOlUserFileupMapper.queryAllTotal(map);
            }


        } catch (RuntimeException e) {
            log.info("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "无文件记录");
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        PageUtils pageUtil = new PageUtils(fileList, total, limit, page);

        return new SuccessBean(pageUtil);
    }

    @Override
    public ResultBean renterRenameFile(Map<String, Object> map, String fileName) {
        String userId = (String) map.get("userid");
        try {
            SnOlUserFileup snOlUserFileup = snOlUserFileupMapper.selectByUserIdAndFileId(map);

            String fn = snOlUserFileup.getName();
            String current_path = snOlUserFileup.getSiaPath();

            int pos = fn.indexOf('.');
            String realFileNameExt = fn.substring(pos + 1, fn.length());
            String genFileName = fileName + "." + realFileNameExt;
            String new_path = userId + Constants.FILE_SEPARATOR + NowDate.creatDate() + Constants.FILE_SEPARATOR + UUIDGenerator.getUUID() + Constants.FILE_SEPARATOR + genFileName;

            JSONArray jsonArray = so.renterRenameFile(current_path, new_path);
            if (CommonConfig.callResult(jsonArray)) {
                log.error("renterRenameFile" + ": 重命名StorNet网络上的文件失败");
                return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "重命名StorNet网络上的文件失败");
            }

            snOlUserFileup.setSiaPath(new_path);
            snOlUserFileup.setName(genFileName);

            snOlUserFileupMapper.updateByPrimaryKeySelective(snOlUserFileup);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean renterDeleteFile(String userId,String[] arr) {
        try {
            List<SnOlUserFileup> snOlUserFileupList = snOlUserFileupMapper.selectBatchByUserIdAndFileId(userId,arr);
            for (SnOlUserFileup snOlUserFileup : snOlUserFileupList) {
                String filename = snOlUserFileup.getName();
                String sia_path = snOlUserFileup.getSiaPath();
                //String genRealPath = Constants.FILE_SEPARATOR + NowDate.creatDate();

                //  删除文件的存储目录路径
                //String targerFilename = CommonConfig.getBaseFilePath() + Constants.DELETE_FILE_CATALOG + genRealPath;
                String targerFilename = CommonConfig.getBaseFilePath() + Constants.DELETE_FILE_CATALOG + Constants.FILE_SEPARATOR + NowDate.creatDate() + Constants.FILE_SEPARATOR + UUIDGenerator.getUUID();

                // 判断文件夹是否存在
                File targerFile = new File(targerFilename);

                // 判断是否存在目录
                if (!targerFile.exists()) {
                    targerFile.mkdirs();
                }

                //  删除文件的存储路径
                String downFile = targerFilename + Constants.FILE_SEPARATOR + filename;

                JSONArray jsonArray = so.renterDownloadFile(sia_path, downFile);
                if (CommonConfig.callResult(jsonArray)) {
                    log.error("renterDeleteFile" + ": 从StorNet网络下载文件失败");
                    return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "从StorNet网络下载文件失败");
                }

                JSONArray jsonArray1 = so.renterDeleteFile(sia_path);
                if (CommonConfig.callResult(jsonArray1)) {
                    log.error("renterDeleteFile" + ": 从StorNet网络删除文件失败");
                    return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "从StorNet网络删除文件失败");
                }

                Long nowTime = System.currentTimeMillis();
                snOlUserFileup.setStatus("0");
                snOlUserFileup.setLocalPath(downFile);
                snOlUserFileup.setUpdateTime(nowTime);
                snOlUserFileupMapper.updateByPrimaryKeySelective(snOlUserFileup);
            }
            return new SuccessBean(ErrorConstants.ERRORMSG_OK);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }
    }

    @Override
    public ResultBean renterRealDeleteFile(String userId,String[] arr) {
        try {
            List<SnOlUserFileup> snOlUserFileupList = snOlUserFileupMapper.selectBatchByUserIdAndFileId(userId,arr);
            for (SnOlUserFileup snOlUserFileup : snOlUserFileupList) {
                String fileLocalPath = snOlUserFileup.getLocalPath();

                File targerFile = new File(fileLocalPath);

                targerFile.delete();

                snOlUserFileupMapper.deleteByPrimaryKey(snOlUserFileup.getFileid());
            }
            return new SuccessBean(ErrorConstants.ERRORMSG_OK);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }
    }

    @Override
    public ResultBean renterRestoreFile(String userId,String[] arr) {
        try {
            List<SnOlUserFileup> snOlUserFileupList = snOlUserFileupMapper.selectBatchByUserIdAndFileId(userId,arr);
            for (SnOlUserFileup snOlUserFileup : snOlUserFileupList) {
                String targerFilename = snOlUserFileup.getLocalPath();
                String realFileName = snOlUserFileup.getName();
                log.info(targerFilename);

                // 判断文件夹是否存在
                File targerFile = new File(targerFilename);
                log.info(targerFile.getCanonicalPath());

                String siaPath = userId + Constants.FILE_SEPARATOR + NowDate.creatDate() + Constants.FILE_SEPARATOR + UUIDGenerator.getUUID() + Constants.FILE_SEPARATOR + realFileName;
                // 从本地上传文件到StorNet网络
                JSONArray jsonArray = so.renterUploadFile(targerFile.getCanonicalPath(), siaPath);
                if (CommonConfig.callResult(jsonArray)) {
                    log.error("renterRestoreFile" + ": 上传文件到StorNet网络失败");
                    return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "上传文件到StorNet网络失败");
                }

                if(!CommonConfig.getUploadResult(so,siaPath)){
                    log.error("renterRestoreFile" + ": 上传文件到StorNet网络失败000");
                    return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "上传文件到StorNet网络失败");
                }

                Long nowTime = System.currentTimeMillis();
                snOlUserFileup.setStatus("1");
                snOlUserFileup.setSiaPath(siaPath);
                snOlUserFileup.setUpdateTime(nowTime);

                targerFile.delete();

                snOlUserFileupMapper.updateByPrimaryKeySelective(snOlUserFileup);
            }
            return new SuccessBean(ErrorConstants.ERRORMSG_OK);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }
    }

    @Override
    public ResultBean renterPushFile(String userId,String[] arr) {
        try {
            List<SnOlUserFileup> snOlUserFileupList = snOlUserFileupMapper.selectBatchByUserIdAndFileId(userId,arr);
            for (SnOlUserFileup snOlUserFileup : snOlUserFileupList) {

                String filename = snOlUserFileup.getName();
                String sia_path = snOlUserFileup.getSiaPath();
                //String genRealPath = Constants.FILE_SEPARATOR + NowDate.creatDate();

                //  推送文件的存储目录
                //String targerFilename = CommonConfig.getBaseFilePath() + Constants.PUSH_FILE_CATALOG + genRealPath;
                String targerFilename = CommonConfig.getBaseFilePath() + Constants.PUSH_FILE_CATALOG + Constants.FILE_SEPARATOR + NowDate.creatDate() + Constants.FILE_SEPARATOR + UUIDGenerator.getUUID();

                // 判断文件夹是否存在
                File targerFile = new File(targerFilename);

                // 判断是否存在目录
                if (!targerFile.exists()) {
                    targerFile.mkdirs();
                }

                //  推送文件的存储路径
                String downFile = targerFilename + Constants.FILE_SEPARATOR + filename;

                JSONArray jsonArray = so.renterDownloadFile(sia_path, downFile);
                if (CommonConfig.callResult(jsonArray)) {
                    log.error("renterDeleteFile" + ": 从StorNet网络下载文件失败");
                    return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, "从StorNet网络下载文件失败");
                }

                //接收文件的服务器地址
                String result = Utils.uploadToServer(CommonConfig.getUploadURL(), new File(downFile));
                if (result.equals("null")) {
                    return new ErrorBean("-1", "文件推送到HashStor失败");
                }

                JSONObject jsonObject = new JSONObject(result);
                boolean success = jsonObject.getBoolean("success");
                String res = jsonObject.getString("result");
                String errorCode = jsonObject.getString("errorCode");
                if (success != true || !res.equals("null") || !errorCode.equals("000000")) {
                    return new ErrorBean("-1", "HashStor服务器内部错误");
                }
            }

            return new SuccessBean(ErrorConstants.ERRORMSG_OK);
        } catch (RuntimeException e) {
        log.error("系统异常," + e.getMessage(), e);
        return new ErrorBean(ErrorConstants.ERRORCODE_FILE_NOEXIST, ErrorConstants.ERRORMSG_FILE_NOEXIST);
    } catch (Exception e) {
        log.error("系统异常,请稍后重试", e);
        return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
    }

    }
}
