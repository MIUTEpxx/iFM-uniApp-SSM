package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Channel;
import com.pxx.ifmserver.entity.dto.Hashtag;
import com.pxx.ifmserver.entity.dto.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/*频道详情页视图层实体类*/
@Data
public class ChannelVO {
    private Integer         channelId;
    private String          channelTitle;
    private String          channelDetile;
    private Integer         channelSubscribe;
    private Integer         channelPostCount;
    private LocalDateTime   channelUpdateTime;
    private LocalDateTime   channelCreateTime;
    private String          channelPicture;
    private List<Hashtag>   channelHashtag;
    private Integer         userId;
    private String          userName;
    private String          userPicture;

    public void setChannel(Channel channel){
        this.channelId = channel.getChannelId();
        this.channelTitle = channel.getChannelTitle();
        this.channelDetile = channel.getChannelDetail();
        this.channelSubscribe = channel.getChannelSubscribe();
        this.channelUpdateTime = channel.getChannelUpdateTime();
        this.channelCreateTime = channel.getGmtCreate();
        this.userId = channel.getUserId();
        this.channelPicture = channel.getChannelPicture();
        this.channelPostCount= channel.getChannelPostCount();
    }

    public void setUser(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPicture = user.getUserPicture();
    }
}
