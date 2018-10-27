package com.ssm.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class ServiceBeanUtil implements BeanFactoryAware {

	private static List<BeanFactory> beanFactorys = null;
	private static BeanFactory beanFactory = null ;
	private static boolean isMore = false ;

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if(beanFactorys == null)
			beanFactorys = new ArrayList<BeanFactory>();
		beanFactorys.add(beanFactory);
		
		if(beanFactorys.size() > 1){ // 包含多个spring项目时，按顺序查询bean
			isMore = true ;
			return ;
		}
		ServiceBeanUtil.beanFactory = beanFactory ;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		if(isMore){
			int len = beanFactorys.size();
			for(int i = 0 ;i < len ;i ++){
				T bean = (T)getBean(beanFactorys.get(i), beanName);
				if(bean != null)
					return bean ;
			}
			return null ;
		}else{ 
			return (T)getBean(beanFactory, beanName);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(BeanFactory beanFactory, String beanName){
		try {
			return (T)beanFactory.getBean(beanName);
		} catch (Exception e) {
			
			return null ;
		}
	}

}