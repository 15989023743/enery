package com.gdwz.energy.web.controller.manager.sysparam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.energy.sysparam.entity.RunMode;
import com.gdwz.energy.sysparam.service.IRunModeService;
import com.gdwz.rbac.interfaces.ISessionLogin;
import com.ibm.icu.text.SimpleDateFormat;


/**
 * @author Gary
 *
 */
@Controller
@RequestMapping(value="/manager/sysParam/runManage/",description="运营参数管理")
public class RunModeController extends CrudController<RunMode,String> {

	@Autowired
	private IRunModeService runService;
	
	@Autowired
	private ISessionLogin sessionLogin;
  
    @Override
    protected IGeneralService<RunMode> getDefaultService() {
        return this.runService;
    }

    @Override
    protected String getViewPath() {
        return "/manager/sysParam/runManage/";
    }
    
    /**
	 * @Title: index
	 * @Description: 运行参数管理首页面
	 * @param request
	 * @param response
	 * @return ModelAndView 返回类型
	 */
	@RequestMapping({ "/index.htm" })
	public ModelAndView index(@RequestParam("mid") String mid, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(getViewPath()+ "index");
		//request.setAttribute("submenu",sessionLogin.getSubmenu(MemberAuthenticationUtils.getCurrentUserName(request),mid));
		mv.addObject("submenu", sessionLogin.getSubmenu(MemberAuthenticationUtils.getCurrentUserName(request),mid));
		return mv;
	}
    
	/**
	 * @Title: run_mode_list
	 * @Description: 运营参数列表
	 * @param request
	 * @param response
	 * @return ModelAndView 返回类型
	 */
	@RequestMapping({ "/run_mode_list.htm" })
	public ModelAndView runModeList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = createModeView(request.getParameter("runFlag"));
		List<RunMode> runList = runService.findAll();
		if (runList != null && runList.size()>0){
			mv.addObject("entity", runList.get(0));
		}
		return mv;
	}
	
	
	/**
	 * @Title: run_mode_setting
	 * @Description: 编辑运营参数
	 * @param request
	 * @param response
	 * @return ModelAndView 返回类型
	 */
	@RequestMapping({ "/run_mode_setting.htm" })
	public ModelAndView runModeSetting(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = createModeView(request.getParameter("runFlag"));
		this.editRunMode(request, mv);
		return mv;
	}

	/**
	 * 编辑运营参数
	 * @param request
	 * @param mv
	 */
	private void editRunMode(HttpServletRequest request, ModelAndView mv){
		try {
			String runFlag = request.getParameter("runFlag");
			String id = request.getParameter("id");
			String optType = request.getParameter("optType");
			RunMode entity = runService.findById(id);
			
			if (entity!=null){
				if ("type".equals(runFlag)){//交易状态
					System.out.println("交易状态");
					entity.setBusinessType(request.getParameter("businessType"));
				}else if ("date".equals(runFlag)){//交易日期
					System.out.println("交易日期");
					if ("sssx".equals(optType)){//实时生效
						entity.setNoMonD(request.getParameter("noMonUCheckbox")!=null?"0":"1");
						entity.setNoTuesD(request.getParameter("noTuesUCheckbox")!=null?"0":"1");
						entity.setNoWednesD(request.getParameter("noWednesUCheckbox")!=null?"0":"1");
						entity.setNoThursD(request.getParameter("noThursUCheckbox")!=null?"0":"1");
						entity.setNoFriD(request.getParameter("noFriUCheckbox")!=null?"0":"1");
						entity.setNoSaturD(request.getParameter("noSaturUCheckbox")!=null?"0":"1");
						entity.setNoSunD(request.getParameter("noSunUCheckbox")!=null?"0":"1");
						entity.setNoDateD(request.getParameter("noDateU"));
					}
					entity.setNoMonU(request.getParameter("noMonUCheckbox")!=null?"0":"1");
					entity.setNoTuesU(request.getParameter("noTuesUCheckbox")!=null?"0":"1");
					entity.setNoWednesU(request.getParameter("noWednesUCheckbox")!=null?"0":"1");
					entity.setNoThursU(request.getParameter("noThursUCheckbox")!=null?"0":"1");
					entity.setNoFriU(request.getParameter("noFriUCheckbox")!=null?"0":"1");
					entity.setNoSaturU(request.getParameter("noSaturUCheckbox")!=null?"0":"1");
					entity.setNoSunU(request.getParameter("noSunUCheckbox")!=null?"0":"1");
					entity.setNoDateU(request.getParameter("noDateU"));
				}else if ("time".equals(runFlag)){//交易时间
					System.out.println("交易时间");
					if ("sssx".equals(optType)){//实时生效
						entity.setBpmTimeD(formatShortDate(request.getParameter("bpmtu")));
						entity.setbTimeD(formatShortDate(request.getParameter("btu")));
						entity.setEpmTimeD(formatShortDate(request.getParameter("epmtu")));
						entity.seteTimeD(formatShortDate(request.getParameter("etu")));
					}
					
					entity.setBpmTimeU(formatShortDate(request.getParameter("bpmtu")));
					entity.setbTimeU(formatShortDate(request.getParameter("btu")));
					entity.setEpmTimeU(formatShortDate(request.getParameter("epmtu")));
					entity.seteTimeU(formatShortDate(request.getParameter("etu")));
				}else{//运行模式
					System.out.println("运行模式");
					entity.setRunMode(request.getParameter("runMode"));
				}
				//执行修改
				this.runService.save(entity);
			}
			List<RunMode> runList = runService.findAll();
			if (runList != null && runList.size()>0){
				mv.addObject("entity", runList.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取返回模型
	 * @param runFlag
	 * @return
	 */
	private ModelAndView createModeView(String runFlag){
		StringBuffer retPage = new StringBuffer(getViewPath());
		if ("type".equals(runFlag)){//交易状态
			retPage.append("run_type_setting");
		}else if ("date".equals(runFlag)){//交易日期
			retPage.append("run_date_setting");
		}else if ("time".equals(runFlag)){//交易时间
			retPage.append("run_time_setting");
		}else{//运行模式
			retPage.append("run_mode_setting");
		}
		ModelAndView mv = new ModelAndView(retPage.toString());
		return mv;
	}
	
	/**
     * @param v
     * @return 格式化的字符串
     * @Title: formatShortDate
     * @Description: 对象型转日期类型字符串 yyyy-MM-dd HH:mi:ss
     */
    private Date formatShortDate(String time) {
    	Date retDate = null;
        try {
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date());
			retDate = sdf.parse(date+" "+time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return retDate;
    }
}
