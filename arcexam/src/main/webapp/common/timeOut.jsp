<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	var s = encodeURI(encodeURI('${message}'));
	window.top.location = '${ctx}/stu/login?message='+s;
</script>

