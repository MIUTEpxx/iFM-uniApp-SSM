package com.pxx.ifmserver.controller;


import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.BroadcastService;
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
@RequestMapping("/broadcast")
public class BroadcastController {
    @Autowired
    private BroadcastService broadcastService;

    /**
     * 根据节目id获取节目数据接口
     * @param broadcastId
     * @return
     */
    @GetMapping("/getBroadcastByBroadcastId")
    public Result getBroadcastByBroadcastId(@RequestParam Integer broadcastId) {
        return broadcastService.getBroadcastByBroadcastId(broadcastId);
    }

    /**
     * 根据频道ID获取频道下的节目接口
     * @param channelId
     * @return
     */
    @GetMapping("/getBroadcastByChannelId")
    public Result getBroadcastByChannelId(@RequestParam Integer channelId) {
        return broadcastService.listBroadcastByChannelId(channelId);
    }


    /**
     * 根据关键词模糊匹配标题搜索节目
     * @param keyword
     * @return
     */
    @GetMapping("/getBroadcastByKeyword")
    public Result getBroadcastByKeyword(@RequestParam String keyword) {
        return broadcastService.searchBroadcast(keyword);
    }

    /**
     * 获取热门节目接口
     * @return
     */
    @GetMapping("/getPopularBroadcast")
    public Result getPopularBroadcast() {
        return broadcastService.listPopularBroadcast();
    }

    /**
     * 检查用户是否已收藏该节目接口
     * @param userId
     * @param broadcastId
     * @return
     */
    @GetMapping("/checkBroadcast")
    public Result checkBroadcast(@RequestParam Integer userId,@RequestParam Integer broadcastId) {
        return broadcastService.checkBroadcast(userId, broadcastId);
    }

    /**
     * 获取用户收藏表中的节目 接口
     * @param userId
     * @return
     */
    @GetMapping("/getFavoriteBroadcast")
    public Result getCollection(@RequestParam Integer userId) {
        return broadcastService.listFavoriteBroadcast(userId);
    }

    /**
     * 获取用户收听历史表中的节目 接口
     * @param userId
     * @return
     */
    @GetMapping("/getHistory")
    public Result getHistory(@RequestParam Integer userId) {
        return broadcastService.listBroadcastHistory(userId);
    }

    /**
     * 检查节目是否在用户收听历史中,以决定起始播放时长 接口
     * @param userId
     * @param broadcastId
     * @return
     */
    @GetMapping("/checkHistory")
    public Result checkHistory(@RequestParam Integer userId,@RequestParam Integer broadcastId) {
        return broadcastService.getBroadcastHistory(userId,broadcastId);
    }

    /**
     * 获取节目音频信息(包括作者名,若为登录状态,则可获得上次播放到的时长数据)
     * @param userId
     * @param broadcastId
     * @return
     */
    @GetMapping("/getBroadcastAudio")
    public Result getBroadcastAudio(@RequestParam Integer userId,@RequestParam Integer broadcastId) {
        return broadcastService.getBroadcastAudioById(userId,broadcastId);
    }

    /**
     * 根据节目id数组,一次性请求复数的节目项信息
     * @param broadcastIdList
     * @return
     */
    @GetMapping("/getBroadcastByIdList")
    public Result getBroadcastByIdList(@RequestParam List<Integer> broadcastIdList) {
        return broadcastService.getBroadcastByIdList(broadcastIdList);
    }



    /**
     * 创建节目接口
     * @param channelId
     * @param userId
     * @param broadcastTitle
     * @param broadcastDetail
     * @param broadcastPicture
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/createBroadcast")
    public Result createBroadcast(
            @RequestParam  Integer channelId, @RequestParam Integer userId,
            @RequestParam String broadcastTitle, @RequestParam String broadcastDetail,
            @RequestParam MultipartFile broadcastPicture,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 节目创建
        Result r= broadcastService.createBroadcast(channelId, userId, broadcastTitle, broadcastDetail, broadcastPicture);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 为节目添加音频数据(配合创建节目接口使用)
     * @param userId
     * @param broadcastId
     * @param audioFile
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/addAudio")
    public Result addAudio(
            @RequestParam Integer userId,
            @RequestParam Integer broadcastId,
            @RequestParam MultipartFile audioFile,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 节目创建
        Result r= broadcastService.addAudioForBroadcast(userId, broadcastId, audioFile);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 更改节目封面
     * @param broadcastId
     * @param userId
     * @param broadcastPicture
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateBroadcastPicture")
    public Result updateBroadcastPicture(
            @RequestParam  Integer broadcastId, @RequestParam Integer userId,
            @RequestParam MultipartFile broadcastPicture,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更改界面封面
        Result r= broadcastService.updateBroadcastPicture(userId,broadcastId,broadcastPicture);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     *
     * 更改节目详情内容
     * @param broadcastId
     * @param userId
     * @param broadcastDetail
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateBroadcastDetail")
    public Result updateBroadcastDetail(
            @RequestParam  Integer broadcastId, @RequestParam Integer userId,
            @RequestParam String broadcastDetail,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更改节目详情内容
        Result r= broadcastService.updateBroadcastDetail(userId,broadcastId,broadcastDetail);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 更改节目标题
     * @param broadcastId
     * @param userId
     * @param broadcastTitle
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateBroadcastTitle")
    public Result updateBroadcastTitle(
            @RequestParam  Integer broadcastId, @RequestParam Integer userId,
            @RequestParam String broadcastTitle,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更改节目标题
        Result r=broadcastService.updateBroadcastTitle(userId,broadcastId,broadcastTitle);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 更改节目音频文件
     * @param broadcastId
     * @param userId
     * @param broadcastAudio
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateBroadcastAudio")
    public Result updateBroadcastAudio(
            @RequestParam  Integer broadcastId, @RequestParam Integer userId,
            @RequestParam MultipartFile broadcastAudio,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 更改节目音频文件
        Result r= broadcastService.updateBroadcastAudio(userId,broadcastId,broadcastAudio);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 用户收藏/取消收藏接口
     * @param userId
     * @param broadcastId
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("/changeFavorite")
    public Result changeFavorite(
            @RequestParam Integer userId,
            @RequestParam Integer broadcastId,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 用户收藏/取消收藏接口
        Result r= broadcastService.changeFavorite(userId,broadcastId);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 更新节目收听历史记录
     * @param userId
     * @param broadcastId
     * @param lastListenDuraction
     * @return
     */
    @PostMapping("/updateHistory")
    public Result updateHistory(
            @RequestParam Integer userId,
            @RequestParam Integer broadcastId,
            @RequestParam Integer lastListenDuraction){
        if(userId==-1){return Result.ok();}
        return broadcastService.updateHistory(userId,broadcastId,lastListenDuraction);
    }

    /**
     * 增加节目播放量接口
     * @param broadcastId
     * @return
     */
    @PostMapping("/increasePlayCount")
    public Result increasePlayCount(@RequestParam Integer broadcastId){
        return broadcastService
                .increasePlayCount(broadcastId);
    }

    /**
     * 用户删除自己创建的节目接口
     * @param userId
     * @param broadcastId
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @DeleteMapping("/deleteBroadcast")
    public Result deleteBroadcast(
            @RequestParam  Integer userId,
            @RequestParam  Integer broadcastId,
            HttpServletRequest req,
            HttpServletResponse resp)throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 用户删除节目
        Result r= broadcastService.deleteBroadcast(userId,broadcastId);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 删除节目收听历史记录接口
     * @param userId
     * @param broadcastId
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @DeleteMapping("/deleteHistory")
    public Result deleteHistory(
            @RequestParam  Integer userId,
            @RequestParam  Integer broadcastId,
            HttpServletRequest req,
            HttpServletResponse resp)throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        // 用户删除节目收听历史记录
        Result r= broadcastService.deleteBroadcastHistory(userId,broadcastId);
        r.getData().put("token",newToken);
        return r;
    }


}
