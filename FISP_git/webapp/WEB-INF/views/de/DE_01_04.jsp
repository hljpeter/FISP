<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dtTableCfgForm">
			<form:form commandName="dtTableCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"> 任务管理 / 数据映射配置 / <spring:message code="index.lable.DataExtractDetailSearch"/></div>

<!-- body -->

<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/de01/02/init" method="post"  traget="curWindow" modelAttribute="dtTableCfgForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="de.label.projId"/></td>
				<td>
					<form:input id="projId" path="dtTableCfg.projId" type="text" class=".input-small" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="de.label.branchId"/></td>
				<td>
					<form:input id="branchId" path="dtTableCfg.branchId" type="text" class=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.batchNo"/></td>
				<td>
					<form:input id="batchNo" path="dtTableCfg.batchNo" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="de.label.seqNo"/></td>
				<td>
					<form:input id="seqNo" path="dtTableCfg.seqNo" type="text" class=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="de.label.destTable"/></td>
				<td>
					<form:input id="destTable" path="dtTableCfg.destTable" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="de.label.srcTable"/></td>
				<td>
					<form:input id="srcTable" path="dtTableCfg.srcTable" type="text" class=".input-small" readonly="true"/>
				</td>
			<tr>
			
	    	<tr>
				<td class="label_td"><spring:message code="de.label.procType"/></td>
				<td>
					<form:select id="procType" path="dtTableCfg.procType" class=".input-small"  readonly="true"  disabled="true" >
						<form:options items="${DE_0001}" />
					</form:select>
			
				</td>
				<td class="label_td"><spring:message code="de.label.procCfg"/></td>
				<td>
					<form:textarea id="procCfg" path="dtTableCfg.procCfg" type="text" rows="3" class=".input-small" readonly="true"/>
				</td>
	    	</tr>
	    	<tr>
				<td class="label_td"><spring:message code="de.label.comments"/></td>
				<td colspan="3">
					<form:textarea id="comments" path="dtTableCfg.comments" type="text" rows="3" class=".input-small" style="width: 666px;" readonly="true"/>
				</td>
	    	</tr>
	    </table>											
	</form:form>
	
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>