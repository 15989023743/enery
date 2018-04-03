package com.gdwz.energy.sysparam.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.sysparam.dao.IRunModeDao;
import com.gdwz.energy.sysparam.entity.RunMode;

/**
 * Created by lemon on 14-8-19.
 */
@Service
@Transactional
public class RunModeDao extends EntityDaoSupport<RunMode, String> implements IRunModeDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1175487715990743999L;

}
