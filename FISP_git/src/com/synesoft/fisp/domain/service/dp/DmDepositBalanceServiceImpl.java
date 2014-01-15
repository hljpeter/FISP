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
import com.synesoft.fisp.app.dp.model.DepositBalanceForm;
import com.synesoft.fisp.domain.model.DmDepositBalance;
import com.synesoft.fisp.domain.repository.dp.DmDepositBalanceRepository;
import com.synesoft.fisp.domain.service.NumberService;

@Service("dmDepositBalanceService")
public class DmDepositBalanceServiceImpl implements DmDepositBalanceService {

	@Autowired
	protected DmDepositBalanceRepository depositBalanceRepository;
	
	@Autowired
	protected NumberService numberService;
	
	@Override
	public DmDepositBalance transQueryDmDepositBalance(DmDepositBalance depositBalance) {
		return depositBalanceRepository.query(depositBalance);
	}

	@Override
	public Page<DmDepositBalance> transQueryDmDepositBalanceInputList(
			Pageable pageable,DmDepositBalance depositBalance) {
		return depositBalanceRepository.queryInputList(pageable, depositBalance);
	}
	
	@Override
	public Page<DmDepositBalance> transQueryDmDepositBalanceAuthList(
			Pageable pageable,DmDepositBalance depositBalance) {
		return depositBalanceRepository.queryAuthList(pageable, depositBalance);
	}

	@Override
	@Transactional
	public void transUpdate(DepositBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance.setProductType(form.getProductType());
		depositBalance.setWorkDate(depositBalance.getWorkDate().replace("-", ""));
		depositBalance.setDepositAgreementSdate(form.getDepositAgreementSdate().replace("-", ""));
		depositBalance.setDepositAgreementEdate(form.getDepositAgreementEdate().replace("-", ""));
		depositBalance.setInterestRateFix(form.getInterestRateFix());
		depositBalance.setDepositBalance(new BigDecimal(form.getDepositBalanceDis().replace(",", "")));
		depositBalance.setInterestRate(new BigDecimal(form.getInterestRate()));
		depositBalance.setSinputStatus("2");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(depositBalance.getRsv2()!=null&&!depositBalance.getRsv2().equals("")){
				sb.append(depositBalance.getRsv2());
				sb.append("\n");
				sb.append("补录用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";补录时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				depositBalance.setRsv2(sb.toString());
			}else{
				sb.append("补录用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";补录时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				depositBalance.setRsv2(sb.toString());
			}
		}
		int result=depositBalanceRepository.update(depositBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transDel(DepositBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmDepositBalance depositBalance=form.getDepositBalance();
		int result=depositBalanceRepository.delete(depositBalance);
		if(1!=result){
			messages.add("e.dp.1002");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transAuth(DepositBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance.setSinputStatus("3");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(depositBalance.getRsv2()!=null&&!depositBalance.getRsv2().equals("")){
				sb.append(depositBalance.getRsv2());
				sb.append("\n");
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				depositBalance.setRsv2(sb.toString());
			}else{
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				depositBalance.setRsv2(sb.toString());
			}
		}
		int result=depositBalanceRepository.update(depositBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transReject(DepositBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance.setSinputStatus("4");
		StringBuffer sb=new StringBuffer();
		if(form.getComment()!=null&&!form.getComment().equals("")){
			if(depositBalance.getRsv2()!=null&&!depositBalance.getRsv2().equals("")){
				sb.append(depositBalance.getRsv2());
				sb.append("\n");
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				depositBalance.setRsv2(sb.toString());
			}else{
				sb.append("审核用户：");
				sb.append(ContextConst.getCurrentUser().getUserid());
				sb.append(";审核时间：");
				sb.append(DateUtil.getNow(DateUtil.LONG_DATE_FORMAT));
				sb.append(";备注：");
				sb.append(form.getComment());
				depositBalance.setRsv2(sb.toString());
			}
		}
		int result=depositBalanceRepository.update(depositBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transCancel(DepositBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance.setSinputStatus("4");
		int result=depositBalanceRepository.update(depositBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transAdd(DepositBalanceForm form) {
		ResultMessages messages = ResultMessages.error();
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance.setId(numberService.getSysIDSequence("0000", 20));
		depositBalance.setReferenceNo("SHA"+numberService.getSysIDSequence("", 3));
		if(depositBalance.getWorkDate()!=null&&!depositBalance.getWorkDate().equals("")){
			depositBalance.setWorkDate(depositBalance.getWorkDate().replace("-", ""));
		}
		if(depositBalance.getDepositAgreementSdate()!=null&&!depositBalance.getDepositAgreementSdate().equals("")){
			depositBalance.setDepositAgreementSdate(depositBalance.getDepositAgreementSdate().replace("-", ""));
		}
		if(depositBalance.getDepositAgreementEdate()!=null&&!depositBalance.getDepositAgreementEdate().equals("")){
			depositBalance.setDepositAgreementEdate(depositBalance.getDepositAgreementEdate().replace("-", ""));
		}
		depositBalance.setProductType(form.getProductType());
		depositBalance.setDepositBalance(new BigDecimal(form.getDepositBalanceDis().replace(",", "")));
		if(form.getFcurrDollar()!=null&&!form.getFcurrDollar().equals("")){
			depositBalance.setFcurrDollar(new BigDecimal(form.getFcurrDollar().replace(",", "")));
		}
		depositBalance.setCustomerNo(form.getCustomerNo());
		depositBalance.setDepositAccCode(form.getDepositAccCode());
		depositBalance.setDepositAgreementCode(form.getDepositAgreementCode());
		depositBalance.setInterestRateFix(form.getInterestRateFix());
		depositBalance.setInterestRate(new BigDecimal(form.getInterestRate()));
		depositBalance.setSinputStatus("2");
		int result=depositBalanceRepository.insert(depositBalance);
		if(1!=result){
			messages.add("e.dp.1001");
			throw new BusinessException(messages);
		}
	}

	@Override
	public Page<DmDepositBalance> transQueryDmDepositBalanceQueryList(
			Pageable pageable, DmDepositBalance depositBalance) {
		return depositBalanceRepository.queryList(pageable, depositBalance);
	}
}
