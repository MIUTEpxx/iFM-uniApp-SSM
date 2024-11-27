package com.pxx.ifmserver.mapper;


import com.pxx.ifmserver.entity.dto.Channel;
import com.pxx.ifmserver.entity.dto.Hashtag;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChannelMapper {

    /**
     * 获取所有频道的数据。
     * @return 返回一个包含所有频道数据的List<Channel>列表。
     */
    @Select("SELECT * FROM channel")
    List<Channel> listChannel();

    /**
     * 根据频道id获取一个频道数据
     * @param channelId 频道id
     * @return 返回一个对应的频道数据
     */
    @Select("SELECT * FROM channel WHERE channel_id = #{channelId}")
    Channel getChannelByChannelId(Integer channelId);

    /**
     * 根据关键词模糊查询频道记录
     * @param keyWord 关键词
     * @return 匹配的频道列表
     */
    @Select("SELECT * FROM channel WHERE channel_title LIKE CONCAT('%', #{keyWord}, '%') " +
            "OR channel_detail LIKE CONCAT('%', #{keyWord}, '%')")
    List<Channel> listChannelByKeyWord(@Param("keyWord") String keyWord);

    /**
     * 根据用户ID,查询用户创建的频道
     * @param userId 用户ID
     * @return 频道数据列表
     */
    @Select("SELECT * FROM channel WHERE user_id = #{userId}")
    List<Channel> listChannelByUserId(Integer userId);

    /**
     * 根据频道id获取频道的主题标签数据
     * @param channelId 频道id
     * @return 对应的主题标签列表
     */
    @Select("SELECT * FROM channel_hashtag WHERE channel_id = #{channelId}")
    List<Hashtag> listHashtagByChannelId(Integer channelId);

    /**
     * 查询频道订阅表中,是否有用户订阅该频道的记录
     * @param channelId 频道ID
     * @param userId 用户ID
     * @return true/false
     */
    @Select("SELECT EXISTS(" +
            "SELECT 1 FROM channel_subscription WHERE user_id=#{userId} AND channel_id=#{channelId} " +
            ") AS has_subscription;")
    boolean checkChannelSubscriptionById( Integer userId,Integer channelId);

    /**
     * 查询频道订阅表中,用户ID订阅的频道ID
     * @param userId 用户id
     * @return 频道ID列表
     */
    @Select("SELECT channel_id FROM channel_subscription WHERE user_id=#{userId}")
    List<Integer> listSubscribedChannelIdByUserId(Integer userId);


    /**
     * 获取36小时内更新过节目的频道，并按订阅数降序排序，选取前20条记录
     * @return 36小时内更新过节目的频道列表
     */
    @Select("SELECT * FROM channel WHERE channel_update_time > NOW() - INTERVAL 36 HOUR ORDER BY channel_subscribe DESC LIMIT 20")
    List<Channel> listPopularChannel();


    /**
     *
     * 添加新频道
     * @param channel 频道数据。
     * @return 表示受影响的行数。在执行插入操作时，这个值通常是1，表示一行被成功插入
     * useGeneratedKeys: 当设置为 true 时，这告诉 MyBatis 在执行插入操作后，尝试检索数据库自动生成的键（通常是自增的主键）。这个功能通常用于在插入记录后获取该记录的自增 channel_id。
     * keyProperty: 这个属性指定了应该将检索到的自动生成的键值设置到目标对象的哪个属性上。
     */
    @Insert({ "insert into channel(channel_title, channel_detail,user_id) values(#{channelTitle}, #{channelDetail}, #{userId})" })
    @Options(useGeneratedKeys = true, keyProperty = "channelId")
    int insertChannel(Channel channel);

    /**
     * 储存频道的主题标签数据
     * @param channelId 频道ID
     * @param hashtag 主题标签数据(主题标签ID:hashtagId +主题标签名: hashtagName)
     * @return
     */
    @Insert({"INSERT INTO channel_hashtag(channel_id, hashtag_id, hashtag_name) VALUES (#{channelId}, #{hashtag.hashtagId}, #{hashtag.hashtagName})"})
    int insertChannleHashtag(@Param("channelId") Integer channelId, @Param("hashtag") Hashtag hashtag);


    /**
     * 添加用户订阅频道记录
     * @param channelId
     * @param userId
     * @return
     */
    @Insert({"INSERT INTO channel_subscription(channel_id,user_id) VALUES (#{channelId},#{userId})"})
    int insertChannelSubscription(Integer userId,Integer channelId);


    /**
     * 根据频道ID更新对应频道的封面图片路径。
     * @param channelId，作为查询条件。
     * @param channePicurl 作为新头像图片文件路径
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE channel SET channel_picture = #{channePicurl} WHERE channel_id = #{channelId}")
    int updateChannelPicurlByChannelId(Integer channelId, String channePicurl);

    /**
     * 根据频道ID更新对应频道的详情内容。
     * @param channelId，作为查询条件。
     * @param channelDetile 频道详情内容
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE channel SET channel_detail = #{channelDetile} WHERE channel_id = #{channelId}")
    int updateChannelDetailByChannelId(Integer channelId, String channelDetile);

    /**
     * 根据频道ID更新对应频道的标题。
     * @param channelId，作为查询条件。
     * @param channelTitle 频道标题
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE channel SET channel_title = #{channelTitle} WHERE channel_id = #{channelId}")
    int updateChannelTitleByChannelId(Integer channelId, String channelTitle);

    /**
     * 更新频道的上一次节目更新时间
     * @param channelId
     * @return
     */
    @Update("UPDATE channel SET channel_update_time = NOW() WHERE channel_id=#{channelId}")
    int updateChannelUpdateTimeByChannelId(Integer channelId);

    /**
     * 更新界面订阅数
     * @param channelId
     * @param num
     * @return
     */
    @Update("UPDATE channel SET channel_subscribe = channel_subscribe + #{num} WHERE channel_id=#{channelId}")
    int updateChannelSubscribeByChannelId(Integer channelId, Integer num);

    /**
     * 根据频道ID删除对应的频道。
     * @param channelId 频道ID作为查询条件。
     * @return 被修改的行数,期望为1,错误为0
     */
    @Delete("DELETE FROM channel  WHERE channel_id = #{channelId}")
    int deleteChannelByChannelId(Integer channelId);


    /**
     * 根据频道ID删除频道的所有主题标签数据记录。
     * @param channelId 频道ID作为查询条件。
     * @return 被修改的行数,期望为1+,错误为0
     */
    @Delete("DELETE FROM channel_hashtag  WHERE channel_id = #{channelId}")
    int deleteChannelHashtagByChannelId(Integer channelId);

    /**
     * 删除用户订阅频道记录
     * @param channelId
     * @param userId
     * @return
     */
    @Delete("DELETE FROM channel_subscription WHERE channel_id=#{channelId} AND user_id=#{userId}")
    int deleteChannelSubscriptionById(Integer userId,Integer channelId);

}
