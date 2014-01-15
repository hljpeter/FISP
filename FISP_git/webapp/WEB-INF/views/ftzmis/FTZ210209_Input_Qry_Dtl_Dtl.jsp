<!-- 6.3.3　应付保函/备用证（210209） / 录入新增 - 交易页面 -->
<script type="text/javascript">
$(function() {
var actionFlag = '${FTZ210209Form.actionFlag }';
if (actionFlag == "addTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/AddTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("select").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/AddTxn/Init");
		$("#sbm").html('<spring:message code="ftz.label.GOON_KEY_IN"/>');
	}
} else if (actionFlag == "dtlTxn") {
	$(".btn-small").css("display", "none");
	$("input").attr("readonly", true);
	$("select").attr("disabled", true);
	$("#sbm").attr("disabled", true);
	$(".date").unbind();
} else if (actionFlag == "uptTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/UptTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("select").attr("disabled", true);
		$("#sbm").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/UptTxn/Init");
	}
} 

$("#sbm").click(function() {
	$("#amount").val($("#amount").val().replaceAll(",", ""));
	$("#form").submit();
});

});

function queryShowSelReg() {
	showSelReg([ {
		"districtCode" : "param1"
	} ]);
};

function queryShowSelNation(){
	showSelNation([{"countryCode":"param1"}]);
}

function queryShowAllOrg1(){
	showAllOrg([{"accOrgCode":"param1"}]);
}

function queryShowAllOrg2(){
	showAllOrg([{"institutionCode":"param1"}]);
}

function queryShowAllOrg3(){
	showAllOrg([{"swiftCode":"param1"}]);
}

function querytranType() {
	showSelMeta([ {
		"tranType" : "param1"
	} ]);

}

function queryBankCode() {
	showSelBank([ {
		"oppBankCode" : "param1",
		"oppBankName" : "param2"
	} ]);
};

</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
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
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210209.input.dtl.dtl"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210209Form" class="form-horizontal">
		<form:hidden path="ftzInTxnDtl.msgId" id="msgId"/>
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
						code="ftz.label.AMOUNT" />：</td>
				<td colspan="3"><t:moneyFormat type="text" id="amount"
						name="ftzInTxnDtl.amount"
						value="${FTZ210209Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true"  maxlength="24"/></td>
			</tr>
			
			
			
			<tr>
				<td class="label_td"><spring:message code="ftz.label.OPP_BANK_CODE" />：</td>
				<td><form:input id="oppBankCode"
						path="ftzInTxnDtl.oppBankCode" class=".input-large" />
						<button type="button" class="btn btn-small" onclick="queryBankCode()">
						<spring:message code="button.label.Search" />
					</button></td>
						
				<td class="label_td"><spring:message
						code="ftz.label.OPP_BANK_NAME" />：</td>
				<td><form:input id="oppBankName" path="ftzInTxnDtl.oppBankName"
						class=".input-large" readonly="true" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message code="ftz.label.OPP_ACCOUNT" />：</td>
				<td><form:input id="oppAccount"
						path="ftzInTxnDtl.oppAccount" class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.OPP_NAME" />：</td>
				<td><form:input id="oppName"
						path="ftzInTxnDtl.oppName" class=".input-large" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.COUNTRY_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.countryCode"
						id="countryCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_COUNTRY_CODE}" />
					</form:select>
					<button type="button" class="btn btn-small"
						onclick="queryShowSelNation()">
						<spring:message code="button.label.Search" />
					</button></td>
				<td class="label_td"><spring:message
						code="ftz.label.DISITRICT_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.disitrictCode"
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
						code="ftz.label.TERM_LENGTH" />：</td>
				<td><form:input id="termLength" path="ftzInTxnDtl.termLength"
						class=".input-large"  onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/></td>
				<td class="label_td"><font color="red">*</font><spring:message
						code="ftz.label.TERM_UNIT" />：</td>
				<td><form:select path="ftzInTxnDtl.termUnit"
						id="termUnit">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_UNIT}" />
					</form:select>
					<button type="button" class="btn btn-small" onclick="">
						<spring:message code="button.label.Search" />
					</button></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message
						code="210207ftz.label.VALUE_DATE" />：</td>
				<td><form:input id="valueDate" path="ftzInTxnDtl.valueDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
						
						<td class="label_td"><spring:message
						code="ftz.label.EXPIRE_DATE" />：</td>
				<td><form:input id="valueDate" path="ftzInTxnDtl.expireDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="210207ftz.label.INTERESTRATE" />：</td>
				<td><t:moneyFormat type="text" id="interestRate"
						name="ftzInTxnDtl.interestRate"
						value="${FTZ210209Form.ftzInTxnDtl.interestRate}"
						format="###.000000" dot="true" maxlength="9"   /></td>
						
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

			<tr class="dtl"><td colspan="4"><hr/></td><td>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td>
				<td colspan="3"><t:codeValue items="${FTZ_MSG_STATUS }" key="${FTZ210209Form.ftzInTxnDtl.chkStatus }" type="text"  cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td><form:input path="ftzInTxnDtl.makUserId" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210209Form.ftzInTxnDtl.makDatetime }" format="datetime" name="ftzInTxnDtl.makDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td><form:input path="ftzInTxnDtl.chkUserId" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210209Form.ftzInTxnDtl.chkDatetime }" format="datetime" name="ftzInTxnDtl.chkDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3"><form:input path="ftzInTxnDtl.chkAddWord" type="text" class="input-xxlarge" readonly="true"/></td>
			</tr>
	    </table>			
	    
	    	    </form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="sbm" name="btn" class="btn btn-primary"><spring:message code="ftz.label.SUBMIT_MSG_DTL"/></button>
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>		