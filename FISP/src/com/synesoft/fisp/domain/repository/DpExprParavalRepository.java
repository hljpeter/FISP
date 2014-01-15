package com.synesoft.fisp.domain.repository;

import java.util.List;

import jp.terasoluna.fw.dao.SqlHolder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpExprParaval;

/**
 * @author zhongHubo
 * @date 2013年12月4日 19:56:06
 * @version 1.0
 * @Description 表达式参数值Repository
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface DpExprParavalRepository {

	/**
	 * 根据主键获取表达式参数值对象
	 * @param dpExprParaval
	 * @return
	 */
	public DpExprParaval queryDpExprParavalByExpId(DpExprParaval dpExprParaval);
	
	/**
	 * 批量执行
	 * @param list
	 * @return
	 */
	public int bathUpdate(List<SqlHolder> list);
	
	/**
	 * 删除无效的数据
	 */
	public int deleteByUseFlag(DpExprParaval dpExprParaval);
	
	/**
	 * 修改全部数据的useFlag
	 */
	public int updateByUseFlag(DpExprParaval dpExprParaval);
	
	/**
	 * 分页查询所有无效数据
	 */
	public Page<DpExprParaval> queryDpExprParavalPage(Pageable pageable,DpExprParaval dpExprParaval);
	
}
