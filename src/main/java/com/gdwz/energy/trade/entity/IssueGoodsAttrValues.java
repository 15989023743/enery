package com.gdwz.energy.trade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gdwz.core.entity.BusinessLongIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;


@Entity
@Table(name="T_TRADE_ISSUE_ATTRS")
public class IssueGoodsAttrValues extends BusinessLongIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="ISSUE_ID",updatable=false,insertable=false)
	private Long issueId;
	
	@JoinColumn(name = "ISSUE_ID", nullable = false)
	@AnnoField(caption = "挂牌ID")
	@ManyToOne
	private IssueInfo issue;
	
	@Column(name = "ITEM_ID", nullable = false)
	@AnnoField(caption = "品名ID")
	private long itemId;
	
	@Column(name = "ATTR_ID", nullable = false, length = 19)
	@AnnoField(caption = "属性ID")
	@NotNull
	private long attrId;

	@Column(name = "ATTR_VALUE_ID", nullable = false, length = 19)
	@AnnoField(caption = "属性值ID")
	@NotNull
	private long attrValueId;
	
	@Column(name = "ATTR_NAME", nullable = false, length = 100)
	@AnnoField(caption = "属性名称")
	private String attrName;
	
	@Column(name = "ATTR_VALUE", nullable = false, length = 100)
	@AnnoField(caption = "属性值")
	private String attrValue;
	
	@Column(name = "ATTR_GROUP")
    @AnnoField( caption = "分组" )
    private String group;

	@Column(name = "ATTR_CODE")
    @AnnoField( caption = "属性代码" )
	private String attrCode;
	
	

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public long getAttrId() {
		return attrId;
	}

	public void setAttrId(long attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public long getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public IssueInfo getIssue() {
		return issue;
	}

	public void setIssue(IssueInfo issue) {
		this.issue = issue;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	
	
}
