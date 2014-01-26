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
			form.action = "${pageContext.request.contextPath}/FTZ210202/AddDtlSubmit";
		} else if ('upt' == input_flag) {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210202/UptDtlSubmit";
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
							showDialog('${pageContext.request.contextPath}/FTZ210202/QryDtlDtl?selected_msgId='
							+ selected_msgId
							+ "&selected_seqNo="
							+ selected_seqNo,'500','1024');
							
						});
	});

	function accoutQry() {
		var accountNo = $("#accountNo").val();
		var subAccountNo = $("#subAccountNo").val();
		$.ajax({
			url : contextPath + "/FTZ210101/DtlAccountQry",
			type : "post",
			dataType : "json",
			async : false,
			data : {
				accountNo : accountNo,
				subAccountNo : subAccountNo
			},
			success : function(rs) {
				dtlExist = rs.dtlExist;
				if (null == dtlExist || false == dtlExist) {
					alert("无此账号信息!");
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
		var subAccountNo = $("#subAccountNo").val();
		if ("" != accountNo && "" != subAccountNo) {
			accoutQry();
		}
	}

	function DtlSubmit() {
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		form.submit();
	}

	function addRef() {
		var form = document.getElementById("form1");
		form.action = "${pageContext.request.contextPath}/FTZ210202/AddDtlInit";
		form.submit();
	}

	function queryAct() {
		showSelAct([ {
			"accountNo" : "param1",
			"subAccountNo" : "param2"
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
			showDialog('${pageContext.request.contextPath}/FTZ210202/QryDtlDtl?selected_msgId='
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
			form.action = "${pageContext.request.contextPath}/FTZ210202/InputDtlDel";
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
		var selected_msgId = $("#msgId").val();
		showDialog('${pageContext.request.contextPath}/FTZ210202/AddDtlDtlInit?selected_msgId='
				+ selected_msgId,'500','1024');
		queryFTZ210202();
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
			showDialog('${pageContext.request.contextPath}/FTZ210202/UptDtlDtlInit?selected_msgId='
					+ selected_msgId + "&selected_seqNo="
					+ selected_seqNo,'500','1024');
			
			queryFTZ210202();
		}
	}
	function queryFTZ210202() {
		$("#selected_msgId").val($("#msgId").val());
		$("#balance").val($("#balance").val().replaceAll(",", ""));
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210202/DtlInitReflash?page.page="+${page.number+1};
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210202Form">
			<form:form commandName="FTZ210202Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="ftzmis.title.210202.input.dtl" />
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210202/AddDtlSubmit"
		method="post" modelAttribute="FTZ210202Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="input_flag" id="input_flag" />		
		<form:hidden path="ftzInMsgCtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzInMsgCtl.chkDatetime" id="chkDatetime" />
		<input type="hidden" id="selected_chkStatus" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCHID" />：</td>
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
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUSS" />：</td>
				<td><form:select path="ftzInMsgCtl.msgStatus" disabled="true">
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
					<form:hidden path="ftzInMsgCtl.subAccountNo" id="subAccountNo" /></td>				
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME" />：</td>
				<td><form:input id="accountName" path="ftzInMsgCtl.accountName"
						class=".input-large" readonly="true" /></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.CURRENCY" />：</td>
				<td>
				<form:hidden path="ftzInMsgCtl.currency" id="currency1"/>
				<form:select path="ftzInMsgCtl.currency" id="currency"
						disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY}" />
					</form:select></td>

			<td class="label_td"><font color="red">* </font><spring:message code="ftz.label.DAILY_BALANCE" />：</td>
				<td><t:moneyFormat type="text" id="balance" name="ftzInMsgCtl.balance"
						value="${FTZ210202Form.ftzInMsgCtl.balance}"
						format="###,###,###,###.00" dot="true" readonly="true" />	
			</tr>
			<tr>
			<td class="label_td" colspan="2"><font color="red">* </font>
				<spring:message code="ftz.label.BALANCE_CODE" /><form:hidden
						path="ftzInMsgCtl.balanceCode" id="balanceCode1" /> ：<form:select
						id="balanceCode" path="ftzInMsgCtl.balanceCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_BALANCE_INDEX_CODE}" />
					</form:select></td>
			<td class="label_td"><font color="red">* </font>
				<spring:message code="ftz.label.ACCORGCODE" />：</td>
				<td><form:input id="accOrgCode" path="ftzInMsgCtl.accOrgCode" class="input-large"  readonly="true"/></td>
			</tr>
			<c:if test="${ FTZ210202Form.input_flag eq 'upt'}">
			<tr><td colspan="4"><hr/></td></tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzInMsgCtl.result" disabled="true">
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
					
				</td>
			</tr>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page" style="border:1">
			<thead>
				<p class="text-info" align="center">	
					<spring:message code="ftz.label.MSG_DTL_List" />
				</p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.CD_FLAG" /></th>
							
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.TRAN_DATE" /></th>
							
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message
							code="ftz.label.AMOUNT" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.COUNTRY_CODE" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.SECURITIES_TYPE" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.SECURITIES_CODE" /></th>							
						<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
						code="ftz.label.DTL_STATUS" /></th>
				
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 1">
			<tbody>
				<c:forEach var="dto1" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						
						<td class="vtip" style="text-align: left; width: 40px;"><t:codeValue
								items="${FTZ_CD_FLAG}" key="${dto1.cdFlag}" type="label" /></td>
								
						<td class="vtip" style="text-align: left; width: 40px;">${dto1.tranDate}</td>
						
						<td class="vtip" style="text-align: right; width: 40px;"><t:moneyFormat
								type="label" value="${dto1.amount}" /></td>
								
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_COUNTRY_CODE}" key="${dto1.countryCode}"
								type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_SECURITIES_TYPE}" key="${dto1.securitiesType}"
								type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto1.securitiesCode}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto1.chkStatus}" type="label" /></td>
						<td style="display: none;">${dto1.msgId}</td>
						<td style="display: none;">${dto1.seqNo}</td>
						<td style="display: none;">${dto1.chkStatus}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}"
			query="selected_msgId=${FTZ210202Form.ftzInMsgCtl.msgId}"
			action="${pageUrl}" />
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="add" type="button" class="btn btn-primary" onclick="addDetailDtl();"	value="<spring:message code="ftz.label.ADD_DTL"/>"> 
		<input id="upt" type="button" class="btn btn-primary" onclick="uptDetailDtl();"	value="<spring:message code="ftz.label.UPT_DTL"/>"> 
		<input id="del" type="button" class="btn btn-primary" onclick="delDetailDtl();"	value="<spring:message code="ftz.label.DEL_DTL"/>"> 
		<input id="detail" type="button" class="btn btn-primary" onclick="showDetailDtl();"	value="<spring:message code="ftz.label.DTL_DTL"/>"> 
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
<form:form id="form1" />