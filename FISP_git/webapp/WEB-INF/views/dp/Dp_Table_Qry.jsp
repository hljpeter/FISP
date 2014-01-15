<script type="text/javascript">
var oldUrl = window.location.href;
if (oldUrl.indexOf("DP_Table_Qry/Qry") < 0) {
	window.location.href = "${pageContext.request.contextPath}/DP_Table_Qry/Qry";
}
//add button
var add = function() {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_Table_Add/Init', '500', '1280');
};	
//search detail information
var dtl = function(id) {
	showDialog('${pageContext.request.contextPath}/DP_Table_Dtl/Init?dpTable.tableId=' + id, '500', '1040');
};
//update record
var upt = function(id) {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_Table_Mdf/Init?dpTable.tableId=' + id, '500', '1280');
};
//delete record
var del = function(id, updateTime, updateUser) {
	//oldUrl = window.location.href;
	$("#tableId").val(id);
	$("#updateTime").val(updateTime);
	$("#updateUser").val(updateUser);

	$("#dpTableQryForm").attr("action", "${pageContext.request.contextPath}/DP_Table_Qry/Del");
	var msg=$("#confirmMsg1").val()+$("#delete").val()+$("#confirmMsg2").val();
	if (confirm(msg)){
		$("#dpTableQryForm").submit();
		//window.location.href = oldUrl;
	}else{
		return false;
	}
};
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dpTableQryForm">
			<form:form commandName="dpTableQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.table.dpTableQry"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/DP_Table_Qry/Qry" method="post" modelAttribute="dpTableQryForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
				<td><form:input id="tableName" path="dpTable.tableName" maxLength="32"/></td>
				
				<td class="label_td"><spring:message code="fisp.label.common.tableDesc"/></td>
				<td><form:input id="fileName" path="dpTable.tableDesc" maxLength="128"/></td>
			</tr>
			<tr>
				<td colspan="4">
	    			<div align="right">
	    				<input type="submit" class="btn btn-primary" value='<spring:message code="button.label.Search"/>'>
	    				<button type="button" class="btn btn-primary" onclick="add();"><spring:message code="button.lable.Add"/></button>
	    				<button type="button" class="btn btn-primary" id="export"><spring:message code="fisp.button.label.export"/></button>
	    			</div>
	    		</td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="tbl_page_head">
	<table class="table table-striped table-bordered table-condensed tbl_page">
      <thead>
        <tr>
        	<th class="tbl_page_th" width="10px"><spring:message code="fisp.label.common.no"/></th>
          	<th class="tbl_page_th" width="150px"><spring:message code="fisp.label.common.tableName"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="fisp.label.common.tableDesc"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
		<table class="table table-striped table-bordered table-condensed tbl_page">
		<tbody>
		<form:form id="dpTableQryForm" action="${pageContext.request.contextPath}" modelAttribute="dpTableQryForm">
	        <form:hidden id="tableId" path="dpTable.tableId"/>
	        <form:hidden id="updateUser" path="dpTable.updateUser"/>
	        <form:hidden id="updateTime" path="dpTable.updateTime"/>
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="10px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="150px">${dto.tableName}</td>
		            <td class="tbl_page_td_left vtip" width="200px">${dto.tableDesc}</td>
		            <td class="tbl_page_td_left" width="100px">
			           	<div align="center" style="height: 25px">
							<input type="button" id="detail" class="btn btn-small" onclick="dtl('${f:h(dto.tableId)}')" value="<spring:message code="dp.title.detail"/>">
							<input type="button" id="modify" class="btn btn-small" onclick="upt('${dto.tableId }')" value="<spring:message code="button.lable.Modify"/>">
							<input type="button" id="delete" class="btn btn-small" onclick="del('${dto.tableId }', '${dto.updateTime }', '${dto.updateUser }');" value="<spring:message code="button.lable.Del"/>">
		               </div>
		            </td>
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
		<util:pagination page="${page}" query="tableName=${dpTableQryForm.dpTable.tableName}&tableDesc=${dpTableQryForm.dpTable.tableDesc}" />
	</div>
</div>
