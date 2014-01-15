<!-- title -->
<div class="page_title">任务管理 / 映射配置 / 导入文件定义 / 新增</div>
<!-- body -->
<div class="row form-horizontal" style="margin-bottom: 20px;">
	<table class="tbl_search">
		<tr>
			<td class="label_td">文件名</td>
			<td><form:input id="fileName" path="impFileDef.fileName"
					type="text" class=".input_small" /></td>
			<td class="label_td">文件类型</td>
			<td><form:select id="fileType" path="impFileDef.fileType"
					class=".input-small" disabled="disabled">
					<form:options items="${EFC_0001}" />
				</form:select></td>
		</tr>
		<tr>
			<td class="label_td">文件路径</td>
			<td colspan="3"><form:input id="filePath"
					path="impFileDef.filePath" type="text" class="span6" /></td>
		</tr>
		<tr>
			<td class="label_td">分隔符</td>
			<td><form:input id="delimiter" path="impFileDef.delimiter"
					type="text" class=".input_small" /></td>
			<td class="label_td">SHEET号</td>
			<td><form:input id="sheetNum" path="impFileDef.sheetNum"
					type="text" class=".input_small" /></td>
		</tr>
		<tr>
			<td class="label_td">开始忽略行数</td>
			<td><form:input id="srowIgnrNum" path="impFileDef.srowIgnrNum"
					type="text" class=".input_small" /></td>
			<td class="label_td">结束忽略行数</td>
			<td><form:input id="erowIgnrNum" path="impFileDef.erowIgnrNum"
					type="text" class=".input_small" /></td>
		</tr>
		<tr>

			<td class="label_td">起始列</td>
			<td><form:input id="startColumn" path="impFileDef.startColumn"
					type="text" class=".input_small" /></td>
			<td class="label_td">截止列</td>
			<td><form:input id="endColumn" path="impFileDef.endColumn"
					type="text" class=".input_small" /></td>
		</tr>
		<tr>
			<td class="label_td">文件说明</td>
			<td colspan="1"><form:input id="fileDesc"
					path="impFileDef.fileDesc" type="text" class="span6" /></td>
			<td colspan="2" align="right">
				<button class="btn btn-primary">保存</button>
			</td>
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
					<th class="tbl_page_th" width="140px">操作</th>
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
					<c:forEach var="dto" items="${impfieldList}" varStatus="i">
						<tr>
							<td class="tbl_page_td_left vtip" width="50px">${dto.colIndex}</td>
							<td class="tbl_page_td_left vtip" width="150px">${dto.fieldName}</td>
							<td class="tbl_page_td_left vtip" width="70px">${dto.fieldLen}</td>
							<td class="tbl_page_td_left vtip" width="100px">${dto.fieldType}</td>
							<td class="tbl_page_td_left vtip" width="100px">${dto.nullFlag}</td>
							<td class="tbl_page_td_left vtip" width="220px">${dto.fieldDesc}</td>
							<td class="tbl_page_td_left" width="140px">
								<div style="height: 25px">
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
</div>

<div class="navbar navbar-fixed-bottom text-right" id="footer"
	style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
	<input type="button" class="btn btn-primary" value="新增字段"> <input
		type="button" class="btn btn-primary"
		onclick="javascript:window.close();"
		value="<spring:message code="button.lable.close"/>">
</div>

