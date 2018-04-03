package com.gdwz.energy.trade.entity;

import java.util.Arrays;

public enum IssueRemoveType {
	/**
	 * 正常
	 */
	NORMAL,
	
	/**
	 * 挂牌方撤销
	 */
	MEMBER_CANCEL,
	
	/**
	 * 管理员撤销
	 */
	ADMIN_CANCEL,
	
	/**
	 * 系统撤销
	 */
	SYSTEM_CANCEL,
	
	/**
	 * 挂牌方调价撤销
	 */
	MEMBER_PRICES_CANCEL,
	
	/**
	 * 定向挂单卖方撤销
	 */
	DIRECTIONAL_GUADAN_CANCEL;
	
	@Override
	public String toString() {
		return String.valueOf(Arrays.asList(IssueRemoveType.values()).indexOf(IssueRemoveType.valueOf(super.toString())));
	}
}
