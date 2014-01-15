<script type="text/javascript">
	if (typeof(window.opener) == 'undefined') {
		window.opener = window.dialogArguments;
	}
	//input button
	function tableSelect(tableId, tableName, tableDesc) {
		var json = {"param1":tableId,"param2":tableName,"param3":tableDesc};
		window.returnValue = JSON.stringify(json);

		if (window.opener && window.opener != null)

			window.opener.ReturnValue =JSON.stringify(json);

		window.close();
	};
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="tableNameSearchForm">
			<form:form commandName="tableNameSearchForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.common.tableNameSearch"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/search/tablename/search" method="post" modelAttribute="tableNameSearchForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
				<td><form:input id="tableName" path="dpTable.tableName"/></td>
				
				<td class="label_td"><spring:message code="fisp.label.common.tableDesc"/></td>
				<td><form:input id="tableDesc" path="dpTable.tableDesc"/></td>
				<td align="right">
					<button type="submit" class="btn btn-primary">
						<spring:message code="button.label.Search" />
					</button>
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
        	<th class="tbl_page_th" width="20px"><spring:message code="fisp.label.common.no"/></th>
          	<th class="tbl_page_th" width="120px"><spring:message code="fisp.label.common.tableName"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.tableDesc"/></th>
          	<th class="tbl_page_th" width="130px"><spring:message code="fisp.label.common.comments"/></th>
          	<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation" /></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
		<table class="table table-striped table-bordered table-condensed tbl_page">
		<tbody>
		<form:form id="tableNameSearchForm" action="${pageContext.request.contextPath}" modelAttribute="tableNameSearchForm">
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="20px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="120px">${dto.tableName}</td>
		            <td class="tbl_page_td_left vtip" width="100px">${dto.tableDesc}</td>
		            <td class="tbl_page_td_left vtip" width="130px">${dto.comments}</td>
		             <td align="center" width="40px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small"
									onclick="tableSelect('${dto.tableId}','${dto.tableName}','${dto.tableDesc}')"
									value="<spring:message code="button.lable.Choose"/>">
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
<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 40px;">
	<div class="leftPage">
		<util:pagination page="${page}" query="tableName=${tableNameSearchForm.dpTable.tableName}&tableDesc=${tableNameSearchForm.dpTable.tableDesc}" />
	</div>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
