<!-- title -->
<div class="page_title">任务管理 / 映射配置 / 导入文件定义 / 新增字段</div>
<!-- body -->
<div class="row form-horizontal" style="margin-bottom: 20px;  width: 768px;">
<form:form id="impFileDefForm" action="${pageContext.request.contextPath}"
			modelAttribute="impFileDefForm">
	<table class="tbl_search">
		<tr>
			<td class="label_td">文件名</td>
			<td><input id="fileName" value="${fileName}"
					type="text" class="input_small" readonly="readonly"/></td>
			<td class="label_td">文件路径</td>
			<td><input id="filePath" value="${filePath}"
					type="text" class="input-large" readonly="readonly"/></td>
		</tr>
		<tr>
			<td class="label_td">字段名称</td>
			<td><form:input id="fieldName" path="impFieldDef.fieldName"
					type="text" class="input-large" /></td>
			<td class="label_td">文件类型</td>
			<td><form:select id="fieldType" path="impFieldDef.fieldType"
					class="input-large">
					<form:options items="${IFDC_0001}" />
				</form:select></td>
		</tr>
		<tr>
			<td class="label_td">字段长度</td>
			<td><form:input id="fieldLen" path="impFieldDef.fieldLen"
					type="text" class="input-large" /></td>
			<td class="label_td">字段描述</td>
			<td><form:input id="fieldDesc" path="impFieldDef.fieldDesc"
					type="text" class="input-large" /></td>
		</tr>
		<tr>
			<td class="label_td">是否可空</td>
			<td colspan="3"><form:select id="nullFlag" path="impFieldDef.nullFlag"
					class=".input-small">
					<form:options items="${Com_0001}" />
				</form:select></td>
		</tr>
	</table>
	</form:form>
</div>
<div class="navbar navbar-fixed-bottom text-right" id="footer"
	style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
	<input type="button" class="btn btn-primary" value="保存"> <input
		type="button" class="btn btn-primary"
		onclick="javascript:window.close();"
		value="<spring:message code="button.lable.close"/>">
</div>