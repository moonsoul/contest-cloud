<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>

<div class="pageContent" style="padding:5px">
		<div class="tabsContent">
			<div>
	
				<div layoutH="15" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				 
				    <ul class="tree treeFolder">
				       <%-- 
				    <li><a href="${ctx}/GroupManageAction.do?method=showGroupInfoByID&level=0&groupid=9001" target="ajax" rel="jbsxBox6">根目录</a>
				    <ul>--%>
					<c:forEach items="${funcAndChidList}" var="obj" varStatus="vs">
						<li><a href="${ctx}/group/showGroupInfoByID?level=1&groupid=${obj.groupid}" target="ajax" rel="jbsxBox6">${obj.groupname}</a>
							<c:forEach items="${obj.childFunctionList}" var="func">
							<ul>
								<li><a href="${ctx}/group/showGroupInfoByID?level=2&groupid=${func.groupid}" target="ajax" rel="jbsxBox6">${func.groupname}</a>
									<c:forEach items="${func.childFunctionList}" var="func2">
									<ul>
										<li><a href="${ctx}/group/showGroupInfoByID?level=3&groupid=${func2.groupid}" target="ajax" rel="jbsxBox6">${func2.groupname}</a></li>
										
									</ul>
									</c:forEach>
								</li>
							
							</ul>
							</c:forEach>
						</li>
					</c:forEach>
					<%-- 
					</ul>
					</li>--%>
				     </ul>
				</div>
				
				<div id="jbsxBox6" class="unitBox" style="margin-left:246px;">
					<!--#include virtual="list1.html" -->
				</div>
	
			</div>
		</div>
</div>
