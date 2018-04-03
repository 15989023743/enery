package com.gdwz.energy.goods.service.impl;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.goods.dao.IGoodsItemAttrValueDao;
import com.gdwz.energy.goods.entity.GoodsItemAttrValue;
import com.gdwz.energy.goods.service.IGoodsItemAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.service.impl
 * @创建日期：14-8-29
 */

@Service
@Transactional
public class GoodsItemAttrValueService extends GeneralServiceImpl<GoodsItemAttrValue,IGoodsItemAttrValueDao> implements IGoodsItemAttrValueService {

	@Override
	public int deleteByIds(Object... primaryKeys) {
		StringBuffer hql=new StringBuffer("DELETE FROM GoodsItemAttrValue e where e.itemId=?1 and e.attrId=?2 and e.valuesId=?3");
		return super.getDefaultDAO().executeUpdate(hql.toString(), primaryKeys);
	}
	
	
	public long getCountByAllKeys(Long itemId,Long attrId,String valueId){
		StringBuffer hql=new StringBuffer("where e.itemId=?1 and e.attrId=?2 and e.valuesId like ?3");
		return super.getDefaultDAO().getCount(hql.toString(), new Object[]{itemId,attrId,"%"+valueId+"%"});
	}
	
}
