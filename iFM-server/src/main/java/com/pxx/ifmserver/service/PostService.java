package com.pxx.ifmserver.service;

import com.pxx.ifmserver.entity.dto.Post;
import com.pxx.ifmserver.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    /**
     * 根据帖子id获取帖子
     * @param postId
     * @return
     */
    Result getPostByPostId(Integer postId);

    /**
     * 根据用户id获取用户创建的帖子
     * @param userId
     * @return
     */
    Result listPostByUserId(Integer userId);

    /**
     * 根据关联节目id/频道id, 获取其关联的帖子
     * @param postAssociation
     * @param associationId
     * @return
     */
    Result listPostByAssociationId(Integer postAssociation,Integer associationId);

    /**
     * 根据板块id获取对应的帖子
     * @param sectionId
     * @return
     */
    Result listPostBySectionId(Integer sectionId);

    /**
     * 获取热门帖子
     * @return
     */
    Result listPopularPost();

    /**
     * 检查用户是否收藏了该帖子
     * @param userId 用户id
     * @param postId 帖子id
     * @return
     */
    Result checkCollection(Integer userId,Integer postId);

    /**
     * 根据关键词搜索帖子
     * @param keyword
     * @return
     */
    Result listPostByKeyword(String keyword);

    /**
     * 用户收藏/取消收藏 帖子 (具体取决于帖子是否处于用户收藏表)
     * @param userId
     * @param postId
     * @return
     */
    Result changeCollection(Integer userId,Integer postId);

    /**
     * 创建帖子
     * @param post 帖子基本信息(标题, 详情, 用户id)
     * @param hashtagList 帖子主题标签
     * @return
     */
    Result createPost(Post post,  List<Integer> hashtagList);

    Result addImageForPost(Integer postId, MultipartFile image);

    /**
     * 点赞/取消点赞帖子,更改帖子点赞量
     * @param postId
     * @param value 点赞(+1) 取消点赞(-1)
     * @return
     */
    Result changeLike(Integer postId, int value);

    /**
     * 删除帖子接口
     * @param userId
     * @param postId
     * @return
     */
    Result deletePostById(Integer userId, Integer postId);

    /**
     * 根据用户id获取其收藏的帖子
     * @param userId
     * @return
     */
    Result listFavoritePost(Integer userId);
}
