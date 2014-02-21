
<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/nsm01/07/search";
		form.submit();
	}
	//detail button
	function detailSearch(orgid) {
		document.getElementById("orgInfOrgid").value = orgid;
		window.showModalDialog('${pageContext.request.contextPath}/nsm01/05/detailSearch_01?orgInf.orgid='+orgid, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
	
	
	
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="orgInfForm">
			<form:form commandName="orgInfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.OrganizationInfoQuery"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/nsm01/01/init" method="post" modelAttribute="orgInfForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.sm.OrganizationId"/></td>
				<td colspan="3">
					<form:input path="orgid" type="text" class=".input-small" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.OrganizationName"/></td>
				<td>
					<form:input path="orgname" type="text" class="input-xxlarge"/>
				</td>
				<td colspan="2">
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="export()"><spring:message code="button.title.Export"/></button>
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
        	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.OrganizationId"/></th>
          	<th class="tbl_page_th" width="300px"><spring:message code="index.label.sm.OrganizationName"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="index.label.sm.SuperiorOrganizationName"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="index.label.sm.OperationStatus"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="orgInfForm" action="${pageContext.request.contextPath}" modelAttribute="orgInfForm">
      	<input id="orgInfOrgid" type="hidden" name="orgInf.orgid"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="100px">${dto.orgid}</td>
            <td class="tbl_page_td_left vtip" width="300px"> ${dto.orgname} </td>
            <td class="tbl_page_td_left vtip" width="200px">${dto.suprorgname}</td>
            <td class="tbl_page_td_left" width="70px">
            	<font size="2px" class="vtip">
            	<t:codeValue items="${CL_0003 }" key="${dto.optstatus}" type="label" /></font>
            </td>
            <td class="tbl_page_td_left" width="50px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.orgid)}')"value="<spring:message code="dp.lable.detail"/>">
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
		<util:pagination page="${page}" query="orgid=${orgInfForm.orgid}&orgname=${orgInfForm.orgname}" />
	</div>
</div>
