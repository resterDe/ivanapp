package com.ivanapp.ivanapp.Mapper;

import com.ivanapp.ivanapp.Pojo.TUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/1 17:06
 * 表t_user操作接口
 */
@Component
@Mapper //使用xml配置文件需要@Mapper+@@Component
public interface UserMapper {
    /**
     * 根据openId查询是否存在该用户
     */
    @Select("select * from t_user where openId=#{openId}")
    TUser isLogin(String openId);

    /**
     * 新增绑定一个用户
     * 将openId入库并且保存相应信息
     */
    @Insert("insert into t_user(openId,userName,sex,birthday) values (#{openId},#{userName},#{sex},#{birthday})")
    int bingUser(String openId,String userName,String sex,String birthday);

    /**
     * 根据openId跟新用户信息
     * 更新操作返回的是匹配到的行数，可以更改为受影响的行数
     * 成功返回1(至少匹配一行)
     */
    @Update("update t_user set userName=#{userName},sex=#{sex},birthday=#{birthday} where openId=#{openId}")
    int updateUserInfo(String openId,String userName,String sex,String birthday);
}
