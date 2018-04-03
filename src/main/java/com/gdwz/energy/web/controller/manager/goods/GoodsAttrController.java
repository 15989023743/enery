package com.gdwz.energy.web.controller.manager.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.dict.service.IDictService;
import com.gdwz.core.entity.BaseDict;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.goods.entity.GoodsAttr;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsAttrService;
import com.gdwz.energy.goods.service.IGoodsItemService;

/**
 * Created by lemon on 14-9-2.
 */
@Controller
@RequestMapping(value="/manager/goods/goodAttr/",description="后台商品管理信息管理")
public class GoodsAttrController extends CrudController<GoodsAttr,Long> {

    @Autowired
    private IGoodsAttrService goodsAttrService;
    
    @Autowired
    private IGoodsItemService goodsItemService;
    
    @Autowired
    private IDictService dictService;

    @Override
    protected IGeneralService<GoodsAttr> getDefaultService() {
        return this.goodsAttrService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/goods/goodAttr/";
    }
    
    
    /*private String returnUrl=null;
    
    
    @Override
	public String getDefaultRedirectReturnUrl() {
		return returnUrl;
	}*/
    
    
    /**
     * AJAX 属性名称是否唯一
     * @param orgCode
     * @param code
     * @return
     */
    @RequestMapping(value="checkName",description="AJAX属性名称是否唯一",aclLevel=EAclLevel.SESSION)
    @ResponseBody
    public String checkName(@RequestParam("orgName") String orgCode, @RequestParam("name") String name, @RequestParam("itemid") Long itemid) {
        if(name.equals(orgCode)){
            return "true";
        }
        List<Long> list=null;
        if(itemid!=null){
	        GoodsItem goodsItem = goodsItemService.findById(itemid);
			list=getIdsByString(goodsItem.getParentids(),"-");
			list.add(itemid);
        }else{
        	list=new ArrayList<Long>();
			list.add(-1L);
        }
        if (goodsAttrService.countByUnique(name, list)==0){
            return "true";
        }
        return "false";
    }
    
    
    @RequestMapping(value="changeEnable",description="改变停用启用状态",aclLevel=EAclLevel.SESSION)
    @ResponseBody
    public String changeEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
    	try {
			GoodsAttr goodsAttr=getModelById(id);
			goodsAttr.setEnabled(enable);
			goodsAttrService.save(goodsAttr);
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
    }

	@RequestMapping(value="goods_attr_json",description="获取商品类目属性Json",aclLevel=EAclLevel.SESSION)
    @ResponseBody
    public void goodsAttrsJson(HttpServletRequest request, HttpServletResponse response,Long itemid){
    	GoodsItem item=goodsItemService.findById(itemid);
    	ListPropertyFilter listPropertyFilter=ListPropertyFilter.getInstance();
    	Map<String,Object> jsonData=new HashMap<String,Object>();
    	List<Long> list=new ArrayList<Long>();
    	if(!StringUtils.isEmpty(item.getParentids())){
    		String[] ids=item.getParentids().split("-");
    		for(String pid:ids){
    			list.add(Long.parseLong(pid));
    		}
    		list.add(item.getId());
    	}
    	listPropertyFilter.addFilter("goodsItem.id", list, MatchType.IN); 
    	//私有属性迭代
    	jsonData.put("selfAttr", goodsItemService.findByFilters(listPropertyFilter));
    	listPropertyFilter=ListPropertyFilter.getInstance();
    	
    	//公有属性
    	listPropertyFilter.addFilter("goodsItem.id", MatchType.ISNULL);
    	jsonData.put("pubAttr", goodsItemService.findByFilters(listPropertyFilter));
    	jsonMapper.renderJson(jsonData, response);
    }
	
	

	@Override
	protected void afterList(Page<GoodsAttr> page, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,BaseDict> dictMap=new HashMap<String,BaseDict>();
		for(GoodsAttr attr:page.getResult()){
			if(!StringUtils.isEmpty(attr.getDictName())){
				List<BaseDict> dicts=dictService.selectByTblNameZdlx(attr.getDictName());
				if(dicts!=null&&dicts.size()>0){
					dictMap.put(attr.getDictName(), dicts.get(0));
				}
			}
		}
		modelAndView.addObject("dictMap", dictMap);
	}

	/*@Override
	protected void onCreate(GoodsAttr entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		returnUrl=request.getHeader("referer");
	}*/
	
	
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
