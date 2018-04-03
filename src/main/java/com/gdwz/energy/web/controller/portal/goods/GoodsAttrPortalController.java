package com.gdwz.energy.web.controller.portal.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.energy.goods.entity.GoodsAttr;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsAttrService;
import com.gdwz.energy.goods.service.IGoodsItemService;


@Controller
@RequestMapping(value="/portal/goods/goodsAttr/",description="前台类目属性")
public class GoodsAttrPortalController extends SimpleController {

	@Autowired
	private IGoodsAttrService goodsAttrService;
	
	@Autowired
    private IGoodsItemService goodsItemService;
	
	
	@RequestMapping(value="attrJsonByGoodsId",description="通过父id获取商品json",aclLevel=EAclLevel.SESSION)
	public void getAttrJsonByGoodsId(HttpServletRequest request, HttpServletResponse response,Long goodsId){
		try{
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			
			if(goodsId!=null){
				GoodsItem item=goodsItemService.findById(goodsId);
				ListPropertyFilter listPropertyFilter=ListPropertyFilter.getInstance();
				List<GoodsAttr> goodsAttrs=new ArrayList<GoodsAttr>();


				listPropertyFilter.addFilter("enabled", true, MatchType.EQ);
		    	listPropertyFilter.addFilter("goodsItem.id", item.getId(), MatchType.EQ);
		    	List<GoodsAttr> selfAttrs=goodsAttrService.findByFilters(listPropertyFilter);
		    	goodsAttrs.addAll(selfAttrs);
		    	
		    	
		    	//公有属性
		    	listPropertyFilter=ListPropertyFilter.getInstance();
		    	listPropertyFilter.addFilter("goodsItem.id", MatchType.ISNULL);
		    	listPropertyFilter.addFilter("enabled", true, MatchType.EQ);
		    	List<GoodsAttr> pubAttrs=goodsAttrService.findByFilters(listPropertyFilter);
		    	goodsAttrs.addAll(pubAttrs);
		    	
		    	
		    	
		    	List<GoodsAttr> parAttrs=null;
		    	//上级属性
		    	if(item.getParent()!=null){
		    		List<Long> list=getIdsByString(item.getParentids(),"-");
		    		listPropertyFilter=ListPropertyFilter.getInstance();
		    		listPropertyFilter.addFilter("enabled", true, MatchType.EQ);
		    		listPropertyFilter.addFilter("goodsItem.id", list, MatchType.IN);
		    		parAttrs=goodsAttrService.findByFilters(listPropertyFilter);
		    		goodsAttrs.addAll(parAttrs);
		    	}
				
				
				for(GoodsAttr attr:goodsAttrs){
					Map<String,Object> data=new HashMap<String, Object>();
					data.put("id", attr.getId());
					data.put("name", attr.getName());
					data.put("dictName", attr.getDictName());
					data.put("inputType", attr.getInputType());
					data.put("category", attr.getCategory());
					datas.add(data);
				}
			}
			jsonMapper.renderJson(datas, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Long> getIdsByString(String resId,String spStr){

		if(StringUtils.isEmpty(resId)){
			return new ArrayList<Long>();
		}
		List<Long> list=new ArrayList<Long>();
		String[] ids=resId.split("-");
		for(String pid:ids){
			list.add(Long.parseLong(pid));
		}
		return list;
	}
}
