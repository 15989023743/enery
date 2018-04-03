package com.gdwz.energy.code.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessStringIdEntity;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： CodeType.java
 * @类描述： 编码项信息实体类
 * @当前版本：1.0
 * @修改备注
 */
@Entity
@Table(name = "T_SYS_CODE_TYPE")
public class CodeType extends BusinessStringIdEntity{

    private static final long serialVersionUID = -8641657276713426019L;

	@Column(name = "NAME", nullable = false, length = 80)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false, length = 80)
	private String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "codeType")
	private Set<Code> codes = new HashSet<Code>(0);

	public CodeType() {
	}

	public CodeType(String id, String name) {
		super.setId(id);
		this.name = name;
	}

	public CodeType(String name, String description, Set<Code> codes) {
		this.name = name;
		this.description = description;
		this.codes = codes;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Code> getCodes() {
		return this.codes;
	}

	public void setCodes(Set<Code> codes) {
		this.codes = codes;
	}

}