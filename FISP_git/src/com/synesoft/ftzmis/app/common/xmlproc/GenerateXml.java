package com.synesoft.ftzmis.app.common.xmlproc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210101Repository;
import com.synesoft.ftzmis.domain.service.FTZOffCommonService;

@Service
public class GenerateXml {

	@Resource
	protected FTZ210101Repository ftz210101Repos;
	
	@Autowired
	private FTZOffCommonService ftz210301Service;
	
	public void writeXml(String msg_no, String msg_id) {
		String xml = "";

		if ("210101".equals(msg_no)) {
			xml = xml210101(msg_id);
		} else if ("210301".equals(msg_no)) {
			xml = xml210301(msg_id);
		}

		File filename = new File("D:\\" + msg_no + "_" + msg_id + ".xml");
		if (!filename.exists()) {
			try {
				filename.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			RandomAccessFile mm = null;
			try {
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filename),"GBK"); 
				BufferedWriter writer=new BufferedWriter(write);   
				writer.write(xml); 
				writer.close(); 
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (mm != null) {
					try {
						mm.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
	}

	private String xml210101(String msg_id) {
		FtzInMsgCtl conFtzInMsgCtl = new FtzInMsgCtl();
		conFtzInMsgCtl.setMsgId(msg_id);
		FtzInMsgCtl ftzInMsgCtl = ftz210101Repos.queryFtzInMsgCtl(conFtzInMsgCtl);
		
		FtzInTxnDtl conFtzInTxnDtl = new FtzInTxnDtl(); 
		conFtzInTxnDtl.setMsgId(msg_id);
		List<FtzInTxnDtl> lFtzInTxnDtl = ftz210101Repos.queryFtzInTxnDtlList(conFtzInTxnDtl);
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>").append("\n");
		sb.append("<CFX>").append("\n");
		sb.append("<HEAD>").append("\n");
		sb.append("<VER>").append(ftzInMsgCtl.getVer()).append("</VER>").append("\n");
		sb.append("<SRC>").append(ftzInMsgCtl.getSrc()).append("</SRC>").append("\n");
		sb.append("<DES>").append(ftzInMsgCtl.getDes()).append("</DES>").append("\n");
		sb.append("<APP>").append(ftzInMsgCtl.getApp()).append("</APP>").append("\n");
		sb.append("<MsgNo>").append(ftzInMsgCtl.getMsgNo()).append("</MsgNo>").append("\n");
		sb.append("<MsgID>").append(ftzInMsgCtl.getMsgId()).append("</MsgID>").append("\n");
		sb.append("<MsgRef>").append("").append("</MsgRef>").append("\n");
		sb.append("<WorkDate>").append(ftzInMsgCtl.getWorkDate()).append("</WorkDate>").append("\n");
		sb.append("<EditFlag>").append(ftzInMsgCtl.getEditFlag()).append("</EditFlag>").append("\n");
		sb.append("<Reserve>").append("").append("</Reserve>").append("\n");
		sb.append("</HEAD>").append("\n");
		sb.append("<MSG>").append("\n");
		sb.append("<FTZ210101>").append("\n");
		sb.append("<BATCH>").append("\n");
		sb.append("<AccountNo>").append(ftzInMsgCtl.getAccountNo()).append("</AccountNo>").append("\n");
		sb.append("<AccountName>").append(ftzInMsgCtl.getAccountName()).append("</AccountName>").append("\n");
		sb.append("<AccType>").append(ftzInMsgCtl.getAccType()).append("</AccType>").append("\n");
		sb.append("<DepositRate>").append(ftzInMsgCtl.getDepositRate()).append("</DepositRate>").append("\n");
		sb.append("<CustomType>").append(ftzInMsgCtl.getCustomType()).append("</CustomType>").append("\n");
		sb.append("<BalanceCode>").append(ftzInMsgCtl.getBalanceCode()).append("</BalanceCode>").append("\n");
		sb.append("<DocumentType>").append(ftzInMsgCtl.getDocumentType()).append("</DocumentType>").append("\n");
		sb.append("<DocumentNo>").append(ftzInMsgCtl.getDocumentNo()).append("</DocumentNo>").append("\n");
		sb.append("<Currency>").append(ftzInMsgCtl.getCurrency()).append("</Currency>").append("\n");
		sb.append("<Balance>").append(ftzInMsgCtl.getBalance()).append("</Balance>").append("\n");
		sb.append("<SubmitDate>").append(ftzInMsgCtl.getSubmitDate()).append("</SubmitDate>").append("\n");
		sb.append("<TotalCount>").append(ftzInMsgCtl.getTotalCount()).append("</TotalCount>").append("\n");
		sb.append("<AccOrgCode>").append(ftzInMsgCtl.getAccOrgCode()).append("</AccOrgCode>").append("\n");
		sb.append("</BATCH>").append("\n");
		
		if (lFtzInTxnDtl == null || lFtzInTxnDtl.isEmpty()) {
			sb.append("<TRANLIST/>").append("\n");
		} else {
			sb.append("<TRANLIST>").append("\n");
			
			for (int i = 0; i < lFtzInTxnDtl.size(); i++) {
				FtzInTxnDtl ftzInTxnDtl = lFtzInTxnDtl.get(i);
				
				sb.append("<TRAN>").append("\n");
				sb.append("<SeqNo>").append(ftzInTxnDtl.getSeqNo()).append("</SeqNo>").append("\n");
				sb.append("<CDFlag>").append(ftzInTxnDtl.getCdFlag()).append("</CDFlag>").append("\n");
				sb.append("<TranDate>").append(ftzInTxnDtl.getTranDate()).append("</TranDate>").append("\n");
				sb.append("<OrgTranDate>").append(ftzInTxnDtl.getOrgTranDate()).append("</OrgTranDate>").append("\n");
				sb.append("<Amount>").append(ftzInTxnDtl.getAmount()).append("</Amount>").append("\n");
				sb.append("<OppAccount>").append(ftzInTxnDtl.getOppAccount()).append("</OppAccount>").append("\n");
				sb.append("<OppName>").append(ftzInTxnDtl.getOppName()).append("</OppName>").append("\n");
				sb.append("<OppBankCode>").append(ftzInTxnDtl.getOppBankCode()).append("</OppBankCode>").append("\n");
				sb.append("<CountryCode>").append(ftzInTxnDtl.getCountryCode()).append("</CountryCode>").append("\n");
				sb.append("<DistrictCode>").append(ftzInTxnDtl.getDisitrictCode()).append("</DistrictCode>").append("\n");
				sb.append("<TranType>").append(ftzInTxnDtl.getTranType()).append("</TranType>").append("\n");
				sb.append("<TermLength>").append(ftzInTxnDtl.getTermLength()).append("</TermLength>").append("\n");
				sb.append("<TermUnit>").append(ftzInTxnDtl.getTermUnit()).append("</TermUnit>").append("\n");
				sb.append("<ExpireDate>").append(ftzInTxnDtl.getExpireDate()).append("</ExpireDate>").append("\n");
				sb.append("</TRAN>").append("\n");
			}
			
			sb.append("</TRANLIST>").append("\n");
		}
		
		sb.append("</FTZ210101>").append("\n");
		sb.append("</MSG>").append("\n");
		sb.append("</CFX>").append("\n");
		
		return sb.toString();
	}

	private String xml210301(String msg_id) {
		return null;
	}

}
