<script type="text/javascript">
//search button
function search() {
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/Draft_Item/QryEdit";
	form.submit();
}
	//detail button
	function sheetDetail(sheetNo,itemNo) {
		window.showModalDialog('${pageContext.request.contextPath}/Draft_Item/DetilEdit?sheetNo='+sheetNo+'&itemNo='+itemNo,window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
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
<div class="page_title"><spring:message code="pisa.draft.ItemQurEdit"/></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="draft_Sheet_Form" class="form-horizontal">
		<table class="tbl_search">	
			<tr>
			<td class="label_td"><spring:message code="pisa.draft.ITEM_NO"/></td>
			<td>
				<form:input path="itemNo" type="text" class=".input-small"/> 
			</td>
				<td class="label_td"><spring:message code="pisa.draft.ITEM_NAME"/></td>
				<td>
				<form:input path="itemName" type="text" class=".input-small"/> 					
				</td>									
			</tr>				
			
	    	<tr>
	    		<td class="label_td"><spring:message code="pisa.draft.Sub_NO"/></td>
				<td>
				<form:input path="subNo" type="text" class=".input-small"/> 					
				</td>
				<td class="label_td"><spring:message code="pisa.draft.Area_Type"/></td>
				<td>
				<form:select path="areaType" >
	    				<form:option value=""></form:option>
						<form:options items="${BQ_0003}" />
				</form:select>					
				</td>				
			</tr>
			<tr>
				<td class="label_td"><spring:message code="pisa.draft.Area_Code"/></td>
				<td colspan="2">
				<form:input path="areaCode" type="text" class=".input-small"/> 	
						
				</td>
									
				<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
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
        	<th class="tbl_page_th" width="40px"><spring:message code="pisa.draft.Sub_NO"/></th>			
        	<th class="tbl_page_th" width="30px"><spring:message code="pisa.draft.Area_Type"/></th>	
        	<th class="tbl_page_th" width="30px"><spring:message code="pisa.draft.Area_Code"/></th>	
        	<th class="tbl_page_th" width="70px"><spring:message code="pisa.draft.Sheet_Cjbd"/></th>	        	        	
        	<th class="tbl_page_th" width="50px"><spring:message code="pisa.draft.Item_No"/></th>
          	<th class="tbl_page_th" width="120px"><spring:message code="pisa.draft.ITEM_NAME"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="Draft_Sheet_Form" action="${pageContext.request.contextPath}" modelAttribute="draft_Sheet_Form">
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="40px">${dto.subNo}</td>
            <td class="tbl_page_td_left vtip" width="30px">${dto.areaType}</td>
            <td class="tbl_page_td_left vtip" width="30px">${dto.areaCode}</td>
            <td class="tbl_page_td_left vtip" width="70px">${dto.sheetNo}</td>
            <td class="tbl_page_td_left vtip" width="50px">${dto.itemNo}</td>
            <td class="tbl_page_td_left vtip" width="120px">${dto.itemName}</td>
            <td class="tbl_page_td_left" width="50px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="sheetDetail('${f:h(dto.sheetNo)}','${f:h(dto.itemNo)}')"value="<spring:message code="fisp.title.buru"/>">
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
		<util:pagination page="${page}" action="/Draft_Item/QryEdit" query="itemNo=${draft_Sheet_Form.itemNo}&itemName=${draft_Sheet_Form.itemName}&subNo=${draft_Sheet_Form.subNo}&areaType=${draft_Sheet_Form.areaType}&areaCode=${draft_Sheet_Form.areaCode}" />
	</div>
</div>
