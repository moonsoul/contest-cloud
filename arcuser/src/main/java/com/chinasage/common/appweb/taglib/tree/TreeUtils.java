package com.chinasage.common.appweb.taglib.tree;

import java.util.List;

/**
 * 
 * <pre>
 * Description: 构造树形脚本帮助类
 * </pre>
 * 
 * @author Zhao Yuyang
 * 
 */
public final class TreeUtils {

	public final static String	FUNCTION_JAVASCRIPT_HREF	= "#";

	public static String buildTreeNodeJsScript(List list, String function,
			String pidString, String treeid)
	{

		String Jsfunction = "";
		StringBuffer temp = new StringBuffer("");
		temp.append("var node=getNodeById('" + pidString + "');\n");

		for (int i = 0; i < list.size(); i++) {

			TreeNode node = (TreeNode) list.get(i);

			Jsfunction = function;

			temp.append("node.add(new JsNode('" + treeid + "_node_"
					+ node.getNodeId() + "','" + Jsfunction + "','"
					+ node.getNodePid() + "','" + node.getNodeName() + "','"
					+ node.hasLeafNode() + "'));\n");
		}

		/**
		 * 输出信息
		 */

		return temp.toString();
	}
}
