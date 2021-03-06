<!-- 6.3.4　信用证保兑（210304） - 进口应付 / 录入新增&修改 - 批量页面 -->
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

$(".tbl_page_body table tr ").dblclick(function() {
	var selectedRow = eval("(" + $(this).attr("id") + ")"); 
	showDialog('${pageContext.request.contextPath}/FTZ210304/Input/DtlTxn/Init?ftzOffTxnDtl.msgId=' 
			+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
});

var actionFlag = '${FTZ210304Form.actionFlag }';
if (actionFlag == "addMsg") {
	$("button:not(#cls)").attr("disabled", true);
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Input/AddMsg/Sumbit");
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
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Input/UptMsg/Sumbit");
	$(".pboc").css("display", "");
	//var success = '${successmsg}';
	//if (success && success != '') {
	//	$("input[type=submit]").attr("disabled", true);
	//}
} else {
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Input/UptMsg/Sumbit");
	//var operFlag = '${FTZ210304Form.operFlag }';
	//if (operFlag == "updated") {
	//	$("input[type=submit]").attr("disabled", true);
	//}
}

// add
$("#add").click(function() {
	showDialog('${pageContext.request.contextPath}/FTZ210304/Input/AddTxn/Init?ftzOffTxnDtl.msgId=' 
		+ $("#msgId").val() + '&time=' + new Date().getTime(), '600', '1040');
	
	$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Input/UptMsg/Init");
	$("#form").submit();
});

//detail
$("#dtl").click(function() {
	var selectedRow = checkSelected(); 
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		showDialog('${pageContext.request.contextPath}/FTZ210304/Input/DtlTxn/Init?ftzOffTxnDtl.msgId=' 
				+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
	}
});

//update
$("#upt").click(function() {
	var selectedRow = checkSelected(); 
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		if (selectedRow.chkStatus == '03') {
			alert('<spring:message code="e.ftzmis.2103.0027"/>');
		} else {
			$("#operFlag").val("updated");
			showDialog('${pageContext.request.contextPath}/FTZ210304/Input/UptTxn/Init?ftzOffTxnDtl.msgId=' 
					+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo + '&time=' + new Date().getTime(), '600', '1040');
				
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Input/UptMsg/Init?page.page=" + ${page.number + 1 });
			$("#form").submit();
		}
	}
});

// delete
$("#del").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		if (selectedRow.chkStatus == '03') {
			alert('<spring:message code="e.ftzmis.2103.0027"/>');
		} else {
			var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
			if (confirm(msg)) {
				$("#operFlag").val("updated");
				$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210304/Input/DelTxn/Submit?page.page=" + ${page.number + 1 } + "&ftzOffTxnDtl.msgId=" 
						+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo + "&ftzOffTxnDtl.makDatetime=" + selectedRow.makDatetime 
						+ "&ftzOffTxnDtl.chkDatetime=" + selectedRow.chkDatetime);
				$("#form").submit();
			} else {
				return false;
			}
		}
	}
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
<div class="page_title"><spring:message code="ftzmis.title.210304.input.msg"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210304Form" class="form-horizontal">
		<form:hidden path="ftzOffMsgCtl.makDatetime" id="msg_makDatetime"/>
		<form:hidden path="ftzOffMsgCtl.chkDatetime" id="msg_chkDatetime"/>
		<form:hidden path="ftzOffMsgCtl.msgStatus"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="ftz.label.BRANCH"/>：</td>
				<td>
					<form:select path="ftzOffMsgCtl.branchId">
						<option value=""></option>
						<form:options items="${SM_0002 }" />
					</form:select>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.WORK_DATE"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210304Form.ftzOffMsgCtl.workDate }" format="date" name="ftzOffMsgCtl.workDate" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.MSG_ID"/>：</td>
				<td><form:input path="ftzOffMsgCtl.msgId" id="msgId" type="text" class="input-xlarge" readonly="true"/></td>
	
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS"/>：</td>
				<td>
					<form:select path="ftzOffMsgCtl.msgStatus" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_MSG_STATUS }" />
					</form:select>
				</td>
			</tr>
			<tr class="pboc" style="display: none;"><td colspan="4"><hr/></td></tr>
			<tr class="pboc" style="display: none;">	
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS"/>：</td>
				<td colspan="3">
					<form:select path="ftzOffMsgCtl.result" disabled="true" class="forever">
						<option value=""></option>
						<form:options items="${FTZ_PROC_RESULT }" />
					</form:select>
				</td>
			</tr>
			<tr class="pboc" style="display: none;">	
				<td class="label_td"><spring:message code="ftz.label.ADDWORD"/>：</td>
				<td colspan="3">
					<form:input path="ftzOffMsgCtl.addWord" type="text" class="forever input-xxlarge" readonly="true"/>
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
	        	<th class="tbl_page_th" width="20px"><spring:message code="ftz.label.NO"/></th>
	          	<th class="tbl_page_th" width="100px"><spring:message code="ftz.label.ACCORGCODE"/></th>
	          	<th class="tbl_page_th" width="80px"><spring:message code="ftz.label.SUBMIT_DATE"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="ftz.label.AMOUNT"/></th>
	          	<th class="tbl_page_th" width="100px"><spring:message code="ftz.label.CURRENCY"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="ftz.label.APPLICANT_COUNTRY_CODE"/></th>
	          	<th class="tbl_page_th" width="160px"><spring:message code="ftz.label.APPLICANT_ENT_NAME"/></th>
	          	<th class="tbl_page_th" width="80px"><spring:message code="ftz.label.DTL_STATUS"/></th>
			</tr>
			</thead>
		</table>
    </div>
    <div class="tbl_page_body">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
			<form:form id="FTZ210304Form" action="${pageContext.request.contextPath}" modelAttribute="FTZ210304Form">
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr id='{seqNo:"${dto.seqNo }",makDatetime:"${dto.makDatetime }",chkDatetime:"${dto.chkDatetime }",chkStatus:"${dto.chkStatus }"}'>
		          	<td class="tbl_page_td_left vtip" width="20px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="100px"><t:codeValue items="${SM_0002 }" key="${dto.accOrgCode }" type="label" /></td>
				  	<td class="tbl_page_td_left vtip" width="80px"><t:dateTimeFormat type="label" value="${dto.submitDate }" format="date"/></td>
		            <td class="tbl_page_td_right vtip" width="120px"><t:moneyFormat type="label" value="${dto.amount }" format="###,###,###,###.00" dot="true"/></td>
		            <td class="tbl_page_td_left vtip" width="100px"><t:codeValue items="${SYS_CURRENCY }" key="${dto.currency }" type="label" /></td>
		            <td class="tbl_page_td_left vtip" width="120px">${dto.countryCode }</td>
		            <td class="tbl_page_td_left vtip" width="160px">${dto.accountName }</td>
		            <td class="tbl_page_td_left vtip" width="80px"><t:codeValue items="${FTZ_MSG_STATUS }" key="${dto.chkStatus }" type="label" /></td>
				</tr>
	        </c:forEach>
	        </form:form>
			</tbody>
		</table>
	</div>
</div>
<!-- page and buttons -->
<div class="row" style="margin-top: 10px;">
	<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 0px;">
		<div class="leftPage">
			<util:pagination page="${page }" action="/FTZ210304/Input/UptMsg/Init" query="ftzOffMsgCtl.msgId=${FTZ210304Form.ftzOffMsgCtl.msgId }" />
		</div>
	</div>
</div>
<div class="row" style="margin-top: 40px;">
	<div class=" navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="add" name="btn" class="btn btn-primary"><spring:message code="ftz.label.ADD_DTL"/></button>
		<button id="upt" name="btn" class="btn btn-primary"><spring:message code="ftz.label.UPT_DTL"/></button>
		<button id="del" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DEL_DTL"/></button>
		<button id="dtl" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DTL_DTL"/></button>
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>