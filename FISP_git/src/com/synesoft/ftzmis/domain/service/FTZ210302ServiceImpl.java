package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.repository.FTZ210302Repository;

/**
 * @author 李峰
 * @date 2013-12-29 上午11:55:45
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service
public class FTZ210302ServiceImpl implements FTZ210302Service {

	@Autowired
	private FTZ210302Repository ftz210302Repository;
	
	public Page<FtzOffMsgCtl> queryFtzOffMsgCtlPage(Pageable pageable,
			FtzOffMsgCtl query_FtzOffMsgCtl){
		return ftz210302Repository.queryFtzOffMsgCtlPage(pageable,query_FtzOffMsgCtl);
	}
	
	@Transactional
	public int insertFtzOffMsgCtl(FtzOffMsgCtl insert_FtzOffMsgCtl){
		return ftz210302Repository.insertFtzOffMsgCtl(insert_FtzOffMsgCtl);
	}
	
	public List<FtzOffTxnDtl> queryFtzOffTxnDtlList(
			FtzOffTxnDtl query_FtzOffTxnDtl){
		return ftz210302Repository.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
	}
	
	@Transactional
	public int insertFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl){
		int seqNo = ftz210302Repository.getSeqNo(ftzOffTxnDtl);
		ftzOffTxnDtl.setSeqNo(seqNo);
		return ftz210302Repository.insertFtzOffTxnDtl(ftzOffTxnDtl);
	}
	
	public FtzOffMsgCtl queryFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl){
		return ftz210302Repository.queryFtzOffMsgCtl(ftzOffMsgCtl);
	}

	public Page<FtzOffTxnDtl> queryFtzOffTxnDtlPage(Pageable pageable,
			FtzOffTxnDtl ftzOffTxnDtl){
		return ftz210302Repository.queryFtzOffTxnDtlPage(pageable,ftzOffTxnDtl);
	}
	
	public FtzOffTxnDtl queryFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl){
		return ftz210302Repository.queryFtzOffTxnDtl(ftzOffTxnDtl);
	}
	
	@Transactional
	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl){
		return ftz210302Repository.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
	}
	
	@Transactional
	public int updateFtzOffTxnDtlSelective(FtzOffTxnDtl ftzOffTxnDtl){
		return ftz210302Repository.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
	}
	
	@Transactional
	public int deleteFtzOffTxnDtl(FtzOffTxnDtl del_FtzOffTxnDtl){
		return ftz210302Repository.deleteFtzOffTxnDtl(del_FtzOffTxnDtl);
	}
	
	@Transactional
	public int deleteFtzOffMsgCtl(FtzOffMsgCtl del_FtzOffMsgCtl){
		ftz210302Repository.deleteFtzOffTxnDtl(del_FtzOffMsgCtl);
		return ftz210302Repository.deleteFtzOffMsgCtl(del_FtzOffMsgCtl);
	}
	
	@Transactional
	public int updateFtzOffMsgCtlForSubmit(FtzOffMsgCtl ftzOffMsgCtl){
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
		List<FtzOffTxnDtl> FtzOffTxnDtls = this.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
		if(null == FtzOffTxnDtls || FtzOffTxnDtls.size()<1){
			ftzOffMsgCtl.setMsgStatus("02");
			int i = this.updateFtzOffMsgCtl(ftzOffMsgCtl);
			return i;
		}else{
			int count =0;
			for(FtzOffTxnDtl FtzOffTxnDtl :FtzOffTxnDtls){
				FtzOffTxnDtl.setChkStatus("02");
				this.updateFtzOffTxnDtlSelective(FtzOffTxnDtl);
				count++;
			}
			if(count == FtzOffTxnDtls.size()){
				ftzOffMsgCtl.setMsgStatus("02");
				int i = this.updateFtzOffMsgCtl(ftzOffMsgCtl);
				return i;
			}else{
				return 0;
			}
		}
	}
	
	@Transactional
	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl,
			List<FtzOffTxnDtl> ftzOffTxnDtls){
		if(null ==ftzOffTxnDtls){
			return ftz210302Repository.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
		}else{
			for (FtzOffTxnDtl ftzOffTxnDtl : ftzOffTxnDtls) {
				this.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
			}
			return ftz210302Repository.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
		}
	}
}
