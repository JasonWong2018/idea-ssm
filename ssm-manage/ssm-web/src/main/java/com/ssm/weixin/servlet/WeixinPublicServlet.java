package com.ssm.weixin.servlet;

import com.ssm.common.utils.ServiceBeanUtil;
import com.ssm.weixin.service.WeixinPublicService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WeixinPublicServlet extends HttpServlet {

    private static final String TOKEN = "weixin";

    private static final Logger logger = Logger.getLogger(WeixinPublicServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("系统接收用户消息开始！");
        PrintWriter out = response.getWriter();
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            logger.info("signature:"+signature+";timestamp:"+timestamp+";nonce:"+nonce);
            if (checkSignature(signature, timestamp, nonce)) {
                out.print(request.getParameter("echostr"));
            }
        } catch (Exception e) {
            out.print("error");
            logger.error(e.getMessage(),e);
        }finally{
            out.flush();
            out.close();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("系统接收用户消息开始！");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        InputStream in = request.getInputStream();
        try {

            WeixinPublicService service = (WeixinPublicService) ServiceBeanUtil.getBean("weixinPublicService");
            logger.info(service);
            String replyText = service.saveReceiveAndReplyMsg(in);
            logger.info(replyText);
            out.print(replyText);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
            in.close();
            in = null;
            out.flush();
            out.close();
        }
    }

    private static boolean checkSignature(String signature, String timestamp,
                                          String nonce) {
        String[] arr = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(),e);
        }

        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }
    // 将字节转换为十六进制字符串
    private static String byteToHexStr(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];

        String s = new String(ob);
        return s;
    }

    // 将字节数组转换为十六进制字符串
    private static String byteToStr(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexStr(bytearray[i]);
        }
        return strDigest;
    }

}
