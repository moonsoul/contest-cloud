<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@	page import="com.chinasage.common.apputil.ImportFilePath"%>
<%
	String downLoadFile = ImportFilePath.DOWN_LOAD_TEMPLATE_FILE_PATH + "templetGroup.xls";
	downLoadFile = downLoadFile.replace("\\", "/");
%>
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
   		
   		//判断文件是否为Excel2003格式
		function checkFileExcel2003(){
   		   var val = $("#imageFile").val();
   		   if(val != ""){
			   if(val.lastIndexOf('.xls') != -1){
			    	return true; 
			   }else{
			   		return false;
			   }
			}
			else{
				return false;
			} 
		}
	</script>
	
	<body>
			<html:form styleId="formID"
				action="/GroupManageAction.do?method=importGroup"
				enctype="multipart/form-data">
				<html:hidden property="groupid" />
				<table border="0" cellpadding="0" cellspacing="0" class="columns_conditiontable">
                  <tr>
                    <td class="rr">选择导入文件：</td>
                    <td class="ll"><html:file property="imageFile" styleId="imageFile"
														style="width:300px;" />
													<font color="red"> *</font></td>
                  </tr>
                  <tr>
                    <td height="30" colspan="2"  class="rr"><input type="button" onclick="importGroup();" name="button2" id="button2" value="保 存" />
                        <input type="button" onclick="downloadTemplateFile('templetGroup.xls', '<%=downLoadFile%>');" name="button3" id="button3" value="导出模板" />
                        <input type="button" name="button3" onclick="showFuncDetail('<c:out value='${groupManageForm.map.groupid}'/>','','');" id="button3" value="返 回" />
                        </td>
                  </tr>
                </table>
			</html:form>
	</body>
	
	<script type="text/javascript">
		//校验文件格式必须是Excel2003
		function importGroup(){
			var userfile=document.forms['formID'].imageFile.value;
	     	if(userfile==""){
	     		alert('请选择您要导入的机构文件！');
	     		return false;
	     	}else{
	     		if(userfile.lastIndexOf(".xls")<=0){
	     			alert('您上传的文件格式不正确，请上传EXCEL2003格式的文件！');
	     			return false;
	     		}
	     		else{
	     			if(userfile.lastIndexOf(".xlsx") != -1 || userfile.lastIndexOf(".xlsx") > 0){
	     				alert('您上传的文件格式不正确，请上传EXCEL2003格式的文件！');
	     				return false;
	     			}
	     		}
	     	}
	     		//$.ajax({
			    // 	url: "${ctx}/GroupManageAction.do?method=importGroup",
			    //	cache: false,
			    //	type:"post",
			    //	data:$("#formID").serialize(),
			    //	success: function(html){
			    //    $("#leftIframe").empty();
			   	//	$("#leftIframe").append(html);
			   //    }
			  // 	}); 
			     var url = "${ctx}/GroupManageAction.do?method=importGroup";
			     document.formID.submit();
		}
		
		function downloadTemplateFile(filename, filepath){
			window.location.href = "SearchEmaterialAction.do?method=downLoadLocationFile&filename=" + filename + "&filepath=" + filepath;
		}
	</script>
</html>