<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.OrganizationMaintenanceDetailSearch"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form_01DetailSearch" action="${pageContext.request.contextPath}/nsm01/05/detailSearch_01" method="post" modelAttribute="orgInfForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td width="30%" class="label_td"><spring:message code="index.label.sm.OrganizationId"/></td>
				<td width="20%">
					<form:input id="orgid" path="orgInf.orgid" type="text" class=".input-small" readonly="true"/>
				</td>
				<td  width="15%"class="label_td"><spring:message code="index.label.sm.OperationStatus"/></td>
				<td  width="35%">
					<t:codeValue items="${CL_0003}" key="${orgInfForm.orgInf.optstatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td width="30%" class="label_td"><spring:message code="index.label.sm.OrganizationName"/></td>
				<td colspan="3">
					<form:input id="orgname" path="orgInf.orgname" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.SuperiorOrganizationId"/></td>
				<td colspan="3">
					<form:input id="suprorgid" path="orgInf.suprorgid" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.SuperiorOrganizationName"/></td>
				<td colspan="3">
					<form:input id="suprorgname" path="orgInf.suprorgname" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			<!-- close by wy 2013-12-05
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.BankId"/></td>
				<td colspan="3">
					<form:input id="bankid" path="orgInf.bankid" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.BankName"/></td>
				<td colspan="3">
					<form:input path="orgInf.bankname" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			-->
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Creater"/></td>
				<td>
					<form:input path="orgInf.creater" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CreateTime"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${orgInfForm.orgInf.createtime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.LastOperator"/></td>
				<td>
					<form:input path="orgInf.lastoperator" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.LastOptTime"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${orgInfForm.orgInf.lastopttime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Checker"/></td>
				<td>
					<form:input path="orgInf.checker" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CheckTime"/></td>
				<td >
					<t:dateTimeFormat type="text" value="${orgInfForm.orgInf.checktime }" readonly="true" cssClass=".input-small" format="datetime"/>
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