<script type="text/javascript">
	$(function() {
		var msgId = $("#msgId").val();
		if ("" == msgId || null == msgId) {
			$("#add").attr("disabled", "disabled");
			$("#upt").attr("disabled", "disabled");
			$("#del").attr("disabled", "disabled");
			$("#detail").attr("disabled", "disabled");
		} else {
			$("#add").removeAttr("disabled");
			$("#upt").removeAttr("disabled");
			$("#del").removeAttr("disabled");
			$("#detail").removeAttr("disabled");
		}

		var input_flag = $("#input_flag").val();
		if ('add' == input_flag) {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210112/AddDtlSubmit";
		} else if ('upt' == input_flag) {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210112/UptDtlSubmit";
		}

		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(8)").text();
			var selected_seqNo = $(this).find("td:eq(9)").text();
			var selected_chkStatus = $(this).find("td:eq(10)").text();
			var old_selected_msgId = $("#msgId").val();
			var old_selected_seqNo = $("#selected_seqNo").val();
			if (null == old_selected_seqNo) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_seqNo").val(selected_seqNo);
				$("#selected_chkStatus").val(selected_chkStatus);
				return;
			}
			if (old_selected_seqNo == selected_seqNo) {
				$("#selected_msgId").val("");
				$("#selected_seqNo").val("");
				$("#selected_chkStatus").val("");
				return;
			}
			if (old_selected_seqNo != selected_seqNo) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_seqNo").val(selected_seqNo);
				$("#selected_chkStatus").val(selected_chkStatus);
				return;
			}

		});
		$("#pageTable")
				.find("tr")
				.bind(
						'dblclick',
						function() {
							var selected_msgId = $(this).find("td:eq(8)")
									.text();
							var selected_seqNo = $(this).find("td:eq(9)")
									.text();
							showDialog('${pageContext.request.contextPath}/FTZ210112/QryDtlDtl?selected_msgId='
							+ selected_msgId
							+ "&selected_seqNo="
							+ selected_seqNo,'500','1024');
						});
	});

	function accoutQry() {
		var accountNo = $("#accountNo").val();
		$.ajax({
			url : contextPath + "/FTZ210106/DtlAccountQry",
			type : "post",
			dataType : "json",
			async : false,
			data : {
				accountNo : accountNo
			},
			success : function(rs) {
				dtlExist = rs.dtlExist;
				if (null == dtlExist || false == dtlExist) {
					alert('<spring:message code="w.cm.1007"/>');
					$("#branchId").val("");
					$("#branchId1").val("");
					$("#accType").val("");
					$("#accType1").val("");
					$("#accountName").val("");
					$("#depositRate").val("");
					$("#customType").val("");
					$("#customType1").val("");
					$("#balanceCode").val("");
					$("#documentType").val("");
					$("#balanceCode1").val("");
					$("#documentType1").val("");
					$("#currency").val("");
					$("#currency1").val("");
					$("#documentNo").val("");
					$("#balance").val("");
					$("#accOrgCode").val("");
				} else {
					$("#branchId").val(rs.branchId);
					$("#branchId1").val(rs.branchId);
					$("#accType").val(rs.accType);
					$("#accType1").val(rs.accType);
					$("#accountName").val(rs.accountName);
					$("#depositRate").val(rs.depositRate);
					$("#customType").val(rs.customType);
					$("#customType1").val(rs.customType);
					$("#balanceCode").val(rs.balanceCode);
					$("#documentType").val(rs.documentType);
					$("#balanceCode1").val(rs.balanceCode);
					$("#documentType1").val(rs.documentType);
					$("#currency").val(rs.currency);
					$("#currency1").val(rs.currency);
					$("#documentNo").val(rs.documentNo);
					$("#balance").val(rs.balance);
					$("#accOrgCode").val(rs.accOrgCode);
				}
			}
		});
	}

	function accountFill() {
		var accountNo = $("#accountNo").val();
		if ("" != accountNo) {
			accoutQry();
		}
	}

	function DtlSubmit() {
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		form.submit();
	}

	function addRef() {
		var form = document.getElementById("form1");
		form.action = "${pageContext.request.contextPath}/FTZ210112/AddDtlInit";
		form.submit();
	}

	function queryAct() {
		showSelAct([ {
			"accountNo" : "param1"
			
		} ]);
		accountFill();
	};

	function showDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZ210112/QryDtlDtl?selected_msgId='
					+ selected_msgId + "&selected_seqNo="
					+ selected_seqNo,'500','1024');
		}
	}
	function delDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		var selected_chkStatus = $("#selected_chkStatus").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			if("03"==selected_chkStatus){
				alert('<spring:message code="ftz.validate.chk.success"/>');
				return;
			}
			$("#balance").val($("#balance").val().replaceAll(",", ""));
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210112/InputDtlDel";
			var msg = $("#confirmMsg1").val() + $("#del").val()
					+ $("#confirmMsg2").val();
			if (confirm(msg)) {
				form.submit(); 
			} else {
				return false;
			}

		}
	}
	function addDetailDtl() {
		$("#amount").val($("#amount").val().replaceAll(",", ""));
		var selected_msgId = $("#msgId").val();
		
		showDialog('${pageContext.request.contextPath}/FTZ210112/AddDtlDtlInit?selected_msgId='
				+ selected_msgId,'500','1024');
		queryFTZ210112();
	}
	function uptDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		var selected_chkStatus = $("#selected_chkStatus").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data"/>');
			return;
		} else {
			if("03"==selected_chkStatus){
				alert('<spring:message code="ftz.validate.chk.success"/>');
				return;
			}
				showDialog('${pageContext.request.contextPath}/FTZ210112/UptDtlDtlInit?selected_msgId='
					+ selected_msgId + "&selected_seqNo="
					+ selected_seqNo,'500','1024');
			queryFTZ210112();
		}
	}
	function queryFTZ210112() {
		$("#selected_msgId").val($("#msgId").val());
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210112/DtlInitReflash?page.page="+${page.number+1};
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210112Form">
			<form:form commandName="FTZ210112Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.210112.input.dtl" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210112/UptDtlSubmit"
		method="post" modelAttribute="FTZ210112Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="input_flag" id="input_flag" />
		<form:hidden path="ftzInMsgCtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="chkDatetime" />
		<input type="hidden" id="selected_chkStatus" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCHID" />：</td>
				<td><form:hidden path="ftzInMsgCtl.branchId" id="branchId1" />
				<form:select path="ftzInMsgCtl.branchId" disabled="true" id="branchId">
					<form:option value=""></form:option>
					<form:options items="${SM_0002}"/>
				</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_ID" />：</td>
				<td><form:input id="msgId" path="ftzInMsgCtl.msgId"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.SUBMIT_DATE" />：</td>
				<td><form:input id="submitDate" path="ftzInMsgCtl.submitDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUSS" /></td>
				<td><form:select path="ftzInMsgCtl.msgStatus" disabled="true" id="msgStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.ACCOUNT_NO" />：</td>
				<td><form:input id="accountNo" path="ftzInMsgCtl.accountNo"
						class=".input-large" onblur="accountFill()"/>
					<button type="button" class="btn btn-small" onclick="queryAct()">  
						<spring:message code="button.label.Search" />
					</button></td>				
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="accountName" path="ftzInMsgCtl.accountName"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.CURRENCY" />：</td>
				<td>
				<form:hidden path="ftzInMsgCtl.currency" id="currency1"/>
				<form:select path="ftzInMsgCtl.currency" id="currency"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>

				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="ftzInMsgCtl.balance"
						value="${FTZ210112Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" />	
			</tr>
			<tr>
				<td class="label_td" colspan="2"><font color="red">*</font> <spring:message
						code="ftz.label.BALANCE_CODE" />： <form:hidden
						path="ftzInMsgCtl.balanceCode" id="balanceCode1" /> <form:select
						id="balanceCode" path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
				<td class="label_td"><font color="red">* </font>
				<spring:message code="ftz.label.ACCORGCODE" />：</td>
				<td><form:input id="accOrgCode" path="ftzInMsgCtl.accOrgCode" class="input-large"  readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.CD_FLAG" />：</td>
				<td><form:select path="ftzInTxnDtl.cdFlag">
						<form:option value=""></form:option>
						<form:options items="${FTZ_CD_FLAG}" />
					</form:select></td>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.AMOUNTS" />：</td>
				<td colspan="3"><t:moneyFormat type="text" id="amount" name="ftzInTxnDtl.amount"
						value="${FTZ210112Form.ftzInTxnDtl.amount}"
						format="###,###,###,###.00" dot="true"  maxlength="24"/></td>
			</tr>
			<c:if test="${ FTZ210112Form.input_flag eq 'upt'}">
				<tr>
					<td colspan="4"><hr /></td>
				</tr>
				<tr>
					<td class="label_td"><spring:message
							code="ftz.label.PBOC_STATUS" />：</td>
					<td colspan="3"><form:select path="ftzInMsgCtl.result"
							disabled="true">
							<form:option value=""></form:option>
							<form:options items="${FTZ_PROC_RESULT}" />
						</form:select></td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="ftz.label.ADDWORD" />：</td>
					<td colspan="3"><form:input id="addWord"
							path="ftzInMsgCtl.addWord" class="input-xxlarge" readonly="true" /></td>
				</tr>
			</c:if>
			<tr>
				<td style="text-align: center;" colspan="4">
					<button type="button" class="btn btn-primary" onclick="DtlSubmit()"><spring:message code="ftz.label.SUBMIT_MSG" /></button>
					<button type="button" class="btn btn-primary" onclick="javascript:window.close()"><spring:message code="button.lable.close" /></button>
				</td>
			</tr>
			</tr>
		</table>
	</form:form>
</div>
