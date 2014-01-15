
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.sysParam.detail"/></div>

<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="SM_Prm_QryForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td">
					<spring:message code="fisp.label.sysParam.group"/>
				</td>
				<td>
					<form:input id="paramGroup" path="sysParam.paramGroup" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td">
				<spring:message code="fisp.label.sysParam.code"/>
				</td>
				<td>
					<form:input id="paramCode" path="sysParam.paramCode" type="text" class=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.label.sysParam.val"/>
				</td>
				<td>
					<form:input id="paramVal" path="sysParam.paramVal" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td">
					<spring:message code="fisp.label.sysParam.name"/>
				</td>
				<td>
					<form:input id="paramName" path="sysParam.paramName" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td">
					<spring:message code="fisp.label.sysParam.desc"/>
				</td>
				<td colspan="3">
					<form:input id="paramDesc" path="sysParam.paramDesc" type="text" class="span6" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td">
				<spring:message code="fisp.label.common.createTime"/>
				</td>
				<td>
					<form:input id="createTime" path="sysParam.createTime" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.label.common.createUser"/>
				</td>
				<td>
					<form:input id="createUser" path="sysParam.createUser" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td">
					<spring:message code="fisp.label.common.updateTime"/>
				</td>
				<td>
					<form:input id="updateTime" path="sysParam.updateTime" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td">
					<spring:message code="fisp.label.common.updateUser"/>
				</td>
				<td>
					<form:input id="updateUser" path="sysParam.updateUser" type="text" class=".input-small" readonly="true"/>
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