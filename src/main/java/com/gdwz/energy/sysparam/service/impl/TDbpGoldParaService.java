/**
 * 
 */
package com.gdwz.energy.sysparam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.sysparam.dao.ITDbpGoldParaDao;
import com.gdwz.energy.sysparam.entity.TDbpGoldPara;
import com.gdwz.energy.sysparam.service.ITDbpGoldParaService;

/**
 * @author Gary
 *
 */
@Service
@Transactional
public class TDbpGoldParaService extends GeneralServiceImpl<TDbpGoldPara,ITDbpGoldParaDao> implements ITDbpGoldParaService {

	

}
