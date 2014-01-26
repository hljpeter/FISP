<script type="text/javascript">
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210201Form">
			<form:form commandName="FTZ210201Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210201.qry.dtl.dtl" /></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/BMG_TASK_Qry/Qry"
		method="post" modelAttribute="FTZ210201Form" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO" />：</td>
				<td><form:input id="seqNo" path="ftzInTxnDtl.seqNo"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.CD_FLAG" />：</td>
				<td><form:select path="ftzInTxnDtl.cdFlag" disabled="true" >
						<form:option value=""></form:option>
						<form:options items="${FTZ_CD_FLAG}" />
					</form:select></td>

			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.TRAN_DATE" />：</td>
				<td><form:input id="tranDate" path="ftzInTxnDtl.tranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" disabled="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.ORG_TRAN_DATE" />：</td>
				<td><form:input id="orgTranDate" path="ftzInTxnDtl.orgTranDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" disabled="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font> <spring:message
						code="ftz.label.AMOUNT" />：</td>
				<td colspan="3"><t:moneyFormat type="text" id="amount"
						name="ftzInTxnDtl.amount"
						value="${FTZ210201Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true" maxlength="24" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.OPP_ACCOUNT" />：</td>
				<td><form:input id="oppAccount" path="ftzInTxnDtl.oppAccount"
						class=".input-large" disabled="true" /></td>
				<td class="label_td"><spring:message code="ftz.label.OPP_NAME1" />：</td>
				<td><form:input id="oppName" path="ftzInTxnDtl.oppName"
						class=".input-large" disabled="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* 	</font> <spring:message
						code="ftz.label.COUNTRY_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.countryCode" disabled="true" 
						id="countryCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_COUNTRY_CODE}" />
					</form:select>
				</td>	
				<td class="label_td"><spring:message
						code="ftz.label.DISITRICT_CODE" />：</td>
				<td><form:select path="ftzInTxnDtl.disitrictCode"  disabled="true" 
						id="disitrictCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE}" />
					</form:select>
				</td>
			</tr>
			<tr>
				
				<td class="label_td"><spring:message code="ftz.label.VALUE_DATES" />：</td>
				<td><form:input id="valueDate"
						path="ftzInTxnDtl.valueDate" class=".input-large" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  disabled="true" /></td>
				<td class="label_td"><spring:message code="ftz.label.EXPIRE_DATE" />：</td>
				<td><form:input id="expireDate"
						path="ftzInTxnDtl.expireDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large"  disabled="true" /></td>
			
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.INTEREST_RATESS" />：</td>
				<td><form:input id="interestRate" path="ftzInTxnDtl.interestRate" class=".input-large"  disabled="true" /></td>
				<td class="label_td"><font color="red">* </font> <spring:message
						code="ftz.label.TRAN_TYPE" />：</td>
				<td><form:select path="ftzInTxnDtl.tranType" id="tranType"  disabled="true" >
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_TYPE}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.TERM_LENGTH" />：</td>
				<td><form:input id="termLength" path="ftzInTxnDtl.termLength"  disabled="true" 
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.TERM_UNIT" />：</td>
				<td><form:select path="ftzInTxnDtl.termUnit"  disabled="true" >
						<form:option value=""></form:option>
						<form:options items="${FTZ_REBUY_TERM_UNIT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.personIdTypeS"/>：</td>
					<td><form:select path="ftzInTxnDtl.documentType" id="documentType"  disabled="true" >
					<form:option value=""></form:option>
					<form:options items="${FTZ_DOCUMENT_TYPE}" />
				</form:select></td>
	
				<td class="label_td"><spring:message code="fisp.la.personIdNoS"/>：</td>
				<td>
					<form:input id="documentNo" path="ftzInTxnDtl.documentNo" type="text" class=".input-small"  disabled="true" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message
							code="ftz.label.LOAN_TYPE" />:</td>
				<td><form:select path="ftzInTxnDtl.loanType" id="loanType"  disabled="true" >
					<form:option value=""></form:option>
					<form:options items="${FTZ_LOAN_TYP}" />
				</form:select></td>
				<td class="label_td"><font color="red">* </font><spring:message
							code="ftz.label.OVERDUE" />:</td>
				<td>
					<form:select path="ftzInTxnDtl.overdue" id="overdue" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_IS_OVERDUE}" />
					</form:select>
				</td>
			</tr>
			<tr><td colspan="4"><hr/></td></tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.DTL_STATUS" />：</td>
				<td colspan="3"><form:select path="ftzInTxnDtl.chkStatus" 
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.MAK_USER_IDS" />：</td>
				<td><form:input id="makUserId" path="ftzInTxnDtl.makUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.MAK_DATETIMES" />：</td>
				<td><form:input id="makDatetime" path="ftzInTxnDtl.makDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_USER_ID" />：</td>
				<td><form:input id="chkUserId" path="ftzInTxnDtl.chkUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_DATETIME" />：</td>
				<td><form:input id="chkDatetime" path="ftzInTxnDtl.chkDatetime"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message
						code="ftz.label.CHK_ADD_WORD" />：</td>
				<td colspan="3"><form:input id="chkAddWord"
						path="ftzInTxnDtl.chkAddWord" class="input-xxlarge"
						readonly="true" /></td>
			</tr>
		</table>

	</form:form>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>