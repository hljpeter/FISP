<script type="text/javascript">
$(function() {
	if ($("#fileType").find("option:selected").val() == 4) {
		$("#excel").css("display", "block");
	} else {
		$("#excel").css("display", "none");
	}
});
</script>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.file.dpFielDtl"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<table class="tbl_search">
    	<tr><td colspan="4">
    		<p class="text-info"><spring:message code="fisp.titls.dp.file.dpFielDtl.file"/></p>
    	</td></tr>
    	<tr>
			<td class="label_td" width="20%"><spring:message code="fisp.label.common.fileName"/></td>
			<td width="30%">
				<form:input path="dpFileDtlForm.dpFile.fileName" type="text" class=".input-medium" readonly="true"/>
			</td>
			
			<td class="label_td" width="20%"><spring:message code="fisp.label.common.fileType"/></td>
			<td width="30%">
				<form:select id="fileType" path="dpFileDtlForm.dpFile.fileType" disabled="true">
					<option value=""></option>
					<form:options items="${DP_FILE_FILETYPE}" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.fileDesc"/></td>
			<td colspan="3"><form:input path="dpFileDtlForm.dpFile.comments" type="text" class="input-xxlarge" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.delimiter"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.delimiter" type="text" class=".input-large" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.fixLenCfg"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.fixedLenCfg" type="text" class=".input-large" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.fileEncoding"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.fileEncoding" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.ioFlag"/></td>
			<td>
				<form:select path="dpFileDtlForm.dpFile.ioFlag" disabled="true">
					<option value=""></option>
					<form:options items="${DP_FILE_IOFLAG}" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.srowIgnrNum"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.srowIgnrNum" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.erowIgnrNum"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.erowIgnrNum" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.commitCount"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.commitCount" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.cutFlag"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.cutFlag" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr id="excel" style="display: none">
			<td colspan="4">
				<div class="row" style="margin-bottom: 40px;">
					<table class="tbl_search">
				    	<tr><td colspan="4">
				    		<p class="text-info"><spring:message code="fisp.titls.dp.file.dpFielDtl.file.excel"/></p>
				    	</td></tr>
				    	<tr>
							<td class="label_td"><spring:message code="fisp.label.common.sheetno"/></td>
							<td colspan="3"><form:input path="dpFileDtlForm.dpFile.sheetNo" type="text" class=".input-small" readonly="true"/></td>
						</tr>
				    	<tr>
							<td class="label_td"><spring:message code="fisp.label.common.startColumn"/></td>
							<td><form:input path="dpFileDtlForm.dpFile.startColumn" type="text" class=".input-small" readonly="true"/></td>
							
							<td class="label_td"><spring:message code="fisp.label.common.endColumn"/></td>
							<td><form:input path="dpFileDtlForm.dpFile.endColumn" type="text" class=".input-small" readonly="true"/></td>
						</tr>
				    </table>
				</div>
			</td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.createTime"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.createTime" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.createUser"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.createUser" type="text" class=".input-small" readonly="true"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.updateTime"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.updateTime" type="text" class=".input-small" readonly="true"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.updateUser"/></td>
			<td><form:input path="dpFileDtlForm.dpFile.updateUser" type="text" class=".input-small" readonly="true"/></td>
		</tr>
    </table>											
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="tbl_page_head">
	<table class="table table-striped table-bordered table-condensed tbl_page">
      <thead>
        <tr>
        	<th class="tbl_page_th" width="10px"><spring:message code="fisp.label.common.no"/></th>
          	<th class="tbl_page_th" width="50px"><spring:message code="fisp.label.common.fieldSeqNo"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.fieldName"/></th>
          	<th class="tbl_page_th" width="200px"><spring:message code="fisp.label.common.fieldDesc"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.fieldLen"/></th>
          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.cutFlag"/></th>
          	<th width="2px"></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body" style="overflow-y:scroll">
		<table class="table table-striped table-bordered table-condensed tbl_page">
		<tbody>
	        <c:forEach var="dto" items="${dpFileDtlForm.dpFileDtlList}" varStatus="i">
				<tr>
		          	<td class="tbl_page_td_left vtip" width="10px">${(page.number * page.size) + (i.index + 1)}</td>
				  	<td class="tbl_page_td_left vtip" width="50px">${dto.fieldSeqNo}</td>
				  	<td class="tbl_page_td_left vtip" width="100px">${dto.fieldName}</td>
		            <td class="tbl_page_td_left vtip" width="200px">${dto.fieldDesc}</td>
				  	<td class="tbl_page_td_left vtip" width="100px">${dto.fieldLen}</td>
				  	<td class="tbl_page_td_left vtip" width="80px">
		            	<t:codeValue items="${DP_FILE_CUTFLAG }" key="${dto.cutFlag}" type="label" />
		            </td>
				</tr>
	        </c:forEach>
		</tbody>
		</table>
	</div>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>