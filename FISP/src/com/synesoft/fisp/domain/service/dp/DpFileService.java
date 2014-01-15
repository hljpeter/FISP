package com.synesoft.fisp.domain.service.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;

/**
 * 文件定义Service
 * @date 2013-11-12
 * @author yyw
 *
 */
public interface DpFileService {
	
	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（fileId)
	 * @return
	 * 		DpFile	结果对象
	 */
	public DpFile transQueryDpFile(String id);

	/**
	 * 查询一页记录
	 * @param pageable	分页对象
	 * @param dpFile	条件对象
	 * @return
	 * 		Page<DpFile>	结果对象
	 */
	public Page<DpFile> transQueryDpFilePage(Pageable pageable, DpFile dpFile);

	/**
	 * 查询所有符合条件的记录
	 * @param dpFile	条件对象
	 * @return
	 * 		List<DpFile>	结果对象
	 */
	public List<DpFile> transQueryDpFileList(DpFile dpFile);

	/**
	 * 查询文件名，以某个字符开头的
	 * @param name	开头字符
	 * @return
	 * 		List<String>	结果对象
	 */
	public List<String> transQueryDpFileNameList(String name);
	
	/**
	 * 查询文件定义详细信息
	 * @param id	文件定义ID
	 * @return
	 * 		List<Object>	结果List
	 */
	public List<Object> transQueryDetail(String id);

	/**
	 * 查询文件定义详细信息
	 * @param id	文件定义ID
	 * @return
	 * 		List<Object>	结果List
	 */
	public List<Object> transQueryDetailForUpt(String id);
	
	/**
	 * 删除一条记录，根据主键，修改时间和修改用户
	 * @param dpFile	条件对象
	 */
	public void transDel(DpFile dpFile);

	/**
	 * 新增一条记录
	 * @param dpFile	文件定义对象
	 * @param list	文件定义明细列表
	 */
	public void transAdd(DpFile dpFile, List<DpFileDtl> list);

	/**
	 * 更新一条记录，根据主键，修改时间和修改用户
	 * @param dpFile	文件定义对象
	 * @param list	文件定义明细列表
	 */
	public void transUpt(DpFile dpFile, List<DpFileDtl> list);

}
