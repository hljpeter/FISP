package com.synesoft.fisp.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DpExprParaval;

/**
 * @author zhongHubo
 * @date 2013年12月4日 19:56:41
 * @version 1.0
 * @Description 表达式参数值Repository实现类
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository("dpExprParavalRepository")
public class DpExprParavalRepositoryImpl implements DpExprParavalRepository {
	
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DpExprParaval> pageDpExprParaval;
	
	/**
	 * 根据主键获取表达式参数值对象
	 * @param dpExprParaval
	 * @return
	 */
	@Override
	public DpExprParaval queryDpExprParavalByExpId(DpExprParaval dpExprParaval) {
		return queryDAO.executeForObject(Table.DP_EXPR_PARAVAL + "."
				+ SQLMap.SELECT_BYKEY, dpExprParaval, DpExprParaval.class);
	}
	
	/**
	 * 批量执行
	 * @param list
	 * @return
	 */
	@Override
	public int bathUpdate(List<SqlHolder> list) {
		return updateDAO.executeBatch(list);
	}
	
	/**
	 * 删除无效的数据
	 */
	@Override
	public int deleteByUseFlag(DpExprParaval dpExprParaval) {
		return updateDAO.execute(Table.DP_EXPR_PARAVAL + "." + SQLMap.DeleteByUseFlag, dpExprParaval);
	}
	
	/**
	 * 修改全部数据的useFlag
	 */
	@Override
	public int updateByUseFlag(DpExprParaval dpExprParaval) {
		return updateDAO.execute( Table.DP_EXPR_PARAVAL + CommonConst.POINT + SQLMap.UpdateByUseFlag, dpExprParaval);
	}
	
	/**
	 * 分页查询所有无效数据
	 */
	@Override
	public Page<DpExprParaval> queryDpExprParavalPage(Pageable pageable,DpExprParaval dpExprParaval) {
		return pageDpExprParaval.getPage(Table.DP_EXPR_PARAVAL, SQLMap.SELECT_COUNTS,
				SQLMap.SelectByUseFlag, dpExprParaval, pageable);
	}

}
