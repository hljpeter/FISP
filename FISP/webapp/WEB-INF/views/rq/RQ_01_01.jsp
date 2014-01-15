<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/rq01/01/search";
		form.submit();
	}
	//detail button
	function detailSearch(reportId) {
		document.getElementById("reportDataId").value = reportId;
		window.showModalDialog('${pageContext.request.contextPath}/rq01/02/detailSearch?reportData.reportId='+reportId, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="reportDataForm">
			<form:form commandName="reportDataForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.DataManageReportStatusQuery"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/rq01/01/init" method="post" modelAttribute="reportDataForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="fisp.rq.reportMonth"/></td>
				<td>
					<form:input path="reportData.reportMonth" type="text" maxlength="6" class="Wdate .input-small"
					 onclick="WdatePicker({dateFmt:'yyyy-MM'});" />
				</td>
	    		<td class="label_td"><spring:message code="fisp.rq.reportStatus"/></td>
				<td>
					<form:select id="reportStatus" path="reportData.reportStatus" >
						<form:option value="" />
						<form:options items="${RP_0001}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.rq.feedbStatus"/></td>
				<td>
					<form:select id="feedbStatus" path="reportData.feedbStatus" >
						<form:option value="" />
						<form:options items="${RP_0002}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="fisp.rq.errorStatus"/></td>
				<td>
					<form:select id="errorStatus" path="reportData.errorStatus" >
						<form:option value="" />
						<form:options items="${RP_0003}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.rq.reportName"/></td>
				<td colspan="2">
					<form:input path="reportData.reportName" type="text" class="input-xxlarge"/>
				</td>
				<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="search()">导出</button>
	    				<button type="button" class="btn btn-primary" onclick="search()">打印</button>
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
        	<th class="tbl_page_th" width="50px"><spring:message code="fisp.rq.reportMonth"/></th>
          	<th class="tbl_page_th" width="180px"><spring:message code="fisp.rq.reportName"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="fisp.rq.sumCnt"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.rq.sumAmount"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="fisp.rq.reportStatus"/></th>
          	<th class="tbl_page_th" width="65px"><spring:message code="fisp.rq.feedbStatus"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="fisp.rq.errorCnt"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="fisp.rq.errorStatus"/></th>
          	<th class="tbl_page_th" width="60px"><spring:message code="fisp.rq.inputCnt"/></th>
          	<th class="tbl_page_th" width="60px"><spring:message code="fisp.rq.authCnt"/></th>
          	<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="reportDataListForm" action="${pageContext.request.contextPath}" modelAttribute="reportDataForm">
      	<input id="reportDataId" type="hidden" name="reportData.reportId"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="50px"><t:dateTimeFormat
						type="label" value="${dto.reportMonth}" format="month" />
			</td>
			<td class="tbl_page_td_left vtip" width="180px">${dto.reportName}</td>
			<td class="tbl_page_td_right vtip" width="50px">
				<t:moneyFormat type="label" value="${dto.sumCnt}" format="###,###,###,###"/>
			</td>
			<td class="tbl_page_td_right vtip" width="100px">
				<t:moneyFormat type="label" value="${dto.sumAmount}"/>
			</td>
            <td class="tbl_page_td_left vtip" width="50px">
            	<font size="2px" class="vtip">
            		<t:codeValue items="${RP_0001 }" key="${dto.reportStatus}" type="label" />
            	</font>
           	</td>
           	<td class="tbl_page_td_left vtip" width="65px">
            	<font size="2px" class="vtip">
            		<t:codeValue items="${RP_0002 }" key="${dto.feedbStatus}" type="label" />
            	</font>
           	</td>
           	<td class="tbl_page_td_right vtip" width="50px">
				<t:moneyFormat type="label" value="${dto.errorCnt}" format="###,###,###,###"/>
			</td>
			<td class="tbl_page_td_left vtip" width="50px">
            	<font size="2px" class="vtip">
            		<t:codeValue items="${RP_0003 }" key="${dto.errorStatus}" type="label" />
            	</font>
           	</td>
            <td class="tbl_page_td_right vtip" width="60px">
				<t:moneyFormat type="label" value="${dto.inputCnt}" format="###,###,###,###"/>
			</td>
			 <td class="tbl_page_td_right vtip" width="60px">
				<t:moneyFormat type="label" value="${dto.authCnt}" format="###,###,###,###"/>
			</td>
            <td class="tbl_page_td_left" width="40px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.reportId)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
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
		<util:pagination page="${page}" query="reportMonth=${reportDataForm.reportData.reportMonth}&reportStatus=${reportDataForm.reportData.reportStatus}
		&feedbStatus=${reportDataForm.reportData.feedbStatus}&errorStatus=${reportDataForm.reportData.errorStatus}&reportName=${reportDataForm.reportData.reportName}" />
	</div>
</div>
