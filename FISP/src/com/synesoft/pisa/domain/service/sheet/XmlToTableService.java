package com.synesoft.pisa.domain.service.sheet;

import java.util.List;

import com.synesoft.pisa.domain.model.XmlToTable;

/**
 * @author 李峰
 * @date 2013-12-27 下午3:27:02
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface XmlToTableService {

	public List<XmlToTable> queryXmlToTable(String xmlType);
}
