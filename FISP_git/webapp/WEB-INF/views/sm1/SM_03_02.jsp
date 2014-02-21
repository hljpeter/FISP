<%@page import="com.synesoft.fisp.app.common.constants.CommonConst"%>
<%@page import="com.synesoft.fisp.app.common.utils.StringUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.synesoft.fisp.domain.model.RoleInf"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.synesoft.fisp.domain.model.UserRoleInf"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>


<script type="text/javascript">

	//init page
	$(function() {
		var msg = "${successmsg}";
		if(msg && msg!=""){
			$("#orgId_current").css("display","block");
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("#confirmBtn").attr("disabled", "disabled");
			$("#alluserOrgInfArr").attr("disabled", "disabled");
			$("#loginorg").attr("disabled", "disabled");
			$("#addBtn").attr("disabled", "disabled");
			$("#delBtn").attr("disabled", "disabled");
			$("input[name=roleArray]").attr("disabled", true);
			var roleMode = "${requestScope.RoleMode }";
			if (roleMode == "1") {
				$("#userOrgInfArr").attr("disabled", true);
			} else {
				$("#userOrgInfArr").attr("disabled", false);
			}
			var roleOrg = document.getElementsByName("roleOrgArray");
			for(var i=0;i<roleOrg.length;i++){
				//循环将选择角色的多选框不可以修改
				roleOrg[i].disabled = true;
			}
		}else{
			$("#addMoreBtn").attr("disabled", "disabled");
		}
	});

	function check(obj)
	{
		$("#Badmin").attr("checked",false);
		$("#Boper").attr("checked",false);
		$("#Cmaker").attr("checked",false);
		$("#Cchecker").attr("checked",false);
		$(obj).attr("checked",true);
	}
	
	//addMore button
	function addGo() {
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/nsm03/02/init";
		form.submit();
	}
	
	//add button
	function add() {
		
		
		if($("#userid").val()=="")
		{
		   alert("操作员ID不能为空");
		   return false;
		}
		if($("#username").val()=="")
		{
		   alert("操作员名称不能为空");
		   return false;
		}
		if($("#userOrgInfArr").children().length!=1)
		{
		   alert("所属机构不能为空");
		   return false;
		}
		
		if($("#Badmin").attr("checked")=="checked"||$("#Boper").attr("checked")=="checked"||$("#Cmaker").attr("checked")=="checked"||$("#Cchecker").attr("checked")=="checked")
		{
		 
		}
		else
		{
		   alert("角色不能为空");
		   return false;
		}
		if($("#department").val()=="")
		{
		   alert("部门不能为空");
		   return false;
		}
		$("select[name='userOrgInfArr'] option").attr("selected",true);
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/nsm03/02/add";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	// 添加
	function addOrgId(){
		
		if($("#userOrgInfArr").children().length==1)
		{
			alert("只能选择一个所属机构");
			return;
		}
		var cp=$("#alluserOrgInfArr option:selected").clone();
		$("#userOrgInfArr").append($("#alluserOrgInfArr option:selected"));
		$("#userOrgInfArr").find("option").change(function(){
			showRole(this.value);
		});
		$("#loginorg").append(cp);
	}
	// 移除
	function delOrgId(){ 
		$("#orgId_current").css("display","none");
		//移除机构时，设置机构的角色checked为false
		var orgId = $("#userOrgInfArr option:selected").val();
		$("."+orgId).each(function(i){
			this.checked = false;
			}); 
		$("#alluserOrgInfArr").append($("#userOrgInfArr option:selected"));
		var cp=$("#userOrgInfArr option").clone();
		$("#loginorg option").replaceWith(cp);
	}	
	//显示角色
	function showRole(){
		$("#orgId_current").css("display","block");
		
		var branchid=$.trim( $("#userOrgInfArr").val());
		$("#Badmin").val(branchid+"_Badmin");
		$("#Boper").val(branchid+"_Boper");
		$("#Cmaker").val(branchid+"_Cmaker");
		$("#Cchecker").val(branchid+"_Cchecker");
		
	}
	function getElementsByName (tag, name){
	    var returns = document.getElementsByName(name);
	    if(returns.length > 0) return returns;
	    returns = new Array();
	    var e = document.getElementsByTagName(tag);
	    for(var i = 0; i < e.length; i++){
	        if(e[i].getAttribute("name") == name){
	            returns[returns.length] = e[i];
	        }
	    }
	    return returns;
	}
</script>
<!-- tips information -->
<div id="id_showMsg" style="display: none"> 
	<br /><br />
	<div id="id_result">
		<t:messagePanel messagesAttributeName="errmsg" messagesType="error" />
		<t:messagePanel messagesAttributeName="infomsg" messagesType="info" />
		<t:messagePanel messagesAttributeName="successmsg" messagesType="success" />
		<spring:hasBindErrors name="userInfTmpForm">
			<form:form commandName="userInfTmpForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.UserInfoMaintenanceAdd"/></div>

<!-- body -->
<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/nsm03/02/init" method="post" modelAttribute="userInfTmpForm" class="form-horizontal">
		<form:hidden path="roleMode" value="${userInfTmpForm.roleMode }"/>
		<table class="tbl_search">
			<tr><td colspan="2">
				<form:input id="createorg" path="userInfTmp.createorg" type="hidden"/>
				<form:input id="userstatus" path="userInfTmp.userstatus" type="hidden"/>
			</td></tr>
	    	<tr>
				<td width="40%" class="label_td">
					<font color="red">*</font>
					<spring:message code="index.label.sm.UserId"/>
				</td>
				<td width="60%">
					<form:input id="userid" path="userid" type="text" class=".input-small" maxlength="20" onkeyup="numberStringFormat(this);" onbeforepaste="numberStringFormatCopy(this);" />
				</td>
			</tr>
			<tr>
				<td class="label_td">
				<font color="red">*</font>
				<spring:message code="index.label.sm.UserName"/></td>
				<td>
					<form:input id="username" path="username" type="text" class="input-xxlarge" maxlength="60"/>
				</td>
			<tr>
			

			<tr>
				<td class="label_td"><spring:message code="index.label.sm.UserStatus"/></td>
				<td>
					<t:codeValue id="userstatus" items="${CL_0012}" key="${userInfTmpForm.userInfTmp.userstatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.sm.CreateOrgName"/></td>
				<td>
					<table class="tbl_search">
		     			<tr>
		     				<td>
		     					<fieldset style="width: 100%;padding: 2px;">
	 								<table>
	 									<tr height="20px">
			 								<td><spring:message code="index.label.sm.CanChooseOrgidList"/></td>
			 								<td>&nbsp;</td>
			 								<td><spring:message code="index.label.sm.ChooseOrgidList"/></td>
			 								<td width="20px"></td>
								     		<%
						     					String roleMode = (String) request.getAttribute("RoleMode");
						     					if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG) || 
						     							roleMode.equals(CommonConst.ROLE_MODE_BOTH)) {
						     				%>
			 								<td id="roleOrgListDesc" style="width:150px;display: inline;">&nbsp;&nbsp;<spring:message code="index.label.sm.CanChooseRoleidList"/></td>
			 								<%} %>
			 							</tr>
				 						<tr>
							     			<td>		     				
												<select  name="alluserOrgInfArr" id="alluserOrgInfArr" style="width: 230px;margin: 2px;" size="11">
													<c:forEach var="dto" items="${AvailabledOrg }" varStatus="i">
						            					<option value="${dto.orgid}">${dto.orgid}-${dto.orgname}</option>
							         				</c:forEach>
												</select>	
																
								     		</td>
								     		<td align="center">
								     		<input id="addBtn" type="button" value="&gt" class="btn" onclick="addOrgId()"/><br/>
								     		<input id="delBtn" type="button" value="&lt" class="btn" onclick="delOrgId()"/>
								     		    <%
								     		    String roleID = (String) request.getAttribute("RoleID");
								     		    if(roleID.equals("Boper")){ %>
								     			<script type="text/javascript">
								     			  $("#addBtn").attr("disabled","true");
								     			  $("#delBtn").attr("disabled","true");
								     			</script>
								     			<%} %>
								     		</td>
								     		<td>	
								     		<select onclick="showRole()" multiple="multiple" name="userOrgInfArr" id="userOrgInfArr"style="width: 230px;margin: 2px;" size="11">
													
											</select>	
								     			
												
												<%if(roleID.equals("Boper")){   String orgId=(String) request.getAttribute("OrgID");
						                        String orgName= (String) request.getAttribute("OrgName");%>
						 
								     			<script type="text/javascript">
								     			  $("#userOrgInfArr").append('<option value="<%=orgId%>"><%=orgName%></option>');
								     			  $("#alluserOrgInfArr").attr("disabled","true");  
								     			</script>
								     			<%} %>
								     		</td>
								     		<td></td>
								     		<%
						     					if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG) || 
						     							roleMode.equals(CommonConst.ROLE_MODE_BOTH)) {
						     				%>
								     		<td >
								     			<div class="tbl_page_body" style="min-height: 185px;height: 185px; width:200px;">
								     				<table style="overflow-x: hidden; overflow-y: auto;">
								     					<tr><td>
												     		
												    		<div id="orgId_current" name="roleOrgListDiv" style="display: none;" >
													    		 <%
								     		    if(roleID.equals("Aoper")){ %>
																    		<input id="Badmin" class="Badmin" name="roleOrgArray" type="checkbox" value="Badmin"  onclick="check(this)"/>分行管理员
																    		<br>
																    		<input id="Boper" class="Boper" name="roleOrgArray" type="checkbox" value="Boper" onclick="check(this)"/>分行操作员
																    		
															    <%}if(roleID.equals("Boper")){%>
																    <script type="text/javascript">
								     			                    </script>
																    	    <input id="Cmaker" class="Cmaker" name="roleOrgArray" type="checkbox" value="Cmaker" onclick="check(this)" />录入员
																    		<br>
																    		<input id="Cchecker" class="Cchecker" name="roleOrgArray" type="checkbox" value="Cchecker" onclick="check(this)"/>审核员
																    	<%}	%>
												    		
												    		   <%  if(request.getAttribute("OwnerRole")!=null) {
												    		String[] ownerRole=(String[]) request.getAttribute("OwnerRole"); for(String info :ownerRole){ String role=info.split("_")[1]; %>
												    		        <script type="text/javascript">
												    		        $("#<%=role%>").attr("checked",true);
								     			                    </script>
												    		<%}} %>
												    		</div>
													    	
											     		</td></tr>
											     	</table>
								     			</div>
								     		</td>
											<%} %>
					     				</tr>
				     				</table>
					    		 </fieldset>
		     				</td>
		     			</tr>
					</table>
	   			</td>
	   		</tr>
	   		<%
				if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG) || roleMode.equals(CommonConst.ROLE_MODE_BOTH)) {
			%>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.UserStatus"/></td>
				<td>
					<div class="tbl_page_body" style="min-height: 185px;height: 185px; width:200px;">
					<table style="overflow-x: hidden; overflow-y: auto;"><tr><td>
					<%
	     				List<RoleInf> allRoleList = (List<RoleInf>) request.getAttribute("AvailabledRole");
	     				if (allRoleList != null && allRoleList.size() > 0) {
		    				for (int i = 0; i < allRoleList.size(); i++) {
		    					String roleId = StringUtil.trim(allRoleList.get(i).getRoleid());
		    					String roleName = allRoleList.get(i).getRolename();
		    					String[] selectedRoleArray = (String[]) request.getAttribute("SelectedRole");
		    					String check = "";
		    					if(selectedRoleArray != null && selectedRoleArray.length>0) {
		    						for (int j = 0 ; j < selectedRoleArray.length; j++) {
				    					if(selectedRoleArray[j].trim().equals(roleId)){
				    						check = "checked";
				    						break;
				    					}
				    				}
			    				}
		    					if(check.equals("checked")) { 
				    				%>
							    		<input id="<%=roleId %>" class="<%=roleId %>" name="roleArray" type="checkbox"   value="<%=roleId %>"checked="<%=check %>" /><%=roleName %>
							    		<br>
						    		<%	
		    					} else {
		    						%>
							    		<input id="<%=roleId %>" class="<%=roleId %>" name="roleArray" type="checkbox"   value="<%=roleId %>" /><%=roleName %>
							    		<br>
						    		<%	
		    					}
		    				}
		    			}
		     		%>
		     		</td></tr></table>
		     		</div>
				</td>
			<tr>
			<%} %>
	
	<tr>
	<td class="label_td"><font color="red">*</font><spring:message
						code="ftz.label.DEPARTMENT" />：</td>
				<td><form:select path="userInfTmp.department" id="department">
						<option value=""></option>
						<form:options items="${FTZ_DEPARTMENT}" />
					</form:select></td>
				<td></td>
				<td></td>
	</tr>
	
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="addMoreBtn" type="button" class="btn btn-primary" onclick="addGo()" value="<spring:message code="button.lable.AddMore"/>">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="add()" value="<spring:message code="button.lable.Submit"/>">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>