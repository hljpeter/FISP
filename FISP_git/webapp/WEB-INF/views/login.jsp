
<%@page import="com.synesoft.fisp.app.common.constants.CommonConst"%>
<%@page import="com.synesoft.fisp.app.common.constants.UserConst"%>
<%@page import="com.synesoft.fisp.app.common.constants.UrlMap"%>
<%@page import="com.synesoft.fisp.app.common.constants.ContextConst"%>
<%@page import="com.synesoft.fisp.domain.model.UserInf"%>

<%
	// 当前用户已经登录，则自动进入系统
	
	// 获取当前用户
	UserInf user = ContextConst.getCurrentUser();
	
	if(user != null && !"".equals(user.getUserid()) && !"".equals(user.getPassword())) {
		if(UserConst.STATUS_LOGINED.equals(user.getStatus())) {
			try {
				request.getRequestDispatcher(UrlMap.INDEX_URL).forward(request, response);
			} catch (Exception e) {
				
			}
			return;
		}
	}
	
%>

<script type="text/javascript">

<!--
$(function(){
	$('#userId').focus();
});
//-->
function Language(){
	var form = document.getElementById("form");
	var languageType = $("#languageType").val();
	//var locale="zh_CN";
	if(languageType == 1){
		locale = "zh_CN";
	}else if(languageType == 2){
		locale = "en_US";
	}
	form.action = "${pageContext.request.contextPath}/language?locale="+locale;
	form.submit();
}
</script>

<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error"/>
		
		<spring:hasBindErrors name="loginForm">
			<form:form commandName="loginForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div  class="row-fluid" style="margin-bottom:20px;margin-top:60px;margin-left:60px;margin-right:20px;height:500px;width:800px; background: url(${pageContext.request.contextPath}/resources/vendor/img/welcom.jpg);">
	<form:form id="form" action="${pageContext.request.contextPath}/login" modelAttribute="loginForm" class="form-horizontal">
	<div style="height:550px;" >
		<table height="100%" align="center" valign="center">
		<tr><td>
			<div align="center"><h2 style="color: #015A9C;"><spring:message code="system.title"/></h2></div>
		</td></tr>
		<tr><td>
			<div style="margin-left:-100px;">
				<div class="control-group">
					<div class="control-label" style="color: #015A9C;"><spring:message code="login.label.language"/></div>
					<div class="controls" style="text-align: left; width:280px;">
						<form:select id="languageType" path="languageType" onchange="Language()">
							<form:options items="${LanguageChoose}" />
						</form:select>
					</div>
				</div>
			</div>
			<div style="margin-left:-100px;">
				<div class="control-group">
					<div class="control-label" style="color: #015A9C;"><spring:message code="login.label.userId"/></div>
					<div class="controls" style="text-align: left;">
						<form:input path="userId" type="text" id="userId" maxlength="20"/>
					</div>
				</div>
				<div class="control-group">
					<div class="control-label" style="color: #015A9C;"><spring:message code="login.label.password"/></div>
					<div class="controls" style="text-align: left;">
						<form:input path="password" type="password" maxlength="60"/>
					</div>
				</div>
				<div class="control-group">
		    		<div class="controls">
		    			<input type="submit" class="btn btn-primary"  value="<spring:message code="login.label.login"/>"/> 
		    		</div>
		    	</div>
		    </div>
		</td></tr>
		</table>
		</div>
	</form:form>
</div>
