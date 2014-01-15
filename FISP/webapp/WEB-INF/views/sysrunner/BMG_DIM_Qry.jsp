<script type="text/javascript">
	//detail button
	function detailSearch(dimTypeId,dimensionId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_DIM_Qry/Dtl?dimTypeId='
								+ dimTypeId+"&dimensionId="+dimensionId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryDim();
	}

	//modify button
	function modify(dimTypeId,dimensionId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_TASK_Upt/Init?taskId='
								+ taskId + '&ReflashFlag=',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryDim();
	}

	//del button
	function del(dimTypeId,dimensionId) {
		document.getElementById("del_dimTypeId").value = dimTypeId;
		document.getElementById("del_dimensionId").value = dimensionId;
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_DIM_Qry/Del";
		var msg = $("#confirmMsg1").val() + $("#deleteBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	}

	// button
	function createDim() {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/BMG_DIM_Add/Init',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryDim();
	}

	function queryDim() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_DIM_Qry/Qry";
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
	<spring:message code="sysrunner.title.BatchManageTaskDim" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_DIM_Qry/Qry"
		method="post" modelAttribute="batchManageForm" class="form-horizontal">
		<form:input path="del_dimTypeId" type="hidden" id="del_dimTypeId" />
		<form:input path="del_dimensionId" type="hidden" id="del_dimensionId" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimType" /></td>
				<td><form:input id="query_dimTypeId" path="query_dimTypeId"
						class="input-large" /></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimId" /></td>
				<td><form:input id="query_dimensionId" path="query_dimensionId"
						class="input-large" /></td>
			</tr>

			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.TaskDimName" /></td>
				<td><form:input id="query_dimensionName"
						path="query_dimensionName" class="input-large" /></td>
				<td style="text-align: right;" colspan="2">
					<button type="submit" class="btn btn-primary">
						<spring:message code="button.label.Search" />
					</button>
					<button type="button" class="btn btn-primary"
						onclick="createDim()">
						<spring:message code="button.lable.Add" />
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
					<th class="tbl_page_th" width="20px">No.</th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="sysrunner.label.TaskDimType" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="sysrunner.label.TaskDimId" /></th>
					<th class="tbl_page_th" width="200px"><spring:message
							code="sysrunner.label.TaskDimName" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
						<td class="tbl_page_td_left vtip" width="100px">${dto.dimTypeId}</td>
						<td class="tbl_page_td_left vtip" width="100px">${dto.dimensionId}</td>
						<td class="tbl_page_td_right vtip" width="200px">${dto.dimensionName}</td>
						<td align="center" width="100px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small" id="deleteBtn"
									onclick="del('${dto.dimTypeId}','${dto.dimensionId}')"
									value="<spring:message code="button.lable.Del"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<!-- page and buttons -->
<div class="pagination pull-right"
	style="margin-top: 5px; margin-bottom: 40px;">
	<div class="leftPage">
		<util:pagination page="${page}" action="/BMG_DIM_Qry/Qry" query="query_dimensionId=${batchManageForm.query_dimensionId}&query_dimTypeId=${batchManageForm.query_dimTypeId}&query_dimensionName=${batchManageForm.query_dimensionName}"/>
	</div>
</div>

