package com.gdwz.energy.goods.service;

import java.util.List;

import com.gdwz.core.service.IGeneralTreeService;
import com.gdwz.energy.goods.entity.GoodsItem;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.service
 * @创建日期：14-8-29
 */
public interface IGoodsItemService extends IGeneralTreeService<GoodsItem> {
	public Long countByUnique(String name,List<Long> ids);
	/**
	 * 获得顶层父类
	 * @return
	 */
	public List<GoodsItem> getGoodsItem();
	
	/**
	 * 根据父类ID获得子类
	 * @param pid
	 * @param isleaf
	 * @return
	 */
	public List<GoodsItem> getGoodsByParentId(Long pid,Boolean isleaf);
	
	/**
	 * 根据父类ID获得子类-查找有效的子类
	 * @param pid
	 * @param isleaf
	 * @param enabled
	 * @return
	 */
	public List<GoodsItem> getEnabledGoodsByParentId(Long pid,Boolean isleaf);
	
	/**
	 * 查询商品类型，供前台ajax调用。（价格指数）
	 * @param num 返回结果数。
	 * @return
	 */
	List<Object> queryGoodsItemList(int num);
	
	/**
	 * 根据depth查询GoodsItem
	 * @param depth
	 * @return
	 */
	public List<GoodsItem> queryGoodsItemByDepth(int depth);
	
	
	/**
	 * 获取可用的类目
	 * @param parentId
	 * @return
	 */
	public List<GoodsItem> findEnabledChildrensByPid(Object parentId);

    /**
     * 查询商品类型，供前台ajax调用。(主力合约)
     * @param num 返回结果数。
     * @return
     */
    List<Object> queryGoodsItemListWithDynamicFutures(int num);
}
