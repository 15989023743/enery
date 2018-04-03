package com.gdwz.energy.dataExchange.service.impl;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.dataExchange.dao.IMainContractDao;
import com.gdwz.energy.dataExchange.dao.IPriceTrendDao;
import com.gdwz.energy.dataExchange.entity.MainContract;
import com.gdwz.energy.dataExchange.service.IMainContractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @包名称：com.gdwz.energy.dataExchange.service.impl
 * @作者：YQ
 * @创建日期：2014/9/22
 */
@Service
@Transactional
public class MainContractService extends GeneralServiceImpl<MainContract,IMainContractDao> implements IMainContractService{


    @Override
    public boolean checkDataExist(String oldId,String dateTime) throws Exception{
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            StringBuffer sb = new StringBuffer(" where e.deleteStatus = false and e.priceDate = :priceDate");
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("priceDate", format.parse(dateTime));
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
}
