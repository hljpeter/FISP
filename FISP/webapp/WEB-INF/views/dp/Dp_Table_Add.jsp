<script type="text/javascript">
$(function() {
	// init page
	var msg = "${successmsg}";
	if (msg && msg != "") {
		$("input:not(:button,:hidden)").prop("readonly", true);
		$("input[type=button]:not('#close')").attr("disabled", true);
		$("select").prop("disabled", true);
		$("#add").attr("disabled", true);
		$("#dbTableName").find("option").each(function(i, obj) {
			if ($("#tableName").val() == $(obj).text()) {
				$(obj).attr("selected", true);
			}
		});
		parseJSONAndDisplay($("#dbTableName").val(), [{"tableComments":"desc","tableName":"name"}]);
		getColsAjax();
	} else {
		$("input:not(:button,:hidden)").prop("readonly", false);
		$("select").attr("disabled", false);
		$("#tableName").prop("readonly", true);
		$("#tableComments").prop("readonly", true);
		//$("#submitcfg").attr("disabled", "true");
	}
	
	
	$("#dbTableName").change(function() {
		refresh();
		getColsAjax();
		
	});
	// table information display
	var refresh = function() {
		var tv = $("#dbTableName").val();
		var eljson = [{"tableComments":"desc","tableName":"name","tableDesc":"desc"}];
		if (tv && tv != "") {
			parseJSONAndDisplay(tv, eljson);
		} else {
			parseJSONAndDisplay('{"name":"","desc":""}', eljson);
		}
		if (!$("#tableComments").val() || $("#tableComments").val() == "") {
			$("#tableComments").val("N/A");
		} 
		if ($("#dbTableName").val() == "") {
			$("#tableComments").val("");
			$("#add").attr("disabled", true);
		} else {
			$("#add").attr("disabled", false);
		}
		$("#cols").find("option").remove();
		$("#tt").find("tr").remove();
	};
	function getColsAjax() {
		$.ajax({ 
			type: "GET", 
			url: "${pageContext.request.contextPath}/DP_Table_Add/Add/searchsync", 
			data: "tableName=" + $("#tableName").val(),//$("#dbTableName").find("option:selected").text(),
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
	};

	$("#add").click(function() {
		var name = $("#tableName").val();
		if (name && name != "") {
			var msg=$("#confirmMsg1").val()+$("#add").val()+$("#confirmMsg2").val();
			if (confirm(msg)){
				$("#dpTableAddForm").submit();
			} else {
				return false;
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
			+ '<td class="tbl_page_td_left vtip" width="20">' + j + '<input type="hidden" name="dpTableList[' + (j - 1) + '].colSeqNo" value="' + j + '"/></td>'
			+ '<td class="tbl_page_td_left vtip" width="170">' + objJSON.colName + '<input type="hidden" name="dpTableList[' + (j - 1) + '].colName" value="' + objJSON.colName + '"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="220"><input name="dpTableList[' + (j - 1) + '].colDesc" value="' + objJSON.colComments + '" maxLength="128" class=".input-small"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="210"><input name="dpTableList[' + (j - 1) + '].comments" maxLength="1024" class=".input-small"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="100">' + objJSON.colType + '<input type="hidden" name="dpTableList[' + (j - 1) + '].colType" value="' + objJSON.colType + '"/></td>'
          	+ '<td class="tbl_page_td_left vtip" width="30">' + objJSON.colLen + '<input type="hidden" name="dpTableList[' + (j - 1) + '].colLen" value="' + objJSON.colLen + '"/></td>'
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
		<spring:hasBindErrors name="dpImpCfgForm">
			<form:form commandName="dpImpCfgForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
				
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="fisp.titls.dp.table.dpTableAdd"/></div>
<!-- body -->
<div class="row" style="left: 10px;margin-bottom: 40px; position:absolute;">
<form:form id="dpTableAddForm" action="${pageContext.request.contextPath}/DP_Table_Add/Add" modelAttribute="dpTableAddForm">
	<table class="tbl_search" height="100%">
		<tr>
			<td width="34%" height="100%">
				<table class="tbl_search" height="100%">
			    	<tr><td colspan="2">
			    		<p class="text-info"><spring:message code="fisp.titls.dp.table.dpTableDtl.table"/></p>
			    	</td></tr>
			    	<tr>
						<td class="label_td"><spring:message code="fisp.label.common.tableName"/></td>
						<td width="50%">
							<select id="dbTableName">
								<option value=""></option>
								<c:forEach var="dto" items="${dpTableAddForm.dbTableList }" varStatus="i">
									<option value='{"name":"${dto.tableName }","desc":"${dto.tableComments }"}'>${dto.tableName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
			    	<tr>
						<td class="label_td" nowrap><spring:message code="fisp.label.common.tableDesc"/></td>
						<td><input id="tableComments" type="text" class=".input-small" readonly="readonly"/></td>
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
				<input type="button" class="btn btn-small" onclick="moveToLeft()" value="<spring:message code="fisp.button.label.left"/>"/>
			</td>
			<td width="60%" height="100%">
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
						<td><form:input id="tableDesc" path="dpTable.tableDesc" type="text" class=".input-larger" style="width: 400px" maxLength="128"/></td>
					</tr>
			    	<tr>
						<td class="label_td"><spring:message code="fisp.label.common.comments"/></td>
						<td><form:input path="dpTable.comments" type="text" maxLength="1024" class=".input-small" style="width: 400px"/></td>
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
								</table>
							</div>
						</td>
					</tr>
			    </table>
			</td>
		</tr>
	</table>	
</form:form>										
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="add" class="btn btn-primary" disabled value="<spring:message code="button.lable.Submit"/>">
		<input type="button" id="close" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>