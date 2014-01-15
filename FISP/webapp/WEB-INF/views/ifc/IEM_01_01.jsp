<script type="text/javascript">
window.name="curWindow";


$(function() {
	
	   $("#methodTable").find("tr").bind('click', function(){
		    var mid=$(this).find("td:eq(0)").text();
		    var form = document.getElementById("form");
			form.action = '${pageContext.request.contextPath}/iem01/02/param?mid='+mid+'&alternative='+1;
			form.target="curWindow";
			form.submit();
	   }); 
	
	
	
	$("input[name='value']").keyup(function(){   
		 var type=$(this).parent().prev().find("input").val();
		 var tmptxt=$(this).val();  
		if(type=='java.lang.Integer')
		{
		  $(this).attr("maxlength","8");
          $(this).val(tmptxt.replace(/\D|^0/g,'')); 
		}
		else if(type=='java.lang.Double'||type=='java.lang.Float')
		{
			
			  var regStrs = [ 

			                 ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0 

			                 ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点 

			                 ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点 

			                 ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上 

			             ]; 

			             for(i=0; i<regStrs.length; i++){ 

			                 var reg = new RegExp(regStrs[i][0]); 
			                 $(this).val($(this).val().replace(reg, regStrs[i][1])); 

			             }
		}
		else 
		{
			  $(this).attr("maxlength","8");
		}
});
	
	
	 $(':checkbox[name=checkbox]').each(function(){            $(this).click(function(){                if($(this).attr('checked')){                    $(':checkbox[name=checkbox]').removeAttr('checked');                    $(this).attr('checked','checked');                }            });        });
	
}
);

function getMethodParam(mId) {
    $.ajax({
        url: "${pageContext.request.contextPath}/iem01/01/getparam",
        type: "get",
        dataType: "html",
        async: true,
        data: { param: value },
        success: function (json) {
            $('#abc').append(json);
        }
    }
);
}

function searchParam(mid) {
	var form = document.getElementById("form");
	form.action = '${pageContext.request.contextPath}/iem01/02/param?mid='+mid+'&alternative='+1;
	form.target="curWindow";
	form.submit();
}

function getValue() {
	var form = document.getElementById("form1");
	form.action = "${pageContext.request.contextPath}/iem01/03/value";
	form.target="curWindow";
	form.submit();
}

function choose() {
		window.returnValue = $("#bds").val();
		window.close();
	}


function getLSValue() {
	var form = document.getElementById("form1");
	form.action = "${pageContext.request.contextPath}/iem01/04/staticparam";
	form.target="curWindow";
	form.submit();
}

function chooseValue(obj)
{
	 $(':checkbox[name=checkbox]').each(function(){      
		 if($(this).attr('checked')){   
			 if($(obj).parents(".tbl_page_td_left").prev().prev().find("input").val()!=$(this).parent().next().next().text())
					 {
				 alert("类型不匹配");
				       return;
					 }
			 $(obj).parents(".tbl_page_td_left").prev().find("input").attr("readonly",true);
			 $(obj).parents(".tbl_page_td_left").prev().find("input").val( $(this).parent().next().next().next().text() );         
			 }  });
}


</script>


<div class="page_title">任务管理  / 映射配置  / 导入映射配置 / 修改字段 / 表达式</div>


<div style="margin-bottom: 2px;"><spring:message code="iem.title.GongShiQu"/>
<form id="form" method="post">

<div class="tbl_page_head">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<thead>
		<tr>
		    <th class="tbl_page_th" width="80px" style="display: none"></th>
			<th class="tbl_page_th" width="80px"><spring:message code="iem.label.METHOD_NAME"/></th>
			<th class="tbl_page_th" width="80px"><spring:message code="iem.label.METHOD_Des"/></th>

		</tr>
	</thead>
</table>
</div>
<div class="tbl_page_body"  style="min-height: 100px; height: 155px;">
<table id="methodTable"
	class="table table-striped table-bordered table-condensed tbl_page">
	<tbody>
		
			<c:forEach var="dto" items="${methodList}" varStatus="i">
				<tr>
				    <td  class="tbl_page_td_left vtip" style="display: none" width="80px">${dto.mId}</td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.methodName}</td>
					<td class="tbl_page_td_left vtip" width="80px">${dto.methodDesc}</td>
				</tr>
			</c:forEach>
	</tbody>
</table>
</div>
</form>

</div>

<% 
if(request.getParameter("alternative")!= null)
if(request.getParameter("alternative").toString().equals("1")){%>
<script type="text/javascript">
$(function() {
	$('#addZZBtn').removeAttr("disabled");
	$('#addLSBtn').removeAttr("disabled");
}
);

</script>
<%}%>


<div  style="margin-bottom: 2px;"><spring:message code="iem.title.BiaodaShi"/>
        <input id="bds" value="${result}" style="width:500px;" readonly="readonly"></input>

    	<input type="button" class="btn btn-primary" value="<spring:message code="exm.exit"/>" id="addBtn" onclick="choose()"/>
</div>

<div style="margin-bottom: 2px;"><spring:message code="iem.title.LinshiGongshiQu"/>


<div class="tbl_page_head">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<thead>
		<tr>
		    <th class="tbl_page_th" width="30px"><spring:message code="button.check"/></th>
			<th class="tbl_page_th" width="120px" style="display: none" ><spring:message code="iem.label.P_ID"/></th>
			<th class="tbl_page_th" width="120px"><spring:message code="iem.label.TYPE"/></th>
			<th class="tbl_page_th" width="120px"><spring:message code="iem.title.BiaodaShi"/></th>
		</tr>
	</thead>
</table>
</div>
<div class="tbl_page_body"  style="min-height: 100px; height: 95px;">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<tbody>
		
			<c:forEach var="dto" items="${staticmethodparamList}" varStatus="i">
				<tr>
				    <td class="tbl_page_td_left vtip" width="30px"><input type="checkbox" name="checkbox" ></td>
					<td class="tbl_page_td_left vtip" width="120px" style="display: none" >${dto.pId}</td>
					<td class="tbl_page_td_left vtip" width="120px">${dto.paramType}</td>
					<td class="tbl_page_td_left vtip" width="120px">${dto.rsv1}</td>
				</tr>
			</c:forEach>
	</tbody>
</table>
</div>

</div>


<div style="margin-bottom: 2px;"><spring:message code="iem.title.CaozuoQu"/>
      
<form id="form1" method="post">  
<div class="tbl_page_head">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<thead>
		<tr>
			<th class="tbl_page_th" style="display: none"  width="120px"><spring:message code="iem.label.P_ID"/></th>
			<th class="tbl_page_th" width="120px"><spring:message code="iem.label.DESC"/></th>
			<th class="tbl_page_th" width="120px"><spring:message code="iem.label.TYPE"/></th>
			<th class="tbl_page_th" width="120px"><spring:message code="itm.label.canshuValue"/></th>
					    <th class="tbl_page_th" width="60px"><spring:message code="index.label.sm.Operation"/></th>
		</tr>
	</thead>
</table>
</div>
<div class="tbl_page_body"  style="min-height: 100px; height: 95px;" style="margin-bottom: 40px;">
<table
	class="table table-striped table-bordered table-condensed tbl_page">
	<tbody>
		
			<c:forEach var="dto" items="${methodparamList}" varStatus="i">
				<tr>
					<td class="tbl_page_td_left vtip" style="display: none"  width="120px"><input name="pid" value="${dto.pId}"  readonly="readonly"/></td>
					<td class="tbl_page_td_left vtip" width="120px"><input name="type" value="${dto.paramDesc}" readonly="readonly"/></td>
					<td class="tbl_page_td_left vtip" width="120px"><input name="type" value="${dto.paramType}" readonly="readonly"/></td>
					<td class="tbl_page_td_left vtip" width="120px"><input name="value" value="" /></td>
					<td class="tbl_page_td_left" width="60px">
						<div align="center" style="height: 25px">
							<input type="button" class="btn btn-small" onclick="chooseValue(this)" value="<spring:message code="button.label.Choose"/>">
						</div>
					</td>
				</tr>
			</c:forEach>
	</tbody>
</table>
</div>
     
  <div class="row" >
  <div class="navbar  text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
     <input type="button" class="btn btn-primary" value="<spring:message code="exm.baodashi"/>" id="addZZBtn" onclick="getValue()" disabled="disabled"/>
     <input type="button" class="btn btn-primary" value="<spring:message code="exm.linshigongshiqu"/>" id="addLSBtn" onclick="getLSValue()" disabled="disabled"/>
     <input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>" >
     </div>
     </div>
      </form> 
</div>