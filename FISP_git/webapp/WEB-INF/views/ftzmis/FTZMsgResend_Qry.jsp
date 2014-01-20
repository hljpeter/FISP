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
				.bind('dblclick',function() {
					var selected_msgId = $(this).find("td:eq(4)").text();
					window.showModalDialog(
						'${pageContext.request.contextPath}/FTZMsgResend/QryDtl?selected_msgId='
								+ selected_msgId, window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
						});
	});

	function showDetail() {
		var selected_msgId = $("#selected_msgId").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			window.showModalDialog(
				'${pageContext.request.contextPath}/FTZMsgResend/QryDtl?selected_msgId='
						+ selected_msgId, window,
				'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}
	}
	function queryFTZMsgResend() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZMsgResend/Qry";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZMsgResendForm">
			<form:form commandName="FTZMsgResendForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.MsgResend.qry" /></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZMsgResend/Qry"
		method="post" modelAttribute="FTZMsgResendForm" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH" />：</td>
				<td><form:select path="query_branchId">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.SUBMIT_DATE" />：</td>
				<td colspan="2"><form:input id="query_submitDate_start"
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
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td colspan="2"><form:input id="query_accountName" path="query_accountName"
						class=".input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="query_accountNo" path="query_accountNo"
						class=".input-large" onkeyup="numberFormat(this);"
						onbeforepaste="numberFormatCopy(this);" /></td>
				<td class="label_td"><spring:message code="ftz.label.SUB_ACCOUNT_NO" />：</td>
				<td colspan="2"><form:input id="query_subAccountNo"
						path="query_subAccountNo" class=".input-large" onkeyup="numberFormat(this);"
						onbeforepaste="numberFormatCopy(this);" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="query_msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_TYPE" />：</td>
				<td><form:select path="query_msgNo">
						<form:option value=""></form:option>
						<form:options items="${FTZ_2101_MSG}" />
					</form:select></td>
				<td>
				<button type="submit" class="btn btn-primary">
							<spring:message code="button.label.Search" />
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
				<p class="text-info" align="center"><spring:message code="ftz.label.MSG_List" /></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message code="ftz.label.SUBMIT_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message code="index.label.sm.OrganizationName" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message code="ftz.label.MSG_TYPE" /></th>
					<th style="vertical-align: middle; text-align: center" width="70px"><spring:message code="ftz.label.MSG_ID" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message code="ftz.label.ACCOUNT_NAME" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="70px"><spring:message code="ftz.label.ACCOUNT_NO" /></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message code="ftz.label.MSG_STATUS" /></th>
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
						<td class="vtip" style="text-align: left; width: 100px;"><t:codeValue
								items="${FTZ_2101_MSG}" key="${dto.msgNo}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 70px;">${dto.msgId}</td>
						<td class="vtip" style="text-align: left; width: 100px;">${dto.accountName}</td>
						<td class="vtip" style="text-align: left; width: 70px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
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
			<td width="70%" align="center"><input id="detail" type="button"
				class="btn btn-primary" onclick="allResend();" value="<spring:message code="ftz.label.MSG_ALL_RESEND" />">
				<input id="detail" type="button"
				class="btn btn-primary" onclick="showDetail();" value="<spring:message code="ftz.label.MSG_Dtl" />">
				</td>
			<td width="30%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${FTZMsgResendForm.query_branchId}&query_submitDate_start=${FTZMsgResendForm.query_submitDate_start}&query_submitDate_end=${FTZMsgResendForm.query_submitDate_end}&query_msgId=${FTZMsgResendForm.query_msgId}&query_accountName=${FTZMsgResendForm.query_accountName}&query_accountNo=${FTZMsgResendForm.query_accountNo}&query_subAccountNo=${FTZMsgResendForm.query_subAccountNo}&query_msgStatus=${FTZMsgResendForm.query_msgStatus}" action="/FTZMsgResend/Qry"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

