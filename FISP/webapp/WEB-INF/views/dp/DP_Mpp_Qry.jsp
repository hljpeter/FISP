<script type="text/javascript">
	//detail button
	function detailSearch(mppId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Mpp_Dtl/Init?dtl_MppId='
								+ mppId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryMpp();
	};

	//modify button
	function mppCfgModify(mppId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Mpp_Upt/Init?mod_MppId='
								+ mppId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryMpp();
	};

	//FieldMpp button
	function FieldMpp(mppId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Mpp_Cfg/Init?cdg_MppId='
								+ mppId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryMpp();
	};

	function mppAdd() {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/DP_Mpp_Add/Init',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryMpp();
	};

	//del button
	function mppCfgDel(mppId) {
		document.getElementById("del_MppId").value = mppId;
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Mpp_Qry/Del";
		var msg = $("#confirmMsg1").val() + $("#deleteBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};
	
	function queryMpp(){
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Mpp_Qry/Qry";
		form.submit();
	}
	
	function queryOrg() {
		showAllOrg([ {
			"query_branchId" : "param1"
		} ]);
	};
	
	function querydestTable() {
		showSelTable([ {
			"query_destTable" : "param2"
		} ]);
	};
	function querysrcTable() {
		showSelTable([ {
			"query_srcTable" : "param2"
		} ]);
	};
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="DP_Mpp_QryForm">
			<form:form commandName="DP_Mpp_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.MapQry" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/DP_Mpp_Qry/Qry"
		modelAttribute="DP_Mpp_QryForm" class="form-horizontal">
		<input id="del_MppId" type="hidden" name="del_MppId" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="de.label.procType" /></td>
				<td><form:select id="query_procType" path="query_procType">
						<form:option value=""></form:option>
						<form:options items="${DP_0020}" />
					</form:select></td>
				<td class="label_td"><spring:message code="de.label.branchId" /></td>
				<td><form:input id="query_branchId" path="query_branchId"
						type="text" class="input-large" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/> <input type="button"
					class="btn btn-small" onclick="queryOrg()"
					value="<spring:message code="button.label.Search"/>"></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.destTable" /></td>
				<td><form:input id="query_destTable" path="query_destTable"
						type="text" class="input-large" /> <input type="button"
					class="btn btn-small" onclick="querydestTable()"
					value="<spring:message code="button.label.Search"/>"></td>
				<td class="label_td"><spring:message code="de.label.srcTable" /></td>
				<td><form:input id="query_srcTable" path="query_srcTable"
						type="text" class="input-large" /> <input type="button"
					class="btn btn-small" onclick="querysrcTable()"
					value="<spring:message code="button.label.Search"/>"></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="dp.lable.MppName" /></td>
				<td><form:input path="query_mppName" type="text"
						class="input-large" /></td>
				<td colspan="2" align="right">
					<button type="submit" class="btn btn-primary">
						<spring:message code="button.label.Search" />
					</button>
					<button type="button" class="btn btn-primary" onclick="mppAdd()">
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
							code="index.label.sm.OrganizationName" /></th>
					<th class="tbl_page_th" width="50px"><spring:message
							code="de.label.procType" /></th>
					<th class="tbl_page_th" width="120px"><spring:message
							code="de.label.destTable" /></th>
					<th class="tbl_page_th" width="120px"><spring:message
							code="de.label.srcTable" /></th>
					<th class="tbl_page_th" width="150px"><spring:message
							code="dp.lable.MppName" /></th>
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
				<form:form id="DP_Mpp_QryForm"
					action="${pageContext.request.contextPath}"
					modelAttribute="DP_Mpp_QryForm">

					<c:forEach var="dto" items="${page.content}" varStatus="i">
						<tr>
							<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
							<td class="tbl_page_td_left vtip" width="100px"><font
								size="2px" class="vtip"> <t:codeValue items="${BM_1001}"
										key="${dto.branchId}" type="label" /></font></td>
							<td class="tbl_page_td_left vtip" width="50px"><font
								size="2px" class="vtip"> <t:codeValue items="${DP_0020}"
										key="${dto.procType}" type="label" /></font></td>
							<td class="tbl_page_td_left vtip" width="120px">${dto.destTable}</td>
							<td class="tbl_page_td_left vtip" width="120px">${dto.srcTable}</td>
							<td class="tbl_page_td_left vtip" width="150px">${dto.mppName}</td>
							<td align="center" width="200px">
								<div style="height: 25px">
									<input type="button" class="btn btn-small"
										onclick="detailSearch('${f:h(dto.mppId)}')"
										value="<spring:message code="dp.lable.detail"/>"> <input
										type="button" class="btn btn-small"
										onclick="mppCfgModify('${f:h(dto.mppId)}')"
										value="<spring:message code="button.lable.Update"/>">
									<input type="button" id="deleteBtn" class="btn btn-small"
										onclick="mppCfgDel('${f:h(dto.mppId)}')"
										value="<spring:message code="button.lable.Delete"/>">
									<c:if test="${dto.procType == '3'}">
										<input type="button" class="btn btn-small"
											onclick="FieldMpp('${f:h(dto.mppId)}')"
											value="<spring:message code="button.lable.FieldMpp"/>">
									</c:if>
									<c:if test="${dto.procType != '3'}">
										<input type="button" class="btn btn-small"
											onclick="FieldMpp('${f:h(dto.mppId)}')"
											value="<spring:message code="button.lable.FieldMpp"/>"
											disabled="disabled">
									</c:if>
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
		<util:pagination page="${page}" query="${f:query(DP_Mpp_QryForm)}" />
	</div>
</div>
