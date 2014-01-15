<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/sm01/04/search";
		form.submit();
	}
	
	//detail button
	function detailSearch(id) {
		var old_url = window.location.href;
		document.getElementById("orgInfTmpId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/sm01/05/detailSearch_04?orgInfTmp.id='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="orgInfTmpForm">
			<form:form commandName="orgInfTmpForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>	
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.OrganizationAuthorized"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/sm01/04/init" method="post" modelAttribute="orgInfTmpForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.sm.OrganizationId"/></td>
				<td>
					<form:input path="orgInfTmp.orgid" type="text" class=".input-small" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.OperationType"/>
				<td>
					<form:select path="opttype" >
	    				<form:option value=""></form:option>
						<form:options items="${CL_0002}" />
					</form:select>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.OrganizationName"/></td>
				<td colspan="4">
					<form:input path="orgInfTmp.orgname" type="text" class="input-xxlarge"/>
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
        	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.OperationType"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.OrganizationId"/></th>
          	<th class="tbl_page_th" width="220px"><spring:message code="index.label.sm.OrganizationName"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="index.label.sm.SuperiorOrganizationName"/></th>
          	<!-- close by wy 2013-12-05
          	<th class="tbl_page_th" width="90px"><spring:message code="index.label.sm.BankId"/></th>
          	 -->
          	<th class="tbl_page_th" width="90px"><spring:message code="index.label.sm.Creater"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
		<form:form id="orgInfTmpForm" action="${pageContext.request.contextPath}/" modelAttribute="orgInfTmpForm">
			<input id="orgInfTmpId" type="hidden" name="orgInfTmp.id" />
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
	          <tr> 
	          	<td class="tbl_page_td_left" width="20px">${(page.number*page.size)+(i.index+1)}</td>
	          	<td class="tbl_page_td_left" width="100px">
	          		<t:codeValue items="${CL_0002 }" key="${dto.opttype}" type="label" />
	          	</td>
	            <td class="tbl_page_td_left vtip" width="100px">${dto.orgid}</td>
	            <td class="tbl_page_td_left vtip" width="220px"> ${dto.orgname} </td>
	            <td class="tbl_page_td_left vtip" width="200px">${dto.suprorgname}</td>
	            <!-- close by wy 2013-12-05
	            <td class="tbl_page_td_left vtip" width="90px">${dto.bankid}</td>
	            -->
	            <td class="tbl_page_td_left vtip" width="90px">${dto.creater}</td>
	            <td class="tbl_page_td_left" width="80px">
	            	<div align="center" style="height: 25px">
						<input type="button" class="btn btn-small" onclick="detailSearch('${f:h(dto.id)}')" value="<spring:message code="index.label.sm.Operation"/>">
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
		<util:pagination page="${page}" query="orgInfTmp.orgid=${orgInfTmpForm.orgInfTmp.orgid}&opttype=${orgInfTmpForm.opttype}&orgInfTmp.orgname=${orgInfTmpForm.orgInfTmp.orgname} }" />
	</div>
</div>
