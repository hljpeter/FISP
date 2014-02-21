<script type="text/javascript">
	$(function() {
		$("#pageTable").find("tr").bind('click', function(){
			var selected_actNo = $(this).find("td:eq(2)").text();
			var old_actNo = $("#selected_actNo").val();
			var selected_subActNo = $(this).find("td:eq(3)").text();
			var old_subActNo = $("#selected_subActNo").val();
			if (old_actNo == selected_actNo && old_subActNo == selected_subActNo) {
				$("#selected_actNo").val("");
				$("#selected_subActNo").val("");
				return;
			} else {
				$("#selected_actNo").val(selected_actNo);
				$("#selected_subActNo").val(selected_subActNo);
				return;
			}
		});
		$("#pageTable").find("tr").bind('dblclick', function() {
			var selected_actNo = $(this).find("td:eq(2)").text();
			var selected_subActNo = $(this).find("td:eq(3)").text();
			showDialog('${pageContext.request.contextPath}/FTZINCOM/BalanceCheckVa?selected_actNo='
					+ selected_actNo +'&selected_subActNo='+ selected_subActNo,'500','1024');
		});
	});
	
	function DtlView() {
		var selected_actNo = $("#selected_actNo").val();
		var selected_subActNo = $("#selected_subActNo").val();
		if(null == selected_actNo || "" == selected_actNo) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZINCOM/BalanceCheckVa?selected_actNo='+
						selected_actNo+'&selected_subActNo='+selected_subActNo, '500', '1024');
				search();
			
		}
	}
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZINCOM/AddQry?page.page="+${page.number+1};
		form.submit();
	}
	

</script>


</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="ftzInCommonForm">
			<form:form commandName="ftzInCommonForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftz.label.balanceValidationQry" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZINCOM/AddQry"
		method="post" modelAttribute="ftzInCommonForm" class="form-horizontal">
		<form:hidden path="selected_actNo" id="selected_actNo" />
		<form:hidden path="selected_subActNo" id="selected_subActNo" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID" />：</td>
				<td><form:select path="query_branchId">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.SUBMIT_DATE" />：</td>
				
				<td><form:input id="query_submitDate_start"
						path="query_submitDate_start" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MAIN_ACCOUNT_NO" />：</td>
				<td><form:input id="query_accountNo" path="query_accountNo"
						class=".input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.SUB_ACCOUNT_NO" />：</td>
				<td><form:input id="query_subAccountNo"
						path="query_subAccountNo" class=".input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="query_currency">
						<option value=""></option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message
						code="ftz.label.accountNoValidaResult" />：</td>
				<td><form:input id="balanceResult"
						path="balance_result" class=".input-large" /></td>
				
			</tr>
			<tr>
				<td style="text-align: right;" colspan="4">
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
			class="table table-striped table-bordered table-condensed tbl_page" border="1">
			<thead>
				<p class="text-info" align="center">
					<spring:message code="ftz.label.MSG_List" />
				</p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.BRANCH_ID" /></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message
							code="ftz.label.ACCOUNT_NOS" /></th>
					<th style="vertical-align: middle; text-align: center" width="65px"><spring:message
							code="ftz.label.SUB_ACCOUNT_NOS" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="50px"><spring:message code="ftz.label.SUBMIT_DATES" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="50px"><spring:message code="ftz.label.CURRENCY" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="50px"><spring:message code="ftz.label.accountNoValidaTime" /></th>
						<th style="vertical-align: middle; text-align: center"
						width="50px"><spring:message code="ftzmis.title.FtzCommonBalance.result" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 1">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 40px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 60px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: left; width: 65px;">${dto.subAccountNo}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${SYS_CURRENCY}" key="${dto.currency}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.checkDatetime}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.checkStatus}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


<div class="pagination pull-right" style="margin-top: 10px;">
	<table class="text-center">
		<tr>
			<td width="50%" align="center"><input id="add" type="button"
				class="btn btn-primary" onclick="DtlView();"
				value="<spring:message code="ftz.label.detailsView" />"> <input
				id="upt" type="button" class="btn btn-primary"
				onclick="allValidation();"
				value="<spring:message code="ftz.label.allValidation" />"></td>
			<td width="50%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${ftzInCommonForm.query_branchId}&query_submitDate_start=${FTZ210203Form.query_submitDate_start}&query_accountNo=${FTZ210203Form.query_accountNo}&query_subAccountNo=${FTZ210203Form.query_subAccountNo}&query_currency=${FTZ210203Form.query_}}"
								action="/FTZINCOM/AddQry" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
