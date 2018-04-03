package com.gdwz.system.web.controller.manager.cms.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.core.commons.ServicesFactory;
import com.gdwz.template.ConfigInfo;
import com.gdwz.template.utils.TemplateBuilder;

/**
 * 
 * @author jerry
 *
 */
public class CmsTools {

	/**
     * 静态化导航条
     * @param request
     */
	public static void buileNavIgation(HttpServletRequest request){
		Map<String,Object> map_model = new HashMap<String,Object>();
		String template_home = request.getSession().getServletContext().getRealPath("/WEB-INF/template/");
		String out_path = request.getSession().getServletContext().getRealPath("/");
		TemplateBuilder tb = new TemplateBuilder(template_home);
		File file = new File(out_path+"/WEB-INF/templates/portal/share/");
		if(!file.exists())
			file.mkdirs();
		map_model.put("navgations" ,ServicesFactory.getBean(IArticleClassService.class).getAritacleNavigation());
		tb.execute(map_model, "/portal/share/navgation."+ConfigInfo.getProperties("out.format"), file.getPath()+"/navgation."+ConfigInfo.getProperties("out.format"));
    }

	/**
     * 静态化一个文章分类
     * @param request
     */
    public static void buileOneCatalog(HttpServletRequest request,String parentArticleClassId){
		Map<String,Object> map_model = new HashMap<String,Object>();
		String template_home = request.getSession().getServletContext().getRealPath("/WEB-INF/template/");
		String out_path = request.getSession().getServletContext().getRealPath("/");
		TemplateBuilder tb = new TemplateBuilder(template_home);
		File file = new File(out_path+"/WEB-INF/templates/portal/share/catalog");
		if(!file.exists())
			file.mkdirs();
		if(parentArticleClassId!=null&&parentArticleClassId.trim().length()>0){
			map_model.put("entity" ,ServicesFactory.getBean(IArticleClassService.class).getArticleClassAndArticlesByClassid(parentArticleClassId));
			tb.execute(map_model, "/portal/share/catalog."+ConfigInfo.getProperties("out.format"), file.getPath()+"/"+parentArticleClassId+"."+ConfigInfo.getProperties("out.format"));
		}else{
			List<ArticleClass> navs = ServicesFactory.getBean(IArticleClassService.class).getAritacleNavigation();
			for(ArticleClass nav : navs){
				map_model.put("entity" ,ServicesFactory.getBean(IArticleClassService.class).getArticleClassAndArticlesByClassid(nav.getId()));
				tb.execute(map_model, "/portal/share/catalog."+ConfigInfo.getProperties("out.format"), file.getPath()+"/"+nav.getId()+"."+ConfigInfo.getProperties("out.format"));
			}
		}

		
    }
    
    /**
     * 静态化全部文章分类
     * @param request
     */
    public static void buileAllCatalog(HttpServletRequest request){
    	buileOneCatalog(request,null);
    }
}
