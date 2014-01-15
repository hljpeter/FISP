<script type="text/javascript">
	window.name = "curWindow";
	
	 var sourceTable;
	 sourceTable="${tableConfig.srcTable}";
	//init page
	$(function() {
		var flag = $("#procType").val();
		if (flag == 3) {
			$("#allSrcFieldsArr").removeAttr("disabled");
			$("#chooseSrcFieldsArr").removeAttr("disabled");
			$("#chooseDesFieldsArr").removeAttr("disabled");
			$("#allDescFieldsArr").removeAttr("disabled");
			$("#filterFlag").removeAttr("disabled");
			$("#SQL").removeAttr("disabled");
			$("#comments").removeAttr("disabled");
			aa.style.display = "none";
			bb.style.display = "none";
		}
		else if(flag == 1){
			$("#allSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseDesFieldsArr").attr("disabled", "disabled");
			$("#allDescFieldsArr").attr("disabled", "disabled");
			$("#filterFlag").attr("disabled", "disabled");
			$("#SQL").attr("disabled", "disabled");
			$("#comments").attr("disabled", "disabled");
			
			aa.style.display = "block";
			bb.style.display = "none";
		}
		else if(flag==2){
			$("#allSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseDesFieldsArr").attr("disabled", "disabled");
			$("#allDescFieldsArr").attr("disabled", "disabled");
			$("#filterFlag").attr("disabled", "disabled");
			$("#SQL").attr("disabled", "disabled");
			$("#comments").attr("disabled", "disabled");
			aa.style.display = "none";
			bb.style.display = "block";
		}
		var msg = "${successmsg}";
		if (msg && msg != "") {
			$("#confirmBtn").attr("disabled", "disabled");
			$("#allSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseSrcFieldsArr").attr("disabled", "disabled");
			$("#allDescFieldsArr").attr("disabled", "disabled");
			$("#chooseDesFieldsArr").attr("disabled", "disabled");
		}
	});

	//add button
	function add() {
		$("select[name='allSrcFieldsArr'] option").attr("selected", true);
		$("select[name='allDescFieldsArr'] option").attr("selected", true);
		$("select[name='chooseSrcFieldsArr'] option").attr("selected", true);
		$("select[name='chooseDesFieldsArr'] option").attr("selected", true);
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/de02/01/confirm";
		var msg = $("#confirmMsg1").val() + $("#confirmBtn").val()
				+ $("#confirmMsg2").val();
		if (confirm(msg)) {
			form.target = "curWindow";
			form.submit();
		} else {
			return false;
		}
	}
	// æ·»å 
	function addSrcFields() {
		$("#chooseSrcFieldsArr").append($("#allSrcFieldsArr option:selected"));
	}
	// ç§»é¤
	function delSrcFields() {
		$("#allSrcFieldsArr").append($("#chooseSrcFieldsArr option:selected"));
	}

	function addDesFields() {
		$("#chooseDesFieldsArr").append($("#allDescFieldsArr option:selected"));
	}
	// ç§»é¤
	function delDesFields() {
		$("#allDescFieldsArr").append($("#chooseDesFieldsArr option:selected"));
	}
	
	function set(s) {
		if (s == 3) {
			$("#allSrcFieldsArr").removeAttr("disabled");
			$("#chooseSrcFieldsArr").removeAttr("disabled");
			$("#chooseDesFieldsArr").removeAttr("disabled");
			$("#allDescFieldsArr").removeAttr("disabled");
			$("#filterFlag").removeAttr("disabled");
			$("#SQL").removeAttr("disabled");
			$("#comments").removeAttr("disabled");
			aa.style.display = "none";
			bb.style.display = "none";
		}
		else if(s == 1){
			$("#allSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseDesFieldsArr").attr("disabled", "disabled");
			$("#allDescFieldsArr").attr("disabled", "disabled");
			$("#filterFlag").attr("disabled", "disabled");
			$("#SQL").attr("disabled", "disabled");
			$("#comments").attr("disabled", "disabled");
			aa.style.display = "block";
			bb.style.display = "none";
		}
		else if(s==2){
			$("#allSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseSrcFieldsArr").attr("disabled", "disabled");
			$("#chooseDesFieldsArr").attr("disabled", "disabled");
			$("#allDescFieldsArr").attr("disabled", "disabled");
			$("#filterFlag").attr("disabled", "disabled");
			$("#SQL").attr("disabled", "disabled");
			$("#comments").attr("disabled", "disabled");
			aa.style.display = "none";
			bb.style.display = "block";
		}
	};
	
	function openChoose(){
		var rtnStr=window.showModalDialog('${pageContext.request.contextPath}/iem02/01/init?impfileIdValue='+sourceTable, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		if(rtnStr){
			$("#SQL").val(rtnStr);
		}
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none">
	<br /> <br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg"
			messagesType="success" />
		<spring:hasBindErrors name="fieldCfgForm">
			<form:form commandName="fieldCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title">
	任务管理 / 数据映射配置 /
	<spring:message code="index.lable.DtFieldCfgList" />
</div>

<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form"
		action="${pageContext.request.contextPath}/de02/01/detailSearch"
		method="post" modelAttribute="fieldCfgForm" class="form-horizontal">
		<table class="tbl_search">
			<tr>
				<td><form:input id="tId" type="hidden" path="tId" /></td>
			</tr>
			<tr height="400px">
				<td colspan="4">
					<table border="1">
						<tr>
							<td align="center">
								<fieldset style="width: 98%; padding: 2px; height: 98%">
									<table>
										<tr height="20px">
											<td align="left" valign="bottom">&nbsp;&nbsp;<spring:message
													code="index.label.destTableColumns" /></td>
											<td>&nbsp;</td>
										<td align="left" valign="bottom" style="color: #0088CC;">&nbsp;&nbsp;<spring:message
													code="index.label.chooseDestTableColumns" /></td>
											<td>&nbsp;</td>
											<td align="left" valign="bottom" style="color: #0088CC;">&nbsp;&nbsp;<spring:message
													code="index.label.chooseSrcTableColumns" /></td>
											<td>&nbsp;</td>
											<td align="left" valign="bottom">&nbsp;&nbsp;<spring:message
													code="index.label.srcTableColumns" /></td>
										</tr>
										<tr>
											<td><select multiple="multiple" name="allDescFieldsArr"
												id="allDescFieldsArr" style="width: 180px; margin: 2px;"
												size="20">
													<c:forEach var="dto3" items="${desFields}" varStatus="i">
														<option value="${dto3}">${dto3}</option>
													</c:forEach>
											</select></td>
											<td align="center"><input type="button" value="&gt&gt"
												class="btn" onclick="addDesFields()" /> <input
												type="button" value="&lt&lt" class="btn"
												onclick="delDesFields()" /></td>
											<td><select multiple="multiple"
												name="chooseDesFieldsArr" id="chooseDesFieldsArr"
												style="background-color: #CCFFFF; width: 180px; margin: 2px;"
												size="20">
													<c:forEach var="dto4" items="${chooseDesFields}"
														varStatus="i">
														<option value="${dto4}">${dto4}</option>
													</c:forEach>
											</select></td>
											<td>&nbsp;</td>
											<td><select multiple="multiple"
												name="chooseSrcFieldsArr" id="chooseSrcFieldsArr"
												style="background-color: #CCFFFF; width: 180px; margin: 2px;"
												size="20">
													<c:forEach var="dto2" items="${chooseSrcFields}"
														varStatus="i">
														<option value="${dto2}">${dto2}</option>
													</c:forEach>
											</select></td>
											<td align="center"><input
												type="button" value="&lt&lt" class="btn"
												onclick="addSrcFields()" /><input type="button" value="&gt&gt"
												class="btn" onclick="delSrcFields()" /> </td>
											<td><select multiple="multiple" name="allSrcFieldsArr"
												id="allSrcFieldsArr" style="width: 180px; margin: 2px;"
												size="20">
													<c:forEach var="dto1" items="${srcFields}" varStatus="i">
														<option value="${dto1}">${dto1}</option>
													</c:forEach>
											</select></td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td class="label_td">处理类型</td>
				<td colspan="3"><form:select path="procType"
						onchange="set(this.value);">
						<form:options items="${DE_0002}" />
					</form:select></td>
			</tr>
			<tr>
				<td class="label_td">表达式</td>
				<td colspan="3">
					<input id="SQL" type="text" class="input-xxlarge" />
					<input type="button" class="btn btn-primary" onclick="openChoose();" value="<spring:message code="field.detail.bianji"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td">备注</td>
				<td colspan="3">
					<form:input id="comments" path="comments" type="text" class="input-xxlarge" />
				</td>
			</tr>

		</table>
	</form:form>

	<div  id="aa">
		<table>
			<tr>
				<td class="label_td">SP名称</td>
				<td colspan="3">
					<input type="text" class="input-xxlarge" />
				</td>
			</tr>
		</table>
	</div>
	<div  id="bb">
		<table>
			<tr>
				<td class="label_td">SQL语句</td>
				<td colspan="3">
					<input type="text" class="input-xxlarge" />
				</td>
			</tr>
		</table>
	</div>

</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer"
		style="margin-bottom: 0px; line-height: 30px; background-color: #eee; opacity: 0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary"
			onclick="add()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary"
			onclick="javascript:window.close();"
			value="<spring:message code="button.lable.close"/>">
	</div>
</div>