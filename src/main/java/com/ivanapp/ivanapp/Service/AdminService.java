package com.ivanapp.ivanapp.Service;
import com.ivanapp.ivanapp.Pojo.TMerchant;
import com.ivanapp.ivanapp.Pojo.TOrder;
import com.ivanapp.ivanapp.common.ResponseMessage;

import java.util.List;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/9 12:50
 * 管理员管理操作接口
 */
public interface AdminService {
    // 获取全部预约信息
    ResponseMessage getOrderAllInfo(int pages, int limit);

    // 根据用户联系方式查询用户预约信息
    ResponseMessage getOrderInfoByPhone(int pages, int limit,String makePhone);

    // 获取未拍摄预约信息
    ResponseMessage getOrderNoPhoto(int pages, int limit, int photoState);

    // 查询已取片预约信息
    ResponseMessage getOrderIsFetch(int pages, int limit, int photoState,int tfetch);

    // 获取未取片预约信息
    ResponseMessage getOrderNoFetch(int pages, int limit, int photoState,int tfetch);

    // 获取已取消预约信息
    ResponseMessage getNoOrderInfo(int pages, int limit, int photoState);

    // 查询单个预约的信息
    ResponseMessage getOrderOneInfo(int oId);

    // 查询有评价的预约信息
    ResponseMessage getOrderEvaluate(int pages, int limit);

    // 设置摄影状态为已拍摄 photoState = 1
    ResponseMessage upPhotoState(int photoState,int oId);

    // 设置取片状态为已取片 tfetch = 1
    ResponseMessage upTfetchState(int tfetch,int oId);

    // 删除对应的预约信息
    ResponseMessage delOrderById(int oId);

    // 后台管理登录
    TMerchant merLogin(String merAccount, String merPassword);

    // 获取商家信息
    ResponseMessage getMerInfo();

    // 修改商家信息
    ResponseMessage upMerInfo(String merAddress,String email,String merPhone,String merInfo,int mId);

    // 修改登录信息 账号/密码
    ResponseMessage upLoginInfo(String merAccount,String merPassword,int mId);
}
