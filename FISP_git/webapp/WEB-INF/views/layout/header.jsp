<%@page import="com.synesoft.fisp.app.common.*"%>
<%@page import="com.synesoft.fisp.app.common.constants.ContextConst"%>
<%@page import="com.synesoft.fisp.domain.model.UserInf"%>
<%@page import="com.synesoft.fisp.domain.model.OrgInf"%>
<%@page import="com.synesoft.fisp.app.common.utils.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/jquery-ui-1.8.21/css/redmond/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/css/bootstrap.min.css" media="screen">
<title>header</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/jquery-1.7.2.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap.min.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap-popover.js"></script>
<script type="text/javascript">

<!--
// 退出系统
function logout() {
	if (confirm('是否确定退出系统？')) {
		top.window.location="${pageContext.request.contextPath}/logout";
	}
}
//-->
</script>
<body>
	<%
		// 获取当前用户
		UserInf user = ContextConst.getCurrentUser();
		
		OrgInf org = ContextConst.getOrgInfByUser();
		if(user != null && !"".equals(user.getUserid()) && !"".equals(user.getPassword())) {
			if(org != null) {
	%>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span9">
				<h1><spring:message code="system.title"/></h1>
			</div>
   			<div class="span2" style="margin-top:20px;">
   				<span><spring:message code="index.label.LoginOrg"/></span>: <%=user.getLoginorg() %>
   				<span><spring:message code="index.label.SwitchOrg"/></span>: 
   			</div>
   			<div class="span1" style="margin-top:20px; text-align: right;">
				<button type="button" class="btn btn-primary btn-small" onclick="logout();">
					<spring:message code="index.label.logout"/>
				</button>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span12">
   				<div class="navbar navbar-static">
    				<div class="navbar-inner">
    					<%=ContextConst.getMenuList() %>
    				</div>
    			</div>
    		</div>
   		</div>	
	</div>

  	<%
			}
		}
	%>

</body>
</html>
