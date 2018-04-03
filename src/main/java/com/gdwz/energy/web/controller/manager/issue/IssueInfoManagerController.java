package com.gdwz.energy.web.controller.manager.issue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueAccesoryInIssue;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.entity.IssueStateEnum;
import com.gdwz.energy.trade.service.IIssueAccesoryService;
import com.gdwz.energy.trade.service.IIssueInfoService;
import com.gdwz.system.web.controller.manager.cms.utils.CmsTools;
import com.gdwz.web.utils.WebAppUtils;

@Controller
@RequestMapping(value="/manager/trade/issue/",description="后台挂牌管理信息管理")
public class IssueInfoManagerController extends CrudController<IssueInfo,Long>{

	@Autowired
	private IIssueInfoService iIssueInfoService;
	
	@Autowired
	private IIssueAccesoryService iIssueAccesoryService;
	
	@Override
	protected IGeneralService<IssueInfo> getDefaultService() {
		return this.iIssueInfoService;
	}

	@Override
	protected String getViewPath() {
		return "/manager/trade/issue/";
	}

	@RequestMapping(value="auditing",description="审核挂牌信息",aclLevel=EAclLevel.RESTRICTE)
	public String auditing(@RequestParam("id")Long id,HttpServletRequest request, RedirectAttributes redirectAttributes)
	{ 
		boolean flag=iIssueInfoService.auditing(id);
		if(flag){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "上架成功");
		}
		else{			
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "上架失败");
		}
		return this.getRedirectReturnUrl(request);
	}
	
	@RequestMapping(value="backAuditing",description="退回挂牌信息",aclLevel=EAclLevel.RESTRICTE)
	public String backAuditing(@RequestParam("id") long id,HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		boolean flag=iIssueInfoService.auditing(id,IssueStateEnum.FALLBACK,IssueStateEnum.TOAUDIT);
		if(flag){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "退回成功");
		}
		else{			
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "退回失败");
		}
		return this.getRedirectReturnUrl(request);
	}
	
    /**
     * 批量上架
     * @param request
     * @param ids
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping(value="displays",description="批量上架 ")
    public String displays(HttpServletRequest request,String[] ids, char issueStatus,char nowIssueStatus) throws Exception {
    	//this.iIssueInfoService.setDisplayStatu(ids, status);
    	//CmsTools.buileAllCatalog(request);
        return this.doReturn(request);
    }
    
    /**
     * 改变挂牌状态
     * @param id
     * @param changeState
     * @param nowState
     * @param request
     * @param redirectAttributes
     * @return
     */
	@RequestMapping(value="chageState",description="修改挂牌状态",aclLevel=EAclLevel.RESTRICTE)
	public String chageState(@RequestParam("id") long id,@RequestParam("changeState") IssueStateEnum changeState,@RequestParam("nowState") IssueStateEnum nowState,HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		boolean flag=iIssueInfoService.auditing(id,changeState,nowState);
		if(flag){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作成功");
		}
		else{			
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作失败");
		}
		return this.getRedirectReturnUrl(request);
	}
	//updatePrice
	
	@RequestMapping(value="updatePrice",description="调价",aclLevel=EAclLevel.RESTRICTE)
	public String updatePrice(@RequestParam("id") long id,@RequestParam("unitPriceExclud") BigDecimal unitPriceExclud,@RequestParam("unitPrice") BigDecimal unitPrice,HttpServletRequest request,RedirectAttributes redirectAttributes){
		IssueInfo issue=iIssueInfoService.findById(id);
		issue.setUnitPrice(unitPrice);
		issue.setUnitPriceExclud(unitPriceExclud);
		
		if(iIssueInfoService.save(issue)!=null){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作成功");
		}else{
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作失败");
		}
		return this.getRedirectReturnUrl(request);
	}

			
	@Override
	protected void afterList(Page<IssueInfo> page, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String d=formatDate.format(new Date());
		try {
			Date time= formatDate.parse(d);
			modelAndView.addObject("today",time);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="check",description="后台查看挂牌",aclLevel=EAclLevel.RESTRICTE)
	public ModelAndView check(@RequestParam("id") long id,@RequestParam("type") String type,HttpServletRequest request) throws Exception{
		ModelAndView mv=createModeAndView();
		mv.setViewName(getViewPath()+"view");
		//迭代属性
		IssueInfo issueInfo=initEntity(id,mv,request);
		addAttr(issueInfo,mv);
		if(!StringUtils.isEmpty(id+"")){
			mv.addObject("issueUalityIndicator", iIssueAccesoryService.getIssueUalityIndicatorByIssueId(id));
			mv.addObject("issueAccesorys", iIssueAccesoryService.getIssueAccesoryInIssueByIssueId(id));
		}
		return mv;
	}

	@Override
	protected void onUpdate(IssueInfo entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		addAttr(entity,modelAndView);
		modelAndView.addObject("issueUalityIndicator", iIssueAccesoryService.getIssueUalityIndicatorByIssueId(entity.getId()));
		modelAndView.addObject("issueAccesorys", iIssueAccesoryService.getIssueAccesoryInIssueByIssueId(entity.getId()));
		
	}
	
	public void addAttr(IssueInfo issueInfo,ModelAndView mv){
		//迭代属性
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb=new StringBuffer();
		for (IssueGoodsAttrValues attr : issueInfo.getIssueGoodsAttrValues()) {
			sb.append(attr.getAttrName()).append(":").append(attr.getAttrValue());
			sb.append("</br>");
		}
		map.put(issueInfo.getId()+"",sb);
		if(map.size()>0){
			mv.addObject("attrValuesMap", map);
		}
	}
	
	@RequestMapping(value = "downloadAccesory", description = "后台挂牌附件下载", aclLevel = EAclLevel.RESTRICTE)
	public String downloadAccesorys(long id,HttpServletResponse response,HttpServletRequest request,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("urladdress", request.getHeader("referer"));
		IssueAccesory issueAccesory=iIssueAccesoryService.getIssueUalityIndicatorByIssueId(id);
		if(issueAccesory==null){
    		redirectAttributes.addFlashAttribute(MESSAGE_NAME, "找不到该文件");
		}
		String path=issueAccesory.getAccesoryPath();
		String root=request.getSession().getServletContext().getRealPath("/");
    	path=root+path;
        // path是指欲下载的文件的路径。
    	File file = new File(path);
    	if(!file.exists()){
    		redirectAttributes.addFlashAttribute(MESSAGE_NAME, "找不到该文件");
    	}else{
    		// 取得文件名。
            String filename = file.getName();
    		try {
    			WebAppUtils.downloadFile(response, filename, new FileInputStream(file));
    		} catch (FileNotFoundException e) {
    			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "下载失败");
    		} catch (IOException e) {
    			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "下载失败");
    		}
    	}
    	return this.getRedirectReturnUrl(request);
	}
}
