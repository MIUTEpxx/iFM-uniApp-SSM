package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.Broadcast;
import com.pxx.ifmserver.entity.dto.BroadcastFavorite;
import com.pxx.ifmserver.entity.dto.BroadcastHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BroadcastMapper {

    /**
     * 获取所有节目的数据。
     * @return 返回一个包含所有节目数据的List<Broadcast>列表。
     */
    @Select("SELECT * FROM broadcast")
    List<Broadcast> listBroadcast();

    /**
     * 根据节目id获取一个节目数据
     * @param broadcastId
     * @return
     */
    @Select("SELECT * FROM broadcast WHERE broadcast_id = #{broadcastId}")
    Broadcast getBroadcastByBroadcastId(Integer broadcastId);

    /**
     * 根据频道id获取该频道的所有节目
     * @param channelId
     * @return
     */
    @Select("SELECT * FROM broadcast WHERE channel_id = #{channelId}")
    List<Broadcast> listBroadcastByChannelId(Integer channelId);

    /**
     * 根据关键词模糊查询节目记录
     * @param keyWord 关键词
     * @return 匹配的节目列表
     */
    @Select("SELECT * FROM broadcast WHERE broadcast_title LIKE CONCAT('%', #{keyWord}, '%') " +
            "OR broadcast_detail LIKE CONCAT('%', #{keyWord}, '%')")
    List<Broadcast> listBroadcastByKeyWord(@Param("keyWord") String keyWord);

    /**
     * 根据用户ID,查询用户创建的节目
     * @param userId 用户ID
     * @return 节目数据列表
     */
    @Select("SELECT * FROM broadcast WHERE user_id = #{userId}")
    List<Broadcast> listBroadcastByUserId(Integer userId);

    /**
     * 查询节目收藏表中,是否有用户收藏该节目的记录
     * @param broadcastId 节目ID
     * @param userId 用户ID
     * @return true/false
     */
    @Select("SELECT EXISTS(" +
            "SELECT 1 FROM broadcast_favorite WHERE user_id=#{userId} AND broadcast_id=#{broadcastId} " +
            ") AS has_favorite;")
    boolean checkBroadcastFavoriteById( Integer userId,Integer broadcastId);

    /**
     * 查询节目收听历史表中,是否有用户收听该节目的历史记录
     * @param broadcastId 节目ID
     * @param userId 用户ID
     * @return true/false
     */
    @Select("SELECT EXISTS(" +
            "SELECT 1 FROM broadcast_history WHERE user_id=#{userId} AND broadcast_id=#{broadcastId} " +
            ") AS has_history;")
    boolean checkBroadcastHistoryById( Integer userId,Integer broadcastId);

    /**
     * 查询节目收藏表中,用户ID收藏的节目
     * @param userId 用户id
     * @return 节目ID列表
     */
    @Select("SELECT * FROM broadcast_favorite WHERE user_id=#{userId}")
    List<BroadcastFavorite> listFavoriteBroadcastByUserId(Integer userId);

    /**
     * 查询节目收听历史表中,该用户对于该节目的收听历史记录
     * @param userId 用户id
     * @return 节目ID列表
     */
    @Select("SELECT * FROM broadcast_history WHERE user_id=#{userId} AND broadcast_id=#{broadcastId}")
    BroadcastHistory getHistoryBroadcastByUserId(Integer userId,Integer broadcastId);

    /**
     * 查询节目收听历史表中,用户ID收听过的节目ID和上次播放时间等
     * @param userId 用户id
     * @return 节目ID列表
     */
    @Select("SELECT * FROM broadcast_history WHERE user_id=#{userId}")
    List<BroadcastHistory> listHistoryBroadcastByUserId(Integer userId);

    /**
     * 获取 hour 小时内创建的节目 取前num个
     * @return 节目列表
     */
    @Select("SELECT * FROM broadcast WHERE gmt_create > (NOW() - INTERVAL #{hour} HOUR)  LIMIT #{num}")
    List<Broadcast> listBroadcastCtreateInHour(int hour, int num);


    /**
     * 添加新节目
     * @param broadcast 节目数据。
     * @return 表示受影响的行数。在执行插入操作时，这个值通常是1，表示一行被成功插入
     * useGeneratedKeys: 当设置为 true 时，这告诉 MyBatis 在执行插入操作后，尝试检索数据库自动生成的键（通常是自增的主键）。这个功能通常用于在插入记录后获取该记录的自增 broadcast_id。
     * keyProperty: 这个属性指定了应该将检索到的自动生成的键值设置到目标对象的哪个属性上。
     */
    @Insert({ "insert into broadcast(broadcast_title, broadcast_detail,broadcast_duration,channel_id,user_id) " +
            "values(#{broadcastTitle}, #{broadcastDetail},#{broadcastDuration},#{channelId}, #{userId})" })
    @Options(useGeneratedKeys = true, keyProperty = "broadcastId")
    int insertBroadcast(Broadcast broadcast);

    /**
     * 添加用户收藏节目记录
     * @param broadcastId
     * @param userId
     * @return
     */
    @Insert({"INSERT INTO broadcast_favorite(broadcast_id,user_id) VALUES (#{broadcastId},#{userId})"})
    int insertBroadcastFavorite(Integer userId,Integer broadcastId);

    /**
     * 添加收听历史记录
     * @param userId
     * @param broadcastId
     * @param lastListeningDuration
     * @return
     */
    @Insert({"INSERT INTO broadcast_history(broadcast_id,user_id,history_time,last_listen_duration) " +
            "VALUES (#{broadcastId},#{userId},NOW(),#{lastListeningDuration})"})
    int insertBroadcastHistory(Integer userId, Integer broadcastId,Integer lastListeningDuration);

    /**
     * 根据节目ID更新对应节目的封面图片路径。
     * @param broadcastId，作为查询条件。
     * @param broadcastPicurl 作为新头像图片文件路径
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE broadcast SET broadcast_picture = #{broadcastPicurl} WHERE broadcast_id = #{broadcastId}")
    int updateBroadcastPicurlByBroadcastId(Integer broadcastId, String broadcastPicurl);

    /**
     * 根据节目ID更新对应节目的封面图片路径。
     * @param broadcastId，作为查询条件。
     * @param broadcastAudio 作为新音频文件的路径
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE broadcast SET broadcast_audio = #{broadcastAudio} WHERE broadcast_id = #{broadcastId}")
    int updateBroadcastAudioByBroadcastId(Integer broadcastId, String broadcastAudio);

    /**
     * 修改节目音频时长(单位秒)
     * @param broadcastId
     * @param broadcastDuration
     * @return
     */
    @Update("UPDATE broadcast SET broadcast_duration = #{broadcastDuration} WHERE broadcast_id = #{broadcastId}")
    int updateBroadcastDurationByBroadcastId(Integer broadcastId, Long broadcastDuration);

    /**
     * 根据节目ID更新对应节目的详情内容。
     * @param broadcastId，作为查询条件。
     * @param broadcastDetail 节目详情内容
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE broadcast SET broadcast_detail = #{broadcastDetail} WHERE broadcast_id = #{broadcastId}")
    int updateBroadcastDetailByChannelId(Integer broadcastId, String broadcastDetail);

    /**
     *  根据节目ID修改对应节目标题
     * @param broadcastId
     * @param broadcastTitle
     * @return
     */
    @Update("UPDATE broadcast SET broadcast_title =#{broadcastTitle} WHERE broadcast_id = #{broadcastId}")
    int updateBroadcastTitleByChannelId(Integer broadcastId, String broadcastTitle);

    /**
     * 更改节目收藏量
     * @param broadcastId
     * @param num
     * @return
     */
    @Update(("UPDATE broadcast SET broadcast_collection_count = broadcast_collection_count + #{num} WHERE broadcast_id = #{broadcastId}"))
    int updateBroadcastCollectionCount(Integer broadcastId, Integer num);

    /**
     * 增加节目播放量
     * @param broadcastId
     * @return
     */
    @Update(("UPDATE broadcast SET broadcast_play_count = broadcast_play_count + 1 WHERE broadcast_id = #{broadcastId}"))
    int updateBroadcastPlayCount(Integer broadcastId);

    /**
     * 更改界节目关联帖子量
     * @param broadcastId
     * @param num
     * @return
     */
    @Update(("UPDATE broadcast SET broadcast_post_count = broadcast_post_count + #{num} WHERE broadcast_id = #{broadcastId}"))
    int updateBroadcastPostCount(Integer broadcastId, Integer num);

    /**
     * 更新节目收听历史记录
     * @param broadcastId
     * @return
     */
    @Update(("UPDATE broadcast_history SET last_listen_duration = #{lastListenDuration} ,history_time = NOW() WHERE broadcast_id = #{broadcastId} AND user_id = #{userId}"))
    int updateBroadcastHistory(Integer userId,Integer broadcastId,Integer lastListenDuration);


    /**
     * 根据节目ID删除对应的节目。
     * @param broadcastId 节目ID作为查询条件。
     * @return 被修改的行数,期望为1,错误为0
     */
    @Delete("DELETE FROM broadcast  WHERE broadcast_id = #{broadcastId}")
    int deleteBroadcast(Integer broadcastId);
    @Delete(" DELETE FROM broadcast_history WHERE broadcast_id = #{broadcastId}")
    int deleteBroadcastHistoryByBroadcastId(Integer broadcastId);
    @Delete(" DELETE FROM broadcast_favorite WHERE broadcast_id = #{broadcastId}")
    int deleteBroadcastFavoriteByBroadcastId(Integer broadcastId);



    /**
     * 删除节目收听历史记录。
     * @param broadcastId
     * @param userId
     * @return 被修改的行数,期望为1,错误为0
     */
    @Delete("DELETE FROM broadcast_history  WHERE broadcast_id = #{broadcastId} AND user_id=#{userId}")
    int deleteBroadcastHistory(Integer userId,Integer broadcastId);

    /**
     * 删除节目收藏记录。
     * @param broadcastId
     * @param userId
     * @return 被修改的行数,期望为1,错误为0
     */
    @Delete("DELETE FROM broadcast_favorite  WHERE broadcast_id = #{broadcastId} AND user_id= #{userId}")
    int deleteBroadcastFavorite(Integer userId,Integer broadcastId);



}
