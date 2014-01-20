<!-- 6.3.4　信用证保兑（210304） - 进口应付 / 审核 - 交易页面 -->
<script type="text/javascript">
$(function() {
var success = '${successmsg }';
if (success && success != "") {
	$("button[name=btnChk]").attr("disabled", true);
	$("input").attr("readonly", true);
}
var error = '${errmsg }';
if (error && error != "") {
	$("button[name=btnChk]").attr("disabled", true);
	$("input").attr("readonly", true);
}
var msg = '${FTZ210304Form.msg }';
if (msg && msg != "") {
	$("#notice").css("display", "");
	$("#next").attr("disabled", true);
}
	
var status= '${FTZ210304Form.ftzOffTxnDtl.chkStatus }';
if (status && status == '03') {
	$("#chkPass").attr("disabled", true);
}
	
$("button[name=btnChk]").click(function() {
	$("#amount").val($("#amount").val().replaceAll(",", ""));
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Auth/DtlTxn/Auth");
	$("#form").submit();
});

$("#next").click(function() {
	$("#amount").val($("#amount").val().replaceAll(",", ""));
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Auth/DtlTxn/Next");
	$("#form").submit();
});

});

</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="FTZ210304Form">
			<form:form commandName="FTZ210304Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210304.auth.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210304Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId" id="msgId"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<form:hidden path="msg"/>
		<form:hidden path="ftzOffTxnDtl.chkStatus"/>
		<table class="tbl_search">
			<tr>
				<!-- 申报序号 -->
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO"/>：</td>
				<td><form:input path="ftzOffTxnDtl.seqNo" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 申报日期 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SUBMIT_DATE"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210304Form.ftzOffTxnDtl.submitDate }" format="date" name="ftzOffTxnDtl.submitDate" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 所属机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCORGCODE"/>：</td>
				<td colspan="3">
					<form:input id="accOrgCode" path="ftzOffTxnDtl.accOrgCode" class="input-large" maxLength="12" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg1()" value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
			</tr>
			<tr>	
				<!-- 金额 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.AMOUNT"/>：</td>
				<td><t:moneyFormat type="text" id="amount" name="ftzOffTxnDtl.amount" value="${FTZ210304Form.ftzOffTxnDtl.amount }" format="###,###,###,###.00" dot="true"  readonly="true"/></td>
				
				<!-- 货币 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzOffTxnDtl.currency" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<!-- 境内外对手行代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SWIFT_CODE"/>：</td>
				<td>
					<form:input id="swiftCode" path="ftzOffTxnDtl.swiftCode" class="input-large" maxLength="11" readonly="true"/>
					<input type="button" class="btn btn-small"  onclick="queryShowAllOrg3()"  value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
				
				<!-- 申请人企业名称 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.APPLICANT_ENT_NAME"/>：</td>
				<td><form:input path="ftzOffTxnDtl.accountName" type="text" class="input-large" maxLength="128" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 申请人企业机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.APPLICANT_ENT_INST_CODE"/>：</td>
				<td colspan="3"><form:input id="institutionCode" path="ftzOffTxnDtl.institutionCode" class="input-large" maxLength="12" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 申请人国别代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.APPLICANT_COUNTRY_CODE"/>：</td>
				<td>
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" maxLength="3" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
				
				<!-- 申请人国内地区码 -->
				<td class="label_td"><spring:message code="ftz.label.APPLICANT_DISTRICT_CODE"/>：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode"  id="districtCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<input type="button" class="btn btn-small" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
			</tr>
			<tr>	
				<!-- 保兑到期日 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CONFIRMED_EXPIRE_DATE"/>：</td>
				<td colspan="3"><t:dateTimeFormat type="text" value="${FTZ210304Form.ftzOffTxnDtl.expirationDate }" format="date" name="ftzOffTxnDtl.expirationDate" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr><td colspan="4"><hr/></td><td>
			<tr>	
				<!-- 明细状态 -->
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td>
				<td colspan="3"><t:codeValue items="${FTZ_MSG_STATUS }" key="${FTZ210304Form.ftzOffTxnDtl.chkStatus }" type="text"  cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 录入用户 -->
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.makUserId" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 录入时间 -->
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210304Form.ftzOffTxnDtl.makDatetime }" format="datetime" name="ftzOffTxnDtl.makDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 审核用户 -->
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.chkUserId" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 审核时间 -->
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210304Form.ftzOffTxnDtl.chkDatetime }" format="datetime" name="ftzOffTxnDtl.chkDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>
				<!-- 审核附言 -->	
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3"><form:input path="ftzOffTxnDtl.chkAddWord" type="text" class="input-xxlarge"/></td>
			</tr>
	    </table>											
	</form:form>
	<p id="notice" class="text-error" style="display: none;"><spring:message code="i.ftzmis.210304.0006"/></p>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="chkPass" name="btnChk" class="btn btn-primary" onclick='javascript: $("#operFlag").val("03");'><spring:message code="ftz.label.AUTH"/></button>
		<button id="chkRej" name="btnChk" class="btn btn-primary" onclick='javascript: $("#operFlag").val("04");'><spring:message code="ftz.label.UNAUTH"/></button>
		<!--<button id="next" name="btn" class="btn btn-primary"><spring:message code="ftz.label.NEXT"/></button>-->
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>