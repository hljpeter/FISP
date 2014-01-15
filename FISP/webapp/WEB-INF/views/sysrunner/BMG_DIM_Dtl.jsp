<div class="page_title">
	<spring:message code="sysrunner.title.BatchManageTaskDimDtl" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/taskReferModify"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimType" /></td>
				<td><form:input id="dimTypeId" path="batDimInfo.dimTypeId"
						class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimId" /></td>
				<td><form:input id="dimensionId" path="batDimInfo.dimensionId"
						class="input-large" disabled="true" /></td>
			</tr>

			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimName" /></td>
				<td colspan="3"><form:input id="dimensionName"
						path="batDimInfo.dimensionName" class="input-large"
						disabled="true" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>