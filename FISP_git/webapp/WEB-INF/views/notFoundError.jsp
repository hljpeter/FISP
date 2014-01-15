<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/vendor/css/common.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/common.js"></script>
<style type="text/css">
body {
  padding-top: 60px;
  /* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<title>SessionInvalid</title>
</head>
<body>
	<div class="container">
	<br /><br />
	  <div class="row">
	    <div class="span12">
	      <p class="alert alert-error">
	        &nbsp;&nbsp;<spring:message code="exception.label.1003"/><br />
	      </p>
	     <div class="row">
	         <input type="button" class="btn btn-primary" value="<spring:message code="exception.label.1004"/>" onclick="top.location.href='${pageContext.request.contextPath}'">
	     </div>
	    </div>
	  </div>
	</div>
</body>
</html>

