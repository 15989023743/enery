package com.gdwz.energy.trade.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import com.gdwz.common.member.entity.Member;
import com.gdwz.core.entity.BusinessLongIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

/**
 * @项目名称：能化王国
 * @工程名称：gdwz-energy
 * @包名称：com.gdwz.energy.trade.entity
 * @创建日期：14-8-29
 * @作者:chenyuhong
 */

@Entity
@Table(name="T_TRADE_ISSUE")
public class IssueInfo extends BusinessLongIdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ISSUE_CODE",length=50)
    @AnnoField( caption = "挂牌编号" )
	@Size(max=50)
	private String issueCode;
	
	@Column(name = "BUY_OR_SALE",length=1)
    @AnnoField( caption = "挂牌买卖方向",dictname="ZDY_BUY_OR_SALE")
	@NotNull
	//@Size(min=1,max=1)
	private char buyOrSale;  // 1挂牌销售 	2挂牌采购  
	@Transient private String buyOrSalemc;
	
	@Column(name = "ISSUE_TYPE",length=1)
    @AnnoField( caption = "挂牌类型",dictname="ZDY_ISSUE_TYPE")
	@NotNull
	//@Size(min=1,max=1)
	private char issueType; //0非仓单, 1仓单挂牌
	@Transient private String issueTypemc;
	
	@Column(name = "WAREHOUSE_RECEIPT",length=100)
    @AnnoField( caption = "仓单编号" )
	@Size(max=100)
	private String warehouseReceipt;
	
	@Column(name = "GOODS_TITLE",length=100)
    @AnnoField( caption = "商品标题" )
	@NotNull
	@Size(min=1,max=100)
	private String goodsTitle;
	
	@Column(name = "ITEM_GOODS_ID",length=36)
    @AnnoField( caption = "品名ID" )
	@NotNull
	//@Size(min=1,max=36)
	private long goodsItemId;
	
	
	@Column(name = "ITEM_GOODS_NAMEs",length=300)
    @AnnoField( caption = "类目名称" )
	@Size(max=300)
	private String itemGoodsNames;
	
	
	@Column(name = "ITEM_GOODS_NAME",length=100)
    @AnnoField( caption = "产品名称" )
	@Size(max=100)
	@NotNull
	private String itemGoodsName;
	
	@Column(name = "GOODS_UNIT",length=20)
    @AnnoField( caption = "计量单位" ,dictname="ZDY_MEASURING_UNIT")
	@NotNull
	@Size(min=1,max=20)
	private String goodsUnit;
	@Transient private String goodsUnitmc;
	
	@Column(name = "DELIVERY_WAREHOUSE",length=100)
    @AnnoField( caption = "交收仓",dictname="ZDY_TRAN_HOUSE")
	@Size(max=100)
	private String deliveryWarehouse;
	@Transient private String deliveryWarehousemc;
	
	@Column(name = "DELIVERY_PLACE",length=200)
    @AnnoField( caption = "交收地")
	@Size(max=200)
	private String deliveryPlace;
	
	
	@Column(name = "UNIT_PRICE_EXCLUD",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "单价" )
	//@Size(min=1,max=18)
	private BigDecimal unitPriceExclud;
	
	@Column(name = "UNIT_PRICE",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "含税单价" )
	private BigDecimal unitPrice;
	
	@Column(name = "AMOUNT",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "数量" )
	@NotNull
	private double amount;
	
	@Column(name = "STEP_INTO_QUANTITY",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "步进量" )
	private double stepIntoQuantity;
	
	@Column(name = "VOLUMES",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "已成交数量" )
	private double volumes;
	
	@Column(name = "SEC_AMOUNTS",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "已成交件数" )
	private double secAmounts;
	
	@Column(name = "MIN_TRADE_AMOUNT",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "最小交易量" )
	private double minTradeAmount;
	
	@Column(name = "SEC_AMOUNT",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "件数" )
	private double secAmount;
	
	@Column(name = "SINGLE_WGT",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "单件重量" )
	private double singleWgt;
	
	@Column(name = "TRANS_MODE",length=1)
    @AnnoField( caption = "交收方式",dictname="ZDY_TRANS_MODE")
	@NotNull
	private char transMode;  //字典 1自主交收,2监管交易 
	@Transient private String transModemc;
	
	@Column(name = "CURRENCY",length=3)
    @AnnoField( caption = "币种",dictname="ZDY_CURRENCY")
	private String currency; //字典 001:人民币,002：美元,003：欧元 
	@Transient private String currencymc;
	
	@Column(name = "BUYERS_OF_MARGIN",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "买家保证金" )
	private BigDecimal buyersOfMargin;
	
	@Column(name = "SELLERS_OF_MARGIN",length=18,columnDefinition="NUMBER(18,2)")
    @AnnoField( caption = "卖家保证金" )
	private BigDecimal sellersOfMagin;
	
	@Column(name = "PROFIT_LOSS_SCALE",length=18,columnDefinition="NUMBER(18,2) DEFAULT 0")
    @AnnoField( caption = "溢短量比例" )
	private double profitLossScale;
	
	@Column(name = "VALID_TIME")
    @AnnoField( caption = "信息有效期" )
	@NotNull
	private Date validTime;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ISSUE_STATE",length=1,columnDefinition="number(1)")
    @AnnoField( caption = "挂牌状态",dictname="ZDY_ISSUE_STATE")
	private IssueStateEnum issueState; //字典 0新建  1待审核  2上架  3退回   4下架   5作废
	@Transient private String issueStatemc;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "REMOVE_TYPE",length=1,columnDefinition="number(1)")
    @AnnoField( caption = "撤单类型",dictname="ZDY_REMOVE_TYPE")
	private IssueRemoveType removeType=IssueRemoveType.NORMAL; //字典 1挂牌方撤销,2管理员撤销,3系统自动撤销,4挂牌方调价撤销,5定向挂单卖方撤销
	@Transient private String removeTypemc;
	
	@Column(name = "LOCK_STATE",length=1)
    @AnnoField( caption = "锁定状态" )
	private boolean lockState=false;
	
	@Column(name = "LOCKER",length=1)
    @AnnoField( caption = "锁定人" )
	private String locker;
	
	@Column(name = "LOCK_TIME")
    @AnnoField( caption = "锁定时间" )
	private Date lockTime;
	
	@Column(name = "UNLOCK_PEOPLE",length=36)
    @AnnoField( caption = "解锁人" )
	private String unlockPeople;
	
	@Column(name = "UNLOCK_TIME")
    @AnnoField( caption = "解锁时间" )
	private Date unlockTime;
	
	@Column(name = "ADJUST_PRICE_PEOPLE",length=64)
    @AnnoField( caption = "调价人" )
	private String adJustPricePeople;
	
	@Column(name = "ADJUST_PRICE_TIME")
    @AnnoField( caption = "调价时间" )
	private Date adJustPriceTime;
	
	@Column(name = "CHECK_USER_ID",length=36)
    @AnnoField( caption = "审核人ID" )
	private String checkUserId;
	
	@Column(name = "CHECK_TIME",length=36)
    @AnnoField( caption = "审核时间" )
	private Date checkTime;
	
	@JoinColumn(name = "ADD_MEMBER_ID")
    @AnnoField( caption = "挂牌会员" )
	@ManyToOne(cascade =CascadeType.REFRESH)
	private Member member;
	
	
	@Column(name = "LATEST_DELIVERY_DATE")
    @AnnoField( caption = "最迟交货日期" )
	@NotNull
	private Date latestDeliveryDate;
	
	
	@OneToMany(fetch=FetchType.EAGER,targetEntity=IssueGoodsAttrValues.class,mappedBy="issue",cascade=CascadeType.REFRESH)
	@Where(clause=" DELETE_STATUS='0' ")
	private Set<IssueGoodsAttrValues> issueGoodsAttrValues=new HashSet<IssueGoodsAttrValues>();

	@OneToMany(fetch=FetchType.LAZY,targetEntity=IssueAccesory.class,mappedBy="issueInfo",cascade=CascadeType.REFRESH)
	@Where(clause=" DELETE_STATUS='0' ")
	private Set<IssueAccesory> issueAccesoryInIssues=new HashSet<IssueAccesory>();
	
	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public char getBuyOrSale() {
		return buyOrSale;
	}

	public void setBuyOrSale(char buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	public char getIssueType() {
		return issueType;
	}

	public void setIssueType(char issueType) {
		this.issueType = issueType;
	}

	public String getWarehouseReceipt() {
		return warehouseReceipt;
	}

	public void setWarehouseReceipt(String warehouseReceipt) {
		this.warehouseReceipt = warehouseReceipt;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public long getGoodsItemId() {
		return goodsItemId;
	}

	public void setGoodsItemId(long goodsItemId) {
		this.goodsItemId = goodsItemId;
	}

	public String getItemGoodsName() {
		return itemGoodsName;
	}

	public void setItemGoodsName(String itemGoodsName) {
		this.itemGoodsName = itemGoodsName;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getDeliveryWarehouse() {
		return deliveryWarehouse;
	}

	public void setDeliveryWarehouse(String deliveryWarehouse) {
		this.deliveryWarehouse = deliveryWarehouse;
	}

	public BigDecimal getUnitPriceExclud() {
		return unitPriceExclud;
	}

	public void setUnitPriceExclud(BigDecimal unitPriceExclud) {
		this.unitPriceExclud = unitPriceExclud;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double  amount) {
		this.amount = amount;
	}


	public double getVolumes() {
		return volumes;
	}

	public void setVolumes(double volumes) {
		this.volumes = volumes;
	}

	public double getSecAmounts() {
		return secAmounts;
	}

	public void setSecAmounts(double secAmounts) {
		this.secAmounts = secAmounts;
	}

	public double getMinTradeAmount() {
		return minTradeAmount;
	}

	public void setMinTradeAmount(double minTradeAmount) {
		this.minTradeAmount = minTradeAmount;
	}

	public double getSecAmount() {
		return secAmount;
	}

	public void setSecAmount(double secAmount) {
		this.secAmount = secAmount;
	}

	public void setSecAmount(int secAmount) {
		this.secAmount = secAmount;
	}

	public double getSingleWgt() {
		return singleWgt;
	}

	public void setSingleWgt(double singleWgt) {
		this.singleWgt = singleWgt;
	}

	public char getTransMode() {
		return transMode;
	}

	public void setTransMode(char transMode) {
		this.transMode = transMode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBuyersOfMargin() {
		return buyersOfMargin;
	}

	public void setBuyersOfMargin(BigDecimal buyersOfMargin) {
		this.buyersOfMargin = buyersOfMargin;
	}

	public BigDecimal getSellersOfMagin() {
		return sellersOfMagin;
	}

	public void setSellersOfMagin(BigDecimal sellersOfMagin) {
		this.sellersOfMagin = sellersOfMagin;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public IssueStateEnum getIssueState() {
		return issueState;
	}

	public void setIssueState(IssueStateEnum issueState) {
		this.issueState = issueState;
	}

	public IssueRemoveType getRemoveType() {
		return removeType;
	}

	public void setRemoveType(IssueRemoveType removeType) {
		this.removeType = removeType;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public String getUnlockPeople() {
		return unlockPeople;
	}

	public void setUnlockPeople(String unlockPeople) {
		this.unlockPeople = unlockPeople;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getAdJustPricePeople() {
		return adJustPricePeople;
	}

	public void setAdJustPricePeople(String adJustPricePeople) {
		this.adJustPricePeople = adJustPricePeople;
	}

	public Date getAdJustPriceTime() {
		return adJustPriceTime;
	}

	public void setAdJustPriceTime(Date adJustPriceTime) {
		this.adJustPriceTime = adJustPriceTime;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setLockState(boolean lockState) {
		this.lockState = lockState;
	}

	public String getLocker() {
		return locker;
	}

	public void setLocker(String locker) {
		this.locker = locker;
	}

	public Set<IssueGoodsAttrValues> getIssueGoodsAttrValues() {
		return issueGoodsAttrValues;
	}

	public void setIssueGoodsAttrValues(
			Set<IssueGoodsAttrValues> issueGoodsAttrValues) {
		this.issueGoodsAttrValues = issueGoodsAttrValues;
	}

	public double getProfitLossScale() {
		return profitLossScale;
	}

	public void setProfitLossScale(double profitLossScale) {
		this.profitLossScale = profitLossScale;
	}

	public Set<IssueAccesory> getIssueAccesoryInIssues() {
		return issueAccesoryInIssues;
	}

	public void setIssueAccesoryInIssues(
			Set<IssueAccesory> issueAccesoryInIssues) {
		this.issueAccesoryInIssues = issueAccesoryInIssues;
	}

	public boolean isLockState() {
		return lockState;
	}

	public String getItemGoodsNames() {
		return itemGoodsNames;
	}

	public void setItemGoodsNames(String itemGoodsNames) {
		this.itemGoodsNames = itemGoodsNames;
	}

	public String getBuyOrSalemc() {
		return buyOrSalemc;
	}

	public void setBuyOrSalemc(String buyOrSalemc) {
		this.buyOrSalemc = buyOrSalemc;
	}

	public String getIssueTypemc() {
		return issueTypemc;
	}

	public void setIssueTypemc(String issueTypemc) {
		this.issueTypemc = issueTypemc;
	}

	public String getTransModemc() {
		return transModemc;
	}

	public void setTransModemc(String transModemc) {
		this.transModemc = transModemc;
	}

	public String getCurrencymc() {
		return currencymc;
	}

	public void setCurrencymc(String currencymc) {
		this.currencymc = currencymc;
	}

	public String getIssueStatemc() {
		return issueStatemc;
	}

	public void setIssueStatemc(String issueStatemc) {
		this.issueStatemc = issueStatemc;
	}

	public String getRemoveTypemc() {
		return removeTypemc;
	}

	public void setRemoveTypemc(String removeTypemc) {
		this.removeTypemc = removeTypemc;
	}

	public String getGoodsUnitmc() {
		return goodsUnitmc;
	}

	public void setGoodsUnitmc(String goodsUnitmc) {
		this.goodsUnitmc = goodsUnitmc;
	}

	public String getDeliveryWarehousemc() {
		return deliveryWarehousemc;
	}

	public void setDeliveryWarehousemc(String deliveryWarehousemc) {
		this.deliveryWarehousemc = deliveryWarehousemc;
	}

	public String getDeliveryPlace() {
		return deliveryPlace;
	}

	public void setDeliveryPlace(String deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}

	public double getStepIntoQuantity() {
		return stepIntoQuantity;
	}

	public void setStepIntoQuantity(double stepIntoQuantity) {
		this.stepIntoQuantity = stepIntoQuantity;
	}

	public Date getLatestDeliveryDate() {
		return latestDeliveryDate;
	}

	public void setLatestDeliveryDate(Date latestDeliveryDate) {
		this.latestDeliveryDate = latestDeliveryDate;
	}

	
}
