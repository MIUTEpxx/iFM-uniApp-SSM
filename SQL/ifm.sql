/*
 Navicat Premium Data Transfer

 Source Server         : pxx
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : ifm

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 20/11/2024 16:21:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for broadcast
-- ----------------------------
DROP TABLE IF EXISTS `broadcast`;
CREATE TABLE `broadcast`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `broadcast_id` bigint unsigned NOT NULL COMMENT '广播节目 id',
  `broadcast_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广播节目标题',
  `broadcast_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广播节目详情内容',
  `broadcast_duration` bigint unsigned NULL COMMENT '广播节目时长(单位 : 秒)',
  `broadcast_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广播节目封面图片路径',
  `broadcast_audio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广播音频文件路径',
  `channel_id` bigint unsigned NOT NULL COMMENT '广播节目所属频道',
  `broadcast_play_count` bigint unsigned NOT NULL COMMENT '广播节目播放量',
  PRIMARY KEY (`broadcast_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建时间, 上次更新时间, 表id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for broadcast_favorite
-- ----------------------------
DROP TABLE IF EXISTS `broadcast_favorite`;
CREATE TABLE `broadcast_favorite`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `broadcast_favorite_id` bigint unsigned NOT NULL COMMENT '节目收藏表id',
  `broadcast_id` bigint unsigned NOT NULL COMMENT '节目id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  PRIMARY KEY (`broadcast_favorite_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for broadcast_history
-- ----------------------------
DROP TABLE IF EXISTS `broadcast_history`;
CREATE TABLE `broadcast_history`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `broadcast_history_id` bigint unsigned NOT NULL COMMENT '收听历史表id',
  `broadcast_id` bigint unsigned NOT NULL COMMENT '节目id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `history_time` datetime(0) NOT NULL COMMENT '上次收听的时间',
  `last_listening_duration` bigint unsigned NOT NULL COMMENT '上次收听时长',
  PRIMARY KEY (`broadcast_history_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `channel_id` bigint unsigned NOT NULL COMMENT '频道 id',
  `channel_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '频道标题',
  `channel_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '频道详情内容',
  `channel_subscribe` bigint unsigned NOT NULL COMMENT '频道订阅数',
  `channel_update_time` datetime(0) NULL DEFAULT NULL COMMENT '频道上次更新节目的时间',
  `user_id` bigint unsigned NOT NULL COMMENT '频道作者的用户 id',
  `channel_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '频道封面图片路径',
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建时间, 上次更新时间,表id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_hashtag
-- ----------------------------
DROP TABLE IF EXISTS `channel_hashtag`;
CREATE TABLE `channel_hashtag`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `channel_hashtag_id` bigint unsigned NOT NULL COMMENT '表id',
  `channel_id` bigint unsigned NOT NULL COMMENT '频道id',
  `hashtag_id` bigint(0) NOT NULL COMMENT '主题标签id',
  `hashtag_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题标签名',
  PRIMARY KEY (`channel_hashtag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_subscription
-- ----------------------------
DROP TABLE IF EXISTS `channel_subscription`;
CREATE TABLE `channel_subscription`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `channel_subscription_id` bigint unsigned NOT NULL COMMENT '频道订阅表id',
  `channel_id` bigint(0) NOT NULL COMMENT '频道id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`channel_subscription_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论id',
  `comment_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论的文本内容',
  `post_id` bigint unsigned NOT NULL COMMENT '评论所在的帖子id',
  `user_id` bigint unsigned NOT NULL COMMENT '评论者的用户id',
  `comment_like` bigint unsigned NOT NULL COMMENT '评论的点赞数',
  `comment_reply` bigint unsigned NOT NULL COMMENT '评论回复数',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_picture
-- ----------------------------
DROP TABLE IF EXISTS `comment_picture`;
CREATE TABLE `comment_picture`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `comment_picture_id` bigint unsigned NOT NULL COMMENT '表id',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论id',
  `comment_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`comment_picture_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for hashtag
-- ----------------------------
DROP TABLE IF EXISTS `hashtag`;
CREATE TABLE `hashtag`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `hashtag_id` bigint unsigned NOT NULL COMMENT '主题标签id',
  `hashtag_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主题标签名',
  PRIMARY KEY (`hashtag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `post_id` bigint unsigned NOT NULL COMMENT '帖子id',
  `post_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '帖子标题',
  `post_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '帖子详情内容',
  `post_like` bigint unsigned NOT NULL COMMENT '帖子点赞数',
  `post_collection` bigint unsigned NOT NULL COMMENT '帖子收藏数',
  `post_comment` bigint unsigned NOT NULL COMMENT '帖子评论数',
  `post_association` tinyint unsigned NULL COMMENT '帖子关联 0为频道 1为节目',
  `association_id` bigint unsigned NULL COMMENT '关联的id,必须是存在的频道的id或节目id',
  `section_id` tinyint unsigned NOT NULL COMMENT '帖子所属板块 0:话题讨论 1:分享推荐 2:求助问答',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post_favorite
-- ----------------------------
DROP TABLE IF EXISTS `post_favorite`;
CREATE TABLE `post_favorite`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `post_favorite_id` bigint unsigned NOT NULL COMMENT '帖子收藏表id',
  `post_id` bigint unsigned NOT NULL COMMENT '帖子id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`post_favorite_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post_picture
-- ----------------------------
DROP TABLE IF EXISTS `post_picture`;
CREATE TABLE `post_picture`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `post_picture_id` bigint unsigned NOT NULL COMMENT '表id',
  `post_id` bigint unsigned NOT NULL COMMENT '帖子id',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`post_picture_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `reply_id` bigint unsigned NOT NULL COMMENT '回复评论id',
  `reply_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `reply_like` bigint unsigned NOT NULL COMMENT '回复的点赞数',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论id',
  `user_id` bigint unsigned NOT NULL COMMENT '回复者的用户id',
  PRIMARY KEY (`reply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `user_id` bigint unsigned NOT NULL COMMENT '用户账号(唯一)',
  `user_password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称(唯一)',
  `user_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像路径',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户邮箱(唯一)',
  `user_profile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '这个人很神秘,什么都没有写' COMMENT '个人简介',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT 'token令牌',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE COMMENT '用户账号唯一',
  UNIQUE INDEX `user_email`(`user_email`) USING BTREE COMMENT '用户邮箱唯一',
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT '用户昵称唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2024-11-14 18:36:24', '2024-11-20 10:56:58', 6, '123456789', 'pxx', '/images/user/head/6_1731587343.png', '250717452@qq.com', 'test111', '0');
INSERT INTO `user` VALUES ('2024-11-14 20:29:56', '2024-11-14 20:31:40', 7, '123456789', 'test', '/images/user/head/7_1731587500.png', '250717452test@qq.com', '这个人很神秘,什么都没有写', '0');

SET FOREIGN_KEY_CHECKS = 1;
