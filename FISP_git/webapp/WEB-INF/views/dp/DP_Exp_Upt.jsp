<script type="text/javascript">
	$(function() {
		$("#tmp_val").val($("#fileType").val());
		var customize_flag1 = $("#customize_flag1").val();
		if(customize_flag1 == '1'){
			document.getElementById("customize_flag").checked = true;
			$("#express").attr("disabled", "disabled");
		}else{
			document.getElementById("customize_flag").checked = false;
			$("#tableFilter").attr("readonly", "readonly");
			$("#express").removeAttr("disabled");
		}
		if ($("#fileType").val() == '6') {
			$("#express").attr("disabled", "disabled");
			$("#customize_flag").attr("disabled", "disabled");
			$("#tableName").attr("disabled", "disabled");
			$("#tableFilter").attr("disabled", "disabled");
			$("#xmlConfig").removeAttr("disabled");
			document.getElementById('tableTR').style.display = 'none';
		} else {
			if ('' == $("#tableName").val()) {
				$("#express").attr("disabled", "disabled");
				$("#customize_flag").attr("disabled", "disabled");
			} else {
				$("#express").removeAttr("disabled");
				$("#customize_flag").removeAttr("disabled");
				if(customize_flag1 == '1'){
					document.getElementById("customize_flag").checked = true;
					$("#express").attr("disabled", "disabled");
				}else{
					document.getElementById("customize_flag").checked = false;
					$("#tableFilter").attr("readonly", "readonly");
					$("#express").removeAttr("disabled");
				}
			}
			$("#tableName").removeAttr("disabled");
			$("#tableFilter").removeAttr("disabled");
			$("#xmlConfig").attr("disabled", "disabled");

			document.getElementById('tableTR').style.display = 'table-row';

		}
	});

	//input button
	function input() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/DP_Exp_Upt/SubmitCfg";
		var msg = $("#confirmMsg1").val() + $("#confirmBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	};

	function queryFile() {
		showSelFile('2', [ {
			"fileId" : "param1",
			"fileName" : "param2",
			"fileType" : "param3"
		} ]);
		$("#tmp_val").val($("#fileType").val());
		if ($("#fileType").val() == '6') {
			$("#express").attr("disabled", "disabled");
			$("#customize_flag").attr("disabled", "disabled");
			$("#tableName").attr("disabled", "disabled");
			$("#tableFilter").attr("disabled", "disabled");
			$("#xmlConfig").removeAttr("disabled");
			document.getElementById('tableTR').style.display = 'none';
		} else {
			if ('' == $("#tableName").val()) {
				$("#express").attr("disabled", "disabled");
				$("#customize_flag").attr("disabled", "disabled");
			} else {
				$("#express").removeAttr("disabled");
				$("#customize_flag").removeAttr("disabled");
			}
			$("#tableName").removeAttr("disabled");
			$("#tableFilter").removeAttr("disabled");
			$("#xmlConfig").attr("disabled", "disabled");

			document.getElementById('tableTR').style.display = 'table-row';

		}
	};

	function queryOrg() {
		showSelOrg([ {
			"branchId" : "param1"
		} ]);
	};

	function queryTable() {
		showSelTable([ {
			"tableName" : "param2"
		} ]);
		if ('' != $("#tableName").val()) {
			$("#express").removeAttr("disabled");
			$("#customize_flag").removeAttr("disabled");
		}
	};
	function checkSrcVal() {
		var srcTable = $("#tableName").val();
		if ('' == srcTable) {
			$("#express").attr("disabled", "disabled");
			$("#customize_flag").attr("disabled", "disabled");
		} else {
			$("#express").removeAttr("disabled");
			$("#customize_flag").removeAttr("disabled");
		}

	};
	function expExpress() {
		var srcTable = $("#tableName").val();
		var oldExpress = $("#tableFilter").val();
		var oldExpress_key = $("#tableFilter_key").val();
		var cfgId =  $("#expId").val();
		var branchId =  $("#branchId").val();
		var rtnStr = window
				.showModalDialog(
						'${pageContext.request.contextPath}/Dp_Exp/Init?srcTable='
						+ srcTable + '&oldExpress=' + oldExpress+'&oldExpress_key='+oldExpress_key+'&cfgId='+cfgId+'&branchId='+branchId +'&dpType=3',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		if (typeof (rtnStr) == 'undefined') {
			rtnStr = window.ReturnValue;
		}
		if (rtnStr) {
			$("#tableFilter_key").val(rtnStr.expressKey);
			$("#tableFilter").val(rtnStr.expressVal);
		}
	};

	function getOrgInf(obj) {
		orgid = $(obj).val();
		var orgExist = checkOrgInf(obj);
		if (false == orgExist) {
			var msg = $("#alertMsg1").val();
			document.getElementById("errorMsg").style.display = "block";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			$(obj).val("");
		}
	};
	function changeCustomize() {
		var customize_flag = document.getElementById("customize_flag").checked;
		if (null == customize_flag || customize_flag == false) {
			var tableFilter = $("#tableFilter").val();
			if ('' != tableFilter) {
				var msg = "编辑表达式时，原自定义配置信息将清空！";
				if (confirm(msg)) {
					$("#tableFilter").val("");
					$("#express").removeAttr("disabled");
					$("#tableFilter").attr("readonly", "readonly");
				} else {
					document.getElementById("customize_flag").checked = true;
				}
			}else{
				$("#express").removeAttr("disabled");
				$("#tableFilter").attr("readonly", "readonly");
			}
		} else {
			var tableFilter = $("#tableFilter").val();
			if ('' != tableFilter) {
				var msg = "自定义时，原表达式配置信息将清空！";
				if (confirm(msg)) {
					$("#tableFilter").val("");
					$("#express").attr("disabled", "disabled");
					$("#tableFilter").removeAttr("readonly");
				} else {
					document.getElementById("customize_flag").checked = false;
				}
			}else{
				$("#express").attr("disabled", "disabled");
				$("#tableFilter").removeAttr("readonly");
			}
		}
	};
	
</script>
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagesPanel />
		<spring:hasBindErrors name="DP_Exp_UptForm">
			<form:form commandName="DP_Exp_UptForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.ExpUpt" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/DP_Exp_Upt/SubmitCfg"
		modelAttribute="DP_Exp_UptForm" class="form-horizontal">
		<form:input id="expId" path="dpExpCfg.expId" type="hidden"
			class="input-large" />
		<form:input id="updateTime" path="dpExpCfg.updateTime" type="hidden"
			class="input-large" />
		<form:input id="updateUser" path="dpExpCfg.updateUser" type="hidden"
			class="input-large" />
		<input id="customize_flag1" value="${DP_Exp_UptForm.customize_flag}" type="hidden"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="if.label.PROJID" /></td>
				<td><form:select id="projId" path="dpExpCfg.projId">
						<form:options items="${DP_0021}" />
					</form:select></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="if.label.BRANCHID" /></td>
				<td><form:input id="branchId" path="dpExpCfg.branchId"
						type="text" class="input-large" onblur="getOrgInf(this)"
						onkeyup="numberStringFormat(this);"
						onbeforepaste="numberStringFormatCopy(this);" /><input
					type="button" class="btn btn-small" onclick="queryOrg()"
					value="<spring:message code="button.label.Search"/>"></td>

			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="if.label.BATCHNO" /></td>
				<td><form:input id="batchNo" path="dpExpCfg.batchNo"
						type="text" class="input-large" maxLength="10"
						onkeyup="numberFormat(this);" /></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="if.label.SEQNO" /></td>
				<td><form:input id="seqNo" path="dpExpCfg.seqNo" type="text"
						class="input-large" maxLength="3" onkeyup="numberFormat(this);" /></td>

			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="fisp.label.common.fileName" /></td>
				<td><form:input id="fileId" path="dpExpCfg.fileId"
						type="hidden" /> <form:input id="fileName"
						path="dpExpCfg.fileName" type="text" class="input-large"
						readonly="true" /><input type="button" class="btn btn-small"
					onclick="queryFile()"
					value="<spring:message code="button.label.Search"/>"></td>
				<td class="label_td"><spring:message
						code="fisp.label.common.fileType" /></td>
				<td><form:input id="fileType" path="dpExpCfg.fileType"
						type="hidden" /> <form:select id="tmp_val" path="tmp_val"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${DP_FILE_FILETYPE}" />
					</form:select> <input type="button" class="btn btn-primary" id="xmlConfig"
					onclick="xmlConfig()" disabled="disabled"
					value="<spring:message
						code="dp.lable.XmlFileNodeConfig" />"></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="fisp.label.common.filePath" /></td>
				<td colspan="3"><form:input id="filePath"
						path="dpExpCfg.filePath" type="text" class="input-xxlarge" /></td>
			</tr>

			<tr id="tableTR">
				<td class="label_td"><font color="red">*</font>
				<spring:message code="if.label.TABLENAME" /></td>
				<td><form:input id="tableName" path="dpExpCfg.tableName"
						type="text" class="input-large" onkeyup="checkSrcVal()"
						readonly="true" /> <input type="button" class="btn btn-small"
					onclick="queryTable()"
					value="<spring:message code="button.label.Search"/>"></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="dp.lable.TableExpr" /></td>
				<td>
				<form:input id="tableFilter_key" path="dpExpCfg.tableFilter_key" type="hidden" />
				<form:input id="tableFilter" path="dpExpCfg.tableFilter"
						type="text" class="input-large" /> <input type="button"
					class="btn btn-primary" id="express" onclick="expExpress()"
					disabled="disabled"
					value="<spring:message
						code="dp.lable.Express" />">
					<form:checkbox path="customize_flag" id="customize_flag" value="1"
						cssStyle="margin-right:10px;" onclick="changeCustomize()"
						disabled="disabled" /> <spring:message code="dp.lable.Customize" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.FILETITLE" /></td>
				<td><form:input
						id="fileTitle" path="dpExpCfg.fileTitle" type="text"
						class="input-large" /></td>
				<td class="label_td"><font color="red">*</font>
				<spring:message code="dp.lable.FieldTitleFlag" /></td>
				<td><form:select id="fieldTitleFlag"
						path="dpExpCfg.fieldTitleFlag">
						<form:options items="${DP_0026}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.comments" /></td>
				<td colspan="3"><form:textarea path="dpExpCfg.comments"
						style="overflow-x: hidden; overflow-y: auto; width: 75%; height: 100px; resize: none;" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="confirmBtn" class="btn btn-primary" type="button"
			onclick="input()"
			value="<spring:message code="button.lable.Submit"/>">
		<button class="btn btn-primary" onclick="javascript:window.close();">
			<spring:message code="button.lable.close" />
		</button>
	</div>
</div>
