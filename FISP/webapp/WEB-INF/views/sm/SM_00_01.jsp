<%--
SM_00_01.jsp
菜单显示
 --%>
<%@page import="com.synesoft.fisp.app.common.constants.CommonConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/extjs3.4.0/resources/css/ext-all.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/extjs3.4.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/extjs3.4.0/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/extjs3.4.0/src/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/js/menu.js"></script>
<div>
	<%
		String menuId=request.getParameter("menuId");
		String editable=request.getParameter("editable");
	%>
	  	<input type="hidden" id="menuId" name="menuId" value="<%=menuId %>" />
	  	<input type="hidden" id="editable" name="editable" value="<%=editable %>" />
  	<div>
		<table>
			<tr>
				<td valign="top" style="padding-top:5px;" >
					<table>
				<tr>
					<td style="border-right: 1px solid #b5d6e6; ">
							<div id="funcUI" style="float: left; width: 350px;"></div>
					</td>
				</tr>
				     </table>
				</td>
			</tr>
		</table>
  	</div>
</div>

