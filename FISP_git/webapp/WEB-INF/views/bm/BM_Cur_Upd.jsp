<script type="text/javascript">
  	//modify button
		function modSubmit(currCode,currName,currLan) {			
		var form = document.getElementById("form");
		var bfCurrCode = $("#beforeCurrCode").val();
		var bfCurrLan  =$("#beforeCurrLan").val();
		form.action = "${pageContext.request.contextPath}/BM_Cur_Upd/Update?currCode="+bfCurrCode+"&currLan="+bfCurrLan;
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
		<spring:hasBindErrors name="bm_Cur_QryForm">
			<form:form commandName="bm_Cur_QryForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<!-- title -->
<div class="page_title"><spring:message code="fisp.la.currencyUpdata"/></div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="bm_Cur_QryForm" class="form-horizontal">
		<input id="beforeCurrCode" type="hidden" path="currCode" value="${bm_Cur_QryForm.currCode}" />
		<input id="beforeCurrLan" type="hidden" path="sysCurrency.currLan"  value="${bm_Cur_QryForm.sysCurrency.currLan}" />
		<table class="tbl_search">
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.currCode"/>:</td>
				<td>
					<form:input  id="currCode" path="sysCurrency.currCode" type="text" class=".input-small" />		
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.currLan"/>:</td>
				<td>
					<form:select id="currLan" path="sysCurrency.currLan" class=".input-small">
						<form:option value=""/>
						<form:options items="${BM_DICT}" />
					</form:select>
				</td>
			</tr>		
			<tr>
			   <td class="label_td"><font color="red">*</font><spring:message code="index.label.bm.CurrName"/>:</td>
				<td colspan="3">	
					<form:input id="currName" path="sysCurrency.currName" type="text" class="span5"  />
				</td>				
			</tr>
	    </table>								
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="confirmBtn" class="btn btn-primary" onclick="modSubmit('${f:h(sysCurrency.currCode)}','${f:h(sysCurrency.currName)}','${f:h(sysCurrency.currLan)}')" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
