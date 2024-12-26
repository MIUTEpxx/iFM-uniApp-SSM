package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Broadcast;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class BroadcastVO {
    private Integer       broadcastId;
    private LocalDateTime gmtCreate;
    private String        broadcastTitle;
    private String        broadcastDetail;
    private Integer       broadcastDuration;
    private String        broadcastPicture;
    private String        broadcastAudio;
    private Integer       channelId;
    private Integer       broadcastPlayCount;
    private Integer       userId;
    private Integer       broadcastCollectionCount;
    private Integer       broadcastPostCount;
    private String        channelTitle;


    public void setBroadcast(Broadcast broadcast) {
        this.broadcastId = broadcast.getBroadcastId();
        this.broadcastDuration = broadcast.getBroadcastDuration();
        this.broadcastPicture = broadcast.getBroadcastPicture();
        this.broadcastAudio = broadcast.getBroadcastAudio();
        this.broadcastPlayCount = broadcast.getBroadcastPlayCount();
        this.broadcastCollectionCount = broadcast.getBroadcastCollectionCount();
        this.broadcastPostCount = broadcast.getBroadcastPostCount();
        this.channelId = broadcast.getChannelId();
        this.broadcastTitle = broadcast.getBroadcastTitle();
        this.broadcastDetail = broadcast.getBroadcastDetail();
        this.gmtCreate = broadcast.getGmtCreate();
        this.userId = broadcast.getUserId();
    }
}
