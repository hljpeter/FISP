<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
if (typeof (window.opener) == 'undefined')
	window.opener = window.dialogArguments;
$(function(){
	
	$("#pageTable").find("tr").bind('click', function(){
		var selected_groupCode = $(this).find("td:eq(1)").text();
		var groupCode = $("#selected_groupCode").val();

		if (null == groupCode) {
			$("#selected_groupCode").val(selected_groupCode);
		}
		if (groupCode == selected_groupCode) {
			$("#selected_groupCode").val("");
		}
		if (groupCode!= selected_groupCode) {
			$("#selected_groupCode").val(selected_groupCode);
		}
	});
});

	function actSelect() {
		var selected_groupCode = $("#selected_groupCode").val();
		if((null == selected_groupCode || "" == selected_groupCode)) {
			alert("请选择一条数据");
			return;
		}
		var json = {
			"param1" : selected_groupCode
		};
		window.opener.ReturnValue = JSON.stringify(json);
		if(window.opener && window.opener != null) {
			window.opener.ReturnValue = JSON.stringify(json);
		}
		window.close();
	};
	
	
	function search() {
		$("#selected_groupCode").val("");
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZCMMetaQry/Qry";
		form.submit();
	}
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZCMMetaQryForm">
			<form:form commandName="FTZCMMetaQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title" align="center">
	<spring:message code="ftz.label.CM_META_SEL"/>
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZCMMetaQry/Qry"
		method="post" modelAttribute="FTZCMMetaQryForm" class="form-horizontal">
		<form:hidden path="selected_groupCode" id="selected_groupCode" />	
		<table class="tbl_search">
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.CM_META_NAME_PARA"/></td>
				<td colspan="2"><form:input id="metaName"
						path="metaName" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CM_META_CODE_PARA"/></td>
				<td><form:input id="metaVal"
						path="metaVal" class=".input-large"/>
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
				<p class="text-info" align="center"><spring:message code="ftz.label.CM_META_LIST"/></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_META_CODE"/></th>
					<th style="vertical-align: middle; text-align: center" width="250px"><spring:message code="ftz.label.CM_META_NAME"/></th>
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
						<td class="vtip" style="text-align: left; width: 50px;">${dto.metaVal}</td>
						<td class="vtip" style="text-align: left; width: 250px;">${dto.metaName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="${f:query(FTZCMMetaQryForm)}"
			action="/FTZCMMetaQry/Qry"/>
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