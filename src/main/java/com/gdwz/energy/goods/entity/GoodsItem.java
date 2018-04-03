package com.gdwz.energy.goods.entity;

import com.gdwz.core.entity.AbstractTreeEntity;
import com.gdwz.core.entity.BusinessLongIdTreeEntity;
import com.gdwz.core.entity.annotations.AnnoField;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @项目名称：能化王国
 * @工程名称：gdwz-energy
 * @包名称：com.gdwz.energy.goods.entity
 * @创建日期：14-8-29
 */

@Entity
@Table(name="T_YW_GMS_GOODS_ITEM")
public class GoodsItem extends BusinessLongIdTreeEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade= CascadeType.REFRESH,optional=true)
    @JoinColumn(name = "PARENT_ID",referencedColumnName="id", nullable = true)
    @AnnoField( caption = "上级" )
    private GoodsItem parent;

    @OneToMany(mappedBy="parent",cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
    @OrderBy(value = "index")
    @AnnoField(caption="下级")
    @Where(clause="delete_status=0")
    private Set<GoodsItem> childs=new HashSet<GoodsItem>();

    @Column(name = "ENABLED",nullable = false)
    @AnnoField( caption = "启用状态" )
    private boolean enabled;
    
    @Column(name = "PROFIT_LOSS_SCALE")
    @AnnoField( caption = "损益比例" )
    private String profitLossScale;


    @OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="goodsItem")
    @Where(clause="delete_status=0")
    private Set<GoodsAttr> goodsAttrs=new HashSet<GoodsAttr>();


    @Override
    public AbstractTreeEntity<Long> getParent() {
        return parent;
    }

    @Override
    public void setParent(AbstractTreeEntity<Long> longAbstractTreeEntity) {
        parent=(GoodsItem)longAbstractTreeEntity;
    }

    @Override
    public Set getChildren() {
        return childs;
    }

    @Override
    public void setChildren(Set abstractTreeEntities) {
        childs=abstractTreeEntities;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setParent(GoodsItem parent) {
        this.parent = parent;
    }

    public Set<GoodsAttr> getGoodsAttrs() {
        return goodsAttrs;
    }

    public void setGoodsAttrs(Set<GoodsAttr> goodsAttrs) {
        this.goodsAttrs = goodsAttrs;
    }

    public Set<GoodsItem> getChilds() {
        return childs;
    }

    public void setChilds(Set<GoodsItem> childs) {
        this.childs = childs;
    }

	public String getProfitLossScale() {
		return profitLossScale;
	}

	public void setProfitLossScale(String profitLossScale) {
		this.profitLossScale = profitLossScale;
	}
    
    
}


