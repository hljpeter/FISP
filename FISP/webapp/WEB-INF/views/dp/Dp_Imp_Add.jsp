<script type="text/javascript">
$(window).load(function() {
	// init page
	var msg = "${successmsg}";
	if (msg && msg != "") {
		$("input:not(:button,:hidden)").prop("readonly", true);
		$("select").prop("disabled", true);
		$("#submitcfg").attr("disabled", true);
		$("#filenamesearch").attr("disabled", true);
		$("#tablenamesearch").attr("disabled", true);
		$("textarea").prop("readonly", true);
	} else {
		$("input:not(:button,:hidden)").prop("readonly", false);
		$("select").attr("disabled", false);
		//$("#submitcfg").attr("disabled", "true");
	}
	
	$("#submitcfg").click(function() {
		var msg=$("#confirmMsg1").val()+$("#submitcfg").html()+$("#confirmMsg2").val();
		if (confirm(msg)){
			$("#form").submit();
		}else{
			return false;
		}
	});
	
	$("#filenamesearch").click(function() {showSelFile('1', [{"fileName":"param2"}]); });
	$("#fileName").blur(function() {
			$.ajax({ 
				type: "GET", 
				url: "${pageContext.request.contextPath}/filenamesync/search", 
				data: "fileName="+$("#fileName").val(),
				success: function(data) {
					alert("data" + data);
					alert(eval(data));
				}
			});
		});	
	$("#tablenamesearch").click(function() {showSelTable( [{"tableName":"param2"}] ); });
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
		<spring:hasBindErrors name="dpImpAddForm">
			<form:form commandName="dpImpAddForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
				
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.imp.dpImpAdd"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/DP_Imp_Add/SubmitCfg" method="post" modelAttribute="dpImpAddForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr><td colspan="4">
	    		<p class="text-info"><spring:message code="fisp.titls.dp.imp.dpImpAdd.cfg"/></p>
	    	</td></tr>
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
				<td><form:input path="dpImpCfg.batchNo" type="text" class=".input-small" maxLength="10" onkeyup="numberFormat(this);"/></td>
				
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.seqNo"/></td>
				<td><form:input path="dpImpCfg.seqNo" type="text" class=".input-small" maxLength="3" onkeyup="numberFormat(this);"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.fileName"/></td>
				<td>
					<form:input path="dpImpCfg.fileName" id="fileName" type="text" maxLength="128" class=".input-small"/>
					<button type="button" class="btn btn-small" id="filenamesearch"><spring:message code="button.label.Search"/></button>
				</td>

				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.tableName"/></td>
				<td>
					<form:input path="dpImpCfg.tableName" id="tableName" type="text" maxLength="128" class=".input-small"/>
					<button type="button" class="btn btn-small" id="tablenamesearch"><spring:message code="button.label.Search"/></button>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.filePath"/></td>
				<td><form:input path="dpImpCfg.filePath" type="text" class=".input-small" maxLength="128"/></td>
				
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