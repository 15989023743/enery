package com.gdwz.energy.goods.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdwz.core.entity.BusinessLongIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

/**
 * @项目名称：能化王国
 * @工程名称：gdwz-energy
 * @包名称：com.gdwz.energy.goods.entity
 * @创建日期：14-8-29
 */

@Entity
@Table(name="T_YW_GMS_GOODS_ATTR")
public class GoodsAttr extends BusinessLongIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2183616644577966784L;


	@Column(name = "NAME",nullable=false)
    @AnnoField( caption = "属性名称" )
    private String name;
    
	
	@Column(name = "ATTR_GROUP")
    @AnnoField( caption = "分组" )
    private String group;
	
    @Column(name = "DICT_NAME")
    @AnnoField( caption = "字典名称" )
    private String dictName;

    @Column(name = "INPUT_TYPE",nullable=false)
    @AnnoField( caption = "输入方式" )
    private String inputType;

    @Column(name = "CATEGORY",nullable=false)
    @AnnoField( caption = "类别" )
    private String category;

    @Column(name = "TYPE")
    @AnnoField( caption = "属性类型" )
    private String type;

    @Column(name = "ENABLED",nullable=false)
    @AnnoField( caption = "启用状态" )
    private boolean enabled;

    @ManyToOne(cascade =CascadeType.REFRESH,optional=true)
    @JoinColumn(name = "ITEM_ID")
    @AnnoField(caption="商品类目")
    private GoodsItem goodsItem;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public GoodsItem getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(GoodsItem goodsItem) {
        this.goodsItem = goodsItem;
    }

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
    
    
    
}
