package com.gdwz.energy.goods.entity;


public class GoodsItemAttrValueId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long itemId;

    private Long attrId;


    
    
	public GoodsItemAttrValueId() {
		super();
	}



	public GoodsItemAttrValueId(Long itemId, Long attrId) {
		super();
		this.itemId = itemId;
		this.attrId = attrId;
	}



	public Long getItemId() {
		return itemId;
	}



	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}



	public Long getAttrId() {
		return attrId;
	}



	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}



	/**
	 * 覆盖hashCode方法，必须要有
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (itemId == null ? 0 : itemId.hashCode());
		result = PRIME * result + (attrId == null ? 0 : attrId.hashCode());
		return result;
	}

	/**
	 * 覆盖equals方法，必须要有
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof GoodsItemAttrValueId)) return false;
		GoodsItemAttrValueId objKey = (GoodsItemAttrValueId)obj;
		if(itemId==objKey.itemId && attrId==objKey.attrId) {
			return true;
		}
		return false;
	}

}
