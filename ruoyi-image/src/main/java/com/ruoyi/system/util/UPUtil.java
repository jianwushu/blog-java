package com.ruoyi.system.util;

import com.ruoyi.system.domain.Images;
import com.upyun.RestManager;
import com.upyun.UpException;
import okhttp3.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Author ilvyu.cn
 * @Date 2021/3/17 10:12
 * @Version 520.1314
 */
/*
又拍云存储
 */

public class UPUtil {
    private static final String BUCKET_NAME = "img520";
    private static final String OPERATOR_NAME = "java2574652740";
    private static final String OPERATOR_PWD = "X8BsOIxNlkiQn2Z3Ghbti7vN4gVRu9Fo";
    private static final String CDN_DOMAIN = "https://pic.ilvyu.cn";
    private static RestManager restManager;

    static {
        restManager = new RestManager(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
    }

    /**
     * 多文件上传
     */
    public static List<Images> uploadFile(MultipartFile[] files) {

        List<Images> images = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String path = getPreFilePath();
            String name = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
            String filePath = path + name;

            Images image = new Images();
            Response response;
            try {
                response = restManager.writeFile(filePath, file.getBytes(), (Map) null);
                System.out.println("上传");
                if (response.isSuccessful()) {

                    image.setPath(path);
                    image.setName(name);
                    image.setPathname(filePath);
                    image.setSize(file.getSize());

                    images.add(image);
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UpException e) {
                e.printStackTrace();
            }
        }


        return images;
    }

    /**
     * 解析上传
     */
    public static String uploadFile(String fileUrl) {
        int i = fileUrl.lastIndexOf(".");
        String type = fileUrl.substring(i);
        File file = new File("temp." + type);
        URL url;
        URLConnection urlConnection;
        InputStream is = null;
        OutputStream os = null;
        try {
            url = new URL(fileUrl);
            urlConnection = url.openConnection();
            is = urlConnection.getInputStream();
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.delete();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = uploadFile(file);
        return result;
    }

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    public static String uploadFile(File file) {
        String fileName = file.getName();
        String filePath = getPreFilePath() + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        Response response;
        try {
            response = restManager.writeFile(filePath, file, (Map) null);
            if (response.isSuccessful()) {
                return CDN_DOMAIN + filePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UpException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    private static String getPreFilePath() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        return "/" + year + "/" + month + "/" + day + "/";
    }

    private static String isSuccess(Response response) {
        return response.isSuccessful() ? " 成功" : " 失败";
    }
}

