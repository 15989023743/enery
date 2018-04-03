package com.gdwz.energy.seo.entity;

import com.gdwz.core.entity.BusinessStringIdEntity;
import com.gdwz.core.entity.annotations.AnnoField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @包名称：com.gdwz.energy.seo.entity
 * @作者：YQ
 * @创建日期：2014/9/26
 */
@Entity
@Table(name = "T_SYS_SEO_CONFIG")
public class SeoConfig extends BusinessStringIdEntity{
    @Column(name = "url",nullable=true,unique=true)
    @AnnoField(caption = "url地址")
    @NotNull(message = "url地址不能为空")
    @Size(max = 255,min = 1,message = "url地址长度不能大于255并且小于1")
    private String url;


    @Column(name = "title",nullable=true)
    @AnnoField(caption = "标题")
    @Size(max = 100,min = 0,message = "标题长度不能大于100并且小于0")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_\\#\\/\\s]*$",message="标题只能输入中文、英文、数字空格-_#/")
    private String title;

    @Column(name = "keywords",nullable=true)
    @AnnoField(caption = "关键字")
    @Size(max = 255,min = 0,message = "关键字长度不能大于255并且小于0")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_\\#\\/\\s]*$",message="关键字只能输入中文、英文、数字空格-_#/")
    private String keywords;

    @Column(name = "description",nullable=true)
    @AnnoField(caption = "描述")
    @Size(max = 400,min = 0,message = "描述长度不能大于400并且小于0")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_\\#\\/\\s]*$",message="描述只能输入中文、英文、数字空格-_#/")
    private String description;

    @Column(name = "chanel_name",nullable=true)
    @AnnoField(caption = "频道名")
    @Size(max = 100,min = 0,message = "频道名长度不能大于100并且小于0")
    @Pattern(regexp="^[\\u4e00-\\u9fa50-9a-zA-Z\\-\\_\\#\\/\\s]*$",message="频道名只能输入中文、英文、数字空格-_#/")
    private String chanelName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChanelName() {
        return chanelName;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }
}
