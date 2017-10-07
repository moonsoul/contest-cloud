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
   		
   		//判断电子邮件（非必填，但如果不为空，则进行校验）
   		function checkEmail(){ 
   		   var emailval = $("#email").val();
		   if(emailval ==""){ 
		    	return true; 
		   }else{
		    	var le = /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/;
		    	if(le.test(emailval)){
		    		return true;
		    	}
		    	else{
		    		return false;
		    	}
		   } 
		 }
		 
		 //判断邮政编码（非必填，但如果不为空，则进行校验）
		 function checkPostalCode(){
		 	   var val = $("#postalcode").val();
			   if(val ==""){ 
			    	return true; 
			   }else{
			    	var le = /^[1-9]\d{5}$/;
			    	if(le.test(val)){
			    		return true;
			    	}
			    	else{
			    		return false;
			    	}
			   } 
		 }
		 
		 //判断电话号码（非必填，但如果不为空，则进行校验）
		 function checkPhone(){
		 	   var val = $("#phone").val();
			   if(val ==""){ 
			    	return true; 
			   }else{
			    	var le = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
			    	if(le.test(val)){
			    		return true;
			    	}
			    	else{
			    		return false;
			    	}
			   } 
		 }
		 
		 //判断传真（非必填，但如果不为空，则进行校验）
		 function checkFax(){
		 	   var val = $("#fax").val();
			   if(val ==""){ 
			    	return true; 
			   }else{
			    	var le = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
			    	if(le.test(val)){
			    		return true;
			    	}
			    	else{
			    		return false;
			    	}
			   } 
		 }
	</script>
	
	<body>
	<html:form styleId="formID" action="/GroupManageAction.do?method=updateGroup" method="post">
		<html:hidden property="groupid"/>
		<html:hidden property="creatorid"/>
		<html:hidden property="createtime"/>
		<html:hidden property="deletestatus"/>
		<html:hidden property="parentid"/>
				<table border="0" cellpadding="0" cellspacing="0" class="columns_conditiontable">
                  <tr>
                    <td class="rr">机构编号：</td>
                    <td class="ll"><c:out value="${groupManageForm.map.groupcode}" /></td>
                    
                    <td class="rr">机构名称：</td>
                    <td class="ll"><html:text property="groupname" styleId="groupname" 
													myvalidator="validate[required,length[0,25]]" 
													maxlength="25"/>
												<font color="red"> *</font></td>
                  </tr>
                  <tr>
                    <td class="rr">机构类型：</td>
                    <td class="ll"><html:radio property="type" styleId="type" value="1">院系</html:radio>
												<html:radio property="type" styleId="type" value="2">班级</html:radio>
												<font color="red"> *</font></td>
                    <td class="rr">电话：</td>
                    <td class="ll"><html:text property="phone" styleId="phone" 
													myvalidator="validate[length[0,20],funcCall[checkPhone]]" 
													maxlength="20"/></td>
                  </tr>
                  <tr>
                    <td class="rr">邮政编码：</td>
                    <td class="ll"><html:text property="postalcode" styleId="postalcode" 
													myvalidator="validate[length[0,20],funcCall[checkPostalCode]]" 
													maxlength="20"/></td>
                    <td class="rr">通讯地址：</td>
                    <td class="ll"><html:text property="address" styleId="address" 
													myvalidator="validate[length[0,60]]" 
													maxlength="60"/></td>
                  </tr>
                  <tr>
                    <td class="rr">传真：</td>
                    <td class="ll"><html:text property="fax" styleId="fax" 
													myvalidator="validate[length[0,20],funcCall[checkFax]]" 
													maxlength="20"/></td>
                    <td class="rr">电子邮件：</td>
                    <td class="ll"><html:text property="email" styleId="email" 
													myvalidator="validate[length[0,50],funcCall[checkEmail]]" 
													maxlength="50"/></td>
                  </tr>
                  <tr>
                  	<td class="rr">联系人：</td>
                    <td class="ll"><html:text property="linkman" styleId="linkman" 
													myvalidator="validate[length[0,10]]" 
													maxlength="10"/></td>
                    <td class="rr">备注：</td>
                    <td class="ll"><html:textarea property="remark" styleId="remark" 
													myvalidator="validate[length[0,512]]" 
													cols="30" rows="5"></html:textarea>
                    </td>
                  </tr>
                  <tr>
                    <td height="30" colspan="4"  class="rr"><input type="button" onclick="_updateGroup();" name="button2" id="button2" value="保 存" />
                        <input type="reset" name="button3" id="button3" value="重 置" />
                        <input type="button" name="button3" onclick="showFuncDetail('<c:out value='${groupManageForm.map.groupid}'/>','','');" id="button3" value="返 回" />
                        </td>
                  </tr>
                </table>
		
		</html:form>
	</body>
</html>
