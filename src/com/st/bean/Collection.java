package com.st.bean;

/**
 * Collection entity. @author MyEclipse Persistence Tools
 */

public class Collection implements java.io.Serializable{

	// Fields

	private Integer cid;
	private String collectionId;
	private String userId;
	private String goodsId;
	private String goodsName;
	private String goodsImage;

	// Constructors

	/** default constructor */
	public Collection() {}

	/** full constructor */
	public Collection(String collectionId, String userId, String goodsId,
		String goodsName, String goodsImage) {
		this.collectionId = collectionId;
		this.userId = userId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsImage = goodsImage;
	}

	// Property accessors

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCollectionId() {
		return this.collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getGoodsImage() {
		return this.goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

}