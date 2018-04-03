package com.gdwz.energy.seo.sercice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.seo.dao.ISeoConfigDao;
import com.gdwz.energy.seo.entity.SeoConfig;
import com.gdwz.energy.seo.sercice.ISeoConfigService;

/**
 * @包名称：com.gdwz.energy.seo.sercice.impl
 * @作者：YQ
 * @创建日期：2014/9/26
 */
@Service
@Transactional
public class SeoConfigService extends GeneralServiceImpl<SeoConfig,ISeoConfigDao> implements ISeoConfigService {
    @Override
    public boolean checkDataExist(String oldId, String url) throws Exception{
        try {
            StringBuffer sb = new StringBuffer(" where e.deleteStatus = false and e.url = :url");
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("url", url);
            if(StringUtils.isNotEmpty(oldId)){
                sb.append(" and e.id != :id");
                param.put("id",oldId);
            }

            return super.getDefaultDAO().getCount(sb.toString(), param) > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }

	@Override
	public Map<String, SeoConfig> getAllSeoConfig() {
		Map<String, SeoConfig> resultMap = new HashMap<String, SeoConfig>();
		List<SeoConfig> result = super.findByFilters(ListPropertyFilter.getInstance().addFilter("deleteStatus",false, MatchType.EQ));
		for(SeoConfig sc:result){
			resultMap.put(sc.getUrl(), sc);
		}
		return resultMap;
	}

}
