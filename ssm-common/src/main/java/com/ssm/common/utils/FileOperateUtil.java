package com.ssm.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

/**
 * 文件操作工具类
 * 
 * @author xiepeixiong
 * 
 */
@SuppressWarnings("all")
public final class FileOperateUtil {

	private static final Logger logger = Logger.getLogger(FileOperateUtil.class);
	private static final String SEPARATOR = "/";

	private static final String RESOURCE_PATH = "D:/tomcat7_vmerge/webapps/resource";

	/**
	 * 创建文件夹
	 * 
	 * @param folder
	 *            文件夹路径
	 */
	public final static String createFolder(String folder) {
		String parentPath = RESOURCE_PATH ;
		String baseFolder = parentPath + folder;
		File file = new File(baseFolder);
		if (!file.exists())
			file.mkdirs();
		return baseFolder;
	}
	/**
	 * 创建文件
	 * 
	 */
	public final static String createFile(String filepath, boolean flag,HttpServletRequest request) {
		String parentPath = "";
		if (flag)
			parentPath = CommonUtil.getServerAccessPath(request);
		String baseFile = parentPath + filepath;
		File file = new File(baseFile);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		return baseFile;
	}
	
	public final static void move(String destName, String targetName,
			boolean flag) {
		String parentPath = "";
		if (flag)
			parentPath = RESOURCE_PATH;
		try {
			File src = new File(parentPath + destName);
			String bak = UUID.randomUUID().toString();
			String desc = targetName+bak+SEPARATOR;
			move(src, new File(parentPath+ desc), true);
			//移动图片是加入图片压缩处理功能
			try{
				File desFile = new File(parentPath+ desc);
				File[] files = new File[0];
				if(desFile.isDirectory()){
					files = desFile.listFiles();
				}
				for(File file:files){
					if(Tools.isImage(file.getName())){
						CompressPicUtil.compressPicTempleOne(file.getPath(), parentPath+targetName+SEPARATOR+file.getName());
						copy(file, new File(targetName+SEPARATOR+"bak"+SEPARATOR+bak), flag);
					}else{
						copy(file, new File(targetName+SEPARATOR), flag);
					}
				}
				delete(desFile);
			}catch (Exception e) {
				logger.error(e.getMessage(),e);
				//发生压缩异常，把原图移动过来
				move(parentPath+ desc, parentPath+targetName, true);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 将文件（夹）移动到令一个文件夹
	 */
	public static void move(File resFile, File objFolderFile, boolean flag)
			throws IOException {
		copy(resFile, objFolderFile, flag);
		delete(resFile);
	}
	
	/**
	 * 复制文件（夹）到一个目标文件夹
	 * 
	 * @param resFile
	 *            源文件（夹）
	 * @param objFolderFile
	 *            目标文件夹
	 */
	public static void copy(File resFile, File objFolderFile, boolean flag)
			throws IOException {
		if (!resFile.exists())
			return;
		if (!objFolderFile.exists())
			objFolderFile.mkdirs();
		if (resFile.isFile()) {
			File objFile = new File(objFolderFile.getPath() + File.separator
					+ resFile.getName());
			// 复制文件到目标地
			InputStream ins = new FileInputStream(resFile);
			FileOutputStream outs = new FileOutputStream(objFile);
			byte[] buffer = new byte[1024 * 512];
			int length;
			while ((length = ins.read(buffer)) != -1) {
				outs.write(buffer, 0, length);
			}
			ins.close();
			outs.flush();
			outs.close();
		} else {
			String objFolder;
			if (flag)
				objFolder = objFolderFile.getPath();
			else
				objFolder = objFolderFile.getPath() + File.separator
						+ resFile.getName();

			File _objFolderFile = new File(objFolder);
			_objFolderFile.mkdirs();
			for (File sf : resFile.listFiles()) {
				copy(sf, new File(objFolder), false);
			}
		}
	}
	
	/**
	 * 复制文件（夹）到一个目标文件夹
	 * 
	 * @param resFile
	 *            源文件（夹）
	 * @param objFolderFile
	 *            目标文件夹
	 */
	public static void copyFile(String srcFilePath, String destFilePath, boolean flag)
			throws IOException {
		File srcFile = new File(srcFilePath);
		if (!srcFile.exists())
			return;
		File destFile = new File(destFilePath);
		if (!destFile.getParentFile().exists())
			destFile.getParentFile().mkdirs();
		
		if (srcFile.isFile()) {
			// 复制文件到目标地
			InputStream ins = new FileInputStream(srcFile);
			FileOutputStream outs = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024 * 512];
			int length;
			while ((length = ins.read(buffer)) != -1) {
				outs.write(buffer, 0, length);
			}
			ins.close();
			outs.flush();
			outs.close();
		}
	}
	
	/**
	 * 删除文件（夹）
	 */
	public static void delete(File file) {
		if (file == null)
			return;
		if (!file.exists())
			return;
		if (file.isFile()) {
			file.delete();
		} else {
			for (File f : file.listFiles()) {
				delete(f);
			}
			file.delete();
		}
	}
	
	/**
	 * 读取文件信息
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		BufferedReader reader = null;
		InputStreamReader readIn = null;
		StringBuilder resStr = new StringBuilder();
		try {
			File file = new File(fileName);
			if (!file.exists()) {// 如果文件不存在返回空字符串
				return "{}";
			}
			readIn = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(readIn);
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				resStr.append(tempString);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			if (readIn != null) {
				try {
					readIn.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return resStr.toString();
	}
	
	/**
	 * 把字符串写入到文件里面
	 * 
	 * @param filePath
	 * @param file
	 */
	public static void writeFile(String filePath, String file) {
		File jsonFile = new File(filePath);
		if (!jsonFile.exists()) {
			try {
				jsonFile.getParentFile().mkdirs();
				jsonFile.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			jsonFile.delete();
		}

		FileOutputStream fileOutput = null;
		PrintWriter fileWrite = null;
		try {
			fileOutput = new FileOutputStream(jsonFile);
			fileWrite = new PrintWriter(new OutputStreamWriter(fileOutput,
					"UTF-8"));
			fileWrite.write(file);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fileWrite != null) {
				try {
					fileWrite.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (fileOutput != null) {
				try {
					fileOutput.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 图片压缩处理
	 * @param src
	 */
	public static void pressImg(File src){
		logger.info(src.getPath());
		String bakpath = src.getParentFile()+SEPARATOR+"bak"+SEPARATOR;
		try {
			copy(src , new File(bakpath),false);
			CompressPicUtil.compressPicTempleOne(bakpath+SEPARATOR+src.getName(), src.getPath());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 保存base64为图片
	 */
	public static String uploadPicture(String picBASE64, String fileName) {
		if (StringUtils.isEmpty(picBASE64)) {
			return "";
		}
		// 文件绝对路径
		String filePath = fileName;
		File file = new File(filePath).getParentFile();
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(picBASE64);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					bytes[i] += 256;
				}
			}
			// 生成图片
			OutputStream out = new FileOutputStream(filePath);
			out.write(bytes);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param filePath
	 * @param uploadfile
	 * @throws IOException
	 */
	public static void updateFile(String filePath, File uploadfile)
			throws IOException {
		File file = new File(RESOURCE_PATH + filePath);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		BufferedOutputStream out = null;
		BufferedInputStream input = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			input = new BufferedInputStream(new FileInputStream(uploadfile));
			byte[] b = new byte[1024 * 1024];
			int temp = 0;
			while ((temp = input.read(b)) > 0) {
				out.write(b, 0, temp);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				if (out != null)
					out.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
}
