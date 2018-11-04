package com.edums.common.utils;

import com.edums.common.cons.Constant;
import org.apache.log4j.Logger;

/**
 * 微信工具类
 */
public class WechatUtil {

    private static Logger logger = Logger.getLogger(WechatUtil.class);

    /**
     * 获取微信网页授权accessToken
     * @param code 用户授权后微信接口返回code
     * @return
     */
    public static String getAuthAccessToken(String code){
        /**
         * 正确返回json数据示例
         * { "access_token":"ACCESS_TOKEN",
         * "expires_in":7200,
         * "refresh_token":"REFRESH_TOKEN",
         * "openid":"OPENID",
         * "scope":"SCOPE" }
         */
        logger.info("开始获取微信auth accessToken");
        logger.info("code ===> "+code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url = url.replace("APPID", Constant.WX_APPID);
        url = url.replace("SECRET",Constant.WX_APPSECRET);
        url = url.replace("CODE",code);
        String result = HttpUtil.sendRequest(url, "");
        logger.info("调用微信接口返回结果:"+result);
        logger.info("结束获取微信auth accessToken");
        return result;
    }

    /**
     * 获取微信网页授权登录用户信息(GET请求)
     * @param accessToken
     * @param openId
     * @return
     */
    public static String getWechaUserInfo(String accessToken,String openId){
        /**
         * 正确返回json数据示例
         * {"openid":" OPENID",
         * " nickname": NICKNAME,
         * "sex":"1",
         * "province":"PROVINCE"
         * "city":"CITY",
         * "country":"COUNTRY",
         * "headimgurl":    "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
         * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
         * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
         * }
         */
        logger.info("开始获取微信授权用户信息");
        logger.info("accessToken ==>"+accessToken);
        logger.info("openId ==>"+openId);
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        url = url.replace("ACCESS_TOKEN",accessToken);
        url = url.replace("OPENID",openId);
        String result = HttpUtil.sendGetData(url, "");
        logger.info("调用微信接口返回结果:"+result);
        logger.info("结束获取微信授权用户信息");
        return result;
    }
}
