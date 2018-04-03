package com.gdwz.energy.article.order;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 创建时间：2014年10月11日 下午4:52:06  
 * 项目名称：gdwz-energy-web  
 * @author yanlun  
 * @version 1.0   
 * 文件名称：OrderByMapUtil.java  
 * 类说明：  文章排序规则Map
 */
public final class OrderByRulesMapUtil{
	
	/**
	 * 文章完整排序规则， (order by recommended desc, sequence asc, addTime desc)
	 * @return
	 */
	public static Map<String, String> getFullArticleOrderRules(){
		Map<String,String> fullArticleOrderRules = new LinkedHashMap<String,String>();
		
		fullArticleOrderRules.put(" recommended","desc");
		fullArticleOrderRules.put(" sequence", "asc");
		fullArticleOrderRules.put(" addTime","desc");
		
		return fullArticleOrderRules;
	}
	
	/**
	 * 今日热点新闻排序规则， (order by recommended desc, clickRate desc, sequence asc)
	 * @return
	 */
	public static Map<String, String> getTodayHotArticleOrderRules(){
		Map<String,String> todayHotArticleOrderRules = new LinkedHashMap<String,String>();
		
		todayHotArticleOrderRules.put(" recommended","desc");
		todayHotArticleOrderRules.put(" clickRate","desc");
		todayHotArticleOrderRules.put(" sequence", "asc");
		
		return todayHotArticleOrderRules;
	}

	/**
	 * 文章分类排序规则-按序号排序，(order by index asc)
	 * @return
	 */
	public static Map<String, String> getArticleClassOrderByIndexRules(){
		Map<String,String> articleClassOrderByIndex = new HashMap<String,String>();
		
		articleClassOrderByIndex.put(" index", "asc");
		
		return articleClassOrderByIndex;
	}
	
	/**
	 * 文章排序规则-按照序号排序，(order by sequence asc)
	 * @return
	 */
	public static Map<String,String> getArticleOrderBySequenceRules(){
		Map<String, String> articleOrderBySequence = new HashMap<String,String>();
		
		articleOrderBySequence.put(" sequence","asc");
		
		return articleOrderBySequence;
	}
	
	/**
	 * 挂牌信息-完整排序规则(order by checkTime desc, addTime desc)
	 * @return
	 */
	public static Map<String, String> getFullIssueOrderRules(){
		Map<String, String> fullIssueOrderRules = new LinkedHashMap<String, String>();
		
		fullIssueOrderRules.put(" checkTime", "desc");
		fullIssueOrderRules.put(" addTime", "desc");
		
		return fullIssueOrderRules;
	}
	
	/**
	 * 挂牌信息-默认排序规则(order by checkTime desc)
	 * @return
	 */
	public static Map<String, String> getIssueOrderByCheckTimeRules(){
		Map<String, String> issueOrderByCheckTime = new HashMap<String, String>();
		
		issueOrderByCheckTime.put(" checkTime", "desc");
		
		return issueOrderByCheckTime;
	}
}
