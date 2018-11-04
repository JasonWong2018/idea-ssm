package com.ssm.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码servlet
 */
public class BuildValidCode extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream out = response.getOutputStream();
        int width = 50, height = 20;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D)image.getGraphics();
        Random random = new Random();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("宋体", Font.BOLD, 16));
        String allStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        String sRand = "";
        g.setColor(Color.red);
        for (int i = 0; i < 4; i++) {
            int randIndex = new Random().nextInt(allStr.length() - 1);
            sRand += allStr.charAt(randIndex);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(allStr.charAt(randIndex) + "", 10 * i + 8, 16);
        }
        request.getSession().setAttribute("code", sRand);
        g.dispose();
        ImageIO.write(image, "JPEG", out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }
}
