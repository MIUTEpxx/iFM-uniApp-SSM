package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Reply;
import com.pxx.ifmserver.entity.dto.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class ReplyVO {
    //创建时间
    private long          replyCreateTime;
    //回复id
    private Integer       replyId;
    //回复评论的评论id
    private Integer       commentId;
    //回复详情内容
    private String        replyDetail;
    //回复点赞数
    private Integer       replyLikeCount;
    //回复的用户id
    private Integer       userId;
    //发帖用户昵称
    private String        userName;
    //发帖用户头像路径
    private String        userPicture;

    public void setReply(Reply reply) {
        this.replyId = reply.getReplyId();
        this.commentId = reply.getCommentId();
        this.replyDetail = reply.getReplyDetail();
        this.replyLikeCount = reply.getReplyLikeCount();
        this.userId = reply.getUserId();
        this.replyCreateTime = reply.getGmtCreate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
    public void setUser(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPicture = user.getUserPicture();
    }
}
