<script type="text/javascript">
	
	//add button
	function unlock() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/sm03/08/unlock";
		var msg=$("#confirmMsg1").val()+$("#unlockBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	function password_Reset() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/sm03/08/passwordReset";
		var msg=$("#confirmMsg1").val()+$("#pwdResetBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	function unfreeze() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/sm03/08/unfreeze";
		var msg=$("#confirmMsg1").val()+$("#unfreezeBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="userInfForm">
			<form:form commandName="userInfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<div id="id_showMsg_manual" style="display: none"> 
	<br /><br />
	<div class="alert alert-info"><spring:message code="index.label.sm.NewPwd"/>
		<div id="id_result_manual">
			${userInfForm.newPwd }
		</div>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.UserStatusProcess"/></div>

<!-- body -->

<div class="row" style="margin-top: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/sm03/08/init" method="post"  modelAttribute="userInfForm" class="form-horizontal">
		<table style="width: 500px;border: 1px solid #ddd;">
	    	<tr>
				<td width="200px" class="label_td"><spring:message code="index.label.sm.UserId"/></td>
				<td>
					<form:input id="userid" path="userid" type="text" class=".input-small" maxlength="20" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
			</tr>
			<tr height="30px">
				<td colspan="2">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="unlockBtn" type="button" class="btn btn-primary" onclick="unlock()" value="<spring:message code="fisp.button.label.file.unlock"/>"/>
					<input id="unfreezeBtn" type="button" class="btn btn-primary" onclick="unfreeze()" value="<spring:message code="fisp.button.label.file.unfreeze"/>"/>
					<input id="pwdResetBtn" type="button" class="btn btn-primary" onclick="password_Reset()" value="<spring:message code="fisp.button.label.file.pwdReset"/>"/>
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
