<script type="text/javascript">
	$(function() {
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(3)").text();
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
							var selected_msgId = $(this).find("td:eq(3)")
									.text();
							var selected_msgNo = $(this).find("td:eq(8)")
									.text();
							showDialog('${pageContext.request.contextPath}/FTZ210202/QryAuthRedirect?selected_msgId='
									+ selected_msgId
									+ "&selected_msgNo="
									+ selected_msgNo,'500','1024');
							queryFTZ210101();

						});
	});

	function showAllDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgNo = $("#selected_msgNo").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert("请选择一条批量数据！");
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZ210202/QryAuthRedirect?selected_msgId='
					+ selected_msgId + "&selected_msgNo="
					+ selected_msgNo,'500','1024');
			queryFTZ210202();
		}
	}
	function showDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgNo = $("#selected_msgNo").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert("请选择一条批量数据！");
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZ210202/QryAuthRedirect?selected_msgId='
					+ selected_msgId + "&unAuthFlag=1"
					+ "&selected_msgNo=" + selected_msgNo,'500','1024');
			queryFTZ210202();
		}
	}
	function queryFTZ210202() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210202/AuthQry";
		form.submit();
	}
</script>


</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="fTZ210202Form">
			<form:form commandName="fTZ210202Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.2101.auth" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210202/AuthQry"
		method="post" modelAttribute="FTZ210202Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="selected_msgNo" id="selected_msgNo" />
			<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCHID" />：</td>
				<td><form:hidden path="ftzInMsgCtl.branchId" id="branchId1" />
				<form:select path="ftzInMsgCtl.branchId" disabled="true" id="branchId">
					<form:option value=""></form:option>
					<form:options items="${SM_0002}"/>
				</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="msgId" path="ftzInMsgCtl.msgId"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="submitDate" path="ftzInMsgCtl.submitDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" readOnly="true"/></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUSS" />：</td>
				<td><form:select path="ftzInMsgCtl.msgStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="accountNo" path="ftzInMsgCtl.accountNo"
						class=".input-large" onblur="accountFill()" readOnly="true"/>
					<button type="button" class="btn btn-small" onclick="queryAct()">  
						<spring:message code="button.label.Search" />
					</button></td>				
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="accountName" path="ftzInMsgCtl.accountName"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.CURRENCY" />：</td>
				<td>
				<form:hidden path="ftzInMsgCtl.currency" id="currency1"/>
				<form:select path="ftzInMsgCtl.currency" id="currency"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>

				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="ftzInMsgCtl.balance"
						value="${FTZ210202Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" />	
			</tr>
			<tr>
				<td class="label_td" colspan="2"><font color="red">* </font> <spring:message
						code="ftz.label.BALANCE_CODE" /><form:hidden
						path="ftzInMsgCtl.balanceCode" id="balanceCode1" /> <form:select
						id="balanceCode" path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.ACC_ORG_CODE" />：</td>
				<td><form:input id="accOrgCode" path="ftzInMsgCtl.accOrgCode"
						class=".input-large" readonly="true" /></td>	
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.result" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_PROC_RESULT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.ADDWORD" />：</td>
				<td colspan="3"><form:input id="chkAddWord"
						path="ftzInTxnDtl.chkAddWord" class="input-xxlarge"
						readonly="true" /></td>
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
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.SUBMIT_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message
							code="ftz.label.BRANCH" /></th>
					<th style="vertical-align: middle; text-align: center" width="70px"><spring:message
							code="ftz.label.MSG_ID" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="120px"><spring:message code="ftz.label.MSG_TYPE" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="130px"><spring:message code="ftz.label.ACCOUNT_NO" /></th>
					<th style="vertical-align: middle; text-align: center" width="20px"><spring:message
							code="fisp.rq.sumCnt" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="fisp.rq.authCnt" /></th>

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
						<td class="vtip" style="text-align: center; width: 50px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 60px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 70px;">${dto.msgId}</td>
						<td class="vtip" style="text-align: left; width: 120px;"><t:codeValue
								items="${FTZ_2101_MSG}" key="${dto.msgNo}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 130px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: right; width: 20px;">${dto.totalCount}</td>
						<td class="vtip" style="text-align: right; width: 40px;">${dto.rsv1}</td>
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
			<td width="50%" align="center"><input id="detail" type="button"
				class="btn btn-primary" onclick="showAllDetail();"
				value="<spring:message code="ftz.label.MSG_ALL_Dtl" />"> <input
				id="detail" type="button" class="btn btn-primary"
				onclick="showDetail();"
				value="<spring:message code="ftz.label.MSG_UNAUTH_Dtl" />"></td>
			<td width="50%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${FTZ210101Form.query_branchId}&query_submitDate_start=${FTZ210101Form.query_submitDate_start}&query_submitDate_end=${FTZ210101Form.query_submitDate_end}&query_msgId=${FTZ210101Form.query_msgId}&query_msgNo=${FTZ210101Form.query_msgNo}&query_accountNo=${FTZ210101Form.query_accountNo}"
								action="/FTZ210101/AuthQry" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>


