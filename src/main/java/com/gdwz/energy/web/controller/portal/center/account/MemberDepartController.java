package com.gdwz.energy.web.controller.portal.center.account;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

import com.gdwz.common.member.entity.MemberDept;
import com.gdwz.common.member.service.IMemberDeptService;
import com.gdwz.common.member.service.IMemberRoleService;
import com.gdwz.core.exception.BusinessException;
import com.gdwz.core.service.IGeneralTreeService;
import com.gdwz.core.web.controller.TreeController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.system.web.controller.manager.member.MemberRoleListEditor;
import com.gdwz.web.utils.WebTreeHelper;


/**
 * 部门管理Controller
 * @author cyh
 * Urls:
 */
@Controller
@RequestMapping(value="/center/account/mamber/dept/",description="会员中心_帐户管理_部门管理",subsystem="门户")
public class MemberDepartController extends TreeController<MemberDept,String> {

    @Autowired
    private IMemberDeptService memberDeptService;

    @Autowired
    private IMemberRoleService memberRoleService;

    @Autowired
    private MemberRoleListEditor memberRoleListEditor;

    @Override
    protected IGeneralTreeService<MemberDept> getDefaultService() {
        return memberDeptService;
    }

    @InitBinder
    public void initBinder(WebDataBinder wdb) {
        super.initBinder(wdb);
        wdb.registerCustomEditor(Set.class, "roles", memberRoleListEditor);
    }

    @Override
    protected void onEntity(MemberDept entity,ModelAndView mav,HttpServletRequest request) {
        mav.addObject("allRoles", memberRoleService.getRoleByMember(MemberAuthenticationUtils.getCurrentMember(request).getId()));
    }

    @Override
    protected String getViewPath() {
    	return "portal/center/account/member/dept/";
    }

    /**
     *
     * @param parentId
     * @param request
     * @param response
     * 重写父类Load方法
     */
    @Override
    @ResponseBody
    @RequestMapping(value="load",description="异步加载管理界面数据")
    public void load(String parentId, HttpServletRequest request, HttpServletResponse response) {
    	/*
        MemberUser memberUser=MemberAuthenticationUtils.getLoggedOnUser(request);
        String memberId=memberUser.getMember().getId();
        List<MemberDept> allTrees=null;
        if( parentId==null || parentId==""){
            ListPropertyFilter listPropertyFilter = ListPropertyFilter.getInstance();
            listPropertyFilter.addFilter("deleteStatus", 0, PropertyFilter.MatchType.EQ);
            if(memberId!=""||memberId!=null){
                listPropertyFilter.addFilter("member.id", memberId, PropertyFilter.MatchType.EQ);
                listPropertyFilter.addFilter("depth", 1, PropertyFilter.MatchType.EQ);
            }
            allTrees = getDefaultService().findByFilters(listPropertyFilter);
            for(MemberDept d :allTrees){
                d.setParent(null);
            }
        }else{
            allTrees = getDefaultService().findChildrensByPid((String)parentId);
        }*/
    	if(StringUtils.isBlank(parentId)){
    		parentId = MemberAuthenticationUtils.getCurrentMember(request).getTopDeptId();
    	}
		List<MemberDept> allTrees = getDefaultService().findChildrensByPid(parentId);
		String jsonstring = WebTreeHelper.getTreeItemFromTreeEntity(allTrees.iterator(),parentId==null?"":parentId.toString()).toJsonString();
		Map<String, String> map = Collections.singletonMap("treedata", jsonstring);
		jsonMapper.renderJson(map,response);
    }

	@Override
	protected void afterList(ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		 modelAndView.addObject("parentId",MemberAuthenticationUtils.getCurrentMember(request).getTopDeptId());
	}

    /**
     *
     * @param parentId
     * @param entity
     * @param modelAndView
     * @param request
     * @throws Exception
     */
    @Override
    protected void onSave(String parentId,MemberDept entity,ModelAndView modelAndView,HttpServletRequest request) throws Exception {
        if(entity!=null&&StringUtils.isBlank(entity.getId())){
	    	entity.setCategory("M");
	        entity.setLeaf(false);
	        if(StringUtils.isBlank(parentId)){//顶节点
	        	parentId = MemberAuthenticationUtils.getCurrentMember(request).getTopDeptId();
	        }
        }
        entity = memberDeptService.saveDept(parentId,entity,entity.getRoleIds());
    }

    /**
     * AJAX 部门代码是否唯一
     * @param orgCode
     * @param code
     * @return
     */
    @RequestMapping(value="checkCode",description="AJAX部门代码是否唯一")
    @ResponseBody
    public String checkCode(@RequestParam("orgCode") String orgCode, @RequestParam("code") String code) {
        if(code.equals(orgCode)){
            return "true";
        }
        MemberDept dept = new MemberDept();
        dept.setCode(code);
        if (memberDeptService.isUnique(dept, "code")){
            return "true";
        }
        return "false";
    }
    
    @Override
    protected MemberDept getModelById(String id) throws Exception {
        if(id==null){
            return null;
        }
        return memberDeptService.getDeptById(id);
    }
      
	 /**
	  * 删除实体
	  * 回调函数：beforeDelete(...), afterDelete(...)
	  * @param id
	  * @param redirectAttributes
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 @ResponseBody
	 @RequestMapping(value="delete",description="删除")
	 public String delete( @RequestParam("id") String nodeId, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response)  throws Exception {
			try {
				//beforeDelete(nodeId,request,response);
				memberDeptService.deleteDeptByIds(nodeId);
				//redirectAttributes.addFlashAttribute(MESSAGE_NAME, "删除成功");
				//afterDelete(nodeId,request,response);
				return "true";
			} catch (BusinessException e) {
				//response.setContentType(WebAppUtils.TEXT_TYPE + ";charset=UTF-8");
				response.setContentType("text/plain");
				response.setHeader("Cache-Control", "no-cache");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(e.getMessage());
				return null;//e.getMessage();
			}catch(Exception e){
				return "false";
			}
	 }	 
    
}
