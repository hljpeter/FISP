<!-- 6.3.3　应付保函/备用证（210303） / 录入新增 - 交易页面 -->
<script type="text/javascript">
$(function() {
var actionFlag = '${FTZ210303Form.actionFlag }';
if (actionFlag == "addTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/AddTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("input[type=button]").attr("disabled", true);
		$("select").attr("disabled", true);
		$(".date").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/AddTxn/Init");
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
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/UptTxn/Submit");
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
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/UptTxn/Init");
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
		<spring:hasBindErrors name="FTZ210303Form">
			<form:form commandName="FTZ210303Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210303.input.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210303Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId" id="msgId"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO"/>：</td>
				<td><form:input path="ftzOffTxnDtl.seqNo" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SUBMIT_DATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.submitDate" type="text" class="input-large date" maxLength="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCORGCODE"/>：</td>
				<td colspan="3">
					<form:input id="accOrgCode" path="ftzOffTxnDtl.accOrgCode" class="input-large" maxLength="12" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg1()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.AMOUNT"/>：</td>
				<td><t:moneyFormat type="text" id="amount" name="ftzOffTxnDtl.amount" value="${FTZ210303Form.ftzOffTxnDtl.amount }" format="###,###,###,##0.00" dot="true" /></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzOffTxnDtl.currency">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<!-- 境内外对手行代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SWIFT_CODE"/>：</td>
				<td>
					<form:input id="swiftCode" path="ftzOffTxnDtl.swiftCode" class="input-large" maxLength="11"/>
					<input type="button" class="btn btn-small"  onclick="queryShowAllOrg3()"  value="<spring:message code="button.label.Search"/>">
				</td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DEBT_NAME"/>：</td>
				<td><form:input path="ftzOffTxnDtl.accountName" type="text" class="input-large" maxLength="40"/></td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DEBT_INST_CODE"/>：</td>
				<td>
					<form:input id="institutionCode" path="ftzOffTxnDtl.institutionCode" class="input-large" maxLength="12"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg2()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DEBT_COUNTRY_CODE"/>：</td>
				<td>
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" maxLength="3" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>">
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.DEBT_DISTRICT_CODE"/>：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode" id="districtCode" class="disCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<input type="button" class="btn btn-small disCode" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.LG_EXPIRE_DATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.expirationDate" type="text" class="input-large date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.LG_TYPE"/>：</td>
				<td>
					<form:select path="ftzOffTxnDtl.lgType">
						<form:option value=""></form:option>
						<form:options items="${FTZ_LG_TYPE }" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl"><td colspan="4"><hr/></td><td>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td>
				<td colspan="3"><t:codeValue items="${FTZ_MSG_STATUS }" key="${FTZ210303Form.ftzOffTxnDtl.chkStatus }" type="text"  cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.makUserId" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210303Form.ftzOffTxnDtl.makDatetime }" format="datetime" name="ftzOffTxnDtl.makDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.chkUserId" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210303Form.ftzOffTxnDtl.chkDatetime }" format="datetime" name="ftzOffTxnDtl.chkDatetime" cssClass="input-large" readonly="true"/></td>
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