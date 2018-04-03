package com.gdwz.energy.code.dao;

import java.util.List;

import com.gdwz.core.dao.IEntityDaoSupport;
import com.gdwz.energy.code.entity.CodeType;

public interface ICodeTypeDao extends IEntityDaoSupport<CodeType, String> {
	
	/**
	 * 查询所有的编码类型
	 * 
	 * @return
	 */
	public List<CodeType> getAllCodeTypes();

}
