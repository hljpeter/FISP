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
	form.action = "${pageContext.request.contextPath}/efc01/04/update";
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
		<spring:hasBindErrors name="expFileCfgForm">
			<form:form commandName="expFileCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title">任务管理  / 映射配置  / 报表映射配置 / 修改</div>
<!-- body -->
<form id="form">
<div class="row form-horizontal" style="margin-bottom: 40px;">

	  
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ef.label.FILEID"/></td>
				<td>
					<form:input id="fileId" path="expFileCfg.fileId" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ef.label.PROJID"/></td>
				<td>
					<form:input id="projId" path="expFileCfg.projId" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><spring:message code="ef.label.BRANCHID"/></td>
				<td>
					<form:input id="branchId" path="expFileCfg.branchId" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ef.label.BATCHNO"/></td>
				<td>
					<form:input id="batchNo" path="expFileCfg.batchNo" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.SEQNO"/></td>
				<td>
					<form:input id="seqNo" path="expFileCfg.seqNo" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ef.label.TABLENAME"/></td>
				<td>
					<form:input id="tableName" path="expFileCfg.tableName" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.FILENAME"/></td>
				<td>
					<form:input id="fileName" path="expFileCfg.fileName" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="ef.label.FILEPATH"/></td>
				<td>
					<form:input id="filePath" path="expFileCfg.filePath" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.FORMATTYPE"/></td>
				<td>
					<form:select id="formatType" path="expFileCfg.formatType" class=".input-small">
						<form:options items="${EFC_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ef.label.DELIMITER"/></td>
				<td>
					<form:input id="delimiter" path="expFileCfg.delimiter" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.FIXEDLEN_CFG"/></td>
				<td>
					<form:input id="fixedLenCfg" path="expFileCfg.fixedLenCfg" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="ef.label.FILETITLE"/></td>
				<td>
					<form:input id="fileTitle" path="expFileCfg.fileTitle" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.ROWTITLE_FLAG"/></td>
				<td>
					<form:select id="rowTitleFlag" path="expFileCfg.rowTitleFlag" class=".input-small">
						<form:options items="${EFC_0002}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ef.label.SHEETNUM"/></td>
				<td>
					<form:input id="sheetNum" path="expFileCfg.sheetNum" type="text" class=".input-small"  onkeyup="numberFormat(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.COMMENTS"/></td>
				<td>
					<form:input id="comments" path="expFileCfg.comments" type="text" class=".input-small" />
				</td>
			</tr>
	   	</table>

</div>
</form>

<div class="row" >
 <div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
	<input type="button" class="btn btn-primary" value="<spring:message code="button.lable.Update"/>" id="updateBtn" onclick="update()"/>
	<input type="button" class="btn btn-primary" value="<spring:message code="button.lable.close"/>" id="closeBtn" onclick="javascript:window.close();"/>
</div>
</div>

