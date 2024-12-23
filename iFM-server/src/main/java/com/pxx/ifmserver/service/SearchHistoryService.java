package com.pxx.ifmserver.service;

import com.pxx.ifmserver.entity.dto.SearchHistory;
import com.pxx.ifmserver.result.Result;

public interface SearchHistoryService {

    /**
     * 根据用户Id获取用户的搜索记录
     * @param userId
     * @return
     */
    Result listSearchHistoryByUserId(Integer userId);

    /**
     * 保存搜索历史记录
     * @param userId
     * @param keyword 搜索内容
     * @return
     */
    Result saveSearchHistory(Integer userId, String keyword);

    /**
     * 删除某一条搜索记录
     * @param userId
     * @param searchHistoryId 搜索历史记录id
     * @return
     */
    Result deleteSearchHistoryBySearchHistoryId(Integer userId, Integer searchHistoryId);

    /**
     * 删除用户的所有历史记录
     * @param userId
     * @return
     */
    Result deleteSearchHistoryByUserId(Integer userId);
}
