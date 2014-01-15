<script type="text/javascript">
	$(function() {
		var input_Dtl_flag = $("#input_Dtl_flag").val();
		if ('add' == input_Dtl_flag) {
			var seqNo = $("#seqNo").val();
			if ("" != seqNo && null != seqNo) {
				$("#add").val(
						'<spring:message
		code="button.lable.AddMore" />');
				var dtlSub = document.getElementById("add");
				dtlSub.onclick = addRef;
			}
		}
		var uptFlag = '${uptFlag }';
		if (uptFlag == '1') {
			$("input:not([id='selected_msgId'])").attr("disabled", true);
			$("select").attr("disabled", true);
			if ('add' == input_Dtl_flag) {
				$("#add").removeAttr("disabled");
			}
		}

	});
	function addDetail() {
		$("#buyRate").val($("#buyRate").val().replaceAll(",", ""));
		$("#sellRate").val($("#sellRate").val().replaceAll(",", ""));
		$("#buyAmt").val($("#buyAmt").val().replaceAll(",", ""));
		$("#sellAmt").val($("#sellAmt").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		var input_Dtl_flag = $("#input_Dtl_flag").val();
		if ('add' == input_Dtl_flag) {
			form.action = "${pageContext.request.contextPath}/FTZ210109/AddDtlDtlSubmit";
		} else if ('upt' == input_Dtl_flag) {
			form.action = "${pageContext.request.contextPath}/FTZ210109/UptDtlDtlSubmit";
		}
		form.submit();
	}

	function queryRegion() {
		showSelReg([ {
			"disitrictCode" : "param1"
		} ]);
	};

	function querytranType() {
		showSelMeta([ {
			"tranType" : "param1"
		} ]);

	}

	function querycountryCode() {
		showSelNation([ {
			"countryCode" : "param1"
		} ]);
	};

	function queryBankCode() {
		showSelBank([ {
			"oppBankCode" : "param1",
			"oppBankName" : "param2"
		} ]);
	};
	function addRef() {
		$("#selected_msgId").val($("#msgId").val());
		var form1 = document.getElementById("form1");
		form1.action = "${pageContext.request.contextPath}/FTZ210109/AddDtlDtlInit";
		form1.submit();
	}
</script>
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
		<spring:hasBindErrors name="FTZ210109Form">
			<form:form commandName="FTZ210109Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.210109.input.dtl.dtl" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210109Form" class="form-horizontal">
		<form:hidden path="ftzInTxnDtl.msgId" id="msgId" />
		<form:hidden path="input_Dtl_flag" id="input_Dtl_flag" />
		<form:hidden path="father_makTime" />
		<form:hidden path="father_chkTime" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO" />：</td>
				<td><form:input id="seqNo" path="ftzInTxnDtl.seqNo"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.CD_FLAG" />：</td>
				<td><form:select path="ftzInTxnDtl.cdFlag">
						<form:option value=""></form:option>
						<form:options items="${FTZ_CD_FLAG}" />
					</form:select></td>

			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.TRAN_DATE" />：</td>
				<td><form:input id="tranDate" path="ftzInTxnDtl.tranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.ORG_TRAN_DATE" />：</td>
				<td><form:input id="orgTranDate" path="ftzInTxnDtl.orgTranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.EXCHANGE_TYPE" />：</td>
				<td><form:select path="ftzInTxnDtl.exchangeType"
						id="exchangeType">
						<form:option value=""></form:option>
						<form:options items="${FTZ_EXCHANGE_TYPE}" />
					</form:select></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.TRAN_TYPE" />：</td>
				<td><form:select path="ftzInTxnDtl.tranType" id="tranType">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_TYPE}" />
					</form:select>
					<button type="button" class="btn btn-small"
						onclick="querytranType()">
						<spring:message code="button.label.Search" />
					</button></td>
			</tr>

			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.BUY_CURR" />：</td>
				<td><form:select path="ftzInTxnDtl.buyCurr">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.SELL_CURR" />：</td>
				<td><form:select path="ftzInTxnDtl.sellCurr">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>
			</tr>

			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="210212ftz.label.BUY_AMT" />：</td>
				<td><t:moneyFormat type="text" id="buyAmt"
						name="ftzInTxnDtl.buyAmt"
						value="${FTZ210109Form.ftzInTxnDtl.buyAmt}"
						format="###,###,###,###.00" dot="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="210212ftz.label.SELL_AMT" />：</td>
				<td><t:moneyFormat type="text" id="sellAmt"
						name="ftzInTxnDtl.sellAmt"
						value="${FTZ210109Form.ftzInTxnDtl.sellAmt}"
						format="###,###,###,###.00" dot="true" /></td>
			</tr>

			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="210212ftz.label.BUY_RATE" />：</td>
				<td><t:moneyFormat type="text" id="buyRate"
						name="ftzInTxnDtl.buyRate"
						value="${FTZ210109Form.ftzInTxnDtl.buyRate}"
						format="###,###,###,###.000000" dot="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="210212ftz.label.SELL_RATE" />：</td>
				<td><t:moneyFormat type="text" id="sellRate"
						name="ftzInTxnDtl.sellRate"
						value="${FTZ210109Form.ftzInTxnDtl.sellRate}"
						format="###,###,###,###.000000" dot="true" /></td>
			</tr>

			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.OPP_BANK_CODE" />：</td>
				<td><form:input id="oppBankCode" path="ftzInTxnDtl.oppBankCode"
						class=".input-large" />
					<button type="button" class="btn btn-small"
						onclick="queryBankCode()">
						<spring:message code="button.label.Search" />
					</button></td>
				<td class="label_td"><spring:message
						code="ftz.label.OPP_BANK_NAME" />：</td>
				<td><form:input id="oppBankName" path="ftzInTxnDtl.oppBankName"
						class=".input-large" readonly="true" /></td>
			</tr>

			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.OPP_ACCOUNT" />：</td>
				<td><form:input id="oppAccount" path="ftzInTxnDtl.oppAccount"
						class=".input-large" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.OPP_NAME1" />：</td>
				<td><form:input id="oppName" path="ftzInTxnDtl.oppName"
						class=".input-large" /></td>
			</tr>

			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.COUNTRY_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.countryCode"
						id="countryCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_COUNTRY_CODE}" />
					</form:select>
					<button type="button" class="btn btn-small"
						onclick="querycountryCode()">
						<spring:message code="button.label.Search" />
					</button></td>
				<td class="label_td"><spring:message
						code="ftz.label.DISITRICT_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.disitrictCode"
						id="disitrictCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<button type="button" class="btn btn-small" onclick="queryRegion()">
						<spring:message code="button.label.Search" />
					</button></td>

			</tr>


			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_DTL_STATUS" />：</td>
				<td colspan="3"><form:select path="ftzInTxnDtl.chkStatus"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
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
						path="ftzInTxnDtl.chkAddWord" class="input-xxlarge"
						readonly="true" /></td>
			</tr>
		</table>

	</form:form>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="add" type="button" class="btn btn-primary"
			onclick="addDetail();"
			value="<spring:message code="ftz.label.SUBMIT_MSG_DTL"/>">
		<button name="btn" class="btn btn-primary"
			onclick="javascript: window.close();">
			<spring:message code="button.lable.close" />
		</button>
	</div>
</div>
<form:form id="form1" modelAttribute="FTZ210109Form" method="post">
	<form:hidden path="selected_msgId" id="selected_msgId" />
</form:form>