package com.gdwz.system.web.controller.manager.cms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.core.service.IGeneralTreeService;
import com.gdwz.core.web.controller.TreeController;
import com.gdwz.system.web.controller.manager.cms.utils.CmsTools;


/**
 * 内容分类管理
 * @author jerry
 *
 */
@Controller
@RequestMapping(value="/manager/cms/articleClass/",description="内容分类管理")
public class ArticleClassController  extends TreeController<ArticleClass,String> {

    @Autowired
    private IArticleClassService articleClassService;

    @Override
    protected IGeneralTreeService<ArticleClass> getDefaultService() {
        return this.articleClassService;
    }

    @Override
    protected String getViewPath() {
        return "manager/cms/articleClass/";
    }

    @Override
	protected void afterSave(String parentId, ArticleClass entity,
			ModelAndView modelAndView, HttpServletRequest request,
			HttpServletResponse response) {
    	if(entity!=null){
    		CmsTools.buileNavIgation(request);
    		ArticleClass nav = entity;
    		while(nav.getParent()!=null){
    			nav = nav.getParent();
    		}
    		CmsTools.buileOneCatalog(request,nav.getId());
    	}
	}

	@Override
	protected void afterDelete(String nodeId, HttpServletRequest request,
			HttpServletResponse response) {
		CmsTools.buileNavIgation(request);
		CmsTools.buileAllCatalog(request);
	}

	/**
     * Ajax检验CODE是否重复.
     * @param code
     * @param orgCode
     * @return
     */
    @RequestMapping(value="checkCode",description="Ajax检验CODE是否重复")
    @ResponseBody
    public String checkCode(@RequestParam("orgCode") String orgCode, @RequestParam("code") String code) {
		if(code.equals(orgCode)){
			return "true";
		}
		ArticleClass articleClass = new ArticleClass();
		articleClass.setCode(code);
		if (articleClassService.isUnique(articleClass, "code")){
			return "true";
		}
		return "false";
    }


	@Override
	protected void afterSort(ModelAndView mav, String parentId,
			String currentId, Integer xh, HttpServletRequest request,
			HttpServletResponse response) {
		CmsTools.buileNavIgation(request);
		CmsTools.buileAllCatalog(request);
	}



}
