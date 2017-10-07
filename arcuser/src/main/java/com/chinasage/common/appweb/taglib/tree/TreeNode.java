package com.chinasage.common.appweb.taglib.tree;

/**
 * 
 * <pre>
 * Description: 递归接口类，所有递归操作的实体类需要实现这个接口
 * </pre>
 * 
 * @author Zhao Yuyang
 * 
 */
public interface TreeNode {

	/**
	 * 获取节点的ID
	 */
	public String getNodeId();

	/**
	 * 获取节点的显示内容
	 */
	public String getNodeName();

	/**
	 * 获取节点的父ID
	 */
	public String getNodePid();

	/**
	 * 获取是否有子节点
	 */
	public boolean hasLeafNode();
}
