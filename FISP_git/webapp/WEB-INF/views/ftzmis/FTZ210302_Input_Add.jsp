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
			
			$("#branchId").attr("disabled", "disabled");
			$("#submitDate").attr("disabled", "disabled");
			$("#result").attr("disabled", "disabled");
			$("#addWord").attr("disabled", "disabled");
			
			$("#dtlSubmit").attr("disabled", "disabled");
		}
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(8)").text();
			var selected_seqNo = $(this).find("td:eq(9)").text();
			var old_selected_msgId = $("#msgId").val();
			var old_selected_seqNo = $("#selected_seqNo").val();
			if (null == old_selected_seqNo) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_seqNo").val(selected_seqNo);
				return;
			}
			if (old_selected_seqNo == selected_seqNo) {
				$("#selected_msgId").val("");
				$("#selected_seqNo").val("");
				return;
			}
			if (old_selected_seqNo != selected_seqNo) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_seqNo").val(selected_seqNo);
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
					window
							.showModalDialog(
									'${pageContext.request.contextPath}/FTZ210302/QryDtlDtl?selected_msgId='
											+ selected_msgId
											+ "&selected_seqNo="
											+ selected_seqNo,
									window,
									'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
					queryFTZ210302Dtl();
				});
	});

	function accoutQry() {
		var accountNo = $("#accountNo").val();
		var subAccountNo = $("#subAccountNo").val();
		$.ajax({
			url : contextPath + "/FTZ210302/DtlAccountQry",
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
					alert('<spring:message code="w.cm.1007"/>');
				} else {
					$("#branchId").val(rs.branchId);
					$("#accType").val(rs.accType);
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
		
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210302/AddDtlSubmit";
		form.submit();
	}

	function queryAct() {
		showSelAct([ {
			"accountNo" : "param1",
			"subAccountNo" : "param2"
		} ]);
	};

	function showDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data" />');
			return;
		} else {
			window
					.showModalDialog(
							'${pageContext.request.contextPath}/FTZ210302/QryDtlDtl?selected_msgId='
									+ selected_msgId + "&selected_seqNo="
									+ selected_seqNo,
							window,
							'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
			queryFTZ210302();
		}
	}
	function delDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data" />');
			return;
		} else {
			
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210302/InputDtlDel";
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
		window
				.showModalDialog(
						'${pageContext.request.contextPath}/FTZ210302/AddDtlDtlInit?selected_msgId='
								+ selected_msgId,
						window,
						'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		queryFTZ210302();
	}
	function uptDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data" />');
			return;
		} else {
			window
					.showModalDialog(
							'${pageContext.request.contextPath}/FTZ210302/UptDtlDtlInit?selected_msgId='
									+ selected_msgId + "&selected_seqNo="
									+ selected_seqNo,
							window,
							'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
			queryFTZ210302();
		}
	}
	function queryFTZ210302() {
		$("#selected_msgId").val($("#msgId").val());
		
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210302/UptDtlInit";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210302Form">
			<form:form commandName="FTZ210302Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210302.input.msg" /><!-- 应付信用证录入/修改 - 批量明细 --></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210302/AddDtlSubmit"
		method="post" modelAttribute="FTZ210302Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.common.branchId" />：</td>
				<td>
				<form:select path="ftzOffMsgCtl.branchId" id="branchId">
					<form:option value=""></form:option>
					<form:options items="${SM_0002}"/>
				</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MGS_ID" />：</td>
				<td><form:input id="msgId" path="ftzOffMsgCtl.msgId"
						class=".input-large" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.WORK_DATE" />：</td>
				<td><form:input id="submitDate" path="ftzOffMsgCtl.workDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.msgStatus" id="msgStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_INPUT}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.result" id="result">
						<form:option value=""></form:option>
						<form:options items="${FTZ_PROC_RESULT_EN}" />
					</form:select></td>
				<td class="label_td"></td>
				<td></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ADDWORD" />：</td>
				<td colspan="3"><form:input id="addWord" path="ftzOffMsgCtl.addWord" class="input-large" cssStyle="width:600px"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="4">
					<button id="dtlSubmit" type="button" class="btn btn-primary" onclick="DtlSubmit()"><spring:message code="ftz.label.SAVE_MSG" /></button>
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
				<p class="text-info" align="center"><spring:message code="ftz.label.MSG_DTL_List" /><!-- 明细列表 --></p>
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="10px">No</th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message code="ftz.label.SUBMIT_DATE" /><!-- 申报日期 --></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message code="ftz.label.ACCOUNTNAME" /><!-- 申请人名称 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CURRENCY" /><!-- 货币 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.AMOUNT" /><!-- 金额 --></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message code="ftz.label.COUNTRYCODE" /><!-- 申请人国别代码 --></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message code="ftz.label.DISTRICTCODE" /><!-- 申请人地区代码 --></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message code="ftz.label.DTL_STATUS" /><!-- 明细状态 --></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto1" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 40px;">${dto1.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 80px;">${dto1.institutionCode}</td>
						<td class="vtip" style="text-align: right; width: 50px;">
						<t:codeValue items="${SYS_CURRENCY}" key="${dto1.currency}" type="label" />
						</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:moneyFormat type="label" value="${dto1.amount}" /></td>
						<td class="vtip" style="text-align: left; width: 60px;">${dto1.countryCode}</td>
						<td class="vtip" style="text-align: left; width: 60px;"><t:codeValue items="${FTZ_DISITRICT_CODE}" key="${dto1.districtCode}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 30px;"><t:codeValue
								items="${FTZ_MSG_STATUS}" key="${dto1.chkStatus}" type="label" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}"
			query="selected_msgId=${FTZ210302Form.ftzOffMsgCtl.msgId}" />
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer" style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="add" type="button" class="btn btn-primary" onclick="addDetailDtl();" value="<spring:message code="ftz.label.ADD_DTL" />"> 
		<input id="upt" type="button" class="btn btn-primary" onclick="uptDetailDtl();" value="<spring:message code="ftz.label.UPT_DTL" />"> 
		<input id="del" type="button" class="btn btn-primary" onclick="delDetailDtl();" value="<spring:message code="ftz.label.DEL_DTL" />">
		<input id="detail" type="button" class="btn btn-primary" onclick="showDetailDtl();" value="<spring:message code="ftz.label.DTL_DTL" />"> 
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>