package com.app.dto;

import com.chinasage.common.appweb.taglib.tree.TreeNode;

/**
 * 机构树实体
 * 
 * @author Lian
 * @create on 2010-09-28
 */
public class GroupTreeDTO implements TreeNode {

	private String groupid;

	private String groupname;

	private Long childnum;

	private String parentid	= "-5";

	public String getGroupid()
	{
		return groupid;
	}

	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}

	public String getParentid()
	{
		return parentid;
	}

	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}

	public String getGroupname()
	{
		return groupname;
	}

	public void setGroupname(String groupname)
	{
		this.groupname = groupname;
	}

	public Long getChildnum()
	{
		return childnum;
	}

	public void setChildnum(Long childnum)
	{
		this.childnum = childnum;
	}

	public String getNodeId()
	{
		return getGroupid();
	}

	public String getNodeName()
	{
		return getGroupname();
	}

	public String getNodePid()
	{

		return getParentid();
	}

	public boolean hasLeafNode()
	{
		if (null != childnum && childnum.intValue() > 0) return true;
		else return false;
	}

}
