<script type="text/javascript">
	window.name = "curWindow";
	var expFileIdValue;
	expFileIdValue = "${expFileIdValue}";
	var expFieldIdValue;
	expFieldIdValue = "${expFieldIdValue}";
	var flag = '';
	//验证表单
	function validSearchForm() {
		var obj = {};
		obj.valid = true;

		$(".alert-error").empty();
		obj.fieldId = $("input[name='fieldId']").val();
		obj.fileId = $("input[name='fileId']").val();
		obj.seqNo = $("input[name='seqNo']").val();
		obj.fieldName = $("input[name='fieldName']").val();
		obj.fieldSql = $("input[name='fieldSql']").val();
		obj.fieldDesc = $("input[name='fieldDesc']").val();
		obj.comments = $("input[name='comments']").val();
		obj.rsv1 = $("input[name='rsv1']").val();
		obj.rsv2 = $("input[name='rsv2']").val();
		obj.rsv3 = $("input[name='rsv3']").val();
		obj.rsv4 = $("input[name='rsv4']").val();
		obj.rsv5 = $("input[name='rsv5']").val();

		if (!$.trim(obj.fieldId)) {
			$(".alert-error").append('<spring:message code="e.ta.1050"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.fileId)) {
			$(".alert-error").append('<spring:message code="e.ta.1040"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.seqNo)) {
			$(".alert-error").append('<spring:message code="e.ta.1040"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.fieldName)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.fieldSql)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.fieldDesc)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.comments)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.rsv1)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.rsv2)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.rsv3)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.rsv4)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		if (!$.trim(obj.rsv5)) {
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid = false;
		}
		return obj;
	}

	function operation() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifc01/04/update";
		form.submit();
	}

	function insert() {
		flag = 'insert';
		$("#insertBtn").attr("disabled", "disabled");
		$("#updateBtn").attr("disabled", "disabled");
		$("#deleteBtn").attr("disabled", "disabled");
		$("#operationBtn").removeAttr("disabled");
		$("#cancelBtn").removeAttr("disabled");

		$("input[name='fieldId']").val(expFieldIdValue);
		$("input[name='fileId']").val(expFileIdValue);
		$("input[name='seqNo']").val("");
		$("input[name='fieldName']").val("");
		$("input[name='fieldSql']").val("");
		$("input[name='fieldDesc']").val("");
		$("input[name='comments']").val("");

		$("input[name='seqNo']").removeAttr("readonly");
		$("input[name='fieldName']").removeAttr("readonly");
		$("input[name='fieldSql']").removeAttr("readonly");
		$("input[name='fieldDesc']").removeAttr("readonly");
		$("input[name='comments']").removeAttr("readonly");
	}

	function update() {
		flag = 'update';
		$("#insertBtn").attr("disabled", "disabled");
		$("#updateBtn").attr("disabled", "disabled");
		$("#deleteBtn").attr("disabled", "disabled");
		$("#operationBtn").removeAttr("disabled");
		$("#cancelBtn").removeAttr("disabled");

		$("input[name='fieldId']").removeAttr("readonly");

		$("input[name='seqNo']").removeAttr("readonly");
		$("input[name='fieldName']").removeAttr("readonly");
		$("input[name='fieldSql']").removeAttr("readonly");
		$("input[name='fieldDesc']").removeAttr("readonly");
		$("input[name='comments']").removeAttr("readonly");
	}

	function cancel() {
		$("#insertBtn").removeAttr("disabled");
		$("#updateBtn").attr("disabled", "disabled");
		$("#deleteBtn").attr("disabled", "disabled");
		$("#operationBtn").attr("disabled", "disabled");
		$("#cancelBtn").attr("disabled", "disabled");

		$("input[name='fieldId']").attr("readonly", "true");
		$("input[name='fileId']").attr("readonly", "true");
		$("input[name='seqNo']").attr("readonly", "true");
		$("input[name='fieldName']").attr("readonly", "true");
		$("input[name='fieldSql']").attr("readonly", "true");
		$("input[name='fieldDesc']").attr("readonly", "true");
		$("input[name='comments']").attr("readonly", "true");

		obj.fieldId = $("input[name='fieldId']").val("");
		obj.fileId = $("input[name='fileId']").val("");
		obj.seqNo = $("input[name='seqNo']").val("");
		obj.fieldName = $("input[name='fieldName']").val("");
		obj.fieldSql = $("input[name='fieldSql']").val("");
		obj.fieldDesc = $("input[name='fieldDesc']").val("");
		obj.comments = $("input[name='comments']").val("");
	}

	function operation() {
		var form = document.getElementById("form");
		form.target = "curWindow";
		form.action = "";
		var msg = '';
		if (flag == 'update') {
			msg = $("#confirmMsg1").val() + $("#updateBtn").val()
					+ $("#confirmMsg2").val();
			form.action = "${pageContext.request.contextPath}/efdc01/04/update?expFileIdValue="
					+ expFileIdValue;
			if (confirm(msg)) {
				form.submit();
				return;
			} else {
				return false;
			}
		}
		if (flag = 'insert') {
			msg = $("#confirmMsg1").val() + $("#insertBtn").val()
					+ $("#confirmMsg2").val();
			form.action = "${pageContext.request.contextPath}/efdc01/03/insert?expFileIdValue="
					+ expFileIdValue;
			if (confirm(msg)) {
				form.submit();
				return;
			} else {
				return false;
			}
		}
	}

	function del() {
		form.target = "curWindow";
		form.action = "${pageContext.request.contextPath}/efdc01/05/delete?expFileIdValue="
				+ expFileIdValue;
		if (confirm($("#confirmMsg1").val() + $("#deleteBtn").val()
				+ $("#confirmMsg2").val())) {
			form.submit();
		} else {
			return false;
		}
	}

	//detail button
	function detailSearch(fieldId) {

		var form = document.getElementById("form");
		form.action = '${pageContext.request.contextPath}/efdc01/02/detailSearch?expfieldId='
				+ fieldId + '&updel=' + 1;
		form.target = "curWindow";
		form.submit();
	}

	function setExpFieldInfo(FieldIdValue) {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/efdc01/02/detailSearch?expfieldId="
							+ FieldIdValue + '&updel=' + 1,
					type : "post",
					dataType : "json",
					async : true,
					data : {
						reckfieldIdValue : FieldIdValue
					},
					success : function(rs) {
						setFieldInfo(rs);
					}
				});
	}

	function setFieldInfo(obj) {
		$("#fieldId").val(obj.fieldId);
		$("#fileId").val(obj.fileId);
		$("#seqNo").val(obj.seqNo);
		$("#fieldName").val(obj.fieldName);
		$("#fieldSql").val(obj.fieldSql);
		$("#fieldDesc").val(obj.fieldDesc);
		$("#comments").val(obj.comments);
		$("#updateBtn").removeAttr("disabled");
		$("#deleteBtn").removeAttr("disabled");
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg"
			messagesType="success" />
		<spring:hasBindErrors name="expFieldCfgForm">
			<form:form commandName="expFieldCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>


<form id="form" method="post">
	<div class="page_title">
		任务管理  / 映射配置  / 报表映射配置 / 修改字段
	</div>
	<div class="row" style="margin-bottom: 2px;">
		<div class="tbl_page_head">
			<table
				class="table table-striped table-bordered table-condensed tbl_page">
				<thead>
					<tr>
						<th class="tbl_page_th" width="50px"><spring:message
								code="efd.label.SEQNO" /></th>
						<th class="tbl_page_th" width="150px"><spring:message
								code="efd.label.FIELDNAME" /></th>
						<th class="tbl_page_th" width="130px"><spring:message
								code="efd.label.FIELDSQL" /></th>
						<th class="tbl_page_th" width="130px"><spring:message
								code="efd.label.FIELDDESC" /></th>
						<th class="tbl_page_th" width="130px"><spring:message
								code="efd.label.COMMENTS" /></th>
						<th class="tbl_page_th" width="60px"><spring:message
								code="index.label.sm.Operation" /></th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="tbl_page_body" style="min-height: 100px; height: 355px;">
			<table
				class="table table-striped table-bordered table-condensed tbl_page">
				<tbody>

					<c:forEach var="dto" items="${page}" varStatus="i">
						<tr>
							<td class="tbl_page_td_left vtip" width="50px">${dto.seqNo}</td>
							<td class="tbl_page_td_left vtip" width="150px">${dto.fieldName}</td>
							<td class="tbl_page_td_left vtip" width="130px">${dto.fieldSql}</td>
							<td class="tbl_page_td_left vtip" width="130px">${dto.fieldDesc}</td>
							<td class="tbl_page_td_left vtip" width="130px">${dto.comments}</td>
							<td class="tbl_page_td_left" width="60px">
								<div align="center" style="height: 25px">
									<input type="button" class="btn btn-small"
										onclick="setExpFieldInfo('${f:h(dto.fieldId)}')"
										value="<spring:message code="button.label.Choose"/>">
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div style="text-align: right; margin-bottom: 2px;">
		<input type="button" class="btn btn-primary"
			value="<spring:message code="button.label.Add"/>" id="insertBtn"
			onclick="insert()" /> <input type="button" class="btn btn-primary"
			value="<spring:message code="button.label.Update"/>" id="updateBtn"
			onclick="update()" disabled="disabled" /> <input type="button"
			class="btn btn-primary"
			value="<spring:message code="button.label.Delete"/>" id="deleteBtn"
			onclick="del()" disabled="disabled" />
	</div>


	<%
		if (request.getParameter("updel") != null)
			if (request.getParameter("updel").toString().equals("1")) {
	%>
	<script type="text/javascript">
		$("#updateBtn").removeAttr("disabled");
		$("#deleteBtn").removeAttr("disabled");
	</script>
	<%
		}
	%>



	<div class="page_title">
		<spring:message code="efd_title.empFieldCfgMx" />
	</div>
	<!-- body -->
	<div class="row form-horizontal" style="margin-bottom: 40px;">

		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="efd.label.FIELDID" /></td>
				<td><form:input id="fieldId" path="expFieldCfg.fieldId"
						type="text" class=".input-small" readonly="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="efd.label.FILEID" /></td>
				<td><form:input id="fileId" path="expFieldCfg.fileId"
						type="text" class=".input-small" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="efd.label.SEQNO" /></td>
				<td><form:input id="seqNo" path="expFieldCfg.seqNo" type="text"
						class=".input-small" readonly="true" onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="efd.label.FIELDNAME" /></td>
				<td><form:input id="fieldName" path="expFieldCfg.fieldName"
						type="text" class=".input-small" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="efd.label.FIELDSQL" /></td>
				<td><form:input id="fieldSql" path="expFieldCfg.fieldSql"
						type="text" class=".input-small" readonly="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="efd.label.FIELDDESC" /></td>
				<td><form:input id="fieldDesc" path="expFieldCfg.fieldDesc"
						type="text" class=".input-small" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="efd.label.COMMENTS" /></td>
				<td><form:input id="comments" path="expFieldCfg.comments"
						type="text" class=".input-small" readonly="true" /></td>
				<td colspan="2" align="right"><input type="button"
					class="btn btn-primary" value="更新" id="operationBtn"
					onclick="operation()" /> <input type="button"
					class="btn btn-primary" value="取消" id="cancelBtn"
					onclick="cancel()" /></td>
			</tr>
		</table>
	</div>
</form>


<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>
