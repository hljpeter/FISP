
<script type="text/javascript">
	//search button
	function submitPassword() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/sm/pwdChg/submit";
		form.submit();
	}
	//search button
	function close() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/login";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="pwdChgForm">
			<form:form commandName="pwdChgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.PasswordChange"/></div>

<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/sm/pwdChg/init" method="post" modelAttribute="pwdChgForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="index.label.sm.OldPwd"/></td>
				<td colspan="3">
					<form:password path="oldPwd" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.NewPwd"/></td>
				<td>
					<form:password path="newPwd" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.NewPwdAgain"/></td>
				<td>
					<form:password path="newPwdAgain" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="submitPassword()"><spring:message code="button.lable.Submit"/></button>
	    				<button type="button" class="btn btn-primary" onclick="close()"><spring:message code="button.lable.close"/></button>
	    			</div>
	    		</td>
    		</tr>
	    </table>											
	</form:form>
</div>
