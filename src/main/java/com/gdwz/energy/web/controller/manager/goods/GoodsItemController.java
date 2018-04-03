package com.gdwz.energy.web.controller.manager.goods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.dict.service.IDictService;
import com.gdwz.core.entity.BaseDict;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.web.controller.TreeController;
import com.gdwz.energy.goods.entity.GoodsAttr;
import com.gdwz.energy.goods.entity.GoodsItem;
import com.gdwz.energy.goods.service.IGoodsAttrService;
import com.gdwz.energy.goods.service.IGoodsItemService;
import com.gdwz.template.ConfigInfo;
import com.gdwz.template.utils.TemplateBuilder;


/**
 * Created by lemon on 14-9-2.
 */
@Controller
@RequestMapping(value="/manager/goods/goodsItem/",description="后台分类/品名管理信息管理")
public class GoodsItemController extends TreeController<GoodsItem, Long> {

    @Autowired
    private IGoodsItemService goodsItemService;

    @Autowired
    private IGoodsAttrService iGoodsAttrService;

    @Autowired
    private IDictService dictService;


    @Override
    protected IGoodsItemService getDefaultService() {
        return this.goodsItemService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/goods/goodsItem/";
    }

    
    
    @Override
	protected void afterSave(Long parentId, GoodsItem entity,
			ModelAndView modelAndView, HttpServletRequest request,
			HttpServletResponse response) {
		buileNavIgation(request);
	}

	@Override
	protected void afterDelete(Long nodeId, HttpServletRequest request,
			HttpServletResponse response) {
		buileNavIgation(request);
	}

	@Override
	protected void afterSort(ModelAndView mav, Long parentId, Long currentId,
			Integer xh, HttpServletRequest request, HttpServletResponse response) {
		buileNavIgation(request);
	}

	/**
     * AJAX 类目代码是否唯一
     * @param orgCode
     * @param code
     * @return
     */
    @RequestMapping(value="checkCode",description="AJAX类目代码是否唯一",aclLevel=EAclLevel.SESSION)
    @ResponseBody
    public String checkCode(@RequestParam("orgCode") String orgCode, @RequestParam("code") String code) {
        if(code.equals(orgCode)){
            return "true";
        }
        GoodsItem goodsItem = new GoodsItem();
        goodsItem.setCode(code);
        if (goodsItemService.isUnique(goodsItem, "code")){
            return "true";
        }
        return "false";
    }
    
    
    @RequestMapping(value="checkName",description="AJAX类目名称是否唯一",aclLevel=EAclLevel.SESSION)
    @ResponseBody
    public String checkName(@RequestParam("orgName") String orgName, @RequestParam("name") String name,@RequestParam("pid") Long pid){
    	if(name.equals(orgName)){
    		return "true";
    	}
    	try {
    		List<Long> list=null;
    		if(pid!=null){
				GoodsItem goodsItem = getModelById(pid);
				list=getIdsByString(goodsItem.getParentids(),"-");
				list.add(pid);
    		}else{
    			list=new ArrayList<Long>();
    			list.add(-1L);
    		}
			if(goodsItemService.countByUnique(name, list)==0){
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
    	return "false";
    }

    
    /**
     * 类目管理主页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "item",description="类目管理")
    public ModelAndView item(HttpServletRequest request, HttpServletResponse response,String option,Long id){
    	ModelAndView modelAndView = createModeAndView();
        modelAndView.setViewName(getViewPath()+"item");
        StringBuffer paramterUrl=getParamtersStrByRequest(request);
        if(StringUtils.isEmpty(option)){
        	paramterUrl.append("option=view");
        }
        if(id!=null){
        	onItem(modelAndView,request, response, option, id);
        }
        modelAndView.addObject("paramterStr", paramterUrl.toString());
        return modelAndView;
    }
    
    

    /**
     * 判断是叶子还是树干
     * @param parentId
     * @param entity
     * @param modelAndView
     * @param request
     * @param response
     * @param errors
     */
    @Override
    protected void beforeSave(Long parentId, GoodsItem entity, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response, BindException errors) {
        String isLeaf = request.getParameter("IsLeaf");
        if(isLeaf.equals("true")){ 
            entity.setCategory("F");
            entity.setLeaf(true);
        }else{
            entity.setCategory("M");
            entity.setLeaf(false);
        }
        modelAndView.setViewName(getViewPath()+"input");
    }
    

	@Override
	protected void onView(GoodsItem entity, ModelAndView mav,
			HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName(getViewPath()+"input");
	}
	
	
	public StringBuffer getParamtersStrByRequest(HttpServletRequest request){
		
		Map<String, String[]> paramter=request.getParameterMap();
		StringBuffer paramterUrl=new StringBuffer("");
        for(String key:paramter.keySet()){
        	String[] values=paramter.get(key);
        	for(String value:values){
        		paramterUrl.append(key);
        		paramterUrl.append("=");
        		paramterUrl.append(value);
        		paramterUrl.append("&");
        	}
        }
        return paramterUrl;
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
	
    
	/**
	 * 
	 * @param modelAndView
	 * @param request
	 * @param response
	 * @param option
	 * @param id
	 */
	public void onItem(ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response,String option,Long id){
		GoodsItem item;
		try {
			item = getModelById(id);
			ListPropertyFilter listPropertyFilter=ListPropertyFilter.getInstance();
	    	Map<Long,List<BaseDict>> attrValues=new HashMap<Long,List<BaseDict>>();
	    	
	    	
	    	//私有属性迭代
	    	listPropertyFilter.addFilter("enabled", true, MatchType.EQ);
	    	listPropertyFilter.addFilter("goodsItem.id", item.getId(), MatchType.EQ);
	    	List<GoodsAttr> selfAttrs=iGoodsAttrService.findByFilters(listPropertyFilter);
	    	modelAndView.addObject("selfAttr", selfAttrs);
	    	
	    	
	    	//公有属性
	    	listPropertyFilter=ListPropertyFilter.getInstance();
	    	listPropertyFilter.addFilter("goodsItem.id", MatchType.ISNULL);
	    	listPropertyFilter.addFilter("enabled", true, MatchType.EQ);
	    	List<GoodsAttr> pubAttrs=iGoodsAttrService.findByFilters(listPropertyFilter);
	    	modelAndView.addObject("pubAttr", pubAttrs);
	    	
	    	
	    	
	    	List<GoodsAttr> parAttrs=null;
	    	//上级属性
	    	if(item.getParent()!=null){
	    		List<Long> list=getIdsByString(item.getParentids(),"-");
	    		listPropertyFilter=ListPropertyFilter.getInstance();
	    		listPropertyFilter.addFilter("enabled", true, MatchType.EQ);
	    		listPropertyFilter.addFilter("goodsItem.id", list, MatchType.IN);
	    		parAttrs=iGoodsAttrService.findByFilters(listPropertyFilter);
	    		modelAndView.addObject("parAttrs", parAttrs);
	    		modelAndView.addObject("parentId", item.getParent().getId());
	    	}
	    	
	    	
	    	if(item.isLeaf()){
	    		List<GoodsAttr> attrs=new ArrayList<GoodsAttr>();
		    	attrs.addAll(selfAttrs);
		    	attrs.addAll(pubAttrs);
		    	if(parAttrs!=null){
		    		attrs.addAll(parAttrs);
		    	}
		    	for(GoodsAttr attr:attrs){
		    		if(!StringUtils.isEmpty(attr.getDictName())){
		    			attrValues.put(attr.getId(), dictService.selectByTblNameZdlx(attr.getDictName()));
		    		}
		    	}
		    	modelAndView.addObject("attrValues", attrValues);
	    	}
	    	
	    	modelAndView.addObject("entity", item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private void buileNavIgation(HttpServletRequest request){
        Map<String,Object> map_model = new HashMap<String,Object>();
        String template_home = request.getSession().getServletContext().getRealPath("/WEB-INF/template/");
        String out_path = request.getSession().getServletContext().getRealPath("/");
        TemplateBuilder tb = new TemplateBuilder(template_home);
        File file = new File(out_path+"/WEB-INF/templates/portal/share/");
        if(!file.exists())
            file.mkdirs();
        List<GoodsItem> topItems=goodsItemService.getGoodsItem();
        Map<Long,List<GoodsItem>> itemMapsMap=new HashMap<Long,List<GoodsItem>>();
        for(GoodsItem item:topItems){
        	itemMapsMap.put(item.getId(),goodsItemService.getEnabledGoodsByParentId(item.getId(), true));
        }
        map_model.put("goodsItems" ,topItems);
        map_model.put("itemMapsMap" ,itemMapsMap);
        tb.execute(map_model, "/portal/share/goodsItems."+ ConfigInfo.getProperties("out.format"), file.getPath()+"/goodsItems."+ConfigInfo.getProperties("out.format"));
        tb.execute(map_model, "/portal/share/resourceClassificationMenu."+ ConfigInfo.getProperties("out.format"), file.getPath()+"/resourceClassificationMenu."+ConfigInfo.getProperties("out.format"));
    }
    
}
