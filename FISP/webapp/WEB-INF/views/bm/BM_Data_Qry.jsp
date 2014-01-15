
<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BM_Data_Qry/Qry";
		form.submit();
	}
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/BM_Data_Add/Init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//modify button
	function modify(groupId,metaName,metaLan) {
		document.getElementById("groupCodeid").value = groupId;
		document.getElementById("metaValid").value = metaName;
		document.getElementById("metaLanid").value = metaLan;	
		window.showModalDialog('${pageContext.request.contextPath}/BM_Data_Upd/Init?sysDataDict.groupCode='+groupId+'&sysDataDict.metaVal='+metaName+'&sysDataDict.metaLan='+metaLan, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		search();
	}
	//del button
	function del(groupId,metaName,metaLan) {
		document.getElementById("groupCodeid").value = groupId;
		document.getElementById("metaValid").value = metaName;
		document.getElementById("metaLanid").value = metaLan;
		var form = document.getElementById("dataDictForm");
		form.action = "${pageContext.request.contextPath}/BM_Data_Qry/Del";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
		
	}
	//detail button
	function detailSearch(groupId,metaName,metaLan) {
		document.getElementById("groupCodeid").value = groupId;
		document.getElementById("metaValid").value = metaName;
		document.getElementById("metaLanid").value = metaLan;		
		window.showModalDialog('${pageContext.request.contextPath}/BM_Data_Dtl/Init?sysDataDict.groupCode='+groupId+'&sysDataDict.metaVal='+metaName+'&sysDataDict.metaLan='+metaLan, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
	
	
	
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="bm_Data_QryForm">
			<form:form commandName="bm_Data_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.bm.DataDictMaintain"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/BM_Data_Qry/Init" method="post" modelAttribute="bm_Data_QryForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.bm.dictGroupCode"/></td>
				<td>
					<form:input path="groupCode" type="text" class=".input-small" maxlength="20" />
				</td>
				<td class="label_td"><spring:message code="index.label.bm.dictLan"/></td>
				<td>	
					<form:select path="metaLan" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_DICT}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.bm.dictGroupName"/></td>
				<td>
					<form:input path="groupName" type="text" class=".input-small" maxlength="60" />
				</td>
				<td class="label_td"></td>
				<td>					
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.bm.dictMetaName"/></td>
				<td>
					<form:input path="metaName" type="text" class=".input-small" maxlength="60" />
				</td>
				<td colspan="2">
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
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.bm.metaGropCode"/></th>
          	<th class="tbl_page_th" width="160px"><spring:message code="index.label.bm.metaGropName"/></th>
            <th class="tbl_page_th" width="90px"><spring:message code="index.label.bm.metaVal"/></th>    
          	<th class="tbl_page_th" width="120px"><spring:message code="index.label.bm.metaName"/></th>     
          	<th class="tbl_page_th" width="90px"><spring:message code="index.label.bm.metaLan"/></th>
          	<th class="tbl_page_th" width="140px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="dataDictForm" action="${pageContext.request.contextPath}" modelAttribute="bm_Data_QryForm">
      	<input id="groupCodeid" type="hidden" name="sysDataDict.groupCode"/>
      	<input id="metaValid" type="hidden" name="sysDataDict.metaVal"/>
      	<input id="metaLanid" type="hidden" name="sysDataDict.metaLan"/>
        <c:forEach var="sysDataDict" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            
            <td class="tbl_page_td_left vtip" width="100px"> ${sysDataDict.groupCode} </td>
            <td class="tbl_page_td_left vtip" width="160px">${sysDataDict.groupName}</td>
            <td class="tbl_page_td_left vtip" width="90px">${sysDataDict.metaVal}</td>
            <td class="tbl_page_td_left vtip" width="120px">${sysDataDict.metaName}</td>
             <td class="tbl_page_td_left vtip" width="90px">${sysDataDict.metaLan}</td>
            <td class="tbl_page_td_left" width="140px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(sysDataDict.groupCode)}','${f:h(sysDataDict.metaVal)}','${f:h(sysDataDict.metaLan)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="addBtn" class="btn btn-small" onclick="modify('${f:h(sysDataDict.groupCode)}','${f:h(sysDataDict.metaVal)}','${f:h(sysDataDict.metaLan)}')"value="<spring:message code="button.lable.Modify"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(sysDataDict.groupCode)}','${f:h(sysDataDict.metaVal)}','${f:h(sysDataDict.metaLan)}')" value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination action="/BM_Data_Qry/Qry" page="${page}" query="groupCode=${groupCode}&metaLan=${metaLan}&groupName=${groupName}&metaName=${metaName}" />
	</div>
</div>
