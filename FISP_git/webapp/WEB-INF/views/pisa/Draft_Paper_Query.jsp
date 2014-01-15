<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/Draft_Paper_Qry/Qry";
		form.submit();
	}
	//detail button
	function detailSearch(id) {
		document.getElementById("seqNo").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/Draft_Paper_Qry/Detail?bizDraftAcptList.seqNo='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//input button
	function input(id) {
		var old_url = window.location.href;
		document.getElementById("seqNo").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/Draft_Paper_Qry/Input?bizDraftAcptList.seqNo='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.location.href = old_url;
	}
	//del button
	function del(id) {
		document.getElementById("seqNo").value = id;
		var form = document.getElementById("draft_Paper_Form");
		form.action = "${pageContext.request.contextPath}/Draft_Paper_Qry/Delete";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/Draft_Paper_Qry/Add', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="draft_Paper_Form">
			<form:form commandName="draft_Paper_Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">纸质商业汇票补录（承兑/付款）</div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="draft_Paper_Form" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td">机构：</td>
				<td>
					<form:select path="bizDraftAcptList.instId" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_1001}" />
					</form:select>
				</td>
				<td class="label_td">汇票号码：</td>
				<td  colspan="2">
					<form:input path="bizDraftAcptList.vocNo" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td">付款人账号：</td>
				<td>
					<form:input path="bizDraftAcptList.payerActno" type="text" class=".input-small"/>
				</td>
	    		<td class="label_td">付款人名称：</td>
				<td>
					<form:input path="bizDraftAcptList.payerName" type="text" class=".input-small"/>
				<td>
			</tr>
			<tr>
				<td class="label_td">收款人账号：</td>
				<td>
					<form:input path="bizDraftAcptList.payeeActno" type="text" class=".input-small"/>
				</td>
	    		<td class="label_td">收款人名称：</td>
				<td>
					<form:input path="bizDraftAcptList.payeeName" type="text" class=".input-small"/>
				<td>
			</tr>
			<tr>
				<td class="label_td">票据金额</td>
				<td colspan="2">
					 <t:moneyFormat value="${draft_Paper_Form.minDraftAmt}" type="text" name="minDraftAmt"/>&nbsp;~&nbsp;  
				 	 <t:moneyFormat value="${draft_Paper_Form.maxDraftAmt}" type="text" name="maxDraftAmt"/>
				</td>
				<td colspan="2">
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<button type="button" class="btn btn-primary" onclick="add()"><spring:message code="button.label.Add"/></button>
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
        	<th class="tbl_page_th" width="100px"><spring:message code="label_BRANCH_NAME"/></th>
          	<th class="tbl_page_th" width="200px">付款人名称</th>
          	<th class="tbl_page_th" width="200px">收款人名称</th>
          	<th class="tbl_page_th" width="150px">票据金额</th>
          	<th class="tbl_page_th" width="140px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="loanBalanceListForm" action="${pageContext.request.contextPath}" modelAttribute="loanBalanceForm">
      	<input id="seqNo" type="hidden" name="bizDraftAcptList.seqNo"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="100px">
           		<font size="2px" class="vtip">
           			<t:codeValue items="${BM_1001 }" key="${dto.instId}" type="label" />
           		</font></td>
            <td class="tbl_page_td_left vtip" width="200px">${dto.payerName }</td>
            <td class="tbl_page_td_left vtip" width="200px">${dto.payeeName }</td>
            <td class="tbl_page_td_left vtip" width="150px"> 
            	<t:moneyFormat type="label" value="${dto.draftAmt}" /></td>
            <td class="tbl_page_td_left" width="140px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.seqNo)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="inputBtn" class="btn btn-small" onclick="input('${f:h(dto.seqNo)}')"value="<spring:message code="button.fisp.input"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(dto.seqNo)}')"value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination page="${page}" query="instId=${draft_Paper_Form.bizDraftAcptList.instId}
		&vocNo=${draft_Paper_Form.bizDraftAcptList.vocNo}&payerActno=${draft_Paper_Form.bizDraftAcptList.payerActno}
		&payerName=${draft_Paper_Form.bizDraftAcptList.payerName}&payeeActno=${draft_Paper_Form.bizDraftAcptList.payeeActno}
		&payeeName=${draft_Paper_Form.bizDraftAcptList.payeeName}&minDraftAmt=${draft_Paper_Form.minDraftAmt}&maxDraftAmt=${draft_Paper_Form.maxDraftAmt}&}" />
	</div>
</div>
