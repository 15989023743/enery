package com.gdwz.energy.trade.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.gdwz.core.entity.annotations.AnnoEntity;


/**
 * 挂牌质量指标
 * @author shuanfeng
 *
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("2")
@AnnoEntity(caption = "挂牌质量指标",isWriteLog=true)
public class IssueUalityIndicator extends IssueAccesory {

}
