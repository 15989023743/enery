package com.gdwz.energy.web.controller.manager.goods;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.goods.entity.GoodsItemAttrValue;
import com.gdwz.energy.goods.service.IGoodsItemAttrValueService;

@Controller
@RequestMapping(value="/manager/goods/goodsItemAttrValue/",description="后台分类/品名管理信息管理")
public class GoodsItemAttrValueController extends CrudController<GoodsItemAttrValue,Long> {
	
	
	@Autowired
	IGoodsItemAttrValueService goodsItemAttrValueService;

	@Override
	protected IGeneralService<GoodsItemAttrValue> getDefaultService() {
		return goodsItemAttrValueService;
	}

	@Override
	protected String getViewPath() {
		return "/manager/goods/goodsItemAttr/";
	}

	
	
	/**
	 * 改变商品属性值关联状态
	 * @param request
	 * @param response
	 * @param itemId
	 * @param attrId
	 * @param valueId
	 * @param isBind true为插入，false为取消
	 * @return 0 成功，1失败，2值不符合条件(为空等)
	 */
	@RequestMapping(value="goods_attr_value_bind",description="改变商品属性值关联状态",aclLevel=EAclLevel.SESSION)
    @ResponseBody
	public String changeTheState(HttpServletRequest request, HttpServletResponse response,Long itemId,Long attrId,String valueId,Boolean isBind){
		if (itemId != null && attrId != null && valueId != null) {
			try{
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("itemId",itemId);
				map.put("attrId",attrId);
				GoodsItemAttrValue goodsItemAttrValue = goodsItemAttrValueService.findUniqueByProperty(map);
				if(goodsItemAttrValue==null){
					goodsItemAttrValue=new GoodsItemAttrValue(itemId,attrId,valueId);
				}
				if(isBind!=null&&isBind){
					if(!goodsItemAttrValue.getValuesId().contains(valueId)){
						String values=goodsItemAttrValue.getValuesId()+","+valueId;
						goodsItemAttrValue.setValuesId(values);
					}
				}else{
					if(goodsItemAttrValue.getValuesId().contains(valueId)){
						String repStr=valueId;
						if(goodsItemAttrValue.getValuesId().contains(","))
						if(goodsItemAttrValue.getValuesId().startsWith(valueId)){
							repStr=repStr+",";
						}else{
							repStr=","+repStr;
						}
						goodsItemAttrValue.setValuesId(goodsItemAttrValue.getValuesId().replace(repStr, ""));
					}
				}
				goodsItemAttrValue=goodsItemAttrValueService.save(goodsItemAttrValue);
			}catch (Exception e) {
				e.printStackTrace();
				return "1";
			}
			return "0";
		}else{
			return "2";
		}
	}

}
