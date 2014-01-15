
<script type="text/javascript">
	function createDim() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_DIM_Add/SubmitDim";
		var msg = $("#confirmMsg1").val() + $("#create").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};

</script>
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="batchManageForm">
			<form:form commandName="batchManageForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="sysrunner.title.BatchManageTaskDimAdd" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/taskReferModify"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskDimType" /></td>
				<td><form:input id="dimTypeId" path="batDimInfo.dimTypeId"
						class="input-large" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskDimId" /></td>
				<td><form:input id="dimensionId" path="batDimInfo.dimensionId"
						class="input-large" /></td>
			</tr>

			<tr>
				<td class="label_td"><font color="red">*</font><spring:message
						code="sysrunner.label.TaskDimName" /></td>
				<td colspan="3"><form:input id="dimensionName"
						path="batDimInfo.dimensionName" class="input-large" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="create" type="button" class="btn btn-primary"
			onclick="createDim();"
			value="<spring:message code="button.lable.Submit"/>"> <input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>