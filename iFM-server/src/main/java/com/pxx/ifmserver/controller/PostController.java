package com.pxx.ifmserver.controller;

import com.pxx.ifmserver.entity.dto.Hashtag;
import com.pxx.ifmserver.entity.dto.Post;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.PostService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;


    /**
     * 根据帖子id获取对应帖子数据
     * @param postId 帖子id
     * @return Post
     */
    @GetMapping("/getPostByPostId")
    public Result getPostByPostId( @RequestParam Integer postId) {
        return postService.getPostByPostId(postId);
    }

    /**
     * 根据用户id获取用户创建的帖子
     * @param userId 用户ID
     * @return postList
     */
    @GetMapping("/getPostByUserId")
    public Result getPostByUserId( @RequestParam Integer userId) {
        return postService.listPostByUserId(userId);
    }

    /**
     * 根据频道或节目ID获取对应关联的帖子
     * @param postAssociation 0为频道(默认) 1为节目
     * @param associationId 频道或节目ID
     * @return postList
     */
    @GetMapping("/getPostByAssociationId")
    public Result getPostByAssociationId( @RequestParam Integer postAssociation, @RequestParam Integer associationId) {
        return postService.listPostByAssociationId(postAssociation,associationId);
    }

    /**
     * 根据板块id获取对应帖子
     * @param sectionId 社区板块id(0:话题讨论 1:分享推荐  2:求助问答)
     * @return postList
     */
    @GetMapping("/getPostBySectionId")
    public Result getPostBySectionId( @RequestParam Integer sectionId) {
        return postService.listPostBySectionId(sectionId);
    }

    /**
     * 获取热门帖子
     * @return postList
     */
    @GetMapping("/getPopularPost")
    public Result getPopularPost() {
        return postService.listPopularPost();
    }

    /**
     * 检查帖子是否被用户收藏
     * @param userId 用户id
     * @param postId 帖子id
     * @return favorite (bool)
     */
    @GetMapping("/checkCollection")
    public Result checkCollection( @RequestParam Integer userId, @RequestParam Integer postId) {
        return postService.checkCollection(userId,postId);
    }

    /**
     * 根据关键词搜索帖子
     * @param keyword 关键词
     * @return postList
     */
    @GetMapping("/getPostByKeyword")
    public Result getPostByKeyword(String keyword) {
        return postService.listPostByKeyword(keyword);
    }

    /**
     * 用户收藏/取消收藏 帖子 (具体取决于帖子是否处于用户收藏表)
     * @param userId 用户ID
     * @param postId 帖子ID
     * @return
     */
    @PostMapping("/changeCollection")
    public Result changeCollection( @RequestParam Integer userId, @RequestParam Integer postId) {
            return postService.changeCollection(userId,postId);
    }

    /**
     * 创建帖子(仅帖子非图片信息部分)
     * @param userId 用户id
     * @param postTitle 帖子标题
     * @param postDetail 帖子详情
     * @param postSection 帖子所属板块 0:话题讨论 1:分享推荐 2:求助问答
     * @param postAssociation 帖子关联 0为频道 1为节目
     * @param associationId 关联的id,必须是存在的频道的id或节目id
     * @param postHashtagList 帖子主题标签
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/createPost")
    public Result createPost(
            @RequestParam  Integer userId, @RequestParam String postTitle,
            @RequestParam String postDetail, @RequestParam Integer postSection,
            @RequestParam Integer postAssociation,@RequestParam Integer associationId,
            @RequestParam List<Integer> postHashtagList,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        if(associationId==-1){associationId=null;}
        Post post=new Post();
        post.setUserId(userId);
        post.setPostTitle(postTitle);
        post.setPostDetail(postDetail);
        post.setPostSection(postSection);
        post.setPostAssociation(postAssociation);
        post.setAssociationId(associationId);
        // 节目创建
        Result r=postService.createPost(post,postHashtagList);
        r.getData().put("token",newToken);
        return r;
    }

    /**
     * 为帖子添加图片数据, 该接口配合 创建帖子接口createPost 使用, 以实现帖子的完整创建
     * @param postId 帖子id
     * @param postImage 图片文件
     * @return
     */
    @PostMapping("/addImageForPost")
    public Result addImageForPost(
            @RequestParam Integer userId,
            @RequestParam Integer postId,
            @RequestParam MultipartFile postImage){
        return postService.addImageForPost(postId,postImage);
    }

    /**
     * 点赞/取消点赞帖子,更改帖子点赞量
     * @param postId 帖子ID
     * @param value 点赞(+1) 取消点赞(-1)
     *
     * @return
     */
    @PostMapping("/changeLike")
    public Result changeLike(@RequestParam Integer postId,@RequestParam Integer value){
        return postService.changeLike(postId,value);
    }

    /**
     * 多图接收测试
     * @param files
     */
    @PostMapping("/test/upload")
    public void test( @RequestParam("files[]") List<MultipartFile> files){
        return;
    }

    /**
     * 删除帖子接口
     * @param userId 用户id
     * @param postId 帖子id
     * @return
     */
    @DeleteMapping("/deletePost")
    public Result deletePost(
     @RequestParam Integer userId,
     @RequestParam Integer postId,
     HttpServletRequest req,
     HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException{
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,80000,"处理失败",data);
        }
        //删除节目
        Result r=postService.deletePostById(userId,postId);
        r.getData().put("token",newToken);
        return r;
    }
}
