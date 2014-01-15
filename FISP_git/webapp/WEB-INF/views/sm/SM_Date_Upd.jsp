<script type="text/javascript">
	var no=1;
	var dayList = new Array();
	//append button
	function append() {		
		var strDate = document.getElementById("strDate").value;
		var endDate = document.getElementById("endDate").value;
		var holiFlg = document.getElementById("holiflg").value;
		var remark = document.getElementById("remarkId").value;
		if(strDate.length == 0 || endDate.length == 0){
			var msg="\u65E5\u671F\u4E0D\u80FD\u4E3A\u7A7A\uFF01";					
			document.getElementById("errorMsg").style.display="block";
			document.getElementById("id_result").style.display="none";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			return;
		}
		
		if(strDate>endDate){
			var msg="\u5F00\u59CB\u65E5\u671F\u4E0D\u80FD\u665A\u4E8E\u7ED3\u675F\u65E5\u671F\uFF01";					
			document.getElementById("errorMsg").style.display="block";
			document.getElementById("id_result").style.display="none";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			return;
		}
		
	    
		var dayScope= strDate+"~"+endDate+"#"+holiFlg;
	    var exist = isExist(dayList,dayScope);		   		
	   	if(exist>=0){	
	   		var msg="\u5DF2\u7ECF\u5B58\u5728\u8BE5\u8303\u56F4\u8282\u5047\u65E5\u7EF4\u62A4\u8BB0\u5F55\uFF01";					
			document.getElementById("errorMsg").style.display="block";
			document.getElementById("id_result").style.display="none";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
	   		return;
	   	}else{
	   		dayList.push(dayScope);	   		
	   	} 		

	    $("#daylist").append("<tr id='day"+no+"'>"+	          
	            "<td class='tbl_page_td_center vtip' width='120px' align='center'>"+strDate+"~"+endDate+"</td>"+
	            "<td class='tbl_page_td_center vtip' width='65px' align='center'>"+(holiFlg=='N'?'<spring:message code="index.label.sm.Yholi"/>':'<spring:message code="index.label.sm.Nholi"/>')+"</td>"+
	            "<td class='tbl_page_td_center vtip' width='100px' align='center'>"+remark+"</td>"+ 
	            "<td class='tbl_page_td_center' width='70px'>"+
		        "  <div align='center' style='height: 25px'>"+					
				"	  <input type='button' id='deleteBtn' class='btn btn-small' onclick={$('#day"+no+"').remove();dayListremove('"+dayScope+"');} value='<spring:message code='button.lable.Del'/>'>"+											
	            "  </div>"+
	            "</td>"+
	            "</tr>");
		
	    //$("table.table-striped tbody tr:odd").addClass("table-color-odd");
		$("#day"+no).click(function() {
			var v = $(this).hasClass("table-color-click");
			$("table.table-striped tbody tr").removeClass("table-color-click");
			if(!v){
				 $(this).addClass("table-color-click");
			}
		});
		
		$("table.table-striped tbody tr:last-child td").css("border-bottom","1px solid #dddddd");
	    
		
		tips();      
		
		
	    no++;	   
	}
	function dayListremove(scope){
		var exist = isExist(dayList,scope);
		dayList.remove(exist);
	}
	function strDateChange(){
		var strDate = document.getElementById("strDate").value;
		var endDate = document.getElementById("endDate").value;
		if(endDate.length==0 || strDate>endDate){
			document.getElementById("endDate").value=strDate;
		}
		
	}
	//check value in  arrary
	function isExist(arr,val){
		 if(arr.length ==0){
		  return -1;
		 }
		 for(var i=0;i<arr.length;i++){
		  if(val == arr[i])
		   return i;
		 }

		return -1;
	}
	Array.prototype.remove=function(dx){
	    if(isNaN(dx)||dx>this.length){return false;}
	    for(var i=0,n=0;i<this.length;i++)
	    {
	        if(this[i]!=this[dx])
	        {
	            this[n++]=this[i];
	        }
	    }
	    this.length-=1;
	};
	//modify button
	function modSubmit(){
		document.getElementById("updList").value = dayList.join();
		//alert(document.getElementById("updList").value);		
		smDateListForm.action = "${pageContext.request.contextPath}/SM_Date_Upd/Upd";
		smDateListForm.submit();
	}
	
	function tips(){
		this.xOffset = -10; // x distance from mouse
	    this.yOffset = 10; // y distance from mouse       
	    
	    $(".vtip").unbind().hover(  
	    		
	        function(e) {
	            this.t = this.innerHTML;
	            this.top = (e.pageY + yOffset); this.left = (e.pageX + xOffset);
	            
	            $('body').append( '<p id="vtip"><img id="vtipArrow" />' + this.t + '</p>' );
	                        
	            $('p#vtip #vtipArrow').attr("src", contextPath+'/resources/vendor/img/vtip_arrow.png');
	            $('p#vtip').css("top", this.top+"px").css("left", this.left+"px").fadeIn("slow");
	            
	        },
	        function() {
	        	//alert("remove");
	            $("p#vtip").fadeOut("slow").remove();
	        }
	    ).mousemove(
	        function(e) {
	            this.top = (e.pageY + yOffset);
	            this.left = (e.pageX + xOffset);
	            //this.innerHTML = this.t;            
	            $("p#vtip").css("top", this.top+"px").css("left", this.left+"px");
	        }
	    );      
	}
	
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg"  style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sm_Date_UpdForm">
			<form:form commandName="sm_Date_UpdForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.HolidayMaintain.mdf"/></div>

<!-- body -->
<div class="row">	
		<table class="tbl_search">
	    	<tr>
	    		<td class="label_td"><font color="red">*</font><spring:message code="index.label.sm.dayScope"/> </td>
				<td>
					 <input type=text  id="strDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});"  onchange="strDateChange()"/>&nbsp;~&nbsp;  
				 	<input type=text id="endDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
				</td>	
				<td class="label_td"><spring:message code="index.label.sm.holidayFlag"/></td>
				<td>
					<select id="holiflg" class="span2">
						<option value="N" selected><spring:message code="index.label.sm.Yholi"/></option>
						<option value="Y"><spring:message code="index.label.sm.Nholi"/></option>
					</select>
				</td>		
			</tr>	    		
			<tr>
	    		<td class="label_td"><spring:message code="index.label.sm.remark"/> </td>
				<td colspan="2">	
					<input type=text   class="span5"  id="remarkId"/>
				</td>					
				<td>				    
	    			<div align="right">	    			
	    				<button type="button" class="btn btn-primary" onclick="append()"><spring:message code="button.label.append"/></button>
	    			</div>	    			
	    		</td>	
			</tr>	    	
	    </table>								

</div>
<div class="row">
	<div class="tbl_page_head">
	<table class="table table-striped table-bordered table-condensed tbl_page">
      <thead>
        <tr>        	
          	<th class="tbl_page_th" width="120px"><spring:message code="index.label.sm.dayScope2"/></th>
          	<th class="tbl_page_th" width="65px"><spring:message code="index.label.sm.holidayFlag"/></th>
          	<th class="tbl_page_th" width="100px"><spring:message code="index.label.sm.remark"/></th>
          	
          	<th class="tbl_page_th" width="70px"><spring:message code="index.label.sm.Operation"/></th>
        </tr>
      </thead>
     </table>
    </div>
    <div class="tbl_page_body"    >
	  <table class="table table-striped table-bordered table-condensed tbl_page" >
         <tbody id="daylist">
         
         </tbody>
       </table>
    <form:form id="smDateListForm" action="${pageContext.request.contextPath}" modelAttribute="sm_Date_UpdForm">
    	<input id="updList" type="hidden" name="updList"/>
    </form:form>
	</div>
</div>


<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="modbtnid" class="btn btn-primary" onclick="modSubmit()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
