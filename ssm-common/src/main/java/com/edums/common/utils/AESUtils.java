package com.edums.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * AES加密工具类
 */
public class AESUtils {

	private static final Logger logger = Logger.getLogger(AESUtils.class);
	
	private static final String DEFAULT_AES="AES";
	
	private static final String DEFAULT_RANDOM="SHA1PRNG";
	
	/**
	 * 加密
	 * @param content
	 * @param password
	 * @return
	 */
	
	public static byte[] encrypt(String content, String password) {
        try {
        	logger.info("加密password:"+password);
            KeyGenerator kgen = KeyGenerator.getInstance(DEFAULT_AES);
            SecureRandom sr = SecureRandom.getInstance(DEFAULT_RANDOM);//防止linux下生产随机数不可控
            sr.setSeed(password.getBytes());
            kgen.init(128, sr);  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, DEFAULT_AES);  
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
        	logger.error(e.getMessage(),e);
        } catch (NoSuchPaddingException e) {  
        	logger.error(e.getMessage(),e);
        } catch (InvalidKeyException e) {  
        	logger.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {  
        	logger.error(e.getMessage(),e);
        } catch (IllegalBlockSizeException e) {  
        	logger.error(e.getMessage(),e);
        } catch (BadPaddingException e) {  
        	logger.error(e.getMessage(),e);
        }  
        return null;  
	} 
	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static byte[] decrypt(byte[] content, String password) {  
        try {
        	logger.info("解密password:"+password);
            KeyGenerator kgen = KeyGenerator.getInstance(DEFAULT_AES);
            SecureRandom sr = SecureRandom.getInstance(DEFAULT_RANDOM);//防止linux下生产随机数不可控
            sr.setSeed(password.getBytes());
            kgen.init(128, sr);  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, DEFAULT_AES);              
            Cipher cipher = Cipher.getInstance(DEFAULT_AES);// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
        	logger.error(e.getMessage(),e);
        } catch (NoSuchPaddingException e) {  
        	logger.error(e.getMessage(),e);
        } catch (InvalidKeyException e) {  
        	logger.error(e.getMessage(),e);
        } catch (IllegalBlockSizeException e) {  
        	logger.error(e.getMessage(),e);
        } catch (BadPaddingException e) {  
        	logger.error(e.getMessage(),e);
        }  
        return null;  
	}  
	
	public static void main(String[] args) {
		String content = "test";  
		String password = "12345678";  
		//加密  
		System.out.println("加密前：" + content);  
		byte[] encryptResult = encrypt(content, password);  
		String encryptResultStr = parseByte2HexStr(encryptResult);  
		System.out.println("加密后：" + encryptResultStr);  
		//解密  
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
		byte[] decryptResult = decrypt(decryptFrom,password);  
		System.out.println("解密后：" + new String(decryptResult));

	}
	
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
	}
	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public static byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}
	
	/**
	 * 加密
	 * @param s
	 * @param key
	 * @return
	 */
	public static String encryptStr(String key,String s){
		byte[] encryptResult = encrypt(s, key);
		return parseByte2HexStr(encryptResult);
	}
	
	/**
	 * 解密
	 * @param s
	 * @param key
	 * @return
	 */
	public static  String decryptStr(String key,String s){
		byte[] decryptFrom = parseHexStr2Byte(s);  
		byte[] decryptResult = decrypt(decryptFrom,key);
		return new String(decryptResult);
	}
}
