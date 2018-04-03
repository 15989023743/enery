package com.gdwz.energy.trade.service;

import java.util.List;
import java.util.Map;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;

public interface IIssueGoodsAttrValuesService extends IGeneralService<IssueGoodsAttrValues> {
	/**
	 * 根据挂牌属性取得上架状态的挂牌id列表
	 * @param attrs
	 * @return
	 */
	public List<Long> getIssueIdsByAttrs(Map<String,String> attrs);
	
	/**
	 * 根据挂牌属性取得全部挂牌id列表
	 * @param attrs
	 * @return
	 */
	public List<Long> getIssueIdsByAttrsAll(Map<String,String> attrs);
	
	
	public void updateGoodsAttrValues(Long issueInfoId,List<IssueGoodsAttrValues> issueGoodsAttrValuess);

}
