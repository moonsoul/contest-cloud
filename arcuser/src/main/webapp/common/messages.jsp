<%@ include file="/common/taglibs.jsp" %>
<%-- Error Messages --%>
<logic:messagesPresent>
	<div class="error">
		<html:messages id="error">
			<script type="text/javascript">
			    alert('<c:out value="${error}"/>');
		    </script>
		</html:messages>
	</div>
</logic:messagesPresent>
<%-- Success Messages --%>
<logic:messagesPresent message="true">
	<div class="message">
		<html:messages id="message" message="true">
			<script type="text/javascript">
			    alert('<c:out value="${message}"/>');
		    </script>
		</html:messages>
	</div>
</logic:messagesPresent>