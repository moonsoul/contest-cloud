package com.app.entity;

import com.qq.base.common.Entity;

/**
 * Tbuser entity.
 * 
 * @author Sun ZhanQiang create time 2010-9-25
 */

public class Tbuser extends Entity implements java.io.Serializable {

	// Fields
	private String userid;
	private String username;
	private Long usertype;
	private String teacher;
	private String school;
	private String profession;
	private String contact;
	private String ticketnum;
	private String password;
	private String photo;
	private Long sex;
	private String idnumber;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	private String usercode;

	private String creatorid;
	private String createtime;

	private String jnbh;		//技能编号
	private String dsdm;		//地市代码
	private String dsmc;	//地市名称
	private String sxdm;		//市县代码
	private String sxmc;	//市县名称
	private String bmxh;		//报名序号
	private String authkey;
	
	// 主键
	protected String primaryKey = "userid";

	// Constructors // 主键

	// Constructors
	/** default constructor */
	public Tbuser() {
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public Tbuser(String userid) {
		this.userid = userid;
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

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getUsertype() {
		return usertype;
	}

	public void setUsertype(Long usertype) {
		this.usertype = usertype;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTicketnum() {
		return ticketnum;
	}

	public void setTicketnum(String ticketnum) {
		this.ticketnum = ticketnum;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}



	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getJnbh() {
		return jnbh;
	}

	public void setJnbh(String jnbh) {
		this.jnbh = jnbh;
	}

	public String getDsdm() {
		return dsdm;
	}

	public void setDsdm(String dsdm) {
		this.dsdm = dsdm;
	}

	public String getDsmc() {
		return dsmc;
	}

	public void setDsmc(String dsmc) {
		this.dsmc = dsmc;
	}

	public String getSxdm() {
		return sxdm;
	}

	public void setSxdm(String sxdm) {
		this.sxdm = sxdm;
	}

	public String getSxmc() {
		return sxmc;
	}

	public void setSxmc(String sxmc) {
		this.sxmc = sxmc;
	}

	public String getBmxh() {
		return bmxh;
	}

	public void setBmxh(String bmxh) {
		this.bmxh = bmxh;
	}

}
