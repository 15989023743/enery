package com.gdwz.energy.web.controller.portal.goods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdwz.common.dict.service.IDictService;
import com.gdwz.core.entity.BaseDict;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.energy.goods.entity.GoodsAttr;
import com.gdwz.energy.goods.entity.GoodsItemAttrValue;
import com.gdwz.energy.goods.service.IGoodsAttrService;
import com.gdwz.energy.goods.service.IGoodsItemAttrValueService;


@Controller
@RequestMapping(value="/portal/goods/goodsAttrValues/",description="前台类目属性值")
public class GoodsItemAttrValuePortalController extends SimpleController {


	@Autowired
	private IGoodsItemAttrValueService goodsItemAttrValueService;
	
	@Autowired
	private IDictService dictService;
	
	@Autowired
	private IGoodsAttrService goodsAttrService;
	
	@RequestMapping(value="attrValuesJson",description="通过父id获取商品属性值json",aclLevel=EAclLevel.SESSION)
	public void getGoodsItemAttrValueJson(HttpServletRequest request, HttpServletResponse response,Long itemId,Long attrId){
		try{
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			if(itemId!=null&&attrId!=null){
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("itemId", itemId);
				param.put("attrId", attrId);
				GoodsAttr goodsAttr=goodsAttrService.findById(attrId);
				GoodsItemAttrValue goodsItemAttrValue=goodsItemAttrValueService.findUniqueByProperty(param);
				List<String> valueIds=new ArrayList<String>();
				if(goodsItemAttrValue!=null&&!StringUtils.isEmpty(goodsItemAttrValue.getValuesId())){
					valueIds=Arrays.asList(goodsItemAttrValue.getValuesId().split(","));
				}
				
				List<BaseDict> dicts=dictService.selectByTblNameZdlx(goodsAttr.getDictName());
				for(BaseDict dict:dicts){
					if(valueIds.contains(dict.getId().toString())){
						continue;
					}
					Map<String,Object> data=new HashMap<String, Object>();
					data.put("id", dict.getId());
					data.put("name", dict.getMC());
					datas.add(data);
				}
			}
			jsonMapper.renderJson(datas, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
