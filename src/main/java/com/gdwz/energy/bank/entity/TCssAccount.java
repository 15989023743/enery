package com.gdwz.energy.bank.entity;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

import javax.persistence.*;

/**
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz-energy
 * @类名称： TCssAccount.java
 * @类描述： 账户实体类
 * @当前版本：1.0
 * @修改备注
 */
@Entity
@Table(name = "T_CSS_ACCOUNT")
public class TCssAccount extends BusinessStringIdEntity {

    //资金汇总账号
    @Column(name = "SUPACCTID", nullable = true, length = 32)
    @AnnoField(caption="资金汇总账号")
    private String supacctid;
    //子账户账号
    @Column(name = "CUSTACCTID", nullable = true, length = 32)
    @AnnoField(caption="子账户账号")
    private String custacctid;
    //子账户名称
    @Column(name = "CUSTNAME", nullable = true, length = 200)
    @AnnoField(caption="子账户名称")
    private String custname;
    //入金账号
    @Column(name = "INACCTID", length = 80)
    @AnnoField(caption="入金账号")
    private String inacctid;
    //入金账号开户联行号
    @Column(name = "INACCTIDBANKCODE2", length = 80)
    @AnnoField(caption="入金账号开户联行号")
    private String inacctidbankcode2;
    //入金账号开户行名
    @Column(name = "INACCTIDBANKNAME2", length = 200)
    @AnnoField(caption="入金账号开户行名")
    private String inacctidbankname2;
    //入金账户名称
    @Column(name = "INACCTIDNAME", length = 200)
    @AnnoField(caption="入金账户名称")
    private String inacctidname;
    //入金账号开户行地址
    @Column(name = "INADDRESS", length = 200)
    @AnnoField(caption="入金账号开户行地址")
    private String inaddress;
    //入金转账方式1：本行 2：同城  3：异地汇款
    @Column(name = "INTRANTYPE")
    @AnnoField(caption="入金转账方式")
    private Character intrantype;
    //出金账号
    @Column(name = "OUTACCTID", length = 80)
    @AnnoField(caption="出金账号")
    private String outacctid;
    //出金账号开户联行号
    @Column(name = "OUTACCTIDBANKCODE", length = 80)
    @AnnoField(caption="出金账号开户联行号")
    private String outacctidbankcode;
    //出金账号开户行名
    @Column(name = "OUTACCTIDBANKNAME", length = 200)
    @AnnoField(caption="出金账号开户行名")
    private String outacctidbankname;
    //出金账户名称
    @Column(name = "OUTACCTIDNAME", length = 200)
    @AnnoField(caption="出金账户名称")
    private String outacctidname;
    //出金账号开户行地址
    @Column(name = "OUTADDRESS", length = 200)
    @AnnoField(caption="出金账号开户行地址")
    private String outaddress;
    //出金转账方式1：本行 2：同城  3：异地汇款
    @Column(name = "OUTTRANTYPE")
    @AnnoField(caption="出金转账方式")
    private Character outtrantype;
    /**
     * 银行ID
     */
    @ManyToOne(optional=false)
    @JoinColumn(name = "BANK_ID", nullable = false)
    @AnnoField(caption="银行ID")
    private TCssBank tCssBank;
    /**
     * 会员ID
     */
    @Column(name = "MEMNER_ID", length = 36, nullable = false)
    @AnnoField(caption="会员ID")
    private String memberId;
    /**
     * 可用资金
     */

    @Column(name = "EXP_FUND", nullable = false)
    @AnnoField(caption="可用资金")
    private Double expFund;
    /**
     * 冻结资金
     */

    @Column(name = "FRO_FUND", nullable = false)
    @AnnoField(caption="冻结资金")
    private Double froFund;
    /**
     * 资金状态：1-正常;2-冻结
     */

    @Column(name = "FUND_STATUS", nullable = false)
    @AnnoField(caption="资金状态")
    private String fundStatus;
    /**
     * 诚信保障金
     */

    @Column(name = "GF_SECURITY", nullable = false)
    @AnnoField(caption="诚信保障金")
    private Double gfSecurity;
    /**
     * 诚信金(广物账户为诚信金资金池总额)
     */

    @Column(name = "HON_GOLD", nullable = false)
    @AnnoField(caption="诚信金")
    private Double honGold;
    /**
     * 占用保证金
     */

    @Column(name = "OCC_DES", nullable = false)
    @AnnoField(caption="占用保证金")
    private Double occDes;
    /**
     * 占用诚信保障金
     */

    @Column(name = "OCC_GF", nullable = false)
    @AnnoField(caption="占用诚信保障金")
    private Double occGf;
    /**
     * 占用诚信金
     */

    @Column(name = "OCC_IG", nullable = false)
    @AnnoField(caption="占用诚信金")
    private Double occIg;
    /**
     * 占用货款
     */

    @Column(name = "OCC_PAY", nullable = false)
    @AnnoField(caption="占用货款")
    private Double occPay;

    public String getSupacctid() {
        return supacctid;
    }

    public void setSupacctid(String supacctid) {
        this.supacctid = supacctid;
    }

    public String getCustacctid() {
        return custacctid;
    }

    public void setCustacctid(String custacctid) {
        this.custacctid = custacctid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getInacctid() {
        return inacctid;
    }

    public void setInacctid(String inacctid) {
        this.inacctid = inacctid;
    }

    public String getInacctidbankcode2() {
        return inacctidbankcode2;
    }

    public void setInacctidbankcode2(String inacctidbankcode2) {
        this.inacctidbankcode2 = inacctidbankcode2;
    }

    public String getInacctidbankname2() {
        return inacctidbankname2;
    }

    public void setInacctidbankname2(String inacctidbankname2) {
        this.inacctidbankname2 = inacctidbankname2;
    }

    public String getInacctidname() {
        return inacctidname;
    }

    public void setInacctidname(String inacctidname) {
        this.inacctidname = inacctidname;
    }

    public String getInaddress() {
        return inaddress;
    }

    public void setInaddress(String inaddress) {
        this.inaddress = inaddress;
    }

    public Character getIntrantype() {
        return intrantype;
    }

    public void setIntrantype(Character intrantype) {
        this.intrantype = intrantype;
    }

    public String getOutacctid() {
        return outacctid;
    }

    public void setOutacctid(String outacctid) {
        this.outacctid = outacctid;
    }

    public String getOutacctidbankcode() {
        return outacctidbankcode;
    }

    public void setOutacctidbankcode(String outacctidbankcode) {
        this.outacctidbankcode = outacctidbankcode;
    }

    public String getOutacctidbankname() {
        return outacctidbankname;
    }

    public void setOutacctidbankname(String outacctidbankname) {
        this.outacctidbankname = outacctidbankname;
    }

    public String getOutacctidname() {
        return outacctidname;
    }

    public void setOutacctidname(String outacctidname) {
        this.outacctidname = outacctidname;
    }

    public String getOutaddress() {
        return outaddress;
    }

    public void setOutaddress(String outaddress) {
        this.outaddress = outaddress;
    }

    public Character getOuttrantype() {
        return outtrantype;
    }

    public void setOuttrantype(Character outtrantype) {
        this.outtrantype = outtrantype;
    }

    public TCssBank gettCssBank() {
        return tCssBank;
    }

    public void settCssBank(TCssBank tCssBank) {
        this.tCssBank = tCssBank;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Double getExpFund() {
        return expFund;
    }

    public void setExpFund(Double expFund) {
        this.expFund = expFund;
    }

    public Double getFroFund() {
        return froFund;
    }

    public void setFroFund(Double froFund) {
        this.froFund = froFund;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public Double getGfSecurity() {
        return gfSecurity;
    }

    public void setGfSecurity(Double gfSecurity) {
        this.gfSecurity = gfSecurity;
    }

    public Double getHonGold() {
        return honGold;
    }

    public void setHonGold(Double honGold) {
        this.honGold = honGold;
    }

    public Double getOccDes() {
        return occDes;
    }

    public void setOccDes(Double occDes) {
        this.occDes = occDes;
    }

    public Double getOccGf() {
        return occGf;
    }

    public void setOccGf(Double occGf) {
        this.occGf = occGf;
    }

    public Double getOccIg() {
        return occIg;
    }

    public void setOccIg(Double occIg) {
        this.occIg = occIg;
    }

    public Double getOccPay() {
        return occPay;
    }

    public void setOccPay(Double occPay) {
        this.occPay = occPay;
    }
}
