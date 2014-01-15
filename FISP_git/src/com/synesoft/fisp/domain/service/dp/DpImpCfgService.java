package com.synesoft.fisp.domain.service.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.vo.DpImpCfgDtlVO;

/**
 * 数据导入映射Service
 * @date 2013-11-11
 * @author yyw
 *
 */
public interface DpImpCfgService {

	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（impId)
	 * @return
	 * 		DpImpCfg	结果对象
	 */
	public DpImpCfg transQueryDpImpCfg(String id);
	
	/**
	 * 查询一条记录，根据逻辑主键(projId + branchId + batchNo + seqNo)
	 * @param dpImpCfg	逻辑主键对象
	 * @return
	 */
	public DpImpCfg transQueryDpImpCfgLogicKey(DpImpCfg dpImpCfg);
	
	/**
	 * 查询一页记录
	 * @param pageable	分页对象
	 * @param dpImpCfg	条件对象
	 * @return
	 * 		Page<DpImpCfg>	结果对象
	 */
	public Page<DpImpCfg> transQueryDpImpCfgPage(Pageable pageable, DpImpCfg dpImpCfg);

	/**
	 * 查询所有符合条件的记录
	 * @param dpImpCfg	条件对象
	 * @return
	 * 		List<DpImpCfg>	结果对象
	 */
	public List<DpImpCfg> transQueryDpImpCfgList(DpImpCfg dpImpCfg);
	
	/**
	 * 删除一条记录，根据主键，修改时间和修改用户
	 * @param dpImpCfg	条件对象
	 */
	public void transDel(DpImpCfg dpImpCfg);

	/**
	 * 新增一条记录
	 * @param dpImpCfg	条件对象
	 */
	public void transAdd(DpImpCfg dpImpCfg);
	
	/**
	 * 查询详细信息，根据主键
	 * @param id	主键（IMP_ID）
	 * @return
	 * 		List<Object>	结果List，包括4个值，分别为
	 * 		<p>第一个，配置信息对象 DpImpCfg</p>
	 * 		<p>第二个，配置信息明细列表 DpImpCfgDtl</p>
	 * 		<p>第三个，文件定义明细列表 DpFileDtl</p>
	 * 		<p>第四个，表定义明细列表 DpTableDtl</p>
	 */
	public List<Object> transQueryDetail(String id);
	
	/**
	 * 更新一条记录
	 * @param dpImpCfg	更新记录对象
	 */
	public void transUpt(DpImpCfg dpImpCfg);

	/**
	 * 配置数据导入字段映射
	 * @param id	数据导入ID
	 * @param list	明细配置List
	 */
	public void transAddDtl(String id, List<DpImpCfgDtlVO> list);
	
}
