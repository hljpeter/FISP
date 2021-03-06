<script type="text/javascript">
	$(function() {
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(3)").text();
			var selected_msgStatus = $(this).find("td:eq(6)").text();
			var old_selected_msgId = $("#selected_msgId").val();
			if (null == old_selected_msgId) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_msgStatus").val(selected_msgStatus);
				return;
			}
			if (old_selected_msgId == selected_msgId) {
				$("#selected_msgId").val("");
				$("#selected_msgStatus").val("");
				return;
			}
			if (old_selected_msgId != selected_msgId) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_msgStatus").val(selected_msgStatus);
				return;
			}


		});
		$("#pageTable")
				.find("tr")
				.bind(
						'dblclick',
						function() {
							var selected_msgId = $(this).find("td:eq(3)")
									.text();
							showDialog('${pageContext.request.contextPath}/FTZ210102/QryDtl?selected_msgId='
									+ selected_msgId,'500','1024');

						});
	});

	function showDetail() {
		var selected_msgId = $("#selected_msgId").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZ210102/QryDtl?selected_msgId='
					+ selected_msgId,'500','1024');
		}
	}

	function sbDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgType = $("#selected_msgType").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			if("02" == selected_msgType){
				alert('<spring:message code="w.cm.1006"/>');
				return;
			}
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210102/SubmitDtl";
			var msg = $("#confirmMsg1").val() + "提交" + $("#confirmMsg2").val();
			if (confirm(msg)) {
				form.submit();
			} else {
				return false;
			}

		}
	}

	function delDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgType = $("#selected_msgType").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			if("03"==selected_msgType){
				alert('<spring:message code="ftz.validate.chk.success"/>');
				return;
			}
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210102/InputDel";
			var msg = $("#confirmMsg1").val() + $("#del").val()
					+ $("#confirmMsg2").val();
			if (confirm(msg)) {
				form.submit();
			} else {
				return false;
			}

		}
	}
	function addDetail() {
		showDialog('${pageContext.request.contextPath}/FTZ210102/AddDtlInit','500','1024');
		queryFTZ210102();
	}
	function uptDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgStatus = $("#selected_msgStatus").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			if ("01" == selected_msgStatus || "04" == selected_msgStatus|| "02" == selected_msgStatus) {
				showDialog('${pageContext.request.contextPath}/FTZ210102/UptDtlInit?selected_msgId='
						+ selected_msgId,'500','1024');
				queryFTZ210102();
			} else {
				alert("非录入或审核失败状态，无法编辑！");
			}

		}
	}
	function queryFTZ210102() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210102/AddQry?page.page="+${page.number+1};
		form.submit();
	}
</script>


</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210102Form">
			<form:form commandName="FTZ210102Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.210102.input" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210102/AddQry"
		method="post" modelAttribute="FTZ210102Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<input type="hidden" id="selected_msgStatus">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID" />：</td>
				<td><form:select path="query_branchId">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><spring:message
						code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="query_submitDate_start"
						path="query_submitDate_start"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" />-<form:input
						id="query_submitDate_end" path="query_submitDate_end"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="query_accountNo" path="query_accountNo"
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="query_msgId" path="query_msgId"
						class=".input-large" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="query_msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_INPUT}" />
					</form:select></td>
				<td style="text-align: right;" colspan="2">
					<button type="submit" class="btn btn-primary">
						<spring:message code="ftz.label.SELECT_MSG" />
					</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<p class="text-info" align="center">
					<spring:message code="ftz.label.MSG_List" />
				</p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.SUBMIT_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="ftz.label.BRANCH" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="ftz.label.MSG_ID" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="100px"><spring:message code="ftz.label.ACCOUNT_NO" /></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message
							code="ftz.label.MSG_STATUS" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: center; width: 40px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 80px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 80px;">${dto.msgId}</td>
						<td class="vtip" style="text-align: left; width: 100px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto.msgStatus}" type="label" /></td>
						<td style="display: none;">${dto.msgStatus}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


<div class="pagination pull-right" style="margin-top: 10px;">
	<table class="text-center">
		<tr>
			<td width="50%" align="center"><input id="add" type="button"
				class="btn btn-primary" onclick="addDetail();"
				value="<spring:message code="ftz.label.ADD_MSG" />"> <input
				id="upt" type="button" class="btn btn-primary"
				onclick="uptDetail();"
				value="<spring:message code="ftz.label.UPT_MSG" />"> <input
				id="del" type="button" class="btn btn-primary"
				onclick="delDetail();"
				value="<spring:message code="ftz.label.DEL_MSG" />"> <input
				id="detail" type="button" class="btn btn-primary"
				onclick="sbDetail();"
				value="<spring:message code="ftz.label.FINISH_MSG" />"> <input
				id="detail" type="button" class="btn btn-primary"
				onclick="showDetail();"
				value="<spring:message code="ftz.label.MSG_Dtl" />"></td>
			<td width="50%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${FTZ210102Form.query_branchId}&query_submitDate_start=${FTZ210102Form.query_submitDate_start}&query_submitDate_end=${FTZ210102Form.query_submitDate_end}&query_msgId=${FTZ210102Form.query_msgId}&query_accountNo=${FTZ210102Form.query_accountNo}&query_msgStatus=${FTZ210102Form.query_msgStatus}"
								action="/FTZ210102/AddQry" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
