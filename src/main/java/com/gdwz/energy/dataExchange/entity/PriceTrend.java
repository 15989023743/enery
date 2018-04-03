package com.gdwz.energy.dataExchange.entity;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;
import com.gdwz.energy.goods.entity.GoodsItem;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @包名称：com.gdwz.energy.dataExchange.entity
 * @作者：YQ
 * @创建日期：2014/9/15
 */
@Entity
@Table(name="T_CMS_PRICE_TREND")
public class PriceTrend extends BusinessStringIdEntity{

    @Column(name = "price",nullable=true,length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField(caption = "价格")
    @NotNull(message = "价格不能为空")
//    @Pattern(regexp = "^(0|[1-9]\\d{1,17})(\\.[0-9]{1,2})?$",message = "价格格式只能是16位整数或者带有2位小数")
    @DecimalMin(value = "0",message = "价格必须大于等于0")
    @DecimalMax(value = "9999999999999999.99",message = "价格最大值只能是16位整数或者带有2位小数")
    private BigDecimal price;

    @Transient
    private String priceStr;

    @Column(name = "exponentpre",nullable=true,length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField(caption = "价格指数环比")
//    @NotNull(message = "价格指数环比不能为空")
    @DecimalMin(value = "-9999999999999999.99",message = "价格指数环比最小值只能是16位负整数或者带有2位小数的负数")
    @DecimalMax(value = "9999999999999999.99",message = "价格指数环比最大值只能是16位整数或者带有2位小数")
    private BigDecimal exponentPre;

    @Column(name = "exponentpreadd",nullable=true,length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField(caption = "价格指数环比增幅")
//    @NotNull(message = "价格指数环比增幅不能为空")
    @DecimalMin(value = "-9999999999999999.99",message = "价格指数环比增幅最小值只能是16位负整数或者带有2位小数的负数")
    @DecimalMax(value = "9999999999999999.99",message = "价格指数环比增幅最大值只能是16位整数或者带有2位小数的正数")
    private BigDecimal exponentPreAdd;

    @Column(name = "exponentyear",nullable=true,length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField(caption = "价格指数同比")
//    @NotNull(message = "价格指数同比不能为空")
    @DecimalMin(value = "-9999999999999999.99",message = "价格指数同比最小值只能是16位负整数或者带有2位小数的负数")
    @DecimalMax(value = "9999999999999999.99",message = "价格指数同比最大值只能是16位整数或者带有2位小数的正数")
    private BigDecimal exponentYear;

    @Column(name = "exponentyearadd",nullable=true,length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField(caption = "价格指数同比增幅")
//    @NotNull(message = "价格指数同比增幅不能为空")
    @DecimalMin(value = "-9999999999999999.99",message = "价格指数同比增幅最小值只能是16位负整数或者带有2位小数的负数")
    @DecimalMax(value = "9999999999999999.99",message = "价格指数同比增幅最大值只能是16位整数或者带有2位小数的正数")
    private BigDecimal exponentYearAdd;


    @Column(name = "price_date",nullable=true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @AnnoField(caption = "价格时间")
    @NotNull(message = "价格时间不能为空")
    private Date priceDate;

    @ManyToOne(cascade= CascadeType.REFRESH)
    @JoinColumn(name = "goodsItem_id",referencedColumnName="id", nullable = true)
    @AnnoField( caption = "商品分类id" )
    @NotNull(message = "商品分类id不能为空")
    private GoodsItem goodsItem;

    @Column(name = "unit",nullable=true)
    @AnnoField(caption = "价格单位",dictname = "ZDY_DJ")
    @NotNull(message = "价格单位不能为空")
    private Integer unit;
    @Transient private String unitmc;


    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getExponentPre() {
        return exponentPre;
    }

    public void setExponentPre(BigDecimal exponentPre) {
        this.exponentPre = exponentPre;
    }

    public BigDecimal getExponentPreAdd() {
        return exponentPreAdd;
    }

    public void setExponentPreAdd(BigDecimal exponentPreAdd) {
        this.exponentPreAdd = exponentPreAdd;
    }

    public BigDecimal getExponentYear() {
        return exponentYear;
    }

    public void setExponentYear(BigDecimal exponentYear) {
        this.exponentYear = exponentYear;
    }

    public BigDecimal getExponentYearAdd() {
        return exponentYearAdd;
    }

    public void setExponentYearAdd(BigDecimal exponentYearAdd) {
        this.exponentYearAdd = exponentYearAdd;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public GoodsItem getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(GoodsItem goodsItem) {
        this.goodsItem = goodsItem;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUnitmc() {
        return unitmc;
    }

    public void setUnitmc(String unitmc) {
        this.unitmc = unitmc;
    }
}
