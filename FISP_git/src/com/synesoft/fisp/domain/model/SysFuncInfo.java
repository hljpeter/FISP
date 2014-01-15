package com.synesoft.fisp.domain.model;

public class SysFuncInfo {
	
	private int level;
	
	private int connectByIsleaf;
	
    private String funcId;

    
    private String funcDesc;

    
    private String funcI18n;

    
    private String supFuncId;

    
    private Short funcLvl;

    
    private String funcUrl;

    
    private String funcPara;

    
    private String createTime;

    
    private String createUser;

    
    private String updateTime;

    
    private String updateUser;

    
    private String rsv1;

    
    private String rsv2;

    
    private String rsv3;

    
    private String rsv4;

    
    private String rsv5;

    
    public String getFuncId() {
        return funcId;
    }

    
    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    
    public String getFuncDesc() {
        return funcDesc;
    }

    
    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }

    
    public String getFuncI18n() {
        return funcI18n;
    }

    
    public void setFuncI18n(String funcI18n) {
        this.funcI18n = funcI18n;
    }

    
    public String getSupFuncId() {
        return supFuncId;
    }

    
    public void setSupFuncId(String supFuncId) {
        this.supFuncId = supFuncId;
    }
    
    public String getFuncUrl() {
        return funcUrl;
    }

    
    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    
    public String getRsv1() {
        return rsv1;
    }

    
    public void setRsv1(String rsv1) {
        this.rsv1 = rsv1;
    }

    
    public String getRsv2() {
        return rsv2;
    }

    
    public void setRsv2(String rsv2) {
        this.rsv2 = rsv2;
    }

    
    public String getRsv3() {
        return rsv3;
    }

    
    public void setRsv3(String rsv3) {
        this.rsv3 = rsv3;
    }

    
    public String getRsv4() {
        return rsv4;
    }

    
    public void setRsv4(String rsv4) {
        this.rsv4 = rsv4;
    }

    
    public String getRsv5() {
        return rsv5;
    }

    
    public void setRsv5(String rsv5) {
        this.rsv5 = rsv5;
    }

	public Short getFuncLvl() {
		return funcLvl;
	}

	public void setFuncLvl(Short funcLvl) {
		this.funcLvl = funcLvl;
	}

	public String getFuncPara() {
		return funcPara;
	}

	public void setFuncPara(String funcPara) {
		this.funcPara = funcPara;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	
	public int getLevel() {
		return level;
	}

	
	public void setLevel(int level) {
		this.level = level;
	}

	
	public int getConnectByIsleaf() {
		return connectByIsleaf;
	}

	
	public void setConnectByIsleaf(int connectByIsleaf) {
		this.connectByIsleaf = connectByIsleaf;
	}

}