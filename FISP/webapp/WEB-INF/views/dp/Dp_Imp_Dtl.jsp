<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.imp.dpImpDtl"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;width:1000px">
	<form:form id="form" action="${pageContext.request.contextPath}/DP_Imp_Dtl/Init" method="post" modelAttribute="dpImpDtlForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr><td colspan="4">
	    		<p class="text-info"><spring:message code="fisp.titls.dp.imp.dpImpDtl.cfg"/></p>
	    	</td></tr>
	    	<tr>
				<td class="label_td" width="20%"><spring:message code="fisp.label.common.projId"/></td>
				<td width="30%">
					<form:input path="dpImpCfg.projId" type="text" class=".input-small" readonly="true"/>
				</td>
				
				<td class="label_td" width="20%"><spring:message code="fisp.label.common.branchId"/></td>
				<td width="30%">
					<form:input path="dpImpCfg.branchId" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.label.common.batchNo"/></td>
				<td><form:input path="dpImpCfg.batchNo" type="text" class=".input-small" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="fisp.label.common.seqNo"/></td>
				<td><form:input path="dpImpCfg.seqNo" type="text" class=".input-small" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.label.common.fileName"/></td>
				<td><form:input path="dpImpCfg.fileName" type="text" class=".input-small" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
				<td><form:input path="dpImpCfg.tableName" type="text" class=".input-small" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.label.common.filePath"/></td>
				<td><form:input path="dpImpCfg.filePath" type="text" class=".input-small" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="fisp.label.common.mandFlag"/></td>
				<td><form:input path="dpImpCfg.mandFlag" type="text" class=".input-small" readonly="true"/></td>
			</tr>			
			<tr>
				<td class="label_td"><spring:message code="fisp.label.common.comments"/></td>
				<td colspan="3"><form:textarea path="dpImpCfg.comments" class="input-xxlarge" readonly="true" rows="5" /></td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row" style="margin-bottom: 40px;width:1000px">
	<table class="tbl_search">
    	<tr><td colspan="5">
    		<p class="text-info"><spring:message code="fisp.titls.dp.imp.dpImpDtl.dtl"/></p>
    	</td></tr>
    	<tr>
			<td><spring:message code="fisp.label.common.fileName"/>:${dpImpDtlForm.dpImpCfg.fileName }</td>
			<td></td>
			<td align="center"><spring:message code="fisp.label.common.selectedfiledtl"/></td>
			<td></td>
			<td><spring:message code="fisp.label.common.tableName"/>:${dpImpDtlForm.dpImpCfg.tableName }</td>
		</tr>
		<tr>
			<td>
				<!-- all field in file -->
				<div class="tbl_page_head">
					<table class="table table-striped table-bordered table-condensed tbl_page">
						<thead>
				        <tr>
				        	<th class="tbl_page_th" width="20px"><spring:message code="fisp.label.common.no"/></th>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.fieldName"/></th>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.fieldDesc"/></th>
				          	<th class="tbl_page_th" width="40px"><spring:message code="fisp.label.common.fieldLen"/></th>
				          	<th width="1px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body">
					<table class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
				        <c:forEach var="dto" items="${dpImpDtlForm.fList}">
							<tr>
					          	<td class="tbl_page_td_left vtip" width="20px">${dto.fieldSeqNo}</td>
					            <td class="tbl_page_td_left vtip" width="80px">${dto.fieldName}</td>
							  	<td class="tbl_page_td_left vtip" width="80px">${dto.fieldDesc}</td>
					            <td class="tbl_page_td_left vtip" width="40px">${dto.fieldLen}</td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
			</td>
			<td width="20" align="center">
				<button disabled><spring:message code="fisp.button.label.right"/></button><br/><br/>
			</td>
			<td align="center">
				<!-- the detail in configuration -->
				<div class="tbl_page_head">
					<table class="table table-striped table-bordered table-condensed tbl_page">
						<thead>
				        <tr>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.fieldName"/></th>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.colName"/></th>
				          	<th width="2px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body" style="overflow-y:scroll">
					<table class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
				        <c:forEach var="dto" items="${dpImpDtlForm.cList}">
							<tr>
					            <td class="tbl_page_td_left vtip" width="80px">${dto.fieldName}</td>
							  	<td class="tbl_page_td_left vtip" width="80px">${dto.colName}</td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
			</td>
			<td width="20" align="center">
				<button disabled><spring:message code="fisp.button.label.left"/></button><br/><br/>
			</td>
			<td align="right">
				<!-- all column in table -->
				<div class="tbl_page_head">
					<table class="table table-striped table-bordered table-condensed tbl_page">
						<thead>
				        <tr>
				        	<th class="tbl_page_th" width="20px"><spring:message code="fisp.label.common.no"/></th>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.colName"/></th>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.colDesc"/></th>
				          	<th class="tbl_page_th" width="40px"><spring:message code="fisp.label.common.colLen"/></th>
				          	<th width="3px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body" style="overflow-y:scroll">
					<table class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
				        <c:forEach var="dto" items="${dpImpDtlForm.tList}">
							<tr>
					          	<td class="tbl_page_td_left vtip" width="20px">${dto.colSeqNo}</td>
					            <td class="tbl_page_td_left vtip" width="80px">${dto.colName}</td>
							  	<td class="tbl_page_td_left vtip" width="80px">${dto.colDesc}</td>
					            <td class="tbl_page_td_left vtip" width="40px">${dto.colLen}</td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
			</td>
		</tr>
    </table>											
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>