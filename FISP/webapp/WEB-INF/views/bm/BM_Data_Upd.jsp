<script type="text/javascript">
  	//modify button
	function modSubmit() {			
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/BM_Data_Upd/Upd";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){			
			form.submit();
		}else{
			return false;
		}
	}

</script>

<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="bm_Data_UpdForm">
			<form:form commandName="bm_Data_UpdForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title"><spring:message code="index.label.bm.DataDictMaintain.modify"/></div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="bm_Data_UpdForm" class="form-horizontal">
		<input id="beforeMetaNameId" type="hidden" name="beforeMetaName" value="${bm_Data_UpdForm.metaName}" />
		<input id="beforeMetaValId" type="hidden" name="beforeMetaVal" value="${bm_Data_UpdForm.metaVal}" />
		<input id="metaLanId" type="hidden" name="sysDataDict.metaLan" value="${bm_Data_UpdForm.sysDataDict.metaLan}" />
		<table class="tbl_search">		   
		    <tr>
	    		<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.metaGropCode"/></td>
				<td>
					<form:input path="sysDataDict.groupCode" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.metaLan"/></td>
				<td>				
					<t:codeValue items="${BM_DICT}" key="${bm_Data_UpdForm.sysDataDict.metaLan}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
	    		<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.metaGropName"/></td>
				<td colspan="3">
					<form:input path="sysDataDict.groupName" type="text" class="span6" readonly="true"/>
				</td>				
			</tr>
			<tr>
	    		<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.metaVal"/></td>
				<td colspan="3">
					<form:input path="metaVal" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.metaName"/></td>
				<td colspan="3">
					<form:input path="metaName" type="text" class="span6" />
				</td>
			</tr>
			<tr>
	    		<td class="label_td"><spring:message code="index.label.bm.metaDesc"/></td>
				<td colspan="3">
					<form:textarea path="sysDataDict.metaDesc" class="span9" rows="5" />
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="confirmBtn" class="btn btn-primary" onclick="modSubmit()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
