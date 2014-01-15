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
		form.action = "${pageContext.request.contextPath}/bp05/05/auth";
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
		form.action = "${pageContext.request.contextPath}/bp05/05/cancel";
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
		form.action = "${pageContext.request.contextPath}/bp05/05/reject";
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
		<spring:hasBindErrors name="depositBalanceForm">
			<form:form commandName="depositBalanceForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.title.processedListDepositBalanceAuthDetailQuery"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="detailSearchForm" action="${pageContext.request.contextPath}/bp05/05/detailSearch" method="post" modelAttribute="depositBalanceForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="depositBalanceId" type="hidden" path="depositBalance.id"/>
				<form:input id="workdate" type="hidden" path="depositBalance.workDate"/>
				<form:input id="rsv1" type="hidden" path="depositBalance.rsv1"/>
				<form:input id="depositAccType" type="hidden" path="depositBalance.depositAccType"/>
				<form:input id="productType" type="hidden" path="depositBalance.productType"/>
				<form:input id="customerType" type="hidden" path="depositBalance.customerType"/>
				<form:input id="depositAgreementSdate" type="hidden" path="depositBalance.depositAgreementSdate"/>
				<form:input id="depositAgreementEdate" type="hidden" path="depositBalance.depositAgreementEdate"/>
				<form:input id="currency" type="hidden" path="depositBalance.currency"/>
				<form:input id="depositBalance" type="hidden" path="depositBalance.depositBalance"/>
				<form:input id="interestRateFix" type="hidden" path="depositBalance.interestRateFix"/>
				<form:input id="interestRate" type="hidden" path="depositBalance.interestRate"/>
			</td></tr>
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
					<form:input path="depositBalance.rsv2" type="text" class="input-xxlarge" readonly="true"/>
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
		<c:if test="${depositBalanceForm.depositBalance.sinputStatus=='3'}">
			<input type="button" id="authCancelBtn" class="btn btn-primary" onclick="authCancel();" value="<spring:message code="button.lable.CheckCancel"/>">
		</c:if>
		<c:if test="${depositBalanceForm.depositBalance.sinputStatus=='2'}">
		<input type="button" id="authBtn" class="btn btn-primary" onclick="auth();" value="<spring:message code="button.lable.Check"/>">
		<input type="button" id="rejectBtn" class="btn btn-primary" onclick="reject();" value="<spring:message code="button.label.Reject"/>">
 		</c:if>
 		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>