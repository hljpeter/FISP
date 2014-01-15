package com.synesoft.fisp.domain.service.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义Service
 * @date 2013-11-11
 * @author yyw
 *
 */
public interface DpTableService {
	
	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（tableId)
	 * @return
	 * 		DpTable	结果对象
	 */
	public DpTable transQueryDpTable(String id);

	/**
	 * 查询一页记录
	 * @param pageable	分页对象
	 * @param dpTable	条件对象
	 * @return
	 * 		Page<DpTable>	结果对象
	 */
	public Page<DpTable> transQueryDpTablePage(Pageable pageable, DpTable dpTable);

	/**
	 * 查询所有符合条件的记录
	 * @param dpTable	条件对象
	 * @return
	 * 		List<DpTable>	结果对象
	 */
	public List<DpTable> transQueryDpTableList(DpTable dpTable);
	
	/**
	 * 查询表定义详细信息，根据主键
	 * @param pageable	分页对象
	 * @param id	主键（TABLE_ID）
	 * @return
	 * 		List<Object>	结果List
	 */
	public List<Object> transQueryDetail(Pageable pageable, String id); 

	/**
	 * 查询表定义详细信息，根据主键
	 * @param id	主键（TABLE_ID）
	 * @return
	 * 		List<Object>	结果List
	 */
	public List<Object> transQueryDetailForUpt(String id); 
	
	/**
	 * 删除一条记录，根据主键，修改时间和修改用户
	 * @param dpTable	条件对象
	 */
	public void transDel(DpTable dpTable);

	/**
	 * 新增记录，文件定义信息和文件定义明细信息
	 * @param dpTable	文件定义对象
	 * @param list	文件定义明细对象
	 */
	public void transAdd(DpTable dpTable, List<DpTableDtl> list);

	/**
	 * 修改记录，文件定义信息和文件定义明细信息
	 * @param dpTable	文件定义对象
	 * @param list	文件定义明细对象
	 */
	public void transUpt(DpTable dpTable, List<DpTableDtl> list);

}
