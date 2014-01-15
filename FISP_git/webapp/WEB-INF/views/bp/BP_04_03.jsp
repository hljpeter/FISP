<!-- title -->
<div class="page_title"><spring:message code="fisp.title.processedListLoanAmountInputDetailQuery"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="detailSearchForm" action="${pageContext.request.contextPath}/dp01/03/detailSearch" method="post" modelAttribute="loanAmountForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td class="label_td" width="20%"><spring:message code="fisp.la.branchNo"/></td>
				<td width="30%">
					<form:input path="loanAmount.branch" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td" width="15%"><spring:message code="fisp.la.workdate"/></td>
				<td width="30%">
					<t:dateTimeFormat type="text" value="${loanAmountForm.loanAmount.workDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv1"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0019}" key="${loanAmountForm.loanAmount.rsv1}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerNo"/></td>
				<td>
					<form:input path="loanAmount.customerNo" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.customerType"/></td>
				<td>
					<t:codeValue items="${DP_0016}" key="${loanAmountForm.loanAmount.customerType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerName"/></td>
				<td colspan="3">
					<form:input path="loanAmount.customerName" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanorganizationCode"/></td>
				<td colspan="3">
					<form:input path="loanAmount.organizationCode" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.personIdType"/></td>
				<td>
					<t:codeValue items="${DP_0013}" key="${loanAmountForm.loanAmount.personIdType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.personIdNo"/></td>
				<td>
					<form:input path="loanAmount.personIdNo" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanIndustryType"/></td>
				<td>
					<t:codeValue items="${DP_0002}" key="${loanAmountForm.loanAmount.loanIndustryType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.borrowerRegisterCode"/></td>
				<td>
					<form:input path="loanAmount.borrowerRegisterCode" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.ciEconomy"/></td>
				<td>
					<t:codeValue items="${DP_0003}" key="${loanAmountForm.loanAmount.ciEconomy}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.scaleEnterprise"/></td>
				<td>	
					<t:codeValue items="${DP_0004}" key="${loanAmountForm.loanAmount.scaleEnterprise}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanIouCode"/></td>
				<td>
					<form:input path="loanAmount.loanIouCode" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.productType"/></td>
				<td>
					<t:codeValue items="${DP_0005}" key="${loanAmountForm.loanAmount.productType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanActualInvest"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0006}" key="${loanAmountForm.loanAmount.loanActualInvest}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanOriginationDate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${loanAmountForm.loanAmount.loanOriginationDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.loanMaturityDate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${loanAmountForm.loanAmount.loanMaturityDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.currency"/></td>
				<td>
					<t:codeValue items="${DP_0014}" key="${loanAmountForm.loanAmount.currency}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.loanIouAmount"/></td>
				<td>
					<t:moneyFormat value="${loanAmountForm.loanAmount.loanIouAmount}" type="text" name="loanIouAmount" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.interestRateFix"/></td>
				<td>
					<t:codeValue items="${DP_0008}" key="${loanAmountForm.loanAmount.interestRateFix}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.interestRate"/></td>
				<td>
					<t:moneyFormat value="${loanAmountForm.loanAmount.interestRate}"  dot="true"  type="text" name="interestRate" readonly="true" percent="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanGuaranteeKind"/></td>
				<td>
					<t:codeValue items="${DP_0007}" key="${loanAmountForm.loanAmount.loanGuaranteeKind}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.loanStatus"/></td>
				<td>
					<t:codeValue items="${DP_0017}" key="${loanAmountForm.loanAmount.status}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.loanRecoverySign"/></td>
				<td>
					<t:codeValue items="${DP_0010}" key="${loanAmountForm.loanAmount.loanRecoverySign}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.endDate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${loanAmountForm.loanAmount.endDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv2"/></td>
				<td colspan="3">
					<form:textarea path="loanAmount.rsv2" type="text" class="input-xxlarge" readonly="true" rows="6"  />
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