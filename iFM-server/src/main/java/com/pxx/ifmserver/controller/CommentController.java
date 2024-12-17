package com.pxx.ifmserver.controller;

import com.pxx.ifmserver.entity.dto.CommentDTO;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.CommentService;
import com.pxx.ifmserver.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 根据帖子id获取评论
     * @param postId
     * @return commentList
     */
    @GetMapping("/getCommentByPostId")
    public Result getCommentByPostId(@RequestParam Integer postId) {
        return commentService.listCommentByPostId(postId);
    }

    /**
     * 点赞/取消点赞评论,更改评论点赞量
     * @param commentId
     * @param value
     * @return
     */
    @PostMapping("/changeLike")
    public Result changeLike(@RequestParam Integer commentId, @RequestParam int value) {
        return commentService.changeLike(commentId, value);
    }

    /**
     * 用户在帖子下添加评论(非图片部分)
     * @param userId 评论的用户id
     * @param postId 被评论的帖子id
     * @param commentDetail 评论文本内容
     * @param req
     * @param resp
     * @return commentId
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/addComment")
    public Result addComment(
            @RequestParam Integer userId,
            @RequestParam Integer postId,
            @RequestParam String commentDetail,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        CommentDTO comment = new CommentDTO();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setCommentDetail(commentDetail);
        Result r = commentService.addComment(comment);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 为评论添加图片数据, 该接口配合 添加评论接口addComment使用, 以实现评论的完整创建
     * @param userId 用户id
     * @param commentId 帖子id
     * @param commentImage 图片文件
     * @return
     */
    @PostMapping("/addImageForComment")
    public Result addImageForComment(
            @RequestParam Integer userId,
            @RequestParam Integer commentId,
            @RequestParam MultipartFile commentImage) {
        return  commentService.addImageForComment(userId,commentId,commentImage);
    }

}
