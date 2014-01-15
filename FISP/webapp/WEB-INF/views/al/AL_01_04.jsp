<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sysAlertRcptForm">
			<form:form commandName="sysAlertRcptForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">任务管理 / 警报配置 / <spring:message code="index.lable.SysAlertPrctDetailSearch"/></div>

<!-- body -->

<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post"  traget="curWindow" modelAttribute="sysAlertRcptForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><spring:message code="al.label.projId"/></td>
				<td>
					<form:input path="projId" id="projId" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="al.label.branchId"/></td>
				<td>
					<form:input path="branchId" id="branchId" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="al.label.alertType"/></td>
				<td>
					<form:select id="alertType" path="alertType" class=".input-small" readonly="true" disabled="true">
						<form:options items="${AL_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="al.label.noticeMthd"/></td>
				<td>
					<form:select id="noticeMthd" path="noticeMthd" class=".input-small" readonly="true" disabled="true">
						<form:options items="${AL_0002}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="al.label.rcptAddr"/></td>
				<td>
					<form:textarea path="rcptAddr" id="rcptAddr" type="text" cols="10" rows="3" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="al.label.comments"/></td>
				<td>
					<form:textarea path="sysAlertRcpt.comments" id="comments" cols="10" rows="3" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="al.label.maker"/></td>
				<td>
					<form:input path="sysAlertRcpt.maker" id="maker" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="al.label.mkTime"/></td>
				<td>
					<form:input path="sysAlertRcpt.mkTime" id="mkTime" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="al.label.checker"/></td>
				<td>
					<form:input path="sysAlertRcpt.checker" id="checker" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="al.label.chkTime"/></td>
				<td>
					<form:input path="sysAlertRcpt.chkTime" id="chkTime" type="text" class=".input-small" readonly="true"/>
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