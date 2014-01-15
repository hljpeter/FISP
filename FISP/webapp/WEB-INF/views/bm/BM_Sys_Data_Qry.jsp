<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BM_Sys_Data_Qry/Qry";
		form.submit();
	}
	//detail button
	function detailSearch(id) {
		document.getElementById("sysDataId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/BM_Sys_Data_Qry/Init?sysChgSysLog.sysDataId='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="BM_Sys_DataForm">
			<form:form commandName="BM_Sys_DataForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="Title_BM_Sys_Data_Qry"/></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="BM_Sys_DataForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="label_BRANCH_NAME"/></td>
				<td>
					<form:select path="branchId" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_1001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="label_FUNC_NAME"/></td>
				<td colspan="2">
					<form:select path="funcId" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_1002}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="label_OPER_TYPE"/></td>
				<td>
					<form:select path="operType" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_1003}" />
					</form:select>
				</td>
	    		<td class="label_td"><spring:message code="label_USER_ID"/></td>
				<td>
					<form:input path="userId" type="text" class=".input-small"/>
				<td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="label_OPER_DATE"/></td>
				<td>
					 <form:input path="minOperDate" id="minOperDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />&nbsp;~&nbsp;  
				 	 <form:input path="maxOperDate" id="maxOperDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
				</td>
				<td class="label_td"><spring:message code="sm.label.optime"/></td>
				<td>
					 <form:input path="minOperTime" id="minOperTime" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm:ss'});" />&nbsp;~&nbsp;  
				 	 <form:input path="maxOperTime" id="maxOperTime" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm:ss'});" />
				</td>
				<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
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
        	<th class="tbl_page_th" width="20px">No.</th>
        	<th class="tbl_page_th" width="100px"><spring:message code="label_BRANCH_NAME"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="label_FUNC_NAME"/></th>
          	<th class="tbl_page_th" width="90px"><spring:message code="label_USER_ID"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="label_USER_NAME_CN"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="label_OPER_DATE"/></th>
            <th class="tbl_page_th" width="60px"><spring:message code="label_OPER_TIME"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="label_OPER_TYPE"/></th>
          	<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="loanBalanceListForm" action="${pageContext.request.contextPath}" modelAttribute="loanBalanceForm">
      	<input id="sysDataId" type="hidden" name="sysChgSysLog.sysDataId"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="100px">
           		<font size="2px" class="vtip">
           			<t:codeValue items="${BM_1001 }" key="${dto.branchId}" type="label" />
           		</font></td>
            <td class="tbl_page_td_left vtip" width="200px">
            	<font size="2px" class="vtip">
           			<t:codeValue items="${BM_1002 }" key="${dto.funcId }" type="label" />
           		</font></td>
            <td class="tbl_page_td_left vtip" width="90px">${dto.userId }</td>
            <td class="tbl_page_td_left vtip" width="100px">${dto.userNameCn}</td>
           <td class="tbl_page_td_left vtip" width="80px">
            <t:dateTimeFormat type="label" value="${dto.operDate}" format="date" />
			</td>
			<td class="tbl_page_td_left vtip" width="60px">
            <t:dateTimeFormat type="label" value="${dto.operTime}" format="shortTime" />
			</td>
			<td class="tbl_page_td_left vtip" width="50px">
           		<font size="2px" class="vtip">
           			<t:codeValue items="${BM_1003 }" key="${dto.operType}" type="label" />
           		</font></td>
            <td class="tbl_page_td_left" width="40px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.sysDataId)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
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
		<util:pagination page="${page}" query="branchId=${BM_Sys_DataForm.branchId}
		&funcId=${BM_Sys_DataForm.funcId}&userId=${BM_Sys_DataForm.userId}&operType=${BM_Sys_DataForm.operType}&minOperDate=${BM_Sys_DataForm.minOperDate}
		&maxOperDate=${BM_Sys_DataForm.maxOperDate}&minOperTime=${BM_Sys_DataForm.minOperTime}&maxOperTime=${BM_Sys_DataForm.maxOperTime}&}" />
	</div>
</div>
