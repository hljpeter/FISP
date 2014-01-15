package com.synesoft.fisp.domain.service.cm;

import java.util.List;
import java.util.Map;

import com.synesoft.fisp.domain.model.MenuFunc;
import com.synesoft.fisp.domain.model.TipsConn;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserOrgInf;

/**
 * @author zhongHubo
 * @date 2013年7月15日 16:58:25
 * @version 1.0
 * @Description 公共Service
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface CommonService {

	/**
	 * 获取菜单信息
	 * 
	 * @param userInf	当前用户信息
	 * @return String[strbuf_header, strbuf_left]
	 */
	public String[] transQueryMenuList(UserInf userInf);
	
	/**
	 * 获取菜单明细信息
	 * 
	 * @param menuFunc	菜单信息
	 * @return
	 */
	public MenuFunc transMenuDetail(MenuFunc menuFunc);
	
	/**
	 * 查询当前用户的TipsConn信息
	 * @param userInf
	 * @return
	 */
	public TipsConn queryTipsConn(UserInf userInf);
	
	/**
	 * 获取当前用户可选择登录的所有机构信息
	 * @param userInf
	 * @return
	 */
	public List<UserOrgInf> queryUserOrgList(UserInf userInf);
	
	public Map<String , Object> initParamLoader();
	
	/**
	 * 查询当前用户的功能权限
	 */
	public void queryAuthority();
	
}
