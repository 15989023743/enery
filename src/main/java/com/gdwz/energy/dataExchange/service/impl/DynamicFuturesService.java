package com.gdwz.energy.dataExchange.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.dataExchange.dao.IDynamicFuturesDao;
import com.gdwz.energy.dataExchange.entity.DynamicFutures;
import com.gdwz.energy.dataExchange.service.IDynamicFuturesService;
import com.gdwz.energy.goods.entity.GoodsItem;

/**
 * @包名称：com.gdwz.energy.dataExchange.service.impl
 * @作者：YQ
 * @创建日期：2014/9/22
 */
@Service
@Transactional
public class DynamicFuturesService extends GeneralServiceImpl<DynamicFutures,IDynamicFuturesDao> implements IDynamicFuturesService{

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

	@Override
	public List<DynamicFutures> queryDynamicFuturesList(int num) {
//		StringBuffer jplsql = new StringBuffer(" SELECT distinct e FROM ");
//		jplsql.append(DynamicFutures.class.getName())
//			.append(" e inner join e.goodsItem g ")
//			.append(" where e.isShow='1' and e.deleteStatus='0' ")
//			.append	(" and g.deleteStatus='0' and g.enabled='1' ")
//			.append(" order by e.priceDate,e.showNum desc ");

        StringBuffer jplsql = new StringBuffer(" from ");
        jplsql.append(DynamicFutures.class.getName())
                .append(" e where e.priceDate =  ")
                .append(" ( select max(b.priceDate) from ")
                .append( DynamicFutures.class.getName() )
                .append( " b inner join " )
                .append( " b.goodsItem item " )
                .append(" where  b.isShow=1 and b.deleteStatus = 0 and item.deleteStatus=0 and item.enabled =1 and e.goodsItem = b.goodsItem ) ")
                .append(" order by e.priceDate desc e.showNum desc ");

		List<DynamicFutures> list = getDefaultDAO().query(jplsql.toString());
		List<DynamicFutures> retList = new ArrayList<DynamicFutures>();
		if (list!=null && list.size()>0){
			for (int i=0,l = Math.min(num,list.size());i< l ;i++){
				retList.add(list.get(i));
			}
		}
		return retList;
	}
	
	

}
