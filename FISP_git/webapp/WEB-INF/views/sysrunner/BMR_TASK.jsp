<script type="text/javascript">
	$(function() {
		var day_check = document.getElementById("day_task_flag").checked;
		var month_check = document.getElementById("month_task_flag").checked;
		var season_check = document.getElementById("season_task_flag").checked;
		var year_check = document.getElementById("year_task_flag").checked;
		var cus_check = document.getElementById("cus_task_flag").checked;
		if (null == day_check || day_check == false) {
			day_div.style.display = "none";
		} else {
			day_div.style.display = "block";
		}

		if (null == month_check || month_check == false) {
			month_div.style.display = "none";
		} else {
			month_div.style.display = "block";
		}

		if (null == season_check || season_check == false) {
			season_div.style.display = "none";
		} else {
			season_div.style.display = "block";
		}
		if (null == year_check || year_check == false) {
			year_div.style.display = "none";
		} else {
			year_div.style.display = "block";
		}

		if (null == cus_check || cus_check == false) {
			cus_div.style.display = "none";
		} else {
			cus_div.style.display = "block";
		}

		setInterval(function() {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/BMR_TASK/Qry";
			form.submit();
		}, 20000);
	});

	function changeQuery() {
		var day_check = document.getElementById("day_task_flag").checked;
		var month_check = document.getElementById("month_task_flag").checked;
		var season_check = document.getElementById("season_task_flag").checked;
		var year_check = document.getElementById("year_task_flag").checked;
		var cus_check = document.getElementById("cus_task_flag").checked;
		var day_div = document.getElementById("day_div");
		var month_div = document.getElementById("month_div");
		var season_div = document.getElementById("season_div");
		var year_div = document.getElementById("year_div");
		var cus_div = document.getElementById("cus_div");
		if (null == day_check || day_check == false) {
			day_div.style.display = "none";
		} else {
			day_div.style.display = "block";
		}

		if (null == month_check || month_check == false) {
			month_div.style.display = "none";
		} else {
			month_div.style.display = "block";
		}

		if (null == season_check || season_check == false) {
			season_div.style.display = "none";
		} else {
			season_div.style.display = "block";
		}
		if (null == year_check || year_check == false) {
			year_div.style.display = "none";
		} else {
			year_div.style.display = "block";
		}
		if (null == cus_check || cus_check == false) {
			cus_div.style.display = "none";
		} else {
			cus_div.style.display = "block";
		}
	}

	//detail button
	function detailSearch(taskId, runtimeId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMR_STEP/Init?taskId='
								+ taskId + "&runtimeId=" + runtimeId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}

	//detail button
	function query() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMR_TASK/Qry";
		form.submit();
	}
</script>

<br>
<div class="page_title">
	<spring:message code="sysrunner.title.BatchMonitorTask" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/taskRefer"
		method="post" modelAttribute="batchMonitorForm"
		class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskRunStartDay" /></td>
				<td colspan="3"><form:input id="query_bat_date"
						path="query_bat_date"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						onpropertychange="query()" oninput="query()" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.ShowSelect" /></td>
				<td colspan="3"><form:checkbox path="day_task_flag"
						id="day_task_flag" value="1" cssStyle="margin-right:10px;"
						onclick="changeQuery()" />
					<spring:message code="sysrunner.label.DayTask" /> <form:checkbox
						path="month_task_flag" id="month_task_flag" value="1"
						cssStyle="margin-right:10px;" onclick="changeQuery()" />
					<spring:message code="sysrunner.label.MonthTask" />
					<form:checkbox path="season_task_flag" id="season_task_flag"
						value="1" cssStyle="margin-right:10px;" onclick="changeQuery()" />
					<spring:message code="sysrunner.label.SeasonTask" /> <form:checkbox
						path="year_task_flag" id="year_task_flag" value="1"
						cssStyle="margin-right:10px;" onclick="changeQuery()" />
					<spring:message code="sysrunner.label.YearTask" /> <form:checkbox
						path="cus_task_flag" id="cus_task_flag" value="1"
						cssStyle="margin-right:10px;" onclick="changeQuery()" />
					<spring:message code="sysrunner.label.CustomizeTask" /></td>
			</tr>
		</table>
	</form:form>
</div>


<div class="tbl_search" style="width: 98%;" id="day_div">
	<div class="tbl_page_head">
		<p class="text-info">
			<spring:message code="sysrunner.label.DayTaskList" />
		</p>
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="150px"><spring:message code="sysrunner.label.TaskName" /></th>
					<th style="vertical-align: middle; text-align: center" width="200px"><spring:message
							code="sysrunner.label.TaskRunStat" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMStartTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMEndTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 160px; height: 160px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto1" items="${dayTaskRtmList}" varStatus="i1">
					<tr>
						<td class="vtip" style="text-align: center; width: 20px;">${(i1.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto1.task_Name}</td>
						<td class="vtip" style="text-align: center; width: 200px;"><c:if
								test="${dto1.batTaskRtm.runStatus eq 0 }"><spring:message code="sysrunner.label.RUN_STATUS_NOT_START" /></c:if> <c:if
								test="${dto1.batTaskRtm.runStatus eq 2}"><spring:message code="sysrunner.label.RUN_STATUS_RUNNING" /></c:if> <c:if
								test="${dto1.batTaskRtm.runStatus eq 3}">
								<font color="green"><b><spring:message code="sysrunner.label.RUN_STATUS_END" /></b></font>
							</c:if> <c:if test="${dto1.batTaskRtm.runStatus eq 4}">
								<font color="#9F4D95"><b><spring:message code="sysrunner.label.RUN_STATUS_SKIPPED" /></b></font>
							</c:if> <c:if test="${dto1.batTaskRtm.runStatus eq 5}">
								<b><a style="text-decoration: underline; color: red;"><spring:message code="sysrunner.label.RUN_STATUS_ERROR" /></a></b>
							</c:if> <c:if test="${dto1.batTaskRtm.runStatus eq 6}">
								<font color="#613030"><b><spring:message code="sysrunner.label.RUN_STATUS_FIX" /></b></font>
							</c:if> <c:if test="${dto1.batTaskRtm.runStatus eq 7}"><spring:message code="sysrunner.label.RUN_STATUS_STOP" /></c:if></td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto1.batTaskRtm.startTime}</td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto1.batTaskRtm.endTime}</td>
						<td class="tbl_page_td_left" width="50px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto1.batTaskRtm.taskId)}','${f:h(dto1.runtimeId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="tbl_search" style="width: 98%;" id="month_div">
	<div class="tbl_page_head">
		<p class="text-info">
			<spring:message code="sysrunner.label.MonthTaskList" />
		</p>
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="150px"><spring:message code="sysrunner.label.TaskName" /></th>
					<th style="vertical-align: middle; text-align: center" width="200px"><spring:message
							code="sysrunner.label.TaskRunStat" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMStartTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMEndTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 160px; height: 160px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto2" items="${monthTaskRtmList}" varStatus="i2">
					<tr>
						<td class="vtip" style="text-align: center; width: 20px;">${(i2.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto2.task_Name}</td>
						<td class="vtip" style="text-align: center; width: 200px;"><c:if
								test="${dto2.batTaskRtm.runStatus eq 0 }">未运行</c:if> <c:if
								test="${dto2.batTaskRtm.runStatus eq 2}">运行中</c:if> <c:if
								test="${dto2.batTaskRtm.runStatus eq 3}">
								<font color="green"><b>已完成</b></font>
							</c:if> <c:if test="${dto2.batTaskRtm.runStatus eq 4}">
								<font color="#9F4D95"><b>跳过</b></font>
							</c:if> <c:if test="${dto2.batTaskRtm.runStatus eq 5}">
								<b><a style="text-decoration: underline; color: red;">出错</a></b>
							</c:if> <c:if test="${dto2.batTaskRtm.runStatus eq 6}">
								<font color="#613030"><b>已修复</b></font>
							</c:if> <c:if test="${dto2.batTaskRtm.runStatus eq 7}">已停止</c:if></td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto2.batTaskRtm.startTime}</td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto2.batTaskRtm.endTime}</td>
						<td class="tbl_page_td_left" width="50px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto2.batTaskRtm.taskId)}','${f:h(dto2.runtimeId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="tbl_search" style="width: 98%;" id="season_div">
	<div class="tbl_page_head">
		<p class="text-info">
			<spring:message code="sysrunner.label.SeasonTaskList" />
		</p>
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="150px"><spring:message code="sysrunner.label.TaskName" /></th>
					<th style="vertical-align: middle; text-align: center" width="200px"><spring:message
							code="sysrunner.label.TaskRunStat" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMStartTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMEndTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 160px; height: 160px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto3" items="${seasonTaskRtmList}" varStatus="i3">
					<tr>
						<td class="vtip" style="text-align: center; width: 20px;">${(i3.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto3.task_Name}</td>
						<td class="vtip" style="text-align: center; width: 200px;"><c:if
								test="${dto3.batTaskRtm.runStatus eq 0 }">未运行</c:if> <c:if
								test="${dto3.batTaskRtm.runStatus eq 2}">运行中</c:if> <c:if
								test="${dto3.batTaskRtm.runStatus eq 3}">
								<font color="green"><b>已完成</b></font>
							</c:if> <c:if test="${dto3.batTaskRtm.runStatus eq 4}">
								<font color="#9F4D95"><b>跳过</b></font>
							</c:if> <c:if test="${dto3.batTaskRtm.runStatus eq 5}">
								<b><a style="text-decoration: underline; color: red;">出错</a></b>
							</c:if> <c:if test="${dto3.batTaskRtm.runStatus eq 6}">
								<font color="#613030"><b>已修复</b></font>
							</c:if> <c:if test="${dto3.batTaskRtm.runStatus eq 7}">已停止</c:if></td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto3.batTaskRtm.startTime}</td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto3.batTaskRtm.endTime}</td>
						<td class="tbl_page_td_left" width="50px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto3.batTaskRtm.taskId)}','${f:h(dto3.runtimeId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="tbl_search" style="width: 98%;" id="year_div">
	<div class="tbl_page_head">
		<p class="text-info">
			<spring:message code="sysrunner.label.YearTaskList" />
		</p>
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="150px"><spring:message code="sysrunner.label.TaskName" /></th>
					<th style="vertical-align: middle; text-align: center" width="200px"><spring:message
							code="sysrunner.label.TaskRunStat" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMStartTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMEndTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 160px; height: 160px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto4" items="${yearTaskRtmList}" varStatus="i4">
					<tr>
						<td class="vtip" style="text-align: center; width: 20px;">${(i4.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto4.task_Name}</td>
						<td class="vtip" style="text-align: center; width: 200px;"><c:if
								test="${dto4.batTaskRtm.runStatus eq 0 }">未运行</c:if> <c:if
								test="${dto4.batTaskRtm.runStatus eq 2}">运行中</c:if> <c:if
								test="${dto4.batTaskRtm.runStatus eq 3}">
								<font color="green"><b>已完成</b></font>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 4}">
								<font color="#9F4D95"><b>跳过</b></font>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 5}">
								<b><a href="###"
									onclick="javascript:fixStep('${dto4.batTaskRtm.runtimeId}','${dto4.batTaskRtm.stepId}');"
									style="text-decoration: underline; color: red;">出错</a></b>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 6}">
								<font color="#613030"><b>已修复</b></font>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 7}">已停止</c:if></td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto4.batTaskRtm.startTime}</td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto4.batTaskRtm.endTime}</td>
						<td class="tbl_page_td_left" width="50px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto4.batTaskRtm.taskId)}','${f:h(dto4.runtimeId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="tbl_search" style="width: 98%; margin-bottom: 50px;" id="cus_div">
	<div class="tbl_page_head">
		<p class="text-info">
			<spring:message code="sysrunner.label.CustomizeTaskList" />
		</p>
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center"
						width="150px"><spring:message code="sysrunner.label.TaskName" /></th>
					<th style="vertical-align: middle; text-align: center" width="200px"><spring:message
							code="sysrunner.label.TaskRunStat" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMStartTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="sysrunner.label.TaskRTMEndTime" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 160px; height: 160px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto4" items="${cusTaskRtmList}" varStatus="i4">
					<tr>
						<td class="vtip" style="text-align: center; width: 20px;">${(i4.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto4.task_Name}</td>
						<td class="vtip" style="text-align: center; width: 200px;"><c:if
								test="${dto4.batTaskRtm.runStatus eq 0 }">未运行</c:if> <c:if
								test="${dto4.batTaskRtm.runStatus eq 2}">运行中</c:if> <c:if
								test="${dto4.batTaskRtm.runStatus eq 3}">
								<font color="green"><b>已完成</b></font>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 4}">
								<font color="#9F4D95"><b>跳过</b></font>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 5}">
								<b><a href="###"
									onclick="javascript:fixStep('${dto4.batTaskRtm.runtimeId}','${dto4.batTaskRtm.stepId}');"
									style="text-decoration: underline; color: red;">出错</a></b>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 6}">
								<font color="#613030"><b>已修复</b></font>
							</c:if> <c:if test="${dto4.batTaskRtm.runStatus eq 7}">已停止</c:if></td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto4.batTaskRtm.startTime}</td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto4.batTaskRtm.endTime}</td>
						<td class="tbl_page_td_left" width="50px">
							<div align="center" style="height: 25px">
								<input type="button" id="detailBtn" class="btn btn-small"
									onclick="detailSearch('${f:h(dto4.batTaskRtm.taskId)}','${f:h(dto4.runtimeId)}')"
									value="<spring:message code="button.lable.DeatilSearch"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

