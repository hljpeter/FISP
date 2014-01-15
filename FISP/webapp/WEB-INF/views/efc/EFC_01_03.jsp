<script type="text/javascript">
	
	window.name="curWindow";
	
	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("select").attr("disabled", "disabled");
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("input:button").attr("disabled", "disabled");
			$("#closeBtn").attr("disabled", false);
		
		}
	});
	
	//add button
	function add() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/efc01/03/insert";
		form.target="curWindow";
		var msg=$("#confirmMsg1").val()+$("#addBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
		form.submit();
		}
		else
		{
			return false;	
		}
		
	}
	
	//验证表单
	function validSearchForm(){
		var obj={};
		obj.valid=true;

		$(".alert-error").empty();
		obj.fileId=$("input[name='fileId']").val();
		obj.projId=$("input[name='projId']").val();
		obj.branchId=$("input[name='branchId']").val();
		obj.batchNo=$("input[name='batchNo']").val();
		obj.seqeNo=$("input[name='seqeNo']").val();
		obj.tableName=$("input[name='tableName']").val();
		obj.fileName=$("input[name='fileName']").val();
		obj.filePath=$("input[name='filePath']").val();
		obj.formatType=$("input[name='formatType']").val();
		obj.delimiter=$("input[name='delimiter']").val();
		obj.fixedLenCfg=$("input[name='fixedLenCfg']").val();
		obj.fileTitle=$("input[name='fileTitle']").val();
		obj.rowTitleFlag=$("input[name='rowTitleFlag']").val();
		obj.sheetNum=$("input[name='sheetNum']").val();
		obj.comments=$("input[name='comments']").val();
		obj.rsv1=$("input[name='rsv1']").val();
		obj.rsv2=$("input[name='rsv2']").val();
		obj.rsv3=$("input[name='rsv3']").val();
		obj.rsv4=$("input[name='rsv4']").val();
		obj.rsv5=$("input[name='rsv5']").val();
		
		if(!$.trim(obj.fileId)){
			$(".alert-error").append('<spring:message code="e.ta.1050"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.projId)){
			$(".alert-error").append('<spring:message code="e.ta.1040"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.branchId)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.batchNo)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.seqeNo)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.tableName)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.fileName)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.filePath)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.formatType)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.delimiter)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.fixedLenCfg)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.fileTitle)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.rowTitleFlag)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.sheetNum)){
			$(".alert-error").append('<spring:message code="e.ta.1020"/><br/>');
			obj.valid=false;
		}
		if(!$.trim(obj.comments)){
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
	
	
</script>



<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="expFileCfgForm">
			<form:form commandName="expFileCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>

<div class="page_title">任务管理  / 映射配置  / 报表映射配置 / 新增</div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
<form:form  id="form" action="${pageContext.request.contextPath}/efc01/03/init" method="post" modelAttribute="expFileCfgForm" class="form-horizontal">


		<table class="tbl_search">
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ef.label.FILEID"/></td>
				<td>
					<form:input path="fileId"  type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ef.label.PROJID"/></td>
				<td>
					<form:input path="projId"  type="text" class=".input-small" />
				</td>
			</tr>
	    	<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ef.label.BRANCHID"/></td>
				<td>
					<form:input path="branchId"  type="text" class=".input-small" />
				</td>
				<td class="label_td"><font color="red">*</font><spring:message code="ef.label.BATCHNO"/></td>
				<td>
					<form:input path="batchNo"  type="text" class=".input-small"  onkeyup="numberFormat(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="ef.label.SEQNO"/></td>
				<td>
					<form:input path="seqNo"  type="text" class=".input-small" onkeyup="numberFormat(this);" />
				</td>
				<td class="label_td"><spring:message code="ef.label.TABLENAME"/></td>
				<td>
					<form:input path="tableName" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.FILENAME"/></td>
				<td>
					<form:input path="fileName"  type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="ef.label.FILEPATH"/></td>
				<td>
					<form:input path="filePath" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="if.label.FORMATTYPE"/></td>
				<td>
					<form:select id="formatType" path="formatType" class=".input-small">
						<form:options items="${EFC_0001}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ef.label.DELIMITER"/></td>
				<td>
					<form:input path="delimiter" type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.FIXEDLEN_CFG"/></td>
				<td>
					<form:input path="fixedLenCfg"  type="text" class=".input-small" />
				</td>
				<td class="label_td"><spring:message code="ef.label.FILETITLE"/></td>
				<td>
					<form:input path="fileTitle"  type="text" class=".input-small" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.ROWTITLE_FLAG"/></td>
				<td>
					<form:select id="rowTitleFlag" path="rowTitleFlag" class=".input-small">
						<form:options items="${EFC_0002}" />
					</form:select>
				</td>
				<td class="label_td"><spring:message code="ef.label.SHEETNUM"/></td>
				<td>
					<form:input path="sheetNum"  type="text" class=".input-small" onkeyup="numberFormat(this);" />
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="ef.label.COMMENTS"/></td>
				<td>
					<form:input id="comments" path="comments" type="text" class=".input-small" />
				</td>
			</tr>
	   	</table>
	   	</form:form>
</div>


<div class="row" >
   <div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
	<input type="button" class="btn btn-primary" value="<spring:message code="button.lable.Add"/>" id="addBtn" onclick="add()"/>
	<input type="button" class="btn btn-primary" value="<spring:message code="button.lable.close"/>" id="closeBtn" onclick="javascript:window.close();"/>
   </div>
</div>
