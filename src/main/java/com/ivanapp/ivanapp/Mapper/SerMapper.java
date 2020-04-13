package com.ivanapp.ivanapp.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/12 21:20
 * 套餐具体信息操作接口
 */
@Component
@Mapper
public interface SerMapper {
    /**
     * 更改信息
     */
    @Update("update t_servicepar set modelling=#{modelling},shoot=#{shoot},anaphase=#{anaphase},plot=#{plot},afterSale=#{afterSale},`explain`=#{explain} where pId=#{pId}")
    int upSerInfo(String modelling,String shoot,String anaphase,String plot,String afterSale,String explain,int pId);
}
