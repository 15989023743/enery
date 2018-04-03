package com.gdwz.energy.web.controller.portal.encyclopedia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.core.enums.EAclLevel;
import com.gdwz.energy.web.controller.portal.ArticleParentController;

@Controller
@RequestMapping(value="/cyclopedia/",description="门户_能化百科")
public class EncyclopediaController extends ArticleParentController {

    /**
     * 能化百科标示。
     */
    private static final String CYLOPEDIA_MARK = "cyclopedia";
    /**
     * 返回URL地址
     */
    private static final String URL = "/portal/encyclopedia/encyclopedia";
       
    @RequestMapping(value = "index",description="能化百科首页",aclLevel=EAclLevel.WHITE)
    public ModelAndView encyclopedia(HttpServletRequest request, HttpServletResponse response,String classId,String articleId) throws Exception {
        return this.queryArticleInfo(CYLOPEDIA_MARK, URL, classId, articleId);
    }
   
}

