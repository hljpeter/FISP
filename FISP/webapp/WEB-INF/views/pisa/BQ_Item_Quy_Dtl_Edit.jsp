<script type="text/javascript">

	//detail button
	function edit(sheetNo,seqNo) {
		window.showModalDialog('${pageContext.request.contextPath}/Draft_Item/DetilEditPre?sheetNo='+sheetNo+'&seqNo='+seqNo,window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		query();
	}	
	
	function query() {
		
		var form = document.getElementById("form");
		
		form.submit();
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="Draft_Sheet_Form">
			<form:form commandName="Draft_Sheet_Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/Draft_Item/DetilEdit" method="post" modelAttribute="draft_Sheet_Form" class="form-horizontal">
		<table class="tbl_search">
		
	    	<tr>
	    		<td class="label_td"><spring:message code="pisa.draft.Sub_NO"/></td>
				<td>
				<form:input path="itemNo" cssStyle="display:none" readonly="true"/>
				<form:input path="subNo" type="text" class=".input-small" readonly="true"/> 					
				</td>
				<td class="label_td"><spring:message code="pisa.draft.Area_Type"/></td>
				<td>
				<form:select path="areaType" disabled="true">	    				
						<form:options items="${BQ_0003}" readonly="true"/>
				</form:select>			
				</td>				
			</tr>
			<tr>
				<td class="label_td"><spring:message code="pisa.draft.Area_Code"/></td>
				<td>
				<form:input path="areaCode" type="text" class=".input-small" readonly="true"/> 					
				</td>
				<td class="label_td"><spring:message code="pisa.draft.Sheet_Cjbd"/> </td>					
				<td>
	    		<form:input path="sheetNo" type="text" class=".input-small" readonly="true"/>
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
        	<th class="tbl_page_th" width="15px"><spring:message code="pisa.draft.Sheet_No"/></th>
        	<th class="tbl_page_th" width="40px"><spring:message code="pisa.draft.Item_No"/></th>
          	<th class="tbl_page_th" width="30px"><spring:message code="pisa.draft.Dim_No"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="pisa.draft.Item_Name"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="pisa.draft.Dim_Name"/></th>
          	<th class="tbl_page_th" width="75px"><spring:message code="pisa.draft.Data_Type1"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="pisa.draft.Data_Value1"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="pisa.draft.Data_Value2"/></th>
          	<th class="tbl_page_th" width="30px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="Draft_Sheet_Form" action="${pageContext.request.contextPath}" modelAttribute="draft_Sheet_Form">
       	<input id="sheetNo" type="hidden" name="draft_Sheet_Form.sheetNo" />
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="15px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="40px">${dto.itemNo}</td>
            <td class="tbl_page_td_left vtip" width="30px">${dto.dimNo}</td>
            <td class="tbl_page_td_left vtip" width="100px">${dto.itemName}</td>
            <td class="tbl_page_td_left vtip" width="80px">${dto.dimName}</td>
            <td class="tbl_page_td_left vtip" width="75px">${dto.dataType1}</td>
            <td class="tbl_page_td_left vtip" width="50px">${dto.dataValue1}</td>
            <td class="tbl_page_td_left vtip" width="50px">${dto.dataValue2}</td>
            <td class="tbl_page_td_left" width="30px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="edit('${dto.sheetNo}','${dto.seqNo}')" value="<spring:message code="dp.lable.Express"/>">
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
		<util:pagination page="${page}" action="/Draft_Item/DetilEdit" query="subNo=${draft_Sheet_Form.subNo}&areaType=${draft_Sheet_Form.areaType}&areaCode=${draft_Sheet_Form.areaCode}&sheetNo=${draft_Sheet_Form.sheetNo}&itemNo=${draft_Sheet_Form.itemNo}" />	
	</div>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>