package com.gdwz.energy.goods.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralTreeServiceImpl;
import com.gdwz.energy.goods.dao.IGoodsItemDao;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsItemService;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.service.impl
 * @创建日期：14-8-29
 */

@Service
@Transactional
public class GoodsItemService extends GeneralTreeServiceImpl<GoodsItem,IGoodsItemDao> implements IGoodsItemService {
	
	public Long countByUnique(String name,List<Long> ids){
		StringBuffer sb=new StringBuffer(" where e.name=:name ");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("name", name);
		if(ids!=null&&ids.size()>0){
			sb.append(" and (e.parent.id in (:ids) or e.parentids is null)");
			param.put("ids", ids);
		}else{
			sb.append(" and e.parentids is null");
		}
		return super.getDefaultDAO().getCount(sb.toString(), param);
	}

	/**
	 * 获得顶层父类
	 * @return
	 */
	@Override
	public List<GoodsItem> getGoodsItem() {
		
		List<GoodsItem> goodsItems = this.query("SELECT DISTINCT e FROM GoodsItem e  WHERE  e.depth=0 AND e.deleteStatus = false AND enabled = true ORDER BY e.addTime ASC");
		return goodsItems;
	}
	
	/**
	 * 根据父类ID获得子类
	 * @param pid
	 * @param isleaf
	 * @return
	 */
	@Override
	public List<GoodsItem> getGoodsByParentId(Long pid,Boolean isleaf){
		return this.getGoodsByParentId(pid, isleaf, null);
	}
	
	
	@Override
	public List<GoodsItem> getEnabledGoodsByParentId(Long pid, Boolean isleaf) {
		return this.getGoodsByParentId(pid, isleaf, true);
	}

	/**
	 * 公共方法-根据父类ID获得字类
	 * @param pid
	 * @param isleaf
	 * @param enabled
	 * @return
	 */
	private List<GoodsItem> getGoodsByParentId(Long pid, Boolean isleaf,
			Boolean enabled) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("parentids1", pid.toString());
		map.put("parentids2", pid+"-%");
		map.put("leaf", isleaf);
		map.put("deleteStatus", false);
		StringBuffer jpasql = new StringBuffer(" SELECT DISTINCT e FROM ");
		jpasql.append(GoodsItem.class.getName())
				.append(" e WHERE (e.parentids=:parentids1 or e.parentids like:parentids2) ")
				.append(" and e.leaf=:leaf ");
		if(enabled != null){
			map.put("enabled", enabled);
			jpasql.append(" and e.enabled = :enabled ");
		}
		
		jpasql.append(" and e.deleteStatus = :deleteStatus ");
		
		List<GoodsItem> goods = this.query(jpasql.toString(), map);
		return goods;
	}

	@Override
	public List<Object> queryGoodsItemList(int num){
		List<Object> relist = new ArrayList<Object>();
//		StringBuffer sb=new StringBuffer("select distinct g from PriceTrend e , GoodsItem g where e.goodsItem.id = g.id and  g.deleteStatus='0' and g.depth='0' ");
		StringBuffer sb=new StringBuffer("select distinct g from PriceTrend e inner join e.goodsItem g where e.deleteStatus = '0' and g.enabled=1 and g.deleteStatus='0' and g.depth='0' ");
		List<GoodsItem> list = this.getDefaultDAO().query(sb.toString());
		if (list!=null && list.size()>0){
			for (int i=0,len=list.size();i < len;i++){
				if (i==num){break;}
				GoodsItem item = new GoodsItem();
				item.setId(list.get(i).getId());
				item.setName(list.get(i).getName());
				relist.add(item);
			}
		}
		return relist;
	}


    @Override
    public List<Object> queryGoodsItemListWithDynamicFutures(int num){
        List<Object> relist = new ArrayList<Object>();
//		StringBuffer sb=new StringBuffer("select distinct g from PriceTrend e , GoodsItem g where e.goodsItem.id = g.id and  g.deleteStatus='0' and g.depth='0' ");
        StringBuffer sb=new StringBuffer("select distinct g from DynamicFutures e inner join e.goodsItem g where e.deleteStatus = '0' and e.isShow=1 and g.enabled=1 and g.deleteStatus='0' and g.leaf=1 ");
        List<GoodsItem> list = this.getDefaultDAO().query(sb.toString());
        if (list!=null && list.size()>0){
            for (int i=0,len=list.size();i < len;i++){
                if (i==num){break;}
                GoodsItem item = new GoodsItem();
                item.setId(list.get(i).getId());
                item.setName(list.get(i).getName());
                relist.add(item);
            }
        }
        return relist;
    }



	@Override
	public List<GoodsItem> queryGoodsItemByDepth(int depth) {
		StringBuffer jpasql = new StringBuffer(" SELECT distinct e FROM ");
		jpasql.append(GoodsItem.class.getName())
				.append(" e LEFT JOIN FETCH e.childs c1 ")
				.append(" WHERE e.depth=?1 and e.deleteStatus=false and e.enabled=true ORDER BY e.index asc");
		return this.query(jpasql.toString(),new Object[]{Integer.valueOf(depth)});
	}

	@Override
	public List<GoodsItem> findEnabledChildrensByPid(Object parentId) {
		StringBuffer jplsql = new StringBuffer();
		Object[] paramsArray = new Object[1];
		if(null==parentId || parentId.toString().equals("") || parentId.toString().equals("0")) {
			jplsql.append("SELECT e FROM ").append(GoodsItem.class.getName()).append(" e WHERE e.parent IS null AND 1=?1 ");
			paramsArray[0] = 1;
			//return super.query(jplsql.toString());
		}else {
			jplsql.append("SELECT e FROM ").append(GoodsItem.class.getName()).append(" e WHERE e.parent.id = ?1 ");
			paramsArray[0] = parentId;
		}
		jplsql.append(" AND e.deleteStatus = false");
		jplsql.append(" AND e.enabled = true");
		jplsql.append(" ORDER BY e.index,e.id ");
		return getDefaultDAO().query(jplsql.toString(),paramsArray);
	}
	
}
