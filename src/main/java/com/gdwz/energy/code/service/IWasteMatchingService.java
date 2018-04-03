package com.gdwz.energy.code.service;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.code.entity.WasteMatching;

/**
 * 
 * @类名称：IWasteMatchingService
 * @类描述： 业务功能接口
 * @version 1.0
 * @修改备注：
 */
public interface IWasteMatchingService extends IGeneralService<WasteMatching> {

	/**
	 * 
	 * @param code
	 * @return
	 */
	public String getCode(String code);

	public String getCode(String code, String[] var);
}
