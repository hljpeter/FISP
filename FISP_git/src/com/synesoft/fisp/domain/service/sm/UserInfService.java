package com.synesoft.fisp.domain.service.sm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.app.sm.model.UserInfTmpForm;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserInfTmp;

/**
 * @author zhongHubo
 * @date 2013年7月8日 15:56:26
 * @version 1.0
 * @Description 用户操作Service
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface UserInfService {

	/**
	 * 根据用户Id查询用户信息
	 * @param userid	用户Id
	 * @return			UserInf
	 */
	public UserInf queryUser(String userid);
	
	/**
	 * 修改用户信息
	 * @param userInf	待修改的用户信息
	 * @return			成功与否
	 */
	public boolean updateUser(UserInf userInf);
	
	/**
	 * 用户信息维护查询功能
	 * @param pageable
	 * @param userInf
	 * @return
	 */
	public Page<UserInf> transQueryUserInfList(Pageable pageable,
			UserInf userInf);
	

	/**
	 * 用户信息维护查询功能
	 * @param pageable
	 * @param userInfTmp
	 * @return
	 */
	public Page<UserInfTmp> transQueryUserInfTmpList(Pageable pageable,
			UserInfTmp userInfTmp);
	
	/**
	 * 查询用户信息表
	 * @param userInfTmp
	 * @return
	 */
	public UserInf transQueryUserInf(UserInf userInf);
	
	/**
	 * 查询用户信息表
	 * @param userInfTmp
	 * @return
	 */
	public UserInfTmp transQueryUserInfTmp(UserInfTmp userInfTmp);
	/**
	 * 新增操作
	 * 1.检查主表和临时表中是否存在相同用户ID的记录
	 * 2.修改主表中处理状态字段
	 * 3.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public int transAdd(UserInfTmpForm form);
	
	/**
	 * <p>新增操作</p>
	 * <p>1.检查主表和临时表中是否存在相同用户ID的记录</p>
	 * <p>2.修改主表中处理状态字段</p>
	 * <p>3.临时表中新增一条记录</p>
	 * @param form
	 * @return
	 */
	public void transAddByMode(UserInfTmpForm form);

	/**
	 * <p>修改操作</p>
	 * <p>1.检查主表中该记录是否处于正常状态</p>
	 * <p>2.检查临时表中是否已经存在相同用户ID的记录</p>
	 * <p>3.修改主表中处理状态字段</p>
	 * <p>4.临时表中新增一条记录</p>
	 * @param form
	 * @param optType  02-修改 03-删除 
	 * @return
	 */
	public void transUpdateByMode(UserInfForm form, String optType);

	/**
	 * <p>授权操作</p>
	 * <p>1.检查临时表中该记录的状态是否为待授权</p>
	 * <p>2.更新临时表中记录状态</p>
	 * <p>3.根据操作类型分别处理</p>
	 * <p>&nbsp;&nbsp;3.1.新增操作，将记录信息插入到主表中</p>
	 * <p>&nbsp;&nbsp;3.2.修改操作，将更新主表中的记录信息</p>
	 * <p>&nbsp;&nbsp;3.3.删除操作，删除主表中该记录信息</p>
	 * @param form
	 * @return
	 */
	public void transAuthByMode(UserInfTmpForm form);

	/**
	 * <p>拒绝操作</p>
	 * <p>1.检查临时表中该记录的状态是否为待授权</p>
	 * <p>2.更新临时表中记录状态</p>
	 * <p>3.当维护操作类型为修改或删除时，更新主表的处理状态为02-正常状态</p>
	 * @param form
	 * @return
	 */
	public void transRejectByMode(UserInfTmpForm form);
	
	/*****************************************新增临时start******************************************/
	/**
	 * 新增操作
	 * 1.检查主表和临时表中是否存在相同用户ID的记录
	 * 2.修改主表中处理状态字段
	 * 3.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transAdd1(UserInfTmpForm form);
	/*****************************************新增操作临时end******************************************/
	
	/**
	 * 修改/删除操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同用户ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @param optType  02-修改 03-删除 
	 * @return
	 */
	public int transUpdate(UserInfForm form,String optType);
/*****************************************修改/删除操作临时start******************************************/	
	/**
	 * 修改/删除操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同用户ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @param optType  02-修改 03-删除 
	 * @return
	 */
	public int transUpdate1(UserInfForm form,String optType);
/*****************************************修改/删除操作临时end******************************************/		

	/**
	 * 授权操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.根据操作类型分别处理
	 * 	3.1.新增操作，将记录信息插入到主表中
	 * 	3.2.修改操作，将更新主表中的记录信息
	 * 	3.3.删除操作，删除主表中该记录信息
	 * @param form
	 * @return
	 */
/*****************************************授权临时start******************************************/
	public int transAuth1(UserInfTmpForm form);
	
	/**
	 * 授权操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.根据操作类型分别处理
	 * 	3.1.新增操作，将记录信息插入到主表中
	 * 	3.2.修改操作，将更新主表中的记录信息
	 * 	3.3.删除操作，删除主表中该记录信息
	 * @param form
	 * @return
	 */
	/*****************************************授权临时end******************************************/
	public int transAuth(UserInfTmpForm form);
	
	/**
	 * 拒绝操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.当维护操作类型为修改或删除时，更新主表的处理状态为02-正常状态
	  * @param userInfTmp
	 * @return
	 */
	public int transRejct(UserInfTmpForm form);
	
	/*****************************************拒绝临时end******************************************/
	/**
	 * 拒绝操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.当维护操作类型为修改或删除时，更新主表的处理状态为02-正常状态
	  * @param userInfTmp
	 * @return
	 */
	public int transRejct1(UserInfTmpForm form);
	/*****************************************拒绝临时end******************************************/
	
	/**
	 * 根据用户ID及系统中目前的操作状态重置用户状态为01-正常
	 * @param form
	 * @param status
	 */
	public void transSetStatusNormal(UserInfForm form,String status);
	
	/**
	 * 重置用户密码信息
	 * @param form
	 * @return
	 */
	public String transPasswordReset(UserInfForm form);
}
