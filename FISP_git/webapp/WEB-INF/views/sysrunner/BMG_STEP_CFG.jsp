<script type="text/javascript">
	if (typeof (window.opener) == 'undefined')
		window.opener = window.dialogArguments;
	$(function() {
		var impListFlag = $("#impListFlag").val();
		var mppListFlag = $("#mppListFlag").val();
		var expListFlag = $("#expListFlag").val();
		if(impListFlag){
			document.getElementById('impList').style.display = 'block';
		}else{
			document.getElementById('impList').style.display = 'none';
		}
		
		if(mppListFlag){
			document.getElementById('mppList').style.display = 'block';
		}else{
			document.getElementById('mppList').style.display = 'none';
		}
		
		if(expListFlag){
			document.getElementById('expList').style.display = 'block';
		}else{
			document.getElementById('expList').style.display = 'none';
		}
		
	});
	
	
	//input button
	function cfgSelect(type, cfgId, cfgName) {
		var json = {
			"param1" : type,
			"param2" : cfgId,
			"param3" : cfgName
		};
		window.returnValue = JSON.stringify(json);

		if (window.opener && window.opener != null)

			window.opener.ReturnValue = json;

		window.close();
	};

	function query() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BMG_STEP_CFG/Qry";
		form.submit();
	};
</script>


<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="batchManageForm">
			<form:form commandName="batchManageForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="sysrunner.title.BatchManageStepCfgList" />
</div>

<div class="row">
	<form:form id="form" method="post"
		action="${pageContext.request.contextPath}/OrgSearch/Qry"
		modelAttribute="batchManageForm" class="form-horizontal">
		<input type="hidden" id="impListFlag" value="${impListFlag}">
		<input type="hidden" id="mppListFlag" value="${mppListFlag}">
		<input type="hidden" id="expListFlag" value="${expListFlag}">
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message
						code="sysrunner.label.CfgName" /></td>
				<td><form:input path="query_cfg_name" type="text"
						class="input-large" /></td>
				<td class="label_td"><spring:message
						code="sysrunner.label.CfgType" /></td>
				<td><form:select id="query_cfg_type" path="query_cfg_type">
						<form:option value=""></form:option>
						<form:options items="${BMG_CFG_TYPE}" />
					</form:select></td>
				<td align="right">
					<button type="button" class="btn btn-primary" onclick="query()">
						<spring:message code="button.label.Search" />
					</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div class="row" id="impList">
	<div>
		<p class="text-info">
			<spring:message code="sysrunner.label.Import" />
		</p>
	</div>
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="20px">No.</th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="fisp.label.common.fileName" /></th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="fisp.label.common.tableName" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 100px; height: 100px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<c:forEach var="dto" items="${impList}" varStatus="i">
					<tr>
						<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
						<td class="tbl_page_td_left vtip" width="300px">${dto.fileName}</td>
						<td class="tbl_page_td_right vtip" width="300px">${dto.tableName}</td>
						<td align="center" width="100px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small"
									onclick="cfgSelect('1','${dto.impId}','${dto.comments}')"
									value="<spring:message code="button.lable.Choose"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="row" id="mppList">
	<div>
		<p class="text-info">
			<spring:message code="sysrunner.label.Mapping" />
		</p>
	</div>
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="20px">No.</th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="de.label.destTable" /></th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="de.label.srcTable" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 100px; height: 100px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<c:forEach var="dto" items="${mppList}" varStatus="i">
					<tr>
						<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
						<td class="tbl_page_td_left vtip" width="300px">${dto.destTable}</td>
						<td class="tbl_page_td_right vtip" width="300px">${dto.srcTable}</td>
						<td align="center" width="100px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small"
									onclick="cfgSelect('2','${dto.mppId}','${dto.mppName}')"
									value="<spring:message code="button.lable.Choose"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="row" id="expList">
	<div>
		<p class="text-info">
			<spring:message code="sysrunner.label.Export" />
		</p>
	</div>
	<div class="tbl_page_head">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<thead>
				<tr>
					<th class="tbl_page_th" width="20px">No.</th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="if.label.TABLENAME" /></th>
					<th class="tbl_page_th" width="300px"><spring:message
							code="if.label.FILENAME" /></th>
					<th class="tbl_page_th" width="100px"><spring:message
							code="index.label.sm.Operation" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl_page_body" style="min-height: 100px; height: 100px;">
		<table
			class="table table-striped table-bordered table-condensed tbl_page">
			<tbody>
				<c:forEach var="dto" items="${expList}" varStatus="i">
					<tr>
						<td class="vtip" align="center" width="20px">${(page.number*page.size)+(i.index+1)}</td>
						<td class="tbl_page_td_left vtip" width="300px">${dto.tableName}</td>
						<td class="tbl_page_td_right vtip" width="300px">${dto.fileName}</td>
						<td align="center" width="100px">
							<div style="height: 25px">
								<input type="button" class="btn btn-small"
									onclick="cfgSelect('3','${dto.expId}','${dto.comments}')"
									value="<spring:message code="button.lable.Choose"/>">
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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

