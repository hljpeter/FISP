package com.synesoft.fisp.domain.service.sm;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.utils.FuncTreeNode;
import com.synesoft.fisp.app.common.utils.JsonUtils;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.SysFuncInfo;
import com.synesoft.fisp.domain.model.SysRoleFunc;
import com.synesoft.fisp.domain.model.SysRoleFuncTmp;
import com.synesoft.fisp.domain.repository.sm.MenuFuncRepository;
import com.synesoft.fisp.domain.repository.sm.SysFuncInfoRepository;
import com.synesoft.fisp.domain.repository.sm.SysRoleFuncReponsitory;
import com.synesoft.fisp.domain.repository.sm.SysRoleFuncTmpReponsitory;

/**
 * @author zhongHubo
 * @date 2013年7月22日 14:19:43
 * @version 1.0
 * @Description 菜单ServiceImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("menuFuncService")
public class MenuFuncServiceImpl implements MenuFuncService {

	/**
	 * 查询菜单列表
	 * 
	 * @param menuFunc
	 * @return
	 */
	public String queryList(String menuId) {

//		// 获取sys_func_info中所有的菜单
//		String funcIds = "";
//		SysRoleFunc sysRoleFuncTemp = new SysRoleFunc();
//		sysRoleFuncTemp.setRoleId(StringUtil.trim(roleId));
//		// 获取用户拥有的所有功能编号
//		List<SysRoleFunc> list_sysrolefunc = sysRoleFuncReponsitory
//				.querySysRoleFunc(sysRoleFuncTemp);
//		if (list_sysrolefunc.size() > 0) {
//			for (SysRoleFunc sysRoleFunc : list_sysrolefunc) {
//				if (!funcIds.contains("'" + sysRoleFunc.getFuncId() + "'")) {
//					funcIds = funcIds + "'"
//							+ StringUtil.trim(sysRoleFunc.getFuncId()) + "'"
//							+ ",";
//				}
//			}
//		}
		// 获取所有一级菜单
		List<SysFuncInfo> firstMenuList = sysFuncInfoRepository
				.queryAllFirMenus();
		// 申明存放菜单信息的list
		List<FuncTreeNode> nlist = new ArrayList<FuncTreeNode>();
		String menuStr = null;
		if (firstMenuList != null && firstMenuList.size() > 0) {
			for (SysFuncInfo sysFuncInfo : firstMenuList) {
				// 申明存放一级菜单的节点
				FuncTreeNode firstNode = new FuncTreeNode(
						sysFuncInfo.getFuncDesc(), sysFuncInfo.getFuncId(),
						sysFuncInfo.getFuncId(), false, false);
				if (menuId.contains(sysFuncInfo.getFuncId())) {
					firstNode.setChecked(true);
				}
				firstNode = iterationNode(sysFuncInfo, firstNode, menuId);
				nlist.add(firstNode);
			}
		}

		// // 获取所有被选中的菜单Id
		// String[] menus_selected = menuId.split(CommonConst.UNSEPERATOR);
		// // 申明存放所有被选中的菜单Id的Map
		// Map<String, Boolean> map_selected = new HashMap<String, Boolean>();
		// if(menus_selected != null) {
		// int length = menus_selected.length;
		// for (int i = 0; i < length; i++) {
		// map_selected.put(menus_selected[i].trim(), true);
		// }
		// }

		// MenuFunc menuFunc = new MenuFunc();
		// // 菜单汇总
		// String menuStr = null;
		//
		// List<MenuFunc> list_menu = menuFuncRepository.queryList(menuFunc);
		//
		// // 申明存放菜单信息的list
		// List<FuncTreeNode> nlist = new ArrayList<FuncTreeNode>();
		//
		// // 申明已处理过的菜单Map<菜单Id, 当前同级子节点的索引位置>
		// Map<String, Integer> map_used = new HashMap<String, Integer>();
		//
		// if(list_menu != null && list_menu.size() > 0) {
		// for (MenuFunc menu : list_menu) {
		// if(menu != null) {
		//
		// String firstmenuid = menu.getFirstmenuid().trim();
		// String secondmenuid = menu.getSecondmenuid().trim();
		// String thirdmenuid = menu.getThirdmenuid().trim();
		// String firstmenuname = menu.getFirstmenuname().trim();
		// String secondmenuname = menu.getSecondmenuname().trim();
		// String thirdmenuname = menu.getThirdmenuname().trim();
		//
		// // 判断三级菜单是否已经添加过
		// if(!map_used.containsKey(thirdmenuid)) {
		//
		// // 初始化三级菜单
		// FuncTreeNode thirdNode = new FuncTreeNode(thirdmenuname, thirdmenuid,
		// thirdmenuid, true,
		// map_selected.get(thirdmenuid)==null?false:map_selected.get(thirdmenuid));
		//
		// // 判断一级菜单是否已经添加过
		// if(map_used.containsKey(firstmenuid)) {
		//
		// // 获取已添加过的一级菜单
		// FuncTreeNode firstNode = nlist.get(map_used.get(firstmenuid));
		//
		// // 判断二级菜单是否已经添加过
		// if(map_used.containsKey(secondmenuid)) {
		//
		// // 获取已添加过的二级菜单
		// FuncTreeNode secondNode =
		// firstNode.getChildren().get(map_used.get(secondmenuid));
		//
		// // 设置层级
		// secondNode.getChildren().add(thirdNode);
		//
		// // 设置已用
		// map_used.put(thirdmenuid, secondNode.getChildren().size()-1);
		//
		// } else {
		//
		// // 初始化二级菜单
		// FuncTreeNode secondNode = new FuncTreeNode(secondmenuname,
		// secondmenuid, secondmenuid, false,
		// map_selected.get(secondmenuid)==null?false:map_selected.get(secondmenuid));
		//
		// // 设置层级
		// secondNode.getChildren().add(thirdNode);
		// firstNode.getChildren().add(secondNode);
		//
		// // 设置已用
		// map_used.put(secondmenuid, firstNode.getChildren().size()-1);
		// map_used.put(thirdmenuid, secondNode.getChildren().size()-1);
		//
		// }
		// } else {
		// FuncTreeNode firstNode = new FuncTreeNode(firstmenuname, firstmenuid,
		// firstmenuid, false,
		// map_selected.get(firstmenuid)==null?false:map_selected.get(firstmenuid));
		// FuncTreeNode secondNode = new FuncTreeNode(secondmenuname,
		// secondmenuid, secondmenuid, false,
		// map_selected.get(secondmenuid)==null?false:map_selected.get(secondmenuid));
		//
		// // 设置层级
		// secondNode.getChildren().add(thirdNode);
		// firstNode.getChildren().add(secondNode);
		// nlist.add(firstNode);
		//
		// // 设置已用
		// map_used.put(firstmenuid, nlist.size()-1);
		// map_used.put(secondmenuid, firstNode.getChildren().size()-1);
		// map_used.put(thirdmenuid, secondNode.getChildren().size()-1);
		// }
		// }
		// }
		// }
		// }
		//
		// 获取Json格式的节点
		try {
			menuStr = JsonUtils.listToJson(nlist);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return menuStr;
	}

	// 迭代查询下级节点
	private FuncTreeNode iterationNode(SysFuncInfo sysFuncInfo,
			FuncTreeNode node, String menuId) {
		// 判断是否有下级菜单
		Map<String, Object> secondMap = new HashMap<String, Object>();
		secondMap.put("supFuncId", StringUtil.trim(sysFuncInfo.getFuncId()));
		List<SysFuncInfo> nextMenuList = sysFuncInfoRepository
				.queryMenusBySupId(secondMap);
		if (nextMenuList != null && nextMenuList.size() > 0) {
			// 有下级菜单
			for (SysFuncInfo nexrSysFuncInfo : nextMenuList) {
				FuncTreeNode nextNode = new FuncTreeNode(
						nexrSysFuncInfo.getFuncDesc(),
						nexrSysFuncInfo.getFuncId(),
						nexrSysFuncInfo.getFuncId(), false, false);
				if (menuId.contains(nexrSysFuncInfo.getFuncId())) {
					nextNode.setChecked(true);
				}
				node.getChildren().add(nextNode);
				iterationNode(nexrSysFuncInfo, nextNode, menuId);
			}
		} else {
			// 无下级菜单
			node.setLeaf(true);
		}
		return node;
	}

	// 根据roleID拼装menuList正式表
	public String makeMenuList(String roleId) {
		// 获取sys_func_info中所有的菜单
		String menuList = "";
		SysRoleFunc sysRoleFuncTemp = new SysRoleFunc();
		sysRoleFuncTemp.setRoleId(StringUtil.trim(roleId));
		//sysRoleFuncTemp.setOptstatus("02");
		// 获取用户拥有的所有功能编号
		List<SysRoleFunc> list_sysrolefunc = sysRoleFuncReponsitory
				.querySysRoleFunc(sysRoleFuncTemp);
		if (list_sysrolefunc.size() > 0) {
			for (SysRoleFunc sysRoleFunc : list_sysrolefunc) {
				if (!menuList.contains("'" + sysRoleFunc.getFuncId() + "'")) {
					menuList = menuList + "'"
							+ StringUtil.trim(sysRoleFunc.getFuncId()) + "'"
							+ ",";
				}
			}
		}
		return menuList;
	}
	// 根据roleID拼装menuList零时表
	public String makeMenuListTmp(String roleId) {
		// 获取sys_func_info中所有的菜单
		String menuList = "";
		SysRoleFuncTmp sysRoleFuncTmp = new SysRoleFuncTmp();
		sysRoleFuncTmp.setRoleid((StringUtil.trim(roleId)));
		sysRoleFuncTmp.setOptstatus("01");
		// 获取用户拥有的所有功能编号
		List<SysRoleFuncTmp> list_sysrolefuncTmp = sysRoleFuncTmpReponsitory
				.querySysRoleFuncTmp(sysRoleFuncTmp);
		if (list_sysrolefuncTmp.size() > 0) {
			for (SysRoleFuncTmp sysRoleFuncTmp1 : list_sysrolefuncTmp) {
				if (!menuList.contains("'" + sysRoleFuncTmp1.getFuncId() + "'")) {
					menuList = menuList + "'"
							+ StringUtil.trim(sysRoleFuncTmp1.getFuncId()) + "'"
							+ ",";
				}
			}
		}
		return menuList;
	}
	@Autowired
	protected MenuFuncRepository menuFuncRepository;
	@Autowired
	protected SysFuncInfoRepository sysFuncInfoRepository;
	@Autowired
	protected SysRoleFuncReponsitory sysRoleFuncReponsitory;
	@Autowired
	protected SysRoleFuncTmpReponsitory sysRoleFuncTmpReponsitory;

}
