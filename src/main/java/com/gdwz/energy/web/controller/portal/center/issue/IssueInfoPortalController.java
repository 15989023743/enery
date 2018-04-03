package com.gdwz.energy.web.controller.portal.center.issue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.Member;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.energy.code.service.IWasteMatchingService;
import com.gdwz.energy.goods.entity.GoodsAttr;
import com.gdwz.energy.goods.service.IGoodsAttrService;
import com.gdwz.energy.goods.service.IGoodsItemService;
import com.gdwz.energy.trade.entity.IssueAccesory;
import com.gdwz.energy.trade.entity.IssueAccesoryInIssue;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.entity.IssueStateEnum;
import com.gdwz.energy.trade.entity.IssueUalityIndicator;
import com.gdwz.energy.trade.service.IIssueAccesoryService;
import com.gdwz.energy.trade.service.IIssueGoodsAttrValuesService;
import com.gdwz.energy.trade.service.IIssueInfoService;
import com.gdwz.web.utils.WebAppUtils;

@Controller
@RequestMapping(value="/center/trade/issue/",description="前台挂牌管理")
public class IssueInfoPortalController extends CrudController<IssueInfo, Long> {

	@Autowired
	private IIssueInfoService issueInfoService;
	
	@Autowired
	private IGoodsItemService goodsItemService;
	
	@Autowired
	private IIssueAccesoryService issueAccesoryService;
	
	@Autowired
	private IWasteMatchingService wasteMatchingService;
	
	@Autowired
	private IGoodsAttrService goodsAttrService;
	
	@Autowired
	private IIssueGoodsAttrValuesService issueGoodsAttrValuesService;
	
	@Autowired
	public IIssueGoodsAttrValuesService iIssueGoodsAttrValuesService;
	
	@Autowired
	private IIssueAccesoryService iIssueAccesoryService;
	
	
	@Override
	protected IIssueInfoService getDefaultService() {
		return issueInfoService;
	}

	@Override
	protected String getViewPath() {
		return "portal/center/issue/";
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
		boolean flag=false;
		try {
			flag = issueInfoService.auditing(id,changeState,nowState);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作成功");
		}
		else{			
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作失败");
		}
		return this.getRedirectReturnUrl(request);
	}
	

	@Override
	protected void onCreate(IssueInfo entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		entity.setSellersOfMagin(new BigDecimal(1000));
		entity.setBuyersOfMargin(new BigDecimal(1000));
		//request.setAttribute(WebConstants.REQUEST_ENCODED_CURRENT_PATH,request.getHeader("referer"));
	}

	@Override
	protected void beforeSave(IssueInfo entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response,
			BindException errors) {
		String issueCode="";
		if(StringUtils.isEmpty(entity.getIssueCode())){
			issueCode=wasteMatchingService.getCode("issueCode");
			entity.setIssueCode(issueCode);
		}
		entity.setUnitPriceExclud(StringUtils.isEmpty(request.getParameter("unitPriceExclud"))?new BigDecimal(0):new BigDecimal(request.getParameter("unitPriceExclud")));
		entity.setUnitPrice(StringUtils.isEmpty(request.getParameter("unitPrice"))?new BigDecimal(0):new BigDecimal(request.getParameter("unitPrice")));
		entity.setSellersOfMagin(StringUtils.isEmpty(request.getParameter("sellersOfMagin"))?new BigDecimal(0):new BigDecimal(request.getParameter("sellersOfMagin")));
		entity.setBuyersOfMargin(StringUtils.isEmpty(request.getParameter("buyersOfMargin"))?new BigDecimal(0):new BigDecimal(request.getParameter("buyersOfMargin")));
		entity.setIssueState(IssueStateEnum.values()[Integer.parseInt(request.getParameter("issueState"))]);
		if(entity.getMember()==null){
			entity.setMember(MemberAuthenticationUtils.getCurrentMember(request));
		}
	}
	
	

	@Override
	protected void onSave(IssueInfo entity, ModelAndView modelAndView,
			HttpServletRequest request) throws Exception {
		if(!StringUtils.isEmpty(entity.getIssueCode())){
			entity.setCurrency("￥");
			super.onSave(entity, modelAndView, request);
			String redirectUrl=request.getParameter("redirect_url");
			modelAndView.setViewName(REDIRECT+"/message.htm"+(StringUtils.isEmpty(redirectUrl)?"":"?urladdress="+redirectUrl));
		}else{
			//处理没有挂牌编码的跳转
			modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, entity);
			modelAndView.setViewName(getViewPath() + INPUT_VIEW_NAME);
			modelAndView.addObject("errorType", "issueCode");
		}
	}

	@Override
	protected void afterSave(IssueInfo entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		if(entity==null||entity.getId()==null||StringUtils.isEmpty(entity.getIssueCode())){
			return;
		}
		
		Enumeration<String> names=request.getParameterNames();
		List<IssueGoodsAttrValues> issueGoodsAttrValuess=new ArrayList<IssueGoodsAttrValues>();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			if(name.startsWith("ATTRS_")){
				Long attrId=Long.parseLong(name.replace("ATTRS_", ""));
				GoodsAttr goodsAttr=goodsAttrService.findById(attrId);
				if(goodsAttr!=null){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("issueId", entity.getId());
					param.put("itemId", entity.getGoodsItemId());
					param.put("attrId", goodsAttr.getId());
					IssueGoodsAttrValues issueGoodsAttrValues=issueGoodsAttrValuesService.findUniqueByProperty(param);
					if(issueGoodsAttrValues==null){
						issueGoodsAttrValues=new IssueGoodsAttrValues();
						issueGoodsAttrValues.setIssue(entity);
					}else{
						issueGoodsAttrValues.setDeleteStatus(false);
					}
					super.addOrModifyUserInfo(request, issueGoodsAttrValues);
					issueGoodsAttrValues.setAttrId(goodsAttr.getId());
					issueGoodsAttrValues.setItemId(entity.getGoodsItemId());
					issueGoodsAttrValues.setAttrName(goodsAttr.getName());
					issueGoodsAttrValues.setAttrValue(request.getParameter("valuefor_"+name));
					issueGoodsAttrValues.setAttrValueId(StringUtils.isEmpty(request.getParameter(name))?0:Long.parseLong(request.getParameter(name)));
					issueGoodsAttrValues.setGroup(goodsAttr.getGroup());
					issueGoodsAttrValues.setAttrCode(goodsAttr.getDictName());
					issueGoodsAttrValuess.add(issueGoodsAttrValues);
				}
			}
		}
		issueGoodsAttrValuesService.updateGoodsAttrValues(entity.getId(), issueGoodsAttrValuess);
		
		
		//删除需要删除的附件
		String removeAccesoryIds=request.getParameter("TheRealFigureRemoveId");
		if(!StringUtils.isEmpty(removeAccesoryIds)){
			String[] idstrs=removeAccesoryIds.split(",");
			List<Long> ids=new ArrayList<Long>();
			for(String id:idstrs){
				if(!StringUtils.isEmpty(id)&&StringUtils.isNumeric(id)){
					ids.add(Long.parseLong(id));
				}
			}
			issueAccesoryService.deleteByIds(ids.toArray());
		}
		
		
		if(this.isExistUpload(request)){
			ListPropertyFilter listPropertyFilter=ListPropertyFilter.getInstance();
			listPropertyFilter.addFilter("issueInfo.id", entity.getId(), MatchType.EQ);
			List<IssueAccesory> issueAccesories=new ArrayList<IssueAccesory>();
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				String name=iter.next();
				//上传质量指标
				if(name.equals("qualityIndicator")){
					String filePath=saveFileForUpload(multiRequest,name);
					if(!StringUtils.isEmpty(filePath)){
						IssueUalityIndicator issueUalityIndicator=issueAccesoryService.getIssueUalityIndicatorByIssueId(entity.getId());
						if(issueUalityIndicator==null){
							issueUalityIndicator=new IssueUalityIndicator();
							issueUalityIndicator.setIssueInfo(entity);
						}
						super.addOrModifyUserInfo(request, issueUalityIndicator);
						issueUalityIndicator.setAccesoryPath(filePath);
						issueAccesories.add(issueUalityIndicator);
					}
				}
				
				//上传实物图
				if(name.startsWith("realFigure")){
					String filePath=saveFileForUpload(multiRequest,name);
					if(!StringUtils.isEmpty(filePath)){
						name=name.replace("realFigure_", "");
						Long id=Long.parseLong(name);
						IssueAccesoryInIssue issueAccesory=(IssueAccesoryInIssue) issueAccesoryService.findById(id);
						if(issueAccesory==null){
							issueAccesory=new IssueAccesoryInIssue();
							issueAccesory.setIssueInfo(entity);
						}
						super.addOrModifyUserInfo(request, issueAccesory);
						issueAccesory.setAccesoryPath(filePath);
						issueAccesories.add(issueAccesory);
					}
				}
			}
			issueAccesoryService.saves(issueAccesories);
		}
		
	}
	
	
	private String saveFileForUpload(MultipartHttpServletRequest multiRequest,String name){
		Assert.notNull(name,"Name is not null");
		Assert.notNull(multiRequest,"MultipartHttpServletRequest is not null");
		String destPath="";
		String filePath=null;
		try{
			MultipartFile file = multiRequest.getFile(name);
			if(file != null&&file.getSize()>0){
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
				String uploadDir = "/resources/uploadfile/"+ dateformat.format(new Date());//构建图片保存的目录
				String destDir = multiRequest.getSession().getServletContext().getRealPath(uploadDir);
				File savedir = new File(destDir);
				if(!savedir.exists()) savedir.mkdirs();//如果目录不存在就创建
				String myFileName = file.getOriginalFilename();
				String ext = myFileName.substring(myFileName.lastIndexOf('.'));//文件扩展名
				String newFilename = UUID.randomUUID().toString()+ ext;//构建新文件名称
				destPath = destDir+ File.separatorChar + newFilename; 
				filePath=uploadDir+File.separatorChar + newFilename;
				filePath=filePath.replace("\\", "/");
				File destFile = new File(destPath);
				this.writeFile(file.getInputStream(), destFile);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}
	

	@Override
	protected void onUpdate(IssueInfo entity, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			modelAndView.addObject("issueUalityIndicator", issueAccesoryService.getIssueUalityIndicatorByIssueId(entity.getId()));
			modelAndView.addObject("issueAccesorys", issueAccesoryService.getIssueAccesoryInIssueByIssueId(entity.getId()));
			
			ListPropertyFilter listPropertyFilter=ListPropertyFilter.getInstance();
			listPropertyFilter.addFilter("issue.id", entity.getId() , MatchType.EQ);
			listPropertyFilter.addFilter("itemId", entity.getGoodsItemId() , MatchType.EQ);
			listPropertyFilter.addFilter("deleteStatus" , false , MatchType.EQ);
			List<IssueGoodsAttrValues> issueGoodsAttrValuess=issueGoodsAttrValuesService.findByFilters(listPropertyFilter);
			List<Map<String,Object>> maps=new ArrayList<Map<String,Object>>();
			if(issueGoodsAttrValuess!=null){
				for(IssueGoodsAttrValues goodsAttrValues:issueGoodsAttrValuess){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("issueId",goodsAttrValues.getIssueId());
					map.put("attrId",goodsAttrValues.getAttrId());
					map.put("valueId",goodsAttrValues.getAttrValueId());
					map.put("itemId",goodsAttrValues.getIssue().getGoodsItemId());
					map.put("attrValue", goodsAttrValues.getAttrValue());
					maps.add(map);
				}
			}
			modelAndView.addObject("attrValues", jsonMapper.toJson(maps));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 作废挂牌状态
	 * @param id
	 * @param changeState
	 * @param nowState
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="cancel",description="作废挂牌状态",aclLevel=EAclLevel.RESTRICTE)
	public String cancel(@RequestParam("id") long id,HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		boolean flag=issueInfoService.cancel(id);
		if(flag){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作成功");
		}
		else{			
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "操作失败");
		}
		return this.getRedirectReturnUrl(request);
	}
	/**
	 * 查看挂牌
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="check",description="查看挂牌",aclLevel=EAclLevel.SESSION)
	public ModelAndView check(@RequestParam("id") long id,HttpServletRequest request) throws Exception{
		ModelAndView mv=createModeAndView();
		mv.setViewName(getViewPath()+"view");
		initEntity(id,mv,request);
		return mv;
	}
	
	/**
	 * 调价
	 * @param id
	 * @param unitPriceExclud
	 * @param unitPrice
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="updatePrice",description="调价",aclLevel=EAclLevel.RESTRICTE)
	public String updatePrice(@RequestParam("id") long id,@RequestParam("unitPriceExclud") BigDecimal unitPriceExclud,@RequestParam("unitPrice") BigDecimal unitPrice,HttpServletRequest request,RedirectAttributes redirectAttributes){
		IssueInfo issue=issueInfoService.findById(id);
		issue.setUnitPrice(unitPrice);
		issue.setUnitPriceExclud(unitPriceExclud);
		
		if(issueInfoService.save(issue)!=null){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "调价成功");
		}else{
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "调价失败");
		}
		return this.getRedirectReturnUrl(request);
	}

	@Override
	protected void beforeList(Page<IssueInfo> page, ListPropertyFilter filters,
			HttpServletRequest request, HttpServletResponse response) {
		Member member = MemberAuthenticationUtils.getCurrentMember(request);
		if(member.getId()!=""|| !"".equals(member.getId())){
			filters.addFilter("member.id", member.getId(), MatchType.EQ);
		}
		
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
		List<Long> infosId = iIssueGoodsAttrValuesService.getIssueIdsByAttrsAll(attrMap);
		if (attrMap.size() > 0) {
			if (infosId.size() > 0) {
				filters.addFilter("id", infosId, MatchType.IN);
			} else {
				filters.addFilter("id", MatchType.ISNULL);
			}
		}
	}

	@Override
	protected void afterList(Page<IssueInfo> page, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
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
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			String d=formatDate.format(new Date());
			try {
				Date time= formatDate.parse(d);
				modelAndView.addObject("today",time);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onView(IssueInfo entity, ModelAndView mav,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb=new StringBuffer();
		for (IssueGoodsAttrValues attr : entity.getIssueGoodsAttrValues()) {
			sb.append(attr.getAttrName()).append(":").append(attr.getAttrValue());
			sb.append("</br>");
		}
		map.put(entity.getId()+"",sb);
		if(map.size()>0){
			mav.addObject("attrValuesMap", map);
		}		
		mav.addObject("issueUalityIndicator", issueAccesoryService.getIssueUalityIndicatorByIssueId(entity.getId()));
		mav.addObject("issueAccesorys", issueAccesoryService.getIssueAccesoryInIssueByIssueId(entity.getId()));
	}
	
	@RequestMapping(value = "downloadAccesory", description = "前台挂牌附件下载", aclLevel = EAclLevel.SESSION)
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
