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
			onClick : onClick18
		}
	};

	var zNodes18 = ${funclist};
	var level = 0;

		function beforeClick(treeId, treeNode) {
		if(level < 2){alert("请在机构管理建立三级目录");return false;}
		
			var check = (treeNode && !treeNode.isParent);
			if (!check) alert("只能选择最末一级分类...");
			return check;
		}
//////////////////////////////////////
	
	var curExpandNode = null;
		function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			var zTree = $.fn.zTree.getZTreeObj("treeDemo18");
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
				var zTree = $.fn.zTree.getZTreeObj("treeDemo18");
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
	function onClick18(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo18");
		
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

		var cityObj = $("#citySel18");
		var parentid = $('#groupid');
		cityObj.attr("value", v);
		parentid.attr("value", t);


		var pNode = treeNode.getParentNode();
		
		pNode = treeNode.getParentNode();
		
		$('#usercompanyid').attr("value",pNode.id);
		
		
	}

	function showMenu18(a) {
		var cityObj = $("#citySel18");
		
		
				var obj = a;
		for (var t = 0,i=0; i<4;obj = obj.offsetParent,i++) {  
		        t += obj.offsetTop;   
		}  
		
		for (var l = 0,i=0,obj = a; i<2;obj = obj.offsetParent,i++) {  
		        l += obj.offsetLeft;
		}

		
		$("#menuContent18").css({
			left : l +25+"px",
			top : t + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
		
		
	}
	function hideMenu() {
		$("#menuContent18").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn18" || event.target.id == "menuContent18" || $(
				event.target).parents("#menuContent18").length > 0)) {
			hideMenu();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo18"), setting, zNodes18);
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo18");
		var nodes = zTree.getNodes(); 
		var nodes_array = zTree.transformToArray (nodes);
		for(var i=0;i<nodes_array.length;i++){
 			if(nodes_array[i].level > level){
 				level = nodes_array[i].level;
 			}
 		}
	});
	<c:if test="${savemethod eq '1'}">
		navTab.closeTab('editUser');
	</c:if>
	<c:if test="${savemethod eq '2'}">
		navTab.closeTab('addUser');
	</c:if>
</script>

<div class="pageContent">
	<form id="userManageForm" method="post"
		action="${ctx}/user/addUser"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="55">
			<input type="hidden" name="userid" value="${dto.userid}"/>
			<input type="hidden" name="companyid" value="${dto.companyid}"/>
			<input type="hidden" name="position" value="9001"/>
			<input type="hidden" name="password" value="${dto.password}"/>
			<input type="hidden" name="status" value="1"/>
			<input type="hidden" name="savemethod" value="${savemethod}"/>
			
		<c:if test="${not empty teacherupdate}">
			<c:if test="${not empty dto.photo}">
			<div class="unit">
				<label>照片：</label>
				<img width="90" height="100" src="${ctx}/resource/userphoto/${dto.photo}"/>
			</div>
			</c:if>
			<c:if test="${empty dto.photo}">
			<div class="unit">
				<label>照片：</label>
				未上传照片
			</div>
			</c:if>
			</c:if>
			<dl>
				<dt>上传照片：</dt>
				<dd>
				<input type="file" name="photofile" if="photofile" />
			    <input type="hidden" name="photo"/>
				</dd>
			</dl>
			<dl>
				<dt>用户名：</dt>
				<dd>
				<c:if test="${savemethod eq '1'}">
				<input type="text" name="usercode" id="usercode" maxlength="30" class="required alphanumeric"  size="30"/>
				<span class="info">用户名只能输入数字、字母和下划线</span>
				</c:if>
				<c:if test="${savemethod eq '2'}">
				<input type="text" name="usercode" id="usercode" maxlength="30" class="required alphanumeric" readonly="true"  size="30"/>
				</c:if>
				</dd>
			</dl>
			<dl>
				<dt>真实姓名：</dt>
				<dd>
				<input type="text" name="username" id="username" maxlength="20" class="required"  size="30"/>
				</dd>
			</dl>
			<dl>
				<dt>性别：</dt>
				<dd>
					<input type="radio" name="sex" value="1" <c:if test="${dto.sex==1}">checked="checked"</c:if>>男</input>
					<input type="radio" name="sex" value="2" <c:if test="${dto.sex==2}">checked="checked"</c:if>>女</input>
				</dd>
			</dl>
	
			<dl>
				<dt>所属班级：</dt>
				<dd>
				
				<input size="30" name="usercompanyid" id="usercompanyid" type="hidden" value=""/> 
				<input size="30" name="groupid" id="groupid" type="hidden" value="${dto.groupid}"/>
				<input readonly="readonly" id="citySel18" size="30" type="text" class="required" value="${dto.groupname}"/>
				<a id="menuBtn18" class="menuBtn" href="#" onclick="showMenu18(this); return false;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>专业：</dt>
				<dd>
					<input type="text" name="speciality" id="speciality" maxlength="25" size="30"/>
				</dd>
			</dl>
			<dl>
			<c:if test="${empty teacherupdate}">
			
				<dt>入学时间：</dt>
				</c:if>
			<c:if test="${not empty teacherupdate}">
			
				<dt>入职时间：</dt>
				</c:if>
				<dd>
					<input type="text" name="boarddate" id="boarddate" class="date" size="30" readonly="true"/>
				</dd>
			</dl>
			<dl>
				<dt>通信地址：</dt>
				<dd>
					<input type="text" name="address" id="address" maxlength="100"  size="30"/>
				</dd>
			</dl>
			<dl>
				<dt>电话：</dt>
				<dd>
					<input type="text" name="phone" id="phone" maxlength="20" class="phone" size="30"/>
					<span class="info">电话格式：010 88888888或18666666666</span>
				</dd>
			</dl>
			<c:if test="${empty teacherupdate}">
				<c:if test="${dto.usercode=='admin'}">
					<dl>
						<input type="hidden" name="usertype" value="2"/>
						<dt>所属角色：</dt>
						<dd>管理员</dd>
					</dl>
				</c:if>
				<c:if test="${dto.usercode!='admin'}">
					<dl>
						<dt>所属角色：</dt>
						<dd>
							<input type="radio" name="usertype" value="0" <c:if test="${dto.usertype==0}">checked="checked"</c:if>>学员</input>
							<input type="radio" name="usertype" value="1" <c:if test="${dto.usertype==1}">checked="checked"</c:if>>教师</input>
							<input type="radio" name="usertype" value="2" <c:if test="${dto.usertype==2}">checked="checked"</c:if>>管理员</input>
						</dd>
					</dl>
				</c:if>
			</c:if>
			<c:if test="${not empty teacherupdate}">
			<dl>
				<input type="hidden" name="usertype" value="1"/>
				<dt>所属角色：</dt>
				<dd>教师</dd>
			</dl>
			</c:if>
			
			<dl class="nowrap">
				<dt>说明：</dt>
				<dd>
						<textarea id="remark" name="remark" cols="85" rows="3" >${dto.remark}</textarea>
				</dd>
			</dl>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								保存
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								取消
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

<div id="menuContent18" class="menuContent" style="display: none; position: absolute;background-color:#EEF3F4; border-radius:6px;">
	<ul id="treeDemo18" class="ztree" style="margin-top: 0; width: 175px;overflow-x:scroll;"></ul>
</div>