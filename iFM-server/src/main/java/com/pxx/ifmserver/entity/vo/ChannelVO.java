package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Channel;
import com.pxx.ifmserver.entity.dto.Hashtag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChannelVO {
    private Integer         channelId;
    private String          channelTitle;
    private String          channelDetile;
    private Integer         channelSubscribe;
    private LocalDateTime   channelUpdateTime;
    private LocalDateTime   channelCreateTime;
    private Integer         userId;
    private String          channelPicture;
    private List<Hashtag>   channelHashtag;

    public void setChannel(Channel channel){
        this.channelId = channel.getChannelId();
        this.channelTitle = channel.getChannelTitle();
        this.channelDetile = channel.getChannelDetail();
        this.channelSubscribe = channel.getChannelSubscribe();
        this.channelUpdateTime = channel.getChannelUpdateTime();
        this.channelCreateTime = channel.getGmtCreate();
        this.userId = channel.getUserId();
        this.channelPicture = channel.getChannelPicture();
    }
}
