package com.st.bean;

/**
 * GoodsSort entity. @author MyEclipse Persistence Tools
 */

public class GoodsSort implements java.io.Serializable{

	// Fields

	private Integer gsid;
	private String goodsSortId;
	private String sortName;
	private String belongClassId;
	private String sortDesc;

	// Constructors

	/** default constructor */
	public GoodsSort() {}

	/** minimal constructor */
	public GoodsSort(String goodsSortId, String sortName, String belongClassId) {
		this.goodsSortId = goodsSortId;
		this.sortName = sortName;
		this.belongClassId = belongClassId;
	}

	/** full constructor */
	public GoodsSort(String goodsSortId, String sortName, String belongClassId,
		String sortDesc) {
		this.goodsSortId = goodsSortId;
		this.sortName = sortName;
		this.belongClassId = belongClassId;
		this.sortDesc = sortDesc;
	}

	// Property accessors

	public Integer getGsid() {
		return this.gsid;
	}

	public void setGsid(Integer gsid) {
		this.gsid = gsid;
	}

	public String getGoodsSortId() {
		return this.goodsSortId;
	}

	public void setGoodsSortId(String goodsSortId) {
		this.goodsSortId = goodsSortId;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getBelongClassId() {
		return this.belongClassId;
	}

	public void setBelongClassId(String belongClassId) {
		this.belongClassId = belongClassId;
	}

	public String getSortDesc() {
		return this.sortDesc;
	}

	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}

}