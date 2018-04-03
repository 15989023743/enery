package com.gdwz.energy.sysparam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.sysparam.dao.IRunModeDao;
import com.gdwz.energy.sysparam.entity.RunMode;
import com.gdwz.energy.sysparam.service.IRunModeService;


/**
 * @author Gary
 *
 */
@Service
@Transactional
public class RunModeService extends GeneralServiceImpl<RunMode,IRunModeDao> implements IRunModeService{
}
