<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	var setting = {
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeExpand: beforeExpand,
			onExpand: onExpand,
			onClick : onClick19
		}
	};

	var zNodes19 = ${funclist};
//////////////////////////////////////
	
	var curExpandNode = null;
		function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			var zTree = $.fn.zTree.getZTreeObj("treeDemo19");
			for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
				if (treeNode !== treeNodeP.children[i]) {
					zTree.expandNode(treeNodeP.children[i], false);
				}
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				singlePath(treeNode);
			}

		}
		function singlePath(newNode) {
			if (newNode === curExpandNode) return;
			if (curExpandNode && curExpandNode.open==true) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo19");
				if (newNode.parentTId === curExpandNode.parentTId) {
					zTree.expandNode(curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents!=null) {
						var oldNode = curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length>0) {
							zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
						} else {
							zTree.expandNode(oldParents[oldParents.length-1], false);
						}
					}
				}
			}
			curExpandNode = newNode;
		}

		function onExpand(event, treeId, treeNode) {
			curExpandNode = treeNode;
		}
	
	
	
////////////////////////////////////////////////
	function onClick19(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo19");
		zTree.expandNode(treeNode, null, null, null, true);
		var nodes = zTree.getSelectedNodes();
		var v = "";
		var t = "";

		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		for ( var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			t += nodes[i].id + ",";
		}

		if (v.length > 0) {
			v = v.substring(0, v.length - 1);
			t = t.substring(0, t.length - 1);

		}

		var cityObj = $("#citySel19").attr("value", v);
		var parentid = $("#searchgroupid").attr("value", t);

	}

	function showMenu19(a) {
		var cityObj = $("#citySel19");
		
						var obj = a;
		for (var t = 0,i=0; i<4;obj = obj.offsetParent,i++) {  
		        t += obj.offsetTop;   
		}  
		
		for (var l = 0,i=0,obj = a; i<2;obj = obj.offsetParent,i++) {  
		        l += obj.offsetLeft;
		}

		
		$("#menuContent19").css({
			left : l +25+"px",
			top : t-90 + "px"
		}).slideDown("fast");
		


		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent19").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn19" || event.target.id == "menuContent19" || $(
				event.target).parents("#menuContent19").length > 0)) {
			hideMenu();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo19"), setting, zNodes19);
	});
</script>
<form id="pagerForm" method="post"
	action="${ctx}/user/listUserInfo">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="10" />
	<input type="hidden" name="searchchildflag" value="0" />
	<input type="hidden" name="searchusercode" value="${dto.searchusercode}" />
	<input type="hidden" name="searchusername" value="${dto.searchusername}" />
	<input type="hidden" name="searchstatus" value="${dto.searchstatus}" />
	<input type="hidden" name="searchgroupid" value="${dto.searchgroupid}" />
	<input type="hidden" name="orderField" value="${dto.orderField}" />  
    <input type="hidden" name="orderDirection"  value="${dto.orderDirection}" /> 
</form>

<div class="pageHeader">
	<form rel="pagerForm"
		action="${ctx}/user/listUserInfo"
		method="post" onsubmit="return navTabSearch(this);">
		
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>
						用户名：
					</label>
					
						<input type="text" name="searchusercode" value="${dto.searchusercode}">
				</li>
				<li>
					<label>
						姓名：
					</label>
					<input type="text" name="searchusername" value="${dto.searchusername}">
				</li>
				<li>
					<label>
						所属班级：
					</label>
				<input size="15" name="searchgroupid" id="searchgroupid" type="hidden" value="${dto.searchgroupid}"/> 
				<input readonly="readonly" name="groupname" id="citySel19" type="text"  value="${dto.groupname}"/> 
				<a id="menuBtn19" class="menuBtn" href="#" onclick="showMenu19(this); return false;">选择</a>
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>
						状态：
					</label>
					<input type="radio" name="searchstatus" value="" ${empty dto.searchstatus ?'checked':''}>全部
					<input type="radio" name="searchstatus" value="1" ${dto.searchstatus eq '1'?'checked':''}>有效
					<input type="radio" name="searchstatus" value="0" ${dto.searchstatus eq '0'?'checked':''}>无效
				</li>
				<li>
					<label>
						角色：
					</label>
					<input type="radio" name="searchusertype" value="" ${empty dto.searchusertype ?'checked':''}>全部
					<input type="radio" name="searchusertype" value="0" ${dto.searchusertype eq '0'?'checked':''}>学员
					<input type="radio" name="searchusertype" value="1" ${dto.searchusertype eq '1'?'checked':''}>教师
					<input type="radio" name="searchusertype" value="2" ${dto.searchusertype eq '2'?'checked':''}>管理员
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
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="reset">
									重置
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add"
					href="${ctx}/user/showAdd?savemethod=1"
					target="navTab" rel="addUser" title="新增人员"><span>新增</span> </a>
				<li class="line">line</li>
				<a class="delete"
					href="${ctx}/user/deleteBatchUser"
					target="selectedTodo" title="确实要删除所选记录吗?" rel="ids"
					postType="string"><span>删除</span> </a>
							
				<li class="line">line</li>
				<a class="icon" href="${ctx}/user/gotoImportUsers"
					target="navTab" rel="impuser"><span>批量导入</span>
				</a>

			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="158" >
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80" orderField="usercode" <c:if test='${dto.orderField == "usercode" }'> class="${dto.orderDirection}"</c:if>>用户名</th>
				<th width="80">姓名</th>
				<th width="80">班级</th>
				<th width="80">角色</th>
				<th width="80">状态</th>
				<th width="80">积分</th>
				<th width="100">操作</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entityList}" var="item" varStatus="s">
				<tr target="entityid" rel="${item.userid}">
					<td>
						<c:if test="${item.usercode =='admin'}">系统管理员不可删除!</c:if>
						
						<c:if test="${item.usercode !='admin' }"><input name="ids" value="${item.userid}" type="checkbox" ></c:if>
						
					</td>
					<td>
						${item.usercode}
					</td>

					<td>
						${item.username}
					</td>
					<td>
						${item.groupname}
					</td>
					<td>
						<c:if test="${item.usertype=='0'}">
							&nbsp;<span>学员</span>
						</c:if>
						<c:if test="${item.usertype=='1'}">
							&nbsp;<span>教师</span>
						</c:if>
						<c:if test="${item.usertype=='2'}">
							&nbsp;<span>管理员</span>
						</c:if>
					</td>
					<td>
					<c:if test="${item.usercode =='admin'}">有效</c:if>
					<c:if test="${item.usercode !='admin'}">
						<c:if test="${item.status=='0'}">
							&nbsp;<a target="ajaxTodo" href="${ctx}/user/updateStatus?userid=${item.userid}&status=1"><span>无效</span> </a>
						</c:if>
						<c:if test="${item.status=='1'}">
							&nbsp;<a target="ajaxTodo" href="${ctx}/user/updateStatus?userid=${item.userid}&status=0"><span>有效</span> </a>
						</c:if>
					</c:if>
					</td>
					<td>
						${item.integration}
					</td>
					<td>
					<a target="dialog" mask="true" width="980" height="500" rel="QuestionsDetail" href="${ctx}/user/showOneUserInfo?userid=${item.userid}">查看信息</a>
					|&nbsp;<a title="编辑" target="navTab" href="${ctx}/user/showUpdate?savemethod=2&userid=${item.userid}" rel="editUser" title="编辑人员">编辑</a>
					|&nbsp;<a target="ajaxTodo" href="${ctx}/user/resetpassword?userid=${item.userid}&usercode=${item.usercode}" title="确认密码重置吗?" ><span>重置密码</span> </a>
					|&nbsp;<a href="${ctx}/user/updateUserIntegration?integration=${item.integration}&flag=1&userid=${item.userid}" mask=true target="dialog" width="600"><span>调整积分</span></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>共${totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalRows}"
			numPerPage="10" pageNumShown="10" currentPage="${currentPage}"></div>

	</div>
</div>
<div id="menuContent19" class="menuContent" style="display: none; position: absolute;background-color:#EEF3F4; border-radius:6px;">
	<ul id="treeDemo19" class="ztree" style="margin-top: 0; width: 175px;overflow-x:scroll;"></ul>
</div>