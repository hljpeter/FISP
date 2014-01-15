<%@page import="com.synesoft.fisp.app.common.constants.CommonConst"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/jquery-ui-1.8.21/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript">
	if (typeof (window.opener) == 'undefined')
		window.opener = window.dialogArguments;
	var dragsource_number_all;
	var paramType_str = "<%=CommonConst.paramType_str%>";
	var paramType_col = "<%=CommonConst.paramType_col%>";
	var paramType_expr = "<%=CommonConst.paramType_expr%>";
	$(function() {
		if ('undefined' == $("#express").val()
				|| '' == $.trim($("#express").val())) {
			$("#confirmBtn").attr("disabled", "disabled");
		}

		var dpMethodParam_count = document
				.getElementsByName('dpMethodParam_count');
		var dpMethod_count = document.getElementsByName('dpMethod_count');
		var dpExprTmps_count = document.getElementsByName('dpExprTmps_count');
		var dpTableDtls_count = document.getElementsByName('dpTableDtls_count');
		$("#methodTable").find("tr").bind('click', function() {
			$('#addZZBtn').removeAttr("disabled");
			$('#addLSBtn').removeAttr("disabled");
			var methodName = $(this).find("td:eq(0)").text();
			var methodNameList = document.getElementsByName('methodName');
			$("#selected_mothedName").val(methodName);
			for ( var i = 0; i < methodNameList.length; i++) {
				var methodName_tmp = methodNameList[i];
				if (methodName == methodName_tmp.value) {
					var divId = 'div_' + methodName;
					document.getElementById(divId).style.display = 'block';
				} else {
					var divId = 'div_' + methodName_tmp.value;
					document.getElementById(divId).style.display = 'none';
				}
			}

		});

		for (i = 0; i < dpTableDtls_count.length; i++) {
			$("#dragsource_" + i).draggable(
					{
						start : function(event, ui) {

							$("#selected_field_type").val(
									$(this).find("td:eq(0)").text());
							$("#selected_field_name").val(
									$(this).find("td:eq(2)").text());
							$("#selected_param_type").val(paramType_col);
						},

						helper : "clone",
						cursor : 'move',
						appendTo : "parent",
						revert : "invalid",
					});
		}

		for (i = 0; i < dpExprTmps_count.length; i++) {
			$("#dragsource_tmpExpress_" + i).draggable(
					{
						start : function(event, ui) {
							$("#selected_field_type").val(
									$(this).find("td:eq(0)").text());
							$("#selected_field_name").val(
									$(this).find("td:eq(1)").text());
							$("#selected_field_key").val(
									$(this).find("td:eq(2)").text());
							$("#selected_param_type").val(paramType_expr);
						},

						helper : "clone",
						cursor : 'move',
						appendTo : "parent",
						revert : "invalid"
					});
		}

		for (k = 0; k < dpMethod_count.length; k++) {
			for (j = 0; j < dpMethodParam_count.length; j++) {
				$("#container_" + k + "_" + j)
						.droppable(
								{
									drop : function(event, ui) {

										var selected_type = $(
												"#selected_field_type").val();
										var selected_name = $(
												"#selected_field_name").val();
										var selected_param_type = $(
												"#selected_param_type").val();
										var selected_field_key = $(
												"#selected_field_key").val();
										var param_customize_flag = $(this)
												.find("input:eq(3)").attr(
														"checked");

										if (null != param_customize_flag
												|| true == param_customize_flag) {
											alert("自定义不能拖拽");
											return;
										}
										if (selected_type != $(this).parent()
												.find("td:eq(1)").text()) {
											var match_type = selected_type
													+ "|"
													+ $(this).parent().find(
															"td:eq(1)").text();
											var matchList = document
													.getElementsByName('matchList');
											for ( var i = 0; i < matchList.length; i++) {
												if (matchList[i].value == match_type) {
													$(this)
															.find("input:eq(0)")
															.val(
																	selected_field_key);
													$(this)
															.find("input:eq(1)")
															.val(
																	selected_param_type);
													$(this).find("input:eq(2)")
															.val(selected_name);
													return;
												}

											}
											alert($("#alertMsg2").val());
											return;
										}

										$(this).find("input:eq(0)").val(
												selected_field_key);
										$(this).find("input:eq(1)").val(
												selected_param_type);
										$(this).find("input:eq(2)").val(
												selected_name);
									}
								});
			}
		}

	});

	function getValue() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/Dp_Exp/CreateExpress";
		form.submit();
	};

	function getLSValue() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/Dp_Exp/CreateTmpExpress";
		form.submit();
	};
	//input button
	function input() {

		window.returnValue = {
			'expressVal' : $("#express").val(),
			'expressKey' : $("#express_key").val()
		};
		if (window.opener && window.opener != null) {
			window.opener.ReturnValue = {
				'expressVal' : $("#express").val(),
				'expressKey' : $("#express_key").val()
			};
		}
		window.close();

	};

	function expressClose() {
		window.returnValue = null;
		if (window.opener && window.opener != null) {
			window.opener.ReturnValue = null;
		}
		window.close();
	};

	function checkInput(obj) {
		var type = $(obj).parent().parent().find("td:eq(1)").text();
		var tmptxt = $(obj).val();
		if (type == 'java.lang.Integer') {
			$(obj).attr("maxlength", "8");
			$(obj).val(tmptxt.replace(/\D|^0/g, ''));
		} else if (type == 'java.lang.Double' || type == 'java.lang.Float') {

			var regStrs = [

			[ '^0(\\d+)$', '$1' ], //禁止录入整数部分两位以上，但首位为0 

			[ '[^\\d\\.]+$', '' ], //禁止录入任何非数字和点 

			[ '\\.(\\d?)\\.+', '.$1' ], //禁止录入两个以上的点 

			[ '^(\\d+\\.\\d{2}).+', '$1' ] //禁止录入小数点后两位以上 

			];

			for (i = 0; i < regStrs.length; i++) {

				var reg = new RegExp(regStrs[i][0]);
				$(obj).val($(obj).val().replace(reg, regStrs[i][1]));

			}
		} else {
			$(obj).attr("maxlength", "8");
		}
	};

	function changeType(id) {
		var checkId = id + "_check";
		var valueId = id + "_value";
		var check_flag = document.getElementById(checkId).checked;
		if (null == check_flag || check_flag == false) {
			if ('' != $("#" + valueId).val()) {
				var msg = "使用列名或是表达式时，不可修改参数配置信息，原自定义参数配置信息将清空！";
				if (confirm(msg)) {
					$("#" + valueId).val("");
					$("#" + valueId).attr("readonly", "readonly");
				} else {
					document.getElementById(checkId).checked = true;
				}
			} else {
				$("#" + valueId).attr("readonly", "readonly");
			}

		} else {
			if ('' != $("#" + valueId).val()) {
				var msg = "自定义时，不可使用列名或是表达式，原参数配置信息将清空！";
				if (confirm(msg)) {
					$("#" + valueId).val("");
					$("#" + valueId).removeAttr("readonly");
				} else {
					document.getElementById(checkId).checked = false;
				}
			} else {
				$("#" + valueId).removeAttr("readonly");
			}
		}
	};
</script>

<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel />
		<spring:hasBindErrors name="dp_ExpForm">
			<form:form commandName="dp_ExpForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">
	<spring:message code="dp.title.MapExpr" />
</div>
<form:form id="form" method="post"
	action="${pageContext.request.contextPath}/DP_Mpp_Add/SubmitCfg"
	modelAttribute="dp_ExpForm" class="form-horizontal">
	<div>
		<input type="hidden" id="selected_field_type"> <input
			type="hidden" id="selected_field_name"> <input type="hidden"
			id="selected_mothedName" name="selected_mothedName"> <input
			type="hidden" id="srcTable" name="srcTable"
			value="${dp_ExpForm.srcTable}"> <input type="hidden"
			id="selected_param_type">
		<form:input path="dp_type" type="hidden" />
		<form:input path="cfg_Id" type="hidden" />
		<form:input path="branch_Id" type="hidden" />

		<input type="hidden" id="selected_field_key">
		<table class="tbl_search">

			<tr>
				<td align="left">
					<fieldset style="width: 98%; padding: 2px; height: 98%">
						<table>
							<tr height="20px">
								<td align="left" valign="bottom">&nbsp;&nbsp;<spring:message
										code="iem.title.GongShiQu" />&nbsp;&nbsp;&nbsp;&nbsp;<font
									color="red" style="font-size: 12px;">(<spring:message
											code="fisp.notice.express.notice3" />)
								</font></td>
								<td>&nbsp;</td>
								<td align="left" valign="bottom">&nbsp;&nbsp;<spring:message
										code="dp.lable.FieldArea" />&nbsp;&nbsp;&nbsp;&nbsp;<font
									color="red" style="font-size: 12px">(<spring:message
											code="fisp.notice.express.notice1" />)
								</font>
								</td>
							</tr>
							<tr>
								<td style="width: 50%">
									<table style="width: 100%">
										<tr>
											<td>
												<div class="tbl_page_head">
													<table
														class="table table-striped table-bordered table-condensed tbl_page">
														<thead>
															<tr>
																<th class="tbl_page_th" width="60px"><spring:message
																		code="iem.label.METHOD_NAME" /></th>
																<th class="tbl_page_th" width="180px"><spring:message
																		code="iem.label.METHOD_Des" /></th>
															</tr>
														</thead>
													</table>
												</div>
												<div class="tbl_page_body"
													style="min-height: 100px; height: 155px;">
													<table id="methodTable"
														class="table table-striped table-bordered table-condensed tbl_page">
														<tbody>
															<c:forEach var="dto" items="${dp_ExpForm.dpExprMethods}"
																varStatus="i">
																<input type="hidden" name="dpMethod_count" />
																<tr>
																	<td class="tbl_page_td_left vtip" width="60px">${dto.methodName}</td>
																	<td class="tbl_page_td_left vtip" width="180px">${dto.methodDesc}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td align="center"><br></td>
								<td style="width: 50%">
									<table style="width: 100%">
										<tr>
											<td>
												<div class="tbl_page_head">
													<table
														class="table table-striped table-bordered table-condensed tbl_page">
														<thead>
															<tr>
																<th class="tbl_page_th" width="50px"><spring:message
																		code="iem.label.TYPE" /></th>
																<th class="tbl_page_th" width="100px"><spring:message
																		code="de.title.srctable" /></th>
																<th class="tbl_page_th" width="80px"><spring:message
																		code="fisp.label.common.colName" /></th>
															</tr>
														</thead>
													</table>
												</div>
												<div class="tbl_page_body"
													style="min-height: 100px; height: 155px;">

													<table id="dpTableDtls"
														class="table table-bordered table-condensed tbl_page">

														<tbody>
															<c:forEach var="dto" items="${dp_ExpForm.dpTableDtls}"
																varStatus="i">
																<input type="hidden" name="dpTableDtls_count">
																<tr id="dragsource_${i.index}">
																	<td class="tbl_page_td_left vtip" width="57px">${dto.colType}</td>
																	<td class="tbl_page_td_left vtip" width="100px">${dto.colDesc}</td>
																	<td class="tbl_page_td_left vtip" width="80px">${dto.colName}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>

	<div style="margin-bottom: 40px;">

		<table class="tbl_search">

			<tr>
				<td align="left">
					<fieldset style="width: 98%; padding: 2px; height: 98%">
						<table>
							<tr height="20px">
								<td align="left" valign="bottom">&nbsp;&nbsp;<spring:message
										code="dp.lable.ParamArea" /></td>
								<td>&nbsp;</td>
								<td align="left" valign="bottom">&nbsp;&nbsp;<spring:message
										code="dp.lable.TmpExprArea" />&nbsp;&nbsp;&nbsp;&nbsp;<font
									color="red" style="font-size: 12px">(<spring:message
											code="fisp.notice.express.notice2" />)
								</font></td>
							</tr>
							<tr>
								<td style="width: 50%"><c:if
										test="${null ne dp_ExpForm.dpExprMethodDtls}">
										<c:forEach var="dto" items="${dp_ExpForm.dpExprMethodDtls}"
											varStatus="i">
											<div id="div_${dto[0].methodName}" style="display: none;">
												<input name="methodName" value="${dto[0].methodName}"
													type="hidden" />
												<table style="width: 100%">
													<tr>
														<td>
															<div class="tbl_page_head">
																<table
																	class="table table-striped table-bordered table-condensed tbl_page">
																	<thead>
																		<tr>
																			<th class="tbl_page_th" width="30px"><spring:message
																					code="iem.label.DESC" /></th>
																			<th class="tbl_page_th" width="60px"><spring:message
																					code="iem.label.TYPE" /></th>
																			<th class="tbl_page_th" width="160px"><spring:message
																					code="iem.title.BiaodaShi" /></th>
																		</tr>
																	</thead>
																</table>
															</div>
															<div class="tbl_page_body"
																style="min-height: 100px; height: 155px;">
																<table
																	class="table table-bordered table-condensed tbl_page">
																	<tbody>
																		<c:forEach var="dto1" items="${dto}" varStatus="i1">
																			<input type="hidden" name="dpMethodParam_count">
																			<tr>
																				<td class="tbl_page_td_left vtip" width="30px">${dto1.paramDesc}</td>
																				<td class="tbl_page_td_left vtip" width="60px">${dto1.paramType}</td>
																				<td class="tbl_page_td_left" width="160px"
																					id="container_${i.index}_${i1.index}"><input
																					id="container_${i.index}_${i1.index}_key"
																					name="dpMethodParamVals['${dto[0].methodName}'][${i1.index}]"
																					value="" type="hidden" /> <input
																					id="container_${i.index}_${i1.index}_type"
																					name="dpMethodParamVals['${dto[0].methodName}'][${i1.index}]"
																					value="" type="hidden" /> <input
																					id="container_${i.index}_${i1.index}_value"
																					name="dpMethodParamVals['${dto[0].methodName}'][${i1.index}]"
																					value="" class="input-large"
																					onblur="checkInput(this)" readonly="readonly"
																					style="height: 20px;" /> <input type="checkbox"
																					id="container_${i.index}_${i1.index}_check"
																					value="1"
																					onclick="changeType('container_${i.index}_${i1.index}')">
																				</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</td>
													</tr>
												</table>
											</div>

										</c:forEach>

									</c:if></td>
								<td align="center"><br></td>
								<td style="width: 50%">
									<table style="width: 100%">
										<tr>
											<td>
												<div class="tbl_page_head">
													<table
														class="table table-striped table-bordered table-condensed tbl_page">
														<thead>
															<tr>
																<th class="tbl_page_th" width="120px"><spring:message
																		code="iem.label.TYPE" /></th>
																<th class="tbl_page_th" width="120px"><spring:message
																		code="iem.title.BiaodaShi" /></th>
															</tr>
														</thead>
													</table>
												</div>
												<div class="tbl_page_body"
													style="min-height: 100px; height: 155px;">

													<table
														class="table table-bordered table-condensed tbl_page">

														<tbody>
															<c:forEach var="dto" items="${dp_ExpForm.dpExprTmps}"
																varStatus="i">
																<input type="hidden" name="dpExprTmps_count">
																<tr id="dragsource_tmpExpress_${i.index}">
																	<td class="tbl_page_td_left vtip" width="120px">${dto.expressReturn}</td>
																	<td class="tbl_page_td_left vtip"
																		style="text-align: center;" width="120px">${dto.expressVal}</td>
																	<td style="display: none;">${dto.rsv1}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="20px">
								<td align="right" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;<input
									type="button" class="btn btn-primary"
									value="<spring:message code="exm.linshigongshiqu"/>"
									id="addLSBtn" onclick="getLSValue()" disabled="disabled" />
								</td>
								<td>&nbsp;</td>
								<td align="left" valign="bottom">&nbsp;&nbsp;<input
									type="button" class="btn btn-primary"
									value="<spring:message code="exm.baodashi"/>" id="addZZBtn"
									onclick="getValue()" disabled="disabled" /></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

		</table>

		<div style="display: none;">
			<c:forEach var="dto" items="${matchList}" varStatus="i">
				<input type="hidden" name="matchList" value="${dto}" />
			</c:forEach>
		</div>

		<table class="tbl_search">

			<tr>
				<td align="center"><spring:message code="iem.title.BiaodaShi" /></td>
				<td align="left"><form:input id="express_key"
						path="express_key" type="hidden" /> <form:textarea id="express"
						path="express" rows="2"
						style="overflow-x: hidden; overflow-y: auto; width: 75%; resize: none;"
						readonly="true" /></td>
			</tr>
			<tr>
				<td align="center"><spring:message code="dp.lable.srcExpress" /></td>
				<td align="left"><form:input id="oldExpress_key"
						path="oldExpress_key" type="hidden" /> <form:textarea
						id="oldExpress" path="oldExpress" rows="2"
						style="overflow-x: hidden; overflow-y: auto; width: 75%; resize: none;"
						readonly="true" /></td>
			</tr>

		</table>
	</div>

</form:form>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary"
			onclick="input()"
			value="<spring:message code="button.lable.Submit"/>"> <input
			type="button" class="btn btn-primary" onclick="expressClose()"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>