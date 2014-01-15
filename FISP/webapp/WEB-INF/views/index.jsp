<%@page import="com.synesoft.fisp.domain.model.UserOrgInf"%>
<%@page import="com.synesoft.fisp.app.common.context.CommonResourceHelper"%>
<%@page import="com.synesoft.fisp.domain.model.TipsConn"%>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/css/font-awesome.min.css">
<style type="text/css">
html {
	height: 100%;
}

</style>
<title> PBOC Financial  Information Standardization Project</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/jquery-1.7.2.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/common.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="${pageContext.request.contextPath}/resources/vendor/css/tipswindown.css" />
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/js/tipswindown.js"></script>	
<script type="text/javascript">
$(function(){
	var h = document.body.clientHeight - 101 - 28;
	$("#myFrame").attr("height",h);
	$("#header1").css("width",document.body.clientWidth);
	$("#header2").css("width",document.body.clientWidth);
	$("#header3").css("width",document.body.clientWidth);
	$(document).click(function() {
		$(".dropdown-toggle").parent().removeClass('open');
	});
});
// 退出系统
function logout() {
	if (confirm('是否确定退出系统？')) {
		top.window.location="${pageContext.request.contextPath}/logout";
	}
}
//-->
</script>
<body style="height:100%; overflow-x: hidden; overflow-y: hidden;">
	<%
		// 获取当前用户
		UserInf user = ContextConst.getCurrentUser();
		OrgInf orgInf = ContextConst.getOrgInfByUser();
		if(user != null && !"".equals(user.getUserid()) && !"".equals(user.getPassword())) {
			if(orgInf != null) {
				user.setLoginorg(user.getLoginorg().trim());
				pageContext.setAttribute("user", user);
				pageContext.setAttribute("orgInf", orgInf);
	%>

<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="loginForm" >
			<form:form commandName="loginForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="container-fluid">
	<div class="navbar navbar-fixed-top" style="background-color: #eee;">
		<div class="row-fluid main_header_tb1" style="height:60px;margin:0px;position: relative;" id="header1">
			<div class="span3" style="margin-left:2px; width:181px;margin-top:7px;margin-bottom:5px;position: relative;float: left;">
				<img src="${pageContext.request.contextPath}/resources/vendor/img/logo.jpg" style="display:block;">
			</div>
			<div class="span13" style="margin-left:2px;position: relative;float: left;padding:0;white-space:nowrap;overflow:hidden;">
				<h2 style="color: #EFEFED; font-size: 27px;" ><spring:message code="system.title"/></h2>
			</div>
	<!-- 		<div class="span1" style="position: relative;float: left;padding:0;white-space:nowrap;overflow:hidden;"></div>   -->
   			<div class="span3" style="margin-top:26px; font-size: 12px;position: relative;float: left;padding:0;">
   				<div class="dropdown pull-right">
	   				<strong style="color: #EFEFED;">登录机构:</strong><span style="color: #5C9CCB;"><!-- <%=user.getLoginorg() %>- --><%=orgInf.getOrgname() %></span>
	   				<i style="color: #ccc;"> | </i>
	   				<a class="dropdown-toggle main_dropdown" data-toggle="dropdown" href="#" style="color: #EFEFED;">
						机构切换</a>
						<ul class="dropdown-menu main_dropdown" role="menu" aria-labelledby="dropdownMenu">
		   				  <c:forEach var="userorg" items="${USERORGLIST_SESSION }">
		   				  	<c:if test="${userorg.orgid ne orgInf.orgid }">
						  		<li><a tabindex="-1" href="${pageContext.request.contextPath}/reLogin?userlogorg=${userorg.orgid }"><%=CommonResourceHelper.getOrgNameById( ((UserOrgInf)pageContext.getAttribute("userorg")).getOrgid() ) %></a></li>
						  	</c:if>
		   				  </c:forEach>
						</ul>
	   				<i style="color: #ccc;"> | </i>
	   				<a target="_menuTarget" href="rightFrame" style="color: #EFEFED;">首页</a>
   				</div>
   			</div>
   			<div class="span1" style="margin-top:15px; margin-right: 1px;position: relative;float: left;padding:0;white-space:nowrap;overflow:hidden;">
				<button type="button" class="btn btn-primary btn-small pull-right" onclick="logout();">
					<spring:message code="index.label.logout"/>
				</button>
			</div>
		</div>
		<div style="height:10px;margin-left:0px;white-space:nowrap;overflow:hidden;margin-right:0px;background: url(${pageContext.request.contextPath}/resources/vendor/img/logo_bg.jpg);"  id="header2"></div>
		<div class="row-fluid"   id="header3">
			<div class="span12">
    			<div class="navbar-inner" >
    				<%=ContextConst.getMenuList() %>
    			</div>
    		</div>
   		</div>
	</div>
</div>

	<iframe id="myFrame" src="main" width="100%" frameborder="0" style="margin-top: 111px;margin-bottom: 28px"></iframe>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="navbar navbar-fixed-bottom" id="footer" style="margin-bottom:0px; height:21px;">
			<div class="main_footer">
			&nbsp;&nbsp;
	     	<span class="STYLE1">操作员ID:</span>
	     	<span class="STYLE2"><%=user.getUserid() %></span>
	     	&nbsp;&nbsp;&nbsp;&nbsp;
	     	<span class="STYLE1">操作员名称:</span>
	     	<span class="STYLE2"><%=user.getUsername() %></span>
	     	&nbsp;&nbsp;&nbsp;&nbsp;
	     	<span class="STYLE1">金融机构代码:</span>
	     	<span class="STYLE2"><%=orgInf.getBankid() %></span>
	     	&nbsp;&nbsp;&nbsp;&nbsp;
	     	<span class="STYLE1">金融机构名称:</span>
	     	<span class="STYLE2"><%=orgInf.getBankname() %></span>
	     	&nbsp;&nbsp;&nbsp;&nbsp;
	     	<span class="STYLE1">当前系统日期:</span>
	     	<span class="STYLE2"><%=DateUtil.formatStringToDatePattern(orgInf.getSystemdate())%></span>
	     	&nbsp;&nbsp;&nbsp;&nbsp;
	     	<%--items="${CL_0011 }"取不到值
	     	<t:codeValue items="${CL_0011 }" key="0" type="label" />
	     	 --%>
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
	