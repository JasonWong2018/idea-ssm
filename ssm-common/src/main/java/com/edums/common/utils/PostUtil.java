package com.edums.common.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PostUtil {

    private static Logger logger = Logger.getLogger(PostUtil.class);

    public static String getResultPostByParams(String urlString, String code,Map<String,String> paramsMap) throws Exception{
        StringBuffer buffer = new StringBuffer();
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", code);
        con.getOutputStream().write(getparamsString(paramsMap).toString().getBytes(code));
        BufferedReader reader = null;
        // 定义BufferedReader输入流来读取URL的响应
        reader = new BufferedReader(new InputStreamReader(con.getInputStream(),code));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    @SuppressWarnings("rawtypes")
    public static String getparamsString(Map<String, String> paramsMap){
        StringBuffer sb = new StringBuffer();
        Set<?> es = paramsMap.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        return sb.toString();
    }

    /**
     * GET请求
     * @param get_url
     * @param content
     * @return
     * @throws Exception
     */
    public static String sendGetData(String get_url, String content) {
        String result = "";
        URL getUrl = null;
        BufferedReader reader = null;
        String lines = "";
        HttpURLConnection connection = null;
        try {
            if (content != null && !content.equals(""))
                get_url = get_url + "?" + content; // get_url = get_url + "?" + URLEncoder.encode(content, "utf-8");
            getUrl = new URL(get_url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect(); // 取得输入流，并使用Reader读取
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
            while ((lines = reader.readLine()) != null) {
                result = result + lines;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
                reader = null;
            }
            connection.disconnect();
        }
        return result;
    }
}
