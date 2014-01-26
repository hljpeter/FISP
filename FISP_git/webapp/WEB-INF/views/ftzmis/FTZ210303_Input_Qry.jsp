<!-- 6.3.3　应付保函/备用证（210303） / 录入查询页面 -->
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
	showDialog('${pageContext.request.contextPath}/FTZ210303/Input/Dtl/Init?ftzOffMsgCtl.msgId=' + selectedRow.msgId, '500', '1040');
});

//$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/Qry");

//delete the batch
$("#del").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else if (selectedRow.msgStatus == '03') {
		alert('<spring:message code="ftz.validate.chk.success"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
		if (confirm(msg)) {
			$("#msgId").val(selectedRow.msgId);
			$("#makDatetime").val(selectedRow.makDatetime);
			$("#chkDatetime").val(selectedRow.chkDatetime);
			$("#msgStatus").val(selectedRow.msgStatus);
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/DelMsg?page.page=" + ${page.number + 1 });
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
	} else if (selectedRow.msgStatus == '03') {
		alert('<spring:message code="ftz.validate.chk.success"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
		if (confirm(msg)) {
			$("#msgId").val(selectedRow.msgId);
			$("#makDatetime").val(selectedRow.makDatetime);
			$("#msgStatus").val(selectedRow.msgStatus);
			$("#form").attr("action", "${pageContext.request.contextPath}/FTZ210303/Input/SubmitMsg?page.page=" + ${page.number + 1 });
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
	} else if (selectedRow.msgStatus == '03') {
		alert('<spring:message code="ftz.validate.chk.success"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		var msg = $("#confirmMsg1").val() + $(this).html() + $("#confirmMsg2").val();
		$("#makDatetime").val(selectedRow.makDatetime);
		showDialogAndRefresh('${pageContext.request.contextPath}/FTZ210303/Input/UptMsg/Init?ftzOffMsgCtl.msgId=' + selectedRow.msgId, '500', '1040');
		//window.location.href = '${pageContext.request.contextPath}/FTZ210303/Input/Qry?ftzOffMsgCtlVO.branchId=${FTZ210303Form.ftzOffMsgCtlVO.branchId }&ftzOffMsgCtlVO.startDate=${FTZ210303Form.ftzOffMsgCtlVO.startDate }&ftzOffMsgCtlVO.endDate=${FTZ210303Form.ftzOffMsgCtlVO.endDate }&ftzOffMsgCtlVO.msgNo=${FTZ210303Form.ftzOffMsgCtlVO.msgNo }&ftzOffMsgCtlVO.msgStatus=${FTZ210303Form.ftzOffMsgCtlVO.msgStatus }';
	}
});

// detail
$("#dtl").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		showDialog('${pageContext.request.contextPath}/FTZ210303/Input/Dtl/Init?ftzOffMsgCtl.msgId=' + selectedRow.msgId, '500', '1040');
	}
});

// add
$("#add").click(function() {
	showDialogAndRefresh('${pageContext.request.contextPath}/FTZ210303/Input/AddMsg/Init', '500', '1040');
	//window.location.href = '${pageContext.request.contextPath}/FTZ210303/Input/Qry?ftzOffMsgCtlVO.startDate=' 
	//	+ $("#startDate").val() + '&ftzOffMsgCtlVO.endDate=' + $("#endDate").val();
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
		<spring:hasBindErrors name="FTZ210303Form">
			<form:form commandName="FTZ210303Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210303.input"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210303/Input/Qry" method="post" modelAttribute="FTZ210303Form" class="form-horizontal">
		<form:hidden path="ftzOffMsgCtl.msgId" id="msgId"/>
		<form:hidden path="ftzOffMsgCtl.msgStatus" id="msgStatus"/>
		<form:hidden path="ftzOffMsgCtl.makDatetime" id="makDatetime"/>
		<form:hidden path="ftzOffMsgCtl.chkDatetime" id="chkDatetime"/>
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="ftz.label.New_BRANCH_ID"/>：</td>
				<td>
					<form:select path="ftzOffMsgCtlVO.branchId">
						<option value=""></option>
						<form:options items="${SM_0002 }" />
					</form:select>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.WORK_DATE"/>：</td>
				<td>
					<form:input path="ftzOffMsgCtlVO.startDate" id="startDate" type="text" class="input-small" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
						onpropertychange="query()" oninput="query()" />
					~
					<form:input path="ftzOffMsgCtlVO.endDate" id="endDate" type="text" class="input-small" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
						onpropertychange="query()" oninput="query()" />
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.MSG_ID"/>：</td>
				<td><form:input path="ftzOffMsgCtlVO.msgId" type="text" class="input-large"/></td>
	
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS"/>：</td>
				<td>
					<form:select path="ftzOffMsgCtlVO.msgStatus">
						<option value=""></option>
						<form:options items="${FTZ_MSG_STATUS_INPUT }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td" colspan="4" style="text-align:right;"><input type="submit" id="qry" name="btn" class="btn btn-primary" value='<spring:message code="ftz.label.SELECT_MSG"/>'/></td>
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
	        	<th class="tbl_page_th" width="20px"><spring:message code="ftz.label.NO"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="ftz.label.WORK_DATE"/></th>
	          	<th class="tbl_page_th" width="180px"><spring:message code="ftz.label.New_BRANCH_ID"/></th>
	          	<th class="tbl_page_th" width="200px"><spring:message code="ftz.label.MSG_ID"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="ftz.label.MSG_STATUS"/></th>
			</tr>
			</thead>
		</table>
    </div>
    <div class="tbl_page_body">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
			<form:form id="FTZ210303Form" action="${pageContext.request.contextPath}" modelAttribute="FTZ210303Form">
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr id='{msgId:"${dto.msgId }",makDatetime:"${dto.makDatetime }",chkDatetime:"${dto.chkDatetime }",msgStatus:"${dto.msgStatus }"}'>
		          	<td class="tbl_page_td_left vtip" width="20px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="120px"><t:dateTimeFormat type="label" value="${dto.workDate}" format="date"/></td>
		            <td class="tbl_page_td_left vtip" width="180px"><t:codeValue items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
		            <td class="tbl_page_td_left vtip" width="200px">${dto.msgId }</td>
		            <td class="tbl_page_td_left vtip" width="120px"><t:codeValue items="${FTZ_MSG_STATUS }" key="${dto.msgStatus}" type="label" /></td>
				</tr>
	        </c:forEach>
	        </form:form>
			</tbody>
		</table>
	</div>
</div>
<!-- page and buttons -->
<div class="pagination pull-right" style="margin-top: 10px;">
	<table class="text-center">
		<tr>
			<td width="50%" align="center">
				<button id="add" name="btn" class="btn btn-primary"><spring:message code="ftz.label.ADD_MSG"/></button>
				<button id="upt" name="btn" class="btn btn-primary"><spring:message code="ftz.label.UPT_MSG"/></button>
				<button id="del" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DEL_MSG"/></button>
				<button id="sbm" name="btn" class="btn btn-primary"><spring:message code="ftz.label.FINISH_MSG"/></button>
				<button id="dtl" name="btn" class="btn btn-primary"><spring:message code="ftz.label.MSG_Dtl"/></button>
			</td>
			<td width="50%" align="right">
				<table><tr><td>
					<util:pagination page="${page }" action="/FTZ210303/Input/Qry" query="ftzOffMsgCtlVO.branchId=${FTZ210303Form.ftzOffMsgCtlVO.branchId }&ftzOffMsgCtlVO.startDate=${FTZ210303Form.ftzOffMsgCtlVO.startDate }&ftzOffMsgCtlVO.endDate=${FTZ210303Form.ftzOffMsgCtlVO.endDate }&ftzOffMsgCtlVO.msgId=${FTZ210303Form.ftzOffMsgCtlVO.msgId }&ftzOffMsgCtlVO.msgStatus=${FTZ210303Form.ftzOffMsgCtlVO.msgStatus }" />	
				</td></tr></table>
			</td>
		</tr>
	</table>
</div>
