<!-- 用户登录/登出详情 -->
<!-- body -->
<div class="page_title"><spring:message code="LOGIN_LOG_DTL"/></div>
<div class="row">
	<div class="tbl_page_head">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
			<tr>
	        	<th class="tbl_page_th" width="20px"><spring:message code="ftz.label.NO"/></th>
	          	<th class="tbl_page_th" width="100px"><spring:message code="LOGIN_LOG_ORG"/></th>
	          	<th class="tbl_page_th" width="80px"><spring:message code="LOGIN_LOG_IP"/></th>
	          	<th class="tbl_page_th" width="50px"><spring:message code="LOGIN_LOG_TYPE"/></th>
	          	<th class="tbl_page_th" width="50px"><spring:message code="LOGIN_LOG_RESULT"/></th>
	          	<th class="tbl_page_th" width="180px"><spring:message code="LOGIN_LOG_FAIL_SEASON"/></th>
	          	<th class="tbl_page_th" width="120px"><spring:message code="LOGIN_LOG_DATETIME"/></th>
			</tr>
			</thead>
		</table>
    </div>
    <div class="tbl_page_body">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
			<form:form action="${pageContext.request.contextPath}" modelAttribute="MainForm">
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="20px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="100px">${dto.loginorg }</td>
		            <td class="tbl_page_td_left vtip" width="80px">${dto.ip }</td>
		            <td class="tbl_page_td_left vtip" width="50px"><t:codeValue items="${SYS_LOGIN_LOG_TYPE}" key="${dto.type }" type="label" /></td>
		            <td class="tbl_page_td_left vtip" width="50px"><t:codeValue items="${SYS_LOGIN_LOG_RESULT}" key="${dto.result }" type="label" /></td>
		            <td class="tbl_page_td_left vtip" width="180px">${dto.failSeason }</td>
		            <td class="tbl_page_td_left vtip" width="120px"><t:dateTimeFormat type="label" value="${dto.loginDatetime}" format="datetime"/></td>
				</tr>
	        </c:forEach>
	        </form:form>
			</tbody>
		</table>
	</div>
</div>
<!-- page and buttons -->
<div class="pagination pull-right" style="margin-top: 10px;">
	<table class="text-center">
		<tr>
			<td width="20%" align="center">
				<button class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
			</td>
			<td width="50%" align="right">
				<table><tr><td>
					<util:pagination page="${page }" action="/main/loginLogDtl" query="" />	
				</td></tr></table>
			</td>
		</tr>
	</table>
</div>
