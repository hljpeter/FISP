<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.RoleInfoMaintenanceDetailSearch"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form_01DetailSearch" action="${pageContext.request.contextPath}/sm02/05/detailSearch_01" method="post" modelAttribute="roleInfForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td width="40%" class="label_td"><spring:message code="index.label.sm.RoleId"/>
				</td>
				<td width="20%">
					<form:input id="roleid" path="roleInf.roleid" type="text" class=".input-small" readonly="true"/>
				</td>
				<td width="10%" class="label_td"><spring:message code="index.label.sm.OperationStatus"/>
				</td>
				<td width="30%">
					<t:codeValue items="${CL_0003}" key="${roleInfForm.roleInf.optstatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleName"/>
				</td>
				<td colspan="3">
					<form:input id="rolename" path="roleInf.rolename" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleDesc"/>
				</td>
				<td colspan="3">
					<form:input id="roledesc" path="roleInf.roledesc" type="text" class="span8" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.CreateOrgName"/>
				</td>
				<td colspan="3">
					<t:codeValue items="${SM_0002 }" key="${roleInfForm.roleInf.createorg}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.InfrUseFlag"/>
				</td>
				<td class="label_td" colspan="3">
					<form:checkbox path="roleInf.infruseflag" value="01" disabled="true" cssStyle="margin-right:10px;"/><spring:message code="index.lable.CanUse"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.MenuList"/>
				</td>
				<td colspan="3">
					<jsp:include page="SM_00_01.jsp?menuId=${form.roleInf.menulist }&editable=${form.editable }"></jsp:include>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Creater"/>
				</td>
				<td>
					<form:input path="roleInf.creater" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CreateTime"/>
				</td>
				<td>
					<t:dateTimeFormat type="text" value="${roleInfForm.roleInf.createtime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.LastOperator"/>
				</td>
				<td>
					<form:input path="roleInf.lastoperator" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.LastOptTime"/>
				</td>
				<td>
					<t:dateTimeFormat type="text" value="${roleInfForm.roleInf.lastopttime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Checker"/>
				</td>
				<td>
					<form:input path="roleInf.checker" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CheckTime"/>
				</td>
				<td>
					<t:dateTimeFormat type="text" value="${roleInfForm.roleInf.checktime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button type="button" class="btn btn-primary" onclick="javascript:window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>