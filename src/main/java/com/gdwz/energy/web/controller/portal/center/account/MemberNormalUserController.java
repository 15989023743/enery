package com.gdwz.energy.web.controller.portal.center.account;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.Member;
import com.gdwz.common.member.entity.MemberUser;
import com.gdwz.common.member.entity.NormalUser;
import com.gdwz.common.member.service.IMemberRoleService;
import com.gdwz.common.member.service.IMemberUserService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.exception.BusinessException;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.core.web.enums.EUserType;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.system.web.controller.manager.member.MemberRoleListEditor;

/**
 * 会员中心_帐户管理_交易员管理Controller
 * @author fanwei
 * Urls:
 */
@Controller
@RequestMapping(value="/center/account/mamber/user/",description="会员中心_帐户管理_交易员管理",subsystem="门户")
public class MemberNormalUserController extends CrudController<MemberUser,String> {
    @Autowired
    private IMemberRoleService memberRoleService;
    @Autowired
    private IMemberUserService memberUserService;

    @Autowired
    private MemberRoleListEditor memberRoleListEditor;

    @Override
    protected IGeneralService<MemberUser> getDefaultService() {
        return memberUserService;
    }

    @Override
    protected String getViewPath() {
    	return "portal/center/account/member/user/";
    }

    @InitBinder
    public void initBinder(WebDataBinder wdb) {
        super.initBinder(wdb);
        wdb.registerCustomEditor(Set.class, "roles", memberRoleListEditor);
    }


	@Override
    protected void onEntity(MemberUser entity,ModelAndView mav,HttpServletRequest request) {
		//获取当前会员下所有的角色
        mav.addObject("allRoles", memberRoleService.getRoleByMember(MemberAuthenticationUtils.getCurrentMember(request).getId()));
    }
    
	@Override
	protected void beforeList(Page<MemberUser> page,
			ListPropertyFilter filters, HttpServletRequest request,
			HttpServletResponse response) {
		if( EUserType.FRONT == MemberAuthenticationUtils.getCurrentLoginUserType(request)){
			//1.过虑当前会员
			filters.addFilter("member", MemberAuthenticationUtils.getCurrentMember(request), MatchType.EQ);
		}else{
			filters.addFilter("member.id", -1, MatchType.EQ);
		}
	}

	/**
	 * 获取实体_如果找不到实体则新建一个实例(当需要获取一个实体时可以调用，例如：保存数据时可以先使用该方法把实体查询出来再把最新的数据邦定在一起)
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected MemberUser getEntity(String id) throws Exception {
		MemberUser entity = getModelById(id);
		if(null!=entity){
			return entity;
		}
		return new NormalUser();
	}
	
    @Override
    protected void onSave(MemberUser entity,ModelAndView modelAndView,HttpServletRequest request) throws Exception {
    	
    	if(entity != null && StringUtils.isBlank(entity.getId())){
            entity.setInputTime(new Date());
            entity.setLastLoginTime(new Date());         
            entity.setLoginTimes(0);
            entity.setErrorTimes(0);
            entity.setStatus(true);
        	Member member = MemberAuthenticationUtils.getCurrentMember(request);
        	entity.setMember(member);        	
        }
		entity = memberUserService.saveUser(entity, entity.getRoleIds());
    }

	@Override
    protected void onDelete(String id) throws Exception {
        memberUserService.deleteUserByIds(id);
    }

	/**
	 * 会员用户启用
	 * @param id
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="enable",description="启用")
	public String enable(@RequestParam("id") String id,HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		try {
			if(memberUserService.startOrStopMemberUser(id, true)){
				redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员用户启用成功");
			}
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, e.getMessage());
		}
		return this.getRedirectReturnUrl(request);
	}
	
	/**
	 * 会员用户停用
	 * @param id
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="disable",description="停用")
	public String disable(@RequestParam("id") String id,HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		try {
			if(memberUserService.startOrStopMemberUser(id, false)){
				redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员用户停用成功");
			}
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, e.getMessage());
		}
		return this.getRedirectReturnUrl(request);
	}
	

	/**
	 * 交易员密码重置
	 * @param id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="resetpwd",description="会员用户密码重置")
	public String resetpwd(@RequestParam("id") String id,RedirectAttributes redirectAttributes,HttpServletRequest request){
		try {
			this.memberUserService.passwordReset(id);
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "密码重置成功!");
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, e.getMessage());
		}
		return this.getRedirectReturnUrl(request);
	}

	/**
     * AJAX 检验用户名是否唯一
     * @param orgUserName
     * @param userName
     * @return
     */
    @RequestMapping(value="checkUserName",description="AJAX 检验用户名是否唯一",aclLevel=EAclLevel.SESSION)
    @ResponseBody
    public String checkUserName(@RequestParam("orgUserName") String orgUserName, @RequestParam("userName") String userName) {
    	if(memberUserService.isLoginNameUnique(userName,orgUserName)) {
            return "true";
        }else{
            return "false";
        }
    }

}
