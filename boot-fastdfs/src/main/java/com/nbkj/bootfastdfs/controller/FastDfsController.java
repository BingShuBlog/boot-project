package com.nbkj.bootfastdfs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.nbkj.bootfastdfs.utils.fastdfs.FastDFSFile;
import com.nbkj.bootfastdfs.utils.fastdfs.FastDfsClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDfsController
 */
@RestController
@RequestMapping("/fastdfs")
public class FastDfsController {
    private static Logger logger = LoggerFactory.getLogger(FastDfsController.class);

    @PostMapping("/upload")
    public Map<String, Object> singleFileUpload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("code", 200);
            result.put("message", "上传文件为空!");
            return result;
        } else {
            try {
                String path = saveFile(file);
                result.put("code", 200);
                result.put("message", "上传成功!");
                result.put("data", path);
                return result;
            } catch (Exception e) {
                logger.error("上传异常", e);
            }
        }
        return result;
    }

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    private String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDfsClient.upload(file); // upload to fastdfs
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }
        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }
        String path = FastDfsClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        return path;
    }

}