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
			$("#addBtn").attr("disabled", "disabled");
			$("#delBtn").attr("disabled", "disabled");
			$("#loginorg").attr("disabled", "disabled");
			
		}
		var roleOrg = document.getElementsByName("roleOrgArray");
		for(var i=0;i<roleOrg.length;i++){
			//循环将选择角色的多选框不可以修改
			roleOrg[i].disabled = true;
		}
	});
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
<!-- title -->
<div class="page_title"><spring:message code="index.label.sm.UserInfoMaintenanceDetailSearch"/></div>
<!-- body -->
<div class="row" style="margin-bottom: 40px;">
	<form:form id="form_01DetailSearch" action="${pageContext.request.contextPath}/sm02/05/detailSearch_01" method="post" modelAttribute="userInfForm" class="form-horizontal">
		<table class="tbl_search">
	    	<tr>
				<td width="40%" class="label_td"><spring:message code="index.label.sm.UserId"/>
				<td width="20%">
					<form:input id="userid" path="userInf.userid" type="text" class=".input-small" readonly="true"/>
				</td>
				<td width="15%" class="label_td"><spring:message code="index.label.sm.OperationStatus"/></td>
				<td width="25%">
					<t:codeValue items="${CL_0003}" key="${userInfForm.userInf.optstatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td width="40%" class="label_td"><spring:message code="index.label.sm.UserName"/>
				<td colspan="3">
					<form:input id="username" path="userInf.username" type="text" class="input-xxlarge" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.CreateOrgName"/>
				<td colspan="3">
					<t:codeValue items="${BM_1001}" key="${userInfForm.userInf.createorg}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.LoginOrg"/>
				<td colspan="3">
					<t:codeValue items="${BM_1001}" key="${userInfForm.userInf.loginorg}" type="text" cssClass=".input-small" readonly="true"/>
				</td>	
			</tr>	
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.UserStatus"/>
				</td>
				<td colspan="3">
					<t:codeValue items="${CL_0012}" key="${userInfForm.userInf.userstatus}" type="text" cssClass=".input-small" readonly="true"/>
				</td>
			<tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.OrgidList"/></td>
				<td colspan="3">
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
												<select multiple="multiple" name="alluserOrgInfArr" disabled="disabled" id="alluserOrgInfArr" style="width: 250px;margin: 2px;"size="11">
													<c:forEach var="dto" items="${AvailabledOrg }" varStatus="i">
						            					<option value="${dto.orgid}">${dto.orgid}-${dto.orgname}</option>
							         				</c:forEach>
												</select>							
								     		</td>
								     		<td align="center">
								     			<input type="button" value="&gt" class="btn" onclick=""/>
								     			<input type="button" value="&lt" class="btn" onclick=""/>
								     		</td>
								     		<td>				
												<select onclick="showRole()" multiple="multiple" name="userOrgInfArr" id="userOrgInfArr" style="width: 250px;margin: 2px;"size="11">
													<c:forEach var="dto2" items="${SelectedOrg }" varStatus="i">
						            					<option  value="${dto2.orgid}">${dto2.orgid}-${dto2.orgname}</option>
							         				</c:forEach>
												</select>
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
			<tr>
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
							    		<input id="<%=roleId %>" class="<%=roleId %>" name="roleArray" type="checkbox"   value="<%=roleId %>"checked="<%=check %>" disabled="disabled"/><%=roleName %>
							    		<br>
						    		<%	
		    					} else {
		    						%>
							    		<input id="<%=roleId %>" class="<%=roleId %>" name="roleArray" type="checkbox"   value="<%=roleId %>" disabled="disabled"/><%=roleName %>
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
				<td class="label_td"><spring:message code="index.label.sm.Creater"/></td>
				<td>
					<form:input path="userInf.creater" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CreateTime"/>
				<td>
					<t:dateTimeFormat type="text" value="${userInfForm.userInf.createtime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.LastOperator"/>
				<td>
					<form:input path="userInf.lastoperator" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.LastOptTime"/>
				<td>
					<t:dateTimeFormat type="text" value="${userInfForm.userInf.lastopttime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
			<tr>
				<td class="label_td"><spring:message code="index.label.sm.Checker"/>
				<td>
					<form:input path="userInf.checker" type="text" class=".input-small"  readonly="true"/>
				</td>
				<td class="label_td"><spring:message code="index.label.sm.CheckTime"/>
				<td>
					<t:dateTimeFormat type="text" value="${userInfForm.userInf.checktime }" readonly="true" cssClass=".input-small" format="datetime"/>
				</td>
			</tr>
	    </table>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button type="button" class="btn btn-primary" onclick="javascript:window.close();"><spring:message code="button.lable.close"/></button>
	</div>
</div>