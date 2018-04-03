package com.gdwz.energy.seo.sercice;

import java.util.Map;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.energy.seo.dao.ISeoConfigDao;
import com.gdwz.energy.seo.entity.SeoConfig;

/**
 * @包名称：com.gdwz.energy.seo.sercice
 * @作者：YQ
 * @创建日期：2014/9/26
 */
public interface ISeoConfigService extends IGeneralService<SeoConfig> {
    /**
     * 检查同一页面URL是否已经被配置SEO
     * @param oldId
     * @param url
     * @return
     */
    boolean checkDataExist(String oldId, String url) throws Exception;
    
    /**
     * 获取所有seo配置信息
     * @return
     */
	public Map<String,SeoConfig> getAllSeoConfig();

    
}
