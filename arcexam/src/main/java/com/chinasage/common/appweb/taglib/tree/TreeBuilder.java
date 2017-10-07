package com.chinasage.common.appweb.taglib.tree;


import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <pre>
 * Description: 构造初始化树JS脚本
 * </pre>
 * 
 * @author Zhao Yuyang
 * 
 */
public class TreeBuilder {
	/**
	 * 构造树脚本
	 */
	public static String buildTree(String path, String autoSelect,
			String hasCheckBox, String method, String divid, String idfield,
			String namefield, String rootname, String bizRootID, String height)
	{
		StringBuffer result = new StringBuffer();
		if (StringUtils.isEmpty(idfield) && StringUtils.isEmpty(namefield)) {
			result.append(getTreeBody(autoSelect, hasCheckBox, method, divid,
					"left", rootname, bizRootID));
		}
		else {
			result.append(buildShowSelectTree(path, divid));
			result.append(buildSelectScript(divid, idfield, namefield,
					hasCheckBox, height));
			result.append(buildSelectTree(autoSelect, hasCheckBox, method,
					divid, rootname, bizRootID));
		}
		return result.toString();
	}

	/**
	 * 获取树主体
	 */
	public static String getTreeBody(String autoSelect, String hasCheckBox,
			String method, String treeid, String treeWay, String rootname,
			String bizRootID)
	{
		String urlFirstSplit = "?pid";

		if (StringUtils.contains(method, "?")) {
			urlFirstSplit = "&pid";
		}
		StringBuffer result = new StringBuffer();
		result.append("<div id='" + treeid + "'>").append(
				"<script type='text/javascript'>\n");
		result.append(" var tree=new JsTree('" + treeid + "');\n")
				.append(" var root=new JsNode('" + treeid + "_node_-1');\n")
				.append(" root.text='" + rootname + "';\n")
				.append(" root.name='" + rootname + "';\n")
				.append(" tree.setAutoSelect(" + autoSelect + ");\n")
				.append(" tree.hasCheckBox=" + hasCheckBox + ";\n")
				.append(" tree.action='" + method + "';\n")
				.append(" tree.setRoot(root);\n ")
				.append(" function loadleaf(action,id){\n ")
				.append("	if(checkHaveNodeById(id)){\n ")
				.append("	$.ajax({\n")
				// .append("	url:ctxpatch+action+'?pid='+id+'&treeWay='+'"+treeWay+"',\n")
				.append("	url:ctxpatch+action+'" + urlFirstSplit
						+ "='+id+'&treeWay='+'" + treeWay + "',\n")
				.append("	type:'POST',\n")
				.append("	success:function(data,textStatus){\n")
				// .append("	execScript(data,'javascript');} });\n")
				.append("	eval(data,'javascript');} });\n")
				.append("	}\n")
				.append("	}\n")
				// .append(" loadleaf('"+method+"','" + treeid+"_node_"+bizRootID+"');\n")//此行控制是否在进入页面时加载

				.append(" loadleaf('" + method + "','" + treeid
						+ "_node_-1');\n")// 此行控制是否在进入页面时加载
				.append("</script>\n").append("</div>");
		return result.toString();
	}

	private static String buildShowSelectTree(String path, String treeid)
	{
		StringBuffer result = new StringBuffer();
		result.append("<img src=\""
				+ path
				+ "\" onclick=\"showSelectTree"
				+ treeid
				+ "();\"  alt=\"请选择\" style=\"cursor:hand\" width=\"34\" height=\"18\" >");
		return result.toString();
	}

	/**
	 * 获取树所用的脚本
	 */
	private static String buildSelectScript(String treeid, String idfield,
			String namefield, String hasCheckBox, String height)
	{

		StringBuffer result = new StringBuffer();
		result.append("<script type=\"text/javascript\">");
		result.append("function showSelectTree" + treeid + "() {");
		result.append("if (document.all._SelectTreeDiv" + treeid
				+ ".style.display == \"none\") {");
		result.append("var iWidth = 300;");
		result.append("if((document.body.clientWidth-event.x)<iWidth){");
		result.append("document.all._SelectTreeDiv" + treeid
				+ ".style.left = document.body.clientWidth-iWidth-10;");
		result.append("}else{");
		result.append("document.all._SelectTreeDiv" + treeid
				+ ".style.left=event.x ;");
		result.append("}");
		// modify by Steven 2011-03-02 高度调整
		result.append("document.all._SelectTreeDiv" + treeid
				+ ".style.top = event.clientY +" + Integer.parseInt(height)
				+ ";");
		result.append(" document.all._SelectTreeDiv" + treeid
				+ ".style.display = \"block\";");
		result.append(" document.getElementById('iframe1').style.top=document.all._SelectTreeDiv"
				+ treeid + ".style.top;");
		result.append(" document.getElementById('iframe1').style.left=document.all._SelectTreeDiv"
				+ treeid + ".style.left;");
		result.append(" document.getElementById('iframe1').style.width=document.all._SelectTreeDiv"
				+ treeid + ".style.width;");
		result.append(" document.getElementById('iframe1').style.height=document.all._SelectTreeDiv"
				+ treeid + ".style.height;");
		result.append(" document.getElementById('iframe1').style.display = 'block';");
		result.append("}else{");
		result.append("document.all._SelectTreeDiv" + treeid
				+ ".style.display = \"none\";");
		result.append(" document.getElementById('iframe1').style.display = 'none';");
		result.append("}");
		result.append("}");

		result.append(" function _TreeChecked" + treeid + "() {");
		result.append(" var options=document.getElementById('" + idfield
				+ "');");
		result.append(" var ids=getTreeCheckedByDivId('" + treeid
				+ "').split(',');");
		result.append(" var names=getTreeCheckedNameByDivId('" + treeid
				+ "').split(',');");
		result.append(" for(var i=0;i<ids.length-1;i++){");
		result.append(" var option = document.createElement(\"OPTION\");");
		result.append(" option.text = names[i];");
		result.append(" option.value = ids[i];	");
		result.append(" if(hasOption(options,ids[i])){");
		result.append(" options.add(option);");
		result.append(" }");
		result.append(" }");
		result.append(" document.all._SelectTreeDiv" + treeid
				+ ".style.display = \"none\";");
		result.append(" document.getElementById('iframe1').style.display = 'none';");
		result.append(" }");

		result.append(" function hasOption(options,option) {");
		result.append(" for(var i=0;i<options.length;i++){");
		result.append(" if(options[i].value==option){");
		result.append(" return false;");
		result.append(" }");
		result.append(" }");
		result.append(" return true;");
		result.append(" }");

		result.append(" function _selectTree" + treeid
				+ "(idfield,namefield) {");

		// 树形根节点做为查询条件时不可选中 Add By Gang 2011-4-13
		result.append("if(idfield!=9001){");
		if (!"true".equals(hasCheckBox)) {
			result.append(" var idfields=document.getElementsByName(\""
					+ idfield + "\");");
			result.append(" for(var i=0;i<idfields.length;i++){idfields[i].value=idfield;}");
			result.append(" var namefields=document.getElementsByName(\""
					+ namefield + "\");");
			result.append(" for(var i=0;i<namefields.length;i++){namefields[i].value=namefield;}");
			result.append(" document.all._SelectTreeDiv" + treeid
					+ ".style.display = \"none\";");
			result.append(" document.getElementById('iframe1').style.display = 'none';");
		}
		result.append(" }");
		result.append(" }");
		result.append("</script>");
		return result.toString();
	}

	/**
	 * 获取弹出式树
	 */
	private static String buildSelectTree(String autoSelect,
			String hasCheckBox, String method, String treeid, String rootname,
			String bizRootID)
	{

		StringBuffer result = new StringBuffer();
		result.append("<div id=\"_SelectTreeDiv"
				+ treeid
				+ "\" style=\"width:298px;height:auto;TOP:0px;left:0px;display:none;POSITION: absolute;Z-INDEX: 12000; float:left;background:#fff;border:1px solid #99bbe8;\">");
		result.append("<div class=\"Popping\">");
		result.append("<div class=\"Popping_1\">");
		result.append("<div class=\"Popping_1_1\">");
		result.append("<div class=\"Popping_1_1_1\">");
		result.append("<a onclick='showSelectTree"
				+ treeid
				+ "()'style='font-size:12px;cursor:hand;'><font style='FONT-FAMILY: Webdings' >r</font></a>");
		result.append("</div>");
		result.append("</div>");
		result.append("<div class=\"Popping_1_2\">");
		result.append("<div style=\"margin-left:0px;\" class=\"Popping_1_2_1\">");
		/** 绑定树主体 ******************************************************************* */
		result.append(getTreeBody(autoSelect, hasCheckBox, method, treeid,
				"right", rootname, bizRootID));
		/** javascript部分 ******************************************************************* */
		result.append("<iframe src=\"javascript:false\" style=\"position:absolute; visibility:inherit; top:5px; left:5px; width:295px; height:200px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\">");
		result.append("</iframe>");
		result.append("</div>");
		result.append("</div>");
		if ("true".equals(hasCheckBox)) {
			result.append("<div class=\"Popping_2\">");
			result.append("<div class=\"Popping_2_1\">");
			result.append("<input type='button' name=submit__checkbox' value='请选择' class='textbox_3' onclick='_TreeChecked"
					+ treeid + "()'>");
			result.append("</div>");
			result.append("</div>");
		}
		result.append("</div>");
		result.append("</div>");
		result.append("</div>");

		result.append("<iframe id='iframe1'; src='' scroll=none style='width:298px; height:auto; top:0px; left:0px; position:absolute; z-index:200; display:none;'></iframe>");
		return result.toString();
	}

}
