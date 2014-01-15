<script type="text/javascript">
	function audit(){
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/Draft_Item/Audit";
		form.submit();
	}

</script>
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="draft_Sheet_Form">
			<form:form commandName="draft_Sheet_Form">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="pisa.title.index.audit"/></div>
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="draft_Sheet_Form" class="form-horizontal">
			<table class="tbl_search">
		    	<tr>
		    		<td class="label_td"><spring:message code="pisa.index.sheet.no"/></td>
					<td>
						<form:input path="expSheetDtl.sheetNo" type="text" class=".input-small" readonly="true"/>
					</td>
					<td class="label_td"><spring:message code="pisa.index.sheet.seqno"/></td>
					<td>
						<form:input path="expSheetDtl.seqNo" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.draft.Sub_NO"/></td>
					<td>
						<form:input path="expSheetDtl.subNo" type="text" class=".input-small" readonly="true"/>
					</td>
					<td class="label_td"><spring:message code="pisa.draft.Bat_No"/></td>
					<td>
						<form:input path="expSheetDtl.batNo" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.draft.Area_Type"/></td>
					<td>
					<form:select path="expSheetDtl.areaType" disabled="true">	    				
							<form:options items="${BQ_0003}" readonly="true"/>
					</form:select>			
					</td>
					<td class="label_td"><spring:message code="pisa.draft.Area_Code"/></td>
					<td>
					<form:input path="expSheetDtl.areaCode" type="text" class=".input-small" readonly="true"/> 					
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.item.name"/></td>
					<td colspan="3">
						<form:input path="expSheetDtl.itemName" type="text" class="span8" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.index.dim.name"/></td>
					<td colspan="3">
						<form:input path="expSheetDtl.dimName" type="text" class="span8" readonly="true"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.index.item.no"/></td>
					<td>
						<form:input path="expSheetDtl.itemNo" type="text" class=".input-small" readonly="true"/>
					</td>
					<td class="label_td"><spring:message code="pisa.index.dim.no"/></td>
					<td>
						<form:input path="expSheetDtl.dimNo" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td"><spring:message code="pisa.draft.Data_Type1"/></td>
					<td  colspan="3">
						<form:input path="expSheetDtl.dataType1" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="pisa.draft.Data_Value1"/></td>
					<td>
						<form:input path="expSheetDtl.dataValue1" type="text" class=".input-small" readonly="true"/>
					</td>
					<td class="label_td"><spring:message code="pisa.draft.Data_Value2"/></td>
					<td>
						<form:input path="expSheetDtl.dataValue2" type="text" class=".input-small" readonly="true"/>
					</td>
				</tr>
				<tr>
				<td class="label_td"><spring:message code="pisa.index.creater" />：</td>
				<td><form:input id="makUserId" path="expSheetDtl.makUserId"
						class=".input-large" readonly="true" /></td>
				<td class="label_td"><spring:message code="pisa.index.create.time" />：</td>
				<td><form:input id="makDatetime" path="expSheetDtl.makDatetime"
						class=".input-large" readonly="true" /></td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="index.label.sm.Checker" />：</td>
					<td><form:input id="chkUserId" path="expSheetDtl.chkUserId"
							class=".input-large" readonly="true" /></td>
					<td class="label_td"><spring:message code="index.label.sm.CheckTime" />：</td>
					<td><form:input id="chkDatetime" path="expSheetDtl.chkDatetime"
							class=".input-large" readonly="true" /></td>
				</tr>
	    	</table>
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
	<input type="button" class="btn btn-primary" onclick="audit();" value="<spring:message code="button.lable.Auth"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
