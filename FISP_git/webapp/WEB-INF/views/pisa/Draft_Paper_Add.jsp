<!-- title -->
<div class="page_title">纸质商业汇票（承兑/付款）新增</div>

<div class="row" style="margin-bottom: 40px;">
	<form:form id="form" action="${pageContext.request.contextPath}/" method="post" modelAttribute="draft_Paper_Form" class="form-horizontal">
		<div  class="tbl_search" style="padding: 10px;">
			<table class="tbl_search">
				<t:messagePanel>基础信息：</t:messagePanel>
		    	<tr>
		    		<td class="label_td">机构：</td>
					<td>
						<t:codeValue items="${BM_1001}" key="${draft_Paper_Form.bizDraftAcptList.instId}" type="text" cssClass=".input-small"  readonly="true"/>
					</td>
					<td class="label_td">汇票号码：</td>
					<td>
						<form:input path="bizDraftAcptList.vocNo" type="text" class=".input-small"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">出票日期：</td>
					<td>
						<form:input path="bizDraftAcptList.startDate" id="startDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
					</td>
					<td class="label_td">票据种类：</td>
					<td>
						<form:select path="bizDraftAcptList.draftType" class=".input-small">
							<form:options items="${BM_1001}" />
						</form:select>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">票据到期日期：</td>
					<td>
						<form:input path="bizDraftAcptList.endDate" id="endDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
					</td>
					<td class="label_td">票据金额：</td>
					<td>
						<t:moneyFormat value="${draft_Paper_Form.bizDraftAcptList.draftAmt}" type="text" name="draftAmt"/>
					</td>
				</tr>
				<tr>
					<td class="label_td">付款期限：</td>
					<td>
						<form:input path="bizDraftAcptList.draftTerm" type="text" class=".input-small"  readonly="true"/>
					</td>
					<td class="label_td">票据状态：</td>
					<td>
						<form:select path="bizDraftAcptList.draftSts" class=".input-small">
							<form:options items="${BM_1001}" />
						</form:select>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">兑付/拒付日期：</td>
					<td>
						<form:input path="bizDraftAcptList.payDate" id="payDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
					</td>
					<td class="label_td">出票人所属行业：</td>
					<td>
						<form:select path="bizDraftAcptList.payerBizType" class=".input-small">
							<form:options items="${BM_1001}" />
						</form:select>
					</td>
				</tr>
			</table>
			<table class="tbl_search">
			<t:messagePanel>付款人信息：</t:messagePanel>
				<tr>
		    		<td class="label_td">付款人账号：</td>
					<td>
						<form:input path="bizDraftAcptList.payerActno" type="text" class=".input-small"/><button>查询</button>
					</td>
		    		<td class="label_td">付款人名称：</td>
					<td>
						<form:input path="bizDraftAcptList.payerName" type="text" class=".input-small"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">付款人开户行号：</td>
					<td>
						<form:input path="bizDraftAcptList.payerBankCode" type="text" class=".input-small"/>
					</td>
		    		<td class="label_td">付款人开户行名：</td>
					<td>
						<form:input path="bizDraftAcptList.payerBankName" type="text" class=".input-small"/>
					</td>
				</tr>
			</table>
			<table class="tbl_search">
				<t:messagePanel>收款人信息：</t:messagePanel>
				<tr>
		    		<td class="label_td">收款人账号：</td>
					<td>
						<form:input path="bizDraftAcptList.payeeActno" type="text" class=".input-small"/><button>查询</button>
					</td>
		    		<td class="label_td">收款人名称：</td>
					<td>
						<form:input path="bizDraftAcptList.payeeName" type="text" class=".input-small"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">收款人开户行号：</td>
					<td>
						<form:input path="bizDraftAcptList.payeeBankCode" type="text" class=".input-small"/>
					</td>
		    		<td class="label_td">收款人开户行名：</td>
					<td>
						<form:input path="bizDraftAcptList.payeeBankName" type="text" class=".input-small"/>
					</td>
				</tr>
			</table>
			<table class="tbl_search">
				<t:messagePanel>承兑人信息：</t:messagePanel>
				<tr><td colspan="4"></td></tr>
				<tr>
					<td class="label_td">承兑协议编号：</td>
					<td>
						<form:input path="bizDraftAcptList.agrmtNo" type="text" class=".input-small"/>
					</td>
					<td class="label_td">承兑日期：</td>
					<td>
						<form:input path="bizDraftAcptList.acptDate" id="acptDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
					</td>
				</tr>
				<tr>
		    		<td class="label_td">承兑人账号：</td>
					<td>
						<form:input path="bizDraftAcptList.acptActno" type="text" class=".input-small"/><button>查询</button>
					</td>
		    		<td class="label_td">承兑人名称：</td>
					<td>
						<form:input path="bizDraftAcptList.acptName" type="text" class=".input-small"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">承兑人开户行号：</td>
					<td>
						<form:input path="bizDraftAcptList.acptBankCode" type="text" class=".input-small"/>
					</td>
		    		<td class="label_td">承兑人开户行名：</td>
					<td>
						<form:input path="bizDraftAcptList.acptBankName" type="text" class=".input-small"/>
					</td>
				</tr>
			</table>
			<table class="tbl_search">
				<t:messagePanel>其他信息：</t:messagePanel>
				<tr><td colspan="4"></td></tr>
				<tr>
		    		<td class="label_td">逾期垫款金额：</td>
					<td>
						<t:moneyFormat value="${draft_Paper_Form.bizDraftAcptList.advAmt}" type="text" name="advAmt"/>
					</td>
		    		<td class="label_td">逾期垫款核销金额：</td>
					<td>
						<t:moneyFormat value="${draft_Paper_Form.bizDraftAcptList.woffAmt}" type="text" name="woffAmt"/>
					</td>
				</tr>
				<tr>
		    		<td class="label_td">逾期垫款日期：</td>
					<td>
						<form:input path="bizDraftAcptList.advDate" id="advDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
					</td>
		    		<td class="label_td">逾期垫款核销日期：</td>
					<td>
						<form:input path="bizDraftAcptList.woffDate" id="woffDate" type="text" maxlength="8" class="WdateInput"
					 onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'});" />
					</td>
				</tr>
				<tr>
		    		<td class="label_td">附言：</td>
					<td colspan="3">
						<form:textarea path="remark" class="span9" rows="7"/>
					</td>
				</tr>
	    	</table>
	    </div>											
	</form:form>
</div>
<div class="row">
	<div class="navbar navbar-fixed-bottom text-right" id="footer" style="margin-bottom:0px; line-height:30px; background-color: #eee; opacity:0.9;">
		<input type="button" class="btn btn-primary" onclick="javascript:window.close();" value="<spring:message code="button.lable.close"/>">
	</div>
</div>
