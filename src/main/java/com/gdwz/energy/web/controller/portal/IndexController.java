package com.gdwz.energy.web.controller.portal;

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

import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.enums.EAclLevel;
import com.gdwz.core.query.ListPropertyFilter;
import com.gdwz.core.query.Page;
import com.gdwz.core.web.controller.SimpleController;
import com.gdwz.energy.article.order.OrderByRulesMapUtil;
import com.gdwz.energy.dataExchange.entity.LatestTrend;
import com.gdwz.energy.dataExchange.service.ILatestTrendService;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.service.IIssueInfoService;

/**
 *
 * @项目名称：广东物资集团项目
 * @工程名称：platformPortal
 * @类名称： IndexViewController.java
 * @类描述： 门户首页-广东物资
 * @当前版本：1.0
 * @修改备注
 */

@Controller
@RequestMapping(value="/",description="门户_首页")
public class IndexController  extends SimpleController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IIssueInfoService issueInfoService;
    
    @Autowired
    private ILatestTrendService latestTrendService;
    
    @RequestMapping(value = "index",description="首页",aclLevel=EAclLevel.WHITE)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = createModeAndView();
        modelAndView.setViewName("portal/index");
        
        Map<String,String> fullArticleOrderRuels = OrderByRulesMapUtil.getFullArticleOrderRules();
        
        //Map<String,String> issueOrderByCheckTimeRules = OrderByRulesMapUtil.getIssueOrderByCheckTimeRules();
        
        //现货超市挂牌信息查询
        //this.marketIssueInfoDataSourcesConfigure(modelAndView, issueOrderByCheckTimeRules);
        
        //能化咨询置顶文章获取
        this.articleDataSourcesConfigure(modelAndView, fullArticleOrderRuels, "zxzxrdxw", 7);
        
        //行业法则、价格行情、期货动态、市场分析
        this.batchArticleDataSourcesConfigure(modelAndView, fullArticleOrderRuels, 
        		new String[]{"zxzxhyfg","zxzxjghq","zxzxscfx"}, new int[]{12,12,12});
        //买家入门、卖家入门、能化规则
        this.batchArticleDataSourcesConfigure(modelAndView, fullArticleOrderRuels, 
        		new String[]{"bzzxmjrm","bzzxmaijrm","bzzxnhgz"}, new int[]{3,3,3});
        //资讯中心-网站公告
        this.articleDataSourcesConfigure(modelAndView, fullArticleOrderRuels, "zxzxwzgg", 3);
        //交易模式介绍
        this.articleDataSourcesConfigure(modelAndView, fullArticleOrderRuels, "bzzxjyms", 3);
        //首页横幅广告
        this.articleDataSourcesConfigure(modelAndView, fullArticleOrderRuels, "syhf", 5);
        
        //最新交易动态
        //this.latestTrendDataSourcesConfigure(modelAndView);
        
        //today
        modelAndView.addObject("today", new Date());
        return modelAndView;
    }

    /**
     * 现货超市挂牌相关信息配置
     * @param modelAndView
     * @param orderByCheckTime
     */
	private void marketIssueInfoDataSourcesConfigure(ModelAndView modelAndView,
			Map<String, String> orderByCheckTime) {
        long validIssueCount = issueInfoService.getValidIssueCount();
        List<IssueInfo> validIssueInfo = issueInfoService.getValidIssueByLen(12, orderByCheckTime);
        //挂牌信息-资源产地获取
        Map<String, Object> map = new HashMap<String, Object>();
        for (IssueInfo info : validIssueInfo) {
        	for (IssueGoodsAttrValues attr : info.getIssueGoodsAttrValues()) {
        		map.put(info.getId() + "_" + attr.getAttrCode(),attr.getAttrValue());
        	}
        }
        modelAndView.addObject("attrValuesMap", map);
        modelAndView.addObject("validIssueCount", validIssueCount);
        modelAndView.addObject("validIssueInfo", validIssueInfo);
	}

    /**
     * 最新成交动态配置
     * @param modelAndView
     */
	private void latestTrendDataSourcesConfigure(ModelAndView modelAndView) {
		Page<LatestTrend> page = new Page<LatestTrend>(15);
        page.setOrderBy("dealTime");
        page.setOrder("desc");
        page = latestTrendService.findPagesByFilters(page,new ListPropertyFilter());
        modelAndView.addObject("latestTrendResult", page.getResult());
	}
    
    /**
     * 执行文章数据来源的配置（单一配置）
     * @param modelAndView
     * @param orderByMap
     * @param articleMark
     * @param getNumber
     */
    private void articleDataSourcesConfigure(ModelAndView modelAndView,Map<String, String> orderByMap,String articleMark,int getNumber){
    	 List<Article> dataSources=articleService.getArticleByMark(articleMark,getNumber,orderByMap);
    	 modelAndView.addObject(articleMark,dataSources);
    }
    
    /**
     * 批量配置文章数据来源（统一排序规则）
     * @param modelAndView
     * @param orderByMap
     * @param articleMarks
     * @param getNumbers
     */
   private void batchArticleDataSourcesConfigure(ModelAndView modelAndView,Map<String, String> orderByMap,String[] articleMarks,int[] getNumbers){
	   for (int i = 0; i < getNumbers.length; i++) {
		   this.articleDataSourcesConfigure(modelAndView, orderByMap, articleMarks[i], getNumbers[i]);
	   }
   }
}
