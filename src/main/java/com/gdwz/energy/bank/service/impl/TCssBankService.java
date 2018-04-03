package com.gdwz.energy.bank.service.impl;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.bank.dao.ITCssBankDao;
import com.gdwz.energy.bank.entity.TCssBank;
import com.gdwz.energy.bank.service.ITCssBankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lemon on 14-8-19.
 */
@Service
@Transactional
public class TCssBankService extends GeneralServiceImpl<TCssBank,ITCssBankDao> implements ITCssBankService{
}
