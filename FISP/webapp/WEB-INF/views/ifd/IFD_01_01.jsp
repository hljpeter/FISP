<script type="text/javascript">
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifd01/01/search";
		form.submit();
	}

	//detail button
	function detailSearch(fileId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/ifd01/01/detailSearch?fileId='
								+ fileId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}

	//add button
	function add() {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/ifd01/01/addImp',
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');

	}
	//update button
	function update(fileId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/ifd01/01/modifySearch?fileId='
								+ fileId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');

	}

	//updatefield button
	function updateField(fileId) {
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/ifdc01/01/search?fileId='
								+ fileId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}

	//delete button
	function del(fileId) {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifd01/05/delete?fileId="
				+ fileId;
		var msg = $("#confirmMsg1").val() + '删除' + $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		} else {
			return false;
		}
	}
</script>

<!-- tips information -->
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg"
			messagesType="success" />
		<spring:hasBindErrors name="impFileCfgForm">
			<form:form commandName="impFileDefForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title">任务管理 / 映射配置 / 导入文件定义</div>

<!-- body -->

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/ifd01/01/init"
		modelAttribute="impFileDefForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td">文件名</td>
				<td><form:input path="fileName" type="text" class="input-large"
						maxlength="20" /></td>
				<td class="label_td">文件类型</td>
				<td><form:select id="fileType" path="fileType"
						class="input-large">
						<form:option value="" selected="selected"></form:option>
						<form:options items="${EFC_0001}" />
					</form:select></td>
				<td colspan="2" align="right">
					<button type="button" class="btn btn-primary" onclick="search()">
						<spring:message code="button.label.Search" />
					</button>
					<button type="button" class="btn btn-primary" onclick="add()">
						<spring:message code="button.label.Add" />
					</button>
					<button type="button" class="btn btn-primary">导出</button>
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
				<tr>
					<th class="tbl_page_th" width="20px">No.</th>
					<th class="tbl_page_th" width="100px">文件名</th>
					<th class="tbl_page_th" width="100px">文件类型</th>
					<th class="tbl_page_th" width="50px">分隔符</th>
					<th class="tbl_page_th" width="150px">文件路径</th>
					<th class="tbl_page_th" width="210px">文件说明</th>
					<th class="tbl_page_th" width="140px">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<form:form id="impFileDefForm"
					action="${pageContext.request.contextPath}"
					modelAttribute="impFileDefForm">
					<input id="rttpayid" type="hidden" name="rttpay" />
					<c:forEach var="dto" items="${page.content}" varStatus="i">
						<tr>
							<td class="tbl_page_td_left vtip" width="20px">${(page.number*page.size)+(i.index+1)}</td>
							<td class="tbl_page_td_left vtip" width="100px">${dto.fileName}</td>
							<td class="tbl_page_td_left vtip" width="100px"><t:codeValue
									items="${EFC_0001}" key="${dto.fileType}" type="label" /></td>
							<td class="tbl_page_td_left vtip" width="50px">${dto.delimiter}</td>
							<td class="tbl_page_td_left vtip" width="150px">${dto.filePath}</td>
							<td class="tbl_page_td_left vtip" width="210px">${dto.fileDesc}</td>
							<td class="tbl_page_td_left" width="140px">
								<div style="height: 25px">
									<input type="button" class="btn btn-small"
										onclick="detailSearch('${f:h(dto.fileId)}')"
										value="<spring:message code="index.lable.DeatilSearch"/>">
									<input type="button" class="btn btn-small"
										onclick="update('${f:h(dto.fileId)}')"
										value="<spring:message code="button.label.Update"/>">
									<input type="button" class="btn btn-small"
										onclick="del('${f:h(dto.fileId)}')"
										value="<spring:message code="button.label.Delete"/>">

								</div>
							</td>
						</tr>
					</c:forEach>
				</form:form>
			</tbody>
		</table>
	</div>
</div>

<!-- page and buttons -->
<div class="pagination pull-right"
	style="margin-top: 5px; margin-bottom: 0px;">
	<div class="leftPage">
		<util:pagination page="${page}"
			query="fileId=${listForm.fileId}&fileType=${listForm.fileType}" />
	</div>
</div>


