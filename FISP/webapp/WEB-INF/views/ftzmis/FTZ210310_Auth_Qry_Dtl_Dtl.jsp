<script type="text/javascript">
$(function() {
	var chkStatus = $("#chkStatus").val();
	var authFinishFlag = $("#authFinishFlag").val();
	if("1"==authFinishFlag || "03"==chkStatus || "04"==chkStatus){
		$("#authPass").attr("disabled","disabled");
		if( "04"==chkStatus){
			$("#authRefuse").attr("disabled","disabled");
			$("#chkAddWord").attr("disabled","true");
		} 
		
	}else if(""==authFinishFlag){
		$("#authPass").removeAttr("disabled");
		$("#authRefuse").removeAttr("disabled");
		$("#chkAddWord").removeAttr("readonly");
	}
	
	var fatherStatus = '${fatherStatus }';
	if ('03' == fatherStatus) {
		$("input").attr("disabled", true);
		$("select").attr("disabled", true);
		$("#clswin").removeAttr("disabled");
	}
});

function authPass() {
	$("#buyAmt").val($("#buyAmt").val().replaceAll(",", ""));
	$("#sellAmt").val($("#sellAmt").val().replaceAll(",", ""));
	$("#buyRate").val($("#buyRate").val().replaceAll(",", ""));
	$("#sellRate").val($("#sellRate").val().replaceAll(",", ""));
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210310/AuthDtlDtlSubmit?authStat=1";
	form.submit();
}

function authRefuse() {
	$("#buyAmt").val($("#buyAmt").val().replaceAll(",", ""));
	$("#sellAmt").val($("#sellAmt").val().replaceAll(",", ""));
	$("#buyRate").val($("#buyRate").val().replaceAll(",", ""));
	$("#sellRate").val($("#sellRate").val().replaceAll(",", ""));
	var form = document.getElementById("form");
	form.action = "${pageContext.request.contextPath}/FTZ210310/AuthDtlDtlSubmit?authStat=0";
	form.submit();
}
</script>
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210310Form">
			<form:form commandName="FTZ210310Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210310.auth.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}/FTZ210310/AddDtlDtlSubmit" 
		method="post" modelAttribute="FTZ210310Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId"/>
		<form:hidden path="authFinishFlag" id="authFinishFlag"/>
		<form:hidden path="ftzOffTxnDtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzOffTxnDtl.chkDatetime" id="chkDatetime" />
		<form:hidden path="father_makTime" />
		<form:hidden path="father_chkTime" />
		<table class="tbl_search">
			<tr>
	    		<td class="label_td"><spring:message code="ftz.label.SEQ_NO1"/>：</td><!-- 申报序号 -->
				<td><form:input id="seqNo" path="ftzOffTxnDtl.seqNo" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.SUBMIT_DATE"/>：</td><!-- 申报日期 -->
				<td>
					<form:input id="submitDate" path="ftzOffTxnDtl.submitDate" class="input-large date" readonly="true"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.ACCORGCODE"/>：</td><!-- 所属机构代码 -->
				<td>
					<form:select path="ftzOffTxnDtl.accOrgCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}"/>
					</form:select>
				</td>
	
				<td class="label_td"><spring:message code="ftz.label.TRAN_GENRE"/>：</td><!-- 结售汇交易类型 -->
				<td>
					<form:select path="ftzOffTxnDtl.tranGenre" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_GENRE}"/>
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.BUY_CURR"/>：</td><!--买入币种 -->
				<td>
					<form:select path="ftzOffTxnDtl.buyCurr" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.SELL_CURR"/>：</td><!-- 卖出币种 -->
				<td>
					<form:select path="ftzOffTxnDtl.sellCurr" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}"/>
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.BUY_AMT"/>：</td><!-- 买入金额 -->
				<td>
					<t:moneyFormat type="text" id="buyAmt" name="ftzOffTxnDtl.buyAmt"
						value="${FTZ210310Form.ftzOffTxnDtl.buyAmt}" format="###,###,###,###.00" dot="true" readonly="true"/>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.SELL_AMT"/>：</td><!-- 卖出金额 -->
				<td>
					<t:moneyFormat type="text" id="sellAmt" name="ftzOffTxnDtl.sellAmt"
						value="${FTZ210310Form.ftzOffTxnDtl.sellAmt}" format="###,###,###,###.00" dot="true" readonly="true"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.BUY_RATE"/>：</td><!-- 买入牌价 -->
				<td>
					<t:moneyFormat type="text" id="buyRate" name="ftzOffTxnDtl.buyRate"
						value="${FTZ210310Form.ftzOffTxnDtl.buyRate}" format="###.000000" dot="true" readonly="true"/>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.SELL_RATE"/>：</td><!-- 卖出牌价 -->
				<td>
					<t:moneyFormat type="text" id="sellRate" name="ftzOffTxnDtl.sellRate"
						value="${FTZ210310Form.ftzOffTxnDtl.sellRate}" format="###.000000" dot="true" readonly="true"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.SETTLE_DAY1"/>：</td><!-- 交割日1 -->
				<td>
					<form:input path="ftzOffTxnDtl.tranDate" type="text" class="input-large date" readonly="true"/>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.SETTLE_DAY2"/>：</td><!-- 交割日2 -->
				<td>
					<form:input path="ftzOffTxnDtl.expirationDate" type="text" class="input-large date" readonly="true"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td" colspan="2"><spring:message code="ftz.label.OPP_BANK_CODE"/>：<!-- 对方银行或机构代码 -->
				
					<form:input id="oppBankCode" path="ftzOffTxnDtl.institutionCode" class="input-large" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.OPP_BANK_NAME"/>：</td><!-- 对方银行名称 -->
				<td><form:input id="oppBankName" path="ftzOffTxnDtl.oppBankName" type="text" class="input-xlarge" readonly="true"/></td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.OPP_ACCOUNT"/>：</td><!-- 对方账号 -->
				<td>
					<form:input path="ftzOffTxnDtl.accountNo" type="text" class="input-large" readonly="true"/>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.OPP_NAME1"/>：</td><!-- 对方户名 -->
				<td>
					<form:input path="ftzOffTxnDtl.accountName" type="text" class="input-xlarge" readonly="true"/>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.countryCode"/>：</td><!-- 国别代码 -->
				<td>
					<form:select id="countryCode" path="ftzOffTxnDtl.countryCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_COUNTRY_CODE }" />
					</form:select>
				</td>
				
				<td class="label_td"><spring:message code="ftz.label.disitrictCode"/>：</td><!-- 国内地区代码 -->
				<td>
					<form:select id="disitrictCode" path="ftzOffTxnDtl.districtCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.TRAN_TYPE"/>：</td><!-- 交易性质 -->
				<td>
					<form:select path="ftzOffTxnDtl.tranType" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_TRAN_TYPE }" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td><!-- 明细状态 -->
				<td colspan="3">
					<form:select path="ftzOffTxnDtl.chkStatus" disabled="true" id="chkStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS }" />
					</form:select>
				</td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td><!-- 录入用户 -->
				<td><form:input id="makUserId" path="ftzOffTxnDtl.makUserId" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td><!-- 录入时间 -->
				<td><form:input id="makDatetime" path="ftzOffTxnDtl.makDatetime" class="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td><!-- 审核用户 -->
				<td><form:input id="chkUserId" path="ftzOffTxnDtl.chkUserId" class="input-large" readonly="true"/></td>
				
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td><!-- 审核时间 -->
				<td><form:input id="chkDatetime" path="ftzOffTxnDtl.chkDatetime" class="input-large" readonly="true"/></td>
			</tr>
			<tr class="dtl">	
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td><!-- 审核附言 -->
				<td colspan="3"><form:input id="chkAddWord" path="ftzOffTxnDtl.chkAddWord" class="input-xxlarge"/></td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id=authPass type="button" class="btn btn-primary"
			onclick="authPass()" value="<spring:message code="ftz.label.AUTH" />"> 
		<input id="authRefuse"
			type="button" class="btn btn-primary" onclick="authRefuse()" value="<spring:message code="ftz.label.UNAUTH" />">
		<button name="btn" class="btn btn-primary" onclick="javascript: window.close();">
		<spring:message code="button.lable.close"/></button><!-- 关闭 -->
	</div>
</div>