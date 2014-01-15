package com.synesoft.fisp.app.common.utils;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.TlrOperationLogService;

/**
 * 操作日志记录
 * @author michelle.wang
 *
 */
public class TlrLogPrint {
	private static TlrOperationLogService logPrt = (TlrOperationLogService) ContextUtil.getCtx()
			.getBean("tlrOperationLogService");

	/**
	 * 操作员系统维护操作日志记录
	 * @param funcId 模块ID
	 * @param branchId 机构号，从session中取得
	 * @param userId   用户ID，从session中取得
	 * @param userName 用户名称，从session中取得
	 * @param operType 操作类型，格式为:A-新增，M-修改，D-删除， C-审核，R-拒绝，S-授权
	 * @param operDate 操作时间，取当前系统8位日期
	 * @param operTime 操作时间，取当前系统6位时间
	 * @param beforeData  操作前内容（用于更新时）如:"机构ID：XXX1;机构名称：XXX1"
	 * @param afterData   操作后内容 （用于新增及更新时）如："机构ID:XXX2;机构名称：XXX2"
	 */
	public static void tlrSysLogPrint(String funcId, String branchId, String userId,
			String userName, String operType, String operDate,String operTime,
			String beforeData, String afterData) {
		logPrt.insertSysChgSysLog(funcId, branchId, userId, userName,
				operType, operDate,operTime, beforeData, afterData);
	}

	/**
	 * 操作员系统维护操作日志记录
	 * @param funcId 模块ID
	 * @param operType 操作类型，格式为:A-新增，M-修改，D-删除， C-审核，R-拒绝，S-授权
	 * @param beforeData  操作前内容（用于更新时）如:"机构ID：XXX1;机构名称：XXX1"
	 * @param afterData   操作后内容 （用于新增及更新时）如："机构ID:XXX2;机构名称：XXX2"
	 * @author yyw
	 */
	public static void tlrSysLogPrint(String funcId, String operType, 
			String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String date = DateUtil.getNow("yyyyMMdd");
		String time = DateUtil.getNow("HHmmss");
		logPrt.insertSysChgSysLog(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(),
				operType, date,time, beforeData, afterData);
	}
	
	/**
	 * 操作员业务操作日志记录
	 * @param funcId 模块ID
	 * @param branchId 机构号，从session中取得
	 * @param userId   用户ID，从session中取得
	 * @param userName 用户名称，从session中取得
	 * @param operType 操作类型，格式为:A-新增，M-修改，D-删除， C-审核，R-拒绝，S-授权
	 * @param operDate 操作时间，取当前系统8位日期
	 * @param operTime 操作时间，取当前系统6位时间
	 * @param beforeData  操作前内容（用于更新时）如："账户：XXX1;户名:XXX1"
	 * @param afterData   操作后内容 （用于新增及更新时）如:"账户XXX2;户名:XXX2"
	 */
	public static void tlrBizLogPrint(String funcId, String branchId, String userId,
			String userName, String operType,String operDate, String operTime,
			String beforeData, String afterData) {
		logPrt.insertSysChgBizLog(funcId, branchId, userId, userName,
				operType,operDate, operTime, beforeData, afterData);
	}

}
