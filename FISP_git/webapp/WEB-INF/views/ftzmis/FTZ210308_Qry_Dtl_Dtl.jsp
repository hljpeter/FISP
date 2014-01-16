<!-- 6.3.8　信用证保兑（出口应收）（210308） / 查询 - 交易页面 -->
<!-- title -->
<div class="page_title"><spring:message code="ftzmis.title.210308.qry.txn"/></div>
<!-- body -->
<div class="row">
	<form:form id="form" action="${pageContext.request.contextPath}" method="post" modelAttribute="FTZ210308Form" class="form-horizontal">
		<form:hidden path="ftzOffTxnDtl.msgId" id="msgId"/>
		<table class="tbl_search">
			<tr>
				<!-- 申报序号 -->
				<td class="label_td"><spring:message code="ftz.label.SEQ_NO"/>：</td>
				<td><form:input path="ftzOffTxnDtl.seqNo" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 申报日期 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SUBMIT_DATE"/>：</td>
				<td><form:input path="ftzOffTxnDtl.submitDate" type="text" class="input-large date" maxLength="10" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 所属机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.ACCORGCODE"/>：</td>
				<td colspan="3">
					<form:input id="accOrgCode" path="ftzOffTxnDtl.accOrgCode" class="input-large" maxLength="12" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg1()" value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
			</tr>
			<tr>	
				<!-- 金额 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.AMOUNT"/>：</td>
				<td><t:moneyFormat type="text" id="amount" name="ftzOffTxnDtl.amount" value="${FTZ210308Form.ftzOffTxnDtl.amount }" format="###,###,###,###.00" dot="true" readonly="true"/></td>
				
				<!-- 货币 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.CURRENCY"/>：</td>
				<td>
					<form:select path="ftzOffTxnDtl.currency" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${SYS_CURRENCY }" />
					</form:select>
				</td>
			</tr>
			<tr>	
				<!-- 境内外对手行代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.SWIFT_CODE"/>：</td>
				<td>
					<form:input id="swiftCode" path="ftzOffTxnDtl.swiftCode" class="input-large" maxLength="11" readonly="true"/>
					<input type="button" class="btn btn-small"  onclick="queryShowAllOrg3()"  value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
				
				<!-- 受益人名称 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.RECEIPTOR_ENT_NAME"/>：</td>
				<td><form:input path="ftzOffTxnDtl.accountName" type="text" class="input-xlarge" maxLength="128" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 受益人机构代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.RECEIPTOR_ENT_INST_CODE"/>：</td>
				<td>
					<form:input id="institutionCode" path="ftzOffTxnDtl.institutionCode" class="input-large" maxLength="12" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowAllOrg2()"  value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
			</tr>
			<tr>	
				<!-- 受益人国别代码 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.RECEIPTOR_COUNTRY_CODE"/>：</td>
				<td>
					<form:input id="countryCode" path="ftzOffTxnDtl.countryCode" class="input-large" maxLength="3" readonly="true"/>
					<input type="button" class="btn btn-small" onclick="queryShowSelNation()" value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
				
				<!-- 受益人国内地区码 -->
				<td class="label_td"><spring:message code="ftz.label.RECEIPTOR_DISITRICT_CODE"/>：</td>
				<td>
					 <form:select path="ftzOffTxnDtl.districtCode" id="districtCode" disabled="true">
						<form:option value=""></form:option>
						<form:options items="${FTZ_DISITRICT_CODE }" />
					</form:select>
					<input type="button" class="btn btn-small" onclick="queryShowSelReg()" value="<spring:message code="button.label.Search"/>" disabled="true">
				</td>
			</tr>
			<tr>	
				<!-- 保兑到期日 -->
				<td class="label_td"><font color="red">*</font><spring:message code="ftz.label.EXPIRATIONDATE"/>：</td>
				<td colspan="3"><form:input path="ftzOffTxnDtl.expirationDate" type="text" class="input-large date" maxLength="10" readonly="true"/></td>
			</tr>
			<tr><td colspan="4"><hr/></td><td>
			<tr>	
				<!-- 明细状态 -->
				<td class="label_td"><spring:message code="ftz.label.DTL_STATUS"/>：</td>
				<td colspan="3"><t:codeValue items="${FTZ_MSG_STATUS }" key="${FTZ210308Form.ftzOffTxnDtl.chkStatus }" type="text"  cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 录入用户 -->
				<td class="label_td"><spring:message code="ftz.label.MAK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.makUserId" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 录入时间 -->
				<td class="label_td"><spring:message code="ftz.label.MAK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210308Form.ftzOffTxnDtl.makDatetime }" format="datetime" name="ftzOffTxnDtl.makDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>	
				<!-- 审核用户 -->
				<td class="label_td"><spring:message code="ftz.label.CHK_USER_ID"/>：</td>
				<td><form:input path="ftzOffTxnDtl.chkUserId" type="text" class="input-large" readonly="true"/></td>
				
				<!-- 审核时间 -->
				<td class="label_td"><spring:message code="ftz.label.CHK_DATETIME"/>：</td>
				<td><t:dateTimeFormat type="text" value="${FTZ210308Form.ftzOffTxnDtl.chkDatetime }" format="datetime" name="ftzOffTxnDtl.chkDatetime" cssClass="input-large" readonly="true"/></td>
			</tr>
			<tr>
				<!-- 审核附言 -->	
				<td class="label_td"><spring:message code="ftz.label.CHK_ADD_WORD"/>：</td>
				<td colspan="3"><form:input path="ftzOffTxnDtl.chkAddWord" type="text" class="input-xxlarge" readonly="true"/></td>
			</tr>
	    </table>											
	</form:form>
	<p id="notice" class="text-error" style="display: none;"><spring:message code="i.ftzmis.210308.0006"/></p>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="text-align:center; margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<button id="cls" name="btn" class="btn btn-primary" onclick="javascript: window.close();"><spring:message code="ftz.label.CLOSE"/></button>
	</div>
</div>