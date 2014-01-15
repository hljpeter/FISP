<script type="text/javascript">
	$(function() {
		var colFormulaFlag_count = document.getElementsByName('colFormulaFlag');
		var destColName_count = document.getElementsByName('destColName');
		for ( var i = 0; i < colFormulaFlag_count.length; i++) {
			if('1' == colFormulaFlag_count[i].value){
				var colValId = 'colFormula_' + destColName_count[i].value;
				var colCheckId = 'colFormula_' + destColName_count[i].value +"_check";
				var colbuttonId = 'colFormula_' + destColName_count[i].value +"_express";
				document.getElementById(colCheckId).checked=true;
				$("#" + colValId).removeAttr("readonly");
				$("#" + colbuttonId).attr("disabled","disabled");
			}
		}

		$("#destTable").find("tr").bind('click', function() {
			$("#selectCol").val($(this).find("td:eq(0)").text());
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

	//input button
	function input() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Mpp_Cfg/SubmitCfgDtl";
		var msg = $("#confirmMsg1").val() + $("#confirmBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};
	function mppExpress() {
		var text_id_value = "#colFormula_" + $("#selectCol").val();
		var text_id_key = "#colFormula_key_" + $("#selectCol").val();
		var srcTable = $("#srcTable").val();
		var procCfg = $(text_id_value).val();
		var procCfg_key = $(text_id_key).val();
		var cfgId = $("#mppId").val();
		var branchId = $("#branchId").val();
		var rtnStr = window
				.showModalDialog(
						'${pageContext.request.contextPath}/Dp_Exp/Init?srcTable='
								+ srcTable + '&oldExpress=' + procCfg +'&oldExpress_key='+procCfg_key
								+ '&cfgId=' + cfgId + '&branchId=' + branchId
								+ '&dpType=2',
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
		<t:messagePanel />
		<spring:hasBindErrors name="DP_Mpp_CfgForm">
			<form:form commandName="DP_Mpp_CfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.MapCfg" />
</div>

<form:form id="form" method="post"
	action="${pageContext.request.contextPath}/DP_Mpp_Add/SubmitCfg"
	modelAttribute="DP_Mpp_CfgForm" class="form-horizontal">
	<div class="row">
		<input id="mppId" name="dpMppCfg.mppId" type="hidden"
			value="${DP_Mpp_CfgForm.dpMppCfg.mppId}" /> <input id="branchId"
			name="dpMppCfg.branchId" type="hidden"
			value="${DP_Mpp_CfgForm.dpMppCfg.branchId}" /> <input id="selectCol"
			type="hidden" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="de.label.destTable" /></td>
				<td><form:input path="dpMppCfg.destTable" type="text"
						class="input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="de.label.srcTable" /></td>
				<td><form:input id="srcTable" path="dpMppCfg.srcTable"
						type="text" class="input-large" readonly="true" /></td>
			</tr>
		</table>

	</div>

	<div class="row">
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
				class="table table-striped table-bordered table-condensed tbl_page">
				<tbody>
					<c:forEach var="dto" items="${DP_Mpp_CfgForm.dpTableDtlVOs}"
						varStatus="i">
						<tr>
							<td class="tbl_page_td_left vtip" width="150px">${dto.destColName}</td>
							<td class="tbl_page_td_left vtip" width="250px">${dto.colFormula}</td>
							<td class="tbl_page_td_right vtip" width="250px">${dto.comments}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div class="row" style="margin-bottom: 40px;">
		<c:forEach var="dto1" items="${DP_Mpp_CfgForm.dpTableDtlVOs}"
			varStatus="i1">
			<div id="div_${dto1.destColName}" style="display: none;">
				<input name="destColName" value="${dto1.destColName}" type="hidden" />
				<input name="colFormulaFlag" value="${dto1.colFormula_flag}" type="hidden" />
				<table class="tbl_search">
					<tr align="center">
						<td class="label_td"><font color="red">*</font>
						<spring:message code="dp.lable.DestColName" /></td>
						<td colspan="3" align="left"><input
							name="dpTableDtlVOs[${i1.index}].destColName"
							value="${dto1.destColName}" class="input-xxlarge"
							readonly="readonly" style="height: 20px;"/></td>
					</tr>
					<tr align="center">
						<td class="label_td"><spring:message
								code="dp.lable.ColExpress" /></td>
						<td colspan="3" align="left"><input type="hidden"
							name="dpTableDtlVOs[${i1.index}].colFormula_key"
							id="colFormula_key_${dto1.destColName}"
							value="${dto1.colFormula_key}" /> <input
							name="dpTableDtlVOs[${i1.index}].colFormula"
							id="colFormula_${dto1.destColName}" value="${dto1.colFormula}"
							class="input-xxlarge" readonly="readonly"/>
							<button type="button" class="btn btn-primary" id="colFormula_${dto1.destColName}_express"
								onclick="mppExpress()">
								<spring:message code="dp.lable.Express" />
							</button> <input type="checkbox"
							id="colFormula_${dto1.destColName}_check" value="1"
							name="dpTableDtlVOs[${i1.index}].colFormula_flag"
							onclick="changeType('colFormula_${dto1.destColName}')">
						</td>
					</tr>
					<tr align="center">
						<td class="label_td"><font color="red">*</font>
						<spring:message code="dp.lable.UkFlag" /></td>
						<td align="left"><form:select
								path="dpTableDtlVOs[${i1.index}].ukFlag">
								<form:options items="${DP_0024}" />
							</form:select></td>
						<td class="label_td"><font color="red">*</font>
						<spring:message code="dp.lable.DupProcType" /></td>
						<td><form:select
								path="dpTableDtlVOs[${i1.index}].dupProcType">
								<form:options items="${DP_0025}" />
							</form:select></td>
					</tr>
					<tr align="center">
						<td class="label_td"><spring:message code="if.label.COMMENTS" /></td>
						<td align="left" colspan="3"><form:textarea
								path="dpTableDtlVOs[${i1.index}].comments"
								style="overflow-x: hidden; overflow-y: auto; width: 75%; height: 70px; resize: none;" /></td>
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
			onclick="input()"
			value="<spring:message code="button.lable.Submit"/>"> <input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>

