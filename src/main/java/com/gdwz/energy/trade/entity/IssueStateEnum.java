package com.gdwz.energy.trade.entity;

import java.util.Arrays;

/**
 * 挂牌状态
 * @author shuangfeng
 *
 */
public enum IssueStateEnum {
	/**
	 * 新建
	 */
	CREATE,
	/**
	 * 待审核
	 */
	TOAUDIT,
	/**
	 * 上架
	 */
	SHELVES,
	/**
	 * 退回
	 */
	FALLBACK,
	/**
	 * 下架
	 */
	THESHELVES,
	/**
	 * 作废
	 */
	INVALID;

	@Override
	public String toString() {
		return String.valueOf(Arrays.asList(IssueStateEnum.values()).indexOf(IssueStateEnum.valueOf(super.toString())));
	}
	
	
	
}
