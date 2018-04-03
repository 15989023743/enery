package com.gdwz.energy.dataExchange.service;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.dataExchange.entity.MainContract;

/**
 * @包名称：com.gdwz.energy.dataExchange.service
 * @作者：YQ
 * @创建日期：2014/9/22
 */
public interface IMainContractService extends IGeneralService<MainContract> {
    /**
     * 检查同一时段是否存在报价
     * @param oldId
     * @param dateTime
     * @return
     */
    boolean checkDataExist(String oldId,String dateTime) throws Exception;
}
