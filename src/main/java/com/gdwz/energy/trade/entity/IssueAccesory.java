package com.gdwz.energy.trade.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessLongIdEntity;


/**
 * 挂牌附件
 * @author shuangfeng
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="T_TRADE_ISSUE_ACCESORY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACCESORY_USE", discriminatorType = DiscriminatorType.INTEGER)
public class IssueAccesory extends BusinessLongIdEntity{

	@Column(name="ACCESORY_PATH",nullable=false)
	private String accesoryPath;
	
	@JoinColumn(name="ISSUE_ID",nullable=false)
	@ManyToOne
	private IssueInfo issueInfo;

	public String getAccesoryPath() {
		return accesoryPath;
	}

	public void setAccesoryPath(String accesoryPath) {
		this.accesoryPath = accesoryPath;
	}

	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}
	
	
}
