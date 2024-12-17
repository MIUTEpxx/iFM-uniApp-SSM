package com.pxx.ifmserver.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    //创建时间
    private LocalDateTime gmtCreate;
    //帖子id
    private Integer       postId;
    //帖子标题
    private String        postTitle;
    //帖子详情内容
    private String        postDetail;
    //帖子关联 0为频道 1为节目
    private Integer       postAssociation;
    //关联的id,必须是存在的频道的id或节目id
    private Integer       associationId;
    //帖子所属板块 0:话题讨论 1:分享推荐 2:求助问答
    private Integer       postSection;
    //发帖的用户id
    private Integer       userId;
    //帖子收藏数
    private Integer       postCollectionCount;
    //帖子点赞数
    private Integer       postLikeCount;
    //帖子评论数
    private Integer       postCommentCount;
}
