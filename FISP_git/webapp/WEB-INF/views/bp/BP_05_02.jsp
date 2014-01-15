<script type="text/javascript">
	window.name="curWindow";

	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("select").prop("disabled", "disabled");
			$("#confirmBtn").attr("disabled", "disabled");
		}
	});
	//input button
	function input() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/bp05/02/modify";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.target="curWindow";
			form.submit();
		}else{
			return false;
		}
	}
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
<div class="page_title"><spring:message code="fisp.title.processedListDepositBalanceInput"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/bp05/02/search" method="post" modelAttribute="depositBalanceForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="depositBalanceId" type="hidden" path="depositBalance.id"/>
				<form:input id="customerType" type="hidden" path="depositBalance.customerType"/>
				<form:input id="rsv1" type="hidden" path="depositBalance.rsv1"/>
			</td></tr>
	    	<tr>
	    		<td class="label_td"><spring:message code="fisp.la.branchNo"/></td>
				<td>
					<form:input path="depositBalance.branch" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.workdate"/></td>
				<td>
					<form:input path="depositBalance.workDate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv1"/></td>
				<td colspan="3">
					<t:codeValue items="${DP_0019}" key="${depositBalanceForm.depositBalance.rsv1}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.customerNo"/></td>
				<td>
					<form:input path="customerNo" type="text" class=".input-small"/>
				</td>
				<td class="label_td"><spring:message code="fisp.la.customerType"/></td>
				<td>
					<form:select id="customerType" path="depositBalance.customerType" disabled="true" >
						<form:option value=""/>
						<form:options items="${DP_0016}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.customerName"/></td>
				<td colspan="3">
					<form:input path="depositBalance.customerName" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.depositAccCode"/></td>
				<td>
					<form:input path="depositAccCode" type="text" class=".input-small"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.depositAgreementCode"/></td>
				<td>
					<form:input path="depositAgreementCode" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.productType"/></td>
				<td colspan="3">
					<form:select id="productType" path="productType" >
						<form:option value=""/>
						<form:options items="${DP_0005}" />
					</form:select>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.depositAgreementSdate"/></td>
				<td>
					<form:input path="depositAgreementSdate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
				</td>
				<td class="label_td"><spring:message code="fisp.la.depositAgreementEdate"/></td>
				<td>
					<form:input path="depositAgreementEdate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.currency"/></td>
				<td>
					<form:select id="currency" path="depositBalance.currency" >
						<form:option value=""/>
						<form:options items="${DP_0014}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.depositBalanceDis"/></td>
				<td>
					<t:moneyFormat value="${depositBalanceForm.depositBalanceDis}" type="text" name="depositBalanceDis"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.interestRateFix"/></td>
				<td>
					<form:select id="interestRateFix" path="interestRateFix" >
						<form:option value=""/>
						<form:options items="${DP_0008}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.interestRate"/></td>
				<td>
					<t:moneyFormat value="${depositBalanceForm.interestRate}" dot="true" type="text" name="interestRate" percent="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.rsv2"/></td>
				<td colspan="3">
					<form:textarea path="depositBalance.rsv2" type="text" class="input-xxlarge" readonly="true" rows="6"  />
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
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="input()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>