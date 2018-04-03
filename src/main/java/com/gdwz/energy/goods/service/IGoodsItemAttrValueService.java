package com.gdwz.energy.goods.service;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.goods.entity.GoodsItemAttrValue;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.service
 * @创建日期：14-8-29
 */
public interface IGoodsItemAttrValueService extends IGeneralService<GoodsItemAttrValue> {
	public long getCountByAllKeys(Long itemId,Long attrId,String valueId);
}
