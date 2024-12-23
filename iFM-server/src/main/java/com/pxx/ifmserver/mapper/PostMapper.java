package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.Broadcast;
import com.pxx.ifmserver.entity.dto.Hashtag;
import com.pxx.ifmserver.entity.dto.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 获取所有帖子数据。
     * @return 返回一个包含所有节目数据的List<Broadcast>列表。
     */
    @Select("SELECT * FROM post")
    List<Post> listPost();

    /**
     * 根据帖子id获取id
     * @param postId
     * @return
     */
    @Select(("SELECT * FROM post WHERE post_id = #{postId}"))
    Post getPostById(Integer postId);

    /**
     * 根据用户id,获取该用户创建的帖子
     * @param userId
     * @return
     */
    @Select("SELECT * FROM post WHERE user_id = #{userId}")
    List<Post> listPostByUserId(Integer userId);

    /**
     * 根据板块(0:话题讨论 1:分享推荐 2:求助问答)获取帖子
     * @param postSection 板块(0:话题讨论 1:分享推荐 2:求助问答)
     * @return
     */
    @Select("SELECT * FROM post WHERE post_section =#{postSection} ")
    List<Post> listPostByPostSection(Integer postSection);

    /**
     * 根据关联内容id获取对应帖子
     * @param postAssociation 帖子关联 0为频道 1为节目
     * @param associationId 关联的id,必须是存在的频道的id或节目id
     * @return
     */
    @Select("SELECT * FROM post WHERE post_association=#{postAssociation} AND association_id =#{associationId} ")
    List<Post> listPostByAssociationId(Integer postAssociation ,Integer associationId);

    /**
     * 获取若干小时内创建的帖子 前num个帖子
     * @param hour 小时
     * @return 帖子列表
     */
    @Select("SELECT * FROM post WHERE gmt_create > (NOW() - INTERVAL #{hour} HOUR)  LIMIT #{num}")
    List<Post> listPostCtreateInHour(int hour, int num);

    /**
     * 检查用户是否收藏了帖子
     * @param userId 用户id
     * @param postId 帖子ID
     * @return 布尔值
     */
    @Select("SELECT EXISTS(" +
            "SELECT 1 FROM post_favorite WHERE user_id=#{userId} AND post_id=#{postId} " +
            ") AS has_favorite;")
    boolean checkPostFavoriteById( Integer userId,Integer postId);

    /**
     * 根据关键词获取帖子
     * @param keyword
     * @return 帖子列表
     */
    @Select("SELECT * FROM post WHERE post_title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR post_detail LIKE CONCAT('%', #{keyword}, '%')")
    List<Post> listPostByKeyWord(String keyword);

    /**
     * 获取帖子图片路径
     * @param postId
     * @return
     */
    @Select("SELECT image FROM post_image WHERE post_id = #{postId} ")
    List<String> listImageByPostId(Integer postId);

    /**
     * 获取帖子的主题标签
     * @param postId
     * @return
     */
    @Select("SELECT * FROM post_hashtag WHERE post_id = #{postId} ")
    List<Hashtag> listHashtagByPostId(Integer postId);

    /**
     * 获取用户收藏的帖子的id
     * @param userId
     * @return
     */
    @Select("SELECT post_id FROM post_favorite WHERE user_id = #{userId}")
    List<Integer> listFavoritePostIdByUserId(Integer userId);

    /**
     * 用户添加帖子收藏
     * @param userId 用户id
     * @param postId 帖子id
     * @return 改变的行数(期望为1)
     */
    @Insert({"INSERT INTO post_favorite(user_id,post_id) VALUES (#{userId},#{postId})"})
    int insertPostFavorite(Integer userId,Integer postId);

    /**
     * 创建帖子:添加帖子基本信息记录
     * @param post 帖子数据
     * @return
     */
    @Insert("INSERT INTO post(post_title, post_detail, post_section, post_association, association_id, user_id) " +
            "VALUES (#{postTitle},#{postDetail},#{postSection},#{postAssociation},#{associationId},#{userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    int insertPost(Post post);

    /**
     * 创建帖子:添加帖子图片路径记录
     * @param postId
     * @param image
     * @return
     */
    @Insert({"INSERT INTO post_image(post_id , image) VALUES (#{postId},#{image})"})
    int insertPostImage(Integer postId,String image);

    /**
     * 创建帖子:添加帖子主题标签记录
     * @param postId
     * @param hashtagId
     * @param hashtagName
     * @return
     */
    @Insert({"INSERT INTO post_hashtag(post_id , hashtag_id, hashtag_name) VALUES (#{postId},#{hashtagId}, #{hashtagName})"})
    int insertPostHashtag(Integer postId,Integer hashtagId,String hashtagName);

    /**
     * 更改帖子点赞量
     * @param postId 帖子id
     * @param value 值
     * @return 改变的行数(期望为1)
     */
    @Update("UPDATE post SET post_like_count = post_like_count + #{value} WHERE post_id = #{postId}")
    int updatePostLikeCount(Integer postId,int value);

    /**
     * 更改帖子的收藏量
     * @param postId
     * @param value
     * @return
     */
    @Update("UPDATE post SET post_collection_count = post_collection_count + #{value} WHERE post_id = #{postId}")
    int updatePostCollectionCount(Integer postId,int value);

    /**
     * 更新帖子评论数
     * @param postId
     * @param num
     * @return
     */
    @Update("UPDATE post SET post_comment_count = post_comment_count + #{num} WHERE post_id = #{postId}")
    int updatePostCommentCount(Integer postId,int num);

    /**
     * 删除帖子记录
     * @param postId
     * @return
     */
    @Delete("DELETE FROM post WHERE post_id=#{postId}")
    int deletePostById(Integer postId);

    /**
     * 删除帖子主题标签记录
     * @param postId
     * @return
     */
    @Delete("DELETE FROM post_hashtag WHERE post_id=#{postId}")
    int deletePostHashtag(Integer postId);

    /**
     * 删除帖子图像记录
     * @param postId
     * @return
     */
    @Delete("DELETE FROM post_image WHERE post_id=#{postId}")
    int deletePostImage(Integer postId);

    /**
     * 将用户对该节目的收藏记录从收藏表中删除
     * @param postId
     * @param userId
     * @return 改变的行数(期望为1)
     */
    @Delete("DELETE FROM post_favorite WHERE post_id=#{postId} AND user_id=#{userId}")
    int deletePostFavoriteById(Integer userId,Integer postId);
}
