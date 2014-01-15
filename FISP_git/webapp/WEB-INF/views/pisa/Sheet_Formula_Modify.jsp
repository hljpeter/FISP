<script type="text/javascript">

	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input").prop("disabled", true);
			$("select").prop("disabled", true);
			$("textarea").prop("disabled", true);
		}
	});

	//add button
	function modify() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/Sheet_Formula_Modify/Modify";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sheet_Formula_Form">
			<form:form commandName="sheet_Formula_Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="pisa.title.sheet.formula.add"/></div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/Init" method="post" modelAttribute="sheet_Formula_Form" class="form-horizontal">
			<table class="tbl_search">
				<tr><td colspan="4">
					<form:input id="formulaId" path="expSheetFormula.formulaId" type="hidden"/>
					<form:input id="updater" path="expSheetFormula.updater" type="hidden"/>
					<form:input id="updateTime" path="expSheetFormula.updateTime" type="hidden"/>
				</td></tr>
		    	<tr>
		    		<td class="label_td"><font color="red">*</font><spring:message code="pisa.index.sheet.no"/></td>
					<td>
						<form:select path="sheetNo" class=".input-small">
	    				<form:option value=""></form:option>
						<form:options items="${BQ_0001}" />
					</form:select>
					</td>
					<td class="label_td"><spring:message code="pisa.index.item.no"/></td>
					<td>
						<form:input path="expSheetFormula.itemNo" type="text" class=".input-small"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.item.name"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.itemName" type="text" class="span9"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.index.dim.no"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.dimNo" type="text" class=".input-small"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.dim.name"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.dimName" type="text" class="span9"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><font color="red">*</font><spring:message code="pisa.index.formula.name"/></td>
					<td colspan="3">
						<form:input path="formulaName" type="text" class="span9"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><font color="red">*</font><spring:message code="pisa.index.formula"/></td>
					<td colspan="3">
						<form:textarea path="formulaArea" class="span9" rows="5"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.formula.comment"/></td>
					<td colspan="3">
						<form:textarea path="expSheetFormula.formulaDesc" class="span9" rows="3"/>
					</td>
				</tr>
	    	</table>
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="modify()" value="<spring:message code="button.lable.Submit"/>"/>
		<button type="button" class="btn btn-primary" onclick="javascript:window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>
