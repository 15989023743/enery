package com.gdwz.energy.bank.dao.impl;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.bank.dao.ITCssBankDao;
import com.gdwz.energy.bank.entity.TCssBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lemon on 14-8-19.
 */
@Service
@Transactional
public class TCssBankDao extends EntityDaoSupport<TCssBank, String> implements ITCssBankDao{

}
