package com.gdwz.common.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.common.cms.dao.IArticleDao;
import com.gdwz.common.cms.entity.Article;
import com.gdwz.common.cms.entity.ArticleClass;
import com.gdwz.common.cms.service.IArticleService;
import com.gdwz.core.commons.EntityHelper;
import com.gdwz.core.query.Page;
import com.gdwz.core.query.QueryResult;
import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.utils.Assert;

/**
 * 
 * 项目名称：华南大宗商品交易平台
 * 工程名称：gdwz.platform
 * 类名称：     ArticleServiceImpl extends GeneralServiceImpl
 * 类描述：  文章业务功能类
 * 当前版本     1.0
 * 修改备注：
 */
@Service
@Transactional
public class ArticleService extends GeneralServiceImpl<Article,IArticleDao> implements IArticleService{

    @Override
	public Article save(Article entity) {
    	if(entity!=null&&(entity.getId()==null||entity.getId().equals(""))){
    		int maxIndex = 0;
    		if(entity.getArticleClass()!=null&&entity.getArticleClass().getId()!=null){
    			maxIndex = this.getMaxValue("sequence", " WHERE e.articleClass.id=?1 ", new Object[]{entity.getArticleClass().getId()}).intValue();
    		}else{
    			maxIndex = this.getMaxValue("sequence", " WHERE e.articleClass IS null ", null).intValue();
    		}
    		
    		entity.setSequence(maxIndex+1);
    	}
		return super.save(entity);
	}

    /*
	public List<Article> getParentId(String parentId){
        List<Article> articles = this.getParentId(parentId);
        return articles;
    }

    public List<Article> getArticleImg() {
        return this.getDefaultDAO().getArticleImg();
    }


    public List<Article> queryArticleByArticleClassMark(String articleClassMrak,String OrderBy,Date addTime){
        List<Article> acs = this.articleDao.queryArticleByArticleClassMark(articleClassMrak,OrderBy,addTime);
        return acs;
    }*/

    public List<Article> getArticleByMark(String mark,int len,Map<String,String> orderBy){
        //return getDefaultDAO().getArticleByMark(mark,len,orderBy);
    	Assert.notNull(mark, "mark not null");
    	Assert.notNull(len, "len not null");
    	String wherejpql = " WHERE e.articleClass.code = ?1 AND e.deleteStatus = ?2 AND e.display = ?3 AND e.articleClass.deleteStatus = ?4";
        wherejpql+=" AND e.auditStatus = ?5";
        QueryResult<Article> qr =  this.getDefaultDAO().getScrollData(0, len, wherejpql, new Object[]{mark,false,true,false,true},orderBy);
        return qr!=null?qr.getResultlist():null;
    }
    /*
    public boolean isUniqueArticle(Article s,String sequence,String classId){
        return this.articleDao.isUniqueArticle(s,sequence,classId);
    }*/

    public List<Article> queryArticleByClass(int topCount,String articleClassId){
    	
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("display",true);
        map.put("auditStatus",true);
        map.put("deleteStatus",false);
        map.put("display",true);
        map.put("articleClassId",articleClassId);
        map.put("articleClassD",false);
        
        Map<String,String> orderBy = new HashMap<String, String>();
        orderBy.put("sequence",Page.ASC);
        orderBy.put("addTime",Page.DESC);
        
        StringBuffer wherejpql = new StringBuffer();

        wherejpql.append(" WHERE e.display=?1");
        wherejpql.append(" AND e.auditStatus=?2");
        wherejpql.append(" AND e.deleteStatus=?3");
        wherejpql.append(" AND e.articleClass.id=?4");
        wherejpql.append(" AND e.articleClass.deleteStatus=?5");
        QueryResult<Article> qr =  this.getScrollData(0, topCount, wherejpql.toString(), new Object[]{true,true,false,articleClassId,false}, orderBy);
        
        if(qr!=null&qr.getTotalrecord()>0){
        	return qr.getResultlist();
        }
        return null;
        
        
    }

	
	public Article getFirstArticleByArticleClassId(String articleClassId) {
		return getFirstArticleByArticleClassId(articleClassId,null);
	}

	
	public Article getFirstArticleByArticleClassId(String articleClassId,String css) {
		Assert.notNull(articleClassId, "articleClassId 不能为空！");
        Map<String,String> orderBy = new HashMap<String, String>();
        orderBy.put("sequence",Page.ASC);
        orderBy.put("addTime",Page.DESC);
        if(css!=null){
	    	String wherejpql = " WHERE e.articleClass.deleteStatus = ?1 AND e.articleClass.id = ?2 AND e.deleteStatus = ?3 AND e.auditStatus = ?4 AND e.display = ?5 AND e.articleClass.css = ?6";
	        QueryResult<Article> qr =  getScrollData(0, 1, wherejpql, new Object[]{false,articleClassId,false,true,true,"2"},orderBy);
	        if(qr!=null&qr.getTotalrecord()>0){
	        	return qr.getResultlist().get(0);
	        }
        }else{
	    	String wherejpql = " WHERE e.articleClass.deleteStatus = ?1 AND e.articleClass.id = ?2 AND e.deleteStatus = ?3 AND e.auditStatus = ?4 AND e.display = ?5 ";
	        QueryResult<Article> qr =  getScrollData(0, 1, wherejpql, new Object[]{false,articleClassId,false,true,true},orderBy);
	        if(qr!=null&qr.getTotalrecord()>0){
	        	return qr.getResultlist().get(0);
	        }
        }

		return null;
	}
	
	
	public Article getLastArticleByArticleClassId(String articleClassId) {
		Assert.notNull(articleClassId, "articleClassId 不能为空！");
        Map<String,String> orderBy = new HashMap<String, String>();
        orderBy.put("sequence",Page.DESC);
        orderBy.put("addTime",Page.ASC);
    	String wherejpql = " WHERE e.articleClass.deleteStatus = ?1 AND e.articleClass.id = ?2 AND e.deleteStatus = ?3 AND e.auditStatus = ?4 AND e.display = ?5";
        QueryResult<Article> qr =  getScrollData(0, 1, wherejpql, new Object[]{false,articleClassId,false,true,true},orderBy);
        if(qr!=null&qr.getTotalrecord()>0){
        	return qr.getResultlist().get(0);
        }
        return null;
	}

	
	public Article getPreArticleByArticleClassId(String articleClassId, Integer currentArticleIndex) {
		Assert.notNull(articleClassId, "articleClassId 不能为空！");
		Assert.notNull(currentArticleIndex, "currentArticleId 不能为空！");
        Map<String,String> orderBy = new HashMap<String, String>();
        orderBy.put("sequence",Page.DESC);
        orderBy.put("addTime",Page.ASC);
    	String wherejpql = " WHERE e.articleClass.deleteStatus = ?1 AND e.articleClass.id = ?2 AND e.deleteStatus = ?3 AND e.auditStatus = ?4 AND e.display = ?5 AND e.sequence < ?6";
        QueryResult<Article> qr =  getScrollData(0, 1, wherejpql, new Object[]{false,articleClassId,false,true,true,currentArticleIndex},orderBy);
        if(qr!=null&qr.getTotalrecord()>0){
        	return qr.getResultlist().get(0);
        }
        return null;
	}

	
	public Article getNextArticleByArticleClassId(String articleClassId, Integer currentArticleIndex) {
		Assert.notNull(articleClassId, "articleClassId 不能为空！");
		Assert.notNull(currentArticleIndex, "currentArticleId 不能为空！");
        Map<String,String> orderBy = new HashMap<String, String>();
        orderBy.put("sequence",Page.ASC);
        orderBy.put("addTime",Page.DESC);
    	String wherejpql = " WHERE e.articleClass.deleteStatus = ?1 AND e.articleClass.id = ?2 AND e.deleteStatus = ?3 AND e.auditStatus = ?4 AND e.display = ?5 AND e.sequence > ?6";
        QueryResult<Article> qr =  getScrollData(0, 1, wherejpql, new Object[]{false,articleClassId,false,true,true,currentArticleIndex},orderBy);
        if(qr!=null&qr.getTotalrecord()>0){
        	return qr.getResultlist().get(0);
        }
        return null;
	}

	
	public long pv(String articleId) {
		Assert.notNull(articleId, "articleId not is null");
		this.increment(articleId, "clickRate");
		return 0;//this.getMaxValue("clickRate", "where e.id = ?1 ", new Object[]{articleId}).longValue();
	}
	

	public void setCommendStatu(String[] ids, boolean status) {
		if(ids!=null && ids.length>0){
			StringBuffer jpql = new StringBuffer();
			for(int i=0;i<ids.length;i++){
				jpql.append('?').append((i+2)).append(',');
			}
			jpql.deleteCharAt(jpql.length()-1);
			Query query = this.getDefaultDAO().getEntityManager().createQuery("UPDATE "+EntityHelper.getEntityName(Article.class)+" e set e.recommended=?1 where e.id in("+ jpql.toString()+ ")");
			query.setParameter(1, status);
			for(int i=0;i<ids.length;i++){
				query.setParameter(i+2, ids[i]);
			}
			query.executeUpdate();
		}
	}

	public void setDisplayStatu(String[] ids, boolean status) {
		if(ids!=null && ids.length>0){
			StringBuffer jpql = new StringBuffer();
			for(int i=0;i<ids.length;i++){
				jpql.append('?').append((i+2)).append(',');
			}
			jpql.deleteCharAt(jpql.length()-1);
			Query query = this.getDefaultDAO().getEntityManager().createQuery("UPDATE "+EntityHelper.getEntityName(Article.class)+" e set e.display=?1 where e.id in("+ jpql.toString()+ ")");
			query.setParameter(1, status);
			for(int i=0;i<ids.length;i++){
				query.setParameter(i+2, ids[i]);
			}
			query.executeUpdate();
		}
	}

	@Override
	public void deleteAllArticleByArticleClass(String articleClassId) {
		Assert.notNull(articleClassId, "articleClassId is not null");
		StringBuffer updatejpql = new StringBuffer("UPDATE ")
				.append(EntityHelper.getEntityName(Article.class))
				.append(" e SET e.deleteStatus = true ")
				.append(" WHERE e.articleClass.id = ?1 ")
				.append("OR e.articleClass.id in(select id FROM ")
				.append(EntityHelper.getEntityName(ArticleClass.class))
				.append(" WHERE parentids LIKE '"+articleClassId+"%')");
		//StringBuffer jpql = new StringBuffer("UPDATE Article e SET e.deleteStatus=true WHERE e.articleClass.id=?1 OR e.articleClass.parentids LIKE '"+articleClassId+"%'");
		super.getDefaultDAO().executeUpdate(updatejpql.toString(), new Object[]{articleClassId});
	}
	
}