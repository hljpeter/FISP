<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
if (typeof (window.opener) == 'undefined')
	window.opener = window.dialogArguments;
$(function(){	
	$("#pageTable").find("tr").bind('click', function(){
		var selected_bankCode = $(this).find("td:eq(1)").text();
		var bankCode = $("#selected_bankCode").val();
		var selected_bankName = $(this).find("td:eq(2)").text();
		var bankName = $("#selected_bankName").val();
		if (null == bankCode) {
			$("#selected_bankCode").val(selected_bankCode);
		}
		if (bankCode == selected_bankCode) {
			$("#selected_bankCode").val("");
		}
		if (bankCode != selected_bankCode) {
			$("#selected_bankCode").val(selected_bankCode);
		}
		
		if (null == bankName) {
			$("#selected_bankName").val(selected_bankName);
		}
		if (bankName == selected_bankName) {
			$("#selected_bankName").val("");
		}
		if (bankName != selected_bankName) {
			$("#selected_bankName").val(selected_bankName);
		}
	});
});

	function actSelect() {
		var selected_bankCode = $("#selected_bankCode").val();
		var selected_bankName = $("#selected_bankName").val();
		if((null == selected_bankCode || "" == selected_bankCode)||(null == selected_bankName || "" == selected_bankName)) {
			alert("请选择一条数据");
			return;
		}
		var json = {
			"param1" : selected_bankCode,
			"param2" : selected_bankName
		};
		window.returnValue = JSON.stringify(json);
		if(window.opener && window.opener != null) {
			window.opener.ReturnValue = JSON.stringify(json);
		}
		window.close();
	};
	
	
	function search() {
		var form = document.getElementById("form");
		$("#selected_bankName").val("");
		$("#selected_bankCode").val("");
		form.action = "${pageContext.request.contextPath}/FTZCMBankQry/Qry";
		form.submit();
	}
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZCMBankQryForm">
			<form:form commandName="FTZCMBankQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title" align="center">
	<spring:message code="ftz.label.CM_BANK_SEL"/>
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZCMBankQry/Qry"
		method="post" modelAttribute="FTZCMBankQryForm" class="form-horizontal">
		<form:hidden path="selected_bankName" id="selected_bankCode" />
		<form:hidden path="selected_bankCode" id="selected_bankName" />
		<table class="tbl_search">
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.CM_BANK_NAME_PARA"/></td>
				<td colspan="2"><form:input id="bankName"
						path="bankName" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CM_BANK_CODE_PARA"/></td>
				<td><form:input id="bankCode"
						path="bankCode" class=".input-large"/>
				</td>
			<td>
	    			<div align="right">
	    				<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search"/></button>
	    			</div>
	    		</td>
			</tr>
			
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<p class="text-info" align="center"><spring:message code="ftz.label.CM_BANK_LIST"/></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_BANK_CODE"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.CM_BANK_NAME"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_CLEAR_BNAKCODE"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.CM_CLEAR_BANMNAME"/></th>				
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
						<td class="vtip" style="text-align: left; width: 50px;">${dto.bankCode}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.bankName}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.clearBankCode}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.clearBankName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="${f:query(FTZCMBankQryForm)}"
			action="/FTZCMBankQry/Qry"/>
	</div>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="create" type="button" class="btn btn-primary" onclick="actSelect()"
			value="<spring:message code="button.lable.Choose"/>"> 
		<input
			type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>