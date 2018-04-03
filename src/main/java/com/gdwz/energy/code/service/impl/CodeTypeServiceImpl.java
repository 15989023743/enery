package com.gdwz.energy.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.code.dao.ICodeTypeDao;
import com.gdwz.energy.code.entity.CodeType;
import com.gdwz.energy.code.service.ICodeTypeService;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称：     CodeTypeServiceImpl
 * @类描述：    编码类型业务功能类
 * @当前版本     1.0
 * @修改备注：
 */
@Service
@Transactional
public class CodeTypeServiceImpl extends GeneralServiceImpl<CodeType,ICodeTypeDao> implements ICodeTypeService {

	public List<CodeType> getAllCodeTypes() {
		return getDefaultDAO().getAllCodeTypes();
	}
	
}
