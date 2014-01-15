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
import com.synesoft.fisp.app.dp.model.LoanBalanceForm;
import com.synesoft.fisp.domain.model.DmLoanBalance;
import com.synesoft.fisp.domain.repository.dp.DmLoanBalanceRepository;
import com.synesoft.fisp.domain.service.NumberService;

@Service("dmLoanBalanceService")
public class DmLoanBalanceServiceImpl implements DmLoanBalanceService {

	@Autowired
	protected DmLoanBalanceRepository loanBalanceRepository;
	
	@Autowired
	protected NumberService numberService;
	
	@Override
	public DmLoanBalance transQueryDmLoanBalance(DmLoanBalance loanBalance) {
		return loanBalanceRepository.query(loanBalance);
	}

	@Override
	public Page<DmLoanBalance> transQueryDmLoanBalanceInputList(
			Pageable pageable,DmLoanBalance loanBalance) {
		return loanBalanceRepository.queryInputList(pageable, loanBalance);
	}
	
	@Override
	public Page<DmLoanBalance> transQueryDmLoanBalanceAuthList(
			Pageable pageable,DmLoanBalance loanBalance) {
		return loanBalanceRepository.queryAuthList(pageable, loanBalance);
	}

	@Override
	@Transactional
	public void transUpdate(LoanBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance.setWorkDate(form.getWorkdate().replace("-", ""));
		loanBalance.setCustomerType(form.getCustomerType());
		loanBalance.setLoanIndustryType(form.getLoanIndustryType());
		loanBalance.setLoanIouCode(form.getLoanIouCode());
		loanBalance.setProductType(form.getProductType());
		loanBalance.setLoanActualInvest(form.getLoanActualInvest());
		loanBalance.setLoanOriginationDate(form.getLoanOriginationDate().replace("-", ""));
		loanBalance.setLoanMaturityDate(form.getLoanMaturityDate().replace("-", ""));
		loanBalance.setCurrency(form.getCurrency());
		loanBalance.setLoanIouBalance(new BigDecimal(form.getLoanIouBalance().replace(",", "")));
		loanBalance.setInterestRateFix(form.getInterestRateFix());
		loanBalance.setInterestRate(new BigDecimal(form.getInterestRate()));
		loanBalance.setLoanGuaranteeKind(form.getLoanGuaranteeKind());
		loanBalance.setStatus(form.getStatus());
		if(form.getExtensionEdate()!=null&&!form.getExtensionEdate().equals("")){
			loanBalance.setExtensionEdate(form.getExtensionEdate().replace("-", ""));
		}
		loanBalance.setLoanQuality(form.getLoanQuality());
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(loanBalance.getRsv2()!=null&&!loanBalance.getRsv2().equals("")){
				sb.append(loanBalance.getRsv2());
				sb.append("\n");
				sb.append("补录用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";补录时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanBalance.setRsv2(sb.toString());
			}else{
				sb.append("补录用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";补录时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanBalance.setRsv2(sb.toString());
			}
		}
		loanBalance.setSinputStatus("2");
		int result=loanBalanceRepository.update(loanBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transDel(LoanBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanBalance loanBalance=form.getLoanBalance();
		int result=loanBalanceRepository.delete(loanBalance);
		if(1!=result){
			messages.add("e.dp.1002");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transAuth(LoanBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance.setSinputStatus("3");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(loanBalance.getRsv2()!=null&&!loanBalance.getRsv2().equals("")){
				sb.append(loanBalance.getRsv2());
				sb.append("\n");
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanBalance.setRsv2(sb.toString());
			}else{
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanBalance.setRsv2(sb.toString());
			}
		}
		int result=loanBalanceRepository.update(loanBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transReject(LoanBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance.setSinputStatus("4");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(loanBalance.getRsv2()!=null&&!loanBalance.getRsv2().equals("")){
				sb.append(loanBalance.getRsv2());
				sb.append("\n");
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanBalance.setRsv2(sb.toString());
			}else{
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				loanBalance.setRsv2(sb.toString());
			}
		}
		int result=loanBalanceRepository.update(loanBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transCancel(LoanBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance.setSinputStatus("4");
		int result=loanBalanceRepository.update(loanBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transAdd(LoanBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance.setId(numberService.getSysIDSequence("0000", 20));
		loanBalance.setReferenceNo("SHA"+numberService.getSysIDSequence("", 3));
		loanBalance.setWorkDate(form.getWorkdate().replace("-", ""));
		loanBalance.setCustomerType(form.getCustomerType());
		loanBalance.setLoanIndustryType(form.getLoanIndustryType());
		loanBalance.setLoanIouCode(form.getLoanIouCode());
		loanBalance.setProductType(form.getProductType());
		loanBalance.setLoanActualInvest(form.getLoanActualInvest());
		loanBalance.setLoanOriginationDate(form.getLoanOriginationDate().replace("-", ""));
		loanBalance.setLoanMaturityDate(form.getLoanMaturityDate().replace("-", ""));
		loanBalance.setCurrency(form.getCurrency());
		loanBalance.setLoanIouBalance(new BigDecimal(form.getLoanIouBalance().replace(",", "")));
		loanBalance.setInterestRateFix(form.getInterestRateFix());
		loanBalance.setInterestRate(new BigDecimal(form.getInterestRate()));
		loanBalance.setLoanGuaranteeKind(form.getLoanGuaranteeKind());
		loanBalance.setStatus(form.getStatus());
		if(form.getExtensionEdate()!=null&&!form.getExtensionEdate().equals("")){
			loanBalance.setExtensionEdate(form.getExtensionEdate().replace("-", ""));
		}
		loanBalance.setLoanQuality(form.getLoanQuality());
		loanBalance.setSinputStatus("2");
		int result=loanBalanceRepository.insert(loanBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	public Page<DmLoanBalance> transQueryDmLoanBalanceQueryList(
			Pageable pageable, DmLoanBalance loanBalance) {
		return loanBalanceRepository.queryList(pageable, loanBalance);
	}
}
