package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Broadcast;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
    private long          broadcastCreateTime;
    private String        channelTitle;
    private String        userName;
    private Integer       lastListenDuration=-1;
    private long          hitoryTime=-1;
    private long          favoriteTime=-1;

    public void setBroadcast(Broadcast broadcast) {
        this.broadcastId = broadcast.getBroadcastId();
        this.broadcastTitle = broadcast.getBroadcastTitle();
        this.broadcastDuration = broadcast.getBroadcastDuration();
        this.broadcastPicture = broadcast.getBroadcastPicture();
        this.broadcastAudio = broadcast.getBroadcastAudio();
        this.broadcastPlayCount = broadcast.getBroadcastPlayCount();
        this.broadcastCollectionCount = broadcast.getBroadcastCollectionCount();
        this.broadcastPostCount = broadcast.getBroadcastPostCount();
        this.broadcastCreateTime = broadcast.getGmtCreate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
