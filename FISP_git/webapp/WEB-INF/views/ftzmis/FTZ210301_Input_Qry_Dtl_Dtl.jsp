<!-- 6.3.1　代理发债（210301） / 录入新增 - 交易页面 -->
<script type="text/javascript">
$(function() {
var actionFlag = '${FTZ210301Form.actionFlag }';
if (actionFlag == "addTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210301/Input/AddTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("input[type=button]").attr("disabled", true);
		$("select").attr("disabled", true);
		$(".date").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210301/Input/AddTxn/Init");
		$("#sbm").html('<spring:message code="ftz.label.GOON_KEY_IN"/>');
	}
} else if (actionFlag == "dtlTxn") {
	$("input").attr("readonly", true);
	$("select").attr("disabled", true);
	$("#sbm").attr("disabled", true);
	$("input[type=button]").attr("disabled", true);
	$(".date").attr("disabled", true);
} else if (actionFlag == "uptTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210301/Input/UptTxn/Submit");
	if ($("#countryCode").val() == "CHN") {
		$(".disCode").attr("disabled", false);
	} else {
		$(".disCode").attr("disabled", true);
	}
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("input[type=button]").attr("disabled", true);
		$("select").attr("disabled", true);
		$(".date").attr("disabled", true);
		$("#sbm").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210301/Input/UptTxn/Init");
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
	if ($("#countryCode").val() == "CHN") {
		$(".disCode").attr("disabled", false);
	} else {
		$(".disCode").attr("disabled", true);
	}
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


</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="FTZ210301Form">
			<form:form commandName="FTZ210301Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210301.input.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210301Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId" id="msgId"/>
		<table class="tbl_search">
			<tr>
				<!-- 申报序号 -->
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO"/>：</td>
				<td><form:input path="ftzOffTxnDtl.seqNo" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 申报日期 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SUBMIT_DATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.submitDate" type="text" class="input-large date" maxLength="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
			</tr>
			<tr>	
				<!-- 企业机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TRAN_INSTITUTION_CODE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.institutionCode" type="text" class="input-large" maxLength="12"/></td>
		
				<!-- 企业账号 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ENT_ACCOUNT_NO"/>：</td>
				<td><form:input path="ftzOffTxnDtl.accountNo" type="text" class="input-large" maxLength="35"/></td>
			</tr>
			<tr>	
				<!-- 债券号码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.BONDS_CODE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.bondsCode" type="text" class="input-large" maxLength="20"/></td>
				
				<!-- 名称 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.BONDS_NAME"/>：</td>
				<td><form:input path="ftzOffTxnDtl.bondsName" type="text" class="input-large" maxLength="40"/></td>
			</tr>
			<tr>	
				<!-- 货币 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzOffTxnDtl.currency">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY }" />
					</form:select>
				</td>
				
				<!-- 金额 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ISSUE_AMOUNT"/>：</td>
				<td><t:moneyFormat type="text" id="amount" name="ftzOffTxnDtl.amount" value="${FTZ210301Form.ftzOffTxnDtl.amount }" format="###,###,###,###.00" dot="true" /></td>
			</tr>
			<tr>	
				<!-- 国别代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.COUNTRY_CODE"/>：</td>
				<td>
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" maxLength="3" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>">
				</td>
				
				<!-- 国内地区码 -->
				<td class="label_td"><spring:message code="ftz.label.DISTRICT_CODE"/>：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode"  id="districtCode" class="disCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<input type="button" class="btn btn-small disCode" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TRAN_TYPE"/>：</td>
				<td colspan="3">
					<form:select path="ftzOffTxnDtl.tranType">
						<option value=""></option>
						<form:options items="${FTZ_TRAN_TYPE }" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_UNIT"/>：</td>	
				<td>
					<form:select path="ftzOffTxnDtl.termUnit">
						<option value=""></option>
						<form:options items="${FTZ_REBUY_TERM_UNIT }" />
					</form:select>
				</td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_LENGTH"/>：</td>	
				<td><form:input path="ftzOffTxnDtl.termLength" type="text" class="input-large" maxLength="4" onkeyup="numberFormat(this);"/></td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SETTLE_DATE"/>：</td>	
				<td><form:input path="ftzOffTxnDtl.tranDate" type="text" class="input-large date" maxLength="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.EXPIRE_DATE"/>：</td>	
				<td><form:input path="ftzOffTxnDtl.expirationDate" type="text" class="input-large date" maxLength="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.INTEREST_TYPE"/>：</td>	
				<td>
					<form:select path="ftzOffTxnDtl.interestType" id="interestType">
						<option value=""></option>
						<form:options items="${FTZ_INTEREST_TYPE }" />
					</form:select>
				</td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.FIXED_INTEREST_RATES"/>：</td>
				<td><form:input path="ftzOffTxnDtl.interestRate" type="text" class="input-large" onkeyup="numberFormat(this);"/></td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.BENCHMARK"/>：</td>
				<td>
					<form:select path="ftzOffTxnDtl.benchmark">
						<option value=""></option>
						<form:options items="${FTZ_BENCHMARK }" />
					</form:select>
				</td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.FLOAT_RATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.floatRate" type="text" class="input-large" onkeyup="numberFormat(this);"/></td>
			</tr>
			<tr class="dtl"><td colspan="4"><hr/></td><td>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td>
				<td colspan="3"><t:codeValue items="${FTZ_MSG_STATUS }" key="${FTZ210301Form.ftzOffTxnDtl.chkStatus }" type="text"  cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.makUserId" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210301Form.ftzOffTxnDtl.makDatetime }" format="datetime" name="ftzOffTxnDtl.makDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.chkUserId" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210301Form.ftzOffTxnDtl.chkDatetime }" format="datetime" name="ftzOffTxnDtl.chkDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3"><form:input path="ftzOffTxnDtl.chkAddWord" type="text" class="input-xxlarge" readonly="true"/></td>
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