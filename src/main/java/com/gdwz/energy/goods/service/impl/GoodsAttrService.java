package com.gdwz.energy.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.goods.dao.IGoodsAttrDao;
import com.gdwz.energy.goods.entity.GoodsAttr;
import com.gdwz.energy.goods.service.IGoodsAttrService;
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
public class GoodsAttrService extends GeneralServiceImpl<GoodsAttr,IGoodsAttrDao> implements IGoodsAttrService {
	public Long countByUnique(String name,List<Long> ids){
		StringBuffer sb=new StringBuffer(" where e.name=:name and (e.goodsItem.id in (:ids) or e.goodsItem is null)");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("name", name);
		param.put("ids", ids);
		return super.getDefaultDAO().getCount(sb.toString(), param);
	}
}
