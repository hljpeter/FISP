package com.synesoft.pisa.app.common.formulacheck;

import com.synesoft.pisa.app.common.constants.CommonConst;


public class FormulaParser {
	
	private StringBuffer sb=new StringBuffer();

	public String getFormulaStr(String str){
		if(str.length()>0||!str.equals("")){
			int startIndex=0;
			int endIndex=0;
			startIndex=str.indexOf("[");
			endIndex=str.indexOf("]");
			if(startIndex!=0&&endIndex!=0){
				sb.append(str.substring(startIndex,endIndex+1));
				if(str.length()>endIndex+2){
					if(str.substring(endIndex+1).startsWith(CommonConst.equal_seperator)){
						sb.append(str.charAt(endIndex+1));
						str=str.substring(endIndex+1);
						getFormulaStr(str);
					}else if((str.substring(endIndex+2).startsWith(CommonConst.equal_seperator))){
						sb.append(str.substring(endIndex+1, endIndex+3));
						str=str.substring(endIndex+2);
						getFormulaStr(str);
					}else if(str.substring(endIndex+1).startsWith(CommonConst.plus_seperator)){
						sb.append(str.charAt(endIndex+1));
						str=str.substring(endIndex+2);
						if(str.startsWith("......")){
							int index=str.indexOf(CommonConst.plus_seperator);
							sb.append(str.substring(0, index+1));
							str=str.substring(index+1);
						}
						getFormulaStr(str);
					}
				}
			}
		}
		return sb.toString();
	}
}
