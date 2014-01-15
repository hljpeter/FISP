package com.synesoft.fisp.app.sm.controller;





import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.app.sm.model.Sm_Date_UpdForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.sm.HolidayMaintainService;


/**
 * @author 李敬昌
 * @date 2013年11月14日 09:00:22
 * @version 1.0
 * @Description 节假日信息文字版修改
 * @System 
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */

@Controller
@RequestMapping("SM_Date_Upd")
public class SM_Date_UpdController {
	
	private static final Logger logger = LoggerFactory.getLogger(SM_Date_UpdController.class);

	@ModelAttribute
	public Sm_Date_UpdForm setDateForm() {
		return new Sm_Date_UpdForm();
	}
	

	/**
	 * 查询节假日明细
	 * @return
	 */
	@RequestMapping("Init")
	public String detailSearch(Sm_Date_UpdForm form,
			BindingResult result, Model model,HttpServletRequest request) {
		logger.info("update jsp...");					
		return "sm/SM_Date_Upd";
	}
	
	@RequestMapping("Upd")
	public String modify(Sm_Date_UpdForm form,
			BindingResult result, Model model,HttpServletRequest request) {
		logger.info("start modify ...");
		String dayList= form.getUpdList();
		
		dayList=dayList.replace("-", "");
		System.out.println(dayList);
		if (result.hasErrors()) {
			
			return "sm/SM_Date_Upd";
		}
		try {
			holidayService.transUpdate2(dayList);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			
			return "sm/SM_Date_Upd";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0002")));	
		
		
		
		/*记录日志*/
		StringBuffer afterData = new StringBuffer();
		afterData.append("如下更新[");
		afterData.append(dayList);
		afterData.append("]");		
		UserInf userInfo = ContextConst.getCurrentUser();
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		TlrLogPrint.tlrSysLogPrint("节假日维护", orgInfo.getOrgid().trim(), userInfo.getUserid(), 
				userInfo.getUsername(), "M", DateUtil.getNow("yyyyMMdd"), DateUtil.getNow("HHmmss"),"", afterData.toString());
		return "sm/SM_Date_Upd";
	}
	
	@Autowired
	private HolidayMaintainService holidayService;

}
