package com.gdwz.energy.web.controller.portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.PropertyFilter;
import com.gdwz.core.query.PropertyFilter.MatchType;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.core.web.utils.orm.JpaWebUtils;
import com.gdwz.energy.article.order.OrderByRulesMapUtil;
import com.gdwz.energy.dataExchange.entity.DynamicFutures;
import com.gdwz.energy.dataExchange.service.IDynamicFuturesService;
import com.gdwz.utils.Assert;

/**
 * 创建时间：2014年10月11日 下午4:40:11  
 * 项目名称：gdwz-energy-web  
 * @author yanlun  
 * @version 1.0   
 * 文件名称：InfoCenterController.java  
 * 类说明：  
 */

@Controller
@RequestMapping(value="/info/",description="门户_资讯中心")
public class InfoCenterController extends SimpleController{

	@Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleClassService articleClassService;
    @Autowired
    private IDynamicFuturesService dynamicFuturesService;
    
    private static final String INFO_CENTER_MARK = "info";//资讯中心mark
    
    @RequestMapping(value = "index",description="资讯中心",aclLevel=EAclLevel.WHITE)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView mv = createModeAndView();
    	mv.setViewName("portal/info/index");
    	
    	Map<String,String> fullArticleOrderRules = OrderByRulesMapUtil.getFullArticleOrderRules();
    	
    	List<Article> zxzxrdxw = articleService.getArticleByMark("zxzxrdxw",10,fullArticleOrderRules);
    	//首篇文章处理
    	if(zxzxrdxw != null && zxzxrdxw.size() > 0){
    		Article firstArticle = zxzxrdxw.get(0);
    		String articleIntro = firstArticle.getIntro();//文章简介
    		if(articleIntro != null && articleIntro.length() > 0){
    			if(articleIntro.length() > 100){
    				articleIntro = articleIntro.substring(0,100) + "...";
    			}
    			mv.addObject("articleIntro", articleIntro);
    		}
    		mv.addObject("rdxwFirstArticle", firstArticle);
    	}
    	mv.addObject("zxzxrdxw", zxzxrdxw);
    	
    	//咨询中心-价格行情、行业法规、热点新闻除外的所有分类，各取6篇文章
    	List<List<Article>> counselingCenterArticle = 
    			this.getAritacleClassByParentMark(INFO_CENTER_MARK, new String[]{"zxzxrdxw","zxzxhyfg","zxzxjghq","zxzxtpgl","zxzxwzgg"}, 
    					6, 6, fullArticleOrderRules);
    	mv.addObject("counselingCenterArticle", counselingCenterArticle);
    	
    	//价格行情-全部字类文章分类-13篇/类
    	List<List<Article>> priceQuotesArticle = 
    			this.getAritacleClassByParentMark("zxzxjghq", new String[]{}, 10, 13, fullArticleOrderRules);
    	if(priceQuotesArticle.size() > 0){
    		List<Article> firstClassArticleList = priceQuotesArticle.get(0);
    		//通过价格行情的子集分类元素逆向查询价格行情的ClassId
    		mv.addObject("priceQuotesClassId", firstClassArticleList.get(0).getArticleClass().getParent().getId());
    	}
    	mv.addObject("priceQuotesArticle", priceQuotesArticle);
    	
    	//行业法规
    	List<Article> zxzxhyfg = this.articleService.getArticleByMark("zxzxhyfg", 10, fullArticleOrderRules);
    	mv.addObject("zxzxhyfg", zxzxhyfg);
    	
    	//add by ganzq 图片管理
        List<Article> zxzxtpgl=articleService.getArticleByMark("zxzxtpgl",4,fullArticleOrderRules);
        mv.addObject("zxzxtpgl",zxzxtpgl);
        
        //期货信息
        this.dynamicFuturesDataSourcesConfigure(mv);
    	
    	return mv;
    }
    
    @RequestMapping(value = "list",description="资讯列表",aclLevel=EAclLevel.WHITE)
    public ModelAndView list(Page<Article> page,HttpServletRequest request, HttpServletResponse response,
    		String classId){
        ModelAndView modelAndView = createModeAndView();
        modelAndView.setViewName("portal/info/list");
        
        if(null==classId || classId.trim().length()==0){
	        return this.redirectHome(modelAndView);
        }
        
        //处理文章分类
        ArticleClass selectArticleClass = this.articleClassService.findById(classId);
        //分类下的文章分页
        if(selectArticleClass != null){
        	//当前分类下子集分类获取
            List<ArticleClass> childrens = this.articleClassService.findChildrensByPid(classId);
            ListPropertyFilter filters = JpaWebUtils.buildPropertyFilters(request);
            if(childrens != null && childrens.size() > 0){
            	//构造子集分类Id的in集合
            	List<String> childrensClassIdInList = new ArrayList<String>();
            	for (ArticleClass articleClass : childrens) {
					childrensClassIdInList.add(articleClass.getId());
				}
            	//自身ClassId
            	childrensClassIdInList.add(classId);
            	filters.addFilter("articleClass.id",childrensClassIdInList , MatchType.IN);
            }else{
            	filters.addFilter("articleClass.id", selectArticleClass.getId(), MatchType.EQ);
            }
            
            filters.addFilter("deleteStatus", false, MatchType.EQ);
            filters.addFilter("display", true, MatchType.EQ);
            filters.addFilter("auditStatus", true, MatchType.EQ);
            page.setOrderBy("addTime");
            page.setOrder(Page.DESC);
            //page.setPageNo(page.getPageNo());
            if("3".equals(selectArticleClass.getCss())){
                page.setPageSize(5);   //图文显示的
            }else{
                page.setPageSize(15);
            }
            page = this.articleService.findPagesByFilters(page, filters);
        }else{
            page = null;
        }
        
        Map<String,String> fullArticleOrderRules = OrderByRulesMapUtil.getFullArticleOrderRules();
        //除当前分类资讯外的两栏分类获取
        List<List<Article>> counselingCenterArticle = 
			this.getAritacleClassByParentMark(INFO_CENTER_MARK, new String[]{selectArticleClass.getCode()}, 2, 7, fullArticleOrderRules);
        
        //面包导航构建
        this.constructionOfBreadNavigation(modelAndView, selectArticleClass);
        
        modelAndView.addObject("counselingCenterArticle", counselingCenterArticle);
        modelAndView.addObject("selectArticleClass", selectArticleClass);//打开的分类
        modelAndView.addObject("page", page);
        return modelAndView;
    }
    
    @RequestMapping(value = "article",description="文章详情页面",aclLevel=EAclLevel.WHITE)
    public ModelAndView article(HttpServletRequest request, HttpServletResponse response,
    		String classId, String articleId) throws Exception {
    	//设置默认返回视图
       ModelAndView modelAndView = createModeAndView();
       modelAndView.setViewName("portal/info/article");
    	
       if( (null==classId || classId.trim().length()==0 )
    		   || (null==articleId || articleId.trim().length()==0 ) ){
       		return redirectHome(modelAndView);
       }
       
       //处理打开的文章、文章分类
       Article selectArticle = null;//记录当前打开的文章
       ArticleClass selectArticleClass = null;//记录当前打开的下级分类
       Article upArticle = null;
       Article nextArticle = null;
       
       if(null!=articleId && articleId.trim().length() > 0){//如果是打开一个指定的文章
            selectArticle = articleService.findById(articleId);
       }
       
      if(selectArticle==null){//当前文章不存在
      		return redirectHome(modelAndView);
      }else{
    	selectArticleClass = selectArticle.getArticleClass();
      	Map<String,String> fullArticleOrderRules = OrderByRulesMapUtil.getFullArticleOrderRules();
      	Map<String,String> todayHotArticleOrderRules = OrderByRulesMapUtil.getTodayHotArticleOrderRules();
      	
      	//相关新闻资讯
      	List<Article> xgxwzx = articleService.getArticleByMark(selectArticleClass.getCode(), 5, fullArticleOrderRules);
      	//今日热点新闻
      	List<Article> jrrdxw = articleService.getArticleByMark("zxzxrdxw", 4, todayHotArticleOrderRules);
      	//除当前分类资讯、今日热点新闻外的两栏分类获取
      	List<List<Article>> counselingCenterArticle = 
    			this.getAritacleClassByParentMark(INFO_CENTER_MARK, new String[]{"zxzxrdxw",selectArticleClass.getCode()}, 2, 7, fullArticleOrderRules);
      	
        upArticle = this.articleService.getPreArticleByArticleClassId(classId, selectArticle.getSequence());
        nextArticle = this.articleService.getNextArticleByArticleClassId(classId, selectArticle.getSequence());

        //增加文章点击率
        this.articleService.pv(articleId);
        //临时数据
        if(selectArticle.getClickRate() != null){
        	selectArticle.setClickRate(selectArticle.getClickRate() + 1);
        }else{
        	selectArticle.setClickRate(1);
        }
        
        //面包导航构建
        this.constructionOfBreadNavigation(modelAndView, selectArticleClass);
        
        modelAndView.addObject("selectArticle", selectArticle);
        modelAndView.addObject("selectArticleClass", selectArticleClass);
        modelAndView.addObject("xgxwzx", xgxwzx);
        modelAndView.addObject("jrrdxw", jrrdxw);
        modelAndView.addObject("counselingCenterArticle", counselingCenterArticle);
        modelAndView.addObject("upArticle", upArticle);
        modelAndView.addObject("nextArticle", nextArticle);
        this.dynamicFuturesDataSourcesConfigure(modelAndView);
        return modelAndView;
      }
    }

    /**
     * 面包导航构建-list集合
     * @param modelAndView
     * @param selectArticleClass
     */
	private void constructionOfBreadNavigation(ModelAndView modelAndView,
			ArticleClass selectArticleClass) {
		ArticleClass temp = selectArticleClass;
        List<ArticleClass> nav = new ArrayList<ArticleClass>();
        nav.add(temp);
        //面包导航递归获取全部分类
        while(temp.getParent() != null 
        		&& !INFO_CENTER_MARK.equals(temp.getParent().getCode())){//排除资讯中心大类
        	temp = temp.getParent();
        	nav.add(temp);
        }
        Collections.reverse(nav);
        modelAndView.addObject("nav",nav);
	}

    /**
     * 重定向到首页
     * @param modelAndView
     * @return
     */
	private ModelAndView redirectHome(ModelAndView modelAndView) {
		modelAndView.setViewName(REDIRECT+"/");
		return modelAndView;
	}
    
    /**
     * 期货动态-数据源配置
     * @param modelAndView
     */
	/*private void dynamicFuturesDataSourcesConfigure(ModelAndView modelAndView) {
		Page<DynamicFutures> page = new Page<DynamicFutures>(3);
        page.setOrderBy("showNum");
        page.setOrder("desc");
        ListPropertyFilter filter = new ListPropertyFilter();
        filter.addFilter("isShow", "1", PropertyFilter.MatchType.EQ);
        filter.addFilter("goodsItem.enabled", "1", PropertyFilter.MatchType.EQ);
        page = dynamicFuturesService.findPagesByFilters(page,filter);
        modelAndView.addObject("dynamicFutures", page.getResult());
	}*/
    
	/**
     * 期货动态-数据源配置
     * @param modelAndView
     */
	private void dynamicFuturesDataSourcesConfigure(ModelAndView modelAndView) {
        List<DynamicFutures> list = dynamicFuturesService.queryDynamicFuturesList(3);
        modelAndView.addObject("dynamicFutures", list);
	}
	
    /**
	 * 自定义：通过分类父类标示获取ArticleClass，并封装指定个数的子分类文章，排除无文章的集合查询结果；
	 * @return
	 */
	private List<List<Article>> getAritacleClassByParentMark(String mark, Object[] excludeMark, int articleClasseGetNum, 
			int articleGetNum, Map<String, String> orderBy){
		  Map<String, String> articleClassOrderByIndex = OrderByRulesMapUtil.getArticleClassOrderByIndexRules();
		  int articleClasseNeedGetNum = articleClasseGetNum;//临时变量，判断是否获取了足够的文章分类信息
		  
		  //articleClasseGetNum再次匹配，需要加上排除的分类个数，以保证取到足够的分类(excludeMark.length + articleClasseGetNum)
		  if(excludeMark != null && articleClasseGetNum < Integer.MAX_VALUE){
			  articleClasseGetNum += excludeMark.length;
		  }
		  
	      List<ArticleClass> articleClassByMark = 
	    		  this.articleClassService.getAritacleClassByParentMark(mark, articleClasseGetNum, articleClassOrderByIndex);
	      Assert.notNull(articleClassByMark);
	      
	      List<List<Article>> handlingResult = new ArrayList<List<Article>>();
	      if(excludeMark != null && excludeMark.length > 0){
	    	  boolean isExcludeMark;
		      for (ArticleClass articleClass : articleClassByMark) {
		    	  if(handlingResult.size() >= articleClasseNeedGetNum){break;}
		    	  isExcludeMark = ArrayUtils.contains(excludeMark, articleClass.getCode());
	    		  if(!isExcludeMark){
	    			  filterEmptyResultList(articleGetNum, orderBy,handlingResult, articleClass);
	    		  }
		      }
	      }else{
	    	  for (ArticleClass articleClass : articleClassByMark) {
	    		  if(handlingResult.size() >= articleClasseNeedGetNum){break;}
	    		  filterEmptyResultList(articleGetNum, orderBy,handlingResult, articleClass);
		      }
	      }
	      return handlingResult;
	}

	/**
	 * 过滤size为空的文章集合
	 * @param articleGetNum
	 * @param orderBy
	 * @param handlingResult
	 * @param articleClass
	 */
	private void filterEmptyResultList(int articleGetNum,
			Map<String, String> orderBy, List<List<Article>> handlingResult,
			ArticleClass articleClass) {
		List<Article> articleByMark = this.articleService.getArticleByMark(articleClass.getCode(), articleGetNum, orderBy);
		  if(articleByMark != null && articleByMark.size() > 0){
			  handlingResult.add(articleByMark);
		  }
	}
}
