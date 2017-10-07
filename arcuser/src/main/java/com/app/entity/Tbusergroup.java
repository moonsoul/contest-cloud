package com.app.entity;

import com.qq.base.common.Entity;

/**
 * Tbuser entity.
 * 
 * @author Sun ZhanQiang create time 2010-9-25
 */

public class Tbusergroup extends Entity implements java.io.Serializable {

	// Fields
	private String ugid;
	private String userid;
	private String groupid;
	private String creatorid;
	private String createtime;

	// 主键
	protected String primaryKey = "ugid";

	// Constructors // 主键

	// Constructors
	/** default constructor */
	public Tbusergroup() {
	}

	public Tbusergroup(String ugid) {
		this.ugid = ugid;
	}

	public String getUgid() {
		return ugid;
	}

	public void setUgid(String ugid) {
		this.ugid = ugid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
}
