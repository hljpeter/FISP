<script type="text/javascript">
<!--
	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("#authBtn").attr("disabled", "disabled");
			$("#rejectBtn").attr("disabled", "disabled");
		}
	});
	//authroize button
	function auth() {
		var form = document.getElementById("form_04DetailSearch");
		form.action = "${pageContext.request.contextPath}/nsm01/06/auth";
		var msg=$("#confirmMsg1").val()+$("#authBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	//reject button
	function reject() {
		var form = document.getElementById("form_04DetailSearch");
		form.action = "${pageContext.request.contextPath}/nsm01/06/reject";
		var msg=$("#confirmMsg1").val()+$("#rejectBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
//-->
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="orgInfTmpForm">
			<form:form commandName="orgInfTmpForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.OrganizationAuthorizedDetailSearch"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form_04DetailSearch" action="${pageContext.request.contextPath}/nsm01/05/detailSearch_04" method="post" modelAttribute="orgInfTmpForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="3">
					<form:input id="opttype" path="orgInfTmp.opttype" type="hidden"/>
					<form:input id="createtime" path="orgInfTmp.createtime" type="hidden"/>
			</td></tr>
			<tr>
				<td width="30%" class="label_td"><spring:message code="index.label.sm.Id"/></td>
				<td width="20%" >
					<form:input path="orgInfTmp.id" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td width="15%" class="label_td"><spring:message code="index.label.sm.OperationType"/></td>
				<td width="35%" >
					<t:codeValue items="${CL_0002}" key="${orgInfTmpForm.orgInfTmp.opttype}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
				<td width="30%" class="label_td"><spring:message code="index.label.sm.OrganizationId"/></td>
				<td colspan="3">
					<form:input id="orgid" path="orgInfTmp.orgid" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.OrganizationName"/></td>
				<td colspan="3">
					<form:input id="orgname" path="orgInfTmp.orgname" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			
			
			<!-- close by wy 2013-12-05
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.BankId"/></td>
				<td colspan="3">
					<form:input id="bankid" path="orgInfTmp.bankid" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.BankName"/></td>
				<td colspan="3">
					<form:input path="orgInfTmp.bankname" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			-->
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Creater"/></td>
				<td>
					<form:input path="orgInfTmp.creater" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CreateTime"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${orgInfTmpForm.orgInfTmp.createtime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="authBtn" class="btn btn-primary" onclick="auth();" value="<spring:message code="button.lable.Auth"/>">
		<input type="button" id="rejectBtn" class="btn btn-primary" onclick="reject();" value="<spring:message code="button.label.Reject"/>">
 		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>