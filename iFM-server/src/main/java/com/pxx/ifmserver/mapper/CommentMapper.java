package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.CommentDTO;
import org.apache.ibatis.annotations.*;

import javax.xml.stream.events.Comment;
import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 根据评论id获取评论内容
     * @param commentId
     * @return
     */
    @Select("SELECT * FROM comment WHERE  comment_id = #{commentId}")
    public CommentDTO getCommentByCommentId(Integer commentId);

    /**
     * 根据帖子id获取帖子下的评论
     * @param postId
     * @return
     */
    @Select("SELECT * FROM comment WHERE  post_id = #{postId}")
    public List<CommentDTO> listCommentByPostId(Integer postId);

    /**
     * 获取评论的图片
     * @param commentId
     * @return
     */
    @Select("SELECT comment_image FROM comment_image WHERE comment_id = #{commentId}")
    public List<String> listImageByCommentId (Integer commentId);

    /**
     * 创建评论(非图片部分)
     * @param comment
     */
    @Insert("INSERT INTO comment(comment_detail,post_id,user_id) VALUES (#{commentDetail},#{postId},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    public int insertComment(CommentDTO comment);

    /**
     * 为评论添加图片路径记录
     * @param commentId
     * @param commentImage 图片在服务器的相对路径
     */
    @Insert("INSERT INTO comment_image(comment_id,comment_image) VALUES (#{commentId},#{commentImage})")
    public int insertCommentImage(Integer commentId, String commentImage);

    /**
     * 跟新评论的点赞数据
     * @param commentId
     * @param num
     */
    @Update("UPDATE comment SET comment_like_count = comment_like_count + #{num} WHERE comment_id = #{commentId}")
    public int updateCommentLikeCount(Integer commentId, int num);

    /**
     * 更新评论的回复数
     * @param commentId
     * @param num
     */
    @Update("UPDATE comment SET comment_reply_count = comment_reply_count + #{num} WHERE comment_id = #{commentId}")
    public int updateCommentReplyCount(Integer commentId, int num);

    /**
     * 删除帖子下的所有评论
     * @param postId
     * @return
     */
    @Delete("DELETE FROM comment WHERE post_id = #{postId}")
    public int deleteCommentByPostId(Integer postId);

}
