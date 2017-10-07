<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
		
			<c:if test="${dto.level ne '3' }">
			<li><a class="add" href="${ctx}/group/showAdd?groupid=${dto.groupid}" target="dialog" rel="addgroupdialog" mask="true" width="800" height="370"><span>新增</span></a></li>
			<li class="line">line</li>
			</c:if>
			
			<%--
			<c:if test="${dto.groupid ne '9001' && (dto.parentid != '9001')}">
			 --%>
			 <c:if test="${dto.groupid ne '9001' }">
				<li><a class="edit" href="${ctx}/group/showUpdate?groupid=${dto.groupid}" target="dialog" rel="updategroupdialog" mask="true"  width="800" height="370"><span>修改</span></a></li>
				
				<c:if test="${(dto.childnum le 0)&&(dto.parentid != '9001')}">
				
				<li class="line">line</li>
				<li><a class="delete" href="${ctx}/group/deleteGroup?groupid=${dto.groupid}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
				
				</c:if>
			
			</c:if>
		
		</ul>
	</div>
</div>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
<div class="pageFormContent  nowrap " layoutH="73">
			<dl>
				<dt>
					编号：
				</dt>
				<dd>
					${dto.groupcode}
				</dd>
			</dl>
			<dl>
				<dt>
					名称：
				</dt>
				<dd>
					${dto.groupname}
				</dd>
			</dl>
			<%-- 
			<dl>
				<dt>
					联系人：
				</dt>
				<dd>
					${groupManageForm.map.linkman}
				</dd>
			</dl>
			--%>
			<dl>
				<dt>
					组织类型：
				</dt>
				<dd>
					${dto.typename}
				</dd>
			</dl>
			<dl>
				<dt>
					备          注：
				</dt>
				<dd>
					<textarea name="remark" id="remark" cols="80" rows="4" readonly="readonly">${dto.remark}</textarea>
				</dd>
			</dl>
			</div>
</div>
