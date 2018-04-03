package com.gdwz.energy.goods.service;

import java.util.List;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.goods.entity.GoodsAttr;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.service
 * @创建日期：14-8-29
 */
public interface IGoodsAttrService extends IGeneralService<GoodsAttr> {
	public Long countByUnique(String name,List<Long> ids);
}
