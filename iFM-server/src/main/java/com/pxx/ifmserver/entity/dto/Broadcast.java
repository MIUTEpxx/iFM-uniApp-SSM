package com.pxx.ifmserver.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Broadcast {
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
}
