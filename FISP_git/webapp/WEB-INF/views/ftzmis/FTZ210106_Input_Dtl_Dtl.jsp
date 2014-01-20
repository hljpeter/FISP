<script type="text/javascript">
	$(function() {
		var input_Dtl_flag = $("#input_Dtl_flag").val();
		if ('add' == input_Dtl_flag) {
			var seqNo = $("#seqNo").val();
			if ("" != seqNo && null != seqNo) {
				$("#add")
						.val(
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
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		var input_Dtl_flag = $("#input_Dtl_flag").val();
		if ('add' == input_Dtl_flag) {
			form.action = "${pageContext.request.contextPath}/FTZ210106/AddDtlDtlSubmit";
		} else if ('upt' == input_Dtl_flag) {
			form.action = "${pageContext.request.contextPath}/FTZ210106/UptDtlDtlSubmit";
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
		var form = document.getElementById("form1");
		form.action = "${pageContext.request.contextPath}/FTZ210106/AddDtlDtlInit";
		form.submit();
	}
</script>
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
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

<div class="page_title">
	<spring:message code="ftzmis.title.210106.input.dtl.dtl" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210106Form" class="form-horizontal">
		<form:hidden path="ftzInTxnDtl.msgId" id="msgId" />
		<form:hidden path="input_Dtl_flag" id="input_Dtl_flag" />
		<form:hidden path="father_makTime" />
		<form:hidden path="father_chkTime" />

		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NOS" />：</td>
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
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.TRAN_DATE" />：</td>
				<td><form:input id="tranDate" path="ftzInTxnDtl.tranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.ORG_TRAN_DATE" />：</td>
				<td><form:input id="orgTranDate" path="ftzInTxnDtl.orgTranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.AMOUNT" />：</td>
				<td><t:moneyFormat type="text" id="amount"
						name="ftzInTxnDtl.amount"
						value="${FTZ210106Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true" maxlength="24"/></td>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.OPP_NAMES" />：</td>
				<td><form:input id="oppName" path="ftzInTxnDtl.oppName"
						class=".input-large"/></td>
				
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message
						code="ftz.label.TERM_LENGTH" />：</td>
				<td><form:input id="termLength" path="ftzInTxnDtl.termLength" maxlength="4"
						class=".input-large"  onkeyup="numberFormat(this);"
						onbeforepaste="numberFormatCopy(this);"/></td>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.TERM_UNIT" />：</td>
				<td><form:select path="ftzInTxnDtl.termUnit">
						<form:option value=""></form:option>
						<form:options items="${FTZ_REBUY_TERM_UNIT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.VALUE_DATES" />：</td>
				<td><form:input id="valueDate"
						path="ftzInTxnDtl.valueDate" class=".input-large" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.EXPIRE_DATE" />：</td>
				<td><form:input id="expireDate"
						path="ftzInTxnDtl.expireDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.INTEREST_RATES" />：</td>
				<td><t:moneyFormat type="text" id="interestRate"
						name="ftzInTxnDtl.interestRate"
						value="${FTZ210106Form.ftzInTxnDtl.interestRate}"
						format="###,###,###,###.000000" dot="true" maxlength="9" /></td>
				<td class="label_td"><font color="red">* </font> <spring:message
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
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUSSS" />：</td>
				<td colspan="3"><form:select path="ftzInTxnDtl.chkStatus"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MAK_USER_IDS" />：</td>
				<td><form:input id="makUserId" path="ftzInTxnDtl.makUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MAK_DATETIMES" />：</td>
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
<form:form id="form1" modelAttribute="FTZ210106Form" method="post">
	<form:hidden path="selected_msgId" id="selected_msgId" />
</form:form>