<script type="text/javascript">
$(window).load(function() {
	// init page
	var msg = "${successmsg}";
	if (msg && msg != "") {
		$("input:not(:button,:hidden)").prop("readonly", true);
		$("select").prop("disabled", "disabled");
		$("#submitcfg").attr("disabled", "disabled");
		$("textarea").prop("readonly", true);
	} else {
		$("input:not(:button,:hidden)").prop("readonly", false);
		$("#fileName").prop("readonly", true);
		$("#tableName").prop("readonly", true);
		$("select").attr("disabled", false);
		//$("#submitcfg").attr("disabled", "false");
	}
	
	$("#submitcfg").click(function() {
		var msg=$("#confirmMsg1").val()+$("#submitcfg").html()+$("#confirmMsg2").val();
		if (confirm(msg)){
			$("#form").submit();
		}else{
			return false;
		}
	});
});
function queryOrg() {
	showSelOrg([ {
		"query_branchId" : "param1"
	} ]);
};
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dpImpUptForm">
			<form:form commandName="dpImpUptForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
				
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.imp.dpImpUpt"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/DP_Imp_Upt/SubmitCfg" method="post" modelAttribute="dpImpUptForm" class="form-horizontal">
		<form:hidden path="dpImpCfg.impId"/>
		<form:hidden path="dpImpCfg.updateTime"/>
		<form:hidden path="dpImpCfg.updateUser"/>
		<table class="tbl_search">
	    	<tr>
				<td class="label_td" width="20%"><font color="red">*</font><spring:message code="fisp.label.common.projId"/></td>
				<td width="30%">
					<form:select id="projId" path="dpImpCfg.projId">
						<form:options items="${DP_0021}" />
					</form:select>
				</td>
				
				<td class="label_td" width="20%"><font color="red">*</font><spring:message code="fisp.label.common.branchId"/></td>
				<td width="30%">
					<form:input id="query_branchId" path="dpImpCfg.branchId" type="text" class=".input-small" maxLength="20" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryOrg()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.batchNo"/></td>
				<td><form:input path="dpImpCfg.batchNo" type="text" class=".input-small" onkeyup="numberFormat(this);" maxLength="10" /></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.seqNo"/></td>
				<td><form:input path="dpImpCfg.seqNo" type="text" class=".input-small" onkeyup="numberFormat(this);" maxLength="3" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.fileName"/></td>
				<td>
					<form:input path="dpImpCfg.fileName" id="fileName" type="text" class=".input-small" readonly="true" maxLength="128" />
					<button type="button" class="btn btn-small" disabled><spring:message code="button.label.Search"/></button>
				</td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.tableName"/></td>
				<td>
					<form:input path="dpImpCfg.tableName" id="tableName" type="text" class=".input-small" readonly="true" maxLength="128" />
					<button type="button" class="btn btn-small" disabled><spring:message code="button.label.Search"/></button>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.filePath"/></td>
				<td><form:input path="dpImpCfg.filePath" type="text" maxLength="128" class=".input-small"/></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.mandFlag"/></td>
				<td>
					<form:select path="dpImpCfg.mandFlag">
						<form:option value=""></form:option>
						<form:options items="${DP_FILE_MANDFLAG }"/>
					</form:select>
				</td>
			</tr>			
			<tr>
				<td class="label_td"><spring:message code="fisp.label.common.comments"/></td>
				<td colspan="3"><form:textarea path="dpImpCfg.comments" class="input-xxlarge" rows="4" maxLength="255" /></td>
			</tr>
	    </table>											
	</form:form>
	<span style="font-size: 12px;"><spring:message code="fisp.notice.common.required"/></span>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="submitcfg" class="btn btn-primary"><spring:message code="button.lable.Submit"/></button>
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>