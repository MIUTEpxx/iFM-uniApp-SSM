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
     * 检测邮箱格式是否正确,然后向该邮箱发送验证码
     * @param userEmail 邮箱字符串
     * @return 1/-1
     */
    @GetMapping("/getVCode")
    public Result getVCode(@RequestParam String userEmail) {
        return userService.getVCode(userEmail);
    }



    /**
     * 用户登录
     * @param userId 账号
     * @param userPassword 密码
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @PostMapping("/userLoginPassword")
    public Result loginPassword(@RequestParam("userId") Integer userId,
                        @RequestParam("userPassword") String  userPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.loginPassword(userId,userPassword);
    }

    /**
     * 用户登录
     * @param userEmail 账号
     * @param code 密码
     * @return 成功则返回完整的用户信息,失败则返回错误信息
     */
    @PostMapping("/userLoginEmail")
    public Result loginEmail(@RequestParam String userEmail,
                        @RequestParam String code ) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.loginEamil(userEmail,code);
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

    /**
     * 修改密码接口
     * @param userId
     * @param userEmail
     * @param userPassword
     * @param code
     * @param req
     * @param resp
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateUserPassword")
    public Result updateUserPassword(
            @RequestParam Integer  userId,
            @RequestParam String userEmail,
            @RequestParam String userPassword,
            @RequestParam String code,
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
        Result r = userService.updateUserPassword(userId,userEmail,userPassword,code);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 修改邮箱接口
     * @param userId
     * @param userEmail
     * @param userNewEmail
     * @param code1
     * @param code2
     * @param req
     * @param resp
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateUserEmail")
    public Result updateUserEmail(
            @RequestParam Integer  userId,
            @RequestParam String userEmail,
            @RequestParam String userNewEmail,
            @RequestParam String code1,
            @RequestParam String code2,
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
        Result r = userService.updateUserEmail(userId,userEmail,userNewEmail,code1,code2);
        r.getData().put("Token",newToken);
        return r;
    }



}
