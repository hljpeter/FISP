<script type="text/javascript">
	if (typeof (window.opener) == 'undefined')
		window.opener = window.dialogArguments;
	//input button
	function orgSelect(orgid, orgname) {
		var json = {
			"param1" : $.trim(orgid),
			"param2" : $.trim(orgname)
		};
		window.returnValue = JSON.stringify(json);

		if (window.opener && window.opener != null)

			window.opener.ReturnValue = JSON.stringify(json);
		window.close();
	};

	function query() {
		var flag = $("#flag").val();
		if (flag && flag == '1') {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/OrgSearch/QryAll";
			form.submit();
		} else {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/OrgSearch/Qry";
			form.submit();
		}

	};
</script>


<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg"
			messagesType="success" />
		<spring:hasBindErrors name="orgSearchForm">
			<form:form commandName="orgSearchForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.OrgQry" />
</div>

<div class="row">
	<input type="hidden" id="flag" value="${flag}">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/OrgSearch/Qry"
		modelAttribute="orgSearchForm" class="form-horizontal">
		<input id="del_MppId" type="hidden" name="del_MppId" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="de.label.branchId" /></td>
				<td><form:input path="query_orgid" type="text"
						class="input-large" /></td>
				<td class="label_td"><spring:message
						code="index.label.sm.OrganizationName" /></td>
				<td><form:input path="query_orgname" type="text"
						class="input-large" /></td>
				<td align="right">
					<button type="button" class="btn btn-primary" onclick="query()">
						<spring:message code="button.label.Search" />
					</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row">
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="20px">No.</th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="de.label.branchId" /></th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="index.label.sm.OrganizationName" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<c:forEach var="dto" items="${page.content}" varStatus="i">
					<tr>
						<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
						<td class="tbl_page_td_left vtip" width="300px">${dto.orgid}</td>
						<td class="tbl_page_td_right vtip" width="300px">${dto.orgname}</td>
						<td align="center" width="100px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small"
									onclick="orgSelect('${dto.orgid}','${dto.orgname}')"
									value="<spring:message code="button.lable.Choose"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<!-- page and buttons -->
<div class="pagination pull-right"
	style="margin-top: 5px; margin-bottom: 40px;">
	<div class="leftPage">
		<util:pagination page="${page}" query="${f:query(orgSearchForm)}" />
	</div>
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>
