package com.gdwz.energy.trade.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.trade.dao.IIssueInfoDao;
import com.gdwz.energy.trade.entity.IssueInfo;

@Service
@Transactional
public class IssueInfoDao extends EntityDaoSupport<IssueInfo,Long>implements IIssueInfoDao{

}
