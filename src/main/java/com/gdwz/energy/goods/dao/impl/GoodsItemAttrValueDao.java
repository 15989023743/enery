package com.gdwz.energy.goods.dao.impl;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.goods.dao.IGoodsItemAttrValueDao;
import com.gdwz.energy.goods.entity.GoodsItemAttrValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.dao.impl
 * @创建日期：14-8-29
 */

@Service
@Transactional
public class GoodsItemAttrValueDao extends EntityDaoSupport<GoodsItemAttrValue, String> implements IGoodsItemAttrValueDao {
}
