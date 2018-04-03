/**
 * 
 */
package com.gdwz.energy.sysparam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.sysparam.dao.impl.TDbpTimeParaDao;
import com.gdwz.energy.sysparam.entity.TDbpTimePara;
import com.gdwz.energy.sysparam.service.ITDbpTimeParaService;

/**
 * @author Gary
 *
 */
@Service
@Transactional
public class TDbpTimeParaService extends GeneralServiceImpl<TDbpTimePara,TDbpTimeParaDao> implements ITDbpTimeParaService {


}
