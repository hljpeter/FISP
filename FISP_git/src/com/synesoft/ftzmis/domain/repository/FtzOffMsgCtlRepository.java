package com.synesoft.ftzmis.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;

/**
 * 表外报文控制表(DAT_OFF_MSG_CTL)
 * @author yyw
 * @date 2013-12-25
 */
public interface FtzOffMsgCtlRepository {	

	/**
	 * 查询一页数据(DAT_OFF_MSG_CTL - 表外报文控制表)
	 * @param pageable - 分页参数
	 * @param ftzOffMsgCtl - 封装了条件值的对象(BRANCH_ID,MSG_ID,MSG_STATUS,WORK_DATE,MSG_NO(default),EDIT_FLAG)
	 * @return
	 * 		Page<ftzOffMsgCtlVO> - 分页对象
	 */
	public Page<FtzOffMsgCtlVO> queryPage(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO);

	/**
	 * 查询一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)，根据主键
	 * @param ftzOffMsgCtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		FtzOffMsgCtl - 结果对象
	 */
	public FtzOffMsgCtl queryByPK(FtzOffMsgCtl ftzOffMsgCtl);
	
	/**
	 * 查询一页数据（审核专用，统计明细总数和待审核数量）(DAT_OFF_MSG_CTL - 表外报文控制表)
	 * @param pageable - 分页参数
	 * @param ftzOffMsgCtl - 封装了条件值的对象(BRANCH_ID,MSG_NO,MSG_STATUS,WORK_DATE)
	 * @return
	 * 		Page<ftzOffMsgCtlVO> - 分页对象
	 */
	public Page<FtzOffMsgCtlVO> queryPageForApr(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO);

	/**
	 * 新增一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)
	 * @param ftzOffMsgCtl - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int insert(FtzOffMsgCtl ftzOffMsgCtl);

	/**
	 * 更新一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)，为修改提供，根据MSG_ID, MAK_DATETIME, MSG_STATUS(原)
	 * @param ftzOffMsgCtlVO - 封装了值的对象(MAK_DATETIME, MAK_USER_ID, EDIT_FLAG, BRANCH_ID, WORK_DATE, MSG_STATUS)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int updateMsg(FtzOffMsgCtlVO ftzOffMsgCtlVO);

	/**
	 * 删除一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)，为删除提供，根据MSG_ID, MAK_DATETIME, MSG_STATUS(原)
	 * @param ftzOffMsgCtlVO - 封装了值的对象(MSG_ID, MAK_DATETIME, MSG_STATUS)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int deleteMsg(FtzOffMsgCtlVO ftzOffMsgCtlVO);

	/**
	 * 更新一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)，为提交和审核等
	 * @param ftzOffMsgCtlVO - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int updateMsgStatus(FtzOffMsgCtlVO ftzOffMsgCtlVO);
	
	//更新表外表头状态(发送报文使用)
	public int updateFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);

}
