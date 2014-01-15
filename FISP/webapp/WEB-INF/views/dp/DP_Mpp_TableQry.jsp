<script type="text/javascript">
	 if (typeof (window.opener) == 'undefined') window.opener = window.dialogArguments;
	//input button
	function tableSelect(tableName) {
		window.returnValue = tableName;
		  if (window.opener && window.opener != null)
              window.opener.ReturnValue = tableName;
		window.close();
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
		<spring:hasBindErrors name="DP_Mpp_TableQryForm">
			<form:form commandName="DP_Mpp_TableQryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.TableQry" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/DP_Mpp_TableQry/Qry"
		modelAttribute="DP_Mpp_TableQryForm" class="form-horizontal">
		<input id="del_MppId" type="hidden" name="del_MppId" />
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="if.label.TABLENAME" /></td>
				<td><form:input path="query_tableName" type="text"
						class="input-large" /></td>
				<td class="label_td"><spring:message
						code="dp.lable.TableDesc" /></td>
				<td><form:input path="query_tableDesc" type="text"
						class="input-large" /></td>
				<td align="right">
					<button type="submit" class="btn btn-primary">
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
							code="if.label.TABLENAME" /></th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="dp.lable.TableDesc" /></th>
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
						<td class="tbl_page_td_left vtip" width="300px">${dto.tableName}</td>
						<td class="tbl_page_td_right vtip" width="300px">${dto.tableDesc}</td>
						<td align="center" width="100px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small"
									onclick="tableSelect('${f:h(dto.tableName)}')"
									value="<spring:message code="button.lable.Choose"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
