package com.synesoft.fisp.app.dp.model;

import java.util.List;
import java.util.Map;

import com.synesoft.fisp.domain.model.DpExprMethod;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.vo.DpExprMethodDtlVO;

public class Dp_ExpForm {

	private List<DpExprMethod> dpExprMethods;
	
	private List<List<DpExprMethodDtlVO>> dpExprMethodDtls;
	
	private List<DpTableDtl> dpTableDtls;
	
	private List<DpExprTmp> dpExprTmps;
	
	private Map<String ,List<String[]>> dpMethodParamVals;
	
	private String selected_mothedName ="";
	
	private String express = "";
	
	private String express_key = "";
	
	private String srcTable = "";
	
	private String oldExpress = "";
	
	private String oldExpress_key = "";
	
	private String dp_type = "";
	
	private String cfg_Id="";
	
	private String branch_Id="";
	
	public List<DpExprMethod> getDpExprMethods() {
		return dpExprMethods;
	}

	public void setDpExprMethods(List<DpExprMethod> dpExprMethods) {
		this.dpExprMethods = dpExprMethods;
	}

	public List<List<DpExprMethodDtlVO>> getDpExprMethodDtls() {
		return dpExprMethodDtls;
	}

	public void setDpExprMethodDtls(List<List<DpExprMethodDtlVO>> dpExprMethodDtls) {
		this.dpExprMethodDtls = dpExprMethodDtls;
	}

	public List<DpTableDtl> getDpTableDtls() {
		return dpTableDtls;
	}

	public void setDpTableDtls(List<DpTableDtl> dpTableDtls) {
		this.dpTableDtls = dpTableDtls;
	}

	public List<DpExprTmp> getDpExprTmps() {
		return dpExprTmps;
	}

	public void setDpExprTmps(List<DpExprTmp> dpExprTmps) {
		this.dpExprTmps = dpExprTmps;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getSrcTable() {
		return srcTable;
	}

	public void setSrcTable(String srcTable) {
		this.srcTable = srcTable;
	}

	public String getOldExpress() {
		return oldExpress;
	}

	public void setOldExpress(String oldExpress) {
		this.oldExpress = oldExpress;
	}

	public String getSelected_mothedName() {
		return selected_mothedName;
	}

	public void setSelected_mothedName(String selected_mothedName) {
		this.selected_mothedName = selected_mothedName;
	}

	public Map<String, List<String[]>> getDpMethodParamVals() {
		return dpMethodParamVals;
	}

	public void setDpMethodParamVals(Map<String, List<String[]>> dpMethodParamVals) {
		this.dpMethodParamVals = dpMethodParamVals;
	}

	public String getExpress_key() {
		return express_key;
	}

	public void setExpress_key(String express_key) {
		this.express_key = express_key;
	}

	public String getOldExpress_key() {
		return oldExpress_key;
	}

	public void setOldExpress_key(String oldExpress_key) {
		this.oldExpress_key = oldExpress_key;
	}

	public String getDp_type() {
		return dp_type;
	}

	public void setDp_type(String dp_type) {
		this.dp_type = dp_type;
	}

	public String getCfg_Id() {
		return cfg_Id;
	}

	public void setCfg_Id(String cfg_Id) {
		this.cfg_Id = cfg_Id;
	}

	public String getBranch_Id() {
		return branch_Id;
	}

	public void setBranch_Id(String branch_Id) {
		this.branch_Id = branch_Id;
	}

}
