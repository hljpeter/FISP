<script type="text/javascript">
//add button
var add = function() {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_Imp_Add/Init', '500', '1000');
};	
//search detail information
var dtl = function(id) {
	showDialog('${pageContext.request.contextPath}/DP_Imp_Dtl/Init?dpImpCfg.impId=' + id, '500', '1180');
};
//update record
var upt = function(id) {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_Imp_Upt/Init?dpImpCfg.impId=' + id, '500', '1040');
};
//delete record
var del = function(id, updateTime, updateUser) {
	oldUrl = window.location.href;
	$("#impId").val(id);
	$("#updateTime").val(updateTime);
	$("#updateUser").val(updateUser);

	$("#dpImpQryForm").attr("action", "${pageContext.request.contextPath}/DP_Imp_Qry/Del");
	var msg=$("#confirmMsg1").val()+$("#delete").val()+$("#confirmMsg2").val();
	if (confirm(msg)){
		$("#dpImpQryForm").submit();
		//window.location.href = oldUrl;
	}else{
		return false;
	}
};
//config record
var cfg = function(id) {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_Imp_Cfg/Init?dpImpCfg.impId=' + id, '570', '1280');
};
$(function() {
	$("#filenamesearch").click(function() {showSelFile('1', [{"fileName":"param2"}]); });	
	$("#tablenamesearch").click(function() {showSelTable( [{"tableName":"param2"}] ); });
	var tmsg = "<spring:message code='fisp.notice.common.vague.igncase'/>";
	//$("#fileName").attr("title", tmsg);
	//$("#tableName").attr("title", tmsg);
});
function queryOrg() {
	showSelOrg([ {
		"query_branchId" : "param1"
	} ]);
};
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dpImpQryForm">
			<form:form commandName="dpImpQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.dp.imp.dpImpQry"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/DP_Imp_Qry/Qry" method="post" modelAttribute="dpImpQryForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="fisp.label.common.branchId"/></td>
				<td>
					<form:input id="query_branchId" path="dpImpCfg.branchId" type="text" class=".input-small" maxLength="20" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryOrg()" value="<spring:message code="button.label.Search"/>">
				</td>
				
				<td class="label_td"><spring:message code="fisp.label.common.fileName"/></td>
				<td>
					<form:input id="fileName" path="dpImpCfg.fileName" maxLength="128"/>					
					<button type="button" class="btn btn-small" id="filenamesearch"><spring:message code="button.label.Search"/></button>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
				<td>
					<form:input id="tableName" path="dpImpCfg.tableName" maxLength="128"/>
					<button type="button" class="btn btn-small" id="tablenamesearch"><spring:message code="button.label.Search"/></button>
				</td>
			
				<td colspan="2">
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
          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.branchName"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="fisp.label.common.batchNo"/></th>
          	<th class="tbl_page_th" width="15px"><spring:message code="fisp.label.common.seqNo"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.fileName"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.tableName"/></th>
          	<th class="tbl_page_th" width="130px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
		<table class="table table-striped table-bordered table-condensed tbl_page">
		<tbody>
		<form:form id="dpImpQryForm" action="${pageContext.request.contextPath}" modelAttribute="dpImpQryForm">
	        <form:hidden path="dpImpCfg.impId" id="impId"/>
	        <form:hidden path="dpImpCfg.updateTime" id="updateTime"/>
	        <form:hidden path="dpImpCfg.updateUser" id="updateUser"/>
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="10px">${(page.number * page.size) + (i.index + 1)}</td>
		            <td class="tbl_page_td_left vtip" width="80px">
						<t:codeValue items="${BM_1001 }" key="${dto.branchId}" type="label" />
					</td>
				  	<td class="tbl_page_td_left vtip" width="50px">${dto.batchNo}</td>
		            <td class="tbl_page_td_left vtip" width="15px">${dto.seqNo}</td>
		            <td class="tbl_page_td_left vtip" width="100px">${dto.fileName}</td>
		            <td class="tbl_page_td_left vtip" width="100px">${dto.tableName}</td>
		            <td class="tbl_page_td_left" width="130px">
			           	<div align="center" style="height: 25px">
							<input type="button" id="detail" class="btn btn-small" onclick="dtl('${f:h(dto.impId)}')" value="<spring:message code="dp.title.detail"/>">
							<input type="button" id="modify" class="btn btn-small" onclick="upt('${dto.impId }')" value="<spring:message code="button.lable.Modify"/>">
							<input type="button" id="delete" class="btn btn-small" onclick="del('${dto.impId }', '${dto.updateTime }', '${dto.updateUser }');" value="<spring:message code="button.lable.Del"/>">
		               		<input type="button" id="config" class="btn btn-small" onclick="cfg('${dto.impId }')" value="<spring:message code="fisp.button.label.imp.cfgdtl"/>">
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
		<util:pagination page="${page}" query="branchId=${dpImpQryForm.dpImpCfg.branchId}&fileName=${dpImpQryForm.dpImpCfg.fileName}&tableName=${dpImpQryForm.dpImpCfg.tableName}" />
	</div>
</div>
