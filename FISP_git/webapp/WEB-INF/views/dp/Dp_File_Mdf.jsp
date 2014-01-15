<script type="text/javascript">
$(function() {
	// init page
	var msg = "${successmsg}";
	if (msg && msg != "") {
		$("input").prop("readonly", true);
		$("select").prop("disabled", true);
		$("input[type=button]").attr("disabled", true);
	} else {
		//$("input:not(:button,:hidden, '#fileName')").prop("readonly", false);
		//$("select").attr("disabled", false);
		//unlockTable();
	}
	changeField();
	var infomsg = "${infomsg}";
	if (infomsg && infomsg != "") {
		$("#lock").prop("disabled", true);
	}
	var errmsg = "${errmsg}";
	if (errmsg && errmsg != "") {
		$("#fieldtbl input").prop("readonly", false);
		$("#fieldtbl select").prop("disabled", false);
		$("#fieldtbl button").prop("disabled", false);
		$("#lock").val('<spring:message code="fisp.button.label.file.lock"/>');
		$("#lock").attr("name", "lock");
	}
	
	$("#submit").click(function() {
		var flag = true;
		$("#fieldtbl tr").each(function(i, obj) {
			var tmp = $(obj).find("input:first").val();
			if (tmp && $.trim(tmp) != "") {
				$("#fieldtbl tr:eq(" + i + ")").nextAll().each(function(j, obj2) {
					var tmp2 = $(obj2).find("input:first").val();
					if (tmp2 && $.trim(tmp2) != "") {
						if ($.trim(tmp) == $.trim(tmp2)) {
							document.getElementById("errorMsg").style.display = "block";
							document.getElementById("errorMsg").innerHTML = '<spring:message code="e.dp.file.0092"/>'
							showMsg("id_showMsg");
							flag = false;
							return;
						}
					}
				});
			}
		});
		if (flag) {
			var msg=$("#confirmMsg1").val()+$("#submit").val()+$("#confirmMsg2").val();
			if (confirm(msg)){
				lockTable();
				$("#form").submit();
			}else{
				return false;
			}
		}
	});
	
	$("#fileType").change(function() {
		changeField();
	});

	$("#lock").click(function() {
		//$("#fieldtbl tr:last").clone().append("#fieldtbl");
		var name = $("#lock").attr("name");
		// unlock table 
		if (name == "unlock") {
			unlockTable();
		} else {
			lockTable();
		}
	});
	$("#fieldtbl input").live('keyup', function() {
		var id = $(this).closest("tr").attr("id").substring(3);
		var count = $("#fieldtbl tr").size();
		if (id == (count - 1)) {
			addNewLine();
		}
	});
	$("#fieldtbl select").live('change', function() {
		var id = $(this).closest("tr").attr("id").substring(3);
		var count = $("#fieldtbl tr").size();
		if (id == (count - 1)) {
			addNewLine();
		}
		$(this).next("input[type=hidden]").val($(this).val());
	});
	$("#fieldtbl button").live('click', function() {
		$(this).closest("tr").remove();
		$("#fieldtbl").find("tr").each(function(i, obj) {
			var j = i++;
			$(obj).find("#seq").html(j + 1);
		});
	});
	var addNewLine = function() {
		var count = $("#fieldtbl tr").size();
		var html = '<tr id="tr_' + count + '">'
	  		+ '<td class="tbl_page_td_left vtip" width="40px" id="seq">' + (count + 1) + '</td>'
        	+ '<td class="tbl_page_td_left" width="180px"><input name="dpFileDtlList[' + count + '].fieldName" class="span3" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);"/></td>'
        	+ '<td class="tbl_page_td_left" width="250px"><input name="dpFileDtlList[' + count + '].fieldDesc" class="span4"/></td>'
        	+ '<td class="tbl_page_td_left" width="60px"><input name="dpFileDtlList[' + count + '].fieldLen" class="span1" style="width: 60px;" onkeyup="numberFormat(this);" onbeforepaste="numberFormatCopy(this);"/></td>'
        	+ '<td class="tbl_page_td_left" width="100px"><form:select path="dpFileMdfForm.cutFlag" style="width: 100px"><option value=""></option><form:options  items="${DP_FILE_CUTFLAG}"/></form:select>'
        	+ '<input type="hidden" name="dpFileDtlList[' + count + '].cutFlag"></td>'
        	+ '<td class="tbl_page_td_left" width="40px"><div align="center" style="height: 25px"><button class="btn btn-small"><spring:message code='button.lable.Del'/></button></div></td>'
			+ '</tr>';
		if (count == 0) {
			$("#fieldtbl").append(html);
			$("#fieldtbl button").prop("disabled", true);
		} else {
			$("#fieldtbl tr:last").after(html);
			$("#fieldtbl button").prop("disabled", false);
		}
		// $("#fieldtbl input").attr("class", "input-medium");
	};
	var lockTable = function() {
		var inputflag = false;
		$("#fieldtbl tr:last").find("input").each(function(i, obj) {
			if ($(obj).val() && $(obj).val() != "") {
				inputflag = true;
			} 
		});
		$("#fieldtbl tr:last").find("select").each(function(i, obj) {
			if ($(obj).val() && $(obj).val() != "") {
				inputflag = true;
			} 
		});
		if (!inputflag) {
			$("#fieldtbl tr:last").remove();
		}
		$("#fieldtbl input").prop("readonly", true);
		$("#fieldtbl select").prop("disabled", true);
		$("#fieldtbl button").prop("disabled", true);
		$("#lock").val('<spring:message code="fisp.button.label.file.unlock"/>');
		$("#lock").attr("name", "unlock");
	};
	var unlockTable = function() {
		$("#fieldtbl input").prop("readonly", false);
		$("#fieldtbl select").prop("disabled", false);
		$("#fieldtbl button").prop("disabled", false);
		$("#lock").val('<spring:message code="fisp.button.label.file.lock"/>');
		$("#lock").attr("name", "lock");
		addNewLine();
	};
	function changeField() {
		var selValue = $("#fileType").find("option:selected").val();
		switch (selValue) {
		case '1':
			$("#delimiter").prop("readonly", true);
			$("#fixedLenCfg").prop("readonly", false);
			$("#excel").css("display", "none");
			break;
		case '2':
			$("#delimiter").prop("readonly", false);
			$("#fixedLenCfg").prop("readonly", true);
			$("#excel").css("display", "none");
			break;
		case '3':
			$("#delimiter").prop("readonly", true);
			$("#fixedLenCfg").prop("readonly", true);
			$("#excel").css("display", "none");
			break;
		case '4':
			$("#excel").css("display", "block");
			$("#delimiter").prop("readonly", true);
			break;
		}
	};
});
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dpFileMdfForm">
			<form:form commandName="dpFileMdfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
				
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.file.dpFielMdf"/></div>
<!-- body -->
<form:form id="form" action="${pageContext.request.contextPath}/DP_File_Mdf/Mdf" modelAttribute="dpFileMdfForm">
<div class="row" style="margin-bottom: 40px;">
	<form:hidden id="fileId" path="dpFile.fileId"/>
	<form:hidden id="updateTime" path="dpFile.updateTime"/>
	<form:hidden id="updateUser" path="dpFile.updateUser"/>
	<table class="tbl_search">
    	<tr><td colspan="4">
    		<p class="text-info"><spring:message code="fisp.titls.dp.file.dpFielDtl.file"/></p>
    	</td></tr>
    	<tr>
			<td class="label_td" width="20%"><font color="red">*</font><spring:message code="fisp.label.common.fileName"/></td>
			<td width="30%">
				<form:input id="fileName" path="dpFile.fileName" type="text" class=".input-medium" maxLength="128" readonly="true"/>
			</td>
			
			<td class="label_td" width="20%"><font color="red">*</font><spring:message code="fisp.label.common.fileType"/></td>
			<td width="30%">
				<form:select id="fileType" path="dpFile.fileType">
					<form:options items="${DP_FILE_FILETYPE}" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.fileDesc"/></td>
			<td colspan="3" width="80%"><form:input path="dpFile.comments" type="text" class=".input-xlarge" style="width: 600px" maxLength="255"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.delimiter"/></td>
			<td><form:input id="delimiter" path="dpFile.delimiter" type="text" class=".input-large" maxLength="20"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.fixLenCfg"/></td>
			<td><form:input id="fixedLenCfg" path="dpFile.fixedLenCfg" type="text" class=".input-large"/></td>
		</tr>
		<tr>
			<td class="label_td"><font color="red">*</font><spring:message code="fisp.label.common.fileEncoding"/></td>
			<td><form:input path="dpFile.fileEncoding" type="text" class=".input-small"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.ioFlag"/></td>
			<td>
				<form:select id="ioFlag" path="dpFile.ioFlag">
					<option value=""></option>
					<form:options items="${DP_FILE_IOFLAG}" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.srowIgnrNum"/></td>
			<td><form:input path="dpFile.srowIgnrNum" type="text" class=".input-small" onkeyup="numberFormat(this);"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.erowIgnrNum"/></td>
			<td><form:input path="dpFile.erowIgnrNum" type="text" class=".input-small" onkeyup="numberFormat(this);"/></td>
		</tr>
		<tr>
			<td class="label_td"><spring:message code="fisp.label.common.commitCount"/></td>
			<td><form:input path="dpFile.commitCount" type="text" class=".input-small" onkeyup="numberFormat(this);"/></td>
			
			<td class="label_td"><spring:message code="fisp.label.common.cutFlag"/></td>
			<td>
				<form:select id="ioFlag" path="dpFile.cutFlag">
					<option value=""></option>
					<form:options items="${DP_FILE_CUTFLAG}" />
				</form:select>
			</td>
		</tr>
		<tr id="excel" style="display: none">
			<td colspan="4">
				<div class="row" style="width:100%">
					<table class="tbl_search">
				    	<tr><td colspan="4">
				    		<p class="text-info"><spring:message code="fisp.titls.dp.file.dpFielDtl.file.excel"/></p>
				    	</td></tr>
				    	<tr>
							<td class="label_td"><spring:message code="fisp.label.common.sheetno"/></td>
							<td colspan="3"><form:input path="dpFile.sheetNo" type="text" class=".input-small"/></td>
						</tr>
				    	<tr>
							<td class="label_td"><spring:message code="fisp.label.common.startColumn"/></td>
							<td><form:input path="dpFile.startColumn" type="text" class=".input-small" onkeyup="numberFormat(this);"/></td>
							
							<td class="label_td"><spring:message code="fisp.label.common.endColumn"/></td>
							<td><form:input path="dpFile.endColumn" type="text" class=".input-small" onkeyup="numberFormat(this);"/></td>
						</tr>
				    </table>
				</div>
			</td>
		</tr>
    </table>		
	<span style="font-size: 12px;"><spring:message code="fisp.notice.common.required"/></span>									
</div>
<div class="row" style="margin-bottom: 40px;">
	<input type="button" id="lock" name="unlock" class="btn btn-primary" value="<spring:message code="fisp.button.label.file.unlock"/>">
	<div class="tbl_page_head">
	<table class="table table-striped table-bordered table-condensed tbl_page">
      <thead>
        <tr>
          	<th class="tbl_page_th" width="40px"><spring:message code="fisp.label.common.fieldSeqNo"/></th>
          	<th class="tbl_page_th" width="180px"><spring:message code="fisp.label.common.fieldName"/></th>
          	<th class="tbl_page_th" width="250px"><spring:message code="fisp.label.common.fieldDesc"/></th>
          	<th class="tbl_page_th" width="60px"><spring:message code="fisp.label.common.fieldLen"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.cutFlag"/></th>
          	<th class="tbl_page_th" width="40px"><spring:message code="index.label.sm.Operation"/></th>
          	<th class="tbl_page_th" width="3px"></th>
        </tr>
      </thead>
     </table>
    </div>
    <div id="fieldDiv" class="tbl_page_body" style="overflow-y:scroll">
		<table id="fieldtbl" class="table table-striped table-bordered table-condensed tbl_page" width="100%">
	        <c:forEach var="dto" items="${dpFileMdfForm.dpFileDtlList}" varStatus="i">
				<tr id="tr_${i.index }">
				  	<td class="tbl_page_td_left vtip" width="40px">${dto.fieldSeqNo}</td>
		            <td class="tbl_page_td_left" width="180px">
		            	<form:input path="dpFileDtlList[${i.index }].fieldName" value="${dto.fieldName}" class="span3" readonly="true"/>
		            </td>
		            <td class="tbl_page_td_left" width="250px">
		            	<form:input path="dpFileDtlList[${i.index }].fieldDesc" value="${dto.fieldDesc}" class="span4" readonly="true"/>
		            </td>
		            <td class="tbl_page_td_left" width="60px">
		            	<form:input path="dpFileDtlList[${i.index }].fieldLen" value=" ${dto.fieldLen}" class="span1" style="width: 60px;" readonly="true"/>
		           </td>
		            <td class="tbl_page_td_left" width="100px">
			            <form:select id="cutFlag" path="dpFileDtlList[${i.index }].cutFlag" style="width: 100px;" disabled="true">
							<form:options items="${DP_FILE_CUTFLAG}" />
						</form:select>
						<form:hidden path="dpFileDtlList[${i.index }].cutFlag" value="${dto.cutFlag }"/>
		            </td>
		            <td class="tbl_page_td_left" width="40px">
			           	<div align="center" style="height: 25px">
			           		<button id="delete" class="btn btn-small" disabled="disabled"><spring:message code="button.lable.Del"/></button>
		               	</div>
		            </td>
				</tr>
	        </c:forEach>
		</table>
	</div>
</div>
</form:form>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="submit" class="btn btn-primary" value="<spring:message code="button.lable.Submit"/>">
		<button id="close" class="btn btn-primary" onclick="javascript:window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>