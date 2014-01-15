<!-- title -->
<div class="page_title"><spring:message code="fisp.title.DataQueryLoanBalanceQueryDetail"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/dp04/07/detailSearch" method="post" modelAttribute="loanBalanceForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="loanBalanceId" type="hidden" path="loanBalance.id"/>
				<form:input id="workdate" type="hidden" path="loanBalance.workDate"/>
			</td></tr>
			<tr>
	    		<td class="label_td"  width="20%"><spring:message code="fisp.la.branchNo"/></td>
				<td width="30%">
					<form:input path="loanBalance.branch" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td" width="15%"><spring:message code="fisp.la.workdate"/></td>
				<td width="30%">
					<t:dateTimeFormat type="text" value="${loanBalanceForm.loanBalance.workDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv1"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0019}" key="${loanBalanceForm.loanBalance.rsv1}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerNo"/></td>
				<td>
					<form:input path="loanBalance.customerNo" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.customerType"/></td>
				<td>
					<t:codeValue items="${DP_0016}" key="${loanBalanceForm.loanBalance.customerType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerName"/></td>
				<td colspan="3">
					<form:input path="loanBalance.customerName" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanorganizationCode"/></td>
				<td colspan="3">
					<form:input path="loanBalance.organizationCode" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.personIdType"/></td>
				<td>
					<t:codeValue items="${DP_0013}" key="${loanBalanceForm.loanBalance.personIdType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.personIdNo"/></td>
				<td>
					<form:input path="loanBalance.personIdNo" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanIndustryType"/></td>
				<td>
					<t:codeValue items="${DP_0002}" key="${loanBalanceForm.loanBalance.loanIndustryType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.borrowerRegisterCode"/></td>
				<td>
					<form:input path="loanBalance.borrowerRegisterCode" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.ciEconomy"/></td>
				<td>
					<t:codeValue items="${DP_0003}" key="${loanBalanceForm.loanBalance.ciEconomy}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.scaleEnterprise"/></td>
				<td>	
					<t:codeValue items="${DP_0004}" key="${loanBalanceForm.loanBalance.scaleEnterprise}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanIouCode"/></td>
				<td>
					<form:input path="loanBalance.loanIouCode" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.productType"/></td>
				<td>
					<t:codeValue items="${DP_0005}" key="${loanBalanceForm.loanBalance.productType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanActualInvest"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0006}" key="${loanBalanceForm.loanBalance.loanActualInvest}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanOriginationDate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${loanBalanceForm.loanBalance.loanOriginationDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.loanMaturityDate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${loanBalanceForm.loanBalance.loanMaturityDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.extensionEdate"/></td>
				<td colspan="3">
					<t:dateTimeFormat type="text" value="${loanBalanceForm.loanBalance.extensionEdate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.currency"/></td>
				<td>
					<t:codeValue items="${DP_0014}" key="${loanBalanceForm.loanBalance.currency}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.LoanIouBalance"/></td>
				<td>
					<t:moneyFormat value="${loanBalanceForm.loanIouBalance}" type="text" name="LoanIouBalance" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.interestRateFix"/></td>
				<td>
					<t:codeValue items="${DP_0008}" key="${loanBalanceForm.loanBalance.interestRateFix}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.interestRate"/></td>
				<td>
					<t:moneyFormat value="${loanBalanceForm.interestRate}" dot="true" type="text" name="interestRate" readonly="true" percent="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanGuaranteeKind"/></td>
				<td>
					<t:codeValue items="${DP_0007}" key="${loanBalanceForm.loanBalance.loanGuaranteeKind}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.loanQuality"/></td>
				<td>
					<t:codeValue items="${DP_0018}" key="${loanBalanceForm.loanBalance.loanQuality}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanStatus"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0017}" key="${loanBalanceForm.loanBalance.status}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv2"/></td>
				<td colspan="3">
					<form:textarea path="loanBalance.rsv2" type="text" class="input-xxlarge" readonly="true" rows="6"  />
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