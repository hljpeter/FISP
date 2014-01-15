<script type="text/javascript">
	//detail button
	function detailSearch(taskId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_TASK_Dtl/Init?taskId='
								+ taskId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryExp();
	}

	//modify button
	function modify(taskId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_TASK_Upt/Init?taskId='
								+ taskId + '&ReflashFlag=',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryExp();
	}

	//del button
	function del(taskId) {
		document.getElementById("del_task_id").value = taskId;
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_TASK_Qry/Del";
		var msg = $("#confirmMsg1").val() + $("#deleteBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	}

	// button
	function createTask() {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_TASK_Add/Init',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryExp();
	}

	function queryExp() {
		var form = document.getElementById("form");
		form.submit();
	}
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
	<spring:message code="sysrunner.title.BatchManageTaskQry" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<form:input path="del_task_id" type="hidden" id="del_task_id" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskName" /></td>
				<td><form:input id="query_task_name" path="query_task_name"
						class=".input-large" /></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskType" /></td>
				<td><form:select path="query_task_type">
						<form:option value=""></form:option>
						<form:options items="${BMG_TASK_TYPE}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskStartDay" /></td>
				<td><form:input id="query_task_start_day"
						path="query_task_start_day" class=".input-large" /></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskStartTime" /></td>
				<td><form:input id="query_taskStartTimeStart"
						path="query_taskStartTimeStart"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" cssClass="span2" />-<form:input
						id="query_taskStartTimeEnd" path="query_taskStartTimeEnd"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" cssClass="span2" /></td>
			</tr>
			<tr>
				<td style="text-align: right;" colspan="4">
					<button type="submit" class="btn btn-primary">
						<spring:message code="button.label.Search" />
					</button>
					<button type="button" class="btn btn-primary"
						onclick="createTask()">
						<spring:message code="button.lable.Add" />
					</button>
					<button type="button" class="btn btn-primary">
						<spring:message code="dp.title.Export" />
					</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="250px"><spring:message code="sysrunner.label.TaskName" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="sysrunner.label.TaskType" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="sysrunner.label.TaskStartDay" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="sysrunner.label.TaskStartTime" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 20px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 250px;">${dto.taskName}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${BMG_TASK_TYPE}" key="${dto.taskStartType}" type="label" /></td>
						<td class="vtip" style="text-align: right; width: 50px;">${dto.taskStartDay}</td>
						<td class="vtip" style="text-align: center; width: 50px;">${dto.taskStartTime}</td>
						<td class="tbl_page_td_left" width="100px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto.taskId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
								<input type="button" id="modifyBtn" class="btn btn-small"
									onclick="modify('${f:h(dto.taskId)}')"
									value="<spring:message code="button.lable.Modify"/>"> <input
									type="button" id="deleteBtn" class="btn btn-small"
									onclick="del('${f:h(dto.taskId)}')"
									value="<spring:message code="button.lable.Del"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}"
			query="query_taskStartTimeStart=${taskManageForm.query_taskStartTimeStart}&query_task_name=${taskManageForm.query_task_name}&query_task_type=${taskManageForm.query_task_type}&query_task_start_day=${taskManageForm.query_task_start_day}&query_taskStartTimeEnd=${taskManageForm.query_taskStartTimeEnd}" />
	</div>
</div>
