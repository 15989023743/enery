package com.gdwz.energy.web.controller.portal.center.account;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.Member;
import com.gdwz.common.member.service.IMemberService;
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
public class UpdateCurrentMemberController extends SimpleController {
	
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IMemberUserService memberUserService;
	
	@RequestMapping(value="updateMember",description="进入修改会员信息界面",aclLevel=EAclLevel.RESTRICTE)
	public ModelAndView updateMember(HttpServletRequest request, RedirectAttributes redirectAttributes){
		ModelAndView modelAndView = createModeAndView();
		if(MemberAuthenticationUtils.getCurrentMember(request)==null){
			redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员信息找不到！");
			modelAndView.setViewName(this.getRedirectReturnUrl(request));
			return modelAndView;
		}
		else{
			Member member = memberService.findById(MemberAuthenticationUtils.getCurrentMember(request).getId());
			if(member==null){
				redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员信息找不到！");
				modelAndView.setViewName(this.getRedirectReturnUrl(request));
				return modelAndView;
			}else{
				modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, member);
				modelAndView.setViewName("portal/center/account/member/updateMember");		
				return modelAndView;
			}
		}
	}
	
		@RequestMapping(value="saveMember",description="保存会员信息",aclLevel=EAclLevel.RESTRICTE)
		public ModelAndView saveMember(HttpServletRequest request, RedirectAttributes redirectAttributes){
			ModelAndView modelAndView = createModeAndView();
			if(MemberAuthenticationUtils.getCurrentMember(request)==null){
				redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员信息找不到！");
				modelAndView.setViewName(this.getRedirectReturnUrl(request));
				return modelAndView;
			}
			else{
				Member member = memberService.findById(MemberAuthenticationUtils.getCurrentMember(request).getId());
				if(member==null){
					redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员信息找不到！");
					modelAndView.setViewName(this.getRedirectReturnUrl(request));
					return modelAndView;
				}else{
					try {
						bindValidateRequestEntity(request,member);//把表单数据邦定到实体
						memberService.save(member);
						redirectAttributes.addFlashAttribute(MESSAGE_NAME, "会员信息保存成功！");
						modelAndView.setViewName(getRedirectReturnUrl(request));
						return modelAndView;
					} catch (ConstraintViolationException e) {
						modelAndView.addObject(ERRORS_NAME, e);
						modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, member);
						modelAndView.setViewName("portal/center/account/member/updateMember");
						return modelAndView;
					}				
				}
			}
		}
		
}
