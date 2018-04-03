package com.gdwz.energy.code.service;

import java.util.List;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.code.entity.CodeItem;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： IAccessoryService
 * @类描述： 图片业务接口
 * @当前版本 1.0
 * @修改备注：
 */
public interface ICodeItemService extends IGeneralService<CodeItem> {

	/**
	 * 根据编码名称查询所有的编码项
	 * 
	 * @param codeName
	 *            编码名称
	 * @return
	 */
	public List<CodeItem> getCodeItemsByCodeName(String codeName);

	
	public int updateItemValue(String id,String value);
}
