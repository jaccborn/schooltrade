package com.st.bean;

/**
 * WantBuy entity. @author MyEclipse Persistence Tools
 */

public class WantBuy implements java.io.Serializable{

	// Fields

	private Integer wbid;
	private String wantBuyId;
	private String wantUser;
	private String title;
	private String description;
	private String publishTime;

	// Constructors

	/** default constructor */
	public WantBuy() {}

	/** full constructor */
	public WantBuy(String wantBuyId, String wantUser, String title,
		String description, String publishTime) {
		this.wantBuyId = wantBuyId;
		this.wantUser = wantUser;
		this.title = title;
		this.description = description;
		this.publishTime = publishTime;
	}

	// Property accessors

	public Integer getWbid() {
		return this.wbid;
	}

	public void setWbid(Integer wbid) {
		this.wbid = wbid;
	}

	public String getWantBuyId() {
		return this.wantBuyId;
	}

	public void setWantBuyId(String wantBuyId) {
		this.wantBuyId = wantBuyId;
	}

	public String getWantUser() {
		return this.wantUser;
	}

	public void setWantUser(String wantUser) {
		this.wantUser = wantUser;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

}