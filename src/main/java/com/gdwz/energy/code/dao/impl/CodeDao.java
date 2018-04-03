package com.gdwz.energy.code.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.code.dao.ICodeDao;
import com.gdwz.energy.code.entity.Code;

/**
 * 
 * @类名称： CodeDao.java
 * @类描述： 编码信息访问对象
 * @当前版本：1.0
 * @修改备注
 */
@SuppressWarnings("serial")
@Service
@Transactional
public class CodeDao extends EntityDaoSupport<Code,String> implements ICodeDao {

	/**
	 * 查询所有的编码
	 * 
	 * @return
	 */
	public List<Code> getAllCodes() {
		return query("select e from Code e where e.deleteStatus=false");
	}
}
