<script type="text/javascript">
var oldUrl = window.location.href;
if (oldUrl.indexOf("DP_File_Qry/Qry") < 0) {
	window.location.href = "${pageContext.request.contextPath}/DP_File_Qry/Qry";
}
//add button
var add = function() {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_File_Add/Init', '600', '1024');
	//window.showModalDialog('${pageContext.request.contextPath}/DP_File_Add/Init', window, 'dialogHeight: 300px; dialogWidth: 1000px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	
};	
//search detail information
var dtl = function(id) {
	showDialog('${pageContext.request.contextPath}/DP_File_Dtl/Init?dpFile.fileId=' + id, '500', '1040');
};
//update record
var upt = function(id) {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_File_Mdf/Init?dpFile.fileId=' + id, '600', '1024');
};
//delete record
var del = function(id, updateTime, updateUser) {
	//var oldUrl = window.location.href;

	$("#fileId").val(id);
	$("#updateTime").val(updateTime);
	$("#updateUser").val(updateUser);

	$("#dpFileQryForm").attr("action", "${pageContext.request.contextPath}/DP_File_Qry/Del");
	var msg=$("#confirmMsg1").val()+$("#delete").val()+$("#confirmMsg2").val();
	if (confirm(msg)){
		$("#dpFileQryForm").submit();
		//window.location.href = oldUrl;
		return;
	}else{
		return false;
	}
};
//configuration
var cfg = function(id) {
	showDialogAndRefresh('${pageContext.request.contextPath}/DP_File_Dtl_Cfg/Init?dpFile.fileId=' + id, '500', '1040');
};
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dpFileQryForm">
			<form:form commandName="dpFileQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.file.dpFielQry"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/DP_File_Qry/Qry" method="post" modelAttribute="dpFileQryForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="fisp.label.common.fileName"/></td>
				<td><form:input id="fileName" path="dpFile.fileName"/></td>
				
				<td class="label_td"><spring:message code="fisp.label.common.fileType"/></td>
				<td>
					<form:select path="dpFile.fileType">
						<option value=""></option>
						<form:options items="${DP_FILE_FILETYPE}" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="fisp.label.common.fileDesc"/></td>
				<td><form:input id="fileDesc" path="dpFile.comments"/></td>

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
        	<th class="tbl_page_th" width="15px"><spring:message code="fisp.label.common.no"/></th>
          	<th class="tbl_page_th" width="150px"><spring:message code="fisp.label.common.fileName"/></th>
          	<th class="tbl_page_th" width="180px"><spring:message code="fisp.label.common.fileDesc"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.fileType"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.ioFlag"/></th>
          	<th class="tbl_page_th" width="120px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
		<table class="table table-striped table-bordered table-condensed tbl_page">
		<tbody>
		<form:form id="dpFileQryForm" action="${pageContext.request.contextPath}" modelAttribute="dpFileQryForm">
	        <form:hidden id="fileId" path="dpFile.fileId"/>
	        <form:hidden id="updateUser" path="dpFile.updateUser"/>
	        <form:hidden id="updateTime" path="dpFile.updateTime"/>
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="15px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="150px">${dto.fileName}</td>
		            <td class="tbl_page_td_left vtip" width="180px">${dto.comments}</td>
		            <td class="tbl_page_td_left vtip" width="80px">
		            	<t:codeValue items="${DP_FILE_FILETYPE }" key="${dto.fileType}" type="label" />
		            </td>
		            <td class="tbl_page_td_left vtip" width="80px">
		            	<t:codeValue items="${DP_FILE_IOFLAG }" key="${dto.ioFlag}" type="label" />
		            </td>
		            <td class="tbl_page_td_left" width="120px">
			           	<div align="center" style="height: 25px">
							<input type="button" id="detail" class="btn btn-small" onclick="dtl('${f:h(dto.fileId)}')" value="<spring:message code="button.lable.DeatilSearch"/>">
							<input type="button" id="modify" class="btn btn-small" onclick="upt('${dto.fileId }')" value="<spring:message code="button.lable.Modify"/>">
							<input type="button" id="delete" class="btn btn-small" onclick="del('${dto.fileId }', '${dto.updateTime }', '${dto.updateUser }');" value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination page="${page}" query="fileName=${dpFileQryForm.dpFile.fileName}&comments=${dpFileQryForm.dpFile.comments}&fileType=${dpFileQryForm.dpFile.fileType }" />
	</div>
</div>
