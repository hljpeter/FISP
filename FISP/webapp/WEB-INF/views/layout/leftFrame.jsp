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
<title>header</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/jquery-1.7.2.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/js/bootstrap.min.js"></script>

<!-- 自定义CSS -->
<style>
.big_menu {
  background-color: #F0F0F0;
}

.mid_menu2 {
  background-color: #D1E9E9;
}

.mid_menu3 {
  background-color: #D2E9FF;
}

.mid_menu4 {
  background-color: #DFFFDF;
}

.mid_menu5 {
  background-color: #E8FFC4;
}

.mid_menu6 {
  background-color: #FBFBFF;
}

.mid_menu7 {
  background-color: #FBFBFF;
}
.noborder {
  border-width: 0px;
}

.nomargin_btm {
  margin-bottom: 0px;
}

.nopadding_btm {
  padding-bottom: 0px;
}
</style>
<style>
body {
	background: url(${pageContext.request.contextPath}/resources/vendor/img/ground_bg.jpg);
	 filter:alpha(opacity=40); 
}
</style>
<script  type ="text/javascript">
    function ChangeVisible()
    {
	    if(parent.document.getElementById('mainFrame').cols != "10,*")
	    {
	    	$("#left_td").hide();
		    parent.document.getElementById('mainFrame').cols = "10,*";
				parent.document.getElementById("mainFrame").rows=parent.document.getElementById("mainFrame").rows;
		    document.all.menuSwitch.innerHTML = "<img alt='' src='${pageContext.request.contextPath}/resources/vendor/img/sidebox_r.jpg' onclick='ChangeVisible();' style='cursor:pointer;'/>";
	    }
	    else
	    {
	    	$("#left_td").show();
		    parent.document.getElementById('mainFrame').cols = "230,*";
				parent.document.getElementById("mainFrame").rows=parent.document.getElementById("mainFrame").rows;
			document.all.menuSwitch.innerHTML = "<img alt='' src='${pageContext.request.contextPath}/resources/vendor/img/sidebox_l.jpg' onclick='ChangeVisible();' style='cursor:pointer;'/>";
	    }
    }
    $(function(){
   	$(document).click(function() {
   		$(window.parent.parent.document).find("li.dropdown").removeClass('open');
   	});
   }); 
    </script>
</head>
<body style="width: 230px">
    <table border="0" width="230px" style="margin-right: 0px">
    	<Tr>
    	<TD width="222PX" id="left_td" style="vertical-align:top"><div style="position:absolute;margin-top: 0px">${LEFT_MENU_SESSION }</div><div></div></TD>
    	<TD width="8PX" style="vertical-align: middle;">
    	  <div id="menuSwitch" style="margin-top:220px;margin-right:0px;border: 0px;border-color: blue;">
 		  <img alt="" src="${pageContext.request.contextPath}/resources/vendor/img/sidebox_l.jpg" onclick="ChangeVisible();" style="cursor:pointer;"/>
	 	  </div>
	 	</TD>
    	</Tr>
    </table>
    
</body>
</html>
