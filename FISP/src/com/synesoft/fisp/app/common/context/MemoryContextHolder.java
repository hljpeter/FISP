package com.synesoft.fisp.app.common.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.domain.model.IMemoryResource;
import com.synesoft.fisp.domain.model.OrgInf;

public final class MemoryContextHolder {

	public static enum MemoryResourceType {
		TipsBaCInf, // 银行代码表
		TipsNoCInf, // 节点代码表
		TipsAcctInf, // 账户信息表
		SYS_PARAM //系统参数
	};
	
	private static Map<MemoryResourceType, List<? extends IMemoryResource>> memRes = new HashMap<MemoryResourceType, List<? extends IMemoryResource>>();
	
	private MemoryContextHolder() {
	}
	
	/**
	 * 初始化内存资源
	 * @param memResType
	 * @param limr
	 */
	public static void initMemoryResource(MemoryResourceType memResType, List<? extends IMemoryResource> limr) {
		memRes.put(memResType, limr);
	}

	/**
	 * 销毁内存资源
	 * @param memResType
	 */
	public static void destoryMemoryResource(MemoryResourceType memResType) {
		memRes.remove(memResType);
	}

	/**
	 * 刷新内存资源
	 * @param memResType
	 * @param lmr
	 */
	public static void refreshMemoryResource(MemoryResourceType memResType, List<? extends IMemoryResource> lmr) {
		destoryMemoryResource(memResType);
		initMemoryResource(memResType, lmr);
	}

	/**
	 * 获取内存资源集合
	 * @param memResType
	 * @return
	 */
	public static List<? extends IMemoryResource> getMemoryResource(MemoryResourceType memResType) {
		return memRes.get(memResType);
	}

	/**
	 * 根据code获取内存资源
	 * @param memResType
	 * @param code
	 * @return
	 */
	public static IMemoryResource getMemoryResourceByCode(MemoryResourceType memResType, String code) {
		List<? extends IMemoryResource> listMemoryResource = getMemoryResource(memResType);
		for (int i = 0; i < listMemoryResource.size(); i++) {
			if (listMemoryResource.get(i).searchByCode(code.trim()))
				return listMemoryResource.get(i);
		}
		return null;
	}

	/**
	 * 根据name获取内存资源集合
	 * @param memResType
	 * @param name
	 * @return
	 */
	public static List<? extends IMemoryResource> getMemoryResourceByName(MemoryResourceType memResType, String name) {
		List<? extends IMemoryResource> listMemoryResource = getMemoryResource(memResType);
		List<IMemoryResource> rtnMemortResource = new ArrayList<IMemoryResource>();
		for (int i = 0; i < listMemoryResource.size(); i++) {
			if (listMemoryResource.get(i).searchByCode(name.trim()))
				rtnMemortResource.add(listMemoryResource.get(i));
		}
		return rtnMemortResource;
	}
	
	
	/**
	 * 根据code,name模糊匹配获取内存资源集合
	 * @param memResType
	 * @param code
	 * @param name
	 * @return
	 */
	public static List<? extends IMemoryResource> getMemoryResourceByFilter(MemoryResourceType memResType,String code, String name) {
		List<? extends IMemoryResource> listMemoryResource = getMemoryResource(memResType);
		List<IMemoryResource> rtnMemortResource = new ArrayList<IMemoryResource>();
		for (int i = 0; i < listMemoryResource.size(); i++) {
			if (code==null || "".equals(code.trim()) || listMemoryResource.get(i).searchByCode(code.trim())){
				if (name==null || "".equals(name.trim()) || listMemoryResource.get(i).searchByName(name.trim())){
					rtnMemortResource.add(listMemoryResource.get(i));
				}
			}
		}
		return rtnMemortResource;
	}

	/**
	 * 初始化机构信息
	 * @param listOrgInf
	 */
	public static void initOrgInf(List<OrgInf> listOrgInf) {
		if(listOrgInf != null && listOrgInf.size() > 0) {
			for (OrgInf orgInf : listOrgInf) {
				CommonUtil.map_orginf.put(orgInf.getOrgid().trim(), orgInf);
			}
		}
	}

}
