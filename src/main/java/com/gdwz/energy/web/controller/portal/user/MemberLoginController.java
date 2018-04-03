package com.gdwz.energy.web.controller.portal.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.MemberResource;
import com.gdwz.common.member.service.IMemberSessionLogin;
import com.gdwz.core.commons.ClientInfo;
import com.gdwz.core.commons.IClientInfo;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.core.web.filter.MemcachedSessionFilter;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.system.web.WebConstants;
import com.gdwz.system.web.dd.DDConstants;
import com.gdwz.system.web.dd.DataDict;
import com.gdwz.web.utils.ReturnPathUtils;
import com.gdwz.web.utils.WebAppUtils;
import com.gdwz.web.utils.WebTreeHelper;

/**
 * 会员用户登录
 * @author jerry
 *
 */
@Controller
@RequestMapping(value="/user/",description="门户_会员用户登录")
public class MemberLoginController  extends SimpleController {

	  @Autowired
	  private IMemberSessionLogin memberSessionLogin;
	  
	  /**
	   * 进入登录界面
	   * @param request
	   * @return
	   */
	  @RequestMapping(value="login",description="进入登录界面",aclLevel=EAclLevel.WHITE)
	  public String login(HttpServletRequest request){
	    //request.setAttribute("request_returnPath", request.getParameter(WebConstants.PARAMETER_RETURN_PATH));
	    if(MemberAuthenticationUtils.isLoggedOn(request)){
	    	return REDIRECT+"/center/index.htm";
	    }
	    String userName = WebAppUtils.getCookieByName(request, "userName");
	    if(StringUtils.isBlank(userName)){
	    	userName = WebAppUtils.getCookieByName(request, "tempUserName");
	    }
	    request.setAttribute("userName", userName);
	    /*
	    Cookie[] cookies=request.getCookies();
	    for(Cookie cookie:cookies){
	    	if(cookie.getName().equals("userName")){
	    		request.setAttribute("userName", cookie.getValue());
	    	}
	    	if(cookie.getName().equals("tempUserName")){
	    		request.setAttribute("userName", cookie.getValue());
	    	}
	    }*/
	    return "portal/user/login";
	  }
	  
	  /**
	   * 登录检查处理
	   * @param j_username
	   * @param j_password
	   * @param request
	   * @param response
	   * @param redirectAttributes
	   * @return
	   * @throws IOException
	   */
	  @RequestMapping(value="loginCheck",description="登录检查处理",aclLevel=EAclLevel.WHITE)
	  public String loginCheck(String j_username, String j_password, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		  	//request.setAttribute(WebConstants.PARAMETER_RETURN_PATH, request.getParameter(WebConstants.PARAMETER_RETURN_PATH));
		    try {
		    	IClientInfo clientInfo = new ClientInfo(j_username, j_password,
						MemberAuthenticationUtils.getRemoteIpAddress(request),WebAppUtils.getCookieByName(request,MemcachedSessionFilter.SESSION_ID));
		    	//登录处理
				clientInfo = this.memberSessionLogin.login(clientInfo);//调用服务
			    MemberAuthenticationUtils.login(request,response, clientInfo);
			    
			    //获取当前用户操作菜单
			    HttpSession session = request.getSession();
			    List<MemberResource> resources = memberSessionLogin.getPrimaryMenu(MemberAuthenticationUtils.getCurrentUserName(request));
			    String jsonp = WebTreeHelper.getTreeItemFromTreeEntity(resources.iterator(),true,"",true).toJsonString();
			    session.setAttribute("MENU_JSON", jsonp);
			    
			    //记录账号到Cookie
		    	if(!StringUtils.isBlank(request.getParameter("remenberAccount"))){
		    		WebAppUtils.addCookie(response, "userName", j_username, 60*60*24*7);
		    		/*
		    		//Cookie remenberCookie=new Cookie("remenberAccount","true");
	            	Cookie usernameCookie=new Cookie("userName",new String(j_username.getBytes("ISO-8859-1"),"utf-8"));
	            	//remenberCookie.setMaxAge(60*60*24*7);
	            	usernameCookie.setMaxAge(60*60*24*7);
	            	response.addCookie(usernameCookie);*/
		    		
		    	}
			    //跳转回到登录前的一个页面
			    if (ReturnPathUtils.rediectPreviousPage(request, response)) {
			      return null;
			    }
			    //否则跳转到默认的页面
			    return REDIRECT+DataDict.getString(DDConstants.LOGIN_USER_SUCCESS_DEFAULT_TARGET_URL);
			} catch (Exception e) {
				WebAppUtils.addCookie(response, "tempUserName", j_username, 10);
			    redirectAttributes.addFlashAttribute(MESSAGE_NAME, e.getMessage());
			    return REDIRECT+DataDict.getString(DDConstants.LOGIN_USER_URL)+"?src=login&" + WebConstants.PARAMETER_RETURN_PATH + "=" + request.getParameter(WebConstants.PARAMETER_RETURN_PATH);
			}
		    
	  }
	
	  /**
	   * 退出系统
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping(value="logout",description="退出系统",aclLevel=EAclLevel.SESSION)
	  public String logout(HttpServletRequest request,HttpServletResponse response){
			try {
				this.memberSessionLogin.logout(new ClientInfo(MemberAuthenticationUtils.getCurrentUserName(request),MemberAuthenticationUtils.getRemoteIpAddress(request)));
				MemberAuthenticationUtils.logout(request,response);
			} catch (Exception e) {
				if(logger.isErrorEnabled()){
					logger.error(e.getMessage(), e);
				}
			}
		 return REDIRECT+DataDict.getString(DDConstants.LOGIN_USER_URL);
	  }
	  
}
