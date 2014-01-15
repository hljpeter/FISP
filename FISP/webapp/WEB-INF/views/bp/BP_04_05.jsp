<script type="text/javascript">
<!--
	window.name="curWindow";
	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("#authBtn").attr("disabled", "disabled");
			$("#rejectBtn").attr("disabled", "disabled");
			$("#authCancelBtn").attr("disabled", "disabled");
		}
	});
	//authroize button
	function auth() {
		var form = document.getElementById("detailSearchForm");
		form.action = "${pageContext.request.contextPath}/bp04/05/auth";
		var msg=$("#confirmMsg1").val()+$("#authBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
	}
	//authroize button
	function authCancel() {
		var form = document.getElementById("detailSearchForm");
		form.action = "${pageContext.request.contextPath}/bp04/05/cancel";
		var msg=$("#confirmMsg1").val()+$("#authCancelBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
	}
	//reject button
	function reject() {
		var form = document.getElementById("detailSearchForm");
		form.action = "${pageContext.request.contextPath}/bp04/05/reject";
		var msg=$("#confirmMsg1").val()+$("#rejectBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
	}
//-->
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="loanAmountForm">
			<form:form commandName="loanAmountForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.processedListLoanAmountAuthDetailQuery"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="detailSearchForm" action="${pageContext.request.contextPath}/bp04/05/detailSearch" method="post" modelAttribute="loanAmountForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="loanAmountId" type="hidden" path="loanAmount.id"/>
				<form:input id="workDate" type="hidden" path="loanAmount.workDate"/>
				<form:input id="rsv1" type="hidden" path="loanAmount.rsv1"/>
				<form:input id="personIdType" type="hidden" path="loanAmount.personIdType"/>
				<form:input id="loanIndustryType" type="hidden" path="loanAmount.loanIndustryType"/>
				<form:input id="productType" type="hidden" path="loanAmount.productType"/>
				<form:input id="customerType" type="hidden" path="loanAmount.customerType"/>
				<form:input id="ciEconomy" type="hidden" path="loanAmount.ciEconomy"/>
				<form:input id="scaleEnterprise" type="hidden" path="loanAmount.scaleEnterprise"/>
				<form:input id="loanActualInvest" type="hidden" path="loanAmount.loanActualInvest"/>
				<form:input id="loanGuaranteeKind" type="hidden" path="loanAmount.loanGuaranteeKind"/>
				<form:input id="loanOriginationDate" type="hidden" path="loanAmount.loanOriginationDate"/>
				<form:input id="loanMaturityDate" type="hidden" path="loanAmount.loanMaturityDate"/>
				<form:input id="currency" type="hidden" path="loanAmount.currency"/>
				<form:input id="loanIouAmount" type="hidden" path="loanAmount.loanIouAmount"/>
				<form:input id="interestRateFix" type="hidden" path="loanAmount.interestRateFix"/>
				<form:input id="interestRate" type="hidden" path="loanAmount.interestRate"/>
				<form:input id="status" type="hidden" path="loanAmount.status"/>
				<form:input id="loanRecoverySign" type="hidden" path="loanAmount.loanRecoverySign"/>
			</td></tr>
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
			<tr>
				<td class="label_td"><spring:message code="fisp.la.comment"/></td>
				<td colspan="3">
					<form:input path="comment" type="text" class="input-xxlarge"/>
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<c:if test="${loanAmountForm.loanAmount.sinputStatus=='3'}">
			<input type="button" id="authCancelBtn" class="btn btn-primary" onclick="authCancel();" value="<spring:message code="button.lable.CheckCancel"/>">
		</c:if>
		<c:if test="${loanAmountForm.loanAmount.sinputStatus=='2'}">
		<input type="button" id="authBtn" class="btn btn-primary" onclick="auth();" value="<spring:message code="button.lable.Check"/>">
		<input type="button" id="rejectBtn" class="btn btn-primary" onclick="reject();" value="<spring:message code="button.label.Reject"/>">
 		</c:if>
 		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>