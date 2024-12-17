package com.pxx.ifmserver.service.impl;

import com.pxx.ifmserver.entity.dto.Broadcast;
import com.pxx.ifmserver.entity.dto.Hashtag;
import com.pxx.ifmserver.entity.dto.Post;
import com.pxx.ifmserver.entity.dto.User;
import com.pxx.ifmserver.entity.vo.BroadcastItemVO;
import com.pxx.ifmserver.entity.vo.PostVO;
import com.pxx.ifmserver.mapper.HashtagMapper;
import com.pxx.ifmserver.mapper.PostMapper;
import com.pxx.ifmserver.mapper.UserMapper;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.BroadcastService;
import com.pxx.ifmserver.service.ChannelService;
import com.pxx.ifmserver.service.PostService;
import com.pxx.ifmserver.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    HashtagMapper hashtagMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ChannelService channelService;
    @Autowired
    BroadcastService broadcastService;

    //图片储存路径
    private static final String POST_IMAGE_PATH="/resources/images/post/";

    @Override
    public Result getPostByPostId(Integer postId) {
        Map<String, Object> data = new HashMap<>();
        try {
            //获取帖子基础数据
            Post post = postMapper.getPostById(postId);
            //获取帖子图片数据
            List<String> postImageList = postMapper.listImageByPostId(postId);
            //获取帖子主题标签数据
            List<Hashtag> postHashtagList = postMapper.listHashtagByPostId(postId);
            //获取帖子创作用户的数据
            User user=userMapper.getUserByUserId(post.getUserId());
            //视图层帖子实体类
            PostVO postVO = new PostVO();
            postVO.setPost(post);
            postVO.setUser(user);
            postVO.setPostImageList(postImageList);
            postVO.setPostHashtagList(postHashtagList);
            //将数据填入返回体
            data.put("post", postVO);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子数据获取失败",data);
        }
    }

    @Override
    public Result listPostByUserId(Integer userId) {
        Map<String, Object> data = new HashMap<>();
        try {
            //根据用户id获取帖子基础数据列表
            List<Post> postIdList = postMapper.listPostByUserId(userId);
            //视图层帖子实体类列表
            List<PostVO> postVOList = new ArrayList<>();
            //获取帖子创作用户的数据
            User user=userMapper.getUserByUserId(userId);
            for (Post post : postIdList) {
                //获取帖子图片数据
                List<String> postImageList = postMapper.listImageByPostId(post.getPostId());
                //获取帖子主题标签数据
                List<Hashtag> postHashtagList = postMapper.listHashtagByPostId(post.getPostId());
                //视图层帖子实体类
                PostVO postVO = new PostVO();
                postVO.setPost(post);
                postVO.setUser(user);
                postVO.setPostImageList(postImageList);
                postVO.setPostHashtagList(postHashtagList);
                //添加至视图层帖子实体类列表中
                postVOList.add(postVO);
            }
            //将数据填入返回体
            data.put("postList", postVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子数据获取失败",data);
        }
    }

    @Override
    public Result listPostByAssociationId(Integer postAssociation, Integer associationId) {
        Map<String, Object> data = new HashMap<>();
        try {
            //根据关联id获取帖子基础数据列表
            List<Post> postIdList = postMapper.listPostByAssociationId(postAssociation, associationId);
            //视图层帖子实体类列表
            List<PostVO> postVOList = new ArrayList<>();
            for (Post post : postIdList) {
                //获取帖子图片数据
                List<String> postImageList = postMapper.listImageByPostId(post.getPostId());
                //获取帖子主题标签数据
                List<Hashtag> postHashtagList = postMapper.listHashtagByPostId(post.getPostId());
                //获取帖子创作用户的数据
                User user=userMapper.getUserByUserId(post.getUserId());
                //视图层帖子实体类
                PostVO postVO = new PostVO();
                postVO.setPost(post);
                postVO.setUser(user);
                postVO.setPostImageList(postImageList);
                postVO.setPostHashtagList(postHashtagList);
                //添加至视图层帖子实体类列表中
                postVOList.add(postVO);
            }
            //将数据填入返回体
            data.put("postList", postVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子数据获取失败",data);
        }
    }

    @Override
    public Result listPostBySectionId(Integer sectionId) {
        Map<String, Object> data = new HashMap<>();
        try {
            //根据社区板块id获取帖子基础数据列表
            List<Post> postIdList = postMapper.listPostByPostSection(sectionId);
            //视图层帖子实体类列表
            List<PostVO> postVOList = new ArrayList<>();
            for (Post post : postIdList) {
                //获取帖子图片数据
                List<String> postImageList = postMapper.listImageByPostId(post.getPostId());
                //获取帖子主题标签数据
                List<Hashtag> postHashtagList = postMapper.listHashtagByPostId(post.getPostId());
                //获取帖子创作用户的数据
                User user=userMapper.getUserByUserId(post.getUserId());
                //视图层帖子实体类
                PostVO postVO = new PostVO();
                postVO.setPost(post);
                postVO.setUser(user);
                postVO.setPostImageList(postImageList);
                postVO.setPostHashtagList(postHashtagList);
                //添加至视图层帖子实体类列表中
                postVOList.add(postVO);
            }
            //将数据填入返回体
            data.put("postList", postVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子数据获取失败",data);
        }
    }

    /**
     * 热门帖子算法
     * @param postList  帖子列表
     * @param limit 要筛选帖子的数量
     * @return 热门帖子列表
     */
    private static List<Post> filterHotPosts(List<Post> postList, int limit) {
        // 根据热度得分排序
        Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return calculateScore(p2) - calculateScore(p1); // 降序排序
            }
        });

        // 返回前limit个帖子
        return postList.subList(0, Math.min(limit, postList.size()));
    }

    /**
     * 热门标准权重计算
     * @param post
     * @return
     */
    private static int calculateScore(Post post) {
        // 热度得分计算公式可以根据实际情况调整
        // 根据收藏数、点赞数和评论数的权重来计算得分
        int likeWeight = 1;
        int commentWeight = 2;
        int collectionWeight = 3;

        return (post.getPostCollectionCount() * collectionWeight) +
                (post.getPostLikeCount() * likeWeight) +
                (post.getPostCommentCount() * commentWeight);
    }

    @Override
    public Result listPopularPost() {
        Map<String, Object> data = new HashMap<>();
        try {
            //单位 小时
            int hour = 72;
            //获取近期(一星期以内)更新的至少88个帖子
            List<Post> postIdList = new ArrayList<>();
            while(postIdList.size()<88 && hour<24*7){
                postIdList = postMapper.listPostCtreateInHour(hour);
                hour+=6;
            }
            //根据热门算法筛选30个帖子数据
            postIdList=filterHotPosts(postIdList,30);

            //视图层帖子实体类列表
            List<PostVO> postVOList = new ArrayList<>();
            for (Post post : postIdList) {
                //获取帖子图片数据
                List<String> postImageList = postMapper.listImageByPostId(post.getPostId());
                //获取帖子主题标签数据
                List<Hashtag> postHashtagList = postMapper.listHashtagByPostId(post.getPostId());
                //获取帖子创作用户的数据
                User user=userMapper.getUserByUserId(post.getUserId());
                //视图层帖子实体类
                PostVO postVO = new PostVO();
                postVO.setPost(post);
                postVO.setUser(user);
                postVO.setPostImageList(postImageList);
                postVO.setPostHashtagList(postHashtagList);
                //添加至视图层帖子实体类列表中
                postVOList.add(postVO);
            }
            //将数据填入返回体
            data.put("postList", postVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子数据获取失败",data);
        }
    }

    @Override
    public Result checkCollection(Integer userId, Integer postId) {
        Map<String, Object> data = new HashMap<>();
        try{
            //订阅信息储存于返回体
            data.put("favorite",postMapper.checkPostFavoriteById(userId,postId));
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误",data);
        }
        return Result.ok().data(data);
    }

    @Override
    public Result listPostByKeyword(String keyword) {
        Map<String, Object> data = new HashMap<>();
        try {
            //根据关键词获取帖子
            List<Post> postIdList = postMapper.listPostByKeyWord(keyword);
            //视图层帖子实体类列表
            List<PostVO> postVOList = new ArrayList<>();
            for (Post post : postIdList) {
                //获取帖子图片数据
                List<String> postImageList = postMapper.listImageByPostId(post.getPostId());
                //获取帖子主题标签数据
                List<Hashtag> postHashtagList = postMapper.listHashtagByPostId(post.getPostId());
                //获取帖子创作用户的数据
                User user=userMapper.getUserByUserId(post.getUserId());
                //视图层帖子实体类
                PostVO postVO = new PostVO();
                postVO.setPost(post);
                postVO.setUser(user);
                postVO.setPostImageList(postImageList);
                postVO.setPostHashtagList(postHashtagList);
                //添加至视图层帖子实体类列表中
                postVOList.add(postVO);
            }
            //将数据填入返回体
            data.put("postList", postVOList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子数据获取失败",data);
        }
    }

    @Override
    public Result changeCollection(Integer userId, Integer postId) {
        Map<String, Object> data = new HashMap<>();
        try{
            if(postMapper.checkPostFavoriteById(userId,postId)){
                //已收藏帖子, 则取消收藏,返回收藏结果:false
                postMapper.deletePostFavoriteById(userId,postId);
                postMapper.updatePostCollectionCount(postId,-1);
                data.put("favorite",false);
            }else{
                //收藏频道,返回收藏结果:true
                postMapper.insertPostFavorite(userId,postId);
                postMapper.updatePostCollectionCount(postId,1);
                data.put("favorite",true);
            }
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子收藏失败",data);
        }
    }

    @Override
    public Result createPost(Post post,  List<Integer> hashtagIdList) {
        //返回体
        Map<String, Object> data = new HashMap<>();
        try{
            //保存帖子的基本信息, 并获得帖子id(自动赋值给post的postId)
            postMapper.insertPost(post);
            //保存帖子的主题标签数据记录
            for (Integer hashtagId : hashtagIdList) {
                Hashtag hashtag = hashtagMapper.getHashtagByHashId(hashtagId);
                postMapper.insertPostHashtag(post.getPostId(), hashtag.getHashtagId(),hashtag.getHashtagName());
            }
            //若有关联内容,更新其关联帖子数量
            if(post.getPostAssociation()==0) {
                channelService.changeChannelPostCount(post.getAssociationId(), 1);
            } else if (post.getPostAssociation()==1) {
                broadcastService.changeBroadcastPostCount(post.getAssociationId(), 1);
            }
            //返回帖子的id
            data.put("postId",post.getPostId());
            return Result.ok().data(data);
        }catch (RuntimeException e){
            String deleteError = deletePost(post.getPostId(),null);
            if(deleteError!=null){
                data.put("deleteError", deleteError);
            }
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子发布失败",data);
        }
    }

    @Override
    public Result addImageForPost(Integer postId, MultipartFile image){
        Map<String, Object> data = new HashMap<>();
        if (image.isEmpty()){
            return new Result(false,70000,"未知错误, 帖子图像上传失败",data);
        }
        //储存帖子图像相对路径
        String imageFilePath ="";
        try {
            //保存图像,并获取图像文件名称
            String imageFileName = FileUtils.savePicture(postId, image,POST_IMAGE_PATH);
            //获取图像路径
            imageFilePath = "/images/post/"+imageFileName;
        } catch (IOException e) {
            List<String> imageFilePathList = new ArrayList<>();
            imageFilePathList.add(imageFilePath);
            String deleteError = deletePost(postId, imageFilePathList);
            if(deleteError!=null){
                data.put("deleteError", deleteError);
            }
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子图像上传失败",data);
        }
        try{
            //保存帖子图片文件路径记录
            postMapper.insertPostImage(postId, imageFilePath);

            return Result.ok().data(data);
        }catch (RuntimeException e){
            List<String> imageFilePathList = new ArrayList<>();
            imageFilePathList.add(imageFilePath);
            String deleteError = deletePost(postId, imageFilePathList);
            if(deleteError!=null){
                data.put("deleteError", deleteError);
            }
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 帖子图像上传失败",data);
        }
    }

    /**
     * 删除帖子
     * @param postId
     * @param imageFilePathList
     * @return
     */
    public String deletePost(Integer postId, List<String> imageFilePathList) {
        //删除图像文件
        try {
            if(imageFilePathList!=null){
                for (String filePath : imageFilePathList) {
                FileUtils.deletePicture(filePath);
            }}
        } catch (IOException e) {
            return e.getMessage();
        }
        //删除帖子相关记录
        try{
            if(postId!=null){
                Post post = postMapper.getPostById(postId);
                //若有关联内容,更新其关联帖子数量
                if(post.getPostAssociation()==0) {
                    channelService.changeChannelPostCount(post.getAssociationId(), -1);
                } else if (post.getPostAssociation()==1) {
                    broadcastService.changeBroadcastPostCount(post.getAssociationId(), -1);
                }
                postMapper.deletePostById(postId);
                postMapper.deletePostHashtag(postId);
                postMapper.deletePostImage(postId);

            }
        }catch (RuntimeException e){
            return e.getMessage();
        }
        return null;
    }

    @Override
    public Result changeLike(Integer postId, int value) {
        Map<String, Object> data = new HashMap<>();
        try{
            //更新帖子点赞数
            postMapper.updatePostLikeCount(postId,value);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error.message", e.getMessage());
            return new Result(false,20001,"未知错误, 点赞失败",data);
        }
    }
    @Override
    public Result deletePostById(Integer userId, Integer postId){
        Map<String, Object> data = new HashMap<>();
        try{
            Post post = postMapper.getPostById(postId);
            if(!userId.equals(post.getUserId())){
                data.put("error","非帖子创作者, 无权删除此帖子");
                return new Result(false,20002,"删除失败",data);
            }
            //获取帖子图片路径
            List<String> imageFilePathList = postMapper.listImageByPostId(postId);
            //删除帖子和帖子的图片数据
            deletePost(postId,imageFilePathList);
            //删除评论和回复
            // 待实现......

            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误, 删除失败",data);
        }
    }
}
