<script type="text/javascript">
$(function() {
var checkSelected = function() {
	var id = '';
	$(".tbl_page_body table tr ").each(function(i, obj) {
		var v = $(obj).hasClass("table-color-click");
		if(v){
			id = $(obj).attr("id");
			return;
		} 
	});
	return id;
};

var success = '${successmsg }';
if (success && success != "") {
	$("button[name=btn]").attr("disabled", true);
}

$("#msgChk").click(function() {
	$("#form").submit();
});

$(".tbl_page_body table tr").dblclick(function() {
	var selectedRow = eval("(" + $(this).attr("id") + ")"); 
	showDialog('${pageContext.request.contextPath}/FTZ210302/Auth/DtlTxn/Init?ftzOffTxnDtl.msgId=' 
			+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
});

//detail
$("#dtl").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); alert(selectedRow.seqNo);
		showDialog('${pageContext.request.contextPath}/FTZ210302/Auth/DtlTxn/Init?ftzOffTxnDtl.msgId=' 
				+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
				
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210302/Auth/DtlMsg/Init?page.page=" + ${page.number + 1 } + "&FtzOffMsgCtl.msgId="+ ${FTZ210302Form.ftzOffMsgCtl.msgId });
		$("#form").submit();
	}
});

});
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
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

<div class="page_title"><spring:message code="ftzmis.title.210302.query.txn" /><!-- 应付信用证 - 批量明细 --></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210302/Auth/DtlMsg/Auth"
		method="post" modelAttribute="FTZ210302Form" class="form-horizontal">
		<form:hidden path="ftzOffMsgCtl.makDatetime" id="msg_makDatetime"/>
		<form:hidden path="ftzOffMsgCtl.chkDatetime" id="msg_chkDatetime"/>
		<form:hidden path="ftzOffMsgCtl.msgStatus"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.common.branchId" />：</td>
				<td>
				<form:select path="ftzOffMsgCtl.branchId" id="branchId" disabled="true">
					<form:option value=""></form:option>
					<form:options items="${SM_0002}"/>
				</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MGS_ID" />：</td>
				<td><form:input id="msgId" path="ftzOffMsgCtl.msgId"
						class=".input-large" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.WORK_DATE" />：</td>
				<td><form:input id="submitDate" path="ftzOffMsgCtl.workDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large"  disabled="true"/></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.msgStatus" id="msgStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_INPUT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.result" id="result" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_PROC_RESULT_EN}" />
					</form:select></td>
				<td class="label_td"></td>
				<td></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ADDWORD" />：</td>
				<td colspan="3"><form:input id="addWord" path="ftzOffMsgCtl.addWord" class="input-large" cssStyle="width:600px" disabled="true"/></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
				<p class="text-info" align="center"><spring:message code="ftz.label.MSG_DTL_List" /><!-- 明细列表 --></p>
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px">No</th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message code="ftz.label.SUBMIT_DATE" /><!-- 申报日期 --></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message code="ftz.label.ACCOUNTNAME" /><!-- 申请人名称 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CURRENCY" /><!-- 货币 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.AMOUNT" /><!-- 金额 --></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message code="ftz.label.COUNTRYCODE" /><!-- 申请人国别代码 --></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message code="ftz.label.DISTRICTCODE" /><!-- 申请人地区代码 --></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message code="ftz.label.DTL_STATUS" /><!-- 明细状态 --></th>
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
						<td class="vtip" style="text-align: left; width: 40px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 80px;">${dto.institutionCode}</td>
						<td class="vtip" style="text-align: right; width: 50px;">
						<t:codeValue items="${SYS_CURRENCY}" key="${dto.currency}" type="label" />
						</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:moneyFormat type="label" value="${dto.amount}" /></td>
						<td class="vtip" style="text-align: left; width: 60px;">${dto.countryCode}</td>
						<td class="vtip" style="text-align: left; width: 60px;"><t:codeValue items="${FTZ_DISITRICT_CODE}" key="${dto.districtCode}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto.chkStatus}" type="label" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page }" action="/FTZ210302/Auth/DtlMsg/Init" query="operFlag=${FTZ210302Form.operFlag }&ftzOffMsgCtl.msgId=${FTZ210302Form.ftzOffMsgCtl.msgId }" />
		</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer" style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<button id="dtl" name="btnDtl" class="btn btn-primary"><spring:message code="ftz.label.DTL_DTL"/></button>
		<button id="msgChk" name="btn" class="btn btn-primary"><spring:message code="ftz.label.AUTH_MSG"/></button>
		<button id="cls" name="btnClose" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>