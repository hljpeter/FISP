<%@page import="com.synesoft.fisp.app.common.*"%>
<%@page import="com.synesoft.fisp.app.common.constants.ContextConst"%>
<%@page import="com.synesoft.fisp.domain.model.UserInf"%>
<%@page import="com.synesoft.fisp.domain.model.OrgInf"%>
<%@page import="com.synesoft.fisp.app.common.utils.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	window.name="curWindow";
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
			window.showModalDialog('${pageContext.request.contextPath}/bp04/07/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="LOAB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp06/07/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		}else if(msgType=="DEPB"){
			window.showModalDialog('${pageContext.request.contextPath}/bp05/07/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
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
<div class="page_title"><spring:message code="fisp.title.DataManageReportStatusDetailQuery"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/rq01/02/init" method="post" modelAttribute="reportDataForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="fisp.rq.reportMonth"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${reportDataForm.reportData.reportMonth}" format="month" cssClass=".input-small" readonly="true"/>
				</td>
	    		<td class="label_td"><spring:message code="fisp.rq.reportStatus"/></td>
				<td>
					<t:codeValue items="${RP_0001}" key="${reportDataForm.reportData.reportStatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<c:if test="${reportDataForm.reportData.reportStatus=='2'||reportDataForm.reportData.reportStatus=='3'}">
			<tr>
				<td class="label_td"><spring:message code="fisp.rq.orgName"/></td>
				<td colspan="3">
					<form:input path="reportData.orgName" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.rq.reportTlrno"/></td>
				<td>
					<form:input path="reportData.reportTlrno" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.rq.reportTime"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${reportDataForm.reportData.reportTime}" format="datetime" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			</c:if>
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
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
