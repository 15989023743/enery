package com.gdwz.energy.bank.entity;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz-energy
 * @类名称： TCssBank
 * @类描述： 银行实体类
 * @当前版本 1.0
 * @修改备注：
 * @作者：陈育宏
 */
@Entity
@Table(name="T_CSS_BANK")
public class TCssBank extends BusinessStringIdEntity {

    /*
     * 银行代码
     */
    @Column(name = "BANK_CODE", nullable = false, length=36)
    @NotNull
    @Size(min=1,max=36,message="{product.title.err}")
    @AnnoField(caption="银行代码")
    private String bankCode;

    /*
     * 银行缩写
     */
    @Column(name = "BANK_NAME", nullable = true, length=36)
    @AnnoField(caption="银行缩写")
    @NotNull
    @Size(min=1,max=36,message="{product.title.err}")
    private String bankName;

    /*
     * 银行全称
     */
    @Column(name = "BANK_FULL_NAME", nullable = false, length=200)
    @Size(min=1,max=200,message="{product.title.err}")
    @AnnoField(caption="银行全称")
    private String bankFullName;

    /*
     * 银行LOGO
     */
    @Column(name = "BANK_LOGO", nullable = false, length=2000)
    @AnnoField(caption="银行LOGO")
    private String bankLogo;

    /*
     * 启用状态
     */
    @Column(name = "STATE_FLAG", length=1, columnDefinition="CHAR(1)")
    @AnnoField(caption="启用状态")
    private String stateFlag;

    /*
     * 每日最大出金金额
     */
    @Column(name = "DAY_MONEY", length=18)
    @AnnoField(caption="每日最大出金金额")
    @NotNull
    @Max(18)
    private Double dayMoney;

    /*
     * 单笔最大出金金额
     */
    @Column(name = "SINGLE_MONEY", length=18)
    @AnnoField(caption="单笔最大出金金额")
    @NotNull
    @Max(18)
    private Double singleMoney;

    /*
     * 每日最大出金次数
     */
    @Column(name = "DAY_NUM", length=18)
    @AnnoField(caption="每日最大出金次数")
    @NotNull
    @Max(18)
    private Double dayNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tCssBank")
/*
    @OrderBy(value = "sequence,addTime desc")
*/
    @Where(clause="delete_status=0")
    private Set<TCssAccount> tCssAccounts = new HashSet<TCssAccount>(0);

    public Set<TCssAccount> gettCssAccounts() {
        return tCssAccounts;
    }

    public void settCssAccounts(Set<TCssAccount> tCssAccounts) {
        this.tCssAccounts = tCssAccounts;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(String stateFlag) {
        this.stateFlag = stateFlag;
    }

    public Double getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(Double dayMoney) {
        this.dayMoney = dayMoney;
    }

    public Double getSingleMoney() {
        return singleMoney;
    }

    public void setSingleMoney(Double singleMoney) {
        this.singleMoney = singleMoney;
    }

    public Double getDayNum() {
        return dayNum;
    }

    public void setDayNum(Double dayNum) {
        this.dayNum = dayNum;
    }
}
