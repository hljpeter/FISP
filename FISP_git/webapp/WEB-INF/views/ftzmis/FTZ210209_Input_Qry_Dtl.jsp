<!-- 6.3.3　应付保函/备用证（210209） / 录入新增页面 -->
<script type="text/javascript">
$(function() {
	$("#balance").val($("#balance").val().replaceAll(",", ""));
	
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


var msgStatus=$("#msgStatus").val();
var actionFlag = '${FTZ210209Form.actionFlag }';
if (actionFlag == "addMsg") {
	$("button:not(#cls)").attr("disabled", true);
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/AddMsg/Sumbit");
	var success = '${successmsg}';
	if (success && success != '') {
		$("button:not(#cls)").attr("disabled", false);
		$("input[type=submit]").attr("disabled", true);
	}
	
} else if (actionFlag == "dtlMsg") {
	$("input[type=submit]").css("display", "none");
	$("button:not(#dtl, #cls)").css("display", "none");
	$(".pboc").css("display", "");
} else if (actionFlag == "uptMsg") {
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/UptMsg/Sumbit");
	//var success = '${successmsg}';
	//if (success && success != '') {
		//$("input[type=submit]").attr("disabled", true);
	//}
	
} else {
	//var operFlag = '${FTZ210209Form.operFlag }';
	//if (operFlag == "updated") {
		//$("input[type=submit]").attr("disabled", true);
	//}
}


// add
$("#add").click(function() {
	if("03" == msgStatus){
	       alert("批量审核已完成");
	       return;
	}
	$("#balance").val($("#balance").val().replaceAll(",", ""));
	showDialog('${pageContext.request.contextPath}/FTZ210209/Input/AddTxn/Init?ftzInTxnDtl.msgId=' + $("#msgId").val(), '600', '1040');
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/UptMsg/Init");
	$("#form").submit();
	
});

//detail
$("#dtl").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		
		selectedRow = eval("(" + checkSelected() + ")"); 
		showDialog('${pageContext.request.contextPath}/FTZ210209/QryDtlDtl?ftzInTxnDtl.msgId=' 
				+ $("#msgId").val() + "&ftzInTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
	}
});

//update
$("#upt").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		
		selectedRow = eval("(" + selectedRow + ")");
		if("03" == msgStatus){
		       alert("批量审核已完成");
		       return;
		}
		if("03" == selectedRow.chkStatus){
		       alert("审核通过明细无法修改或删除");
		       return;
		}
		$("#operFlag").val("updated");
		showDialog('${pageContext.request.contextPath}/FTZ210209/Input/UptTxn/Init?ftzInTxnDtl.msgId=' 
				+ $("#msgId").val() + '&ftzInTxnDtl.seqNo=' + selectedRow.seqNo, '600', '1040');
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210209/Input/UptMsg/Init");
		$("#form").submit();
	}
});

// delete
$("#del").click(function() {
	var selectedRow = checkSelected(); 
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")");
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
		if (confirm(msg)) {
			if("03" == msgStatus){
			       alert("批量审核已完成");
			       return;
			}
			if("03" == selectedRow.chkStatus){
			       alert("审核通过明细无法修改或删除");
			       return;
			}
			$("#operFlag").val("updated");
			$("#balance").val($("#balance").val().replaceAll(",", ""));
			$("#form").attr("action", '${pageContext.request.contextPath}/FTZ210209/Input/DelTxn/Submit?ftzInTxnDtl.msgId=' 
					+ $("#msgId").val() + "&ftzInTxnDtl.seqNo=" + selectedRow.seqNo + "&ftzInTxnDtl.makDatetime=" + selectedRow.makDatetime 
					+ "&ftzInTxnDtl.chkDatetime=" + selectedRow.chkDatetime);
			$("#form").submit();

		} else {
			return false;
		}
	}
});


$("#pageTable")
.find("tr")
.bind(
		'dblclick',
		function() {
			var selected_msgId = $(this).find("td:eq(11)")
					.text();
			var selected_seqNo = $(this).find("td:eq(12)")
					.text();
			showDialog('${pageContext.request.contextPath}/FTZ210209/QryDtlDtl?ftzInTxnDtl.msgId=' 
					+ selected_msgId + "&ftzInTxnDtl.seqNo=" + selected_seqNo, '600', '1040');
		});

$("#accountBtn").attr("disabled", false);
$("#save").attr("disabled", false);
});


function queryAct() {
	showSelAct([ {
		"accountNo" : "param1"
	} ]);
	accountFill();
}

function accountFill() {
	var accountNo = $("#accountNo").val();
	if ("" != accountNo ) {
		accoutQry();
	}
}

function accoutQry() {
	var accountNo = $("#accountNo").val();
	$.ajax({
		url : contextPath + "/FTZ210209/DtlAccountQry",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			accountNo : accountNo
		},
		success : function(rs) {
			dtlExist = rs.dtlExist;
			if (null == dtlExist || false == dtlExist) {
				alert("无此账号信息！");
				$("#branchId").val("");
				$("#branchId1").val("");
				$("#accountName").val("");
				$("#balanceCode").val("");
				$("#balanceCode1").val("");
				$("#currency").val("");
				$("#currency1").val("");
				$("#balance").val("");
				$("#balance1").val("");
				$("#accOrgCode").val("");
				$("#accOrgCode1").val("");
			} else {
				$("#branchId").val(rs.branchId);
				$("#branchId1").val(rs.branchId);
				$("#accountName").val(rs.accountName);
				$("#balanceCode").val(rs.balanceCode);
				$("#balanceCode1").val(rs.balanceCode);
				$("#currency").val(rs.currency);
				$("#currency1").val(rs.currency);
				$("#balance").val(rs.balance);
				$("#balance1").val(rs.balance);
				$("#accOrgCode").val(rs.accOrgCode);
				$("#accOrgCode1").val(rs.accOrgCode);
			}
		}
	});
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
		<spring:hasBindErrors name="FTZ210209Form">
			<form:form commandName="FTZ210209Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210209.input.dtl"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210209Form" class="form-horizontal">
		<form:hidden path="ftzInMsgCtl.makDatetime" id="msg_makDatetime"/>
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="msg_chkDatetime"/>
		<form:hidden path="ftzInMsgCtl.msgStatus" id="msgStatus"/>
		<form:hidden path="ftzInMsgCtl.branchId" id="branchId"/>
		<form:hidden path="ftzInMsgCtl.balance" id="balance"/>
		<form:hidden path="ftzInMsgCtl.currency" id="currency"/>
		<form:hidden path="ftzInMsgCtl.balanceCode" id="balanceCode"/>
		<form:hidden path="ftzInMsgCtl.accOrgCode" id="accOrgCode"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID" />：</td>
				<td>
					<form:select id="branchId1" path="ftzInMsgCtl.branchId" disabled="true">
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
				<td><form:input id="submitDate" path="ftzInMsgCtl.submitDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.msgStatus" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_MSG_STATUS }" />
					</form:select></td>
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="accountNo" path="ftzInMsgCtl.accountNo"
						class=".input-large" onblur="accountFill()" />
					<button type="button" id="accountBtn" class="btn btn-small" onclick="queryAct()">
						<spring:message code="button.label.Search" />
					</button></td>
				
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="accountName" path="ftzInMsgCtl.accountName"
						class=".input-large" readonly="true" /></td>
				
			</tr>
			
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.CURRENCY" />：</td>
				<td>
					<form:select path="ftzInMsgCtl.currency" id="currency1"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>
						
			<td class="label_td"><font color="red">*</font>
				<spring:message code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance1"
						name="ftzInMsgCtl.balance"
						value="${FTZ210209Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" /></td>
			</tr>
			
			<tr>
				<td class="label_td" colspan="2"><font color="red">*</font>
				<spring:message code="ftz.label.BALANCE_CODE" />：  <form:select
						id="balanceCode1" path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
			
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.OWNER_ACC_ORG_CODE" />：</td>
				<td>
					<form:select id="accOrgCode1" path="ftzInMsgCtl.accOrgCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
			</tr>
			
			<tr class="pboc" style="display: none;"><td colspan="4"><hr/></td></tr>
			<tr class="pboc" style="display: none;">	
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS"/>：</td>
				<td colspan="3">
					<form:select path="ftzInMsgCtl.result" disabled="true" class="forever">
						<option value=""></option>
						<form:options items="${FTZ_PROC_RESULT }" />
					</form:select>
				</td>
			</tr>
			<tr class="pboc" style="display: none;">	
				<td class="label_td"><spring:message code="ftz.label.ADDWORD"/>：</td>
				<td colspan="3">
					<form:input path="ftzInMsgCtl.addWord" type="text" class="forever input-xxlarge" readonly="true"/>
				</td>
			</tr>
		
			
			<tr><td colspan="4" align="center">
				<input type="submit" id="save" name="btn" class="btn btn-primary" value='<spring:message code="ftz.label.SUBMIT_MSG"/>' onclick="javascript: $('#operFlag').val('updated');"/>
			</td></tr>
	    </table>											
	</form:form>
</div>
<p class="text-info" align="center"><spring:message code="ftz.label.MSG_DTL_List"/></p>

<div class="row">
	<div class="tbl_page_head">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
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
					code="ftz.label.TERM_LENGTH" /></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message
					code="ftz.label.TERM_UNIT" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
					code="ftz.label.valueDate" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
					code="ftz.label.EXPIRE_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="210209ftz.label.INTERESTRATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
					code="ftz.label.DTL_STATUS" /></th>
					
			</tr>
			</thead>
		</table>
    </div>
    <div class="tbl_page_body">
		<table id="pageTable" class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
			<form:form id="FTZ210206Form" action="${pageContext.request.contextPath}" modelAttribute="FTZ210206Form">
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr id='{seqNo:"${dto.seqNo }",makDatetime:"${dto.makDatetime }",chkDatetime:"${dto.chkDatetime }",chkStatus:"${dto.chkStatus}"}'>
		          <td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 40px;"><t:codeValue
								items="${FTZ_CD_FLAG}" key="${dto.cdFlag}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 40px;">${dto.tranDate}</td>
						<td class="vtip" style="text-align: right; width: 50px;"><t:moneyFormat
								type="label" value="${dto.amount}" /></td>
						
						<td class="vtip" style="text-align: left; width: 50px;">${dto.countryCode}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.termLength}</td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
								items="${FTZ_REBUY_TERM_UNIT}" key="${dto.termUnit}"
								type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.valueDate}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.expireDate}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:moneyFormat
								type="label" value="${dto.interestRate}" dot="true" format="###,###,###,###.000000"/></td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto.chkStatus}" type="label" /></td>
						<td style="display: none;">${dto.msgId}</td>
						<td style="display: none;">${dto.seqNo}</td>
				</tr>
	        </c:forEach>
	        </form:form>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 0px;">
	<div class="leftPage">
		<util:pagination page="${page }" query="msgId=${ftzInMsgCtl.msgId }" />
	</div>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="add" name="btn" class="btn btn-primary"><spring:message code="ftz.label.ADD_DTL"/></button>
		<button id="upt" name="btn" class="btn btn-primary"><spring:message code="ftz.label.UPT_DTL"/></button>
		<button id="del" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DEL_DTL"/></button>
		<button id="dtl" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DTL_DTL"/></button>
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>