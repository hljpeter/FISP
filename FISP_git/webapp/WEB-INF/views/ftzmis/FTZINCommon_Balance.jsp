<script type="text/javascript">

</script>



<div class="page_title"><spring:message code="ftzmis.title.FtzCommonBalance.result"/></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210501/Insert" 
		method="post" modelAttribute="ftzInCommonForm" class="form-horizontal">
		
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID"/>：</td>
				<td>
					<form:select path="ftzActMstr.branchId" disabled="true">
						<option value=""></option>
						<form:options items="${SM_0002}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME"/>：</td>
				<td>
					<form:input id="accountName" path="ftzActMstr.accountName" class=".input-large" 
						maxlength="128" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td" colspan="2"><spring:message code="ftz.label.DEPT_TYPE"/>：
					<form:select path="ftzActMstr.deptType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_DEPT_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.BALANCE_CODE"/>：</td>
				<td>
					<form:select path="ftzActMstr.balanceCode" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NO"/>：</td>
				<td>
					<form:input id="accountNo" path="ftzActMstr.accountNo" class=".input-large" 
						maxlength="35" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.SUB_ACCOUNT_NO"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstr.subAccountNo" class=".input-large" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzActMstr.currency" disabled="true">
						<option value=""></option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACC_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstr.accType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_ACC_TYPE}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.DOCUMENT_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstr.documentType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_DOCUMENT_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.DOCUMENT_NO"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstr.documentNo" class=".input-large" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CUSTOM_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstr.customType" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_CUSTOM_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACC_ORG_CODE"/>：</td>
				<td>
					<form:input id="accOrgCode" path="ftzActMstr.accOrgCode" class=".input-large" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DEPOSIT_RATE"/>：</td>
				<td>
					<t:moneyFormat type="text" id="depositRate" name="ftzActMstr.depositRate"
						value="${ftzInCommonForm.ftzActMstr.depositRate}" format="###.000000" dot="true" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.BALANCE"/>：</td>
				<td>
					<t:moneyFormat type="text" id="balance" name="ftzActMstr.balance"
						value="${ftzInCommonForm.ftzActMstr.balance}" format="###,###,###,###.00" dot="true" readonly="true"/>
				</td>
			</tr>		
			<tr>
				<td colspan="4"><hr /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACC_STATUS"/>：</td>
				<td colspan="3">
					<form:select path="ftzActMstr.accStatus" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_ACC_STATUS}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="submitDate" path="check_SubmitDate"
						class=".input-large" disabled="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.beginBalance" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="ftzActMstr.balance"
						value="${ftzInCommonForm.ftzActMstr.balance}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.chargeOffBalance" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="balance_expend"
						value="${ftzInCommonForm.balance_expend}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
				<td class="label_td"><spring:message
					code="ftz.label.entryBalance" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="balance_enter"
						value="${ftzInCommonForm.balance_enter}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.chargeOffBalanceReverse" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="balance_expendflushes"
						value="${ftzInCommonForm.balance_expendflushes}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
				<td class="label_td"><spring:message
					code="ftz.label.entryBalanceReverse" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="balance_enterflushes"
						value="${ftzInCommonForm.balance_enterflushes}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.balanceCalResult" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="balance_current"
						value="${ftzInCommonForm.balance_current}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
				<td class="label_td"><spring:message
					code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="ftzActMstr.balance"
						value="${ftzInCommonForm.ftzActMstr.balance}" format="###,###,###,###.00" dot="true" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.balanceContinuity"/>：</td>
				<td>
					<form:input id="balance_result" path="balance_result" class=".input-large" maxlength="12" readonly="true"/>
				</td>
			</tr>		
		</table>
	</form:form>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		
		<button name="btn" class="btn btn-primary" onclick="javascript: window.close();">
		<spring:message code="button.lable.close"/></button>
	</div>
</div>
<form:form id="form1" modelAttribute="ftzInCommonForm">
</form:form>