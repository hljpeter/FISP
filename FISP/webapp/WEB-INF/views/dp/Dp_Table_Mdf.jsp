<script type="text/javascript">
window.name="curWindow";
$(function() {
	// init page
	var msg = "${successmsg}";
	if (msg && msg != "") {
		$("input:not(:button,:hidden)").prop("readonly", true);
		$("input[type=button]:not('#close')").attr("disabled", true);
		$("select").prop("disabled", true);
		$("#dbTableName").find("option").each(function(i, obj) {
			if ($(obj).text() == $("#tableName").val()) {
				$(obj).attr("selected", true);
			}
		});
	} else {
		$("input:not(:button,:hidden)").prop("readonly", false);
		$("#cols").attr("disabled", false);
		$("#tableName").prop("readonly", true);
		$("#tableComments").prop("readonly", true);
		$("#dbTableName").attr("disabled", true);
	}
	var flag = "${dpTableMdfForm.flag}";
	var errmsg = "${errmsg}";
	if (errmsg && errmsg != "") { 
		$("input:not(:button,:hidden)").prop("readonly", true);
		$("input[type=button]:not('#close')").attr("disabled", true);
		$("#cols").attr("readonly", true);
		$("#dbTableName").attr("disabled", true);
	}
	if (flag == "false") {
		$("input:not(:button,:hidden,'#tableDesc','#comments')").attr("readonly", true);
		$("input[type=button]:not('#close', '#submit')").attr("disabled", true);
		$("#cols").attr("readonly", true);
		$("#dbTableName").attr("disabled", true);
	}

	$("#submit").click(function() {
		var msg=$("#confirmMsg1").val()+$("#submit").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			$("#dpTableMdfForm").submit();
		} else {
			return false;
		}
	});
	
	$.ajax({ 
		type: "GET", 
		url: "${pageContext.request.contextPath}/DP_Table_Add/Add/searchsync", 
		data: "tableName=" + $("#dbTableName").find("option:selected").text(),
		success: function(data) {
			//alert("data" + data);
			var obj = eval("(" + data + ")");
			
			for (var key in obj) {
				//alert(obj[key].colName);
				jQuery("<option></option>").val('{"colName":"' + obj[key].colName 
					+ '","colType":"' + obj[key].colType + '","colLen":"' + obj[key].colLen 
					+ '","colComments":"' + obj[key].colComments.replace(/[ ]/g, '&nbsp;') + '"}').text(obj[key].colName
							+ "【" + obj[key].colType + ", " + obj[key].colLen + "】").appendTo("#cols");
			}
		}
	});
});

var moveToRight = function() {
	var html = "";
	var vcount = -1;
	$("#cols").find("option:selected").each(function(i, obj) {
		var objJSON = eval("(" + $(obj).val() + ")");
		vcount = $("#tt tr").size();
		var j = (vcount + i + 1);
		html = html + '<tr id=' + $(obj).val() + '>' 
			+ '<td class="tbl_page_td_left vtip" width="15">' + j + '<input type="hidden" name="dpTableList[' + (vcount + i) + '].colSeqNo" value="' + j + '"/></td>'
			+ '<td class="tbl_page_td_left vtip" width="80">' + objJSON.colName + '<input type="hidden" name="dpTableList[' + (vcount + i) + '].colName" value="' + objJSON.colName + '"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="80"><input name="dpTableList[' + (vcount + i) + '].colDesc" value="' + objJSON.colComments + '" maxLength="128" class=".input-small"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="80"><input name="dpTableList[' + (vcount + i) + '].comments" maxLength="1024" class=".input-small"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="60">' + objJSON.colType + '<input type="hidden" name="dpTableList[' + (vcount + i) + '].colType" value="' + objJSON.colType + '"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="20">' + objJSON.colLen + '<input type="hidden" name="dpTableList[' + (vcount + i) + '].colLen" value="' + objJSON.colLen + '"/></td>'
          	+ '</tr>';
		
        $(obj).remove();
	});
	if (vcount == 0) {
		$("#tt").append(html);
	} else {
		$("#tt tr:last").after(html);
	}
	if ($("#tt tr").size() != 0) {
		$("#add").attr("disabled", false);
	}
};
var moveToLeft = function() {
	if (typeof $("#tt tr:last").attr("id") != 'undefined') {
		var obj = eval("(" + $("#tt tr:last").attr("id") + ")");
		jQuery("<option></option>").val('{"colName":"' + obj.colName 
				+ '","colType":"' + obj.colType + '","colLen":"' + obj.colLen 
				+ '","colComments":"' + obj.colComments + '"}').text(obj.colName 
						+ "【" + obj.colType + ", " + obj.colLen + "】").appendTo("#cols");
		$("#tt tr:last").remove();
	}
};
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg" style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="dpTableMdfForm">
			<form:form commandName="dpTableMdfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
				
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.table.dpTableMdf"/></div>
<!-- body -->
<form:form id="dpTableMdfForm" action="${pageContext.request.contextPath}/DP_Table_Mdf/Mdf" modelAttribute="dpTableMdfForm">
<div class="row" style="left: 10px;margin-bottom: 40px;width: 1200px; position:absolute;">

	<form:hidden path="dpTable.tableId" value="${dpTableMdfForm.dpTable.tableId }"/>
	<form:hidden path="dpTable.updateTime" value="${dpTableMdfForm.dpTable.updateTime }"/>
	<form:hidden path="dpTable.updateUser" value="${dpTableMdfForm.dpTable.updateUser }"/>
	<form:hidden path="dbTableInfo.tableName" value="${dpTableMdfForm.dbTableInfo.tableName }"/>
	<form:hidden path="dbTableInfo.tableComments" value="${dpTableMdfForm.dbTableInfo.tableComments }"/>
	<table class="tbl_search" height="100%">
		<tr>
			<td width="340" height="100%">
				<table class="tbl_search" height="100%">
			    	<tr><td colspan="2">
			    		<p class="text-info"><spring:message code="fisp.titls.dp.table.dpTableDtl.table"/></p>
			    	</td></tr>
			    	<tr>
						<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
						<td width="50%">
							<select id="dbTableName" disabled="disabled">
								<option value='{"name":"${dpTableMdfForm.dbTableInfo.tableName }","desc":"${dpTableMdfForm.dbTableInfo.tableComments }"}'>${dpTableMdfForm.dbTableInfo.tableName }</option>
							</select>
						</td>
					</tr>
			    	<tr>
						<td class="label_td" nowrap><spring:message code="fisp.label.common.tableDesc"/></td>
						<td><input id="tableComments" type="text" class=".input-small" value="${dpTableMdfForm.dbTableInfo.tableComments }" readonly="readonly"/></td>
					</tr>
					<tr>
						<td class="label_td" colspan="2">
							<select id="cols" multiple="multiple" size="26" style="width: 280px;" ondblclick="moveToRight();">
								
							</select>
						</td>
					</tr>
			    </table>
			</td>
			<td align="center">
				<input type="button" class="btn btn-small" onclick="moveToRight()" value="<spring:message code="fisp.button.label.right"/>"/><br/><br/>
				<input type="button" class="btn btn-small" onclick="moveToLeft('fileUnSelected', 'fileSelected')" value="<spring:message code="fisp.button.label.left"/>"/>
			</td>
			<td width="800" height="100%">
				<table class="tbl_search" height="100%">
			    	<tr><td colspan="2">
			    		<p class="text-info"><spring:message code="fisp.titls.dp.table.dpTableDtl.table"/></p>
			    	</td></tr>
			    	<tr>
						<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
						<td><form:input id="tableName" path="dpTable.tableName" type="text" class=".input-small" maxLength="32" readonly="true"/></td>
					</tr>
			    	<tr>
						<td class="label_td"><spring:message code="fisp.label.common.tableDesc"/></td>
						<td><form:input id="tableDesc" path="dpTable.tableDesc" type="text" maxLength="128" class=".input-small" style="width: 400px"/></td>
					</tr>
			    	<tr>
						<td class="label_td"><spring:message code="fisp.label.common.comments"/></td>
						<td><form:input id="comments" path="dpTable.comments" type="text" maxLength="1024" class=".input-small" style="width: 400px"/></td>
					</tr>
					<tr>
						<td class="label_td" colspan="2">
							<div class="tbl_page_head">
								<table class="table table-striped table-bordered table-condensed tbl_page">
							      <thead>
							        <tr>
							        	<th class="tbl_page_th" width="20px"><spring:message code="fisp.label.common.no"/></th>
							          	<th class="tbl_page_th" width="170px"><spring:message code="fisp.label.common.colName"/></th>
							          	<th class="tbl_page_th" width="220px"><spring:message code="fisp.label.common.colDesc"/></th>
							          	<th class="tbl_page_th" width="210px"><spring:message code="fisp.label.common.comments"/></th>
							          	<th class="tbl_page_th" width="100px"><spring:message code="fisp.label.common.colType"/></th>
							          	<th class="tbl_page_th" width="30px"><spring:message code="fisp.label.common.colLen"/></th>
							          	<th class="tbl_page_th" width="3px"></th>
							        </tr>
							      </thead>
								</table>
							</div>
							<div class="tbl_page_body" style="overflow-y:scroll">
								<table id="tt" class="table table-striped table-bordered table-condensed tbl_page">
									<c:forEach var="dto" items="${dpTableMdfForm.dpTableList }" varStatus="i">
										<tr id='{"colName":"${dto.colName }","colType":"${dto.colType }","colLen":"${dto.colLen }","colComments":"${dto.comments }"}'>
											<td class="tbl_page_td_left vtip" width="20px">
												${dto.colSeqNo }
												<input type="hidden" name="dpTableList[${i.index }].colSeqNo" value="${dto.colSeqNo }"/>
											</td>
											<td class="tbl_page_td_left vtip" width="170px">
												${dto.colName }
												<input type="hidden" name="dpTableList[${i.index }].colName" value="${dto.colName }"/>
											</td>
          									<td class="tbl_page_td_left" width="220px">
          										<input name="dpTableList[${i.index }].colDesc" class=".input-small" value="${dto.colDesc }"/>
          									</td>
          									<td class="tbl_page_td_left" width="210px">
          										<input name="dpTableList[${i.index }].comments" class=".input-small" value="${dto.comments }"/>
          									</td>
          									<td class="tbl_page_td_left vtip" width="100px">
          										${dto.colType }
          										<input type="hidden" name="dpTableList[${i.index }].colType" value="${dto.colType }"/>
          									</td>
          									<td class="tbl_page_td_left vtip" width="30px">
          										${dto.colLen }
          										<input type="hidden" name="dpTableList[${i.index }].colLen" value="${dto.colLen }"/>
          									</td>
          								</tr>
									</c:forEach>
								</table>
							</div>
						</td>
					</tr>
			    </table>
			</td>
		</tr>
	</table>	
</div>
</form:form>	
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="submit" class="btn btn-primary" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" id="close" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>