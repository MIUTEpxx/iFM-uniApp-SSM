package com.pxx.ifmserver.controller;

import com.pxx.ifmserver.utils.EmailUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/email")
public class EmailController {

    private EmailUtil emailUtil = EmailUtil.instance;
    //private String vCode;  // 后台产生的验证码
    private Map<String, String> vCode=new ConcurrentHashMap<>();



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



