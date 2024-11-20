package com.pxx.ifmserver.controller;

import com.pxx.ifmserver.entity.dto.User;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.UserService;
import com.pxx.ifmserver.utils.EmailUtil;
import com.pxx.ifmserver.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    private EmailUtil emailUtil = EmailUtil.instance;
    //private String vCode;  // 后台产生的验证码
    private Map<String, String> vCode=new ConcurrentHashMap<>();

    @Autowired//注解用于自动装配UserService的实例，以便在控制器中使用它们的方法
    private UserService userService;

    /**
     * 根据用户ID获取用户信息。
     * @param userId 用户ID，作为查询条件。
     * @return 返回一个User对象，包含用户的所有基本信息 或 未找到提示。
     */
    @GetMapping("/getUserById")
    public Result getUserById(@RequestParam Integer userId) {
        return userService.getUserByUserId(userId);
    }

    /**
     * 用户登录
     * @param userId 账号
     * @param userPassword 密码
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @PostMapping("/login")
    public Result login(@RequestParam("userId") Integer userId,
                        @RequestParam("userPassword") String  userPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.login(userId,userPassword);
    }

    /**
     *创建新用户
     * @param user 待插入的用户数据
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @PostMapping("/creatNewUser")
    public Result insertUser(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.insertUser(user);
    }

    /**
     * 更新用户头像
     * @param file 头像图片文件
     * @param userId  用户ID
     * @return
     */
    @PostMapping("/updateUserPicture")
    public Result uploadUserPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer  userId,
            HttpServletRequest req,
            HttpServletResponse resp) throws ParseException, IOException, NoSuchAlgorithmException {

        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        Result r = userService.updateUserPicurlByUserId(userId,file);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 更新用户个性签名
     * @param userProfile 个性签名
     * @param userId  用户ID
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @PostMapping("/updateUserProfile")
    public Result updateUserProfile(
            @RequestParam("userProfile") String userProfile,
            @RequestParam("userId") Integer  userId,
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ParseException, IOException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        Result r = userService.updateUserProfileByUserId(userId,userProfile);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 更新用户昵称
     * @param userName 用户昵称
     * @param userId  用户ID
     * @return 成功则返回用户昵称和新Token,失败则返回错误信息
     */
    @PostMapping("/updateUserName")
    public Result updateUserName(
            @RequestParam("userName") String userName,
            @RequestParam("userId") Integer  userId,
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ParseException, IOException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        Result r = userService.updateUserNameByUserId(userId,userName);
        r.getData().put("Token",newToken);
        return r;
    }


    /*
     * 检测邮箱格式是否正确,然后向该邮箱发送验证码
     * @param 邮箱字符串
     * @return 1/-1
     */
    @GetMapping("/getVCode")
    public ResponseEntity<String> getVCode(@RequestParam String email) {
        if (!isEmail(email)) {  // 邮箱不正确
            return ResponseEntity.badRequest().body("-1");
        }
        try {
            emailUtil.sendEmail(email);//生成验证码并发送验证码
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("-1");
        }
        String vCode_ = emailUtil.getVCode();//获取验证码

        if(vCode.containsKey(email)){
            vCode.remove(email);//删除该邮箱的旧验证码(如果存在的话)
        }

        vCode.put(email, vCode_);//保存邮箱和对应的新验证码

        System.out.println("验证码为：" + vCode_);
        return ResponseEntity.ok("1");
    }

    /*
     * 验证码验证
     */
    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String email,@RequestParam String vCodeReceive) {
        if ((vCode.containsKey(email) && vCode.get(email).equals(vCodeReceive))) {
            // 验证成功后，删除验证码和定时任务
            vCode.remove(email);
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.badRequest().body("-1");
        }
    }

    /*
     * 邮箱正确性检测
     * @param 邮箱字符串
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

}
