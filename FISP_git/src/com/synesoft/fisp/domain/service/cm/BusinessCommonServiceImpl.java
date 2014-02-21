/**
 * 
 */
package com.synesoft.fisp.domain.service.cm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.SysControlDept;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.repository.SysControlDeptRepository;

/**
 * @author Peter
 * @date 2014-2-18 上午11:39:26
 * @version 1.0
 * @description
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

@Service("businessCommonService")
public class BusinessCommonServiceImpl implements BusinessCommonService {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synesoft.fisp.domain.service.cm.BusinessCommonService#
	 * commonBusinessValidate(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void commonBusinessValidate(String pageId, Object obj) {
		ResultMessages messages = ResultMessages.error();
		SysControlDept query_SysControlDept = new SysControlDept();
		UserInf userInfo = ContextConst.getCurrentUser();
		query_SysControlDept.setControlDept(userInfo.getDepartment());
		query_SysControlDept.setPageId(pageId);
		List<SysControlDept> sysControlDepts = sysControlDeptRepos.queryListByPageCheck(query_SysControlDept);
		Class clazz = obj.getClass();

		if (null != sysControlDepts) {
			for (int i = 0; i < sysControlDepts.size(); i++) {
				Object objValue = null;
				String getValue = "get" + sysControlDepts.get(i).getControlId();
				Method method;
				try {
					method = clazz.getMethod(getValue, new Class[] {});
					objValue = method.invoke(obj);
					if (objValue instanceof String) {
						if (!StringUtil.isNotTrimEmpty((String) objValue)) {
							ResultMessage resultMessage = ResultMessage
									.fromCode("e.fisp.commonValidate."
											+ sysControlDepts.get(i).getControlId());
							messages.add(resultMessage);
						}
					} else {
						if (null == objValue) {
							ResultMessage resultMessage = ResultMessage
									.fromCode("e.fisp.commonValidate."
											+ sysControlDepts.get(i).getControlId());
							messages.add(resultMessage);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if (messages.isNotEmpty()) {
					throw new BusinessException(messages);
				}
			}
		}
	}
	
	public boolean isValidate(String department,String pageId,String controlId){
		SysControlDept query_SysControlDept = new SysControlDept();
		query_SysControlDept.setControlDept(department);
		query_SysControlDept.setPageId(pageId);
		query_SysControlDept.setControlId(controlId);
		SysControlDept sysControlDept = sysControlDeptRepos.querySysControlDept(query_SysControlDept);
		if(null != sysControlDept && "0".equals(sysControlDept.getCheckFlag())){
			return true;
		}else{
			return false;
		}
	}
	
	@Resource
	protected SysControlDeptRepository sysControlDeptRepos;
}
