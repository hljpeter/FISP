<script type="text/javascript">
$(function() {
	var chkStatus = $("#chkStatus").val();
	var authFinishFlag = $("#authFinishFlag").val();
	if("1"==authFinishFlag || "03"==chkStatus|| "04"==chkStatus){
		$("#authPass").attr("disabled","disabled");
		if( "04"==chkStatus){
			$("#authRefuse").attr("disabled","disabled");
		}
		$("#chkAddWord").attr("readonly","true");
		
	}else if(""==authFinishFlag){
		$("#authPass").removeAttr("disabled");
		$("#authRefuse").removeAttr("disabled");
		$("#chkAddWord").removeAttr("readonly");
	}
});

function authPass() {
	$("#amount").val($("#amount").val().replaceAll(",",""));
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210201/AuthDtlDtlSubmit?authStat=1";
	form.submit();
}

function authRefuse() {
	$("#amount").val($("#amount").val().replaceAll(",",""));
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210201/AuthDtlDtlSubmit?authStat=0";
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

<div class="page_title"><spring:message code="ftzmis.title.210201.auth.dtl.dtl" /></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210201Form" class="form-horizontal">
		<form:hidden path="ftzInTxnDtl.msgId"/>
		<form:hidden path="authFinishFlag" id="authFinishFlag"/>
		<form:hidden path="ftzInTxnDtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzInTxnDtl.chkDatetime" id="chkDatetime" />
		<form:hidden path="father_makTime" />
		<form:hidden path="father_chkTime" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO" />：</td>
				<td><form:input id="seqNo" path="ftzInTxnDtl.seqNo"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.CD_FLAG" />：</td>
				<td><form:select path="ftzInTxnDtl.cdFlag" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_CD_FLAG}" />
					</form:select></td>

			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.TRAN_DATE" />：</td>
				<td><form:input id="tranDate" path="ftzInTxnDtl.tranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" readonly="true"/></td>
				<td class="label_td"><spring:message
						code="ftz.label.ORG_TRAN_DATE" />：</td>
				<td><form:input id="orgTranDate" path="ftzInTxnDtl.orgTranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.AMOUNT" />：</td>
				<td colspan="3"><t:moneyFormat type="text" id="amount" readonly="true"
						name="ftzInTxnDtl.amount"
						value="${FTZ210201Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true" maxlength="24" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.OPP_ACCOUNT" />：</td>
				<td><form:input id="oppAccount" path="ftzInTxnDtl.oppAccount" readonly="true"
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.OPP_NAME1" />：</td>
				<td><form:input id="oppName" path="ftzInTxnDtl.oppName" readonly="true"
						class=".input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* 	</font> <spring:message
						code="ftz.label.COUNTRY_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.countryCode" disabled="true"
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
				<td><form:select path="ftzInTxnDtl.disitrictCode" disabled="true"
						id="disitrictCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<button type="button" class="btn btn-small" onclick="queryRegion()">
						<spring:message code="button.label.Search" />
					</button></td>
			</tr>
			<tr>
				
				<td class="label_td"><spring:message code="ftz.label.VALUE_DATES" />：</td>
				<td><form:input id="valueDate"
						path="ftzInTxnDtl.valueDate" class=".input-large" readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				<td class="label_td"><spring:message code="ftz.label.EXPIRE_DATE" />：</td>
				<td><form:input id="expireDate"
						path="ftzInTxnDtl.expireDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" readonly="true"/></td>
			
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.INTEREST_RATESS" />：</td>
				<td><form:input id="interestRate" path="ftzInTxnDtl.interestRate" class=".input-large" readonly="true"/></td>
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.TRAN_TYPE" />：</td>
				<td><form:select path="ftzInTxnDtl.tranType" id="tranType" disabled="true">
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
						code="ftz.label.TERM_LENGTH" />：</td>
				<td><form:input id="termLength" path="ftzInTxnDtl.termLength" readonly="true"
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.TERM_UNIT" />：</td>
				<td><form:select path="ftzInTxnDtl.termUnit" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_REBUY_TERM_UNIT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.personIdTypeS"/>：</td>
				<td>
					<form:select id="documentType" path="ftzInTxnDtl.documentType" disabled="true"><!-- wpp -->
						<form:option value="" />
						<form:options items="${DP_0013}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="fisp.la.personIdNoS"/>：</td>
				<td>
					<form:input id="documentNo" path="ftzInTxnDtl.documentNo" readonly="true" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message
							code="ftz.label.LOAN_TYPE" />:</td><!-- wpp2 -->
				<td><form:select path="ftzInTxnDtl.loanType" id="loanType" disabled="true">
					<form:option value=""></form:option>
					<form:options items="${FTZ_LOAN_TYP}" />
				</form:select></td>
				<td class="label_td"><font color="red">* </font><spring:message
							code="ftz.label.OVERDUE" />：</td>
				<td>
					<form:select path="ftzInTxnDtl.overdue" id="overdue" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_GENRE}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.DTL_STATUS" />：</td>
				<td colspan="3"><form:select path="ftzInTxnDtl.chkStatus" id="chkStatus"
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
						code="ftz.label.CHK_DATETIME" />：</td>
				<td><form:input id="chkDatetime" path="ftzInTxnDtl.chkDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_ADD_WORD" />：</td>
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
		<input id=authPass type="button" class="btn btn-primary"
			onclick="authPass()" value="<spring:message code="ftz.label.AUTH" />"> <input id="authRefuse"
			type="button" class="btn btn-primary" onclick="authRefuse()" value="<spring:message code="ftz.label.UNAUTH" />">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>