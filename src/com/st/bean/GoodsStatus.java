package com.st.bean;

/**
 * GoodsStatus entity. @author MyEclipse Persistence Tools
 */

public class GoodsStatus implements java.io.Serializable{

	// Fields

	private Integer gstid;
	private String goodsStatusId;
	private String status;

	// Constructors

	/** default constructor */
	public GoodsStatus() {}

	/** full constructor */
	public GoodsStatus(String goodsStatusId, String status) {
		this.goodsStatusId = goodsStatusId;
		this.status = status;
	}

	// Property accessors

	public Integer getGstid() {
		return this.gstid;
	}

	public void setGstid(Integer gstid) {
		this.gstid = gstid;
	}

	public String getGoodsStatusId() {
		return this.goodsStatusId;
	}

	public void setGoodsStatusId(String goodsStatusId) {
		this.goodsStatusId = goodsStatusId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}