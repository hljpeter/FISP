<!-- 6.3.3　应付保函/备用证（210206） / 审核 - 批量页面 -->
<script type="text/javascript">
$(function() {
var checkSelected = function() {
	var id = '';
	$(".tbl_page_body table tr").each(function(i, obj) {
		
		var v = $(obj).hasClass("table-color-click");
		if(v){
			id = $(obj).attr("id");
			return;
		} 
	});
	return id;
};

var success = '${successmsg }';
//if (success && success != "") {
	//("button[name=btn]").attr("disabled", true);
//}

$("#msgChk").click(function() {
	
	$("#balance").val($("#balance").val().replaceAll(",", ""));
	$("#form").submit();
});

//detail
$("#dtl").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + checkSelected() + ")"); 
		showDialog('${pageContext.request.contextPath}/FTZ210206/Auth/DtlTxn/Init?ftzInTxnDtl.msgId=' 
				+ $("#msgId").val() + "&ftzInTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
		
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210206/Auth/DtlMsg/Init?page.page=${page.number+1}");
		$("#form").submit();
		
	}
});


$("#pageTable")
.find("tr")
.bind(
		'dblclick',
		function() {
			var selectedRow = eval("(" + $(this).attr("id") + ")"); 
			showDialog('${pageContext.request.contextPath}/FTZ210206/Auth/DtlTxn/Init?ftzInTxnDtl.msgId=' 
					+ $("#msgId").val() + "&ftzInTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
			
			$("#balance").val($("#balance").val().replaceAll(",", ""));
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210206/Auth/DtlMsg/Init?page.page=${page.number+1}");
			$("#form").submit();
				
			
});

var msgStatus = '${FTZ210206Form.ftzInMsgCtl.msgStatus}';
if(msgStatus=='01'||msgStatus=='03'||msgStatus=='04')
{
	$("#msgChk").attr("disabled", true);
}

});

function BalanceValidation(){
		var submitDate = $("#submitDate").val();//申报日期
		var accountNo = $("#accountNo").val();//账号		
		var subAccountNo = $("#subAccountNo").val();//主账号
		
		if(null == subAccountNo || ""==subAccountNo){
			subAccountNo = accountNo;
		}				
		showDialog(
				'${pageContext.request.contextPath}/FTZINCOM/BalanceCheck?check_AccountNo='
						+ accountNo + "&check_SubAccountNo="
						+ subAccountNo+ "&check_SubmitDate="
						+ submitDate, '500', '1024');
		
	}

</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210206Form">
			<form:form commandName="FTZ210206Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210206.auth.dtl" /></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210206/Auth/DtlMsg/Auth" method="post" modelAttribute="FTZ210206Form" class="form-horizontal">
		<form:hidden path="ftzInMsgCtl.makDatetime" id="msg_makDatetime"/>
		<form:hidden path="ftzInMsgCtl.makUserId" id="makUserId"/>
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="msg_chkDatetime"/>
		<form:hidden path="ftzInMsgCtl.msgStatus"/>
		<form:hidden path="ftzInMsgCtl.msgNo"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID" />：</td>
				<td><form:hidden path="ftzInMsgCtl.branchId" id="branchId1" />
					<form:select id="branchId" path="ftzInMsgCtl.branchId" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="msgId" path="ftzInMsgCtl.msgId"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="submitDate" path="ftzInMsgCtl.submitDate" readonly="true"
						 class="input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUS" />：</td>
				<td><form:hidden path="ftzInMsgCtl.msgStatus" /> <form:select
						path="ftzInMsgCtl.msgStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="accountNo" path="ftzInMsgCtl.accountNo" readonly="true"
						class=".input-large"  />
					</td>
				
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="accountName" path="ftzInMsgCtl.accountName"
						class=".input-large" readonly="true" /></td>
				
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.CURRENCY" />：</td>
				<td><form:hidden path="ftzInMsgCtl.currency" id="currency1" />
					<form:select path="ftzInMsgCtl.currency" id="currency"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>
						
			<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance"
						name="ftzInMsgCtl.balance"
						value="${FTZ210206Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" /></td>
			</tr>
			
			<tr>
				<td class="label_td" colspan="2"><font color="red">*</font>
				<spring:message code="ftz.label.BALANCE_CODE" />： <form:hidden
						path="ftzInMsgCtl.balanceCode" id="balanceCode1" /> <form:select
						id="balanceCode" path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
			
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.OWNER_ACC_ORG_CODE" />：</td>
				<td><form:input id="accOrgCode" path="ftzInMsgCtl.accOrgCode"
						class=".input-large" readonly="true" /></td>
			</tr>
			
			
			
		</table>

		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SND_DATETIME" />：</td>
				<td><form:input id="sndDatetime" path="ftzInMsgCtl.sndDatetime"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="ftz.label.ACK_DATETIME" />：</td>
				<td><form:input id="ackDatetime" path="ftzInMsgCtl.ackDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.result" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_PROC_RESULT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ADD_WORD" />：</td>
				<td colspan="3"><form:input id="addWord"
						path="ftzInMsgCtl.addWord" class=".input-large" readonly="true" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<p class="text-info" align="center"><spring:message code="ftz.label.MSG_DTL_List" /></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.CD_FLAG" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.TRAN_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.AMOUNT" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.COUNTRY_CODE" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
					code="ftz.label.valueDate" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.interestRate" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.DTL_STATUS" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr id='{seqNo:"${dto.seqNo }",makDatetime:"${dto.makDatetime }",chkDatetime:"${dto.chkDatetime }"}'>
						 <td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 40px;"><t:codeValue
								items="${FTZ_CD_FLAG}" key="${dto.cdFlag}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 40px;">${dto.tranDate}</td>
						<td class="vtip" style="text-align: right; width: 50px;"><t:moneyFormat
								type="label" value="${dto.amount}" /></td>
						
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_COUNTRY_CODE}" key="${dto.countryCode}"
								type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.valueDate}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:moneyFormat
								type="label" value="${dto.interestRate}" dot="true" format="###,###,###,###.000000"/></td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto.chkStatus}" type="label" /></td>
						<td style="display: none;">${dto.msgId}</td>
						<td style="display: none;">${dto.seqNo}</td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


<!-- page and buttons -->
<div class="row" style="margin-top: 10px;">
	<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 0px;">
		<div class="leftPage">
			<util:pagination page="${page }" query="ftzInMsgCtl.msgId=${FTZ210206Form.ftzInMsgCtl.msgId }&operFlag=${FTZ210206Form.operFlag }" />
		</div>
	</div>
</div>
<div class="row" style="margin-top: 40px;">
	<div class=" navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="dtl" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DTL_DTL"/></button>
		<button id="msgChk" name="btn" class="btn btn-primary"><spring:message code="ftz.label.AUTH_MSG"/></button>
		<input id="balanceValidation" type="button" class="btn btn-primary"
			onclick="BalanceValidation();"
			value="<spring:message code="ftz.label.balanceValidation"/>">
		<button id="cls" name="btnClose" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>