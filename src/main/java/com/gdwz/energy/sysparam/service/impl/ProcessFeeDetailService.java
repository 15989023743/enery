/**
 * 
 */
package com.gdwz.energy.sysparam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.sysparam.dao.impl.ProcessFeeDetailDao;
import com.gdwz.energy.sysparam.entity.ProcessFeeDetail;
import com.gdwz.energy.sysparam.service.IProcessFeeDetailService;

/**
 * @author Gary
 *
 */
@Service
@Transactional
public class ProcessFeeDetailService extends GeneralServiceImpl<ProcessFeeDetail,ProcessFeeDetailDao> implements IProcessFeeDetailService {


}
