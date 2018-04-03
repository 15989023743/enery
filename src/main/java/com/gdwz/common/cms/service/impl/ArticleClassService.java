package com.gdwz.common.cms.service.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.common.cms.dao.IArticleClassDao;
import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleClassService;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.QueryResult;
import com.gdwz.core.service.impl.GeneralTreeServiceImpl;
import com.gdwz.utils.Assert;

/**
 * 
 * 项目名称：华南大宗商品交易平台
 * 工程名称：gdwz.platform
 * 类名称：     ArticleClassServiceImpl extends GeneralServiceImpl
 * 类描述：  文章分类业务功能类
 * 当前版本     1.0
 * 修改备注：
 */
@Service
@Transactional
public class ArticleClassService extends GeneralTreeServiceImpl<ArticleClass,IArticleClassDao>
  implements IArticleClassService{
	
	@Resource
	private IArticleService articleService;
    
	
	
	@Override
	public boolean deleteNodeById(Object nodeId) {
		Assert.notNull(nodeId, "nodeId is not null");
		articleService.deleteAllArticleByArticleClass((String)nodeId);
		return super.deleteNodeById(nodeId);
	}

	public ArticleClass getAritacleClassAndAritacleById(String classId,String css) {
		ArticleClass articleClass = this.findById(classId);
		if(null!=articleClass){
			if(articleClass.getCss()!=null&&articleClass.getCss().equals("1")){//列表
                List<ArticleClass> acs = this.queryChildrenArticleClass(classId);
                if(null!=acs && acs.size()> 0){
                    Set<ArticleClass> set= new HashSet<ArticleClass>(acs);
                    articleClass.setChilds(set);
                }
                return articleClass;
			}else if(articleClass.getChildren().size()==0 || css.equals("2")){//内容
                List<Article> articles = this.articleService.queryArticleByClass(10, classId);
                if(null!=articles && articles.size()>0){
                    Set<Article> article= new HashSet<Article>(articles);
                    articleClass.setArticles(article);
                }
                return articleClass;
            }
		}
		return null;
	}
	
	private List<ArticleClass> queryChildrenArticleClass(String classId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select e from ArticleClass e");
		sb.append(" where e.deleteStatus=false");
		sb.append(" and e.parent.id='").append(classId).append("'");
		sb.append(" and e.deleteStatus=false");
		sb.append(" order by e.addTime desc");
		return this.query(sb.toString());
	}
    @Override
    public List<ArticleClass> getAritacleNavigation() {
        StringBuffer hql=new StringBuffer();
		hql.append("SELECT distinct e FROM ArticleClass e LEFT JOIN FETCH e.children c1");
        hql.append(" WHERE e.depth=0 AND e.type=1 AND e.deleteStatus=false");
        hql.append(" ORDER BY e.index");
		List<ArticleClass> navigation = this.query(hql.toString());


		return navigation;    
	}

	@Override
     public ArticleClass getArticleClassAndArticlesByClassid(String classId) {
		Assert.notNull(classId, "classId not null");
		/*
		ArticleClass articleClass = this.findById(classId);
		if(articleClass==null||articleClass.isDeleteStatus()){
			return null;
		}
		articleClass.getArticles().size();
		*/
		
		StringBuffer jpasql = new StringBuffer();// LEFT JOIN FETCH e.children c
													// LEFT JOIN FETCH
													// c.articles ca// LEFT JOIN
													// FETCH c.articles ca
		jpasql.append(" SELECT distinct e FROM ").append(ArticleClass.class.getName()).append(" e LEFT JOIN FETCH e.articles ea LEFT JOIN FETCH e.children c1 LEFT JOIN FETCH c1.children c2");
		jpasql.append(" WHERE  e.id=?1");
		jpasql.append(" AND e.deleteStatus=false");
        jpasql.append(" ORDER BY ea.sequence, ea.addTime DESC");
		// jpasql.append("  AND ea.deleteStatus=false AND ea.auditStatus=true AND ea.display=true");
		// jpasql.append(" AND ea.deleteStatus=false");
		// jpasql.append(" AND c.deleteStatus=false AND c.css='2' ");
		// sb.append(" order by e.addTime desc");
		List<ArticleClass> results = this.query(jpasql.toString(),
				new Object[] { classId });
		
		
		
		ArticleClass articleClass = results != null && results.size() > 0 ? results.get(0) : null;
				
		
		if (articleClass != null) {
			/*
			int maxDepth = 0;
			for(ArticleClass ac:results){
				if(ac.getParent().equals(articleClass)){
					System.out.println(ac.getName());
					articleClass.getChildren().add(ac);
				}
			}*/
			
			for (Object c1 : articleClass.getChildren()) {
				ArticleClass cac1 = (ArticleClass) c1;
				if (cac1.getCss()!=null&&cac1.getCss().equals("2")) {// 正文
					cac1.getArticles().size();
				}
				for (Object c2 : cac1.getChildren()) {
					ArticleClass cac2 = (ArticleClass) c2;
					if (cac2.getCss()!=null&&cac2.getCss().equals("2")) {// 正文
						cac2.getArticles().size();
					}
				}
			}
			
		}
		return articleClass;
    }

    @Override
    public ArticleClass getArticleClassAndArticlesByClassidA(String classId) {
    	Assert.notNull(classId, "classId not null");
        StringBuffer jpasql = new StringBuffer();
        jpasql.append(" SELECT distinct e FROM ")
                .append(ArticleClass.class.getName())
                .append(" e LEFT JOIN FETCH e.articles ea LEFT JOIN FETCH e.children c");
        jpasql.append(" WHERE  e.id=?1 ");
        jpasql.append(" AND e.deleteStatus=false ");

        jpasql.append(" ORDER BY ea.addTime DESC");
        List<ArticleClass> results = this.query(jpasql.toString(),
                new Object[] { classId });
        ArticleClass articleClass = results != null && results.size() > 0 ? results
                .get(0) : null;
        if (articleClass != null) {
            for (Object c : articleClass.getChildren()) {
                ArticleClass cac = (ArticleClass) c;
                if (cac.getCss()!=null&&cac.getCss().equals("2")) {// 正文
                    cac.getArticles().size();
                }
            }
        }

        return articleClass;
    }
    
	public List<ArticleClass> getAritacleClassByParentMark(String mark,
			int len, Map<String, String> orderBy) {
		String wherejpql = " WHERE e.parent.code = ?1 AND e.deleteStatus = ?2 AND e.parent.deleteStatus = ?3";
		QueryResult<ArticleClass> qr = this.getDefaultDAO().getScrollData(0, len, wherejpql,
				new Object[] { mark, false, false }, orderBy);
		return qr != null ? qr.getResultlist() : null;
	}

	@Override
	public ArticleClass getFirstArticleClassByParentArticleClassId(String parentArticleClassId) {
		Assert.notNull(parentArticleClassId, "parentArticleClassId 不能为空！");
        Map<String,String> orderBy=new HashMap<String, String>();
        orderBy.put("index",Page.ASC);
    	String wherejpql = " WHERE e.parent.deleteStatus = ?1 AND e.parent.id = ?2 AND e.deleteStatus = ?3 ";
        QueryResult<ArticleClass> qr =  getScrollData(0, 1, wherejpql, new Object[]{false,parentArticleClassId,false},orderBy);
        if(qr!=null&qr.getTotalrecord()>0){
        	return qr.getResultlist().get(0);

        }
		return null;
	}
	
}