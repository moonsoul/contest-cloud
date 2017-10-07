<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>中望建筑工程识图能力实训评价软件</title>
		<%@ include file="/common/meta.jsp"%>
</head>
<html:base />

	<body>
		<chinasage:pagelocation/>
		<html:form action="/UserManageAction.do?method=gotoImportUsers">
			<html:hidden property="searchgroupid"/>
			
			<div id="frame_right">
				<table width="100%" align="center" cellpadding="0" cellspacing="0"
					class="sider_content">
					<tr>
						<td align="left" valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td colspan="2" height="24"
										background="${ctx}/themes/default/images/conditions_bg.jpg">
										<img src="${ctx}/themes/default/images/btn2.png" width="16"
											height="16" />
										&nbsp;人员批量导入成功提示信息
									</td>
								</tr>

								<tr>
									<td colspan="2">
										<table width="98%" border="0" align="left" cellpadding="0"
											cellspacing="3" class="table_query">
											<tr>
												<td height="30" align="center">
													数据导入成功！
												</td>
											</tr>
											
											<tr>
												<td align="center" height="40">
													<input type="button" name="button2" id="button2" onclick="javascript:window.location='UserManageAction.do?method=listUserInfo&searchgroupid==<c:out value='${userManageForm.map.searchgroupid}'/>'" value="返 回"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</body>
</html>
