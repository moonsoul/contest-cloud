<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%System.out.println(request.getAttribute("statusCode").toString()); 
%>
{
	"statusCode":"${statusCode}",
	"message":"${message}",
	"navTabId":"${navTabId}",
	"rel":"${rel}",
	"callbackType":"${callbackType}",
	"forwardUrl":"${forwardUrl}"
}
