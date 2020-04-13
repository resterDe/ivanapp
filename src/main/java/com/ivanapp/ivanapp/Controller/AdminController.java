package com.ivanapp.ivanapp.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ivanapp.ivanapp.Pojo.TMerchant;
import com.ivanapp.ivanapp.Pojo.TOrder;
import com.ivanapp.ivanapp.Service.AdminService;
import com.ivanapp.ivanapp.common.LoginSessionMap;
import com.ivanapp.ivanapp.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/9 12:57
 */
@Slf4j
@CrossOrigin // 说是解决跨域问题的（因为我页面不是在这里写的，因此会存在跨域问题）
@RestController
@RequestMapping("admin-api")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取全部预约信息
     * layui默认传递两个参数
     * @param page
     * @param limit
     */
    @GetMapping(value = "getOrderAllInfo")
    public ResponseMessage getOrderAllInfo(int page, int limit) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getOrderAllInfo(pages, limit);
        return ResponseMessage.createBySuccess("获取全部预约信息",responseMessage.getData());
    }

    /**
     * 根据联系方式指定查询用户预约信息
     * @param page
     * @param limit
     * @param makePhone
     * @return
     */
    @GetMapping(value = "getOrderInfoByPhone")
    public ResponseMessage getOrderInfoByPhone(int page, int limit,String makePhone) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getOrderInfoByPhone(pages, limit, makePhone);
        return ResponseMessage.createBySuccess("获取指定联系方式用户",responseMessage.getData());
    }
    /**
     * 获取未拍摄预约信息
     * photoState = 0
     * @return
     */
    @GetMapping("getOrderNoPhoto")
    public ResponseMessage getOrderNoPhoto(int page, int limit) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getOrderNoPhoto(pages, limit, 0);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess("获取指定联系方式用户",responseMessage.getData());
        else
            return ResponseMessage.createByError();
    }

    /**
     * 查询已取片预约信息
     *  photoState = 1
     *  tfetch = 1
     * @return
     */
    @GetMapping("getOrderIsFetch")
    public ResponseMessage getOrderIsFetch(int page, int limit) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getOrderIsFetch(pages, limit,1,1);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess("查询已取片预约信息",responseMessage.getData());
        else
            return ResponseMessage.createByError();
    }

    /**
     * 获取未取片预约信息
     *  photoState  = 1
     *  tfetch = 0
     * @return
     */
    @GetMapping("getOrderNoFetch")
    public ResponseMessage getOrderNoFetch(int page, int limit) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getOrderNoFetch(pages, limit,1,0);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess("获取未取片预约信息",responseMessage.getData());
        else
            return ResponseMessage.createByError();
    }

    /**
     * 获取已取消预约信息
     *  photoState = 2
     * @return
     */
    @GetMapping("getNoOrderInfo")
    public ResponseMessage getNoOrderInfo(int page, int limit) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getNoOrderInfo(pages, limit,2);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess("获取已取消预约信息",responseMessage.getData());
        else
            return ResponseMessage.createByError();
    }

    /**
     * 获取单个预约信息 根据oId
     *  oId
     * @return
     */
    @GetMapping("getOrderOneInfo")
    public ResponseMessage getOrderOneInfo(int oId) {
        ResponseMessage responseMessage = adminService.getOrderOneInfo(oId);
        return ResponseMessage.createBySuccess("获取已取消预约信息",responseMessage.getData());
    }

    /**
     * 获取有评价的预约信息
     * evaluate
     * @return
     */
    @GetMapping("getOrderEvaluate")
    public ResponseMessage getOrderEvaluate(int page, int limit) {
        int pages = (page - 1) * limit;
        ResponseMessage responseMessage = adminService.getOrderEvaluate(pages, limit);
        return ResponseMessage.createBySuccess("获取已取消预约信息",responseMessage.getData());
    }

    /**
     * 设置摄影状态为已摄影 photoState = 1
     * @param oId
     * @return
     */
    @PostMapping(value = "upPhotoState")
    public ResponseMessage upPhotoState(int oId) {
        return adminService.upPhotoState(1,oId);
    }

    /**
     * 设置取片状态为已取片 tfetch = 1
     * @param oId
     * @return
     */
    @PostMapping("upTfetchState")
    public ResponseMessage upTfetchState(int oId) {
        return adminService.upTfetchState(1,oId);
    }

    /**
     * 设置取片状态为已取片 tfetch = 1
     * @param oId
     * @return
     */
    @PostMapping("delOrderById")
    public ResponseMessage delOrderById(int oId) {
        return adminService.delOrderById(oId);
    }

    /**
     * 管理登录
     * @param merAccount
     * @param merPassword
     * @return
     */
    @PostMapping("merLogin")
    public ResponseMessage merLogin(HttpSession session,String merAccount,String merPassword){
        TMerchant merchant = adminService.merLogin(merAccount, merPassword);
        if (merchant != null) {
            // 登录成功 将用户信息保存到session 未写
            String sessionKey = UUID.randomUUID ().toString ().replaceAll ("-", "");
            session.setAttribute("merchantKey",merchant);
            // 因为页面不在该服务器下，因此存在跨域问题
            System.out.println("session信息"+session.getAttribute("merchantKey"));
            // 返回sessionkey
            return ResponseMessage.createBySuccess(sessionKey);
        } else {
            return ResponseMessage.createByErrorMessage("登陆失效");
        }
    }

    /**
     * 获取商家信息
     * @param
     * @return
     */
    @GetMapping("getMerInfo")
    public ResponseMessage getMerInfo(){
        ResponseMessage responseMessage = adminService.getMerInfo();
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        return ResponseMessage.createByError();
    }

    /**
     * 修改商家信息
     * @param merAddress
     * @param email
     * @param merPhone
     * @param merInfo
     * @return
     */
    @PostMapping("upMerInfo")
    public ResponseMessage upMerInfo(String merAddress, String email, String merPhone, String merInfo){
        ResponseMessage responseMessage = adminService.upMerInfo(merAddress, email, merPhone, merInfo, 1);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError();
    }

    /**
     * 修改登录信息
     * @param merAccount
     * @param merPassword
     * @return
     * 正常来说应该先获取密码判断是否正确
     * 不过现在啊嘛，算了
     */
    @PostMapping("upLoginInfo")
    public ResponseMessage upLoginInfo(String merAccount, String merPassword){
        ResponseMessage responseMessage = adminService.upLoginInfo(merAccount, merPassword, 1);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError();
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("exitLogin")
    public ResponseMessage exitLogin(HttpSession session){
        // mId是用来判断是否存在的 暂时没用
        // 先判断是否存在session 不写了，以后有缘再说
        // 直接做个样子退出↓
        session.removeAttribute("merchantKey");
        return ResponseMessage.createBySuccessMessage("成功退出登录");
    }

    @PostMapping("test")
    public ResponseMessage test (@RequestParam("file") MultipartFile file) {
        System.out.println("获取的文件对象" + file);
        return ResponseMessage.createBySuccess();
    }
}
