package com.gdwz.energy.bank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.bank.dao.ITCssAccountDao;
import com.gdwz.energy.bank.entity.TCssAccount;
import com.gdwz.energy.bank.service.ITCssAccountService;

/**
 * Created by lemon on 14-8-19.
 */
@Service
@Transactional
public class TCssAccountService extends GeneralServiceImpl<TCssAccount,ITCssAccountDao> implements ITCssAccountService{

}
