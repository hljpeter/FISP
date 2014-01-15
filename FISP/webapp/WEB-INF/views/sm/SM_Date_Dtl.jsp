<style>
<!--
.divcss5{width:600px; height:650px} 
-->
</style>


<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.HolidayMaintain.detail"/></div>

<!-- body -->
<center>
<div class="row">
    <table  class="divcss5">
	    <tr>
			<td class="label_td"><div id="m1"></div></td>
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
		<button type="button" class="btn btn-primary" onclick="window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>
<script type="text/javascript">
var json=<%=request.getAttribute("holiJson") %>;

var mon1 = new Kalendae("m1",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	
	directionScrolling:true
	
});  
mon1.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});

var mon2 = new Kalendae("m2",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month2,
	directionScrolling:true
	
});  
mon2.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});

var mon3 = new Kalendae("m3",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month3,
	directionScrolling:true
	
});  
mon3.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});


var mon4 = new Kalendae("m4",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month4,
	directionScrolling:true
	
});  
mon4.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});


var mon5 = new Kalendae("m5",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month5,
	directionScrolling:true
	
});  
mon5.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});


var mon6 = new Kalendae("m6",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month6,
	directionScrolling:true
	
});  
mon6.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});


var mon7 = new Kalendae("m7",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month7,
	directionScrolling:true
	
});  
mon7.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});

var mon8 = new Kalendae("m8",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month8,
	directionScrolling:true
	
});  
mon8.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});

var mon9 = new Kalendae("m9",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month9,
	directionScrolling:true
	
});  
mon9.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});


var mon10 = new Kalendae("m10",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month10,
	directionScrolling:true
	
});  
mon10.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});

var mon11 = new Kalendae("m11",{
	months:1,
	mode:'multiple',			
	direction: 'post',	
	format:"YYYYMMDD",
	selected:json.month11,
	directionScrolling:true
	
});  
mon11.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});

var mon12 = new Kalendae("m12",{
	months:1,
	mode:'multiple',			
	direction: 'post',
	format:"YYYYMMDD",
	selected:json.month12,
	directionScrolling:true
	
});  
mon12.subscribe('date-clicked', function(date) {
	   //setSelected(date);
	return false;
});



mon1.setSelected(json.month1);
</script>