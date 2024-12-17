package com.pxx.ifmserver.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {
    //创建时间
    private LocalDateTime gmtCreate;
    //回复id
    private Integer       replyId;
    //回复评论的评论id
    private Integer       commentId;
    //回复详情内容
    private String        replyDetail;
    //回复的用户id
    private Integer       userId;
    //回复点赞数
    private Integer       replyLikeCount;
}
