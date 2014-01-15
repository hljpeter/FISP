<script type="text/javascript">

window.name="curWindow";

$(function() {
	var msg = "${successmsg}";
	if(msg && msg!=""){
		$("select").attr("disabled", "disabled");
		$("input:not(:button,:hidden)").prop("readonly", true);
		$("input:button").attr("disabled", "disabled");
		$("#closeBtn").attr("disabled", false);
	}
});

function update() {
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/ifc01/04/update";
	var msg=$("#confirmMsg1").val()+$("#updateBtn").val()+$("#confirmMsg2").val();
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
		<spring:hasBindErrors name="impFileCfgForm">
			<form:form commandName="impFileCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title">任务管理  / 映射配置  / 导入映射配置 / 修改</div>
<!-- body -->
<form id="form">
<div class="row form-horizontal" style="margin-bottom: 40px;">
	   	
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="if.label.FILEID"/></td>
				<td>
					<form:input id="fileId" path="impFileCfg.fileId" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.PROJID"/></td>
				<td>
					<form:input id="projId" path="impFileCfg.projId" type="text" class=".input-small" readonly="true" />
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><spring:message code="if.label.BRANCHID"/></td>
				<td>
					<form:input id="branchId" path="impFileCfg.branchId" type="text" class=".input-small" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="if.label.BATCHNO"/></td>
				<td>
					<form:input id="batchNo" path="impFileCfg.batchNo" type="text" class=".input-small" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.SEQNO"/></td>
				<td>
					<form:input id="seqeNo" path="impFileCfg.seqeNo" type="text" class=".input-small" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="if.label.TABLENAME"/></td>
				<td>
					<form:input id="tableName" path="impFileCfg.tableName" type="text" class=".input-small"  readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.FILENAME"/></td>
				<td>
					<form:input id="fileName" path="impFileCfg.fileName" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="if.label.FILEPATH"/></td>
				<td>
					<form:input id="filePath" path="impFileCfg.filePath" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.MANDATORYFLAG"/></td>
				<td>
					<form:select id="mandatoryFlag" path="impFileCfg.mandatoryFlag" class=".input-small">
						<form:options items="${IFC_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="if.label.FORMATTYPE"/></td>
				<td>
				    <form:select id="formatType" path="impFileCfg.formatType" class=".input-small">
						<form:options items="${IFC_0002}" />
					</form:select>
	
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.DELIMITER"/></td>
				<td>
					<form:input id="delimiter" path="impFileCfg.delimiter" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="if.label.FIXEDLEN_CFG"/></td>
				<td>
					<form:input id="fixedLenCfg" path="impFileCfg.fixedLenCfg" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.UPDATEFLAG"/></td>
				<td>
					 <form:select id="updateFlag" path="impFileCfg.updateFlag" class=".input-small">
						<form:options items="${IFC_0003}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="if.label.SROWIGNR_NUM"/></td>
				<td>
					<form:input id="srowIgnrNum" path="impFileCfg.srowIgnrNum" type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.EROWIGNR_NUM"/></td>
				<td>
					<form:input id="erowIgnrNum" path="impFileCfg.erowIgnrNum" type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><spring:message code="if.label.STARTCOLUMN"/></td>
				<td>
					<form:input id="startColumn" path="impFileCfg.startColumn" type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.ENDCOLUMN"/></td>
				<td>
					<form:input id="endColumn" path="impFileCfg.endColumn" type="text" class=".input-small" onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><spring:message code="if.label.SHEETNUM"/></td>
				<td>
					<form:input id="sheetNum" path="impFileCfg.sheetNum" type="text" class=".input-small" onkeyup="numberFormat(this);"  />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.COMMITCOUNT"/></td>
				<td>
					<form:input id="commitCount" path="impFileCfg.commitCount" type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><spring:message code="if.label.COMMENTS"/></td>
				<td>
					<form:input id="comments" path="impFileCfg.comments" type="text" class=".input-small" />
				</td>
			</tr>
	   	</table>

</div>

<div class="row" >
 <div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
	<input type="button" class="btn btn-primary" value="<spring:message code="button.label.Update"/>" id="updateBtn" onclick="update()"/>
	<input type="button" class="btn btn-primary" value="<spring:message code="button.lable.close"/>" id="closeBtn" onclick="javascript:window.close();"/>
</div>
</div>
</form>



