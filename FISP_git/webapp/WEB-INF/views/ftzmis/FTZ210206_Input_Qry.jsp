<!-- 6.3.3　（210206） / 录入查询页面 -->
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

//$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210206/AddQry");

//delete the batch
$("#del").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + checkSelected() + ")"); 
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
		if (confirm(msg)) {
			if("03" == selectedRow.msgStatus){
			       alert("批量审核已完成");
			       return false;
				}
			$("#msgId").val(selectedRow.msgId);
			$("#makDatetime").val(selectedRow.makDatetime);
			$("#msgStatus").val(selectedRow.msgStatus);
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210206/Input/DelMsg");
			$("#form").submit();
		} else {
			return false;
		}
	}
});

// submit the batch
$("#sbm").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + checkSelected() + ")"); 
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
		if (confirm(msg)) {
			$("#msgId").val(selectedRow.msgId);
			$("#makDatetime").val(selectedRow.makDatetime);
			$("#msgStatus").val(selectedRow.msgStatus);
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210206/Input/SubmitMsg");
			$("#form").submit();
		} else {
			return false;
		}
	}
});

// update
$("#upt").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} 
	else {
		selectedRow = eval("(" + checkSelected() + ")"); 
		if("03" == selectedRow.msgStatus){
	       alert("批量审核已完成");
	       return;
		}
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
			//$("#makDatetime").val(selectedRow.makDatetime);
			showDialog('${pageContext.request.contextPath}/FTZ210206/Input/UptMsg/Init?ftzInMsgCtl.msgId=' + selectedRow.msgId, '500', '1040');
			queryFTZ210206();
			//window.location.href = '${pageContext.request.contextPath}/FTZ210206/Input/Qry?ftzInMsgCtlVO.branchId=${FTZ210206Form.ftzInMsgCtlVO.branchId }&ftzInMsgCtlVO.startDate=${FTZ210206Form.ftzInMsgCtlVO.startDate }&ftzInMsgCtlVO.endDate=${FTZ210206Form.ftzInMsgCtlVO.endDate }&ftzInMsgCtlVO.msgNo=${FTZ210206Form.ftzInMsgCtlVO.msgNo }&ftzInMsgCtlVO.msgStatus=${FTZ210206Form.ftzInMsgCtlVO.msgStatus }';
	}
});

// detail
$("#dtl").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		
		selectedRow = eval("(" + checkSelected() + ")"); 
		showDialog('${pageContext.request.contextPath}/FTZ210206/QryDtl?ftzInMsgCtl.msgId=' + selectedRow.msgId, '500', '1040');
	}
});

//add
$("#add").click(function() {
	showDialog('${pageContext.request.contextPath}/FTZ210206/Input/AddMsg/Init', '500', '1040');
	queryFTZ210206();
	//window.location.href = '${pageContext.request.contextPath}/FTZ210206/Input/Qry?ftzInMsgCtlVO.startDate=' 
	//	+ $("#startDate").val() + '&ftzInMsgCtlVO.endDate=' + $("#endDate").val();
});

$("#pageTable")
.find("tr")
.bind(
		'dblclick',
		function() {
			var selected_msgId = $(this).find("td:eq(3)")
					.text();
			
			showDialog('${pageContext.request.contextPath}/FTZ210206/QryDtl?ftzInMsgCtl.msgId=' + selected_msgId, '500', '1040');

		});



});

function queryFTZ210206() {
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210206/AddQry";
	form.submit();
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
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210206.input"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210206/AddQry" method="post" modelAttribute="FTZ210206Form" class="form-horizontal">
		<form:hidden path="ftzInMsgCtl.msgId" id="msgId"/>
		<form:hidden path="ftzInMsgCtl.msgStatus" id="msgStatus"/>
		<form:hidden path="ftzInMsgCtl.makDatetime" id="makDatetime"/>
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="chkDatetime"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.New_BRANCH_ID" />：</td>
				<td><form:select path="ftzInMsgCtlVO.branchId">
						<option value=""></option>
						<form:options items="${SM_0002 }" />
					</form:select></td>
				<td class="label_td"><spring:message
						code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input path="ftzInMsgCtlVO.startDate" id="startDate" type="text" class="input-small" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
						onpropertychange="query()" oninput="query()" />
					~
					<form:input path="ftzInMsgCtlVO.endDate" id="endDate" type="text" class="input-small" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
						onpropertychange="query()" oninput="query()" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input path="ftzInMsgCtlVO.msgId" type="text" class="input-large"/></td>
			<td class="label_td"><spring:message
						code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input  path="ftzInMsgCtlVO.accountNo" type="text"
						class=".input-large" /></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtlVO.msgStatus">
						<option value=""></option>
						<form:options items="${FTZ_MSG_STATUS_INPUT }" />
					</form:select></td>
				<td style="text-align: right;" colspan="2">
					<button type="submit" class="btn btn-primary">
						<spring:message code="ftz.label.SELECT_MSG" />
					</button>
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
<p class="text-info" align="center"><spring:message code="ftz.label.MSG_List"/></p>
<div class="row">
	<div class="tbl_page_head">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
			<tr>
	        	<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.SUBMIT_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message
							code="ftz.label.New_BRANCH_ID" /></th>
					<th style="vertical-align: middle; text-align: center" width="65px"><spring:message
							code="ftz.label.MSG_ID" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message code="ftz.label.ACCOUNT_NO" /></th>
					<th style="vertical-align: middle; text-align: center" width="65px"><spring:message
							code="ftz.label.Currency" /></th>
					<th style="vertical-align: middle; text-align: center" width="65px"><spring:message
					code="ftz.label.Balance" /></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message
							code="ftz.label.MSG_STATUS" /></th>
			</tr>
			</thead>
		</table>
    </div>
    <div class="tbl_page_body">
		<table id="pageTable" class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
			<form:form id="FTZ210206Form" action="${pageContext.request.contextPath}" modelAttribute="FTZ210206Form">
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr id='{msgId:"${dto.msgId }",makDatetime:"${dto.makDatetime }",chkDatetime:"${dto.chkDatetime }",msgStatus:"${dto.msgStatus }"}'>
		          	<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: center; width: 40px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 60px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 65px;">${dto.msgId}</td>
						<td class="vtip" style="text-align: left; width: 100px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: left; width: 65px;">${dto.currency}</td>
						<td class="vtip" style="text-align: right; width: 65px;"><t:moneyFormat
								type="label" value="${dto.balance}" /></td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto.msgStatus}" type="label" /></td>
						<td style="display: none;">${dto.msgStatus}</td>
				</tr>
	        </c:forEach>
	        </form:form>
			</tbody>
		</table>
	</div>
</div>
<!-- page and buttons -->
<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 0px;">
	<div class="leftPage">
		<util:pagination page="${page }" query="ftzInMsgCtlVO.branchId=${FTZ210206Form.ftzInMsgCtlVO.branchId }&ftzInMsgCtlVO.startDate=${FTZ210206Form.ftzInMsgCtlVO.startDate }&ftzInMsgCtlVO.endDate=${FTZ210206Form.ftzInMsgCtlVO.endDate }&ftzInMsgCtlVO.msgId=${FTZ210206Form.ftzInMsgCtlVO.msgId }&ftzInMsgCtlVO.msgStatus=${FTZ210206Form.ftzInMsgCtlVO.msgStatus }&ftzInMsgCtlVO.accountNo=${FTZ210206Form.ftzInMsgCtlVO.accountNo }" />
	</div>
</div>
<div class="row">
	<div class="navbar text-right" id="footer" style="text-align:center; margin-top: 50px; margin-bottom:10px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="add" name="btn" class="btn btn-primary"><spring:message code="ftz.label.ADD_MSG"/></button>
		<button id="upt" name="btn" class="btn btn-primary"><spring:message code="ftz.label.UPT_MSG"/></button>
		<button id="del" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DEL_MSG"/></button>
		<button id="sbm" name="btn" class="btn btn-primary"><spring:message code="ftz.label.SUBMIT_MSG"/></button>
		<button id="dtl" name="btn" class="btn btn-primary"><spring:message code="ftz.label.MSG_Dtl"/></button>
	</div>
</div>
