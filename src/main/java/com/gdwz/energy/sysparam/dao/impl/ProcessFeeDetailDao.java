package com.gdwz.energy.sysparam.dao.impl;

import org.springframework.stereotype.Repository;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.sysparam.dao.IProcessFeeDetailDao;
import com.gdwz.energy.sysparam.entity.ProcessFeeDetail;

@Repository("processFeeDetailDao")
public class ProcessFeeDetailDao extends EntityDaoSupport<ProcessFeeDetail, String> implements IProcessFeeDetailDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9207169736070201930L;

}
