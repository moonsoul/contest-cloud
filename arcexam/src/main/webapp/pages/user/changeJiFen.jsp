<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent">
	<form styleId="userManageForm" method="post"
		action="${ctx}/user/updateUserIntegration"
		styleClass="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" id="userid" name="userid" value="${param.userid}"/>
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>当前积分</label>${integration}
				
			</div>
			<div class="unit">
				<label>变更积分值</label>
				<input type="text" name="integration" size="30" class="digits"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>