<script type="text/javascript">

$(function() {
	$("select").attr("disabled", "disabled");
}
);
    window.name="curWindow";
	var impFileIdValue;
    impFileIdValue="${impFileIdValue}";
    var impFieldIdValue;
    impFieldIdValue="${impFieldIdValue}";
	var flag='';
	//验证表单
	function validSearchForm(){
		var obj={};
		obj.valid=true;

		$(".alert-error").empty();
		obj.fieldId=$("input[name='fieldId']").val();
		obj.fileId=$("input[name='fileId']").val();
		obj.fieldName=$("input[name='fieldName']").val();
		obj.dataType=$("input[name='dataType']").val();
		obj.updateWay=$("input[name='updateWay']").val();
		obj.uniquekeyFlag=$("input[name='uniquekeyFlag']").val();
		obj.updateFlag=$("input[name='updateFlag']").val();
		obj.fieldFormula=$("input[name='fieldFormula']").val();
		obj.rsv1=$("input[name='rsv1']").val();
		obj.rsv2=$("input[name='rsv2']").val();
		obj.rsv3=$("input[name='rsv3']").val();
		obj.rsv4=$("input[name='rsv4']").val();
		obj.rsv5=$("input[name='rsv5']").val();
		
		if(!$.trim(obj.fieldId)){
			$(".alert-error").append('<spring:message code="e.ta.1050"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.fileId)){
			$(".alert-error").append('<spring:message code="e.ta.1040"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.fieldName)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.dataType)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.updateWay)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.uniquekeyFlag)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.updateFlag)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.fieldFormula)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.rsv1)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.rsv2)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.rsv3)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.rsv4)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.rsv5)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		return obj;
	}
	
	function operation() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/ifc01/04/update";
		form.submit();
	}
	
	function insert() {
		flag='insert';
		$("#insertBtn").attr("disabled","disabled");
		$("#updateBtn").attr("disabled","disabled");
		$("#deleteBtn").attr("disabled","disabled");
		$("#operationBtn").removeAttr("disabled");
		$("#cancelBtn").removeAttr("disabled");
		
		$("input[name='fieldName']").removeAttr("readonly");
		$("select[name='dataType']").removeAttr("disabled");
		$("select[name='dataType']").removeAttr("readonly");
		$("select[name='updateWay']").removeAttr("disabled");
		$("select[name='updateWay']").removeAttr("readonly");
		$("select[name='uniquekeyFlag']").removeAttr("disabled");
		$("select[name='uniquekeyFlag']").removeAttr("readonly");
		$("select[name='updateFlag']").removeAttr("disabled");
		$("select[name='updateFlag']").removeAttr("readonly");
		$("input[name='fieldFormula']").removeAttr("readonly");
		$("input[name='rsv1']").removeAttr("readonly");
		$("input[name='rsv2']").removeAttr("readonly");
		$("input[name='rsv3']").removeAttr("readonly");
		$("input[name='rsv4']").removeAttr("readonly");
		$("input[name='rsv5']").removeAttr("readonly");
		
		
		$("input[name='fieldId']").val(impFieldIdValue);
		$("input[name='fileId']").val(impFileIdValue);
		$("input[name='fieldName']").val("");
		$("select[name='dataType']").val("-1");
		$("select[name='updateWay']").val("-1");
		$("select[name='uniquekeyFlag']").val("-1");
		$("select[name='updateFlag']").val("-1");
		$("input[name='fieldFormula']").val("");
		$("input[name='rsv1']").val("");
		$("input[name='rsv2']").val("");
		$("input[name='rsv3']").val("");
		$("input[name='rsv4']").val("");
		$("input[name='rsv5']").val("");
		
		
	}
	
	function update() {
		flag='update';
		$("#insertBtn").attr("disabled","disabled");
		$("#updateBtn").attr("disabled","disabled");
		$("#deleteBtn").attr("disabled","disabled");
		$("#operationBtn").removeAttr("disabled");
		$("#cancelBtn").removeAttr("disabled");
		
		$("input[name='fieldName']").removeAttr("readonly");
		$("select[name='dataType']").removeAttr("disabled");
		$("select[name='updateWay']").removeAttr("disabled");
		$("select[name='uniquekeyFlag']").removeAttr("disabled");
		$("select[name='updateFlag']").removeAttr("disabled");
		$("input[name='fieldFormula']").removeAttr("readonly");
		$("input[name='rsv1']").removeAttr("readonly");
		$("input[name='rsv2']").removeAttr("readonly");
		$("input[name='rsv3']").removeAttr("readonly");
		$("input[name='rsv4']").removeAttr("readonly");
		$("input[name='rsv5']").removeAttr("readonly");
	}
	
	function cancel() {
		$("#insertBtn").removeAttr("disabled");
		$("#updateBtn").attr("disabled","disabled");
		$("#deleteBtn").attr("disabled","disabled");
		$("#operationBtn").attr("disabled","disabled");
		$("#cancelBtn").attr("disabled","disabled");
		
		$("input[name='fieldId']").attr("readonly","true");
		$("input[name='fileId']").attr("readonly","true");
		$("input[name='fieldName']").attr("readonly","true");
		$("select[name='dataType']").attr("disabled","disabled");
		$("select[name='updateWay']").attr("disabled","disabled");
		$("select[name='uniquekeyFlag']").attr("disabled","disabled");
		$("select[name='updateFlag']").attr("disabled","disabled");
		$("input[name='fieldFormula']").attr("readonly","true");
		$("input[name='rsv1']").attr("readonly","true");
		$("input[name='rsv2']").attr("readonly","true");
		$("input[name='rsv3']").attr("readonly","true");
		$("input[name='rsv4']").attr("readonly","true");
		$("input[name='rsv5']").attr("readonly","true");
		
		$("input[name='fieldId']").val("");
		$("input[name='fileId']").val("");
		$("input[name='fieldName']").val("");
		$("select[name='dataType']").val("-1");
		$("select[name='updateWay']").val("-1");
		$("select[name='uniquekeyFlag']").val("-1");
		$("select[name='updateFlag']").val("-1");
		$("input[name='fieldFormula']").val("");
		$("input[name='rsv1']").val("");
		$("input[name='rsv2']").val("");
		$("input[name='rsv3']").val("");
		$("input[name='rsv4']").val("");
		$("input[name='rsv5']").val("");
	}
	
	function operation() {
		var form = document.getElementById("form");
		form.target="curWindow";
		form.action="";
		var msg='';
		if(flag=='update')
		{
			msg=$("#confirmMsg1").val()+$("#updateBtn").val()+$("#confirmMsg2").val();
			form.action = "${pageContext.request.contextPath}/ifdc01/04/update?impFileIdValue="+impFileIdValue;
			if (confirm(msg)){
				form.submit();
				return;
				}
				else{
					return false;	
				}
		}
		if(flag='insert')
		{
			msg=$("#confirmMsg1").val()+$("#insertBtn").val()+$("#confirmMsg2").val();
			form.action = "${pageContext.request.contextPath}/ifdc01/03/insert?impFileIdValue="+impFileIdValue;
			if (confirm(msg)){
				form.submit();
				return;
				}
				else{
					return false;	
				}
		}
	}
	
	//delete button
	function del()
	{
		form.target="curWindow";
		form.action = "${pageContext.request.contextPath}/ifdc01/05/delete?impFileIdValue="+impFileIdValue;
		if (confirm($("#confirmMsg1").val()+$("#deleteBtn").val()+$("#confirmMsg2").val())){
			form.submit();
			}
			else{
				return false;	
			}
	}
	
	//detail button
	function detailSearch(fieldId) {
		
		var form = document.getElementById("form");
		form.action = '${pageContext.request.contextPath}/ifdc01/02/detailSearch?impfieldId='+ fieldId+'&updel='+1;
		form.target="curWindow";
		form.submit();
	}
	
	
	function openChoose(){
		var rtnStr=window.showModalDialog('${pageContext.request.contextPath}/iem01/01/init', window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		if(rtnStr){
			$("#fieldFormula").val(rtnStr);
		}
	}
	
	
	function setImpFieldInfo(FieldIdValue){
		$.ajax({
			url: "${pageContext.request.contextPath}/ifdc01/02/detailSearch?impfieldId="+ FieldIdValue+'&updel='+1,
			type:"post",
			dataType:"json",
			async:true,
			data : { 
				reckfieldIdValue:FieldIdValue
		 	},
		 	success : function(rs) {
		 		setFieldInfo(rs);
			}
		});
	}
	
	function setFieldInfo(obj){
		$("#fieldId").val(obj.fieldId);
		$("#fileId").val(obj.fileId);
		$("#fieldName").val(obj.fieldName);
		$("#dataType").val(obj.dataType);
		$("#updateWay").val(obj.updateWay);
		$("#uniquekeyFlag").val(obj.uniquekeyFlag);
		$("#updateFlag").val(obj.updateFlag);
		$("#fieldFormula").val(obj.fieldFormula);
		$("#updateBtn").removeAttr("disabled");
		$("#deleteBtn").removeAttr("disabled");
	}
	
</script>

<div id="id_showMsg" style="display: none"><br />
<br />
<div id="id_result"><t:messagePanel messagesAttributeName="errmsg"
	messagesType="error" /> <t:messagePanel
	messagesAttributeName="infomsg" messagesType="info" /> <t:messagePanel
	messagesAttributeName="successmsg" messagesType="success" /> <spring:hasBindErrors
	name="impFieldCfgForm">
	<form:form commandName="impFieldCfgForm">
		<div class="alert alert-error"><form:errors path="*"
			cssStyle="color:red"></form:errors></div>
	</form:form>
</spring:hasBindErrors></div>
<br />
</div>


<form id="form" method="post">

<div class="page_title"><spring:message code="ifd_title.impFieldCfg"/></div>

<div class="row" style="margin-bottom: 2px;">
<div class="tbl_page_head">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<thead>
		<tr>
			<th class="tbl_page_th" width="80px"><spring:message code="ifd.label.FIELDID"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="ifd.label.FILEID"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="ifd.label.FIELDNAME"/></th>
			<th class="tbl_page_th" width="60px"><spring:message code="ifd.label.DATATYPE"/></th>
			<th class="tbl_page_th" width="100px"><spring:message code="ifd.label.UPDATEWAY"/></th>
			<th class="tbl_page_th" width="100px"><spring:message code="ifd.label.UNIQUEKEYFLAG"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="ifd.label.UPDATEFLAG"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="ifd.label.FIELDFORMULA"/></th>
			<th class="tbl_page_th" width="60px"><spring:message code="index.label.sm.Operation"/></th>
		</tr>
	</thead>
</table>
</div>
<div class="tbl_page_body" style="min-height: 100px; height: 155px;" >
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<tbody>
			<c:forEach var="dto" items="${page}" varStatus="i">
				<tr>
					<td class="tbl_page_td_left vtip" width="80px">${dto.fieldId}</td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.fileId}</td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.fieldName}</td>
					<td class="tbl_page_td_left vtip" width="60px"><t:codeValue items="${IFDC_0001}" key="${dto.dataType}" type="label" /></td>
					<td class="tbl_page_td_left vtip" width="100px"><t:codeValue items="${IFDC_0002}" key="${dto.updateWay}" type="label" /></td>
					<td class="tbl_page_td_left vtip" width="100px"><t:codeValue items="${IFDC_0003}" key="${dto.uniquekeyFlag}" type="label" /></td>
					<td class="tbl_page_td_left vtip" width="80px"><t:codeValue items="${IFDC_0004}" key="${dto.updateFlag}" type="label" /></td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.fieldFormula}</td>
					<td class="tbl_page_td_left" width="60px">
						<div align="center" style="height: 25px">
							<input type="button" class="btn btn-small" onclick="setImpFieldInfo('${dto.fieldId}')" value="<spring:message code="button.label.Choose"/>">
						</div>
					</td>
				</tr>
			</c:forEach>
	</tbody>
</table>
</div>
</div>

<div  style="text-align:center;margin-bottom: 2px;" >
    <input type="button" class="btn btn-primary" value="<spring:message code="button.label.Add"/>" id="insertBtn" onclick="insert()" />
	<input type="button" class="btn btn-primary" value="<spring:message code="button.label.Update"/>" id="updateBtn" onclick="update()" disabled="disabled"/>
    <input type="button" class="btn btn-primary" value="<spring:message code="button.label.Delete"/>" id="deleteBtn" onclick="del()"  disabled="disabled"/>
</div>


<% 
if(request.getParameter("updel")!= null)
if(request.getParameter("updel").toString().equals("1")){%>
<script type="text/javascript">
$("#updateBtn").removeAttr("disabled");
$("#deleteBtn").removeAttr("disabled");
</script>
<%}%>

<div class="page_title"><spring:message code="ifd_title.impFieldCfgMx"/></div>
<!-- body -->
<div class="row form-horizontal" style="margin-bottom: 40px;">

		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.FIELDID"/></td>
				<td>
					<form:input id="fieldId" path="impFieldCfg.fieldId" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.FILEID"/></td>
				<td>
					<form:input id="fileId" path="impFieldCfg.fileId" type="text" class=".input-small" readonly="true"/>
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.FIELDNAME"/></td>
				<td>
					<form:input id="fieldName" path="impFieldCfg.fieldName" type="text" class=".input-small" readonly="true"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.DATATYPE"/></td>
				<td>
					<form:select id="dataType" path="impFieldCfg.dataType" class=".input-small" readonly="true">
						<form:options items="${IFDC_0001}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.UPDATEWAY"/></td>
				<td>
					<form:select id="updateWay" path="impFieldCfg.updateWay" class=".input-small" readonly="true">
						<form:options items="${IFDC_0002}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.UNIQUEKEYFLAG"/></td>
				<td>
					<form:select id="uniquekeyFlag" path="impFieldCfg.uniquekeyFlag" class=".input-small" readonly="true">
						<form:options items="${IFDC_0003}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.UPDATEFLAG"/></td>
				<td>
					<form:select id="updateFlag" path="impFieldCfg.updateFlag" class=".input-small" readonly="true">
						<form:options items="${IFDC_0004}" />
					</form:select>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ifd.label.FIELDFORMULA"/></td>
				<td>
					<form:input id="fieldFormula" path="impFieldCfg.fieldFormula" type="text" class=".input-small" readonly="true"/>
					<input type="button" class="btn btn-primary" value="<spring:message code="button.lable.Choose"/>"  onclick="openChoose();" />
				</td>
			</tr>
	   	</table>
</div>
</form>

<div class="row" >
  <div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
    <input type="button" class="btn btn-primary" value="<spring:message code="button.label.Operation"/>" id="operationBtn" onclick="operation()" disabled="disabled"/>
    <input type="button" class="btn btn-primary" value="<spring:message code="button.label.Cancel"/>" id="cancelBtn" onclick="cancel()"  disabled="disabled"/>
	<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
  </div>
</div>

