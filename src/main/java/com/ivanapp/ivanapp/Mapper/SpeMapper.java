package com.ivanapp.ivanapp.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/12 18:47
 * 规格操作接口
 */
@Component
@Mapper
public interface SpeMapper {
    /**
     * 根据speId删除对应规格
     */
    @Delete("delete from t_specification where speId=#{speId}")
    int delSpeById(int speId);

    /**
     * 添加一个规格
     */
    @Insert("insert into t_specification(pId,speName,price) values (#{pId},#{speName},#{price})")
    int addSpe(int pId,String speName,double price);
}
