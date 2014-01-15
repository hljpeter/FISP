
<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/de01/01/search";
		form.submit();
	}
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/de01/02/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//modify button
	function modify(tId) {
		var old_url = window.location.href;
		document.getElementById("dtTableCfgtId").value = tId;
		window.showModalDialog('${pageContext.request.contextPath}/de01/03/search?dtTableCfg.tId='+tId, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	}
	//del button
	function del(tId) {
		document.getElementById("dtTableCfgtId").value = tId;
		var form = document.getElementById("dtTableCfgForm");
		form.action = "${pageContext.request.contextPath}/de01/01/del";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
		
	}
	//detail button
	function detailSearch01(tId) {
		document.getElementById("dtTableCfgtId").value = tId;
		window.showModalDialog('${pageContext.request.contextPath}/de01/04/detailSearch_01?dtTableCfg.tId='+tId, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
	function detailSearch02(tId) {
		document.getElementById("dtTableCfgtId").value = tId;
		window.showModalDialog('${pageContext.request.contextPath}/de02/01/init?dtFieldCfg.tId='+tId, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
	
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dtTableCfgForm">
			<form:form commandName="dtTableCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">任务管理 / 映射配置 / <spring:message code="shuju.yinshen"/>
</div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/de01/01/init" method="post" modelAttribute="dtTableCfgForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    	
	    	   <td class="label_td"><spring:message code="de.label.branchId"/></td>
				<td>
					<form:input id="branchId" path="dtTableCfg.branchId" type="text" class=".input-small" maxlength="20" />
				</td>
				<td class="label_td"><spring:message code="de.label.srcTable"/></td>
				<td>
					<form:input id="srcTable" path="dtTableCfg.srcTable" type="text" class=".input-small" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.destTable"/></td>
				<td>
					<form:input id="destTable" path="dtTableCfg.destTable" type="text" class=".input-small" maxlength="20" />
				</td>
				<td colspan="2">
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
        	<th class="tbl_page_th" width="50px">No.</th>
          	<th class="tbl_page_th" width="70px"><spring:message code="de.label.branchId"/></th>
          	<th class="tbl_page_th" width="130px"><spring:message code="de.label.destTable"/></th>
          	<th class="tbl_page_th" width="130px"><spring:message code="de.label.srcTable"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="de.label.procType"/></th>
          	<th class="tbl_page_th" width="240px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body">
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="dtTableCfgForm" action="${pageContext.request.contextPath}" modelAttribute="dtTableCfgForm">
      	<input id="dtTableCfgtId" type="hidden" name="dtTableCfg.tId"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left" width="50px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="70px"> ${dto.branchId} </td>
            <td class="tbl_page_td_left vtip" width="130px">${dto.destTable}</td>
            <td class="tbl_page_td_left vtip" width="130px">${dto.srcTable}</td>
            <td class="tbl_page_td_left" width="70px">
            	<font size="2px" class="vtip">
            	<t:codeValue items="${DE_0001 }" key="${dto.procType}" type="label" /></font>
            </td>
            
            <td class="tbl_page_td_left" width="240px">
	           	<div align="left" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch01('${f:h(dto.tId)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="addBtn" class="btn btn-small" onclick="modify('${f:h(dto.tId)}')"value="<spring:message code="button.lable.Modify"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(dto.tId)}')" value="<spring:message code="button.lable.Del"/>"> 
                    <c:choose>
					   <c:when test="${dto.procType eq 3}">
					     <input type="button" id="detailBtn1" class="btn btn-small" onclick="detailSearch02('${dto.tId}')"value="<spring:message code="button.lable.DtFieldCfgSearch"/>"/>
					   </c:when >
					   <c:otherwise>
					    <input type="button" id="detailBtn1" class="btn btn-small" onclick="detailSearch02('${dto.tId}')"value="<spring:message code="button.lable.DtFieldCfgSearch"/>" disabled="disabled"/>
					   </c:otherwise>
					</c:choose>
					
					
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
		<util:pagination page="${page}" query="destTable=${dtTableCfgForm.destTable}&srcTable=${dtTableCfgForm.srcTable}" />
	</div>
</div>
