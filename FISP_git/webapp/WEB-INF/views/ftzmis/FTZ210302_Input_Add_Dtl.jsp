<script type="text/javascript">
	function addDetail() {
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210302/AddDtlDtlSubmit";
		form.submit();
	}
	
	function keyIn(){
		window.location.href = "${pageContext.request.contextPath}/FTZ210302/AddDtlDtlInit?selected_msgId="+$("#msgId").val();
	}

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
	
	
	$(function() {
		if($("#seqNo").val()!=""){
			$("#add").hide();
			$("#keyid").show();
		}
	});
	
	function changeTermCondition(){
		if($("#termCondition").val()=="1"){
			$("#termLength").val(0);
			$("#termLength").attr("readonly","true");
		}else{
			$("#termLength").val("");
			$("#termLength").removeAttr("readonly");
		}
	}
</script>
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
		<spring:hasBindErrors name="FTZ210302Form">
			<form:form commandName="FTZ210302Form">
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
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210302Form" class="form-horizontal">
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
					<form:input id="accOrgCode" path="ftzOffTxnDtl.accOrgCode" class="input-large" />
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
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" />
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.DISTRICTCODE" /><!-- 申请人国内地区码 -->：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode"  id="districtCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
					<input type="button" class="btn btn-small" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>">
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
					<form:select path="ftzOffTxnDtl.termCondition" id="termCondition" onchange="changeTermCondition()">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TERM_CONDITION_EN}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.TERM_LENGTH" /><!-- 期限长度 -->：</td>
				<td>
					<form:input id="termLength" path="ftzOffTxnDtl.termLength" class="input-large" />
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
			<tr>
				<td class="label_td"><spring:message code="ftz.label.OPER_STATUS" /><!-- 操作状态 -->：</td>
				<td colspan="3"><form:select path="ftzOffTxnDtl.chkStatus"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_EN}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID" /><!-- 录入用户 -->：</td>
				<td><form:input id="makUserId" path="ftzOffTxnDtl.makUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME" /><!-- 录入时间 -->：</td>
				<td><form:input id="makDatetime" path="ftzOffTxnDtl.makDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID" /><!-- 审核用户 -->：</td>
				<td><form:input id="chkUserId" path="ftzOffTxnDtl.chkUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME" /><!-- 审核时间 -->：</td>
				<td><form:input id="chkDatetime" path="ftzOffTxnDtl.chkDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD" /><!-- 审核附言 -->：</td>
				<td colspan="3"><form:input id="chkAddWord"
						path="ftzOffTxnDtl.chkAddWord" class="input-xxlarge"
						readonly="true" /></td>
			</tr>
		</table>

	</form:form>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="add" type="button" class="btn btn-primary" onclick="addDetail();" value="<spring:message code="ftz.label.SUBMIT_MSG_DTL"/>"> 
		<input id="keyid" type="button" class="btn btn-primary" onclick="keyIn();" value="<spring:message code="ftz.label.GOON_KEY_IN"/>" style="display: none">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>