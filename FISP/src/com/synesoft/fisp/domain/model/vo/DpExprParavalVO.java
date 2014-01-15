package com.synesoft.fisp.domain.model.vo;

/**
 * @author zhongHubo
 * @date 2013年12月4日 20:15:51
 * @version 1.0
 * @Description 表达式参数值VO类
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class DpExprParavalVO {

	/** 序列号 */
	private String seqNo;
	
	/** 方法名 */
	private String methodName;
	
	/** 未解析的值 */
	private String noAnalyValue;
	
	/** 已解析的值 */
	private String analyValue;
	
	/** 当前值的类型 */
	private String paramType;

	
	public DpExprParavalVO() {
		
	}
	
	public DpExprParavalVO(String seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNoAnalyValue() {
		return noAnalyValue;
	}

	public void setNoAnalyValue(String noAnalyValue) {
		this.noAnalyValue = noAnalyValue;
	}

	public String getAnalyValue() {
		return analyValue;
	}

	public void setAnalyValue(String analyValue) {
		this.analyValue = analyValue;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	
}
