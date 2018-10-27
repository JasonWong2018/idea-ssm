package com.ssm.common.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);

    public static String sendRequest(String urlStr, String requestObj){
        try {
            StringBuffer buffer = new StringBuffer();
            URL url = new URL(urlStr);
            URLConnection rulConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http 正文内，因此需要设为true, 默认情况下是false;
            httpUrlConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // Post 请求不能使用缓存
            httpUrlConnection.setUseCaches(false);
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛 java.io.EOFException)
            httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("POST");
            // 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
            httpUrlConnection.connect();
            // 此处 getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
            // 所以在开发中不调用上述的connect()也可以)。
            PrintWriter out = new PrintWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream(),"utf-8"));
            //OutputStream outStrm = httpUrlConnection.getOutputStream();
            out.write(requestObj);
            // 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
            //ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
            //OutputStreamWriter objOutputStrm = new OutputStreamWriter(httpUrlConnection.getOutputStream(), "utf-8");
            // 向对象输出流写出数据，这些数据将存到内存缓冲区中
            //objOutputStrm.writeObject(requestUrl);
            //objOutputStrm.write(requestUrl);
            // 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
            out.flush();
            // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
            // 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
            out.close();
            // 调用HttpURLConnection连接对象的getInputStream()函数,
            // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
            //InputStream inStrm = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里
            //ObjectInputStream oi = new ObjectInputStream(inStrm);
            //String messages = (String) oi.readObject();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(),"UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            logger.error(buffer.toString());
            return buffer.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
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
