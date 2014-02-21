/**
 * format the input money ,repalce the character to "" , auto add the commas
 * between numbers format the input money ,replace the illegal
 * character(contains '.') to ""
 */
function formatMoney(obj) {
	formatMoneyWithDot(obj, '1');
}

/**
 * format the input money ,repalce the character to "" , auto add the commas
 * between numbers format input money ,keep the dot .
 */
function formatMoneyWithDot(obj, type) {
	if (type == null || type == undefined || type == '') {
		type = '0';
	}
	var str = obj.value.replace(/,/g, "");
	str = str.replace("-", "");
	var temp = str.match(/^(-?)(\d*)(\.?)(\d*)$/);
	var bMinus = (str.substring(0, 1) == "-");
	if (bMinus)
		str = str.substring(1);
	if (temp == null) {
		var str1 = obj.value.replace(/[^\d]/g, "");
		var tempStr = str1.split(".");
		var i = 0;
		var arr = new Array();
		do {
			arr[i] = tempStr[0].substring(tempStr[0].length - i * 3,
					tempStr[0].length - (i + 1) * 3);
			i++;
		} while (tempStr[0].length - (i * 3) > 0)
		var sResult = arr.reverse().join(",");
		if (type == '0') {
			if (str.indexOf(".") != -1) {
				if (tempStr[1] != undefined) {
					sResult += "." + tempStr[1];
				}
			}
		}
		if (bMinus)
			sResult = "-" + sResult;
		obj.value = sResult;
	} else {
		var tempStr = str.split(".");
		var i = 0;
		var arr = new Array();
		do {
			arr[i] = tempStr[0].substring(tempStr[0].length - i * 3,
					tempStr[0].length - (i + 1) * 3);
			i++;
		} while (tempStr[0].length - (i * 3) > 0)
		var sResult = arr.reverse().join(",");
		if (type == '0') {
			if (str.indexOf(".") != -1) {
				sResult += "." + tempStr[1];
			}
		}
		if (bMinus)
			sResult = "-" + sResult;
		obj.value = sResult;
	}
}
/**
 * format the input number ,repalce the character to ""
 */
function numberFormat(obj) {
	obj.value = obj.value.replace(/[^\d]/g, '');
}
/**
 * format the input number when the number is copied ,repalce the character to ""
 */
function numberFormatCopy(obj) {
	window.clipboardData.setData('text', window.clipboardData.getData('text')
			.replace(/[^\d]/g, ''));
}

function numberStringFormat(obj) {
	obj.value = obj.value.replace(/[\W]/g, '');
}
/**
 * format the input number when the number is copied ,repalce the character to ""
 */
function numberStringFormatCopy(obj) {
	window.clipboardData.setData('text', window.clipboardData.getData('text')
			.replace(/[\W]/g, ''));
}

/**
 * Back to Menu Page add by Jon 20130424
 */
function backMenu() {
	location.href = contextPath + '/index';
}

/**
 * back to last url add by Jon 20130510
 */
function backLastUrl(formName) {
	var form = document.getElementById(formName);
	var referer = document.getElementById('refererUrl').value;
	referer = referer.substring(referer.indexOf(contextPath));
	form.action = contextPath + '/cm/backLastUrl?refererUrl=' + referer;
	form.submit();
}

function setHead() {
	if (window.parent.external) {
		$("#id_header").css("display", "none");
	}
}

$(function() {
	if ($.trim($("#id_result")) && $.trim($("#id_result").html()) != "") {
		tipsWindown("提示", "id:id_showMsg", "360", "230", "true", "2400",
				"true", "id");
	}
	if ($.trim($("#id_result_manual"))
			&& $.trim($("#id_result_manual").html()) != "") {
		tipsWindown("提示", "id:id_showMsg_manual", "360", "230", "true", "",
				"true", "id");
	}
});

function showMsg(elId) {
	if ($.trim($("#" + elId)) && $.trim($("#" + elId).html()) != "") {
		tipsWindown("提示", "id:id_showMsg", "360", "230", "true", "2400",
				"true", "id");
	}
}

function showMsg11(elId) {
	if ($.trim($("#" + elId)) && $.trim($("#" + elId).html()) != "") {
		tipsWindown("提示", "id:id_showMsg", "360", "230", "true", "", "true",
				"id");
	}
}

/**
 * Name： showDialog Description：显示模态化窗口，默认大小：500 x 1024 Parameters： url：路径
 * Return： 字符串：模态化窗口需要返回到原页面的值 Author： yyw Date： 2013-11-13
 */
function showDialog(url) {
	var ind = url.indexOf("?");
	if(ind==-1){
		url = url+'?time='+new Date().getTime();
	}else{
		url = url+'&time='+new Date().getTime();
	}
	return showDialog(url, '500', '1024');
}
/**
 * Name： showDialogAndRefresh Description：显示模态化窗口，在窗口关闭后，刷新原页面，默认大小：500 x 1024
 * Parameters： url：路径 Return： 字符串：模态化窗口需要返回到原页面的值 Author： yyw Date： 2013-11-13
 */
function showDialogAndRefresh(url) {
	return showDialogAndRefresh(url, '500', '1024');
}
/**
 * Name： showDialogAndRefresh Description：显示模态化窗口，在窗口关闭后，刷新原页面，默认大小：500 x 1024
 * Parameters： url：路径 height：高度（px） width：宽度（px） Return： 字符串：模态化窗口需要返回到原页面的值
 * Author： yyw Date： 2013-11-13
 */
function showDialogAndRefresh(url, height, width) {
	var oldUrl = window.location.href;
	var rv = showDialog(url, height, width);
	window.location.href = oldUrl;
	return rv;
}
/**
 * Name： showDialog Description：显示模态化窗口，在参数中指定大小 Parameters： url：路径
 * height：高度（px） width：宽度（px） Return： 字符串：模态化窗口需要返回到原页面的值 Author： yyw Date：
 * 2013-11-13
 */
function showDialog(url, height, width) {
	var ind = url.indexOf("?");
	if(ind==-1){
		url = url+'?time='+new Date().getTime();
	}else{
		url = url+'&time='+new Date().getTime();
	}
	return window
			.showModalDialog(
					url,
					window,
					'dialogHeight: '
							+ height
							+ 'px; dialogWidth: '
							+ width
							+ 'px;edge: Raised; center: Yes; help: no; resizable: Yes; status: no;');
}
/**
 * Name： parseJSONAndDisplay Description：解析JSON字符串，并显示到指定的页面标签中（页面标签也以JSON字符串传入）
 * JSON格式：{"param1":"value","param2":"value"}
 * 页面标签JSON格式：[{"tagId":"param1","tagId":"param2"}] Parameters：
 * value：返回值（JSON字符串） objEl：页面标签（JSON字符串） Return： 无 Author： yyw Date： 2013-11-13
 */
function parseJSONAndDisplay(value, objEl) {
	if (value && value != "") {
		value = value.replace('\n', "/");
		var dataObj = eval("(" + value + ")");
		var elObj = eval(objEl);
		for ( var i in elObj) {
			for ( var key in elObj[i]) {
				var key2 = elObj[i][key];
				var name = $("#" + key).get(0).tagName;
				if (name.toUpperCase() == "INPUT") {
					$("#" + key).val(dataObj[key2]);
				} else if (name.toUpperCase() == "SPAN") {
					$("#" + key).html(dataObj[key2]);
				} else if (name.toUpperCase() == "SELECT") {
					$("#" + key).val(dataObj[key2]);
				}

			}
		}
		/*
		 * } else { var elObj = eval(objEl); for ( var i in elObj) { for ( var
		 * key in elObj[i]) { var name = $("#" + key).get(0).tagName; if (name ==
		 * "INPUT") { $("#" + key).val(""); } else if (name == "SPAN") { $("#" +
		 * key).html(""); } } }
		 */
	}
}

/**
 * Name： showSelFile Description：显示文件定义信息查询页面 Parameters： flag：导入/出标志
 * objEl：页面标签（JSON字符串） Return： 无 Author： yyw Date： 2013-11-13
 */
function showSelFile(flag, objEl) {
	var rv = showDialog(contextPath + '/search/filename/init?ioFlag=' + flag,
			'500', '1040');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, objEl);
	window.ReturnValue = "";
}
/**
 * Name： showSelTable Description：显示表定义信息查询页面 Parameters： flag：导入/出标志
 * objEl：页面标签（JSON字符串） Return： 无 Author： yyw Date： 2013-11-13
 */
function showSelTable(obj) {
	var rv = showDialog(contextPath + '/search/tablename/init', '500', '1040');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

function showSelOrg(obj) {
	var rv = showDialog(contextPath + '/OrgSearch/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

function showAllOrg(obj) {
	var rv = showDialog(contextPath + '/OrgSearch/QryAll', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

// 弹出公共账户查询页面
function showSelAct(obj) {
	var rv = showDialog(contextPath + '/FTZCMAccountQry/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

function showSelCfg(obj) {
	var rv = showDialog(contextPath + '/BMG_STEP_CFG/Init', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

// 弹出国内地区代码查询页面
function showSelReg(obj) {
	var rv = showDialog(contextPath + '/FTZCMRegionQry/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

// 弹出交易性质查询页面
function showSelMeta(obj) {
	var rv = showDialog(contextPath + '/FTZCMMetaQry/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

// 弹出国别代码查询页面
function showSelNation(obj) {
	var rv = showDialog(contextPath + '/FTZCMNationQry/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}
// 弹出国资产负债指标查询页面
function showSelBalance(obj) {
	var rv = showDialog(contextPath + '/FTZCMBalanceQry/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}
// 弹出银行代码查询页面
function showSelBank(obj) {
	var rv = showDialog(contextPath + '/FTZCMBankQry/Qry', '500', '1024');
	if (typeof (rv) == 'undefined') {
		rv = window.ReturnValue;
	}
	parseJSONAndDisplay(rv, obj);
	window.ReturnValue = "";
}

/**
 * Name： getOrgInf Description： 异步查询机构号是否存在 Parameters：
 * obj--在控件中调getOrgInf(this) Return： orgExist(true/false) Author： hlj Date：
 * 2013-11-22
 */
function checkOrgInf(obj) {
	orgid = $(obj).val();
	if (orgid != null && orgid != "") {
		var orgExist = "";
		$.ajax({
			url : contextPath + "/OrgSearch/OrgCheck?orgId=" + orgid,
			type : "post",
			dataType : "json",
			async : false,
			data : {
				orgid : orgid
			},
			success : function(rs) {
				orgExist = rs.orgExist;
			}
		});
		return orgExist;
	}
}

/**
 * 将字符串obj加密。在后台使用CommonUtil.decryptor(encrytor)可解密
 * 
 * @param obj
 *            待加密的字符串
 * @returns {String} 已加密的字符串
 */
function encrytorInf(obj) {
	var encrytor = "";
	if (obj != null && obj != "") {
		$.ajax({
			url : contextPath + "/encryp/getEncrytor",
			type : "post",
			async : false,
			data : {
				content : obj
			},
			success : function(rs) {
				encrytor = rs;
			}
		});
	}
	return encrytor;
}

function initControlAttr(pageId,obj){
	var runtime = {
		    start : function() {
		        this.startTime = new Date();
		    },
		    end : function() {
		        return (new Date() - this.startTime);
		    }
		};
	runtime.start();
	var input = JSON.parse(obj);
	var inputString = input[pageId];
	for ( var i = 0; i < inputString.length; i++) {
		var input_tmp = $("#" + inputString[i])[0].type;
		if('text'!=input_tmp){
			$("#" + inputString[i]).prop("disabled", false);
		}else{
			
			$("#" + inputString[i]).prop("readonly", false);
		}
		
	}
	alert(runtime.end());
}

String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};