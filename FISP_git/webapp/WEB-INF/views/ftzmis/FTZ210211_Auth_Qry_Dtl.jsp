<script type="text/javascript">
	function authPass() {
		var msgStatus = $("#msgStatus").val();
		if ("02" != msgStatus) {
			alert('<spring:message code="ftz.validate.auth.msg"/>');
			return;
		}
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#selected_msgId").val($("#msgId").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210211/AuthDtlSubmit?authStat=1";
		form.submit();
	
	}
	function authRefuse() {
		var msgStatus = $("#msgStatus").val();
		if ("02" != msgStatus) {
			alert('<spring:message code="ftz.validate.auth.msg"/>');
			return;
		}
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#selected_msgId").val($("#msgId").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210211/AuthDtlSubmit?authStat=0";
		form.submit();
		$("#authPass").attr("disabled", "disabled");
		$("#authRefuse").attr("disabled", "disabled");
	}
	var BalanceValidation = function() {
		var submitDate = $("#submitDate").val();//申报日期
		var accountNo = $("#accountNo").val();//账号		
		var subAccountNo = $("#subAccountNo").val();//主账号
		
		if(null == subAccountNo || ""==subAccountNo){
			subAccountNo = accountNo;
		}				
		showDialog(
				'${pageContext.request.contextPath}/FTZINCOM/BalanceCheck?check_AccountNo='
						+ accountNo + "&check_SubAccountNo="
						+ subAccountNo+ "&check_SubmitDate="
						+ submitDate, '500', '1024');
		
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210211Form">
			<form:form commandName="FTZ210211Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.210211.auth.dtl" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210211Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="ftzInMsgCtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="chkDatetime" />
		<form:hidden path="unAuthFlag" id="unAuthFlag" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID" />：</td>
				<td><form:select path="ftzInMsgCtl.branchId" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="msgId" path="ftzInMsgCtl.msgId"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="submitDate" path="ftzInMsgCtl.submitDate"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.msgStatus" disabled="true" id="msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="accountNo" path="ftzInMsgCtl.accountNo"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="accountName" path="ftzInMsgCtl.accountName"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td colspan="2" class="label_td"><spring:message
						code="ftz.label.BALANCE_CODE" />：<form:select
						path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.CURRENCY" />：</td>
				<td><form:select path="ftzInMsgCtl.currency" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text"
						value="${FTZ210211Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" id="balance"/>
				<td class="label_td"><spring:message
						code="ftz.label.ACC_ORG_CODE" />：</td>
				<td><form:input id="accOrgCode" path="ftzInMsgCtl.accOrgCode"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CD_FLAG" />：
				</td>
				<td><form:select path="ftzInTxnDtl.cdFlag" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_CD_FLAG}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message
						code="ftz.label.AMOUNTS" />：
				</td>
				<td><t:moneyFormat type="text" id="amount"
						name="ftzInTxnDtl.amount"
						value="${FTZ210211Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true"  readonly="true" /></td>
			</tr>

		</table>

		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.result" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_PROC_RESULT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ADDWORD" />：</td>
				<td colspan="3"><form:input id="addWord"
						path="ftzInMsgCtl.addWord" class="input-xxlarge" readonly="true" /></td>
			</tr>
			<tr class="dtl"><td colspan="4"><hr/></td></tr>
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
			value="<spring:message code="ftz.label.UNAUTH" />"> 
		<input id="balanceValidation" type="button" class="btn btn-primary"
			onclick="BalanceValidation();"
			value="<spring:message code="ftz.label.balanceValidation"/>">	
		<input type="button" class="btn btn-primary" id="clswin"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>