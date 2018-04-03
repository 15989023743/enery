package com.gdwz.energy.dataExchange.entity;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;
import com.gdwz.energy.goods.entity.GoodsItem;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @包名称：com.gdwz.energy.dataExchange.entity
 * @作者：YQ
 * @创建日期：2014/9/25
 */
@Entity
@Table(name="T_CMS_MAIN_CONTRACT")
public class MainContract extends BusinessStringIdEntity {

    @Column(name = "price", nullable = true, length = 18, columnDefinition = "NUMBER(18,2)")
    @AnnoField(caption = "价格")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0", message = "价格必须大于等于0")
    @DecimalMax(value = "9999999999999999.99", message = "价格最大值只能是16位整数或者带有2位小数")
    private BigDecimal price;
    @Transient
    private String priceStr;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
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
