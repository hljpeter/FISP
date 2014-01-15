<script type="text/javascript">
	window.name="curWindow";
	function validSearchForm(){
		var obj={};
		obj.valid=true;

		$(".alert-error").empty();
		obj.procType=$("#procType").val();
		obj.procCfg=$("#procCfg").val();
		if(obj.procType!='3'){
			if($.trim(obj.procCfg)==''){
				document.getElementById("errorMsg").innerHTML='<spring:message code="e.de.2012"/><br/>';
				obj.valid=false;
			}
		}
		return obj;
	}
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
	function add() {
		var dataObj=validSearchForm();
		if(dataObj.valid){
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/de01/02/add";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
		}
		else{
			document.getElementById("errorMsg").style.display="block";
			showMsg("id_showMsg");}
		
	}

</script>

<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errMsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dtTableCfgForm">
			<form:form commandName="dtTableCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<br />
</div>
<!-- title -->
<div class="page_title">任务管理 / 数据映射配置 / <spring:message code="index.lable.DataExtractAdd"/></div>

<!-- body -->

<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/de01/02/init" method="post"  traget="curWindow" modelAttribute="dtTableCfgForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.projId"/></td>
				<td>
					<form:input id="projId" path="projId" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.branchId"/></td>
				<td>
					<form:input id="branchId" path="branchId" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.batchNo"/></td>
				<td>
					<form:input id="batchNo" path="batchNo" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.seqNo"/></td>
				<td>
					<form:input id="seqNo" path="seqNo" type="text" class=".input-small" maxlength="20" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><font color="red">*</font><spring:message code="de.label.destTable"/></td>
				<td>
					<form:input id="destTable" path="destTable" type="text" class=".input-small" maxlength="20"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.srcTable"/></td>
				<td>
					<form:input id="srcTable" path="srcTable" type="text" class=".input-small" maxlength="20"/>
				</td>
			<tr>
			
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="de.label.procType"/></td>
				<td>
					<form:select id="procType" path="procType" class=".input-small">
						<form:options items="${DE_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="de.label.procCfg"/></td>
				<td>
					<form:textarea id="procCfg" path="dtTableCfg.procCfg" type="text" rows="3" class=".input-small"/>
				</td>
	    	</tr>
	    	<tr>
				<td class="label_td"><spring:message code="de.label.comments"/></td>
				<td colspan="3">
					<form:textarea id="comments" path="dtTableCfg.comments" type="text" rows="3" class=".input-small" style="width: 666px;"/>
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