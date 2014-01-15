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
	href="${pageContext.request.contextPath}/resources/vendor/bootstrap-2.2.1/css/bootstrap.min.css" />	
<title>footbar</title>
</head>
<body>
<div class="navbar navbar-fixed-top" id="main">
	<div class="navbar-inner">
		<%
			// 获取当前用户
			UserInf user = ContextConst.getCurrentUser();
			
			OrgInf org = ContextConst.getOrgInfByUser();
			if(user != null && !"".equals(user.getUserid()) && !"".equals(user.getPassword())) {
				if(org != null) {
		%>
		<div class="main_footer">
	  		<table width="100%"  height="25" >
				<tr>
					<td align="left">
						<table>
				   			<tr>
				     			<td align="right" nowrap >
								     	<span class="STYLE1">操作员ID</span>:
								     	<%=user.getUserid() %>
								     	&nbsp;&nbsp;
								     	<span class="STYLE1">操作员名称</span>:
								     	<%=user.getUsername() %>
								     	&nbsp;&nbsp;
								     	<span class="STYLE1">当前系统日期</span>:
								     	<%=DateUtil.formatStringToDatePattern(org.getSystemdate())%>
								     	&nbsp;&nbsp;
								     	<span class="STYLE1">TIPS日期</span>:
								     	<%=DateUtil.formatStringToDatePattern(ContextConst.getTipsConn().getCursysdate()) %>
								     	&nbsp;&nbsp;
								     	<span class="STYLE1">TIPS状态</span>:
								     	<t:codeValue items="${SM_0002 }" key="${fn.trim(TIPS_CONN_SESSION.cursysstatus) }" type="label" />
								     	&nbsp;&nbsp;
							     </td>
				     			 <td align="right">
							     	<table>
							        	<tr>
								          	<td width="21px" ></td>
								          	<td nowrap >
								          	&nbsp;&nbsp;
								          	</td>
							        	</tr>
						      		</table>
						     	</td>
					     	</tr>
				     	</table>
		     		</td>
			   	</tr>
			</table>
	  	</div>
	  	<%
				}
			}
		%>
	</div>
</div>
</body>
</html>