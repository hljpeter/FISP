<script type="text/javascript">
$(function(){
	var authFlag = '${authFlag}';
	if (authFlag == '1'){
		$("#authPass").attr("disabled", true);
		$("#authRefuse").attr("disabled", true);
		$("#chkAddWord").attr("readonly", true);
	}
});
function authPass() {
	$("#balance").val($("#balance").val().replaceAll(",", ""));
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210501/AuthSubmit?authStat=1";
	form.submit();
}

function authRefuse() {
	$("#balance").val($("#balance").val().replaceAll(",", ""));
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210501/AuthSubmit?authStat=0";
	form.submit();
}
</script>
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error"/>
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info"/>
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success"/>
		<spring:hasBindErrors name="FTZ210501Form">
			<form:form commandName="FTZ210501Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210501.auth.dtl"/></div>

<div class="row">
	<form:form id="form" action="" method="post" 
		modelAttribute="FTZ210501Form" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.branchId" disabled="true">
						<option value=""></option>
						<form:options items="${SM_0002}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME"/>：</td>
				<td>
					<form:input id="accountName" path="ftzActMstrTmp.accountName" class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td" colspan="2"><spring:message code="ftz.label.DEPT_TYPE"/>：
					<form:select path="ftzActMstrTmp.deptType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_DEPT_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.BALANCE_CODE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.balanceCode" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NO"/>：</td>
				<td>
					<form:input id="accountNo" path="ftzActMstrTmp.accountNo" class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="ftz.label.SUB_ACCOUNT_NO"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstrTmp.subAccountNo" class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.currency" disabled="true">
						<option value=""></option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACC_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.accType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_ACC_TYPE}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.DOCUMENT_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.documentType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_DOCUMENT_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.DOCUMENT_NO"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstrTmp.documentNo" class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CUSTOM_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.customType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_CUSTOM_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACC_ORG_CODE"/>：</td>
				<td>
					<form:input id="accOrgCode" path="ftzActMstrTmp.accOrgCode" class=".input-large" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.AMOUNT"/>：</td>
				<td colspan="3">
					<t:moneyFormat type="text" id="balance" name="ftzActMstrTmp.balance"
						value="${FTZ210501Form.ftzActMstrTmp.balance}" format="###,###,###,###.00" dot="true" readonly="true"/>
				</td>
			</tr>		
		</table>
		
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACC_STATUS"/>：</td>
				<td colspan="3">
					<form:select path="ftzActMstrTmp.accStatus" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_ACC_STATUS}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td>
					<form:input id="makUserId" path="ftzActMstrTmp.makUserId" class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td>
					<form:input id="makDatetime" path="ftzActMstrTmp.makDatetime" class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td>
					<form:input id="chkUserId" path="ftzActMstrTmp.chkUserId" class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td>
					<form:input id="chkDatetime" path="ftzActMstrTmp.chkDatetime" class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3">
					<form:input id="chkAddWord" path="ftzActMstrTmp.chkAddWord" 
						class="input-xxlarge"/>
				</td>
			</tr>			
		</table>
	</form:form>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer" 
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="authPass" type="button" class="btn btn-primary" onclick="authPass();" 
			value="<spring:message code="ftz.label.AUTH"/>">
		<input id="authRefuse" type="button" class="btn btn-primary" onclick="authRefuse();" 
			value="<spring:message code="ftz.label.UNAUTH"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" 
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>