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

 Date: 17/12/2024 16:52:53
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
  `channel_id` bigint unsigned NOT NULL COMMENT '广播节目所属频道',
  `user_id` bigint unsigned NOT NULL COMMENT '作者的用户id',
  `broadcast_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广播节目标题',
  `broadcast_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '广播节目详情内容',
  `broadcast_duration` bigint unsigned NULL COMMENT '广播节目时长(单位 : 秒)',
  `broadcast_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/images/broadcast/D.png' COMMENT '广播节目封面图片路径',
  `broadcast_audio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/audio/D.mp3' COMMENT '广播音频文件路径',
  `broadcast_play_count` bigint unsigned NOT NULL COMMENT '广播节目播放量',
  `broadcast_collection_count` bigint unsigned NOT NULL COMMENT '广播收藏量',
  `broadcast_post_count` bigint unsigned NOT NULL COMMENT '广播关联帖子数',
  PRIMARY KEY (`broadcast_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建时间, 上次更新时间, 表id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast
-- ----------------------------
INSERT INTO `broadcast` VALUES ('2024-12-31 11:40:48', '2024-12-16 11:26:57', 17, 13, 7, '修改节目标题测试1', '修改节目详情测试1', 257, '/images/broadcast/17_1732592448.png', '/audio/17_1732603964.mp3', 1, 1, 0);
INSERT INTO `broadcast` VALUES ('2024-12-31 19:29:11', '2024-12-10 15:23:24', 21, 13, 7, 'test', 'test', 257, '/images/broadcast/D.png', '/audio/D.mp3', 0, 1, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast_favorite
-- ----------------------------
INSERT INTO `broadcast_favorite` VALUES ('2024-11-26 14:57:17', '2024-11-26 14:57:17', 2, 17, 7);
INSERT INTO `broadcast_favorite` VALUES ('2024-12-04 10:26:00', '2024-12-04 10:26:00', 12, 21, 7);
INSERT INTO `broadcast_favorite` VALUES ('2024-12-09 10:05:59', '2024-12-09 10:05:59', 14, 22, 7);

-- ----------------------------
-- Table structure for broadcast_history
-- ----------------------------
DROP TABLE IF EXISTS `broadcast_history`;
CREATE TABLE `broadcast_history`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `broadcast_history_id` bigint unsigned NOT NULL COMMENT '收听历史表id',
  `broadcast_id` bigint unsigned NOT NULL COMMENT '节目id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `history_time` datetime(0) NOT NULL COMMENT '上次收听的时间',
  `last_listen_duration` bigint unsigned NOT NULL COMMENT '上次收听时长(单位:秒)',
  PRIMARY KEY (`broadcast_history_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast_history
-- ----------------------------
INSERT INTO `broadcast_history` VALUES ('2024-12-07 23:29:13', '2024-12-07 23:30:05', 3, 11, 7, '2024-12-07 23:30:05', 111);
INSERT INTO `broadcast_history` VALUES ('2024-12-11 21:32:14', '2024-12-11 21:37:31', 4, 22, 7, '2024-12-11 21:37:31', 191);
INSERT INTO `broadcast_history` VALUES ('2024-12-11 21:36:24', '2024-12-11 21:37:40', 5, 21, 7, '2024-12-11 21:37:40', 1);
INSERT INTO `broadcast_history` VALUES ('2024-12-15 21:27:34', '2024-12-15 21:27:41', 6, 24, 7, '2024-12-15 21:27:41', 7);

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `channel_id` bigint unsigned NOT NULL COMMENT '频道 id',
  `channel_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '默认标题' COMMENT '频道标题',
  `channel_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '频道详情内容',
  `channel_subscribe` bigint unsigned NULL COMMENT '频道订阅数',
  `channel_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '频道上次更新节目的时间',
  `user_id` bigint unsigned NOT NULL COMMENT '频道作者的用户 id',
  `channel_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '/images/channel/D.png' COMMENT '频道封面图片路径',
  `channel_post_count` bigint unsigned NOT NULL COMMENT '频道关联帖子数',
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建时间, 上次更新时间,表id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('2024-11-22 18:57:35', '2024-12-16 11:15:37', 13, '测试频道标题的修改', '测试频道详情内容的修改', 3344569, '2024-12-15 21:27:17', 7, '/images/channel/13_1732280918.png', 1);
INSERT INTO `channel` VALUES ('2024-11-22 19:04:18', '2024-12-10 15:23:39', 15, '频道创建测试', '频道创建测试详情', 16, '2024-12-31 15:50:44', 7, '/images/channel/15_1732273458.png', 0);
INSERT INTO `channel` VALUES ('2024-11-22 19:04:18', '2024-12-14 20:30:53', 16, '频道标题搜索测试#PDBT', '频道详情搜索测试#PDXQ', 29, '2024-12-31 15:50:49', 7, '/images/channel/16_1732285296.png', 0);
INSERT INTO `channel` VALUES ('2024-11-22 19:04:18', '2024-12-14 20:30:53', 17, '频道标题搜索测试#PDBT1', '频道详情搜索测试#PDXQ2', 25, '2024-12-31 15:50:49', 7, '/images/channel/15_1732273458.png', 0);
INSERT INTO `channel` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 19, '下北泽往事~回到二十世纪八十年代的黄金岁月 (迫真)', '下北泽俗称“下北”（Shimokita）规模不大但深受年轻人喜爱。街上有很多时装店、二手衣店及日用品店，是很适合散步逛街的地方。有很多值得搜寻一番新潮又有个性的商品。这里不乏优雅的咖啡厅和酒吧，又有很多老字号日式酒馆和大众食堂等老店，酝酿出一股独特的街景氛围。此外，“本多剧场”、“站前剧场”等小剧场、“短片影院Tollywood”等小剧院，以及“Shelter”、“下北泽屋根里”等Live House，为数众多的展演空间，使此地因“戏剧之城”、“音乐之城”的号称而闻名。其中，下北泽还以“亚文化天堂”为人熟知，使得很多年轻人慕名而来', 0, '2024-12-13 13:41:34', 7, '/images/channel/19_1734068494.png', 0);

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
  PRIMARY KEY (`channel_hashtag_id`) USING BTREE,
  UNIQUE INDEX `channel_hashtag_id`(`channel_id`, `hashtag_id`) USING BTREE COMMENT '频道id对应的主题标签id不可重复'
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel_hashtag
-- ----------------------------
INSERT INTO `channel_hashtag` VALUES ('2024-11-22 18:57:36', '2024-11-22 18:57:36', 3, 13, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-11-22 19:04:18', '2024-11-22 19:04:18', 5, 15, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-11-23 15:51:12', '2024-11-23 15:51:12', 9, 16, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-11-23 15:51:29', '2024-11-23 15:51:29', 10, 16, 11, '新闻');
INSERT INTO `channel_hashtag` VALUES ('2024-11-23 15:51:42', '2024-11-23 15:51:46', 11, 16, 12, '商业');
INSERT INTO `channel_hashtag` VALUES ('2024-11-23 15:51:29', '2024-11-23 15:51:29', 12, 17, 11, '新闻');
INSERT INTO `channel_hashtag` VALUES ('2024-11-29 16:27:20', '2024-11-29 16:27:20', 13, 17, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 15, 19, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 16, 19, 17, '影视娱乐');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 17, 19, 19, '故事奇谈');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 18, 19, 21, '闲谈吐槽');

-- ----------------------------
-- Table structure for channel_hashtag_copy1
-- ----------------------------
DROP TABLE IF EXISTS `channel_hashtag_copy1`;
CREATE TABLE `channel_hashtag_copy1`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `channel_hashtag_id` bigint unsigned NOT NULL COMMENT '表id',
  `channel_id` bigint unsigned NOT NULL COMMENT '频道id',
  `hashtag_id` bigint(0) NOT NULL COMMENT '主题标签id',
  `hashtag_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题标签名',
  PRIMARY KEY (`channel_hashtag_id`) USING BTREE,
  UNIQUE INDEX `channel_hashtag_id`(`channel_id`, `hashtag_id`) USING BTREE COMMENT '频道id对应的主题标签id不可重复'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_subscription
-- ----------------------------
DROP TABLE IF EXISTS `channel_subscription`;
CREATE TABLE `channel_subscription`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `channel_subscription_id` bigint unsigned NOT NULL COMMENT '频道订阅表id',
  `channel_id` bigint unsigned NOT NULL COMMENT '频道id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  PRIMARY KEY (`channel_subscription_id`) USING BTREE,
  UNIQUE INDEX `channel_user_subscription_id`(`channel_id`, `user_id`) USING BTREE COMMENT '订阅表中,频道id+用户id代表此频道被用户订阅,显然两个id的组合应该是唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel_subscription
-- ----------------------------
INSERT INTO `channel_subscription` VALUES ('2024-12-02 19:41:27', '2024-12-02 19:41:27', 12, 15, 7);
INSERT INTO `channel_subscription` VALUES ('2024-12-16 11:15:37', '2024-12-16 11:15:37', 16, 13, 7);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论id',
  `comment_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论的文本内容',
  `post_id` bigint unsigned NOT NULL COMMENT '评论所在的帖子id',
  `user_id` bigint unsigned NOT NULL COMMENT '评论者的用户id',
  `comment_like_count` bigint unsigned NOT NULL COMMENT '评论的点赞数',
  `comment_reply_count` bigint unsigned NOT NULL COMMENT '评论回复数',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('2024-12-16 19:59:06', '2024-12-17 15:15:34', 8, '123', 42, 7, 2, 2);
INSERT INTO `comment` VALUES ('2024-12-16 21:00:06', '2024-12-16 21:00:06', 9, '123123', 42, 7, 0, 0);
INSERT INTO `comment` VALUES ('2024-12-16 22:17:40', '2024-12-16 22:17:40', 10, '美图分享', 42, 7, 0, 0);

-- ----------------------------
-- Table structure for comment_image
-- ----------------------------
DROP TABLE IF EXISTS `comment_image`;
CREATE TABLE `comment_image`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `comment_picture_id` bigint unsigned NOT NULL COMMENT '表id',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论id',
  `comment_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`comment_picture_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_image
-- ----------------------------
INSERT INTO `comment_image` VALUES ('2024-12-16 19:59:08', '2024-12-16 20:58:05', 4, 8, '/images/comment/8_17343503486.png');
INSERT INTO `comment_image` VALUES ('2024-12-16 22:17:40', '2024-12-16 22:17:40', 5, 10, '/images/comment/10_17343586605.png');
INSERT INTO `comment_image` VALUES ('2024-12-16 22:17:41', '2024-12-16 22:17:41', 6, 10, '/images/comment/10_17343586611.png');
INSERT INTO `comment_image` VALUES ('2024-12-16 22:17:41', '2024-12-16 22:17:41', 7, 10, '/images/comment/10_17343586617.png');
INSERT INTO `comment_image` VALUES ('2024-12-16 22:17:42', '2024-12-16 22:17:42', 8, 10, '/images/comment/10_17343586622.png');
INSERT INTO `comment_image` VALUES ('2024-12-16 22:17:42', '2024-12-16 22:17:42', 9, 10, '/images/comment/10_17343586628.png');
INSERT INTO `comment_image` VALUES ('2024-12-16 22:17:43', '2024-12-16 22:17:43', 10, 10, '/images/comment/10_17343586633.png');

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
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hashtag
-- ----------------------------
INSERT INTO `hashtag` VALUES ('2024-11-22 17:23:28', '2024-11-22 17:23:28', 10, '社会');
INSERT INTO `hashtag` VALUES ('2024-11-22 17:23:38', '2024-11-22 17:23:38', 11, '新闻');
INSERT INTO `hashtag` VALUES ('2024-11-22 17:23:48', '2024-11-22 17:23:48', 12, '商业');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:51:05', '2024-11-28 15:51:05', 13, '科技');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:51:40', '2024-11-28 15:51:40', 14, '文学');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:52:21', '2024-11-28 15:52:21', 15, '音乐');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:52:36', '2024-11-28 15:52:49', 16, '教育');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:53:02', '2024-11-28 15:53:02', 17, '影视娱乐');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:53:13', '2024-11-28 15:53:17', 18, '动漫游戏');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:53:25', '2024-11-28 15:53:25', 19, '故事奇谈');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:53:48', '2024-11-28 15:53:48', 20, '健康养生');
INSERT INTO `hashtag` VALUES ('2024-11-28 15:54:25', '2024-11-28 15:54:25', 21, '闲谈吐槽');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `post_id` bigint unsigned NOT NULL COMMENT '帖子id',
  `post_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '标题五个字' COMMENT '帖子标题',
  `post_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '帖子详情内容',
  `post_like_count` bigint unsigned NOT NULL COMMENT '帖子点赞数',
  `post_collection_count` bigint unsigned NOT NULL COMMENT '帖子收藏数',
  `post_comment_count` bigint unsigned NOT NULL COMMENT '帖子评论数',
  `post_association` tinyint(0) NOT NULL DEFAULT -1 COMMENT '帖子关联 0为频道 1为节目 -1表示无关联内容',
  `association_id` bigint unsigned NULL COMMENT '关联的id,必须是存在的频道的id或节目id',
  `post_section` tinyint unsigned NOT NULL COMMENT '帖子所属板块 0:话题讨论 1:分享推荐 2:求助问答',
  `user_id` bigint unsigned NOT NULL COMMENT '发帖的用户id',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('2024-12-14 14:42:39', '2024-12-17 15:15:34', 42, '黑神话:悟空~电影级画面大赏! 大伙觉得这个画质如何?', '第三章的小雷音寺风景确实不错! 就是容易迷路......', 4, 1, 5, -1, NULL, 0, 7);
INSERT INTO `post` VALUES ('2024-12-14 14:44:52', '2024-12-14 14:44:52', 43, '那些年我们追过的爱豆', '时光不再,令人唏嘘', 0, 0, 0, -1, NULL, 1, 7);
INSERT INTO `post` VALUES ('2024-12-16 10:26:16', '2024-12-16 10:26:16', 44, '帖子关联频道测试', '帖子关联频道测试', 0, 0, 0, 0, 13, 0, 7);

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
  PRIMARY KEY (`post_favorite_id`) USING BTREE,
  UNIQUE INDEX `post-favorite-id`(`post_id`, `user_id`) USING BTREE COMMENT '在帖子收藏表中, 用户id与帖子id的组合是唯一的, 代表同一时间一个帖子只能被用户收藏一次'
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_favorite
-- ----------------------------
INSERT INTO `post_favorite` VALUES ('2024-12-14 20:24:14', '2024-12-14 20:24:14', 16, 42, 7);

-- ----------------------------
-- Table structure for post_hashtag
-- ----------------------------
DROP TABLE IF EXISTS `post_hashtag`;
CREATE TABLE `post_hashtag`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `post_hashtag_id` bigint unsigned NOT NULL COMMENT '表id',
  `post_id` bigint unsigned NOT NULL COMMENT '帖子id',
  `hashtag_id` bigint unsigned NOT NULL COMMENT '主题标签id',
  `hashtag_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主题标签名',
  PRIMARY KEY (`post_hashtag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_hashtag
-- ----------------------------
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 73, 42, 17, '影视娱乐');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 74, 42, 18, '动漫游戏');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 75, 42, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:44:52', '2024-12-14 14:44:52', 76, 43, 15, '音乐');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:44:52', '2024-12-14 14:44:52', 77, 43, 17, '影视娱乐');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:44:52', '2024-12-14 14:44:52', 78, 43, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-16 10:26:16', '2024-12-16 10:26:16', 79, 44, 10, '社会');
INSERT INTO `post_hashtag` VALUES ('2024-12-16 10:26:16', '2024-12-16 10:26:16', 80, 44, 11, '新闻');
INSERT INTO `post_hashtag` VALUES ('2024-12-16 10:26:16', '2024-12-16 10:26:16', 81, 44, 21, '闲谈吐槽');

-- ----------------------------
-- Table structure for post_image
-- ----------------------------
DROP TABLE IF EXISTS `post_image`;
CREATE TABLE `post_image`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `post_picture_id` bigint unsigned NOT NULL COMMENT '表id',
  `post_id` bigint unsigned NOT NULL COMMENT '帖子id',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`post_picture_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_image
-- ----------------------------
INSERT INTO `post_image` VALUES ('2024-12-14 14:33:51', '2024-12-14 14:33:51', 81, 38, '/images/post/38_17341580316.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:33:51', '2024-12-14 14:33:51', 82, 38, '/images/post/38_17341580316.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:33:51', '2024-12-14 14:33:51', 83, 38, '/images/post/38_17341580316.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 101, 42, '/images/post/42_17341585592.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 102, 42, '/images/post/42_17341585597.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:42:40', '2024-12-14 14:42:40', 103, 42, '/images/post/42_17341585603.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:42:40', '2024-12-14 14:42:40', 104, 42, '/images/post/42_17341585608.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:42:41', '2024-12-14 14:42:41', 105, 42, '/images/post/42_17341585614.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:42:42', '2024-12-14 14:42:42', 106, 42, '/images/post/42_17341585619.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:44:52', '2024-12-14 14:44:52', 107, 43, '/images/post/43_17341586927.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:44:53', '2024-12-14 14:44:53', 108, 43, '/images/post/43_17341586932.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:44:53', '2024-12-14 14:44:53', 109, 43, '/images/post/43_17341586938.png');
INSERT INTO `post_image` VALUES ('2024-12-14 14:44:54', '2024-12-14 14:44:54', 110, 43, '/images/post/43_17341586943.png');
INSERT INTO `post_image` VALUES ('2024-12-16 10:26:16', '2024-12-16 10:26:16', 111, 44, '/images/post/44_17343159762.png');

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `reply_id` bigint unsigned NOT NULL COMMENT '回复评论id',
  `reply_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `reply_like_count` bigint unsigned NOT NULL COMMENT '回复的点赞数',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论id',
  `user_id` bigint unsigned NOT NULL COMMENT '回复者的用户id',
  PRIMARY KEY (`reply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('2024-12-17 14:56:24', '2024-12-17 14:56:24', 1, '123', 0, 8, 7);
INSERT INTO `reply` VALUES ('2024-12-17 15:15:34', '2024-12-17 15:15:34', 2, '这里是回复评论测试', 0, 8, 7);

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
  `user_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/images/user/head/D.png' COMMENT '用户头像路径',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户邮箱(唯一)',
  `user_profile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '这个人很神秘,什么都没有写' COMMENT '个人简介',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE COMMENT '用户账号唯一',
  UNIQUE INDEX `user_email`(`user_email`) USING BTREE COMMENT '用户邮箱唯一',
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT '用户昵称唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2024-11-14 18:36:24', '2024-12-01 20:41:13', 6, '123456789', 'pxx', '/images/user/head/6_1732262627.png', '25070174521@qq.com', 'test111');
INSERT INTO `user` VALUES ('2024-11-14 20:29:56', '2024-12-03 16:09:04', 7, '123456789', 'pxx123', '/images/user/head/7_1732373035.png', '2507017452@qq.com', 'bsdh5133393');
INSERT INTO `user` VALUES ('2024-11-20 17:57:19', '2024-11-20 17:57:19', 8, '123456', 'test3', '/images/user/head/D.png', '123@test.com', '这个人很神秘,什么都没有写');
INSERT INTO `user` VALUES ('2024-11-20 18:00:01', '2024-11-20 18:00:01', 9, '123456', 'test4', '/images/user/head/D.png', '1234@test.com', '这个人很神秘,什么都没有写');
INSERT INTO `user` VALUES ('2024-12-01 20:42:00', '2024-12-01 20:47:50', 10, '123123q', '123', '/images/user/head/D.png', '250701745212@qq.com', '这个人很神秘,什么都没有写');
INSERT INTO `user` VALUES ('2024-12-01 20:56:35', '2024-12-03 15:30:15', 11, '123456q', 'pxx123123', '/images/user/head/D.png', '123', '这个人很神秘,什么都没有写');

SET FOREIGN_KEY_CHECKS = 1;
