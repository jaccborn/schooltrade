package com.st.bean;

/**
 * GoodsClass entity. @author MyEclipse Persistence Tools
 */

public class GoodsClass implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer gcid;
	private String goodsClassId;
	private String className;
	private String classDesc;

	// Constructors

	/** default constructor */
	public GoodsClass() {}

	/** minimal constructor */
	public GoodsClass(String goodsClassId, String className) {
		this.goodsClassId = goodsClassId;
		this.className = className;
	}

	/** full constructor */
	public GoodsClass(String goodsClassId, String className, String classDesc) {
		this.goodsClassId = goodsClassId;
		this.className = className;
		this.classDesc = classDesc;
	}

	// Property accessors

	public Integer getGcid() {
		return this.gcid;
	}

	public void setGcid(Integer gcid) {
		this.gcid = gcid;
	}

	public String getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(String goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassDesc() {
		return this.classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

}