<script type="text/javascript">
//init page
$(function() {
		$("select").attr("disabled", "disabled");
	}
);
</script>
<!-- title -->
<div class="page_title">任务管理  / 映射配置  / 导入映射配置 / 明细</div>
<!-- body -->
<div class="row form-horizontal" style="margin-bottom: 40px;">

		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="if.label.FILEID"/></td>
				<td>
					<form:input id="fileId" path="impFileCfg.fileId" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.PROJID"/></td>
				<td>
					<form:input id="projId" path="impFileCfg.projId" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><spring:message code="if.label.BRANCHID"/></td>
				<td>
					<form:input id="branchId" path="impFileCfg.branchId" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.BATCHNO"/></td>
				<td>
					<form:input id="batchNo" path="impFileCfg.batchNo" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.SEQNO"/></td>
				<td>
					<form:input id="seqeNo" path="impFileCfg.seqeNo" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.TABLENAME"/></td>
				<td>
					<form:input id="tableName" path="impFileCfg.tableName" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.FILENAME"/></td>
				<td>
					<form:input id="fileName" path="impFileCfg.fileName" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.FILEPATH"/></td>
				<td>
					<form:input id="filePath" path="impFileCfg.filePath" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.MANDATORYFLAG"/></td>
				<td>
					<form:select id="mandatoryFlag" path="impFileCfg.mandatoryFlag" class=".input-small" readonly="true" disabled="disabled">
						<form:options items="${IFC_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="if.label.FORMATTYPE"/></td>
				<td>
					<form:select id="formatType" path="impFileCfg.formatType" class=".input-small" readonly="true" disabled="disabled">
						<form:options items="${IFC_0002}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.DELIMITER"/></td>
				<td>
					<form:input id="delimiter" path="impFileCfg.delimiter" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.FIXEDLEN_CFG"/></td>
				<td>
					<form:input id="fixedLenCfg" path="impFileCfg.fixedLenCfg" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.UPDATEFLAG"/></td>
				<td>
					<form:select id="updateFlag" path="impFileCfg.updateFlag" class=".input-small" readonly="true" disabled="disabled">
						<form:options items="${IFC_0003}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="if.label.SROWIGNR_NUM"/></td>
				<td>
					<form:input id="srowIgnrNum" path="impFileCfg.srowIgnrNum" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.EROWIGNR_NUM"/></td>
				<td>
					<form:input id="erowIgnrNum" path="impFileCfg.erowIgnrNum" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.STARTCOLUMN"/></td>
				<td>
					<form:input id="startColumn" path="impFileCfg.startColumn" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.ENDCOLUMN"/></td>
				<td>
					<form:input id="endColumn" path="impFileCfg.endColumn" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.SHEETNUM"/></td>
				<td>
					<form:input id="sheetNum" path="impFileCfg.sheetNum" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.COMMITCOUNT"/></td>
				<td>
					<form:input id="commitCount" path="impFileCfg.commitCount" type="text" class=".input_small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="if.label.COMMENTS"/></td>
				<td>
					<form:input id="comments" path="impFileCfg.comments" type="text" class=".input_small" readonly="true"/>
				</td>
			</tr>
	   	</table>
</div>


<div class="row" >
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>


