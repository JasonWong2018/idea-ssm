package com.edums.common.utils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 分页类
 *
 * @author xiaof
 *
 */
public class PageUtils implements java.io.Serializable {

	private static final long serialVersionUID = 2387515373707451333L;

	private int start = 1;// 开始条数

	private String countSelect = "select count(*) ";

	private String select = "";

	private String from = "";

	private String where = "";

	private String countFrom = "";

	private String countWhere = "";

	private String groupBy = "";

	private String orderBy = "";

	private List<Object> parametersName;// 查询参数名称，主要用于组合上下页链接用到

	private List<Object> parameters;// 查询参数值

	private Map<String, Object> otherParameters;// 不参与查询的参数名称及值，主要用于组合上下页链接用到

	private Object result; // 当确定结果只有一条时，用来存储数据

	private int pageSize = 10;// 每页大小

	private int pi = 1;// 当前页数

	private int countPage;// 总页数

	private int count = 0;// 总条数

	private List<Map<String, Object>> resultList;// 查询数据集合

	private List<Object> midList = new ArrayList<Object>();

	private List<String> ListOrder ;

	private String firstHref;// 第一页链接

	private String preHref;// 上一页链接

	private String nextHref;// 下一页链接

	private String lastHref;// 末页链接

	private String hideStr = "";

	private int len = 3;

	private String originUrl = "";

	private int step;

	private Map<String,Object> resultMap;
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}


	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getCountSelect() {
		return countSelect;
	}

	public void setCountSelect(String countSelect) {
		this.countSelect = countSelect;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getCountFrom() {
		return countFrom;
	}

	public void setCountFrom(String countFrom) {
		this.countFrom = countFrom;
	}

	public String getCountWhere() {
		return countWhere;
	}

	public void setCountWhere(String countWhere) {
		this.countWhere = countWhere;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<Object> getParametersName() {
		return parametersName;
	}

	public void setParametersName(List<Object> parametersName) {
		this.parametersName = parametersName;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Object> getOtherParameters() {
		return otherParameters;
	}

	public void setOtherParameters(Map<String, Object> otherParameters) {
		this.otherParameters = otherParameters;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPi() {
		return pi;
	}

	public void setPi(int pi) {
		this.pi = pi;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public List<Object> getMidList() {
		return midList;
	}

	public void setMidList(List<Object> midList) {
		this.midList = midList;
	}

	public String getFirstHref() {
		return firstHref;
	}

	public void setFirstHref(String firstHref) {
		this.firstHref = firstHref;
	}

	public String getPreHref() {
		return preHref;
	}

	public void setPreHref(String preHref) {
		this.preHref = preHref;
	}

	public String getNextHref() {
		return nextHref;
	}

	public void setNextHref(String nextHref) {
		this.nextHref = nextHref;
	}

	public String getLastHref() {
		return lastHref;
	}

	public void setLastHref(String lastHref) {
		this.lastHref = lastHref;
	}

	public String getHideStr() {
		return hideStr;
	}

	public void setHideStr(String hideStr) {
		this.hideStr = hideStr;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public PageUtils() {

	}

	public PageUtils(int pi, int pageSize) {
		this.pi = pi;
		this.pageSize = pageSize;
	}


	// 组合链接
	private void createUrl() {
		String pUrl = createPUrl();
		firstHref = originUrl + "pageUtils.pi=1" + pUrl + "&pageUtils.count="
				+ count;
		preHref = originUrl + "pageUtils.pi=" + (pi - 1 <= 0 ? 1 : pi - 1)
				+ pUrl + "&pageUtils.count=" + count;
		nextHref = originUrl + "pageUtils.pi="
				+ (pi + 1 > countPage ? countPage : pi + 1) + pUrl
				+ "&pageUtils.count=" + count;
		lastHref = originUrl + "pageUtils.pi=" + countPage + pUrl
				+ "&pageUtils.count=" + count;
		createMidNum(pUrl);
	}

	// 组中间数字页链接
	private void createMidNum(String pUrl) {
		int s = 1;
		int e = 1;
		s = pi - len <= 0 ? 1 : pi - len;
		e = pi + len > countPage ? countPage : pi + len;
		midList.clear();
		for (int i = s; i <= e; i++) {
			Map<Object, Object> midMap = new HashMap<Object, Object>();
			midMap.put("pi", i);
			midMap.put("url", originUrl + "pageUtils.pi=" + i + pUrl
					+ "&pageUtils.count=" + count);
			midList.add(midMap);
		}
	}

	private String createPUrl() {
		StringBuffer pUrl = new StringBuffer("");
		int parlen = parametersName == null ? 0 : parametersName.size();
		if (parlen > 0) {
			for (int i = 0; i < parlen; i++) {
				String key = (String) parametersName.get(i);
				String value = parameters.get(i) + "";
				if (key.endsWith("_")) {// 以下划线(_)结尾，表示该字段是模糊查询字段
					key = key.substring(0, key.lastIndexOf("_"));
					if (value.startsWith("%")) {
						value = value.substring(1);
					}
					if (value.endsWith("%")) {
						value = value.substring(0, value.lastIndexOf("%"));
					}
					pUrl.append("&" + key + "=" + getEncoder(value));
				} else {
					pUrl.append("&" + key + "=" + getEncoder(value));
				}
				hideStr += "<input type='hidden' name='" + key + "' id='" + key
						+ "' value='" + value + "'/>";
			}
		}
		if (otherParameters != null && otherParameters.size() > 0) {
			for (Map.Entry<String, Object> entry : otherParameters.entrySet()) {
				pUrl.append("&" + entry.getKey() + "=" + entry.getValue());
				hideStr += "<input type='hidden' name='" + entry.getKey()
						+ "' id='" + entry.getKey() + "' value='"
						+ entry.getValue() + "'/>";
			}
		}
		return pUrl.toString();
	}

	private Object[] list2object(List<Object> list) {
		if (list != null && list.size() > 0) {
			Object[] obj = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				obj[i] = list.get(i);
			}
			return obj;
		} else {
			return null;
		}

	}

	private String getEncoder(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public List<String> getListOrder() {
		return ListOrder;
	}

	public void setListOrder(List<String> listOrder) {
		ListOrder = listOrder;
	}

}
