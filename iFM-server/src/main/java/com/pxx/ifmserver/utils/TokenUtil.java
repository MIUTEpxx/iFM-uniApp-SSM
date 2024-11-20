package com.pxx.ifmserver.utils;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Base64;
import java.util.UUID;

public class TokenUtil {

    private static final String SALT = "your-salt"; // 用于增强 Token 安全性的盐值

    /**
     * 创建 Token 对象
     *
     * @param id
     * @param status
     * @return 返回 Token 字符串
     */
    public static String createToken(int id, int status) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        // 当前时间加60分钟，作为 Token 的有效时间
        Date dateafter = new Date(date.getTime() + 3600000);
        Object endtime = sif.format(dateafter);
        String json = "id:" + id + ",status:" + status + ",endtime:" + endtime;
        JSONObject jsonObject = new JSONObject();
        String ss = jsonObject.toJSONString(json);

        // 使用 UUID 生成随机字符串
        String randomString = UUID.randomUUID().toString();

        // 使用 SHA-256 进行签名
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(ss.getBytes("UTF-8"));
        byte[] byteArray = md.digest();
        StringBuilder sha256StrBuff = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
                sha256StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            }
            else{
                sha256StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        String token = ss + ";" + sha256StrBuff.toString() + ";" + randomString;
        // 使用 Base64 编码 Token
        return Base64.getEncoder().encodeToString(token.getBytes("UTF-8"));
    }


    /**
     * 后端拿到前端请求头的token对象，验证身份，比较有效时间
     *
     * @param req
     * @param resp
     * @param userId
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String verifyToken(HttpServletRequest req, HttpServletResponse resp,Integer userId) throws IOException, ParseException, NoSuchAlgorithmException {

        // 拿到token对象
        String token = req.getHeader("Token");
        if (token == null || token.isEmpty()) {
            //resp.getWriter().println("Token 缺失或无效");
            return null;
        }

        // 解码 Base64 编码的 Token
        String decodedToken = new String(Base64.getDecoder().decode(token), "UTF-8");
        String[] parts = decodedToken.split(";");
        if (parts.length != 3) {
            //resp.getWriter().println("Token 格式错误");
            return null;
        }


        // 解析 Token 内容
        String jsonPart = URLDecoder.decode(parts[0], "UTF-8");
        String sha256Hex = parts[1];
        String randomString = parts[2]; // 这个字段在验证过程中并不使用

        // 提取 id 和 status
        String idPart = jsonPart.substring(jsonPart.indexOf("id:") + 3, jsonPart.indexOf(","));
        int id = Integer.parseInt(idPart);
        String statusPart = jsonPart.substring(jsonPart.indexOf("status:") + 7, jsonPart.indexOf(",endtime:"));
        int status = Integer.parseInt(statusPart);

        // 验证 Token 是否过期
        String endtimePart = jsonPart.substring(jsonPart.indexOf("endtime:") + 8);
        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endTime = sif.parse(endtimePart);
        long currentTime = System.currentTimeMillis();
        if (currentTime > endTime.getTime()) {
            //resp.getWriter().println("Token有效时间已过，请重新登录!");
            return null;
        }

        // 验证 Token 签名
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(jsonPart.getBytes("UTF-8"));
        byte[] digest = md.digest();
        String calculatedSha256Hex = Hex.encodeHexString(digest);
        if (!calculatedSha256Hex.equals(sha256Hex) || id != userId) {
            //resp.getWriter().println("Token信息有误!");
            return null;
        }

        // 业务处理成功，返回新的 Token
        return createToken(id, status);
    }


    /**
     * 生成一个随机的字符串
     *
     * @return 返回随机字符串
     */
    private static String getRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }


}
//        // 解析当前token对象的id，状态
//        int id = Integer.parseInt(decodedParts[0].substring(decodedParts[0].indexOf(":") + 1, decodedParts[0].indexOf(",")));
//        int status = Integer.parseInt(decodedParts[0].substring(decodedParts[0].indexOf(":") + 1, decodedParts[0].lastIndexOf(",")));
//
//        // 验证 Token 是否过期
//        String endtime = decodedParts[2].substring(decodedParts[2].indexOf(":") + 1, decodedParts[2].length());
//        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date data = sif.parse(endtime);
//        long stime = data.getTime();
//        Date date1 = new Date();
//        long nowtime = date1.getTime();
//        long differen = stime - nowtime;
//
//        if (differen < 0) {
//            resp.getWriter().println("Token有效时间已过，请重新登录!");
//            return null;
//        }
//
//        // 验证 Token 签名
//        String ss = decodedParts[0] + ";" + decodedParts[1];
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        md.update(ss.getBytes("UTF-8"));
//        byte[] digest = md.digest();
//        String sha256Hex = Hex.encodeHexString(digest);
//        if (!sha256Hex.equals(decodedParts[1])) {
//            resp.getWriter().println("Token信息有误!");
//            return null;
//        }

// 处理业务逻辑
// 解析当前token对象的id，状态