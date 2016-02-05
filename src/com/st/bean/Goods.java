package com.st.bean;

/**
 * Goods entity. @author MyEclipse Persistence Tools
 */

public class Goods implements java.io.Serializable{

	// Fields

	public Integer gid;
	public String goodsId;
	public String goodsName;
	public String goodsDesc;
	public Double goodsPrice;
	public String goodsImage;
	public String goodsPublisher;
	public String goodsPublishTime;
	public String goodsUndercarriage;
	public String goodsStatusId;
	public String goodsClassId;
	public String goodsSortId;
	public String contacts;

	// Constructors

	/** default constructor */
	public Goods() {}

	/** minimal constructor */
	public Goods(String goodsId, String goodsName, String goodsDesc,
		Double goodsPrice, String goodsImage, String goodsPublisher,
		String goodsPublishTime, String goodsStatusId, String goodsClassId,
		String goodsSortId, String contacts) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsDesc = goodsDesc;
		this.goodsPrice = goodsPrice;
		this.goodsImage = goodsImage;
		this.goodsPublisher = goodsPublisher;
		this.goodsPublishTime = goodsPublishTime;
		this.goodsStatusId = goodsStatusId;
		this.goodsClassId = goodsClassId;
		this.goodsSortId = goodsSortId;
		this.contacts = contacts;
	}

	/** full constructor */
	public Goods(String goodsId, String goodsName, String goodsDesc,
		Double goodsPrice, String goodsImage, String goodsPublisher,
		String goodsPublishTime, String goodsUndercarriage,
		String goodsStatusId, String goodsClassId, String goodsSortId,
		String contacts) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsDesc = goodsDesc;
		this.goodsPrice = goodsPrice;
		this.goodsImage = goodsImage;
		this.goodsPublisher = goodsPublisher;
		this.goodsPublishTime = goodsPublishTime;
		this.goodsUndercarriage = goodsUndercarriage;
		this.goodsStatusId = goodsStatusId;
		this.goodsClassId = goodsClassId;
		this.goodsSortId = goodsSortId;
		this.contacts = contacts;
	}

	// Property accessors

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return this.goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public Double getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsImage() {
		return this.goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getGoodsPublisher() {
		return this.goodsPublisher;
	}

	public void setGoodsPublisher(String goodsPublisher) {
		this.goodsPublisher = goodsPublisher;
	}

	public String getGoodsPublishTime() {
		return this.goodsPublishTime;
	}

	public void setGoodsPublishTime(String goodsPublishTime) {
		this.goodsPublishTime = goodsPublishTime;
	}

	public String getGoodsUndercarriage() {
		return this.goodsUndercarriage;
	}

	public void setGoodsUndercarriage(String goodsUndercarriage) {
		this.goodsUndercarriage = goodsUndercarriage;
	}

	public String getGoodsStatusId() {
		return this.goodsStatusId;
	}

	public void setGoodsStatusId(String goodsStatusId) {
		this.goodsStatusId = goodsStatusId;
	}

	public String getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(String goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getGoodsSortId() {
		return this.goodsSortId;
	}

	public void setGoodsSortId(String goodsSortId) {
		this.goodsSortId = goodsSortId;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

}