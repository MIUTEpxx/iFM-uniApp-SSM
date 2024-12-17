package com.pxx.ifmserver.service.impl;

import com.pxx.ifmserver.entity.dto.*;
import com.pxx.ifmserver.entity.vo.ChannelItemVO;
import com.pxx.ifmserver.entity.vo.ReplyVO;
import com.pxx.ifmserver.mapper.CommentMapper;
import com.pxx.ifmserver.mapper.PostMapper;
import com.pxx.ifmserver.mapper.ReplyMapper;
import com.pxx.ifmserver.mapper.UserMapper;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostMapper postMapper;

    @Override
    public Result listReplyByCommentId(Integer commentId) {
        Map<String, Object> data = new HashMap<>();
        try{
            List<ReplyVO> replyVOList = new ArrayList<>();
            //获取评论下的回复数据
            List<Reply> replyList = replyMapper.listReplyByCommentId(commentId);
            for (Reply reply : replyList) {
                User usre = userMapper.getUserByUserId(reply.getUserId());
                ReplyVO replyVO = new ReplyVO();
                replyVO.setReply(reply);
                replyVO.setUser(usre);
                replyVOList.add(replyVO);
            }
            data.put("replyList", replyVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,回复数据获取失败",data);
        }
    }

    @Override
    public Result addReply(Reply reply) {
        Map<String, Object> data = new HashMap<>();
        try{
            replyMapper.insertReply(reply);
            commentMapper.updateCommentReplyCount(reply.getCommentId(),1);
            CommentDTO comment = commentMapper.getCommentByCommentId(reply.getCommentId());
            postMapper.updatePostCommentCount(comment.getPostId(),1);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,回复失败",data);
        }
    }

    @Override
    public Result changeLike(Integer replyId, int value) {
        Map<String, Object> data = new HashMap<>();
        try{
            replyMapper.updateReplyLikeCount(replyId,value);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,点赞失败",data);
        }
    }
}
