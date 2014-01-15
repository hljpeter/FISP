<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/Sheet_Formula_Qry/Qry";
		form.submit();
	}
	//detail button
	function detailSearch(id) {
		document.getElementById("formulaId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/Sheet_Formula_Qry/Detail?expSheetFormula.formulaId='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//input button
	function modify(id) {
		document.getElementById("formulaId").value = id;
		window.showModalDialog('${pageContext.request.contextPath}/Sheet_Formula_Modify/Init?expSheetFormula.formulaId='+id, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		search();
	}
	//del button
	function del(id) {
		document.getElementById("formulaId").value = id;
		var form = document.getElementById("sheet_Formula_Form");
		form.action = "${pageContext.request.contextPath}/Sheet_Formula_Qry/Delete";
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/Sheet_Formula_Add/Init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sheet_Formula_Form">
			<form:form commandName="sheet_Formula_Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="pisa.title.sheet.formula.query"/></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="sheet_Formula_Form" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="pisa.index.sheet.no"/></td>
				<td>
					<form:select path="sheetNo" class=".input-small">
						<form:option value=""/>
						<form:options items="${BQ_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="pisa.index.item.no"/></td>
				<td>
					<form:input path="itemNo" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="pisa.index.item.name"/></td>
				<td colspan="3">
					<form:input path="itemName" type="text" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="pisa.index.formula.name"/></td>
				<td colspan="3">
					<form:input path="formulaName" type="text" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="pisa.index.dim.no"/></td>
				<td colspan="2">
					<form:input path="dimNo" type="text" class=".input-small"/>
				</td>
				<td>
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
        	<th class="tbl_page_th" width="130px"><spring:message code="pisa.index.sheet.no"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="pisa.index.formula.name"/></th>
          	<th class="tbl_page_th" width="350px"><spring:message code="pisa.index.formula"/></th>
          	<th class="tbl_page_th" width="140px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="sheet_Formula_Form" action="${pageContext.request.contextPath}" modelAttribute="sheet_Formula_Form">
      	<input id="formulaId" type="hidden" name="expSheetFormula.formulaId"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="130px">${dto.sheetNo }</td>
            <td class="tbl_page_td_left vtip" width="200px">${dto.formulaName }</td>
            <td class="tbl_page_td_left vtip" width="350px">${dto.formulaArea }</td>
            <td class="tbl_page_td_left" width="140px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.formulaId)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
					<input type="button" id="inputBtn" class="btn btn-small" onclick="modify('${f:h(dto.formulaId)}')"value="<spring:message code="button.lable.Modify"/>">
					<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(dto.formulaId)}')"value="<spring:message code="button.lable.Del"/>">
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
		<util:pagination action="/Sheet_Formula_Qry/Qry" page="${page}" query="sheetNo=${sheet_Formula_Form.sheetNo}
		&itemNo=${sheet_Formula_Form.itemNo}&itemName=${sheet_Formula_Form.itemName}
		&dimNo=${sheet_Formula_Form.dimNo}&formulaName=${sheet_Formula_Form.formulaName}" />
	</div>
</div>
