package com.app.entity;

import com.qq.base.common.Entity;

/**
 * Tbgroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Tbgroup extends Entity implements java.io.Serializable {

	// Fields
	private String groupid;
	private String groupcode;
	private String parentid;
	private String groupname;
	private Long type;
	private Long location;
	private String address;
	private String postalcode;
	private String phone;
	private String fax;
	private String email;
	private String linkman;
	private String remark;
	private Long deletestatus;
	private String creatorid;
	private String createtime;

	// 主键
	protected String primaryKey	= "groupid";

	// Constructors
	/** default constructor */
	public Tbgroup() {
	}

	/** minimal constructor */
	public Tbgroup(String groupcode, String parantid, String groupname,
				   Long type, Long location, Long deletestatus, String creatorid,
				   String createtime) {
		this.groupcode = groupcode;
		this.parentid = parantid;
		this.groupname = groupname;
		this.type = type;
		this.location = location;
		this.deletestatus = deletestatus;
		this.creatorid = creatorid;
		this.createtime = createtime;
	}

	/** full constructor */
	public Tbgroup(String groupcode, String parantid, String groupname,
				   Long type, Long location, String address, String postalcode,
				   String phone, String fax, String email, String linkman,
				   String remark, Long deletestatus, String creatorid,
				   String createtime) {
		this.groupcode = groupcode;
		this.parentid = parantid;
		this.groupname = groupname;
		this.type = type;
		this.location = location;
		this.address = address;
		this.postalcode = postalcode;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.linkman = linkman;
		this.remark = remark;
		this.deletestatus = deletestatus;
		this.creatorid = creatorid;
		this.createtime = createtime;
	}

	public Tbgroup(String groupid) {
		this.groupid = groupid;
	}

	// Property accessors

	public String getGroupid()
	{
		return this.groupid;
	}

	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}

	public String getGroupcode()
	{
		return this.groupcode;
	}

	public void setGroupcode(String groupcode)
	{
		this.groupcode = groupcode;
	}

	public String getParentid()
	{
		return this.parentid;
	}

	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}

	public String getGroupname()
	{
		return this.groupname;
	}

	public void setGroupname(String groupname)
	{
		this.groupname = groupname;
	}

	public Long getType()
	{
		return this.type;
	}

	public void setType(Long type)
	{
		this.type = type;
	}

	public Long getLocation()
	{
		return this.location;
	}

	public void setLocation(Long location)
	{
		this.location = location;
	}

	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPostalcode()
	{
		return this.postalcode;
	}

	public void setPostalcode(String postalcode)
	{
		this.postalcode = postalcode;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getFax()
	{
		return this.fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getLinkman()
	{
		return this.linkman;
	}

	public void setLinkman(String linkman)
	{
		this.linkman = linkman;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Long getDeletestatus()
	{
		return this.deletestatus;
	}

	public void setDeletestatus(Long deletestatus)
	{
		this.deletestatus = deletestatus;
	}

	public String getCreatorid()
	{
		return this.creatorid;
	}

	public void setCreatorid(String creatorid)
	{
		this.creatorid = creatorid;
	}

	public String getCreatetime()
	{
		return this.createtime;
	}

	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}

	public String getPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey)
	{
		this.primaryKey = primaryKey;
	}

}
