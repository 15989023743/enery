package com.gdwz.energy.code.service;

import java.util.List;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.code.entity.CodeType;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： ICodeTypeService
 * @类描述：编码类型业务接口
 * @当前版本 1.0
 * @修改备注：
 */
public interface ICodeTypeService extends IGeneralService<CodeType> {

	/**
	 * 查询所有的编码类型
	 * 
	 * @return
	 */
	public List<CodeType> getAllCodeTypes();
}
