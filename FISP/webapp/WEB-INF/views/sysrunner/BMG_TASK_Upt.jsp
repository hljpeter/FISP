
<script type="text/javascript">
	$(function() {
		document.getElementById('polling_div').style.display = 'none';
		document.getElementById('manual_div').style.display = 'none';
		var s = $("#taskStartType").val();
		if ('2' == s || '3' == s || '4' == s || '5' == s || '6' == s) {
			$("#taskStartDay").removeAttr("readonly");
		} else {
			$("#taskStartDay").attr("readonly", "true");
		}
		var dimTypeId = $("#dimTypeId").val();
		if ('' == dimTypeId) {
			$("#editDim").attr("disabled", "true");
		} else {
			$("#editDim").removeAttr("disabled");
		}

		if ('B' == s) {
			document.getElementById('polling_div').style.display = 'table-row';
			document.getElementById('manual_div').style.display = 'none';
		} else {
			document.getElementById('polling_div').style.display = 'none';
			if ('0' != s) {
				document.getElementById('manual_div').style.display = 'table-row';
			} else {
				document.getElementById('manual_div').style.display = 'none';
			}
		}

		var taskId = $("#taskId").val();
		var msg = "${resultMessages.type}";
		if (msg && msg == "SUCCESS") {
			if (form.action == "${pageContext.request.contextPath}/BMG_STEP_Dtl/Del") {
				form.action = "${pageContext.request.contextPath}/BMG_TASK_Upt/Init?taskId="
						+ taskId + '&ReflashFlag=1';
				form.submit();
			}
		}
	});
	//detail button
	function detailSearch(taskId, stepId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_STEP_Dtl/Init?taskId='
								+ taskId + '&stepId=' + stepId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}

	//modify button
	function modify(taskId, stepId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_STEP_Upt/Init?taskId='
								+ taskId + '&stepId=' + stepId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		reflashTaskUpt();
	}

	//del button
	function del(taskId, stepId) {
		document.getElementById("del_task_id").value = taskId;
		document.getElementById("del_step_id").value = stepId;
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_STEP_Dtl/Del";
		var msg = $("#confirmMsg1").val() + $("#deleteBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	}
	//task modify
	function updateTask() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_TASK_Upt/SubmitTask";
		var msg = $("#confirmMsg1").val() + $("#update").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}

	}
	//create button
	function createStepInit() {
		var taskId = $("#taskId").val();
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_STEP_Add/Init?taskId='
								+ taskId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		reflashTaskUpt();
	}

	function reflashTaskUpt() {
		var taskId = $("#taskId").val();
		form.action = "${pageContext.request.contextPath}/BMG_TASK_Upt/Init?taskId="
				+ taskId + '&ReflashFlag=1';
		form.submit();
	};

	function changeTaskType(s) {
		if ('2' == s || '3' == s || '4' == s || '5' == s || '6' == s) {
			$("#taskStartDay").removeAttr("readonly");
		} else {
			$("#taskStartDay").attr("readonly", "true");
		}

		if ('B' == s) {
			document.getElementById('polling_div').style.display = 'table-row';
			document.getElementById('manual_div').style.display = 'none';
		} else {
			document.getElementById('polling_div').style.display = 'none';
			if ('0' != s) {
				document.getElementById('manual_div').style.display = 'table-row';
			} else {
				document.getElementById('manual_div').style.display = 'none';
			}
		}
	};

	function changeTaskDimType(s) {
		if ('' == s) {
			$("#editDim").attr("disabled", "true");
		} else {
			$("#editDim").removeAttr("disabled");
		}
	}

	function editDimInfo() {
		var taskId = $("#taskId").val();
		var dimTypeId = $("#dimTypeId").val();
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_Task_DIM_Add/Init?taskId='
								+ taskId + '&dimTypeId=' + dimTypeId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');

		reflashTaskUpt();
	};
	function getDim(obj) {
		dimTypeId = $(obj).val().replace(/\s/g, "");
		if ("" == dimTypeId) {
			$("#editDim").attr("disabled", "true");
		} else {
			$("#editDim").removeAttr("disabled");
		}
	};

	function getDimInf(obj) {
		var taskId = $("#taskId").val();
		var dimTypeId = $("#dimTypeId").val();
		var orgExist = checkDimInf(taskId, dimTypeId);
		if (false == orgExist[0]) {
			$("#editDim").attr("disabled", "true");
		} else {
			$("#editDim").removeAttr("disabled");
		}
		if (false == orgExist[1]) {
			var msg = "任务维度不存在";
			document.getElementById("errorMsg").style.display = "block";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			$(obj).val("");
			$("#editDim").attr("disabled", "true");
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
	<spring:message code="sysrunner.title.BatchManageTaskUpt" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/taskReferModify"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<form:input path="del_step_id" type="hidden" id="del_step_id" />
		<form:input path="del_task_id" type="hidden" id="del_task_id" />
		<form:input path="batTaskInfo.taskId" type="hidden" id="taskId" />
		<table class="tbl_search">
			<tr>
				<td colspan="4">
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
				<td class="label_td"><font color="red">*</font> <spring:message
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
				<td><form:select id="dimTypeId" path="batTaskInfo.dimTypeId"
						onchange="changeTaskDimType(this.value)">
						<form:option value=""></form:option>
						<form:options items="${BMG_DIM_Type}" />
					</form:select> <input type="button" id="editDim" class="btn btn-small"
					onclick="editDimInfo()"
					value="<spring:message code="dp.lable.Express"/>"></td>
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
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskEndTime" /></td>
				<td><form:input id="taskEndTime" path="batTaskInfo.taskEndTime"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskPollingInterval" /></td>
				<td><form:input id="taskPollingInterval"
						path="batTaskInfo.taskPollingInterval" class="input-large" /></td>
			</tr>
			<tr id="manual_div">
				<td class="label_td"><font color="red">*</font> <spring:message
						code="sysrunner.label.TaskStartTN" /></td>
				<td colspan="3"><form:input id="taskStartTn"
						path="batTaskInfo.taskStartTn" class="input-large" /></td>
			</tr>
		</table>
	</form:form>
</div>
<div class="row">
	<div class="tbl_page_head">
		<p class="text-info">
			<spring:message code="sysrunner.title.BatchManageStepInfoList" />
		</p>
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="220px"><spring:message code="sysrunner.label.StepName" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message code="sysrunner.label.StepType" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message
							code="sysrunner.label.StepPriority" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="140px"><spring:message code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<c:forEach var="dto" items="${list}" varStatus="i">
					<tr>
						<td class="tbl_page_td_center vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
						<td class="tbl_page_td_center vtip" width="220px">${dto.stepName}</td>
						<td class="tbl_page_td_center vtip" width="100px"><t:codeValue
								items="${BMG_STEP_TYPE}" key="${dto.stepStartType}" type="label" /></td>
						<td class="tbl_page_td_center vtip" width="100px">${dto.priority}</td>
						<td class="tbl_page_td_left" width="140px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto.taskId)}','${f:h(dto.stepId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
								<input type="button" id="modifyBtn" class="btn btn-small"
									onclick="modify('${f:h(dto.taskId)}','${f:h(dto.stepId)}')"
									value="<spring:message code="button.lable.Modify"/>"> <input
									type="button" id="deleteBtn" class="btn btn-small"
									onclick="del('${f:h(dto.taskId)}','${f:h(dto.stepId)}')"
									value="<spring:message code="button.lable.Del"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="update" type="button" class="btn btn-primary"
			onclick="updateTask();"
			value="<spring:message code="button.lable.Modify"/>"> <input
			id="createStep" type="button" class="btn btn-primary"
			onclick="createStepInit();"
			value="<spring:message code="sysrunner.label.AddStep"/>"> <input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>
