<script type="text/javascript">

	//submit button
	function modSubmit() {
		//alert("add: "+add.join()+"\n"+"remove: "+rmv.join());
		
		if(add.length==0&&rmv.length==0){
			var msg="\u6CA1\u6709\u66F4\u6539\uFF01";			
			//alert("No update");
			document.getElementById("errorMsg").style.display="block";
			document.getElementById("id_result").style.display="none";
			document.getElementById("errorMsg").innerHTML = msg;
			showMsg("id_showMsg");
			return false;
		}
		var modYear;
		if(add.length!=0){
			modYear=add[0].substr(0,4);
		}else{
			modYear=rmv[0].substr(0,4);
		}		
		document.getElementById("modYearId").value = modYear;
		document.getElementById("addHolidaysId").value = add.join();
		document.getElementById("removeHolidaysId").value = rmv.join();		
		smDateModForm.action = "${pageContext.request.contextPath}/SM_Date_Mdf/Mdf";
		smDateModForm.submit();
	/*	$.ajax({
		    url: "${pageContext.request.contextPath}/SM_Date_Mdf/Mdf",
		    dataType: "json",
		    data: {
		     "addHolidays": add.join(),
		     "removeHolidays":rmv.join()
		    },
		    success: function(){
		    	alert("SUCCESS");
		    }
		});
	}*/
   }
</script>
<style>
<!--
.divcss5{width:600px; height:650px} 
-->
</style>
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div class="alert alert-error" id="errorMsg"  style="display: none"></div>
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="sm_Date_ModForm">
			<form:form commandName="sm_Date_ModForm">
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
<center>
<div class="row">
    
    <form:form id="smDateModForm" action="${pageContext.request.contextPath}" modelAttribute="sm_Date_ModForm">   
    <input id="modYearId" type="hidden" name="modYear"/>
    <input id="addHolidaysId" type="hidden" name="addHolidays"/>
    <input id="removeHolidaysId" type="hidden" name="removeHolidays"/> 
    </form:form>
    <table  class="divcss5">
	    <tr>
			<td class="label_td"><div id="m1" ></div></td>
			<td class="label_td"><div id="m2" ></div></td>
			<td class="label_td"><div id="m3" ></div></td>
			<td class="label_td"><div id="m4" ></div></td>	
		</tr>
		<tr>
			<td class="label_td"><div id="m5" ></div></td>
			<td class="label_td"><div id="m6" ></div></td>
			<td class="label_td"><div id="m7" ></div></td>
			<td class="label_td"><div id="m8" ></div></td>	
		</tr>
		<tr>
			<td class="label_td"><div id="m9" ></div></td>
			<td class="label_td"><div id="m10" ></div></td>
			<td class="label_td"><div id="m11" ></div></td>
			<td class="label_td"><div id="m12" ></div></td>	
		</tr>		
		
	</table>
	
</div>
</center>
<br/>
<br/>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" id="modbtnid" class="btn btn-primary" onclick="modSubmit()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
<script type="text/javascript">
var json=<%=request.getAttribute("holiJson") %>;

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

var add = new Array(); 
var rmv = new Array();

var mon1 = new Kalendae("m1",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month1,
	directionScrolling:true
	
});  
mon1.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon2 = new Kalendae("m2",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month2,
	directionScrolling:true
	
});  
mon2.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon3 = new Kalendae("m3",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month3,
	directionScrolling:true
	
});  
mon3.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon4 = new Kalendae("m4",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month4,
	directionScrolling:true
	
});  
mon4.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon5 = new Kalendae("m5",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month5,
	directionScrolling:true
	
});  
mon5.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon6 = new Kalendae("m6",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month6,
	directionScrolling:true
	
});  
mon6.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon7 = new Kalendae("m7",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month7,
	directionScrolling:true
	
});  
mon7.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon8 = new Kalendae("m8",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month8,
	directionScrolling:true
	
});  
mon8.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon9 = new Kalendae("m9",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month9,
	directionScrolling:true
	
});  
mon9.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon10 = new Kalendae("m10",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month10,
	directionScrolling:true
	
});  
mon10.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon11 = new Kalendae("m11",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month11,
	directionScrolling:true
	
});  
mon11.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});


var mon12 = new Kalendae("m12",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month12,
	directionScrolling:true
	
});  
mon12.subscribe('change', function(date) {	  
	var chgDate=Kalendae.moment(date).format("YYYYMMDD");
	var chgDateTmp=Kalendae.moment(date).format("YYYY-MM-DD");
	if(this.isSelected(chgDateTmp)){	   			   		
	   	var exit = isExist(rmv,chgDate);		   		
	   	if(exit>=0){
	   		rmv.remove(exit);
	   			   				
	   	}else{
	   		if(isExist(add,chgDate)<0){
				add.push(chgDate);		   			
			}
	   	}	   		
	}else{	   	  	   			
	   	var exit = isExist(add,chgDate);		   		
	   	if(exit>=0){		   				
	   		add.remove(exit);				   				   				
	   	}else{
	   		if(isExist(rmv,chgDate)<0){
			   	rmv.push(chgDate);
			}	
	   	}	   		
	}	  
	  
});



//k.setSelected("2013-01-02");
</script>