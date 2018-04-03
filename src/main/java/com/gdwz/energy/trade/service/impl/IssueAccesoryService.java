package com.gdwz.energy.trade.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.trade.dao.IIssueAccesoryDao;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueAccesoryInIssue;
import com.gdwz.energy.trade.entity.IssueUalityIndicator;
import com.gdwz.energy.trade.service.IIssueAccesoryService;

/**
 * 挂牌附件服务
 * @author shuanfeng
 *
 */
@Service
@Transactional
public class IssueAccesoryService extends
		GeneralServiceImpl<IssueAccesory, IIssueAccesoryDao> implements
		IIssueAccesoryService {

	public IssueUalityIndicator getIssueUalityIndicatorByIssueId(Long issueId) {
		return getDefaultDAO().getIssueUalityIndicatorByIssueId(issueId);
	}

	public List<IssueAccesoryInIssue> getIssueAccesoryInIssueByIssueId(
			Long issueId) {
		return getDefaultDAO().getIssueAccesoryInIssueByIssueId(issueId);
	}

}
