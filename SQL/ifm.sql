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

 Date: 23/12/2024 10:52:40
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
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建时间, 上次更新时间, 表id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast
-- ----------------------------
INSERT INTO `broadcast` VALUES ('2024-12-31 11:40:48', '2024-12-20 21:26:07', 17, 13, 7, '第一章 认知篇，你的认知层次，决定你的人生高度', '一直以来，我们都被告诫说，我们的思维方式决定了我们的行动，而我们的行动又决定了我们的结果。但是，很少有人深入思考过背后的原因。事实上，我们的思维方式源自于我们的认知层次。\n\n所谓认知层次，指的是我们对世界和自己的理解和认识。它包括我们对事物的看法、信念、价值观、态度等等。这些因素将影响我们的决策、行为和成就。换句话说，我们的认知层次决定了我们的人生高度。\n\n\n想象一下两个人，他们在同样的情况下面临着挑战。第一个人，我们称之为小明，是一个怀有积极态度、坚定信念的人。他相信自己具备克服困难的能力，并且不断寻求成长和进步。第二个人，我们称之为小红，持有消极和悲观的态度，认为自己不行，并且对未来感到绝望。\n\n当遇到挑战时，小明会积极地寻找解决办法，勇敢地面对困难，不断努力。他相信自己能够战胜困难，并从中获取经验和成长。最终，他成功地克服了挑战，取得了辉煌的成就。\n\n而小红则不同，她对未来失去了信心，认为自己注定会失败。她没有积极主动地寻找解决办法，也没有努力去克服困难。最终，她确实失败了，陷入了沮丧和无助的境地。\n\n\n这个例子展示了认知层次对人生高度的影响。小明的积极态度和坚定信念使得他拥有勇气和毅力去攀登高峰，最终取得了成功。而小红的消极态度和负面信念却将她推向了失败的深渊。\n\n如何提升自己的认知层次呢？我们要认识到自己的思维方式和信念对人生的影响。我们需要审视自己的看法和态度，是否有助于我们的成长和进步。如果发现消极的思维模式和负面的信念，我们应该努力改变它们，转变为积极的思维方式和正面的信念。\n\n我们还可以通过阅读和学习来拓宽自己的认知层次。读书可以让我们接触到各种各样的思想和观点，开阔我们的视野，帮助我们建立正确的认知模式。我们可以选择一些著名的哲学家、心理学家或成功人士的作品，从中汲取智慧和经验。\n\n\n与积极、成功的人交往也是提升认知层次的有效途径。和他们的交往可以让我们学习到他们的思维方式和行为习惯，从而激发自己的潜能和动力。\n\n你的认知层次决定了你的人生高度。要提升自己的认知层次，我们需要审视自己的思维方式和信念，并且通过阅读、学习和与成功人士的交往来不断拓宽自己的认知边界。只有这样，我们才能在人生的舞台上展现出更高的境界和更大的成就。正如尼采所说：“我们因自己相信什么而伟大。”\n\n本文由心灵之旅中转站原创，欢迎关注，带你一起长知识', 121, '/images/broadcast/17_17344375044.png', '/audio/17_1734437656.mp3', 1, 2, 1);
INSERT INTO `broadcast` VALUES ('2024-12-31 19:29:11', '2024-12-17 20:35:22', 21, 13, 7, '第二章 情绪篇 了解情感变化的奥秘，让人生一路开挂', '生气、抑郁、焦虑等情绪只是人对环境的一种反应，没有好坏之分，但却深深地影响着我们的生活。因此，我们要了解情绪、受情绪，并适度地调节情绪，才能更好地生活并与他人和谐相处。', 257, '/images/broadcast/21_17344388644.png', '/audio/D.mp3', 0, 1, 0);
INSERT INTO `broadcast` VALUES ('2024-12-17 20:41:53', '2024-12-17 20:41:53', 25, 37, 7, '01 节约是美德吗？', '我们都生活在中国这样一个东方的文化传统里，很多时候家长都会教育我们勤俭节约，这是一个美德。但是在经济学的视角里，勤俭节约一定是美德吗？\r\n\r\n现在我们都已经步入了汽车消费时代，所以今天我们就以汽车消费为例来给大家讲讲GDP的构成以及消费和储蓄的关系。\r\n\r\nGDP就是国内生产总值，由三个部分构成，消费、投资、净出口。比如说，就以汽车生产为例，一家企业投资开厂，生产汽车，这个构成了投资，它创造了就业和收入，还有税收，当然就应该进入到一个国家的国内生产总值里。\r\n\r\n我们消费者去买车，这种消费也会产生相应的收入和就业，也是GDP的一个非常重要的构成部分。在一个国家当中，如果它生产的汽车数量比消费者购买的汽车数量多，这个时候它可以把生产出来的汽车卖到别的国家去，这时候出口就构成了一个国家的国内生产总值的一个部分。所以我们通常都讲经济增长有三驾马车，指消费、投资和净出口。\r\n\r\n消费和投资之间有什么样的关系？我们说一个人最终是一个消费单位，不只是一个生产单位，因为我们最后的生活幸福水平是取决于我们消费了多少产品和服务。', 257, '/images/broadcast/25_17344393138.png', '/audio/25_1734439313.mp3', 0, 0, 0);
INSERT INTO `broadcast` VALUES ('2024-12-17 20:43:03', '2024-12-21 12:20:13', 26, 37, 7, '02 为什么经济衰退十年一次？', '我们之前的课程里讲到过经济有周期性的现象，有的时候经济增长速度越来越快，而有的时候经济增长速度会有所下降，甚至严重的情况下会出现经济增长是负增长的这样一种情况。这样一种经济的周期就表现为经济的繁荣期，伴随着经济的衰退期，大约每隔十年左右就会有一次。当然这里所讲的十年是一个约数，其实也并不一定就那么有规律，否则我们不是能够准确预期到经济周期的来临了吗？\r\n\r\n大约从马克思所在的时代开始，经济周期大概有十年左右出现一次的迹象。比如说在最近这些年，1998年的时候，曾经出现过一场非常严重的东南亚金融危机，当时一些东南亚的小国受到了非常严重的冲击。2001年前后，美国曾经有过一场网络股的泡沫破灭，之后就到了2008年，2008年的时候，国际经济危机对整个全球的经济增长出现了非常严重的危机。最近到了2018年，大家可能也会觉得经济的风险非常大，中国经济出现了多年的持续下滑的状态，最近状态也不太好。\r\n\r\n经济周期是怎么产生的？经济学家认为经济周期通常有供给方的冲击和需求方的冲击两种因素，但是主要是由需求不足所导致的。', 121, '/images/broadcast/26_17344393831.png', '/audio/26_1734439383.mp3', 0, 2, 0);
INSERT INTO `broadcast` VALUES ('2024-12-17 20:45:36', '2024-12-17 20:45:36', 27, 13, 7, '第三章 社会篇 剖析规律，让你在生活中如鱼得水', '社会心理学（social psychology）是指研究个体和群体在社会相互作用中的心理和行为发生及变化规律。社会心理学在个体水平和社会群体水平上对人际关系进行探讨。在个体水平上进行研究的内容有：个体社会化过程，交往，言语发展，伙伴，家庭和居住环境及学校对个人的影响等。在社会群体水平上进行研究的内容有：群体交往结构、群体规范，态度、种族偏见攻击行为、风俗习惯和文化等', 121, '/images/broadcast/27_17344395360.png', '/audio/27_1734439536.mp3', 0, 0, 0);
INSERT INTO `broadcast` VALUES ('2024-12-17 20:51:35', '2024-12-17 20:51:35', 28, 38, 7, '俄罗斯篇：01俄罗斯的家底究竟有多厚？', '俄罗斯在俄乌战争中展现出强大的军事实力，其家底丰厚，继承了苏联大部分遗产，包括先进的武器装备、发达的重工业和丰富的资源。 在军火贸易方面，俄罗斯也取得了巨大的成功，2023年军火出口总额达到550亿美元，创下历史新高。 尽管面临欧美各国的打压，但俄罗斯仍能保持游刃有余的态势，足以应对欧美国家的联合攻伐。', 257, '/images/broadcast/28_17344398956.png', '/audio/28_1734439895.mp3', 0, 0, 0);
INSERT INTO `broadcast` VALUES ('2024-12-17 20:53:22', '2024-12-17 21:01:24', 29, 38, 7, '俄罗斯篇：02俄罗斯此次俄乌冲突是赔是赚呢？', '2022年俄乌冲突（俄罗斯称为“特别军事行动” [282] [285]，乌克兰称为“俄罗斯全面入侵战争” [283-284]），是俄罗斯对乌克兰发起的大规模军事行动。 [286-287]\r\n2022年2月24日，因北约东扩及顿巴斯冲突，普京宣布开展“特别军事行动”，计划对乌克兰进行非军事化和去纳粹化，俄乌冲突正式爆发 [286-287]；行动之初，俄军发起“闪电战”，从乌克兰的北、东北、东、东南、南部方向同时发起进攻，快速向基辅、哈尔科夫等城市推进，并控制战略要地赫尔松；因乌军抵抗，俄军进攻受阻，3月底，俄军从基辅地区撤军；此后，经数月激战，俄罗斯逐渐控制乌克兰约20%领土 [287-289]；9月，乌军成功发动哈尔科夫大反攻，俄乌双方力量对比趋于均衡；同月，乌克兰的卢甘斯克、顿涅茨克、扎波罗热、赫尔松四地举行“入俄公投”，正式加入俄罗斯联邦；11月，因后勤网络遭到打击，俄军撤出赫尔松，冲突进入僵持阶段 [286] [288-289]。2023年6月，乌军在多个方向发起反攻，但都遭到俄军阻击，冲突再次陷入僵局；10月，俄军对乌克兰东部的阿夫杰耶夫卡、马林卡、巴赫穆特等地乌军防线发动攻势。2024年2月，俄军完全控制阿夫杰耶夫卡 [290-291]。\r\n俄乌冲突是二战结束以来欧洲伤亡人数最多的战争。冲突发生后，西方国家对俄罗斯施加严厉的制裁，俄罗斯经济遭受震荡，但之后逐步展现出韧性；乌克兰经济则遭到严重破坏，国家预算近四分之一依赖西方援助。同时，冲突还对地区政治与安全、世界经济复苏、全球减贫、粮食和能源安全、生态环境等诸多方面带来严重负面冲击。 [290-291]\r\n截至2024年10月，冲突仍在持续。', 257, '/images/broadcast/29_17344400026.png', '/audio/29_1734440002.mp3', 0, 0, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast_favorite
-- ----------------------------
INSERT INTO `broadcast_favorite` VALUES ('2024-12-20 21:26:07', '2024-12-20 21:26:07', 17, 17, 7);
INSERT INTO `broadcast_favorite` VALUES ('2024-12-21 12:20:13', '2024-12-21 12:20:13', 18, 26, 7);

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast_history
-- ----------------------------
INSERT INTO `broadcast_history` VALUES ('2024-12-07 23:29:13', '2024-12-07 23:30:05', 3, 11, 7, '2024-12-07 23:30:05', 111);
INSERT INTO `broadcast_history` VALUES ('2024-12-11 21:32:14', '2024-12-11 21:37:31', 4, 22, 7, '2024-12-11 21:37:31', 191);
INSERT INTO `broadcast_history` VALUES ('2024-12-11 21:36:24', '2024-12-11 21:37:40', 5, 21, 7, '2024-12-11 21:37:40', 1);
INSERT INTO `broadcast_history` VALUES ('2024-12-15 21:27:34', '2024-12-15 21:27:41', 6, 24, 7, '2024-12-15 21:27:41', 7);
INSERT INTO `broadcast_history` VALUES ('2024-12-17 17:15:23', '2024-12-21 12:30:36', 7, 17, 7, '2024-12-21 12:30:36', 118);
INSERT INTO `broadcast_history` VALUES ('2024-12-17 20:43:22', '2024-12-21 12:32:36', 8, 26, 7, '2024-12-21 12:32:36', 116);

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
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建时间, 上次更新时间,表id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('2024-11-22 18:57:35', '2024-12-17 21:30:30', 13, '有趣又好读的心理学', '心理学效应、心理学定律、心理学法则、心理学理论、爱情心理学、情绪心理学、人际交往心理学、儿童教育心理学、行为心理学、重口味心理学、微表情心理学，弗洛伊德、荣格、阿德勒、华生、卡伦霍妮一生研究，揭示那些无处不在左右你生活的心理学，好玩又实用..', 3344569, '2024-12-17 20:45:36', 7, '/images/channel/13_17344386793.png', 1);
INSERT INTO `channel` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 19, '下北泽往事~回到二十世纪八十年代的黄金岁月 (迫真)', '下北泽俗称“下北”（Shimokita）规模不大但深受年轻人喜爱。街上有很多时装店、二手衣店及日用品店，是很适合散步逛街的地方。有很多值得搜寻一番新潮又有个性的商品。这里不乏优雅的咖啡厅和酒吧，又有很多老字号日式酒馆和大众食堂等老店，酝酿出一股独特的街景氛围。此外，“本多剧场”、“站前剧场”等小剧场、“短片影院Tollywood”等小剧院，以及“Shelter”、“下北泽屋根里”等Live House，为数众多的展演空间，使此地因“戏剧之城”、“音乐之城”的号称而闻名。其中，下北泽还以“亚文化天堂”为人熟知，使得很多年轻人慕名而来', 0, '2024-12-13 13:41:34', 7, '/images/channel/19_1734068494.png', 0);
INSERT INTO `channel` VALUES ('2024-12-17 20:39:45', '2024-12-17 20:43:03', 37, '《经济观察站 : 中国经济》经济学名师陆铭', '各位听众朋友们，大家好，我是上海交通大学的陆铭，今天开始，我的第一门中国宏观经济学音频课就在喜马拉雅FM上与大家见面了。\r\n在我们每天的日常生活当中，可能每个人都必须或多或少的跟经济学发生关系，但是可能很多人都没有意识到这点。其实不管你是什么样的职业或者年龄或者收入，你都必须要跟经济和经济学打交道。比如说年轻的时候，你最重要的选择就是就业。在大城市就业还是在小城市就业?你最重要的选择，再长大一点呢，要结婚买房子，一定是要到房价便宜的地方去买房子吗?是买房子还是租房子，这又是一个基本的决策和经济学的问题。在之后呢等到你有了一些自己的积累，你可能就要考虑投资了。在哪里投资?投什么什么时间投，这些也都是经济学问题。\r\n就比如说最近一段时间，中国的二三线城市的房价涨得很快，那你要不要跟进呢?特别是当你自己的老家是一个线城市的时候，你要不要也跟风一起去买那里的房子呢?这个就要看你是长线的投资，还是短线的投资!如果是短线的投资，当然在房价上涨非常快的一段时期里，如果你踩点踩得好，买进再卖出，当然可以挣笔钱。但是如果尔要是长线的投资，就要看基本面的需求和供给了。从需求角度来讲，一个地区的房价主要来自于收入增长的速衰，以及人口增长的速度。而从供给方面角度来讲呢，你就需要去了解当地的住房市场上是不是有很多的住房库存，以及当地的土地供应是不是充分，有没有受到政府严格的管制等等这样的一些问题。所以最基本的一些经济学的分析，就能帮助你更加理性地科学地来决策自己将进行什么样的投资，以及在哪里投资和什么时间投资。\r\n可以这么说，每一个人都应该把经济学和对于中国经济的了解当做是一个必备的知识。大家可能会说，对，那我就买本书看看喽，我要告诉大家的是，买本书是远远不够的。因为通常的经济学教科书只会告诉你经济学的原理在哪里，虽然原理是相通的，但是每一个国家的国情事实上都是不一样的。比如说在中国，我们的改革的起点是一个计划经济国家，就跟别的国家非常不一样。这就使得如果我们不懂中国的实际情况的话，简单地用经济学的教科书的京理来看待中国，就会出现很多的误区\r\n所以我认为一个人是不是好的经济学家，或者是不是懂中国经济就必须具备至少两个条件。第一是必须懂得经济学的一般原理。第二是必须懂得真实的中国到底在发生什么样的事情，要懂得中国的制度和政策。也许就是因为这样的原因呢，喜马拉雅找到了我。我自己呢以前是在中国拿的经济学的博士学位，同时我也有非常丰富的在国外游学的经历，我还经常给国际机构，包括世界银行亚洲开发银行做咨询，我还在全亚洲最好的商学院之一，上海交通大学安泰经济与管理学院，讲授MBA和E MBA的中国经济课程。由于有了这样的背景，呢使得我也许是一个给大家讲授中国经济的非常不错的人选，而在你现在听到的有关中国经济的语音课程里，我所要做的就是告诉大家，你黑要结合经济学的知识和中国实际发生的情况，来更新你对现实经济的认知和思维。这门课讲授的是中国经济，我会把有关中国的制度和政策分析融入到宏观经济学发展经济学区域经济学等知识当中去，简明而又仔细的分析中国经齐的成功经验以及有待改革的一些问题，澄清一些有关中国经济的认识误区，最终当然也希望能够帮助大家理解经济政策的走向，把握投资和个人发展的机会。\r\n最后我有两点想与大家分享一下，也许我告诉大家，很多的事情将会对大家的思维方式和对于中国经济的理解形成挑战。你不要怕这种挑战性的思维，你们也不要怕，听完了这样的课以后，自己和周围的朋友谈不来了。如果真的淡不来了那就对了，因为这意味着你听完这门课程以后，你拥有了别人不拥有的知识，而机会就属于你了。\r\n其次呢，经济学的课程也许有那么一点点枯燥，但是当你用心学完并吃透所有这些知识之后呢，感觉就好像跑完-汤马拉松比赛一样。回过头来，看看经过的路程，再看看未来把握宏观经济的能力，以及因此获得的成功决策，将使得你和你的家庭获得真正的幸福。好的，现在就让我们一起开启学习中国经济的课程。', 0, '2024-12-17 20:43:03', 7, '/images/channel/37_17344391858.png', 0);
INSERT INTO `channel` VALUES ('2024-12-17 20:47:48', '2024-12-17 20:53:22', 38, '西方军事大讲堂|俄乌战争|巴以冲突|大国博弈', '对话的形式与听众讲述正在发生的战事以及已发生的战事，问答式讲述战事主体国家实际情形以及发生人的状态，用客观角度讲述战争痛点以及引起听众共鸣。', 0, '2024-12-17 20:53:22', 7, '/images/channel/38_17344396689.png', 0);
INSERT INTO `channel` VALUES ('2024-12-17 20:55:39', '2024-12-17 21:24:41', 39, '骑士的献祭|那多悬疑集', '★《十九年间谋杀小叙》作者那多，改编自让作者五年都无法释怀的真实案件，预定2020年度中国最佳犯罪小说！\r\n\r\n★双线叙事线索，警察和案犯从两个相反的方向，向真相汇合。\r\n\r\n★以极致的善良为锋，斩开浑浊人间。\r\n\r\n★在这个故事中，爱是紧紧的。“我伸出手，把她够着了。够着了，我能再松开 吗？”\r\n\r\n守护是流血的。 “警官，你肯定在想，是不是我们和爸爸一起，杀死了妈妈？”\r\n\r\n爸爸是孤独的。“此时此境，世界对他来说如同荒原，行走其上，赤裸来去无心遮掩。”\r\n\r\n温暖彻骨，椎心揾泪。\r\n\r\n**********\r\n\r\n小区河道里陆续发现了装有尸块的蛇皮袋，经鉴定，死者被分尸，作案人手段残忍，具备较强的反侦察能力，专案组一时陷入困局。警察老冯沿着蛇皮袋的线索一路追踪，赶到了案犯的居住地，而后者却似乎刚刚逃走。\r\n\r\n蹊跷的是，在同事、邻居及女儿的叙述中，案犯似乎是个 “老好人”，怎么也不像灭绝人性的碎尸犯……\r\n\r\n拒不配合的女儿、若隐若现的“第五口人”、“吸血老鼠”传闻、频频发生的失火案……\r\n\r\n随着案情逐渐明朗，一出尘封多年的家庭悲剧浮出水面。原来，死者是案犯的前妻。对他，她背叛，伤害，离开，她曾有凌霄之志，却在险恶世道中摔得身心俱碎；对她，他一次次伸出手，想拉她从泥泞中拔出腿来，却复与其深陷其中。\r\n\r\n有强烈的爱，才会有强烈的恨，但是善叫他去解脱一个人。世情的荒原中有一道闷雷贴着地黯然远去，一位父亲走到了决死时刻……', 1, '2024-12-17 20:55:39', 7, '/images/channel/39_17344401399.png', 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel_hashtag
-- ----------------------------
INSERT INTO `channel_hashtag` VALUES ('2024-11-22 18:57:36', '2024-11-22 18:57:36', 3, 13, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 15, 19, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 16, 19, 17, '影视娱乐');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 17, 19, 19, '故事奇谈');
INSERT INTO `channel_hashtag` VALUES ('2024-12-13 13:41:34', '2024-12-13 13:41:34', 18, 19, 21, '闲谈吐槽');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:39:45', '2024-12-17 20:39:45', 36, 37, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:39:45', '2024-12-17 20:39:45', 37, 37, 12, '商业');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:39:45', '2024-12-17 20:39:45', 38, 37, 13, '科技');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:39:45', '2024-12-17 20:39:45', 39, 37, 16, '教育');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:47:48', '2024-12-17 20:47:48', 40, 38, 10, '社会');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:47:48', '2024-12-17 20:47:48', 41, 38, 11, '新闻');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:47:48', '2024-12-17 20:47:48', 42, 38, 13, '科技');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:55:39', '2024-12-17 20:55:39', 43, 39, 14, '文学');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:55:39', '2024-12-17 20:55:39', 44, 39, 17, '影视娱乐');
INSERT INTO `channel_hashtag` VALUES ('2024-12-17 20:55:39', '2024-12-17 20:55:39', 45, 39, 19, '故事奇谈');

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel_subscription
-- ----------------------------
INSERT INTO `channel_subscription` VALUES ('2024-12-02 19:41:27', '2024-12-02 19:41:27', 12, 15, 7);
INSERT INTO `channel_subscription` VALUES ('2024-12-16 11:15:37', '2024-12-16 11:15:37', 16, 13, 7);
INSERT INTO `channel_subscription` VALUES ('2024-12-17 20:55:51', '2024-12-17 20:55:51', 17, 39, 7);

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('2024-12-16 19:59:06', '2024-12-17 15:15:34', 8, '123', 42, 7, 2, 2);
INSERT INTO `comment` VALUES ('2024-12-16 21:00:06', '2024-12-16 21:00:06', 9, '123123', 42, 7, 0, 0);
INSERT INTO `comment` VALUES ('2024-12-16 22:17:40', '2024-12-16 22:17:40', 10, '美图分享', 42, 7, 0, 0);
INSERT INTO `comment` VALUES ('2024-12-17 21:02:56', '2024-12-17 21:08:03', 11, '但是按购买力平价，俄罗斯GDP已超越日本，重回全球第4\n根据国际货币基金组织（IMF）最新发布的《世界经济展望报告》，以购买力平价计算，2024年俄罗斯的国内生产总值（GDP）占全球GDP的3.55%，超过了日本的3.38%。', 49, 7, 0, 1);
INSERT INTO `comment` VALUES ('2024-12-17 21:17:03', '2024-12-18 10:01:46', 12, '您的分析十分精彩~ 我觉得知道分尸但不举报那段，肯定是作者的虚构，真实案件应该不是这个逻辑。 话说手记的新作萌芽连载过了，书估计得和这本错开时间出版', 50, 7, 0, 1);
INSERT INTO `comment` VALUES ('2024-12-17 21:17:20', '2024-12-17 21:17:39', 13, '我也觉的女儿的故事比父亲精彩，女儿的形象也更出彩更让人震动', 50, 7, 0, 1);
INSERT INTO `comment` VALUES ('2024-12-17 21:32:06', '2024-12-17 21:32:06', 14, '哇！正好有这方面需求，感谢推荐！', 52, 7, 0, 0);
INSERT INTO `comment` VALUES ('2024-12-18 10:01:32', '2024-12-18 10:01:32', 15, '123', 50, 7, 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `comment_image` VALUES ('2024-12-17 21:32:06', '2024-12-17 21:32:06', 11, 14, '/images/comment/14_17344423268.png');
INSERT INTO `comment_image` VALUES ('2024-12-18 10:01:32', '2024-12-18 10:01:32', 12, 15, '/images/comment/15_17344872925.png');

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
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('2024-12-14 14:42:39', '2024-12-20 22:29:51', 42, '黑神话:悟空~电影级画面大赏! 大伙觉得这个画质如何?', '第三章的小雷音寺风景确实不错! 就是容易迷路......', 4, 0, 5, -1, NULL, 0, 7);
INSERT INTO `post` VALUES ('2024-12-17 21:01:15', '2024-12-21 12:22:08', 49, '俄乌战争持续了近3年，两国的经济到底损失了多少？ ', '俄罗斯在这场战争中的经济损失是显而易见的。\n\n从军事角度来看，俄罗斯的军事开支大幅增加。据俄罗斯政府和多个独立机构的统计，战争爆发后，2024 年第三季度的“国防”支出达到了前所未有的430亿美元。毕竟，俄罗斯为了维持这场战争，不仅需购买大量的武器、弹药和军事装备，还需支付战争相关的人员工资和补给。\n\n因此，俄罗斯在战争中的直接军事支出，已超过1200亿美元。\n\n自战争爆发以来，西方国家对俄罗斯实施前所未有的经济制裁，这给俄罗斯的经济带来长期且深刻的影响。', 10, 1, 2, 1, 29, 0, 7);
INSERT INTO `post` VALUES ('2024-12-17 21:16:19', '2024-12-18 10:01:46', 50, '《骑士的献祭》分析: 谁才是真正的家庭守护者？李善斌还是李怡诺？', '看了豆瓣上对这部作品的全部评价，看到的都是认为书名《骑士的献祭》中骑士就是指男主角李善斌为了爱和善意成为罪犯，并对他愿意为了深爱着的妻子献出一切感到动容。但我的性格使然，并不这样认为，反而觉得相比起李善斌，无论从动机还是行为上来看，在书中还有一个角色更加担得起“骑士”这个称谓。\n\n那多在2018年出版了根据真实案件改编的《十九年间谋杀小叙》后，一边继续那多灵异手记系列新篇，一边埋首创作了这一部作品。两年时间的辛苦耕耘之下完成的本作，从质量上来说比《十九年间谋杀小叙》还是稍微差了一点，但比起更早出版的的《甲骨碎》《一路去死》等要好很多。\n\n这一次那多仍旧选择了以真实案件为根基来创作小说，本书的原型是在上海轰动一时的2011年宝山“714碎尸案”。书中大部分的犯罪事实和真实案件完全一致，一些细节如黑色垃圾袋的突破口、离婚后又再次同居、妻子患有精神病、有两个孩子等也是照搬真实案件。但最核心的犯罪动机部分我个人认为还是文学创作的成分居多，不然真的达不到现在感动大部分读者的地步。\n好了，现在让我们回到本文开头提出的问题，《骑士的献祭》这个书名中的“骑士”究竟是指谁呢？在看书的过程中，我的内心中一直在思考书名是不是仅仅指李善斌是时灵仪的骑士？文章中究竟还有没有其他人呢？真的这么简单这么直接就把题目给解释了？虽然从表面看来确实如此，但是想深一层，似乎还是存在着更加符合的人物形象。\n\n直到看到小说的末尾，刑警们每人给了李善斌的女儿李怡诺一块钱，才真正认知到书中唯一一个完全为了家人奋斗的骑士正是李怡诺。李怡诺以16岁的少女之姿为了维护家庭的完整，直面那么多大人，冷静成熟到出人意料，而且为了保护自己心爱的家人，甚至对自己也冷酷到底，不仅使出美人计，还不惜自残身体。从外人看，她极其善于运用自己美丽的身躯作为武装，但作为一名弱女子，要想和这个残酷的现实战斗，她能想到的方法还有其他吗？\n\n在李善斌杀了妻子后，她早就知道了这个秘密，但一直以来她都凭着自己的微不足道的资本在对抗着所有想探知这个秘密的人。为了让爸爸能陪伴更长的时间，对警察的询问可以装作不知道；为了完成和爸爸守护弟弟的约定，可以借色相引诱弟弟的生父，自残让其入罪。可以说，她的所有举动没有一点是对家庭不好的。\n\n她最快乐的时光也许只在妈妈离家出走后爸爸帮她编织的美好幻想之中，随着时灵仪被李善斌带回了家，她就开始承受着本不该她需要承受的无穷压力，但她仅仅是对外人露出了坚强的伪装，而对家人则始终呵护备至。甚至对于她不喜欢的妈妈，也仅仅是默默承受妈妈给家庭带来的伤害，只为了给亲爱的爸爸温暖的支持。\n\n反观李善斌虽然号称是为了爱而亲手杀了已经变疯且没有生存欲望的妻子，并在最后为了妻子，以死完成了对罪魁祸首孙九刀的复仇。但在这个过程中，如果读者跳出作者设置的情感绑架后仔细思考，会发现李善斌所谓的很正义很温暖的以爱之名杀人也只不过是自私的爱。他没有考虑过家庭，没有考虑过犯罪之后年少的女儿、儿子和年迈的母亲要怎么生活下去。文中给出的解决方案是将从孙九刀手上骗抢过来的30万留给家人，但他们要的真的是这个吗？\n\n李善斌确实深爱着时灵仪，再次相遇时她的悲惨境况让他决定保护好她，他确实已经重新够到了她的手，再也不想放开。但最终不还是亲手杀死了自己爱的人。李善斌始终被困在了自己搭建出来的爱的囚笼里，想深一层，为了家庭最好的选择不应该是让时灵仪住进疯人院吗？一定要把她留在身边，往好了说是爱，想自己照顾，不想让她遭受到外面的风雨。但往坏了说，就是自私，为了表现自己对她的执着的爱这个执念，弃家人的其他利益于不顾。关于对家庭的照顾，李善斌甚至远远不及他的女儿。\n\n李怡诺的一系列不得不完成的对自己的伤害，把自己当成家庭的挡箭牌，直至全身布满了创伤，都是从为了保护爸爸的秘密开始的。这个世界上，她最喜爱的就是爸爸了。因为在妈妈离开之后，爸爸带给了她生活的希望，现在轮到她来守护爸爸。从一开始，一家人就已经知道了李善斌做了无法挽回的事。这件事之后，李怡诺能做的只有更加珍惜和爸爸在的每一天。因此，回看书中前几章中描写的李善斌带李怡诺和李立去锦江乐园玩，李怡诺一直缠着爸爸，包括李怡诺要和爸爸一起睡这些文字才会显得那么痛苦和心酸。\n\n在故事的最后，刑警们每个人给了李怡诺一块钱，正好是17名警察，17块钱。原本李怡诺拿到了爸爸用命换来的钱，但始终还是不保险的。现在，每个警察都承诺了我们是“一块”的，至少那30万能够给李怡诺即将面临的未来生活的各种困难小小的支撑。同时，17也象征着16岁的李怡诺在这一刻真正失去了可以依靠的人，从17岁开始，自己变成了这个家的支柱，今后仍要继续完成和爸爸的承诺，就像她的名字一样“一诺千金”。\n\n书中另一个主角刑警老冯的这一条线是小说中非常重要的对比线，他和李善斌正好截然相反。两个主角的故事线的对比应该是那多老师故意为之，以此来让读者自己选择进入故事的角度。李善斌感情太过强烈，而老冯则对感情差不多到了无感的程度，按书中说的是一种无法感知情感波动的病。在侦查这个案件的过程中，老冯的感情渐渐充沛了起来。老冯的妻子也和李善斌一样离婚了，连出走的理由都类似，都以更爱另一个人为借口离开了丈夫。但时灵仪是从心里就向往更好的生活才离开丈夫，而老冯的妻子崔影是真的受不了老冯原来的没有感情的生活才说谎离开的。两人动机的不同造成了往后生活的巨大差异。而两人都有一个女儿，李怡诺一直想办法留住爸爸，结果只能独力负担整个家庭的责任。而老冯的女儿冯小瑶前面已经渐渐地和爸爸越行越远，反而在这次事件后和爸爸的关系更紧密了。\n\n从小说结构看，前半部分写到的李怡诺的举动到确实是非常具备隐蔽性，让读者一直以为是由于女儿的什么事情才会让李善斌分尸，结果作者在这里给了一个反转，这部分处理得不错。前面几章实际上还是有一些铺垫和伏笔没有多大效果的。书中两次出现的时灵仪的小本子上的内容，读者当时还会以为李善斌会想办法把名单上的人全都杀了，但最后也没有用到，变得没有实际用处。而第二章薛长久明知分尸真相但假装钓鱼报案这一部分也没有必要这样写，这个装腔作势没有对剧情起到推进作用，反而让读者奇怪为什么他知道是谁弃尸的，却没有直接向警察举报。按照后文他的目的是为了夺回儿子的抚养权，那么回看就会发现第二章的操作是存在问题的。', 5, 1, 5, 0, 39, 0, 7);
INSERT INTO `post` VALUES ('2024-12-17 21:19:09', '2024-12-17 21:24:07', 51, '以善良为剑，即使深陷炼狱，也要劈开丑恶', '知道那多老师很久了，还是第一次看他的书。这本《骑士的献祭》是由真实事件改编的。最近看了很多悬疑、刑侦类的小说，很多都是真实事件改编，但让我看得唏嘘不已的还是第一次。\n\n本案的原型是“714上海宝山碎尸案”，有兴趣的同学可以网上查一下。案子和小说还是有点出入的。小说做了一些美化，增加了一些冲突。\n\n这本书虽然是推理小说，但是本格的部分不强，通过内裤和垃圾袋破案的侦破过程确实挺精彩的。这本书的重点还是在杀人动机上面。通过警察和杀人犯两条线来推导出了事情的本来面目。\n\n书名《骑士的献祭》和腰封基本已经算是半剧透了，一位父亲的决死时刻，这位父亲作为骑士，守护的是谁呢？\n\n开始我以为父亲李善斌守护的是自己的爱人。当爱人落难的时候，他伸出手够到她，再也不忍心放手推她回去炼狱之中。就算被爱人拖进深渊，也无怨无悔。\n\n但是看到后来，越来越觉得，他最想守护的还是自己的家人。当爱人和家人的安危相冲突的时候。他选择保护了家人，献祭了自己。\n\n李善斌对于时灵仪是有爱的，但后来已经演变成了一种责任和道义。他的良知不允许自己放任昔日的爱人不管。但是一个人的容忍也是有极限的。当爱人哀求李善斌杀了她的时候，李善斌的精神世界也已经崩溃了。他是被自己的责任和善良带崩溃的。\n\n\n杀人分尸之后，他的寻仇过程更像是一种求死的方式。\n\n他面对认出他想要报警的店主并没有杀人灭口，面对破坏他婚姻的第三者也没有痛下杀手，甚至面对“刀哥”这个罪魁祸首的复仇，也像是不要命的横冲直撞。最后他终于死了，死在了他认为的“复仇”的路上。\n\n直到死，他也再没有做出伤害别人的事情。也许死的这个时刻，他才能真正得到平静。他用自己的一条命向爱人付清了杀人的代价。他生命最后的那句“谢谢”更像是一种解脱。他苟延残喘了这么久，终于结束了。\n\n这个世界以痛吻我，我却报之以歌。\n李善斌为了爱人献祭了自己，让自己的家人后半生孤立无援，是一种自私吗？我觉得不是。\n\n照顾别人的前提是自己能好好地活着。李善斌的精神显然已经没有办法维持正常了。他的世界在掐死时灵仪的时候已经毁灭了。无论他是生是死，都已经不能照顾自己的家人。还不如离他们远远的，免得受到自己的牵连。\n\n第一次看那多老师的书，觉得非常的惊艳。这种惊艳在于那多老师对于故事的改编，也在于故事的文笔。很多悬疑小说，都意图用情节的离奇吸引读者，难免在文笔上就弱化一些。这样的小说更类似于方便面，闻着很香，吃下去觉得就这样。\n\n那多老师的小说，文笔华丽，触及内心，是我喜欢的那种文风，值得反复阅读欣赏。', 1, 0, 0, 0, 39, 1, 7);
INSERT INTO `post` VALUES ('2024-12-17 21:30:30', '2024-12-17 21:32:39', 52, '频道推荐! 如果你对心理学感兴趣，或想减少困惑', '从我2006年开始读心理学本科、硕士，到如今我从事着心理老师、心理咨询师的工作，已经有11年时间了。这11年里，我读过许多专业书以及心理相关的书籍，也发现越来越多的人希望接触心理学，来帮助自己和他人走出困境，更快乐地生活。但是经常有朋友因为苦于不知如何入门，在此我推荐这个频道，频道内容很有用，希望能减少你的困惑，帮你概览心理学的全貌。', 2, 1, 1, 0, 13, 1, 7);
INSERT INTO `post` VALUES ('2024-12-19 23:10:23', '2024-12-19 23:10:23', 53, '建议所有人听一听这个广播节目', '认识自我真的非常重要!', 0, 0, 0, 1, 17, 1, 7);

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
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_favorite
-- ----------------------------
INSERT INTO `post_favorite` VALUES ('2024-12-17 21:08:31', '2024-12-17 21:08:31', 17, 49, 7);
INSERT INTO `post_favorite` VALUES ('2024-12-17 21:20:42', '2024-12-17 21:20:42', 18, 50, 7);
INSERT INTO `post_favorite` VALUES ('2024-12-17 21:32:19', '2024-12-17 21:32:19', 19, 52, 7);

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
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_hashtag
-- ----------------------------
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 73, 42, 17, '影视娱乐');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 74, 42, 18, '动漫游戏');
INSERT INTO `post_hashtag` VALUES ('2024-12-14 14:42:39', '2024-12-14 14:42:39', 75, 42, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:01:15', '2024-12-17 21:01:15', 91, 49, 10, '社会');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:01:15', '2024-12-17 21:01:15', 92, 49, 11, '新闻');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:01:15', '2024-12-17 21:01:15', 93, 49, 13, '科技');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:01:15', '2024-12-17 21:01:15', 94, 49, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:16:19', '2024-12-17 21:16:19', 95, 50, 14, '文学');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:16:19', '2024-12-17 21:16:19', 96, 50, 17, '影视娱乐');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:16:19', '2024-12-17 21:16:19', 97, 50, 19, '故事奇谈');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:16:19', '2024-12-17 21:16:19', 98, 50, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:19:09', '2024-12-17 21:19:09', 99, 51, 14, '文学');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:19:09', '2024-12-17 21:19:09', 100, 51, 17, '影视娱乐');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:19:09', '2024-12-17 21:19:09', 101, 51, 19, '故事奇谈');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:19:09', '2024-12-17 21:19:09', 102, 51, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:30:30', '2024-12-17 21:30:30', 103, 52, 10, '社会');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:30:30', '2024-12-17 21:30:30', 104, 52, 16, '教育');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:30:30', '2024-12-17 21:30:30', 105, 52, 20, '健康养生');
INSERT INTO `post_hashtag` VALUES ('2024-12-17 21:30:30', '2024-12-17 21:30:30', 106, 52, 21, '闲谈吐槽');
INSERT INTO `post_hashtag` VALUES ('2024-12-19 23:10:23', '2024-12-19 23:10:23', 107, 53, 20, '健康养生');

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
) ENGINE = InnoDB AUTO_INCREMENT = 119 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `post_image` VALUES ('2024-12-17 21:01:15', '2024-12-17 21:01:15', 115, 49, '/images/post/49_17344404759.png');
INSERT INTO `post_image` VALUES ('2024-12-17 21:16:19', '2024-12-17 21:16:19', 116, 50, '/images/post/50_17344413798.png');
INSERT INTO `post_image` VALUES ('2024-12-17 21:19:09', '2024-12-17 21:19:09', 117, 51, '/images/post/51_17344415493.png');
INSERT INTO `post_image` VALUES ('2024-12-17 21:30:30', '2024-12-17 21:30:30', 118, 52, '/images/post/52_17344422300.png');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('2024-12-17 14:56:24', '2024-12-17 14:56:24', 1, '123', 0, 8, 7);
INSERT INTO `reply` VALUES ('2024-12-17 15:15:34', '2024-12-17 15:15:34', 2, '这里是回复评论测试', 0, 8, 7);
INSERT INTO `reply` VALUES ('2024-12-17 21:08:03', '2024-12-17 21:08:03', 3, '你还真信啊', 0, 11, 7);
INSERT INTO `reply` VALUES ('2024-12-17 21:17:39', '2024-12-17 21:17:39', 4, '女儿看起来是照着白夜行的女主角写的啊', 0, 13, 7);
INSERT INTO `reply` VALUES ('2024-12-18 10:01:46', '2024-12-18 10:01:46', 5, '123', 0, 12, 7);

-- ----------------------------
-- Table structure for search_history
-- ----------------------------
DROP TABLE IF EXISTS `search_history`;
CREATE TABLE `search_history`  (
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次更新时间',
  `search_history_id` bigint unsigned NOT NULL COMMENT '搜索记录id',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '搜索关键词',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  PRIMARY KEY (`search_history_id`) USING BTREE,
  UNIQUE INDEX `user_id_keyword`(`keyword`, `user_id`) USING BTREE COMMENT '同一时间内,对于一个用户,一个关键词搜索记录只能存在一个'
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of search_history
-- ----------------------------
INSERT INTO `search_history` VALUES ('2024-12-20 17:34:45', '2024-12-20 17:34:45', 10, '大声道撒范德萨刚发如果然鹅', 7);
INSERT INTO `search_history` VALUES ('2024-12-20 17:34:49', '2024-12-20 17:34:49', 11, '大声道撒范德萨刚发如果然鹅萨达2人', 7);
INSERT INTO `search_history` VALUES ('2024-12-20 17:34:59', '2024-12-21 12:23:45', 12, '大声道撒', 7);
INSERT INTO `search_history` VALUES ('2024-12-20 17:42:09', '2024-12-20 19:17:26', 13, 'pxx', 7);
INSERT INTO `search_history` VALUES ('2024-12-20 18:54:58', '2024-12-20 18:54:58', 14, 'undefined', 7);
INSERT INTO `search_history` VALUES ('2024-12-23 10:31:08', '2024-12-23 10:31:08', 15, '212', 7);

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
INSERT INTO `user` VALUES ('2024-11-14 20:29:56', '2024-12-23 10:45:35', 7, '123456789', '21212', '/images/user/head/7_17349219352.png', '2507017452@qq.com', '114514');
INSERT INTO `user` VALUES ('2024-11-20 17:57:19', '2024-11-20 17:57:19', 8, '123456', 'test3', '/images/user/head/D.png', '123@test.com', '这个人很神秘,什么都没有写');
INSERT INTO `user` VALUES ('2024-11-20 18:00:01', '2024-11-20 18:00:01', 9, '123456', 'test4', '/images/user/head/D.png', '1234@test.com', '这个人很神秘,什么都没有写');
INSERT INTO `user` VALUES ('2024-12-01 20:42:00', '2024-12-01 20:47:50', 10, '123123q', '123', '/images/user/head/D.png', '250701745212@qq.com', '这个人很神秘,什么都没有写');
INSERT INTO `user` VALUES ('2024-12-01 20:56:35', '2024-12-03 15:30:15', 11, '123456q', 'pxx123123', '/images/user/head/D.png', '123', '这个人很神秘,什么都没有写');

SET FOREIGN_KEY_CHECKS = 1;
