<%@page import="com.synesoft.fisp.app.common.*"%>
<%@page import="com.synesoft.fisp.app.common.constants.ContextConst"%>
<%@page import="com.synesoft.fisp.domain.model.UserInf"%>
<%@page import="com.synesoft.fisp.domain.model.OrgInf"%>
<%@page import="java.util.List"%>
<%@page import="com.synesoft.fisp.domain.model.*"%>
<%@page import="com.synesoft.fisp.app.common.model.*"%>
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
<title>header</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/jquery-1.7.2.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap.min.js"></script>
<style>
body {
	background: url(${pageContext.request.contextPath}/resources/vendor/img/ground_bg.jpg);
}
</style>
<script type="text/javascript">
 $(function(){
	$(document).click(function() {
		$(window.parent.parent.document).find("li.dropdown").removeClass('open');
	});
	
	$("#loginLogDtl").click(function() {
		showDialog('${pageContext.request.contextPath}/main/loginLogDtl', '500', '1040');
	});
}); 
</script>
</head>
<body>
<div class="container">
<div class="row">
	<div class="span5">
		<h3>
		</h3>
	</div>
</div>

	<%
		// 获取当前用户
		UserInf user = ContextConst.getCurrentUser();
		user.setUserid(user.getUserid().trim());
		pageContext.setAttribute("user", user);
		
		// 获取当前用户上一次登录的信息
		UserInf user_old = ContextConst.getUserLastLogin();
		pageContext.setAttribute("user_old", user_old);
		
		if(user != null && !"".equals(user.getUserid()) && !"".equals(user.getPassword())) {
	%>
<div class="row">
	<div class="span4"></div>
	<div class="span6">
		<div class="text-right">
			<small>上次登录时间：<%=DateUtil.formatStringToDatePattern(user_old.getLastlogintime())%> <i style="color: #ddd;"> | </i> 上次登录IP：<%=user_old.getIpaddress() %></small> <i style="color: #ddd;"> | </i> <a id="loginLogDtl" href="javascript: void(0);"><small>详情</small></a>
		</div>
	</div>
</div>

<br>

<c:if test="${! empty MENU_SESSION }">
	<div class="row">
		<div class="span5">
			<div class="alert alert-info text-center"><strong>一般待办事项</strong></div>
			<!-- <ul>
				<c:if test="${user.userid=='admin' }">
					<li>机构管理待审核：<a href="#"><span class="badge badge-info">1</span></a> 笔</li>
					<li>操作员角色管理待审核：<a href="#"><span class="badge badge-info">0</span></a> 笔</li>
					<li>操作员管理待审核：<a href="#"><span class="badge badge-info">2</span></a> 笔</li>
				</c:if>
				<c:if test="${user.userid=='shmaker1' }">
					<li>贷款发生额待补录：<a href="#"><span class="badge badge-info">2</span></a> 笔</li>
					<li>贷款余额待补录：<a href="#"><span class="badge badge-info">6</span></a> 笔</li>
					<li>存款余额待补录：<a href="#"><span class="badge badge-info">10</span></a> 笔</li>
				</c:if>
				<c:if test="${user.userid=='shchecker1' }">
					<li>存款余额待审核：<a href="#"><span class="badge badge-info">3</span></a> 笔</li>
					<li>贷款发生额待审核：<a href="#"><span class="badge badge-info">9</span></a> 笔</li>
					<li>存款余额待审核：<a href="#"><span class="badge badge-info">6</span></a> 笔</li>
				</c:if>
			</ul> -->
			<ul>
			<%List<MainParam> generalList = (List<MainParam>)request.getAttribute("generalList");
				for (int i = 0; i < generalList.size(); i++) {
					MainParam param = generalList.get(i);
					if (null == param.getFlag()) {
			%>
			<li><spring:message code="<%=param.getTabName() %>"/><a href="#"><span class="badge badge-info"><%=param.getCnt() %></span></a> <spring:message code="UNIT_MAIN"/></li>
	        <%} else { %>
			<li><spring:message code='<%=param.getTabName() + "_" + param.getFlag() %>'/><a href="#"><span class="badge badge-info"><%=param.getCnt() %></span></a> <spring:message code="UNIT_MAIN"/></li>
	        <%}} %>
	        </ul>
		</div>
		<div class="span5">
			<div class="alert alert-error 	text-center"><strong>重要提醒事项</strong></div>
			<ul>
			<%List<MainParam> importantList = (List<MainParam>)request.getAttribute("importantList");
				for (int i = 0; i < importantList.size(); i++) {
					MainParam param = importantList.get(i);
					if (("ACT_MSTR_UNDO").equals(param.getTabName())) {
			%>
			<li><spring:message code="ACT_MSTR_UNDO_1"/><%=param.getFlag() %><spring:message code="ACT_MSTR_UNDO_2"/></li>
	        <%} else { %>
			<li><spring:message code='<%=param.getTabName() %>'/><a href="#"><span class="badge badge-info"><%=param.getCnt() %></span></a> <spring:message code="UNIT_MAIN"/></li>
	        <%}} %>
			<!-- 	<li>资金来源有3笔数据尚未审核！</li>
				<li>有一个报文被人行拒绝,请及时处理！</li>
			
				<li>贷款发生额补录：<a href="#"><span class="badge badge-important">1</span></a> 笔</li>
				<li>存款余额补录：<a href="#"><span class="badge badge-important">2</span></a> 笔</li>
				<li>角色信息维护授权：<a href="#"><span class="badge badge-important">3</span></a> 笔</li>
				<li>……</li>
			-->
			</ul>
			</div>
	</div>
</c:if>
	<%
		}
	%>
</div>
</body>
</html>
