package com.pxx.ifmserver.service;

import com.pxx.ifmserver.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface BroadcastService {
    Result getBroadcastByBroadcastId(Integer broadcastId);
    Result listBroadcastByChannelId(Integer channelId);
    Result createBroadcast(Integer channelId, Integer userId, String broadcastTitle, String broadcastDetail, MultipartFile broadcastPicture,MultipartFile broadcastAudio);
    Result updateBroadcastPicture(Integer userId, Integer broadcastId, MultipartFile broadcastPicture);
    Result updateBroadcastAudio(Integer userId, Integer broadcastId, MultipartFile broadcastAudio);
    Result updateBroadcastTitle(Integer userId, Integer broadcastId, String broadcastTitle);
    Result updateBroadcastDetail(Integer userId, Integer broadcastId, String broadcastDetail);
    Result searchBroadcast(String keyword);
    Result deleteBroadcast(Integer userId, Integer broadcastId);
    Result listPopularBroadcast();
    Result checkBroadcast(Integer userId, Integer broadcastId);
    Result updateHistory(Integer userId, Integer broadcastId, Integer lastListenDuraction);
    Result changeCollection(Integer userId, Integer broadcastId);
    Result increasePlayCount(Integer broadcastId);
    Result deleteBroadcastHistory(Integer userId, Integer broadcastId);
    Result listBroadcastHistory(Integer userId);
    Result getBroadcastHistory(Integer userId,Integer broadcastId);
    Result listFavoriteBroadcast(Integer userId);
}
