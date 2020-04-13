package com.ivanapp.ivanapp.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/12 20:53
 * 展示图操作
 */
@Component
@Mapper
public interface ShowMapper {
    /**
     * 添加前先删除清空原有展示图
     */
    @Delete("delete from t_show where pId=#{pId}")
    int delShowList(int pId);

    /**
     * 添加展示图
     */
    @Insert("insert into t_show(pId,imgUrl) values(#{pId},#{imgUrl})")
    int addShowList(int pId,String imgUrl);
}
