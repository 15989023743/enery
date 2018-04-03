package com.gdwz.energy.web.controller.portal.center.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.member.entity.Member;
import com.gdwz.common.member.entity.MemberResource;
import com.gdwz.common.member.entity.MemberRole;
import com.gdwz.common.member.service.IMemberResourceService;
import com.gdwz.common.member.service.IMemberRoleService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.enums.ERightCode;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.core.web.enums.EUserType;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.web.utils.WebTreeHelper;
/**
 * 会员中心_帐户管理_角色管理
 * @author fanwei
 * */
@Controller
@RequestMapping(value="/center/account/mamber/role/",description="会员中心_帐户管理_角色管理",subsystem="门户")
public class MemberRoleManagerController extends CrudController<MemberRole, String> {

	  @Autowired
	  private IMemberRoleService memberRoleService;
	  @Autowired
	  private IMemberResourceService memberResourceService;

	  protected IGeneralService<MemberRole> getDefaultService(){
	    return this.memberRoleService;
	  }

	  protected String getViewPath(){
	    return "portal/center/account/member/role/";
	  }
	  protected MemberRole getModelById(String id) {
	    if (id == null || id.isEmpty()) {
	      return null;
	    }
	    return this.memberRoleService.getRoleById(id);
	  }
	  
	@Override
	protected void beforeList(Page<MemberRole> page,
			ListPropertyFilter filters, HttpServletRequest request,
			HttpServletResponse response) {
		if( EUserType.FRONT == MemberAuthenticationUtils.getCurrentLoginUserType(request)){
			//1.过虑当前会员
			filters.addFilter("member", MemberAuthenticationUtils.getCurrentMember(request), MatchType.EQ);
		}else{
			filters.addFilter("member.id", -1, MatchType.EQ);
		}
	}

	protected void onSave(MemberRole entity, ModelAndView modelAndView, HttpServletRequest request)
	    throws Exception{
		String checkedResourceids = request.getParameter("checkedResourceids");
		List<String> checkedResourceIds = new ArrayList<String>();
		if(checkedResourceids!=null&&!"".equals(checkedResourceids)){
			String[] checkedResourceByArraytemp = checkedResourceids.split(",");
			if(checkedResourceByArraytemp!=null&&checkedResourceByArraytemp.length>0){
				//checkedResourceIds = new ArrayList<Long>();
				for(String id:checkedResourceByArraytemp){
					checkedResourceIds.add(id);
				}
			}
		}
		
		String[] aclids = request.getParameterValues("aclids");
		Map<String,ERightCode> aclRightCodes = new HashMap<String, ERightCode>();
		if(aclids!=null){
			String rightCode;
			for(String aclid:aclids){
				rightCode = request.getParameter(aclid.toString());
				aclRightCodes.put(aclid, ERightCode.getValue(rightCode));
			}
		}		
	    //与 当前会员进行关联
	    Member currentMember = MemberAuthenticationUtils.getCurrentMember(request);
	    entity.setMember(currentMember);
	    this.memberRoleService.saveRole(entity, null, checkedResourceIds, aclRightCodes);
	  }
	

	  @Override
	protected void onEntity(MemberRole entity, ModelAndView mav,
      HttpServletRequest request) {
		  Member member=MemberAuthenticationUtils.getCurrentMember(request);
		  List<MemberResource> allResourceAndChildrenByMemberId = memberResourceService.getAllResourceAndChildrenByMemberId(member.getId());
		  String jsonstring = WebTreeHelper
				  .getTreeItemFromTreeEntity(allResourceAndChildrenByMemberId.iterator())
				  .selectItem(entity.getResourceIds()).toJsonString();
		  mav.addObject("treedata", jsonstring);
		  mav.addObject("aclMenu", this.memberRoleService.getAclMenu(entity.getId()));
	}

	protected void onDelete(String id) throws Exception{
	    this.memberRoleService.deleteRoleByIds(new String[] { id });
	  }
	
	@RequestMapping(value={"kill"}, description="停用/激活")
	public String kill(@RequestParam("id") String id, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
	    MemberRole entity = getEntity(id);
	    entity.setStatus(!entity.getStatus());
	    this.memberRoleService.save(entity);
	    redirectAttributes.addFlashAttribute("message", (entity.getStatus() ? "激活“" : "停用“") + entity.getName() + "”角色成功");
	    return getRedirectReturnUrl(request);
	}

	@RequestMapping(value={"checkRoleName"}, description="AJAX 检验角色名是否重复",aclLevel=EAclLevel.SESSION)
	@ResponseBody
	public String checkRoleName(@RequestParam("orgName") String orgName, @RequestParam("name") String name){
	    if (this.memberRoleService.isRoleNameUnique(name, orgName)) {
	      return "true";
	    }
	    return "false";
	}
	  
	@RequestMapping(value={"view"}, description="查看角色")
	public ModelAndView view(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    ModelAndView modelAndView = createModeAndView();
	    initEntity(id, modelAndView, request);
	    modelAndView.setViewName(getViewPath() + "view");
	    return modelAndView;
	}
}
