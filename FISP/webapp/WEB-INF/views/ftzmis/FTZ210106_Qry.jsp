<script type="text/javascript">
	$(function() {
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(3)").text();
			var selected_msgNo = $(this).find("td:eq(7)").text();
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
							var selected_msgId = $(this).find("td:eq(3)")
									.text();
							var selected_msgNo = $(this).find("td:eq(7)").text();
							window
									.showModalDialog(
											'${pageContext.request.contextPath}/FTZ210106/QryRedirect?selected_msgId='
													+ selected_msgId+"&selected_msgNo="+selected_msgNo,
											window,
											'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');

						});
	});

	function showDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgNo = $("#selected_msgNo").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert("请选择一条批量数据！");
			return;
		} else {
			window
					.showModalDialog(
							'${pageContext.request.contextPath}/FTZ210106/QryRedirect?selected_msgId='
									+ selected_msgId+"&selected_msgNo="+selected_msgNo,
							window,
							'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}
	}
	function queryFTZ210106() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210106/Qry";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210106Form">
			<form:form commandName="FTZ210106Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210106.qry" /></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210106/Qry"
		method="post" modelAttribute="FTZ210106Form" class="form-horizontal">
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
				<td class="label_td"><spring:message code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="query_submitDate_start"
						path="query_submitDate_start"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" />-<form:input
						id="query_submitDate_end" path="query_submitDate_end"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="query_msgId" path="query_msgId"
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="query_accountName" path="query_accountName"
						class=".input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NO" /></td>
				<td><form:input id="query_accountNo" path="query_accountNo"
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.SUB_ACCOUNT_NO" />�</td>
				<td><form:input id="query_subAccountNo"
						path="query_subAccountNo" class=".input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUSS" />�</td>
				<td><form:select path="query_msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
				<td style="text-align: right;" colspan="2">
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
				<p class="text-info" align="center"><spring:message code="ftz.label.MSG_DTL_List" /></p>
				<tr>
					<tr>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="20px"><spring:message code="fisp.label.common.no" /></th>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="60px"><spring:message code="ftz.label.CD_FLAG" /></th>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.TRAN_DATE" /></th>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="40px"><spring:message code="ftz.label.AMOUNT" /></th>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.OPP_NAME" /></th>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="30px"><spring:message code="ftz.label.VALUE_DATE" /></th>
					<th class="vtip" style="vertical-align: middle; text-align: center" width="30px"><spring:message code="ftz.label.EXPIRE_DATE" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto1" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 30px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 40px;"><t:codeValue items="${FTZ_CD_FLAG}" key="${dto1.cdFlag}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 40px;">${dto1.tranDate}</td>
						<td class="vtip" style="text-align: right; width: 40px;"><t:moneyFormat	type="label" value="${dto1.amount}" /></td>
						<td class="vtip" style="text-align: right; width: 50px;">${dto1.oppName}</td>
						<td class="vtip" style="text-align: right; width: 30px;">${dto1.valueDate}</td>
						<td class="vtip" style="text-align: right; width: 30px;">${dto1.expireDate}</td>
						<td style="display: none;">${dto1.msgId}</td>
						<td style="display: none;">${dto1.seqNo}</td>
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
				class="btn btn-primary" onclick="showDetail();" value="<spring:message code="ftz.label.MSG_Dtl" />"></td>
			<td width="30%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${FTZ210106Form.query_branchId}&query_submitDate_start=${FTZ210106Form.query_submitDate_start}&query_submitDate_end=${FTZ210106Form.query_submitDate_end}&query_msgId=${FTZ210106Form.query_msgId}&query_accountName=${FTZ210106Form.query_accountName}&query_accountNo=${FTZ210106Form.query_accountNo}&query_subAccountNo=${FTZ210106Form.query_subAccountNo}&query_msgStatus=${FTZ210106Form.query_msgStatus}" action="/FTZ210106/Qry"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

