package com.pxx.ifmserver.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PictureUtil {

    private static final String STATIC_RESOURCE_PATH="/resources";

    public static String savePicture(Integer id, MultipartFile picture,String url) throws IOException {
        //获取时间戳,用于组成图片名称
        long currentTimestamp = System.currentTimeMillis() / 1000;
        String timestampString = String.valueOf(currentTimestamp);
        // 构建完整的文件路径
        File directory = new File("");//参数为空
        String filename = id.toString() + "_" + timestampString + ".png";
        Path destinationFile = Paths.get(directory.getCanonicalPath()+ url + filename);

        // 检查同名文件是否存在，如果存在则删除
        if (Files.exists(destinationFile)) {
            Files.delete(destinationFile);
        }
        // 保存新文件
        picture.transferTo(destinationFile.toFile());
        return filename;
    }

    public static void deletePicture(String url) throws IOException {
        if(url.length() >= 5 && url.charAt(url.length() - 5) != 'D'){
            //若不是默认头像D.pn 则删除
            File directory = new File("");//参数为空
            Path destinationFile=Paths.get(directory.getCanonicalPath()+STATIC_RESOURCE_PATH +url);
            // 检查文件是否存在，如果存在则删除
            if (Files.exists(destinationFile)) {
                Files.delete(destinationFile);
            }
        }

    }


}
