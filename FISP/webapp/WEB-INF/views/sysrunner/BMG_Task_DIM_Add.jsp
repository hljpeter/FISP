<script type="text/javascript">
	//init page
	$(function() {
		var dimIdList1 = document.getElementsByName('dimId');
		for ( var i = 0; i < dimIdList1.length; i++) {
			var dimId_tmp1 = dimIdList1[i].value;
			var taskStartDay= "#taskStartDay_"+ dimId_tmp1 ;
			var polling_div ="polling_div_"+ dimId_tmp1 ;
			var manual_div = "manual_div_"+ dimId_tmp1 ;
			var taskStartType ="#taskStartType_"+ dimId_tmp1 ;
			var s= $(taskStartType).val();
			if ('2' == s || '3' == s || '4' == s || '5' == s || '6' == s) {
				
				$(taskStartDay).removeAttr("disabled");
			} else {
				$(taskStartDay).attr("disabled", "true");
			}

			if ('B' == s) {
				document.getElementById(polling_div).style.display = 'table-row';
			} else {
				document.getElementById(polling_div).style.display = 'none';
				if ('0' != s) {
					document.getElementById(manual_div).style.display = 'table-row';
				} else {
					document.getElementById(manual_div).style.display = 'none';
				}
			}
		}
		

		$("#dimTable").find("tr").bind('click', function() {
			$("#selDimId").val($(this).find("td:eq(2)").text());
			var dimId = $(this).find("td:eq(2)").text();
			var dimIdList = document.getElementsByName('dimId');
			for ( var i = 0; i < dimIdList.length; i++) {
				var dimId_tmp = dimIdList[i];
				if (dimId == dimId_tmp.value) {
					var divId = 'div_' + dimId;
					document.getElementById(divId).style.display = 'block';
				} else {
					var divId = 'div_' + dimId_tmp.value;
					document.getElementById(divId).style.display = 'none';
				}
			}

		});
	});
	function createTask() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_Task_DIM_Add/SubmitDim";
		var msg = $("#confirmMsg1").val() + $("#create").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};

	function changeTaskType(s,dimensionId) {
		var taskStartDay= "#taskStartDay_"+ dimensionId ;
		var polling_div ="polling_div_"+ dimensionId ;
		var manual_div = "manual_div_"+ dimensionId ;
		if ('2' == s || '3' == s || '4' == s || '5' == s || '6' == s) {
			
			$(taskStartDay).removeAttr("disabled");
		} else {
			$(taskStartDay).attr("disabled", "true");
		}

		if ('B' == s) {
			document.getElementById(polling_div).style.display = 'table-row';
		} else {
			document.getElementById(polling_div).style.display = 'none';
			if ('0' != s) {
				document.getElementById(manual_div).style.display = 'table-row';
			} else {
				document.getElementById(manual_div).style.display = 'none';
			}
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
	<spring:message code="sysrunner.title.BatchManageTaskDimInfo" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/taskReferModify"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<input id="selDimId" type="hidden" />
		<div class="row">
			<div class="tbl_page_head">
				<table
					class="table table-striped table-bordered table-condensed tbl_page">
					<thead>
						<tr>
							<th class="tbl_page_th" width="20px"></th>
							<th class="tbl_page_th" width="100px"><spring:message
									code="sysrunner.label.TaskDimType" /></th>
							<th class="tbl_page_th" width="100px"><spring:message
									code="sysrunner.label.TaskDimId" /></th>
							<th class="tbl_page_th" width="200px"><spring:message
									code="sysrunner.label.TaskDimName" /></th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tbl_page_body"
				style="min-height: 155px; height: 155px; overflow-x: hidden; overflow-y: auto;">
				<table id="dimTable"
					class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
						<c:forEach var="dto" items="${batchManageForm.batTaskDimVOs}"
							varStatus="i">
							<tr>
								<td width="20px"><form:checkbox path="batTaskDimVOs[${i.index}].selectedFalg" value="1"/></td>
								<td class="tbl_page_td_left vtip" width="100px">${dto.dimTypeId}</td>
								<td class="tbl_page_td_left vtip" width="100px">${dto.dimensionId}</td>
								<td class="tbl_page_td_right vtip" width="200px">${dto.dimensionName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="row" style="margin-bottom: 40px;">
			<c:forEach var="dto1" items="${batchManageForm.batTaskDimVOs}"
				varStatus="i1">
				<div id="div_${dto1.dimensionId}" style="display: none;">
					<input name="dimId" value="${dto1.dimensionId}" type="hidden" />
					<table class="tbl_search">
						<tr>
							<td class="label_td"><font color="red">*</font> <spring:message
									code="sysrunner.label.TaskId" /></td>
							<td><form:input id="taskId_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskId" class="input-large"
									readonly="true" /></td>
							<td class="label_td"><font color="red">*</font> <spring:message
									code="sysrunner.label.TaskDimType" /></td>
							<td><form:input id="dimTypeId_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].dimTypeId" class="input-large"
									readonly="true" /></td>

						</tr>
						<tr>
							<td class="label_td"><font color="red">*</font> <spring:message
									code="sysrunner.label.TaskDimId" /></td>
							<td><form:input id="dimensionId_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].dimensionId"
									class="input-large" readonly="true" /></td>
							<td class="label_td"><font color="red">*</font> <spring:message
									code="sysrunner.label.TaskType" /></td>
							<td><form:select id="taskStartType_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskStartType"
									onchange="changeTaskType(this.value,'${dto1.dimensionId}')">
									<form:option value=""></form:option>
									<form:options items="${BMG_TASK_TYPE}" />
								</form:select></td>
						</tr>
						<tr>
							<td class="label_td">&nbsp&nbsp&nbsp<spring:message
									code="sysrunner.label.TaskStartDay" /></td>
							<td><form:input id="taskStartDay_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskStartDay"
									class=".input-large" /></td>
							<td class="label_td"><font color="red">*</font> <spring:message
									code="sysrunner.label.TaskStartTime" /></td>
							<td><form:input id="taskStartTime_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskStartTime"
									onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
						</tr>
						<tr>
							<td class="label_td"><font color="red">*</font> <spring:message
									code="sysrunner.label.TaskEnable" /></td>
							<td><form:select id="taskEnable_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskEnable">
									<form:option value=""></form:option>
									<form:options items="${BMG_FLAG}" />
								</form:select></td>
						</tr>
						<tr id="polling_div_${dto1.dimensionId}" style="display: none;">
							<td class="label_td">&nbsp&nbsp&nbsp <spring:message
									code="sysrunner.label.TaskEndTime" /></td>
							<td><form:input id="taskEndTime_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskEndTime"
									onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
							<td class="label_td">&nbsp&nbsp&nbsp <spring:message
									code="sysrunner.label.TaskPollingInterval" /></td>
							<td><form:input id="taskPollingInterval_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskPollingInterval"
									class="input-large" /></td>
						</tr>
						<tr id="manual_div_${dto1.dimensionId}" style="display: none;">
							<td class="label_td">&nbsp&nbsp&nbsp <spring:message
									code="sysrunner.label.TaskStartTN" /></td>
							<td colspan="3"><form:input
									id="taskStartTn_${dto1.dimensionId}"
									path="batTaskDimVOs[${i1.index}].taskStartTn"
									class="input-large" /></td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>

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
