package com.gdwz.energy.dataExchange.service.impl;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.dataExchange.dao.ILatestTrendDao;
import com.gdwz.energy.dataExchange.entity.LatestTrend;
import com.gdwz.energy.dataExchange.service.ILatestTrendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @包名称：com.gdwz.energy.dataExchange.service.impl
 * @作者：YQ
 * @创建日期：2014/9/22
 */
@Service
@Transactional
public class LatestTrendService extends GeneralServiceImpl<LatestTrend,ILatestTrendDao> implements ILatestTrendService{

}
