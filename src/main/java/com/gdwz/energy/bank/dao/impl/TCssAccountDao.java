package com.gdwz.energy.bank.dao.impl;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.bank.dao.ITCssAccountDao;
import com.gdwz.energy.bank.entity.TCssAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lemon on 14-8-19.
 */
@Service
@Transactional 
public class TCssAccountDao  extends EntityDaoSupport<TCssAccount,String> implements ITCssAccountDao {

}
