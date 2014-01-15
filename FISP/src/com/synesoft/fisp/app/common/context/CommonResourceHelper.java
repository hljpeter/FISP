package com.synesoft.fisp.app.common.context;

import java.util.List;

import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.domain.model.IMemoryResource;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.TipsAcctInf;
import com.synesoft.fisp.domain.model.TipsBaCInf;

public class CommonResourceHelper {

	
	/**
	 * 根据 银行行号 查询 银行代码信息
	 * @param ReckBankNo
	 * @return 银行代码信息
	 */
	public static TipsBaCInf BankCodeQuery(String ReckBankNo){
		IMemoryResource imr = MemoryContextHolder.getMemoryResourceByCode(MemoryResourceType.TipsBaCInf, ReckBankNo);
		if (imr != null)
			return (TipsBaCInf) imr;
		else 
			return null;
	}
	
	/**
	 * 根据账号代码  查询 账号信息
	 * @param AcctCode
	 * @return 账号信息
	 */
	public static TipsAcctInf AcctInfQuery(String AcctCode){
		IMemoryResource imr = MemoryContextHolder.getMemoryResourceByCode(MemoryResourceType.TipsAcctInf, AcctCode);
		if (imr != null)
			return (TipsAcctInf) imr;
		else 
			return null;
	}
	
	
	/**
	 * 根据机构Id获取机构名称
	 * @param orgid
	 * @return
	 */
	public static String getOrgNameById(String orgid) {
		//只显示机构名称，不显示机构号  modify by hannj 20130928
		//return getOrgNameById(orgid, " - ");
		return getOrgNameById(orgid, null);
	}
	
	/**
	 * 根据机构Id获取机构名称
	 * @param orgid
	 * @param seperator	机构
	 * @return
	 */
	public static String getOrgNameById(String orgid, String seperator) {
		String orgname = orgid;
		
		if(orgid != null && !"".equals(orgid)) {
			if(CommonUtil.map_orginf != null && CommonUtil.map_orginf.size() > 0) {
				OrgInf orginf = CommonUtil.map_orginf.get(orgid.trim());
				if(orginf != null) {
					if(seperator == null) {
						orgname = orginf.getOrgname().trim();
					} else {
						orgname += seperator + orginf.getOrgname().trim();
					}
				}
			}
		}
		
		return orgname;
	}
	
	/**
	 * 根据参数组号、参数号获取参数对象
	 * @param paramGroup
	 * @param paramCode
	 * @return
	 */
	public static SysParam getSysParam(String paramGroup, String paramCode) {
		List<? extends IMemoryResource> limr = MemoryContextHolder
				.getMemoryResourceByFilter(MemoryResourceType.SYS_PARAM,
						paramCode, paramGroup);
		if (limr != null && limr.size() > 0)
			return (SysParam) limr.get(0);
		else
			return null;
	}
	
	/**
	 * 根据参数组号获取参数组对象
	 * @param paramGroup
	 * @return
	 */
	public static List<SysParam> getSysParam(String paramGroup) {
		List<? extends IMemoryResource> limr = MemoryContextHolder
				.getMemoryResourceByFilter(MemoryResourceType.SYS_PARAM, null,
						paramGroup);
		if (limr != null && limr.size() > 0)
			return (List<SysParam>) limr;
		else
			return null;
	}

}
