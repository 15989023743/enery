package com.gdwz.energy.trade.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.common.member.entity.MemberUser;
import com.gdwz.core.query.QueryResult;
import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.trade.dao.IIssueInfoDao;
import com.gdwz.energy.trade.dao.impl.IssueInfoDao;
import com.gdwz.energy.trade.entity.IssueInfo;
import com.gdwz.energy.trade.entity.IssueStateEnum;
import com.gdwz.energy.trade.service.IIssueInfoService;
import com.gdwz.utils.Assert;

/**
 * @项目名称：能化王国
 * @工程名称：gdwz-energy
 * @包名称：com.gdwz.energy.trade.service
 * @创建日期：14-8-29
 * @作者:chenyuhong
 */
@Service
@Transactional
public class IssueInfoService extends GeneralServiceImpl<IssueInfo,IIssueInfoDao> implements IIssueInfoService{

	/**
	 * 审核挂牌上架方法
	 * @param issueId
	 * @return
	 */
	public boolean auditing(long issueId) {
		Assert.notNull(issueId, "memberId is not null");
		//StringBuffer jpql = new StringBuffer();
		//jpql.append("UPDATE FROM ").append(IssueInfo.class.getName());
		//jpql.append(" e SET e.issueState=?1 where e.issueState=?2 ");
		//jpql.append(" and e.id=?3 and e.deleteStatus=false");
		//return super.getDefaultDAO().executeUpdate(jpql.toString(), new Object[]{'2','1',issueId})>0?true:false;
		synchronized(issueId+""){
			IssueInfo issue=this.findById(issueId);
			if(issue.isDeleteStatus()==true){
				return false;
			}
			if(issue.getIssueState()!=IssueStateEnum.TOAUDIT){
				return false;
			}
			issue.setIssueState(IssueStateEnum.SHELVES);
			issue.setCheckTime(new Date());
			this.save(issue);
			return true;
		}
	}
	
	
	/**
	 * 审核方法重载
	 * @param issueId
	 * @param issueState
	 * @return
	 */
	public boolean auditing(long issueId, IssueStateEnum issueState,IssueStateEnum nowIssueState) {
		Assert.notNull(issueId, "memberId is not null");
		IssueInfo issue=this.findById(issueId);
		if(issue.isDeleteStatus()==true){
			return false;
		}
		if(issue.getIssueState()!=nowIssueState){
			return false;
		}
		issue.setIssueState(issueState);
		this.save(issue);
		return true;
	}

	public List<IssueInfo> getValidIssueByLen(int len,int byeOrSale,
			Map<String, String> orderBySequence) {
		StringBuffer wherejpql = new StringBuffer(" WHERE e.deleteStatus = ?1 and e.issueState=?2 and e.validTime>=trunc(sysdate)");//当前有效期时间格式为"yyyy-MM-dd"
		Object[] params=null;
		if(byeOrSale!=0){
			wherejpql.append(" and e.buyOrSale=?3");
			params=new Object[] { Boolean.valueOf(false),IssueStateEnum.SHELVES,(char)(byeOrSale+'0')};
		}else{
			params=new Object[] { Boolean.valueOf(false),IssueStateEnum.SHELVES};
		}
	    QueryResult<IssueInfo> qr = ((IIssueInfoDao)getDefaultDAO()).getScrollData(0, len, wherejpql.toString(), 
	      params, orderBySequence);
	    getDefaultDAO().buildDict(qr.getResultlist());
	    return qr != null ? qr.getResultlist() : null;
	}
	
	public List<IssueInfo> getValidIssueByLen(int len,
			Map<String, String> orderBySequence) {
		return this.getValidIssueByLen(len, 0, orderBySequence);
	}

	public long getValidIssueCount(int byeOrSale) {
		return getValidIssueCount(byeOrSale,null);
	}
	
	public long getValidIssueCount(int byeOrSale,String menberId) {
		StringBuffer wherejpql = new StringBuffer(" WHERE e.deleteStatus = :deleteStatus and e.issueState=:issueState and e.validTime>=trunc(sysdate)");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("deleteStatus", Boolean.valueOf(false));
		params.put("issueState", IssueStateEnum.SHELVES);
		if(byeOrSale!=0){
			wherejpql.append(" and e.buyOrSale=:buyOrSale");
			params.put("buyOrSale", byeOrSale);
		}
		if(menberId!=null){
			wherejpql.append(" and e.member.id=:menberId");
			params.put("menberId", menberId);
		}
		return getDefaultDAO().getCount(wherejpql.toString(), params);
	}

	

	public long getValidIssueCount() {
		return getValidIssueCount(0);
	}


	/**
	 * 挂牌作废
	 * @param id
	 * @return
	 */
	public boolean cancel(long id) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("UPDATE FROM ").append(IssueInfo.class.getName());
		jpql.append(" e SET e.issueState=5 where e.issueState!=5 ");
		jpql.append(" and e.id=?1 and e.deleteStatus=false");
		return super.getDefaultDAO().executeUpdate(jpql.toString(), new Object[]{id})>0?true:false;
		//return false;
	}


}
