package com.gdwz.energy.dataExchange.service;

import java.util.List;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.dataExchange.entity.DynamicFutures;

/**
 * @包名称：com.gdwz.energy.dataExchange.service
 * @作者：YQ
 * @创建日期：2014/9/22
 */
public interface IDynamicFuturesService extends IGeneralService<DynamicFutures> {
	/**
     * 检查同一时段是否存在报价
     * @param oldId
     * @param dateTime
     * @return
     */
    boolean checkDataExist(String oldId,String dateTime) throws Exception;
    
    /**
     * 期货动态（获取前三条记录，供前台资讯中心展示）-数据源配置
     * @return
     */
    List<DynamicFutures> queryDynamicFuturesList(int num);
}
