package com.gdwz.energy.code.dao;

import java.util.List;

import com.gdwz.core.dao.IEntityDaoSupport;
import com.gdwz.energy.code.entity.Code;

public interface ICodeDao extends IEntityDaoSupport<Code, String> {

	public List<Code> getAllCodes();
	
}
