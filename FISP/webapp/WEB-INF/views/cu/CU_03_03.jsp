<script type="text/javascript">
	var rtn={};
	window.name="curWindow";
	var chooseRecord = false;
	
	function trim(str) {
		for ( var i = 0; i < str.length && str.charAt(i) == " "; i++)
			;
		for ( var j = str.length; j > 0 && str.charAt(j - 1) == " "; j--)
			;
		if (i > j)
			return "";
		return str.substring(i, j);
	}

	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/cu03/03/search";
		form.submit();
	}

	function choose(obj) {
		if (chooseRecord) {
			rtn.acctcode = trim(obj.cells[1].innerHTML);
			rtn.acctname = trim(obj.cells[2].innerHTML);
			rtn.reckbankno = trim(obj.cells[3].innerHTML);
			rtn.genbankname = trim(obj.cells[4].innerHTML);
			window.returnValue = $.toJSON(rtn);
			window.close();
		}
	}

	//select button
	function selectRecord() {
		chooseRecord = true;
	}
</script>

<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="tipsAcctInfForm">
			<form:form commandName="tipsAcctInfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title"><spring:message code="cu.label.AcctInfQuery"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/cu03/03/init" method="post" modelAttribute="tipsAcctInfForm" class="form-horizontal" target="curWindow">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="cu.label.AcctCode"/></td>
				<td>
					<form:input path="acctcode" type="text" class="input-xxlarge" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="cu.label.AcctName"/> </td>
				<td>
					<form:input path="acctname" type="text" class="input-xxlarge"/>
    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
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
        	<th class="tbl_page_th" width="80px"><spring:message code="cu.label.AcctCode"/></th>
          	<th class="tbl_page_th" width="150px"><spring:message code="cu.label.AcctName"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="cu.label.ReckBankNo"/></th>
          	<th class="tbl_page_th" width="150px"><spring:message code="index.label.sm.BankName"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="cu.label.AcctProp"/></th>
          	<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="tipsAcctInfForm" action="${pageContext.request.contextPath}" modelAttribute="tipsAcctInfForm">
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr onclick="choose(this);">
          	<td class="tbl_page_td_left" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="80px">${dto.acctcode}</td>
            <td class="tbl_page_td_left vtip" width="150px">${dto.acctname}</td>
            <td class="tbl_page_td_left vtip" width="80px">${dto.reckbankno}</td>
            <td class="tbl_page_td_left vtip" width="150px">${dto.genbankname}</td>
            <td class="tbl_page_td_left vtip" width="100px">
            	<t:codeValue items="${CU_0001}" key="${dto.accttype}" type="label" />
            </td>
            <td class="tbl_page_td_left" width="40px">
	           	<div align="center" style="height: 25px">
					<input type="button" class="btn btn-small" onclick="selectRecord()" value="<spring:message code="button.lable.Choose"/>">
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
<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 35px;">
	<div class="leftPage">
		<util:pagination page="${page}" query="tipsAcctInf.acctcode=${tipsAcctInfForm.tipsAcctInf.acctcode}" />
	</div>
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="closeBtn" class="btn btn-primary" value="<spring:message code="button.lable.close"/>" onclick="javascript:window.close();"/>
	</div>
</div>