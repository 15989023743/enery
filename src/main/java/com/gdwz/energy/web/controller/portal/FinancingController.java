package com.gdwz.energy.web.controller.portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 项目名称：gdwz-energy-web
 * @version 1.0    
 * 类说明：  
 */

@Controller
@RequestMapping(value="/financing/",description="门户_融资服务")
public class FinancingController extends SimpleController{

    @RequestMapping(value = "index",description="融资服务",aclLevel=EAclLevel.WHITE)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView mv = createModeAndView();
    	mv.setViewName("portal/financing/index");
    	return mv;
    }
}
