<%@page import="com.synesoft.fisp.app.common.*"%>
<%@page import="com.synesoft.fisp.app.common.constants.ContextConst"%>
<%@page import="com.synesoft.fisp.domain.model.UserInf"%>
<%@page import="com.synesoft.fisp.domain.model.OrgInf"%>
<%@page import="com.synesoft.fisp.app.common.utils.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	//generate button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/bp03/01/search";
		form.submit();
	}
	
	//generate button
	function generate() {
		var form = document.getElementById("reportDataListForm");
		form.action = "${pageContext.request.contextPath}/bp03/01/generateReport";
		var msg=$("#confirmMsg1").val()+$("#generateBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			alert("有待补录、待审核记录，不能生成报表");
		}else{
			return false;
		}
	}
	
	function transInput(msgType) {
		document.getElementById("reportDataMsgType").value = msgType;
		if(msgType=="LOAF"){
			window.showModalDialog('${pageContext.request.contextPath}/bp04/01/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="LOAB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp06/01/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="DEPB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp05/01/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else{
			return false;
		}
	}
	
	function transAuth(msgType) {
		document.getElementById("reportDataMsgType").value = msgType;
		if(msgType=="LOAF"){
			window.showModalDialog('${pageContext.request.contextPath}/bp04/04/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="LOAB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp06/04/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="DEPB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp05/04/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else{
			return false;
		}
	}
	
	function detailSearch(msgType) {
		document.getElementById("reportDataMsgType").value = msgType;
		if(msgType=="LOAF"){
			window.showModalDialog('${pageContext.request.contextPath}/bp04/07/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="LOAB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp06/07/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="DEPB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp05/07/search', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
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
<div class="page_title"><spring:message code="fisp.title.processedListQuery"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/rq01/02/init" method="post" modelAttribute="reportDataForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="fisp.rq.reportMonth"/></td>
				<td>
					<form:input path="reportMonth" type="text" maxlength="6" class="Wdate .input-small"
					 onclick="WdatePicker({dateFmt:'yyyy-MM'});" />
				</td>
	    		<td class="label_td"><spring:message code="fisp.rq.reportStatus"/></td>
				<td>
					<form:select id="reportStatus" path="reportData.reportStatus"  readonly="true">
						<form:option value="" />
						<form:options items="${RP_0001}" />
					</form:select>
				</td>
				<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    				<input id="generateBtn" type="button" class="btn btn-primary" onclick="generate()" value="<spring:message code="fisp.rq.generateReport"/>">
	    			</div>
	    		</td>
			</tr>
	    </table>											
	</form:form>
</div>
<%
		// 获取当前用户
		UserInf user = ContextConst.getCurrentUser();
		user.setUserid(user.getUserid().trim());
		pageContext.setAttribute("user", user);
		
	%>
<div class="row"  style="margin-bottom: 40px;">
	<div class="tbl_page_head">
	<table class="table table-striped table-bordered table-condensed tbl_page">
      <thead>
        <tr>
        	<th class="tbl_page_th" width="20px">No.</th>
        	<th class="tbl_page_th" width="200px"><spring:message code="fisp.rq.msgType"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="fisp.rq.sumCnt"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.rq.sumAmount"/></th>
          	<th class="tbl_page_th" width="60px"><spring:message code="fisp.rq.inputCnt"/></th>
          	<th class="tbl_page_th" width="60px"><spring:message code="fisp.rq.authCnt"/></th>
          	<th class="tbl_page_th" width="70px"><spring:message code="fisp.rq.errorStatus"/></th>
          	<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page">
      <tbody>
      <form:form id="reportDataListForm" action="${pageContext.request.contextPath}" modelAttribute="reportDataForm">
        <form:input id="reportDataMsgType" type="hidden" path="reportDataDetail.msgType"/>
        <form:input id="reportDataInputCnt" type="hidden" path="reportDataDetail.inputCnt"/>
        <form:input id="reportDataAuthCnt" type="hidden" path="reportDataDetail.authCnt"/>
        <c:forEach var="dto" items="${page.content}" varStatus="i">
          <tr>
          	<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
            <td class="tbl_page_td_left vtip" width="200px">
            	<font size="2px" class="vtip">
            		<t:codeValue items="${RP_0004 }" key="${dto.msgType}" type="label" />
            	</font>
            </td>
			<td class="tbl_page_td_right vtip" width="70px">
				<t:moneyFormat type="label" value="${dto.sumCnt}" format="###,###,###,###"/>
			</td>
			<td class="tbl_page_td_right vtip" width="100px">
				<t:moneyFormat type="label" value="${dto.sumAmount}"/>
			</td>
           <td class="tbl_page_td_right vtip" width="60px">
            	<c:if test="${dto.inputCnt==0}">
					<t:moneyFormat type="label" value="${dto.inputCnt}" format="###,###,###,###"/>
				</c:if>
				<c:if test="${dto.inputCnt>0 && user.userid=='shmaker1' }">
					<a onclick="transInput('${f:h(dto.msgType)}')">
						<t:moneyFormat type="label" value="${dto.inputCnt}" format="###,###,###,###"/>
					</a>
				</c:if>
				<c:if test="${dto.inputCnt>0 && user.userid!='shmaker1'}">
					<t:moneyFormat type="label" value="${dto.inputCnt}" format="###,###,###,###"/>
				</c:if>
			</td>
			 <td class="tbl_page_td_right vtip" width="60px">
			 	<c:if test="${dto.authCnt==0}">
					<t:moneyFormat type="label" value="${dto.authCnt}" format="###,###,###,###"/>
				</c:if>
				<c:if test="${dto.authCnt>0 && user.userid=='shchecker1'}">
					<a onclick="transAuth('${f:h(dto.msgType)}')">
						<t:moneyFormat type="label" value="${dto.authCnt}" format="###,###,###,###"/>
					</a>
				</c:if>	
				<c:if test="${dto.inputCnt>0 && user.userid!='shchecker1' }">
					<t:moneyFormat type="label" value="${dto.authCnt}" format="###,###,###,###"/>
				</c:if>					
			</td>
			<td class="tbl_page_td_left vtip" width="70px">
            	<font size="2px" class="vtip">
            		<t:codeValue items="${RP_0003 }" key="${dto.errorStatus}" type="label" />
            	</font>
           	</td>
            <td class="tbl_page_td_left" width="40px">
	           	<div align="center" style="height: 25px">
					<input type="button" id="detailBtn" class="btn btn-small" onclick="detailSearch('${f:h(dto.msgType)}')"value="<spring:message code="button.lable.DeatilSearch"/>">
               </div>
            </td>
          </tr>
        </c:forEach>
        </form:form>
      </tbody>
    </table>
	</div>
</div>
