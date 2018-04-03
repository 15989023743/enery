package com.gdwz.energy.trade.service;

import java.util.List;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueAccesoryInIssue;
import com.gdwz.energy.trade.entity.IssueUalityIndicator;

/**
 * 挂牌附件服务接口
 * @author shuanfeng
 *
 */
public interface IIssueAccesoryService extends IGeneralService<IssueAccesory> {

	/**
	 * 获取挂牌质量指标
	 * @param issueId 挂牌ID
	 * @return 质量指标附件
	 */
	public IssueUalityIndicator getIssueUalityIndicatorByIssueId(Long issueId);
	
	
	/**
	 * 获取挂牌普通附件
	 * @param issueId 挂牌ID
	 * @return 挂牌普通附件列表
	 */
	public List<IssueAccesoryInIssue> getIssueAccesoryInIssueByIssueId(Long issueId);
	
}
