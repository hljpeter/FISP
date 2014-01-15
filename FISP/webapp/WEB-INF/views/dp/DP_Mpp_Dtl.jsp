<script type="text/javascript">
	$(function() {
		if ($("#procType").val() == '3') {
			$("#express").removeAttr("disabled");
			$("#srcTable").removeAttr("disabled");
			$("#destTable").removeAttr("disabled");
			document.getElementById('tableTR').style.display = 'table-row';
		} else {
			$("#express").attr("disabled", "disabled");
			$("#srcTable").attr("disabled", "disabled");
			$("#destTable").attr("disabled", "disabled");
			document.getElementById('tableTR').style.display = 'none';
			document.getElementById("tableDiv").style.display = 'none';
			document.getElementById("colDiv").style.display = 'none';
			document.getElementById("colDiv1").style.display = 'none';
		}
		$("#destTable").find("tr").bind('click', function() {
			var destColName = $(this).find("td:eq(0)").text();
			var destColNameList = document.getElementsByName('destColName');
			for ( var i = 0; i < destColNameList.length; i++) {
				var destColNam_tmp = destColNameList[i];
				if (destColName == destColNam_tmp.value) {
					var divId = 'div_' + destColName;
					document.getElementById(divId).style.display = 'block';
				} else {
					var divId = 'div_' + destColNam_tmp.value;
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
		<spring:hasBindErrors name="DP_Mpp_DtlForm">
			<form:form commandName="DP_Mpp_DtlForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.MapDtl" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/DP_Mpp_Dtl/SubmitCfg"
		modelAttribute="DP_Mpp_DtlForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="if.label.PROJID" /></td>
				<td><form:select id="projId" path="dpMppCfg.projId"
						disabled="true">
						<form:options items="${DP_0021}" />
					</form:select></td>
				<td class="label_td"><spring:message code="if.label.BRANCHID" /></td>
				<td><input type="text" class="input-large" disabled="disabled"
					value="${orgName}" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.BATCHNO" /></td>
				<td><form:input id="batchNo" path="dpMppCfg.batchNo"
						type="text" class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="if.label.SEQNO" /></td>
				<td><form:input id="seqNo" path="dpMppCfg.seqNo" type="text"
						class="input-large" disabled="true" /></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.procType" /></td>
				<td><form:select id="procType" path="dpMppCfg.procType"
						disabled="true">
						<form:options items="${DP_0020}" />
					</form:select></td>
				<td class="label_td"><spring:message code="dp.lable.MppName" /></td>
				<td><form:input id="mppName" path="dpMppCfg.mppName"
						type="text" class="input-large" disabled="true" /></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="dp.lable.DupProcType" /></td>
				<td><form:select id="dupProcType" path="dpMppCfg.dupProcType"
						disabled="true">
						<form:options items="${DP_0022}" />
					</form:select></td>
				<td class="label_td"><spring:message
						code="dp.lable.ErrProcType" /></td>
				<td><form:select id="errProcType" path="dpMppCfg.errProcType"
						disabled="true">
						<form:options items="${DP_0023}" />
					</form:select></td>
			</tr>
			<tr id="tableTR">
				<td class="label_td"><spring:message code="de.label.destTable" /></td>
				<td><form:input path="dpMppCfg.destTable" type="text"
						class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="de.label.srcTable" /></td>
				<td><form:input path="dpMppCfg.srcTable" type="text"
						class="input-large" disabled="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.procCfg" /></td>
				<td colspan="3"><form:textarea id="procCfg" rows="2"
						path="dpMppCfg.procCfg" style="overflow-x: hidden; overflow-y: auto; width: 75%; resize: none;" disabled="true"/>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.comments" /></td>
				<td colspan="3"><form:textarea path="dpMppCfg.comments"
						style="overflow-x: hidden; overflow-y: auto; width: 75%; height: 100px; resize: none;"
						disabled="true" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row" id="tableDiv">
	<form:form id="form1" method="post"
		action="${pageContext.request.contextPath}/DP_Mpp_Dtl/SubmitCfg"
		modelAttribute="DP_Mpp_DtlForm" class="form-horizontal">
		<table class="tbl_search">
			<tr align="center">
				<td class="label_td"><spring:message code="de.label.srcTable" /></td>
				<td><form:input path="dpMppCfg.srcTable" type="text"
						class="input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="de.label.destTable" /></td>
				<td><form:input path="dpMppCfg.destTable" type="text"
						class="input-large" readonly="true" /></td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row" id="colDiv">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
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
		<table id="destTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${DP_Mpp_DtlForm.dpTableDtlVOs}"
					varStatus="i">
					<tr>
						<td class="tbl_page_td_left vtip" width="150px">${dto.destColName}</td>
						<td class="tbl_page_td_right vtip" width="250px">${dto.colFormula}</td>
						<td class="tbl_page_td_right vtip" width="250px">${dto.comments}</td>
					</tr>
				</c:forEach>


			</tbody>
		</table>
	</div>
</div>

<div class="row" style="margin-bottom: 40px;" id="colDiv1">
	<c:forEach var="dto1" items="${DP_Mpp_DtlForm.dpTableDtlVOs}"
		varStatus="i1">
		<div id="div_${dto1.destColName}" style="display: none;">
			<input name="destColName" value="${dto1.destColName}" type="hidden" />
			<table class="tbl_search">
				<tr align="center">
					<td class="label_td"><spring:message
							code="dp.lable.DestColName" /></td>
					<td colspan="3" align="left"><input
						name="DP_Mpp_DtlForm.dpTableDtlVOs[${i1.index}].destColName"
						value="${dto1.destColName}" class="input-xxlarge"
						readonly="readonly" /></td>
				</tr>
				<tr align="center">
					<td class="label_td"><spring:message
							code="dp.lable.ColExpress" /></td>
					<td colspan="3" align="left"><input
						name="DP_Mpp_DtlForm.dpTableDtlVOs[${i1.index}].colFormula"
						value="${dto1.colFormula}" class="input-xxlarge"
						disabled="disabled" readonly="readonly" /></td>
				</tr>
				<tr align="center">
					<td class="label_td"><spring:message code="dp.lable.UkFlag" /></td>
					<td align="left"><form:select
							path="DP_Mpp_DtlForm.dpTableDtlVOs[${i1.index}].ukFlag"
							disabled="true">
							<form:options items="${DP_0024}" />
						</form:select></td>
					<td class="label_td"><spring:message
							code="dp.lable.DupProcType" /></td>
					<td><form:select
							path="DP_Mpp_DtlForm.dpTableDtlVOs[${i1.index}].dupProcType"
							disabled="true">
							<form:options items="${DP_0025}" />
						</form:select></td>
				</tr>
				<tr align="center">
					<td class="label_td"><spring:message code="if.label.COMMENTS" /></td>
					<td colspan="3" align="left"><form:textarea
							path="DP_Mpp_DtlForm.dpTableDtlVOs[${i1.index}].comments"
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
