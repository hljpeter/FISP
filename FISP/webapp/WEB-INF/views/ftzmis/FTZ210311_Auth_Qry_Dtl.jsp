<script type="text/javascript">
	$(function() {
		var msgStatus = $("#msgStatus").val();
		if("03"==msgStatus || "04"==msgStatus){
			$("#sbdetail").attr("disabled", "disabled");
		}else{
			$("#sbdetail").removeAttr("disabled");
		}
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(8)").text();
			var selected_seqNo = $(this).find("td:eq(9)").text();
			var old_selected_msgId = $("#selected_msgId").val();
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
									'${pageContext.request.contextPath}/FTZ210311/QryDtlDtl?selected_msgId='
											+ selected_msgId
											+ "&selected_seqNo="
											+ selected_seqNo,
									window,
									'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
					queryFTZ210311Dtl();
				});
	});

	//show detail
	function showDetailDtl() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_seqNo = $("#selected_seqNo").val();
		if (null == selected_seqNo || "" == selected_seqNo) {
			alert('<spring:message code="ftz.validate.choose.data" />');
			return;
		} else {
			window
					.showModalDialog(
							'${pageContext.request.contextPath}/FTZ210311/QryDtlDtl?selected_msgId='
									+ selected_msgId + "&selected_seqNo="
									+ selected_seqNo,
							window,
							'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
				queryFTZ210311Dtl();
		}
	}
	
	function queryFTZ210311() {
		$("#selected_msgId").val($("#msgId").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210311/QryAuthDtl";
		form.submit();
	}
	
	function sbDtl() {
		$("#selected_msgId").val($("#msgId").val());
		$("#selected_msgNo").val($("#msgNo").val());
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210311/AuthDtlSubmit";
		form.submit();
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZ210311Form">
			<form:form commandName="FTZ210311Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title"><spring:message code="ftzmis.title.210311.auth.msg" /><!-- 汇率掉期业务审核 - 批量明细 --></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ210311/AddDtlSubmit"
		method="post" modelAttribute="FTZ210311Form" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_seqNo" id="selected_seqNo" />
		<form:hidden path="selected_msgNo" id="selected_msgNo"/>
		<form:hidden path="ftzOffMsgCtl.msgNo" id="msgNo" />
		<form:hidden path="ftzOffMsgCtl.makDatetime" id="makDatetime" />
		<form:hidden path="ftzOffMsgCtl.chkDatetime" id="chkDatetime" />
		<form:hidden path="unAuthFlag" id="unAuthFlag"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH" />：</td>
				<td>
				<form:select path="ftzOffMsgCtl.branchId" id="branchId" disabled="true">
					<form:option value=""></form:option>
					<form:options items="${SM_0002}"/>
				</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.MGS_ID" />：</td>
				<td><form:input id="msgId" path="ftzOffMsgCtl.msgId"
						class=".input-large" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.WORK_DATE" />：</td>
				<td><form:input id="workDate" path="ftzOffMsgCtl.workDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-large" readonly="true"/></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.msgStatus" id="msgStatus" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_MSG_STATUS_INPUT}" />
					</form:select></td>
			</tr>
			</table>
			<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.PBOC_STATUS" />：</td>
				<td><form:select path="ftzOffMsgCtl.result" disabled="true">
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
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.COUNTRY_CODE" /><!-- 国别代码 --></th>
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
						<td class="vtip" style="text-align: left; width: 50px;">${dto1.accOrgCode}</td>
						<td class="vtip" style="text-align: left; width: 40px;">${dto1.submitDate}</td>
						<td class="vtip" style="text-align: right; width: 50px;">
							<t:moneyFormat type="label" value="${dto1.buyAmt}" />
						</td>
						<td class="vtip" style="text-align: left; width: 40px;">
							<t:codeValue items="${SYS_CURRENCY}" key="${dto1.buyCurr}" type="label" />
						</td>
						<td class="vtip" style="text-align: left; width: 50px;">
							<t:codeValue items="${FTZ_COUNTRY_CODE }" key="${dto1.countryCode}" type="label"/>
						</td>
						<td class="vtip" style="text-align: left; width: 80px;">${dto1.accountName}</td>
						<td class="vtip" style="text-align: left; width: 50px;">
							<t:codeValue items="${FTZ_MSG_STATUS}" key="${dto1.chkStatus}" type="label" />
						</td>
						<td style="display: none;">${dto1.msgId}</td>
						<td style="display: none;">${dto1.seqNo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}"
			query="selected_msgId=${FTZ210311Form.ftzOffMsgCtl.msgId}" 
			action=""/>
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center" id="footer" style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="detail" type="button" class="btn btn-primary" onclick="showDetailDtl();" value="<spring:message code="ftz.label.DTL_DTL" />"> 
		<input id="sbdetail" type="button" class="btn btn-primary" onclick="sbDtl()"
			value="<spring:message code="ftz.label.AUTH_MSG"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>