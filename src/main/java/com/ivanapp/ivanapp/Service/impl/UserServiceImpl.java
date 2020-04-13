package com.ivanapp.ivanapp.Service.impl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ivanapp.ivanapp.Mapper.OrderMapper;
import com.ivanapp.ivanapp.Mapper.UserMapper;
import com.ivanapp.ivanapp.Pojo.TOrder;
import com.ivanapp.ivanapp.Pojo.TUser;
import com.ivanapp.ivanapp.Service.UserService;
import com.ivanapp.ivanapp.common.ResponseMessage;
import com.ivanapp.ivanapp.httpClient.Client;
import com.ivanapp.ivanapp.httpClient.ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ivanapp.ivanapp.config.WechatConfig.APP_ID;
import static com.ivanapp.ivanapp.config.WechatConfig.APP_SECRET;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/4/2 15:36
 * 用户逻辑实现
 * 1、判断是否已绑定
 * 2、绑定微信使用（首次）
 * 3、更新用户信息
 * 4、查询用户订单（联表查询订单信息）
 * 5、用户安全退出（清除登录状态即可）
 * 判断responseMessage.isSuccess()为true就成功
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    // 用户操作接口
    @Autowired
    private UserMapper userMapper;

    // 订单操作接口
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 判断是否已绑定、获取用户信息
     * @param openId
     * @return 用户信息
     */
    @Override
    public ResponseMessage isLogin(String openId) {
        TUser users = userMapper.isLogin(openId);
        if (users == null)
            return ResponseMessage.createByErrorMessage("不存在该用户，需要入库");
        else
            return ResponseMessage.createBySuccess("用户信息",users);
    }

    /**
     * 根据code获取openId等信息
     * @param code
     * @return
     */
    @Override
    public ResponseMessage getOpenId(String code) {
        // 创建client对象
        Client client = ClientFactory.creatClient(ClientFactory.HTTPS_POST_JSON);
        // 将获取的code 以及小程序appId，appSecret传递给第三方服务，换取openId，session_key
        String json = client.getBody("https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code");
        log.info("微信服务器返回的信息："+json);
        // 将数据封装成json格式
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        // 解析微信服务器返回的Json
        //has判断json中是否含有该属性值
        if (jsonObject.has("openid")) {
            //存在openid值，将返回的openid，sessionKey存入map
            Map<String, String> map = new HashMap<>(2);
            //转换成字符串存入
            map.put("openId", jsonObject.get("openid").getAsString());
            map.put("session_key", jsonObject.get("session_key").getAsString());
            log.info(map.get("openId"));
            //统一接口返回信息
            return ResponseMessage.createBySuccess(map);
        } else {
            // 错误信息
            return ResponseMessage.createByErrorMessage(jsonObject.get("errmsg").getAsString());
        }
    }

    /**
     * 新增一个用户，将openId入库
     * @param openId
     * @param userName
     * @param sex
     * @param birthday
     * @return
     */
    @Override
    public ResponseMessage bingUser(String openId, String userName, String sex, String birthday) {
        int status = userMapper.bingUser(openId, userName, sex, birthday);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError();
    }

    /**
     * 更新用户信息
     * @param openId
     * @param userName
     * @param sex
     * @param birthday
     * @return
     */
    @Override
    public ResponseMessage updateUserInfo(String openId, String userName, String sex, String birthday) {
        int status = userMapper.updateUserInfo(openId, userName, sex, birthday);
        if (status == 1)
            return ResponseMessage.createBySuccess();
        else
            return ResponseMessage.createByError();
    }

    /**
     * 根据用户openId查询用户订单信息
     * @param openId
     * @return
     */
    @Override
    public ResponseMessage getOrderByOpenId(String openId) {
        List<TOrder> orderList = orderMapper.getOrderByOpenId(openId);
        if (orderList.size() == 0)
            return ResponseMessage.createByErrorMessage("该用户无任何订单");
        else
            return ResponseMessage.createBySuccess(orderList);
    }
}
