package com.gdwz.energy.sysparam.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称：   DbpProcessFeeDetail
 * @类描述：   手续费明细实体类
 * @当前版本   1.0
 * @修改备注：
 */

@Entity
@Table(name="T_DBP_PROCESS_FEE_DETAIL")
public class ProcessFeeDetail extends BusinessStringIdEntity{

    private static final long serialVersionUID = 1894185778479646448L;
    
	/*
	 * 手续费ID
	 */
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name="FEE_ID", nullable=false)
	@AnnoField(caption = "手续费")
	private ProcessFee processFee;
	
	/*
	 * 起始范围
	 */
	@Column(name = "START_RANGE", nullable = true, columnDefinition="number(18,3)")
	@AnnoField(caption = "起始范围")
	private double startRange;
	
	/*
	 * 结束范围
	 */
	@Column(name = "END_RANGE", nullable = true, columnDefinition="number(18,3)")
	@AnnoField(caption = "结束范围")
	private double endRange;
	
	/*
	 * '参数生效值'
	 */
	@Column(name = "PAVA_VAL_D", nullable = false, columnDefinition="number(18,4)")
	@AnnoField(caption = "参数生效值")
	private double paraValDefault;
	
	/*
	 * 资金参数类型生效值：0－固定值（元/笔），1－可变值'
	 */
	@Column(name = "PARA_TYPE_D", columnDefinition="CHAR(1)")
	@AnnoField(caption = "资金参数类型生效值：0－固定值（元/笔），1－可变值")
	private String paraTypeDefault;
	
	/*
	 * '状态：０－修改，１－生效，２－失效';
	 */
	@Column(name = "STATE_FLAG", columnDefinition="CHAR(1)")
	@AnnoField(caption = "状态：０－修改，１－生效，２－失效")
	private String stateFlag;

	/*
	 * '计费类型：1－金额，2－吨数';
	 */
	@Column(name = "FEE_TYPE", columnDefinition="CHAR(1)")
	@AnnoField(caption = "计费类型：1－金额，2－吨数")
	private String feeType;

	

	/**
	 * @return the processFee
	 */
	public ProcessFee getProcessFee() {
		return processFee;
	}

	/**
	 * @param processFee the processFee to set
	 */
	public void setProcessFee(ProcessFee processFee) {
		this.processFee = processFee;
	}

	/**
	 * @return the startRange
	 */
	public double getStartRange() {
		return startRange;
	}

	/**
	 * @param startRange the startRange to set
	 */
	public void setStartRange(double startRange) {
		this.startRange = startRange;
	}

	/**
	 * @return the endRange
	 */
	public double getEndRange() {
		return endRange;
	}

	/**
	 * @param endRange the endRange to set
	 */
	public void setEndRange(double endRange) {
		this.endRange = endRange;
	}

	/**
	 * @return the paraValDefault
	 */
	public double getParaValDefault() {
		return paraValDefault;
	}

	/**
	 * @param paraValDefault the paraValDefault to set
	 */
	public void setParaValDefault(double paraValDefault) {
		this.paraValDefault = paraValDefault;
	}

	/**
	 * @return the paraTypeDefault
	 */
	public String getParaTypeDefault() {
		return paraTypeDefault;
	}

	/**
	 * @param paraTypeDefault the paraTypeDefault to set
	 */
	public void setParaTypeDefault(String paraTypeDefault) {
		this.paraTypeDefault = paraTypeDefault;
	}

	/**
	 * @return the stateFlag
	 */
	public String getStateFlag() {
		return stateFlag;
	}

	/**
	 * @param stateFlag the stateFlag to set
	 */
	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}

	/**
	 * @return the feeType
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	
	
}
