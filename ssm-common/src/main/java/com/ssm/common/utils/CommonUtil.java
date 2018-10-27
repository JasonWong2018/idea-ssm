package com.ssm.common.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

    private final static Logger logger = Logger.getLogger(CommonUtil.class);

    /**
     * 获取当前项目的访问地址
     * @param request
     * @return
     */
    public static String getServerAccessPath(HttpServletRequest request){
        String res=  "http://"+request.getServerName()+"/";
        logger.info("当前项目的访问地址 ========= "+res+"=======");
        return res;
    }
}
