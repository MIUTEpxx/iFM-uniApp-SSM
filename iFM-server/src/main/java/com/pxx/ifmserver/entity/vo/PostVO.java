package com.pxx.ifmserver.entity.vo;

import com.pxx.ifmserver.entity.dto.Hashtag;
import com.pxx.ifmserver.entity.dto.Post;
import com.pxx.ifmserver.entity.dto.User;
import lombok.Data;

import java.time.ZoneOffset;
import java.util.List;

@Data
public class PostVO {
    //创建时间(发帖时间)
    private long          postCreateTime;
    //帖子id
    private Integer       postId;
    //帖子标题
    private String        postTitle;
    //帖子详情内容
    private String        postDetail;
    //帖子关联 0为频道 1为节目
    private Integer       postAssociation;
    //关联的id,必须是存在的频道的id或节目id
    private Integer       associationId;
    //帖子所属板块 0:话题讨论 1:分享推荐 2:求助问答
    private Integer       postSection;
    //发帖的用户id
    private Integer       userId;
    //发帖用户昵称
    private String        userName;
    //发帖用户头像路径
    private String        userPicture;
    //帖子收藏数
    private Integer       postCollectionCount;
    //帖子点赞数
    private Integer       postLikeCount;
    //帖子评论数
    private Integer       postCommentCount;
    //帖子图片路径
    private List<String>  postImageList;
    //帖子主题标签
    private List<Hashtag> postHashtagList;

    public void setPost(Post post){
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postDetail = post.getPostDetail();
        this.postAssociation = post.getPostAssociation();
        this.associationId = post.getAssociationId();
        this.postSection = post.getPostSection();
        this.userId = post.getUserId();
        this.postCollectionCount = post.getPostCollectionCount();
        this.postLikeCount = post.getPostLikeCount();
        this.postCommentCount = post.getPostCommentCount();
        this.postCreateTime = post.getGmtCreate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
   public void setUser(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPicture = user.getUserPicture();
   }
}
