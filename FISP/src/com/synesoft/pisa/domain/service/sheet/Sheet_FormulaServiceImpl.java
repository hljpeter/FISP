package com.synesoft.pisa.domain.service.sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.MessagesUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.pisa.app.common.formulacheck.FormulaParser;
import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form;
import com.synesoft.pisa.domain.model.ExpSheetFormula;
import com.synesoft.pisa.domain.repository.sheet.Sheet_FormulaRepository;

@Service("sheet_FormulaService")
public class Sheet_FormulaServiceImpl implements Sheet_FormulaService{

	private static LogUtil log=new LogUtil(Sheet_FormulaServiceImpl.class);
	
	private MessagesUtil message = MessagesUtil.getInstance();
	
	@Autowired
	private Sheet_FormulaRepository sheet_FormulaRepository;
	
	@Autowired
	protected NumberService numberService;
	
	@Override
	public Page<ExpSheetFormula> transQueryExpSheetFormulaList(
			Pageable pageable, ExpSheetFormula expSheetFormula) {
		return sheet_FormulaRepository.queryList(pageable, expSheetFormula);
	}

	@Override
	public ExpSheetFormula transExpSheetFormula(ExpSheetFormula expSheetFormula) {
		return sheet_FormulaRepository.query(expSheetFormula);
	}

	@Override
	@Transactional
	public void transAdd(Sheet_Formula_Form form) {
		log.info("新增开始！");
		ResultMessages messages = ResultMessages.error();
		String formula=StringUtil.trim(form.getFormulaArea());
		if(formula.endsWith("]")){
			ExpSheetFormula expSheetFormula=form.getExpSheetFormula();
			expSheetFormula.setSheetNo(form.getSheetNo());
			expSheetFormula.setFormulaName(form.getFormulaName());
			expSheetFormula.setFormulaArea(formula);
			//根据表单中的信息查询数据库中是否已经存在同一表单代码、同一指标代码、同一维度代码、同一公式的信息
			ExpSheetFormula old=sheet_FormulaRepository.query(expSheetFormula);
			if(old==null){
				expSheetFormula.setFormulaId(numberService.getSysIDSequence(20));
				FormulaParser fp=new FormulaParser();
				expSheetFormula.setFormula(fp.getFormulaStr(formula));
				expSheetFormula.setCreater(ContextConst.getCurrentUser().getUserid());
				expSheetFormula.setCreateTime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				if(sheet_FormulaRepository.insertExpSheetFormula(expSheetFormula)!=1){
					log.error("插入数据库表错误！");
					messages.add("e.pisa.4002");
					throw new BusinessException(messages);
				}else{
					//记录操作日志
					TlrLogPrint.tlrSysLogPrint("表单验证公式配置", CommonConst.DATA_LOG_OPERTYPE_ADD, "", getString(expSheetFormula));
				}
			}else{
				log.error("数据库表中已存在相同信息！");
				messages.add("e.pisa.4001");
				throw new BusinessException(messages);
			}
		}else{
			log.error("输入公式没有正常结束！");
			messages.add("e.pisa.4006");
			throw new BusinessException(messages);
		}
	}
	
	@Override
	@Transactional
	public void transUpdate(Sheet_Formula_Form form) {
		log.info("修改开始！");
		ResultMessages messages = ResultMessages.error();
		String formula=StringUtil.trim(form.getFormulaArea());
		if(formula.endsWith("]")){
			ExpSheetFormula expSheetFormula=new ExpSheetFormula();
			expSheetFormula.setFormulaId(form.getExpSheetFormula().getFormulaId());
			expSheetFormula.setUpdater(form.getExpSheetFormula().getUpdater());
			expSheetFormula.setUpdateTime(form.getExpSheetFormula().getUpdateTime());
			//根据公式ID及更新人更新时间去数据库中查询相应信息
			ExpSheetFormula old=sheet_FormulaRepository.query(expSheetFormula);
			if(old!=null){
				expSheetFormula=form.getExpSheetFormula();
				expSheetFormula.setSheetNo(form.getSheetNo());
				expSheetFormula.setFormulaName(form.getFormulaName());
				expSheetFormula.setFormulaArea(form.getFormulaArea());
				FormulaParser fp=new FormulaParser();
				expSheetFormula.setFormula(fp.getFormulaStr(formula));
				expSheetFormula.setUpdater(ContextConst.getCurrentUser().getUserid());
				expSheetFormula.setUpdateTime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				if(sheet_FormulaRepository.updateExpSheetFormula(expSheetFormula)!=1){
					log.error("更新数据库表错误！");
					messages.add("e.pisa.4004");
					throw new BusinessException(messages);
				}else{
					//记录操作日志
					TlrLogPrint.tlrSysLogPrint("表单验证公式配置", CommonConst.DATA_LOG_OPERTYPE_MODIFY, getString(old), getString(expSheetFormula));
				}
			}else{
				log.error("数据库表中不存在该信息！");
				messages.add("e.pisa.4003");
				throw new BusinessException(messages);
			}
		}else{
			log.error("输入公式没有正常结束！");
			messages.add("e.pisa.4006");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transDel(Sheet_Formula_Form form) {
		log.info("删除开始！");
		ResultMessages messages = ResultMessages.error();
		ExpSheetFormula expSheetFormula=form.getExpSheetFormula();
		//根据公式ID查询相应信息
		ExpSheetFormula old=sheet_FormulaRepository.query(expSheetFormula);
		if(old!=null){
			if(sheet_FormulaRepository.deleteExpSheetFormula(old)!=1){
				log.error("删除数据库表错误！");
				messages.add("e.pisa.4005");
				throw new BusinessException(messages);
			}else{
				//记录操作日志
				TlrLogPrint.tlrSysLogPrint("表单验证公式配置", CommonConst.DATA_LOG_OPERTYPE_DELETE, getString(old),"");
			}
		}else{
			log.error("数据库表中不存在该信息！");
			messages.add("e.pisa.4003");
			throw new BusinessException(messages);
		}
		
	}
	private String getString(ExpSheetFormula expSheetFormula) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("pisa.index.sheet.no"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getSheetNo())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.item.no"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getItemNo())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.item.name"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getItemName())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.dim.no"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getDimNo())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.dim.name"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getDimName())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.formula.name"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getFormulaName())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.formula"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getFormula())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("pisa.index.formula.comment"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(expSheetFormula.getFormulaDesc())
				.append(CommonConst.SEPARATE_TWO_FIELD);

		return sb.toString();
	}

}
