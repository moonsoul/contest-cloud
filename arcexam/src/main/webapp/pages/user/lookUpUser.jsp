<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<form id="pagerForm" method="post"
	action="${ctx}/UserManageAction.do?method=lookUpUser&mode=${mode}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="10" />
	<input type="hidden" name="searchchildflag" value="0" />
	<input type="hidden" name="searchusercode" value="${userManageForm.map.searchusercode}" />
	<input type="hidden" name="searchusername" value="${userManageForm.map.searchusername}" />
	<input type="hidden" name="searchstatus" value="1" />
</form>
<div class="pageHeader">

	<form rel="pagerForm"
		action="${ctx}/UserManageAction.do?method=lookUpUser&mode=${mode}"
		method="post" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">

			<ul class="searchContent">
				<li>
					<label>
						编号：
					</label>
					
						<input type="text" name="searchusercode" value="${userManageForm.map.searchusercode}">
				</li>
				<li>
					<label>
						名称：
					</label>
					<input type="text" name="searchusername" value="${userManageForm.map.searchusername}">
				</li>
			</ul>

				
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									检索
								</button>
							</div>
						</div>
					</li>
					<c:if test="${!mode}">
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" multLookup="dh" warn="请选择">
									确定
								</button>
							</div>
						</div>
					</li>
					</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138"  targetType="dialog" >
		<thead>
			<tr>
				<c:if test="${!mode}">
				<th width="20">
					<input type="checkbox" class="checkboxCtrl" group="dh" />
				</th>
				</c:if>
				<th width="80">
					编号
				</th>
				<th width="100">
					名称
				</th>
				<th width="80">
					班级
				</th>
				<c:if test="${mode}" >
				<th width="30">
					选择
				</th>
				</c:if>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entityList}" var="item" varStatus="s">
				<tr target="entityid" rel="${item.userid}">
					<c:if test="${!mode}">
					<td>
						<input type="checkbox" name="dh"
							value="{id:'${item.userid}', name:'${item.username}',code:'${item.usercode}',groupname:'${item.groupname}'}" />
					</td>
					</c:if>
					<td>
						${item.usercode}
					</td>
					<td>
						${item.username}
					</td>
					<td>
						${item.groupname}
					</td>
					<c:if test="${mode}" >
					<td>
					<a class="btnSelect" href="javascript:$.bringBack({id:'${item.userid}', name:'${item.username}',code:'${item.usercode}',groupname:'${item.groupname}'})" title="选择">选择</a>
					</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>共${totalRows}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalRows}"
			numPerPage="10" pageNumShown="10" currentPage="${currentPage}"></div>

	</div>
</div>
<div id="menuContent21" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo21" class="ztree" style="margin-top: 0; width: 175px;"></ul>
</div>