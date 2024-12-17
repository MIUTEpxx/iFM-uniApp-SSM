package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.CommentDTO;
import com.pxx.ifmserver.entity.dto.User;
import lombok.Data;

import java.time.ZoneOffset;
import java.util.List;

@Data
public class CommentVO {
    //创建时间戳
    private long          commentCreateTime;
    //评论id
    private Integer       commentId;
    //评论所在帖子的帖子id
    private  Integer      postId;
    //评论详情内容
    private String        commentDetail;
    //评论的用户id
    private Integer       userId;
    //评论用户昵称
    private String        userName;
    //评论用户头像路径
    private String        userPicture;
    //评论图片路径
    private List<String>  commentImageList;
    //评论点赞数
    private Integer       commentLikeCount;
    //评论回复数
    private Integer       commentReplyCount;

    public void setComment(CommentDTO commentDTO) {
        this.commentId = commentDTO.getCommentId();
        this.postId = commentDTO.getPostId();
        this.commentDetail = commentDTO.getCommentDetail();
        this.userId = commentDTO.getUserId();
        this.commentLikeCount = commentDTO.getCommentLikeCount();
        this.commentReplyCount = commentDTO.getCommentReplyCount();
        this.commentCreateTime = commentDTO.getGmtCreate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public void setUser(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPicture = user.getUserPicture();
    }
}
