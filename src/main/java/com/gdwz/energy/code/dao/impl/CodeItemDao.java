package com.gdwz.energy.code.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.code.dao.ICodeItemDao;
import com.gdwz.energy.code.entity.CodeItem;

/**
 * 
 * @类名称： CodeItemDao.java
 * @类描述： 编码项信息访问对象
 * @当前版本：1.0
 * @修改备注
 */
@SuppressWarnings({"serial" })
@Service
@Transactional
public class CodeItemDao extends EntityDaoSupport<CodeItem,String> implements ICodeItemDao {

	/**
	 * 根据编码名称查询所有的编码项
	 * 
	 * @param codeName
	 *            编码名称
	 * @return
	 */
	public List<CodeItem> getCodeItemsByCodeName(String codeName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", codeName);
		map.put("deleteStatus", false);
		return query("select obj from CodeItem obj where obj.code.name=:code and obj.code.deleteStatus=:deleteStatus order by obj.sequence", map);
	}

}
