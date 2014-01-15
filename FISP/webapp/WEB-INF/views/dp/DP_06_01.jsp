<script type="text/javascript">

$(function() {
	$("#censusMonth").val("2013-09");
}
);

	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/dp06/01/search";
		form.submit();
	}

	//detail button
	function detailSearch() {
		window.location.href="${pageContext.request.contextPath}/dp03/02/init";
	}
	
	//add button
	function add() {
		window.showModalDialog('${pageContext.request.contextPath}/ifc01/03/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	
	}
	
	//update button
	function update(fileId) {
		window.showModalDialog(
				'${pageContext.request.contextPath}/ifc01/04/init?fileId='+ fileId,window,
				'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	
	}
	
	//updatefield button
	function updateField(fileId) {
		//window.showModalDialog(
				//'${pageContext.request.contextPath}/ifdc01/01/search?fileId='+ fileId,window,
				//'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		window.showModalDialog(
				'${pageContext.request.contextPath}/ifdc02/01/search?fileId='+ fileId,window,
				'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
	}
	
	//delete button
	function del(fileId)
	{
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifc01/05/delete?fileId="+ fileId;
		var msg=$("#confirmMsg1").val()+'删除'+$("#confirmMsg2").val();
		if (confirm(msg)){
		form.submit();
		}
	}
</script>

<!-- tips information -->
<div id="id_showMsg" style="display: none"><br />
<br />
<div id="id_result"><t:messagePanel messagesAttributeName="errmsg"
	messagesType="error" /> <t:messagePanel
	messagesAttributeName="infomsg" messagesType="info" /> <t:messagePanel
	messagesAttributeName="successmsg" messagesType="success" /> <spring:hasBindErrors
	name="depositBalanceCheckForm">
	<form:form commandName="depositBalanceCheckForm">
		<div class="alert alert-error"><form:errors path="*"
			cssStyle="color:red"></form:errors></div>
	</form:form>
</spring:hasBindErrors></div>
<br />
</div>

<!-- title -->
<div class="page_title">数据管理 / 数据核对 / 存款余额核对</div>

<!-- body -->
<div class="row"><form:form id="form" method="post" action="${pageContext.request.contextPath}/dp06/01/init"
	modelAttribute="depositBalanceCheckForm" class="form-horizontal">
	<table class="tbl_search">
		<tr>
			<td class="label_td"><spring:message code="dp.title.CensusMonth"/></td>
			<td >
			
			<form:input id="censusMonth" path="censusMonth" type="text" maxlength="6" class="Wdate .input-small"
					 onclick="WdatePicker({dateFmt:'yyyy-MM'});" />
			</td>
			<td colspan="4" align="right">
			<button type="button" class="btn btn-primary" onclick="search()"><spring:message code="button.label.Search" /></button>
			<button type="button"  class="btn btn-primary" ><spring:message code="dp.title.Export"/></button>
		</td>
		</tr>
	</table>
</form:form></div>
<div class="row">
<div class="tbl_page_head">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<thead>
		<tr>
			<th class="tbl_page_th" width="80px"><spring:message code="dp.title.SubjectID"/></th>
			<th class="tbl_page_th" width="150px"><spring:message code="dp.title.SubjectName"/></th>
			<th class="tbl_page_th" width="60px"><spring:message code="dp.title.GLCount"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="dp.title.GLBalance"/></th>
			<th class="tbl_page_th" width="60px"><spring:message code="dp.title.ApplicationCount"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="dp.title.ApplicationBalance"/></th>
			<th class="tbl_page_th" width="60px"><spring:message code="dp.title.DifferenceCount"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="dp.title.DifferenceBalance"/></th>
			<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation"/></th>
		</tr>
	</thead>
</table>
</div>
<div class="tbl_page_body" style="min-height: 355px; height: 355px;">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<tbody>
		<form:form id="impFileCfgForm" action="${pageContext.request.contextPath}"
			modelAttribute="impFileCfgForm">
			<input id="rttpayid" type="hidden" name="rttpay" />
			<c:forEach var="dto" items="${page.content}" varStatus="i">
				<tr>
					<td class="vtip"  align="center" width="80px">${dto.subjectId}</td>
					<td class="tbl_page_td_left vtip"    width="150px">${dto.subjectName}</td>
					<td class="tbl_page_td_right vtip" width="60px">
					<t:moneyFormat type="label" format="#,###,###,###" value="${dto.glCount}"/></td>
					<td class="tbl_page_td_right vtip" width="80px">
					<t:moneyFormat type="label" value="${dto.glBalance}"/></td>
					<td class="tbl_page_td_right vtip" width="60px">
					<t:moneyFormat type="label" format="#,###,###,###" value="${dto.applicationCount}"/></td>
					<td class="tbl_page_td_right vtip" width="80px">
					<t:moneyFormat type="label" value="${dto.applicationBalance}"/></td>
					<td class="tbl_page_td_right vtip" width="60px">
					<t:moneyFormat type="label" format="#,###,###,###" value="${dto.differenceCount}"/></td>
					<td class="tbl_page_td_right vtip" width="80px">
					<t:moneyFormat type="label" value="${dto.differenceBalance}"/></td>
					<td align="center" width="40px">
						<div  style="height: 25px">
							<input type="button" class="btn btn-small" onclick="detailSearch()" value="<spring:message code="dp.title.detail"/>">
						</div>
					</td>
				</tr>
			</c:forEach>
		</form:form>
	</tbody>
</table>
</div>
</div>

<!-- page and buttons -->
<div class="pagination pull-right" style="margin-top: 5px; margin-bottom: 0px;">
	<div class="leftPage">
		<util:pagination page="${page}" query="censusMonth=${listForm.censusMonth}" />
	</div>
</div>


