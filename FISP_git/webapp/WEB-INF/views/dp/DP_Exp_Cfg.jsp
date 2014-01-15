<script type="text/javascript">
	$(function() {
		
		var fieldFormulaFlag_count = document.getElementsByName('fieldFormulaFlag');
		var seqNo_count = document.getElementsByName('seqNo');
		for ( var i = 0; i < fieldFormulaFlag_count.length; i++) {
			if('1' == fieldFormulaFlag_count[i].value){
				var fieldValId = 'fieldFormula_' + seqNo_count[i].value;
				var fieldCheckId = 'fieldFormula_' + seqNo_count[i].value +"_check";
				var fieldbuttonId = 'fieldFormula_' + seqNo_count[i].value +"_express";
				document.getElementById(fieldCheckId).checked=true;
				$("#" + fieldValId).removeAttr("readonly");
				$("#" + fieldbuttonId).attr("disabled","disabled");
			}
		}
		
		$("#fieldTable").find("tr").bind('click', function() {
			$("#selectSeq").val($(this).find("td:eq(0)").text());
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
	function expExpress() {
		var text_id_value = "#fieldFormula_" + $("#selectSeq").val();
		var text_id_key = "#fieldFormula_key_" + $("#selectSeq").val();
		var srcTable = $("#tableName").val();
		var fieldFormula = $(text_id_value).val();
		var fieldFormula_key = $(text_id_key).val();
		var cfgId = $("#expId").val();
		var branchId = $("#branchId").val();
		var rtnStr = window
				.showModalDialog(
						'${pageContext.request.contextPath}/Dp_Exp/Init?srcTable='
						+ srcTable + '&oldExpress=' + fieldFormula+'&oldExpress_key='+fieldFormula_key+'&cfgId='+cfgId+'&branchId='+branchId +'&dpType=3',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		if (typeof (rtnStr) == 'undefined') {
			rtnStr = window.ReturnValue;
		}
		if (rtnStr) {
			$(text_id_key).val(rtnStr.expressKey);
			$(text_id_value).val(rtnStr.expressVal);
		}
	};
	//input button
	function input_exp() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Exp_Cfg/SubmitCfgDtl";
		var msg = $("#confirmMsg1").val() + $("#confirmBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};
	
	function changeType(id) {
		var checkId = id + "_check";
		var valueId = id;
		var buttonId = id+"_express";
		var check_flag = document.getElementById(checkId).checked;
		if (null == check_flag || check_flag == false) {
			if ('' != $("#" + valueId).val()) {
				var msg = "使用表达式时，不可修改配置信息，原自定义配置信息将清空！";
				if (confirm(msg)) {
					$("#" + valueId).val("");
					$("#" + valueId+"_key").val("");
					$("#" + valueId).attr("readonly", "readonly");
					$("#" + buttonId).removeAttr("disabled");
				} else {
					document.getElementById(checkId).checked = true;
				}
			}else{
				$("#" + valueId).attr("readonly", "readonly");
				$("#" + buttonId).removeAttr("disabled");
			}
			
		} else {
			if ('' != $("#" + valueId).val()) {
				var msg = "自定义时，不可使用表达式，原配置信息将清空！";
				if (confirm(msg)) {
					$("#" + valueId).val("");
					$("#" + valueId+"_key").val("");
					$("#" + valueId).removeAttr("readonly");
					$("#" + buttonId).attr("disabled","disabled");
				} else {
					document.getElementById(checkId).checked = false;
				}
			}else{
				$("#" + valueId).removeAttr("readonly");
				$("#" + buttonId).attr("disabled","disabled");
			}
		}
	};
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
		<spring:hasBindErrors name="DP_Exp_CfgForm">
			<form:form commandName="DP_Exp_CfgForm">
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
<form:form id="form" method="post"
	action="${pageContext.request.contextPath}/DP_Mpp_Dtl/SubmitCfg"
	modelAttribute="DP_Exp_CfgForm" class="form-horizontal">
	<div class="row">

		<form:input id="expId" path="cfg_ExpId" type="hidden" />
		
		<input id="branchId"
			name="dpExpCfg.branchId" type="hidden"
			value="${DP_Exp_CfgForm.dpExpCfg.branchId}" />
		<input id="selectSeq" type="hidden" />
		<table class="tbl_search">
			<tr align="center">
				<td class="label_td"><spring:message
						code="fisp.label.common.tableName" /></td>
				<td><form:input id="tableName" path="dpExpCfg.tableName"
						type="text" class="input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="if.label.FILENAME" /></td>
				<td><form:input path="dpExpCfg.fileName" type="text"
						class="input-large" readonly="true" /></td>
			</tr>
		</table>

	</div>

	<div class="row">
		<div class="tbl_page_head">
			<table
				class="table table-striped table-bordered table-condensed tbl_page">
				<thead>
					<tr>
						<th class="tbl_page_th" width="30px" style="display: none;"><spring:message
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
					<c:forEach var="dto" items="${DP_Exp_CfgForm.dpFileDtlVOs}"
						varStatus="i">
						<tr>
							<td class="tbl_page_td_left vtip" width="30px"
								style="display: none;">${dto.seqNo}</td>
							<td class="tbl_page_td_left vtip" width="150px">${dto.fieldName}</td>
							<td class="tbl_page_td_left vtip" width="250px">${dto.fieldFormula}</td>
							<td class="tbl_page_td_right vtip" width="250px">${dto.comments}</td>
						</tr>
					</c:forEach>


				</tbody>
			</table>
		</div>
	</div>

	<div class="row" style="margin-bottom: 40px;">
		<c:forEach var="dto1" items="${DP_Exp_CfgForm.dpFileDtlVOs}"
			varStatus="i1">
			<div id="div_${dto1.seqNo}" style="display: none;">
				<input name="seqNo" value="${dto1.seqNo}" type="hidden" /> <input
					name="dpFileDtlVOs[${i1.index}].seqNo" value="${dto1.seqNo}"
					class="input-large" type="hidden" />
				<input name="fieldFormulaFlag" value="${dto1.fieldFormula_flag}" type="hidden" />
				<table class="tbl_search">

					<tr align="center">
						<td class="label_td"><font color="red">*</font>
						<spring:message code="ifd.label.FIELDNAME" /></td>
						<td colspan="3" align="left"><input name="dpFileDtlVOs[${i1.index}].fieldName"
							value="${dto1.fieldName}" class="input-xxlarge" readonly="readonly" style="height: 20px;"/></td>
					</tr>
					<tr align="center">
						<td class="label_td"><spring:message
								code="dp.lable.ColExpress" /></td>
						<td colspan="3" align="left">
						<input type="hidden"
							name="dpFileDtlVOs[${i1.index}].fieldFormula_key"
							id="fieldFormula_key_${dto1.seqNo}"
							value="${dto1.fieldFormula_key}" />
						
						<input
							name="dpFileDtlVOs[${i1.index}].fieldFormula"
							id="fieldFormula_${dto1.seqNo}" value="${dto1.fieldFormula}"
							class="input-xxlarge" readonly="readonly"/>
							<button type="button" class="btn btn-primary" id="fieldFormula_${dto1.seqNo}_express"
								onclick="expExpress()">
								<spring:message code="dp.lable.Express" />
							</button>
							<input type="checkbox"
							id="fieldFormula_${dto1.seqNo}_check" value="1"
							name="dpFileDtlVOs[${i1.index}].fieldFormula_flag"
							onclick="changeType('fieldFormula_${dto1.seqNo}')">
							</td>
					</tr>
					<tr align="center">
						<td class="label_td"><spring:message code="if.label.COMMENTS" /></td>
						<td colspan="3" align="left"><form:textarea
								path="dpFileDtlVOs[${i1.index}].comments"
								style="overflow-x: hidden; overflow-y: auto; width: 75%; height: 100px; resize: none;" /></td>
					</tr>
				</table>
			</div>
		</c:forEach>
	</div>
</form:form>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary"
			onclick="input_exp()"
			value="<spring:message code="button.lable.Submit"/>"> <input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>
