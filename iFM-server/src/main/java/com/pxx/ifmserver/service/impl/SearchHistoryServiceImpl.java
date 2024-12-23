package com.pxx.ifmserver.service.impl;

import com.pxx.ifmserver.entity.dto.Reply;
import com.pxx.ifmserver.entity.dto.SearchHistory;
import com.pxx.ifmserver.entity.dto.User;
import com.pxx.ifmserver.entity.vo.ReplyVO;
import com.pxx.ifmserver.mapper.SearchHistoryMapper;
import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public Result listSearchHistoryByUserId(Integer userId) {
        Map<String, Object> data = new HashMap<>();
        try{
            //返回用户搜索记录, 按时间排序
            List<SearchHistory> searchHistoryList = searchHistoryMapper.listSearchHistoyByUserId(userId);
            data.put("searchHistoryList", searchHistoryList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,搜索记录获取失败",data);
        }
    }

    @Override
    public Result saveSearchHistory(Integer userId, String keyword) {
        Map<String, Object> data = new HashMap<>();
        try{
            //检查是否已有该搜索内容的历史记录 有则更新该搜索记录的时间, 无则添加
            if (searchHistoryMapper.updateSearchHistoryIfExist(userId, keyword) == 0) {
                //无则添加
                searchHistoryMapper.insertSearchHistory(userId,keyword);
            }
            //检查用户的搜索记录是否超过了20条
            final int maxHistorySize = 20;
            int currentHistorySize = searchHistoryMapper.countSearchHistoryByUserId(userId);
            // 如果当前记录数量超过最大限制，则删除超出部分的旧记录
            if (currentHistorySize > maxHistorySize) {
                int recordsToDelete = currentHistorySize - maxHistorySize;
                searchHistoryMapper.deleteOldSearchHistory(userId,  recordsToDelete);
            }
            //返回最新的用户的所有搜索记录
            List<SearchHistory> searchHistoryList = searchHistoryMapper.listSearchHistoyByUserId(userId);
            data.put("searchHistoryList", searchHistoryList);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,搜索记录保存失败",data);
        }
    }

    @Override
    public Result deleteSearchHistoryBySearchHistoryId(Integer userId, Integer searchHistoryId) {
        Map<String, Object> data = new HashMap<>();
        try{
            //检查是否有权删除
            SearchHistory searchHistory = searchHistoryMapper.getSearchHistoyBySearchHistoyId(searchHistoryId);
            if(!userId.equals(searchHistory.getUserId())) {
                data.put("error","无权删除此搜索记录");
                return new Result(false,20002,"删除失败",data);
            }
            //删除
            searchHistoryMapper.deleteSearchHistory(searchHistoryId);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,搜索记录获取失败",data);
        }
    }

    @Override
    public Result deleteSearchHistoryByUserId(Integer userId) {
        Map<String, Object> data = new HashMap<>();
        try{
            searchHistoryMapper.deleteSearchHistoryByUserId(userId);
            return Result.ok().data(data);
        }catch (RuntimeException e){
            data.put("error", e.getMessage());
            return new Result(false,20001,"未知错误,搜索记录获取失败",data);
        }
    }
}
