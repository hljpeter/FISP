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
			showDialog('${pageContext.request.contextPath}/FTZ210501/QryDtl?selected_actNo='
					+ selected_actNo +'&selected_subActNo='+ selected_subActNo,'500','1024');
		});
	});
	
	//search button
	function search() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/FTZ210501/InputQry?page.page="+${page.number+1};
		form.submit();
	}
	
	//add button
	function addAct() {
		var form = document.getElementById("form");
		showDialog('${pageContext.request.contextPath}/FTZ210501/InputInit','500','1024');
		form.submit();
	}
	
	//update button
	function updateAct() {
		var selected_actNo = $("#selected_actNo").val();
		var selected_subActNo = $("#selected_subActNo").val();
		$.ajax({
			url : contextPath + "/FTZ210501/QryActDtl",
			type : "post",
			dataType : "json",
			async : false,
			data : {
				accountNo : selected_actNo,
				subAccountNo : selected_subActNo
			},
			success : function(rs) {
				dtlExist = rs.dtlExist;
				if (null == dtlExist || false == dtlExist) {
					$("#updAuthFlag").val("n");
				} else {
					$("#updAuthFlag").val("y");
				}
			}
		});
		if(null == selected_actNo || "" == selected_actNo) {
			alert('<spring:message code="ftz.validate.choose.dataAct"/>');
			return;
		} else {
			if("n" == $("#updAuthFlag").val()) {
				showDialog('${pageContext.request.contextPath}/FTZ210501/InputUpd?selected_actNo='+
						selected_actNo+'&selected_subActNo='+selected_subActNo, '500', '1024');
				search();
			} else if("y" == $("#updAuthFlag").val()){
				alert('<spring:message code="ftz.validate.act.notChk"/>');
			}
		}
	}
	
	//delete button
	function deleteAct() {
		var selected_actNo = $("#selected_actNo").val();
		if(null == selected_actNo || "" == selected_actNo) {
			alert('<spring:message code="ftz.validate.choose.dataAct"/>');
			return;
		} else {
			var form = document.getElementById("form");
			form.action = "${pageContext.request.contextPath}/FTZ210501/Delete";
			var msg = $("#confirmMsg1").val() + $("#del").val() + $("#confirmMsg2").val();
			if(confirm(msg)) {
				form.submit();
			} else {
				return false;
			}
		}
	}
	
	//detail button
	function openDetail() {
		var selected_actNo = $("#selected_actNo").val();
		var selected_subActNo = $("#selected_subActNo").val();
		if(null == selected_actNo || "" == selected_actNo) {
			alert('<spring:message code="ftz.validate.choose.dataAct"/>');
			return;
		} else {
			showDialog('${pageContext.request.contextPath}/FTZ210501/QryDtl?selected_actNo='
					+ selected_actNo+'&selected_subActNo='+selected_subActNo,'500','1024');
		}
	}
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div id="id_result">
		<t:messagePanel />
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error"/>
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info"/>
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success"/>
		<spring:hasBindErrors name="FTZ210501Form">
			<form:form commandName="FTZ210501Form">
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
		action="${pageContext.request.contextPath}/FTZ210501/InputQry"
		method="post" modelAttribute="FTZ210501Form" class="form-horizontal">
		<form:hidden path="selected_actNo" id="selected_actNo" />
		<form:hidden path="selected_subActNo" id="selected_subActNo" />
		<input type="hidden" id="updAuthFlag"/>
		<table class="tbl_search">
			<tr>
				<td class="label_td"><spring:message code="ftz.label.BRANCH"/>：</td>
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
				<td><form:input id="query_accountNo" path="query_accountNo" class=".input-large"
						onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
				</td>
				<td class="label_td"><spring:message code="ftz.label.SUB_ACCOUNT_NO"/>：</td>
				<td><form:input id="query_subAccountNo" path="query_subAccountNo" class=".input-large"
						onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
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
				</form:select></td>
			</tr>
			<tr>
				<td style="text-align: right;" colspan="4">
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
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.BRANCH"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.ACCOUNT_NAME"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.ACCOUNT_NO"/></th>
					<th style="vertical-align: middle; text-align: center" width="150px"><spring:message code="ftz.label.SUB_ACCOUNT_NO"/></th>
					<th style="vertical-align: middle; text-align: center" width="100px"><spring:message code="ftz.label.CURRENCY"/></th>
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
						<td class="vtip" style="text-align: left; width: 150px;"><t:codeValue
								items="${SM_0002}" key="${dto.branchId}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.accountName}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.accountNo}</td>
						<td class="vtip" style="text-align: left; width: 150px;">${dto.subAccountNo}</td>
						<td class="vtip" style="text-align: left; width: 100px;"><t:codeValue
								items="${SYS_CURRENCY}" key="${dto.currency}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_ACC_TYPE}" key="${dto.accType}" type="label" /></td>
						<td class="vtip" style="text-align: left; width: 50px;"><t:codeValue
								items="${FTZ_ACC_STATUS}" key="${dto.accStatus}" type="label" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pagination pull-right" style="margin-top: 10px;">
	<div class="rightPage">
		<util:pagination page="${page}" query="query_branchId=${FTZ210501Form.query_branchId}&query_accountName=${FTZ210501Form.query_accountName}
			&query_accountNo=${FTZ210501Form.query_accountNo}&query_subAccountNo=${FTZ210501Form.query_subAccountNo}&query_currency=${FTZ210501Form.query_currency}
			&query_accType=${FTZ210501Form.query_accType}&query_accStatus=${FTZ210501Form.query_accStatus}"
			action="/FTZ210501/InputQry"/>
	</div>
</div>
<div class="row" style="margin-bottom: 40px;">
	<div class="navbar navbar-fixed-bottom text-center"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="add" type="button" class="btn btn-primary" onclick="addAct();" value="<spring:message code="button.lable.Add"/>">
		<input id="upd" type="button" class="btn btn-primary" onclick="updateAct();" value="<spring:message code="button.lable.Modify"/>">
		<input id="del" type="button" class="btn btn-primary" onclick="deleteAct();" value="<spring:message code="button.lable.Del"/>">
		<input id="dtl" type="button" class="btn btn-primary" onclick="openDetail();" value="<spring:message code="dp.lable.detail"/>">
	</div>
</div>