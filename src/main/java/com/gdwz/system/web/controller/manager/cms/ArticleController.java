package com.gdwz.system.web.controller.manager.cms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.service.IGeneralService;
import com.gdwz.core.web.controller.CrudController;
import com.gdwz.core.web.utils.MemberAuthenticationUtils;
import com.gdwz.system.web.controller.manager.cms.utils.CmsTools;

/**
 * 内容管理
 * @author jerry
 *
 */
@Controller
@RequestMapping(value="/manager/cms/article/",description="内容管理")
public class ArticleController extends CrudController<Article,String> {
    @Autowired
    private IArticleService articleService;
    
    @Override
    protected IGeneralService<Article> getDefaultService() {
        return this.articleService;
    }

    @Override
    protected String getViewPath() {
        return "manager/cms/article/";
    }

	@Override
    protected void beforeList(Page<Article> page, ListPropertyFilter filters, HttpServletRequest request, HttpServletResponse response) {
        //super.beforeList(page, filters, request, response);
        //filters.addFilter("deleteStatus", false, PropertyFilter.MatchType.EQ);
        //filters.addFilter("disPublish", false, PropertyFilter.MatchType.EQ);//是否退回
		if(null==page.getOrderBy() || "".equals(page.getOrderBy()) || "id".equals(page.getOrderBy())){
			page.setOrderBy("addTime");
			page.setOrder(Page.DESC);
		}
    }


    @Override
	protected void afterSave(Article entity, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        if(entity!=null && entity.getArticleClass()!=null && entity.getArticleClass().getCss()!=null && entity.getArticleClass().getCss().equals("2")){
        	ArticleClass ac = entity.getArticleClass();
        	while(ac.getParent()!=null){
        		ac = ac.getParent();
        	}
        	CmsTools.buileOneCatalog(request, ac.getId());
        }
	}

    /**
     * 审核
     * @param request
     * @param id
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
	@RequestMapping(value="audit",description="审核")
    public String audit(String id,HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {
        Article article = getModelById(id);
        if (article!=null){
            article.setAuditStatus(true);
            article.setAuditor(MemberAuthenticationUtils.getUserId(request));
            article.setAuditTime(new Date());
            article.setDisplay(true);
            article = getDefaultService().save(article);
            
            if(article.getArticleClass()!=null
            		&&article.getArticleClass().getCss()!=null
            		&&article.getArticleClass().getCss().equals("2")){
            	ArticleClass ac = article.getArticleClass();
            	while(ac.getParent()!=null){
            		ac = ac.getParent();
            	}
            	CmsTools.buileOneCatalog(request, ac.getId());
            }
            
            redirectAttributes.addFlashAttribute(MESSAGE_NAME, "审核成功！");

        }
        return this.doReturn(request);
    }
	
	/**
	 * 批量删除文章
	 * @param request
	 * @param ids
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="deletes",description="批量删除")
    public String deletes(HttpServletRequest request,String[] ids,RedirectAttributes redirectAttributes) throws Exception {
    	if(ids!=null&&ids.length>0){
    		getDefaultService().deleteByIds(ids);
    		CmsTools.buileAllCatalog(request);
    	}
    	redirectAttributes.addFlashAttribute(MESSAGE_NAME, "批量删除成功！");
        return this.doReturn(request);
    }


    /**
     * 发布（播放/停播）
     * @param id
     * @param request
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
	@RequestMapping(value="display",description="发布")
    public String display(String id, boolean status,HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {
        Article article = getModelById(id);
        if (article!=null){
            article.setDisplay(status);
            article = getDefaultService().save(article);
            if(article.getArticleClass()!=null
            		&&article.getArticleClass().getCss()!=null
            		&&article.getArticleClass().getCss().equals("2")){
            	ArticleClass ac = article.getArticleClass();
            	while(ac.getParent()!=null){
            		ac = ac.getParent();
            	}
            	CmsTools.buileOneCatalog(request, ac.getId());
            }
            redirectAttributes.addFlashAttribute(MESSAGE_NAME, status?"播出成功！":"停播成功！");
        }
        return this.doReturn(request);
    }

    /**
     * 批量发布（播放/停播）
     * @param request
     * @param ids
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping(value="displays",description="批量发布（播放/停播） ")
    public String displays(HttpServletRequest request,String[] ids, boolean status,RedirectAttributes redirectAttributes) throws Exception {
    	this.articleService.setDisplayStatu(ids, status);
    	CmsTools.buileAllCatalog(request);
    	redirectAttributes.addFlashAttribute(MESSAGE_NAME, status?"批量播出成功！":"批量停播成功！");
        return this.doReturn(request);
    }



    /**
     * 推荐
     * @param id
     * @param request
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
	@RequestMapping(value="commend",description="推荐")
    public String commend(String id, boolean status,HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {
        Article article = getModelById(id);
        if (article!=null){
            article.setRecommended(status);
            article = getDefaultService().save(article);
            if(article.getArticleClass()!=null
            		&&article.getArticleClass().getCss()!=null
            		&&article.getArticleClass().getCss().equals("2")){
            	ArticleClass ac = article.getArticleClass();
            	while(ac.getParent()!=null){
            		ac = ac.getParent();
            	}
            	CmsTools.buileOneCatalog(request, ac.getId());
            }
            redirectAttributes.addFlashAttribute(MESSAGE_NAME,(status?"推荐成功！":"不推荐成功！"));
        }
        return this.doReturn(request);
    }
    
    /**
     * 批量推荐
     * @param request
     * @param ids
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping(value="commends",description="批量推荐")
    public String commends(HttpServletRequest request,String[] ids, boolean status,RedirectAttributes redirectAttributes) throws Exception {
    	this.articleService.setCommendStatu(ids, status);
    	CmsTools.buileAllCatalog(request);
    	redirectAttributes.addFlashAttribute(MESSAGE_NAME, status?"批量推荐成功！":"批量不推荐成功！");
        return this.doReturn(request);
    }
    
    /*
    @RequestMapping({"article_ajax"})
    @ResponseBody
    public void ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value)
            throws ClassNotFoundException
    {
        try
        {
        Field[] arrayOfField1;
        Article obj = getEntity(id);
        //User user=(User)request.getSession().getAttribute(WebConstants.SESSION_LOGGED_ON_USER);
        if(fieldName.equals("auditStatus")){
            obj.setAuditor(MemberAuthenticationUtils.getUserId(request));
            obj.setAuditTime(new Date());
            if(obj.isAuditStatus()){
                obj.setAuditStatus(false);
            }else{
                obj.setAuditStatus(true);
            }
        }else if(fieldName.equals("display")){
            if(obj.isDisplay()){
                obj.setDisplay(false);
            }else{
                obj.setDisplay(true);
            }
        }else if(fieldName.equals("recommended")){
            if(obj.isRecommended()){
                obj.setRecommended(false);
            }else{
                obj.setRecommended(true);
            }
        }
        //obj.setModifyUserId(MemberAuthenticationUtils.getUserId(request));
        //obj.setModifyTime(new Date());
        getDefaultService().save(obj);
        if(obj.getArticleClass().getCss().equals("2")){
        	ArticleClass ac = obj.getArticleClass();
        	while(ac.getParent()!=null){
        		ac = ac.getParent();
        	}
        	CmsTools.buileOneCatalog(request, ac.getId());
        }
        
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            if(fieldName.equals("auditStatus")){
                if(obj.isAuditStatus()){
                    writer.print(true);
                }else{
                    writer.print(false);
                }
            }else if(fieldName.equals("display")){
                if(obj.isDisplay()){
                    writer.print(true);
                }else{
                    writer.print(false);
                }
            }else if(fieldName.equals("recommended")){
                if(obj.isRecommended()){
                    writer.print(true);
                }else{
                    writer.print(false);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }*/
    
}
