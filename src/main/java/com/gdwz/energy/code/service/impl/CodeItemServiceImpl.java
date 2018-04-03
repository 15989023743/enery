package com.gdwz.energy.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.code.dao.ICodeItemDao;
import com.gdwz.energy.code.entity.CodeItem;
import com.gdwz.energy.code.service.ICodeItemService;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： CodeItemServiceImpl extends GeneralServiceImpl
 * @类描述： 编码项业务功能类
 * @当前版本 1.0
 * @修改备注：
 */
@Service
@Transactional
public class CodeItemServiceImpl extends GeneralServiceImpl<CodeItem,ICodeItemDao> implements
		ICodeItemService {



	public List<CodeItem> getCodeItemsByCodeName(String codeName) {
		return getDefaultDAO().getCodeItemsByCodeName(codeName);
	}

	public int updateItemValue(String id,String value){
		StringBuffer sb = new StringBuffer("update CodeItem set value='");
        sb.append(value).append("' where id='").append(id).append("'");
        return getDefaultDAO().executeUpdate(sb.toString());
	}
	
}
