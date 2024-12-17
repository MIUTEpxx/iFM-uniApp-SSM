package com.pxx.ifmserver.service;

import com.pxx.ifmserver.entity.dto.Reply;
import com.pxx.ifmserver.result.Result;

public interface ReplyService {

    /**
     * 根据评论id获取评论下的所有回复
     * @param commentId
     * @return
     */
    Result listReplyByCommentId(Integer commentId);

    /**
     * 添加回复
     * @param reply
     * @return
     */
    Result addReply(Reply reply);

    /**
     * 点赞/取消点赞回复,更改回复点赞量
     * @param replyId
     * @param value
     * @return
     */
    Result changeLike(Integer replyId, int value);
}
