package com.gdwz.energy.sysparam.dao.impl;

import org.springframework.stereotype.Repository;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.sysparam.dao.IProcessFeeDao;
import com.gdwz.energy.sysparam.entity.ProcessFee;

@Repository("processFeeDao")
public class ProcessFeeDao extends EntityDaoSupport<ProcessFee, String> implements IProcessFeeDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9207169736070201930L;

}
