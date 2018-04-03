package com.gdwz.energy.sysparam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.sysparam.dao.ITBrpNewsDao;
import com.gdwz.energy.sysparam.entity.TBrpNews;
import com.gdwz.energy.sysparam.service.ITBrpNewsService;


/**
 * @author Gary
 *
 */
@Service
@Transactional
public class TBrpNewsService extends GeneralServiceImpl<TBrpNews,ITBrpNewsDao> implements ITBrpNewsService{
}
