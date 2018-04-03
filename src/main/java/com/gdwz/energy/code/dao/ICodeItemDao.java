package com.gdwz.energy.code.dao;

import java.util.List;

import com.gdwz.core.dao.IEntityDaoSupport;
import com.gdwz.energy.code.entity.CodeItem;

public interface ICodeItemDao extends IEntityDaoSupport<CodeItem, String> {
	
	/**
	 * 根据编码名称查询所有的编码项
	 * 
	 * @param codeName
	 *            编码名称
	 * @return
	 */
	public List<CodeItem> getCodeItemsByCodeName(String codeName);

}
