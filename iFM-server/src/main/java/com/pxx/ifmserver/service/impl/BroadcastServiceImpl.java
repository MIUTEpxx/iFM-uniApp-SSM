package com.pxx.ifmserver.service.impl;

import com.pxx.ifmserver.entity.dto.*;
import com.pxx.ifmserver.entity.vo.BroadcastItemVO;
import com.pxx.ifmserver.mapper.BroadcastMapper;
import com.pxx.ifmserver.mapper.ChannelMapper;
import com.pxx.ifmserver.mapper.UserMapper;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.BroadcastService;
import com.pxx.ifmserver.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class BroadcastServiceImpl implements BroadcastService {

    @Autowired
    private BroadcastMapper broadcastMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private UserMapper userMapper;

    //封面图片储存路径
    private static final String BROADCAST_PICTURE_PATH="/resources/images/broadcast/";
    //音频文件储存路径
    private static final String BROADCAST_AUDIO_PATH="/resources/audio/";
    // 热门排序权重定义
    private static final double WEIGHT_PLAY_COUNT = 0.5;
    private static final double WEIGHT_COLLECTION_COUNT = 0.3;
    private static final double WEIGHT_POST_COUNT = 0.2;
    /**
     * 根据节目id获取节目详细数据
     * @param broadcastId
     * @return
     */
    @Override
    public Result getBroadcastByBroadcastId(Integer broadcastId){
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("broadcast",broadcastMapper.getBroadcastByBroadcastId(broadcastId));
            return Result.ok().data(data);
        }catch (DuplicateKeyException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 获取频道ID为channelId的频道所有的节目
     * @param channelId
     * @return 节目简项数据列表
     */
    @Override
    public Result listBroadcastByChannelId(Integer channelId) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<Broadcast> broadcastList = broadcastMapper.listBroadcastByChannelId(channelId);
            //将节目信息整合到节目简项 并存入返回体的数据部分
            List<BroadcastItemVO> broadcastItemVOList = new ArrayList<>();
            for (Broadcast broadcast : broadcastList) {
                BroadcastItemVO broadcastItemVO = new BroadcastItemVO();
                Channel channel=channelMapper.getChannelByChannelId(broadcast.getChannelId());
                User user=userMapper.getUserByUserId(broadcast.getUserId());
                broadcastItemVO.setBroadcast(broadcast);
                broadcastItemVO.setChannelTitle(channel.getChannelTitle());
                broadcastItemVO.setUserName(user.getUserName());
                broadcastItemVOList.add(broadcastItemVO);
            }
            data.put("broadcastList",broadcastItemVOList);
            return Result.ok().data(data);
        }catch (DuplicateKeyException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 创建节目
     * @param channelId
     * @param userId
     * @param broadcastTitle
     * @param broadcastDetail
     * @param broadcastPicture
     * @return
     */
    @Override
    public Result createBroadcast(
            Integer channelId, Integer userId,
            String broadcastTitle, String broadcastDetail,
            MultipartFile broadcastPicture){
        Map<String, Object> data = new HashMap<>();
        if (broadcastPicture.isEmpty()) {
            data.put("error","封面图片文件为空");
            Result result = new Result(false,70000,"文件上传失败",data);
            return result;
        }else if(!userId.equals(channelMapper.getChannelByChannelId(channelId).getUserId())){
            //若频道作者ID不等于此用户ID,无权操作
            data.put("error","非频道创建者,无权在ID为 "+channelId+" 的频道下创建节目");
            return new Result(false,20002,"操作失败,无权操作",data);
        }

        Broadcast broadcast=new Broadcast();
        //节目封面图片文件名称
        String pictureFileName=null;
        try{
            //节目基本信息
            broadcast.setBroadcastTitle(broadcastTitle);
            broadcast.setBroadcastDetail(broadcastDetail);
            broadcast.setUserId(userId);
            broadcast.setChannelId(channelId);
            //储存节目数据记录到表中,并获得节目id(自动保存在broadcast.broadcastId中)
            broadcastMapper.insertBroadcast(broadcast);
            try {
                pictureFileName = FileUtils.savePicture(broadcast.getBroadcastId(), broadcastPicture,BROADCAST_PICTURE_PATH);

            } catch (IOException e) {
                //删除节目
                deleteBroadcast(userId, broadcast.getBroadcastId());
                data.put("error.message", e.getMessage());
                return new Result(false,20001,"未知错误,封面或音频文件上传失败",data);
            }
            //更新数据库中该节目的封面图片路径和音频文件路径,音频时长
            broadcastMapper.updateBroadcastPicurlByBroadcastId(broadcast.getBroadcastId(), "/images/broadcast/"+pictureFileName);
            //更新频道的上次更新时间
            channelMapper.updateChannelUpdateTimeByChannelId(channelId);
            data.put("broadcastId",broadcast.getBroadcastId());//返回节目ID
            return Result.ok().data(data);
        }catch (RuntimeException e){
            //创建失败,删除节目数据记录
/*            broadcastMapper.deleteBroadcast(broadcast.getBroadcastId());
            try {
                //删除音频文件和封面图片文件
                if(pictureFileName!=null){
                    FileUtils.deletePicture("/images/broadcast/"+pictureFileName);
                }
            }catch (IOException ee){
                data.put("error.message", ee.getMessage());
                data.put("channelId",broadcast.getBroadcastId());//返回节目ID
                return new Result(false,20001,"未知错误,封面或音频文件删除失败",data);
            }*/
            deleteBroadcast(userId,broadcast.getBroadcastId());
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 更改节目封面图片
     * @param userId
     * @param broadcastId
     * @param broadcastPicture
     * @return
     */
    @Override
    public Result updateBroadcastPicture(Integer userId, Integer broadcastId, MultipartFile broadcastPicture) {
        Map<String, Object> data = new HashMap<>();
        if (broadcastPicture.isEmpty()) {
            data.put("error","封面图片文件为空");
            Result result = new Result(false,70000,"文件上传失败",data);
            return result;
        }
        try{
            Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
            if(broadcast==null){
                data.put("error", "id为 "+broadcastId+" 的节目不存在");
                return new Result(false,90001,"修改失败,节目不存在",data);
            }
            if(!broadcast.getUserId().equals(userId)){
                //若节目作者ID不等于此用户ID,无权操作
                data.put("error","非节目创建者,无权修改ID为 "+broadcastId+" 的节目");
                return new Result(false,20002,"修改失败,无权操作",data);
            }
            //用于储存新封面图片文件名称
            String pictureFileName;
            //获取旧头像图片路径
            String oldPicturePath = broadcast.getBroadcastPicture();
            try {
                //储存新图片文件并获取文件名称
                pictureFileName = FileUtils.savePicture(broadcastId,broadcastPicture,BROADCAST_PICTURE_PATH);
                //删除旧图片
                FileUtils.deletePicture(oldPicturePath);
            } catch (IOException e) {
                data.put("error.message", e.getMessage());
                return new Result(false,20001,"未知错误,封面图片上传失败",data);
            }
            //更新数据库中该节目的封面图片路径数据
            broadcastMapper.updateBroadcastPicurlByBroadcastId(broadcastId, "/images/broadcast/"+pictureFileName);
            data.put("broadcastPicture","/images/broadcast/"+pictureFileName);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }
    /**
     * 修改节目音频
     * @param userId
     * @param broadcastId
     * @param broadcastAudio
     * @return
     */
    @Override
    public Result updateBroadcastAudio(Integer userId, Integer broadcastId, MultipartFile broadcastAudio) {
        Map<String, Object> data = new HashMap<>();
        try{
            Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
            if(broadcast==null){
                data.put("error", "id为 "+broadcastId+" 的节目不存在");
                return new Result(false,90001,"修改失败,节目不存在",data);
            }
            if(!broadcast.getUserId().equals(userId)){
                //若节目作者ID不等于此用户ID,无权操作
                data.put("error","非节目创建者,无权修改ID为 "+broadcastId+" 的节目");
                return new Result(false,20002,"修改失败,无权操作",data);
            }

            //用于储存新音频文件名称和音频时长(fileName,)
            Map<String, Object> audioFile;
            //获取旧音频文件路径
            String oldAudioPath = broadcast.getBroadcastAudio();
            try {
                //储存新图片文件并获取文件名称
                audioFile = FileUtils.saveAudio(broadcastId,broadcastAudio,BROADCAST_AUDIO_PATH);
                //删除旧音频文件
                FileUtils.deleteAudio(oldAudioPath);
            } catch (IOException e) {
                data.put("error.message", e.getMessage());
                return new Result(false,20001,"未知错误,音频文件修改失败",data);
            }
            //更新数据库中该节目的音频文件路径数据
            broadcastMapper.updateBroadcastAudioByBroadcastId(broadcastId, "/audio/"+audioFile.get("fileName"));
            //更新数据库中该界面音频时长(秒)
            broadcastMapper.updateBroadcastDurationByBroadcastId(broadcast.getBroadcastId(), (Long) audioFile.get("duration"));
            //向客户端返回相关数据
            data.put("broadcastAudio","/audio/"+audioFile.get("fileName"));
            data.put("broadcastDuraction",(Long) audioFile.get("duration"));
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,修改失败",data);
        }
    }
    /**
     *  修改节目标题
     * @param broadcastId
     * @param userId
     * @param broadcastTitle 节目详情
     * @return
     */
    @Override
    public Result updateBroadcastTitle(Integer userId, Integer broadcastId, String broadcastTitle) {
        Map<String, Object> data = new HashMap<>();
        try{
            Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
            if(broadcast==null){
                data.put("error", "id为 "+broadcastId+" 的节目不存在");
                return new Result(false,90001,"修改失败,节目不存在",data);
            }
            if(!broadcast.getUserId().equals(userId)){
                //若节目作者ID不等于此用户ID,无权操作
                data.put("error","非节目创建者,无权修改ID为 "+broadcastId+" 的节目");
                return new Result(false,20002,"修改失败,无权操作",data);
            }
            //更新数据库中节目的标题
            broadcastMapper.updateBroadcastTitleByChannelId(broadcastId,broadcastTitle);
            data.put("broadcastTitle",broadcastTitle);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 修改节目详情
     * @param broadcastId
     * @param userId
     * @param broadcastDetail 节目详情
     * @return
     */
    @Override
    public Result updateBroadcastDetail(Integer userId, Integer broadcastId, String broadcastDetail) {
        Map<String, Object> data = new HashMap<>();
        try{
            Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
            if(broadcast==null){
                data.put("error", "id为 "+broadcastId+" 的节目不存在");
                return new Result(false,90001,"修改失败,节目不存在",data);
            }
            if(!broadcast.getUserId().equals(userId)){
                //若节目作者ID不等于此用户ID,无权操作
                data.put("error","非节目创建者,无权修改ID为 "+broadcastId+" 的节目");
                return new Result(false,20002,"修改失败,无权操作",data);
            }
            broadcastMapper.updateBroadcastDetailByChannelId(broadcastId,broadcastDetail);
            data.put("broadcastDetail",broadcastDetail);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
        return Result.ok().data(data);
    }


    /**
     * 根据关键词搜索节目
     * @param keyWord
     * @return
     */
    @Override
    public Result searchBroadcast(String keyWord) {
        Map<String, Object> data = new HashMap<>();
        try{
            //根据关键词获取节目数据
            List<Broadcast> broadcastList = broadcastMapper.listBroadcastByKeyWord(keyWord);
            //将节目信息整合到节目简项 并存入返回体的数据部分
            List<BroadcastItemVO> broadcastItemVOList = new ArrayList<>();
            for (Broadcast broadcast : broadcastList) {
                BroadcastItemVO broadcastItemVO = new BroadcastItemVO();
                Channel channel=channelMapper.getChannelByChannelId(broadcast.getChannelId());
                User user=userMapper.getUserByUserId(broadcast.getUserId());
                broadcastItemVO.setBroadcast(broadcast);
                broadcastItemVO.setChannelTitle(channel.getChannelTitle());
                broadcastItemVO.setUserName(user.getUserName());
                broadcastItemVOList.add(broadcastItemVO);
            }
            data.put("broadcastList", broadcastItemVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 删除节目
     * @param userId
     * @param broadcastId
     * @return
     */
    @Override
    public Result deleteBroadcast(Integer userId, Integer broadcastId) {
        Map<String, Object> data = new HashMap<>();
        try{
           Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
            if(broadcast==null){
                data.put("error", "id为 "+broadcastId+" 的节目不存在");
                return new Result(false,90001,"删除失败,节目不存在",data);
            }
            if(!broadcast.getUserId().equals(userId)){
                //若节目作者ID不等于此用户ID,无权操作
                data.put("error","非节目创建者,无权修改ID为 "+broadcastId+" 的节目");
                return new Result(false,20002,"删除失败,无权操作",data);
            }
            //获取音频文件路径
            String oldAudioPath = broadcast.getBroadcastAudio();
            //获取封面图片文件路径
            String oldPicturePath= broadcast.getBroadcastPicture();
            //删除文件
            try {
                FileUtils.deleteAudio(oldAudioPath);
                FileUtils.deletePicture(oldPicturePath);
            } catch (IOException e) {
                data.put("error.message", e.getMessage());
                return new Result(false,20001,"未知错误,节目删除失败",data);
            }

            //删除节目记录
            broadcastMapper.deleteBroadcast(broadcastId);
            broadcastMapper.deleteBroadcastHistoryByBroadcastId(broadcastId);
            broadcastMapper.deleteBroadcastFavoriteByBroadcastId(broadcastId);
            //删除
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,节目删除失败",data);
        }
        return Result.ok();
    }


    /**
     * 计算热门分数
     * @param broadcast
     * @return
     */
    private double calculateHotScore(Broadcast broadcast) {
        return (broadcast.getBroadcastPlayCount() * WEIGHT_PLAY_COUNT) +
                (broadcast.getBroadcastCollectionCount() * WEIGHT_COLLECTION_COUNT) +
                (broadcast.getBroadcastPostCount() * WEIGHT_POST_COUNT);
    }
    /**
     * 获取热门节目
     * @return
     */
    @Override
    public Result listPopularBroadcast() {
        Map<String, Object> data = new HashMap<>();
        //单位 小时
        int hour = 72;
        //获取近期(一个月以内)更新的至少88个节目
        List<Broadcast> broadcastList=new ArrayList<>();
        while(broadcastList.size()<88 && hour<24*30){
            broadcastList=broadcastMapper.listBroadcastCtreateInHour(hour,89);
            hour+=6;
        }
        // 计算每个节目的热门分数并排序
        Collections.sort(broadcastList, new Comparator<Broadcast>() {
            @Override
            public int compare(Broadcast b1, Broadcast b2) {
                return Double.compare(calculateHotScore(b2), calculateHotScore(b1));
            }
        });
        // 获取前20个热门节目
        List<Broadcast> top20Broadcasts = broadcastList.subList(0, Math.min(20, broadcastList.size()));
        //将节目信息整合到节目简项 并存入返回体的数据部分
        List<BroadcastItemVO> broadcastItemVOList = new ArrayList<>();
        for (Broadcast broadcast : top20Broadcasts) {
            BroadcastItemVO broadcastItemVO = new BroadcastItemVO();
            Channel channel=channelMapper.getChannelByChannelId(broadcast.getChannelId());
            User user=userMapper.getUserByUserId(broadcast.getUserId());
            broadcastItemVO.setBroadcast(broadcast);
            broadcastItemVO.setChannelTitle(channel.getChannelTitle());
            broadcastItemVO.setUserName(user.getUserName());
            broadcastItemVOList.add(broadcastItemVO);
        }
        data.put("broadcastList",broadcastItemVOList);
        return Result.ok().data(data);
    }

    /**
     * 检查节目是否被用户收藏
     * @param userId
     * @param broadcastId
     * @return
     */
    @Override
    public Result checkBroadcast(Integer userId, Integer broadcastId) {
        Map<String, Object> data = new HashMap<>();
        try{
            //订阅信息储存于返回体
            data.put("favorite",broadcastMapper.checkBroadcastFavoriteById(userId,broadcastId));
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
        return Result.ok().data(data);
    }

    /**
     * 更新节目收听历史记录
     * @param userId
     * @param broadcastId
     * @param lastListenDuration 上次收听到的时间(秒)
     * @return
     */
    @Override
    public Result updateHistory(Integer userId, Integer broadcastId, Integer lastListenDuration) {
        Map<String, Object> data = new HashMap<>();
        try{
            //检查收听历史记录是否已存在
            if(broadcastMapper.checkBroadcastHistoryById(userId,broadcastId)){
                //存在则跟新
                broadcastMapper.updateBroadcastHistory(userId,broadcastId,lastListenDuration);
            }else{
                //否则添加收听历史记录
                broadcastMapper.insertBroadcastHistory(userId,broadcastId,lastListenDuration);
            }
            return Result.ok();
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }

    }


    /**
     * 用户收藏/取消收藏
     * @param userId
     * @param broadcastId
     * @return
     */
    @Override
    public Result changeFavorite(Integer userId, Integer broadcastId) {
        Map<String, Object> data = new HashMap<>();
        try{
            if(broadcastMapper.checkBroadcastFavoriteById(userId,broadcastId)){
                //取消收藏,返回收藏结果:false
                broadcastMapper.deleteBroadcastFavorite(userId,broadcastId);
                broadcastMapper.updateBroadcastCollectionCount(broadcastId,-1);
                data.put("favorite",false);
            }else{
                //收藏频道,返回收藏结果:true
                broadcastMapper.insertBroadcastFavorite(userId,broadcastId);
                broadcastMapper.updateBroadcastCollectionCount(broadcastId,1);
                data.put("favorite",true);
            }
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }


    /**
     * 增加播放量
     * @param broadcastId
     * @return
     */
    @Override
    public Result increasePlayCount(Integer broadcastId) {
        Map<String, Object> data = new HashMap<>();
        try{
            //播放量加一
            broadcastMapper.updateBroadcastPlayCount(broadcastId);
            return Result.ok();
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
    }

    /**
     * 删除节目收听历史记录
     * @param userId
     * @param broadcastId
     * @return
     */
    @Override
    public Result deleteBroadcastHistory(Integer userId, Integer broadcastId){
        Map<String, Object> data = new HashMap<>();
        try{
            if(!broadcastMapper.checkBroadcastHistoryById(userId,broadcastId)){
                data.put("error", "id为 "+broadcastId+" 的节目不在收听历史记录中");
                return new Result(false,90002,"历史记录删除失败",data);
            }
            //删除节目收听记录
            broadcastMapper.deleteBroadcastHistory(userId,broadcastId);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,历史记录删除失败",data);
        }
        return Result.ok();

    }

    /**
     * 获取用户收听历史表中的节目(附带上次收听时长)
     * @param userId
     * @return
     */
    @Override
    public Result listBroadcastHistory(Integer userId){
        Map<String, Object> data = new HashMap<>();
        try {
            //获取此用户节目历史收听记录数据
            List<BroadcastHistory> broadcastHistoryList=broadcastMapper.listHistoryBroadcastByUserId(userId);
            //整合节目数据,储存到返回体中
            List<BroadcastItemVO> broadcastItemVOList=new ArrayList<>();
            for(BroadcastHistory broadcastHistory:broadcastHistoryList){
                //整合节目基础数据
                Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastHistory.getBroadcastId());
                BroadcastItemVO broadcastItemVO=new BroadcastItemVO();
                broadcastItemVO.setBroadcast(broadcast);
                //获取上次收听到的上次
                broadcastItemVO.setLastListenDuration(broadcastHistory.getLastListenDuration());
                //获取上次收听的时间戳
                broadcastItemVO.setHitoryTime(broadcastHistory.getHistoryTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                broadcastItemVOList.add(broadcastItemVO);
            }
            data.put("broadcastList",broadcastItemVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,历史收听节目数据获取失败",data);
        }
    }

    /**
     * 查询用户是否有该节目收听历史记录,有则返回上次收听时长
     * @param userId
     * @param broadcastId
     * @return
     */
    @Override
    public Result getBroadcastHistory(Integer userId,Integer broadcastId){
        Map<String, Object> data = new HashMap<>();
        try {
            //获取此用户节目历史收听记录数据
            BroadcastHistory broadcastHistory = broadcastMapper.getHistoryBroadcastByUserId(userId,broadcastId);
            if(broadcastHistory==null){
                data.put("lastListenDuration",0);
            }else {
                data.put("lastListenDuration",broadcastHistory.getLastListenDuration());
            }
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,历史收听节目数据获取失败",data);
        }
    }

    /**
     * 获取用户收藏表中的节目
     * @param userId
     * @return
     */
    @Override
    public Result listFavoriteBroadcast(Integer userId){
        Map<String, Object> data = new HashMap<>();
        try {
            //获取此用户节目收藏表数据
            List<BroadcastFavorite> broadcastFavoriteList=broadcastMapper.listFavoriteBroadcastByUserId(userId);
            //整合节目数据,储存到返回体中
            List<BroadcastItemVO> broadcastItemVOList=new ArrayList<>();
            for(BroadcastFavorite broadcastFavorite:broadcastFavoriteList){
                //整合节目基础数据
                Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastFavorite.getBroadcastId());
                if(broadcast==null){continue;}
                Channel channel = channelMapper.getChannelByChannelId(broadcast.getChannelId());
                BroadcastItemVO broadcastItemVO=new BroadcastItemVO();
                broadcastItemVO.setBroadcast(broadcast);
                if(channel!=null){
                    broadcastItemVO.setChannelTitle(channel.getChannelTitle());
                } else {
                    continue;
                }
                //获取收藏时间戳
                broadcastItemVO.setFavoriteTime(broadcastFavorite.getGmtCreate().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                broadcastItemVOList.add(broadcastItemVO);
            }
            data.put("broadcastList",broadcastItemVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,历史收听节目数据获取失败",data);
        }
    }

    /**
     * 获取节目信息(包含作者名,用户上次收听到的时长)
     * @param userId
     * @param broadcastId
     * @return
     */
    @Override
    public Result getBroadcastAudioById(Integer userId, Integer broadcastId){
        Map<String, Object> data = new HashMap<>();
        try {
            //获取此用户节目历史收听记录数据
            BroadcastHistory broadcastHistory = broadcastMapper.getHistoryBroadcastByUserId(userId,broadcastId);
            BroadcastItemVO broadcastItemVO=new BroadcastItemVO();
            Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
            broadcastItemVO.setBroadcast(broadcast);
            if(broadcastHistory!=null){
                broadcastItemVO.setLastListenDuration(broadcastHistory.getLastListenDuration());
            }else{
                broadcastItemVO.setLastListenDuration(0);
            }
            data.put("broadcast",broadcastItemVO);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,节目音频数据获取失败",data);
        }
    }

    @Override
    public Result getBroadcastByIdList(List<Integer> broadcastIdList){
        Map<String, Object> data = new HashMap<>();
        try {
            List<BroadcastItemVO> broadcastItemVOList=new ArrayList<>();
            for(Integer broadcastId:broadcastIdList){
                Broadcast broadcast=broadcastMapper.getBroadcastByBroadcastId(broadcastId);
                BroadcastItemVO broadcastItemVO=new BroadcastItemVO();
                broadcastItemVO.setBroadcast(broadcast);
                broadcastItemVOList.add(broadcastItemVO);
            }
            data.put("broadcastList",broadcastItemVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,节目音频数据获取失败",data);
        }
    }

    /**
     * 为节目添加音频
     * @param userId
     * @param broadcastId
     * @param audioFile
     * @return
     */
    @Override
    public Result addAudioForBroadcast(Integer userId, Integer broadcastId,MultipartFile audioFile){
        Map<String, Object> data = new HashMap<>();
        //节目音频文件名称和时长(fileName,duration)
        Map<String, Object> audioFileInfo =null;
        try{
            if (audioFile.isEmpty()) {
                data.put("error","音频文件为空");
                Result result = new Result(false,70001,"文件上传失败",data);
                return result;
            }else if(!userId.equals(broadcastMapper.getBroadcastByBroadcastId(broadcastId).getUserId())){
                //若节目作者ID不等于此用户ID,无权操作
                data.put("error","非节目创建者,无权为ID为 "+broadcastId+" 的节目添加音频文件");
                return new Result(false,20002,"操作失败,无权操作",data);
            }

            try {
                audioFileInfo =FileUtils.saveAudio(broadcastId, audioFile,BROADCAST_AUDIO_PATH);
            } catch (IOException e) {
                //删除帖子
                deleteBroadcast(userId,broadcastId);
                data.put("error.message", e.getMessage());
                return new Result(false,20001,"未知错误,节目创建失败",data);
            }
            //更新数据库中该节目的封面图片路径和音频文件路径,音频时长
            broadcastMapper.updateBroadcastAudioByBroadcastId(broadcastId, "/audio/"+ audioFileInfo.get("fileName"));
            broadcastMapper.updateBroadcastDurationByBroadcastId(broadcastId, (Long) audioFileInfo.get("duration"));

            return Result.ok().data(data);
        }catch (RuntimeException e){
            //删除帖子
            deleteBroadcast(userId,broadcastId);
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,节目创建失败",data);
        }
    }
    @Override
    public  Result changeBroadcastPostCount(Integer id, int num){
        Map<String, Object> data = new HashMap<>();
        try {
            //更新关联帖子数量
            broadcastMapper.updateBroadcastPostCount(id,num);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误,节目帖子数量修改失败",data);
        }
    }
}
