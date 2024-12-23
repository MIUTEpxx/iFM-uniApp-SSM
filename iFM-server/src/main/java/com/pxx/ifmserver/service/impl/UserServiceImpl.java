package com.pxx.ifmserver.service.impl;

import com.pxx.ifmserver.entity.dto.User;
import com.pxx.ifmserver.mapper.UserMapper;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.UserService;
import com.pxx.ifmserver.utils.EmailUtil;
import com.pxx.ifmserver.utils.FileUtils;
import com.pxx.ifmserver.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    //用户头像储存的目录常量
    private static final String USER_HEAD_PICTURE_PATH="/resources/images/user/head/";

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> listUser(){
        return userMapper.listUser();
    }

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    @Override
    public Result getUserByUserId(Integer userId) {
       // return userMapper.getUserByUserId(userid);
        Map<String, Object> data = new HashMap<>();

        User user= userMapper.getUserByUserId(userId);

        if(user!=null){
            data.put("user", user);
            return Result.ok().data(data);
        }

        data.put("error","账号为 "+userId+" 的用户不存在");
        Result result = new Result(false, 60000, "未能找到此账号对应用的户信息", data);
        return result;
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }
    @Override
    public User getUserByUserEmail(String userEmail){
        return userMapper.getUserByUserEmail(userEmail);
    }

    /**
     * 先检查新头像图片文件是否为空
     * 然后将新头像图片文件命名为 "userId_时间戳.png"
     * 将新头像图片文件存储于src/main/resources/static/images/user
     * 删除用户旧头像图片
     * 最后更新数据库中对应的用户信息
     *
     * @param userId 用户ID
     * @param userPicture 头像图片文件
     * @return 成功则返回新头像路径,失败则返回错误信息
     */
    @Override
    public Result updateUserPicurlByUserId(Integer userId, MultipartFile userPicture){
        Map<String, Object> data = new HashMap<>();
        if (userPicture.isEmpty()) {
            data.put("error","文件为空");
            Result result = new Result(false,70000,"文件上传失败",data);
            return result;
        }
        try {
           /* //获取时间戳,用于组成图片名称
            long currentTimestamp = System.currentTimeMillis() / 1000;
            String timestampString = String.valueOf(currentTimestamp);
            // 构建完整的文件路径
            File directory = new File("");//参数为空
            String filename = userId.toString() + "_" + timestampString + ".png";
            Path destinationFile = Paths.get(directory.getCanonicalPath()+"/src/main/resources/static/images/user/head/" + filename);

            // 检查文件是否存在，如果存在则删除
            if (Files.exists(destinationFile)) {
                Files.delete(destinationFile);
            }
            // 保存新文件
            userPicture.transferTo(destinationFile.toFile());*/
            /* if(oldPicUrl.length() >= 5 && oldPicUrl.charAt(oldPicUrl.length() - 5) != 'D'){
                //若不是默认头像D.png则删除
                destinationFile=Paths.get(directory.getCanonicalPath()+"/src/main/resources/static" +oldPicUrl);
                // 检查文件是否存在，如果存在则删除
                if (Files.exists(destinationFile)) {
                    Files.delete(destinationFile);
                }
            }*/
            //储存新头像文件
            String filename= FileUtils.savePicture(userId,userPicture,USER_HEAD_PICTURE_PATH);

            //删除旧头像图片
            String oldPicture = userMapper.getUserByUserId(userId).getUserPicture();//获取旧头像储存路径
            FileUtils.deletePicture(oldPicture);


            try {
                // 更新数据库
                userMapper.updateUserPicurlByUserId(userId, "/images/user/head/" + filename);
                data.put("userPicture","/images/user/head/" + filename);
                return  Result.ok().data(data);
            } catch (RuntimeException e) {
                data.put("error",e.getMessage());
                Result result = new Result(false,20001,"未知错误",data);
                return result;
            }

        } catch (IOException e) {
            //e.printStackTrace();
            data.put("error",e.getMessage());
            Result result = new Result(false,20001,"未知错误",data);
            return result;
        }
    }

    /**
     * 修改用户密码
     * @param userId
     * @param userEmail
     * @param userPassword
     * @param code
     * @return
     */
    @Override
    public Result updateUserPassword(Integer userId, String userEmail, String userPassword,  String code){
        Map<String, Object> data = new HashMap<>();
        if(!verifyEmail(userEmail,code)){
            data.put("error","验证码错误");
            return new Result(false,20004,"修改失败",data);
        }
        try{
            userMapper.updateUserPasswordByUserId(userId,userPassword);
            return  Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error",e.getMessage());
            Result result = new Result(false,20001,"未知错误,修改失败",data);
            return result;
        }
    }

    /**
     * 更改用户邮箱
     * @param userId
     * @param userEmail
     * @param newEmail
     * @param code1
     * @param code2
     * @return
     */
    @Override
    public Result updateUserEmail(Integer userId, String userEmail, String newEmail,  String code1,String code2){
        Map<String, Object> data = new HashMap<>();
        try{
            User user=userMapper.getUserByUserEmail(userEmail);
            if(user!=null){
                data.put("error","该邮箱已被其他用户绑定");
                return new Result(false,50002,"修改失败",data);
            }
            if(!verifyEmail(newEmail,code2)||!verifyEmail(userEmail,code1)){
                data.put("error","验证码错误");
                return new Result(false,20004,"修改失败",data);
            }
            userMapper.updateUserEmailByUserId(userId,newEmail);
            data.put("userEmail",newEmail);
            return  Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error",e.getMessage());
            Result result = new Result(false,20001,"未知错误,修改失败",data);
            return result;
        }
    }

    /**
     * 向数据库user表插入新的用户信息记录(用户注册)
     * 依次检查昵称,邮箱是否被使用
     * 最后尝试插入数据库user表中,成功则返回用户信息,失败返回错误信息
     *
     * @param user 待插入的用户数据
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @Override
    public Result insertUser(User user,String code) {
        Map<String, Object> data = new HashMap<>();
        if(userMapper.getUserByUserName(user.getUserName())!=null){
            data.put("error","昵称已被使用");
            Result result = new Result(false,50001,"用户创建失败",data);
            return result;

        }else if(userMapper.getUserByUserEmail(user.getUserEmail())!=null){
            data.put("error","邮箱已被使用");
            Result result = new Result(false,50002,"用户创建失败",data);
            return result;

        }else if(!verifyEmail(user.getUserEmail(), code)){
            data.put("error","验证码错误");
            return new Result(false,20004,"用户注册失败",data);
        }

        try {
            user.setUserPicture("/images/user/head/D.png");//默认头像
            userMapper.insertUser(user);
            data.put("user",user);
            return  Result.ok().data(data);
        } catch (DuplicateKeyException e) {
            data.put("error",e.getMessage());
            Result result = new Result(false,20001,"未知错误",data);
            return result;
        }


    }
    /**
     * 修改用户个性签名
     * @param userId 账号
     * @param userProfile 新个性签名内容
     * @return 成功则返回新个性签名内容,失败则返回错误信息
     */
    @Override
    public Result updateUserProfileByUserId(Integer userId, String userProfile){
        Map<String, Object> data = new HashMap<>();
        try {
            userMapper.updateUserProfileByUserId(userId, userProfile);
            data.put("userProfile",userProfile);
            return Result.ok().data(data);
        }catch (DuplicateKeyException e) {
            data.put("error",e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }

    }
    /**
     * 修改用户昵称,需要先检查用户昵称是否已被使用
     * @param userId 账号
     * @param userName 新昵称
     * @return 成功则返回新昵称,失败则返回错误信息
     */
    @Override
    public Result updateUserNameByUserId(Integer userId, String userName){
        Map<String, Object> data = new HashMap<>();
        User user=userMapper.getUserByUserName(userName);
        if(user!=null){
            data.put("error","昵称已被其他用户使用");
            return new Result(false,50001,"修改失败",data);
        }

        try {
            userMapper.updateUserNameByUserId(userId, userName);
            data.put("userName",userName);
            return Result.ok().data(data);
        }catch (DuplicateKeyException e) {
            data.put("error",e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 用户登录 先检查账号是否存在 再检查密码是否匹配
     * @param userId 账号
     * @param userPassword 密码
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @Override
    public Result loginPassword(Integer userId, String userPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, Object> data = new HashMap<>();
        User user = userMapper.getUserByUserId(userId);
        if(user==null){
            data.put("error","账号 "+userId+" 不存在");
            return new Result(false,60000,"登录失败",data);
        }
        if(!userPassword.equals(user.getUserPassword())){
            data.put("error","密码错误");
            return new Result(false,60001,"登录失败",data);
        }
        //生成Token安全令牌
        String newToken = TokenUtil.createToken(user.getUserId(),1);
        data.put("token",newToken);
        data.put("user",user);
        return Result.ok().data(data);
    }

    /**
     * 邮箱验证登录
     * @param userEmail
     * @param code
     * @return
     */
    @Override
    public  Result loginEamil(String userEmail, String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, Object> data = new HashMap<>();
        User user = userMapper.getUserByUserEmail(userEmail);
        if(user==null){
            data.put("error","该邮箱未绑定用户");
            return new Result(false,60003,"登录失败",data);
        }
        if(!verifyEmail(userEmail,code)){
            data.put("error","验证码错误");
            return new Result(false,20004,"登录失败",data);
        }
        //生成Token安全令牌
        String newToken = TokenUtil.createToken(user.getUserId(),1);
        data.put("token",newToken);
        data.put("user",user);
        return Result.ok().data(data);
    }

    /**
     * 根据关键词搜索用户
     * @param keyword
     * @return
     */
    @Override
    public  Result searchUser(String keyword) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<User> userList = userMapper.listUserByKeyWord(keyword);
            data.put("userList",userList);
            return Result.ok().data(data);
        }catch (DuplicateKeyException e) {
            data.put("error",e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 邮箱正确性检测
     * @param email 邮箱字符串
     * @return true/false
     */
    private boolean isEmail(String email) {
        if (email == null || email.length() == 0) {
            return false;
        }
        // 正则表达式验证邮箱
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        return pattern.matcher(email).matches();
    }

    private EmailUtil emailUtil = EmailUtil.instance;
    //private String vCode;  // 后台产生的验证码
    private Map<String, String> vCode=new ConcurrentHashMap<>();

    /**
     * 向邮箱发生验证码
     * @param userEmail
     * @return
     */
    @Override
    public  Result getVCode(String userEmail){
        Map<String, Object> data = new HashMap<>();

        if (!isEmail(userEmail)) {
            // 邮箱不正确
            data.put("error","邮箱格式错误");
            return new Result(false,20003,"验证码发送失败",data);
        }
        try {
            //生成验证码并发送验证码
            emailUtil.sendEmail(userEmail);
        } catch (Exception e) {
            data.put("error",e.getMessage());
            return new Result(false,20003,"未知错误,验证码发送失败",data);
        }
        //获取验证码
        String vCode_ = emailUtil.getVCode();

        if(vCode.containsKey(userEmail)){
            //删除该邮箱的旧验证码(如果存在的话)
            vCode.remove(userEmail);
        }
        //保存邮箱和对应的新验证码
        vCode.put(userEmail, vCode_);
        return Result.ok().data(data);
    }

    /**
     * 验证邮箱验证码
     * @param userEmail
     * @param code
     * @return
     */
    @Override
    public boolean verifyEmail(String userEmail, String code){
        if ((vCode.containsKey(userEmail) && vCode.get(userEmail).equals(code))) {
            // 验证成功后，删除验证码和定时任务
            vCode.remove(userEmail);
            return true;
        } else {
            return false;
        }
    }
}
