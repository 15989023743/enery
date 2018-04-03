package com.gdwz.energy.sysparam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

/**
 * 
 * @项目名称：华南大宗商品交易平台
 * @工程名称：gdwz-energy-web
 * @类名称： TBrpNews
 * @类描述： 交易消息通讯实体类
 * @当前版本 1.0
 * @修改备注：
 * @作者：甘志群
 */
@Entity
@Table(name = "T_BRP_NEWS")
public class TBrpNews extends BusinessStringIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7173546906654859741L;

	@Column(name = "MES_CONTENT", length=36)
    @NotNull
    @AnnoField(caption="消息内容")
	private String mesContent;

	@Column(name = "SEND")
    @NotNull
    @AnnoField(caption="发送人")
	private String send;

	@Column(name = "SEND_TYPE")
    @NotNull
    @AnnoField(caption="0:普通用户；1：管理员用户")
	private char sendType;

	@Column(name = "RECEIVE")
    @NotNull
    @AnnoField(caption="接收人")
	private String receive;

	@Column(name = "RECEIVE_TYPE")
    @NotNull
    @AnnoField(caption="0:在线用户；1:在线管理员；2:在线交易员；3:指定管理员；4:指定交易员；5：指定会员")
	private String receiveType;

	@Column(name = "NEWS_CODE")
    @NotNull
    @AnnoField(caption="消息编码")
	private String newsCode;

	@Column(name = "NEWS_TYPE", length = 1, columnDefinition = "CHAR(1 CHAR) default 1")
    @NotNull
    @AnnoField(caption="0－普通消息，1－交易消息")
	private Character newsType;

	@Column(name = "URL", length = 300)
    @AnnoField(caption="URL")
	private String url;

	/**
	 * @return the mesContent
	 */
	public String getMesContent() {
		return mesContent;
	}

	/**
	 * @param mesContent the mesContent to set
	 */
	public void setMesContent(String mesContent) {
		this.mesContent = mesContent;
	}

	/**
	 * @return the send
	 */
	public String getSend() {
		return send;
	}

	/**
	 * @param send the send to set
	 */
	public void setSend(String send) {
		this.send = send;
	}

	/**
	 * @return the sendType
	 */
	public char getSendType() {
		return sendType;
	}

	/**
	 * @param sendType the sendType to set
	 */
	public void setSendType(char sendType) {
		this.sendType = sendType;
	}

	/**
	 * @return the receive
	 */
	public String getReceive() {
		return receive;
	}

	/**
	 * @param receive the receive to set
	 */
	public void setReceive(String receive) {
		this.receive = receive;
	}

	/**
	 * @return the receiveType
	 */
	public String getReceiveType() {
		return receiveType;
	}

	/**
	 * @param receiveType the receiveType to set
	 */
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	/**
	 * @return the newsCode
	 */
	public String getNewsCode() {
		return newsCode;
	}

	/**
	 * @param newsCode the newsCode to set
	 */
	public void setNewsCode(String newsCode) {
		this.newsCode = newsCode;
	}

	/**
	 * @return the newsType
	 */
	public Character getNewsType() {
		return newsType;
	}

	/**
	 * @param newsType the newsType to set
	 */
	public void setNewsType(Character newsType) {
		this.newsType = newsType;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
