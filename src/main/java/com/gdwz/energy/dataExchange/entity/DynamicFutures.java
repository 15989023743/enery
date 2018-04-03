package com.gdwz.energy.dataExchange.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;
import com.gdwz.energy.goods.entity.GoodsItem;

/**
 * @包名称：com.gdwz.energy.dataExchange.entity
 * @作者：gzq
 * @创建日期：2014/9/15
 */
@Entity
@Table(name="T_CMS_DYNAMIC_FUTURES")
public class DynamicFutures extends BusinessStringIdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4906866685557539947L;

	
	@ManyToOne(targetEntity = GoodsItem.class, fetch=FetchType.EAGER)
	@JoinColumn(name="GOODS_ITEM_ID", insertable=false, updatable=false)
	@AnnoField( caption = "商品分类id" )
    @NotFound(action= NotFoundAction.IGNORE)
    private GoodsItem goodsItem;
	
	@Column(name = "GOODS_ITEM_ID", nullable = false )
	@AnnoField(caption = "品名id")
	private Long goodsItemId;
	
	@Column(name = "PRICE", nullable = false)
	@AnnoField(caption = "最新价格")
	private String price;
	
	@Transient
    private String priceStr;
	
	@Column(name = "UP_AND_DOWN", nullable = false)
	@AnnoField(caption = "上下涨幅")
	private String upAndDown;
	
	@Column(name = "POSITION", nullable = false)
	@AnnoField(caption = "持仓量")
	private String position;
	
	@Column(name = "IS_SHOW", nullable = false)
	@AnnoField(caption = "前台是否展示")
	private int isShow;
	
	@Column(name = "SHOW_NUM", nullable = false)
	@AnnoField(caption = "前台展示顺序，从大到小排序")
	private int showNum;

	@Column(name = "price_date", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @AnnoField(caption = "价格时间")
    @NotNull(message = "价格时间不能为空")
    private Date priceDate;

    @Column(name = "unit", nullable = true)
    @AnnoField(caption = "价格单位", dictname = "ZDY_DJ")
    @NotNull(message = "价格单位不能为空")
    private Integer unit;
    
    @Transient
    private String unitmc;
    
	
	/**
	 * @return the unitmc
	 */
	public String getUnitmc() {
		return unitmc;
	}

	/**
	 * @param unitmc the unitmc to set
	 */
	public void setUnitmc(String unitmc) {
		this.unitmc = unitmc;
	}

	/**
	 * @return the priceStr
	 */
	public String getPriceStr() {
		return priceStr;
	}

	/**
	 * @param priceStr the priceStr to set
	 */
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	/**
	 * @return the priceDate
	 */
	public Date getPriceDate() {
		return priceDate;
	}

	/**
	 * @param priceDate the priceDate to set
	 */
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	/**
	 * @return the unit
	 */
	public Integer getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	/**
	 * @return the isShow
	 */
	public int getIsShow() {
		return isShow;
	}

	/**
	 * @param isShow the isShow to set
	 */
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}


	/**
	 * @return the showNum
	 */
	public int getShowNum() {
		return showNum;
	}

	/**
	 * @param showNum the showNum to set
	 */
	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

	/**
	 * @return the goodsItem
	 */
	public GoodsItem getGoodsItem() {
		return goodsItem;
	}

	/**
	 * @param goodsItem the goodsItem to set
	 */
	public void setGoodsItem(GoodsItem goodsItem) {
		this.goodsItem = goodsItem;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the upAndDown
	 */
	public String getUpAndDown() {
		return upAndDown;
	}

	/**
	 * @param upAndDown the upAndDown to set
	 */
	public void setUpAndDown(String upAndDown) {
		this.upAndDown = upAndDown;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the goodsItemId
	 */
	public Long getGoodsItemId() {
		return goodsItemId;
	}

	/**
	 * @param goodsItemId the goodsItemId to set
	 */
	public void setGoodsItemId(Long goodsItemId) {
		this.goodsItemId = goodsItemId;
	}
	
	
	
}
