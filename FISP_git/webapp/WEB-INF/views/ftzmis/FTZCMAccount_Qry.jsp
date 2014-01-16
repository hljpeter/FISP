<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$("#pageTable").find("tr").bind('click', function(){
			var selected_actNo = $(this).find("td:eq(3)").text();
			var old_actNo = $("#selected_actNo").val();
			var selected_subActNo = $(this).find("td:eq(4)").text();
			var old_subActNo = $("#selected_subActNo").val();
			if (old_actNo == selected_actNo && old_subActNo == selected_subActNo) {
				$("#selected_actNo").val("");
				$("#selected_subActNo").val("");
				return;
			} else {
				$("#selected_actNo").val(selected_actNo);
				$("#selected_subActNo").val(selected_subActNo);
				return;
			}
		});
		$("#pageTable").find("tr").bind('dblclick', function() {
			var selected_actNo = $(this).find("td:eq(3)").text();
			var selected_subActNo = $(this).find("td:eq(4)").text();
			showDialog('${pageContext.request.contextPath}/FTZCMAccountQry/QryDtl?selected_actNo='
					+ selected_actNo +'&selected_subActNo='+ selected_subActNo,'500','1024');
		});
	});
	
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZCMAccountQry/Qry";
		form.submit();
	}
	
	if (typeof (window.opener) == 'undefined')
		window.opener = window.dialogArguments;
	
	function actSelect() {
		var selected_actNo = $("#selected_actNo").val();
		var selected_subActNo = $("#selected_subActNo").val();
		var json = {
			"param1" : selected_actNo,
			"param2" : selected_subActNo
		};
		window.returnValue = JSON.stringify(json);
		if(window.opener && window.opener != null) 
			
			window.opener.ReturnValue = JSON.stringify(json);
		
		window.close();
	};
	
	function openDetail() {
		var selected_actNo = $("#selected_actNo").val();
		var selected_subActNo = $("#selected_subActNo").val();
		if(null == selected_actNo || "" == selected_actNo) {
			alert("请选择一条账户数据!");
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZCMAccountQry/QryDtl?selected_actNo='
					+ selected_actNo +'&selected_subActNo='+ selected_subActNo,'500','1024');
		}
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZCMAccountQryForm">
			<form:form commandName="FTZCMAccountQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title" align="center">
	<spring:message code="ftzmis.title.210501.input.qry"/>
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZCMAccountQry/QryInit"
		method="post" modelAttribute="FTZCMAccountQryForm" class="form-horizontal">
		<form:hidden path="selected_actNo" id="selected_actNo" />
		<form:hidden path="selected_subActNo" id="selected_subActNo" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH_ID"/>：</td>
				<td><form:select path="query_branchId">
					<form:option value=""></form:option>
					<form:options items="${SM_0002}"/>
				</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NAME"/>：</td>
				<td><form:input id="query_accountName"
						path="query_accountName" class=".input-large"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACCOUNT_NO"/>：</td>
				<td><form:input id="query_accountNo"
						path="query_accountNo" class=".input-large"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.SUB_ACCOUNT_NO"/>：</td>
				<td><form:input id="query_subAccountNo"
						path="query_subAccountNo" class=".input-large"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td><form:select path="query_currency">
					<form:option value=""></form:option>
					<form:options items="${SYS_CURRENCY}"/>
				</form:select>
				</td>
				<td class="label_td"><spring:message code="ftz.label.ACC_TYPE"/>：</td>
				<td><form:select path="query_accType">
					<form:option value=""></form:option>
					<form:options items="${FTZ_ACC_TYPE}"/>
				</form:select>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.ACC_STATUS"/>：</td>
				<td><form:select path="query_accStatus">
						<form:option value=""></form:option>
						<form:options items="${FTZ_ACC_STATUS}" />
					</form:select></td>
				<td style="text-align: right;" colspan="2">
					<button type="submit" class="btn btn-primary" onclick="search()">
						<spring:message code="button.label.Search" />
					</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<p class="text-info" align="center"><spring:message code="ftz.label.ACCOUNT_LIST"/></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px"><spring:message code="fisp.label.common.no" /></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.BRANCH_ID"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.ACCOUNT_NAME"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.ACCOUNT_NO"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.SUB_ACCOUNT_NO"/></th>
					<th style="vertical-align: middle; text-align: center" width="70px"><spring:message code="ftz.label.CURRENCY"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.ACC_TYPE"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.ACC_STATUS"/></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
		<table id="pageTable"
			class="table table-striped table-bordered table-condensed tbl_page"
			style="border: 0">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td style="text-align: center; width: 20px;">${(page.number*page.size)+(i.index+1)}</td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: center; width: 150px;">${dto.accountName}</td>
						<td class="vtip" style="text-align: center; width: 150px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: center; width: 150px;">${dto.subAccountNo}</td>
						<td class="vtip" style="text-align: center; width: 70px;"><t:codeValue
								items="${SYS_CURRENCY}" key="${dto.currency}" type="label" /></td>
						<td class="vtip" style="text-align: center; width: 50px;"><t:codeValue
								items="${FTZ_ACC_TYPE}" key="${dto.accType}" type="label" /></td>
						<td class="vtip" style="text-align: center; width: 50px;"><t:codeValue
								items="${FTZ_ACC_STATUS}" key="${dto.accStatus}" type="label" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="query_branchId=${FTZCMAccountQryForm.query_branchId}
			&query_accountName=${FTZCMAccountQryForm.query_accountName}&query_accountNo=${FTZCMAccountQryForm.query_accountNo}
			&query_subAccountNo=${FTZCMAccountQryForm.query_subAccountNo}&query_currency=${FTZCMAccountQryForm.query_currency}
			&query_accType=${FTZCMAccountQryForm.query_accType}&query_accStatus=${FTZCMAccountQryForm.query_accStatus}"
			action="/FTZCMAccountQry/Qry"/>
	</div>
</div>

<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="create" type="button" class="btn btn-primary" onclick="actSelect()"
			value="<spring:message code="button.lable.Choose"/>"> 
		<input type="button" class="btn btn-primary" onclick="openDetail()" value="<spring:message code="dp.lable.detail"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>