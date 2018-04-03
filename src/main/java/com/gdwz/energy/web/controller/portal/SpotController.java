package com.gdwz.energy.web.controller.portal;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.core.web.utils.orm.JpaWebUtils;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.entity.IssueStateEnum;
import com.gdwz.energy.trade.service.IIssueAccesoryService;
import com.gdwz.energy.trade.service.IIssueGoodsAttrValuesService;
import com.gdwz.energy.trade.service.IIssueInfoService;
import com.gdwz.web.utils.WebAppUtils;

@Controller
@RequestMapping(value = "/spot/", description = "门户_现货超市")
public class SpotController extends SimpleController {

	@Autowired
	public IIssueGoodsAttrValuesService iIssueGoodsAttrValuesService;
	
	@Autowired
	private IIssueAccesoryService iIssueAccesoryService;

	@Autowired
	public IIssueInfoService issueInfoService;

	@RequestMapping(value = "index", description = "现货超市首页", aclLevel = EAclLevel.WHITE)
	public ModelAndView list(Page<IssueInfo> page, HttpServletRequest request,
			HttpServletResponse response, String descOrAsc) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		ModelAndView modelAndView = createModeAndView();
		ListPropertyFilter filters = JpaWebUtils.buildPropertyFilters(request);
		filters.addFilter("deleteStatus", false, MatchType.EQ);
		filters.addFilter("issueState", IssueStateEnum.SHELVES, MatchType.EQ);
		filters.addFilter("validTime", date, MatchType.GE);

		//前台属性查询
		Enumeration<String> names = request.getParameterNames();
		Map<String, String> attrMap = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if (name.startsWith("attr_")) {
				String value = request.getParameter(name).trim();
				if(value!="" || !"".endsWith(value)){
					attrMap.put(name.replace("attr_", ""), value);
				}
			}
		}
		//前台属性查询返回的ID判断
		List<Long> infosId = iIssueGoodsAttrValuesService
				.getIssueIdsByAttrs(attrMap);
		if (attrMap.size() > 0) {
			if (infosId.size() > 0) {
				filters.addFilter("id", infosId, MatchType.IN);
			} else {
				filters.addFilter("id", MatchType.ISNULL);
			}
		}
		
		String orderBy = request.getParameter("orderBy");
		String order = request.getParameter("order");
		if (orderBy != null && order != null) {

		} else {
			page.setOrderBy("checkTime");
			page.setOrder("desc");
		}
		page.setPageSize(12);

		try {
			page = this.issueInfoService.findPagesByFilters(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (page != null) {
			List<IssueInfo> infos = page.getResult();
			//取产地属性
			Map<String, Object> map = new HashMap<String, Object>();
			for (IssueInfo info : infos) {
				for (IssueGoodsAttrValues attr : info.getIssueGoodsAttrValues()) {
					map.put(info.getId() + "_" + attr.getAttrCode(),
							attr.getAttrValue());
				}
			}
			modelAndView.addObject("attrValuesMap", map);
			modelAndView.addObject("isHasNext", page.isHasNext());
			modelAndView.addObject("isHasPre", page.isHasPre());

		}
		modelAndView.addObject("page", page);
		modelAndView.setViewName("portal/spot/index");
		return modelAndView;
	}
	
	@RequestMapping(value = "downloadAccesory", description = "现货超市附件下载", aclLevel = EAclLevel.WHITE)
	public String downloadAccesorys(long id,HttpServletResponse response,HttpServletRequest request,RedirectAttributes redirectAttributes){
		String returnUrl=REDIRECT+"/message.htm";
		redirectAttributes.addFlashAttribute("urladdress", request.getHeader("referer"));
		IssueAccesory issueAccesory=iIssueAccesoryService.getIssueUalityIndicatorByIssueId(id);
		if(issueAccesory==null){
    		redirectAttributes.addFlashAttribute(MESSAGE_NAME, "找不到该文件");
    		return returnUrl;
		}
		String path=issueAccesory.getAccesoryPath();
		String root=request.getSession().getServletContext().getRealPath("/");
    	path=root+path;
        // path是指欲下载的文件的路径。
    	File file = new File(path);
    	if(!file.exists()){
    		redirectAttributes.addFlashAttribute(MESSAGE_NAME, "找不到该文件");
    		return returnUrl;
    	}else{
    		// 取得文件名。
            String filename = file.getName();
    		try {
    			WebAppUtils.downloadFile(response, filename, new FileInputStream(file));
    		} catch (FileNotFoundException e) {
    			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "下载失败");
    			return returnUrl;
    		} catch (IOException e) {
    			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "下载失败");
    			return returnUrl;
    		}
    	}
		return "";
	}		
}
