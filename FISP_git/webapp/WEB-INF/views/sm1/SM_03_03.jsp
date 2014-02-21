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
			$("input:not(:button,:hidden)").prop("readonly", true);
			$("#confirmBtn").attr("disabled", "disabled");
			$("#userstatus").attr("disabled", "disabled");
			$("#alluserOrgInfArr").attr("disabled", "disabled");
			//$("#userOrgInfArr").attr("disabled", "disabled");
			$("#addBtn").attr("disabled", "disabled");
			$("#delBtn").attr("disabled", "disabled");
			$("#loginorg").attr("disabled", "disabled");
			var roleOrg = document.getElementsByName('roleOrgArray');
			for(var i=0;i<roleOrg.length;i++){
				//循环将选择角色的多选框不可以修改
				roleOrg[i].disabled = true;
			}
		}
	});
	//modify button
	function modify() {
		$("select[name='userOrgInfArr'] option").attr("selected",true);
		var form = document.getElementById("form");
		form.action = "${pageContext.request.contextPath}/nsm03/03/modify";
		var msg=$("#confirmMsg1").val()+$("#confirmBtn").val()+$("#confirmMsg2").val();
		if (confirm(msg)){
			form.submit();
		}else{
			return false;
		}
	}
	// 添加
	function addOrgId(){
		var cp=$("#alluserOrgInfArr option:selected").clone();
		$("#userOrgInfArr").append($("#alluserOrgInfArr option:selected"));
		$("#userOrgInfArr").find("option").click(function(){
			showRole(this.value);
		});
		$("#loginorg").append(cp);
	}
	// 移除
	function delOrgId(){ 
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
		$("#roleOrgList").css("display","inline");
		$("#roleOrgListDesc").css("display","inline");
		var roleOrgListDiv = getElementsByName("div","roleOrgListDiv");
		var orgId = $("#userOrgInfArr option:selected").val();
		for(var i=0;i<roleOrgListDiv.length;i++){//循环
			var divId = roleOrgListDiv[i].id;
			if($.trim(orgId)==$.trim(divId)){
				roleOrgListDiv[i].style.display = "inline";
			}else{
				roleOrgListDiv[i].style.display = "none";
			}
		}
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
		<spring:hasBindErrors name="userInfForm">
			<form:form commandName="userInfForm">
				<div class="alert alert-error">
					<form:errors path="*" cssStyle="color:red"></form:errors>
				</div>
			</form:form>
		</spring:hasBindErrors>
	</div>
	<br />
</div>
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.UserInfoMaintenanceModify"/></div>

<!-- body -->
<div class="row" style="margin-bottom: 20px;">
	<form:form id="form" action="${pageContext.request.contextPath}/nsm03/03/init" method="post" modelAttribute="userInfForm" class="form-horizontal">
		<table class="tbl_search">
			<tr><td colspan="2">
				<form:input id="createorg" path="userInf.createorg" type="hidden"/>
				<form:input id="userstatus" path="userInf.userstatus" type="hidden"/>
			</td></tr>
	    	<tr>
				<td width="40%" class="label_td"><spring:message code="index.label.sm.UserId"/></td>
				<td width="60%">
					<form:input id="userid" path="userInf.userid" type="text" class=".input-small" maxlength="20" readonly="true"/>
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
				<td class="label_td"><spring:message code="index.label.sm.CreateOrgName"/></td>
				<td>
					<t:codeValue items="${BM_1001}" key="${userInfForm.userInf.createorg}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.UserStatus"/></td>
				<td>
					<t:codeValue id="userstatus" items="${CL_0012}" key="${userInfForm.userInf.userstatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.sm.OrgidList"/></td>
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
												<select multiple="multiple" name="alluserOrgInfArr" id="alluserOrgInfArr" style="width: 230px;margin: 2px;" size="11">
													<c:forEach var="dto" items="${AvailabledOrg }" varStatus="i">
						            					<option value="${dto.orgid}">${dto.orgid}-${dto.orgname}</option>
							         				</c:forEach>
												</select>							
								     		</td>
								     		<td align="center">
								     			<input id="addBtn" type="button" value="&gt" class="btn" onclick="addOrgId()"/>
								     			<input id="delBtn" type="button" value="&lt" class="btn" onclick="delOrgId()"/>
								     		</td>
								     		<td>				
												<select multiple="multiple" onclick="showRole()" name="userOrgInfArr" id="userOrgInfArr"style="width: 230px;margin: 2px;" size="11">
													<c:forEach var="dto2" items="${SelectedOrg }" varStatus="i">
						            					<option value="${dto2.orgid}">${dto2.orgid}-${dto2.orgname}</option>
							         				</c:forEach>
												</select>
								     		</td>
								     		<td></td>
								     		<%
						     					if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG) || 
						     							roleMode.equals(CommonConst.ROLE_MODE_BOTH)) {
						     				%>
								     		<td>
								     			<div class="tbl_page_body" style="min-height: 185px;height: 185px; width:200px;">
								     				<table style="overflow-x: hidden; overflow-y: auto;">
								     					<tr><td>
												     		<%
												     			HashMap<String, ArrayList<RoleInf>> roleOrgAllMap = (HashMap<String, ArrayList<RoleInf>>)request.getAttribute("AllOrgRoleMap");
												     			List<String> userSelectRoles = (List)request.getAttribute("SelectedOrgRole");
													     		Set<String> set = roleOrgAllMap.keySet();
													    		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
													    			String orgId_current = (String) iterator.next();
												    		%>
												    		<div id="<%=orgId_current %>" name="roleOrgListDiv" style="display: none;" >
													    	<%	
												    			ArrayList<RoleInf> list_role = roleOrgAllMap.get(orgId_current);
												    			for (RoleInf roleInf : list_role) {
												    				String roleId = StringUtil.trim(roleInf.getRoleid());
												    				String roleName = StringUtil.trim(roleInf.getRolename());
												    				String value = orgId_current + "_" + roleId;
												    				String orgId = orgId_current;
												    				String check = "";
												    				if(userSelectRoles.size() > 0){
													    				for (int i = 0 ; i < userSelectRoles.size(); i++) {
													    					if (value.equals(userSelectRoles.get(i))) {
													    						check = "checked";
													    						break;
													    					}
													    				}
												    				}
												    				if(check.equals("checked")){ 
												    					%>
																    		<input id="<%=orgId %>" class="<%=orgId %>" name="roleOrgArray" type="checkbox" value="<%=value %>" checked="<%=check %>" /><%=roleName %>
																    		<br>
															    		<%		
												    				}else{
													    				%>
																    		<input id="roleList<%=orgId %>" class="<%=orgId %>" name="roleOrgArray" type="checkbox" value="<%=value %>"  /><%=roleName %>
																    		<br>
																    	<%			
												    				}
												    			}
												    		%>
												    		</div>
													    	<%} %>
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
				<td class="label_td"><font color="red">*</font><spring:message code="index.label.sm.LoginOrg"/></td>
				<td>
					<select id="loginorg" name="loginorg" >
						<c:forEach var="dto2" items="${SelectedOrg }" varStatus="i">
							<c:if test="${dto2.orgid eq userInfForm.loginorg }">
          						<option selected="selected" value="${dto2.orgid}">${dto2.orgid}-${dto2.orgname}</option>
          					</c:if>
          					<c:if test="${dto2.orgid ne userInfForm.loginorg }">
          						<option value="${dto2.orgid}">${dto2.orgid}-${dto2.orgname}</option>
          					</c:if>
        				</c:forEach>
					</select>
				</td>
	    	</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input id="confirmBtn" type="button" class="btn btn-primary" onclick="modify()" value="<spring:message code="button.lable.Submit"/>">
 		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>