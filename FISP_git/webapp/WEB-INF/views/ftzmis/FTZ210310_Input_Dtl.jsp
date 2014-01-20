<script type="text/javascript">
	$(function() {
		var msgId = $("#msgId").val();
		if ("" == msgId || null == msgId) {
			$("#add").attr("disabled", "disabled");
			$("#upd").attr("disabled", "disabled");
			$("#del").attr("disabled", "disabled");
			$("#detail").attr("disabled", "disabled");
		} else {
			$("#add").removeAttr("disabled");
			$("#upd").removeAttr("disabled");
			$("#del").removeAttr("disabled");
			$("#detail").removeAttr("disabled");
		}
		
		var actionFlag = "${FTZ210310Form.actionFlag}";
		if ("addMsg" == actionFlag) {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210310/AddDtlSubmit";
		} else if ("updMsg" == actionFlag) {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210310/UpdDtlSubmit";
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
					showDialog('${pageContext.request.contextPath}/FTZ210310/QryDtlDtl?selected_msgId='
							+ selected_msgId
							+ "&selected_seqNo="
							+ selected_seqNo,'550','1024');
				});
	});

	//submit msg
	function DtlSubmit() {
		var form = document.getElementById("form");
		form.submit();
	}

	//show detail
	function showDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.dataTxn" />');
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZ210310/QryDtlDtl?selected_msgId='
					+ selected_msgId + "&selected_seqNo="
					+ selected_seqNo,'550','1024');
			queryFTZ210310();
		}
	}
	
	//delete detail
	function delDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		var selected_chkStatus = $("#selected_chkStatus").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.dataTxn" />');
			return;
		} else {
			if("03" == selected_chkStatus) {
				alert('<spring:message code="ftz.validate.chk.success"/>');
				return;
			}
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210310/InputDtlDel";
			var msg = $("#confirmMsg1").val() + $("#del").val()
					+ $("#confirmMsg2").val();
			if (confirm(msg)) {
				form.submit();
			} else {
				return false;
			}
		}
	}
	//add detail
	function addDetailDtl() {
		var selected_msgId = $("#msgId").val();
		showDialog('${pageContext.request.contextPath}/FTZ210310/AddDtlDtlInit?selected_msgId='
				+ selected_msgId,'550','1024');
		queryFTZ210310();
	}
	
	//update detail
	function updDetailDtl() {
		var selected_msgId = $("#msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		var selected_chkStatus = $("#selected_chkStatus").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.dataTxn"/>');
			return;
		} else {
			if ("03" == selected_chkStatus) {
				alert('<spring:message code="ftz.validate.chk.success"/>');
				return;
			}
			showDialog('${pageContext.request.contextPath}/FTZ210310/UpdDtlDtlInit?selected_msgId='
					+ selected_msgId + "&selected_seqNo="
					+ selected_seqNo,'550','1024');
			queryFTZ210310();
		}
	}
	function queryFTZ210310() {
		$("#selected_msgId").val($("#msgId").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210310/DtlInitReflash?page.page="+${page.number+1};
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
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

<div class="page_title"><spring:message code="ftzmis.title.210310.input.msg" /><!-- 远期结售汇录入/修改 - 批量明细 --></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210310/AddDtlSubmit"
		method="post" modelAttribute="FTZ210310Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="ftzOffMsgCtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzOffMsgCtl.chkDatetime" id="chkDatetime" />
		<input type="hidden" id="selected_chkStatus" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.BRANCH" />：</td>
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
				<td><form:input id="workDate" path="ftzOffMsgCtl.workDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large"/></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.msgStatus" id="msgStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_INPUT}" />
					</form:select></td>
			</tr>
			<c:if test="${ FTZ210310Form.actionFlag eq 'updMsg'}">
				<tr>
					<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
					<td colspan="3"><form:select path="ftzOffMsgCtl.result" disabled="true">
							<form:option value=""></form:option>
							<form:options items="${FTZ_PROC_RESULT}"/>
						</form:select></td>
					<td class="label_td"></td>
					<td></td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="ftz.label.ADDWORD" />：</td>
					<td colspan="3"><form:input id="addWord" path="ftzOffMsgCtl.addWord" class="forever input-xxlarge" readonly="true"/></td>
				</tr>
			</c:if>
			<tr>
				<td style="text-align: center;" colspan="4">
					<button id="dtlSubmit" type="button" class="btn btn-primary" onclick="DtlSubmit()">
						<spring:message code="ftz.label.SUBMIT_MSG" /></button>
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
					<th style="vertical-align: middle; text-align: center" width="10px"><spring:message code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.ACCORGCODE" /><!-- 所属机构代码 --></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message code="ftz.label.SUBMIT_DATE" /><!-- 申报日期 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.BUY_AMT" /><!-- 买入金额 --></th>
					<th style="vertical-align: middle; text-align: center" width="40px"><spring:message code="ftz.label.BUY_CURR" /><!-- 买入币种 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.TRAN_GENRE" /><!-- 交易类型 --></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message code="ftz.label.OPP_NAME1" /><!-- 对方户名 --></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.DTL_STATUS" /><!-- 明细状态 --></th>
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
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue 
							items="${SM_0002 }" key="${dto1.accOrgCode}" type="label"/></td>
						<td class="vtip" style="text-align: left; width: 40px;">${dto1.submitDate}</td>
						<td class="vtip" style="text-align: right; width: 50px;">
							<t:moneyFormat type="label" value="${dto1.buyAmt}" />
						</td>
						<td class="vtip" style="text-align: left; width: 40px;">
							<t:codeValue items="${SYS_CURRENCY}" key="${dto1.buyCurr}" type="label" />
						</td>
						<td class="vtip" style="text-align: left; width: 50px;">
							<t:codeValue items="${FTZ_TRAN_GENRE }" key="${dto1.tranGenre}" type="label"/>
						</td>
						<td class="vtip" style="text-align: left; width: 80px;">${dto1.accountName }</td>
						<td class="vtip" style="text-align: left; width: 50px;">
							<t:codeValue items="${FTZ_MSG_STATUS}" key="${dto1.chkStatus}" type="label" />
						</td>
						<td style="display: none;">${dto1.msgId}</td>
						<td style="display: none;">${dto1.seqNo}</td>
						<td style="display: none;">${dto1.chkStatus }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}"
			query="selected_msgId=${FTZ210310Form.ftzOffMsgCtl.msgId}" 
			action="${pageUrl}"/>
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer" style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="add" type="button" class="btn btn-primary" onclick="addDetailDtl();" value="<spring:message code="ftz.label.ADD_DTL" />"> 
		<input id="upd" type="button" class="btn btn-primary" onclick="updDetailDtl();" value="<spring:message code="ftz.label.UPT_DTL" />"> 
		<input id="del" type="button" class="btn btn-primary" onclick="delDetailDtl();" value="<spring:message code="ftz.label.DEL_DTL" />">
		<input id="detail" type="button" class="btn btn-primary" onclick="showDetailDtl();" value="<spring:message code="ftz.label.DTL_DTL" />"> 
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>