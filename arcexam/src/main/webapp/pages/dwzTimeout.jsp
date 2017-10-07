<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <body>
    <%
        out.println("<script>alert('您的会话已过期，请重新登陆!');</script>");
		out.println("<script>top.location.href='"+request.getContextPath()+"'</script>");
	%>
  </body>
</html>
