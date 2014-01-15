
<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/al01/01/search";
		form.submit();
	}
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/al01/02/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//modify button
	function modify(id) {
		var old_url = window.location.href;
		document.getElementById("sysAlertRcptId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/al01/03/search?sysAlertRcpt.id='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	}
	//del button
	function del(id) {
		document.getElementById("sysAlertRcptId").value = id;
		var form = document.getElementById("sysAlertRcptForm");
		form.action = "${pageContext.request.contextPath}/al01/01/del";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
		
	}
	//detail button
	function detailSearch(id) {
		document.getElementById("sysAlertRcptId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/al01/01/detailSearch?sysAlertRcpt.id='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
	
	
	
</script>
<!-- fisp information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sysAlertRcptForm">
			<form:form commandName="sysAlertRcptForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">任务管理 / 警报配置 / <spring:message code="index.lable.SysAlertPrctSelect"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/al01/01/init" method="post" modelAttribute="sysAlertRcptForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="al.label.branchId"/></td>
				<td>
					<form:input path="branchId" type="text" class=".input-small" maxlength="20"/>
				</td>
				<td class="label_td"><spring:message code="al.label.alertType"/></td>
				<td>
					<form:select id="alertType" path="alertType" class=".input-small">
						<form:option value=""></form:option>
						<form:options items="${AL_0002}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td colspan="4">
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="add()"><spring:message code="button.lable.Add"/></button>
	    				<button type="button" class="btn btn-primary">导出</button>
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
          	<th class="tbl_page_th" width="90px"><spring:message code="al.label.branchId"/></th>
          	<th class="tbl_page_th" width="90px"><spring:message code="al.label.alertType"/></th>
          	<th class="tbl_page_th" width="90px"><spring:message code="al.label.noticeMthd"/></th>
          	<th class="tbl_page_th" width="170px"><spring:message code="al.label.rcptAddr"/></th>
          	<th class="tbl_page_th" width="110px"><spring:message code="de.label.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body">
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="sysAlertRcptForm" action="${pageContext.request.contextPath}" modelAttribute="sysAlertRcptForm">
      	<input id="sysAlertRcptId" type="hidden" name="sysAlertRcpt.id"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="90px"> ${dto.branchId} </td>
            <td class="tbl_page_td_left" width="90px">
            	<font size="2px" class="vtip">
            	<t:codeValue items="${AL_0001 }" key="${dto.alertType}" type="label" /></font>
            </td>
            <td class="tbl_page_td_left" width="90px">
            	<font size="2px" class="vtip">
            	<t:codeValue items="${AL_0002 }" key="${dto.noticeMthd}" type="label" /></font>
            </td>
            <td class="tbl_page_td_left vtip" width="170px">${dto.rcptAddr}</td>
            <td class="tbl_page_td_left" width="110px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${dto.id}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="addBtn" class="btn btn-small" onclick="modify('${dto.id}')"value="<spring:message code="button.lable.Modify"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${dto.id}')" value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination page="${page}" query="sysAlertRcpt.branchId=${sysAlertRcptForm.sysAlertRcpt.branchId}&sysAlertRcpt.projId=${sysAlertRcptForm.sysAlertRcpt.projId}&sysAlertRcpt.alertType=${sysAlertRcptForm.sysAlertRcpt.alertType }" />
	</div>
</div>
