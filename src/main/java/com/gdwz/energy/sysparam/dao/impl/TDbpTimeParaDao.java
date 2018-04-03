package com.gdwz.energy.sysparam.dao.impl;

import org.springframework.stereotype.Repository;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.sysparam.dao.ITDbpTimeParaDao;
import com.gdwz.energy.sysparam.entity.TDbpTimePara;

@Repository("tDbpTimeParaDao")
public class TDbpTimeParaDao extends EntityDaoSupport<TDbpTimePara, String> implements ITDbpTimeParaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9207169736070201930L;

}
