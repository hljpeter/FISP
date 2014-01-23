<script type="text/javascript">
$(function() {
	var actionFlag = "${FTZ210311Form.actionFlag}";
	if ('addTxn' == actionFlag) {
		var seqNo = $("#seqNo").val();
		if ("" != seqNo && null != seqNo) {
			$("#add").val("<spring:message code='button.lable.AddMore' />");
			var dtlAdd = document.getElementById("add");
			dtlAdd.onclick = addRef;
		}
	}
	var updFlag = '${updFlag }';
	if (updFlag == '1') {
		$("input:not([id='selected_msgId'])").attr("disabled", true);
		$("select").attr("disabled", true);
		if ('addTxn' == actionFlag) {
			$("#add").removeAttr("disabled");
		}
	}
});

function addRef() {
	$("#selected_msgId").val($("#msgId").val());
	var form = document.getElementById("form1");
	form.action = "${pageContext.request.contextPath}/FTZ210311/AddDtlDtlInit";
	form.submit();
}

function addDetail() {
	$("#buyAmt").val($("#buyAmt").val().replaceAll(",", ""));
	$("#sellAmt").val($("#sellAmt").val().replaceAll(",", ""));
	$("#buyRate").val($("#buyRate").val().replaceAll(",", ""));
	$("#sellRate").val($("#sellRate").val().replaceAll(",", ""));
	var form = document.getElementById("form");
	var actionFlag = "${FTZ210311Form.actionFlag}";
	if ('addTxn' == actionFlag) {
		form.action = "${pageContext.request.contextPath}/FTZ210311/AddDtlDtlSubmit";
	} else if ('updTxn' == actionFlag) {
		form.action = "${pageContext.request.contextPath}/FTZ210311/UpdDtlDtlSubmit";
	}
	form.submit();
}

function queryAccOrgCode() {
	showAllOrg([{"accOrgCode" : "param1"}]);
}

function queryOppBankCode() {
	showSelBank([ {
		"oppBankCode" : "param1",
		"oppBankName" : "param2"
	} ]);
}
function queryCountryCode() {
	showSelNation([{"countryCode" : "param1"}]);
}

function queryDistrictCode() {
	showSelReg([{"disitrictCode" : "param1"}]);
}

function clearName() {
	if($("#oppBankCode").val()=="" || null == $("#oppBankCode").val()){
		$("#oppBankName").attr("value","");
	}
}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
		<spring:hasBindErrors name="FTZ210311Form">
			<form:form commandName="FTZ210311Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210311.input.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210311/AddDtlDtlSubmit" 
		method="post" modelAttribute="FTZ210311Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId" id="msgId"/>
		
		<form:hidden path="father_makTime" />
		<form:hidden path="father_chkTime" />
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="ftz.label.SEQ_NO1"/>：</td><!-- 申报序号 -->
				<td><form:input id="seqNo" path="ftzOffTxnDtl.seqNo" type="text" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.SUBMIT_DATE"/>：</td><!-- 申报日期 -->
				<td>
					<form:input path="ftzOffTxnDtl.submitDate" type="text" class="input-large date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.ACCORGCODE"/>：</td><!-- 所属机构代码 -->
				<td>
					<form:select path="ftzOffTxnDtl.accOrgCode">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}"/>
					</form:select>
				</td>
	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.TRAN_GENRE"/>：</td><!-- 结售汇交易类型 -->
				<td>
					<form:select path="ftzOffTxnDtl.tranGenre">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_GENRE}"/>
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.BUY_CURR"/>：</td><!--买入币种 -->
				<td>
					<form:select path="ftzOffTxnDtl.buyCurr">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.SELL_CURR"/>：</td><!-- 卖出币种 -->
				<td>
					<form:select path="ftzOffTxnDtl.sellCurr">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.BUY_AMT"/>：</td><!-- 买入金额 -->
				<td>
					<t:moneyFormat type="text" id="buyAmt" name="ftzOffTxnDtl.buyAmt"
						value="${FTZ210311Form.ftzOffTxnDtl.buyAmt}" format="###,###,###,###.00" dot="true" maxlength="24"/>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.SELL_AMT"/>：</td><!-- 卖出金额 -->
				<td>
					<t:moneyFormat type="text" id="sellAmt" name="ftzOffTxnDtl.sellAmt"
						value="${FTZ210311Form.ftzOffTxnDtl.sellAmt}" format="###,###,###,###.00" dot="true" maxlength="24"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.BUY_RATE"/>：</td><!-- 买入牌价 -->
				<td>
					<t:moneyFormat type="text" id="buyRate" name="ftzOffTxnDtl.buyRate"
						value="${FTZ210311Form.ftzOffTxnDtl.buyRate}" format="###.000000" dot="true" maxlength="13"/>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.SELL_RATE"/>：</td><!-- 卖出牌价 -->
				<td>
					<t:moneyFormat type="text" id="sellRate" name="ftzOffTxnDtl.sellRate"
						value="${FTZ210311Form.ftzOffTxnDtl.sellRate}" format="###.000000" dot="true" maxlength="13"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.SETTLE_DAY"/>：</td><!-- 交割日 -->
				<td>
					<form:input path="ftzOffTxnDtl.tranDate" type="text" class="input-large date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.EXCHANGE_TYPE"/>：</td><!-- 外汇买卖类型 -->
				<td>
					<form:select id="exchangeType" path="ftzOffTxnDtl.exchangeType">
						<form:option value=""></form:option>
						<form:options items="${FTZ_EXCHANGE_TYPE }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td" colspan="2"><spring:message code="ftz.label.OPP_BANK_CODE"/>：<!-- 对方银行或机构代码 -->
				
					<form:input id="oppBankCode" path="ftzOffTxnDtl.institutionCode" type="text" class="input-large"
						maxlength="12" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
					<input id="bankCode" type="button" class="btn btn-small" onclick="queryOppBankCode()"
						value="<spring:message code="button.label.Search" />"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.OPP_BANK_NAME"/>：</td><!-- 对方银行名称 -->
				<td><form:input id="oppBankName" path="ftzOffTxnDtl.oppBankName" type="text" class="input-xlarge" readonly="true"/></td>
			</tr>
			<tr>	
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.OPP_ACCOUNT"/>：</td><!-- 对方账号 -->
				<td>
					<form:input path="ftzOffTxnDtl.accountNo" type="text" class="input-large" maxlength="35"
						onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.OPP_NAME1"/>：</td><!-- 对方户名 -->
				<td>
					<form:input path="ftzOffTxnDtl.accountName" type="text" class="input-xlarge" maxlength="128"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.countryCode"/>：</td><!-- 国别代码 -->
				<td>
					<form:select id="countryCode" path="ftzOffTxnDtl.countryCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_COUNTRY_CODE }" />
					</form:select>
					<input id="country" type="button" class="btn btn-small" onclick="queryCountryCode()"
						value="<spring:message code="button.label.Search" />"/>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.disitrictCode"/>：</td><!-- 国内地区代码 -->
				<td>
					<form:select id="disitrictCode" path="ftzOffTxnDtl.districtCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE }" />
					</form:select>
					<input id="district" type="button" class="btn btn-small" onclick="queryDistrictCode()"
						value="<spring:message code="button.label.Search" />"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.TRAN_TYPE"/>：</td><!-- 交易性质 -->
				<td>
					<form:select path="ftzOffTxnDtl.tranType">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_TYPE }" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td><!-- 明细状态 -->
				<td colspan="3">
					<form:select path="ftzOffTxnDtl.chkStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS }" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td><!-- 录入用户 -->
				<td><form:input id="makUserId" path="ftzOffTxnDtl.makUserId" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td><!-- 录入时间 -->
				<td><form:input id="makDatetime" path="ftzOffTxnDtl.makDatetime" class="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td><!-- 审核用户 -->
				<td><form:input id="chkUserId" path="ftzOffTxnDtl.chkUserId" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td><!-- 审核时间 -->
				<td><form:input id="chkDatetime" path="ftzOffTxnDtl.chkDatetime" class="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td><!-- 审核附言 -->
				<td colspan="3"><form:input id="chkAddWord" path="ftzOffTxnDtl.chkAddWord" class="input-xxlarge" readonly="true"/></td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="add" type="button" class="btn btn-primary" onclick="addDetail()"
			value="<spring:message code="ftz.label.SUBMIT_MSG_DTL"/>"><!-- 提交明细 -->
		<button name="btn" class="btn btn-primary" onclick="javascript: window.close();">
		<spring:message code="button.lable.close"/></button><!-- 关闭 -->
	</div>
</div>
<form:form id="form1" modelAttribute="FTZ210311Form">
	<form:hidden path="selected_msgId" id="selected_msgId" />
</form:form>