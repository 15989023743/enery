package com.gdwz.energy.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessStringIdEntity;

/**
 * 
 * @类名称： WasteMatching.java
 * @类描述： 动态流水号信息实体类
 * @当前版本：1.0
 * @修改备注
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_SYS_WASTE_MATCHING")
public class WasteMatching extends BusinessStringIdEntity {

    @Column(name = "CODE_ITEM_ID", nullable = false, scale = 0)
	private String codeitemid;

	@Column(name = "KEY", nullable = false, length = 80)
	private String key;

	@Column(name = "WASTE_VALUE", nullable = false, length = 20)
	private String value;

	public WasteMatching() {
	}

	public WasteMatching(String codeitemid, String key, String value) {
		this.codeitemid = codeitemid;
		this.key = key;
		this.value = value;
	}

	public String getCodeitemid() {
		return this.codeitemid;
	}

	public void setCodeitemid(String codeitemid) {
		this.codeitemid = codeitemid;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}