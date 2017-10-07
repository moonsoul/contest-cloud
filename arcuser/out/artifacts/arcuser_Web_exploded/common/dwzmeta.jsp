<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>

<link href="<%=request.getContextPath()%>/dwz146/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/dwz146/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/dwz146/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=request.getContextPath()%>/dwz146/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="<%=request.getContextPath()%>/dwz146/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="<%=request.getContextPath()%>/dwz146/js/speedup.js" type="text/javascript"></script>
<![endif]-->


<script src="<%=request.getContextPath()%>/dwz146/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<%-- 自定义JS 
<script src="<%=request.getContextPath()%>/dwz146/js/customer.js" type="text/javascript"></script>
--%>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="<%=request.getContextPath()%>/dwz146/chart/raphael.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwz146/chart/g.raphael.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwz146/chart/g.bar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwz146/chart/g.line.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwz146/chart/g.pie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwz146/chart/g.dot.js"></script>

<script src="<%=request.getContextPath()%>/dwz146/js/dwz.core.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.util.date.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.drag.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.tree.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.accordion.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.ui.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.theme.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.tab.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.resize.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.dialog.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.stable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.ajax.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.pagination.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.database.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.effects.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.panel.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.history.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.combox.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="<%=request.getContextPath()%>/dwz146/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/dwz146/ztree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/ztree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/dwz146/ztree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/dwz146/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/themes/zw20/js/layer191/layer/layer.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common/highcharts.js"></script>

