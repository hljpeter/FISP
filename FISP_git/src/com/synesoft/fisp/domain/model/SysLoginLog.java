package com.synesoft.fisp.domain.model;

/**
 * @author yyw
 *
 */
public class SysLoginLog {
	private String id;
	private String userid;
	private String loginorg;
	private String type;
	private String result;
	private String failSeason;
	private String ip;
	private String machineName;
	private String loginDatetime;
	
	/**
	 * @return the failSeason
	 */
	public String getFailSeason() {
		return failSeason;
	}
	/**
	 * @param failSeason the failSeason to set
	 */
	public void setFailSeason(String failSeason) {
		this.failSeason = failSeason;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the loginorg
	 */
	public String getLoginorg() {
		return loginorg;
	}
	/**
	 * @param loginorg the loginorg to set
	 */
	public void setLoginorg(String loginorg) {
		this.loginorg = loginorg;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}
	/**
	 * @param machineName the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	/**
	 * @return the loginDatetime
	 */
	public String getLoginDatetime() {
		return loginDatetime;
	}
	/**
	 * @param loginDatetime the loginDatetime to set
	 */
	public void setLoginDatetime(String loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

}
