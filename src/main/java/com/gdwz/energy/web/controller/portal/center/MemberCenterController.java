package com.gdwz.energy.web.controller.portal.center;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.member.entity.Member;
import com.gdwz.common.member.service.IMemberService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.core.web.utils.orm.JpaWebUtils;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.entity.IssueStateEnum;
import com.gdwz.energy.trade.service.IIssueInfoService;

/**
 * 会员中心
 * @author jerry
 *
 */
@Controller
@RequestMapping(value="/center/",description="门户_会员中心")
public class MemberCenterController extends SimpleController {

	@Autowired
	private IMemberService memberService;
	
	@Autowired
	public IIssueInfoService issueInfoService;

	@RequestMapping(value="index",description="会员中心首页",aclLevel=EAclLevel.SESSION)
	public ModelAndView index(Page<IssueInfo> page, HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView modelAndView = createModeAndView();
		Date date = new Date();
		Member member = MemberAuthenticationUtils.getCurrentMember(request);
		ListPropertyFilter filters = JpaWebUtils.buildPropertyFilters(request);
		filters.addFilter("deleteStatus", false, MatchType.EQ);
		filters.addFilter("issueState", IssueStateEnum.SHELVES, MatchType.EQ);
		filters.addFilter("validTime", date, MatchType.GE);
		if(member.getId()!=""|| !"".equals(member.getId())){
			filters.addFilter("member.id", member.getId(), MatchType.EQ);
		}
		page.setOrderBy("addTime");
		page.setOrder(Page.DESC);
		page.setPageSize(12);

		try {
			page = this.issueInfoService.findPagesByFilters(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		}
		modelAndView.addObject("totalIssue", issueInfoService.getValidIssueCount(0, member.getId()));
		modelAndView.addObject("page", page);
		modelAndView.setViewName("portal/center/index");
		return modelAndView;
		//return "portal/center/index";
	}
	
	@RequestMapping(value="top",description="会员中心顶部",aclLevel=EAclLevel.SESSION)
	public String top(){
		return "portal/center/top";
	}
	
	@RequestMapping(value="menu",description="会员中心左边菜单",aclLevel=EAclLevel.SESSION)
	public String menu(){
		return "portal/center/menu";
	}
	
	@RequestMapping(value="right",description="会员中心右边",aclLevel=EAclLevel.SESSION)
	public String right(){
		return "portal/center/right";
	}
	
	@RequestMapping(value="end",description="会员中心底部",aclLevel=EAclLevel.SESSION)
	public String end(){
		return "portal/center/end";
	}
}
