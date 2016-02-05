package com.st.bean;

/**
 * UserIf entity. @author MyEclipse Persistence Tools
 */

public class UserIf implements java.io.Serializable{

	// Fields

	public Integer uid;
	public String userId;
	public String userName;
	public String userPwd;
	public String registerTime;
	public String registerImage;

	// Constructors

	/** default constructor */
	public UserIf() {}

	/** minimal constructor */
	public UserIf(String userId, String userName, String userPwd,
		String registerTime) {
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.registerTime = registerTime;
	}

	/** full constructor */
	public UserIf(String userId, String userName, String userPwd,
		String registerTime, String registerImage) {
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.registerTime = registerTime;
		this.registerImage = registerImage;
	}

	// Property accessors

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterImage() {
		return this.registerImage;
	}

	public void setRegisterImage(String registerImage) {
		this.registerImage = registerImage;
	}

}