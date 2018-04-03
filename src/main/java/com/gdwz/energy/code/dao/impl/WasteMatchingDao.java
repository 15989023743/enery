package com.gdwz.energy.code.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.code.dao.IWasteMatchingDao;
import com.gdwz.energy.code.entity.WasteMatching;

/**
 * 
 * 类名称：     WasteMatchingDao
 * 类描述：     编码信息访问对象
 * 当前版本     1.0
 * 修改备注：
 */
@Service
@Transactional
public class WasteMatchingDao extends EntityDaoSupport<WasteMatching,String> implements IWasteMatchingDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
