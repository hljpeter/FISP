<div class="page_title">
	<spring:message code="sysrunner.title.BatchMonitorWorkInfo" />
</div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form"
		action="${pageContext.request.contextPath}/bm01/03/stepModify"
		method="post" modelAttribute="batchMonitorForm" class="form-horizontal">

		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="if.label.PROJID" /></td>
				<td><form:input id="projId" path="dpExpLog.projId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="if.label.BRANCHID" /></td>
				<td><form:input id="branchId" path="dpExpLog.branchId"
						class=".input-large" disabled="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="if.label.BATCHNO" /></td>
				<td><form:input id="batchNo" path="dpExpLog.batchNo"
						class=".input-large" disabled="true" /></td>
				<td class="label_td"><spring:message
						code="if.label.SEQNO" /></td>
				<td><form:input id="seqNo"
						path="dpExpLog.seqNo" disabled="true"/></td>

			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.WorkDate" /></td>
				<td><form:input id="workDate" path="dpExpLog.workDate"
						class=".input-large" disabled="true"/></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.WorkResult" /></td>
				<td><form:select id="expResult" path="dpExpLog.expResult" disabled="true">
						<form:options items="${BMG_FLAG}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="de.label.srcTable" /></td>
				<td><form:input id="tableName" path="dpExpLog.tableName"
						class=".input-large" disabled="true"/></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.destFileName" /></td>
				<td><form:input id="fileName" path="dpExpLog.fileName"
						class=".input-large" disabled="true"/></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message
						code="if.label.FILEPATH" /></td>
				<td colspan="3"><form:input id="filePath" path="dpExpLog.filePath"
						class="input-xxlarge" disabled="true"/></td>
			</tr>
			
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.errorInfo" /></td>
				<td colspan="3"><form:input id="expErrInfo" path="dpExpLog.expErrInfo"
						class="input-xxlarge" disabled="true"/></td>
			</tr>


		</table>
	</form:form>
</div>



<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>