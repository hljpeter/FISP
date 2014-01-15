<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
if (typeof (window.opener) == 'undefined')
	window.opener = window.dialogArguments;
$(function(){	
	$("#pageTable").find("tr").bind('click', function(){
		var selected_regionInfo = $(this).find("td:eq(1)").text();
		var regionInfo = $("#selected_regionInfo").val();
		var selected_regionNum = $(this).find("td:eq(2)").text();
		var regionNum = $("#selected_regionNum").val();
		if (null == regionInfo) {
			$("#selected_regionInfo").val(selected_regionInfo);
		}
		if (regionInfo == selected_regionInfo) {
			$("#selected_regionInfo").val("");
		}
		if (regionInfo != selected_regionInfo) {
			$("#selected_regionInfo").val(selected_regionInfo);
		}
		
		if (null == regionNum) {
			$("#selected_regionNum").val(selected_regionNum);
		}
		if (regionNum == selected_regionNum) {
			$("#selected_regionNum").val("");
		}
		if (regionNum != selected_regionNum) {
			$("#selected_regionNum").val(selected_regionNum);
		}
	});
});

	function actSelect() {
		var selected_regionInfo = $("#selected_regionInfo").val();
		var selected_regionNum = $("#selected_regionNum").val();
		if((null == selected_regionInfo || "" == selected_regionInfo)||(null == selected_regionNum || "" == selected_regionNum)) {
			alert("请选择一条数据");
			return;
		}
		var json = {
			"param1" : selected_regionInfo,
			"param2" : selected_regionNum
		};
		window.opener.ReturnValue = JSON.stringify(json);
		if(window.opener && window.opener != null) 
			window.opener.ReturnValue = JSON.stringify(json);
		
		window.close();
	};
	
	
	function search() {
		var form = document.getElementById("form");
		$("#selected_regionNum").val("");
		$("#selected_regionInfo").val("");
		form.action = "${pageContext.request.contextPath}/FTZCMRegionQry/Qry";
		form.submit();
	}
	
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="FTZCMRegionQryForm">
			<form:form commandName="FTZCMRegionQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title" align="center">
	<spring:message code="ftz.label.CM_REGION_SEL"/>
</div>

<div class="row">
	<form:form id="form"
		action="${pageContext.request.contextPath}/FTZCMRegionQry/Qry"
		method="post" modelAttribute="FTZCMRegionQryForm" class="form-horizontal">
		<form:hidden path="selected_regionInfo" id="selected_regionInfo" />
		<form:hidden path="selected_regionNum" id="selected_regionNum" />
		<table class="tbl_search">
			<tr>	
				<td class="label_td"><spring:message code="ftz.label.CM_REGION_NAME_PARA"/></td>
				<td><form:input id="regionInfo"
						path="regionInfo" class="input-xxlarge"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ftz.label.CM_REGION_NUM_PARA"/></td>
				<td><form:input id="regionNum"
						path="regionNum" class=".input-large"/>
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
				<p class="text-info" align="center"><spring:message code="ftz.label.CM_REGION_LIST"/></p>
				<tr>
					<th style="vertical-align: middle; text-align: center" width="20px">No</th>
					<th style="vertical-align: middle; text-align: center" width="50px"><spring:message code="ftz.label.CM_REGION_NUM"/></th>
					<th style="vertical-align: middle; text-align: center" width="250px"><spring:message code="ftz.label.CM_REGION_NAME"/></th>
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
						<td class="vtip" style="text-align: left; width: 50px;">${dto.regionNum}</td>
						<td class="vtip" style="text-align: left; width: 250px;">${dto.regionInfo}</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="${f:query(FTZCMRegionQryForm)}"
			action="/FTZCMRegionQry/Qry"/>
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