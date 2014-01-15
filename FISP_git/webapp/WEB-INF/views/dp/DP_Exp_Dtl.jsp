<script type="text/javascript">
	$(function() {
		$("#fieldTable").find("tr").bind('click', function() {
			var seqNo = $(this).find("td:eq(0)").text();
			var seqNoList = document.getElementsByName('seqNo');
			for ( var i = 0; i < seqNoList.length; i++) {
				var seqNo_tmp = seqNoList[i];
				if (seqNo == seqNo_tmp.value) {
					var divId = 'div_' + seqNo;
					document.getElementById(divId).style.display = 'block';
				} else {
					var divId = 'div_' + seqNo_tmp.value;
					document.getElementById(divId).style.display = 'none';
				}
			}

		});
	});
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
		<spring:hasBindErrors name="DP_Exp_DtlForm">
			<form:form commandName="DP_Exp_DtlForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.ExpDtl" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/DP_Exp_Dtl/SubmitCfg"
		modelAttribute="DP_Exp_DtlForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="if.label.PROJID" /></td>
				<td><form:select id="projId" path="dpExpCfg.projId"
						disabled="true">
						<form:options items="${DP_0021}" />
					</form:select></td>
				<td class="label_td"><spring:message code="if.label.BRANCHID" /></td>
				<td><input type="text" class="input-large" disabled="disabled"
					value="${orgName}" /></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.BATCHNO" /></td>
				<td><form:input id="batchNo" path="dpExpCfg.batchNo"
						type="text" class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="if.label.SEQNO" /></td>
				<td><form:input id="seqNo1" name="seqNo1" path="dpExpCfg.seqNo" type="text"
						class="input-large" disabled="true" /></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="fisp.label.common.tableName" /></td>
				<td><form:input id="tableName" path="dpExpCfg.tableName"
						type="text" class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="if.label.FILENAME" /></td>
				<td><form:input id="fileName" path="dpExpCfg.fileName"
						type="text" class="input-large" disabled="true" /></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.FILEPATH" /></td>
				<td><form:input id="filePath" path="dpExpCfg.filePath"
						type="text" class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="dp.lable.TableExpr" /></td>
				<td><form:input id="tableFilter" path="dpExpCfg.tableFilter"
						type="text" class="input-large" disabled="true" /></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.FILETITLE" /></td>
				<td><form:input path="dpExpCfg.fileTitle" type="text"
						class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message
						code="dp.lable.FieldTitleFlag" /></td>
				<td><form:input path="dpExpCfg.fieldTitleFlag" type="text"
						class="input-large" disabled="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.comments" /></td>
				<td colspan="3"><form:textarea path="dpExpCfg.comments"
						style="overflow-x: hidden; overflow-y: auto; width: 75%; height: 100px; resize: none;"
						disabled="true" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<form:form id="form1" method="post"
		action="${pageContext.request.contextPath}/DP_Mpp_Dtl/SubmitCfg"
		modelAttribute="DP_Exp_DtlForm" class="form-horizontal">
		<table class="tbl_search">
			<tr align="center">
				<td class="label_td"><spring:message
						code="fisp.label.common.tableName" /></td>
				<td><form:input path="dpExpCfg.tableName" type="text"
						class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="if.label.FILENAME" /></td>
				<td><form:input path="dpExpCfg.fileName" type="text"
						class="input-large" disabled="true" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="30px"><spring:message
							code="if.label.SEQNO" /></th>
					<th class="tbl_page_th" width="150px"><spring:message
							code="dp.lable.DestColName" /></th>
					<th class="tbl_page_th" width="250px"><spring:message
							code="dp.lable.ColExpress" /></th>
					<th class="tbl_page_th" width="250px"><spring:message
							code="if.label.COMMENTS" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body"
		style="min-height: 155px; height: 155px; overflow-x: hidden; overflow-y: auto;">
		<table id="fieldTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${DP_Exp_DtlForm.dpFileDtlVOs}"
					varStatus="i">
					<tr>
						<td class="tbl_page_td_left vtip" width="30px">${dto.seqNo}</td>
						<td class="tbl_page_td_left vtip" width="150px">${dto.fieldName}</td>
						<td class="tbl_page_td_right vtip" width="250px">${dto.fieldFormula}</td>
						<td class="tbl_page_td_right vtip" width="250px">${dto.comments}</td>
					</tr>
				</c:forEach>


			</tbody>
		</table>
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<c:forEach var="dto1" items="${DP_Exp_DtlForm.dpFileDtlVOs}"
		varStatus="i1">
		<div id="div_${dto1.seqNo}" style="display: none;">
			<input name="seqNo" value="${dto1.seqNo}" type="hidden" />
			<table class="tbl_search">
				<tr align="center">
					<td class="label_td"><spring:message code="if.label.SEQNO" /></td>
					<td><input
						name="DP_Exp_DtlForm.dpFileDtlVOs[${i1.index}].seqNo"
						value="${dto1.seqNo}" class="input-large" readonly="readonly" /></td>
					<td class="label_td"><spring:message code="ifd.label.FIELDNAME" /></td>
					<td><input
						name="DP_Exp_DtlForm.dpFileDtlVOs[${i1.index}].fieldName"
						value="${dto1.fieldName}" class="input-large" readonly="readonly" /></td>
				</tr>
				<tr align="center">
					<td class="label_td"><spring:message
							code="dp.lable.ColExpress" /></td>
					<td colspan="3" align="left"><input
						name="DP_Exp_DtlForm.dpFileDtlVOs[${i1.index}].fieldFormula"
						value="${dto1.fieldFormula}" class="input-xxlarge"
						disabled="disabled" readonly="readonly" /></td>
				</tr>
				<tr align="center">
					<td class="label_td"><spring:message code="if.label.COMMENTS" /></td>
					<td colspan="3" align="left"><form:textarea
							path="DP_Exp_DtlForm.dpFileDtlVOs[${i1.index}].comments"
							style="overflow-x: hidden; overflow-y: auto; width: 75%; height: 100px; resize: none;"
							disabled="true" /></td>
				</tr>
			</table>
		</div>
	</c:forEach>
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>
