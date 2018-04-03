package com.gdwz.energy.web.controller.manager.sysparam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.commons.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.energy.sysparam.entity.TDbpTimePara;
import com.gdwz.energy.sysparam.service.ITDbpTimeParaService;
import com.gdwz.rbac.interfaces.ISessionLogin;



/**
 * @param <T>
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： TDbpDppController.java
 * @类描述： 3.2.1监管与自主交易参数设置
 * @当前版本：1.0
 * @修改备注
 */
@Controller
@RequestMapping(value="/manager/sysParam/timePara/",description="/时间参数管理")
public class TDbpTimeController extends CrudController<TDbpTimePara, String> {

	@Autowired
	private ITDbpTimeParaService tDbpTimeParaService;
	
	@Autowired
	private ISessionLogin sessionLogin;

	@Override
	protected IGeneralService<TDbpTimePara> getDefaultService() {
		return this.tDbpTimeParaService;
	}

	@Override
	protected String getViewPath() {
		
		return "/manager/sysParam/transParam/time/";
	}
	
	
	/**
	 * @Title: index
	 * @Description: 交易参数管理
	 * @param request
	 * @param response
	 * @return ModelAndView 返回类型
	 */
	@RequestMapping({ "/index.htm" })
	public ModelAndView index(@RequestParam("mid") String mid, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(getViewPath()+ "index");
		mv.addObject("submenu", sessionLogin.getSubmenu(MemberAuthenticationUtils.getCurrentUserName(request),mid));
		return mv;
	}
	
	/**
	 * 分页查询(list)回调函数，该方法在执行查询之前调用。可以继续添加过滤条件和排序条件。
	 * @param filters
	 * @param request
	 * @param response
	 */
	protected void beforeList(Page<TDbpTimePara> page,ListPropertyFilter filters,HttpServletRequest request,HttpServletResponse response){
		/*if (CommonUtils.isEmpty(request.getParameter("dbpType"))){
			filters.addFilter("dbpType", "4",  PropertyFilter.MatchType.EQ);
		}
		if (CommonUtils.isEmpty(request.getParameter("dbpStation"))){
			filters.addFilter("dbpStation", "0",  PropertyFilter.MatchType.EQ);
		}*/
		String p_flag = request.getParameter("p_flag");
		if("no".equals(p_flag)){
			filters.addFilter("enterpriseId", "0",PropertyFilter.MatchType.EQ);
		}
		if("yes".equals(p_flag)){
			filters.addFilter("member.fullName"," ", PropertyFilter.MatchType.NE);
		}
	};
	
	
	/**
	 * 分页查询(doList)回调函数，该方法在返回视图之前调用。可以继续添加返回信息。
	 * @param page
	 * @param modelAndView
	 * @param request
	 * @param response
	 */
	protected void afterList(Page<TDbpTimePara> page,ModelAndView modelAndView,HttpServletRequest request,HttpServletResponse response){
		createModeView(request,modelAndView);
	};
	
	/**
	 * 保存实体(save)回调函数，在执行保存之前调用用。可以进行数据校验
	 * @param entity
	 * @param modelAndView
	 * @param request
	 * @param response
	 * @param errors
	 */
	 protected void beforeSave(TDbpTimePara entity,ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response,BindException errors){
		 if(CommonUtils.isEmpty(entity.getId())){//新增
			 entity.setEnterpriseId(entity.getMember().getId());
			 entity.setPavaValD(entity.getPavaValU());
			 entity.setTimeTypeD(entity.getTimeTypeU());
			 entity.setUnitD(entity.getUnitU());
		 }else{//修改
			 String pavaValU = request.getParameter("pavaValU");
			 String timeTypeU = request.getParameter("timeTypeU");
			 String unitU = request.getParameter("unitU");
			 String optFlag = request.getParameter("optFlag");
			 if (CommonUtils.isNotEmpty(pavaValU)){
				 entity.setPavaValU(pavaValU);
				 if ("1".equals(optFlag)){//实时生效
					 entity.setPavaValD(pavaValU);
				 }
			 }
			 if (CommonUtils.isNotEmpty(timeTypeU)){
				 entity.setTimeTypeU(timeTypeU);
				 if ("1".equals(optFlag)){//实时生效
					 entity.setTimeTypeD(timeTypeU);
				 }
			 }
			 
			 if (CommonUtils.isNotEmpty(unitU)){
				 entity.setUnitU(unitU);
				 if ("1".equals(optFlag)){//实时生效
					 entity.setUnitD(unitU);
				 }
			 }
		 }
	 };
	 
	
	 /**
	  * 保存实体(save)回调函数，在返回视图之前调用用。可以继续添加返回信息。
	  * @param entity
	  * @param request
	  * @param response
	  */
	 protected void afterSave(TDbpTimePara entity,ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response){
		 String p_flag = request.getParameter("p_flag");
		 if (CommonUtils.isNotEmpty(p_flag)){//个性化
			 modelAndView.addObject("p_flag",p_flag);
		 }
	 };
	
	/**
	 * 获取返回模型
	 * @param request
	 * @return
	 */
	private ModelAndView createModeView(HttpServletRequest request,ModelAndView modelAndView){
		//个性化区分
		String p_flag = request.getParameter("p_flag");
		StringBuffer retPage = new StringBuffer(getViewPath());
		if("yes".equals(p_flag)){
			retPage.append("time_list_personalise");
		}else{
			retPage.append("time_list");
		}		
		modelAndView.setViewName(retPage.toString());
		modelAndView.addObject("p_flag", p_flag);
		return modelAndView;
	}
}
