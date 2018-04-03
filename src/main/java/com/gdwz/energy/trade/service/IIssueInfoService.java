package com.gdwz.energy.trade.service;

import java.util.List;
import java.util.Map;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.entity.IssueStateEnum;

/**
 * @项目名称：能化王国
 * @工程名称：gdwz-energy
 * @包名称：com.gdwz.energy.trade.service
 * @创建日期：14-8-29
 * @作者:chenyuhong
 *
 */
public interface IIssueInfoService extends IGeneralService<IssueInfo>{
	
	/**
	 * 审核挂牌上架方法
	 * @param issueId
	 * @return
	 */
	public boolean auditing(long issueId);
	
	/**
	 * 审核方法重载
	 * @param issueId
	 * @param issueState
	 * @return
	 */
	public boolean auditing(long issueId,IssueStateEnum issueState,IssueStateEnum nowIssueState);
	
	/**
	 * 获取可用挂牌列表
	 * @param len 条数
	 * @param byeOrSale 1 采购，2销售，0不区分
	 * @param  orderBySequence 排序键值对
	 * @return 挂牌列表
	 */
	public List<IssueInfo> getValidIssueByLen(int len,int byeOrSale,Map<String,String> orderBySequence);
	
	/**
	 * 获取可用挂牌列表
	 * @param len 条数
	 * @param  orderBySequence 排序键值对
	 * @return 挂牌列表
	 */
	public List<IssueInfo> getValidIssueByLen(int len,Map<String,String> orderBySequence);
	
	
	/**
	 * 获取可用挂牌数量
	 * @return 可用挂牌数量
	 */
	public long getValidIssueCount(int byeOrSale);
	
	
	public long getValidIssueCount(int byeOrSale,String menberId);
	
	/**
	 * 获取可用挂牌数量
	 * @return 可用挂牌数量
	 */
	public long getValidIssueCount();
	
	/**
	 * 挂牌作废
	 * @param id
	 * @return
	 */
	public boolean cancel(long id);
}
