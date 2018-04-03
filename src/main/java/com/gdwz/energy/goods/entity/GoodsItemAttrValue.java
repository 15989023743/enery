package com.gdwz.energy.goods.entity;


import com.gdwz.core.entity.AbstractGeneralEntity;
import com.gdwz.core.entity.annotations.AnnoField;

import javax.persistence.*;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：newFrameRemould
 * @包名称：com.gdwz.energy.goods.entity
 * @创建日期：14-8-29
 */

@Entity
@Table(name="T_YW_GMS_ITEM_ATTR_VALUE",uniqueConstraints = {@UniqueConstraint(columnNames={"ITEM_ID", "ATTR_ID","VALUES_ID"})})
@IdClass(GoodsItemAttrValueId.class) 
public class GoodsItemAttrValue extends AbstractGeneralEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ITEM_ID", nullable = false )
    @AnnoField( caption = "类目ID" )
    private Long itemId;

	@Id
    @Column(name = "ATTR_ID", nullable = false )
    @AnnoField( caption = "属性ID" )
    private Long attrId;

    @Column(name = "VALUES_ID", nullable = false )
    @AnnoField( caption = "全属性值" )
    private String valuesId;
	
	
	public GoodsItemAttrValue() {
		super();
	}
	

	public GoodsItemAttrValue(Long itemId, Long attrId, String valuesId) {
		super();
		this.itemId = itemId;
		this.attrId = attrId;
		this.valuesId = valuesId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getValuesId() {
		return valuesId;
	}

	public void setValuesId(String valuesId) {
		this.valuesId = valuesId;
	}
	
	

}
