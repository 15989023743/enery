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

@Entity
@Table(name="T_DBP_GOLD_PARA")
public class TDbpGoldPara extends BusinessStringIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7563350498044913787L;

//  DBP_TYPE             VARCHAR2(2)                     not null,
	@Column(name = "DBP_TYPE", length = 2, nullable = false )
	private String dbpType;
//  DBP_STATION          CHAR(1)                         not null,
  	@Column(name = "DBP_STATION", length = 1, nullable = false )
	private String dbpStation;
	
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
     *会员ID
     */
	@OneToOne(targetEntity = Member.class, fetch=FetchType.EAGER)
	@JoinColumn(name="ENTERPRISE_ID", insertable=false, updatable=false)
    @NotFound(action= NotFoundAction.IGNORE)
    private Member member;
	
	@Column(name = "ENTERPRISE_ID", nullable = false )
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


	//    DPP_ID               CHAR(2),
    @OneToOne(targetEntity = TDbpDpp.class, fetch=FetchType.EAGER)
    @JoinColumn(name="DPP_ID" , nullable = true)
    @NotFound(action=NotFoundAction.IGNORE)
    private TDbpDpp tDbpDpp;
//    PARA_TYPE_D          CHAR(1),
    @Column(name = "PARA_TYPE_D", length = 1 )
	private String paraTypeD;
//    PAVA_VAL_D           NUMBER(18,4)                    not null,
    @Column(name = "PAVA_VAL_D", length = 18, nullable = false )
	private Double pavaValD;
//    PARA_TYPE_U          CHAR(1),
    @Column(name = "PARA_TYPE_U", length = 1 )
	private String paraTypeU;
//    PAVA_VAL_U           NUMBER(18,4)                    not null,
    @Column(name = "PAVA_VAL_U", length = 18, nullable = false )
	private Double pavaValU;

    
    public TDbpGoldPara(){
    	
    }


	/**
	 * @return the 会员ID
	 */
	public Member getMember() {
		return member;
	}


	/**
	 * @param 会员ID the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	
	/**
	 * @return the tDbpDpp
	 */
	public TDbpDpp gettDbpDpp() {
		return tDbpDpp;
	}


	/**
	 * @param tDbpDpp the tDbpDpp to set
	 */
	public void settDbpDpp(TDbpDpp tDbpDpp) {
		this.tDbpDpp = tDbpDpp;
	}


	/**
	 * @return the paraTypeD
	 */
	public String getParaTypeD() {
		return paraTypeD;
	}


	/**
	 * @param paraTypeD the paraTypeD to set
	 */
	public void setParaTypeD(String paraTypeD) {
		this.paraTypeD = paraTypeD;
	}


	/**
	 * @return the pavaValD
	 */
	public Double getPavaValD() {
		return pavaValD;
	}


	/**
	 * @param pavaValD the pavaValD to set
	 */
	public void setPavaValD(Double pavaValD) {
		this.pavaValD = pavaValD;
	}


	/**
	 * @return the paraTypeU
	 */
	public String getParaTypeU() {
		return paraTypeU;
	}


	/**
	 * @param paraTypeU the paraTypeU to set
	 */
	public void setParaTypeU(String paraTypeU) {
		this.paraTypeU = paraTypeU;
	}


	/**
	 * @return the pavaValU
	 */
	public Double getPavaValU() {
		return pavaValU;
	}


	/**
	 * @param pavaValU the pavaValU to set
	 */
	public void setPavaValU(Double pavaValU) {
		this.pavaValU = pavaValU;
	}
    
}
