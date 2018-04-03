package com.gdwz.energy.web.controller.portal.center.account;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.MemberUser;
import com.gdwz.common.member.service.IMemberUserService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;

/**
 * 会员中心_帐户管理_会员管理
 * @author fanwei
 * */
@Controller
@RequestMapping(value="/center/account/member/",description="会员中心_帐户管理",subsystem="门户")
public class UpdateCurrentUserController extends SimpleController {
	
	@Autowired
	private IMemberUserService memberUserService;
	
	@RequestMapping(value="updateUser",description="进入修改当前用户信息界面",aclLevel=EAclLevel.RESTRICTE)
		public ModelAndView updateUser(HttpServletRequest request, RedirectAttributes redirectAttributes){
			ModelAndView modelAndView = createModeAndView();
			if(MemberAuthenticationUtils.getLoggedOnUser(request)==null){
				redirectAttributes.addFlashAttribute(MESSAGE_NAME, "当前用户信息找不到！");
				modelAndView.setViewName(this.getRedirectReturnUrl(request));
				return modelAndView;
			}
			else{
				MemberUser entity = memberUserService.findById(MemberAuthenticationUtils.getLoggedOnUser(request).getId());
				if(entity==null){
					redirectAttributes.addFlashAttribute(MESSAGE_NAME, "当前用户信息找不到！");
					modelAndView.setViewName(this.getRedirectReturnUrl(request));
					return modelAndView;
				}else{
					modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, entity);
					modelAndView.setViewName("portal/center/account/member/updateUser");		
					return modelAndView;
				}
			}
		}
		
			@RequestMapping(value="saveUser",description="保存用户信息",aclLevel=EAclLevel.RESTRICTE)
			public ModelAndView saveUser(HttpServletRequest request, RedirectAttributes redirectAttributes){
				ModelAndView modelAndView = createModeAndView();
				if(MemberAuthenticationUtils.getLoggedOnUser(request)==null){
					redirectAttributes.addFlashAttribute(MESSAGE_NAME, "当前用户信息找不到！");
					modelAndView.setViewName(this.getRedirectReturnUrl(request));
					return modelAndView;
				}
				else{
					MemberUser entity = memberUserService.findById(MemberAuthenticationUtils.getLoggedOnUser(request).getId());
					if(entity==null){
						redirectAttributes.addFlashAttribute(MESSAGE_NAME, "当前用户信息找不到！");
						modelAndView.setViewName(this.getRedirectReturnUrl(request));
						return modelAndView;
					}else{
						try {
							bindValidateRequestEntity(request,entity);//把表单数据邦定到实体
							memberUserService.save(entity);
							redirectAttributes.addFlashAttribute(MESSAGE_NAME, "用户信息保存成功！");
							modelAndView.setViewName(getRedirectReturnUrl(request));
							return modelAndView;
						} catch (ConstraintViolationException e) {
							modelAndView.addObject(ERRORS_NAME, e);
							modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, entity);
							modelAndView.setViewName("portal/center/account/member/updateUser");
							return modelAndView;
						}				
					}
				}
			}
		
}
