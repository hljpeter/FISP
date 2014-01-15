<script type="text/javascript">
	//detail button
	function detailSearch(expId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Exp_Dtl/Init?dtl_ExpId='
								+ expId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryExp();
	};
	function expAdd() {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Exp_Add/Init',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryExp();
	};

	
	function queryOrg() {
		showSelOrg([ {
			"query_branchId" : "param1"
		} ]);
	};
	function queryTable() {
		showSelTable([ {
			"query_tableName" : "param2"
		} ]);
	};
	function queryFile() {
		showSelFile('2', [ {
			"query_fileName" : "param2"
		} ]);
	};

	//del button
	function expCfgDel(expId) {
		document.getElementById("del_ExpId").value = expId;
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Exp_Qry/Del";
		var msg = $("#confirmMsg1").val() + $("#deleteBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};

	//modify button
	function expCfgModify(expId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Exp_Upt/Init?mod_ExpId='
								+ expId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryExp();
	};
	
	function queryExp(){
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Exp_Qry/Qry";
		form.submit();
	}
	
	//FieldMpp button
	function FieldMpp(expId) {
		var old_url = window.location.href;
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Exp_Cfg/Init?cfg_ExpId='
								+ expId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	};
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="DP_Exp_QryForm">
			<form:form commandName="DP_Exp_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.ExpQry" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/DP_Exp_Qry/Qry"
		modelAttribute="DP_Exp_QryForm" class="form-horizontal">
		<input id="del_ExpId" type="hidden" name="del_ExpId" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="de.label.branchId" /></td>
				<td><form:input id="query_branchId" path="query_branchId"
						type="text" class="input-large" onkeyup="numberStringFormat(this);"  onbeforepaste="numberStringFormatCopy(this);" /><input type="button"
					class="btn btn-small" onclick="queryOrg()"
					value="<spring:message code="button.label.Search"/>"></td>
				<td class="label_td"><spring:message code="if.label.TABLENAME" /></td>
				<td><form:input id="query_tableName" path="query_tableName"
						type="text" class="input-large" /><input type="button"
					class="btn btn-small" onclick="queryTable()"
					value="<spring:message code="button.label.Search"/>"></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.label.common.fileName" /></td>
				<td><form:input id="query_fileName" path="query_fileName"
						type="text" class="input-large" /><input type="button"
					class="btn btn-small" onclick="queryFile()"
					value="<spring:message code="button.label.Search"/>"></td>
				<td colspan="2" align="right">
					<button type="submit" class="btn btn-primary">
						<spring:message code="button.label.Search" />
					</button>
					<t:ifAuthorized path="DP_Exp_Qry/Add_Btn">
						<button type="button" class="btn btn-primary" onclick="expAdd()">
							<spring:message code="button.lable.Add" />
						</button>
					</t:ifAuthorized>
					<t:ifNotAuthorized path="DP_Exp_Qry/Add_Btn">
						<button type="button" class="btn btn-primary" disabled="disabled">
							<spring:message code="button.lable.Add" />
						</button>
					</t:ifNotAuthorized>
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
							code="de.label.branchId" /></th>
					<th class="tbl_page_th" width="70px"><spring:message
							code="if.label.BATCHNO" /></th>
					<th class="tbl_page_th" width="30px"><spring:message
							code="if.label.SEQNO" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="if.label.TABLENAME" /></th>
					<th class="tbl_page_th" width="250px"><spring:message
							code="if.label.FILENAME" /></th>
					<th class="tbl_page_th" width="200px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<form:form id="DP_Exp_QryForm"
					action="${pageContext.request.contextPath}"
					modelAttribute="DP_Exp_QryForm">

					<c:forEach var="dto" items="${page.content}" varStatus="i">
						<tr>
							<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
							<td class="tbl_page_td_left vtip" width="100px"><font
								size="2px" class="vtip"> <t:codeValue items="${BM_1001}"
										key="${dto.branchId}" type="label" /></font></td>
							<td class="tbl_page_td_left vtip" width="70px">${dto.batchNo}</td>
							<td class="tbl_page_td_left vtip" width="30px">${dto.seqNo}</td>
							<td class="tbl_page_td_left vtip" width="100px">${dto.tableName}</td>
							<td class="tbl_page_td_left vtip" width="250px">${dto.fileName}</td>
							<td align="center" width="200px">
								<div style="height: 25px">
									<input type="button" class="btn btn-small"
										onclick="detailSearch('${f:h(dto.expId)}')"
										value="<spring:message code="dp.lable.detail"/>"> <input
										type="button" class="btn btn-small"
										onclick="expCfgModify('${f:h(dto.expId)}')"
										value="<spring:message code="button.lable.Update"/>">
									<input type="button" id="deleteBtn" class="btn btn-small"
										onclick="expCfgDel('${f:h(dto.expId)}')"
										value="<spring:message code="button.lable.Delete"/>">
									<input type="button" class="btn btn-small"
										onclick="FieldMpp('${f:h(dto.expId)}')"
										value="<spring:message code="button.lable.FieldMpp"/>">
								</div>
							</td>
						</tr>
					</c:forEach>
				</form:form>
			</tbody>
		</table>
	</div>
</div>

<!-- page and buttons -->
<div class="pagination pull-right"
	style="margin-top: 5px; margin-bottom: 0px;">
	<div class="leftPage">
		<util:pagination page="${page}" query="${f:query(DP_Exp_QryForm)}" />
	</div>
</div>
