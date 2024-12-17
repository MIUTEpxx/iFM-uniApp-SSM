package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReplyMapper {
    /**
     * 根据回复id获取回复内容
     * @param replyId
     * @return
     */
    @Select("SELECT * FROM reply WHERE  reply_id = #{replyId}")
    public Reply getReplyByReplyId(Integer replyId);

    /**
     * 根据评论id获取评论下的回复
     * @param postId
     * @return
     */
    @Select("SELECT * FROM reply WHERE  comment_id = #{commentId}")
    public List<Reply> listReplyByCommentId(Integer postId);


    /**
     * 添加回复
     * @param reply
     */
    @Insert("INSERT INTO reply(reply_detail,comment_id,user_id) VALUES (#{replyDetail},#{commentId},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "replyId")
    public int insertReply(Reply reply);


    /**
     * 跟新回复的点赞数据
     * @param replyId
     * @param num
     */
    @Update("UPDATE reply SET reply_like_count = reply_like_count + #{num} WHERE reply_id = #{replyId}")
    public int updateReplyLikeCount(Integer replyId, int num);
}
