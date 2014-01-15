<script type="text/javascript">
	//init page
	$(function() {
		document.getElementById('polling_div').style.display = 'none';
		document.getElementById('manual_div').style.display = 'none';
		var s = $("#taskStartType").val();
		if ('2' == s || '3' == s || '4' == s || '5' == s || '6' == s) {
			$("#taskStartDay").removeAttr("disabled");
		}else{
			$("#taskStartDay").attr("disabled","true");
		}

		if('B'==s){
			document.getElementById('polling_div').style.display = 'table-row';
			document.getElementById('manual_div').style.display = 'none';
		}else{
			document.getElementById('polling_div').style.display = 'none';
			if('0'!=s){
				document.getElementById('manual_div').style.display = 'table-row';
			}else{
				document.getElementById('manual_div').style.display = 'none';
			}
		}
		var msg = "${resultMessages.type}";
		if (msg && msg == "SUCCESS") {
			$("#taskName").attr("disabled", "disabled");
			$("#taskStartType").attr("disabled", "disabled");
			$("#taskStartTime").attr("disabled", "disabled");
			$("#dimTypeId").attr("disabled", "disabled");
			$("#taskStartDay").attr("disabled", "disabled");
			$("#taskEnable").attr("disabled", "disabled");
			$("#allowManual").attr("disabled", "disabled");
			$("#allowMultiple").attr("disabled", "disabled");
			$("#taskEndTime").attr("disabled", "disabled");
			$("#taskPollingInterval").attr("disabled", "disabled");
			$("#taskStartTn").attr("disabled", "disabled");
			$("#create").attr("disabled", "disabled");
		}
	});
	function createTask() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_TASK_Add/SubmitTask";
		var msg = $("#confirmMsg1").val() + $("#create").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};

	function changeTaskType(s) {
		if ('2' == s || '3' == s || '4' == s || '5' == s || '6' == s) {
			$("#taskStartDay").removeAttr("disabled");
		}else{
			$("#taskStartDay").attr("disabled","true");
		}

		if('B'==s){
			document.getElementById('polling_div').style.display = 'table-row';
		}else{
			document.getElementById('polling_div').style.display = 'none';
			if('0'!=s){
				document.getElementById('manual_div').style.display = 'table-row';
			}else{
				document.getElementById('manual_div').style.display = 'none';
			}
		}
	};
	
	function queryDim() {
		showSelDim([ {
			"dimTypeId" : "param1"
		} ]);
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
	<spring:message code="sysrunner.title.BatchManageTaskAdd" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/taskReferModify"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<form:input path="del_step_id" type="hidden" id="del_step_id" />
		<table class="tbl_search">
			<tr>
				<td colspan="6">
					<p class="text-info">
						<spring:message code="sysrunner.title.BatchManageTaskInfo" />
					</p>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskName" /></td>
				<td><form:input id="taskName" path="batTaskInfo.taskName"
						class="input-large" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskType" /></td>
				<td><form:select id="taskStartType"
						path="batTaskInfo.taskStartType"
						onchange="changeTaskType(this.value)">
						<form:option value=""></form:option>
						<form:options items="${BMG_TASK_TYPE}" />
					</form:select></td>
			</tr>

			<tr>
				<td class="label_td">&nbsp&nbsp&nbsp<spring:message
						code="sysrunner.label.TaskStartDay" /></td>
				<td><form:input id="taskStartDay"
						path="batTaskInfo.taskStartDay" class=".input-large" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskStartTime" /></td>
				<td><form:input id="taskStartTime"
						path="batTaskInfo.taskStartTime"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskAllowMultiple" /></td>
				<td><form:select id="allowMultiple"
						path="batTaskInfo.allowMultiple">
						<form:option value=""></form:option>
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
				<td class="label_td">&nbsp&nbsp&nbsp<spring:message
						code="sysrunner.label.TaskDimType" /></td>
				<td><form:select id="dimTypeId" path="batTaskInfo.dimTypeId">
						<form:option value=""></form:option>
						<form:options items="${BMG_DIM_Type}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskEnable" /></td>
				<td><form:select id="taskEnable" path="batTaskInfo.taskEnable">
						<form:option value=""></form:option>
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskAllowManual" /></td>
				<td><form:select id="allowManual"
						path="batTaskInfo.allowManual">
						<form:option value=""></form:option>
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
			</tr>
			<tr id="polling_div">
				<td class="label_td">&nbsp&nbsp&nbsp <spring:message
						code="sysrunner.label.TaskEndTime" /></td>
				<td><form:input id="taskEndTime" path="batTaskInfo.taskEndTime"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
				<td class="label_td">&nbsp&nbsp&nbsp <spring:message
						code="sysrunner.label.TaskPollingInterval" /></td>
				<td><form:input id="taskPollingInterval"
						path="batTaskInfo.taskPollingInterval" class="input-large" /></td>
			</tr>
			<tr id="manual_div">
				<td class="label_td">&nbsp&nbsp&nbsp <spring:message
						code="sysrunner.label.TaskStartTN" /></td>
				<td colspan="3"><form:input id="taskStartTn"
						path="batTaskInfo.taskStartTn" class="input-large" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="create" type="button" class="btn btn-primary"
			onclick="createTask();"
			value="<spring:message code="button.lable.Submit"/>"> <input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>