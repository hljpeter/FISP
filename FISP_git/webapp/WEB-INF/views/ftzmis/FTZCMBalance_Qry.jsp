<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
if (typeof (window.opener) == 'undefined')
	window.opener = window.dialogArguments;
$(function(){	
	$("#pageTable").find("tr").bind('click', function(){
		var selected_balanceCode = $(this).find("td:eq(4)").text();
		var balanceCode = $("#selected_balanceCode").val();
		var selected_fourTypeName = $(this).find("td:eq(5)").text();
		var fourTypeName = $("#selected_fourTypeName").val();
		if (null == balanceCode) {
			$("#selected_balanceCode").val(selected_balanceCode);
		}
		if (balanceCode == selected_balanceCode) {
			$("#selected_balanceCode").val("");
		}
		if (balanceCode!= selected_balanceCode) {
			$("#selected_balanceCode").val(selected_balanceCode);
		}

		if (null == fourTypeName) {
			$("#selected_fourTypeName").val(selected_fourTypeName);
		}
		if (fourTypeName == selected_fourTypeName) {
			$("#selected_fourTypeName").val("");
		}
		if (fourTypeName!= selected_fourTypeName) {
			$("#selected_fourTypeName").val(selected_fourTypeName);
		}

	});
});

	function actSelect() {
		var selected_balanceCode = $("#selected_balanceCode").val();
		var selected_fourTypeName = $("#selected_fourTypeName").val();
		if((null == selected_balanceCode  || "" == selected_balanceCode )||(null == selected_fourTypeName || "" == selected_fourTypeName)) {
			alert("请选择一条数据");
			return;
		}
		var json = {
			"param1" : selected_balanceCode,
			"param2" : selected_fourTypeName
		};
		window.opener.ReturnValue = JSON.stringify(json);
		if(window.opener && window.opener != null) {
			window.opener.ReturnValue = JSON.stringify(json);
		}
		window.close();
	};
		
	function search() {
		var form = document.getElementById("form");
		$("#selected_balanceCode").val("");
		$("#selected_fourTypeName").val("");
		form.action = "${pageContext.request.contextPath}/FTZCMBalanceQry/Qry";
		form.submit();
	}
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZCMBalanceQryForm">
			<form:form commandName="FTZCMBalanceQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title" align="center">
	<spring:message code="ftz.label.CM_BALANCE_SEL"/>
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZCMBalanceQry/Qry"
		method="post" modelAttribute="FTZCMBalanceQryForm" class="form-horizontal">
		<form:hidden path="selected_balanceCode" id="selected_balanceCode" />
		<form:hidden path="selected_fourTypeName" id="selected_fourTypeName" />
		<table class="tbl_search">
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.CM_FOUR_NAME_PARA"/></td>
				<td colspan="4"><form:input id="fourTypeName"
						path="fourTypeName" class="input-xxlarge"/> 
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CM_BALANCE_CODE_PARA"/></td>
				<td><form:input id="balanceCode"
						path="balanceCode" class=".input-large"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.CM_ONE_PARA"/></td>
				<td colspan="2">
					<form:select id="oneTypeName" path="oneTypeName" class=".input-small">
	    				<form:option value=""></form:option>
						<form:options items="${FTZ_BLANCE_ONE_TYPE_ZH}" />
					</form:select>
				</td>	
			</tr>
			<tr>
			<td class="label_td"><spring:message code="ftz.label.CM_TWO_PARA"/></td>
				<td ><form:input id="twoTypeName"
						path="twoTypeName" class=".input-large"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.CM_THREE_PARA"/></td>
				<td><form:input id="threeTypeName"
						path="threeTypeName" class=".input-large"/>
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
				<p class="text-info" align="center"><spring:message code="ftz.label.CM_BALANCE_LIST"/></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_BALANCE_ONE"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_BALANCE_TWO"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_BALANCE_THREE"/></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message code="ftz.label.CM_BALANCE_CODE"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.CM_FOUR_NAME"/></th>
					<th style="vertical-align: middle; text-align: center" width="30px"><spring:message code="ftz.label.CM_BALANCE_SIGN"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_BALANCE_COMMENTS"/></th>										
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
						<td class="vtip" style="text-align: left; width: 50px;">${dto.oneTypeName}</td>
						<td class="vtip" style="text-align: center; width: 50px;">${dto.twoTypeName}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.threeTypeName}</td>
						<td class="vtip" style="text-align: center; width: 80px;">${dto.balanceCode}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.fourTypeName}</td>
						<td class="vtip" style="text-align: center; width: 30px;">${dto.sign}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.comments}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="${f:query(FTZCMBalanceQryForm)}"
			action="/FTZCMBalanceQry/Qry"/>
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