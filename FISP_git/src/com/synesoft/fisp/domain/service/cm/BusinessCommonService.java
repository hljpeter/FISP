/**
 * 
 */
package com.synesoft.fisp.domain.service.cm;

/**
 * @author Peter
 * @date 2014-2-18 上午11:39:10
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

public interface BusinessCommonService {
	
	public void commonBusinessValidate(String pageId,Object obj);
	
	public boolean isValidate(String department,String pageId,String controlId);

}
