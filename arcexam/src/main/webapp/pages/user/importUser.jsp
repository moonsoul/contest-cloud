<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
<form class="pageForm required-validate" id="userManageForm" action="${ctx}/user/importUser"  method="post" enctype="multipart/form-data" onsubmit="return iframeCallback(this,navTabAjaxDone);">
		<input type="hidden" name="groupid" value=""/>
		<input type="hidden" name="usertype" value="3"  />
		<input type="hidden" name="importnum" />
		<input type="hidden" name="adminid" value="" />
		<div class="pageFormContent nowrap" layoutH="56">
				<dl>
					<dt>选择导入文件：</dt>
					<dd>
					<input type="file" name="excelFile" id="excelFile" class="required"/>
					</dd>
				</dl>
				
			<div class="divider"></div>
			<fieldset>
				<legend>
					导入说明
				</legend>
				<pre style="margin: 5px; line-height: 1.4em">
				<br>
1. 导入文件使用标准的EXCEL文件，兼容97-2003格式，不兼容2007以上版本。
2. 导入十分消耗系统资源，一个文档，只读第一个sheet，多个sheet分开制作多个EXCEL文件。
3. EXCEL模板中所属角色代号为：0--学员，1--教师，2--管理员
4. 导入文件中当数据全部编辑完毕后，在文档最后要增加上"数据结束"的标识，详细见导入模版实例。
<br>
<h3>模版下载 :	<a href="${ctx}/exp/template?fileName=3.xls" target="_blank">导入模版</a></h3>

				
				</pre>
			</fieldset>
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
								返回
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</form>
</div>
