package com.gdwz.energy.code.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.code.dao.ICodeTypeDao;
import com.gdwz.energy.code.entity.CodeType;

/**
 * 
 * @类名称： CodeTypeDao.java
 * @类描述： 编码类型信息访问对象
 * @当前版本：1.0
 * @修改备注
 */
@SuppressWarnings("serial")
@Service
@Transactional
public class CodeTypeDao extends EntityDaoSupport<CodeType,String> implements ICodeTypeDao {

	/**
	 * 查询所有的编码类型
	 * 
	 * @return
	 */
	public List<CodeType> getAllCodeTypes() {
		return query("select e from CodeType e where e deleteStatus=false");
	}
}
