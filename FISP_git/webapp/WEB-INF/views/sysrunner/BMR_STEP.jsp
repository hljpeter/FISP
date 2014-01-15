
<script type="text/javascript">
	function fixStep(runtimeId, stepId) {
		if (confirm('确认修复该步骤吗?')) {
			document.getElementById("form").action = "${pageContext.request.contextPath}/BMR_STEP/fixstep";
			document.getElementById("form").submit();
		}
	}
	function SkipStep(runtimeId, stepId) {
		if (confirm('确认跳过该步骤吗?')) {
			document.getElementById("form").action = "${pageContext.request.contextPath}/BMR_STEP/skipstep";
			document.getElementById("form").submit();
		}
	}
	
	//detail button
	function detailSearch(runtimeId, stepId) {
		var taskId = $("#taskId").val();
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMR_STEP/WorkInfo?taskId='
								+ taskId + "&runtimeId=" + runtimeId+"&stepId="+stepId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="batchMonitorForm">
			<form:form commandName="batchMonitorForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="sysrunner.title.BatchMonitorStep" />
</div>

<form:form action="${pageContext.request.contextPath}/bm04/detail"
	id="form" modelAttribute="batchMonitorForm">
	<form:input path="runtimeId" type="hidden" id="runtimeId" />
	<form:input path="taskId" type="hidden" id="taskId" />

	<div id="main_body" class="row">
		<div style="height: 320px; overflow-y: auto;" class="tbl_page_body">
			<table
				class="table table-striped table-bordered table-condensed tbl_page">
				<thead>
					<tr>
						<th style="vertical-align: middle; text-align: center"
							width="20px">No</th>
						<th style="vertical-align: middle; text-align: center"
							width="60px"><spring:message
								code="sysrunner.label.TaskName" /></th>
						<th style="vertical-align: middle; text-align: center"
							width="200px"><spring:message
								code="sysrunner.label.StepName" /></th>
						<th style="vertical-align: middle; text-align: center"
							width="200px"><spring:message
								code="sysrunner.label.StepRunStat" /></th>
						<th style="vertical-align: middle; text-align: center"
							width="100px"><spring:message
								code="sysrunner.label.StepRTMStartTime" /></th>
						<th style="vertical-align: middle; text-align: center"
							width="100px"><spring:message
								code="sysrunner.label.StepRTMEndTime" /></th>
						<th style="vertical-align: middle; text-align: center"
							width="50px"><spring:message code="index.label.sm.Operation" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dto" items="${stepList}" varStatus="i">
						<tr>
							<td style="text-align: center;">${(i.index+1)}</td>
							<td class="vtip" style="text-align: left;">${dto.taskName}</td>
							<td class="vtip" style="text-align: left;">${dto.stepName}</td>
							<td style="text-align: center;"><c:if
									test="${dto.runStatus eq 0 }">未运行</c:if> <c:if
									test="${dto.runStatus eq 2}">运行中</c:if> <c:if
									test="${dto.runStatus eq 3}">
									<font color="green"><b>已完成</b></font>
								</c:if> <c:if test="${dto.runStatus eq 4}">
									<font color="#9F4D95"><b>跳过</b></font>
								</c:if> <c:if test="${dto.runStatus eq 5}">
									<font color="red"><b>出错</b></font>
									<b><a href="###"
										onclick="javascript:fixStep('${dto.runtimeId}','${dto.stepId}');"
										style="text-decoration: underline; color: red;">修复步骤</a></b>
									<b><a href="###"
										onclick="javascript:SkipStep('${dto.runtimeId}','${dto.stepId}');"
										style="text-decoration: underline; color: red;">跳过步骤</a></b>
								</c:if> <c:if test="${dto.runStatus eq 6}">
									<font color="#613030"><b>已修复</b></font>
								</c:if> <c:if test="${dto.runStatus eq 7}">已停止</c:if></td>
							<td style="text-align: center;">${dto.startTime}</td>
							<td style="text-align: center;">${dto.endTime}</td>
							<td class="tbl_page_td_left" width="50px">
								<div align="center" style="height: 25px">
									<c:if test="${dto.runStatus ne 0 && dto.runStatus ne 2}">
										<input type="button" id="detailBtn" class="btn btn-small"
											onclick="detailSearch('${dto.runtimeId}','${dto.stepId}')"
											value="<spring:message code="button.lable.DeatilSearch"/>">
									</c:if>
									<c:if test="${dto.runStatus eq 0 ||dto.runStatus eq 2}">
										<input type="button" id="detailBtn" class="btn btn-small"
											onclick="detailSearch('${dto.runtimeId}','${dto.stepId}')"
											value="<spring:message code="button.lable.DeatilSearch"/>" disabled="disabled">
									</c:if>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

</form:form>


<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>