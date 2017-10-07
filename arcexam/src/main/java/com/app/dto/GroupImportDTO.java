package com.app.dto;

import com.qq.base.excel.config.ExcelBean;

public class GroupImportDTO extends ExcelBean {

	private String groupcode;
	private String parentcode;
	private String groupame;
	private String type;

	public String getGroupcode()
	{
		return groupcode;
	}

	public void setGroupcode(String groupcode)
	{
		this.groupcode = groupcode;
	}

	public String getParentcode()
	{
		return parentcode;
	}

	public void setParentcode(String parentcode)
	{
		this.parentcode = parentcode;
	}

	public String getGroupame()
	{
		return groupame;
	}

	public void setGroupame(String groupame)
	{
		this.groupame = groupame;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
