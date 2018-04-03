/**
 * 
 */
package com.gdwz.energy.sysparam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.sysparam.dao.impl.ProcessFeeDao;
import com.gdwz.energy.sysparam.entity.ProcessFee;
import com.gdwz.energy.sysparam.service.IProcessFeeService;

/**
 * @author Gary
 *
 */
@Service
@Transactional
public class ProcessFeeService extends GeneralServiceImpl<ProcessFee,ProcessFeeDao> implements IProcessFeeService {


}
