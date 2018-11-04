package com.edums.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * properties属性文件解析
 */
public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\idea-edums\\edums-manage\\edums-web\\src\\main\\java\\com\\edums\\properties\\config.properties"));
        properties.load(bufferedReader);
        String config_base_path = properties.getProperty("config_base_path");
        System.out.println(config_base_path);
    }

}
