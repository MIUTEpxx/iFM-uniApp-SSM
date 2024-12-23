package com.pxx.ifmserver.service.impl;

import com.pxx.ifmserver.entity.dto.CommentDTO;
import com.pxx.ifmserver.entity.dto.User;
import com.pxx.ifmserver.entity.vo.CommentVO;
import com.pxx.ifmserver.mapper.CommentMapper;
import com.pxx.ifmserver.mapper.PostMapper;
import com.pxx.ifmserver.mapper.ReplyMapper;
import com.pxx.ifmserver.mapper.UserMapper;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.CommentService;
import com.pxx.ifmserver.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    ReplyMapper replyMapper;

    //评论图片储存路径
    private static final String COMMENT_IMAGE_PATH="/resources/images/comment/";

    @Override
    public Result getCommentByCommentId(Integer commentId) {
        Map<String, Object> data = new HashMap<>();
        try {
            //获取帖子基础数据
            CommentDTO comment = commentMapper.getCommentByCommentId(commentId);
            //获取帖子图片数据
            List<String> commentImageList = commentMapper.listImageByCommentId(commentId);
            //获取帖子创作用户的数据
            User user=userMapper.getUserByUserId(comment.getUserId());
            //视图层帖子实体类
            CommentVO commentVo = new CommentVO();
            commentVo.setComment(comment);
            commentVo.setUser(user);
            commentVo.setCommentImageList(commentImageList);
            //将数据填入返回体
            data.put("comment", commentVo);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 评论数据获取失败",data);
        }
    }

    @Override
    public Result listCommentByPostId(Integer postId) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<CommentVO> commentVOList = new ArrayList<>();
            //获取帖子下的所有评论
            List<CommentDTO> commentList = commentMapper.listCommentByPostId(postId);
            for (CommentDTO comment : commentList) {
                //获取帖子图片数据
                List<String> commentImageList = commentMapper.listImageByCommentId(comment.getCommentId());
                //获取帖子创作用户的数据
                User user=userMapper.getUserByUserId(comment.getUserId());
                //视图层帖子实体类
                CommentVO commentVO = new CommentVO();
                commentVO.setComment(comment);
                commentVO.setUser(user);
                commentVO.setCommentImageList(commentImageList);
                commentVOList.add(commentVO);
            }
            //将数据填入返回体
            data.put("commentList", commentVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 评论数据获取失败",data);
        }
    }

    @Override
    public Result addComment(CommentDTO comment) {
        Map<String, Object> data = new HashMap<>();
        try {
            commentMapper.insertComment(comment);
            //更新帖子的评论数
            postMapper.updatePostCommentCount(comment.getPostId(),1);
            data.put("commentId",comment.getCommentId() );
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 评论失败",data);
        }
    }

    @Override
    public Result addImageForComment(Integer userId,Integer commentId, MultipartFile image) {
        Map<String, Object> data = new HashMap<>();
        if (image.isEmpty()){
            data.put("error","图形文件为空");
            return new Result(false,70000," 图像上传失败",data);
        }

        try{
            CommentDTO comment = commentMapper.getCommentByCommentId(commentId);
            if(!comment.getUserId().equals(userId)){
                data.put("error","您非此评论的发布用户");
                return new Result(false,20002,"无权操作",data);
            }
            //储存帖子图像相对路径
            String imageFilePath ="";
            try {
                //保存图像,并获取图像文件名称
                String imageFileName = FileUtils.savePicture(commentId, image,COMMENT_IMAGE_PATH);
                //获取图像路径
                imageFilePath = "/images/comment/"+imageFileName;
            } catch (IOException e) {
                data.put("error.message", e.getMessage());
                return new Result(false,20001,"未知错误, 帖子图像上传失败",data);
            }

            //保存帖子图片文件路径记录
            commentMapper.insertCommentImage(commentId,imageFilePath);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 图像上传失败",data);
        }
    }

    @Override
    public Result changeLike(Integer commentId, int value) {
        Map<String, Object> data = new HashMap<>();
        try {
            commentMapper.updateCommentLikeCount(commentId,value);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 评论失败",data);
        }
    }

    @Override
    public Result deleteComment(Integer commentId) {
        Map<String, Object> data = new HashMap<>();
        try {
            //删除评论下所有回复
            replyMapper.deleteReplyByCommentId(commentId);
            //删除评论
            commentMapper.deleteCommentByPostId(commentId);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 评论失败",data);
        }
    }
}
