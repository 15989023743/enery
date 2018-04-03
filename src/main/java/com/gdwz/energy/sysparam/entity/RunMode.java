package com.gdwz.energy.sysparam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz-energy-web
 * @类名称： RunMode
 * @类描述： 交易参数实体类
 * @当前版本 1.0
 * @修改备注：
 * @作者：甘志群
 */
@Entity
@Table(name = "T_BRP_RUN")
public class RunMode extends BusinessStringIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1044499738956968468L;

    //上午开始时间
    @Column(name="B_TIME_D", nullable=false)
    @AnnoField(caption="交易开始时间")
	private Date bTimeD;
    //上午开始时间修改值
	@Column(name="B_TIME_U", nullable=true)
    @AnnoField(caption="交易开始时间(修改值)")
	private Date bTimeU;
    //下午开始时间
	@Column(name="BPM_TIME_D", nullable=false)
    @AnnoField(caption="交易开始时间2")
	private Date bpmTimeD;
	//下午开始时间修改值
	@Column(name="BPM_TIME_U", nullable=true)
    @AnnoField(caption="交易开始时间2(修改值)")
	private Date bpmTimeU;
	
	@Column(name="BUSINESS_TYPE", nullable=false)
    @NotNull
    @AnnoField(caption="交易状态")
	private String businessType;
    //上午结束时间
	@Column(name="E_TIME_D", nullable=false)
    @AnnoField(caption="交易结束时间")
	private Date eTimeD;
    //上午结束时间修改值
	@Column(name="E_TIME_U", nullable=true)
    @AnnoField(caption="交易结束时间(修改值)")
	private Date eTimeU;
	//下午结束时间
	@Column(name="EPM_TIME_D", nullable=false)
    @AnnoField(caption="交易开始时间")
	private Date epmTimeD;
	//下午结束时间修改值
	@Column(name="EPM_TIME_U", nullable=true)
    @AnnoField(caption="交易开始时间(修改值)")
	private Date epmTimeU;
	
	@Column(name="NO_DATE_D", nullable=true, length = 800)
    @AnnoField(caption="非交易日期")
	private String noDateD;

	@Column(name="NO_DATE_U", nullable=true, length = 800)
    @AnnoField(caption="非交易日期(修改值)")
	private String noDateU;

	@Column(name="NO_FRI_D", nullable=false)
    @AnnoField(caption="非交易星期五")
	private String noFriD;

	@Column(name="NO_FRI_U", nullable=true)
    @AnnoField(caption="非交易星期五(修改值)")
	private String noFriU;

	@Column(name="NO_MON_D", nullable=false)
    @AnnoField(caption="非交易星期一")
	private String noMonD;

	@Column(name="NO_MON_U", nullable=true)
    @AnnoField(caption="非交易星期一(修改值)")
	private String noMonU;

	@Column(name="NO_SATUR_D", nullable=false)
    @AnnoField(caption="非交易星期六")
	private String noSaturD;

	@Column(name="NO_SATUR_U", nullable=true)
    @AnnoField(caption="非交易星期六(修改值)")
	private String noSaturU;

	@Column(name="NO_SUN_D", nullable=false)
    @AnnoField(caption="非交易星期日")
	private String noSunD;

	@Column(name="NO_SUN_U", nullable=true)
    @AnnoField(caption="非交易星期日(修改值)")
	private String noSunU;

	@Column(name="NO_THURS_D", nullable=false)
    @AnnoField(caption="非交易星期四")
	private String noThursD;

	@Column(name="NO_THURS_U", nullable=true)
    @AnnoField(caption="非交易星期四(修改值)")
	private String noThursU;

	@Column(name="NO_TUES_D", nullable=false)
    @AnnoField(caption="非交易星期二")
	private String noTuesD;

	@Column(name="NO_TUES_U", nullable=true)
    @AnnoField(caption="非交易星期二(修改值)")
	private String noTuesU;

	@Column(name="NO_WEDNES_D", nullable=false)
    @AnnoField(caption="非交易星期三")
	private String noWednesD;

	@Column(name="NO_WEDNES_U", nullable=true)
    @AnnoField(caption="非交易星期三(修改值)")
	private String noWednesU;

	@Column(name="RUN_MODE", nullable=false)
    @NotNull
    @AnnoField(caption="运行模式")
	private String runMode;

	/**
	 * @return the bTimeD
	 */
	public Date getbTimeD() {
		return bTimeD;
	}

	/**
	 * @param bTimeD the bTimeD to set
	 */
	public void setbTimeD(Date bTimeD) {
		this.bTimeD = bTimeD;
	}

	/**
	 * @return the bTimeU
	 */
	public Date getbTimeU() {
		return bTimeU;
	}

	/**
	 * @param bTimeU the bTimeU to set
	 */
	public void setbTimeU(Date bTimeU) {
		this.bTimeU = bTimeU;
	}

	/**
	 * @return the bpmTimeD
	 */
	public Date getBpmTimeD() {
		return bpmTimeD;
	}

	/**
	 * @param bpmTimeD the bpmTimeD to set
	 */
	public void setBpmTimeD(Date bpmTimeD) {
		this.bpmTimeD = bpmTimeD;
	}

	/**
	 * @return the bpmTimeU
	 */
	public Date getBpmTimeU() {
		return bpmTimeU;
	}

	/**
	 * @param bpmTimeU the bpmTimeU to set
	 */
	public void setBpmTimeU(Date bpmTimeU) {
		this.bpmTimeU = bpmTimeU;
	}

	/**
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * @param businessType the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return the eTimeD
	 */
	public Date geteTimeD() {
		return eTimeD;
	}

	/**
	 * @param eTimeD the eTimeD to set
	 */
	public void seteTimeD(Date eTimeD) {
		this.eTimeD = eTimeD;
	}

	/**
	 * @return the eTimeU
	 */
	public Date geteTimeU() {
		return eTimeU;
	}

	/**
	 * @param eTimeU the eTimeU to set
	 */
	public void seteTimeU(Date eTimeU) {
		this.eTimeU = eTimeU;
	}

	/**
	 * @return the epmTimeD
	 */
	public Date getEpmTimeD() {
		return epmTimeD;
	}

	/**
	 * @param epmTimeD the epmTimeD to set
	 */
	public void setEpmTimeD(Date epmTimeD) {
		this.epmTimeD = epmTimeD;
	}

	/**
	 * @return the epmTimeU
	 */
	public Date getEpmTimeU() {
		return epmTimeU;
	}

	/**
	 * @param epmTimeU the epmTimeU to set
	 */
	public void setEpmTimeU(Date epmTimeU) {
		this.epmTimeU = epmTimeU;
	}

	/**
	 * @return the noDateD
	 */
	public String getNoDateD() {
		return noDateD;
	}

	/**
	 * @param noDateD the noDateD to set
	 */
	public void setNoDateD(String noDateD) {
		this.noDateD = noDateD;
	}

	/**
	 * @return the noDateU
	 */
	public String getNoDateU() {
		return noDateU;
	}

	/**
	 * @param noDateU the noDateU to set
	 */
	public void setNoDateU(String noDateU) {
		this.noDateU = noDateU;
	}

	/**
	 * @return the noFriD
	 */
	public String getNoFriD() {
		return noFriD;
	}

	/**
	 * @param noFriD the noFriD to set
	 */
	public void setNoFriD(String noFriD) {
		this.noFriD = noFriD;
	}

	/**
	 * @return the noFriU
	 */
	public String getNoFriU() {
		return noFriU;
	}

	/**
	 * @param noFriU the noFriU to set
	 */
	public void setNoFriU(String noFriU) {
		this.noFriU = noFriU;
	}

	/**
	 * @return the noMonD
	 */
	public String getNoMonD() {
		return noMonD;
	}

	/**
	 * @param noMonD the noMonD to set
	 */
	public void setNoMonD(String noMonD) {
		this.noMonD = noMonD;
	}

	/**
	 * @return the noMonU
	 */
	public String getNoMonU() {
		return noMonU;
	}

	/**
	 * @param noMonU the noMonU to set
	 */
	public void setNoMonU(String noMonU) {
		this.noMonU = noMonU;
	}

	/**
	 * @return the noSaturD
	 */
	public String getNoSaturD() {
		return noSaturD;
	}

	/**
	 * @param noSaturD the noSaturD to set
	 */
	public void setNoSaturD(String noSaturD) {
		this.noSaturD = noSaturD;
	}

	/**
	 * @return the noSaturU
	 */
	public String getNoSaturU() {
		return noSaturU;
	}

	/**
	 * @param noSaturU the noSaturU to set
	 */
	public void setNoSaturU(String noSaturU) {
		this.noSaturU = noSaturU;
	}

	/**
	 * @return the noSunD
	 */
	public String getNoSunD() {
		return noSunD;
	}

	/**
	 * @param noSunD the noSunD to set
	 */
	public void setNoSunD(String noSunD) {
		this.noSunD = noSunD;
	}

	/**
	 * @return the noSunU
	 */
	public String getNoSunU() {
		return noSunU;
	}

	/**
	 * @param noSunU the noSunU to set
	 */
	public void setNoSunU(String noSunU) {
		this.noSunU = noSunU;
	}

	/**
	 * @return the noThursD
	 */
	public String getNoThursD() {
		return noThursD;
	}

	/**
	 * @param noThursD the noThursD to set
	 */
	public void setNoThursD(String noThursD) {
		this.noThursD = noThursD;
	}

	/**
	 * @return the noThursU
	 */
	public String getNoThursU() {
		return noThursU;
	}

	/**
	 * @param noThursU the noThursU to set
	 */
	public void setNoThursU(String noThursU) {
		this.noThursU = noThursU;
	}

	/**
	 * @return the noTuesD
	 */
	public String getNoTuesD() {
		return noTuesD;
	}

	/**
	 * @param noTuesD the noTuesD to set
	 */
	public void setNoTuesD(String noTuesD) {
		this.noTuesD = noTuesD;
	}

	/**
	 * @return the noTuesU
	 */
	public String getNoTuesU() {
		return noTuesU;
	}

	/**
	 * @param noTuesU the noTuesU to set
	 */
	public void setNoTuesU(String noTuesU) {
		this.noTuesU = noTuesU;
	}

	/**
	 * @return the noWednesD
	 */
	public String getNoWednesD() {
		return noWednesD;
	}

	/**
	 * @param noWednesD the noWednesD to set
	 */
	public void setNoWednesD(String noWednesD) {
		this.noWednesD = noWednesD;
	}

	/**
	 * @return the noWednesU
	 */
	public String getNoWednesU() {
		return noWednesU;
	}

	/**
	 * @param noWednesU the noWednesU to set
	 */
	public void setNoWednesU(String noWednesU) {
		this.noWednesU = noWednesU;
	}

	/**
	 * @return the runMode
	 */
	public String getRunMode() {
		return runMode;
	}

	/**
	 * @param runMode the runMode to set
	 */
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	
}
