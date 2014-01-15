<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
if (typeof (window.opener) == 'undefined')
	window.opener = window.dialogArguments;
$(function(){
	$("#pageTable").find("tr").bind('click', function(){
		var selected_nationCode = $(this).find("td:eq(1)").text();
		var nationCode = $("#selected_nationCode").val();
		var selected_nationNumThree = $(this).find("td:eq(2)").text();
		var nationNumThree = $("#selected_nationNumThree").val();
		var selected_nationShortName = $(this).find("td:eq(3)").text();
		var nationShortName = $("#selected_nationShortName").val();

		if (null == nationCode) {
			$("#selected_nationCode").val(selected_nationCode);
		}
		if (nationCode == selected_nationCode) {
			$("#selected_nationCode").val("");
		}
		if (nationCode!= selected_nationCode) {
			$("#selected_nationCode").val(selected_nationCode);
		}

		if (null == nationNumThree) {
			$("#selected_nationNumThree").val(selected_nationNumThree);
		}
		if (nationNumThree == selected_nationNumThree) {
			$("#selected_nationNumThree").val("");
		}
		if (nationNumThree!= selected_nationNumThree) {
			$("#selected_nationNumThree").val(selected_nationNumThree);
		}

		if (null == nationShortName) {
			$("#selected_nationShortName").val(selected_nationShortName);
		}
		if (nationShortName == selected_nationShortName) {
			$("#selected_nationShortName").val("");
		}
		if (nationShortName!= selected_nationShortName) {
			$("#selected_nationShortName").val(selected_nationShortName);
		}
	});
});

	function actSelect() {
		var selected_nationCode = $("#selected_nationCode").val();
		var selected_nationNumThree = $("#selected_nationNumThree").val();
		var selected_nationShortName = $("#selected_nationShortName").val();
		if((null == selected_nationCode || "" == selected_nationCode)) {
			alert("请选择一条数据");
			return;
		}
		var json = {
			"param1": selected_nationCode,
			"param2": selected_nationNumThree,
			"param3":selected_nationShortName
		};
			window.opener.ReturnValue = JSON.stringify(json);
		if(window.opener && window.opener != null) {
			window.opener.ReturnValue = JSON.stringify(json);
		}
		window.close();
	};
	
	
	function search() {
		var form = document.getElementById("form");
		$("#selected_nationCode").val("");
		$("#selected_nationNumThree").val("");
		$("#selected_nationShortName").val("");
		form.action = "${pageContext.request.contextPath}/FTZCMNationQry/Qry";
		form.submit();
	}
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZCMNationQryForm">
			<form:form commandName="FTZCMNationQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title" align="center">
	<spring:message code="ftz.label.CM_NATION_SEL"/>
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZCMNationQry/Qry"
		method="post" modelAttribute="FTZCMNationQryForm" class="form-horizontal">
		<form:hidden path="selected_nationCode" id="selected_nationCode" />	
		<form:hidden path="selected_nationNumThree" id="selected_nationNumThree" />	
		<form:hidden path="selected_nationShortName" id="selected_nationShortName" />	
		<table class="tbl_search">
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.CM_NATION_SNAME_PARA"/></td>
				<td colspan="4"><form:input id="nationShortName"
						path="nationShortName" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CM_NATION_CODE_PARA"/></td>
				<td><form:input id="nationCode"
						path="nationCode" class=".input-small"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.CM_NATION_NUMCODE_PARA"/></td>
				<td><form:input id="nationNumThree"
						path="nationNumThree" class=".input-small" />
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
				<p class="text-info" align="center"><spring:message code="ftz.label.CM_NATION_LIST"/></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_NATION_CODE"/></th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_NATION_NUMCODE"/></th>
					<th style="vertical-align: middle; text-align: center" width="80px"><spring:message code="ftz.label.CM_NATION_SNAME"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.CM_NATION_NAME"/></th>					
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
						<td class="vtip" style="text-align: left; width: 50px;">${dto.nationCode}</td>
						<td class="vtip" style="text-align: left; width: 50px;">${dto.nationNumThree}</td>
						<td class="vtip" style="text-align: left; width: 80px;">${dto.nationShortName}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.nationName}</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="${f:query(FTZCMNationQryForm)}"
			action="/FTZCMNationQry/Qry"/>
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