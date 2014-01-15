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
		form.action = "${pageContext.request.contextPath}/dp04/05/auth";
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
		form.action = "${pageContext.request.contextPath}/dp04/05/cancel";
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
		form.action = "${pageContext.request.contextPath}/dp04/05/reject";
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
		<spring:hasBindErrors name="loanBalanceForm">
			<form:form commandName="loanBalanceForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.DataManageloanBalanceAuthDetailQuery"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="detailSearchForm" action="${pageContext.request.contextPath}/dp04/05/detailSearch" method="post" modelAttribute="loanBalanceForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="loanBalanceId" type="hidden" path="loanBalance.id"/>
				<form:input id="rsv1" type="hidden" path="loanBalance.rsv1"/>
				<form:input id="personIdType" type="hidden" path="loanBalance.personIdType"/>
				<form:input id="loanIndustryType" type="hidden" path="loanBalance.loanIndustryType"/>
				<form:input id="productType" type="hidden" path="loanBalance.productType"/>
				<form:input id="customerType" type="hidden" path="loanBalance.customerType"/>
				<form:input id="ciEconomy" type="hidden" path="loanBalance.ciEconomy"/>
				<form:input id="scaleEnterprise" type="hidden" path="loanBalance.scaleEnterprise"/>
				<form:input id="loanActualInvest" type="hidden" path="loanBalance.loanActualInvest"/>
				<form:input id="extensionEdate" type="hidden" path="loanBalance.extensionEdate"/>
				<form:input id="loanGuaranteeKind" type="hidden" path="loanBalance.loanGuaranteeKind"/>
				<form:input id="loanQuality" type="hidden" path="loanBalance.loanQuality"/>
				<form:input id="loanOriginationDate" type="hidden" path="loanBalance.loanOriginationDate"/>
				<form:input id="loanMaturityDate" type="hidden" path="loanBalance.loanMaturityDate"/>
				<form:input id="currency" type="hidden" path="loanBalance.currency"/>
				<form:input id="LoanIouBalance" type="hidden" path="loanBalance.LoanIouBalance"/>
				<form:input id="interestRateFix" type="hidden" path="loanBalance.interestRateFix"/>
				<form:input id="interestRate" type="hidden" path="loanBalance.interestRate"/>
				<form:input id="status" type="hidden" path="loanBalance.status"/>
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
		<c:if test="${loanBalanceForm.loanBalance.sinputStatus=='3'}">
			<input type="button" id="authCancelBtn" class="btn btn-primary" onclick="authCancel();" value="<spring:message code="button.lable.CheckCancel"/>">
		</c:if>
		<c:if test="${loanBalanceForm.loanBalance.sinputStatus=='2'}">
		<input type="button" id="authBtn" class="btn btn-primary" onclick="auth();" value="<spring:message code="button.lable.Check"/>">
		<input type="button" id="rejectBtn" class="btn btn-primary" onclick="reject();" value="<spring:message code="button.label.Reject"/>">
 		</c:if>
 		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>