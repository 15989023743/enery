/**
 * 
 */
package com.gdwz.energy.web.controller.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.web.controller.SimpleController;

/**
 * @author Gary
 * 
 */
public abstract class ArticleParentController extends SimpleController {

	@Autowired
	private IArticleService articleService;
	@Autowired
	private IArticleClassService articleClassService;

	/**
	 * 文章内容查询。
	 * @param mark
	 * @param url
	 * @param classId
	 * @param articleId
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryArticleInfo(String mark, String url,
			String classId, String articleId) throws Exception {
        ModelAndView modelAndView = createModeAndView();
        modelAndView.setViewName(url);
        
        Map<String,String> orderByRecommended=new HashMap<String,String>();
        orderByRecommended.put(" recommended","desc");
        orderByRecommended.put(" addTime","desc");
        
        Map<String, String> orderByIndex = new HashMap<String, String>();
        orderByIndex.put(" index", "asc");
        //获取帮助中心的菜单。
        List<ArticleClass> helpCenterAllClass =  this.articleClassService.getAritacleClassByParentMark(mark, Integer.MAX_VALUE, orderByIndex);
        //文章集合。
        List<List<Article>> helpCenterAllArticle = new ArrayList<List<Article>>();
        List<Article> articleList = null;
        for (ArticleClass articleClass : helpCenterAllClass) {
        	//通过具体的菜单去查询该菜单对应的文章。
        	articleList = this.articleService.getArticleByMark(articleClass.getCode(), Integer.MAX_VALUE, orderByRecommended);
			if(articleList != null && articleList.size() > 0){
				helpCenterAllArticle.add(articleList);
			}
		}
        modelAndView.addObject("helpCenterAllArticle", helpCenterAllArticle);
        
        //处理打开的文章、文章分类
        ArticleClass nav = null;
        Article selectArticle = null;//记录当前打开的文章
        if(StringUtils.isNotEmpty(articleId)){//如果是打开一个指定的文章
             selectArticle = articleService.findById(articleId);//直接获取该文章
        }else{
        	return modelAndView;
        }
        if(selectArticle.getArticleClass().getId().equals(classId)){//如果打开的文章所属的文章分类与主导航一样，
     		nav = selectArticle.getArticleClass();
     	}else{
     		nav = selectArticle.getArticleClass().getParent();
     		while(nav!=null&&nav.getParent() !=null){
     			nav = nav.getParent();
     		}
     	}
       selectArticle.setClickRate(selectArticle.getClickRate()==null?1:selectArticle.getClickRate()+1);
       this.articleService.save(selectArticle);
       modelAndView.addObject("selectArticle", selectArticle);
       modelAndView.addObject("nav", nav);
       return modelAndView;
	}


	@RequestMapping(value = "getArticleById", description = "查询文章，供前台ajax调用", aclLevel = EAclLevel.WHITE)
	public void getArticleById(HttpServletResponse response,
			@RequestParam("articleId") String articleId) {
		try {
			Article article = articleService.findById(articleId);
			jsonMapper.renderJson(article.getContent(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
