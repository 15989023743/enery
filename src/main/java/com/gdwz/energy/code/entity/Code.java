package com.gdwz.energy.code.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessStringIdEntity;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz.platform
 * @类名称： Code.java
 * @类描述： 编码信息实体类
 * @当前版本：1.0
 * @修改备注
 */
@Entity
@Table(name = "T_SYS_CODE")
public class Code extends BusinessStringIdEntity {

    private static final long serialVersionUID = 3640235727256944409L;
    
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "CODE_TYPE_ID", nullable = false)
	private CodeType codeType;

	@Column(name = "NAME", nullable = false, length = 80)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false, length = 80)
	private String description;

	@Column(name = "CURRDATE", length = 40)
	private String currdate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "code")
	private Set<CodeItem> codeItems = new HashSet<CodeItem>(0);

	public Code() {
	}

	public Code(String id, String name) {
		super.setId(id);
		this.name = name;
	}

	public Code(CodeType codeType, String name, String description) {
		this.codeType = codeType;
		this.name = name;
		this.description = description;
	}

	public Code(CodeType codeType, String name, String description,
			String currdate, Set<CodeItem> codeItems) {
		this.codeType = codeType;
		this.name = name;
		this.description = description;
		this.currdate = currdate;
		this.codeItems = codeItems;
	}

	public CodeType getCodeType() {
		return this.codeType;
	}

	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
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

	public String getCurrdate() {
		return this.currdate;
	}

	public void setCurrdate(String currdate) {
		this.currdate = currdate;
	}

	public Set<CodeItem> getCodeItems() {
		return this.codeItems;
	}

	public void setCodeItems(Set<CodeItem> codeItems) {
		this.codeItems = codeItems;
	}

}