package com.ssm.common.utils;

import java.util.Random;


public class Tools {
	
	/**
	 * 判断文件是否是图片
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isImage(String fileName) {
		String[] validFiles = { "gif", "jpg", "jpeg", "png" };
		boolean ret = false;
		for (int i = 0; i < validFiles.length; i++) {
			if (fileName.toLowerCase().endsWith(validFiles[i])) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * （生成规则为6为随机数）
	 * @return
	 */
	public static String getDefaultPassword(){
		Random random = new Random();
		int x = random.nextInt(899999);
		x = x+100000;
		return x+"";
	}
}
