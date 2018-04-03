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
@Table(name="T_DBP_TIME_PARA")
public class TDbpTimePara extends BusinessStringIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4474819958816664180L;

    @Column(name = "DBP_TYPE", length = 2, nullable = false )
	private String dbpType;
    @Column(name = "DBP_STATION", length = 1, nullable = false )
	private String dbpStation;

    /**
     *会员ID
     */
	@OneToOne(targetEntity = Member.class, fetch=FetchType.EAGER)
	@JoinColumn(name="ENTERPRISE_ID", insertable=false, updatable=false)
    @NotFound(action= NotFoundAction.IGNORE)
    private Member member;
	
	@Column(name = "ENTERPRISE_ID", nullable = false )
	private String enterpriseId;

	@OneToOne(targetEntity = TDbpDpp.class, fetch=FetchType.EAGER)
	@JoinColumn(name="DPP_ID" , nullable = true)
	@NotFound(action=NotFoundAction.IGNORE)
	private TDbpDpp tDbpDpp;

	@Column(name = "TIME_TYPE_D", length = 1)
	private String timeTypeD;
	@Column(name = "PAVA_VAL_D", length = 10, nullable = false )
	private String pavaValD;
	@Column(name ="UNIT_D", length = 1)
	private String unitD;
	@Column(name = "TIME_TYPE_U", length = 1)
	private String timeTypeU;
	@Column(name = "PAVA_VAL_U", length = 10, nullable = false )
	private String pavaValU;
	@Column(name = "UNIT_U", length = 1)
	private String unitU;
	
  
	
	public TDbpTimePara(){
		
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
	 * @return the timeTypeD
	 */
	public String getTimeTypeD() {
		return timeTypeD;
	}



	/**
	 * @param timeTypeD the timeTypeD to set
	 */
	public void setTimeTypeD(String timeTypeD) {
		this.timeTypeD = timeTypeD;
	}



	/**
	 * @return the pavaValD
	 */
	public String getPavaValD() {
		return pavaValD;
	}



	/**
	 * @param pavaValD the pavaValD to set
	 */
	public void setPavaValD(String pavaValD) {
		this.pavaValD = pavaValD;
	}



	/**
	 * @return the unitD
	 */
	public String getUnitD() {
		return unitD;
	}



	/**
	 * @param unitD the unitD to set
	 */
	public void setUnitD(String unitD) {
		this.unitD = unitD;
	}



	/**
	 * @return the timeTypeU
	 */
	public String getTimeTypeU() {
		return timeTypeU;
	}



	/**
	 * @param timeTypeU the timeTypeU to set
	 */
	public void setTimeTypeU(String timeTypeU) {
		this.timeTypeU = timeTypeU;
	}



	/**
	 * @return the pavaValU
	 */
	public String getPavaValU() {
		return pavaValU;
	}



	/**
	 * @param pavaValU the pavaValU to set
	 */
	public void setPavaValU(String pavaValU) {
		this.pavaValU = pavaValU;
	}



	/**
	 * @return the unitU
	 */
	public String getUnitU() {
		return unitU;
	}



	/**
	 * @param unitU the unitU to set
	 */
	public void setUnitU(String unitU) {
		this.unitU = unitU;
	}
	
}
