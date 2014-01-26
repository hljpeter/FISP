<script type="text/javascript">
	function showPrev() {
		var parent = window.dialogArguments;
		var no = parent.document.getElementById("NO").value;
		
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
	<br/>
</div>

<div class="page_title"><spring:message code="ftzmis.title.210501.qry.dtl"/></div>

<div class="row">
	<form:form id="form" action="" method="post" modelAttribute="FTZ210501Form" 
		class="form-horizontal">
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
						value="${FTZ210501Form.ftzActMstr.depositRate}" format="###.000000" dot="true" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.BALANCE"/>：</td>
				<td>
					<t:moneyFormat type="text" id="balance" name="ftzActMstr.balance"
						value="${FTZ210501Form.ftzActMstr.balance}" format="###,###,###,###.00" dot="true" readonly="true"/>
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
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstr.makUserId" 
						class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstr.makDatetime" 
						class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstr.chkUserId" 
						class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td>
					<form:input id="subAccountName" path="ftzActMstr.chkDatetime" 
						class=".input-large" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3">
					<form:input id="subAccountName" path="ftzActMstr.chkAddWord" 
						class="input-xxlarge" readonly="true"/>
				</td>
			</tr>			
		</table>
	</form:form>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>