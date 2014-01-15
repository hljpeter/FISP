<script type="text/javascript">
<!--
	//window.name="curWindow";
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
		form.action = "${pageContext.request.contextPath}/sm02/06/auth";
		var msg=$("#confirmMsg1").val()+$("#authBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			//form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
	}
	//reject button
	function reject() {
		var form = document.getElementById("form_04DetailSearch");
		form.action = "${pageContext.request.contextPath}/sm02/06/reject";
		var msg=$("#confirmMsg1").val()+$("#rejectBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			//form.target="curWindow";
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
		<spring:hasBindErrors name="roleInfTmpForm">
			<form:form commandName="roleInfTmpForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.RoleInfoAuthorizedDetailSearch"/>
</div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form_04DetailSearch" action="${pageContext.request.contextPath}/sm02/05/detailSearch_04" method="post" modelAttribute="roleInfTmpForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="opttype" path="roleInfTmp.opttype" type="hidden"/>
				<form:input id="infruseflag" path="roleInfTmp.infruseflag" type="hidden"/>
				<form:input id="createorg" path="roleInfTmp.createorg" type="hidden"/>
				<form:input id="meunlist" path="meunlist" type="hidden"/>
				<form:input id="createtime" path="roleInfTmp.createtime" type="hidden"/>
			</td></tr>
			<tr>
				<td width="40%" class="label_td"><spring:message code="index.label.sm.Id"/>
				</td>
				<td width="20%">
					<form:input path="roleInfTmp.id" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td width="15%" class="label_td"><spring:message code="index.label.sm.OperationType"/>
				</td>
				<td width="25%">
					<t:codeValue items="${CL_0002}" key="${roleInfTmpForm.roleInfTmp.opttype}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleId"/>
				</td>
				<td colspan="3">
					<form:input id="roleid" path="roleInfTmp.roleid" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleName"/>
				</td>
				<td colspan="3">
					<form:input id="rolename" path="roleInfTmp.rolename" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.RoleDesc"/>
				</td>
				<td colspan="3">
					<form:input id="roledesc" path="roleInfTmp.roledesc" type="text" class="span8" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.CreateOrgName"/>
				</td>
				<td colspan="3">
					<t:codeValue items="${SM_0002 }" key="${roleInfTmpForm.roleInfTmp.createorg}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.InfrUseFlag"/>
				</td>
				<td colspan="3">
					<form:checkbox path="roleInfTmp.infruseflag" value="01" disabled="true" cssStyle="margin-right:10px;"/><spring:message code="index.lable.CanUse"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.MenuList"/>
				</td>
				<td colspan="3">
					<input type="hidden" id="editable" value="false"/>
					<jsp:include page="SM_00_01.jsp?menuId=${form.meunlist }&editable=${form.editable }"></jsp:include>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Creater"/>
				</td>
				<td>
					<form:input path="roleInfTmp.creater" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CreateTime"/>
				</td>
				<td>
					<t:dateTimeFormat type="text" value="${roleInfTmpForm.roleInfTmp.createtime }" readonly="true" cssClass=".input-small" format="datetime"/>
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