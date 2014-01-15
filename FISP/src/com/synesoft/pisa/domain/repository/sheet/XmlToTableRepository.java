package com.synesoft.pisa.domain.repository.sheet;

import java.util.List;

import com.synesoft.pisa.domain.model.XmlToTable;

/**
 * @author 李峰
 * @date 2013-12-27 下午2:55:14
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface XmlToTableRepository {

	public List<XmlToTable> queryXmlToTable(String xmlType);

}
