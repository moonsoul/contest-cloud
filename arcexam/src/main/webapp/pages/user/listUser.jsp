<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>中望建筑工程识图能力实训评价软件</title>
		<%@ include file="/common/meta.jsp"%>
		
		<script type="text/javascript">	 
			function showFuncDetail(id,name,parentid){
				document.getElementById("leftIframe").src = "${ctx}/user/listUserInfo?searchgroupid=" + id;
			}
		</script>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="1%" align="left" valign="top" class="sider_content">
					<div style="background: url(${ctx}/themes/default/images/assort_bg.jpg) repeat-x; width:187px; height:27px; text-align:center; line-height:27px; font-weight:bold;">组织机构树</div>
					<div style="width:187px; white-space:nowrap; height:591px; margin:0 auto; text-align:left; overflow:auto;">
						<chinasage:tree treeid="resourceTree" method="/group/loadGroupTree"
							rootname="组织机构树"></chinasage:tree>
					</div>
				</td>
				
				<td width="99%">
					<iframe id="leftIframe" frameborder=0 width=100% height=615 marginheight=0 marginwidth=0 src="user/listUserInfo?searchgroupid=9001"> </iframe>
				</td>
			</tr>
		</table>
	</body>
</html>
