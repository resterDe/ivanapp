package com.ivanapp.ivanapp.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ResterDay
 * @version 1.0
 * @date 2020/3/5 21:45
 * 用于保存用户的session_key openId
 */
public class LoginSessionMap {

    private static final Map<String, Map<String, String>> SESSION = new ConcurrentHashMap<>();

    /**
     * 添加登录session_key,openip
     * @param loginSession
     * @param map
     */
    public static void put(String loginSession, Object map) {
        SESSION.put(loginSession, (Map<String, String>) map);
    }

    /**
     * 判断是否存在key,也就是是否登录
     * @param loginSession
     * @return
     */
    public static boolean containsKey(String loginSession) {
        return SESSION.containsKey(loginSession);
    }

    /**
     * 获取
     * @param loginSession
     * @return
     */
    public static Map<String, String> get(String loginSession) {
        return SESSION.get(loginSession);
    }

    /**
     * 清除过期session
     * @param loginSession
     */
    public static void clear(String loginSession) {
        SESSION.remove(loginSession);
    }
}
