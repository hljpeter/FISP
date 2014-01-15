package com.synesoft.fisp.domain.service;

public interface NumberService {
	
	/**
	 * 
	 * @param MsgNo
	 * @return
	 */
	public String getSysIDSequence(String MsgNo,int len);
	
	public String getSysIDSequence(int len);

}
