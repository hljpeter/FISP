<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/SM_Prm_Qry/Qry";
		form.submit();
	}
	//detail button
	function detailSearch(group,code) {
		var tmp_group = encrytorInf(group);
		var tmp_code = encrytorInf(code);
		window.showModalDialog('${pageContext.request.contextPath}/SM_Prm_Dtl/Init?sysParam.paramGroup='+tmp_group+'&sysParam.paramCode='+tmp_code, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//update button
	function update(group,code) {
		var tmp_group = encrytorInf(group);
		var tmp_code = encrytorInf(code);
		window.showModalDialog('${pageContext.request.contextPath}/SM_Prm_Upd/Init?sysParam.paramGroup='+tmp_group+'&sysParam.paramCode='+tmp_code, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		search();
	}
	//add button
	function add(){
		window.showModalDialog('${pageContext.request.contextPath}/SM_Prm_Add/Init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		search();
	}
	//del button
	function del(group,code){
		document.getElementById("paramGroup1").value = group;
		document.getElementById("paramCode1").value = code;
		var form = document.getElementById("SM_Prm_QryForm");
		form.action = "${pageContext.request.contextPath}/SM_Prm_Qry/Del";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
			//search();
		}else{
			return false;
		}
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="SM_Prm_QryForm">
			<form:form commandName="SM_Prm_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.sysParam.query"/></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="SM_Prm_QryForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="fisp.label.sysParam.group"/></td>
				<td>
					<form:input path="paramGroup" type="text" class=".input-small"/>
				</td>
				<td class="label_td"><spring:message code="fisp.label.sysParam.code"/></td>
				<td>
					<form:input path="paramCode" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.label.sysParam.name"/></td>
				<td colspan="2">
					<form:input path="paramName" type="text" class="span6"/>
				</td>
				<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="add()"><spring:message code="button.lable.Add"/></button>
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
        	<th class="tbl_page_th" width="70px"><spring:message code="fisp.label.sysParam.group"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="fisp.label.sysParam.code"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="fisp.label.sysParam.val"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="fisp.label.sysParam.name"/></th>
            <th class="tbl_page_th" width="140px"><spring:message code="fisp.label.sysParam.desc"/></th>
          	<th class="tbl_page_th" width="140px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="SM_Prm_QryForm" action="${pageContext.request.contextPath}" modelAttribute="SM_Prm_QryForm">
      	<input id="paramGroup1" type="hidden" name="sysParam.paramGroup"/>
      	<input id="paramCode1" type="hidden" name="sysParam.paramCode"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="70px">${dto.paramGroup}</td>
            <td class="tbl_page_td_left vtip" width="70px">${dto.paramCode }</td>
            <td class="tbl_page_td_left vtip" width="70px">${dto.paramVal }</td>
            <td class="tbl_page_td_left vtip" width="70px">${dto.paramName}</td>
			<td class="tbl_page_td_left vtip" width="140px">${dto.paramDesc}</td>
            <td class="tbl_page_td_left" width="140px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.paramGroup)}','${f:h(dto.paramCode)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="updateBtn" class="btn btn-small" onclick="update('${f:h(dto.paramGroup)}','${f:h(dto.paramCode)}')"value="<spring:message code="button.lable.Modify"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(dto.paramGroup)}','${f:h(dto.paramCode)}')"value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination page="${page}" action="/Draft_Sheet/Qry" query="paramGroup=${SM_Prm_QryForm.paramGroup}&paramCode=${SM_Prm_QryForm.paramCode}&paramName=${SM_Prm_QryForm.paramName}" />
	</div>
</div>
