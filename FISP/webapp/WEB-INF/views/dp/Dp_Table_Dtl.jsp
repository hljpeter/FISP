<script type="text/javascript">
//search detail information
var dtl = function(id, name) {
	showDialog('${pageContext.request.contextPath}/DP_Table_Dtl_Dtl/Init?dpTableDtl.colId=' + id + '&tableName=' + name, '340', '1040');
};
</script>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.table.dpTableDtl"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<table class="tbl_search">
    	<tr><td colspan="4">
    		<p class="text-info"><spring:message code="fisp.titls.dp.table.dpTableDtl.table"/></p>
    	</td></tr>
    	<tr>
			<td class="label_td" width="20%"><spring:message code="fisp.label.common.tableName"/></td>
			<td colspan="3">
				<form:input path="dpTableDtlForm.dpTable.tableName" type="text" class=".input-medium" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.tableDesc"/></td>
			<td colspan="3"><form:input path="dpTableDtlForm.dpTable.tableDesc" type="text" class=".input-large" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.comments"/></td>
			<td colspan="3"><form:input path="dpTableDtlForm.dpTable.comments" type="text" class=".input-large" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.createTime"/></td>
			<td width="30%" ><form:input path="dpTableDtlForm.dpTable.createTime" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.createUser"/></td>
			<td><form:input path="dpTableDtlForm.dpTable.createUser" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.updateTime"/></td>
			<td><form:input path="dpTableDtlForm.dpTable.updateTime" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.updateUser"/></td>
			<td><form:input path="dpTableDtlForm.dpTable.updateUser" type="text" class=".input-small" readonly="true"/></td>
		</tr>
    </table>											
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="tbl_page_head">
	<table class="table table-striped table-bordered table-condensed tbl_page">
      <thead>
        <tr>
        	<th class="tbl_page_th" width="10px"><spring:message code="fisp.label.common.no"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.colName"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="fisp.label.common.colDesc"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.colType"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
		<table class="table table-striped table-bordered table-condensed tbl_page">
		<tbody>
		<form:form id="form" action="${pageContext.request.contextPath}/DP_Table_Dtl/Init" modelAttribute="dpTableDtlForm">
	        <c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="10px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="100px">${dto.colName}</td>
		            <td class="tbl_page_td_left vtip" width="200px">${dto.colDesc}</td>
				  	<td class="tbl_page_td_left vtip" width="100px">${dto.colType}</td>
		            <td class="tbl_page_td_left" width="100px">
			           	<div align="center" style="height: 25px">
							<input type="button" id="detail" class="btn btn-small" onclick="dtl('${f:h(dto.colId)}','${dpTableDtlForm.dpTable.tableName }')" value="<spring:message code="button.lable.DeatilSearch"/>">
		               </div>
		            </td>
				</tr>
	        </c:forEach>
        </form:form>
		</tbody>
		</table>
	</div>
<!-- page and buttons -->
<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 0px;margin-bottom: 0px;">
	<div class="leftPage">
		<util:pagination page="${page}" query="dpTableDtlForm.dpTable.tableId=${dpTableDtlForm.dpTable.tableId}" />
	</div>
</div>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>