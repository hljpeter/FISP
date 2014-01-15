<!-- title -->
<div class="page_title"><spring:message code="Title_BM_Biz_Data_Dtl"/></div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="BM_Biz_DataForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="label_BRANCH_NAME"/></td>
				<td>
					<t:codeValue items="${BM_1001}" key="${BM_Biz_DataForm.sysChgBizLog.branchId}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="label_FUNC_NAME"/></td>
				<td>
					<t:codeValue items="${BM_1002}" key="${BM_Biz_DataForm.sysChgBizLog.funcId}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="label_USER_ID"/></td>
				<td colspan="3">
					<form:input path="sysChgBizLog.userId" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="label_USER_NAME_CN"/></td>
				<td colspan="3">
					<form:input path="sysChgBizLog.userNameCn" type="text" class="span6" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="label_OPER_DATE"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${BM_Biz_DataForm.sysChgBizLog.operDate }" readonly="true" cssClass=".input-small" format="date"/>
				</td>
				<td class="label_td"><spring:message code="label_OPER_TIME"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${BM_Biz_DataForm.sysChgBizLog.operTime }" readonly="true" cssClass=".input-small" format="shortTime"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="label_OPER_TYPE"/></td>
				<td colspan="3">
					<t:codeValue items="${BM_1003}" key="${BM_Biz_DataForm.sysChgBizLog.operType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="label_DATA_BEFORE"/></td>
				<td colspan="3">
					<form:textarea path="sysChgBizLog.dataBefore" class="span9" rows="7" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="label_DATA_AFTER"/></td>
				<td colspan="3">
					<form:textarea path="sysChgBizLog.dataAfter" class="span9" rows="7" readonly="true"/>
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
