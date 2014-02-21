/**
 * 
 */
package com.synesoft.fisp.domain.repository;

import java.util.List;

import com.synesoft.fisp.domain.model.SysControlDept;

/**
 * @author Peter
 * @date 2014-2-18 下午3:39:04
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

public interface SysControlDeptRepository {
	
	public List<SysControlDept> queryListByPageAll(SysControlDept sysControlDept);
	
	public List<SysControlDept> queryListByPageCheck(SysControlDept sysControlDept);
	
	public List<SysControlDept> queryListForPage();
	
	public SysControlDept querySysControlDept(SysControlDept sysControlDept);

}
