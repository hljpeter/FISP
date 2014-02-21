
<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BM_Cur_Qry/Qry";
		form.submit();
	}	
	function add(){
		window.showModalDialog('${pageContext.request.contextPath}/BM_Cur_Upd/AddInit', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	//modify button
	function updataCur(currCode,currName,currLan) {	
		window.showModalDialog('${pageContext.request.contextPath}/BM_Cur_Upd/Init?currCode='+currCode+'&currName='+currName+'&currLan='+currLan, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		form.submit();
	}
	
	//del button
	function del(currCode,currName,currLan) {
		form.action = "${pageContext.request.contextPath}/BM_Cur_Upd/Delete?currCode="+currCode+"&currName="+currName+"&currLan="+currLan;
		var msg=$("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
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
		<spring:hasBindErrors name="bm_Cur_QryForm">
			<form:form commandName="bm_Cur_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.title.bm.CurrMaintain.qry"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/BM_Cur_Qry/Qry" method="post" modelAttribute="bm_Cur_QryForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.bm.currCode"/></td>
				<td>
					<form:input path="sysCurrency.currCode" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="index.label.bm.currLan"/></td>
				<td>
					<form:select path="sysCurrency.currLan" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_DICT}" />
					</form:select>
				</td>
			</tr>		
			<tr>
			   <td class="label_td"><spring:message code="index.label.bm.CurrName"/></td>
				<td colspan="2">	
					<form:input path="sysCurrency.currName" type="text" class="span5"  />
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
          	<th class="tbl_page_th" width="80px"><spring:message code="index.label.bm.currCode"/></th>
          	<th class="tbl_page_th" width="150px"><spring:message code="index.label.bm.CurrName"/></th>  
          	<th class="tbl_page_th" width="120px"><spring:message code="index.label.bm.currLan"/></th>          
       	    <th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="dataDictForm" action="${pageContext.request.contextPath}" modelAttribute="bm_Cur_QryForm">
        <c:forEach var="sysCurrency" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            
            <td class="tbl_page_td_left vtip" width="80px"> ${sysCurrency.currCode} </td>
            <td class="tbl_page_td_left vtip" width="150px">${sysCurrency.currName}</td>   
            <td class="tbl_page_td_left vtip" width="120px">
				<t:codeValue items="${BM_DICT}" key="${sysCurrency.currLan}" type="label"/>
			</td>   
				
			<td class="tbl_page_td_left " align="center" width="100px">
			<div align ="center" >
			<input type="button" id="updBtn" class="btn btn-small" onclick="updataCur('${f:h(sysCurrency.currCode)}','${f:h(sysCurrency.currName)}','${f:h(sysCurrency.currLan)}')"value="<spring:message code="button.lable.Modify"/>">
			<input type="button" id="deleteBtn" class="btn btn-small" onclick="del('${f:h(sysCurrency.currCode)}','${f:h(sysCurrency.currName)}','${f:h(sysCurrency.currLan)}')" value="<spring:message code="button.lable.Del"/>">      
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
		<util:pagination page="${page}" query="currCode=${sysCurrency.currCode}&currName=${sysCurrency.currName}&currLan=${sysCurrency.currLan}" />
	</div>
</div>
