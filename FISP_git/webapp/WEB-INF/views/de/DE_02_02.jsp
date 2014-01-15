<script type="text/javascript">
	window.name="curWindow";

	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("#bankSelectBtn").attr("disabled", "disabled");
			$("#confirmBtn").attr("disabled", "disabled");
		}else{
			$("#addMoreBtn").attr("disabled", "disabled");
		}
	});

	//add button
	function modify() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/de02/02/modify";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
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
		<spring:hasBindErrors name="dtFieldCfgForm">
			<form:form commandName="dtFieldCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">任务管理 / 数据映射配置 / <spring:message code="index.label.DataExtractFieldmodify"/></div>

<!-- body -->

<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/de02/03/init" method="post"  traget="curWindow" modelAttribute="dtFieldCfgForm" class="form-horizontal">
		<form:input id="tId" type="hidden" path="dtFieldCfg.tId"/>
		<form:input id="tfId" type="hidden" path="dtFieldCfg.tfId"/>
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.seqNo"/></td>
				<td>
					<form:input id="seqNo" path="dtFieldCfg.seqNo" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
				<td class="label_td"><spring:message code="de.label.filterFlag"/></td>
				<td>
					<form:input id="filterFlag" path="dtFieldCfg.filterFlag" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.destField"/></td>
				<td>
					<form:input id="destField" path="dtFieldCfg.destField" type="text" class=".input-small" maxlength="20"/>
				</td>
				<td class="label_td"><spring:message code="de.label.srcField"/></td>
				<td>
					<form:input id="srcField" path="dtFieldCfg.srcField" type="text" class=".input-small" maxlength="20"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.srcFieldSql"/></td>
				<td colspan="3">
					<form:input id="srcFieldSql" path="dtFieldCfg.srcFieldSql" type="text" class=".input-small" maxlength="20"/>
				</td>
			<tr>
			
	    	<tr>
				<td class="label_td"><spring:message code="de.label.comments"/></td>
				<td colspan="3">
					<form:input id="comments" path="dtFieldCfg.comments" type="text" class=".input-small" maxlength="20"/>
				</td>
	    	</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="modify()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>