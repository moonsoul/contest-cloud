package com.chinasage.common.appweb.taglib.tree;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 
 * <pre>
 * Description: 树JS脚本初始化操作
 * </pre>
 * 
 * @author Zhao Yuyang
 * 
 */
public class InitTree {

	private final static String	DEFAULT_FUNCTION	= "javascript:showFuncDetail";

	private final static String	INNER_FUNCTION		= "javascript:_selectTree";

	private final static String	TREE_WAY			= "treeWay";

	private String				pidString			= null;

	private List				nodeList			= null;

	private String				treeWay				= null;

	public InitTree(String pidString, String treeWay) {

		this.pidString = pidString;

		this.treeWay = treeWay;

	}

	public List getNodeList()
	{
		return nodeList;
	}

	public void setNodeList(List nodeList)
	{
		this.nodeList = nodeList;
	}

	public String getPidString()
	{
		return pidString;
	}

	public void setPidString(String pidString)
	{
		this.pidString = pidString;
	}

	public String getTreeWay()
	{
		return treeWay;
	}

	public void setTreeWay(String treeWay)
	{
		this.treeWay = treeWay;
	}

	/**
	 * 得到获取父ID
	 * 
	 * @return
	 */
	public String getPid()
	{

		String[] splitPidAndTreeid = pidString.split("_node_");

		return splitPidAndTreeid[1];
	}

	public String getTreeId()
	{

		String[] splitPidAndTreeid = pidString.split("_node_");

		return splitPidAndTreeid[0];
	}

	/**
	 * 取得构造树节点的JSSCRIPT代码
	 * 
	 * @return
	 */
	public String getTreeNodeJsScript()
	{

		String treeid = getTreeId();

		String function = (StringUtils.isNotEmpty(treeWay) && treeWay
				.equals("right")) ? (INNER_FUNCTION + treeid)
				: DEFAULT_FUNCTION;

		String scriptCode = TreeUtils.buildTreeNodeJsScript(nodeList, function,
				pidString, treeid);

		return scriptCode;
	}
}
