<!-- 6.3.3　应付保函/备用证（210207） / 审核 - 交易页面 -->
<script type="text/javascript">
$(function() {
var success = '${successmsg }';
if (success && success != "") {
	$("button[name=btnChk]").attr("disabled", true);
	$("input").attr("readonly", true);
}
var error = '${errmsg }';
if (error && error != "") {
	//$("button[name=btnChk]").attr("disabled", true);
	$("input").attr("readonly", true);
}

var txnStatus='${FTZ210209Form.ftzInTxnDtl.chkStatus }';
if(txnStatus=='03')
{
   $("#chkPass").attr("disabled", true);
}
if(txnStatus=='04')
{
   $("#chkAddWord").attr("readonly", true);
   $("#chkPass").attr("disabled", true);
   $("#chkRej").attr("disabled", true);
}
var msgStatus='${FTZ210209Form.ftzInMsgCtl.msgStatus }';
if(msgStatus=='03')
{
   $("#chkAddWord").attr("readonly", true);
   $("#chkPass").attr("disabled", true);
   $("#chkRej").attr("disabled", true);
}

var msg = '${FTZ210209Form.msg }';
if (msg && msg != "") {
	$("#notice").css("display", "");
	$("#next").attr("disabled", true);
}
	
$("button[name=btnChk]").click(function() {
	var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210207/Auth/DtlTxn/Auth");
	$("#form").submit();
	
});

$("#next").click(function() {
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210207/Auth/DtlTxn/Next");
	$("#form").submit();
});

});
</script>

<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
	    <t:messagePanel />
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="FTZ210209Form">
			<form:form commandName="FTZ210209Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210207.auth.dtl.dtl" /></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210209Form" class="form-horizontal">
		<form:hidden path="ftzInTxnDtl.msgId" id="msgId"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<form:hidden path="ftzInTxnDtl.countryCode"/>
		<form:hidden path="ftzInTxnDtl.disitrictCode"/>
		<form:hidden path="ftzInTxnDtl.tranType"/>
		<form:hidden path="ftzInTxnDtl.termUnit"/>
		<table class="tbl_search">
			
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO" />：</td>
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
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.TRAN_DATE" />：</td>
				<td><form:input id="tranDate" path="ftzInTxnDtl.tranDate" readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.ORG_TRAN_DATE" />：</td>
				<td><form:input id="orgTranDate" path="ftzInTxnDtl.orgTranDate" readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.AMOUNT" />：</td>
				<td colspan="3"><t:moneyFormat type="text" id="amount" readonly="true"
						name="ftzInTxnDtl.amount"
						value="${FTZ210206Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true" /></td>
			</tr>
			
			
			
			<tr>
				<td class="label_td"><spring:message code="ftz.label.OPP_BANK_CODE" />：</td>
				<td><form:input id="oppBankCode" readonly="true"
						path="ftzInTxnDtl.oppBankCode" class=".input-large" />
						</td>
						
				<td class="label_td"><spring:message
						code="ftz.label.OPP_BANK_NAME" />：</td>
				<td><form:input id="oppBankName" path="ftzInTxnDtl.oppBankName" 
						class=".input-large" readonly="true" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message code="ftz.label.OPP_ACCOUNT" />：</td>
				<td><form:input id="oppAccount"
						path="ftzInTxnDtl.oppAccount" class=".input-large"  readonly="true"/></td>
				<td class="label_td"><spring:message code="ftz.label.OPP_NAME" />：</td>
				<td><form:input id="oppName"
						path="ftzInTxnDtl.oppName" class=".input-large"  readonly="true"/></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.COUNTRY_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.countryCode" disabled="true"
						id="countryCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_COUNTRY_CODE}" />
					</form:select>
					</td>
				<td class="label_td"><spring:message
						code="ftz.label.DISITRICT_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.disitrictCode" disabled="true"
						id="disitrictCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<button type="button" class="btn btn-small" onclick="queryShowSelReg()">
						<spring:message code="button.label.Search" />
					</button></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.TERM_LENGTH"  />：</td>
				<td><form:input id="termLength" path="ftzInTxnDtl.termLength" readonly="true"
						class=".input-large"  onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/></td>
				<td class="label_td"><font color="red">*</font><spring:message
						code="ftz.label.TERM_UNIT" />：</td>
				<td><form:select path="ftzInTxnDtl.termUnit" disabled="true"
						id="termUnit">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_UNIT}" />
					</form:select>
					</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message
						code="210207ftz.label.VALUE_DATE" />：</td>
				<td><form:input id="valueDate" path="ftzInTxnDtl.valueDate" readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
						
						<td class="label_td"><spring:message
						code="ftz.label.EXPIRE_DATE" />：</td>
				<td><form:input id="valueDate" path="ftzInTxnDtl.expireDate" readonly="true"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="210207ftz.label.INTERESTRATE" />：</td>
				<td><t:moneyFormat type="text" id="interestRate" readonly="true"
						name="ftzInTxnDtl.interestRate"
						value="${FTZ210206Form.ftzInTxnDtl.interestRate}"
						format="###.000000" dot="true"  /></td>
						
						<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.TRAN_TYPE" />：</td>
				<td><form:select path="ftzInTxnDtl.tranType" id="tranType" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_TYPE}" />
					</form:select>
					</td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MSG_DTL_STATUS" />：</td>
				<td colspan="3"><form:select path="ftzInTxnDtl.chkStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID" />：</td>
				<td><form:input id="makUserId"
						path="ftzInTxnDtl.makUserId" class=".input-large" readonly="true"/></td>
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME" />：</td>
				<td><form:input id="makDatetime"
						path="ftzInTxnDtl.makDatetime" class=".input-large" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID" />：</td>
				<td><form:input id="chkUserId"
						path="ftzInTxnDtl.chkUserId" class=".input-large" readonly="true"/></td>
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME" />：</td>
				<td><form:input id="chkDatetime"
						path="ftzInTxnDtl.chkDatetime" class=".input-large" readonly="true"/></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_ADD_WORD" />：</td>
				<td colspan="3"><form:input id="chkAddWord"
						path="ftzInTxnDtl.chkAddWord" class="input-xxlarge" /></td>
			</tr>
		</table>

	</form:form>
	<p id="notice" class="text-error" style="display: none;"><spring:message code="i.ftzmis.210309.0006"/></p>
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="chkPass" name="btnChk" class="btn btn-primary" onclick='javascript: $("#operFlag").val("03");$("#amount").val($("#amount").val().replaceAll(",",""));'><spring:message code="ftz.label.AUTH"/></button>
		<button id="chkRej" name="btnChk" class="btn btn-primary" onclick='javascript: $("#operFlag").val("04");$("#amount").val($("#amount").val().replaceAll(",",""));'><spring:message code="ftz.label.UNAUTH"/></button>
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>