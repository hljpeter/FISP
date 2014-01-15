<%@page import="com.synesoft.fisp.app.common.utils.CommonUtil"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width" />
<meta http-equiv=Content-Type content=text/html;charset=utf-8 />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/css/bootstrap.min.css" media="screen">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/jquery-ui-1.8.21/css/redmond/jquery-ui-1.8.21.custom.css" />
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" 
  	href="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/css/bootstrap-ie6.css">
<![endif]-->
<!--[if lte IE 7]>
<link rel="stylesheet" type="text/css" 
	href="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/css/ie.css">
<![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/css/common.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="${pageContext.request.contextPath}/resources/vendor/css/tipswindown.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="${pageContext.request.contextPath}/resources/vendor/css/vtip.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="${pageContext.request.contextPath}/resources/vendor/css/kalendae.css" />
<script type="text/javascript">
	contextPath = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/json/jquery.json-2.4.min.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap-ie.js"></script>
<![endif]-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/js/tipswindown.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap.min.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/table.tr.js"></script>

<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/js/vtip.js"></script>

<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/js/kalendae.standalone.js"></script>

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
}); 
</script>
<c:set var="titleKey">
	<tiles:insertAttribute name="title" ignore="true" />
</c:set>
<title><spring:message code="${titleKey}" text="PBOC FISP" /></title>
<base target="_self">
</head>
<body style="overflow-x:hidden;">
	<div class="container">
		<tiles:insertAttribute name="body" />
		<!-- 
		<hr style="margin-bottom: 2px;">
		<p style="text-align: center; background: #e5eCf9;">Copyright
			&copy; 2013 Shanghai NTT DATA Synergy Corporation.</p>
		 -->
	</div>
	<input type ="hidden" value="<spring:message code="w.cm.1000"></spring:message>" id ="confirmMessage" />
	<input type ="hidden" value="<spring:message code="w.cm.1002"></spring:message>" id ="confirmMsg1" />
	<input type ="hidden" value="<spring:message code="w.cm.1003"></spring:message>" id ="confirmMsg2" />
	<input type ="hidden" value="<spring:message code="w.cm.1004"></spring:message>" id ="alertMsg1" />
	<input type ="hidden" value="<spring:message code="w.cm.1005"></spring:message>" id ="alertMsg2" />
</body>
</html>
