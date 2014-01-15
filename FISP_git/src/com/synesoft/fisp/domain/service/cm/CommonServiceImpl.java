package com.synesoft.fisp.domain.service.cm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.constants.UserConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.MenuFunc;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.SysFuncInfo;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.SysRoleFunc;
import com.synesoft.fisp.domain.model.TipsConn;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.repository.cm.TipsConnRepository;
import com.synesoft.fisp.domain.repository.sm.MenuFuncRepository;
import com.synesoft.fisp.domain.repository.sm.OrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.RoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.SysFuncInfoRepository;
import com.synesoft.fisp.domain.repository.sm.SysParamRepository;
import com.synesoft.fisp.domain.repository.sm.SysRoleFuncReponsitory;
import com.synesoft.fisp.domain.repository.sm.UserOrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfRepository;

/**
 * @author zhongHubo
 * @date 2013年7月15日 17:00:40
 * @version 1.0
 * @Description 公共ServiceImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	// 菜单组,菜单号+序号 RSV1
	class Menu implements Comparable<Menu> {

		private String menu_code;
		private Integer menu_order;

		public String getMenu_code() {
			return menu_code;
		}

		public void setMenu_code(String menu_code) {
			this.menu_code = menu_code;
		}

		public Integer getMenu_order() {
			return menu_order;
		}

		public void setMenu_order(Integer menu_order) {
			this.menu_order = menu_order;
		}

		@Override
		public int compareTo(Menu menu) {
			return this.getMenu_order().compareTo(menu.getMenu_order());
		}
	}

	@Autowired
	protected UserRoleInfRepository userRoleInfRepository;

	@Autowired
	protected RoleInfRepository roleInfRepository;

	@Autowired
	protected MenuFuncRepository menuFuncRepository;

	@Autowired
	protected OrgInfRepository orgInfRepository;

	@Autowired
	protected TipsConnRepository tipsConnRepository;

	@Autowired
	protected UserOrgInfRepository userOrgInfRepository;
	
	@Autowired
	protected SysRoleFuncReponsitory sysRoleFuncReponsitory;
	
	@Autowired
	protected SysFuncInfoRepository sysFuncInfoRepository;

	/**
	 * 获取菜单信息
	 * 
	 * @param userInf
	 *            当前用户信息
	 * @return String[strbuf_header, strbuf_left]
	 */
	@Override
	public String[] transQueryMenuList(UserInf userInf) {

		// 获取用户登录的机构信息
		OrgInf orgInf = ContextConst.getOrgInfByUser();

		// 获取当前用户所登录机构拥有的所有角色
		UserRoleInf userRoleInf = new UserRoleInf();
		userRoleInf.setUserid(userInf.getUserid().trim());
		userRoleInf.setOrgid(orgInf.getOrgid().trim());
//		userRoleInf.setOptstatus("02");
//		List<UserRoleInf> list_userrole = userRoleInfRepository
//				.queryUserRoleIdList(userRoleInf);
		/******************************************-修改*****************************/
		// 申明header菜单字符串
		StringBuffer header_strbuf = new StringBuffer();
		// 申明left菜单字符串
		StringBuffer left_strbuf = new StringBuffer();
		//拼接菜单栏
		jointMenu(header_strbuf,left_strbuf,userRoleInf);
		
		/******************************************修改*****************************/
		
////////////////////////////////// 注释于2013年11月22日 14:57:30-zhonghubo /////////////////////////////////////
//		// 获取当前用户所拥有的所有菜单
//		Map<String, Object> map_menu = getAllMenu(list_userrole);
//		// 处理异常菜单
//		abnormalMenusHandle(map_menu);

//		// 申明header菜单字符串
//		StringBuffer strbuf_header = new StringBuffer();
//		// 申明left菜单字符串
//		StringBuffer strbuf_left = new StringBuffer();
//
//		if (map_menu != null && map_menu.size() > 0) {
//			// 菜单开始
//			strbuf_header.append("<ul class=\"nav\" role=\"navigation\">");
//			strbuf_left
//					.append("<div class=\"row\"><div class=\"span3\"><div class=\"accordion\" id=\"accordion1\">");
//			
//			// 分解菜单 并 组装菜单
//			assembleMenu(strbuf_header, strbuf_left, map_menu);
//			
//			// 菜单结束
//			strbuf_header.append("</ul>");
//			strbuf_left.append("</div></div></div>");
//		}
///////////////////////////////////////////////////////////////////////////////////////////////////////

		String[] menu_all = new String[] { header_strbuf.toString(),
				left_strbuf.toString() };
		return menu_all;
	}

	/**
	 * 处理异常菜单
	 * 
	 * @param map_menu
	 */
	@SuppressWarnings("unused")
	private void abnormalMenusHandle(Map<String, Object> map_menu) {
		if (map_menu != null && map_menu.size() > 0) {

			Set<String> set_menu = map_menu.keySet();
			if (set_menu != null && set_menu.size() > 0) {
				for (Iterator<?> iterator = set_menu.iterator(); iterator
						.hasNext();) {
					String menu = (String) iterator.next();
					// 如果没有子菜单，则移除当前的菜单和父菜单
					if (menu.length() > CommonUtil.len_thirdMenu) {
						// 判断当前菜单是否有子菜单
						if (!map_menu.containsValue(menu)) {
							// 获取当前菜单的父菜单
							Object obj = map_menu.get(menu);
							if (obj != null) {
								String parentMenu = (String) obj;
								// 移除父菜单
								map_menu.remove(parentMenu);
							}
							// 移除当前菜单
							map_menu.remove(menu);
						}
					}
				}
			}
		}
	}
	
	private void jointMenu(StringBuffer header_strbuf, StringBuffer left_strbuf,UserRoleInf userRoleInf){
		//所有菜单
		List<SysFuncInfo> menuList_ = new ArrayList<SysFuncInfo>();
		String roleMode = UserConst.getROLE_MODE();
		if(CommonConst.ROLE_MODE_UNBIND_ORG.equals(roleMode)){
			//角色不绑定机构
			menuList_ = sysFuncInfoRepository.queryAllSysFuncInfoNoOrg(userRoleInf);
		}else if(CommonConst.ROLE_MODE_BIND_ORG.equals(roleMode)){
			//机构与角色有关 
			// 查询当前用户、当前登录机构的功能权限
			menuList_ = sysFuncInfoRepository.queryAllSysFuncInfo(userRoleInf);
		}else{
			//两者兼容 
			menuList_ = sysFuncInfoRepository.queryAllSysFuncInfo(userRoleInf);
			if(menuList_.size()==0){
				menuList_ = sysFuncInfoRepository.queryAllSysFuncInfoNoOrg(userRoleInf);
			}
		}
		/** 从新遍历menuList 生成层级关系***/
		List<SysFuncInfo> menuList = new ArrayList<SysFuncInfo>();
		iterator(menuList_,1,"FUNCID_",menuList);
		/**生成菜单**/
		if(menuList.size()>0){
			header_strbuf.append("<ul class=\"nav\" role=\"navigation\">");
			left_strbuf.append("<div class=\"row\"><div class=\"span3\">" 
										+"<div class=\"accordion\" id=\"accordion1\">");
			getMenuHtml(menuList,"FUNCID_",1,header_strbuf,left_strbuf);
			// 菜单结束
			header_strbuf.append("</ul>");
			//左一级菜单结束
			left_strbuf.append("</div></div></div>");
		}
		
	}
	
	/**
	 * 
	 * @param menuList_
	 * @return
	 */
	private void iterator(List<SysFuncInfo> menuList_,int level,String supFuncId_,List<SysFuncInfo> menuList){
		for(SysFuncInfo func : menuList_){
			String supFuncId = StringUtils.isBlank(func.getSupFuncId())?"FUNCID_":func.getSupFuncId();
			String funcId = func.getFuncId();
			if(supFuncId_.equals(supFuncId)){
				func.setLevel(level);
				/**检查是否存在子菜单**/
				func.setConnectByIsleaf(checkChild(menuList_,funcId));
				menuList.add(func);
				iterator(menuList_,level+1,funcId,menuList);
			}
		}
	}
	
	/**
	 * 检查supFuncId_节点是否存在子节点 (0 存在子节点  1不存在子节点)
	 * @param menuList_
	 * @param supFuncId_
	 * @return
	 */
	private int checkChild(List<SysFuncInfo> menuList_,String funcId_){
		int i = 1 ;
		for(SysFuncInfo func : menuList_){
			String supFuncId = StringUtils.isBlank(func.getSupFuncId())?"FUNCID_":func.getSupFuncId();
			if(funcId_.equals(supFuncId)){
				i = 0;
				break;
			}
		}
		return i;
	}
	
	/**
	 * 递归所有目录菜单
	 * @param menuList 菜单LIst
	 * @param supFuncId_ 父菜单id
	 * @param level_ 级别
	 * @param header_strbuf 头菜单
	 * @param left_strbuf 左侧菜单
	 */
	private void getMenuHtml(List<SysFuncInfo> menuList,String supFuncId_,int level_,StringBuffer header_strbuf, StringBuffer left_strbuf){
		int i = 1;
		for(SysFuncInfo sysFuncInfo : menuList){
			String supFuncId = StringUtils.isBlank(sysFuncInfo.getSupFuncId())?"FUNCID_":sysFuncInfo.getSupFuncId();
			int level = sysFuncInfo.getLevel();
			String funcId =sysFuncInfo.getFuncId().trim();
			if(level==level_&&supFuncId_.equals(supFuncId)){
				/**是否存在子节点**/
				int isLeaf = sysFuncInfo.getConnectByIsleaf();
				String url = StringUtils.isBlank(sysFuncInfo.getFuncUrl())?"":sysFuncInfo.getFuncUrl();
				String desc = StringUtils.isBlank(sysFuncInfo.getFuncDesc())?"":sysFuncInfo.getFuncDesc();
				/**一级菜单**/
				if(level==1){
					//顶端菜单
					header_strbuf.append("<li class=\"dropdown\"><a id=\"drop"+i+"\" role=\"button\" class=\"dropdown-toggle\" "
							+ "data-toggle=\"dropdown\" href=\"" 
							+ ContextConst.getContextPath()
							+ (url.indexOf("/") == 0 ? url : "/" + url)
							+ "\">"
							+ desc
							+ "<b class=\"caret\"></b></a>");
					//左一级菜单开始
					left_strbuf.append("<div class=\"accordion-group noborder\"><div class=\"accordion-heading big_menu\">"
										+ "<a onfocus=\"this.blur();\" class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accordion1\" "
										+ "href=\"#collapse1"+ funcId+ "\"><i class=\"icon-cog\"></i>&nbsp;"
										+ desc+ "</a></div>");
					if(isLeaf==0){
						//上二级菜单开始
						header_strbuf.append("<ul class=\"dropdown-menu main_menu_bg2\" "
								+ "role=\"menu\" aria-labelledby=\"drop"+i+"\">");
						//左二级菜单开始
						left_strbuf.append( "<div id=\"collapse1"+ funcId
								+ "\" class=\"accordion-body collapse\"><div class=\"accordion nomargin_btm\" id=\"accordion1"+ funcId+ "\">"
								+ "<div class=\"accordion-group\">");
						getMenuHtml(menuList,funcId,level+1,header_strbuf,left_strbuf);
						//上一级菜单结束
						header_strbuf.append("</ul>");
						//左一级菜单结束
						left_strbuf.append("</div>");
					}
					
					//上二级菜单结束
					header_strbuf.append("</li>");
					//左二级菜单结束
					left_strbuf.append("</div></div></div>");
				}else {
					/**二级菜单**/
					if(isLeaf==0){
						//拼接上二级菜单
						header_strbuf.append("<li class=\"dropdown-submenu\"><a tabindex=\"-1\" href=\"" 
								+ ContextConst.getContextPath()
								+ (url.indexOf("/") == 0 ? url : "/" + url)
								+ "\">"
								+ desc
								+ "</a>");
						//拼接左二级菜单
						left_strbuf.append("<div class=\"accordion-heading mid_menu"+level+"\"><a  onfocus=\"this.blur();\" style=\"margin-left: "+(10*level)+"px\" class=\"accordion-toggle \" data-toggle=\"collapse\" "
								+ "data-parent=\"#accordion1"+ supFuncId_
								+ "\" href=\"#collapse2"+ funcId+ "\"><i class=\"icon-th-list\"></i>&nbsp;"
								+ desc+ "</a></div>");
						
						//上三级开始
						header_strbuf.append("<ul class=\"dropdown-menu main_menu_bg2\">");
							//左二级菜单开始
							left_strbuf.append( "<div id=\"collapse2"
									+ funcId
									+ "\" class=\"accordion-body collapse\"><div class=\"accordion nomargin_btm\" id=\"accordion1"
									+ sysFuncInfo.getFuncId().trim()
									+ "\">"
									+ "<div class=\"accordion-group1\">");
							getMenuHtml(menuList,funcId,level+1,header_strbuf,left_strbuf);
							left_strbuf.append("</div></div></div>");
						//上三级结束
						header_strbuf.append("</ul>");
						//上二级结束
						header_strbuf.append("</li>");
					}else{
						//菜单
						//拼接上二级菜单
						header_strbuf.append("<li class=\"\"><a tabindex=\"-1\" target=\"_menuTarget\" "+
						(StringUtils.isNotBlank(url)&&!"#".equals(url)?" href=\"" + ContextConst.getContextPath()
								+ (url.indexOf("/") == 0 ? url : "/" + url):"")+ "\">"+ desc+ "</a>");
						//拼接左二级菜单
						left_strbuf.append("<div class=\"accordion-heading mid_menu"+level+"\"><a  onfocus=\"this.blur();\"" +
								" style=\"margin-left: "+(10*level)+"px\" class=\"accordion-toggle \" target=\"_menuTarget\"  " +
								(StringUtils.isNotBlank(url)&&!"#".equals(url)?" href=\""+ ContextConst.getContextPath()+ (url.indexOf("/") == 0 ? url : "/" + url) :"")
								+ "\"><i class=\"icon-ok\"></i>&nbsp;"
								+ desc
								+ "</a></div>");
						//上二级结束
						header_strbuf.append("</li>");
					}
				}
			}
			i++;
		}
	}
	
	
	/**
	 * 分解菜单 并 组装菜单
	 * 
	 * @param header_strbuf
	 *            header菜单字符串
	 * @param strbuf_left
	 *            leftleft_strbuf单字符串
	 * @param list_userrole
	 *            用户所有角色
	 */
	private void jointMenu(StringBuffer header_strbuf, StringBuffer left_strbuf,List<UserRoleInf> list_userrole){
		String funcIds = "";
		if(list_userrole!=null && list_userrole.size()>0){
			for(UserRoleInf userRoleInfTemp:list_userrole){
				SysRoleFunc sysRoleFuncTemp = new SysRoleFunc();
				sysRoleFuncTemp.setRoleId(StringUtil.trim(userRoleInfTemp.getRoleid()));
				sysRoleFuncTemp.setOptstatus("02");
				//获取用户拥有的所有功能编号
				List<SysRoleFunc> list_sysrolefunc= sysRoleFuncReponsitory.querySysRoleFunc(sysRoleFuncTemp);
				if(list_sysrolefunc.size()>0){
					for(SysRoleFunc sysRoleFunc:list_sysrolefunc){
						if(!funcIds.contains("'"+sysRoleFunc.getFuncId()+"'")){
							funcIds = funcIds+"'"+StringUtil.trim(sysRoleFunc.getFuncId())+"'" + ",";
						}
					}
				}
			}
		} else {
			// 修改于2013年11月22日 14:56:30-zhonghubo
			return;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		funcIds = funcIds.substring(0,funcIds.lastIndexOf(","));
		paramMap.put("funcIds", funcIds);
		//获取一级菜单
		List<SysFuncInfo> firstMenuList = sysFuncInfoRepository.queryFirstSysFuncInfo(paramMap);
		
		if(firstMenuList!=null && firstMenuList.size()>0){
			// 菜单开始
			header_strbuf.append("<ul class=\"nav\" role=\"navigation\">");
			left_strbuf.append("<div class=\"row\"><div class=\"span3\">" 
										+"<div class=\"accordion\" id=\"accordion1\">");
			for(SysFuncInfo sysFuncInfo:firstMenuList){
				//拼接一级菜单
				//上一级菜单开始
				header_strbuf.append("<li class=\"dropdown\"><a id=\"drop1\" role=\"button\" class=\"dropdown-toggle\" "
									+ "data-toggle=\"dropdown\" href=\"" 
									+ ContextConst.getContextPath()
									+ (sysFuncInfo.getFuncUrl().indexOf("/") == 0 ? sysFuncInfo.getFuncUrl() : "/" + sysFuncInfo.getFuncUrl())
									+ "\">"
									+ sysFuncInfo.getFuncDesc()
									+ "<b class=\"caret\"></b></a>");
				//左一级菜单开始
				left_strbuf.append("<div class=\"accordion-group noborder\"><div class=\"accordion-heading big_menu\">"
									+ "<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accordion1\" "
									+ "href=\"" 
									+"#collapse1"
									+ sysFuncInfo.getFuncId().trim()
									+ "\"><i class=\"icon-cog\"></i>&nbsp;"
									+ sysFuncInfo.getFuncDesc()
									+ "</a></div>");
				//判断是否有二级菜单
				Map<String,Object> secondMap = new HashMap<String,Object>();
				secondMap.put("funcIds", funcIds);
				secondMap.put("supFuncId", StringUtil.trim(sysFuncInfo.getFuncId()));
				List<SysFuncInfo> secondMenuList = sysFuncInfoRepository.querySecondSysFuncInfo(secondMap);
				if(secondMenuList!=null && secondMenuList.size()>0){
					//上二级菜单开始
					header_strbuf.append("<ul class=\"dropdown-menu main_menu_bg2\" "
										+ "role=\"menu\" aria-labelledby=\"drop1\">");
					//左二级菜单开始
					left_strbuf.append( "<div id=\"collapse1"
										+ sysFuncInfo.getFuncId().trim()
										+ "\" class=\"accordion-body collapse\"><div class=\"accordion nomargin_btm\" id=\"accordion1"
										+ sysFuncInfo.getFuncId().trim()
										+ "\">"
										+ "<div class=\"accordion-group\">");
					for(SysFuncInfo secondSysFuncInfo:secondMenuList){
						//拼接上二级菜单
						header_strbuf.append("<li class=\"dropdown-submenu\"><a tabindex=\"-1\" href=\"" 
								+ ContextConst.getContextPath()
								+ (secondSysFuncInfo.getFuncUrl().indexOf("/") == 0 ? secondSysFuncInfo.getFuncUrl() : "/" + secondSysFuncInfo.getFuncUrl())
								+ "\">"
								+ secondSysFuncInfo.getFuncDesc()
								+ "</a>");
						//拼接左二级菜单
						left_strbuf.append("<div class=\"accordion-heading mid_menu\"><a class=\"accordion-toggle main_menu_bg\" data-toggle=\"collapse\" "
								+ "data-parent=\"#accordion1"
								+ sysFuncInfo.getFuncId().trim()
								+ "\" href=\"#collapse2"
								+ secondSysFuncInfo.getFuncId().trim()
								+ "\"><i class=\"icon-double-angle-right\"></i>&nbsp;"
								+ secondSysFuncInfo.getFuncDesc()
								+ "</a></div>");
						
						//判断是否有三级菜单
						Map<String,Object> thirdMap = new HashMap<String,Object>();
						thirdMap.put("funcIds", funcIds);
						thirdMap.put("supFuncId", StringUtil.trim(secondSysFuncInfo.getFuncId()));
						List<SysFuncInfo> thirdMenuList = sysFuncInfoRepository.queryThirdSysFuncInfo(thirdMap);
						if(thirdMenuList!=null && thirdMenuList.size()>0){
							//上三级开始
							header_strbuf.append("<ul class=\"dropdown-menu main_menu_bg2\">");
							//左三级开始
							left_strbuf.append("<div id=\"collapse2"
										+ secondSysFuncInfo.getFuncId().trim()
										+ "\" class=\"accordion-body collapse\">" 
										+"<div class=\"accordion-inner\">"
										+"<ul class=\"nav nav-pills nav-stacked nomargin_btm\">");
							
							for(SysFuncInfo thirdSysFuncInfo:thirdMenuList){
								//多于三级用三级的样式迭代
								iterationMenu(header_strbuf,left_strbuf,funcIds,thirdSysFuncInfo);
							}
							//上三级结束
							header_strbuf.append("</ul>");
							//左三级开始
							left_strbuf.append("</ul></div></div>");
						}
						//上二级结束
						header_strbuf.append("</li>");
						//左二级结束
						left_strbuf.append("</div></div>");
					}
					//上二级菜单结束
					header_strbuf.append("</ul>");
					//左二级菜单结束
					left_strbuf.append("</div></div></div>");
				}
				//上一级菜单结束
				header_strbuf.append("</li>");
				//左一级菜单结束
				left_strbuf.append("</div>");
			}
			// 菜单结束
			header_strbuf.append("</ul>");
			//左一级菜单结束
			left_strbuf.append("</div></div></div></div>");
		}
	}
	/**
	 * 多于三级，迭代第三级样式
	 * 
	 * @param header_strbuf
	 *            header菜单字符串
	 * @param header_strbuf
	 *            left菜单字符串
	 */
	private void iterationMenu(StringBuffer header_strbuf,
			StringBuffer left_strbuf,String funcIds, SysFuncInfo sysFuncInfo) {
		//判断此菜单是否是叶节点
		Map<String,Object> iterationMap = new HashMap<String,Object>();
		iterationMap.put("funcIds", funcIds);
		iterationMap.put("supFuncId", StringUtil.trim(sysFuncInfo.getFuncId()));
		List<SysFuncInfo> iterationMenuList = sysFuncInfoRepository.queryThirdSysFuncInfo(iterationMap);
		if(iterationMenuList!=null && iterationMenuList.size()>0){
			//为枝节点
			header_strbuf.append("<li class=\"dropdown-submenu\"><a tabindex=\"-1\" href=\""
					+ ContextConst.getContextPath()
					+ (sysFuncInfo.getFuncUrl().indexOf("/") == 0 ? sysFuncInfo.getFuncUrl() : "/" + sysFuncInfo.getFuncUrl()) + "\">"
					+ sysFuncInfo.getFuncDesc() + "</a>");
			left_strbuf.append("<li><a target=\"_menuTarget\" href=\""
					+ ContextConst.getContextPath()
					+ (sysFuncInfo.getFuncUrl().indexOf("/") == 0 ? sysFuncInfo.getFuncUrl()
							: "/" + sysFuncInfo.getFuncUrl())
					+ "\"><i class=\"icon-ok\"></i>&nbsp;"
					+ sysFuncInfo.getFuncDesc() + "</a>");

			//上N级开始
			header_strbuf.append("<ul class=\"dropdown-menu main_menu_bg2\">");
			//左N级开始
//			left_strbuf.append("<ul class=\"nav nav-pills nav-stacked nomargin_btm\">");
			left_strbuf.append("<div id=\"collapse2"
					+ sysFuncInfo.getFuncId().trim()
					+ "\" class=\"accordion-body collapse\">" 
					+"<div class=\"accordion-inner\">"
					+"<ul class=\"nav nav-pills nav-stacked nomargin_btm\">");
			for(SysFuncInfo sysFuncInfoNext:iterationMenuList){
				//多于N+1级用三级的样式迭代
				iterationMenu(header_strbuf,left_strbuf,funcIds,sysFuncInfoNext);
			}
			//上三级结束
			header_strbuf.append("</ul>");
			//左三级开始
//			left_strbuf.append("</ul>");
			left_strbuf.append("</ul></div></div>");
		}else {
			//叶节点
			header_strbuf.append("<li ><a tabindex=\"-1\" target=\"_menuTarget\" href=\""
					+ ContextConst.getContextPath()
					+ (sysFuncInfo.getFuncUrl().indexOf("/") == 0 ? sysFuncInfo.getFuncUrl() : "/" + sysFuncInfo.getFuncUrl()) + "\">"
					+ sysFuncInfo.getFuncDesc() + "</a>");
			left_strbuf.append("<li><a target=\"_menuTarget\" href=\""
					+ ContextConst.getContextPath()
					+ (sysFuncInfo.getFuncUrl().indexOf("/") == 0 ? sysFuncInfo.getFuncUrl()
							: "/" + sysFuncInfo.getFuncUrl())
					+ "\"><i class=\"icon-ok\"></i>&nbsp;"
					+ sysFuncInfo.getFuncDesc() + "</a>");
		}
		header_strbuf.append("</li>");
		left_strbuf.append("</li>");
	}
	/**
	 * 分解菜单 并 组装菜单
	 * 
	 * @param strbuf_header
	 *            header菜单字符串
	 * @param strbuf_left
	 *            left菜单字符串
	 * @param map_menu
	 *            菜单map
	 */
	@SuppressWarnings("unused")
	private void assembleMenu(StringBuffer strbuf_header,
			StringBuffer strbuf_left, Map<String, Object> map_menu) {

		// 所有的菜单
		Map<String, Integer> mapMenuFunc = new HashMap<String, Integer>();
		List<MenuFunc> listMenuFunc = menuFuncRepository
				.queryList(new MenuFunc());
		if (listMenuFunc != null && listMenuFunc.size() > 0) {
			for (int i = 0; i < listMenuFunc.size(); i++) {
				MenuFunc tmpMenuFunc = listMenuFunc.get(i);
				try {
					mapMenuFunc.put(tmpMenuFunc.getThirdmenuid().trim(),
							Integer.valueOf(tmpMenuFunc.getRsv1()));
				} catch (Exception e) {
					mapMenuFunc.put(tmpMenuFunc.getThirdmenuid().trim(), 0);
				}
			}
		}

		List<Menu> list_menu = new ArrayList<Menu>();

		Set<String> set_menu = map_menu.keySet();
		if (set_menu != null && set_menu.size() > 0) {
			for (Iterator<?> iterator = set_menu.iterator(); iterator.hasNext();) {
				String menu = (String) iterator.next();
				Menu tmpMenu = new Menu();
				tmpMenu.setMenu_code(menu);
				if (mapMenuFunc.get(menu) == null) {
					tmpMenu.setMenu_order(0);
				} else {
					tmpMenu.setMenu_order(mapMenuFunc.get(menu));
				}
				list_menu.add(tmpMenu);
			}
		}

		// 将菜单排序
		Collections.sort(list_menu);

		// 获取各一级菜单、二级菜单的个数
		Map<String, Integer> map_menuCount = this.getFirstMenu(list_menu);

		int size_menu = list_menu.size();// 菜单总数
		boolean firstMenu_start = true; // 一级菜单开始
		boolean firstMenu_end = false; // 一级菜单结束
		boolean secondMenu_start = true;// 二级菜单开始
		boolean secondMenu_end = false; // 二级菜单结束
		int count_first = 0; // 当前一级菜单的索引
		int count_second = 0; // 当前二级菜单的索引
		for (int i = 0; i < size_menu; i++) {
			Menu tMenu = list_menu.get(i);
			String menu = tMenu.getMenu_code();
			if (menu == null || "".equals(menu) || menu.length() < 8) {
				continue;
			}
			// 一级菜单
			String firstMenu = menu.substring(0, 2);
			// 二级菜单
			String secondMenu = menu.substring(0, 5);
			// 三级菜单
			String thirdMenu = menu;

			// 获取菜单名
			MenuFunc menuFunc = this.getMenuNameByMenu(thirdMenu);

			count_first++;
			// 获取当前一级菜单的个数
			int firstMenuCount = map_menuCount.get(firstMenu);
			// 最后一个一级菜单
			if (count_first == firstMenuCount) {
				// 重置当前一级菜单的数量
				count_first = 0;
				if (!(firstMenu_start && menuFunc == null)) {
					firstMenu_end = true;
				}
			} else {
				firstMenu_end = false;
			}

			count_second++;
			// 获取当前二级菜单的个数
			int secondMenuCount = map_menuCount.get(secondMenu);
			// 最后一个二级菜单
			if (count_second == secondMenuCount) {
				// 重置当前二级菜单的数量
				count_second = 0;
				if (!(secondMenu_start && menuFunc == null)) {
					secondMenu_end = true;
				}
			} else {
				secondMenu_end = false;
			}

			// 一级菜单新的开始
			if (firstMenu_start && menuFunc != null) {
				strbuf_header
						.append("<li class=\"dropdown\"><a id=\"drop1\" role=\"button\" class=\"dropdown-toggle\" "
								+ "data-toggle=\"dropdown\" href=\"#\">"
								+ menuFunc.getFirstmenuname()
								+ "<b class=\"caret\"></b></a><ul class=\"dropdown-menu main_menu_bg2\" "
								+ "role=\"menu\" aria-labelledby=\"drop1\">");
				strbuf_left
						.append("<div class=\"accordion-group noborder\"><div class=\"accordion-heading big_menu\">"
								+ "<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accordion1\" "
								+ "href=\"#collapse1"
								+ menuFunc.getFirstmenuid().trim()
								+ "\"><i class=\"icon-cog\"></i>&nbsp;"
								+ menuFunc.getFirstmenuname()
								+ "</a></div>"
								+ "<div id=\"collapse1"
								+ menuFunc.getFirstmenuid().trim()
								+ "\" class=\"accordion-body collapse\"><div class=\"accordion nomargin_btm\" id=\"accordion1"
								+ menuFunc.getFirstmenuid().trim()
								+ "\">"
								+ "<div class=\"accordion-group\">");
				firstMenu_start = false;
			}

			// 设置二级菜单
			// 菜单名不为空
			if (menuFunc != null) {
				secondMenu_start = setSecondMenu(strbuf_header, strbuf_left,
						secondMenu_start, secondMenu_end, secondMenu, menuFunc,
						count_first, count_second);
			}

			// 一级菜单的结束
			if (firstMenu_end) {
				strbuf_header.append("</ul></li>");
				strbuf_left.append("</div></div></div>");
				firstMenu_start = true;
			}

		}
	}

	/**
	 * 获取各一级菜单、二级菜单的个数
	 * 
	 * @param list_menu
	 *            菜单list
	 * @return
	 */
	private Map<String, Integer> getFirstMenu(List<Menu> list_menu) {
		Map<String, Integer> map_menuCount = new HashMap<String, Integer>();

		for (Menu menu : list_menu) {
			String menu_code = menu.getMenu_code();
			if (menu_code != null && !"".equals(menu_code)
					&& menu_code.length() >= 8) {
				// 一级菜单、二级菜单
				String[] menus = new String[] { menu_code.substring(0, 2),
						menu_code.substring(0, 5) };

				for (int i = 0; i < menus.length; i++) {
					if (map_menuCount.containsKey(menus[i])) {
						int count = map_menuCount.get(menus[i]);
						count++;
						map_menuCount.put(menus[i], count);
					} else {
						map_menuCount.put(menus[i], 1);
					}
				}
			}
		}

		return map_menuCount;
	}

	/**
	 * 设置二级菜单
	 * 
	 * @param strbuf_header
	 *            菜单字符串
	 * @param firstMenu_start
	 *            一级菜单开始
	 * @param firstMenu_end
	 *            一级菜单结束
	 * @param secondMenu
	 *            当前二级菜单
	 * @param thirdMenu
	 *            当前三级菜单
	 * @return 是否为二级菜单的开始
	 */
	private boolean setSecondMenu(StringBuffer strbuf_header,
			StringBuffer strbuf_left, boolean secondMenu_start,
			boolean secondMenu_end, String secondMenu, MenuFunc menuFunc,
			int count_first, int count_second) {

		// 设置二级菜单的开始
		if (secondMenu_start) {
			strbuf_header
					.append("<li class=\"dropdown-submenu\"><a tabindex=\"-1\" href=\"#\">"
							+ menuFunc.getSecondmenuname()
							+ "</a>"
							+ "<ul class=\"dropdown-menu main_menu_bg2\">");
			strbuf_left
					.append("<div class=\"accordion-heading mid_menu\"><a class=\"accordion-toggle main_menu_bg\" data-toggle=\"collapse\" "
							+ "data-parent=\"#accordion1"
							+ menuFunc.getFirstmenuid().trim()
							+ "\" href=\"#collapse2"
							+ menuFunc.getSecondmenuid().trim()
							+ "\"><i class=\"icon-double-angle-right\"></i>&nbsp;"
							+ menuFunc.getSecondmenuname()
							+ "</a></div><div id=\"collapse2"
							+ menuFunc.getSecondmenuid().trim()
							+ "\" class=\"accordion-body collapse\"><div class=\"accordion-inner\">"
							+ "<ul class=\"nav nav-pills nav-stacked nomargin_btm\">");
			secondMenu_start = false;
		}

		// 设置三级菜单
		seTthirdMenu(strbuf_header, strbuf_left, menuFunc);

		// 设置二级菜单的结束
		if (secondMenu_end) {
			strbuf_header.append("</ul></li>");
			strbuf_left.append("</ul></div></div>");
			secondMenu_start = true;
		}

		return secondMenu_start;
	}

	/**
	 * 设置三级菜单
	 * 
	 * @param strbuf_header
	 *            菜单字符串
	 * @param thirdMenu
	 *            当前三级菜单
	 */
	private void seTthirdMenu(StringBuffer strbuf_header,
			StringBuffer strbuf_left, MenuFunc menuFunc) {
		strbuf_header
				.append("<li><a tabindex=\"-1\" target=\"_menuTarget\" href=\""
						+ ContextConst.getContextPath()
						+ (menuFunc.getUrl().indexOf("/") == 0 ? menuFunc
								.getUrl() : "/" + menuFunc.getUrl()) + "\">"
						+ menuFunc.getThirdmenuname() + "</a></li>");
		strbuf_left.append("<li><a target=\"_menuTarget\" href=\""
				+ ContextConst.getContextPath()
				+ (menuFunc.getUrl().indexOf("/") == 0 ? menuFunc.getUrl()
						: "/" + menuFunc.getUrl())
				+ "\"><i class=\"icon-ok\"></i>&nbsp;"
				+ menuFunc.getThirdmenuname() + "</a></li>");
	}

	/**
	 * 获取当前用户所拥有的所有菜单
	 * 
	 * @param list_userrole
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> getAllMenu(List<?> list_userrole) {
		Map<String, Object> map_menu = new HashMap<String, Object>();
		for (Object obj : list_userrole) {
			if (obj instanceof UserRoleInf) {
				UserRoleInf userRoleInf = (UserRoleInf) obj;
				RoleInf roleInf = roleInfRepository.query(userRoleInf
						.getRoleid().trim());
				if (roleInf != null) {
					//新：查询角色-功能对照表和功能权限信息表
					String menuList = roleInf.getMenulist();
					if (menuList != null && !"".equals(menuList)) {
						String[] menus = menuList
								.split(CommonConst.UNSEPERATOR);
						if (menus != null) {
							for (int i = 0; i < menus.length; i++) {

								String currentMenu = menus[i];
								String parentMenu = "";

								if (currentMenu.length() == CommonUtil.len_thirdMenu) {
									parentMenu = currentMenu.substring(0,
											CommonUtil.len_secondMenu);
								}

								if (currentMenu.length() == CommonUtil.len_secondMenu) {
									parentMenu = currentMenu.substring(0,
											CommonUtil.len_fisrtMenu);
								}

								map_menu.put(menus[i], parentMenu);
							}
						}
					}
				}
			}
		}
		return map_menu;
	}

	/**
	 * 根据菜单获取菜单名
	 * 
	 * @param menu
	 *            三级菜单
	 * @return 菜单对象
	 */
	private MenuFunc getMenuNameByMenu(String thirdMenu) {

		MenuFunc menuFunc = new MenuFunc();
		menuFunc.setThirdmenuid(thirdMenu);
		menuFunc = this.transMenuDetail(menuFunc);

		return menuFunc;
	}

	@Override
	public MenuFunc transMenuDetail(MenuFunc menuFunc) {
		return menuFuncRepository.query(menuFunc);
	}

	/**
	 * 查询当前用户的TipsConn信息
	 * 
	 * @param userInf
	 * @return
	 */
	@Override
	public TipsConn queryTipsConn(UserInf userInf) {
		TipsConn tipsConn = null;

		if (userInf != null) {
			// 获取用户登录的机构信息
			OrgInf orgInf = ContextConst.getOrgInfByUser();
			
			if (orgInf != null) {
				tipsConn = tipsConnRepository
						.query(orgInf.getNodecode() == null ? null : orgInf
								.getNodecode().trim());
			}
		}

		return tipsConn;
	}

	/**
	 * 获取当前用户可选择登录的所有机构信息
	 * 
	 * @param userInf
	 * @return
	 */
	@Override
	public List<UserOrgInf> queryUserOrgList(UserInf userInf) {
		List<UserOrgInf> list = null;

		UserOrgInf userOrgInf = new UserOrgInf();
		userOrgInf.setUserid(userInf.getUserid() == null ? null : userInf
				.getUserid().trim());

		list = userOrgInfRepository.queryUserOrgInfList(userOrgInf);

		if (list != null && list.size() > 0) {
			for (UserOrgInf userorg : list) {
				userorg.setOrgid(userorg.getOrgid().trim());
			}
		}

		return list;
	}

	@Override
	public Map<String, Object> initParamLoader() {
		List<SysParam> list = null;
		Map<String, Object> map = new HashMap<String, Object>();

		list = sysParamRepos.querySysParamList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysParam sysParam = list.get(i);
				map.put(sysParam.getParamInfo(), sysParam.getParamVal());
			}
		}
		return map;
	}
	
	/**
	 * 查询当前用户的功能权限
	 */
	@Override
	public void queryAuthority() {
		String roleMode = UserConst.getROLE_MODE();
		// 存放当前用户的功能权限
		Map<String, Object> map_authority = new HashMap<String, Object>();
		// 申明参数
		UserRoleInf userRoleInf = new UserRoleInf();
		userRoleInf.setUserid(ContextConst.getCurrentUser().getUserid().trim());
		userRoleInf.setOrgid(ContextConst.getOrgInfByUser().getOrgid().trim());
		userRoleInf.setOptstatus(CommonConst.OPTSTATUS_NORMAL);
		List<SysRoleFunc> list = new ArrayList<SysRoleFunc>();
		if(CommonConst.ROLE_MODE_UNBIND_ORG.equals(roleMode)){
			//角色不绑定机构
			list = sysRoleFuncReponsitory.querySysRoleFuncNoOrg(userRoleInf);
		}else if(CommonConst.ROLE_MODE_BIND_ORG.equals(roleMode)){
			//机构与角色有关 
			// 查询当前用户、当前登录机构的功能权限
			list = sysRoleFuncReponsitory.querySysRoleFunc(userRoleInf);
		}else{
			//两者兼容 
			list = sysRoleFuncReponsitory.querySysRoleFunc(userRoleInf);
			if(list.size()==0){
				list = sysRoleFuncReponsitory.querySysRoleFuncNoOrg(userRoleInf);
			}
		}
		for (SysRoleFunc sysRoleFunc : list) {
			map_authority.put(sysRoleFunc.getFuncId().trim(), null);
		}
		// 存入session
		ContextConst.setAttribute(CommonConst.USERFUNC_SESSION, map_authority);
	}

	@Autowired
	protected SysParamRepository sysParamRepos;
}
