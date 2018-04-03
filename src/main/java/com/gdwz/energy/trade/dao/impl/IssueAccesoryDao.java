package com.gdwz.energy.trade.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.energy.trade.dao.IIssueAccesoryDao;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueAccesoryInIssue;
import com.gdwz.energy.trade.entity.IssueUalityIndicator;
import com.gdwz.utils.Assert;

/**
 * 挂牌附件持久层
 * @author shuanfeng
 *
 */
@Service
@Transactional
public class IssueAccesoryDao extends EntityDaoSupport<IssueAccesory, Long> implements IIssueAccesoryDao {
	public IssueUalityIndicator getIssueUalityIndicatorByIssueId(Long issueId){
		Assert.notNull(issueId,"issueId is not null");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("issueInfo.id", issueId);
		map.put("deleteStatus", false);
		return super.findUniqueByProperty(IssueUalityIndicator.class, map);
	}

	public List<IssueAccesoryInIssue> getIssueAccesoryInIssueByIssueId(Long issueId){
		Assert.notNull(issueId,"issueId is not null");
		ListPropertyFilter listPropertyFilter=ListPropertyFilter.getInstance();
		listPropertyFilter.addFilter("issueInfo.id", issueId, MatchType.EQ);
		listPropertyFilter.addFilter("deleteStatus", false, MatchType.EQ);
		return super.findByFilters(IssueAccesoryInIssue.class, listPropertyFilter);
	}
}
