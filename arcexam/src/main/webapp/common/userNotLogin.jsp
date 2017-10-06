<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <%
        out.println("<script>alert('请登陆!');</script>");
		out.println("<script>top.location.href='"+request.getContextPath()+"'</script>");
	%>
  </body>
</html>
