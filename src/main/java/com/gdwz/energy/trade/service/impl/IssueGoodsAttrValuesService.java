package com.gdwz.energy.trade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdwz.core.service.impl.GeneralServiceImpl;
import com.gdwz.energy.trade.dao.IIssueGoodsAttrValuesDao;
import com.gdwz.energy.trade.entity.IssueGoodsAttrValues;
import com.gdwz.energy.trade.entity.IssueStateEnum;
import com.gdwz.energy.trade.service.IIssueGoodsAttrValuesService;

@Service
@Transactional
public class IssueGoodsAttrValuesService extends GeneralServiceImpl<IssueGoodsAttrValues, IIssueGoodsAttrValuesDao> implements IIssueGoodsAttrValuesService {
	public List<Long> getIssueIdsByAttrs(Map<String,String> attrs){
		if(attrs==null||attrs.size()==0){
			return new ArrayList<Long>();
		}
		List<String> code=new ArrayList<String>();
		List<String> value=new ArrayList<String>();
		for(String key:attrs.keySet()){
			code.add(key);
			value.add(attrs.get(key));
		}
		StringBuffer hql=new StringBuffer("select e.issue.id from IssueGoodsAttrValues e where e.deleteStatus=:deleteStatus and e.attrCode in (:codes) and e.attrValue in (:values) and e.issue.issueState=:issueState");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("deleteStatus", false);
		param.put("codes", code);
		param.put("values", value);
		param.put("issueState", IssueStateEnum.SHELVES);
		List<Long> infos= getDefaultDAO().query(hql.toString(), param);
		return infos;
	}
	
	public List<Long> getIssueIdsByAttrsAll(Map<String,String> attrs){
		if(attrs==null||attrs.size()==0){
			return new ArrayList<Long>();
		}
		List<String> code=new ArrayList<String>();
		List<String> value=new ArrayList<String>();
		for(String key:attrs.keySet()){
			code.add(key);
			value.add(attrs.get(key));
		}
		StringBuffer hql=new StringBuffer("select e.issue.id from IssueGoodsAttrValues e where e.deleteStatus=:deleteStatus and e.attrCode in (:codes) and e.attrValue in (:values)");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("deleteStatus", false);
		param.put("codes", code);
		param.put("values", value);
		List<Long> infos= getDefaultDAO().query(hql.toString(), param);
		return infos;
	}

	@Override
	public void updateGoodsAttrValues(Long issueInfoId,
			List<IssueGoodsAttrValues> issueGoodsAttrValuess) {
		if(issueInfoId!=null){
			StringBuffer updateHql=new StringBuffer("update IssueGoodsAttrValues e set e.deleteStatus=true where e.issueId=:issueId and e.deleteStatus=false");
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("issueId", issueInfoId);
			//param.put("deleteStatus", true);
			getDefaultDAO().executeUpdate(updateHql.toString(), param);
		}
		getDefaultDAO().saves(issueGoodsAttrValuess);
	}
}
