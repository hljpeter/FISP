<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.table.dpTableDtl"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<table class="tbl_search">
    	<tr>
			<td class="label_td" width="20%"><spring:message code="fisp.label.common.tableName"/></td>
			<td colspan="3">
				<form:input path="dpTableDtlDtlForm.tableName" type="text" class=".input-medium" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="label_td" width="20%"><spring:message code="fisp.label.common.colSeqNo"/></td>
			<td width="30%" ><form:input path="dpTableDtlDtlForm.dpTableDtl.colSeqNo" type="text" class=".input-small" readonly="true"/></td>
		
			<td class="label_td" width="20%"><spring:message code="fisp.label.common.colName"/></td>
			<td><form:input path="dpTableDtlDtlForm.dpTableDtl.colName" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.colDesc"/></td>
			<td colspan="3"><form:input path="dpTableDtlDtlForm.dpTableDtl.colDesc" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr>	
			<td class="label_td"><spring:message code="fisp.label.common.colType"/></td>
			<td><form:input path="dpTableDtlDtlForm.dpTableDtl.colType" type="text" class=".input-small" readonly="true"/></td>
		
			<td class="label_td"><spring:message code="fisp.label.common.colLen"/></td>
			<td><form:input path="dpTableDtlDtlForm.dpTableDtl.colLen" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.comments"/></td>
			<td colspan="3"><form:input path="dpTableDtlDtlForm.dpTableDtl.comments" type="text" class=".input-small" readonly="true"/></td>
		</tr>
    </table>											
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>