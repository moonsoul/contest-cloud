package com.app.entity;

import com.qq.base.common.Entity;

public class Tbuserlog extends Entity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String logid				= null;		// 日志ID VARCHAR2(32) 非空 Primary
	private String operatorid			= null;		// 操作人id VARCHAR2(32) 非空
	private String logintime			= null;		// 登录时间 CHAR(19) 非空
	private String logouttime			= null;		// 退出时间 CHAR(19)
	private String ip					= null;		// 用户登录IP CHAR(15) 非空
	private String content				= null;		// 操作内容 VARCHAR2(128) 空
	private String flag				= null;		// 操作成功标识 NUMBER(1) 空 操作成功标识：1：成功2：失败
	private String result				= null;		// 操作结果 VARCHAR2(1024) 空 操作失败时需要说明原因
	private Long systype				= null;		// 所属平台 NUMBER(1) 空 1为教师平台/2为管理员平台/3为学员管理平台

	// 主键
	protected String primaryKey			= "logid";

	public Tbuserlog() {

	}

	public Tbuserlog(String logid) {
		this.logid = logid;
	}

	public String getLogid()
	{
		return logid;
	}

	public void setLogid(String logid)
	{
		this.logid = logid;
	}

	public String getOperatorid()
	{
		return operatorid;
	}

	public void setOperatorid(String operatorid)
	{
		this.operatorid = operatorid;
	}

	public String getLogintime()
	{
		return logintime;
	}

	public void setLogintime(String logintime)
	{
		this.logintime = logintime;
	}

	public String getLogouttime()
	{
		return logouttime;
	}

	public void setLogouttime(String logouttime)
	{
		this.logouttime = logouttime;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public Long getSystype()
	{
		return systype;
	}

	public void setSystype(Long systype)
	{
		this.systype = systype;
	}


}
