<script type="text/javascript">
	if (typeof (window.opener) == 'undefined')
		window.opener = window.dialogArguments;
	$(function() {
		$("#constTable").find("tr").bind('click', function() {
			var inputVale = $(this).find("td:eq(0)").text();
			var sql = $("#SQL").val();
			sql = sql + inputVale;
			$("#SQL").val(sql);
		});

	});

	//input button
	function input() {
		window.returnValue = $("#SQL").val();
		if (window.opener && window.opener != null) {
			window.opener.ReturnValue = $("#SQL").val();
		}
		window.close();

	};

	function winClose() {
		window.returnValue = null;
		if (window.opener && window.opener != null) {
			window.opener.ReturnValue = null;
		}
		window.close();
	};
</script>

<div class="page_title">
	<spring:message code="dp.title.MppSqlEdit" />
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="150px"><spring:message
							code="dp.lable.inputValue" /></th>
					<th class="tbl_page_th" width="150px"><spring:message
							code="dp.lable.outputValue" /></th>
					<th class="tbl_page_th" width="150px"><spring:message
							code="dp.lable.mapDesc" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 155px; height: 255px;">
		<table id="constTable"
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>

				<c:forEach var="dto" items="${list}" varStatus="i">
					<tr>
						<td class="tbl_page_td_left vtip" width="150px">${dto.inVal}</td>
						<td class="tbl_page_td_left vtip" width="150px">${dto.outVal}</td>
						<td class="tbl_page_td_left vtip" width="150px">${dto.mapDesc}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="row">
	<table class="tbl_search">
		<tr>
			<td align="center"><spring:message code="dp.lable.SQL" /></td>
			<td align="left"><textarea id="SQL" rows="2"
					style="overflow-x: hidden; overflow-y: auto; width: 75%; resize: none;">${srcSQL}</textarea></td>
		</tr>
		<tr>
			<td align="center"><spring:message code="dp.lable.srcSQL" /></td>
			<td align="left"><textarea id="srcSQL" rows="2"
					style="overflow-x: hidden; overflow-y: auto; width: 75%; resize: none;"
					readonly="readonly">${srcSQL}</textarea></td>
		</tr>

	</table>
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary"
			onclick="input()"
			value="<spring:message code="button.lable.Submit"/>"> <input
			type="button" class="btn btn-primary" onclick="winClose()"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>