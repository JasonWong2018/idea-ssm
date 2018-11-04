package com.ssm.common.base;


import com.github.abel533.entity.Example;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

public class BaseService {
    protected Example.Criteria getCriteria(Class clazz){
        Example e = new Example(clazz);
        Example.Criteria c = e.createCriteria();
        return c;
    }

    /**
     * 分页初始设置
     * @param pageUtils
     */
    protected void startPage(PageUtils pageUtils){
        PageHelper.startPage(pageUtils.getPi(), pageUtils.getPageSize());
    }

    /**
     * 分页结果设置
     * @param pageUtils
     * @param dataList
     */
    protected void setPageUtils(PageUtils pageUtils, List<Map<String,Object>> dataList){
        pageUtils.setCountPage(((Page<Map<String,Object>>) dataList).getPages());
        PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<Map<String, Object>>(dataList);
        pageUtils.setCount((int)mapPageInfo.getTotal());
        pageUtils.setResultList(dataList);
    }
}
