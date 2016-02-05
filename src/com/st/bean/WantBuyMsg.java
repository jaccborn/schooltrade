package com.st.bean;

/**
 * WantBuyMsg entity. @author MyEclipse Persistence Tools
 */

public class WantBuyMsg implements java.io.Serializable{

	// Fields

	private Integer wbmid;
	private String wantBuyMsgId;
	private String userIid;
	private String msg;
	private String time;

	// Constructors

	/** default constructor */
	public WantBuyMsg() {}

	/** full constructor */
	public WantBuyMsg(String wantBuyMsgId, String userIid, String msg,
		String time) {
		this.wantBuyMsgId = wantBuyMsgId;
		this.userIid = userIid;
		this.msg = msg;
		this.time = time;
	}

	// Property accessors

	public Integer getWbmid() {
		return this.wbmid;
	}

	public void setWbmid(Integer wbmid) {
		this.wbmid = wbmid;
	}

	public String getWantBuyMsgId() {
		return this.wantBuyMsgId;
	}

	public void setWantBuyMsgId(String wantBuyMsgId) {
		this.wantBuyMsgId = wantBuyMsgId;
	}

	public String getUserIid() {
		return this.userIid;
	}

	public void setUserIid(String userIid) {
		this.userIid = userIid;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}