package com.pxx.ifmserver.controller;

import com.pxx.ifmserver.entity.dto.Reply;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.ReplyService;
import com.pxx.ifmserver.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * 根据评论id获取评论下的所有回复
     * @param commentId 评论id
     * @return replyList
     */
    @GetMapping("/getReplyByCommentId")
    public Result getReplyByCommentId(@RequestParam Integer commentId) {
        return replyService.listReplyByCommentId(commentId);
    }

    /**
     * 添加回复
     * @param userId 用户id
     * @param commentId 回复的评论id
     * @param replyDetail 回复内容
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/addReply")
    public Result addReply(
            @RequestParam Integer userId,
            @RequestParam Integer commentId,
            @RequestParam String replyDetail,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        Reply reply = new Reply();
        reply.setUserId(userId);
        reply.setCommentId(commentId);
        reply.setReplyDetail(replyDetail);
        Result r = replyService.addReply(reply);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 点赞/取消点赞回复,更改回复点赞量
     * @param replyId 回复id
     * @param value  点赞(+1) 取消点赞(-1)
     * @return
     */
    @PostMapping("/changeLike")
    public Result changeLike(@RequestParam Integer replyId, @RequestParam int value) {
        return replyService.changeLike(replyId, value);
    }

}
