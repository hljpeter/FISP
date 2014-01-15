<script type="text/javascript">
	window.name="curWindow";

	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("#rcptAddr").prop("readonly", true);
			$("#comments").prop("readonly", true);
			$("#alertType").prop("readonly", true);
			$("#noticeMthd").prop("readonly", true);
			$("#bankSelectBtn").attr("disabled", "disabled");
			$("#confirmBtn").attr("disabled", "disabled");
		}else{
			$("#addMoreBtn").attr("disabled", "disabled");
		}
	});

	//add button
	function add() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/al01/02/add";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
	}

</script>
<!-- fisp information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sysAlertRcptForm">
			<form:form commandName="sysAlertRcptForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">任务管理 / 警报配置 / <spring:message code="index.lable.SysAlertPrctAdd"/></div>

<!-- body -->

<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/al01/02/init" method="post"  traget="curWindow" modelAttribute="sysAlertRcptForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="al.label.projId"/></td>
				<td>
					<form:input path="projId" id="projId" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="al.label.branchId"/></td>
				<td>
					<form:input path="branchId" id="branchId" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="al.label.alertType"/></td>
				<td>
					<form:select id="alertType" path="alertType" class=".input-small">
						<form:options items="${AL_0001}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="al.label.noticeMthd"/></td>
				<td>                               
					<form:select id="noticeMthd" path="noticeMthd" class=".input-small">
						<form:options items="${AL_0002}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="al.label.rcptAddr"/></td>
				<td >
					<form:textarea path="rcptAddr" id="rcptAddr" cols="10" rows="3" class=".input-small"/>
				</td>
				<td class="label_td"><spring:message code="al.label.comments"/></td>
				<td colspan="3">
					<form:textarea path="sysAlertRcpt.comments" id="comments" cols="10" rows="3" class=".input-small"/>
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