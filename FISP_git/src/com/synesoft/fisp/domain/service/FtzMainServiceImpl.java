package com.synesoft.fisp.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.constants.MainBizMapping;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.domain.model.MainParam;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.repository.MainRepository;
import com.synesoft.ftzmis.app.common.constants.CommonConst;

@Service("ftzMainService")
public class FtzMainServiceImpl extends MainServiceImpl {
	private final static String GEN_MSG_MENU_ID = "FTZ_QRY_2106";
	private final static String GEN_MSG_UNDO = "GEN_MSG_UNDO";
	private final static String ACT_MSTR_UNDO = "ACT_MSTR_UNDO";
	@Autowired
	protected MainRepository mainRepository;
	@Autowired
	private ProcCommonService procCommonService;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.MainService#getImportantTODO()
	 */
	@Override
	public List<MainParam> getImportantTODO() {
		// 查询待生产报文
		String menuId = null;
		String[] menus = ContextConst.getUserMenuList();
		for (int i = 0; i < menus.length; i++) {
			if (GEN_MSG_MENU_ID.equals(menus[i])) {
				menuId = menus[i];
				break;
			}
		}
		
		List<MainParam> genMsgList = new ArrayList<MainParam>();
		if (null != menuId) {
			int ret = mainRepository.getBatchToMsg();
			if (ret != 0) {
				MainParam param = new MainParam();
				param.setTabName(GEN_MSG_UNDO);
				param.setCnt(ret);
				genMsgList.add(param);
			}
		}
		
		// 查询报文发送失败或待处理
		List<MainParam> msgList = mainRepository.getMsg();
		List<MainParam> resultList = mergeList(genMsgList, msgList);

		// 查询账户信息
		int ret = mainRepository.getActMstr(procCommonService.queryWorkDate());
		if (ret == 0) {
			MainParam param2 = new MainParam();
			param2.setTabName(ACT_MSTR_UNDO);
			param2.setFlag(DateUtil.formatStringToDatePattern(procCommonService.queryWorkDate()));
			resultList.add(param2);
		}
		
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.MainServiceImpl#getBizGeneralTODO()
	 */
	@Override
	protected List<MainParam> getBizGeneralTODO() {
		List<String> msgNoList = getMsgNos();
		List<String> branchIdList = getBranchs();
		List<String> msgStatusList = new ArrayList<String>();
		msgStatusList.add(CommonConst.FTZ_MSG_STATUS_INPUTING);
		
		// 查询正在录入状态下的报文数量
		MainParam param = new MainParam();
		param.setMsgStatusList(msgStatusList);
		param.setBranchIdList(branchIdList);
		param.setFlag(CommonConst.FTZ_MSG_STATUS_INPUTING);
		param.setMsgNoList(msgNoList);
		List<MainParam> inputList = mainRepository.getBizGeneralTODO(param);
		if (null == inputList || inputList.isEmpty()) {
			inputList = new ArrayList<MainParam>();
		}

		// 查询审核状态下的报文数量
		msgStatusList.clear();
		msgStatusList.add(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		msgStatusList.add(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		param.setMsgStatusList(msgStatusList);
		param.setFlag(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		List<MainParam> authList = mainRepository.getBizGeneralTODO(param);
		
		return mergeList(inputList, authList);
	}

	// 查询当前用户可用功能对应的MSG_NO
	private List<String> getMsgNos() {
		List<String> msgNoList = new ArrayList<String>();
		String[] menus = ContextConst.getUserMenuList();
		for (int i = 0; i < menus.length; i++) {
			if (MainBizMapping.checkFlag(MainBizMapping.isSelectTODO(menus[i]))) {
				msgNoList.add(MainBizMapping.getMsgNo(menus[i]));
			}
		}
		return msgNoList;
	}

	// 查询当前用户可用机构
	private List<String> getBranchs() {
		List<String> branchIdList = new ArrayList<String>();
		List<UserOrgInf> list = ContextConst.getUserOrgList();
		for (int i = 0; i < list.size(); i++) {
			UserOrgInf orgInf = list.get(i);
			branchIdList.add(orgInf.getOrgid());
		}
		return branchIdList;
	}
	
}
