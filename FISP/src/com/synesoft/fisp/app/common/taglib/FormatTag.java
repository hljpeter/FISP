package com.synesoft.fisp.app.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.synesoft.fisp.app.common.utils.StringUtil;



/**
 * <!-- 时间格式化 yyyyMMdd -->
 * 
 * @author liming.feng
 *
 */
public class FormatTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 4483361655253585796L;
	public static final String DATE_FORMATE_YYYYMMDD ="0";
	public static final String DATE_FORMATE_YYYYMMDDHHMMSS ="1";
	/**
	 * 控件中使用账号字符串，如:20120608165327或者20120608
	 */
	private String inputValue;
	
	
	//格式类型
	private String formatType;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			// 如果url置空，允许显示
			if (inputValue == null || formatType == null) return EVAL_BODY_INCLUDE;
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e.toString());
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			String formatString="";
			//格式化成yyyyMMdd格式
			if(DATE_FORMATE_YYYYMMDD.equals(formatType)){
				 if(!StringUtil.isBlank(inputValue) && inputValue.length()==8){
					 formatString=inputValue.substring(0,4)+"-"+inputValue.substring(4,6)+"-"+inputValue.substring(6,8);
				 }
			}else if(DATE_FORMATE_YYYYMMDDHHMMSS.equals(formatType)){
				 if(!StringUtil.isBlank(inputValue) && inputValue.length()==14){
					 formatString=inputValue.substring(0,4)+"-"+inputValue.substring(4,6)+"-"+inputValue.substring(6,8)+"  "+inputValue.substring(8, 10)+":"+inputValue.substring(10, 12)+":"+inputValue.substring(8, 10);
				 }
			}
			out.print(formatString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	
	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
}
