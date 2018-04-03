package com.gdwz.energy.web.controller.portal.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.model.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsItemService;


@Controller
@RequestMapping(value="/portal/goods/goodsItem/",description="前台分类/品名信息")
public class GoodsItemPortalController extends SimpleController {

	@Autowired
    private IGoodsItemService goodsItemService;
	
	
	
	
	@RequestMapping(value="goodsItemJsonByPid",description="通过父id获取商品json",aclLevel=EAclLevel.SESSION)
	public void getGoodsItemsJsonByPid(HttpServletRequest request, HttpServletResponse response,Long pid){
		try{
			List<GoodsItem> items=goodsItemService.findEnabledChildrensByPid(pid);
			List<Map<String,Object>> toJsons=new ArrayList<Map<String,Object>>();
			if(items!=null){
				for(GoodsItem item:items){
					Map<String,Object> obj=new HashMap<String, Object>();
					obj.put("id", item.getId());
					obj.put("name", item.getName());
					obj.put("leaf", item.isLeaf());
					obj.put("parentId",item.getParent()!=null?item.getParent().getId():"");
					obj.put("names", item.getNames());
					obj.put("profitLossScale", item.getProfitLossScale());
					toJsons.add(obj);
				}
			}
			jsonMapper.renderJson(toJsons, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="goodsItemsIdByName",description="通过名称获取商品id",aclLevel=EAclLevel.SESSION)
	@ResponseBody
	public String getGoodsItemsIdByName(@RequestParam("names") String names,@RequestParam("split") String split){
		List<Long> ids=new ArrayList<Long>();
		if(!StringUtil.isEmpty(names)){
			if(!StringUtil.isEmpty(split)){
				String[] nameas=names.split(split);
				for(int i=0;nameas!=null&&i<nameas.length;i++){
					GoodsItem item=goodsItemService.findUniqueByProperty("name", nameas[i]);
					if(item!=null){
						ids.add(item.getId());
					}
				}
			}else{
				GoodsItem item=goodsItemService.findUniqueByProperty("name", names);
				if(item!=null){
					ids.add(item.getId());
				}
			}
		}
		return jsonMapper.toJson(ids);
	}
	
	
	@RequestMapping(value="queryGoodsItemList",description="查询商品类型，供前台ajax调用(价格指数)",aclLevel=EAclLevel.WHITE)
	public void queryGoodsItemList(HttpServletResponse response, @RequestParam("rownum") int rownum){
		try {
			List<Object> items = goodsItemService.queryGoodsItemList(rownum);
			jsonMapper.renderJson(items,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    @RequestMapping(value="queryGoodsItemWithDynamicFutures",description="查询商品类型，供前台ajax调用(主力合约)",aclLevel=EAclLevel.WHITE)
    public void queryGoodsItemListWithDynamicFutures(HttpServletResponse response, @RequestParam("rownum") int rownum){
        try {
            List<Object> items = goodsItemService.queryGoodsItemListWithDynamicFutures(rownum);
            jsonMapper.renderJson(items,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="loadGoodsItemList",description="查询商品类型(1-2级)，供前台ajax调用",aclLevel=EAclLevel.WHITE)
    @ResponseBody
    public ResponseEntity<String> loadGoodsItemList(HttpServletResponse response){
        try {
//            StringBuffer jpasql = new StringBuffer(" SELECT distinct e FROM ");
//            jpasql.append(GoodsItem.class.getName())  .append(" e LEFT JOIN FETCH e.childs c1 ") .append(" WHERE e.depth=0 and e.deleteStatus=false");
            List<GoodsItem> relist = new ArrayList<GoodsItem>();
            List<GoodsItem> items =  goodsItemService.queryGoodsItemByDepth(0);
            if (items!=null && items.size()>0){
                for (int i=0,len=items.size();i < len;i++){
                    GoodsItem item = new GoodsItem();
                    item.setId(items.get(i).getId());
                    item.setName(items.get(i).getName());
                    item.setParentids(items.get(i).getParentids());
                    Set<GoodsItem> childs = new HashSet<GoodsItem>();
                    for (GoodsItem goodsItem : items.get(i).getChilds()) {
                        GoodsItem item1 = new GoodsItem();
                        item1.setId(goodsItem.getId());
                        item1.setName(goodsItem.getName());
                        item1.setParentids(goodsItem.getParentids());
                        if(goodsItem.isEnabled()) {
                            childs.add(item1);
                        }
                    }
                    item.setChilds(childs);
                    relist.add(item);
                }
            }
            String returnStr = jsonMapper.toJson(relist);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
            return new ResponseEntity<String>(returnStr, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
