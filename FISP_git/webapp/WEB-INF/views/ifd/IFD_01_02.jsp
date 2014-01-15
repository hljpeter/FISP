<script type="text/javascript">
	//init page
	$(function() {
		var flag = $("#flag").val();
		if (flag == '1') {
			$('.page_title').text('任务管理  / 映射配置  / 导入文件定义 / 修改');
			$("#fileName").removeAttr("readonly");
			$("#fileType").removeAttr("disabled");
			$("#fileType").removeAttr("readonly");
			$("#filePath").removeAttr("readonly");
			$("#delimiter").removeAttr("readonly");
			$("#sheetNum").removeAttr("readonly");
			$("#srowIgnrNum").removeAttr("readonly");
			$("#erowIgnrNum").removeAttr("readonly");
			$("#startColumn").removeAttr("readonly");
			$("#endColumn").removeAttr("readonly");
			$("#fileDesc").removeAttr("readonly");
			$("#srowIgnrNum").removeAttr("readonly");
		} else {
			$('.page_title').text('任务管理  / 映射配置  / 导入文件 定义 / 明细');
			$("#fileType").attr("disabled", "disabled");
		}

	});
	//delete button
	function del(fileId, fieldId) {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifd01/05/delete?fileId="
				+ fileId;
		var msg = $("#confirmMsg1").val() + 'å é¤' + $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.submit();
		}
	}
	//update button
	function update(fileId, fieldId) {
		var fileName = $("#fileName").val();
		var filePath = $("#filePath").val();
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/ifd01/01/modifyField?fieldId='
								+ fieldId + "&fileId=" + fileId + "&fileName="
								+ fileName + "&filePath=" + filePath,
						window,
						'dialogHeight:300px; dialogWidth: 800px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');

	}

	//update button
	function addField() {
		var fileName = $("#fileName").val();
		var filePath = $("#filePath").val();
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/ifd01/01/modifyaddfield?fileName='
								+ fileName + "&filePath=" + filePath,
						window,
						'dialogHeight:300px; dialogWidth: 800px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');

	}
</script>
<!-- title -->
<div class="page_title"></div>
<!-- body -->
<div class="row form-horizontal" style="margin-bottom: 20px;">
	<input type="hidden" id="flag" value="${flag}" />
	<input type="hidden" id="fileId" value="${fileId}" />
	<table class="tbl_search">
		<tr>
			<td class="label_td">文件名</td>
			<td><form:input id="fileName" path="impFileDef.fileName"
					type="text" class=".input_small" readonly="true" /></td>
			<td class="label_td">文件类型</td>
			<td><form:select id="fileType" path="impFileDef.fileType"
					class=".input-small" readonly="true" disabled="disabled">
					<form:options items="${EFC_0001}" />
				</form:select></td>
		</tr>
		<tr>
			<td class="label_td">文件路径</td>
			<td colspan="2"><form:input id="filePath"
					path="impFileDef.filePath" type="text" class="input-xxlarge"
					readonly="true" /></td>
		</tr>
		<tr>
			<td class="label_td">分隔符</td>
			<td><form:input id="delimiter" path="impFileDef.delimiter"
					type="text" class=".input_small" readonly="true" /></td>
			<td class="label_td">SHEET号</td>
			<td><form:input id="sheetNum" path="impFileDef.sheetNum"
					type="text" class=".input_small" readonly="true" /></td>
		</tr>
		<tr>
			<td class="label_td">开始忽略行数</td>
			<td><form:input id="srowIgnrNum" path="impFileDef.srowIgnrNum"
					type="text" class=".input_small" readonly="true" /></td>
			<td class="label_td">结束忽略行数</td>
			<td><form:input id="erowIgnrNum" path="impFileDef.erowIgnrNum"
					type="text" class=".input_small" readonly="true" /></td>
		</tr>
		<tr>

			<td class="label_td">起始列</td>
			<td><form:input id="startColumn" path="impFileDef.startColumn"
					type="text" class=".input_small" readonly="true" /></td>
			<td class="label_td">截止列</td>
			<td><form:input id="endColumn" path="impFileDef.endColumn"
					type="text" class=".input_small" readonly="true" /></td>
		</tr>
		<tr>
			<td class="label_td">文件说明</td>
			<td colspan="2"><form:input id="fileDesc"
					path="impFileDef.fileDesc" type="text" class="input-xxlarge"
					readonly="true" /></td>
			<c:if test="${flag eq 1 }">
				<td colspan="2" align="right">
					<button class="btn btn-primary">保存</button>
				</td>
			</c:if>
		</tr>
	</table>
</div>


<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="50px">列数</th>
					<th class="tbl_page_th" width="150px">字段名称</th>
					<th class="tbl_page_th" width="70px">字段长度</th>
					<th class="tbl_page_th" width="100px">字段类型</th>
					<th class="tbl_page_th" width="100px">是否可空</th>
					<th class="tbl_page_th" width="220px">字段描述</th>
					<th class="tbl_page_th" width="100px">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body"
		style="min-height: 341px; height: 341px; margin-bottom: 40px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<form:form id="impFileDefForm"
					action="${pageContext.request.contextPath}"
					modelAttribute="impFileDefForm">
					<input id="rttpayid" type="hidden" name="rttpay" />
					<c:forEach var="dto" items="${page.content}" varStatus="i">
						<tr>
							<td class="tbl_page_td_left vtip" width="50px">${dto.colIndex}</td>
							<td class="tbl_page_td_left vtip" width="150px">${dto.fieldName}</td>
							<td class="tbl_page_td_left vtip" width="70px">${dto.fieldLen}</td>
							<td class="tbl_page_td_left vtip" width="100px">${dto.fieldType}</td>
							<td class="tbl_page_td_left vtip" width="100px">${dto.nullFlag}</td>
							<td class="tbl_page_td_left vtip" width="220px">${dto.fieldDesc}</td>
							<td class="tbl_page_td_left" width="100px">
								<div style="height: 25px; ">
									<input type="button" class="btn btn-small"
										onclick="del('${dto.fileId}','${dto.fieldId }')" value="删除">
									<input type="button" class="btn btn-small"
										onclick="update('${dto.fileId}','${dto.fieldId }')" value="修改">
								</div>
							</td>
						</tr>
					</c:forEach>
				</form:form>
			</tbody>
		</table>

	</div>
	<div class="row" style="margin-bottom: 40px;">
		<div class="pagination pull-right"
			style="margin-top: 5px; margin-bottom: 0px;">
			<div class="leftPage">
				<util:pagination page="${page}"
					query="fileId=${fileId}" />
			</div>
		</div>

		<div class="navbar navbar-fixed-bottom text-right" id="footer"
			style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
			<c:if test="${flag eq 1 }">
				<input type="button" class="btn btn-primary" onclick="addField()"
					value="新增字段">
			</c:if>
			<input type="button" class="btn btn-primary"
				onclick="javascript:window.close();"
				value="<spring:message code="button.lable.close"/>">
		</div>

	</div>
</div>



