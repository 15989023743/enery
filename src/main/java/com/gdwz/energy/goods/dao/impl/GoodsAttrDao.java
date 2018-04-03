package com.gdwz.energy.goods.dao.impl;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.goods.dao.IGoodsAttrDao;
import com.gdwz.energy.goods.entity.GoodsAttr;
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
public class GoodsAttrDao extends EntityDaoSupport<GoodsAttr, String> implements IGoodsAttrDao {
}
