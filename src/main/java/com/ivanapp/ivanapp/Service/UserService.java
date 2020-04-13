package com.ivanapp.ivanapp.Service;

import com.ivanapp.ivanapp.Pojo.TUser;
import com.ivanapp.ivanapp.common.ResponseMessage;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/2 14:56
 * 用户服务接口
 */
public interface UserService {
    /**
     * 前面可以依据封装的sessionkey信息判断登陆状态
     * 根据openId判断是否已经绑定微信
     * 是：返回用户数据
     * @param openId
     */
    ResponseMessage isLogin(String openId);

    /**
     * 根据小程序的code向微信服务请求获取openId
     * @param code
     * @return
     */
    ResponseMessage getOpenId(String code);

    /**
     * 将openId入库，且保存用户基础信息
     * 注册用户
     * isLogin否：将openId入库，并返回用户数据
     * @param openId
     */
    ResponseMessage bingUser(String openId,String userName,String sex,String birthday);

    /**
     * 更新用户基础信息,根据openId
     * @param openId
     */
    ResponseMessage updateUserInfo(String openId,String userName,String sex,String birthday);

    /**
     * 根据openId查询用户订单
     * @param openId
     */
    ResponseMessage getOrderByOpenId(String openId);
}
