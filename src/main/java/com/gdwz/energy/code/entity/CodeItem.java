package com.gdwz.energy.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessStringIdEntity;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： CodeItem.java
 * @类描述： 编码项信息实体类
 * @当前版本：1.0
 * @修改备注
 */
@Entity
@Table(name = "T_SYS_CODE_ITEM")
public class CodeItem extends BusinessStringIdEntity {

    private static final long serialVersionUID = -3930332041061417350L;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODE_ID", nullable = false)
	private Code code;

	@Column(name = "DESCRIPTION", length = 80)
	private String description;

	@Column(name = "ITEM_TYPE", nullable = false, length = 1)
	private String type;

	@Column(name = "ITEM_VALUE", length = 80)
	private String value;

	@Column(name = "FORMAT", length = 80)
	private String format;

	@Column(name = "SEQUENCE", nullable = false, scale = 0)
	private Long sequence;

	@Column(name = "LENGTH", scale = 0)
	private Long length;

	public CodeItem() {
	}

	public CodeItem(Code code, String type, Long sequence) {
		this.code = code;
		this.type = type;
		this.sequence = sequence;
	}

	public CodeItem(Code code, String description, String type, String value,
			String format, Long sequence, Long length) {
		this.code = code;
		this.description = description;
		this.type = type;
		this.value = value;
		this.format = format;
		this.sequence = sequence;
		this.length = length;
	}

	public Code getCode() {
		return this.code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Long getLength() {
		return this.length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

}