package com.gdwz.energy.sysparam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

@Entity
@Table(name="T_DBP_DPP")
public class TDbpDpp extends BusinessStringIdEntity {

	private static final long serialVersionUID = -3066227282134531323L;
	
    @Column(name = "DPP_TYPE", length = 1 )
    @AnnoField(caption = "主键id")
	private String dppType = "0";
    
    @Column(name = "DPP_NAME", length = 40, nullable = false)
    @AnnoField(caption = "参数名称")
	private String dppName ;

	public TDbpDpp(){
	}
    
	public String getDppType() {
		return dppType;
	}

	public void setDppType(String dppType) {
		this.dppType = dppType;
	}

	public String getDppName() {
		return dppName;
	}

	public void setDppName(String dppName) {
		this.dppName = dppName;
	}

    
}
