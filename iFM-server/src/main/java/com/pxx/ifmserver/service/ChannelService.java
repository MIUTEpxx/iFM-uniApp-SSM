package com.pxx.ifmserver.service;


import com.pxx.ifmserver.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChannelService {
    Result getChannelByChannelId(Integer channelId);
    Result listChannelByUserId(Integer userId);
    Result createChannel(Integer userId,String channelTitle, String channelDetail, MultipartFile channelPicture,  List<Integer> hashtagIdList);
    Result updateChannelPicture(Integer userId,Integer channelId, MultipartFile channelPicture);
    Result updateChannelDetail(Integer userId,Integer channelId, String channelDetail);
    Result updateChannelTitle(Integer userId,Integer channelId, String channelTitle);
    Result deleteChannel(Integer userId,Integer channelId);
    Result listPopularChannel();
    Result checkChannelSubscriptionById(Integer userId,Integer channelId);
    Result changeSubscribe(Integer userId, Integer channelId);
    Result listSubscribedChannel(Integer userId);
    Result searchChannel(String keyWord);
    Result listHashtag();
    Result listChannelIdByHashtag(Integer hashtagId);
    Result changeChannelPostCount (Integer id, int num);
}
