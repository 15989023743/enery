package com.gdwz.energy.trade.dao;

import java.util.List;

import com.gdwz.core.dao.IEntityDaoSupport;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueAccesoryInIssue;
import com.gdwz.energy.trade.entity.IssueUalityIndicator;

/**
 * 挂牌附件持久层
 * @author shuanfeng
 *
 */
public interface IIssueAccesoryDao extends IEntityDaoSupport<IssueAccesory,Long> {

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
  