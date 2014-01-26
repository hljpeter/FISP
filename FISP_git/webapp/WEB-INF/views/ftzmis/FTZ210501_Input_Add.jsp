<script type="text/javascript">
$(function(){
	var success = "${successmsg}";
	if ("" != success && null != success) {
		$("input").attr("disabled", true);
		$("select").attr("disabled", true);
		$("#actSubmit").val('<spring:message
				code="button.lable.AddMore" />');
		var actAdd = document.getElementById("actSubmit");
		actAdd.onclick = addRef;
	} 
});
	
	function qryBalCode() {
		showSelBalance([{
			"balanceCode" : "param1"
		}]);
	};

	//submit button
	function actSubmit(){
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#depositRate").val($("#depositRate").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210501/Insert";
		form.submit();
	}
	
	function addRef(){
		var form = document.getElementById("form1");
		form.action = "${pageContext.request.contextPath}/FTZ210501/InputInit";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
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
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210501.input.add"/></div>

<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210501/Insert" 
		method="post" modelAttribute="FTZ210501Form" class="form-horizontal">
		
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.branchId">
						<option value=""></option>
						<form:options items="${SM_0002 }"/>
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCOUNT_NAME"/>：</td>
				<td>
					<form:input id="accountName" path="ftzActMstrTmp.accountName" class=".input-large" maxlength="128"/>
				</td>
			</tr>
			<tr>
				<td class="label_td" colspan="2"><font color="red">*</font><spring:message code="ftz.label.DEPT_TYPE"/>：
				
					<form:select path="ftzActMstrTmp.deptType">
						<option value=""></option>
						<form:options items="${FTZ_DEPT_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td" colspan="2"><font color="red">*</font><spring:message code="ftz.label.BALANCE_CODE"/>：
					<form:select id="balanceCode" path="ftzActMstrTmp.balanceCode">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}"/>
					</form:select>
					<input id="detail" type="button" class="btn btn-primary" 
						onclick="qryBalCode();" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCOUNT_NO"/>：</td>
				<td>
					<form:input id="accountNo" path="ftzActMstrTmp.accountNo" class=".input-large" maxlength="35"
						onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SUB_ACCOUNT_NO"/>：</td>
				<td>
					<form:input id="subAccountNo" path="ftzActMstrTmp.subAccountNo" class=".input-large" maxlength="20"
						onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.currency">
						<option value=""></option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACC_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.accType">
						<option value=""></option>
						<form:options items="${FTZ_ACC_TYPE}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DOCUMENT_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.documentType">
						<option value=""></option>
						<form:options items="${FTZ_DOCUMENT_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DOCUMENT_NO"/>：</td>
				<td>
					<form:input id="documentNo" path="ftzActMstrTmp.documentNo" class=".input-large" maxlength="20"
						onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CUSTOM_TYPE"/>：</td>
				<td>
					<form:select path="ftzActMstrTmp.customType">
						<option value=""></option>
						<form:options items="${FTZ_CUSTOM_TYPE}"/>
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACC_ORG_CODE"/>：</td>
				<td>
					<form:input id="accOrgCode" path="ftzActMstrTmp.accOrgCode" class=".input-large" maxlength="12"
						onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.DEPOSIT_RATE"/>：</td>
				<td>
					<t:moneyFormat type="text" id="depositRate" name="ftzActMstrTmp.depositRate"
						value="${FTZ210501Form.ftzActMstrTmp.depositRate}" format="###.000000" dot="true" maxlength="10"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.BALANCE"/>：</td>
				<td>
					<t:moneyFormat type="text" id="balance" name="ftzActMstrTmp.balance"
						value="${FTZ210501Form.ftzActMstrTmp.balance}" format="###,###,###,###.00" dot="true" maxlength="24"/>
				</td>
			</tr>		
		<tr>
				<td colspan="4"><hr /></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACC_STATUS"/>：</td>
				<td colspan="3">
					<form:select path="ftzActMstrTmp.accStatus" disabled="true">
						<option value=""></option>
						<form:options items="${FTZ_ACC_STATUS}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td>
					<form:input id="makUserId" path="ftzActMstrTmp.makUserId" 
						class=".input-large" readonly="true" />
				</td>
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td>
					<form:input id="makDatetime" path="ftzActMstrTmp.makDatetime" 
						class=".input-large" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td>
					<form:input id="chkUserId" path="ftzActMstrTmp.chkUserId" 
						class=".input-large" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td>
					<form:input id="chkDatetime" path="ftzActMstrTmp.chkDatetime" 
						class=".input-large" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3">
					<form:input id="chkAddWord" path="ftzActMstrTmp.chkAddWord" 
						class="input-xxlarge" readonly="true"/>
				</td>
			</tr>			
		</table>
	</form:form>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="actSubmit" type="button" class="btn btn-primary"
			onclick="actSubmit();" value="<spring:message code="button.lable.Submit"/>">
		<button name="btn" class="btn btn-primary" onclick="javascript: window.close();">
		<spring:message code="button.lable.close"/></button>
	</div>
</div>
<form:form id="form1" modelAttribute="FTZ210501Form">
</form:form>