package com.gdwz.energy.sysparam.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.sysparam.dao.ITBrpNewsDao;
import com.gdwz.energy.sysparam.entity.TBrpNews;

/**
 * Created by lemon on 14-8-19.
 */
@Service
@Transactional
public class TBrpNewsDao extends EntityDaoSupport<TBrpNews, String> implements ITBrpNewsDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1175487715990743999L;

}
