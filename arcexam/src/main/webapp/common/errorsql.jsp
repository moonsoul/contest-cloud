<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<%
	String em="\""+(String)request.getAttribute("error")+"\"";
 %>
<head>
<title>Error message:</title>
<script language="javascript">
	alert('输入字段中有错误,请修改！\n提示：不可包含"'+<%=em%>+'"，如必须使用，请用中文全角字符代替。');
    window.history.go(-1);
</script>
</head>
<body>
<center>
<table width="100%" height="80%" border="0" align="center" cellpadding="0" cellspacing="0" style="background-repeat:no-repeat; vertical-align:middle" background="interface/scheme1/platform/images/error_bg.gif">
  <tr><td>

      <table width="80%" height="50%" align="center">
         <tr height="32"></tr>
         <tr>
            <td height="32" colspan="6" align="center" nowrap>
            <br><b>"输入字段中有错误,请修改！<br/>提示：不可包含<%=em%></b></td>
         </tr>
      </table>
     
	  <table width="80%" height="20%" align="right">
        <tr>
          <td align="center">
              <button name="button1" onClick="goback()">返回</button>
          </td>
        </tr>
     </table>
		 
  </td></tr>
</table>
</center>
</body>
</html>