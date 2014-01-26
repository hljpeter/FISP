<script type="text/javascript">
	$(function() {
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(4)").text();
			var selected_msgNo = $(this).find("td:eq(8)").text();
			var old_selected_msgId = $("#selected_msgId").val();
			if (null == old_selected_msgId) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_msgNo").val(selected_msgNo);
				return;
			}
			if (old_selected_msgId == selected_msgId) {
				$("#selected_msgId").val("");
				$("#selected_msgNo").val("");
				return;
			}
			if (old_selected_msgId != selected_msgId) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_msgNo").val(selected_msgNo);
				return;
			}

		});
		$("#pageTable")
				.find("tr")
				.bind(
						'dblclick',
						function() {
							var selected_msgId = $(this).find("td:eq(4)")
									.text();
							var selected_msgNo = $(this).find("td:eq(8)")
									.text();
									
							showDialog('${pageContext.request.contextPath}/FTZ210201/QryRedirect?selected_msgId='
								+ selected_msgId
								+ "&selected_msgNo="
								+ selected_msgNo,'500','1024');
						});
	});

	function showDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgNo = $("#selected_msgNo").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
		showDialog('${pageContext.request.contextPath}/FTZ210201/QryRedirect?selected_msgId='
					+ selected_msgId + "&selected_msgNo="
					+ selected_msgNo,'500','1024');
		}
	}
	function queryFTZ210201() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210201/Qry";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210201Form">
			<form:form commandName="FTZ210201Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.2102.qry" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210201/Qry"
		method="post" modelAttribute="FTZ210201Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="selected_msgNo" id="selected_msgNo" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID" />：</td>
				<td><form:select path="query_branchId">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><spring:message
						code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="query_submitDate_start"
						path="query_submitDate_start"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" />-<form:input
						id="query_submitDate_end" path="query_submitDate_end"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="query_msgId" path="query_msgId"
						class=".input-large" onkeyup="numberFormat(this);"
						onbeforepaste="numberFormatCopy(this);" /></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_TYPE" />：</td>
				<td><form:select path="query_msgNo">
						<form:option value=""></form:option>
						<form:options items="${FTZ_2102_MSG}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="query_accountNo" path="query_accountNo"
						class=".input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="query_msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CURRENCY" />：</td>
				<td><form:select path="query_currency">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>
				<td style="text-align: right;" colspan="2">
					<button type="submit" class="btn btn-primary">
						<spring:message code="ftz.label.SELECT_MSG" />
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
				<p class="text-info" align="center">
					<spring:message code="ftz.label.MSG_List" />
				</p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.SUBMIT_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="ftz.label.BRANCH" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="120px"><spring:message code="ftz.label.MSG_TYPE" /></th>
					<th style="vertical-align: middle; text-align: center" width="70px"><spring:message
							code="ftz.label.MSG_ID" /></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message
							code="ftz.label.CURRENCY" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="120px"><spring:message code="ftz.label.ACCOUNT_NO1" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.MSG_STATUS" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: center; width: 40px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 80px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 120px;"><t:codeValue
								items="${FTZ_2102_MSG}" key="${dto.msgNo}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 70px;">${dto.msgId}</td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
								items="${SYS_CURRENCY}" key="${dto.currency}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 120px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: left; width: 40px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto.msgStatus}" type="label" /></td>
						<td style="display: none;">${dto.msgNo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<table class="text-center">
		<tr>
			<td width="50%" align="left"><input id="detail" type="button"
				class="btn btn-primary" onclick="showDetail();"
				value="<spring:message code="ftz.label.MSG_Dtl" />"></td>
			<td width="50%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${FTZ210201Form.query_branchId}&query_submitDate_start=${FTZ210201Form.query_submitDate_start}&query_submitDate_end=${FTZ210201Form.query_submitDate_end}&query_msgId=${FTZ210201Form.query_msgId}&query_accountName=${FTZ210201Form.query_accountName}&query_accountNo=${FTZ210201Form.query_accountNo}&query_subAccountNo=${FTZ210201Form.query_subAccountNo}&query_msgStatus=${FTZ210201Form.query_msgStatus}&query_msgNo=${FTZ210201Form.query_msgNo}&query_currency=${FTZ210201Form.query_currency}"
								action="/FTZ210201/Qry" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

