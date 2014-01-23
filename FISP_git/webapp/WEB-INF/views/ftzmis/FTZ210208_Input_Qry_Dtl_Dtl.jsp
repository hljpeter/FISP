<!-- 6.3.3　应付保函/备用证（210208） / 录入新增 - 交易页面 -->
<script type="text/javascript">
$(function() {
var actionFlag = '${FTZ210208Form.actionFlag }';
if (actionFlag == "addTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210208/Input/AddTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("button[type=button]").attr("disabled", true);
		$("input").attr("readonly", true);
		$("select").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210208/Input/AddTxn/Init");
		$("#sbm").html('<spring:message code="ftz.label.GOON_KEY_IN"/>');
	}
} else if (actionFlag == "dtlTxn") {
	$("button[type=button]").attr("disabled", true);
	$(".btn-small").css("display", "none");
	$("input").attr("readonly", true);
	$("select").attr("disabled", true);
	$("#sbm").attr("disabled", true);
	$(".date").unbind();
} else if (actionFlag == "uptTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210208/Input/UptTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("button[type=button]").attr("disabled", true);
		$("input").attr("readonly", true);
		$("select").attr("disabled", true);
		$("#sbm").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210208/Input/UptTxn/Init");
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

</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
	    <t:messagePanel />
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="FTZ210208Form">
			<form:form commandName="FTZ210208Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210208.input.dtl.dtl"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210208Form" class="form-horizontal">
		<form:hidden path="ftzInTxnDtl.msgId" id="msgId"/>
		<form:hidden path="ftzInTxnDtl.chkStatus" id="chkStatus"/>
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
						value="${FTZ210208Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true" maxlength="24"/></td>
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
						id="districtCode">
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
				<td>
				<form:input id="termLength" path="ftzInTxnDtl.termLength"
						class=".input-large"  onkeyup="numberFormat(this);"
						onbeforepaste="numberFormatCopy(this);"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message
						code="ftz.label.TERM_UNIT" />：</td>
				<td><form:select path="ftzInTxnDtl.termUnit"
						id="termUnit">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_UNIT}" />
					</form:select>
					
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
						value="${FTZ210208Form.ftzInTxnDtl.interestRate}"
						format="###.000000" dot="true" maxlength="9"  /></td>
						
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
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="sbm" name="btn" class="btn btn-primary"><spring:message code="ftz.label.SUBMIT_MSG_DTL"/></button>
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>		