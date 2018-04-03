package com.gdwz.energy.trade.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.trade.dao.IIssueGoodsAttrValuesDao;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;

@Service
@Transactional
public class IssueGoodsAttrValuesDao extends EntityDaoSupport<IssueGoodsAttrValues,Long> implements IIssueGoodsAttrValuesDao {


}
