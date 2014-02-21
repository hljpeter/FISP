<script type="text/javascript">
	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("input").prop("disabled", true);
		}
	});
	//modify button
	function modify() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/nsm01/03/modify";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	
	//bankSearch button
	/*close by wy 2013-12-05
	function bankSearch() {
		var jstr = window.showModalDialog('${pageContext.request.contextPath}/cu03/04/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		if (jstr!=null)
			setTipsBaCInf($.evalJSON(jstr));
	}
	
	function setTipsBaCInf(obj){
		$("#bankid").val(obj.reckbankno);
		$("#bankname").val(obj.genbankname);
		$("#nodecode").val(obj.ofnodecode);
	}
	
	function setUpBankInfo(){
		var bankid=$("#bankid").val();
		$.ajax({
			url: "${pageContext.request.contextPath}/cu03/04/autoMatch",
			type:"post",
			dataType:"json",
			async:true,
			data : { 
				reckbankno:bankid
		 	},
		 	success : function(rs) {
		 		setTipsBaCInf(rs);
			}
		});
	}
	*/
	function queryOrg() {
		showAllOrg([ {
			"suprorgid" : "param1",
			"suprorgname" : "param2"
		} ]);
	};
	function setSuprOrgInf(obj){
		if("false" == obj.existflag){
			var msg = $("#alertMsg1").val();
			document.getElementById("errorMsg").style.display = "block";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
		}
		$("#suprorgid").val(obj.orgid);
		$("#suprorgname").val(obj.orgname);
	}
	
	function setUpSuprOrgInf(){
		var suprorgid=$("#suprorgid").val();
		if(suprorgid!=""){
			$.ajax({
				url: "${pageContext.request.contextPath}/OrgSearch/check",
				type:"post",
				dataType:"json",
				async:true,
				data : { 
					query_orgid:suprorgid
			 	},
			 	success : function(rs) {
			 		setSuprOrgInf(rs);
				}
			});
		}else{
			$("#suprorgname").val("");
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
		<spring:hasBindErrors name="orgInfForm">
			<form:form commandName="orgInfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.OrganizationMaintenanceModify"/></div>

<!-- body -->
<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/nsm01/03/init" method="post" modelAttribute="orgInfForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td width="25%" class="label_td"><spring:message code="index.label.sm.OrganizationId"/></td>
				<td width="75%">
					<form:input path="orgInf.orgid" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="label_td">
				<font color="red">*</font>
				<spring:message code="index.label.sm.OrganizationName"/></td>
				<td>
					<form:input path="orgname" type="text" class="span8" maxlength="60"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.SuperiorOrganizationId"/></td>
				<td>
					<form:input id="suprorgid" path="suprorgid" type="text" class=".input-small" maxlength="20" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);" onblur="setUpSuprOrgInf()"/>
					<input type="button" class="btn btn-small" onclick="queryOrg()"
					value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.SuperiorOrganizationName"/></td>
				<td>
					<form:input id="suprorgname" path="orgInf.suprorgname" type="text" class="span8" maxlength="60" readonly="true"/>
				</td>
			<tr>
			<!-- close by wy 2013-12-05
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.sm.BankId"/></td>
				<td>
					<form:input id="bankid" path="bankid" type="text" class=".input-small" maxlength="12"  onblur="setUpBankInfo();" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/>
					<input id="bankSelectBtn" type="button" class="btn btn-small" onclick="bankSearch()" value="<spring:message code="button.label.Search"/>">
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.BankName"/></td>
				<td>
					<form:input id="bankname" path="orgInf.bankname" type="text" class="span8" readonly="true"/>
				</td>
			<tr>
			-->
			<tr><td colspan="2"></td></tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="modify()" value="<spring:message code="button.lable.Submit"/>"/>
		<button type="button" class="btn btn-primary" onclick="javascript:window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>