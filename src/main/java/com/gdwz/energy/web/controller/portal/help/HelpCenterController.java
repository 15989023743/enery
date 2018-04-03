package com.gdwz.energy.web.controller.portal.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.energy.web.controller.portal.ArticleParentController;

@Controller
@RequestMapping(value = "/helpcenter/", description = "门户_帮助中心")
public class HelpCenterController extends ArticleParentController {

	@Autowired
	private IArticleService articleService;
	@Autowired
	private IArticleClassService articleClassService;
	/**
     * 帮助中心标示。
     */
    private static final String HELP_CENTER = "helpcenter";
    /**
     * 返回URL地址
     */
    private static final String URL = "/portal/helps/helpcenter";
    

	@RequestMapping(value = "index",description="帮助中心首页",aclLevel=EAclLevel.WHITE)
    public ModelAndView helpcenter(HttpServletRequest request, HttpServletResponse response,String classId,String articleId) throws Exception {
       return this.queryArticleInfo(HELP_CENTER, URL, classId, articleId);
    }

}
