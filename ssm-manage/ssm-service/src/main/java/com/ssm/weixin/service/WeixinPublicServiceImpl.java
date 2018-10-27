package com.ssm.weixin.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service("weixinPublicService")
public class WeixinPublicServiceImpl implements WeixinPublicService {


    private static final Logger logger = Logger.getLogger(WeixinPublicServiceImpl.class);
    public static Map<String, String> replyInfo = Collections.synchronizedMap(new HashMap<String, String>());
    public static Map<String, String> replyKeyword = Collections.synchronizedMap(new HashMap<String, String>());

    public String saveReceiveAndReplyMsg(InputStream in) throws Exception {
        logger.info("开始执行saveReceiveAndReplyMsg！");
        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = pullFactory.newPullParser();
        xmlPullParser.setInput(in, "UTF-8");
        int eventType = xmlPullParser.getEventType();
        String toUserName = "";
        String fromUserName = "";
        String msgType = "";
        String content = "";
        String picUrl = "";
        String locationX = "";
        String locationY = "";
        // String scale = "";
        String label = "";
        String title = "";
        String description = "";
        String url = "";
        String event = "";
        String eventKey = "";
        // logger.info("xml解析开始！");
        try{
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String name = xmlPullParser.getName();
                        if (name.equals("ToUserName")) {
                            toUserName = xmlPullParser.nextText();
                        } else if (name.equals("FromUserName")) {
                            fromUserName = xmlPullParser.nextText();
                        } else if (name.equals("MsgType")) {
                            msgType = xmlPullParser.nextText();
                        } else if (name.equals("Content")) {
                            content = xmlPullParser.nextText();
                        } else if (name.equals("PicUrl")) {
                            picUrl = xmlPullParser.nextText();
                        } else if (name.equals("Location_X")) {
                            locationX = xmlPullParser.nextText();
                        } else if (name.equals("Location_Y")) {
                            locationY = xmlPullParser.nextText();
                        } else if (name.equals("Scale")) {
                            // scale = xmlPullParser.nextText();
                        } else if (name.equals("Label")) {
                            label = xmlPullParser.nextText();
                        } else if (name.equals("Title")) {
                            title = xmlPullParser.nextText();
                        } else if (name.equals("Description")) {
                            description = xmlPullParser.nextText();
                        } else if (name.equals("Url")) {
                            url = xmlPullParser.nextText();
                        } else if (name.equals("Event")) {
                            event = xmlPullParser.nextText();
                        } else if (name.equals("EventKey")) {
                            eventKey = xmlPullParser.nextText();
                        }
                        break;

                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            in.close();
            in = null;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        int disposeType = 0;
        final String openId = fromUserName;
        final String userParam = eventKey;
        String searchKey = content;
        logger.info("fromUserName = " + openId + ", toUserName = " + toUserName + ", content = " + content);
        if (searchKey.equals("你好") || searchKey.equals("客户") || searchKey.equals("帮助") || searchKey.equals("service")) {
            disposeType = 7;
        } else {
            if (msgType.equals("event")) {
                if (event.equals("subscribe")) {
                    disposeType = 1; // 关注
                    searchKey = "subscribe";
                } else if (event.equals("unsubscribe")) {
                    disposeType = 2; // 取消关注
                    searchKey = "unsubscribe";
                } else if (event.equalsIgnoreCase("CLICK")) { // 菜单点击(click:关键字(文本回复)
                    // view:链接)
                    content = eventKey; // eventKey为搜索关键字
                    disposeType = 3; // 图文、文本、音频回复(完全匹配比模糊匹配优先级要高)
                    searchKey = content;
                }
            } else if (msgType.equals("text")) {
                disposeType = 3; // 图文、文本、音频回复(完全匹配比模糊匹配优先级要高)
            } else if (msgType.equals("location")) {
                disposeType = 4; // 位置
                searchKey = "location";
            } else if (msgType.equals("image")) {// 图片消息
                disposeType = 5; // 图片
            } else if (msgType.equals("link")) {// 链接消息
                disposeType = 6; // 链接
            }
        }
        logger.info(searchKey);
        if (searchKey.equals("你好") || searchKey.equals("客户") || searchKey.equals("帮助") || searchKey.equals("service")) {
            disposeType = 7;
        }
        // 获取当前用户上次请求的关键字
        String keyword = replyKeyword.get(toUserName);
        String textKey = toUserName + "-" + searchKey;
        switch (disposeType) {
            case 1: // 关注
                // 查询关注时的回复内容
                // 先匹配图文消息
                return "你好";
           /* case 2: // 取消关注
                return "";*/
            case 3: // 图文、文本、音频回复(完全匹配比模糊匹配优先级高)
                if("123".equals(content)){
                    String context = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb45f51d84bc958bd&redirect_uri=http://63191025.ngrok.io/ssm-web/wxapi/authIndex.action&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
                    String text = responseText(toUserName, fromUserName,context);
                    replyInfo.put(textKey, text);
                    return text;
                }
                return "";
            default: // 未识别的处理类型，直接返回
                return "";
        }
    }

    /**
     * 回复文本消息
     *
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    private String responseText(String toUserName, String fromUserName, String content) {
        if (StringUtils.isNotEmpty(content) && content.length() > 2048) {// 内容最多不能超过2048个字符
            content = content.substring(0, 2048);
        }
        StringBuilder retStr = new StringBuilder();
        responseCommon(retStr, toUserName, fromUserName);
        retStr.append("<MsgType><![CDATA[text]]></MsgType>");
        retStr.append("<Content><![CDATA[");
        retStr.append(content);
        retStr.append("]]></Content>");
        retStr.append("</xml>");
        return retStr.toString();
    }
    private void responseCommon(StringBuilder retStr, String toUserName, String fromUserName) {
        retStr.append("<xml>");
        retStr.append("<ToUserName><![CDATA[");
        retStr.append(fromUserName);
        retStr.append("]]></ToUserName>");
        retStr.append("<FromUserName><![CDATA[");
        retStr.append(toUserName);
        retStr.append("]]></FromUserName>");
        retStr.append("<CreateTime>");
        retStr.append(System.currentTimeMillis());
        retStr.append("</CreateTime>");
    }
}
