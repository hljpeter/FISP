<script type="text/javascript">
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
	});
	
</script>


<div class="page_title">
	<spring:message code="sysrunner.title.BatchManageTaskDtl" />
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
						class="input-large" disabled="true"/></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskType" /></td>
				<td><form:select id="taskStartType"
						path="batTaskInfo.taskStartType"  disabled="true">
						<form:option value=""></form:option>
						<form:options items="${BMG_TASK_TYPE}" />
					</form:select></td>
			</tr>

			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskStartDay" /></td>
				<td><form:input id="taskStartDay"
						path="batTaskInfo.taskStartDay" class=".input-large"  disabled="true" /></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskStartTime" /></td>
				<td><form:input id="taskStartTime"
						path="batTaskInfo.taskStartTime"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"  disabled="true"/></td>
			</tr>
			<tr>

				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskAllowMultiple" /></td>
				<td><form:select id="allowMultiple"
						path="batTaskInfo.allowMultiple"  disabled="true">
						<form:option value=""></form:option>
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimType" /></td>
				<td><form:input id="dimTypeId" path="batTaskInfo.dimTypeId"
						class="input-large"  disabled="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskEnable" /></td>
				<td><form:select id="taskEnable" path="batTaskInfo.taskEnable"  disabled="true">
						<form:option value=""></form:option>
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskAllowManual" /></td>
				<td><form:select id="allowManual"
						path="batTaskInfo.allowManual"  disabled="true">
						<form:option value=""></form:option>
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
			</tr>
			<tr id="polling_div">
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskEndTime" /></td>
				<td><form:input id="taskEndTime" path="batTaskInfo.taskEndTime"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"  disabled="true"/></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskPollingInterval" /></td>
				<td><form:input id="taskPollingInterval"
						path="batTaskInfo.taskPollingInterval" class="input-large"  disabled="true"/></td>
			</tr>
			<tr id="manual_div">
				<td class="label_td"><font color="red">*</font>
				<spring:message code="sysrunner.label.TaskStartTN" /></td>
				<td colspan="3"><form:input id="taskStartTn"
						path="batTaskInfo.taskStartTn" class="input-large"  disabled="true"/></td>
			</tr>
		</table>
	</form:form>
</div>

<c:if test="${!empty list}">
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
								items="${BMG_TASK_TYPE}" key="${dto.stepStartType}" type="label" /></td>
						<td class="tbl_page_td_center vtip" width="100px">${dto.priority}</td>
						<td class="tbl_page_td_left" width="140px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto.taskId)}','${f:h(dto.stepId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>"  disabled="disabled">
								<input type="button" id="modifyBtn" class="btn btn-small"
									onclick="modify('${f:h(dto.taskId)}','${f:h(dto.stepId)}')"
									value="<spring:message code="button.lable.Modify"/>"  disabled="disabled"> <input
									type="button" id="deleteBtn" class="btn btn-small"
									onclick="del('${f:h(dto.taskId)}','${f:h(dto.stepId)}')"
									value="<spring:message code="button.lable.Del"/>" disabled="disabled">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</c:if>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>