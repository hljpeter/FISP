
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
	
	//add button
	function add() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/dp01/06/add";
		form.target="curWindow";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
		form.submit();
		}
		else
		{
			return false;	
		}
		
	}
	
	function customerTypeChange(){
		var type=$("#customerType").val();
		if(type==0){
			$("#personIdType").attr("disabled", true);
			$("#personIdNo").attr("readonly", true);
			$("#organizationCode").attr("readonly", false);
			$("#borrowerRegisterCode").attr("readonly", false);
			$("#ciEconomy").attr("disabled", false);
			$("#scaleEnterprise").attr("disabled", false);
			$("#personIdType").val("");
			$("#personIdNo").val("");
		}else if(type==1){
			$("#organizationCode").attr("readonly", true);
			$("#personIdType").attr("disabled", false);
			$("#personIdNo").attr("readonly", false);
			$("#borrowerRegisterCode").attr("readonly", true);
			$("#ciEconomy").attr("disabled", true);
			$("#scaleEnterprise").attr("disabled", true);
			$("#organizationCode").val("");
			$("#borrowerRegisterCode").val("");
			$("#ciEconomy").val("");
			$("#scaleEnterprise").val("");
		}else{
			$("#personIdType").attr("disabled", true);
			$("#personIdNo").attr("readonly", true);
			$("#organizationCode").attr("readonly", true);
			$("#borrowerRegisterCode").attr("readonly", true);
			$("#ciEconomy").attr("disabled", true);
			$("#scaleEnterprise").attr("disabled", true);
			$("#personIdType").val("");
			$("#personIdNo").val("");
			$("#organizationCode").val("");
			$("#borrowerRegisterCode").val("");
			$("#ciEconomy").val("");
			$("#scaleEnterprise").val("");
		}
	}
	
	function loanIndustryTypeChange(){
		var loanIndustryType=$("#loanIndustryType").val();
		if(loanIndustryType==1){
			$("#borrowerRegisterCode").attr("readonly", true);
			$("#ciEconomy").attr("disabled", true);
			$("#scaleEnterprise").attr("disabled", true);
			$("#borrowerRegisterCode").val("");
			$("#ciEconomy").val("");
			$("#scaleEnterprise").val("");
		}else{
			$("#borrowerRegisterCode").attr("readonly", false);
			$("#ciEconomy").attr("disabled", false);
			$("#scaleEnterprise").attr("disabled", false);
		}
	}
	function loanRecoverySignChange(){
		var loanRecoverySign=$("#loanRecoverySign").val();
		if(loanRecoverySign==1){
			$("#endDate").attr("readonly", true);
			$("#endDate").val("");
		}else{
			$("#endDate").attr("readonly", false);
		}
	}
	function dateVerification(){
		var loanOriginationDate=$("#loanOriginationDate").val().replace(/-/g,"");
		var loanMaturityDate=$("#loanMaturityDate").val().replace(/-/g,"");
		var msg ='贷款到期日期不能小于贷款发放日期';
		if(loanMaturityDate<loanOriginationDate){
			document.getElementById("errorMsg").style.display="block";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			$("#loanMaturityDate").val("");
		}
	}
	function dateVerification1(){
		var loanMaturityDate=$("#loanMaturityDate").val().replace(/-/g,"");
		var endDate=$("#endDate").val().replace(/-/g,"");
		var msg ='贷款实际终止日期不能小于贷款到期日期';
		if(endDate<loanMaturityDate){
			document.getElementById("errorMsg").style.display="block";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			$("#endDate").val("");
		}
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
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
<div class="page_title"><spring:message code="fisp.title.DataManageLoanAmountAdd"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/dp01/02/search" method="post" modelAttribute="loanAmountForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="4">
				<form:input id="customerType" type="hidden" path="customerType"/>
				<form:input id="rsv1" type="hidden" path="loanAmount.rsv1"/>
			</td></tr>
	    	<tr>
				<td class="label_td" width="20%"><spring:message code="fisp.la.branchNo"/></td>
				<td width="30%">
					<form:input path="loanAmount.branch" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td" width="15%"><font color="red">*</font><spring:message code="fisp.la.workdate"/></td>
				<td width="30%">
					<form:input path="workdate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
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
					<form:input path="loanAmount.customerNo" type="text" class=".input-small"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.customerType"/></td>
				<td>
					<form:select id="customerType" path="customerType" onChange="customerTypeChange()"  disabled="true">
						<form:option value="" />
						<form:options items="${DP_0016}" />
					</form:select>
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
					<form:input id="organizationCode"  path="loanAmount.organizationCode" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.personIdType"/></td>
				<td>
					<form:select id="personIdType" path="loanAmount.personIdType" >
						<form:option value="" />
						<form:options items="${DP_0013}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="fisp.la.personIdNo"/></td>
				<td>
					<form:input id="personIdNo" path="loanAmount.personIdNo" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanIndustryType"/></td>
				<td>
					<form:select id="loanIndustryType" path="loanIndustryType">
						<form:option value="" />
						<form:options items="${DP_0002}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="fisp.la.borrowerRegisterCode"/></td>
				<td>
					<form:input id="borrowerRegisterCode" path="loanAmount.borrowerRegisterCode" type="text" class=".input-small"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="fisp.la.ciEconomy"/></td>
				<td>
					<form:select id="ciEconomy" path="loanAmount.ciEconomy" >
						<form:option value="" />
						<form:options items="${DP_0003}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="fisp.la.scaleEnterprise"/></td>
				<td>	
					<form:select id="scaleEnterprise" path="loanAmount.scaleEnterprise" >
						<form:option value="" />
						<form:options items="${DP_0004}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanIouCode"/></td>
				<td>
					<form:input path="loanIouCode" type="text" class=".input-small"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.productType"/></td>
				<td>
					<form:select id="productType" path="productType" >
						<form:option value="" />
						<form:options items="${DP_0005}" />
					</form:select>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanActualInvest"/></td>
				<td colspan="3">
					<form:select id="loanActualInvest" path="loanActualInvest" >
						<form:option value="" />
						<form:options items="${DP_0006}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanOriginationDate"/></td>
				<td>
					<form:input id="loanOriginationDate" path="loanOriginationDate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanMaturityDate"/></td>
				<td>
					<form:input id="loanMaturityDate"  path="loanMaturityDate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" onChange="dateVerification()"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.currency"/></td>
				<td>
					<form:select id="currency" path="currency" >
						<form:option value="" />
						<form:options items="${DP_0014}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanIouAmount"/></td>
				<td>
					<t:moneyFormat value="${loanAmountForm.loanIouAmount}" type="text" name="loanIouAmount"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.interestRateFix"/></td>
				<td>
					<form:select id="interestRateFix" path="interestRateFix" >
						<form:option value="" />
						<form:options items="${DP_0008}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.interestRate"/></td>
				<td>
					<t:moneyFormat value="${loanAmountForm.interestRate}"  dot="true"  type="text" name="interestRate" percent="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanGuaranteeKind"/></td>
				<td>
					<form:select id="loanGuaranteeKind" path="loanGuaranteeKind" >
						<form:option value="" />
						<form:options items="${DP_0007}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanStatus"/></td>
				<td>
					<form:select id="status" path="status" >
						<form:option value="" />
						<form:options items="${DP_0017}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="fisp.la.loanRecoverySign"/></td>
				<td>
					<form:select id="loanRecoverySign" path="loanRecoverySign" onChange="loanRecoverySignChange()">
						<form:option value="" />
						<form:options items="${DP_0010}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="fisp.la.endDate"/></td>
				<td>
					<form:input id="endDate"  path="endDate" type="text" maxlength="8" class="Wdate .input-small"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" onChange="dateVerification1()"/>
				</td>
			</tr>	
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="add()" value="<spring:message code="button.lable.Submit"/>">
		<input id="closeBtn" type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>