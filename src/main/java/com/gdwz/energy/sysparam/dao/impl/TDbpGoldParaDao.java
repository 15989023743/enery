package com.gdwz.energy.sysparam.dao.impl;

import org.springframework.stereotype.Repository;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.sysparam.dao.ITDbpGoldParaDao;
import com.gdwz.energy.sysparam.entity.TDbpGoldPara;

@Repository("tDbpGoldParaDao")
public class TDbpGoldParaDao extends EntityDaoSupport<TDbpGoldPara, String> implements ITDbpGoldParaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930613404992434864L;

}
