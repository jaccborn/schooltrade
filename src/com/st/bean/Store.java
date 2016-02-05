package com.st.bean;

/**
 * Store entity. @author MyEclipse Persistence Tools
 */

public class Store implements java.io.Serializable{

	// Fields

	private Integer stid;
	private String stname;
	private String stregistertime;
	private String sttelphone;
	private String stowner;
	private String stplace;

	// Constructors

	/** default constructor */
	public Store() {}

	/** full constructor */
	public Store(String stname, String stregistertime, String sttelphone,
		String stowner, String stplace) {
		this.stname = stname;
		this.stregistertime = stregistertime;
		this.sttelphone = sttelphone;
		this.stowner = stowner;
		this.stplace = stplace;
	}

	// Property accessors

	public Integer getStid() {
		return this.stid;
	}

	public void setStid(Integer stid) {
		this.stid = stid;
	}

	public String getStname() {
		return this.stname;
	}

	public void setStname(String stname) {
		this.stname = stname;
	}

	public String getStregistertime() {
		return this.stregistertime;
	}

	public void setStregistertime(String stregistertime) {
		this.stregistertime = stregistertime;
	}

	public String getSttelphone() {
		return this.sttelphone;
	}

	public void setSttelphone(String sttelphone) {
		this.sttelphone = sttelphone;
	}

	public String getStowner() {
		return this.stowner;
	}

	public void setStowner(String stowner) {
		this.stowner = stowner;
	}

	public String getStplace() {
		return this.stplace;
	}

	public void setStplace(String stplace) {
		this.stplace = stplace;
	}

}