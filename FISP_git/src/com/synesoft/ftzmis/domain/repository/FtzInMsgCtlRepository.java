package com.synesoft.ftzmis.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;

public interface FtzInMsgCtlRepository {	

	/**
	 * 查询一页数据(DAT_OFF_MSG_CTL - 表内报文控制表)
	 * @param pageable - 分页参数
	 * @param ftzInMsgCtl - 封装了条件值的对象(BRANCH_ID,MSG_ID,MSG_STATUS,WORK_DATE,MSG_NO(default),EDIT_FLAG)
	 * @return
	 * 		Page<ftzInMsgCtlVO> - 分页对象
	 */
	public Page<FtzInMsgCtlVO> queryPage(Pageable pageable, FtzInMsgCtlVO ftzInMsgCtlVO);

	/**
	 * 查询一条数据(DAT_OFF_MSG_CTL - 表内报文控制表)，根据主键
	 * @param ftzInMsgCtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		FtzInMsgCtl - 结果对象
	 */
	public FtzInMsgCtl queryByPK(FtzInMsgCtl ftzInMsgCtl);
	
	/**
	 * 查询一页数据（审核专用，统计明细总数和待审核数量）(DAT_OFF_MSG_CTL - 表内报文控制表)
	 * @param pageable - 分页参数
	 * @param ftzInMsgCtl - 封装了条件值的对象(BRANCH_ID,MSG_NO,MSG_STATUS,WORK_DATE)
	 * @return
	 * 		Page<ftzInMsgCtlVO> - 分页对象
	 */
	public Page<FtzInMsgCtlVO> queryPageForApr(Pageable pageable, FtzInMsgCtlVO ftzInMsgCtlVO);

	/**
	 * 新增一条数据(DAT_OFF_MSG_CTL - 表内报文控制表)
	 * @param ftzInMsgCtl - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int insert(FtzInMsgCtl ftzInMsgCtl);

	/**
	 * 更新一条数据(DAT_OFF_MSG_CTL - 表内报文控制表)，为修改和删除提供，根据MSG_ID, MAK_DATETIME, MSG_STATUS(原)
	 * @param ftzInMsgCtlVO - 封装了值的对象(MAK_DATETIME, MAK_USER_ID, EDIT_FLAG, BRANCH_ID, WORK_DATE, MSG_STATUS)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int updateMsg(FtzInMsgCtl ftzInMsgCtl);

	/**
	 * 更新一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)，为提交和审核等
	 * @param ftzInMsgCtlVO - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int updateMsgStatus(FtzInMsgCtl ftzInMsgCtl);
	
	public int delete(FtzInMsgCtl ftzInMsgCtl);
	
	//更新审核状态
	public int updateAuth(FtzInMsgCtl ftzInMsgCtl) ;
	
	//更新报文通讯和反馈状态,通讯功能使用
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);

}

