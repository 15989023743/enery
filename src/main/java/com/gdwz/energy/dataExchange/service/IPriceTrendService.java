package com.gdwz.energy.dataExchange.service;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.dataExchange.entity.PriceTrend;

/**
 * @包名称：com.gdwz.energy.dataExchange.service
 * @作者：YQ
 * @创建日期：2014/9/22
 */
public interface IPriceTrendService extends IGeneralService<PriceTrend> {
    /**
     * 检查同一时段商品分类是否存在报价
     * @param oldId
     * @param itemId
     * @param dateTime
     * @return
     */
    boolean checkDataExist(String oldId,Long itemId, String dateTime) throws Exception;
}
