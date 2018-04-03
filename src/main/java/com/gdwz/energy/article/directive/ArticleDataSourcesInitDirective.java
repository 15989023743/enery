package com.gdwz.energy.article.directive;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.commons.ServicesFactory;
import com.gdwz.core.web.view.velocity.directive.AbstractDirective;
import com.gdwz.energy.article.order.OrderByRulesMapUtil;

/**
 * 
 * 创建时间：2014年9月22日 上午11:29:44
 * 项目名称：gdwz-energy-web
 * @author yanlun
 * @version 1.0
 * 文件名称：ArticleDataSourcesInitDirective.java
 * 类说明：公共模块-文章数据初始化
 */
public class ArticleDataSourcesInitDirective extends AbstractDirective {
    private static final String HELP_CENTER = "helpCenter";//帮助中心分类标示符，避免错误使用，转义为HELP_CENTER_MRAK；
    private static final String HELP_CENTER_MRAK = "helpcenter";
    
    private static final String FOOTER_ARTICLE = "footerArticle";
    private static final String FOOTER_ARTICLE_MARK = "yw";//底部页尾文章分类标示符
	/*
     * 文章标示符，用于获取文章数据列表
     */
    private String articleMark;
    
    /*
     * 文章获取的数目
     */
    private Integer articleGetNumber;
    
    /*
     * 指令内部的标签内容节点
     */
    private Node body;
    
    private IArticleService articleService;
    
    private IArticleClassService articleClassService;
    
    private static final Map<String,String> fullArticleOrderRules = OrderByRulesMapUtil.getFullArticleOrderRules();
    
    private static final Map<String, String> articleClassOrderByIndex = OrderByRulesMapUtil.getArticleClassOrderByIndexRules();
    
    @Override
	protected void doInit(InternalContextAdapter internalContext,
			ViewToolContext context, Writer writer, Node node) {
    	if ( node.jjtGetNumChildren() != 3 )
        {
            throw new VelocityException("#articleDataSourcesInitForeach(): argument missing or body missing at  "
            		+"#articleDataSourcesInitForeach(articleMark,articleGetNumber)"
                 + Log.formatFileString(this));
        }
    	articleService=ServicesFactory.getBean(IArticleService.class);
    	articleClassService = ServicesFactory.getBean(IArticleClassService.class);
    	articleMark = ((SimpleNode) node.jjtGetChild(0)).value(internalContext).toString();
    	articleGetNumber = Integer.parseInt(((SimpleNode) node.jjtGetChild(1)).value(internalContext).toString());
    	body = node.jjtGetChild(2);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean doRender(InternalContextAdapter internalContext,
			ViewToolContext context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {
		if(this.articleMark!=null && this.articleGetNumber != null){
			List articleDataSources = null;
			//判断是否为底部帮助中心文章，其标示符为helpCenter，转为HELP_CENTER_MRAK
			if(HELP_CENTER.equals(articleMark)){
				articleDataSources = this.getAritacleClassByParentMark(HELP_CENTER_MRAK, articleGetNumber, 4, fullArticleOrderRules);
			}else if(FOOTER_ARTICLE.equals(articleMark)){
				//注意：这里是文章分类，并非文章，修改时请注意
				articleDataSources = this.getAritacleClassByParentMark(FOOTER_ARTICLE_MARK, articleGetNumber);
			}else{
				articleDataSources = articleService.getArticleByMark(articleMark,articleGetNumber,fullArticleOrderRules);
			}
			
			
			if(articleDataSources!=null && articleDataSources.size()>0){
				StringWriter sw = new StringWriter();
				internalContext.localPut("articleDataSources", articleDataSources);
				body.render(internalContext, sw);
				writer.write(sw.toString());
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "articleDataSourcesInitForeach";
	}

	@Override
	public int getType() {
		return BLOCK;
	}
	
    
	/**
	 * 自定义：通过分类父类标示获取ArticleClass，并封装指定个数的子分类文章
	 * @return
	 */
	private List<List<Article>> getAritacleClassByParentMark(String mark,int articleClasseGetNum,int articleGetNum,Map<String, String> orderBy){
	      List<ArticleClass> articleClassByMark = this.articleClassService.getAritacleClassByParentMark(mark, articleClasseGetNum, articleClassOrderByIndex);
	      List<List<Article>> handlingResult = new ArrayList<List<Article>>();
	      for (ArticleClass articleClass : articleClassByMark) {
	    	  handlingResult.add(this.articleService.getArticleByMark(articleClass.getCode(), articleGetNum, orderBy));
	      }
	      return handlingResult;
	}
	
	/**
	 * 自定义：通过父类标示获取指定个数的文章分类
	 * @param mark
	 * @param articleClasseGetNum
	 * @param orderBy
	 * @return
	 */
	private List<ArticleClass> getAritacleClassByParentMark(String mark,int articleClasseGetNum){
	      List<ArticleClass> articleClassByMark = 
	    		  this.articleClassService.getAritacleClassByParentMark(mark, articleClasseGetNum, articleClassOrderByIndex);
	      return articleClassByMark;
	}
	
}
