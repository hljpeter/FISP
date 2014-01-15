<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/sm02/04/search";
		form.submit();
	}
	//detail button
	function detailSearch(id) {
		var old_url = window.location.href;
		document.getElementById("roleInfTmpId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/sm02/05/detailSearch_04?roleInfTmp.id='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
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
		<spring:hasBindErrors name="roleInfTmpForm">
			<form:form commandName="roleInfTmpForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.RoleInfoAuthorized"/>
</div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/sm02/04/init" method="post" modelAttribute="roleInfTmpForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleId"/>
				</td>
				<td>
					<form:input path="roleInfTmp.roleid" type="text" class=".input-small" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.OperationType"/>
				</td>
				<td>
					<form:select path="opttype" >
	    				<form:option value=""></form:option>
						<form:options items="${CL_0002}" />
					</form:select>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleName"/>
				</td>
				<td colspan="4">
					<form:input path="roleInfTmp.rolename" type="text" class="input-xxlarge"/>
				</td>
	    		<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/>
						</button>
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
        	<th class="tbl_page_th" width="70px"><spring:message code="index.label.sm.OperationType"/>
			</th>
          	<th class="tbl_page_th" width="80px"><spring:message code="index.label.sm.RoleId"/>
          	</th>
          	<th class="tbl_page_th" width="120px"><spring:message code="index.label.sm.RoleName"/>
          	</th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.InfrUseFlag"/>
          	</th>
          	<th class="tbl_page_th" width="85px"><spring:message code="index.label.sm.CreateOrgName"/>
          	</th>
          	<th class="tbl_page_th" width="70px"><spring:message code="index.label.sm.Creater"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
		<form:form id="roleInfTmpForm" action="${pageContext.request.contextPath}/" modelAttribute="roleInfTmpForm">
			<input id="roleInfTmpId" type="hidden" name="roleInfTmp.id" />
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
	          <tr> 
	          	<td class="tbl_page_td_left" width="20px">${(page.number*page.size)+(i.index+1)}</td>
	          	<td class="tbl_page_td_left" width="70px">
	          		<t:codeValue items="${CL_0002 }" key="${dto.opttype}" type="label" />
	          	</td>
	            <td class="tbl_page_td_left vtip" width="80px">${dto.roleid}</td>
	            <td class="tbl_page_td_left vtip" width="120px"> ${dto.rolename} </td>
	            <td class="tbl_page_td_left" width="100px">
	            	<t:codeValue items="${CL_0010 }" key="${dto.infruseflag}" type="label" />
	            </td>
	            <td class="tbl_page_td_left vtip" width="85px">
	            <t:codeValue items="${SM_0002 }" key="${dto.createorg}" type="label" />
	            </td>
	            <td class="tbl_page_td_left vtip" width="70px">${dto.creater}</td>
	            <td class="tbl_page_td_left" width="50px">
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
		<util:pagination page="${page}" query="roleInfTmp.roleid=${roleInfTmpForm.roleInfTmp.roleid}&opttype=${roleInfTmpForm.opttype}&roleInfTmp.rolename=${roleInfTmpForm.roleInfTmp.rolename}" />
	</div>
</div>
