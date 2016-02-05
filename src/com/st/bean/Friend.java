package com.st.bean;

/**
 * Friend entity. @author MyEclipse Persistence Tools
 */

public class Friend implements java.io.Serializable{

	// Fields

	private Integer fid;
	private String userId;
	private String friendId;

	// Constructors

	/** default constructor */
	public Friend() {}

	/** full constructor */
	public Friend(String userId, String friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}

	// Property accessors

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return this.friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

}