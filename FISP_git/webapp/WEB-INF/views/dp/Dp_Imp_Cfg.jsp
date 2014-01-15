<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/jquery-ui-1.8.21/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/vendor/js/jquery.swimming.js"></script>
	
<script type="text/javascript">
$(window).load(function() {
	var trClickStyle = function(clicktr) { 
		var id = $(clicktr).closest("table").attr("id");
		var clzFlag = $(clicktr).hasClass("table-color-click");
		if (id == "fl" || id == "pl") {
			$("#fl tr").removeClass("table-color-click");
			$("#pl tr").removeClass("table-color-click");
		} else if (id == "cl") {
			$("#cl tr").removeClass("table-color-click");
		} else if (id == "tl") {
			$("#tl tr").removeClass("table-color-click");
			clzFlag = $(clicktr).hasClass("tb_notselected");
		}
		if (!clzFlag) {
			$(clicktr).addClass("table-color-click");
		}
	};
	$("table.table-striped tbody tr").unbind("click");
	$("table.table-striped tbody tr").live("click", function(event) {
		var e = event? event: window.event;
		var clicktr = $(e.srcElement).parent();
		trClickStyle(clicktr);
	});

	$.fn.extend({
		searchObj: function(idx) {
			var flag = false;
			var o;
			var j = -1;
			// search selected
			$(this).find("tr").each(function(i, obj) {
				if ($(obj).hasClass("table-color-click")) {
					j = i;
					flag = true;
					o = $(obj).find("td").eq(idx);
					return false;
				}
			}); 

			if (!flag) { 
				// fill in the first empty
				$(this).find("tr").each(function(i, obj) {
					var rs = parseHtml($(obj).find("td").eq(idx));
					if (rs == "") {
						j = i;
						flag = true;
						o = $(obj).find("td").eq(idx);
						return false;
					}
				});
			} 

			var top, left;
			if (typeof(o) != "undefined" && o.length > 0) {
				top = $(o).offset().top + $(o).height();
				left = $(o).offset().left;
			} else {
				flag = true;
				var tmp = $(this).find("tr:last");
				if (tmp.length == 0) {
					top = $(this).offset().top + $(this).height();
					left = $(this).offset().left;
				} else {
					top = $(tmp).offset().top + $(tmp).height();
					left = $(tmp).offset().left;
				}
			}
			
			return {
				obj: o,
				top: top,
				left: left,
				index: j,
				flag: flag
			};
		},
		setValue: function(params, idx, value) {
			var obj = params.obj;
			var j = params.index;
			if (typeof(obj) == 'undefined' || obj.length == 0) { 
				var count = $(this).find("tr").size();
				var html = '<tr>';
				if (idx == 0) {
					html = html + generateHtml(0, count, value)
						+ '<td class="tbl_page_td_left vtip" width="80px"></td>'
						+ '</tr>';
				} else {
					html = html + '<td class="tbl_page_td_left vtip" width="80px"></td>'
						//+ '<td class="tbl_page_td_left vtip" width="80px">' + value + '<input type="hidden" name="cList[' + count + '].colName" value="' + value + '"/></td>'
						+ generateHtml(1, count, value)
						+ '</tr>';
				}

				if (count == 0) {
					$(this).append(html);
				} else {
					$(this).find("tr:last").after(html);
				}

				$(this).find("tr:last").live("click", function(event) {
					var e = event? event: window.event;
					var clicktr = $(e.srcElement).parent();
					trClickStyle(clicktr);
				});
			} else { 
				var html = value;				
				if (idx == 0) {
					html = html + '<input type="hidden" name="cList[' + j + '].fieldName" value="' + value + '"/>';
				} else {
					html = html + '<input type="hidden" name="cList[' + j + '].colName" value="' + value + '"/>';
				}
				$(obj).html(html);
			}
		},
		disable: function() {
			$(this).addClass("tb_notselected");
			if ($(this).hasClass("table-color-click")) {
				$(this).removeClass("table-color-click");
			}
			$("#tl tr").removeClass("table-color-click");
			$(this).unbind();
		},
		enable: function(i) {
			$(this).removeClass("tb_notselected");
			if (!$(this).data("events") ) {
				$(this).bind({
					"dblclick": function(event) {
						dbEvent(event, 1);
					}
				});
			}
			//$(this).removeAttr("style");
			//$(this).closest("table").find("tr:odd").addClass("table-color-odd");
			///$(this).closest("table").find("tr:odd").addClass("table-color-odd");
		},
		scroll_y: function(i) {
			var h = $(this).height();
			if ((i) > h) {
				$(this).scrollTop(i - h);
			} else {
				$(this).scrollTop(0);
			}
		},
		search: function(value) {
			var result = "";
			$(this).find("tr").each(function(i, obj) {
				var tmp = $(obj).find("td:eq(1)");
				if (tmp && $.trim(tmp) != "") {
					if ($.trim(value) == $.trim(parseHtml(tmp))) {
						result = {
							obj: $(obj),
							top: $(obj).offset().top,
							left: $(obj).offset().left,
							index: i
						};
						return false;
					}
				}
			});
			return result;
		},
		addLine: function(html) {
			var count = $(this).find("tr").size();
			if (count == 0) {
				$(this).append(html);
			} else {
				$(this).find("tr:last").after(html);
			}
		},
		highlight: function(value) {
			//$(this).find("tr").css("background-color","");
			//$("table.table-striped tbody tr:odd").addClass("table-color-odd");
			var c = "";
			var idx = "";
			var re = "";
			$(this).find("tr").each(function(i, obj) {
				//$(obj).css("background-color","");
				var tmp = $(obj).find("td:eq(1)").html();
				if ((tmp && $.trim(tmp)) && $.trim(tmp)== $.trim(value)) {
					c = $(obj).css("background-color");
					idx = i;
					$(obj).css("background-color","#90EE90");
					$(this).closest("div").scroll_y($(obj).scrollTop());
					re = {
						color: c,
						index: idx
					};
					return false;
				}
			});
			return re;
		},
		
	});
	
	//init page
	var msg = "${successmsg}";
	if (msg && msg != "") {
		$("input:not(:hidden)").prop("readonly", true);
		$("input[type=button]").attr("disabled", true);
		$("#cl tr").disable();
		$("#fl tr").disable();
		$("#tl tr").disable();
		$("#pl tr").disable();
	} else {
		$("input:not(:hidden)").prop("readonly", false);
		$("select").attr("readonly", false);
	}
	
	$("#submitcfg").click(function() {
		var msg=$("#confirmMsg1").val()+$("#submitcfg").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			$("#form").submit();
		}else{
			return false;
		}
	});
	$("#auto").click(function() {
		$("#cl").empty();
		var idx = 0;
		$("#tl tr").each(function(i, obj) {
			$(obj).enable();
			var tn = $(obj).find("td:eq(1)").html();
			var tl = $(obj).find("td:eq(3)").html();
			//$(obj).prev().removeClass("table-color-click");
			//$(obj).addClass("table-color-click");
			$("#fl tr").each(function(j, obj2) {
				var fn = $(obj2).find("td:eq(1)").html();
				var fl = $(obj2).find("td:eq(3)").html();
				if (($.trim(tn) == $.trim(fn)) 
						&& (parseInt(tl) >= parseInt(fl))) {
					var html = '<tr>' 
						//+ '<td class="tbl_page_td_left vtip" width="160px"><input type="text" name="cList[' + idx + '].fieldName" value="' + fn + '" style="width: 80px;"/></td>'
						//+ '<td class="tbl_page_td_left vtip" width="160px">' + tn + '<input type="hidden" name="cList[' + idx + '].colName" value="' + tn + '"/></td>'
						+ generateHtml(0, idx, fn)
						+ generateHtml(1, idx, tn)
						+ '</tr>';
					$("#cl").addLine(html);
					idx++;
					$(obj).disable();
				}
			});
		});
		
	});
	$("#tl tr").bind("dblclick", function(event) {dbEvent(event, 1);});
	$("#fl tr").bind("dblclick", function(event) {dbEvent(event, 0);});
	$("#pl tr").bind("dblclick", function(event) {dbEvent(event, 0);});
	$("#cl tr").live({
		"dblclick": function(event) {
			var e = event? event: window.event;
			var obj = $(e.srcElement).parent();
			var field = parseHtml($(obj).find("td:eq(0)"));
			var column = parseHtml($(obj).find("td:eq(1)"));
			var fline = $("#fl").search(field);
			if (!fline || fline.obj.length == 0) {
				fline = $("#pl").search(field);
			} 
			var tline = $("#tl").search(column);

			$(obj).find("td:eq(1)").swimming(tline.obj, {
				time: 500, 
				display: column, 
				deleteSource: false,
				over: function() {
					$(tline.obj).enable();
				}
			});
			$(obj).find("td:eq(0)").swimming(fline.obj, {
				time: 500, 
				deleteSource: false,
				deleteParent: 'tr',
				display: field
			});
			$(obj).remove();
		}
	});
	
	// init table list
	$("#cl tr").each(function(i, obj) {
		var cval = parseHtml($(obj).find("td:last"));
		$("#tl tr").each(function(j, obj2) {
			var tval = $(obj2).find("td:eq(1)").html();
			if ($.trim(cval) == $.trim(tval)) {
				$(obj2).disable();
				$(obj2).unbind();
			}
		});
	});

	// manual update field in file
	$("#manual").click(function() {
		var flag = false;
		$("#cl tr").each(function(i, obj) {
			if ($(obj).hasClass("table-color-click")) {
				flag = true;
				return false;
			}
		}); 
		if (flag) {
			var title = '<spring:message code="fisp.label.dp.imp.manualFieldName"/>';
			tipsWindown(title, "id:manualUpdate", "430", "130", "true", "", "true", "id");
		}
		
	});
	
});
tipsWindown.close = function () {   
	$("#windownbg").remove();   
	$("#windown-box").fadeOut("slow", function () { $(this).remove(); }); 
}; 

var selectRecord = function(el, el2, idx) {
	var f = true;
	$("#" + el).find("tr").each(function(i, obj) {
		if ($(obj).hasClass("table-color-click")) {
			var result = $("#cl").searchObj(idx);
			if (result.flag) {
				scrollCL(result.top); 
				f = false;
				$(obj).swimming({
					top: result.top,
					left: result.left
				}, {
					time: 500, 
					over: function() {
						$("#cl").setValue(result, idx, $(obj).children().eq(1).html());
						if (idx == 1) {
							$(obj).disable();
						}
					}
				});
			}
			return false;
		}
	});
	if (el2 != "" && f) {
		$("#" + el2).find("tr").each(function(i, obj) {
			if ($(obj).hasClass("table-color-click")) {
				var result = $("#cl").searchObj(idx);
				if (result.flag) {
					scrollCL(result.top); 
					$(obj).swimming({
						top: result.top,
						left: result.left
					}, {
						time: 500, 
						over: function() {
							$("#cl").setValue(result, idx, $(obj).children().eq(1).html());
						}
					});
				}
				return false;
			}
		});
	}
};

var dbEvent = function(event, i) {
	var e = event? event: window.event;
	var obj = $(e.srcElement).parent();
	$(obj).addClass("table-color-click");
	var result = $("#cl").searchObj(i);
	if (result.flag) {
		scrollCL(result.top); 

		$(obj).swimming({
			top: result.top,
			left: result.left,
		}, {
			time: 500, 
			over: function() {
				$("#cl").setValue(result, i, $(obj).children().eq(1).html());
				if (i == 1) {
					$(obj).disable();
				}
			}
		});
	}
};
var parseHtml = function(obj) {
	if (typeof(obj) != 'undefined') {
		var text = '';
		var len = $(obj).children().length;
		if (len == 1) {
			text = $(obj).children().eq(0).val();
		}
		if (text == '') {
			text = $(obj).text();
		}
		return text;
	}
	
	return "";
};
var generateHtml = function(idx, count, value) {
	var html = "";
	if (idx == 0) {
		html = '<td class="tbl_page_td_left vtip" width="80px">' + value + '<input type="hidden" name="cList[' + count + '].fieldName" value="' + value + '"/></td>';
	} else {
		html = '<td class="tbl_page_td_left vtip" width="160px">' + value + '<input type="hidden" name="cList[' + count + '].colName" value="' + value + '"/></td>';
	}
	return html;
};
var scrollCL = function(top) {
	var _t = $("#cl").closest("div").offset().top;
	var _h = $("#cl").closest("div").height();
	var _scrollTop = $("#cl").closest("div").scrollTop();
	var stop = top; 
	if (top >= (_t + _h)) { 
		stop = top - (_t + _h) + _scrollTop;
	} else if (top < _t) {
		stop = _scrollTop - (_t - top);
	} else {
		stop = _scrollTop;
	}
	$("#cl").closest("div").scrollTop(stop);
};
//manual update field in file
var fvalue = '';
var con = function() {
	var flag = false;
	var o;
	$("#cl tr").each(function(i, obj) {
		if ($(obj).hasClass("table-color-click")) {
			flag = true;
			o = $(obj).find("td").eq(0);
			return false;
		}
	}); 
	if (flag) {
		var text = $(o).text();
		var html = $(o).html();
		html = html.replace($.trim(text), fvalue);
		$(o).html(html);
		$(o).children().eq(0).val(fvalue);
		tipsWindown.close();
	}
};
var cp = function(el) {
	fvalue = $(el).val();
};
</script>
<div id="manualUpdate" style="display: none" class="row">
	<table class="tbl_search">
        <tr>
        	<td class="label_td" width="40%"><spring:message code="fisp.label.dp.imp.newFieldName"/></td>
			<td width="60%"><input type="text" id="newField" class=".input-medium" onblur="javascript: cp(this)"/></td>
        </tr> 
	</table>
	<br>
	<input type="button" id="updateConfirm" value='<spring:message code="button.label.Confrim"/>' class="btn btn-primary" onclick="javascript: con();"/>
</div>
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
<div class="page_title"><spring:message code="fisp.titls.dp.imp.dpImpCfg"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;width:1200px;position:absolute;left:50px;">
<form:form id="form" action="${pageContext.request.contextPath}/DP_Imp_Cfg/SubmitCfg" method="post" modelAttribute="dpImpCfgForm" class="form-horizontal">
	<form:hidden path="dpImpCfg.impId"/>
	<input type="hidden" id="name"/>
	<table class="tbl_search">
    	<tr><td colspan="5">
    		<p class="text-info"><spring:message code="fisp.titls.dp.imp.dpImpDtl.dtl"/></p>
    	</td></tr>
    	<tr>
			<td><spring:message code="fisp.label.common.fileName"/>：${dpImpCfgForm.dpImpCfg.fileName }</td>
			<td></td>
			<td align="center"><spring:message code="fisp.label.common.selectedfiledtl"/></td>
			<td></td>
			<td><spring:message code="fisp.label.common.tableName"/>：${dpImpCfgForm.dpImpCfg.tableName }</td>
		</tr>
		<tr>
			<td width="367px">
				<!-- ============================ all field in file ============================ -->
				<div class="tbl_page_head" style="position:absolute;top:56px;">
					<table class="table table-striped table-bordered table-condensed tbl_page">
						<thead>
				        <tr>
				        	<th class="tbl_page_th" width="20px"><spring:message code="fisp.label.common.no"/></th>
				          	<th class="tbl_page_th" width="90px"><spring:message code="fisp.label.common.fieldName"/></th>
				          	<th class="tbl_page_th" width="120px"><spring:message code="fisp.label.common.fieldDesc"/></th>
				          	<th class="tbl_page_th" width="60px"><spring:message code="fisp.label.common.fieldLen"/></th>
				          	<th width="5px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body" style="overflow-y:scroll;position:absolute;min-height:200px;height:200px;top:86px">
					<table id="fl" class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
				        <c:forEach var="dto" items="${dpImpCfgForm.fList}">
							<tr>
					          	<td class="tbl_page_td_left vtip" width="20px">${dto.fieldSeqNo}</td>
					            <td class="tbl_page_td_left vtip" width="90px">${dto.fieldName}</td>
							  	<td class="tbl_page_td_left vtip" width="120px">${dto.fieldDesc}</td>
					            <td class="tbl_page_td_left vtip" width="60px">${dto.fieldLen}</td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
				<!-- ============================ all press ============================ -->
				<div class="tbl_page_head" style="position:absolute;top:300px;">
					<table class="table table-striped table-bordered table-condensed tbl_page">
						<thead>
				        <tr>
				          	<th class="tbl_page_th" width="160px"><spring:message code="fisp.label.common.outVal"/></th>
				          	<th class="tbl_page_th" width="140px"><spring:message code="fisp.label.common.inVal"/></th>
				          	<th width="4px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body" style="overflow-y:scroll;position:absolute;min-height:100px;height:100px;top:330px;">
					<table id="pl" class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
				        <c:forEach var="dto" items="${dpImpCfgForm.mList}">
							<tr>
					          	<td class="tbl_page_td_left vtip" width="160px">${dto.outVal}</td>
					            <td class="tbl_page_td_left vtip" width="140px">${dto.inVal}</td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
			</td>
			<td width="20" align="center">
				<input type="button" class="btn btn-small" onclick="selectRecord('fl', 'pl', '0');" value='<spring:message code="fisp.button.label.right"/>'/><br/><br/>
			</td>
			<td align="center">
				<!-- ============================ the detail in configuration ============================ -->
				<div class="tbl_page_head" style="width:450px;">
					<table class="table table-striped table-bordered table-condensed tbl_page" width="100%">
						<thead>
				        <tr>
				          	<th class="tbl_page_th" width="160px"><spring:message code="fisp.label.common.fieldName"/></th>
				          	<th class="tbl_page_th" width="160px"><spring:message code="fisp.label.common.colName"/></th>
				          	<th width="4px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body" style="overflow-y:scroll">
					<table id="cl" class="table table-striped table-bordered table-condensed tbl_page" width="162px">
					<tbody>
				        <c:forEach var="dto" items="${dpImpCfgForm.cList}" varStatus="i">
							<tr>
					            <td class="tbl_page_td_left vtip" width="160px">${dto.fieldName}
					            	<form:hidden path="cList[${i.index }].fieldName" value="${dto.fieldName}"/>
					            </td>
							  	<td class="tbl_page_td_left vtip" width="160px">${dto.colName}
					            	<form:hidden path="cList[${i.index }].colName" value="${dto.colName}"/>
					            </td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
			</td>
			<td width="20" align="center">
				<input type="button" class="btn btn-small" onclick="selectRecord('tl', '', '1');" value='<spring:message code="fisp.button.label.left"/>'/><br/><br/>
			</td>
			<td align="right">
				<!-- ============================ all column in table ============================ -->
				<div class="tbl_page_head">
					<table class="table table-striped table-bordered table-condensed tbl_page" width="265px">
						<thead>
				        <tr>
				        	<th class="tbl_page_th" width="20px"><spring:message code="fisp.label.common.no"/></th>
				          	<th class="tbl_page_th" width="80px"><spring:message code="fisp.label.common.colName"/></th>
				          	<th class="tbl_page_th" width="120px"><spring:message code="fisp.label.common.colDesc"/></th>
				          	<th class="tbl_page_th" width="40px"><spring:message code="fisp.label.common.colLen"/></th>
				          	<th width="2px"></th>
				        </tr>
						</thead>
					</table>
			    </div>
			    <div class="tbl_page_body" style="overflow-y:scroll">
					<table id="tl" class="table table-striped table-bordered table-condensed tbl_page">
					<tbody>
				        <c:forEach var="dto" items="${dpImpCfgForm.tList}">
							<tr>
					          	<td class="tbl_page_td_left vtip" width="20px">${dto.colSeqNo}</td>
					            <td class="tbl_page_td_left vtip" width="80px">${dto.colName}</td>
							  	<td class="tbl_page_td_left vtip" width="120px">${dto.colDesc}</td>
					            <td class="tbl_page_td_left vtip" width="40px">${dto.colLen}</td>
							</tr>
				        </c:forEach>
					</tbody>
					</table>
				</div>
			</td>
		</tr>
    </table>											
</form:form>						
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="manual" class="btn btn-primary" value='<spring:message code="fisp.button.label.imp.manual"/>'/>
		<input type="button" id="auto" class="btn btn-primary" value='<spring:message code="fisp.button.label.file.auto"/>'/>
		<input type="button" id="submitcfg" class="btn btn-primary" value='<spring:message code="button.lable.Submit"/>'/>
		<button class="btn btn-primary" onclick="javascript:window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>