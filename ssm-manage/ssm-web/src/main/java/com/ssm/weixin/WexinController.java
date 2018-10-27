package com.ssm.weixin;

import com.ssm.common.utils.FastJsonUtil;
import com.ssm.common.utils.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/wxapi")
public class WexinController {

    @RequestMapping("/authIndex")
    @ResponseBody
    public String authIndex(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        String result = WechatUtil.getAuthAccessToken(code);
        Map<String, Object> resultMap = FastJsonUtil.json2Map(result);
        String wechaUserInfo = WechatUtil.getWechaUserInfo(resultMap.get("access_token").toString(), resultMap.get("openid").toString());
        return "授权回调";
    }
}
