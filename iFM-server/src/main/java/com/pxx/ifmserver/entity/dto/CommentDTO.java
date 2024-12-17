package com.pxx.ifmserver.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    //创建时间
    private LocalDateTime gmtCreate;
    //评论id
    private Integer       commentId;
    //评论所在帖子的帖子id
    private  Integer      postId;
    //评论详情内容
    private String        commentDetail;
    //评论的用户id
    private Integer       userId;
    //评论点赞数
    private Integer       commentLikeCount;
    //评论回复数
    private Integer       commentReplyCount;
}
