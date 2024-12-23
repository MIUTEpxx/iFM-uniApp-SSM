package com.pxx.ifmserver.entity.dto;

import lombok.Data;

@Data
public class SearchHistory {
    //搜索记录id
    private Integer searchHistoryId;
    //搜索关键词
    private String keyword;
    //用户id
    private Integer userId;
}
