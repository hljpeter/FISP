package com.synesoft.fisp.domain.repository.dp;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义明细Repository
 * @date 2013-11-13
 * @author yyw
 *
 */
public interface DpTableDtlRepository {
	
	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（COL_ID）
	 * @return
	 * 		DpTableDtl	一条记录的对象
	 */
	public DpTableDtl query(String id);
	
	/**
	 * 查询表定义下的所有明细信息
	 * @param tableId	表定义ID
	 * @return
	 * 		List<DpTableDtl>	结果List
	 */
	public List<DpTableDtl> queryList(String tableId);

	/**
	 * 查询表定义下的所有明细信息
	 * @param tableId	表名称
	 * @return
	 * 		List<Map<String, Object>>	结果Map，名称和长度对
	 */
	public List<Map<String, Object>> queryMap(String tableName);

	/**
	 * 查询表定义下的一页表定义明细数据
	 * @param tableId	表定义ID
	 * @return
	 * 		Page<DpTableDtl>	结果Page
	 */
	public Page<DpTableDtl> queryPage(Pageable pageable, String tableId);

	/**
	 * 查询表定义下的所有明细信息
	 * @param tableName	表名
	 * @return
	 * 		List<DpTableDtl>	结果List
	 */
	public List<DpTableDtl> queryListForName(String tableName);
	
	/**
	 * 查询表定义下的未被使用的明细信息
	 * @param tableName	表名
	 * @return
	 * 		List<DpTableDtl>	结果List
	 */
	public List<DpTableDtl> queryNotUsedListForName(String tableName);
	
	/**
	 * 插入一条记录
	 * @param dpTableDtl	封装了值的对象
	 * @return
	 * 		int	成功插入的记录数
	 */
	public int insert(DpTableDtl dpTableDtl);
	
	/**
	 * 删除一条记录，根据表定义ID
	 * @param tableId	表定义ID
	 * @return
	 * 		int	成功删除的记录数
	 */
	public int delete(String tableId);
}
