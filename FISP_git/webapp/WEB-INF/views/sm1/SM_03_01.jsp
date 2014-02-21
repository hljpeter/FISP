<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/nsm03/01/search";
		form.submit();
	}
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/nsm03/02/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//modify button
	function modify(userid) {
		document.getElementById("userInfUserid").value = userid;
		window.showModalDialog('${pageContext.request.contextPath}/nsm03/03/search?userInf.userid='+userid, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		search();
	}
	//del button
	function del(userid) {
		document.getElementById("userInfUserid").value = userid;
		var form = document.getElementById("userInfForm");
		form.action = "${pageContext.request.contextPath}/nsm03/01/del";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	//detail button
	function detailSearch(userid) {
		document.getElementById("userInfUserid").value = userid;
		window.showModalDialog('${pageContext.request.contextPath}/nsm03/05/detailSearch_01?userInf.userid='+userid, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="userInfForm">
			<form:form commandName="userInfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.UserInfoMaintenance"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/nsm03/01/init" method="post" modelAttribute="userInfForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.sm.UserId"/></td>
				<td colspan="3">
					<form:input id="userid" path="userid" type="text" class=".input-small" maxlength="20" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.UserName"/></td>
				<td>
					<form:input path="username" type="text" class="input-xxlarge"/>
				</td>
				<td colspan="2">
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="add()"><spring:message code="button.lable.Add"/></button>
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
        	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.UserId"/></th>
          	<th class="tbl_page_th" width="150px"><spring:message code="index.label.sm.UserName"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.CreateOrgName"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.LoginOrg"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="index.label.sm.OperationStatus"/></th>
          	<th class="tbl_page_th" width="140px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="userInfForm" action="${pageContext.request.contextPath}" modelAttribute="userInfForm">
      	<input id="userInfUserid" type="hidden" name="userInf.userid"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left" width="100px">${dto.userid}</td>
            <td class="tbl_page_td_left" width="150px"> ${dto.username} </td>
            <td class="tbl_page_td_left" width="100px">
            	<t:codeValue items="${BM_1001 }" key="${dto.createorg}" type="label" />
            </td>
            <td class="tbl_page_td_left" width="100px">
            	<t:codeValue items="${BM_1001 }" key="${dto.loginorg}" type="label" />
            </td>
            <td class="tbl_page_td_left" width="80px">
            	<t:codeValue items="${CL_0003 }" key="${dto.optstatus}" type="label" />
            </td>
            <td class="tbl_page_td_left" width="140px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.userid)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="modifyBtn" class="btn btn-small" onclick="modify('${f:h(dto.userid)}')"value="<spring:message code="button.lable.Modify"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(dto.userid)}')" value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination action="/nsm03/01/search" page="${page}" query="userid=${userInfForm.userid}&username=${userInfForm.username}" />
	</div>
</div>
