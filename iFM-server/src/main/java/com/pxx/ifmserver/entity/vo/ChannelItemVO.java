package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Channel;
import com.pxx.ifmserver.entity.dto.Hashtag;
import com.pxx.ifmserver.entity.dto.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/*频道简项视图层实体类*/
@Data
public class ChannelItemVO {
    private Integer         channelId;
    private String          channelTitle;
    private Integer         channelSubscribe;
    private Integer         channelPostCount;
    private LocalDateTime   channelUpdateTime;
    private LocalDateTime   channelCreateTime;
    private String          channelPicture;
    private List<Hashtag>   channelHashtag;
    private String          userName;

    public void setChannel(Channel channel){
        this.channelId = channel.getChannelId();
        this.channelTitle = channel.getChannelTitle();
        this.channelSubscribe = channel.getChannelSubscribe();
        this.channelUpdateTime = channel.getChannelUpdateTime();
        this.channelCreateTime = channel.getGmtCreate();
        this.channelPicture = channel.getChannelPicture();
        this.channelPostCount= channel.getChannelPostCount();
    }

}
