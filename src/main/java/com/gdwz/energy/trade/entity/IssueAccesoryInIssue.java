package com.gdwz.energy.trade.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.gdwz.core.entity.annotations.AnnoEntity;

/**
 * 挂牌信息专用附件
 * @author shuanfeng
 *
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("1")
@AnnoEntity(caption = "挂牌附件",isWriteLog=true)
public class IssueAccesoryInIssue extends IssueAccesory {

}
