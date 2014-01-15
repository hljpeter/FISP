<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/SM_Date_Qry/Qry";
		form.submit();
	}
	//modify button
	function modify(year) {
		/*document.getElementById("modYearId").value = year;
		smDateListForm.action = "${pageContext.request.contextPath}/SM_Date_Mdf/Init";
		smDateListForm.submit();*/
		var old_url = window.location.href;		
		window.showModalDialog('${pageContext.request.contextPath}/SM_Date_Mdf/Init?modYear='+year, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	}
	//detail button
	function detailSearch(year) {
		/*document.getElementById("modYearId").value = year;		
		smDateListForm.action = "${pageContext.request.contextPath}/SM_Date_Dtl/Dtl";
		smDateListForm.submit();*/
		var old_url = window.location.href;		
		window.showModalDialog('${pageContext.request.contextPath}/SM_Date_Dtl/Dtl?modYear='+year, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	}	

	//update
	function upd(){
		var old_url = window.location.href;		
		window.showModalDialog('${pageContext.request.contextPath}/SM_Date_Upd/Init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
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
		<spring:hasBindErrors name="sm_Date_QryForm">
			<form:form commandName="sm_Date_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.HolidayMaintain"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/SM_Date_Qry/Qry" method="post" modelAttribute="sm_Date_QryForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="sm.label.MaintainScop"/> </td>
				<td>
					 <form:input path="floorYear" id="strDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({dateFmt:'yyyy'});" />&nbsp;~&nbsp;  
				 	<form:input path="upperYear" id="endDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({dateFmt:'yyyy'});" />
				</td>	
				<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="upd()"><spring:message code="button.label.charUpdate"/></button>
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
          	<th class="tbl_page_th" width="70px"><spring:message code="field.maintainyear"/></th>
          	<th class="tbl_page_th" width="65px"><spring:message code="field.holidays"/></th>
          	<th class="tbl_page_th" width="65px"><spring:message code="field.workdays"/></th>
          	
          	<th class="tbl_page_th" width="140px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="smDateListForm" action="${pageContext.request.contextPath}" modelAttribute="sm_Date_QryForm">
      	<input id="modYearId" type="hidden" name="modYear"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_center vtip" width="20px" align="center">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_center vtip" width="70px" align="center">${dto.modYear}</td>
            <td class="tbl_page_td_center vtip" width="65px" align="center"> ${dto.holidays} </td>
            <td class="tbl_page_td_center vtip" width="65px" align="center">${dto.workdays}</td>    
            
            <td class="tbl_page_td_center" width="140px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.modYear)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="addBtn" class="btn btn-small" onclick="modify('${f:h(dto.modYear)}')"value="<spring:message code="button.lable.Modify"/>">											
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
		<util:pagination page="${page}" query="floorYear=${sm_Date_QryForm.floorYear}&upperYear=${sm_Date_QryForm.floorYear}" />
	</div>
</div>
