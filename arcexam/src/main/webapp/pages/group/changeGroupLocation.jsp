<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中望建筑工程识图能力实训评价软件</title>
	</head>
	
	<script type="text/javascript">
		// 页面表单校验的js脚本
		$(document).ready(function(){ 
			$("#formID").validationEngine({validateAttribute:"myvalidator"}); 
   		})
		 
	</script>
	
	<body>
	<html:form styleId="formID" action="/GroupManageAction.do?method=changeGroupLocation" method="post">
		<html:hidden property="groupid"/>
		<html:hidden property="location"/>
		<html:hidden property="parentid"/>
				<table border="0" cellpadding="0" cellspacing="0" class="columns_conditiontable">
                  <tr>
                    <td class="rr">机构编号：</td>
                    <td class="ll"><c:out value="${groupManageForm.map.groupcode}"></c:out></td>
                  </tr>
                  <tr>
                    <td class="rr">机构名称：</td>
                    <td class="ll"><c:out value="${groupManageForm.map.groupname}"></c:out></td>
                  </tr>
                  <tr>
                    <td class="rr">当前排序：</td>
                    <td class="ll"><c:out value="${groupManageForm.map.location}"></c:out></td>
                  </tr>
                  <tr>
                    <td class="rr">移动位置：</td>
                    <td class="ll"><html:text property="targetIndex" styleId="targetIndex" 
													myvalidator="validate[required,length[0,5],custom[positiveinteger]]" 
													maxlength="5"/>
													<font color="red"> *</font></td>
                  </tr>
				  <tr>
                    <td height="30" colspan="2"  class="rr">
                    	<input type="button" onclick="_updateGroupLocation();" name="button2" id="button2" value="保 存" />
                        <input type="reset" name="button3" id="button3" value="重 置" />
                        <input type="button" name="button3" onclick="showFuncDetail('<c:out value='${groupManageForm.map.groupid}'/>','','');" id="button3" value="返 回" />
                        </td>
                  </tr>					
			</table>
		</html:form>
	</body>
</html>
