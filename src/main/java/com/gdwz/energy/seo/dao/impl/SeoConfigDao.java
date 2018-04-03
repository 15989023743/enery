package com.gdwz.energy.seo.dao.impl;

import com.gdwz.core.dao.impl.EntityDaoSupport;
import com.gdwz.energy.seo.dao.ISeoConfigDao;
import com.gdwz.energy.seo.entity.SeoConfig;
import org.springframework.stereotype.Repository;

/**
 * @包名称：com.gdwz.energy.seo.dao.impl
 * @作者：YQ
 * @创建日期：2014/9/26
 */
@Repository
public class SeoConfigDao extends EntityDaoSupport<SeoConfig,String> implements ISeoConfigDao {
}
