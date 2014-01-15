<!-- title -->
<div class="page_title"><spring:message code="fisp.title.DataManageDepositBalanceInputDetailQuery"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/dp02/03/detailSearch" method="post" modelAttribute="depositBalanceForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><spring:message code="fisp.la.branchNo"/></td>
				<td>
					<form:input path="depositBalance.branch" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.workdate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${depositBalanceForm.depositBalance.workDate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv1"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0019}" key="${depositBalanceForm.depositBalance.rsv1}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerNo"/></td>
				<td>
					<form:input path="depositBalance.customerNo" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.customerType"/></td>
				<td>
					<t:codeValue items="${DP_0016}" key="${depositBalanceForm.depositBalance.customerType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerName"/></td>
				<td colspan="3">
					<form:input path="depositBalance.customerName" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.depositAccCode"/></td>
				<td>
					<form:input path="depositBalance.depositAccCode" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.depositAgreementCode"/></td>
				<td>
					<form:input path="depositBalance.depositAgreementCode" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.productType"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0005}" key="${depositBalanceForm.depositBalance.productType}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.depositAgreementSdate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${depositBalanceForm.depositBalance.depositAgreementSdate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.depositAgreementEdate"/></td>
				<td>
					<t:dateTimeFormat type="text" value="${depositBalanceForm.depositBalance.depositAgreementEdate}" format="date" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.currency"/></td>
				<td>
					<t:codeValue items="${DP_0014}" key="${depositBalanceForm.depositBalance.currency}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.depositBalanceDis"/></td>
				<td>
					<t:moneyFormat value="${depositBalanceForm.depositBalance.depositBalance}" type="text" name="depositBalanceDis" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.interestRateFix"/></td>
				<td>
					<t:codeValue items="${DP_0008}" key="${depositBalanceForm.depositBalance.interestRateFix}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.interestRate"/></td>
				<td>
					<t:moneyFormat value="${depositBalanceForm.depositBalance.interestRate}" dot="true" type="text" name="interestRate" readonly="true" percent="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv2"/></td>
				<td colspan="3">
					<form:textarea path="depositBalance.rsv2" type="text" class="input-xxlarge" readonly="true" rows="6"  />
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