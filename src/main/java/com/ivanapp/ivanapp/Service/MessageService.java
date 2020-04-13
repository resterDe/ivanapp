package com.ivanapp.ivanapp.Service;

import com.ivanapp.ivanapp.common.ResponseMessage;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/2 18:44
 * 小程序页面数据管理服务接口
 */
public interface MessageService {
    // 读取商家信息
    ResponseMessage getMerchantInfo();

    // 读取套餐全部信息
    ResponseMessage getPhotoLists();

    // 根据id读取套餐对应的信息 展示图 服务详情 规格
    ResponseMessage getPhotoAllInfoByPid(int pId);

    // 根据pId获取套餐对应规格信息
    ResponseMessage getSpecificationInfoByPid(int pId);

    // 新增预约订单 支付后
    ResponseMessage addOrder(int pId,String openId,String speName,String makeDate,int photoState,int postState,int tfetch,String makeName,
                             String makePhoto,String makeSex,double payPrice,String sendSite,String evaluate);

    // 获取用户订单信息
    ResponseMessage getOrderByOpenId(String openId);

    // 取消订单 修改摄影状态photoState为3
    ResponseMessage cancelOrder(int photoState,int oId);

    // 用户评价订单
    ResponseMessage userEvaluate(int oId,String evaluate);

    // 根据speId删除对应规格
    ResponseMessage delSpeById(int speId);

    // 添加一个规格
    ResponseMessage addSpe(int pId,String speName,double price);

    // 修改封面
    ResponseMessage upCoverImg(int pId,String coverImg);

    // 清空原有展示图
    ResponseMessage delShowList(int pId);

    // 添加展示图
    ResponseMessage addShowList(int pId,String imgUrl);

    // 修改套餐详细信息
    ResponseMessage upSerInfo(String modelling,String shoot,String anaphase,String plot,String afterSale,String explain,int pId);
}
