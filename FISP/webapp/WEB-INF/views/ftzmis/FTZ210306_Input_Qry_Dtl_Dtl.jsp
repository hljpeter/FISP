<!-- 6.3.6　应收信用证（210306） / 录入新增&修改 - 交易页面 -->
<script type="text/javascript">
$(function() {
var termCon = '${FTZ210306Form.ftzOffTxnDtl.termCondition }';
if (termCon == "1") {
	$("#termLength").attr("readonly", true);
} else {
	$("#termLength").attr("readonly", false);
}

var actionFlag = '${FTZ210306Form.actionFlag }';
if (actionFlag == "addTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210306/Input/AddTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("input[type=button]").attr("disabled", true);
		$("select").attr("disabled", true);
		$(".date").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210306/Input/AddTxn/Init");
		$("#sbm").html('<spring:message code="ftz.label.GOON_KEY_IN"/>');
	}
} else if (actionFlag == "dtlTxn") {
	$("input").attr("readonly", true);
	$("input[type=button]").attr("disabled", true);
	$("select").attr("disabled", true);
	$("#sbm").attr("disabled", true);
	$(".date").attr("disabled", true);
} else if (actionFlag == "uptTxn") {
	$(".dtl").css("display", "none");
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210306/Input/UptTxn/Submit");
	var success = '${successmsg }';
	if (success && success != "") {
		$("input").attr("readonly", true);
		$("input[type=button]").attr("disabled", true);
		$("select").attr("disabled", true);
		$(".date").attr("disabled", true);
		$("#sbm").attr("disabled", true);
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210306/Input/UptTxn/Init");
	}
} 

$("#sbm").click(function() {
	$("#amount").val($("#amount").val().replaceAll(",", ""));
	$("#form").submit();
});

$("#termCondition").change(function() {
	if ($(this).val() == "1") {
		$("#termLength").attr("readonly", true);
		$("#termLength").val("0");
	} else {
		$("#termLength").attr("readonly", false);
		$("#termLength").val("");
	}
});

});

function queryShowSelReg() {
	showSelReg([ {
		"districtCode" : "param1"
	} ]);
};

function queryShowSelNation(){
	showSelNation([{"countryCode":"param1"}]);
};

function queryShowAllOrg1(){
	showAllOrg([{"accOrgCode":"param1"}]);
};

function queryShowAllOrg2(){
	showAllOrg([{"institutionCode":"param1"}]);
};

function queryShowAllOrg3(){
	showAllOrg([{"swiftCode":"param1"}]);
};

</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="FTZ210306Form">
			<form:form commandName="FTZ210306Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210306.input.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210306Form" class="form-horizontal">
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
				<!-- 所属机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCORGCODE"/>：</td>
				<td colspan="3">
					<form:input id="accOrgCode" path="ftzOffTxnDtl.accOrgCode" class="input-large" maxLength="12" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg1()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>	
				<!-- 金额 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.AMOUNT"/>：</td>
				<td><t:moneyFormat type="text" id="amount" name="ftzOffTxnDtl.amount" value="${FTZ210306Form.ftzOffTxnDtl.amount }" format="###,###,###,###.00" dot="true" /></td>
				
				<!-- 货币 -->
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
				
				<!-- 业务到期日 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TRANDATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.tranDate" type="text" class="input-large date" maxLength="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
			</tr>
			<tr>	
				<!-- 受益人机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.RECEIPTOR_ENT_INST_CODE"/>：</td>
				<td>
					<form:input id="institutionCode" path="ftzOffTxnDtl.institutionCode" class="input-large" maxLength="12"/>
					<input type="button" class="btn btn-small"  onclick="queryShowAllOrg2()"  value="<spring:message code="button.label.Search"/>">
				</td>
				
				<!-- 受益人名称 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.RECEIPTOR_ENT_NAME"/>：</td>
				<td><form:input path="ftzOffTxnDtl.accountName" type="text" class="input-xlarge" maxLength="40"/></td>
			</tr>
			<tr>	
				<!-- 受益人国别代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.RECEIPTOR_COUNTRY_CODE"/>：</td>
				<td>
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" maxLength="3"/>
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>">
				</td>
				
				<!-- 受益人国内地区码 -->
				<td class="label_td"><spring:message code="ftz.label.RECEIPTOR_DISITRICT_CODE"/>：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode"  id="districtCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE }" />
					</form:select>
					<input type="button" class="btn btn-small" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>	
				<!-- 信用证到期日 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.EXPIRATIONDATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.expirationDate" type="text" class="input-large date" maxLength="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
					
				<!-- 期限条件 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_CONDITION"/>：</td>
				<td>
					 <form:select id="termCondition" path="ftzOffTxnDtl.termCondition">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_CONDITION }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<!-- 期限长度 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_LENGTH"/>：</td>
				<td><form:input id="termLength" path="ftzOffTxnDtl.termLength" type="text" class="input-large" maxLength="4" onkeyup="numberFormat(this);"/></td>
					
				<!-- 期限单位 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_UNIT"/>：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.termUnit">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_UNIT }" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl"><td colspan="4"><hr/></td><td>
			<tr class="dtl">	
				<!-- 明细状态 -->
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td>
				<td colspan="3"><t:codeValue items="${FTZ_MSG_STATUS }" key="${FTZ210306Form.ftzOffTxnDtl.chkStatus }" type="text"  cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<!-- 录入用户 -->
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.makUserId" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 录入时间 -->
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210306Form.ftzOffTxnDtl.makDatetime }" format="datetime" name="ftzOffTxnDtl.makDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<!-- 审核用户 -->
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.chkUserId" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 审核时间 -->
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210306Form.ftzOffTxnDtl.chkDatetime }" format="datetime" name="ftzOffTxnDtl.chkDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">
				<!-- 审核附言 -->	
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