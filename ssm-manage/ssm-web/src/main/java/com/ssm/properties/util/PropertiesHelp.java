package com.ssm.properties.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 属性文件操作
 * 
 * @author xiepeixiong
 *
 */
public final class PropertiesHelp extends Properties{
	
	private static final Logger logger = Logger.getLogger(PropertiesHelp.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Map<String, PropertiesHelp> propMap; // 属性文件操作对象集合（键为属性文件名）
	
	static{
		propMap = new HashMap<String, PropertiesHelp>();
	}
	
	public synchronized static PropertiesHelp getInstance(){
		String filePath = "E:\\idea-ssm\\ssm-manage\\ssm-web\\src\\main\\java\\com\\ssm\\properties\\config.properties";
		return getInstance(filePath, true);
	}
	
	public synchronized static PropertiesHelp getInstance(String fileName, boolean isClasspath){
		if(!propMap.containsKey(fileName)){ // 当集合中不存在此属性文件名对应的KEY时
			propMap.put(fileName, new PropertiesHelp(fileName, isClasspath));  // 向集合中插入对象
		}
		return propMap.get(fileName); // 向集合中取出属性文件名对应的属性文件操作对象
	}
	
	public synchronized static PropertiesHelp getInstance(String fileName, boolean isClasspath,boolean loadFlag){
		if(!propMap.containsKey(fileName)||loadFlag){ // 当集合中不存在此属性文件名对应的KEY时
			propMap.put(fileName, new PropertiesHelp(fileName, isClasspath));  // 向集合中插入对象
		}
		return propMap.get(fileName); // 向集合中取出属性文件名对应的属性文件操作对象
	}
	
	private PropertiesHelp(String filePath, boolean isClasspath){
		InputStream is;
		try {
			if(isClasspath){
				is = getClass().getResourceAsStream(filePath);
			}else{
				is = new FileInputStream(new File(filePath));
			}
			InputStreamReader reader = new InputStreamReader(is,"UTF-8");
			super.load(reader); // 加载属性文件至内存
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	public String getPropertiesValue(String key){
		return super.getProperty(key);
	}
}
