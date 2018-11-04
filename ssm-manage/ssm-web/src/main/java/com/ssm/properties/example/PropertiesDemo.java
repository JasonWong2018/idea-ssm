package com.ssm.properties.example;

import com.ssm.properties.util.PropertiesHelp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * properties属性文件解析
 */
public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\idea-ssm\\ssm-manage\\ssm-web\\src\\main\\java\\com\\ssm\\properties\\config.properties"));
        properties.load(bufferedReader);
        String config_base_path = properties.getProperty("config_base_path");
        System.out.println(config_base_path);
    }

}
