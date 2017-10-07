package com.chinasage.common.appweb.taglib.tree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Zhao Yuyang
 * 
 */

public class TreeTag extends TagSupport {
	private static final long	serialVersionUID	= 1L;
	private String				autoSelect			= "false";	// 自动选择下级节点
	private String				hasCheckBox			= "false";	// 是否多选
	private String				method;							// 获取树节点的action
	private String				treeid;							// 树id
	private String				idfield;						// 调用页id的标识id
	private String				namefield;						// 调用页name的标识id
	private String				rootname;						// 虚根名字
	private String				path;							// 图标的图片位置
	private String				bizRootID;						// 首次加载业务数据的根节点

	private String				height				= "0";		// add by Steven 2011-03-02 调整高度的属性

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getAutoSelect()
	{
		return autoSelect;
	}

	public void setAutoSelect(String autoSelect)
	{
		this.autoSelect = autoSelect;
	}

	public String getTreeid()
	{
		return treeid;
	}

	public void setTreeid(String treeid)
	{
		this.treeid = treeid;
	}

	public String getHasCheckBox()
	{
		return hasCheckBox;
	}

	public void setHasCheckBox(String hasCheckBox)
	{
		this.hasCheckBox = hasCheckBox;
	}

	public String getMethod()
	{

		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public int doStartTag() throws JspException
	{
		try {
			JspWriter out = pageContext.getOut();
			String ctx = ((HttpServletRequest) pageContext.getRequest())
					.getContextPath();
			if (path == null | "".equals(path)) {
				path = "/scripts/common/treeIcon/choice.gif";
			}
			if (path.startsWith(ctx)) {
				path = "/scripts/common/treeIcon/choice.gif";
			}
			path = ctx + path;
			String treeContent = TreeBuilder.buildTree(path, autoSelect,
					hasCheckBox, method, treeid, idfield, namefield, rootname,
					bizRootID, height);
			out.print(treeContent);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return SKIP_BODY;

	}

	public String getNamefield()
	{
		return namefield;
	}

	public void setNamefield(String namefield)
	{
		this.namefield = namefield;
	}

	public String getIdfield()
	{
		return idfield;
	}

	public void setIdfield(String idfield)
	{
		this.idfield = idfield;
	}

	public String getRootname()
	{
		return rootname;
	}

	public void setRootname(String rootname)
	{
		this.rootname = rootname;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getBizRootID()
	{
		return bizRootID;
	}

	public void setBizRootID(String bizRootID)
	{
		this.bizRootID = bizRootID;
	}
}
