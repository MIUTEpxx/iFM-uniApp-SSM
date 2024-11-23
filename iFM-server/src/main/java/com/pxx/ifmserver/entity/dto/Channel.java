package com.pxx.ifmserver.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Channel {
    private Integer         channelId;
    private String          channelTitle;
    private String          channelDetail;
    private Integer         channelSubscribe;
    private LocalDateTime   channelUpdateTime;
    private LocalDateTime   gmtCreate;
    private Integer         userId;
    private String          channelPicture;
}
