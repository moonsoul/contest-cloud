<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>中望建筑工程识图能力实训评价软件</title>
		<%@ include file="/common/meta.jsp"%>
	</head>
	<html:base/> 
	
	<body>
		<chinasage:pagelocation/>
		<html:form action="/GroupManageAction.do?method=gotoImportGroups">
			<html:hidden property="groupid"/>
		</html:form>
		
		<ec:mytable items="errorData" var="excelVar" rowsDisplayed="10" action="${ctx}/GroupManageAction.do?method=gotoImportGroups" 
			locale="${locale}" sortable="false" filterable="false" showStatusBar="false" width="100%">
			<ec:row>
				<ec:mycolumn property="null" title="行号" width="5%">
					${excelVar.rowNum}
				</ec:mycolumn>
				<ec:mycolumn property="errorMsg" title="错误信息" width="95%"></ec:mycolumn>
			</ec:row>
			<ec:extendrow>
				<tr>
					<td align="right" colspan="2" height="40">
						<input type="button" name="button2" id="button2" onclick="javascript:window.location='GroupManageAction.do?method=toGroupList&repeat=true&groupid=<c:out value='${groupManageForm.map.groupid}'/>'" value="重新导入"/>
						<input type="button" name="button2" id="button2" onclick="javascript:window.location='GroupManageAction.do?method=toGroupList&repeat=false&groupid=<c:out value='${groupManageForm.map.groupid}'/>'" value="返 回"/>
					</td>
				</tr>
			</ec:extendrow>
		</ec:mytable>

	</body>
</html>
