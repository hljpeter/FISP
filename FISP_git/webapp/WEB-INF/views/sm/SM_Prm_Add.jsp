<script type="text/javascript">
	//window.name="curWindow";
	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("#confirmBtn").attr("disabled", "disabled");
			$("#MenuListBtn").attr("disabled", "disabled");
		}else{
			$("#addMoreBtn").attr("disabled", "disabled");
		}
	});
	
	//add button
	function add() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/SM_Prm_Add/Add";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			//form.target="curWindow";
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
		<spring:hasBindErrors name="SM_Prm_QryForm">
			<form:form commandName="SM_Prm_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.sysParam.add"/></div>

<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/SM_Prm_Qry/Add" method="post" modelAttribute="SM_Prm_QryForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td">
					<font color="red">*</font>
					<spring:message code="fisp.label.sysParam.group"/>
				</td>
				<td>
					<form:input id="paramGroup" path="sysParam.paramGroup" type="text" class=".input-small" maxlength="20"/>
				</td>
				<td class="label_td">
				<font color="red">*</font>
				<spring:message code="fisp.label.sysParam.code"/>
				</td>
				<td>
					<form:input id="paramCode" path="sysParam.paramCode" type="text" class=".input-small" maxlength="20"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.sysParam.val"/>
				</td>
				<td>
					<form:input id="paramVal" path="sysParam.paramVal" type="text" class=".input-small" maxlength="30"/>
				</td>
				<td class="label_td">
					<spring:message code="fisp.label.sysParam.name"/>
				</td>
				<td>
					<form:input id="paramName" path="sysParam.paramName" type="text" class=".input-small" maxlength="60"/>
				</td>
			</tr>
			<tr>
				<td class="label_td">
					<spring:message code="fisp.label.sysParam.desc"/>
				</td>
				<td colspan="3">
					<form:input id="paramDesc" path="sysParam.paramDesc" type="text" class="span6" maxlength="60"/>
				</td>
			</tr>
	    	<tr>
	    		<td colspan="4">
	    		</td>
	    	</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="add()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>