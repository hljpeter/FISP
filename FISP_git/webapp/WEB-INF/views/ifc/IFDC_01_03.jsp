<script type="text/javascript">

$(function() {
	
	 var colName=$("#tableFieldListID").find("td:eq(1)").text();
	 if(colName!="")
		 {
	     $.ajax({
				url: "${pageContext.request.contextPath}/ifdc02/02/detailSearch?impFileIdValue="+ impFileIdValue+"&columName="+colName,
				type:"post",
				dataType:"json",
				async:true,
				data : { 
					reckfileIdValue:impFileIdValue
			 	},
			 	success : function(rs) {
			 		setTableFieldInfo(rs);
				}
			});
		 }
	
	   $("#tableFieldListID").find("tr").bind('click', function(){ 
		  
           var colName=$(this).find("td:eq(1)").text();
           $.ajax({
   			url: "${pageContext.request.contextPath}/ifdc02/02/detailSearch?impFileIdValue="+ impFileIdValue+"&columName="+colName,
   			type:"post",
   			dataType:"json",
   			async:true,
   			data : { 
   				reckfileIdValue:impFileIdValue
   		 	},
   		 	success : function(rs) {
   		 		setTableFieldInfo(rs);
   			}
   		});
           
        }); 
	   
	   
	   
	   
	   $("#fileFieldListID").find("tr").bind('click', function(){ 
		  
           var fieldN=$(this).find("td:eq(0)").text();
           $.ajax({
   			url: "${pageContext.request.contextPath}/ifdc02/02/detailSearch1?impFileIdValue="+ impFileIdValue,
   			type:"post",
   			dataType:"json",
   			contentType: "application/x-www-form-urlencoded; charset=utf-8",
   			async:true,
   			data : { 
   				fieldName:fieldN
   		 	},
   		 	success : function(rs) {
   		 		setfileFieldInfo(rs);
   			}
   		});
           
        }); 
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
		var rtnStr=window.showModalDialog('${pageContext.request.contextPath}/iem01/01/init?impfileIdValue='+impFileIdValue, window, 'dialogHeight:500px; dialogWidth: 1024px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
		if(rtnStr){
			$("#fieldbiaodashi").val(rtnStr);
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
	
	function setTableFieldInfo(obj){
		$("#fieldtable").val(obj.table);
		$("#fieldkey").val(obj.fieldkey);
		$("#fielddieldname").val(obj.fieldname);
		$("#fieldtype").val(obj.type);
		$("#fieldcomments").val(obj.comments);
		$("#fieldlength").val(obj.length);
		$("#fieldbiaodashi").val(obj.biaodashi);
	}
	
	
	function setfileFieldInfo(obj)
	{
		$("#filefilename").val(obj.filename);
		$("#filefield").val(obj.field);
		$("#filelenght").val(obj.lenght);
		$("#filetype").val(obj.type);
	}
	
	
	
	
</script>




<!-- title -->
<div class="page_title">任务管理  / 映射配置  / 导入映射配置 / 修改字段</div>




<!-- body -->


<div class="tbl_page_head"><spring:message code="tablefield.title"/>
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<thead>
		<tr>
			<th class="tbl_page_th" width="50px"><spring:message code="xu.hao"/></th>
				<th class="tbl_page_th" width="130px"><spring:message code="field.name"/></th>
				<th class="tbl_page_th" width="80px"><spring:message code="field.comment"/></th>
				<th class="tbl_page_th" width="80px"><spring:message code="field.null"/></th>
				<th class="tbl_page_th" width="200px"><spring:message code="field.biaodashi"/></th>
		</tr>
	</thead>
</table>
</div>
<div class="tbl_page_body" style="min-height: 341px; height: 341px;margin-bottom: 15px;" >
<table id="tableFieldListID"
	class="table table-striped table-bordered table-condensed tbl_page">
	<tbody>
			<c:forEach var="dto" items="${tableFieldList}" varStatus="i">
				<tr>
					<td class="tbl_page_td_left vtip" width="50px">${dto.ROWNUM}</td>
					<td class="tbl_page_td_left vtip" width="130px">${dto.COLUMN_NAME}</td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.COMMENTS}</td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.EMPTYNULL}</td>
					<td class="tbl_page_td_left vtip" width="200px">${dto.FIELD_FORMULA}</td>
				</tr>
			</c:forEach>
	</tbody>
</table>
</div>
	


<div class="row form-horizontal" style="margin-bottom: 40px;"><spring:message code="tablefield.detail.title"/>
		<table class="tbl_search">
            <tr>
				    <td class="label_td"><spring:message code="field.detail.table"/></td>
					<td>
						<form:input id="fieldtable" path="tableField.table" type="text" class=".input_large"/>
					</td>
					<td class="label_td"><spring:message code="field.detail.key"/></td>
					<td>
						<form:input id="fieldkey" path="tableField.fieldkey" type="text" class=".input_large" />
					</td>
				</tr>
		    	<tr>
					<td class="label_td"><spring:message code="field.detail.fieldname"/></td>
					<td>
						<form:input id="fielddieldname" path="tableField.fieldName" type="text" class=".input_large" />
					</td>
					<td class="label_td"><spring:message code="field.detail.type"/></td>
					<td>
					   <form:input id="fieldtype" path="tableField.fieldType" type="text" class=".input_large" />
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="field.detail.comments"/></td>
					<td>
						 <form:input id="fieldcomments" path="tableField.fieldComment" type="text" class=".input_large"/>
					</td>
					<td class="label_td"><spring:message code="field.detail.length"/></td>
					<td>
						 <form:input id="fieldlength" path="tableField.fieldLength" type="text" class=".input_large" />
					</td>
				</tr>
				<tr>
					<td class="label_td"><spring:message code="field.detail.biaodashi"/></td>
					<td>
					  <form:input id="fieldbiaodashi" path="tableField.biaodashi" type="text" class=".input_large" />
					</td>
					<td class="label_td" colspan="2">
					<input type="button" class="btn btn-primary" onclick="openChoose();" value="<spring:message code="field.detail.bianji"/>">
					</td>
					
				</tr>
	   	</table>   	
</div>

<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary"  value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>