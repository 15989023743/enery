package com.gdwz.energy.code.service;

import java.util.List;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.code.entity.Code;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： ICodeService
 * @类描述：编码业务接口
 * @当前版本 1.0
 * @修改备注：
 */
public interface ICodeService extends IGeneralService<Code> {

	/**
	 * 查询所有的编码
	 * 
	 * @return
	 */
	public List<Code> getAllCodes();
	
	/**
     * 更新当前时间
     *
     * @param codeId
     * @return
     */
    public int updateCurrentDate(String codeId);
    
    
    public long countByWaste(String codeId, String format);
}
