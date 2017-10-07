<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.lang.ClassLoader,java.io.InputStream,java.util.Properties"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中望建筑工程识图能力实训评价软件</title>
<%@ include file="/common/dwzmeta.jsp"%>
<script type="text/javascript">
	$(function() {
		DWZ.init("${ctx}/dwz146/dwz.frag.xml", {
			loginUrl : "${ctx}/goTimeOutLoginPage",
			loginTitle : "登录",
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			},
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			},
			debug : false,
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "${ctx}/dwz146/themes"
				});
			}
		});
	});
	
	
</script>

</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#" style="width: 500px;"></a>
				<ul class="nav">
					<li><c:if test="${currentUser != null}">
							<a style='text-decoration: none;'>您好: ${currentUser.username}</a>
						</c:if></li>
		
					<li><a style='text-decoration: none;' id="datetime" name="datetime">2013年01月01日 00:00:00 星期二 </a></li>

					<li><a href="${ctx}/user/updateAdminPwd?flag=1" mask=true target="dialog" width="600">密码修改</a></li>
					<li><c:if test="${currentUser != null}">
							<a href="${ctx}/admin/doLogout">退出</a>
						</c:if></li>
				</ul>

			</div>
		
		</div>


		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>

				<div class="accordion" fillSpace="sidebar">


					<div class="accordionHeader">
						<h2>
							<span>Folder</span>管理员功能
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder expand">

							<li><a>考试管理</a>
								<ul>

									<li><a
										href="${ctx }/ExamQuestionscategoryManageAction.do?method=listquestCategory"
										target="navTab" rel="exam905">单项试题分类</a></li>

									<li><a
										href="${ctx }/ExamZhQuestionscategoryManageAction.do?method=listzhquestCategory"
										target="navTab" rel="exam907">综合试题分类</a></li>

									<li><a
										href="${ctx }/ExamQuestionManageAction.do?method=listQuestion&tkfw=1"
										target="navTab" rel="exam906">单项试题管理</a></li>

									<li><a
										href="${ctx }/ExamZhQuestionManageAction.do?method=listQuestion&tkfw=1"
										target="navTab" rel="exam915">综合试题管理</a></li>


								</ul></li>

							<li><a>系统管理</a>
								<ul>

									<li>
										<a
										href="${ctx }/group/getGroupList"
										target="navTab" rel="cslms030">机构管理</a>
									</li>

									<li>
										<a
										href="${ctx }/user/listUserInfo"
										target="navTab" rel="cslms031" fresh="true">人员管理</a>
									</li>

									<li>
										<a href="${ctx }/NewsManageAction.do?method=listNewsInfo" 
											target="navTab" rel="cslms042" >
											新闻公告
										</a>
									</li>

								</ul>
							</li>

						</ul>
					</div>

				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">我的主页</span> </span> </a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">

					<div class="page unitBox">
						<div class="accountInfo" id="accountInfo">
							<iframe width="610" scrolling="no" height="60" frameborder="0"
								allowtransparency="true"
								src="http://i.tianqi.com/index.php?c=code&id=38&icon=1&num=3"></iframe>

						</div>
						<div class="pageFormContent" layoutH="80">
							<div class="pageContent sortDrag" selector="h1" layoutH="42">

									<div class="panel" defH="150">
										<h1>
											新闻
										</h1>
										<div>
											<table class="list" width="98%">
												<thead>
													<tr>
														<th width="100">
															标题
														</th>
														<th width="80">
															发布时间
														</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${xwList}" var="obj" varStatus="vst">
														<tr>
															<td>
																<a title="新闻详情" target="dialog" mask="true" width="980" height="500"  rel="news100" href="${ctx}/NewsManageAction.do?method=showNews&newsid=${obj.newsid}">${obj.newstitle}</a>
															</td>
															<td>
																${obj.createtime}
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>

									<div class="panel" defH="150">
										<h1>
											公告
										</h1>
										<div>
											<table class="list" width="98%">
												<thead>
													<tr>
														<th width="100">
															标题
														</th>
														<th width="80">
															发布时间
														</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${ggList}" var="obj" varStatus="vst">
														<tr>
															<td>
																<a title="新闻详情" target="dialog" mask="true" width="980" height="500"  rel="news100" href="${ctx}/NewsManageAction.do?method=showNews&newsid=${obj.newsid}">${obj.newstitle}</a>
															</td>
															<td>
																${obj.createtime}
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>

									<div class="panel" defH="50">
										<h1>
											帮助
										</h1>
										<div>
											<a style="color:#3c7fb1;" href="${ctx}/ExpTemplateManageAction.do?method=expUserguide" >帮助手册下载</a>
										</div>
									</div>
									
							</div>

						</div>
					</div>


				</div>
			</div>
		</div>

	</div>

	<div id="footer">
		浙江建设职业技术学院 & 广州中望龙腾软件股份有限公司 版权所有 <a href="javascript:;"></a>
	</div>

</body>

<script type="text/javascript">
	function initUserRoleAndMenuResource(platFormID) {
		if (platFormID == 9004) {
			window.location = '${ctx}/pages/teacherPage.jsp?platFormId=9004';

		} else {
			window.location = '${ctx}/pages/studentpages/studentIndex.jsp';
		}
	}
	function setTime() {
		var day = "";
		var month = "";
		var ampm = "";
		var ampmhour = "";
		var myweekday = "";
		var year = "";
		var myHours = "";
		var myMinutes = "";
		var mySeconds = "";
		var mydate = new Date();
		var myweekday = mydate.getDay();
		var mymonth = parseInt(mydate.getMonth() + 1) < 10 ? "0"
				+ (mydate.getMonth() + 1) : mydate.getMonth() + 1;
		var myday = parseInt(mydate.getDate()) < 10 ? "0" + mydate.getDate()
				: mydate.getDate();
		var myyear = mydate.getYear();
		myHours = parseInt(mydate.getHours()) < 10 ? "0" + mydate.getHours()
				: mydate.getHours();
		myMinutes = parseInt(mydate.getMinutes()) < 10 ? "0"
				+ mydate.getMinutes() : mydate.getMinutes();
		mySeconds = parseInt(mydate.getSeconds()) < 10 ? "0"
				+ mydate.getSeconds() : mydate.getSeconds();
		year = (myyear > 200) ? myyear : 1900 + myyear;
		var weekday = " 星期日 ";
		if (myweekday == 0)
			weekday = " 星期日 ";
		else if (myweekday == 1)
			weekday = " 星期一 ";
		else if (myweekday == 2)
			weekday = " 星期二 ";
		else if (myweekday == 3)
			weekday = " 星期三 ";
		else if (myweekday == 4)
			weekday = " 星期四 ";
		else if (myweekday == 5)
			weekday = " 星期五 ";
		else if (myweekday == 6)
			weekday = " 星期六 ";
		$("#datetime").html(
				year + "年" + mymonth + "月" + myday + "日 " + myHours + ":"
						+ myMinutes + ":" + mySeconds + " " + weekday);
		setTimeout("setTime()", 1000);
	}
	setTime();
</script>
</html>
