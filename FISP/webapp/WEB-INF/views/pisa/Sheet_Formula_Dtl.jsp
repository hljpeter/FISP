<!-- title -->
<div class="page_title"><spring:message code="pisa.title.sheet.formula.dtl"/></div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="sheet_Formula_Form" class="form-horizontal">
			<table class="tbl_search">
		    	<tr>
		    		<td class="label_td"><spring:message code="pisa.index.sheet.no"/></td>
					<td>
						<form:input path="expSheetFormula.sheetNo" type="text" class=".input-small" readonly="true"/>
					</td>
					<td class="label_td"><spring:message code="pisa.index.item.no"/></td>
					<td>
						<form:input path="expSheetFormula.itemNo" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.item.name"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.itemName" type="text" class="span8" readonly="true"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.index.dim.no"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.dimNo" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.dim.name"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.dimName" type="text" class="span8" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.formula.name"/></td>
					<td colspan="3">
						<form:input path="expSheetFormula.formulaName" type="text" class="span8" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.formula"/></td>
					<td colspan="3">
						<form:textarea path="expSheetFormula.formulaArea" class="span8" rows="5" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.formula.comment"/></td>
					<td colspan="3">
						<form:textarea path="expSheetFormula.formulaDesc" class="span8" rows="3" readonly="true"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.index.creater"/></td>
					<td>
						<form:input path="expSheetFormula.creater" type="text" class=".input-small" readonly="true"/>
					</td>
		    		<td class="label_td"><spring:message code="pisa.index.create.time"/></td>
					<td>
						<t:dateTimeFormat type="text" value="${sheet_Formula_Form.expSheetFormula.createTime }" readonly="true" cssClass=".input-small" format="datetime"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.index.updater"/></td>
					<td>
						<form:input path="expSheetFormula.updater" type="text" class=".input-small" readonly="true"/>
					</td>
		    		<td class="label_td"><spring:message code="pisa.index.update.time"/></td>
					<td>
						<t:dateTimeFormat type="text" value="${sheet_Formula_Form.expSheetFormula.updateTime }" readonly="true" cssClass=".input-small" format="datetime"/>
					</td>
				</tr>
	    	</table>
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
