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
			beforeClick:beforeClick,
			beforeExpand: beforeExpand,
			onExpand: onExpand,
			onClick : onClick19
		}
	};

	var zNodes19 = ${funclist};
	

		function beforeClick(treeId, treeNode) {
			var check = (treeNode && !treeNode.isParent);
			if (!check) alert("只能选择最末一级分类...");
			return check;
		}
	//////////////////////////////////////
	
	var curExpandNode = null;
		function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			var zTree = $.fn.zTree.getZTreeObj("treeNianji19");
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
				var zTree = $.fn.zTree.getZTreeObj("treeNianji19");
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
		var zTree = $.fn.zTree.getZTreeObj("treeNianji19");
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

		var cityObj = $("#citySelNianJi19").attr("value", v);
		var parentid = $("#companyid").attr("value", t);
		//alert($("#searchgroupid").val());

	}

	function showMenuNianJi19(a) {
	
		var cityObj = $("#citySelNianJi19");
		
								var obj = a;
		for (var t = 0,i=0; i<4;obj = obj.offsetParent,i++) {  
		        t += obj.offsetTop;   
		}  
		
		for (var l = 0,i=0,obj = a; i<2;obj = obj.offsetParent,i++) {  
		        l += obj.offsetLeft;
		}

		
		$("#menuContentNianji19").css({
			left : l +25+"px",
			top : t-90 + "px"
		}).slideDown("fast");
		




		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContentNianji19").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtnNianJi19" || event.target.id == "menuContentNianji19" || $(
				event.target).parents("#menuContentNianji19").length > 0)) {
			hideMenu();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeNianji19"), setting, zNodes19);
	
	});
</script>

<form id="pagerForm" method="post"  action="${ctx}/UserManageAction.do?method=getUserListPaiMingByNianJi">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="10" />
	<input type="hidden" name="searchchildflag" value="0" />
	<input type="hidden" name="searchusercode" value="${userManageForm.map.searchusercode}" />
	<input type="hidden" name="searchusername" value="${userManageForm.map.searchusername}" />
	<input type="hidden" name="searchstatus" value="${userManageForm.map.searchstatus}" />
</form>
<div class="pageHeader">

		<form rel="pagerForm"
		action="${ctx}/UserManageAction.do?method=getUserListPaiMingByNianJi"
		method="post" onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>
						所属年级：
					</label>
				<input size="25" name="companyid" id="companyid" type="hidden" value="${userManageForm.map.companyid}"/> 
				<input readonly="readonly" name="groupname" id="citySelNianJi19" type="text"  value="${userManageForm.map.groupname}"/> 
				<a id="menuBtnNianJi19" class="menuBtn" href="#" onclick="showMenuNianJi19(this); return false;">选择</a>
				</li>
				<li>
					<label>
						用户名：
					</label>
					
						<input type="text" name="searchusercode" value="${userManageForm.map.searchusercode}">
				</li>
				<li>
					<label>
						姓名：
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
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="reset">
									重置
								</button>
							</div>
						</div>
					</li>
					<li>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
<%-- 
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx}/UserManageAction.do?method=getUserListPaiMing"
					target="navTab" rel="getUserListPaiMing" title="班级排名" >
					<span>按班级排名</span> 
				</a>
					<li class="line">line</li>
				<a class="add" href="${ctx}/UserManageAction.do?method=getUserListPaiMing"
					target="navTab" rel="getUserListPaiMing" title="年级排名" >
					<span>按年级排名</span> 
				</a>
					
					
			</li>
		</ul>
	</div>
--%>
	<table class="table" width="100%" layoutH="108">
		<thead>
			<tr>
				<th width="80">用户名</th>
				<th width="100">姓名</th>
				<th width="80">班级</th>
				<th width="80">积分</th>
				<th width="80">排名</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entityList}" var="item" varStatus="s">
				<tr target="entityid" rel="${item.userid}">

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
						${item.integration}
					</td>
					<td>
						${item.rank}
						
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
<div id="menuContentNianji19" class="menuContent" style="display: none; position: absolute;background-color:#EEF3F4; border-radius:6px;">
	<ul id="treeNianji19" class="ztree" style="margin-top: 0; width: 175px;overflow-x:scroll;"></ul>
</div>