package com.gdwz.energy.sysparam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.gdwz.common.member.entity.Member;
import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： DbpProcessFee
 * @类描述： 手续费实体类
 * @当前版本 1.0
 * @修改备注：
 */

@Entity
@Table(name="T_DBP_PROCESS_FEE")
public class ProcessFee extends BusinessStringIdEntity{

    private static final long serialVersionUID = 1894185778479646448L;
    
	/*
	 * 交易类型 4－监管交易，5－自主交易';
	 */
	@Column(name = "DBP_TYPE", nullable = false)
	@AnnoField(caption = "交易类型 4－监管交易，5－自主交易")
	private String dbpType;
	
	/*
	 * 交易场所 0－超市，1－店铺';
	 */
	@Column(name = "DBP_STATION", nullable = false, columnDefinition="CHAR(1)")
	@AnnoField(caption = "交易场所 0－超市，1－店铺")
	private String dbpStation;
	
	/*
	 * '资金参数类型生效值：1－金额，2－吨数';
	
	@Column(name = "PARA_TYPE_D", columnDefinition="CHAR(1)")
	private String paraTypeDefault; */
	
	/*
	 * '资金参数类型修改值：1－金额，2－吨数';
	
	@Column(name = "PARA_TYPE_U", columnDefinition="CHAR(1)")
	private String paraTypeUpdate; */
	
	/*
	 *  参数性质
	 * '监管交易：
	 *	04－交易手续费
	 *	05－交收手续费
	 *  自主交易：
	 *  04－交易手续费
	 *  05－交收手续费
	 */
	@Column(name = "DPP_ID")
	@AnnoField(caption = "参数性质：监管交易，自主交易")
	private String dppId;
	
	/*
	 * 企业ID 交易所默认参数为0。其它为指定会员的个性化参数。';
	 */
	@OneToOne(targetEntity = Member.class, fetch=FetchType.EAGER)
	@JoinColumn(name="ENTERPRISE_ID", insertable=false, updatable=false)
	@AnnoField(caption = "会员")
    @NotFound(action= NotFoundAction.IGNORE)
	private Member member;
	
	@Column(name = "ENTERPRISE_ID", nullable = false )
	@AnnoField(caption = "会员id")
	private String enterpriseId;

	/**
	 * @return the enterpriseId
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}

	/**
	 * @param enterpriseId the enterpriseId to set
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/**
	 * @return the dbpType
	 */
	public String getDbpType() {
		return dbpType;
	}

	/**
	 * @param dbpType the dbpType to set
	 */
	public void setDbpType(String dbpType) {
		this.dbpType = dbpType;
	}

	/**
	 * @return the dbpStation
	 */
	public String getDbpStation() {
		return dbpStation;
	}

	/**
	 * @param dbpStation the dbpStation to set
	 */
	public void setDbpStation(String dbpStation) {
		this.dbpStation = dbpStation;
	}

	/**
	 * @return the dppId
	 */
	public String getDppId() {
		return dppId;
	}

	/**
	 * @param dppId the dppId to set
	 */
	public void setDppId(String dppId) {
		this.dppId = dppId;
	}

	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

}
