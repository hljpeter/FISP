<script type="text/javascript">
	$(function() {
		$("#refresh").click(function() {
			window.location.href='${pageContext.request.contextPath}/FTZMsgGenerate/Init';
		});
		$("#generate").click(function() {
			$("#form").submit();
		});
		$("#allcheckbox").click(function() {
			if ($(this).attr("checked") == 'checked') {
				$("input[name=selecteds]").attr("checked", true);
			} else {
				$("input[name=selecteds]").removeAttr("checked");
			}
		});
	});

</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="FTZMsgGenerateForm">
			<form:form commandName="FTZMsgGenerateForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.msg.generate.qry" /></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZMsgGenerate/Gen" method="post" modelAttribute="FTZMsgGenerateForm" class="form-horizontal">
	<div class="tbl_page_head">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><input id="allcheckbox" type="checkbox" checked/></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message code="ftz.label.BRANCH" /></th>
					<th style="vertical-align: middle; text-align: center" width="100px"><spring:message code="ftz.label.MSG_NO_GEN" /><!-- å·¥ä½æ¥æ --></th>
					<th style="vertical-align: middle; text-align: center" width="300px"><spring:message code="ftz.label.BATCH_QRY_RESULT" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable" class="table table-striped table-bordered table-condensed tbl_page" style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${FTZMsgGenerateForm.list}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 10px;"><form:checkbox path="selecteds" value="${dto.branchId }_${dto.msgNo }" checked="true"/></td>
						<td class="vtip" style="text-align: left; width: 60px;">${dto.branchId }</td>
						<td class="vtip" style="text-align: center; width: 100px;">${dto.msgNo }</td>
						<td class="vtip" style="text-align: left; width: 300px;">
							<spring:message code="ftz.label.INPUTING"/>：${dto.inputing }；&nbsp;&nbsp;
							<spring:message code="ftz.label.COMPLETED"/>：${dto.completed }；&nbsp;&nbsp;
							<spring:message code="ftz.label.SUCCESS"/>：${dto.succ }；&nbsp;&nbsp;
							<spring:message code="ftz.label.FAILURE"/>：${dto.fail }；&nbsp;&nbsp;
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</form:form>
</div>


<div class="pagination" style="margin-top: 10px;">
	<table width="100%" class="text-center">
		<tr>
			<td width="100%" align="center">
				<input id="refresh" type="button" class="btn btn-primary" value="<spring:message code="ftz.label.REFRESH"/>"/>
				<input id="generate" type="button" class="btn btn-primary" value="<spring:message code="ftz.label.GENERATE"/>">
			</td>
		</tr>
	</table>
</div>
