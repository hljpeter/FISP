package com.synesoft.fisp.domain.service.dp;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.LoanAmountForm;
import com.synesoft.fisp.domain.model.DmLoanAmount;
import com.synesoft.fisp.domain.repository.dp.DmLoanAmountRepository;
import com.synesoft.fisp.domain.service.NumberService;

@Service("dmLoanAmountService")
public class DmLoanAmountServiceImpl implements DmLoanAmountService {

	@Autowired
	protected DmLoanAmountRepository loanAmountRepository;
	
	@Autowired
	protected NumberService numberService;
	
	@Override
	public DmLoanAmount transQueryDmLoanAmount(DmLoanAmount loanAmount) {
		return loanAmountRepository.query(loanAmount);
	}

	@Override
	public Page<DmLoanAmount> transQueryDmLoanAmountInputList(
			Pageable pageable,DmLoanAmount loanAmount) {
		return loanAmountRepository.queryInputList(pageable, loanAmount);
	}
	
	@Override
	public Page<DmLoanAmount> transQueryDmLoanAmountAuthList(
			Pageable pageable,DmLoanAmount loanAmount) {
		return loanAmountRepository.queryAuthList(pageable, loanAmount);
	}

	@Override
	@Transactional
	public void transUpdate(LoanAmountForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount.setWorkDate(form.getWorkdate().replace("-", ""));
		loanAmount.setCustomerType(form.getCustomerType());
		loanAmount.setLoanIndustryType(form.getLoanIndustryType());
		loanAmount.setLoanIouCode(form.getLoanIouCode());
		loanAmount.setProductType(form.getProductType());
		loanAmount.setLoanActualInvest(form.getLoanActualInvest());
		loanAmount.setLoanOriginationDate(form.getLoanOriginationDate().replace("-", ""));
		loanAmount.setLoanMaturityDate(form.getLoanMaturityDate().replace("-", ""));
		loanAmount.setCurrency(form.getCurrency());
		loanAmount.setLoanIouAmount(new BigDecimal(form.getLoanIouAmount().replace(",", "")));
		loanAmount.setInterestRateFix(form.getInterestRateFix());
		loanAmount.setInterestRate(new BigDecimal(form.getInterestRate()));
		loanAmount.setLoanGuaranteeKind(form.getLoanGuaranteeKind());
		loanAmount.setStatus(form.getStatus());
		loanAmount.setLoanRecoverySign(form.getLoanRecoverySign());
		if(form.getEndDate()!=null&&!form.getEndDate().equals("")){
			loanAmount.setEndDate(form.getEndDate().replace("-", ""));
		}
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(loanAmount.getRsv2()!=null&&!loanAmount.getRsv2().equals("")){
				sb.append(loanAmount.getRsv2());
				sb.append("\n");
				sb.append("补录用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";补录时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanAmount.setRsv2(sb.toString());
			}else{
				sb.append("补录用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";补录时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanAmount.setRsv2(sb.toString());
			}
		}
		loanAmount.setSinputStatus("2");
		int result=loanAmountRepository.update(loanAmount);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transDel(LoanAmountForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanAmount loanAmount=form.getLoanAmount();
		int result=loanAmountRepository.delete(loanAmount);
		if(1!=result){
			messages.add("e.dp.1002");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transAuth(LoanAmountForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount.setSinputStatus("3");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(loanAmount.getRsv2()!=null&&!loanAmount.getRsv2().equals("")){
				sb.append(loanAmount.getRsv2());
				sb.append("\n");
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanAmount.setRsv2(sb.toString());
			}else{
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanAmount.setRsv2(sb.toString());
			}
		}
		int result=loanAmountRepository.update(loanAmount);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transReject(LoanAmountForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount.setSinputStatus("4");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(loanAmount.getRsv2()!=null&&!loanAmount.getRsv2().equals("")){
				sb.append(loanAmount.getRsv2());
				sb.append("\n");
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanAmount.setRsv2(sb.toString());
			}else{
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanAmount.setRsv2(sb.toString());
			}
		}
		int result=loanAmountRepository.update(loanAmount);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transCancel(LoanAmountForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount.setSinputStatus("4");
		int result=loanAmountRepository.update(loanAmount);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transAdd(LoanAmountForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount.setWorkDate(form.getWorkdate().replace("-", ""));
		loanAmount.setCustomerType(form.getCustomerType());
		loanAmount.setLoanIndustryType(form.getLoanIndustryType());
		loanAmount.setLoanIouCode(form.getLoanIouCode());
		loanAmount.setProductType(form.getProductType());
		loanAmount.setLoanActualInvest(form.getLoanActualInvest());
		loanAmount.setLoanOriginationDate(form.getLoanOriginationDate().replace("-", ""));
		loanAmount.setLoanMaturityDate(form.getLoanMaturityDate().replace("-", ""));
		loanAmount.setCurrency(form.getCurrency());
		loanAmount.setLoanIouAmount(new BigDecimal(form.getLoanIouAmount().replace(",", "")));
		loanAmount.setInterestRateFix(form.getInterestRateFix());
		loanAmount.setInterestRate(new BigDecimal(form.getInterestRate()));
		loanAmount.setLoanGuaranteeKind(form.getLoanGuaranteeKind());
		loanAmount.setStatus(form.getStatus());
		loanAmount.setLoanRecoverySign(form.getLoanRecoverySign());
		if(form.getEndDate()!=null&&!form.getEndDate().equals("")){
			loanAmount.setEndDate(form.getEndDate().replace("-", ""));
		}
		loanAmount.setSinputStatus("2");
		loanAmount.setId(numberService.getSysIDSequence("0000", 20));
		int result=loanAmountRepository.insert(loanAmount);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
		
	}

	@Override
	public Page<DmLoanAmount> transQueryDmLoanAmountQueryList(
			Pageable pageable, DmLoanAmount loanAmount) {
		return loanAmountRepository.queryList(pageable, loanAmount);
	}
}
