<!-- 6.3.1　代理发债（210301） / 查询 - 报文页面 -->
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

$(".tbl_page_body table tr").dblclick(function() {
	var selectedRow = eval("(" + $(this).attr("id") + ")"); 
	showDialog('${pageContext.request.contextPath}/FTZ210301/Qry/DtlTxn/Init?ftzOffTxnDtl.msgId=' 
			+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
});

//detail
$("#dtl").click(function() {
	var selectedRow = checkSelected();
	if (!selectedRow || selectedRow == "") {
		alert('<spring:message code="ftz.validate.choose.data"/>');
	} else {
		selectedRow = eval("(" + selectedRow + ")"); 
		showDialog('${pageContext.request.contextPath}/FTZ210301/Qry/DtlTxn/Init?ftzOffTxnDtl.msgId=' 
				+ $("#msgId").val() + "&ftzOffTxnDtl.seqNo=" + selectedRow.seqNo, '600', '1040');
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
		<spring:hasBindErrors name="FTZ210301Form">
			<form:form commandName="FTZ210301Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210301.qry.msg"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210301Form" class="form-horizontal">
		<form:hidden path="ftzOffMsgCtl.msgId" id="msgId" value="${FTZ210301Form.ftzOffMsgCtl.msgId }"/>
		<form:hidden path="ftzOffMsgCtl.makUserId"/>
		<form:hidden path="ftzOffMsgCtl.makDatetime"/>
		<form:hidden path="ftzOffMsgCtl.editFlag"/>
		<form:hidden path="operFlag" id="operFlag"/>
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="ftz.label.New_BRANCH_ID"/>：</td>
				<td>
					<form:select path="ftzOffMsgCtl.branchId" disabled="true">
						<option value=""></option>
						<form:options items="${SM_0002 }" />
					</form:select>
				</td>
				
				<td class="label_td"><span style="color:red;">*</span><spring:message code="ftz.label.WORK_DATE"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210301Form.ftzOffMsgCtl.workDate }" format="date" name="ftzOffMsgCtl.workDate" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.MSG_ID"/>：</td>
				<td><form:input path="ftzOffMsgCtl.msgNo" type="text" class="input-large" readonly="true"/></td>
	
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS"/>：</td>
				<td>
					<form:select path="ftzOffMsgCtl.msgStatus" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_MSG_STATUS }" />
					</form:select>
				</td>
			</tr>
			<tr><td colspan="4"><hr/></td></tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS"/>：</td>
				<td colspan="3">
					<form:select path="ftzOffMsgCtl.result" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_PROC_RESULT }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.ADDWORD"/>：</td>
				<td colspan="3">
					<form:input path="ftzOffMsgCtl.addWord" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			</tr>
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
	          	<th class="tbl_page_th" width="100px"><spring:message code="ftz.label.SUBMIT_DATE"/></th>
	          	<th class="tbl_page_th" width="100px"><spring:message code="ftz.label.ENT_ACCOUNT_NO"/></th>
	          	<th class="tbl_page_th" width="80px"><spring:message code="ftz.label.CURRENCY"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="ftz.label.ISSUE_AMOUNT"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="ftz.label.COUNTRY_CODE"/></th>
	          	<th class="tbl_page_th" width="160px"><spring:message code="ftz.label.DISITRICT_CODE"/></th>
	          	<th class="tbl_page_th" width="80px"><spring:message code="ftz.label.DTL_STATUS"/></th>
			</tr>
			</thead>
		</table>
    </div>
    <div class="tbl_page_body">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
			<form:form id="FTZ210301Form" action="${pageContext.request.contextPath}" modelAttribute="FTZ210301Form">
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr id='{seqNo:"${dto.seqNo }",makDatetime:"${dto.makDatetime }",chkDatetime:"${dto.chkDatetime }"}'>
		          	<td class="tbl_page_td_left vtip" width="20px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="100px"><t:dateTimeFormat type="label" value="${dto.submitDate}" format="date"/></td>
		            <td class="tbl_page_td_left vtip" width="100px">${dto.accountNo}</td>
		            <td class="tbl_page_td_left vtip" width="80px"><t:codeValue items="${SYS_CURRENCY }" key="${dto.currency }" type="label" /></td>
		            <td class="tbl_page_td_right vtip" width="120px"><t:moneyFormat type="label" value="${dto.amount }" format="###,###,###,###.00" dot="true"/></td>
		            <td class="tbl_page_td_left vtip" width="120px">${dto.countryCode}</td>
		            <td class="tbl_page_td_left vtip" width="160px">${dto.districtCode}</td>
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
			<util:pagination page="${page }" action="/FTZ210301/Auth/DtlMsg/Init" query="ftzOffMsgCtl.msgId=${FTZ210301Form.ftzOffMsgCtl.msgId }" />
		</div>
	</div>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="dtl" name="btn" class="btn btn-primary"><spring:message code="ftz.label.DTL_DTL"/></button>
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>