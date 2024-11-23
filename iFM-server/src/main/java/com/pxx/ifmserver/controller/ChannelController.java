package com.pxx.ifmserver.controller;


import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.ChannelService;
import com.pxx.ifmserver.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    /**
     * 根据频道id获取对应频道数据接口
     * @param channelId 频道id
     * @return 对应的频道数据
     */
    @GetMapping("/getChannelByChannelId")
    public Result getChannelByChannelId(@RequestParam Integer channelId) {
        return channelService.getChannelByChannelId(channelId);
    }
    /**
     * 根据用户ID获取用户创建的频道接口
     * @param userId 用户id
     * @return 对应的频道数据列表
     */
    @GetMapping("/getChannelByUserId")
    public Result getChannelByUserId(@RequestParam Integer userId) {
        return channelService.listChannelByUserId(userId);
    }

    /**
     * 检查频道是否被用户收藏接口
     * @param userId
     * @param channelId
     * @return
     */
    @GetMapping("/checkChannel")
    public Result checkChannel(@RequestParam Integer userId,@RequestParam Integer channelId) {
        return channelService.checkChannelSubscriptionById(userId, channelId);
    }

    /**
     * 关键词搜索频道接口
     * @param keyWord
     * @return
     */
    @GetMapping("/searchChannelByKeyWord")
    public Result searchChannelByKeyWord(@RequestParam String keyWord) {
        return channelService.searchChannel(keyWord);
    }

    /**
     * 获取热门频道接口
     * @return
     */
    @GetMapping("/getPopularChannel")
    public Result getPopularChannel() {
        return channelService.listPopularChannel();
    }

    /**
     * 获取用户订阅的频道数据列表接口
     * @param userId
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/getSubscribedChannel")
    public Result getSubscribedChannel(
            @RequestParam Integer userId,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 获取用户订阅的频道列表
        Result r= channelService.listSubscribedChannel(userId);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 订阅频道/取消订阅接口
     * @param userId
     * @param channelId
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/changeSubscribe")
    public Result changeSubscribe(
            @RequestParam Integer userId,
            @RequestParam Integer channelId,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 订阅频道/取消订阅(根据用户是否已订阅)
        Result r= channelService.changeSubscribe(userId,channelId);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 创建新频道的接口
     * @param userId 创建频道的用户账号
     * @param channelTitle 频道标题
     * @param channelDetail 频道详情内容
     * @param channelPicture 频道封面图片文件数据
     * @param hashtagIdList 频道主题标签ID列表
     * @return 新生成的频道ID/错误信息
     */
    @PostMapping("/createChannel") // 使用PostMapping注解表示这是一个处理POST请求的方法
    public Result createChannel(
            @RequestParam("userId") Integer userId,
            @RequestParam("channelTitle") String channelTitle,
            @RequestParam("channelDetail") String channelDetail,
            @RequestParam("channelPicture") MultipartFile channelPicture,
            @RequestParam("hashtagIdList") List<Integer> hashtagIdList,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
        Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 创建频道
        Result r= channelService.createChannel(userId, channelTitle, channelDetail, channelPicture, hashtagIdList);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 跟换频道ID对应频道的封面图片接口
     * @param userId 用户Id
     * @param channelId 频道Id
     * @param channelPicture 频道新封面图片
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateChannelPicture")
    Result updateChannelPicture(
            @RequestParam("userId") Integer userId,
            @RequestParam("channelId") Integer channelId,
            @RequestParam("channelPicture") MultipartFile channelPicture,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更换频道图片
        Result r= channelService.updateChannelPicture(userId,channelId, channelPicture);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 根据频道ID 修改频道详情接口
     * @param userId 用户Id
     * @param channelId 频道Id
     * @param channelDetail 频道详情内容
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateChannelDetail")
    Result updateChannelDetail(
            @RequestParam("userId") Integer userId,
            @RequestParam("channelId") Integer channelId,
            @RequestParam("channelDetail") String channelDetail,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更换频道图片
        Result r= channelService.updateChannelDetail(userId,channelId, channelDetail);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 根据频道ID 修改频道标题接口
     * @param userId 用户Id
     * @param channelId 频道Id
     * @param channelTitle 频道标题
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateChannelTitle")
    Result updateChannelTitle(
            @RequestParam("userId") Integer userId,
            @RequestParam("channelId") Integer channelId,
            @RequestParam("channelTitle") String channelTitle,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("erro","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更换频道图片
        Result r= channelService.updateChannelTitle(userId,channelId, channelTitle);
        r.getData().put("Token",newToken);
        return r;
    }

    /**
     * 根据频道ID 删除频道接口
     * @param userId 用户Id
     * @param channelId 频道Id
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @DeleteMapping("/deleteChannel/{userId}/{channelId}")
    Result deleteChannel(
            @PathVariable("userId") Integer userId,
            @PathVariable("channelId") Integer channelId,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp, userId);
        if (newToken == null) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Token安全令牌失效,请重新登录");
            return new Result(false, 80000, "处理失败", data);
        }
        // 删除频道
        Result r = channelService.deleteChannel(userId, channelId);
        r.getData().put("Token", newToken);
        return r;
    }
}
