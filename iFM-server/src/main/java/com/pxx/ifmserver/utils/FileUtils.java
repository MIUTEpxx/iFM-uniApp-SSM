package com.pxx.ifmserver.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    private static final String STATIC_RESOURCE_PATH="/resources";

    /**
     *  储存图片文件到服务器,返回文件名称
     * @param id 文件所属对象的ID(命名用)
     * @param picture 图片文件
     * @param path 文件所储存的路径
     * @return
     * @throws IOException
     */
    public static String savePicture(Integer id, MultipartFile picture,String path) throws IOException {
        //获取时间戳,用于组成图片名称
        long currentTimestamp = System.currentTimeMillis() / 1000;
        String timestampString = String.valueOf(currentTimestamp);
        // 构建完整的文件路径
        File directory = new File("");//参数为空
        String filename = id.toString() + "_" + timestampString + ".png";
        Path destinationFile = Paths.get(directory.getCanonicalPath()+ path + filename);

        // 检查同名文件是否存在，如果存在则删除
        if (Files.exists(destinationFile)) {
            Files.delete(destinationFile);
        }
        // 保存新文件
        picture.transferTo(destinationFile.toFile());
        return filename;
    }

    /**
     * 删除图片文件
     * @param path
     * @throws IOException
     */
    public static void deletePicture(String path) throws IOException {
        if(path.length() >= 5 && path.charAt(path.length() - 5) != 'D'){
            //若不是默认头像D.pn 则删除
            File directory = new File("");//参数为空
            Path destinationFile=Paths.get(directory.getCanonicalPath()+STATIC_RESOURCE_PATH +path);
            // 检查文件是否存在，如果存在则删除
            if (Files.exists(destinationFile)) {
                Files.delete(destinationFile);
            }
        }

    }

    /**
     * 储存音频文件到服务器上,返回文件名称
     * @param id 文件所属对象的ID(命名用)
     * @param audio 音频文件
     * @param path 文件所储存的路径
     * @return 文件名|音频时长
     * @throws IOException
     */
    public static Map<String, Object> saveAudio(Integer id, MultipartFile audio,String path) throws IOException {
        Map<String, Object> data = new HashMap<>();
        //获取时间戳,用于组成图片名称
        long currentTimestamp = System.currentTimeMillis() / 1000;
        String timestampString = String.valueOf(currentTimestamp);
        // 构建完整的文件路径
        File directory = new File("");//参数为空
        String filename = id.toString() + "_" + timestampString + ".mp3";
        Path destinationFile = Paths.get(directory.getCanonicalPath()+ path + filename);

        // 检查同名文件是否存在，如果存在则删除
        if (Files.exists(destinationFile)) {
            Files.delete(destinationFile);
        }
        // 保存新文件
        audio.transferTo(destinationFile.toFile());
        // 获取音频时长
        long duration = getAudioDuration(destinationFile.toFile());
        data.put("fileName", filename);
        data.put("duration", duration);
        return data;
    }

    /**
     * 删除音频文件
     * @param path
     * @throws IOException
     */
    public static void deleteAudio(String path) throws IOException {
        if(path.length() >= 5 && path.charAt(path.length() - 5) != 'D'){
            //若不是默认头像D.pn 则删除
            File directory = new File("");//参数为空
            Path destinationFile=Paths.get(directory.getCanonicalPath()+STATIC_RESOURCE_PATH +path);
            // 检查文件是否存在，如果存在则删除
            if (Files.exists(destinationFile)) {
                Files.delete(destinationFile);
            }
        }

    }

    /**
     * 获取音频文件的时长
     * @param audioFile 音频文件.mp3
     * @return
     */
    public static long getAudioDuration(File audioFile) {
        long total = 0;
        try {
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(audioFile);
            Map props = aff.properties();
            if (props.containsKey("duration")) {
                total = (long) Math.round((((Long) props.get("duration")).longValue()) / 1000);
            }
            System.out.println(total / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        total=total/1000;
        return total;
    }
}
