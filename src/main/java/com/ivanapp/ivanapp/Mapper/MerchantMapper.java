package com.ivanapp.ivanapp.Mapper;

import com.ivanapp.ivanapp.Pojo.TMerchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/13 12:23
 * 商家用户操作接口
 */
@Component
@Mapper
public interface MerchantMapper {
    /**
     * 后台管理登录操作
     * @param merAccount
     * @param merPassword
     * @return
     */
    @Select("select * from t_merchant where merAccount=#{merAccount} and merPassword=#{merPassword}")
    TMerchant merLogin(String merAccount,String merPassword);

    /**
     * 获取商家信息
     * 一家店直接获取
     * @return
     */
    @Select("select * from t_merchant")
    TMerchant getMerInfo();

    /**
     * 修改店家信息
     * @param merAddress
     * @param email
     * @param merPhone
     * @param merInfo
     * @param mId
     * @return
     */
    @Update("update t_merchant set merAddress=#{merAddress},email=#{email},merPhone=#{merPhone},merInfo=#{merInfo} where mId=#{mId}")
    int upMerInfo(String merAddress,String email,String merPhone,String merInfo,int mId);

    /**
     * 修改登录信息 账号/密码
     * @param merAccount
     * @param merPassword
     * @param mId
     * @return
     */
    @Update("update t_merchant set merAccount=#{merAccount},merPassword=#{merPassword} where mId=#{mId}")
    int upLoginInfo(String merAccount,String merPassword,int mId);
}
