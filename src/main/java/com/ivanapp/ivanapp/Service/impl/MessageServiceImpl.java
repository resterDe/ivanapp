package com.ivanapp.ivanapp.Service.impl;

import com.ivanapp.ivanapp.Mapper.*;
import com.ivanapp.ivanapp.Pojo.*;
import com.ivanapp.ivanapp.Service.MessageService;
import com.ivanapp.ivanapp.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/2 18:46
 * 小程序页面数据管理实现
 * 1、商家信息初始化
 * 2、初始化图片资源->套餐信息数据(封面)
 * 3、获取套餐详细信息
 * 4、生成新订单入库
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SpeMapper speMapper;

    @Autowired
    private ShowMapper showMapper;

    @Autowired
    private SerMapper serMapper;

    // 获取resources下的静态资源文件
    public static void main(String[] args) {
        ClassPathResource classPathResource = new ClassPathResource("/static/temp");
        System.out.println("获取的路径："+classPathResource.getPath());
        try {
            byte[] byteArray = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            System.out.println("图片二进制："+byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取商家信息
     * @return
     */
    @Override
    public ResponseMessage getMerchantInfo() {
        TMerchant merchants = photoMapper.getMerchantInfo();
        if (merchants == null)
            return ResponseMessage.createByErrorMessage("没有该套餐信息,请与管理员联系004");
        else
            return ResponseMessage.createBySuccess(merchants);
    }

    /**
     * 获取套餐基础信息
     * @return
     */
    @Override
    public ResponseMessage getPhotoLists() {
        List<TPhoto> photoLists = photoMapper.getPhotoLists();
        if (photoLists.size() == 0)
            return ResponseMessage.createByErrorMessage("请与管理员联系005");
        else
            return ResponseMessage.createBySuccess(photoLists);
    }

    /**
     * 根据套餐id获取全部信息
     * 套餐信息->展示图->服务详情
     * @param pId
     * @return
     */
    @Override
    public ResponseMessage getPhotoAllInfoByPid(int pId) {
        TPhoto photoInfo = photoMapper.getPhotoAllInfoByPid(pId);
        List<TShow> showList = photoMapper.getShowListByPid(pId);
        HashMap<String,Object> hashMaps = new HashMap<>();
        hashMaps.put("photoInfo",photoInfo);
        hashMaps.put("showList",showList);
        if (photoInfo == null)
            return ResponseMessage.createByErrorMessage("请与管理员联系006");
        else
            return ResponseMessage.createBySuccess(hashMaps);
    }

    /**
     * 根据套餐id获取对应规格信息
     * @param pId
     * @return
     */
    @Override
    public ResponseMessage getSpecificationInfoByPid(int pId) {
        List<TSpecification> specificationLists = photoMapper.getSpecificationInfoByPid(pId);
        if (specificationLists.size() == 0)
            return ResponseMessage.createByErrorMessage("无规格信息，请与管理员联系007");
        else
            return ResponseMessage.createBySuccess(specificationLists);
    }

    /**
     * 新增订单
     * @param pId 套餐id
     * @param openId 用户openId
     * @param speName 规格名
     * @param makeDate 预约时间
     * @param photoState 摄影状态 默认0 未拍摄
     * @param postState 取(寄)状态 用户选择 默认自取
     * @param tfetch 取片状态 默认0 未取 fetch为mysql关键字
     * @param makeName 预约人名称
     * @param makePhoto 预约人联系方式
     * @param makeSex 预约人性别
     * @param payPrice 支付金额
     * @param sendSite 邮寄地址 自取默认为null
     * @param evaluate 订单评价 默认null 选填
     * @return
     */
    @Override
    public ResponseMessage addOrder(int pId, String openId, String speName, String makeDate, int photoState, int postState, int tfetch, String makeName, String makePhoto, String makeSex,
                                    double payPrice, String sendSite, String evaluate) {
        int status = photoMapper.addOrder(pId, openId, speName, makeDate, photoState, postState, tfetch, makeName, makePhoto,
                makeSex, payPrice, sendSite, evaluate);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError("新增失败，请与管理员联系008");
    }

    /**
     * 获取用户预约订单
     * 可以取消
     * @param openId
     * @return
     */
    @Override
    public ResponseMessage getOrderByOpenId(String openId) {
        List<TOrder> orderList = orderMapper.getOrderByOpenId(openId);
        if (orderList.size() == 0)
            return ResponseMessage.createByErrorMessage("查询失败，请与管理员联系009");
        else
            return ResponseMessage.createBySuccess(orderList);
    }

    /**
     * 取消订单
     * @param photoState
     * @param oId
     * @return
     */
    @Override
    public ResponseMessage cancelOrder(int photoState, int oId) {
        int status = orderMapper.cancelOrder(photoState,oId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("取消失败，请与管理员联系010");
    }

    /**
     * 用户评价订单
     * @param oId
     * @param evaluate
     * @return
     */
    @Override
    public ResponseMessage userEvaluate(int oId, String evaluate) {
        int status = orderMapper.userEvaluate(oId, evaluate);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("评价失败，请与管理员联系011");
    }

    /**
     * 删除规格
     * @param speId
     * @return
     */
    @Override
    public ResponseMessage delSpeById(int speId) {
        int status = speMapper.delSpeById(speId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("删除失败，请与管理员联系012");
    }

    /**
     * 添加规格
     * @param pId
     * @param speName
     * @param price
     * @return
     */
    @Override
    public ResponseMessage addSpe(int pId, String speName, double price) {
        int status = speMapper.addSpe(pId, speName, price);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("添加失败，请与管理员联系013");
    }

    /**
     * 修改封面
     * @param pId
     * @param coverImg
     * @return
     */
    @Override
    public ResponseMessage upCoverImg(int pId, String coverImg) {
        int status = photoMapper.upCoverImg(pId, coverImg);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("修改失败，请与管理员联系014");
    }

    /**
     * 清空原有展示图
     * @param pId
     * @return
     */
    @Override
    public ResponseMessage delShowList(int pId) {
        // status表示影响行数
        showMapper.delShowList(pId);
        return ResponseMessage.createBySuccess();
    }

    /**
     * 添加展示图
     * @param pId
     * @param imgUrl
     * @return
     */
    @Override
    public ResponseMessage addShowList(int pId, String imgUrl) {
        int status = showMapper.addShowList(pId,imgUrl);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("添加失败，请与管理员联系016");
    }

    /**
     * 修改详细信息
     * @param modelling
     * @param shoot
     * @param anaphase
     * @param plot
     * @param afterSale
     * @param explain
     * @param pId
     * @return
     */
    @Override
    public ResponseMessage upSerInfo(String modelling, String shoot, String anaphase, String plot, String afterSale, String explain, int pId) {
        int status = serMapper.upSerInfo(modelling, shoot, anaphase, plot, afterSale, explain, pId);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByErrorMessage("修改失败，请与管理员联系016");
    }
}
