<!-- title -->
<div class="page_title"><spring:message code="index.label.bm.DataDictMaintain.detail"/></div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="bm_Data_QryForm" class="form-horizontal">
		<table class="tbl_search">
		    <tr>
	    		<td class="label_td"><spring:message code="index.label.bm.metaGropCode"/></td>
				<td>
					<form:input path="sysDataDict.groupCode" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.bm.metaLan"/></td>
				<td>				
					<t:codeValue items="${BM_DICT}" key="${bm_Data_QryForm.sysDataDict.metaLan}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
	    		<td class="label_td"><spring:message code="index.label.bm.metaGropName"/></td>
				<td colspan="3">
					<form:input path="sysDataDict.groupName" type="text" class="span6" readonly="true"/>
				</td>				
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="index.label.bm.metaVal"/></td>
				<td colspan="3">
					<form:input path="sysDataDict.metaVal" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="index.label.bm.metaName"/></td>
				<td colspan="3">
					<form:input path="sysDataDict.metaName" type="text" class="span6" readonly="true"/>
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="index.label.bm.metaDesc"/></td>
				<td colspan="3">
					<form:textarea path="sysDataDict.metaDesc" class="span9" rows="5" readonly="true"/>
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
