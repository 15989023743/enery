package com.gdwz.energy.dataExchange.entity;

/**
 * @包名称：com.gdwz.energy.dataExchange.entity
 * @作者：YQ
 * @创建日期：2014/9/25
 */

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name="T_CMS_LATEST_TREND")
public class LatestTrend extends BusinessStringIdEntity {

    @Column(name = "deal_time",nullable=true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @AnnoField(caption = "时间日期")
    @NotNull(message = "时间日期不能为空")
    private Date dealTime;

    @Column(name = "buyer",nullable=true)
    @AnnoField(caption = "买家")
    @NotNull(message = "买家不能为空")
    @Size(max = 64,min = 1,message = "买家长度不能大于64并且小于1")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_]+$",message="买家只能输入中文、英文、数字-_")
    private String buyer;

    @Column(name = "seller",nullable=true)
    @AnnoField(caption = "卖家")
    @NotNull(message = "卖家不能为空")
    @Size(max = 64,min = 1,message = "卖家长度不能大于64并且小于1")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_]+$",message="卖家只能输入中文、英文、数字-_")
    private String seller;

    @Column(name = "goods_name",nullable=true)
    @AnnoField(caption = "品名")
    @NotNull(message = "品名不能为空")
    @Size(max = 64,min = 1,message = "品名长度不能大于64并且小于1")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_\\#]+$",message="品名只能输入中文、英文、数字-_#")
    private String goodsName;

    @Column(name = "amount",nullable=true,length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField(caption = "数量")
    @NotNull(message = "数量不能为空")
    @DecimalMin(value = "0",message = "数量必须大于等于0")
    @DecimalMax(value = "9999999999999999.99",message = "数量最大值只能是16位整数或者带有2位小数")
    private BigDecimal amount;

    @Column(name = "origin",nullable=true)
    @AnnoField(caption = "产地")
    @NotNull(message = "产地不能为空")
    @Size(max = 64,min = 1,message = "产地长度不能大于64并且小于1")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_\\#]+$",message="产地只能输入中文、英文、数字-_#")
    private String origin;

    @Column(name = "unit",nullable=true)
    @AnnoField(caption = "计量单位",dictname = "ZDY_JLDW")
    @NotNull(message = "计量单位不能为空")
    private Integer unit;
    @Transient
    private String unitmc;

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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
