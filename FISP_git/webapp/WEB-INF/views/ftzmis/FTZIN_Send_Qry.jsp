<script type="text/javascript">
	$(function() {
		$("#pageTable").find("tr").bind('click', function() {
			var selected_msgId = $(this).find("td:eq(3)").text();
			var selected_msgNo = $(this).attr("id").substr(2);
			var old_selected_msgId = $("#selected_msgId").val();
			if (null == old_selected_msgId) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_msgNo").val(selected_msgNo);
				return;
			}
			if (old_selected_msgId == selected_msgId) {
				$("#selected_msgId").val("");
				$("#selected_msgNo").val("");
				return;
			}
			if (old_selected_msgId != selected_msgId) {
				$("#selected_msgId").val(selected_msgId);
				$("#selected_msgNo").val(selected_msgNo);
				return;
			}
		});
		$("#pageTable")
		.find("tr")
		.bind(
				'dblclick',
				function() {
					var selected_msgId = $(this).find("td:eq(3)").text();
					var selected_msgNo = $(this).attr("id").substr(2);
					window
					.showModalDialog(
							'${pageContext.request.contextPath}/FTZ2102/QryRedirectAuthAll?selected_msgId='
									+ selected_msgId+'&selected_msgNo='+selected_msgNo,
							window,
							'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
			queryFTZIN();

				});
	});

	function showDetail() {
		var selected_msgId = $("#selected_msgId").val();
		var selected_msgNo = $("#selected_msgNo").val();
		if (null == selected_msgId || "" == selected_msgId) {
			alert('<spring:message code="ftz.validate.choose.data" />');
			return;
		} else {
			window
					.showModalDialog(
							'${pageContext.request.contextPath}/FTZ2102/QryRedirectAuth?selected_msgId='
									+ selected_msgId+'&selected_msgNo='+selected_msgNo,
							window,
							'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
			queryFTZIN();
		}
	}
	
	
	function accoutQry() {
		var loadingDiv=document.getElementById("loading");
		loadingDiv.style.display="";
		$.ajax({
			url : contextPath + "/FTZ2102/SendMsg",
			type : "post",
			dataType : "json",
			async : false,
			data : {
				
			},
			success : function(rs) {
				//loadingDiv.style.display="none";
				alert(rs.result);
			},
			error:function(rs)
			{
				//loadingDiv.style.display="none";
				alert(rs.result);
			}
		});
	}
	
	function showAllDetail() {
		accoutQry();
	}

	function queryFTZIN() {
		$("#selected_msgId").val("");
		$("#selected_msgNo").val("");
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ2102/AuthQry?page.page="+${page.number+1};
		form.submit();
	}
	
	
	

	
</script>




<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="fTZINForm">
			<form:form commandName="fTZINForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div id="loading"  style="color:red;position:fixed;left:600px;top:300px;display:none;font-size:xx-large;">处理中。。。</div>
<div class="page_title"><spring:message code="2102ftz.title" /><!-- 应付信用证录入/修改 --></div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZ2102/AuthQry"
		method="post" modelAttribute="FTZINForm" class="form-horizontal">
		<form:hidden path="selected_msgId" id="selected_msgId" />
		<form:hidden path="selected_msgNo" id="selected_msgNo" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.common.branchId" /><!-- 机构号 -->：</td>
				<td><form:select path="query_branchId">
						<form:option value=""></form:option>
						<form:options items="${SM_0002}" />
					</form:select></td>
				<td class="label_td"><spring:message code="ftz.label.WORK_DATE" /><!-- 工作日期 -->：</td>
				<td><form:input id="query_workDate_start"
						path="query_workDate_start"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" />-<form:input
						id="query_workDate_end" path="query_workDate_end"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="span2" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.MGS_ID" /><!-- 批量号 -->：</td>
				<td><form:input id="query_msgId" path="query_msgId"
						class=".input-large" /></td>
				<td class="label_td"><spring:message code="ftz.label.MSG_TYPE" />：</td>
				<td>
					<form:select path="query_msgNo">
						<form:option value=""></form:option>
						<form:options items="${FTZ_2102_MSG}" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<td class="label_td" colspan="4" style="text-align:right;">
				<button type="button" class="btn btn-primary" id="qry" onclick="queryFTZIN()">
						<spring:message code="ftz.label.SELECT_MSG" />
				</button>
				</td><!-- i18n -->
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<p class="text-info" align="center"><spring:message code="ftz.label.MSG_List" /><!-- 批量列表 --></p>
				<tr>
						<th style="vertical-align: middle; text-align: center" width="10px"><spring:message
							code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="ftz.label.SUBMIT_DATE" /></th>
					<th style="vertical-align: middle; text-align: center" width="60px"><spring:message
							code="ftz.label.BRANCH" /></th>
					<th style="vertical-align: middle; text-align: center" width="70px"><spring:message
							code="ftz.label.MSG_ID" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="120px"><spring:message code="ftz.label.MSG_TYPE" /></th>
					<th style="vertical-align: middle; text-align: center"
						width="50px"><spring:message code="ftz.label.ACCOUNT_NO" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message
							code="fisp.rq.sumCnt" /></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message
							code="fisp.rq.authCnt" /></th>
							<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.MSG_STATUS" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 340px; height: 340px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr id="tr${dto.msgNo}">
						<td style="text-align: center; width: 10px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: center; width: 50px;">${dto.submitDate}</td>
						<td class="vtip" style="text-align: left; width: 60px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 70px;">${dto.msgId}</td>
						<td class="vtip" style="text-align: left; width: 120px;"><t:codeValue
								items="${FTZ_2102_MSG}" key="${dto.msgNo}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: right; width: 50px;">${dto.totalCount}</td>
						<td class="vtip" style="text-align: right; width: 80px;">${dto.rsv1}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue items="${FTZ_MSG_STATUS}" key="${dto.msgStatus}" type="label" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


<div class="pagination pull-right" style="margin-top: 10px;">
	<table class="text-center">
		<tr>
			<td width="50%" align="center">
			<input id="detail" type="button" class="btn btn-primary" onclick="showAllDetail();" value="<spring:message code="ftz.label.MSG_ALL_Dtl" />">
			<input id="detail" type="button" class="btn btn-primary" onclick="showDetail();" value="<spring:message code="ftz.label.MSG_UNAUTH_Dtl" />">
			</td>
			<td width="50%" align="right">
				<table>
					<tr>
						<td><util:pagination page="${page}"
								query="query_branchId=${FTZINForm.query_branchId}&query_workDate_start=${FTZINForm.query_workDate_start}&query_workDate_end=${FTZINForm.query_workDate_end}&query_msgId=${FTZINForm.query_msgId}&query_msgNo=${FTZINForm.query_msgNo}" action="/FTZ2102/AuthQry"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
