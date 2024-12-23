package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.SearchHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SearchHistoryMapper {

    /**
     * 根据id获取搜索记录
     * @param searchHistoyId
     * @return
     */
    @Select("SELECT * FROM search_history WHERE search_history_id = #{searchHistoryId}" )
    SearchHistory getSearchHistoyBySearchHistoyId(Integer searchHistoyId);

    /**
     * 根据用户id获取搜索记录
     * @param userId
     * @return
     */
    @Select("SELECT * FROM search_history WHERE user_id = #{userId} ORDER BY gmt_modified DESC" )
    List<SearchHistory> listSearchHistoyByUserId(Integer userId);

    /**
     * 查询用户搜索记录数
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM `search_history` WHERE `user_id` = #{userId}")
    int countSearchHistoryByUserId(@Param("userId") Integer userId);

    /**
     * 删除用户最旧的num条搜索记录
     * @param userId
     * @param num
     * @return
     */
    @Delete({
            "DELETE FROM searchhistory WHERE userid = #{userId} AND gmt_modified IN (",
            "  SELECT gmt_modified FROM (",
            "    SELECT gmt_modified FROM searchhistory WHERE userid = #{userId} ORDER BY gmt_modified ASC LIMIT 0, #{num}",
            "  ) AS subquery",
            ")"
    })
    int deleteOldSearchHistory(Integer userId, int num);

    /**
     * 检查用户是否已经有该关键词的搜索记录
     * @param userId
     * @param keyword
     * @return
     */
    @Select("SELECT * FROM search_history WHERE user_id = #{userId} AND keyword = #{keyword}")
    SearchHistory checkSearchHistory(Integer userId, String keyword);

    /**
     * 添加搜索历史记录
     * @param userId
     * @param keyword
     */
    @Insert("INSERT INTO search_history(user_id,keyword) VALUES (#{userId},#{keyword})")
    void insertSearchHistory(Integer userId, String keyword);

    /**
     * 若关键词keyword的搜索记录已存在,则更新其时间
     * @param userId
     * @param keyword
     * @return 更新的行数(0:不存在符合条件的记录, 1:存在此记录并对此记录时间进行更新)
     */
    @Update("UPDATE search_history SET gmt_modified = CURRENT_TIMESTAMP WHERE user_id = #{userId} AND keyword = #{keyword}")
    int updateSearchHistoryIfExist(Integer userId, String keyword);

    /**
     * 删除某条历史记录
     * @param searchHistoryId
     * @return
     */
    @Delete("DELETE FROM search_history WHERE search_history_id = #{searchHistoryId}")
    int deleteSearchHistory(Integer searchHistoryId);

    /**
     * 删除用户所有历史记录
     * @param userId
     * @return
     */
    @Delete("DELETE FROM search_history WHERE user_id = #{userId}")
    int deleteSearchHistoryByUserId(Integer userId);
}
