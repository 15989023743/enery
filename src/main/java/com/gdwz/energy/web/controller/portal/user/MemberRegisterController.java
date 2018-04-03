package com.gdwz.energy.web.controller.portal.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.AdminUser;
import com.gdwz.common.member.entity.Member;
import com.gdwz.common.member.entity.MemberUser;
import com.gdwz.common.member.service.IMemberService;
import com.gdwz.common.member.service.IMemberUserService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.exception.BusinessException;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.system.web.dd.DDConstants;
import com.gdwz.system.web.dd.DataDict;
import com.octo.captcha.service.CaptchaService;

/**
 * 会员注册
 * @author jerry
 *
 */
@Controller
@RequestMapping(value="/user/",description="门户_会员注册")
public class MemberRegisterController extends SimpleController {

	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private IMemberUserService memberUserService;
	
	@Autowired
	private CaptchaService captchaService;

	@RequestMapping(value="register",description="会员注册界面",aclLevel=EAclLevel.WHITE)
	public ModelAndView register(AdminUser entity,HttpServletRequest request){
		ModelAndView modelAndView=createModeAndView();
		modelAndView.setViewName("portal/user/register");
		//modelAndView.addObject("entity", entity);
		modelAndView.addAllObjects(request.getParameterMap());
		return modelAndView;
	}
	
	@RequestMapping(value="registerFinish",description="会员注册提交",aclLevel=EAclLevel.WHITE)
	public ModelAndView registerFinish(AdminUser entity, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = createModeAndView();
		modelAndView.setViewName(REDIRECT+"/message.htm");
		if(entity!= null){
			try {
				this.validate(entity);
				this.addOrModifyUserInfo(request, entity);
				this.memberService.register(entity);
				redirectAttributes.addFlashAttribute(MESSAGE_NAME, "注册成功！");
			} catch (ConstraintViolationException e) {
				modelAndView.addObject(ERRORS_NAME, e);
				modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, entity);
				modelAndView.setViewName("portal/user/register");
				return modelAndView;
			} catch (BusinessException e) {
				modelAndView.addObject(MESSAGE_NAME, e.getMessage());
				modelAndView.addObject(ENTITY_ATTRIBUTE_NAME, entity);
				modelAndView.setViewName("portal/user/register");
				return modelAndView;
			}
		}
		redirectAttributes.addFlashAttribute("urladdress", DataDict.getString(DDConstants.LOGIN_USER_URL));
		return modelAndView;
	}
	
	/**
	 * AJAX 企业全称是否唯一
	 * @param orgCode
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkFullName",description="AJAX企业全称是否唯一",aclLevel=EAclLevel.WHITE)
	@ResponseBody
	public String checkFullName(@RequestParam("orgFullName") String orgFullName, @RequestParam("member.fullName") String fullName) {
		if(fullName.equals(orgFullName)){
			return "true";
		}
		Member member=new Member();
		member.setFullName(fullName);
		if (memberService.isUnique(member, "fullName")){
			return "true";
		}
		return "false";
	}
	
	
	/**
	 * AJAX 邮箱是否唯一
	 * @param orgCode
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkEMail",description="AJAX邮箱是否唯一",aclLevel=EAclLevel.WHITE)
	@ResponseBody
	public String checkEMail(@RequestParam("orgEMail") String orgEMail, @RequestParam("email") String email) {
		if(orgEMail.equals(email)){
			return "true";
		}
		MemberUser memberUser=new MemberUser();
		memberUser.setEmail(email);
		if (memberUserService.isUnique(memberUser, "email")){
			return "true";
		}
		return "false";
	}
	
	
	/**
	 * AJAX 用户名是否唯一
	 * @param orgCode
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkUserName",description="AJAX用户名是否唯一",aclLevel=EAclLevel.WHITE)
	@ResponseBody
	public String checkUserName(@RequestParam("orgName") String orgName, @RequestParam("userName") String userName) {
		if(orgName.equals(userName)){
			return "true";
		}
		MemberUser memberUser=new MemberUser();
		memberUser.setUserName(userName);
		if (memberUserService.isUnique(memberUser, "userName")){
			return "true";
		}
		return "false";
	}
	
	
	
	/**
	 * AJAX 联系电话是否唯一
	 * @param orgCode
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkTel",description="AJAX联系电话是否唯一",aclLevel=EAclLevel.WHITE)
	@ResponseBody
	public String checkTel(@RequestParam("orgTel") String orgTel, @RequestParam("tel") String tel) {
		if(orgTel.equals(tel)){
			return "true";
		}
		MemberUser memberUser=new MemberUser();
		memberUser.setTel(tel);
		if (memberUserService.isUnique(memberUser, "tel")){
			return "true";
		}
		return "false";
	}
	
	
	
	/**
	 * AJAX AJAX验证码校验
	 * @param orgCode
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkValidateCode",description="AJAX验证码校验",aclLevel=EAclLevel.WHITE)
	@ResponseBody
	public String checkValidateCode(HttpServletRequest request,@RequestParam("j_captcha") String code) {
		if(StringUtils.isEmpty(code)){
			return "false";
		}
		String captchaID = request.getSession().getId();
		String challengeResponse = request.getParameter("j_captcha");
		if ("invalid".equals(challengeResponse))
			return "true";
		return captchaService.validateResponseForID(captchaID, challengeResponse).toString();
	}
}
