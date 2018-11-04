package com.edums.common.utils;

import com.edums.common.cons.Constant;
import com.edums.sys.domain.SysUser;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class CookieUtils {

	private static final Logger logger = Logger.getLogger(CookieUtils.class);
	private static final String path = "/";
	/**
	 * 获得Cookie的值
	 */
	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {
		try {
			request.setCharacterEncoding("UTF-8");
			Cookie[] cookie = request.getCookies();
			int len = cookie == null ? 0 : cookie.length;
			for (int i = 0; i < len; i++) {
				if (cookieName != null
						&& cookieName.equalsIgnoreCase(cookie[i].getName())) {
					return URLDecoder.decode(cookie[i].getValue(), "UTF-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		return "";
	}
	
	/**
	 * 清除cookie
	 */
	public static void clearCookieValue(HttpServletResponse response) {
		Cookie isLoginCookie = new Cookie(Constant.COOKIE_IS_LOGIN, "no");
		Cookie userIdCookie = new Cookie(Constant.COOKIE_USER_ID, null);
		Cookie loginNameCookie = new Cookie(Constant.COOKIE_LOGIN_NAME, null);
		Cookie realNameCookie = new Cookie(Constant.COOKIE_REAL_NAME, null);
		Cookie passwordCookie = new Cookie(Constant.COOKIE_LOGIN_PWD, null);
		isLoginCookie.setPath(path);
		userIdCookie.setPath(path);
		loginNameCookie.setPath(path);
		realNameCookie.setPath(path);
		passwordCookie.setPath(path);
		isLoginCookie.setMaxAge(0);
		userIdCookie.setMaxAge(0);
		loginNameCookie.setMaxAge(0);
		realNameCookie.setMaxAge(0);
		passwordCookie.setMaxAge(0);
		// 添加COOKIE
		response.addCookie(isLoginCookie);
		response.addCookie(userIdCookie);
		response.addCookie(loginNameCookie);
		response.addCookie(realNameCookie);
		response.addCookie(passwordCookie);
	}
	
	/**
	 * 设置cookie
	 * 
	 */
	public static void setCookie(HttpServletResponse response, SysUser user,
			int expiry) {
		String username = user.getUsername();
		username = username == null ? "" : username;
		String realName = user.getRealName();
		realName = realName == null ? "" : realName;
		try {
			username = URLEncoder.encode(username, "UTF-8");
			realName = URLEncoder.encode(realName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 设定COOKIE值
		Cookie isLoginCookie = new Cookie(Constant.COOKIE_IS_LOGIN, "is");
		Cookie userIdCookie = new Cookie(Constant.COOKIE_USER_ID, String
				.valueOf(user.getId()));
		Cookie passwordCookie = new Cookie(Constant.COOKIE_LOGIN_PWD,
				MD5Encrypt.md5(user.getPasswd()));  // 对现在的密码再进行md5加密
		Cookie loginNameCookie = new Cookie(Constant.COOKIE_LOGIN_NAME,
				username);
		Cookie realNameCookie = new Cookie(Constant.COOKIE_REAL_NAME, realName);
		isLoginCookie.setPath(path);
		userIdCookie.setPath(path);
		loginNameCookie.setPath(path);
		realNameCookie.setPath(path);
		passwordCookie.setPath(path);

		isLoginCookie.setMaxAge(expiry);
		userIdCookie.setMaxAge(expiry);
		loginNameCookie.setMaxAge(expiry);
		realNameCookie.setMaxAge(expiry);
		passwordCookie.setMaxAge(expiry);
		// detailCookie.setPath(path);
		// 添加COOKIE
		response.addCookie(isLoginCookie);
		response.addCookie(userIdCookie);
		response.addCookie(loginNameCookie);
		response.addCookie(realNameCookie);
		response.addCookie(passwordCookie);
	}
	
	public static void setCookie(HttpServletResponse response,String key,String value){
		try {
			if(null!=value){
				value = URLEncoder.encode(value,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		Cookie cookie = new Cookie(key,value);
		cookie.setPath(path);
		cookie.setMaxAge(Constant.COOKIE_EXPIRY);
		response.addCookie(cookie);
	}
	
	public static void setCookie(HttpServletResponse response,String key,String value,int expires){
		try {
			if(null!=value){
				value = URLEncoder.encode(value,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		Cookie cookie = new Cookie(key,value);
		cookie.setPath(path);
		cookie.setMaxAge(expires);
		response.addCookie(cookie);
	}
	
	/**
	 * 清楚cookie
	 * @param response
	 * @param key
	 * @author lipeng
	 */
	public static void clearCookie(HttpServletResponse response,String key){
		Cookie cookie = new Cookie(key,null);
		cookie.setPath(path);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	/**
	 * 设定统计的cookie值
	 * 
	 * @param response
	 * @param fromWhere
	 * @param expiry
	 */
	public static void setStatsCookie(HttpServletResponse response,
			String stats, int expiry,String cookieName) {
		// 设定COOKIE值
		Cookie statsCookie = new Cookie(cookieName, stats);
		statsCookie.setPath(path);
		statsCookie.setMaxAge(expiry);
		response.addCookie(statsCookie);
	}
	
	//清楚所有的cookie
	public static void clearCookie(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		try {
			for(int i=0;i<cookies.length;i++) {
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setMaxAge(0);
				cookie.setPath(path);//根据你创建cookie的路径进行填写
				response.addCookie(cookie);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
}
