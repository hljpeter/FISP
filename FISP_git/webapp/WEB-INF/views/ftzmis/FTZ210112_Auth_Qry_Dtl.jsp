<script type="text/javascript">
	$(function() {
		var msgStatus = $("#msgStatus").val();
		if("04"==msgStatus){
			$("#authPass").attr("disabled", "disabled");
			$("#authRefuse").attr("disabled", "disabled");
		}else if("03"==msgStatus){
			
			$("#authPass").attr("disabled", "disabled");
		}else{
			$("#sbdetail").removeAttr("disabled");
		}
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(7)").text();
			var selected_seqNo = $(this).find("td:eq(8)").text();
			var old_selected_msgId = $("#selected_msgId").val();
			var old_selected_seqNo = $("#selected_seqNo").val();
			if (null == old_selected_seqNo) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_seqNo").val(selected_seqNo);
				return;
			}
			if (old_selected_seqNo == selected_seqNo) {
				$("#selected_msgId").val("");
				$("#selected_seqNo").val("");
				return;
			}
			if (old_selected_seqNo != selected_seqNo) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_seqNo").val(selected_seqNo);
				
				return;
			}

		});
		$("#pageTable")
		.find("tr")
		.bind(
				'dblclick',
				function() {
					var selected_msgId = $(this).find("td:eq(7)")
							.text();
					var selected_seqNo = $(this).find("td:eq(8)")
							.text();
					showDialog('${pageContext.request.contextPath}/FTZ210112/QryAuthDtlDtl?selected_msgId='
							+ selected_msgId
							+ "&selected_seqNo="
							+ selected_seqNo,'500','1024');
					queryFTZ210112Dtl();
				});
	});
	function authPass() {
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#selected_msgId").val($("#msgId").val()).val();
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210112/AuthDtlSubmit?authStat=1";
		form.submit();
	
	}

	function authRefuse() {
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#selected_msgId").val($("#msgId").val()).val();
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210112/AuthDtlSubmitResu";
		form.submit();
		$("#authPass").attr("disabled", "disabled");
		$("#authRefuse").attr("disabled", "disabled");
	}
	function queryFTZ210112Dtl() {
		$("#selected_msgId").val($("#msgId").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210112/QryAuthDtl?page.page="+${page.number+1};
		form.submit();
	}
	function sbDtl() {
		$("#selected_msgId").val($("#msgId").val());
		$("#selected_msgNo").val($("#msgNo").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210112/AuthDtlSubmit";
		form.submit();
	}
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210112Form">
			<form:form commandName="FTZ210112Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210112.auth.dtl" /></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210112Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_msgNo" id="selected_msgNo" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="ftzInMsgCtl.msgNo" id="msgNo" />
		<form:hidden path="ftzInMsgCtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="chkDatetime" />
		<form:hidden path="unAuthFlag" id="unAuthFlag"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCHID" />：</td>
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
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUSS"/>：</td> <!-- 批量状态 -->
				<td><form:select path="ftzInMsgCtl.msgStatus" disabled="true" id="msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="accountNo" path="ftzInMsgCtl.accountNo"
						class=".input-large" onblur="accountFill()" disabled="true"/>
					<button type="button" class="btn btn-small" onclick="queryAct()">  
						<spring:message code="button.label.Search" />
					</button></td>				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
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

				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="ftzInMsgCtl.balance"
						value="${FTZ210112Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" />	
			</tr>
			<tr>
			<td class="label_td" colspan="2"><font color="red">* </font>
				<spring:message code="ftz.label.BALANCE_CODE" />：<form:hidden
						path="ftzInMsgCtl.balanceCode" id="balanceCode1" /> <form:select
						id="balanceCode" path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
				<td class="label_td"><font color="red">* </font>
				<spring:message code="ftz.label.ACCORGCODE" />：</td>
				<td><form:input id="accOrgCode" path="ftzInMsgCtl.accOrgCode" class="input-large"  disabled="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.CD_FLAG" />：</td>
				<td><form:select path="ftzInTxnDtl.cdFlag" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_CD_FLAG}" />
					</form:select></td>
				<td class="label_td"><font color="red">* </font> <spring:message code="ftz.label.AMOUNTS" />：</td>
				<td colspan="3"><t:moneyFormat type="text" id="amount" name="ftzInTxnDtl.amount"
						value="${FTZ210112Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.result" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_PROC_RESULT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ADDWORD" />：</td>
				<td colspan="3"><form:input id="addWord"
						path="ftzInMsgCtl.addWord" class="input-xxlarge" readonly="true" /></td>
			</tr>
				<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MAK_USER_ID" />：</td>
				<td><form:input id="makUserId" path="ftzInTxnDtl.makUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MAK_DATETIME" />：</td>
				<td><form:input id="makDatetime" path="ftzInTxnDtl.makDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_USER_ID" />：</td>
				<td><form:input id="chkUserId" path="ftzInTxnDtl.chkUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_DATETIME" />：</td>
				<td><form:input id="chkDatetime" path="ftzInTxnDtl.chkDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_ADD_WORD" />：</td>
				<td colspan="3"><form:input id="chkAddWord"
						path="ftzInTxnDtl.chkAddWord" class="input-xxlarge" /></td>
			</tr>
		</table>
	</form:form>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id=authPass type="button" class="btn btn-primary"
			onclick="authPass()" value="<spring:message code="ftz.label.AUTH" />">
		<input id="authRefuse" type="button" class="btn btn-primary"
			onclick="authRefuse()"
			value="<spring:message code="ftz.label.UNAUTH" />"> <input
			type="button" class="btn btn-primary" id="clswin"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>
