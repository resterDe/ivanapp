package com.ivanapp.ivanapp.Service.impl;

import com.ivanapp.ivanapp.Mapper.MerchantMapper;
import com.ivanapp.ivanapp.Mapper.OrderMapper;
import com.ivanapp.ivanapp.Pojo.TMerchant;
import com.ivanapp.ivanapp.Pojo.TOrder;
import com.ivanapp.ivanapp.Service.AdminService;
import com.ivanapp.ivanapp.common.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/9 12:54
 * 管理员管理实现
 * 1、查询全部预约信息（分页显示）
 * 2、预约电话查询相应的用户预约信息
 * 3、查询未拍摄 预约信息 photoState = 0
 * 4、查询已取片 预约信息 photoState = 1 && tfetch = 1
 * 5、未取片 信息 photoState = 1 && tfetch = 0
 * 6、已取消 信息 photoState = 2
 * 7、有评价 信息 evaluate != null && evaluate != ''
 * 8、根据oid查询单个预约信息
 * 9、编辑预约信息（a摄影状态、b取片状态）
 * 10、删除指定预约信息
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 获取全部预约信息
     * @return
     */
    @Override
    public ResponseMessage getOrderAllInfo(int pages, int limit) {
        List<TOrder> orderList = orderMapper.getOrderAllInfo(pages, limit);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 根据联系方式
     * 获取用户预约信息
     * @param pages
     * @param limit
     * @param makePhone 联系方式 严格对应 无模糊搜索
     * @return
     */
    @Override
    public ResponseMessage getOrderInfoByPhone(int pages, int limit, String makePhone) {
        List<TOrder> orderList = orderMapper.getOrderInfoByPhone(pages, limit, makePhone);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 获取未拍摄预约信息
     * @param photoState = 0
     * @return
     */
    @Override
    public ResponseMessage getOrderNoPhoto(int pages, int limit, int photoState) {
        List<TOrder> orderList = orderMapper.getOrderNoPhoto(pages, limit, photoState);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 查询已取片预约信息
     * @param photoState = 1
     * @param tfetch = 1
     * @return
     */
    @Override
    public ResponseMessage getOrderIsFetch(int pages, int limit, int photoState, int tfetch) {
        List<TOrder> orderList = orderMapper.getOrderIsFetch(pages, limit, photoState,tfetch);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 获取未取片预约信息
     * @param photoState  = 1
     * @param tfetch = 0
     * @return
     */
    @Override
    public ResponseMessage getOrderNoFetch(int pages, int limit, int photoState, int tfetch) {
        List<TOrder> orderList = orderMapper.getOrderNoFetch(pages, limit, photoState,tfetch);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 获取已取消预约信息
     * @param photoState = 2
     * @return
     */
    @Override
    public ResponseMessage getNoOrderInfo(int pages, int limit, int photoState) {
        List<TOrder> orderList = orderMapper.getNoOrderInfo(pages, limit, photoState);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 获取单个预约信息 根据oId
     * @param oId
     * @return
     */
    @Override
    public ResponseMessage getOrderOneInfo(int oId) {
        TOrder orders = orderMapper.getOrderOneInfo(oId);
        if (orders == null)
            return ResponseMessage.createByErrorMessage("查询失败，请联系管理员100");
        else
            return ResponseMessage.createBySuccess(orders);
    }

    /**
     * 获取有评价的预约信息
     *  evaluate
     * @return
     */
    @Override
    public ResponseMessage getOrderEvaluate(int pages, int limit) {
        List<TOrder> orderList = orderMapper.getOrderEvaluate(pages, limit);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("count",orderList.size());
        hashMap.put("data",orderList);
        return ResponseMessage.createBySuccess(hashMap);
    }

    /**
     * 设置摄影状态为已摄影 photoState = 1
     * @param photoState = 1
     * @param oId
     * @return
     */
    @Override
    public ResponseMessage upPhotoState(int photoState, int oId) {
        int status = orderMapper.upPhotoState(photoState,oId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("修改失败，请联系管理员101");
    }

    /**
     * 设置取片状态为已取片 tfetch = 1
     * @param tfetch
     * @param oId
     * @return
     */
    @Override
    public ResponseMessage upTfetchState(int tfetch, int oId) {
        int status = orderMapper.upTfetchState(tfetch,oId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("修改失败，请联系管理员102");
    }

    /**
     * 删除对应的预约信息
     * @param oId
     * @return
     */
    @Override
    public ResponseMessage delOrderById(int oId) {
        int status = orderMapper.delOrderById(oId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("修改失败，请联系管理员103");
    }

    /**
     * 后台管理登录
     * @param merAccount
     * @param merPassword
     * @return
     */
    @Override
    public TMerchant merLogin(String merAccount, String merPassword) {
        TMerchant merchant = merchantMapper.merLogin(merAccount, merPassword);
        return merchant;
    }

    /**
     * 获取商家信息
     * @return
     */
    @Override
    public ResponseMessage getMerInfo() {
        TMerchant merchants = merchantMapper.getMerInfo();
        if (merchants == null)
            return ResponseMessage.createByError();
        else
            return ResponseMessage.createBySuccess(merchants);
    }

    /**
     * 更新商家信息
     * @param merAddress
     * @param email
     * @param merPhone
     * @param merInfo
     * @param mId
     * @return
     */
    @Override
    public ResponseMessage upMerInfo(String merAddress, String email, String merPhone, String merInfo, int mId) {
        int status = merchantMapper.upMerInfo(merAddress, email, merPhone, merInfo, mId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError();
    }

    /**
     * 修改账号密码
     * @param merAccount
     * @param merPassword
     * @param mId
     * @return
     */
    @Override
    public ResponseMessage upLoginInfo(String merAccount, String merPassword, int mId) {
        int status = merchantMapper.upLoginInfo(merAccount, merPassword, mId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError();
    }
}
