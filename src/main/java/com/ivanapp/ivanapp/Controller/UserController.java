package com.ivanapp.ivanapp.Controller;

import com.ivanapp.ivanapp.Pojo.TOrder;
import com.ivanapp.ivanapp.Service.UserService;
import com.ivanapp.ivanapp.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.ivanapp.ivanapp.common.LoginSessionMap.containsKey;
import static com.ivanapp.ivanapp.common.LoginSessionMap.put;
import static com.ivanapp.ivanapp.common.LoginSessionMap.get;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/2 16:36
 * 用户功能控制
 * 1、检查是否登录
 * 2、获取openId，绑定登录，信息入库，绑定一个新用户
 * 3、初始化获取用户信息
 * 4、更新用户信息
 * 5、获取用户订单信息
 */
@Slf4j
@RestController
@RequestMapping("user-api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 检查有没有登陆
     * 不怎用
     * @param loginSession session
     * @return ResponseMessage
     */
    @PostMapping("check-session")
    public ResponseMessage checkSession(String loginSession) {
        if (containsKey(loginSession)) {
            return ResponseMessage.createBySuccess();
        } else {
            return ResponseMessage.createByError();
        }
    }

    /**
     * 用户登录->接收小程序code转发微信服务登录验证
     * 接收用户基础信息
     * 获取微信服务openId跟session_key，并保存到sessionMap中
     * 根据openId判断是否已经存在该用户
     * 是：直接返回用户信息以及sessionMap的key
     * 否：将openId跟用户基础信息入库保存，并将sessionMap的key返回小程序
     */
    @PostMapping("userLogin")
    public ResponseMessage userLogin(String code,String userName,String sex, String birthday){
        // 验证获取openId
        ResponseMessage responseMessage = userService.getOpenId(code);
        // 判断获取是否成功
        if (responseMessage.isSuccess()){
            //UUID生成唯一随机数，重命名
            String newLoginSession = UUID.randomUUID().toString().replaceAll("-", "");
            log.trace("session: " + newLoginSession);
            // 保存在hashmap，唯一随机数，以及data-->openid，sessionKey
            put(newLoginSession, responseMessage.getData());
            //取出openid
            String openId = ((Map<String, String>) responseMessage.getData()).get("openId");
            // 判断是否存在用户
            ResponseMessage responseMessageInfo = userService.isLogin(openId);
            if (responseMessageInfo.isSuccess()) {
                // 存在用户 直接返回sessionkey跟用户信息
                HashMap<String,Object> hashMaps = new HashMap<>();
                hashMaps.put("userInfo",responseMessageInfo.getData());
                hashMaps.put("newSession",newLoginSession);
                return ResponseMessage.createBySuccess(hashMaps);
            } else {
                // 不存在用户 openId入库
                if (userService.bingUser(openId,userName,sex,birthday).isSuccess())
                    // 返回查询的信息
                    return ResponseMessage.createBySuccess(userService.isLogin(openId).getData());
                else
                    return ResponseMessage.createByErrorMessage("请与管理员联系001");
            }
        }
        return responseMessage;
    }

    /**
     * 获取用户信息 小程序依据sessionkey
     * 取出openId
     * 这里不一定用得上
     * @param newSessionKey
     * @return
     */
    @GetMapping("getUserInfo")
    public ResponseMessage getUserInfo(String newSessionKey){
        String openId = get(newSessionKey).get("openId");
        ResponseMessage responseMessage = userService.isLogin(openId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage("请与管理员联系002");
    }

    /**
     * 更新用户信息
     * 用处也不大好像
     * @param openId
     * @param userName
     * @param sex
     * @param birthday
     * @return
     */
    @PutMapping("updateUserInfo")
    public ResponseMessage updateUserInfo(String openId, String userName, String sex, String birthday){
        ResponseMessage responseMessage = userService.updateUserInfo(openId, userName, sex, birthday);
        if (responseMessage.isSuccess())
            return ResponseMessage.createByErrorMessage("更新成功");
        else
            return ResponseMessage.createByError("请与管理员联系003");
    }

    /**
     * 根据用户openId获取用户订单信息
     * @param openId
     * @return
     */
    @GetMapping("getOrderByOpenId")
    public ResponseMessage getOrderByOpenId(String openId){
        ResponseMessage responseMessage = userService.getOrderByOpenId(openId);
        if (responseMessage.isSuccess())
            return ResponseMessage.createBySuccess(responseMessage.getData());
        else
            return ResponseMessage.createByErrorMessage("该用户无订单");
    }
}
