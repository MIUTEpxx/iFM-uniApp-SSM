package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Broadcast;
import lombok.Data;

import java.time.LocalDateTime;

/*节目简项视图层实体类*/
@Data
public class BroadcastItemVO {
    private Integer       broadcastId;
    private String        broadcastTitle;
    private Integer       broadcastDuration;
    private String        broadcastPicture;
    private String        broadcastAudio;
    private Integer       broadcastPlayCount;
    private Integer       broadcastCollectionCount;
    private Integer       broadcastPostCount;
    private LocalDateTime broadcastCreateTime;
    private String        channelTitle;
    private String        userName;
    private Integer       lastListenDuration=-1;
    private LocalDateTime hitoryTime=null;
    private LocalDateTime favoriteTime=null;

    public void setBroadcast(Broadcast broadcast) {
        this.broadcastId = broadcast.getBroadcastId();
        this.broadcastTitle = broadcast.getBroadcastTitle();
        this.broadcastDuration = broadcast.getBroadcastDuration();
        this.broadcastPicture = broadcast.getBroadcastPicture();
        this.broadcastAudio = broadcast.getBroadcastAudio();
        this.broadcastPlayCount = broadcast.getBroadcastPlayCount();
        this.broadcastCollectionCount = broadcast.getBroadcastCollectionCount();
        this.broadcastPostCount = broadcast.getBroadcastPostCount();
        this.broadcastCreateTime = broadcast.getGmtCreate();
    }
}
