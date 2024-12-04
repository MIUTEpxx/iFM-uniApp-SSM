package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.Hashtag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HashtagMapper {
    /**
     * 获取所有主题标签
     * @return List<Hashtag> 主题标签列表
     */
    @Select("SELECT * FROM hashtag")
    List<Hashtag> listHash();

    @Select("SELECT * FROM hashtag WHERE hashtag_id=#{hashId}")
    Hashtag getHashtagByHashId(Integer hashId);

}
