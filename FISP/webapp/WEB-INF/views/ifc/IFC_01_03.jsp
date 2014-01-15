<script type="text/javascript">
	window.name="curWindow";

	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("select").attr("disabled", "disabled");
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("input:button").attr("disabled", "disabled");
			$("#closeBtn").attr("disabled", false);

		}
	});
	
	//add button
	function add() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifc01/03/insert";
		form.target="curWindow";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
		form.submit();
		}
		else
		{
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


<div class="page_title">任务管理  / 映射配置  / 导入映射配置 / 新增</div>
<!-- body -->

<div class="row" style="margin-bottom: 40px;">
	<form:form  id="form"  method="post" action="${pageContext.request.contextPath}/ifc01/03/init" modelAttribute="impFileCfgForm" class="form-horizontal" >
		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="if.label.FILEID"/></td>
				<td>
					<form:input path="fileId"  type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="if.label.PROJID"/></td>
				<td>
					<form:input path="projId"  type="text" class=".input-small" />
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="if.label.BRANCHID"/></td>
				<td>
					<form:input path="branchId"  type="text" class=".input-small" />
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="if.label.BATCHNO"/></td>
				<td>
					<form:input path="batchNo"  type="text" class=".input-small"  onkeyup="numberFormat(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="if.label.SEQNO"/></td>
				<td>
					<form:input path="seqeNo" type="text" class=".input-small" onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="if.label.TABLENAME"/></td>
				<td>
					<form:input path="tableName"  type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.FILENAME"/></td>
				<td>
					<form:input path="fileName" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="if.label.FILEPATH"/></td>
				<td>
					<form:input path="filePath"  type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.MANDATORYFLAG"/></td>
				<td>
					<form:select id="mandatoryFlag" path="mandatoryFlag" class=".input-small">
						<form:options items="${IFC_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="if.label.FORMATTYPE"/></td>
				<td>
					<form:select id="formatType" path="formatType" class=".input-small">
						<form:options items="${IFC_0002}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.DELIMITER"/></td>
				<td>
					<form:input path="delimiter" type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="if.label.FIXEDLEN_CFG"/></td>
				<td>
					<form:input path="fixedLenCfg"  type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.UPDATEFLAG"/></td>
				<td>
					<form:select id="updateFlag" path="updateFlag" class=".input-small">
						<form:options items="${IFC_0003}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="if.label.SROWIGNR_NUM"/></td>
				<td>
					<form:input path="srowIgnrNum" type="text" class=".input-small" onkeyup="numberFormat(this);"  />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.EROWIGNR_NUM"/></td>
				<td>
					<form:input path="erowIgnrNum"  type="text" class=".input-small" onkeyup="numberFormat(this);"  />
				</td>
				<td class="label_td"><spring:message code="if.label.STARTCOLUMN" /></td>
				<td>
					<form:input path="startColumn"  type="text" class=".input-small" onkeyup="numberFormat(this);"  />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.ENDCOLUMN"/></td>
				<td>
					<form:input path="endColumn"  type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><spring:message code="if.label.SHEETNUM"/></td>
				<td>
					<form:input path="sheetNum"  type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.COMMITCOUNT"/></td>
				<td>
					<form:input path="commitCount" type="text" class=".input-small"  onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><spring:message code="if.label.COMMENTS"/></td>
				<td>
					<form:input path="comments"  type="text" class=".input-small" />
				</td>
			</tr>
	   	</table>
	</form:form>
</div>

<div class="row" >
  <div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="add()" value="<spring:message code="button.lable.Add"/>">
		<input id="closeBtn" type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
  </div>
</div>


