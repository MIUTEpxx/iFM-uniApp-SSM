package com.pxx.ifmserver.service;

import com.pxx.ifmserver.entity.dto.CommentDTO;
import com.pxx.ifmserver.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {

    /**
     * 根据评论id获取评论数据
     * @param commentId
     * @return comment
     */
    Result getCommentByCommentId(Integer commentId);

    /**
     * 根据帖子id获取帖子下的评论
     * @param postId
     * @return commentList
     */
    Result listCommentByPostId(Integer postId);

    /**
     * 创建评论
     * @param comment
     * @return commentId
     */
    Result addComment(CommentDTO comment);

    /**
     *
     * @param commentId
     * @param image
     * @return
     */
    Result addImageForComment(Integer userId,Integer commentId, MultipartFile image);

    /**
     * 点赞/取消点赞评论,更改评论点赞量
     * @param commentId
     * @param value
     * @return
     */
    Result changeLike(Integer commentId,int value);

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    Result deleteComment(Integer commentId);

}
