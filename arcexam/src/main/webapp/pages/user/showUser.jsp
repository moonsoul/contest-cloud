<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent ">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>照片：</label>
				<img width="90" height="100" src="${ctx}/${dto.photo}"/>
			</div>
			<div class="unit">
				<label style="margin-top:-5px;">用户名称：</label>
				${dto.usercode}
			</div>
			<div class="unit">
				<label style="margin-top:-5px;">真实姓名：</label>
				${dto.username}
			</div>
			<div class="unit">
				<label style="margin-top:-5px;">性别：</label>
				<c:if test="${dto.sex==1}">男</c:if>
				<c:if test="${dto.sex==2}">女</c:if>
			</div>
			<div class="unit">
				<label style="margin-top:-5px;">班级：</label>
				${dto.groupname}
			</div>
			<div class="unit">
			<c:if test="${dto.usertype=='0' }">
				<label style="margin-top:-5px;">入学时间：</label>${dto.createtime}
				</c:if>
			<c:if test="${dto.usertype!='0' }">
				<label style="margin-top:-5px;">入职时间：</label>
				${dto.createtime}
				</c:if>
				
			</div>
			<div class="unit">
				<label style="margin-top:-5px;">专业：</label>
				${dto.groupname}
			</div>
			<div class="unit">
				<label style="margin-top:-5px;">说明：</label>
				${dto.remark}
			</div>

		</div>
</div>
