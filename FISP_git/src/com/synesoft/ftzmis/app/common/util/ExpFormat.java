package com.synesoft.ftzmis.app.common.util;

import java.text.DecimalFormat;

/**
 * 格式化导出值
 * @author Han
 *
 */
public class ExpFormat {
	
	static final String amount_format_1 = "##0.00";
	static final String amount_format_2 = "##0.000000";
	/**
	 * 格式化金额为小数点后两位(##0.00)
	 * @param value
	 * @return
	 */
	public static String formatAmountTwoScale(String value) {
		DecimalFormat fmt = new DecimalFormat(amount_format_1);
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(value);
			outStr = fmt.format(d);
		} catch (Exception e) {
		}
		return outStr;
	}
	
	/**
	 * 格式化金额为小数点后两位(##0.00)
	 * @param value
	 * @return
	 */
	public static String formatAmountSixScale(String value) {
		DecimalFormat fmt = new DecimalFormat(amount_format_2);
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(value);
			outStr = fmt.format(d);
		} catch (Exception e) {
		}
		return outStr;
	}

}
