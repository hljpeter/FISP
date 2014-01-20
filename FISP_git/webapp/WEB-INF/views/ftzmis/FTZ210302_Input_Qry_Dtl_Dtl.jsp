<!-- 6.3.2　应付信用证（210302） / 录入新增 - 交易页面 -->
<script type="text/javascript">
$(function() {
	var termCon = '${FTZ210302Form.ftzOffTxnDtl.termCondition }';
	if (termCon == "1") {
		$("#termLength").attr("readonly", true);
	} else {
		$("#termLength").attr("readonly", false);
	}

	var actionFlag = '${FTZ210302Form.actionFlag }';
	if (actionFlag == "addTxn") {
		$(".dtl").css("display", "none");
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210302/Input/AddTxn/Submit");
		var success = '${successmsg }';
		if (success && success != "") {
			$("input").attr("readonly", true);
			$("input[type=button]").attr("disabled", true);
			$("select").attr("disabled", true);
			$(".date").attr("disabled", true);
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210302/Input/AddTxn/Init");
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
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210302/Input/UptTxn/Submit");
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
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210302/Input/UptTxn/Init");
		}
	} 

	$("#termCondition").change(function() {
		if ($(this).val() == "1") {
			$("#termLength").attr("readonly", true);
			$("#termLength").val("0");
		} else {
			$("#termLength").attr("readonly", false);
			$("#termLength").val("");
		}
	});


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
<div id="id_showMsg" style="display: none">
	<br /> <br />
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

<div class="page_title"><spring:message code="ftzmis.title.210302.input.txn" /><!-- 应付信用证录入/修改 - 批量明细 - 明细详情 --></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210302Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId" id="msgId"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO" /><!-- 明细序号 -->：</td>
				<td>
					<form:input id="seqNo" path="ftzOffTxnDtl.seqNo" class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SUBMIT_DATE" /><!-- 申报日期 -->：</td>
				<td>
					<form:input id="submitDate" path="ftzOffTxnDtl.submitDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=".input-large"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCORGCODE" /><!-- 所属机构代码 -->：</td>
				<td>
					<form:input id="accOrgCode" path="ftzOffTxnDtl.accOrgCode" class="input-large" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg1()" value="<spring:message code="button.label.Search"/>">
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.INSTITUTIONCODE" />：</td>
				<td>
					<form:input id="institutionCode" path="ftzOffTxnDtl.institutionCode" class="input-large" />
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg2()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCOUNTORGNAME" /><!-- 申请人机构名称 -->：</td>
				<td>
					<form:input id="accountName" path="ftzOffTxnDtl.accountName" class="input-large" />
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.COUNTRYCODE" /><!-- 申请人国别代码 -->：</td>
				<td>
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.DISTRICTCODE" /><!-- 申请人国内地区码 -->：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode" id="districtCode" class="disCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<input type="button" class="btn btn-small disCode" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>">
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SWIFT_CODE" /><!-- 境内外对手行代码 -->：</td>
				<td>
					<form:input id="swiftCode" path="ftzOffTxnDtl.swiftCode" class="input-large" />
					<input type="button" class="btn btn-small"  onclick="queryShowAllOrg3()"  value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CURRENCY" /><!-- 货币 -->：</td>
				<td>
				 <form:select path="ftzOffTxnDtl.currency"  id="currency">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.AMOUNT" /><!-- 金额 -->：</td>
				<td>
				 <t:moneyFormat type="text" id="amount" name="ftzOffTxnDtl.amount" value="${FTZ210302Form.ftzOffTxnDtl.amount}" format="###,###,###,###.00" dot="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TRANDATE" /><!-- 业务到期日 -->：</td>
				<td>
				 <form:input id="tranDate" path="ftzOffTxnDtl.tranDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=".input-large"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.EXPIRATIONDATE" /><!-- 信用证到期日 -->：</td>
				<td>
				 <form:input id="expirationDate" path="ftzOffTxnDtl.expirationDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=".input-large"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_CONDITION" /><!-- 期限条件 -->：</td>
				<td>
					<form:select path="ftzOffTxnDtl.termCondition" id="termCondition">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_CONDITION_EN}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.TERM_LENGTH" /><!-- 期限长度 -->：</td>
				<td>
					<form:input id="termLength" path="ftzOffTxnDtl.termLength" type="text" class="input-large" maxLength="4" onkeyup="numberFormat(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TERM_UNIT" /><!-- 期限单位 -->：</td>
				<td colspan="3">
					<form:select path="ftzOffTxnDtl.termUnit" id="termUnit">
						<form:option value=""></form:option>
						<form:options items="${FTZ_REBUY_TERM_UNIT}" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl"><td colspan="4"><hr/></td><td>
			<tr class="dtl">
				<td class="label_td"><spring:message code="ftz.label.OPER_STATUS" /><!-- 操作状态 -->：</td>
				<td colspan="3">
					<form:select path="ftzOffTxnDtl.chkStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_EN}" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl">
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID" /><!-- 录入用户 -->：</td>
				<td><form:input id="makUserId" path="ftzOffTxnDtl.makUserId" class=".input-large" readonly="true" /></td>
				
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME" /><!-- 录入时间 -->：</td>
				<td><form:input id="makDatetime" path="ftzOffTxnDtl.makDatetime" class=".input-large" readonly="true" /></td>
			</tr>
			<tr class="dtl">
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID" /><!-- 审核用户 -->：</td>
				<td><form:input id="chkUserId" path="ftzOffTxnDtl.chkUserId" class=".input-large" readonly="true" /></td>
				
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME" /><!-- 审核时间 -->：</td>
				<td><form:input id="chkDatetime" path="ftzOffTxnDtl.chkDatetime" class=".input-large" readonly="true" /></td>
			</tr>
			<tr class="dtl">
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD" /><!-- 审核附言 -->：</td>
				<td colspan="3"><form:input id="chkAddWord" path="ftzOffTxnDtl.chkAddWord" class="input-xxlarge" readonly="true" /></td>
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